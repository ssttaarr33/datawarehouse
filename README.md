# Data Warehouse (ETL + query)
## _Springboot_

This is a springboot project that supports ETL operations and dynamic queries.

# Table of Contents
1. [Load](#Load)
2. [Extract](#extract)
3. [Third Example](#third-example)
4. [Fourth Example](#fourth-examplehttpwwwfourthexamplecom)


## Load
- there is an api for loading data:
    ```sh
        <host>/api/data-api/load/{strategy}?url={url}
    ```
    - there are 2 possible strategies for loading data:
        ```sh
            byBulk
            byStep
        ```
        - byBulk - loads all data in memory and saves it to in-memory h2 database
        - byStep - saves data record by record to in-memory h2 database