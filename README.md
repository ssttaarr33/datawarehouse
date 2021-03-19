# Data Warehouse (ETL + query)
## _Springboot_

This is a springboot project that supports ETL operations and dynamic queries.

# Table of Contents
1. [Load](#Load)
2. [Extract](#extract)
3. [Transform](#transform)
4. [Query](#query)


## Load
- there is an api for loading data:
    ```sh
        <host>/api/data-api/load/{strategy}?url={url}
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
