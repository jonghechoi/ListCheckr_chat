FROM mongo
LABEL authors="jonghechoi"

ENV MONGO_INITDB_ROOT_USERNAME=root
ENV MONGO_INITDB_ROOT_PASSWORD=1234
ENV MONGO_INITDB_DATABASE=chat

COPY init-mongo.js /docker-entrypoint-initdb.d/