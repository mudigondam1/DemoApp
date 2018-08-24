DemoApplication

The main intension of this project is to handle transaction management
in a Spring Boot application.

Here I am assuming PNC bank with 5 users. To this bank, I am adding
new users, updating users, deleting users and retrieve usesrs.

Along with these actions, I am also trying to withdraw amount from
one user's account and depositing it in another user's account as requested.
During this process, to avoid inconsistency during transfers, I am using
Transaction Management.

Requiremnts:
> Eclipse with Spring Tool Suite
> H2 Database(In-memory Database)
> JPA