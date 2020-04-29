package authority

import (
	. "did-contract/common"
	"did-contract/common/code"
	. "did-contract/functions/did"
	. "did-contract/http"
	. "did-contract/structs"
	"encoding/json"
	"errors"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"log"
)

func isAuthorityIssuer(stub shim.ChaincodeStubInterface, address string) (bool, error) {
	if IsDidExist(stub, address) == false {
		output := BaseResult{code.CODE_DID_NOT_EXIST, code.MSG_DID_NOT_EXIST}
		outBytes, err := json.Marshal(output)
		if err != nil {
			return false, err
		}
		return false, errors.New(string(outBytes))
	}
	key := AUTHORITY_ISSUER_PREFIX + address
	_, err := stub.GetState(key)
	if err != nil {
		return false, err
	}
	return true, nil
}

func registerAuthorityIssuer(stub shim.ChaincodeStubInterface, address string, obj interface{}) error {
	if IsDidExist(stub, address) == false {
		return errors.New("address " + address + " is not found in ledger")
	}
	err := updateAuthorityIssuerAddressList(stub, address)
	if err != nil {
		return err
	}
	key := AUTHORITY_ISSUER_PREFIX + address
	err = PutObjectToLedger(stub, key, obj)
	if err != nil {
		return err
	}
	log.Println("Function registerAuthorityIssuer: already save the data under dataKey: " + key)
	return nil
}

func removeAuthorityIssuer(stub shim.ChaincodeStubInterface, address string) error {
	//删除AuthorityIssuer信息
	key := AUTHORITY_ISSUER_PREFIX + address
	err := stub.DelState(key)
	if err != nil {
		return err
	}
	//删除AuthorityIssuer AddressList中的地址
	var list AddressList
	err = GetObjectFromLedger(stub, AUTHORITY_ISSUER_LIST, list)
	if err != nil {
		return err
	}
	for i := 0; i < len(list.addresses); i++ {
		if list.addresses[i] == address {
			list.addresses = append(list.addresses[:i], list.addresses[i+1:]...)
			break
		}
	}
	//删除SpecificIssuer AddressList中的地址
	var authorityIssuer AuthorityIssuer
	err = GetObjectFromLedger(stub, AUTHORITY_ISSUER_PREFIX+address, authorityIssuer)
	if err != nil {
		return err
	}
	if authorityIssuer.Type == "" {
		return nil
	}
	err = GetObjectFromLedger(stub, SPECIFIC_ISSUER_TYPE_LIST_PREFIX+authorityIssuer.Type, list)
	if err != nil {
		return err
	}
	for i := 0; i < len(list.addresses); i++ {
		if list.addresses[i] == address {
			list.addresses = append(list.addresses[:i], list.addresses[i+1:]...)
			break
		}
	}
	return nil
}

func getAuthorityIssuerAddressList(stub shim.ChaincodeStubInterface, input GetAuthorityIssuerAddressListInput) (GetAuthorityIssuerAddressListOutput, error) {
	var list AddressList
	var output GetAuthorityIssuerAddressListOutput
	_, err := GetObjectFromLedgerNotFound(stub, AUTHORITY_ISSUER_LIST, list)
	if err != nil {
		return GetAuthorityIssuerAddressListOutput{}, err
	}
	for i := input.Index; i < input.Index+input.Num; i++ {
		var address string
		err = GetObjectFromLedger(stub, list.addresses[i], address)
		if err != nil {
			return GetAuthorityIssuerAddressListOutput{}, err
		}
		var authorityIssuer AuthorityIssuer
		err = GetObjectFromLedger(stub, AUTHORITY_ISSUER_PREFIX+address, authorityIssuer)
		if err != nil {
			return GetAuthorityIssuerAddressListOutput{}, err
		}
		output.AuthorityIssuers = append(output.AuthorityIssuers, authorityIssuer)
	}
	return output, nil
}

func removeIssuer(stub shim.ChaincodeStubInterface, input RemoveIssuerInput) error{
	key := SPECIFIC_ISSUER_TYPE_LIST_PREFIX + input.Type
	var list AddressList
	err := GetObjectFromLedger(stub, key, list)
	if err != nil {
		return err
	}
	for i := 0; i < len(list.addresses); i++ {
		if list.addresses[i] == input.Address {
			list.addresses = append(list.addresses[:i], list.addresses[i+1:]...)
			break
		}
	}
	return nil
}

func updateAuthorityIssuerAddressList(stub shim.ChaincodeStubInterface, address string) error {
	var list AddressList
	_, err := GetObjectFromLedgerNotFound(stub, AUTHORITY_ISSUER_LIST, list)
	if err != nil {
		return err
	}
	list.addresses = append(list.addresses, address)
	err = PutObjectToLedger(stub, AUTHORITY_ISSUER_LIST, &list)
	return err
}
