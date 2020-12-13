# Securing Microservices with Spring security and OAuth2

__BETA TESTING:__

## Implement an OAuth2 server to manage user and client credentials

### 1.1: *Configure the token generation* feature branch - obj-1.1

The current state of the application represents aa authorization server implemented using spring cloud oauth2. 
This authorization server uses asymmetric keys to sign and issue JWT tokens. For the sake of objective 1.1 the client and user details are managed by in-memory implementations
of **ClientDetailsService** and **UserDetailsService**. 

Following the guidelines for the objective 1.1, test cases has been written to validate the token generation for different oauth2 flows.

To validate if the JWT tokens generated by this auth server are properly signed with private key, test case has been written to verify Signature of the generated token 
using the public key from the key pair. Due to this reason the Java-JWT dependency from com.auth0 is added in the test scope.

### 1.2: *Configure the Persistence layer* feature branch - obj-1.2

In this feature branch, JPA capabilities have been added to the authorization server to save users and clients in the DB. As per the guidelines the endpoints involved in adding and fetching Users and Clients have been exposed to all users. The general idea of class design is that User has one or many Authorities. Client has one to many relationship with Scope, GrantedAuthority and RedirectUrl(*I think its always better to have custom Types than having them as simple Strings*).

### 1.2: *Authenticate Using client and User details from the Database* feature branch - obj-1.3

In this feature branch and the sub feature branches(obj-1.3-user & and obj-1.3.client), the respective codes are written to fetch the client id and secret from the Database using JPA entities. There is a rich domain model for Clients domain and users domain.
