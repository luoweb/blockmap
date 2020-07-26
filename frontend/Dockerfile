FROM nginx:1.17.9 AS baas

WORKDIR /app/


# blockmap-frontend 
COPY blockmap/dist /usr/share/nginx/blockmap/app
COPY blockMapFront/dist /usr/share/nginx/blockmap/admin


# baas-frontend 
EXPOSE 80

