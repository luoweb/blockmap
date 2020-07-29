<template>
  <div id="home"> <!-- 手机适配版地图 -->
    <button v-on:click="iconDo" class="choose"> <!-- 图层类型控制按钮 -->
      <img src="../assets/button.png" alt="no resource" width="32" height="32"/>
    </button>
    <yd-popup position="left" width="60%" v-model="decisionLeft"> <!-- 左边导航弹窗 -->
      <div class="popup_left">
        <yd-flexbox> <!-- 用户名显示 -->
          <img src="../assets/portrait_xiaoxin.jpg" alt="no resource" width="64" height="64"/>
          <!-- <yd-icon name="ucenter" color="#A9A9A9" size="1rem"></yd-icon> -->
          <yd-flexbox-item style="font-size: 0.3rem">{{userName}}</yd-flexbox-item>
        </yd-flexbox>
        <yd-cell-group> <!-- 选项卡 -->
          <yd-cell-item arrow class="select" @click.native="goToInformation">
            <yd-icon name="more" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">Personal Information</label>
          </yd-cell-item>
          <yd-cell-item arrow class="select" @click.native="goToSummary">
            <yd-icon name="compose" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">Risk Summary</label>
          </yd-cell-item>
          <yd-cell-item arrow class="select" @click.native="goToReport">
            <yd-icon name="feedback" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">Epidemic Report</label>
          </yd-cell-item>
          <yd-cell-item arrow class="select" @click.native="goToDID">
            <yd-icon name="verifycode" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">DID Identity</label>
          </yd-cell-item>
          <yd-cell-item arrow class="select" @click.native="goToAbout">
            <yd-icon name="question" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">About Us</label>
          </yd-cell-item>
          <yd-cell-item arrow class="select" @click.native="goToLogin">
            <yd-icon name="home-outline" slot="icon" size=".42rem"></yd-icon>
            <label slot="left">Sign in/Sign out</label>
          </yd-cell-item>
        </yd-cell-group>
      </div>
    </yd-popup>
    <yd-popup position="bottom" height="35%" v-model="decisionDown"> <!-- 下方的弹窗（路径搜索） -->
      <div class="popup_down">
        <yd-cell-group title="Location Input">
          <yd-cell-item> <!--  起始点:  placeholder="please input your start place"-->
            <span slot="left">From:</span>
            <yd-input slot="right" v-model="startPlace"></yd-input>
          </yd-cell-item>
          <yd-cell-item> <!--  终点:  placeholder="please input your destination"-->
            <span slot="left">To:</span>
            <yd-input slot="right" v-model="destination"></yd-input>
          </yd-cell-item>
        </yd-cell-group>
        <yd-cell-group title="Path Option">
        </yd-cell-group>
        <yd-radio-group v-model="pathOption" slot="right" size="15">
          <yd-radio val="avoid risk"></yd-radio>
          <yd-radio val="shortest"></yd-radio>
        </yd-radio-group>
        <yd-button bgcolor="#2db7f5" @click.native="checkRoad" size="large" color="#FFF">path query</yd-button> <!--  查询路径 -->
      </div>
    </yd-popup>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="decisionLeft = true" class="showLeft">
      <img src="../assets/go.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 左边弹窗按钮 #2db7f5 -->
    <yd-button shape="circle" size="small" bgcolor="#C0C0C0" @click.native="decisionDown = true" class="showDown">
      <yd-icon name="type" color="#000" size="0.5rem"></yd-icon>
    </yd-button> <!-- 下方弹窗按钮 -->
    <div id="map"></div> <!-- 地图 v-on:contextmenu="checkLocation($event)" -->
  </div>
</template>

<script>
import 'ol/ol.css' // 在OpenLayers 6.3.1 版本使用需要引入css
import Map from 'ol/Map' // 导入map容器
import View from 'ol/View' // 导入view视图
import LayerTile from 'ol/layer/Tile' // 导入瓦片图层
import ImageLayer from 'ol/layer/Image' // 导入图片图层
// import TileArcGISRest from 'ol/source/TileArcGISRest' // 导入arcgis服务源
import ImageWMS from 'ol/source/ImageWMS' // 导入图片WMS服务源
import VectorSource from 'ol/source/Vector' // 导入矢量源
import LayerVector from 'ol/layer/Vector' // 导入矢量图层
import Feature from 'ol/Feature' // 导入地图特征
import Style from 'ol/style/Style' // 导入样式
import Icon from 'ol/style/Icon' // 导入icon
import GeoPoint from 'ol/geom/Point' // 导入地理位置点
// import Text from 'ol/style/Text' // 导入text
// import Fill from 'ol/style/Fill' // 导入填充
import Stroke from 'ol/style/Stroke' // 导入笔画
import markImage from '../assets/point.png' // 导入标注图
import Line from 'ol/geom/LineString' // 导入直线
import XYZ from 'ol/source/XYZ' // XYZ瓦片源
import {defaults} from 'ol/control' // 默认控件设置
export default {
  name: 'home',
  data () { // 属性（数据）
    return {
      count: 0, // 用于实现图层类型切换（0为纯地图，1为玻璃图，2为疫情点地图）
      map: null, // map容器
      dotLayer: null, // 疫情点图层
      glassLayer: null, // 玻璃图图层
      roadLayer: null, // 路线图层
      decisionDown: false, // 用于决定下方弹窗隐藏或显示
      decisionLeft: false, // 用于决定左边弹窗隐藏或显示
      startPlace: '', // 路径起始位置
      destination: '', // 路径终点
      tiandiUrl: 'http://api.tianditu.gov.cn/geocoder?type=geocode&tk=320109f58cbb412b31e478ddc5c651bd&postStr=', // 天地图url
      amapUrl: 'https://restapi.amap.com/v3/geocode/regeo?key=b8dd4a3e40e58d76a9805fe8847d7434&location=', // 高德地图url（逆地理编码），key需要写自己的
      vectorSource: null, // 添加标注的图层的源，因为只有矢量源才可以添加Feature
      userName: 'xiaoxin', // 'Please Sign In' // 用户名
      pathOption: 'avoid risk' // 路径查询选项
    }
  },
  mounted () { // 什么都加载好的时候
    this.map_init() // 进行地图加载
    // this.setRootFontSize() // 设置根元素字体大小（用于手机屏幕适配）
  },
  methods: { // 方法
    map_init () { // 地图初始化函数
      const center = [114.046, 22.620] // 地图中心（深圳）
      const geoserverUrl = 'http://113.106.111.75:5014/geoserver/BlockMap/wms' // geoserver的url
      // const globalmapUrl = 'https://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer' // 底图url（中文）
      // const globalmapUrl = 'https://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunityENG/MapServer' // 底图url（英文）
      const token = '320109f58cbb412b31e478ddc5c651bd' // 天地图的key
      this.vectorSource = new VectorSource({}) // 创建矢量源
      this.dotLayer = new ImageLayer({ // 疫情点图层
        source: new ImageWMS({ // 设置获取源
          url: geoserverUrl, // geoserver的url
          params: {'LAYERS': 'BlockMap:ncov_china_data'}, // 图层名称
          serverType: 'geoserver' // 服务器类型是geoserver
          // crossOrigin: 'anonymous'
        }),
        opacity: 0.7 // 设置透明度为70%
      })
      this.glassLayer = new ImageLayer({ // 玻璃图层
        source: new ImageWMS({ // 设置获取源
          url: geoserverUrl, // geoserver的url
          params: {'LAYERS': 'BlockMap:ncov_china_vonoroi'}, // 图层名称
          serverType: 'geoserver' // 服务器类型是geoserver
          // crossOrigin: 'anonymous'
        }),
        opacity: 0.7 // 设置透明度为70%
      })
      this.roadLayer = new ImageLayer({ // 路线图
        source: new ImageWMS({ // 设置获取源r
          url: geoserverUrl, // geoserver的url
          params: {'LAYERS': 'BlockMap:ShortestRoad'}, // 图层名称
          serverType: 'geoserver' // 服务器类型是geoserver
          // crossOrigin: 'anonymous'
        }),
        zIndex: 9 // 设置图层在次高层
      })
      this.map = new Map({ // 创建地图容器
        target: 'map', // 设置目标为上面的map div
        layers: [
          // new LayerTile({ // 瓦片底图
          //   source: new TileArcGISRest({
          //     url: globalmapUrl
          //   })
          // }),
          new LayerTile({ // 天地图矢量图（底图之一）
            source: new XYZ({
              url: 'https://t0.tianditu.gov.cn/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=' + token
            }),
            type: 'base'
          }),
          new LayerTile({ // 天地图标注（底图之一）
            source: new XYZ({
              url: 'https://t0.tianditu.gov.cn/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=' + token
            }),
            type: 'base'
          }),
          new LayerVector({ // 绘制图标和线路的层
            source: this.vectorSource,
            zIndex: 10 // 设置图层在最高层
          })
          // new ImageLayer({ // 路网图
          //   source: new ImageWMS({ // 设置获取源
          //     url: geoserverUrl, // geoserver的url
          //     params: {'LAYERS': 'BlockMap:road'}, // 图层名称
          //     serverType: 'geoserver' // 服务器类型是geoserver
          //     // crossOrigin: 'anonymous'
          //   }),
          //   opacity: 0.7 // 设置透明度为70%
          // })
        ], // 设置图层
        view: new View({ // 设置视图
          projection: 'EPSG:4326', // 投影系
          center: center, // 初始化地图中心
          zoom: 13, // 默认缩放等级
          maxZoom: 18, // 最大缩放等级
          minZoom: 2 // 最小缩放等级
        }),
        controls: defaults({ // 禁用默认缩放按钮以及旋转按钮（功能）
          zoom: false,
          rotate: false
        })
      })
      // this.addPointsFeature([[116.41667, 39.91667], [113.23333, 23.16667], [120.20000, 30.26667]]) // 测试
      // this.addLinesFeature([[116.41667, 39.91667], [120.20000, 30.26667], [113.23333, 23.16667]])
    },
    checkRoad () { // 查询路径函数
      this.vectorSource.clear() // 清除所有Feature，即点和线
      // var coordinates = [[113.94699754903482, 22.58990586228084],
      //   [113.9468688030021, 22.59038329548549],
      //   [113.94679906556772, 22.590597872206683],
      //   [113.9467239637153, 22.590748075911517],
      //   [113.94697668798054, 22.590800200314444],
      //   [113.94759896047199, 22.59092358192913],
      //   [113.94770624883259, 22.59093431076519],
      //   [113.94773307092274, 22.59093431076519],
      //   [113.94775452859486, 22.59093431076519],
      //   [113.9477974439391, 22.59090748867504],
      //   [113.94784572370136, 22.590848480076712],
      //   [113.94787254579151, 22.590751920552176],
      //   [113.94789936788166, 22.59061781010143],
      //   [113.94793155438984, 22.590548072667044],
      //   [113.94796374089802, 22.590515886158865],
      //   [113.9479959274062, 22.590494428486746],
      //   [113.94802811391438, 22.590478335232657],
      //   [113.94805493600452, 22.590478335232657],
      //   [113.94809785134876, 22.590472970814627],
      //   [113.94909026868427, 22.590674672965292],
      //   [113.94972327001179, 22.590792690161948],
      //   [113.94957306630695, 22.59111348229464],
      //   [113.94934216373997, 22.59147314680278],
      //   [113.94921341770726, 22.591639443761704],
      //   [113.94902029865818, 22.591854020482895],
      //   [113.94892910355168, 22.5919452155894],
      //   [113.94882717960911, 22.592047139531967],
      //   [113.94856968754368, 22.59225635183513],
      //   [113.94830146664219, 22.59243874204814],
      //   [113.94825747834886, 22.59247307429079],
      //   [113.94547119965694, 22.593888207865273],
      //   [113.94360009071363, 22.5951627937856],
      //   [113.94170449688285, 22.596497535511098],
      //   [113.94151129285386, 22.596647805311417],
      //   [113.93975848032741, 22.59801089515254],
      //   [113.93975848032741, 22.59801089515254],
      //   [113.93825435109568, 22.59925026330732],
      //   [113.93777491887563, 22.599686761298717],
      //   [113.93668725174952, 22.600667092853165],
      //   [113.9351716733466, 22.602108251751662],
      //   [113.93465646260266, 22.602609151086053],
      //   [113.93337559144726, 22.60406032814589]] // 模拟
      // this.addPointsFeature([coordinates[0], coordinates[coordinates.length - 1]])
      // this.addLinesFeature(coordinates)
      // this.map.getView().setCenter(coordinates[0]) // 设置地图中心点
      // this.map.getView().setZoom(14) // 设置放大倍数
      this.map.removeLayer(this.roadLayer) // 清除路线
      this.decisionDown = false // 收起弹窗
      this.map.getView().setZoom(18) // 设置放大倍数
      this.map.getView().setCenter([114.0274186903989, 22.671875493519373])
      this.addPointsFeature([[114.0274186903989, 22.671875493519373]]) // 先添加起点
      this.map.addLayer(this.roadLayer) // 添加路线
      setTimeout(this.mapAnimation, 2000)
      setTimeout(this.endAnimation, 6500)
    },
    mapAnimation () { // 地图缩放动画（从大到小，缩放系数从start到end）
      this.map.getView().animate({ // 旋转地图
        duration: 1500,
        rotation: Math.PI / 4
      }, {
        duration: 1500,
        center: [(114.0274186903989 + 114.03136004058267) / 2, (22.671875493519373 + 22.67289465778219) / 2],
        zoom: 15
      }, {
        duration: 1500,
        center: [(114.0274186903989 + 114.03136004058267) / 2, (22.671875493519373 + 22.67289465778219) / 2],
        rotation: -(Math.PI / 16)
      })
    },
    endAnimation () { // 动画的结尾设置
      this.addPointsFeature([[114.03136004058267, 22.67289465778219]]) // 最后添加终点
      this.count = 1 // 唤出玻璃图
    },
    addPointsFeature (coordinates) { // 加载一堆点Feature
      let featureList = []
      for (let i = 0; i < coordinates.length; ++i) { // 遍历所有点
        featureList.push(this.createPointFeature(coordinates[i]))
      }
      this.vectorSource.addFeatures(featureList)
    },
    createPointFeature (coordinate) { // 创建一个地图标注
      var feature = new Feature({
        geometry: new GeoPoint(coordinate)
      })
      feature.setStyle(new Style({
        image: new Icon({ // 设置图标
          anchor: [0.5, 1], // 设置图标偏移
          anchorOrigin: 'top-right', // 标注样式的起点位置
          // anchorXUnits: 'fraction', // X方向单位：分数
          // anchorYUnits: 'pixels', // Y方向单位：像素
          offsetOrigin: 'top-right', // 偏移起点位置的方向
          opacity: 0.9, // 透明度
          src: markImage, // 图片路径
          scale: 0.1
        })
        // text: new Text({
        //   textAlign: 'center', // 位置
        //   textBaseline: 'middle', // 基准线
        //   font: '12px Calibri,sans-serif', // 文字样式
        //   text: '标注点', // 文本内容
        //   fill: new Fill({ // 文本填充样式（即文字颜色)
        //     color: '#000'
        //   }),
        //   stroke: new Stroke({
        //     color: '#F00',
        //     width: 2
        //   })
        // })
      }))
      return feature
    },
    createLineFeature (coordinates) { // 创建线，首尾相接
      var feature = new Feature({ // 创建线feature
        geometry: new Line(coordinates)
      })
      feature.setStyle(new Style({
        stroke: new Stroke({ // 设置笔画
          width: 3,
          color: '#0F0'
        })
      }))
      return feature
    },
    addLinesFeature (coordinates) { // 画线函数（首尾相连）
      this.vectorSource.addFeature(this.createLineFeature(coordinates))
    },
    setRootFontSize () { // 设置根元素字体大小，确定rem
      let root = document.documentElement || document.body // 找root，html或者是body
      root.style.fontSize = 13.33 + 'vw' // 这里需要解释一下，一般手机页面设计图宽为750px，也就是手机屏幕宽度，1px相当于0.13333vw，100px就是13.33vw，相当于1rem等于100px
    },
    iconDo () { // 用于实现图层类型切换
      if (this.count === 2) {
        this.count = 0
      } else {
        this.count += 1
      }
    },
    // checkLocation (event) {
    //   event.preventDefault() // 禁止浏览器右键菜单行为
    //   let coordinate = this.map.getEventCoordinate(event) // 获取点击位置地理坐标
    //   console.log(coordinate)
    // },
    goToSummary () { // 跳转当天风险等级总结页面
      this.$router.push('/summary')
    },
    goToLogin () { // 跳转登录页面
      this.$router.push('/login')
    },
    goToReport () { // 跳转上报页面
      this.$router.push('/report')
    },
    goToInformation () { // 跳转个人信息页面
      this.$router.push('/information')
    },
    goToDID () { // 跳转数字身份验证页面
      this.$router.push('/did')
    },
    goToAbout () { // 跳转数字身份验证页面
      this.$router.push('/about')
    }
  },
  watch: { // 监听并执行
    count (newName, oldName) { // 监听count的变化，改变图层类型
      if (oldName === 1) { // 去除图层
        this.map.removeLayer(this.glassLayer)
      } else if (oldName === 2) {
        this.map.removeLayer(this.dotLayer)
      }
      if (newName === 1) { // 加入图层
        this.map.addLayer(this.glassLayer)
      } else if (newName === 2) {
        this.map.addLayer(this.dotLayer)
      }
    }
  }
}
</script>

<style scoped>
#home{ /*全局*/
  width: 100%; /*占满*/
  height: 100%;
}
#map{ /*地图*/
  width: 100%; /*占满*/
  height: 100%;
}
.popup_down{ /*整个下部弹窗*/
  background-color: #f5f5f5; /*设置背景色*/
  padding-left: 0.5rem; /*左内边距*/
  padding-right: 0.5rem; /*右内边距*/
  padding-top: 0.2rem; /*上内边距*/
}
.popup_left{ /*这个左边弹窗*/
  padding-top: 0.2rem; /*上内边距*/
  padding-left: 0.1rem; /*左内边距*/
  padding-right: 0.1rem; /*右内边距*/
}
.showDown{ /*下方弹窗按钮*/
  position: absolute; /*相对于整个窗口的位置*/
  bottom: 0; /*贴近底部*/
  left: 43vw; /*与左边窗口的距离,43%屏幕宽度*/
  z-index: 99; /*在最高层*/
  opacity: 0.7; /*设置透明度*/
}
.choose{ /*图层类型控制按钮*/
  position: absolute; /*相对全局*/
  right: 0.1rem; /*与右边窗口的距离（根据750px做的计算，100px为1rem）*/
  top: 0.5rem; /*与上边窗口的距离*/
  z-index: 99; /*在最高层*/
  border: none; /*设置边框*/
}
.choose:active{ /*图层类型控制按钮按动效果*/
  opacity: 0.3;
}
span{ /*位置输入栏文字*/
  font-weight: bold; /*字体加粗*/
  width: 1rem; /*设置宽度*/
  text-align: center; /*文字居中*/
}
.showLeft{ /*左方弹窗按钮*/
  position: absolute;
  z-index: 99; /*在最高层*/
  opacity: 0.7; /*设置透明度*/
  top: 1rem; /*离手机顶部的距离*/
}
.select:active{ /*左边弹窗选项卡点击效果*/
  background-color: lightgrey;
}
</style>
