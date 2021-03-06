# Data Warehouse (ETL + query)
## _Springboot_

This is a springboot project that supports ETL operations and dynamic queries.

# Table of Contents
1. [Solution Design](#sdd)
2. [Load](#Load)
3. [Extract](#extract)
4. [Transform](#transform)
5. [Query](#query)
6. [Utilities](#utilities)
7. [High Level Design for Integration](#high-level-design-for-integration)

## Solution Design
![sdd](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/sdd.png)

The design follows the onion architecture principle, being modeled on layers of logic. This lets it open for adding further functionality without changing the current one.

## Load
- there is an api for loading data:
    ```sh
        GET: <host>/api/data-api/load/{strategy}?url={url}
    ```
    - there are 2 possible strategies for loading data;(_different styles can be added because it uses a strategy pattern for deciding based on @PathVariable_):
        ```sh
            byBulk
            byStep
        ```
        - byBulk - loads all data in memory and saves it to in-memory h2 database
        - byStep - saves data record by record to in-memory h2 database
    - you can also specify the _url_ from which to load the data; If it is not specified it will take the one provided from properties file    
        
        
## Extract
- extracting the data is done in strategy designated classes
- if different strategies will be added, _LoadDataInterface_ needs to be implemented
## Transform
- for now data is modeled as in the provided specifications
- _possible future improvement: use JSON structures for a NoSQL storage so any structure of data can be handled_
## Query
### Get all data
- there is a simple api for getting all the stored data
    ```sh
        GET: <host>/api/data-api/query
    ```
- this basically simulates a _select * from table_ , nothing fancy
### Get custom data
- the endpoint for getting the custom data
    ```sh
        POST: <host>/api/data-api/customQuery
    ```
- querying data is done through _@RequestBody_ **QueryRequestModel**; This has the following structure:
    ```sh
        QueryOperation action;
        AdditionalAggregation aggregation;
        FormulaType1 formula1;
        FormulaType2 formula2;
        List<Criteria> criteriaPair
    ```
    - **QueryOperation** provides 2 strategies for querying data (_more strategies can be further added_):
        ```sh
            COUNT
            FIND_ALL
        ```
    - **AdditionalAggregation** provides a way to aggregate data over an existing dataset; _Example: say you already filtered data based on campaign and date interval, and you want to get all the clicks for a datasource, the **AdditionalAggregation** will look like this:_
        ```sh
                ...
                "aggregation":{
                    "key":"dataSourceType",
                    "aggregatedValue":"clicks"
                }
                ...
                Response:
                {
                    "data": {
                        "Google Ads": 64806
                    }
                }
        ```  
    - **FormulaType** provides 2 strategies to evaluate dynamic mathematical formulas:
            **FormulaType1** lets you provide some sets of fields:
                - _List<String> multiplier_ : The data from all records will be added up and used as a **multiplier**
                - _List<String> divisor_ : The data from all records will be added up and used as a **divisor**
                - _constant_ : This will be multiplied with the **multiplier/divisor**
        ```sh
            ...
            "formula1":{
                "multiplier":["clicks"],
                "divisor":["impressions"],
                "constant":100
            },
            ...
            Response:
            {
                 "data": 0.2920056
            }
         ```
        **FormulaType2** lets you provide a mathematical expression and the _set_ of variables used by the expression:
        ```sh
            ...
            "formula2":{
                "expression":"totalClicks*100/totalImpressions",
                "variables":["totalClicks","totalImpressions"]
            },
            ...
            Response:
            {
                 "data": 0.2920056
            }
         ```
    - **criteriaPair** contains the list of **_Criteria_** on which the data will be filtered. The **Criteria** is structured as follows:
        ```sh
            String key
            Object value
            FilterOperation operation
        ```
        - **_FilterOperation_** supports the following _predicates_(other predicates can be added in the future):
        ```sh
            GREATER_THAN,
            LESS_THAN,
            GREATER_THAN_OR_EQUAL,
            LESS_THAN_OR_EQUAL,
            NOT_EQUAL,
            EQUAL,
            MATCH,
            MATCH_END
        ```
        - The list of criteria is used to build a predicate to filter the data. The equivalent of a query like _where dataSourceType like '%google%' and clicks > 0_ looks like this:
        ```sh
            	"criteriaPair":[
		            {
			            "key":"dataSourceType",
			            "value":"Google",
			            "operation": "MATCH"
		            },
		            {
			            "key":"clicks",
			            "value":"0",
			            "operation": "GREATER_THAN"
		            }
		        ]
		```        

## Utilities
### H2 DB browser console
In memory H2 DB can be administered from the bundled console which is available at this endpoint:
```sh
    <host>/api/h2/
```
_username: **sa** password: (empty)_
![console screenshot](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/console.PNG)

### Swagger UI
Swagger interface available at this endpoint:
```sh
    <host>/api/swagger-ui.html
```  
![swagger screenshot](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/swagger.PNG)

### Grafana
The application can be started from the included docker-compose file:
- in the root directory, run:
```sh
    docker-compose up -d
```
- This will run the following containers:
    - prometheus
    - grafana
    - dwhEtl(this application)
    
Grafana will be available on port 3000:  
![grafana screenshot](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/grafana.PNG)

Prometheus will be available on port 9090:
![prometheus screenshot](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/prometheus.PNG)

Data from _Actuator_ is exposed to **Prometheus** via **Micrometer**. **Prometheus** is added as datasource to **Grafana** so you can build all kinds of dashboards.

For now, only a JVM dashboard is added, but _as a future improvement, more dashboards can be added._

### Tests
- unit tests (_java_)
- integration tests (_java_)
- utility test (_groovy_)
- _more tests should be added_
- _contract testing with spring cloud contract can be added_

## High Level Design for Integration
This is a possible scenario for integration.
![hld](https://github.com/ssttaarr33/datawarehouse/blob/master/service/src/main/resources/hld.png)
- multiple instances of this service(**DWH ETL**) can live in a _Kubernetes_ cluster
- in a production scenario, H2 DB could be replace by an **Aerospike cluster** 
- a kafka broker can be added in order to trigger automatic data loading from the external _dataStorage_ or it can populate data directly to Aerospike(by using _byStep_ loading strategy)
