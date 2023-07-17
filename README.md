

# 1. jib build

```sh

# 빌드하고 레지스트리로 전송
$ mvn compile jib:build \
    -Djib.to.auth.username=ssongman \
    -Djib.to.auth.password=my-password


# 도커 데몬 빌드
$ mvn compile jib:dockerBuild


# pom 파일과 관계없이 컨테이너화 
$ mvn compile com.google.cloud.tools:jib-maven-plugin:1.0.0:build -Dimage=docker.io/ssongman/redis-sample


# war 로 패키징
$ mvn package jib:build

```




# 2. curl test



## 0) health check


```sh
curl -X GET http://localhost:8082/health

```




## 1) set


```sh
curl -X POST http://localhost:8082/person \
  -H "Content-Type: application/json" \
  -d '{  
  "id": "aaaa",
  "name": "song",
  "age": 20,
  "createdAt": "2022-07-03T01:03:00"
}'

curl -X POST http://localhost:8082/person \
  -H "Content-Type: application/json" \
  -d '{  
  "id": "bbbb",
  "name": "Park",
  "age": 20,
  "createdAt": "2022-07-03T01:03:00"
}'
```



## 2) get

```sh

curl localhost:8082/person/aaaa

curl localhost:8082/person/bbbb


```


## 3) delete

```sh
curl -X DELETE localhost:8082/person/aaaa

curl -X DELETE localhost:8082/person/bbbb


```




