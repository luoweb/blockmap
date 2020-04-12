1. Deploy Geoserver
       
2. K8S Cluster Deploy
                     
              
3. Geoserver Deploy
                     
              
4. Public Service
```
luweb@k8s-terminal v2 (⎈ mycluster/bq7t552d0akmc6co6ai0:default)$ ibmcloud ks workers --cluster mycluster
OK
ID                                                     Public IP         Private IP      Flavor   State    Status   Zone    Version   
kube-bq7t552d0akmc6co6ai0-mycluster-default-000000cf   173.193.109.188   10.76.165.174   free     normal   Ready    hou02   1.16.8_1527 
```

5. Geoserver URL Visit:
http://173.193.109.188:30657/geoserver/web/


6. Upload Data:
kubectl exec -it geoserver /bin/bash
apt update;apt install ftp ssh unzip
scp ftp@*.*.*.*:/data/data.zip /opt/geoserver/data_dir/uploads/
scp geoserver-2.16.1-vectortiles-plugin.zip ubuntu@118.89.37.37:/home/ubuntu/
scp ubuntu@118.89.37.37:/home/ubuntu/ncov_china_data.zip /opt/geoserver/data_dir/uploads/
scp ubuntu@118.89.37.37:/home/ubuntu/geoserver-2.16.1-vectortiles-plugin.zip /opt/geoserver/data_dir/uploads/

7. Import data:
http://173.193.109.188:30657/geoserver/blockmap/wms?service=WMS&version=1.1.0&request=GetMap&layers=blockmap%3Aprovincepoint&bbox=87.57610581400151%2C19.97015545046543%2C126.56611953177881%2C45.6934683793407&width=768&height=506&srs=EPSG%3A4326&format=application/openlayers

http://173.193.109.188:30657/geoserver/blockmap/wms?service=WMS&version=1.1.0&request=GetMap&layers=blockmap%3Acounty&bbox=73.45169014816452%2C18.163529073724895%2C134.9754744649509%2C53.53138693255646&width=768&height=441&srs=EPSG%3A4326&format=application/openlayers


8. Plugins
Plugins Dir: <Geoserver安装目录>/webapps/geoserver/WEB-INF/lib
/usr/local/tomcat/webapps/geoserver/WEB-INF/lib


9. Access-Control-Allow-Origin.
Geoserver is not allowed by Access-Control-Allow-Origin.
ls -l /usr/local/tomcat/webapps/geoserver/WEB-INF/lib/jetty-servlets.jar
/usr/local/tomcat/webapps/geoserver/WEB-INF/web.xml

filter
```
<filter>
    <filter-name>cross-origin</filter-name>
    <filter-class>org.eclipse.jetty.servlets.CrossOriginFilter</filter-class>
    <init-param>
        <param-name>allowedOrigins</param-name>
        <param-value>*</param-value>
    </init-param>
    <init-param>
        <param-name>allowedMethods</param-name>
        <param-value>GET,POST</param-value>
    </init-param>
    <init-param>
        <param-name>allowedHeaders</param-name>
        <param-value>x-requested-with,content-type</param-value>
    </init-param>
</filter>
```

filter-mapping
```
<filter-mapping>
   <filter-name>cross-origin</filter-name>
   <url-pattern>/*</url-pattern>
</filter-mapping>
```