package http

import "did-contract/structs"

/**
struct did
*/

type CreateDidInput struct {
	structs.DidDocument
}

type SetAttributeInput struct {
	structs.DidDocument
}

type QueryDidInput struct {
	Id string `json:"did"` //did编号
}

/**
struct cpt
*/

type RegisterCptInput struct {
	structs.Cpt
}

type UpdateCptInput struct {
	structs.Cpt
}

type QueryCptInput struct {
	CptId int32 `json:"cptId"`
}

/**
struct authority
*/

type RegisterAuthorityIssuerInput struct {
	AuthorityIssuer structs.AuthorityIssuer `json:"authorityIssuer"`
	DidPrivateKey   structs.DidPrivateKey   `json:"didPrivateKey"`
}

type GetAuthorityIssuerAddressListInput struct {
	Index int32 `json:"index"`
	Num   int32 `json:"num"`
}
type RemoveIssuerInput struct {
	Address string `json:"address"`
	Type    string `json:"type"`
}

type IsSpecificTypeIssuerInput struct {
	IssuerType string `json:"issuerType"`
	Address    string `json:"address"`
}
