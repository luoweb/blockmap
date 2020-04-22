import "ol/ol.css";
import "ol-ext/dist/ol-ext.css";
import Map from "ol/Map";
import View from "ol/View";
// import TileLayer from 'ol/layer/Tile';
import XYZ from "ol/source/XYZ";
import Icon from 'ol/style/Icon';
import {
  equalTo as equalToFilter,
  like as likeFilter,
  and as andFilter,
  intersects as intersects
} from 'ol/format/filter';
import Intersects from 'ol/format/filter/Intersects';
import {
  fromLonLat
} from "ol/proj";
import {
  toLonLat
} from "ol/proj";
// import VectorLayer from 'ol/layer/Vector';
// import VectorSource from 'ol/source/Vector';
// import {Fill, Stroke, Style, Text} from 'ol/style';
import ImageLayer from "ol/layer/Image";
import ImageWMS from "ol/source/ImageWMS";
import {
  defaults as defaultControls,
  Control
} from 'ol/control';
import * as olControl from "ol/control";
import FullScreen from "ol/control/FullScreen";
import ZoomSlider from "ol/control/ZoomSlider";
import ZoomToExtent from "ol/control/ZoomToExtent";
import MousePosition from "ol/control/MousePosition";
import OverviewMap from "ol/control/OverviewMap";
import ScaleLine from "ol/control/ScaleLine";
import Rotate from "ol/control/Rotate";
import SearchFeature from "ol-ext/control/SearchFeature";
import GeolocationBar from "ol-ext/control/GeolocationBar";
import extButton from "ol-ext/control/Button";
import Placemark from "ol-ext/overlay/Placemark";
// import {Dijkstra,sphere} from "./js/ol-ext";
import Heatmap from 'ol/layer/Heatmap';
import LayerSwitcher from 'ol-layerswitcher';
import 'ol-layerswitcher/src/ol-layerswitcher.css';
// import LayerSwitcher from 'ol/control/LayerSwitcher';
import Feature from "ol/Feature";
import {
  unByKey
} from "ol/Observable";
import {
  easeOut
} from "ol/easing";
import Point from "ol/geom/Point";
import {
  Group as LayerGroup,
  Tile as TileLayer,
  Vector as VectorLayer
} from "ol/layer";
import {
  OSM,
  Vector as VectorSource
} from "ol/source";
import {
  getVectorContext
} from "ol/render";
import {
  Circle as CircleStyle,
  Stroke,
  Style,
  Fill,
  Text
} from "ol/style";
import Overlay from 'ol/Overlay';
import Select from 'ol/interaction/Select'
// import Polygon from 'ol/geom/Polygon';
import {
  Polygon,
  fromExtent
} from 'ol/geom/Polygon';


import * as olLoadingstrategy from 'ol/loadingstrategy';
import * as olTilegrid from 'ol/tilegrid';
import {
  WFS,
  GeoJSON
} from 'ol/format';

import {
  bbox as bboxStrategy
} from 'ol/loadingstrategy';


import OLCesium from 'olcs/OLCesium.js';
// import Cesium from 'cesium/Build/Cesium/Cesium'
// import 'cesium/Build/Cesium/Widgets/widgets.css'

import mapApi from './js/mapapi';

//天地图Token
var tdToken = '320109f58cbb412b31e478ddc5c651bd';

// Cesium.Ion.defaultAccessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI0OWY3ZDI5ZS01NjViLTQ2ZTUtODhmNi1kMjQwN2IzODdlNzAiLCJpZCI6MjYxMTUsInNjb3BlcyI6WyJhc3IiLCJnYyJdLCJpYXQiOjE1ODcyODMwMDR9.1sE41iO0myvjWB00X4l-S4IMfSCLg_-ZKIums5J0cM4';
// Cesium.Ion.defaultAccessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI0OWY3ZDI5ZS01NjViLTQ2ZTUtODhmNi1kMjQwN2IzODdlNzAiLCJpZCI6MjYxMTUsInNjb3BlcyI6WyJhc3IiLCJnYyJdLCJpYXQiOjE1ODcyODMwMDR9.1sE41iO0myvjWB00X4l-S4IMfSCLg_-ZKIums5J0cM4';
// Cesium.Camera.DEFAULT_VIEW_RECTANGLE = Cesium.Rectangle.fromDegrees(90, -20, 110, 90);

/*********************显示弹出层**************************/

var container = document.getElementById("popup");
var content = document.getElementById("popup-content");
var popupCloser = document.getElementById("popup-closer");

var overlay = new Overlay({
  element: container,
  autoPan: true
});


/*********************控件自定义**************************/

//
// Define rotate to north control.
//
var RotateNorthControl = /*@__PURE__*/ (function (Control) {
  function RotateNorthControl(opt_options) {
    var options = opt_options || {};

    var button = document.createElement('button');
    button.innerHTML = 'F';

    var element = document.createElement('div');
    element.className = 'rotate-north ol-unselectable ol-control';
    element.appendChild(button);

    Control.call(this, {
      element: element,
      target: options.target
    });

    button.addEventListener('click', this.handleRotateNorth.bind(this), false);
  }

  if (Control) RotateNorthControl.__proto__ = Control;
  RotateNorthControl.prototype = Object.create(Control && Control.prototype);
  RotateNorthControl.prototype.constructor = RotateNorthControl;

  RotateNorthControl.prototype.handleRotateNorth = function handleRotateNorth() {
    this.getMap().getView().setRotation(0);
    this.getMap().getView().fit([
      8092058.047621677, -67251.42979813833,
      16697377.372842146, 9390079.709800594
    ]);

  };

  return RotateNorthControl;
}(Control));


/*********************图层选择**************************/
var layer = new Array(); //图层数组
var layerName = new Array(); //图层名称数组
var layerVisibility = new Array(); //图层可见数组
function loadLayersControl(map, id) {
  var treeContent = document.getElementById(id);
  var layers = ol2d.getLayers(); //获取地图中的所有图层
  for (var i = 0; i < layers.getLength(); i++) {
    layer[i] = layers.item(i);
    layerName[i] = layer[i].get('name');
    layerVisibility[i] = layer[i].getVisible(); //获取每个图层的名称及是否可见
    var elementLi = document.createElement("li");
    treeContent.appendChild(elementLi);
    var elementInput = document.createElement("input");
    elementInput.type = "checkbox";
    elementInput.name = "layers";
    elementLi.appendChild(elementInput);
    var elementLabel = document.createElement("label");
    elementLabel.className = "layer";
    setInnerText(elementLabel, layerName[i]);
    elementLi.appendChild(elementLabel);
    //<ul><li><input type="checkbox" name="layers"/><label class="layer"></label></li></ul>
    if (layerVisibility[i]) {
      elementInput.checked = true;
    }
    addChangeEvent(elementInput, layer[i]);
  }
}

function addChangeEvent(element, layer) {
  element.onclick = function () {
    console.log("click")
    if (element.checked) {
      layer.setVisible(true);
    } else {
      layer.setVisible(false);
    }
  };
}

function setInnerText(element, text) {
  if (typeof element.textContent == "string") {
    element.textContent = text;
  } else {
    element.innerText = text; //FireFox不支持innerText方法,兼容
  }
}

var tdRoadSource = new XYZ({
  url: "https://t0.tianditu.gov.cn/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=" + tdToken,
});
var tdRoadMapLayer = new TileLayer({
  source: tdRoadSource,
  type: 'base',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  title: '天地图路网',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: false,
  isGroup: true,
  name: "天地图路网",
});

var tdImageSource = new XYZ({
  url: "https://t0.tianditu.gov.cn/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=" + tdToken,
});
var tdImageLayer = new TileLayer({
  source: tdImageSource,
  type: 'base',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  title: '天地卫星图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: true,
  isGroup: true,
  name: "天地卫星图",
  crossOrigin: "anonymous",
});

var borderSource = new ImageWMS({
  url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
  params: {
    LAYERS: "border",
  },
  serverType: "geoserver",
  crossOrigin: "anonymous",
});
var borderLayer = new ImageLayer({
  source: borderSource,
  title: '中国边界图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: true,
  isGroup: true,
  name: "中国边界图",
});

var provinceLayer = new ImageLayer({
  source: new ImageWMS({
    url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
    params: {
      LAYERS: "province0",
    },
    serverType: "geoserver",
    crossOrigin: "anonymous",
  }),
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  title: '省份矢量图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: true,
  name: "省份矢量图",
});

var cityLayer = new ImageLayer({
  source: new ImageWMS({
    url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
    params: {
      LAYERS: "city",
    },
    serverType: "geoserver",
    crossOrigin: "anonymous",
  }),
  name: '城市矢量图',
  title: '城市矢量图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: false,
});


var roadLayer = new ImageLayer({
  source: new ImageWMS({
    url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
    params: {
      LAYERS: "road",
    },
    serverType: "geoserver",
    crossOrigin: "anonymous",
  }),
  name: '城市路网图',
  title: '城市路网图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: false,
});

var ncovVoronoiLayer = new ImageLayer({
  source: new ImageWMS({
    url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
    params: {
      LAYERS: "ncov_china_voronoi_multicolor",
    },
    serverType: "geoserver",
    crossOrigin: "anonymous",
  }),
  name: '疫情泰森图',
  title: '疫情泰森图',
  // Setting combine to true causes sub-layers to be hidden
  // in the layerswitcher, only the parent is shown
  visible: false,
});

var layerGroup = new LayerGroup({
  title: 'Base maps',
  layers: [tdRoadMapLayer, provinceLayer],
});


var style = new Style({
  fill: new Fill({
    color: "rgba(255, 255, 255, 0.6)",
  }),
  stroke: new Stroke({
    color: "#319FD3",
    width: 1,
  }),
  text: new Text({
    font: "12px Calibri,sans-serif",
    fill: new Fill({
      color: "#000",
    }),
    stroke: new Stroke({
      color: "#fff",
      width: 3,
    }),
  }),
});


var ncovWmsLayer = new VectorLayer({
  source: new VectorSource({
    url: "http://173.193.109.188:30657/geoserver/wfs?service=wfs&version=1.1.0&request=GetFeature&typeNames=blockmap%3Ancov_china_data&outputFormat=application/json&srsname=EPSG:4326",
    format: new GeoJSON({
      geometryName: "the_geom",
    }),
  }),
  style: function (feature) {
    // style.getText().setText(feature.get('city'));
    return style;
  },
});

// var wfsParams = {
//   service: 'WFS',
//   version: '1.1.1',
//   request: 'GetFeature',
//   typeName: 'blockmap:ncov_china_data', //图层名称，可以是单个或多个
//   outputFormat: 'text/javascript', //重点，不要改变
//   format_options: 'callback:loadFeatures' //回调函数声明
// };

// var ncovVectorSource = new VectorSource({
//   format: new GeoJSON(),
//   loader: function (extent, resolution, projection) { //加载函数
//     var url = 'http://173.193.109.188:30657/geoserver/blockmap/wms';
//     $.ajax({
//       url: url,
//       data: $.param(wfsParams), //传参
//       type: 'GET',
//       dataType: 'jsonp', //解决跨域的关键
//       jsonpCallback: 'loadFeatures' //回调

//     });
//   },
//   strategy: olLoadingstrategy.tile(new olTilegrid.createXYZ({
//     maxZoom: 20
//   })),
//   projection: 'EPSG:4326'
// });


var ncovVectorSource = new VectorSource({
  format: new GeoJSON(),
  url: function (extent) {
    console.log("extent");
    console.log(extent);
    return 'http://173.193.109.188:30657/geoserver/wfs?service=WFS&' +
      'version=1.1.0&request=GetFeature&typename=blockmap:ncov_china_data&' +
      'outputFormat=application/json&srsname=EPSG:4326&' +
      'bbox=' + extent.join(',') + ',EPSG:3857';
  },
  strategy: bboxStrategy
});

var iconStyle = new Style({
  image: new Icon({
    opacity: 0.75,
    src: "http://github.roweb.cn/mapblock/public/assets/ncov_small.png",
    // size: 5,
    size: [30, 60],
    scale: 1,
    anchor: [0.5, 30],
    anchorXUnits: 'fraction',
    anchorYUnits: 'pixels',

  })
});


var ncovPointVectorSource = new VectorSource({});
//创建疫情点矢量层
var ncovPointVectorLayer = new VectorLayer({
  source: ncovPointVectorSource,
  style: iconStyle,
  name: "疫情点地图",
  title: '疫情点地图',
  visible: true,
});

//创建矢量层
var ncovVectorLayer = new VectorLayer({
  source: ncovVectorSource,
  style: iconStyle,
  name: "疫情矢量图",
  title: '疫情矢量图',
  visible: true,
});

var heatMapLayer = new Heatmap({
  source: ncovVectorSource,
  opacity: 0.8, //透明度
  blur: 30, //模糊大小（以像素为单位）,默认15
  radius: 40, //半径大小（以像素为单位,默认8
  shadow: 300, //阴影像素大小，默认250
  //矢量图层的渲染模式：
  //'image'：矢量图层呈现为图像。性能出色，但点符号和文本始终随视图一起旋转，像素在缩放动画期间缩放。
  //'vector'：矢量图层呈现为矢量。即使在动画期间也能获得最准确的渲染，但性能会降低。
  renderMode: "vector",
  name: "疫情风险图",
  title: '疫情风险图',
  visible: false,
});

var view = new View({
  center: fromLonLat([113.3, 23.12]),
  zoom: 3,
});
console.log(fromLonLat([113.3, 23.12]));

var source = new OSM();
var overviewMapControl = new OverviewMap({
  layers: [
    new TileLayer({
      source: source
    })
  ]
});

var ol2d = new Map({
  layers: [
    tdImageLayer,
    tdRoadMapLayer,
    provinceLayer,
    cityLayer,
    borderLayer,
    // roadLayer,
    // ncovVoronoiLayer,
    // heatMapLayer,
    // ncovVectorLayer,

    // ncovPointVectorLayer,
  ],
  target: "map",
  //   controldefaults，地图默认包含的控件，包含缩放控件和旋转控件；
  //   fullscreencontrol，全屏控件，用于全屏幕查看地图；
  //   mousepositioncontrol，鼠标位置控件，显示鼠标所在地图位置的坐标，可以自定义投影；
  //   overviewmapcontrol，地图全局视图控件；
  //   rotatecontrol，地图旋转控件；
  //   scalelinecontrol，比例尺控件；
  //   zoomcontrol，缩放控件；
  //   zoomslidercontrol，缩放刻度控件；
  //   zoomtoextentcontrol，缩放到全局控件。

  controls: olControl.defaults().extend([
    // 将用于将地图定位到指定范围的ZoomToExtent控件加入到地图的默认控件中
    new ZoomToExtent({
      extent: [
        // 指定要定位到的范围-广州
        12602498.306877896,
        2636537.0728182457,
        12622498.306877896,
        2656537.0728182457,
      ],
    }),
    // new ZoomToExtent(),
    new MousePosition(),
    new FullScreen(),
    overviewMapControl,
    new ZoomSlider(),
    new RotateNorthControl(),
  ]),
  view: view,
});


var geolocationBar = new GeolocationBar({
  source: tdRoadMapLayer.getSource(),
  followTrack: 'auto',
  minZoom: 16,
  minAccuracy: 10000
});
ol2d.addControl(geolocationBar);

var select = new Select({});
ol2d.addInteraction(select);
var searchControl = new SearchFeature({ //target: $(".options").get(0),
  source: ncovVectorSource,
  property: "full_addre"
});

ol2d.addControl(searchControl);
// Select feature when click on the reference index
searchControl.on('select', function (e) {
  select.getFeatures().clear();
  // console.log("select");
  // console.log(e)
  select.getFeatures().push(e.search);
  var p = e.search.getGeometry().getFirstCoordinate();
  ol2d.getView().animate({
    center: p
  });
});

var save = new extButton(
  {
    html: '<i class="fa fa-download"></i>',
    className: "save",
    title: "Save",
    handleClick: function () {
      ol3d.setEnabled(false);
      console.log(ol3d.getEnabled())
      ol2d.getView().setRotation(0);
      ol2d.getView().fit(
        [
          // 指定要定位到的范围-广州
          12602498.306877896,
          2636537.0728182457,
          12622498.306877896,
          2656537.0728182457,
        ]
      );
      console.log("Center: " + ol2d.getView().getCenter() + " - zoom: " + ol2d.getView().getZoom());

    }
  });
ol2d.addControl(save);

var layerSwitcher = new LayerSwitcher({
  tipLabel: 'MapSelector', // Optional label for button
  groupSelectStyle: 'children' // Can be 'children' [default], 'group' or 'none'
});
ol2d.addControl(layerSwitcher);

var isLayerExist = function (title) {
  var layers = ol2d.getLayers().getArray();
  var layers_len = layers.length;
  for (var i = 0; i < layers_len; i++) {
      if (layers[i].get('title') == title) {
          return true;
      }
  }
  return false;
}

var layerCount = 2;
ol2d.getView().on('change:resolution', function () {
  var zoomLevel = ol2d.getView().getZoom();
  if (zoomLevel <= 4) {
    ol3d.setEnabled(true);
    ol2d.getLayers
    ol2d.removeLayer(ncovVectorLayer);
    ol2d.removeLayer(ncovVoronoiLayer);
    ol2d.removeLayer(heatMapLayer);
  }
  if (zoomLevel > 4) {
    ol3d.setEnabled(false);
    
    console.log(cityLayer.get('title'))
    if (!isLayerExist(cityLayer.get('title'))) {
      ol2d.addLayer(cityLayer);
    }
    if (!isLayerExist(roadLayer.get('title'))) {
      ol2d.addLayer(roadLayer);
    }
  }

  if (zoomLevel > 9) {
    ol3d.setEnabled(false);
    ol2d.removeLayer(provinceLayer);
    ol2d.removeLayer(cityLayer);
    if (!isLayerExist(ncovVoronoiLayer.get('title'))) {
      ol2d.addLayer(ncovVoronoiLayer);
    }
    if (!isLayerExist(heatMapLayer.get('title'))) {
      ol2d.addLayer(heatMapLayer);
    }
    if (!isLayerExist(ncovVectorLayer.get('title'))) {
      ol2d.addLayer(ncovVectorLayer);
    }
  }
  console.log(zoomLevel)
})
// ol2d.on("pointermove", function (evt) {
//   if (evt.dragging) {
//     return;
//   }
//   var pixel = ol2d.getEventPixel(evt.originalEvent);
//   var hit = ol2d.forEachLayerAtPixel(pixel, function () {
//     return true;
//   });
//   ol2d.getTargetElement().style.cursor = hit ? "pointer" : "";
// });

// var source = new VectorSource({
//   wrapX: false
// });
// var vector = new VectorLayer({
//   source: source
// });
// ol2d.addLayer(vector);

// function addRandomFeature() {
//   var x = Math.random() * 360 - 180;
//   var y = Math.random() * 180 - 90;
//   var geom = new Point(fromLonLat([x, y]));
//   var feature = new Feature(geom);
//   source.addFeature(feature);
// }

// var duration = 3000;
// function flash(feature) {
//   var start = new Date().getTime();
//   var listenerKey = ncovLayer.on('postrender', animate);

//   function animate(event) {
//     var vectorContext = getVectorContext(event);
//     var frameState = event.frameState;
//     var flashGeom = feature.getGeometry().clone();
//     var elapsed = frameState.time - start;
//     var elapsedRatio = elapsed / duration;
//     // radius will be 5 at start and 30 at end.
//     var radius = easeOut(elapsedRatio) * 25 + 5;
//     var opacity = easeOut(1 - elapsedRatio);

//     var style = new Style({
//       image: new CircleStyle({
//         radius: radius,
//         stroke: new Stroke({
//           color: 'rgba(255, 0, 0, ' + opacity + ')',
//           width: 0.25 + opacity
//         })
//       })
//     });

//     vectorContext.setStyle(style);
//     vectorContext.drawGeometry(flashGeom);
//     if (elapsed > duration) {
//       unByKey(listenerKey);
//       return;
//     }
//     // tell OpenLayers to continue postrender animation
//     ol2d.render();
//   }
// }

// source.on('addfeature', function(e) {
//   flash(e.feature);
// });

// window.setInterval(addRandomFeature, 1000);

var highlightStyle = new Style({
  stroke: new Stroke({
    color: "#f00",
    width: 1,
  }),
  fill: new Fill({
    color: "rgba(255,0,0,0.1)",
  }),
  text: new Text({
    font: "12px Calibri,sans-serif",
    fill: new Fill({
      color: "#000",
    }),
    stroke: new Stroke({
      color: "#f00",
      width: 3,
    }),
  }),
});

// var featureOverlay = new VectorLayer({
//   source: new VectorSource(),
//   map: ol2d,
//   style: function (feature) {
//     highlightStyle.getText().setText(feature.get("name"));
//     return highlightStyle;
//   },
// });

// var highlight;
// var displayFeatureInfo = function (pixel) {
//   ncovWmsLayer.getFeatures(pixel).then(function (features) {
//     var feature = features.length ? features[0] : undefined;
//     var info = document.getElementById("info");
//     if (features.length) {
//       info.innerHTML = feature.getId() + ": " + feature.get("name");
//     } else {
//       info.innerHTML = "&nbsp;";
//     }

//     if (feature !== highlight) {
//       if (highlight) {
//         featureOverlay.getSource().removeFeature(highlight);
//       }
//       if (feature) {
//         featureOverlay.getSource().addFeature(feature);
//       }
//       highlight = feature;
//     }
//   });
// };

ol2d.on("click", function (e) {
  var pixel = ol2d.getEventPixel(e.originalEvent);
  console.log(pixel);
  ol2d.forEachFeatureAtPixel(pixel, function (feature) {
    console.log(feature);
    var coodinate = e.coordinate;
    //return feature;
    if (feature != null && feature.getProperties('full_addre') != null) {
      var fullAddr = feature.getProperties('full_addre').full_addre;
      console.log(fullAddr);
      content.innerHTML =
        "<p>疫情点地址为：" + fullAddr + "</p>";
      overlay.setPosition(coodinate);
    } else {
      content.innerHTML =
        "<p>你点击的坐标为：" + new toLonLat(coodinate) + "</p>";
      overlay.setPosition(coodinate);
    }

    ol2d.addOverlay(overlay);
  });
});

popupCloser.addEventListener("click", function () {
  overlay.setPosition(undefined);
});


ol2d.on("pointermove", function (evt) {
  if (evt.dragging) {
    var mapExtent = ol2d.getView().calculateExtent(ol2d.getSize());
    // console.log(mapExtent)
    return;
  }
  var pixel = ol2d.getEventPixel(evt.originalEvent);
  // displayFeatureInfo(pixel);
});

// ########################### openlayer conflict with cesium  ########################### 
// OK, I understand now.
// OL-Cesium has two modes:

// stacked mode: either OpenLayers or Cesium is displayed (no target used for OL-Cesium);
// side-by-side mode: moth OpenLayers and Cesium are displayed at the same time (a target is used for OL-Cesium).
// In side-by-side mode OpenLayers is fully active so it will fetch the source data.
// In stacked mode OpenLayers is partially active: it will not fetch new data and thus they will not be converted to Cesium.

// I see 2 solutions:
// 1- implement fetching of vector source data in OL-Cesium based on the area displayed in Cesium (most complicate);
// 2- load the data in the vector source yourself (easy).

// With 2 you should get the features and add them to the source:
// vectorSource.setFeatures(theFeaturesYouLoadedManually);

ol2d.on("moveend", function (evt) {

  var mapExtent = ol2d.getView().calculateExtent(ol2d.getSize());
  // console.log(mapExtent)
  var newPoly = fromExtent(mapExtent);

  //获取当前地图的缩放级别
  var zoom = ol2d.getView().getZoom();

  // Openlayer4的wfs属性查询和空间查询
  //测试用的geometry类型数据 （Polygon）
  // var newPoly2 = new Polygon([
  //   [
  //     [119.89817, 31.91181],
  //     [119.81655, 31.85485],
  //     [119.95809, 31.84721],
  //     [119.89817, 31.91181]
  //   ]
  // ]);
  // console.log(newPoly2)
  // //创建字符过滤器 可以过滤字段 添加%%可以模糊查询
  // var fcodeFilter = ol.format.filter.equalTo('fcode', value)
  // //创建空间过滤器 可以查询特定区域下的数据
  var areaFilter = new intersects(
    'the_geom',
    newPoly,
    'EPSG:3857'
  );
  //来自官网Example
  // generate a GetFeature request
  var featureRequest = new WFS().writeGetFeature({
    srsName: 'EPSG:4326',
    // featureNS: 'http://173.193.109.188:30657', //命名空间
    featurePrefix: 'blockmap', //工作区域
    featureTypes: ['ncov_china_data'], //图层名
    outputFormat: 'application/json',
    filter: andFilter(
      // likeFilter('fulladdr', '广东省*'),
      areaFilter,
      areaFilter,
      // equalToFilter('province', '广东省'),
      // equalToFilter('city', '广州市')
    )
  });

  // // then post the request and add the received features to a layer
  // fetch('http://173.193.109.188:30657/geoserver/wfs', {
  //   method: 'POST',
  //   body: new XMLSerializer().serializeToString(featureRequest)
  // }).then(function (response) {
  //   console.log(response)
  //   return response.json();
  // }).then(function (json) {

  //   var features = new GeoJSON().readFeatures(json, {
  //     dataProjection: 'EPSG:4326',
  //     featureProjection: 'EPSG:3857'
  //   });
  //   console.log(features)
  //   if (features.length === 0) {
  //     layer.msg('此区域暂无相关地物数据！', {
  //       icon: 2
  //     });
  //     return;
  //   }
  //   ncovPointVectorSource.addFeatures(features);
  //   // //创建矢量层
  //   // var vectorLayer = new VectorLayer({
  //   //   source: ncovVectorSource,
  //   //   style: iconStyle,
  //   //   name: "疫情点地图",
  //   //   title: '疫情点地图',
  //   //   visible: false,
  //   // });
  //   // ol2d.addLayer(vectorLayer)
  //   // ol2d.getView().fit(vectorSource.getExtent());
  // });
})

// ol2d.on('click', function (evt) {
//   displayFeatureInfo(evt.pixel);
// });

/* #################### Cesium Begin#################### */

const ol3d = new OLCesium({
  map: ol2d
});
// ol2dMap is the ol.Map instance

// scene.terrainProvider = Cesium.createWorldTerrain();
ol3d.setEnabled(false);
const scene = ol3d.getCesiumScene();

// var clock = new Cesium.Viewer.prototype.clock;
// var camera = new Cesium.Viewer.prototype.camera;
// 利用改变相机位置，试人感觉上地球在自转,以笛卡尔坐标系定位的模型，物体等都会随地球自转而移动
// var i = Date.now();

// function rotate() {
//   var a = 0.01;
//   var t = Date.now();
//   var n = (t - i) / 1e3;
//   i = t;
//   viewer.scene.camera.rotate(Cesium.Cartesian3.UNIT_Z, -a * n);
// }

// viewer.clock.onTick.addEventListener(rotate);
// setTimeout(function () {
//   viewer.clock.onTick.removeEventListener(rotate);
// }, 5000);

// 官方提供的自转方法

// function icrf() {
//   if (scene.mode !== Cesium.SceneMode.SCENE3D) {
//     return;
//   }
//   var icrfToFixed = Cesium.Transforms.computeIcrfToFixedMatrix(
//     viewer.clock.currentTime
//     // clock.currentTime
//   );
//   if (Cesium.defined(icrfToFixed)) {
//     var camera = viewer.camera;
//     var offset = Cesium.Cartesian3.clone(camera.position);
//     var transform = Cesium.Matrix4.fromRotationTranslation(icrfToFixed);
//     camera.lookAtTransform(transform, offset);
//   }

// }
// scene.postUpdate.addEventListener(icrf);

// loadLayersControl(map, "layerTree");
//新建一个Cesium服务，将画布嵌入到id是cesiumContainer的DOM元素中
// var viewer = new Cesium.Viewer('map', {
//   // 查找位置工具，查找到之后会将镜头对准找到的地址，默认使用bing地图
//   geocoder: true,
//   // 视角返回初始位置
//   homeButton: true,
//   // 选择视角的模式，有三种：3D，2D，哥伦布视图（2.5D)
//   sceneModePicker: true,
//   // 图层选择器，选择要显示的地图服务和地形服务
//   baseLayerPicker: true,
//   // 导航帮助按钮，显示默认的地图控制帮助
//   navigationHelpButton: false,
//   animation: false, //左下角的动画控件的显示
//   shouldAnimate: false, //控制模型动画
//   timeline: false,
//   // 全屏按钮
//   fullscreenButton: false,
//   imageryProvider: new Cesium.WebMapTileServiceImageryProvider({
//     url: "http://t0.tianditu.gov.cn/img_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=img&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles&tk=" + tdToken,
//     layer: "tdtImgBasicLayer",
//     style: "default",
//     format: "image/jpeg",
//     show: false
//   })
// })

// viewer.clock.multiplier = 200;//速度
// viewer.clock.shouldAnimate = true;
// var previousTime = viewer.clock.currentTime.secondsOfDay;
// function onTickCallback() {
//   var spinRate = 1;
//   var currentTime = viewer.clock.currentTime.secondsOfDay;
//   var delta = (currentTime - previousTime) / 1000;
//   previousTime = currentTime;
//   viewer.scene.camera.rotate(Cesium.Cartesian3.UNIT_Z, -spinRate * delta);
// }
// viewer.clock.onTick.addEventListener(onTickCallback);

// var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
// handler.setInputAction(function (click) {
//   viewer.clock.onTick.removeEventListener(onTickCallback);
// }, Cesium.ScreenSpaceEventType.LEFT_DOWN);

// // 图层选择器出事图层
// // viewer.baseLayerPicker.viewModel.selectedImagery = viewer.baseLayerPicker.viewModel.imageryProviderViewModels[9];

// viewer.camera.flyHome(2);

// var provider = new Cesium.WebMapServiceImageryProvider({
//   url: 'http://173.193.109.188:30657/geoserver/blockmap/wms',
//   layers: 'blockmap:province0',
//   parameters: {
//     service: 'WMS',
//     format: 'image/png',
//     transparent: true,
//   }
// });
// viewer.imageryLayers.addImageryProvider(provider);

// //监听地图移动完成事件
// viewer.camera.moveEnd.addEventListener(function () {
//   //获取当前相机高度
//   let height = Math.ceil(viewer.camera.positionCartographic.height);
//   //  let zoom = viewer.heightToZoom(height);
//   let bounds = viewer.camera.computeViewRectangle();
//   console.log('地图变化监听事件', bounds);
// });

// // AJAX获取GeoJson数据
// //    $.ajax({
// //            url:"http://localhost:8082/geoserver/mytest/ows?service=WFS&request=GetFeature&typeName=mytest:river4&outputFormat=application/json",
// //            cache: false,
// //            async: true,
// //            success: function(data) {
// //                  var datasource=Cesium.GeoJsonDataSource.load(data);
// //                viewer.dataSources.add(datasource);
// //            },
// //            error: function(data) {
// //                 console.log("error");
// //            }
// //    });

// // 数据就被加载到cesium中了，然后使用 dataSource.entities.values，就可以对图层进行渲染了,代码如下:
// // var entities = dataSource.entities.values;for (var i = 0; i < entities.length; i++) {
// //   var entity = entities[i];
// //   var polylineVolume = {
// //                   positions:entity.polyline._positions,
// //                   shape:computeCircle(50.0),
// //                   material:Cesium.Color.RED
// //                }
// //   entity.polylineVolume=polylineVolume;
// //   entity.polyline=null;
// // }

// //监听地图移动完成事件
// // onMoveendMap = () => {
// //   //获取当前相机高度
// //   let height = Math.ceil(viewer.camera.positionCartographic.height);
// //   let zoom = viewer.heightToZoom(height);
// //   let bounds = viewer.getCurrentExtent();
// //   console.log('地图变化监听事件', zoom, bounds);
// // };

// viewer.imageryLayers.addImageryProvider(new Cesium.WebMapTileServiceImageryProvider({
//   url: "http://t0.tianditu.gov.cn/cia_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cia&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles&tk=" +
//     tdToken,
//   layer: "tdtImgAnnoLayer",
//   style: "default",
//   format: "image/jpeg",
//   tileMatrixSetID: "GoogleMapsCompatible",
//   show: false
// }));

// /* 三维球转动添加监听事件 */
// viewer.camera.changed.addEventListener(function (percentage) {
//   getCenterPosition();
//   getCurrentExtent();
//   // 打印中心点坐标、高度、当前范围坐标
//   console.log(getCenterPosition());
//   console.log("bounds", getCurrentExtent());
// });

/* 获取camera高度  */
function getHeight() {
  if (viewer) {
    var scene = viewer.scene;
    var ellipsoid = scene.globe.ellipsoid;
    var height = ellipsoid.cartesianToCartographic(viewer.camera.position).height;
    return height;
  }
}

/* 获取camera中心点坐标 */
function getCenterPosition() {
  var result = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(viewer.canvas.clientWidth / 2, viewer.canvas
    .clientHeight / 2));
  var curPosition = Cesium.Ellipsoid.WGS84.cartesianToCartographic(result);
  var lon = curPosition.longitude * 180 / Math.PI;
  var lat = curPosition.latitude * 180 / Math.PI;
  var height = getHeight();
  return {
    lon: lon,
    lat: lat,
    height: height
  };
}

function getCurrentExtent() {
  // 范围对象
  var extent = {};

  // 得到当前三维场景
  var scene = viewer.scene;

  // 得到当前三维场景的椭球体
  var ellipsoid = scene.globe.ellipsoid;
  var canvas = scene.canvas;

  // canvas左上角
  var car3_lt = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(0, 0), ellipsoid);

  // canvas右下角
  var car3_rb = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(canvas.width, canvas.height), ellipsoid);

  // 当canvas左上角和右下角全部在椭球体上
  if (car3_lt && car3_rb) {
    var carto_lt = ellipsoid.cartesianToCartographic(car3_lt);
    var carto_rb = ellipsoid.cartesianToCartographic(car3_rb);
    extent.xmin = Cesium.Math.toDegrees(carto_lt.longitude);
    extent.ymax = Cesium.Math.toDegrees(carto_lt.latitude);
    extent.xmax = Cesium.Math.toDegrees(carto_rb.longitude);
    extent.ymin = Cesium.Math.toDegrees(carto_rb.latitude);
  }

  // 当canvas左上角不在但右下角在椭球体上
  else if (!car3_lt && car3_rb) {
    var car3_lt2 = null;
    var yIndex = 0;
    do {
      // 这里每次10像素递加，一是10像素相差不大，二是为了提高程序运行效率
      yIndex <= canvas.height ? yIndex += 10 : canvas.height;
      car3_lt2 = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(0, yIndex), ellipsoid);
    } while (!car3_lt2);
    var carto_lt2 = ellipsoid.cartesianToCartographic(car3_lt2);
    var carto_rb2 = ellipsoid.cartesianToCartographic(car3_rb);
    extent.xmin = Cesium.Math.toDegrees(carto_lt2.longitude);
    extent.ymax = Cesium.Math.toDegrees(carto_lt2.latitude);
    extent.xmax = Cesium.Math.toDegrees(carto_rb2.longitude);
    extent.ymin = Cesium.Math.toDegrees(carto_rb2.latitude);
  }

  // 获取高度
  extent.height = Math.ceil(viewer.camera.positionCartographic.height);
  return extent;
}

/* #################### Cesium End#################### */


/* #################### dijkstra Routine Begin #################### */

  // // The vector graph
  // var graph = new VectorSource({
  //   url: 'https://viglino.github.io/ol-ext/examples/data/ROUTE120.geojson',
  //   format: new GeoJSON()
  // });
  // listenerKey = graph.on('change', function() {
  //   if (graph.getState() == 'ready') {
  //     $('.loading').hide();
  //     ol.Observable.unByKey(listenerKey);
  //   }
  // });
	// var vector = new VectorLayer({
  //   title: 'Graph',
	// 	source: graph
	// });
  // map.addLayer(vector);

  // // A layer to draw the result
  // var result = new VectorSource();
  // map.addLayer ( new VectorLayer({
  //   source: result,
  //   style: new Style({ 
  //     stroke: new Stroke({ 
  //       width: 2,
  //       color: "#f00"
  //     }) 
  //   })
  // }));
  
  // // Dijkstra
  // var dijkstra = new Dijskra({
	// 	source: graph
  // });
  // // Start processing
  // dijkstra.on('start', function(e) {
  //   $('#warning').hide();
  //   $("#notfound").hide();
  //   $("#notfound0").hide();
  //   $("#result").hide();
  //   result.clear();
  // });
  // // Finish > show the route
  // dijkstra.on('finish', function(e) {
  //   $('#warning').hide();
  //   result.clear();
  //   console.log(e);
  //   if (!e.route.length) {
  //     if (e.wDistance===-1) $("#notfound0").show();
  //     else $("#notfound").show();
  //     $("#result").hide();
  //   } else {
  //     $("#result").show();
  //     var t = (e.distance/1000).toFixed(2) + 'km';
  //     // Weighted distance
  //     if ($("#speed").prop('checked')) {
  //       var h = e.wDistance/1000;
  //       var mn = Math.round((e.wDistance%1000)/1000*60);
  //       if (mn < 10) mn = '0'+mn;
  //       t += '<br/>' + h.toFixed(0) + 'h ' + mn + 'mn';
  //     }
  //     $("#result span").html(t);
  //   }
  //   result.addFeatures(e.route);
  //   start = end;
  //   popStart.show(start);
  //   popEnd.hide();
  // });
  // // Paused > resume
  // dijkstra.on('pause', function(e) {
  //   if (e.overflow) {
  //     $('#warning').show();
  //     dijkstra.resume();
  //   } else {
  //     // User pause
  //   }
  // });
  // // Calculating > show the current "best way"
  // dijkstra.on('calculating', function(e) {
  //   if ($('#path').prop('checked')) {
  //     var route = dijkstra.getBestWay();
  //     result.clear();
  //     result.addFeatures(route);
  //   }
  // });

  // // Get the weight of an edge
  // dijkstra.weight = function(feature) {
  //   var type = feature ? feature.get('type') : 'A';
  //   if (!speed[type]) console.error(type)
  //   return speed[type] || speed.L;
  // };
  // // Get direction of the edge
  // dijkstra.direction = function(feature) {
  //   return feature.get('dir');
  // }
  // // Get the real length of the geom
  // dijkstra.getLength = function(geom) {
  //   if (geom.getGeometry) {
  //     //? return geom.get('km')*1000;
  //     geom = geom.getGeometry();
  //   }
  //   return sphere.getLength(geom)
  // }

  // // Display nodes in a layer
	// var nodes = new VectorLayer({
  //   title: 'Nodes',
  //   source: dijkstra.getNodeSource(),
  //   style: new Style({
  //     image: new Circle({
  //       radius: 5,
  //       fill: new Fill({ color: [255,0,0,.1] })
  //     })
  //   })
	// });
  // ol2d.addLayer(nodes);

  // // Start / end Placemark
  // var popStart = new Placemark({ popupClass: 'flagv', color: '#080' });
  // ol2d.addOverlay(popStart);
  // var popEnd = new Placemark({ popupClass: 'flag finish', color: '#000' });
  // ol2d.addOverlay(popEnd);

  // // Manage start / end on click
  // var start, end;
  // ol2d.on('click', function(e) {
  //   if (!start) {
  //     start = e.coordinate;
  //     popStart.show(start);
  //   } else {
  //     var se = dijkstra.path(start, e.coordinate);
  //     if (se) {
  //       start = se[0];
  //       end = se[1];
  //       popEnd.show(end);
  //     }
  //   }
  // });

  /* #################### dijkstra Routine End #################### */