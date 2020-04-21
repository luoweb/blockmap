var mapApi = function () {
    //坐标系
    var deproj = ol.proj.get('EPSG:4326');
    //中心坐标
    var coordinates = [113.653055, 34.800358];
    //矢量层数据源
    var vectorSource = 'gisData.json';
    //默认的style
    var image = new ol.style.Circle({
        radius: 5,
        fill: new ol.style.Fill({
            color: 'red'
        }),
        stroke: new ol.style.Stroke({
            color: 'red',
            width: 1
        })
    });
    var styles = {
        'Point': new ol.style.Style({
            image: image
        }),
        'LineString': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                width: 1
            })
        }),
        'MultiLineString': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                width: 1
            })
        }),
        'MultiPoint': new ol.style.Style({
            image: image
        }),
        'MultiPolygon': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                width: 1
            }),
            fill: new ol.style.Fill({
                color: 'rgba(255, 255, 0, 0.1)'
            })
        }),
        'Polygon': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                lineDash: [4],
                width: 3
            }),
            fill: new ol.style.Fill({
                color: 'rgba(0, 0, 255, 0.1)'
            })
        }),
        'GeometryCollection': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'magenta',
                width: 2
            }),
            fill: new ol.style.Fill({
                color: 'magenta'
            }),
            image: new ol.style.Circle({
                radius: 10,
                fill: null,
                stroke: new ol.style.Stroke({
                    color: 'magenta'
                })
            })
        }),
        'Circle': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'red',
                width: 2
            }),
            fill: new ol.style.Fill({
                color: 'rgba(255,0,0,0.2)'
            })
        })
    };

    var maxvalue = 4;
    var minvalue = 0;
    var changeStyles = {
        'Point': new ol.style.Style({
            image: image
        }),
        'LineString': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                width: 1
            })
        }),
        'MultiLineString': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'green',
                width: 1
            })
        }),
        'MultiPoint': new ol.style.Style({
            image: image
        }),
        'MultiPolygon': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'yellow',
                width: 1
            }),
            fill: new ol.style.Fill({
                color: 'rgba(255, 255, 0, 0.1)'
            })
        }),
        'Polygon': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'blue',
                lineDash: [4],
                width: 3
            }),
            fill: new ol.style.Fill({
                color: 'rgba(0, 0, 255, 0.1)'
            })
        }),
        'GeometryCollection': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'magenta',
                width: 2
            }),
            fill: new ol.style.Fill({
                color: 'magenta'
            }),
            image: new ol.style.Circle({
                radius: 10,
                fill: null,
                stroke: new ol.style.Stroke({
                    color: 'magenta'
                })
            })
        }),
        'Circle': new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'red',
                width: 2
            }),
            fill: new ol.style.Fill({
                color: 'rgba(255,0,0,0.2)'
            })
        })
    };

    function getColorByLevel(level) {
        // console.log(level+"int"+parseInt(level));
        if (parseInt(level) == 1) {
            return '#cc0000';
        }
        if (parseInt(level) == 2) {
            return '#ff9900';
        }
        if (parseInt(level) == 4) {
            return '#8dc43c';
        }
        if (parseInt(level) == 3) {
            return '#b6d7a8';
        }
        return '#ffffff';
    }

    function getColorByPercentValue(value) {
        // console.log(value);
        if (parseFloat(value) >= 0.3 && parseFloat(value) <= 1) {
            return '#cc0000';
        }
        if (parseFloat(value) >= 0.2 && parseFloat(value) < 0.3) {
            return '#ff9900';
        }
        if (parseFloat(value) >= 0.1 && parseFloat(value) < 0.2) {
            return '#b6d7a8';
        }
        if (parseFloat(value) >= 0 && parseFloat(value) < 0.1) {
            return '#8dc43c';
        }
        return '#ffffff';
    }

    function getColorByDensityValue(value) {
        // console.log(value);
        if (value == 'A' || value == '1') {
            return '#cc0000';
        }
        if (value == 'B' || value == '2') {
            return '#ff9900';
        }
        if (value == 'C' || value == '3') {
            return '#b6d7a8';
        }
        if (value == 'D' || value == '4') {
            return '#8dc43c';
        }
        return '#ffffff';
    }
    //获取宫格的填充样式，根据宫格ID获取宫格对应的指标值，然后进行插值
    function getFillStyle(feature) {
        var gridvalue = feature.getProperties().value;
        var color = getColorByLevel(gridvalue);
        // console.log(color);
        var styleArray = [new ol.style.Style({
            fill: new ol.style.Fill({
                color: color
            }),
            stroke: new ol.style.Stroke({
                color: '#000000',
                width: 1
            })
        })];
        return styleArray;
    }

    function getNetFillStyle(feature) {
        var gridvalue = feature.getProperties().value;
        var color = getColorByPercentValue(gridvalue);
        // console.log(color);
        var styleArray = [new ol.style.Style({
            fill: new ol.style.Fill({
                color: color
            }),
            stroke: new ol.style.Stroke({
                color: '#000000',
                width: 1
            })
        })];
        return styleArray;
    }

    function getDensityFillStyle(feature) {
        var gridvalue = feature.getProperties().value;
        console.log(gridvalue);
        var color = getColorByDensityValue(gridvalue);
        var styleArray = [new ol.style.Style({
            fill: new ol.style.Fill({
                color: color
            }),
            stroke: new ol.style.Stroke({
                color: '#000000',
                width: 1
            })
        })];
        return styleArray;
    }
    /**
     * 设置默认投影系
     *
     * @param   proj  新的投影系
     */
    function setDeproj(proj) {
        deproj = ol.proj.get(proj);
    }
    /**
     * 设置地图中心点坐标
     *
     * @param   coord[Array] 中心点坐标
     */
    function setCoordinates(coord) {
        coordinates = [coord[0], coord[1]];
    }
    /**
     * 设置矢量图层数据源
     *
     * @param   source[string]    矢量图层来源
     */
    function setVectorLayerSource(source) {
        vectorSource = source;
    }
    /**
     * 设置显示的样式
     *
     * @param   style   样式
     */
    function setStyle(style) {
        styles = style;
    }
    /**
     * 根据不同feature按照不同样式显示
     *
     * @param   feature   坐标数据
     */
    function styleFunction(feature) {
        // console.log(feature.getGeometry().getType());
        return styles[feature.getGeometry().getType()];
    }

    function cstyleFunction(feature) {
        //console.log(feature.getGeometry().getType());
        return styles[feature.getGeometry().getType()];
    }
    /**
     * 获得矢量图层
     * 
     * @param  title  根据title创建指定图层
     * 
     * @return  Object    矢量层对象
     */
    function getGeoLayer(title) {
        var GeoJsonLayer = new ol.layer.Vector({
            title: title,
            source: new ol.source.Vector({
                url: vectorSource,
                format: new ol.format.GeoJSON()
            }),
            style: styleFunction
        });
        return GeoJsonLayer;
    }

    function getKmlLayer(title) {
        var KMLLayer = new ol.layer.Vector({
            title: title,
            source: new ol.source.Vector({
                url: vectorSource,
                format: new ol.format.KML()
            }),
            style: styleFunction
        });
        return KMLLayer;
    }

    function createGeoLayer(source, title) {
        if (typeof (source) == 'object') {
            console.log("readFeatures");
            var vectorSource = new ol.source.Vector({
                features: (new ol.format.GeoJSON()).readFeatures(source)
            });
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: vectorSource,
                updateWhileInteracting: true,
                style: styleFunction
            });
        } else {
            console.log("sourceUrl");
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: new ol.source.Vector({
                    url: source,
                    format: new ol.format.GeoJSON()
                }),
                updateWhileInteracting: true,
                style: styleFunction
            });
        }
        return GeoJsonLayer;
    }

    function createKMLLayer(sourceUrl, title) {
        var GeoJsonLayer = new ol.layer.Vector({
            title: title,
            source: new ol.source.Vector({
                url: sourceUrl,
                format: new ol.format.KML({
                    extractStyles: false
                })
            }),
            // updateWhileInteracting: true,
            style: cstyleFunction
        });
        console.log("createKMLLayer");
        return GeoJsonLayer;
    }

    function createGridGeoLayer(source, title) {
        if (typeof (source) == 'object') {
            console.log("readFeatures");
            var vectorSource = new ol.source.Vector({
                features: (new ol.format.GeoJSON()).readFeatures(source)
            });
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: vectorSource,
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getFillStyle(feature);
                }
            });
        } else {
            console.log("sourceUrl");
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: new ol.source.Vector({
                    url: source,
                    format: new ol.format.GeoJSON()
                }),
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getFillStyle(feature);
                }
            });
        }
        return GeoJsonLayer;
    }

    function createPercentGeoLayer(source, title) {
        if (typeof (source) == 'object') {
            console.log("readFeatures");
            var vectorSource = new ol.source.Vector({
                features: (new ol.format.GeoJSON()).readFeatures(source)
            });
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: vectorSource,
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getNetFillStyle(feature);
                }
            });
        } else {
            console.log("sourceUrl");
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: new ol.source.Vector({
                    url: source,
                    format: new ol.format.GeoJSON()
                }),
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getNetFillStyle(feature);
                }
            });
        }
        return GeoJsonLayer;
    }

    function createDensityGeoLayer(source, title) {
        if (typeof (source) == 'object') {
            console.log("readFeatures");
            var vectorSource = new ol.source.Vector({
                features: (new ol.format.GeoJSON()).readFeatures(source)
            });
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: vectorSource,
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getDensityFillStyle(feature);
                }
            });
        } else {
            console.log("sourceUrl");
            var GeoJsonLayer = new ol.layer.Vector({
                title: title,
                source: new ol.source.Vector({
                    url: source,
                    format: new ol.format.GeoJSON()
                }),
                updateWhileInteracting: true,
                style: function (feature, resolution) {
                    return getDensityFillStyle(feature);
                }
            });
        }
        return GeoJsonLayer;
    }
    /**
     * 根据文件名称获取文件后缀名
     *
     * @param   filename  文件名
     *
     * @return  string   后缀名
     */
    function getFileExtension(filenaem) {
        var result = /\.[^\.]+/.exec(filenaem);
        return result;
    }
    /**
     * 获取地图信息
     *
     * @return    Object    map对象
     */
    function getMap() {
        if (arguments.length === 1) {
            setVectorLayerSource(arguments[0]);
        }
        if (getFileExtension(arguments[0]) == 'json') {
            var vectorLayer = getGeoLayer();
        } else if (getFileExtension(arguments[0]) == 'kml') {
            var vectorLayer = getKmlLayer();
        } else {
            var vectorLayer = new ol.layer.Vector({
                title: 'station Layer',
                source: new ol.source.Vector({
                    format: new ol.format.KML()
                }),
                style: styleFunction
            });
        }
        var map = new ol.Map({
            layers: [
                new ol.layer.Tile({
                    title: 'China',
                    source: new ol.source.OSM()
                }),
                vectorLayer
            ],
            target: 'map',
            controls: ol.control.defaults({
                attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
                    collapsible: false
                })
            }),
            view: new ol.View({
                projection: deproj,
                center: coordinates,
                zoom: 4,
                maxZoom: 16,
                minZoom: 4
            })
        });
        return map;
    }
    /**
     * 保留指定图层,移除其他图层
     * 
     * @param  map 地图对象
     * 
     * @param  tilte  指定图层的title
     */
    var removeLayerExceptOne = function (map, title) {
        var layers = map.getLayerGroup().getLayers()['a'];
        for (var i = 0; i < layers.length; i++) {
            console.log(layers[i]['U']['title']);
            if (layers[i]['U']['title'] != 'China' && layers[i]['U']['title'] != title) {
                console.log("remove" + layers[i]['U']['title']);
                map.removeLayer(layers[i]);
            }
        }
    }
    /**
     * 显示指定图层,隐藏其他图层
     * 
     * @param {object} map 
     * @param {string} title 
     */
    var hiddenLayerExceptOne = function (map, title) {
        var layers = map.getLayerGroup().getLayers()['a'];
        for (var i = 0; i < layers.length; i++) {
            console.log(layers[i]['U']['title']);
            if (layers[i]['U']['title'] != 'China' && layers[i]['U']['title'] != title) {
                console.log("hidden" + layers[i]['U']['title']);
                var layer = layers[i];
                layer.setVisible(false);
            } else {
                var layer = layers[i];
                layer.setVisible(true);
            }
        }
    }
    /**
     * 添加图层,并区分是否存在当前 title 值的图层
     * 
     * @param {object} layer 
     * @param {string} title 
     */
    var addMapLayer = function (layer, title) {
        if (!isLayerExist(title)) {
            layer.setVisible(false);
            map.addLayer(layer);
        }
        console.log('当前图层 title 已存在:' + title);
    }
    /**
     * 判断是否存在当前 title 的图层
     * @param {string} title 
     */
    var isLayerExist = function (title) {
        var layers = map.getLayerGroup().getLayers();
        var layers_len = layers.length;
        for (var i = 0; i < layers_len; i++) {
            if (layers['a'][i]['U']['title'] == title) {
                return true;
            }
        }
        return false;
    }

    var trim = function (str) {
        return str.replace(/(^\s*)|(\s*$)/g, "")
    }
    //返回的gisApi
    var gisAPI = {
        map: getMap,
        setProj: setDeproj,
        setCenter: setCoordinates,
        setSource: setVectorLayerSource,
        createGeoLayer: createGeoLayer,
        createKMLLayer: createKMLLayer,
        saveOnlyLayer: removeLayerExceptOne,
        createGridGeoLayer: createGridGeoLayer,
        createPercentGeoLayer: createPercentGeoLayer,
        createDensityGeoLayer: createDensityGeoLayer,
        viewLayer: hiddenLayerExceptOne,
        addMapLayer: addMapLayer
    };
    return gisAPI;
}