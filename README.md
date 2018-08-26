#  How to verify and run your solution.

## Compilation
Project is build in maven.
When project is built only via maven everything should compile at first time.

In Eclipse/Intelij if the code is freshly imported for 1s time it might be failinig on missing classes - due to Immmutables library usage.
It's enough to rebuild (e.g. mvn clean compile) and refresh.

## How to build and run the tests.

To build and verify with all the tests - unit and integration tests:
```
mvn clean verify
```

which is inside script :
```
mvn_verify.sh
```

## How to run the app and test it manually
Usually I prepare the app to be run inside docker (deployed and run on AWS).
Here we can just run it locally:
```
mvn spring-boot:run
```

which is inside script :
```
run_locally.sh
```



To get line 200 @ stop 3 (x:2,y:9) @ 10:10:00 (because it's delayed by 2 minutes)
```
curl -G  "http://localhost:8081/lines" --data-urlencode "timestamp=10:10:00" --data-urlencode "x=2" --data-urlencode "y=9"
```
or in other format:
```
curl "http://localhost:8081/lines?timestamp=10%3A10%3A00&x=2&y=9"
```

The response in JSON is collection of lines (id + name):
```
[{"id":1,"name":"200"}]
```


To test the delays info endpoint:
```
curl http://localhost:8081/lines/M4
```

The response in JSON:
```
{"isDelayed":true}
```

# Core components
* class Application - to boot the spring boot based application
* class LineService - core class with "business logic" to join and filter the data from different repositories
* package "domain" - models for data done using Immutables library for convenience, it's a weak domain, logic is simply in the service, so no DDD approach
* package "repository" - classes to manage access to data + mappings from CSV format (done directly without sophisticated csv-library)
* package "resource" - to expose the service under REST API

# Assumptions

As "web API" I assumed "Rest API".

As "Returns the vehicles for ..." I assumed "Returns the Lines for for...".

All the identifiers assumed to be primitive integer.   
Of course for real data it's quite bad assumption, but OK for this limited task and provided dataset.


## Assumptions about input data
Since I can assume limited size of data - I used just POJOs to represent them in memory.


In regular case when big data is involved (and we can't read/cache the whole data in memory)
the model for data record could be different or the calculation could
even be done using some default abstraction (Spark DataFrame in Spark SQL)

Currently the data files are just bundled inside the jar.

"delays.csv" - was assumed that "delay" is given in **"minutes"**. Otherwise conversion or different model is required.

# Regarding Tests
I didn't cover all the things I usually cover (metrics, exception handling, combinations, consistency of data in CSV etc.)
just to make it within limited time.
But I tried to provide some example of tests on unit and integration level (to make sure REST API is properly returning responses).
Usually all these things should be taken into consideration for proper testing.

# API
Regarding API documentation - I would update swagger with example json output and returned HTTP error codes,
however I wanted to stick to this 4h time limit and just didn't managed.

# Regarding Documentation
Usually I do class level documentation + README.md + swagger documentation for the API.
In README.md: metrics, how to maintain, how to deploy.
Additionally I provide diagrams (Enterprise Architect or in draw.io) about internal architecture and how service interacts with rest of system/microservices.

