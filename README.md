# Money transfer Rest API :moneybag:

A banking API allows you to transfer money between two accounts with following rules in below.

### Rules:

● “Account Number” should be integer and unique.

● “Currency Code” is a string value and can only contain “TRY”, “USD”, “EUR”.

● “Balance” is decimal and precision should be limited to 2.

● If the sender account has no available fund to finish the transaction, there should be
insufficient balance error.

● All responses should have an unique reference number whether the transaction is
successful or not.

● To allow money transactions, both sender and receiver accounts should have the same
currency code.

### Requiremets 🔧

- Java version 8 or higher.
- Maven.

## Installation 🔌

1. Press the Fork button (top right the page) to save copy of this project on your account.
2. Download the repository files (project) from the download section or clone this project by typing in the bash the following command:

```
git clone https://github.com/esra888/DemoApplication.git
```

1. Imported it in Intellij IDEA or any other Java IDE.
2. Run the application :D

### Available Services

| HTTP METHOD | PATH            | USAGE                        |
| ----------- | --------------- | ---------------------------- |
| GET         | /account/all    | get all accounts             |
| GET         | /accounting/all | get all accountings          |
| POST        | /account/create | create new account           |
| POST        | /accounting     | create new money transaction |

### Http Status

- 200 OK: The request has succeeded
- 400 Bad Request: The request could not be understood by the server
- 404 Not Found: The requested resource cannot be found
- 500 Internal Server Error: The server encountered an unexpected condition

### Sample JSON for Account and Accounting

Account :

```json
 {
   "currencyCode" :"TRY",
   "balance": 5000
 }
```

Accounting :

```json
{
  "sendingAccountId" : 1,
  "receivingAccountId" :2,
  "transactionAmount" : 1000
}
```

