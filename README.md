# EPSI CLOUD SOLUTION 


## COMMANDS 


Starting devserver 

```bash
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java
```

Deploying to gcloud 

```bash
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -v ~/.config/gcloud:/root/.config/gcloud -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java bash
```

Then use the following command. You only need to type this once. It will be stored in `config/gcloud`

```bash
gcloud auth login
#copy paste the url in your browser and then paste the token in your bash
gcloud config set project imt-2017-11
gcloud config set app/promote_by_default false
mvn -Dapp.deploy.version=$VERSION appengine:deploy
```

Deploying index

```bash
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -v ~/.config/gcloud:/root/.config/gcloud -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java mvn -Dapp.deploy.version=$VERSION appengine:deployIndex
```

Deploy queue

```bash
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -v ~/.config/gcloud:/root/.config/gcloud -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java mvn -Dapp.deploy.version=$VERSION appengine:deployQueue
```
