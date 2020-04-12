import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
// import TileLayer from 'ol/layer/Tile';
import XYZ from 'ol/source/XYZ';
import {
  fromLonLat
} from 'ol/proj';
import GeoJSON from 'ol/format/GeoJSON';
// import VectorLayer from 'ol/layer/Vector';
// import VectorSource from 'ol/source/Vector';
// import {Fill, Stroke, Style, Text} from 'ol/style';
import ImageLayer from 'ol/layer/Image';
import ImageWMS from 'ol/source/ImageWMS';

import Feature from 'ol/Feature';
import {
  unByKey
} from 'ol/Observable';
import {
  easeOut
} from 'ol/easing';
import Point from 'ol/geom/Point';
import {
  Tile as TileLayer,
  Vector as VectorLayer
} from 'ol/layer';
import {
  OSM,
  Vector as VectorSource
} from 'ol/source';
import {
  getVectorContext
} from 'ol/render';
import {
  Circle as CircleStyle,
  Stroke,
  Style,
  Fill,
  Text
} from 'ol/style';




var tdRoadMapLayer = new TileLayer({
  source: new XYZ({
    url: 'https://t0.tianditu.gov.cn/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=320109f58cbb412b31e478ddc5c651bd'
  }),
  isGroup: true,
  name: '天地图路网'
});

var borderLayer = new ImageLayer({
  source: new ImageWMS({
    url: 'http://173.193.109.188:30657/geoserver/blockmap/wms',
    params: {
      'LAYERS': 'border'
    },
    serverType: 'geoserver',
    crossOrigin: 'anonymous'
  })
});


var provinceLayer = new ImageLayer({
  source: new ImageWMS({
    url: 'http://173.193.109.188:30657/geoserver/blockmap/wms',
    params: {
      'LAYERS': 'province0'
    },
    serverType: 'geoserver',
    crossOrigin: 'anonymous'
  })
});


var cityLayer = new ImageLayer({
  source: new ImageWMS({
    url: 'http://173.193.109.188:30657/geoserver/blockmap/wms',
    params: {
      'LAYERS': 'city'
    },
    serverType: 'geoserver',
    crossOrigin: 'anonymous'
  })
});

var ncovSource = new ImageWMS({
  url: 'http://173.193.109.188:30657/geoserver/blockmap/wms',
  params: {
    'LAYERS': 'ncov_china_data'
  },
  serverType: 'geoserver',
  crossOrigin: 'anonymous'
})
var ncovLayer = new ImageLayer({
  source: ncovSource
});


var style = new Style({
  fill: new Fill({
    color: 'rgba(255, 255, 255, 0.6)'
  }),
  stroke: new Stroke({
    color: '#319FD3',
    width: 1
  }),
  text: new Text({
    font: '12px Calibri,sans-serif',
    fill: new Fill({
      color: '#000'
    }),
    stroke: new Stroke({
      color: '#fff',
      width: 3
    })
  })
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
    url: 'http://173.193.109.188:30657/geoserver/wfs?service=wfs&version=1.1.0&request=GetFeature&typeNames=blockmap%3Ancov_china_data&outputFormat=application/json&srsname=EPSG:4326',
    format: new GeoJSON({
      geometryName: 'the_geom'
    })
  }),
  style: function (feature) {
    // style.getText().setText(feature.get('city'));
    return style;
  }
});

var view = new View({
  center: fromLonLat([113.30, 23.12]),
  zoom: 12
});

var map = new Map({
  layers: [tdRoadMapLayer,
    // borderLayer,
    provinceLayer,
    // provinceWfsLayer,
    // cityLayer,
    ncovWfsLayer,
    ncovLayer
  ],
  target: 'map',
  view: view
});


map.on('singleclick', function (evt) {
  document.getElementById('info').innerHTML = '';
  var viewResolution = /** @type {number} */ (view.getResolution());
  var url = ncovSource.getFeatureInfoUrl(
    evt.coordinate, viewResolution, 'EPSG:3857', {
      'INFO_FORMAT': 'text/html'
    });
  if (url) {
    fetch(url)
      .then(function (response) {
        return response.text();
      })
      .then(function (html) {
        document.getElementById('info').innerHTML = html;
      });
  }
});

map.on('pointermove', function (evt) {
  if (evt.dragging) {
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  var hit = map.forEachLayerAtPixel(pixel, function () {
    return true;
  });
  map.getTargetElement().style.cursor = hit ? 'pointer' : '';
});


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
    color: '#f00',
    width: 1
  }),
  fill: new Fill({
    color: 'rgba(255,0,0,0.1)'
  }),
  text: new Text({
    font: '12px Calibri,sans-serif',
    fill: new Fill({
      color: '#000'
    }),
    stroke: new Stroke({
      color: '#f00',
      width: 3
    })
  })
});

var featureOverlay = new VectorLayer({
  source: new VectorSource(),
  map: map,
  style: function (feature) {
    highlightStyle.getText().setText(feature.get('name'));
    return highlightStyle;
  }
});

var highlight;
var displayFeatureInfo = function (pixel) {

  ncovWfsLayer.getFeatures(pixel).then(function (features) {
    var feature = features.length ? features[0] : undefined;
    var info = document.getElementById('info');
    if (features.length) {
      info.innerHTML = feature.getId() + ': ' + feature.get('name');
    } else {
      info.innerHTML = '&nbsp;';
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

map.on('pointermove', function (evt) {
  if (evt.dragging) {
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

// map.on('click', function (evt) {
//   displayFeatureInfo(evt.pixel);
// });