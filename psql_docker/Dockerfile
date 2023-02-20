FROM postgres:latest

LABEL maintainer="AlexCh <AlexCh_work@gmail.com>"

#USER ${UID}:${GID}
ENV POSTGRES_USER Alex
ENV POSTGRES_PASSWORD postgres

COPY init-microframe-db.sh /docker-entrypoint-initdb.d/1-db.sh
COPY init-microframe-tbl.sql /docker-entrypoint-initdb.d/2-tbl.sql
COPY init-microframe-tbl-data.sql /docker-entrypoint-initdb.d/3-data.sql
RUN echo "host    all    all    0.0.0.0/0    md5" >> /var/lib/postgresql/data/pg_hba.conf

