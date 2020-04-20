# BlockMap 疫情地图

- 常规疫情浏览查询功能，疫情点，人员热力图，颜色显示
- 复工评估：银行网点复工选择，通过疫情点，人口密度，交通便利情况分析
- 最佳上班路径：推荐最短路径+风险规避路径
- 风险扩散趋势图：人员迁徙图，根据热力图生成迁徙地图

### Application Architecture (应用架构图)

![app_arch](http://github.roweb.cn/mapblock/public/assets/app_arch_v1.png)

### Data Architecture (数据架构图)

![data_arch](http://github.roweb.cn/mapblock/public/assets/data_arch_v1.png)

## Frontend (前端架构)

- web(nginx):
  vuejs + openlayers.js
- app(ios&android):
  cordova + h5 + js

## Backend （后端架构）

### Geo server (地图服务)

- 发布地图服务：基础地图图层，疫情点图层，道路图层，
- 展示地图信息: 地图浏览，疫情点
- 空间分析：最短路径分析，疫情趋势分析，复工评估

### App server (应用服务)

- 导入数据到地图服务：通过接口自动导入到 GeoServer 中
- 调用大数据分析接口：前端调用数据实时分析预测服务
- 前端 web 和 app 接口：支持前端页面和 APP 数据交换

### Data server (数据存储)

- PostgreSQL/MySQL
- BigData/IBM Watson

# BlockChain QrCode (区块链健康码)

## 2.1 可信数字身份方案架构

可信数字身份服务，依托于成熟的区块链技术，提供稳定完善的分布式数字身份和可验证声明的全周期管理，用以支持金融、电子政务、医疗等行业的业务场景。

![ar](http://github.roweb.cn/mapblock/public/assets/ar.png)

## 2.2 可信数字身份防疫二维码

用户可以不依赖三方中心机构，真正掌握数字身份所有权。同时，用户可在个人隐私信息受到高度保护的情况下进行身份验证。

![ibm-qr](http://github.roweb.cn/mapblock/public/assets/ibm-qr.png)

## 2.3 防疫二维码方案使用入门

### 2.3.1 使用步骤

- 新建区块链网络
- 部署可信数字身份区块链智能合约
- 调用 SDK

### 2.3.2 视频演示

|                                                         用户出示防疫二维码                                                         |                                                             机构验证防疫情二维码                                                             |
| :--------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------: |
| [![用户出示防疫二维码视频预览](http://github.roweb.cn/mapblock/public/assets/show.jpg)](http://q8mix8qp7.bkt.clouddn.com/show.mp4) | [![用户出示防疫二维码视频预览](http://github.roweb.cn/mapblock/public/assets/verify.jpg)](http://q8mix8qp7.bkt.clouddn.com/verification.mp4) |

# Tech Stack

- Kubernetes
- DB(MySQL/PostreSQL)
- Hyperledger Fabric(BlockChain)
- GIS(Geoserver)
- Frontend(Nodejs/Vuejs)
- Backend(Java/Golang)
- CD(DevOps)
