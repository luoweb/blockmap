/**
 * 获取天地图wmts服务图层实例
 * 
 * @param {String} t 
 * 1.'vec_c' 全球矢量地图服务
 * 2.'img_w' 全球影像地图服务
 * 3.'cva_c' 全球矢量中文注记服务
 * 4.'img_c' 全球影像底图服务
 * 5.'cia_c' 全球影像中文注记服务
 * 6.更多服务可查询http://www.tianditu.com/service/query.html#
 * @returns 
 */
function getTdtLayer(t = 'vec_c') {
    let url = 'http://t0.tianditu.com/DataServer?T=' + t + '&X={x}&Y={y}&L={z}'
    let projection = ol.proj.get('EPSG:4326')
    let projectionExtent = [-180, -90, 180, 90]
    let maxResolution = (ol.extent.getWidth(projectionExtent) / (256 * 2))
    let resolutions = new Array(16)
    for (let z = 0; z < 16; ++z) {
      resolutions[z] = maxResolution / Math.pow(2, z)
    }
    var tileOrigin = ol.extent.getTopLeft(projectionExtent)
    var layer = new ol.layer.Tile({
      extent: [-180, -90, 180, 90],
      source: new ol.source.TileImage({
        tileUrlFunction: function (tileCoord) {
          var z = tileCoord[0] + 1
          var x = tileCoord[1]
          var y = -tileCoord[2] - 1
          var n = Math.pow(2, z + 1)
          x = x % n
          if (x * n < 0) {
            x = x + n
          }
          return url.replace('{z}', z.toString())
            .replace('{y}', y.toString())
            .replace('{x}', x.toString())
        },
        projection: projection,
        tileGrid: new ol.tilegrid.TileGrid({
          origin: tileOrigin,
          resolutions: resolutions,
          tileSize: 256
        })
      })
    })
    return layer
  }
  
  module.exports.getTdtLayer = getTdtLayer
  