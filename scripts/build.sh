#!/bin/bash
#set -x
ls -l 

# build project 
cd ../frontend/blockmap
npm install
npm run-script build
cd dist;zip -r ../blockmap.zip *
cd ..

# check build result
ls -l;pwd


# copy the build resutl to nginx
kubectl get pods | awk '/nginx-static/{print $1}' | xargs -I@ kubectl cp blockmap.zip @:/usr/share/nginx/html/blockmap.zip

# install unzip 
kubectl get pods | awk '/nginx-static/{print $1}' | xargs -I@ kubectl exec -it @ -- bash -c "apt update;apt install unzip"

# unzip the build result in containser nginx-static
kubectl get pods | awk '/nginx-static/{print $1}' | xargs -I@ kubectl exec -it @ -- bash -c "cd /usr/share/nginx/html/;unzip -o blockmap.zip;nginx -s reload"

# clean Evicted pods
kubectl get pods | awk '/Evicted/{print $1}' | xargs delete pods  