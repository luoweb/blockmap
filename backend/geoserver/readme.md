# Deploy Geoserver

1. K8S Cluster Deploy


2. Geoserver Deploy


3. Public Service
```
luweb@k8s-terminal v2 (⎈ mycluster/bq7t552d0akmc6co6ai0:default)$ ibmcloud ks workers --cluster mycluster
OK
ID                                                     Public IP         Private IP      Flavor   State    Status   Zone    Version   
kube-bq7t552d0akmc6co6ai0-mycluster-default-000000cf   173.193.109.188   10.76.165.174   free     normal   Ready    hou02   1.16.8_1527 
```

4、Geoserver URL Visit:
http://173.193.109.188:30657/geoserver/web/