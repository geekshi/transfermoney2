# Transfer money RESTful API V2
A Java RESTful API V2 for retrieve balance and make a transfer between 2 accounts. Rather than first version, this version uses database for data access

## Prepare database
Modify /src/main/resources/application.properties to change MySQL login credentials. Execute /src/main/resources/init.sql for database initialization in MySQL

## How to run
- `mvn install`
- `java -jar target/transfermoney-0.0.1-SNAPSHOT.jar`

## Unit test
Run TransferApplicationTests

## Available Services
1. Use GET method
`http://localhost:8080/accounts`
for get all accounts
>### response:
```sh
{
  "totalAccounts": 3,
  "accounts": [
    {
      "id": "1",
      "name": "USD Account",
      "herf": "/accounts/1"
    },
    {
      "id": "2",
      "name": "HKD Account",
      "herf": "/accounts/2"
    },
    {
      "id": "3",
      "name": "HKD Account",
      "herf": "/accounts/3"
    }
}
```

2. Use GET method
`http://localhost:8080/accounts/{id}`
for retrieve accounts/{id} balance
>### response:
```sh
{
  "id": "1",
  "accountName": "USD Account",
  "balance": 1000,
  "currencyCode": "USD"
}
```

3. Use POST method
`http://localhost:8080/transaction`
for make a transfer between 2 accounts
>### request:
```sh
{
  "currencyCode": "HKD",
  "amount": 100,
  "fromAccountId": "2",
  "toAccountId": "3"
}
```

>### response:
```sh
{
  "code": 100,
  "message": "Success",
  "content": [
    {
      "balance": 900,
      "currencyCode": "HKD"
      "accountName": "HKD Account",
      "id": "2"
    },
    {
      "balance": 1100,
      "currencyCode": "HKD"
      "accountName": "HKD Account",
      "id": "3"
    }
  ]
}
```

## API document
http://localhost:8080/swagger-ui.html

## Link of API specifications in GitHub
/src/main/resources/api-docs.json

## 3rd party library used
com.alibaba.fastjson-1.2.56
