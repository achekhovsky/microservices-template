FROM mongo:latest

LABEL maintainer="AlexCh <AlexCh_work@gmail.com>"

COPY create_adm.js /docker-entrypoint-initdb.d/create_adm.js 

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["mongod"]
