/*
调试与诊断模块
注意：diagnostic文件内的函数仅作系统维护、故障排查用途
不要在正常业务逻辑中调用
*/
package common

import (
	"encoding/json"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)

/*导出worldstate内容
  输入：nil
  输出：key-value对
*/

func ExportWorldState(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	type KeyValue struct {
		Key   string `json:"key"`
		Value string `json:"value"`
	}

	start_key := "A" //ledger header is `FPTP_XXXX`
	end_key := "Z"

	iter, err := stub.RangeQueryState(start_key, end_key)
	if err != nil {
		return nil, err
	}

	var objs []KeyValue
	var kv KeyValue
	for iter.HasNext() {
		key, value, err := iter.Next()
		if err != nil {
			return nil, err
		}
		kv.Key = key
		kv.Value = string(value)
		objs = append(objs, kv)
	}
	bytes, err := json.Marshal(&objs)
	return bytes, nil
}

/*向worldstate插入一条记录
  输入：args[0] key
       args[1] value
  输出：nil
*/

func InsertWorldState(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	key := args[0]
	value := args[1]

	err := stub.PutState(key, []byte(value))
	if err != nil {
		return nil, err
	}
	return nil, nil
}

/*导入worldstate
  输入：args[0] key-value对
  输出：nil
*/
func ImportWorldState(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	type KeyValue struct {
		Key   string `json:"key"`
		Value string `json:"value"`
	}
	var objs []KeyValue
	err := json.Unmarshal([]byte(args[0]), &objs)
	if err != nil {
		return nil, err
	}
	for _, obj := range objs {
		err = stub.PutState(obj.Key, []byte(obj.Value))
		if err != nil {
			return nil, err
		}
	}
	return nil, nil
}

/*从worldstate删除一条记录
  输入：args[0] key
  输出：nil
*/
func DeleteWorldState(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	key := args[0]

	err := stub.DelState(key)
	if err != nil {
		return nil, err
	}
	return nil, nil
}

/*清空worldstate所有记录
  输入：nil
  输出：nil
*/
func PurgeWorldState(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	type KeyValue struct {
		Key   string `json:"key"`
		Value string `json:"value"`
	}

	start_key := "E" //ledger header is `FPTP_XXXX`
	end_key := "G"

	iter, err := stub.RangeQueryState(start_key, end_key)
	if err != nil {
		return nil, err
	}

	for iter.HasNext() {
		key, _, err := iter.Next()
		if err != nil {
			return nil, err
		}
		err = stub.DelState(key)
		if err != nil {
			return nil, err
		}
	}
	return nil, nil
}
