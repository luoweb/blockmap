# blockmap(疫情地图)

- 常规疫情浏览查询功能，疫情点，人员热力图，颜色显示
- 复工评估：银行网点复工选择，通过疫情点，人口密度，交通便利情况分析
- 最佳上班路径：推荐最短路径+风险规避路径
- 风险扩散趋势图：人员迁徙图，根据热力图生成迁徙地图

## frontend
- vuejs + openlayer


## backend
### geo server 地图服务
- 发布地图服务：基础地图图层，疫情点图层，道路图层，
- 展示地图信息
- 空间分析：最短路径分析，疫情趋势分析，复工评估
  
### app server 应用服务
- 导入数据到地图服务-- 通过接口自动导入到GeoServer中
- 调用大数据分析接口-- 前端调用数据实时分析预测服务
  
### 数据存储：
- PostgreSQL/MySQL
- BigData/IBM Watson

# blockchain 区块链健康码


## 2.1 可信数字身份方案架构

可信数字身份服务，依托于成熟的区块链技术，提供稳定完善的分布式数字身份和可验证声明的全周期管理，用以支持金融、电子政务、医疗等行业的业务场景。

![ar](https://github.com/luoweb/blockmap/blob/master/res/ar.png)

## 2.2 可信数字身份防疫二维码

用户可以不依赖三方中心机构，真正掌握数字身份所有权。同时，用户可在个人隐私信息受到高度保护的情况下进行身份验证。

![ibm-qr](https://github.com/luoweb/blockmap/blob/master/res/ibm-qr.png)

## 2.3 防疫二维码方案使用入门

### 2.3.1 使用步骤

- 新建区块链网络
- 部署可信数字身份区块链智能合约
- 调用SDK

### 2.3.2 视频演示

|                   用户出示防疫二维码                    |                     机构验证防疫情二维码                     |
| :-----------------------------------------------------: | :----------------------------------------------------------: |
| [![用户出示防疫二维码视频预览](https://github.com/luoweb/blockmap/blob/master/res/show.jpg)](http://q8mix8qp7.bkt.clouddn.com/show.mp4)| [![用户出示防疫二维码视频预览](https://github.com/luoweb/blockmap/blob/master/res/verify.jpg)](http://q8mix8qp7.bkt.clouddn.com/verification.mp4) |

