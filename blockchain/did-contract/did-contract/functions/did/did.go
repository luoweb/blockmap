package did

import (
	"did-contract/common"
	"did-contract/common/code"
	"did-contract/http"
	"did-contract/structs"
	"encoding/json"
	"errors"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"log"
)

/*
输入：
输出：
*/
func CreateDid(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	log.Println("Function CreateDid: get request.")

	var input http.CreateDidInput
	var output http.BaseResult
	var outBytes []byte

	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output.ErrorCode = code.CODE_ERROR_JSON
		output.ErrorMsg = code.MSG_ERROR_JSON
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function CreateDid: json format is correct.")

	dataKeyCreate := common.DID_KEY + input.Id

	err = common.PutObjectToLedger(stub, dataKeyCreate, &input)
	if err != nil {
		return nil, err
	}
	log.Println("Function CreateDid: already save the data under dataKey: " + dataKeyCreate)

	output.ErrorCode = ""
	output.ErrorMsg = ""

	log.Println("Function CreateDid: function is finished")
	return json.Marshal(output)
}

func SetAttribute(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	log.Println("Function SetAttribute: get request.")

	var input http.SetAttributeInput
	var output http.BaseResult
	var outBytes []byte

	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output.ErrorCode = code.CODE_ERROR_JSON
		output.ErrorMsg = code.MSG_ERROR_JSON
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function SetAttribute: json format is correct.")

	var did structs.DidDocument

	dataKey := common.DID_KEY + did.Id
	dataBytes, err := common.GetObjectFromLedgerNotFound(stub, dataKey, &did)
	if err != nil {
		return nil, err
	}

	if dataBytes == nil {
		output.ErrorCode = code.CODE_DID_NOT_EXIST
		output.ErrorMsg = code.MSG_DID_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	//更新did属性
	did.PublicKeys = input.PublicKeys
	did.Authentications = input.Authentications
	did.Services = input.Services

	err = common.PutObjectToLedger(stub, dataKey, &did)
	if err != nil {
		return nil, err
	}
	log.Println("Function SetAttribute: already update the data under dataKey: " + dataKey)

	output.ErrorCode = ""
	output.ErrorMsg = ""
	log.Println("Function SetAttribute: function is finished")
	return json.Marshal(output)
}

/*查询存证：
输入：
输出：
*/
func QueryDid(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	log.Println("Function QueryDid: get request.")

	var input http.QueryDidInput
	var output http.QueryDidOutput
	var outBytes []byte

	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output.ErrorCode = code.CODE_ERROR_JSON
		output.ErrorMsg = code.MSG_ERROR_JSON
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function QueryDid: json format is correct.")

	var did structs.DidDocument
	dataKey := common.DID_KEY + input.Id
	log.Println("Function QueryDid: qryKey is " + dataKey)
	dataBytes, err := common.GetObjectFromLedgerNotFound(stub, dataKey, &did)
	if err != nil {
		return nil, err
	}

	if dataBytes == nil {
		output.ErrorCode = code.CODE_DID_NOT_EXIST
		output.ErrorMsg = code.MSG_DID_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	output.ErrorCode = ""
	output.ErrorMsg = ""
	output.QueryResult = did
	outBytes, err = json.Marshal(output)
	if err != nil {
		return nil, err
	}
	log.Println("Function QueryDid: function is finished")
	return outBytes, nil
}

func IsDidExist(stub shim.ChaincodeStubInterface, id string)  bool{
	var did structs.DidDocument
	dataKey := common.DID_KEY + id
	log.Println("Function IsDidExist: qryKey is " + dataKey)
	dataBytes, err := common.GetObjectFromLedgerNotFound(stub, dataKey, &did)
	if err != nil || dataBytes == nil{
		return false
	}
	return true
}