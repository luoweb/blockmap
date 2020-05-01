package structs

type Cpt struct {
	CptJsonSchema string      `json:"cptJsonSchema"` //store json schema
	CptBaseInfo   CptBaseInfo `json:"cptBaseInfo"`
	CptMetaData   CptMetaData `json:"cptMetaData"`
}

type CptBaseInfo struct {
	CptId      int32 `json:"cptId"`
	CptVersion int32 `json:"cptVersion"`
}

type CptMetaData struct {
	CptPublisher string `json:"cptPublisher"`
	CptSignature string `json:"cptSignature"`
	Updated      string  `json:"updated"`
	Created      string  `json:"created"`
}
