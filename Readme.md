## To Run

- Run the following commands to build the app.

```shell
$ mvn clean package
$ java -jar ./target/rise-in-homes-0.0.1-SNAPSHOT.jar 
$ docker build -t rise-in-home-.0.0.1 -f ./dockerbuild/Dockerfile .
$ docker run -P -d --name rise-in-homesV0 rise-in-homes-0.0.1

```
> Then open `http://localhost:8080`

## Path

- [ ] - Research using Zillow Neighborhood coordinates (http://www.zillow.com/howto/api/neighborhood-boundaries.htm)
- [ ] How to use ShapeFiles ??  (http://ccarpenterg.github.io/blog/us-census-visualization-with-d3js/)
- [ ] Converting Shaprefile to GeoJSON (http://www.gdal.org/)
- [ ] ------ Online Converter (http://mapshaper.org/)

- [ ] ---- Choropleth by State (https://plot.ly/javascript/choropleth-maps/)

- [ ] -------- Some more Choropleth information
- [ ] ------------ This choropleth encodes unemployment rates from 2008 with a quantize scale ranging from 0 to 15%. (http://bl.ocks.org/mbostock/4060606)
- [ ] ------------ This choropleth displays unemployment rates by US county in 2012 (annual averages). (http://bl.ocks.org/badosa/5652333)
- [ ] ------------ Choropleth map of states in the US... venture capital spent in the US in 2012. (https://d3-geomap.github.io/map/choropleth/us-states/)
- [ ] ------------ Displays world population data from downloaded from the World Bank databank. (https://d3-geomap.github.io/map/choropleth/world/)

- [ ] ------------ More Choropleth Tutorials (http://leafletjs.com/examples/choropleth

- [ ] http://bl.ocks.org/michellechandra/0b2ce4923dc9b5809922

- [ ] http-server -p 8008 &

### Docker

- [X] https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i  <-- Docker Tutorials

- [ ] Heroku and DOCKER -- https://devcenter.heroku.com/articles/local-development-with-docker-compose

- [ ] May need to research **Creating a Docker Group** and **Configure Docker to start on boot** "https://docs.docker.com/engine/installation/linux/ubuntulinux/"

- [X] Learn by examples -- https://docs.docker.com/engine/tutorials/usingdocker/

- [ ] Mount a host directory as a data volume

- [X] Postgres -- https://hub.docker.com/_/postgres/

- [X] use " mvn clean package "

- [ ] turn spring boot into container -- http://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-iii

- [X] *SOLVED* Dockerfile created but receive java error on load "org.postgresql.util.PSQLException: Connection to localhost:32768 refused."

### JHipster

- [ ] ~~https://blog.heroku.com/bootstrapping_your_microservices_architecture_with_jhipster_and_spring~~
