Quick explanations of application: 
- Application contains 3 entities (each one of them has own Controller, Service, Repository)
- Application architecture: User may create cards under own name, then initiate transfers between cards (own or other user's)
- To initiate transactions we need `senderCardId` and `recipientCardId`
- Additionally added simple exception handling and modelMapper configuraions and scheduled task to process transactions to acquirer
- All APIs has DTO for response which includes only needed/allowed fields
- Application has few dependencies:
  - lombok
  - jakarta.validation-api
  - modelmapper
  - unirest-java

- User Controller APIs:
  - GET /user/{userId} - get single User by ID
  - GET /user/all - get all users
  - POST /user - create new user based with RequestBody
  - PUT /user/{userId} - update user details by ID with RequestBody
  - PUT /user/{userId}/status - update user status (ACTIVE / INACTIVE)
  - DELETE /user/{userId} - delete user (soft delete)

- Card Controller APIs:
  - GET /card/{cardId} - get singe card by ID
  - GET /card/all - get all cards
  - POST /card - create new card with RequestBody
  - PUT /card/{cardId} - update card details by ID with RequestBody
  - DELETE /card/{cardId} - delete card (soft delete)

- Transaction Controller APIs:
  - GET /transaction/all - get all transactions
  - GET /transaction/{transactionId} - get single transaction
  - POST /transaction - initiate transaction with RequestBody


RUN:
- Application uses JAVA 1.8 and Spring Framework 3.2.5
- Application does not require any configurations or setting to run
- Could be used run command `$ mvn spring-boot:run.` or by IDEA 
