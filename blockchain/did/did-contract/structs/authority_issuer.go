package structs

type AuthorityIssuer struct {
	Did      string `json:"did"`
	Name     string `json:"name"`
	Created  int32  `json:"created"`
	AccValue string `json:"accValue"`
	Type     string `json:"type"`
}

type DidPrivateKey struct {
	PrivateKey string `json:"privateKey"`
}
