# fakebank-backend

- Java 11
- Spring boot
- Criação de uma nova conta bancária para um cliente
- Pode ter um valor de depósito inicial.
- Um único cliente pode ter várias contas bancárias.
- Transferir valores entre duas contas quaisquer, incluindo aquelas
pertencentes a clientes diferentes.
- Recupere os saldos de uma determinada conta.
- Recupere o histórico de movimentações de uma determinada conta.
- APIs autenticadas
- Testes unitários
- Documentação das APIs

## Build/Run
Let’s build and run the program. Open a command line (or terminal) and navigate to the folder where you have the project files. We can build and run the application by issuing the following command:

### MacOS/Linux:
```
chmod +x mvnw
./mvnw spring-boot:run
```

### Windows:
```
mvnw spring-boot:run
```

## Docker
See Dockerfile