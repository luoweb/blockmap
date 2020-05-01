package main

import (
	"did-contract/common"
	. "did-contract/functions/authority"
	. "did-contract/functions/cpt"
	. "did-contract/functions/did"
	"errors"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type DID struct {
}

func (t *DID) Init(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	return nil, nil
}

func (t *DID) Invoke(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	var resBytes []byte
	var err error

	switch function {
	case "CreateDid":
		resBytes, err = CreateDid(stub, args)
	case "setAttribute":
		resBytes, err = SetAttribute(stub, args)
	case "RegisterCpt":
		resBytes, err = RegisterCpt(stub, args)
	case "UpdateCpt":
		resBytes, err = UpdateCpt(stub, args)
	case "AddAuthorityIssuer":
		resBytes, err = AddAuthorityIssuer(stub, args)
	case "RemoveAuthorityIssuer":
		resBytes, err = RemoveAuthorityIssuer(stub, args)
	default:
		err = errors.New("function code " + function + " is not defined")
	}

	return resBytes, err
}

func (t *DID) Query(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	switch function {
	case "ExportWorldState":
		return common.ExportWorldState(stub, args)
	case "QueryDid":
		return QueryDid(stub, args)
	case "QueryCpt":
		return QueryCpt(stub, args)
	case "IsAuthorityIssuer":
		return IsAuthorityIssuer(stub, args)
	case "GetAuthorityIssuerAddressList":
		return GetAuthorityIssuerAddressList(stub, args)
	default:
		fmt.Printf("function code %s is not defined", function)
	}
	return nil, errors.New("function code " + function + " is not defined")
}

func main() {
	err := shim.Start(new(DID))
	if err != nil {
		fmt.Printf("Error starting Chaincode: %s", err)
	}
}
