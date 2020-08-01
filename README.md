# BlockMap

[static_page]: http://github.roweb.cn/mapblock

- Regular epidemic situation browsing query function, epidemic situation point, personnel heat map, graded color display
- Evaluation of resumption of work: selection of resumption of bank outlets, analysis of epidemic situation, population density, and convenient transportation
- The best way to work: recommend the shortest path + risk avoidance path
- Risk diffusion trend map: personnel migration map, generating migration map based on heat map

#### BlockMap Manual Video Induction：

[![BlockMap Manual Video Induction](http://github.roweb.cn/mapblock/public/assets/blockmap_manual_v3.png)](https://beyondsoftcloud-my.sharepoint.cn/personal/localization-01_beyondsoftcloud_partner_onmschina_cn/Documents/DW020/temp0728/send/BlockMap_New_V5_Subtitle.mp4)

### R & D RoadMap

![roadmap_en](http://github.roweb.cn/mapblock/public/assets/roadmap_en.png)

### Application Architecture

![app_arch_en](http://github.roweb.cn/mapblock/public/assets/app_arch_en_v1.png)

### Data Architecture

![data_arch_en](http://github.roweb.cn/mapblock/public/assets/data_arch_en_v1.png)

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

![ar](https://github.com/luoweb/blockmap/blob/master/public/assets/ar.png)

## Anti-epidemic QR code of trusted digital identity

Users can truly rely on the ownership of digital identity without relying on a three-party central organization. At the same time, users can verify their identity while personal privacy information is highly protected.

![ibm-qr](https://github.com/luoweb/blockmap/blob/master/public/assets/ibm-qr.png)

## Getting started with the QR code for epidemic prevention

### Steps for usage

- New blockchain network
- Deploy trusted digital identity blockchain smart contracts
- Call SDK

### Video demo

|                                                         User presents epidemic prevention QrCode                                                          |                                                      Institutional verification of epidemic prevention QrCode                                                       |
| :-------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------------------------------: |
| [![The user presents a video preview of the anti-epidemic QR code](http://github.roweb.cn/mapblock/public/assets/show.jpg)](http://pic.6and.ltd/show.mp4) | [![The user presents a video preview of the anti-epidemic QR code](http://github.roweb.cn/mapblock/public/assets/verify.jpg)](http://pic.6and.ltd/verification.mp4) |

# Tech Stack

- Kubernetes/Docker/Registry
- Cloud DB(MySQL/PostgreSQL with PostGIS)
- BlockChain(IBM IBP/Hyperledger Fabric)
- OpenGIS(Geoserver with OGC)
- Frontend(Nodejs/Vuejs/iView)
- Backend (Java/Golang/Maven)
- CD/DevOps(IBM ToolChain/Shell)
- AI/BigData(IBM Watson)
