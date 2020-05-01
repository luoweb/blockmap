package cpt

import (
	"did-contract/common"
	"did-contract/common/code"
	. "did-contract/functions/did"
	"did-contract/http"
	"did-contract/structs"
	"encoding/json"
	"errors"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"log"
)

func RegisterCpt(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	log.Println("Function RegisterCpt: get request.")

	var input http.RegisterCptInput
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
	log.Println("Function RegisterCpt: json format is correct.")

	if !IsDidExist(stub, input.CptMetaData.CptPublisher) {
		output.ErrorCode = code.CODE_CPT_PUBLISHER_NOT_EXIST
		output.ErrorMsg = code.MSG_CPT_PUBLISHER_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	cptId := input.CptBaseInfo.CptId

	dataKeyRegister := common.CPT_KEY + string(cptId)

	err = common.PutObjectToLedger(stub, dataKeyRegister, &input)
	if err != nil {
		return nil, err
	}

	log.Println("Function RegisterCpt: already save the data under dataKey: " + dataKeyRegister)

	output.ErrorCode = ""
	output.ErrorMsg = ""

	log.Println("Function RegisterCpt: function is finished")
	return json.Marshal(output)
}

func UpdateCpt(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	log.Println("Function UpdateCpt: get request.")

	var input http.UpdateCptInput
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
	log.Println("Function UpdateCpt: json format is correct.")

	if !IsDidExist(stub, input.CptMetaData.CptPublisher) {
		output.ErrorCode = code.CODE_DID_NOT_EXIST
		output.ErrorMsg = code.MSG_DID_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	if !IsCptExist(stub, string(input.CptBaseInfo.CptId)) {
		output.ErrorCode = code.CODE_CPT_NOT_EXIST
		output.ErrorMsg = code.MSG_CPT_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	cptKey := common.CPT_KEY + string(input.CptBaseInfo.CptId)

	var cptOld structs.Cpt

	common.GetObjectFromLedger(stub, cptKey, &cptOld)

	cptVersion := cptOld.CptBaseInfo.CptVersion
	input.CptBaseInfo.CptVersion = cptVersion + 1

	err = common.PutObjectToLedger(stub, cptKey, &input)
	if err != nil {
		return nil, err
	}
	log.Println("Function UpdateCpt: already save the data under dataKey: " + cptKey)

	output.ErrorCode = ""
	output.ErrorMsg = ""

	log.Println("Function UpdateCpt: function is finished")
	return json.Marshal(output)
}

func QueryCpt(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	log.Println("Function QueryCpt: get request.")

	var input http.QueryCptInput
	var output http.QueryCptOutput
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
	log.Println("Function QueryCpt: json format is correct.")

	var cpt structs.Cpt
	dataKey := common.CPT_KEY + string(input.CptId)
	log.Println("Function QueryCpt: qryKey is " + dataKey)
	dataBytes, err := common.GetObjectFromLedgerNotFound(stub, dataKey, &cpt)
	if err != nil {
		return nil, err
	}

	if dataBytes == nil {
		output.ErrorCode = code.CODE_CPT_NOT_EXIST
		output.ErrorMsg = code.MSG_CPT_NOT_EXIST
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}

	output.ErrorCode = ""
	output.ErrorMsg = ""
	output.QueryResult = cpt
	outBytes, err = json.Marshal(output)
	if err != nil {
		return nil, err
	}
	log.Println("Function QueryCpt: function is finished")
	return outBytes, nil
}
func IsCptExist(stub shim.ChaincodeStubInterface, cptId string)  bool{

	var cpt structs.Cpt
	dataKey := common.CPT_KEY + cptId
	log.Println("Function IsCptExist: qryKey is " + dataKey)
	dataBytes, err := common.GetObjectFromLedgerNotFound(stub, dataKey, &cpt)
	if err != nil || dataBytes == nil{
		return false
	}
	return true
}