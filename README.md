## About The Project

To run this project locally you to clen install Gradle with following command
```sh
./gradlew clean install
```
To connect to MySQL database you need to have installed it on your machine https://www.mysql.com/downloads/

You can create your own username and password to MySQL DB you need to edit the following code
```sh
application.properties
```

```sh
spring.datasource.username=root
spring.datasource.password=root
```

And after your username and password are set you can launch your downloaded MySQL application

## Wallet service curl's
## Register
```sh
curl --location --request POST 'http://localhost:8080/api/wallet/account?email={email}&password={password}'
```

## Login 
```sh
curl --location --request POST 'http://localhost:8080/api/wallet/find-account?email={email}&password={password}'
```

## Balance
```sh
curl --location --request GET 'http://localhost:8080/api/wallet/balance/{accountId}'
```

## Withdraw
```sh
curl --location --request POST 'http://localhost:8080/api/wallet/withdraw/{accountId}?amount={amount}&reference={reference}'
```

## Deposit
```sh
curl --location --request POST 'http://localhost:8080/api/wallet/deposit/{accountId}?amount={amount}&reference={reference}'
```

## Operations
To get a full list of operations that were made with the following accountId
```sh
curl --location --request GET 'http://localhost:8080/api/wallet/operations/{accountId}'
```

## Bet service curl's
## Get all avaliable betslips
```sh
curl --location --request POST 'http://localhost:8081/api/bet/all'
```

## User active bets
```sh
curl --location --request POST 'http://localhost:8081/api/bet/active/{accountId}'
```

## Make a bet
NB! You can make only excisting bet from activeBetRepository
```sh
curl --location --request GET 'http://localhost:8081/api/bet/acceptBet?accountId={accountId}&betId={betId}&amount={amount}'
```

## Admin actions with active bets
*You can get active bets made by user, and make action WIN or LOSE. 

*In LOSE case bet will be removed from activeBetRepository and operation will be added in operationRepository with LOSE status

*In WIN case active bet will be remover from activeBetRepository, user will recieve bet amount * odd and operation will be saved in operationRepository with WIN status
```sh
curl --location --request GET 'http://localhost:8081/api/bet/betAction?accountId={accountId}&betId={betId}&action={WIN or LOSE}'
```











