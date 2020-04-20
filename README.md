# BlockMap

- Regular epidemic situation browsing query function, epidemic situation point, personnel heat map, graded color display
- Evaluation of resumption of work: selection of resumption of bank outlets, analysis of epidemic situation, population density, and convenient transportation
- The best way to work: recommend the shortest path + risk avoidance path
- Risk diffusion trend map: personnel migration map, generating migration map based on heat map

### Application Architecture

![app_arch](http://github.roweb.cn/mapblock/public/assets/app_arch_v1.png)

### Data Architecture

![data_arch](http://github.roweb.cn/mapblock/public/assets/data_arch_v1.png)

## Frontend Architecture

- web(nginx):
  vuejs + openlayers.js
- app(ios&android):
  cordova + h5 + js

## Backend Architecture

### Geo server Service

- Publish map service: basic map layer, epidemic point layer, road layer,
- Display map information: map browsing, epidemic situation
- Spatial analysis: shortest path analysis, epidemic trend analysis, resumption assessment

### App server Service

- Import data into map service: automatically import into GeoServer through interface
- Call big data analysis interface: front-end call data real-time analysis and prediction service
- Front-end web and app interface: support front-end page and APP data exchange

### Data Storage Service

- PostgreSQL/MySQL
- BigData/IBM Watson

# BlockChain Health QrCode

## Trusted digital identity scheme architecture

Trusted digital identity service, relying on mature blockchain technology, provides stable and complete distributed digital identity and verifiable statement full-cycle management to support business scenarios in finance, e-government, medical and other industries.

![ar](http://github.roweb.cn/mapblock/public/assets/ar.png)

## Anti-epidemic QR code of trusted digital identity

Users can truly rely on the ownership of digital identity without relying on a three-party central organization. At the same time, users can verify their identity while personal privacy information is highly protected.

![ibm-qr](http://github.roweb.cn/mapblock/public/assets/ibm-qr.png)

## Getting started with the QR code for epidemic prevention

### Steps for usage

- New blockchain network
- Deploy trusted digital identity blockchain smart contracts
- Call SDK

### Video demo

|                                                                User presents epidemic prevention QrCode                                                                |                                                             Institutional verification of epidemic prevention QrCode                                                             |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
| [![The user presents a video preview of the anti-epidemic QR code](http://github.roweb.cn/mapblock/public/assets/show.jpg)](http://q8mix8qp7.bkt.clouddn.com/show.mp4) | [![The user presents a video preview of the anti-epidemic QR code](http://github.roweb.cn/mapblock/public/assets/verify.jpg)](http://q8mix8qp7.bkt.clouddn.com/verification.mp4) |

# Tech Stack

- Kubernetes
- DB(MySQL/PostreSQL)
- Hyperledger Fabric(BlockChain)
- GIS(Geoserver)
- Frontend(Nodejs/Vuejs)
- Backend(Java/Golang)
- CD(DevOps)
