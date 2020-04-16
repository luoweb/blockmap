import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
// import TileLayer from 'ol/layer/Tile';
import XYZ from "ol/source/XYZ";
import Icon from 'ol/style/Icon';
import {
  equalTo as equalToFilter,
  like as likeFilter,
  and as andFilter
} from 'ol/format/filter';
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
import FullScreen from "ol/control/FullScreen";
import * as olControl from "ol/control";
import Zoom from "ol/control/Zoom";
import ZoomToExtent from "ol/control/ZoomToExtent";
import Heatmap from 'ol/layer/Heatmap';

import Feature from "ol/Feature";
import {
  unByKey
} from "ol/Observable";
import {
  easeOut
} from "ol/easing";
import Point from "ol/geom/Point";
import {
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

import Polygon from 'ol/geom/Polygon';

import * as olLoadingstrategy from 'ol/loadingstrategy';
import * as olTilegrid from 'ol/tilegrid';
import { WFS, GeoJSON } from 'ol/format';

/*********************显示弹出层**************************/
var container = document.getElementById("popup");
var content = document.getElementById("popup-content");
var popupCloser = document.getElementById("popup-closer");

var overlay = new Overlay({
  element: container,
  autoPan: true
});

var tdRoadMapLayer = new TileLayer({
  source: new XYZ({
    url: "https://t0.tianditu.gov.cn/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=320109f58cbb412b31e478ddc5c651bd",
  }),
  isGroup: true,
  name: "天地图路网",
});

var borderLayer = new ImageLayer({
  source: new ImageWMS({
    url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
    params: {
      LAYERS: "border",
    },
    serverType: "geoserver",
    crossOrigin: "anonymous",
  }),
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
});

var ncovSource = new ImageWMS({
  url: "http://173.193.109.188:30657/geoserver/blockmap/wms",
  params: {
    LAYERS: "ncov_china_data",
  },
  serverType: "geoserver",
  crossOrigin: "anonymous",
});
var ncovLayer = new ImageLayer({
  source: ncovSource,
});

// Heatmap热力图
//热力图数据 GeoJSON默认参考坐标系为 EPSG:4326.，根据实际需要进行更改
var heatData = [{
  type: "FeatureCollection",
  features: [{
    type: "Point",
    coordinates: fromLonLat([113.25, 23.11]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([113.29, 23.14]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([113.3, 23.14]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([113.31, 23.11]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([113.32, 23.12]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([112.15, 22.21]),
    count: 90,
  },
  {
    type: "Point",
    coordinates: fromLonLat([112.17, 22.23]),
    count: 90,
  },
  {
    type: "Point",
    coordinates: fromLonLat([112.27, 22.13]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([113.27, 23.13]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([112.29, 22.57]),
    count: 80,
  },
  {
    type: "Point",
    coordinates: fromLonLat([112.29, 24.17]),
    count: 80,
  }
  ],
},];

// var vectorLayer = new ol.layer.Vector({
//         source: new ol.source.Vector({
//                 url: 'data/geojson/countries.geojson',
//                 format: new ol.format.GeoJSON()
//         })
// });

var heatMapLayer = new Heatmap({
  source: new VectorSource({
    features: new GeoJSON().readFeatures(heatData[0]),
  }),
  opacity: 0.8, //透明度
  blur: 15, //模糊大小（以像素为单位）,默认15
  radius: 20, //半径大小（以像素为单位,默认8
  shadow: 250, //阴影像素大小，默认250
  //矢量图层的渲染模式：
  //'image'：矢量图层呈现为图像。性能出色，但点符号和文本始终随视图一起旋转，像素在缩放动画期间缩放。
  //'vector'：矢量图层呈现为矢量。即使在动画期间也能获得最准确的渲染，但性能会降低。
  renderMode: "vector",
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

// var provinceWfsLayer = new VectorLayer({
//   source: new VectorSource({
//     url: 'http://173.193.109.188:30657/geoserver/wfs?service=wfs&version=1.1.0&request=GetFeature&typeNames=blockmap%3Anprovince&outputFormat=application/json&srsname=EPSG:4326',
//     format: new GeoJSON({
//       geometryName: 'the_geom'
//     })
//   }),
//   style: function (feature) {
//     style.getText().setText(feature.get('name'));
//     return style;
//   }
// });



var ncovWfsLayer = new VectorLayer({
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



var vectorSource = new VectorSource({});
//创建图标特性
var iconFeature = new Feature({
  geometry: new Point(new fromLonLat([113.27, 23.13]), "XY"),
  name: "my Icon",
});
//将图标特性添加进矢量中
vectorSource.addFeature(iconFeature);
//创建图标样式
var iconStyle = new Style({
  image: new Icon({
    opacity: 0.75,
    src: "https://openlayers.org/en/latest/examples/data/icon.png"
  }),
});
//创建矢量层
var vectorLayer = new VectorLayer({
  source: vectorSource,
  style: iconStyle
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

// //回调函数使用
// window.loadFeatures = function (response) {
//   console.log("##############")
//   console.log(response)
//   ncovVectorSource.addFeatures((new GeoJSON()).readFeatures(response)); //载入要素
// };

// var wfsVectorLayer = new VectorLayer({
//   source: ncovVectorSource,
//   style: new Style({
//     stroke: new Stroke({
//       color: 'rgba(0, 0, 255, 1.0)',
//       width: 2
//     })
//   })
// });

var view = new View({
  center: fromLonLat([113.3, 23.12]),
  zoom: 12,
});
console.log(fromLonLat([113.3, 23.12]));

var fullScreenControl = new FullScreen();
var zoomControl = new Zoom();
var map = new Map({
  layers: [
    tdRoadMapLayer,
    // borderLayer,
    provinceLayer,
    // provinceWfsLayer,
    // cityLayer,
    ncovWfsLayer,
    ncovLayer,
    heatMapLayer,
    // wfsVectorLayer
  ],
  target: "map",
  controls: olControl.defaults().extend([
    // 将用于将地图定位到指定范围的ZoomToExtent控件加入到地图的默认控件中
    new ZoomToExtent({
      extent: [
        // 指定要定位到的范围-广州
        12602498.306877896,
        2636537.0728182457,
        12622498.306877896,
        2656537.0728182457,
        // 813079.7791264898,
        // 5929220.284081122,
        // 848966.9639063801,
        // 5936863.986909639,
      ],
    }),
  ]),
  view: view,
});

map.addControl(fullScreenControl);
map.addControl(zoomControl);

map.on("singleclick", function (evt) {
  document.getElementById("info").innerHTML = "";
  var viewResolution = /** @type {number} */ (view.getResolution());
  var url = ncovSource.getFeatureInfoUrl(
    evt.coordinate,
    viewResolution,
    "EPSG:3857", {
    INFO_FORMAT: "text/html",
  }
  );
  if (url) {
    fetch(url)
      .then(function (response) {
        return response.text();
      })
      .then(function (html) {
        document.getElementById("info").innerHTML = html;
      });
  }
});

// map.on("pointermove", function (evt) {
//   if (evt.dragging) {
//     return;
//   }
//   var pixel = map.getEventPixel(evt.originalEvent);
//   var hit = map.forEachLayerAtPixel(pixel, function () {
//     return true;
//   });
//   map.getTargetElement().style.cursor = hit ? "pointer" : "";
// });

// var source = new VectorSource({
//   wrapX: false
// });
// var vector = new VectorLayer({
//   source: source
// });
// map.addLayer(vector);

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
//     map.render();
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

var featureOverlay = new VectorLayer({
  source: new VectorSource(),
  map: map,
  style: function (feature) {
    highlightStyle.getText().setText(feature.get("name"));
    return highlightStyle;
  },
});

var highlight;
var displayFeatureInfo = function (pixel) {
  ncovWfsLayer.getFeatures(pixel).then(function (features) {
    var feature = features.length ? features[0] : undefined;
    var info = document.getElementById("info");
    if (features.length) {
      info.innerHTML = feature.getId() + ": " + feature.get("name");
    } else {
      info.innerHTML = "&nbsp;";
    }

    if (feature !== highlight) {
      if (highlight) {
        featureOverlay.getSource().removeFeature(highlight);
      }
      if (feature) {
        featureOverlay.getSource().addFeature(feature);
      }
      highlight = feature;
    }
  });
};

map.on("click", function (e) {
  var pixel = map.getEventPixel(e.originalEvent);
  console.log(pixel);
  map.forEachFeatureAtPixel(pixel, function (feature) {
    //console.log(feature);
    //return feature;
    var coodinate = e.coordinate;
    content.innerHTML =
      "<p>你点击的坐标为：" + new toLonLat(coodinate) + "</p>";
    overlay.setPosition(coodinate);
    map.addOverlay(overlay);
  });
});

popupCloser.addEventListener("click", function () {
  overlay.setPosition(undefined);
});




map.on("pointermove", function (evt) {
  if (evt.dragging) {
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

map.on("moveend", function (evt) {

  var mapExtent = map.getView().calculateExtent(map.getSize());
  console.log(mapExtent)
  // Openlayer4的wfs属性查询和空间查询
  //测试用的geometry类型数据 （Polygon）
  var newPoly = new Polygon([
    [
      [119.89817, 31.91181],
      [119.81655, 31.85485],
      [119.95809, 31.84721],
      [119.89817, 31.91181]
    ]
  ]);
  // //创建字符过滤器 可以过滤字段 添加%%可以模糊查询
  // var fcodeFilter = ol.format.filter.equalTo('fcode', value)
  // //创建空间过滤器 可以查询特定区域下的数据
  // var areaFilter = ol.format.filter.intersects(
  //   'points',
  //   newPoly
  // )
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
      equalToFilter('province', '广东省'),
      equalToFilter('city', '广州市')
    )
  });

  // then post the request and add the received features to a layer
  fetch('http://173.193.109.188:30657/geoserver/wfs', {
    method: 'POST',
    body: new XMLSerializer().serializeToString(featureRequest)
  }).then(function (response) {
    console.log(response)
    return response.json();
  }).then(function (json) {

    var features = new GeoJSON().readFeatures(json, {
      dataProjection: 'EPSG:4528',
      featureProjection: 'EPSG:3857'
    });
    console.log(json)
    if (features.length === 0) {
      layer.msg('此区域暂无相关地物数据！', {
        icon: 2
      });
      return;
    }
    vectorSource.addFeatures(features);
    // facilities.set(value, features);
    var iconStyle = new Style({
      image: new Icon({
        opacity: 0.75,
        src: "https://openlayers.org/en/latest/examples/data/icon.png"
      }),
    });
    //创建矢量层
    var vectorLayer = new VectorLayer({
      source: vectorSource,
      style: iconStyle
    });
    map.addLayer(vectorLayer)
    // map.getView().fit(vectorSource.getExtent());
  });
})

// map.on('click', function (evt) {
//   displayFeatureInfo(evt.pixel);
// });