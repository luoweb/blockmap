package http

import "did-contract/structs"

type BaseResult struct {
	ErrorCode string `json:"errorCode"`    //错误代码
	ErrorMsg  string `json:"errorMessage"` //错误信息
}

/**
struct did
 */

type QueryDidOutput struct {
	BaseResult
	QueryResult structs.DidDocument `json:"queryResult"` //查询结果
}

/**
struct cpt
 */


type QueryCptOutput struct {
	BaseResult
	QueryResult structs.Cpt `json:"queryResult"`
}

/**
struct authority
 */


type GetAuthorityIssuerAddressListOutput struct {
	BaseResult
	AuthorityIssuers []structs.AuthorityIssuer `json:"authorityIssuers"`
}

type IsAuthorityIssuerOutput struct{
	Flag bool `json:"flag"`
	BaseResult
}
