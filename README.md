# orders

To run: mvn spring-boot:run

Check application-default.yml for settings

Creating an order
    
    curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{  
        "customerName":"Sample Data",
        "orderDate":"2016",
        "orderLines":[  
            {  
                "qty":1,
                "product":"Yo yo yo",
                "price":50
            },
             {  
                "qty":1,
                "product":"Eggplant",
                "price":50
            }
        ]}' "http://localhost:9000/api/v1/orders"


Query all the orders

    curl http://localhost:9000/api/v1/orders

Add a query

    curl http://localhost:9000/api/v1/orders?query=sample

Query all orders with pagination 

    curl http://localhost:9000/api/v1/orders?query=sample&start=0&count=20

 

