# Welcome to our coding test!

Your solution to this coding test will be evaluated based on its:
 * Adherence to best coding practices
   * Add unit tests that cover the basic features of TransactionDataFetcher. One test per method would be enough
 * Correctness
 * Efficiency

Take your time to fully understand the problem and formulate a plan before starting to code, and don't hesitate to ask any questions if you have doubts.

# Objective

Since we are a money transfer company this test will revolve around a (very) simplified transaction model. Our aim is to implement the methods listed in `com.smallworld.TransactionDataFetcher`, a component that allows us to get some insight into the transactions our system has.

A battery of test transactions is stored in `transactions.json` that is going to be used as a datasource for all our data mapping needs.

Each entry in `transactions.json` consists of:
 * mtn: unique identifier of the transaction
 * amount
 * senderFullName, senderAge: sender information
 * beneficiaryFullName, beneficiaryAge: beneficiary information
 * issueId, issueSolved, issueMessage: issue information. Transactions can:
   * Contain no issues: in this case, issueId = null.
   * Contain a list of issues: in this case, the transaction information will be repeated in different entries in `transactions.json` changing the issue related information.

You can think of the json as the output of an SQL query joining transaction with transaction_issue, so the following example would represent a **unique transaction** with **2 issues**:
```
{
    "mtn": 1284564,
    "amount": 150.2,
    "senderFullName": "Tom Shelby",
    "senderAge": 22,
    "beneficiaryFullName": "Arthur Shelby",
    "beneficiaryAge": 60,
    "issueId": 2,
    "issueSolved": true,
    "issueMessage": "Never gonna give you up"
  },
  {
    "mtn": 1284564,
    "amount": 150.2,
    "senderFullName": "Tom Shelby",
    "senderAge": 22,
    "beneficiaryFullName": "Arthur Shelby",
    "beneficiaryAge": 60,
    "issueId": 3,
    "issueSolved": false,
    "issueMessage": "Looks like money laundering"
  }
```

Each method to be implemented includes a brief description of what's expected of it.

The parameters and return types of each method can be modified to fit the model that contains the transaction information

Have fun!