<template> <!-- 地图模块 -->
  <div id="olmap_glass">
    <RadioGroup v-model="whatlayer" type="button"> <!-- 单选按钮组 -->
      <Radio label="glass" border>{{$t('mapGlass')}}</Radio> <!-- 玻璃图 map with glass-->
      <Radio label="dot" border>{{$t('mapEpi')}}</Radio> <!-- 疫情点 map with epidemic location-->
      <Radio label="none" border>{{$t('mapOnly')}}</Radio> <!-- 纯地图 map only-->
    </RadioGroup>
    <div id="nav">
      <Button class="show_hide" v-on:click="nav_operation" type="info"> <!-- 导航栏收缩按钮 -->
        <Icon type="md-apps" />
      </Button>
      <div id="manage"> <!-- 用于实现导航栏功能的 -->
        <label class="sl">{{$t('startPoint')}}</label> <!-- from: 起始点: -->
        <Input placeholder="start location" v-model="startPlace" clearable class="si"/>
        <label class="dl">{{$t('endPoint')}}</label> <!-- to: 终点: -->
        <Input placeholder="destination" v-model="destination" clearable class="di"/>
        <Button type="primary" v-on:click="checkRoad" class="bc">{{$t('pathQuery')}}</Button> <!-- path query 查询路径 -->
      </div>
    </div>
    <Input type="textarea" v-model="locationName" class="show_location" readonly :autosize="true"/>
    <div id="map" v-on:contextmenu="checkLocation($event)"></div> <!-- 右击事件 -->
  </div> <!-- 玻璃图 -->
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
import axios from 'axios' // 导入ajax库
// import Text from 'ol/style/Text' // 导入text
// import Fill from 'ol/style/Fill' // 导入填充
import Stroke from 'ol/style/Stroke' // 导入笔画
import markImage from '../../assets/images/point.png' // 导入标注图
import Line from 'ol/geom/LineString' // 导入直线
import XYZ from 'ol/source/XYZ'
export default {
  name: 'olmapglass',
  data () { // 属性（数据）
    return {
      whatlayer: 'none', // 选择了加载哪个图层
      map: null, // map容器
      dotLayer: null, // 疫情点图层
      glassLayer: null, // 玻璃图图层
      nav: null, // 取得导航栏
      butt: null, // 取得按钮
      manage: null, // 取得导航栏功能实现div
      nwidth: null, // 导航栏的宽度
      bleft: null, // 按钮左边偏移量
      temp: null, // 临时量，以上五个数据用于导航栏动画
      decision: true, // 用于决定导航栏隐藏或显示
      startPlace: '', // 路径起始位置
      destination: '', // 路径终点
      locationName: 'Have the right-click to get the place name here!', // 鼠标点击位置的地名
      tiandiUrl: 'http://api.tianditu.gov.cn/geocoder?type=geocode&tk=320109f58cbb412b31e478ddc5c651bd&postStr=',
      amapUrl: 'https://restapi.amap.com/v3/geocode/regeo?key=b8dd4a3e40e58d76a9805fe8847d7434&location=', // 高德地图url（逆地理编码），key需要写自己的
      vectorSource: null // 添加标注的图层的源，因为只有矢量源才可以添加Feature
    }
  },
  mounted () { // 什么都加载好的时候
    this.map_init() // 进行地图加载
  },
  methods: { // 方法
    map_init () { // 地图初始化函数
      const center = [116, 40] // 地图中心（北京）
      const geoserverUrl = 'http://113.106.111.75:5014/geoserver/BlockMap/wms' // geoserver的url
      // const globalmapUrl = 'https://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer' // 底图url（中文）
      // const globalmapUrl = 'https://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunityENG/MapServer' // 底图url（英文）
      const token = '320109f58cbb412b31e478ddc5c651bd' // 天地图的key
      this.vectorSource = new VectorSource({}) // 创建矢量源
      this.dotLayer = new ImageLayer({ // 疫情点图层
        source: new ImageWMS({ // 设置获取源
          url: geoserverUrl, // geoserver的url
          params: {'LAYERS': 'BlockMap:ncov_china_data'}, // 图层名称
          serverType: 'geoserver', // 服务器类型是geoserver
          crossOrigin: 'anonymous'
        }),
        opacity: 0.7 // 设置透明度为50%
      })
      this.glassLayer = new ImageLayer({ // 玻璃图层
        source: new ImageWMS({ // 设置获取源
          url: geoserverUrl, // geoserver的url
          params: {'LAYERS': 'BlockMap:ncov_china_vonoroi'}, // 图层名称
          serverType: 'geoserver', // 服务器类型是geoserver
          crossOrigin: 'anonymous'
        }),
        opacity: 0.7 // 设置透明度为50%
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
        ], // 设置图层
        view: new View({ // 设置视图
          projection: 'EPSG:4326', // 投影系
          center: center, // 初始化地图中心
          zoom: 5, // 默认缩放等级
          maxZoom: 18, // 最大缩放等级
          minZoom: 2 // 最小缩放等级
        })
      })
      // this.addPointsFeature([[116.41667, 39.91667], [113.23333, 23.16667], [120.20000, 30.26667]]) // 测试
      // this.addLinesFeature([[116.41667, 39.91667], [120.20000, 30.26667], [113.23333, 23.16667]])
    },
    nav_operation () { // 导航栏动作（收起和展开）
      this.nav = document.getElementById('nav')
      this.butt = document.getElementsByClassName('show_hide')[0]
      this.manage = document.getElementById('manage')
      if (this.decision) { // 导航栏在显示
        this.decision = false
        this.nwidth = 280
        this.bleft = 280
        this.temp = 0 // 导航栏收缩到宽度为0
        this.manage.style.display = 'none'
        this.startShorter() // 导航栏收起
      } else {
        this.decision = true
        this.nwidth = 0
        this.bleft = 0
        this.temp = 280
        this.startLonger() // 导航栏展开
      }
    },
    startShorter () { // 收缩函数（配合导航栏操作使用）
      if (this.nwidth > this.temp) { // 如果导航栏还没有缩到最小
        this.nwidth -= 40
        this.bleft -= 40
        this.nav.style.width = this.nwidth + 'px'
        this.butt.style.left = this.bleft + 'px'
        setTimeout(() => { this.startShorter() }, 20) // 20毫秒执行一次
      } else {
        this.nav.style.width = 0 + 'px'
        this.butt.style.left = 0 + 'px'
      }
    },
    startLonger () { // 弹出函数（配合导航栏操作使用）
      if (this.nwidth < this.temp) { // 还没展开到极致
        this.nwidth += 40
        this.bleft += 40
        this.nav.style.width = this.nwidth + 'px'
        this.butt.style.left = this.bleft + 'px'
        setTimeout(() => { this.startLonger() }, 20) // 20毫秒执行一次
      } else {
        this.manage.style.display = 'inline'
        this.nav.style.width = 280 + 'px'
        this.butt.style.left = 280 + 'px'
      }
    },
    checkRoad () { // 查询路径函数
      this.vectorSource.clear() // 清除所有Feature，即点和线
      var coordinates = [[113.49937642079894, 22.52621453154738],
        [113.49844204807535, 22.52679274301404],
        [113.50031496447882, 22.528878490826997],
        [113.49781774260752, 22.53180137551727],
        [113.48371277526294, 22.526059987137067],
        [113.47707082609007, 22.53215187133217],
        [113.45427537005472, 22.51906295573921],
        [113.43755584501956, 22.523611775507423],
        [113.43542276760446, 22.52505922089624],
        [113.43359441553437, 22.530315733097726],
        [113.42826172199663, 22.528335018355136],
        [113.42468119919272, 22.5242974075337],
        [113.4153870761698, 22.520183615376013],
        [113.41047783017287, 22.5267579660601],
        [113.40689730736895, 22.529348131492718],
        [113.40400241659131, 22.537042446454322],
        [113.38451822543963, 22.5397195374974],
        [113.38139479065325, 22.540862257541203],
        [113.38596567082845, 22.552594183324242],
        [113.38237668343157, 22.569201714627503],
        [113.3848144861917, 22.583499905816094],
        [113.38610956890801, 22.585785345903698]] // 模拟
      this.addPointsFeature(coordinates)
      this.addLinesFeature(coordinates)
      this.map.getView().setCenter([113.49937642079894, 22.52621453154738])
      this.map.getView().setZoom(9)
    },
    checkLocation (event) { // 查询地名函数
      event.preventDefault() // 禁止浏览器右键菜单行为
      let coordinate = this.map.getEventCoordinate(event) // 获取点击位置地理坐标
      // let url = this.amapUrl + coordinate[0].toFixed(6) + ',' + coordinate[1].toFixed(6) // 小数点后不要超过6位
      let url = this.tiandiUrl + '{\'lon\':' + coordinate[0] + ',\'lat\':' + coordinate[1] + ',\'ver\':1}'
      let name = 'place: '
      console.log(coordinate)
      axios.get(url).then(
        response => { // 获取数据后的处理
          let result = response.data.result
          if (result.formatted_address === '') {
            if (result.addressComponent.nation) {
              name += result.addressComponent.nation
            }
            if (result.addressComponent.address) {
              name += result.addressComponent.address
            }
          } else {
            name += result.formatted_address
          }
          // name += response.data.regeocode.formatted_address // 加载地址
          this.locationName = name // 在地名框显示
        }
      ).catch(
        error => {
          console.log(error)
          this.locationName = 'place: Get Place Error！'
        }
      )
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
    createLineFeature (coordinates) { // 创建线，首位相接
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
    }
  },
  watch: { // 监听并执行
    whatlayer (newName, oldName) { // 监听whatlayer的变化
      if (oldName === 'glass') { // 去除图层
        this.map.removeLayer(this.glassLayer)
      } else if (oldName === 'dot') {
        this.map.removeLayer(this.dotLayer)
      }
      if (newName === 'glass') { // 加入图层
        this.map.addLayer(this.glassLayer)
      } else if (newName === 'dot') {
        this.map.addLayer(this.dotLayer)
      }
    }
  }
}
</script>

<style scoped>
#olmap_glass{ /*全局*/
  position: absolute; /*嵌入时需要设置*/
  width: 100%; /*宽度是父组件的100%*/
  height: 100%; /*高度是父组件的100%，就是App.vue的app*/
}
#map{ /*地图*/
  height: 100%; /*高度是父组件的95.7%，就是olmap_glass*/
  width: 100%; /*宽度是父组件的100%，就是olmap_glass*/
}
#nav{ /*导航栏*/
  background-color: white; /*设置背景颜色*/
  height: 300px; /*height: 50%;*/ /*高度是300px*/
  width: 280px; /*width: 20%;*/ /*宽度是280px*/
  position: absolute; /*位置相对父组件*/
  top: 25%; /*高度为父组件的25%高度*/
  z-index: 99; /*层数设置为99，要在所有图层上方*/
  border-radius: 5px; /*设置圆角*/
  border: 1px solid deepskyblue; /*设置边框*/
}
#manage{ /*用于实现导航栏功能的*/
  width: 100%;
  height: 100%;
}
.sl{
  position: absolute;  /*相对父组件的位置，manage*/
  top: 21%; /*上边距离父组件20%高度*/
  color: black; /*字体颜色*/
  left: 8%; /*右边距离父组件8%宽度*/
  font-size: 0.9rem; /*设置字体大小*/
}
.si{ /*起始点输入框*/
  position: absolute; /*相对父组件的位置，manage*/
  width: 60%; /*宽度为父组件60%*/
  right: 10%; /*右边距离父组件10%宽度*/
  top: 20%; /*上边距离父组件20%高度*/
}
.dl{
  position: absolute;  /*相对父组件的位置，manage*/
  top: 51%; /*上边距离父组件50%高度*/
  color: black; /*字体颜色*/
  left: 8%; /*右边距离父组件8%宽度*/
  font-size: 0.9rem; /*设置字体大小*/
}
.di{ /*终止点输入框*/
  position: absolute; /*相对父组件的位置，manage*/
  width: 60%; /*宽度为父组件60%*/
  right: 10%; /*右边距离父组件10%宽度*/
  top: 50%; /*上边距离父组件50%高度*/
}
.bc{ /*查询路径按钮*/
  position: absolute; /*相对父组件的位置，manage*/
  top: 70%; /*上边距离父组件70%高度*/
  left: 32%; /*左边距离父组件32%宽度*/
}
.show_location{ /*显示地名框*/
  position: absolute;  /*相对父组件的位置，olmap_glass*/
  z-index:99;  /*层数设置为99，要在所有图层上方*/
  width: 30%; /*宽度为30%父组件宽度*/
  left: 600px;  /*左边距离为600px*/
}
.show_hide{ /*导航栏按钮*/
  position: absolute; /*设置相对父组件位置，nav*/
  left: 100%; /*左边距离为nav宽度*/
  top: 45%; /*高度为父组件高度45%*/
  z-index:99;  /*层数设置为99，要在所有图层上方*/
}
</style>
