
# PrologApp - Teste Técnico Java Pleno

Projeto desenvolvido para o teste técnico da PrologApp, utilizando principalmente **Java**, **Spring Boot** e testes.

### Principais Tecnologias

-   **Spring Boot 3.5**
    
-   **Java 21**

-   **Maven**: Gerenciar as dependências
    
-   **PostgreSQL + Flyway**: Migrations para versionamento, tipos ENUM nativos para refletir os Enums do Java.
    
-   **JPA/Hibernate**: Entidades, repositórios e relacionamentos mapeados.
    
-   **MapStruct**: Para mapear DTOs/Entidades e manter os controllers limpos.
    
-   **SpringDoc OpenAPI**: Documentação dos endpoints.
    
-   **JUnit 5 + Mockito**: Cobertura de testes unitários dos services, incluindo testes de exceção.

## Sobre o Projeto

A aplicação gerencia veículos e pneus, permitindo:
- Cadastro e consulta de veículos (com e sem pneus aplicados)
- Cadastro de pneus
- Vinculação e desvinculação de pneus em veículos
- Listagem paginada de veículos
- Cobertura de testes unitários
- API documentada e fácil de consumir via Swagger

---

## Como Rodar Localmente

### 1. **Pré-requisitos**
- [Docker](https://www.docker.com/)
- [Java 21+](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Maven 3.8+](https://maven.apache.org/)

### 2. **Subindo Banco de Dados (PostgreSQL)**

Utilize o `docker-compose.yml` já incluso no projeto:

1. docker-compose build
2. docker-compose up

    O serviço do banco estará disponível na porta 5432 com as credenciais já configuradas.
  
   Caso queira resetar o banco, basta derrubar o container do banco e subir novamente.
   
### 3. **Rodando a Aplicação**

Com o banco rodando, basta rodar o projeto localmente e a API estará disponível em [http://localhost:8080](http://localhost:8080)

## Documentação e Testes da API

Acesse a documentação do Swagger em:

-   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    

Todos os endpoints, exemplos de request/response, regras de negócio e mensagens de erro estão detalhados lá.


## Endpoints Principais

----------

| entidade | método | endpoint                | descrição                                    |
|----------|--------|-------------------------|----------------------------------------------|
| veiculo  | POST   | /vehicles               | Cadastrar novo veículo                       |
| veiculo  | GET    | /vehicles               | Listar veículos (paginado)                   |
| veiculo  | GET    | /vehicles/{id}          | Detalhar veículo e com seus pneus vinculados |
| veiculo  | GET    | /vehicles/plate/{plate} | Detalhar um veículo buscado pela placa       |
| veiculo  | PATCH  | /vehicles/{id}/status   | Altera o status de um veículo                |
| veiculo  | DELETE | /vehicles/{id}          | Remove um veículo                            |
| pneu     | POST   | /tires                  | Cadastrar novo pneu                          |
| pneu     | POST   | /tires/link             | Vincular pneu ao veículo                     |
| pneu     | POST   | /tires/unlink           | Desvincular pneu de um veículo               |
| pneu     | DELETE | /tires/{id}             | Remove um pneu que está desvinculado         |

## Decisões Técnicas & Considerações    

### Estrutura de Pacotes

-   `domain.entities`: Entidades de negócio (JPA)
    
-   `domain.enums`: Enums usados no projeto
    
-   `infra.controllers`: Controllers REST da API
    
-   `infra.dtos`: DTOs de entrada e saída (Request/Response)
    
-   `infra.services`: Serviços (camada de negócio)
    
-   `infra.repositories`: Repositórios JPA
    
-   `infra.mappers`: Mappers do MapStruct
    
-   `infra.exceptions`: Exceções customizadas e handler global    

### Regras de Negócio implementadas

-   **Não é possível cadastrar veículos com marca de pneu e vice-versa**
    
-   **Veículo não pode ter dois pneus na mesma posição**
    
-   **Só é possível vincular pneus disponíveis**
    
-   **Não é possível cadastrar pneus/veículos duplicados**
    
-   **Validações de limite de posições/pneus, Bean Validation, status, etc.**
    
-   **Todos os cenários de erro são tratados com respostas estruturadas via ApiError**

 -   **Enum no banco:** Usado tipos ENUM nativos no PostgreSQL (criando via Flyway) para garantir integridade, refletindo os mesmo enums do Java.
    
-   **MapStruct:** Para mapear DTOs <-> Entidades e evitar setters diretos.
    
-   **Validação:** Toda entrada é validada com Bean Validation e regras de negócio em service.
    
-   **Respostas de erro:** Mensagens de erro padronizadas em todos os endpoints (`ApiError`), facilitando consumo do front-end.
    
-   **Testes:** Services cobertos com unitários, simulando cenários de sucesso e falhas possíveis.
    
-   **Paginação:** Endpoint de listagem de veículos usa Spring Data Pageable (`page`, `size`, `sort`).
