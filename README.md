# Protools POC avec Flowable
POC Protools pour un cas d'utilisation simple dans le cadre d'un cadre de master 2.
![](https://github.com/Stage2022/protools-activiti/blob/main/images/bpmn.png?raw=true)
Currently testing advanced functionalities for l'enquête famille
### Installation
[Link to swagger](https://protools-flowable.dev.insee.io/)

##### Installation via Docker
```bash
docker pull mailinenguyen/protools-flowable
docker run -d --name protoolsflowable -p 8080:8080  mailinenguyen/protools-flowable:latest
```
##### Manual Install
``` bash
git clone git@github.com:Stage2022/Protools-Flowable.git
cd Protools-Flowable
./mvnw spring-boot:run
```
