package authority

import (
	"did-contract/common/code"
	"did-contract/http"
	"encoding/json"
	"errors"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"log"
	"time"
)

func IsAuthorityIssuer(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function IsAuthorityIssuer: get request.")
	var input string //address
	var outBytes []byte
	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output := http.BaseResult{code.CODE_ERROR_JSON, code.MSG_ERROR_JSON}
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function IsAuthorityIssuer: json format is correct.")
	flag, err := isAuthorityIssuer(stub, input)
	if err != nil {
		return nil, err
	} else {
		output := http.IsAuthorityIssuerOutput{flag,http.BaseResult{"",""}}
		log.Println("Function IsAuthorityIssuer: function is finished")
		return json.Marshal(output)
	}
}

func AddAuthorityIssuer(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function RegisterAuthorityIssuer: get request.")
	var input http.RegisterAuthorityIssuerInput
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
	log.Println("Function RegisterAuthorityIssuer: json format is correct.")
	//存db
	input.AuthorityIssuer.Created = int32(time.Now().UnixNano() / 1e6)
	err = registerAuthorityIssuer(stub, input.AuthorityIssuer.Did, &input)
	if err != nil {
		return nil, err
	}
	output.ErrorCode = ""
	output.ErrorMsg = ""
	log.Println("Function RegisterAuthorityIssuer: function is finished")
	return json.Marshal(output)
}

func RemoveAuthorityIssuer(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function RemoveAuthorityIssuer: get request.")
	var input string //address
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
	log.Println("Function RemoveAuthorityIssuer: json format is correct.")
	//判断
	err = removeAuthorityIssuer(stub, input)
	if err != nil {
		return nil, err
	}
	output.ErrorCode = ""
	output.ErrorMsg = ""
	log.Println("Function RemoveAuthorityIssuer: function is finished")
	return json.Marshal(output)
}

func GetAuthorityIssuerAddressList(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function GetAuthorityIssuerAddressList: get request.")
	var input http.GetAuthorityIssuerAddressListInput //address
	var outBytes []byte
	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output := http.BaseResult{code.CODE_ERROR_JSON, code.MSG_ERROR_JSON}
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function GetAuthorityIssuerAddressList: json format is correct.")
	output, err := getAuthorityIssuerAddressList(stub, input)
	if err != nil {
		return nil, err
	}
	log.Println("Function GetAuthorityIssuerAddressList: function is finished")
	return json.Marshal(output)
}

func RemoveIssuer(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function RemoveIssuer: get request.")
	var input http.RemoveIssuerInput //address
	var outBytes []byte
	err := json.Unmarshal([]byte(args[0]), &input)
	if err != nil {
		output := http.BaseResult{code.CODE_ERROR_JSON, code.MSG_ERROR_JSON}
		outBytes, err = json.Marshal(output)
		if err != nil {
			return nil, err
		}
		return nil, errors.New(string(outBytes))
	}
	log.Println("Function RemoveIssuer: json format is correct.")
	err = removeIssuer(stub, input)
	if err != nil {
		return nil, err
	}
	log.Println("Function RemoveIssuer: function is finished")
	return json.Marshal(&http.BaseResult{})
}

func IsSpecificTypeIssuer(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	//请求参数反序列化
	log.Println("Function IsSpecificTypeIssuer: get request.")
	var input http.IsSpecificTypeIssuerInput
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
	log.Println("Function IsSpecificTypeIssuer: json format is correct.")
	//判断
	return nil, nil
}

func isIssuerTypeExist(stub shim.ChaincodeStubInterface, name string) bool {
	return false
}
