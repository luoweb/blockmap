package structs

type DidDocument struct {
	Id              string                  `json:"id"`
	Created         string                  `json:"created"` //创建时间
	Updated         string                  `json:"updated"` //更新时间
	PublicKeys      []PublicKeyProperty     `json:"publicKeys"`
	Authentications []AuthenticationProerty `json:"authentications"`
	Services        []Services              `json:"services"`
}

type PublicKeyProperty struct {
	Id        string `json:"id"`
	Type      string `json:"type"`
	Owner     string `json:"owner"`
	PublicKey string `json:"publicKey"`
}

type AuthenticationProerty struct {
	Type      string `json:"type"`
	PublicKey string `json:"publicKey"`
}

type Services struct {
	Type            string `json:"type"`
	ServiceEndpoint string `json:"serviceEndpoint"`
}
