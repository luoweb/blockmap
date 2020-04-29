package main

import (
	"encoding/json"
	"fmt"
)

type BaseResult struct {
	ErrorCode string `json:"errorCode"`    //错误代码
	ErrorMsg  string `json:"errorMessage"` //错误信息
}

func main() {

	bytes, err := json.Marshal(&BaseResult{"11", "da"})
	if err != nil {
		fmt.Println("Marshal has error")
	}
	var res BaseResult
	err = json.Unmarshal(bytes, &res)
	if err != nil {
		fmt.Println("Unmarshal has error")
	}
	fmt.Println(res)

}
