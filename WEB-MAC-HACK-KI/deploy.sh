docker-compose down

#git pull

./gradlew clean build buildDocker --stacktrace -x test

cd nginx_docker
docker build -t real/nginx .

docker-compose -p realamigo down

docker-compose -p realamigo up -d

