package common

import (
	"encoding/json"
	"errors"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"strconv"
	"strings"
)

func IsExistInLedger(stub shim.ChaincodeStubInterface, key string)  bool{
	bytes, err := stub.GetState(key)
	if err != nil || bytes == nil{
		return false
	}
	return true
}

func GetObjectFromLedger(stub shim.ChaincodeStubInterface, key string, obj interface{}) error {
	bytes, err := stub.GetState(key)
	if err != nil {
		return err
	}
	if bytes == nil {
		return errors.New("key " + key + " is not found in ledger")
	}
	err = json.Unmarshal(bytes, obj)
	if err != nil {
		return err
	}
	return nil
}

//找不到记录不返回error，而是返回nil。适用于找不到记录也不需要报错的场景。
func GetObjectFromLedgerNotFound(stub shim.ChaincodeStubInterface, key string, obj interface{}) ([]byte, error) {
	bytes, err := stub.GetState(key)
	if err != nil {
		return nil, err
	}
	if bytes == nil {
		fmt.Printf("key is not found in ledger", key)
		return nil, nil
	}
	err = json.Unmarshal(bytes, obj)
	if err != nil {
		return nil, err
	}
	return bytes, nil
}

func PutObjectToLedger(stub shim.ChaincodeStubInterface, key string, obj interface{}) error {
	bytes, err := json.Marshal(obj)
	if err != nil {
		return err
	}
	err = stub.PutState(key, bytes)
	if err != nil {
		return err
	}
	return nil
}

func CreateSeqno(stub shim.ChaincodeStubInterface, seqType int) (int64, error) { //seqType 序列号类型：1-存证编号 2-交易编号
	var seqStr string
	var curSeqno int64
	var nxtSeqno int64
	var increment int64 = 1
	switch seqType {
	case 1:
		bytes, err := stub.GetState(SEQUENCE_EVIDENCE_NO)
		if err != nil {
			return -1, err
		}

		if bytes == nil {
			seqStr = "1"
		} else {
			seqStr = string(bytes)
		}
		curSeqno, err = strconv.ParseInt(seqStr, 10, 64)
		if err != nil {
			return -1, err
		}
		nxtSeqno = curSeqno + increment
		err = PutObjectToLedger(stub, SEQUENCE_EVIDENCE_NO, nxtSeqno)
		if err != nil {
			return -1, err
		}

	case 2:
		bytes, err := stub.GetState(SEQUENCE_TRANSACTION_NO)
		if err != nil {
			return -1, err
		}

		if bytes == nil {
			seqStr = "1"
		} else {
			seqStr = string(bytes)
		}

		curSeqno, err = strconv.ParseInt(seqStr, 10, 64)
		if err != nil {
			return -1, err
		}
		nxtSeqno = curSeqno + increment
		err = PutObjectToLedger(stub, SEQUENCE_TRANSACTION_NO, nxtSeqno)
		if err != nil {
			return -1, err
		}
	default:
		return -1, errors.New("Transaction type is not correct. Failed to get sequence number")

	}
	return curSeqno, nil
}

func StrLeftZero(str string, length int) string {
	str = strings.TrimSpace(str)
	strLen := len(str)
	if strLen >= length {
		return str
	} else {
		for i := strLen; i < length; i++ {
			str = "0" + str
		}
		return str
	}
}
