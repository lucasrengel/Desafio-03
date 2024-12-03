### **SP_SpringBoot_AWS_Desafio_03**

---

Este projeto é composto por três microserviços desenvolvidos em **Java Spring Boot** para gerenciar as operações de **Pedidos**, **Estoque** e **Clientes**. Cada microserviço possui uma arquitetura desacoplada e utiliza **PostgreSQL** como banco de dados, **Docker** para execução em contêineres e comunicação via **REST APIs**.

---

### **Arquitetura do Projeto**

- **Pedido Service**  
  Gerencia pedidos, incluindo criação, atualização e consulta de pedidos.  
  Comunicação com o serviço de Estoque para verificar e reservar produtos.

- **Estoque Service**  
  Gerencia o inventário, incluindo adição, atualização e consulta da disponibilidade de produtos.

- **Cliente Service**  
  Gerencia o cadastro de clientes e fornece o histórico de pedidos, recuperando informações do serviço de Pedido.

Cada serviço está configurado para executar de forma independente, permitindo escalabilidade e manutenção simplificada.

---

### **Tecnologias Utilizadas**
- **Linguagem:** Java 21+
- **Framework:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Containerização:** Docker
- **Orquestração:** Docker Compose
- **Comunicação entre Serviços:** REST (com OpenFeign ou RestTemplate)
- **Monitoramento:** Métricas básicas configuradas no Docker (opcional)
- **Aws:** Implementado com instancias e EC2
---

### **Requisitos para Execução**
- **Docker** e **Docker Compose** instalados na máquina.
- Ambiente configurado para executar Java (opcional para desenvolvimento local sem contêineres).

---

### **Estrutura do Projeto**
Cada microserviço está em um diretório separado, contendo:
1. **`pedido/`**: Código e configuração do serviço de pedidos.
2. **`estoque/`**: Código e configuração do serviço de estoque.
3. **`cliente/`**: Código e configuração do serviço de clientes.

---

### **Como Executar**

#### 1. **Configuração com Docker Compose**  
Abaixo, temos o exemplo do Docker Compose para cada microserviço.

- **Pedido** (`pedido/docker-compose.yml`):
    ```yaml
  version: '3.8'
  services:
    postgres-pedido:
      image: postgres:15-alpine
      container_name: postgres-pedido
      environment:
        POSTGRES_USER: postgres
        POSTGRES_PASSWORD: 123456
        POSTGRES_DB: pedido_db
      ports:
        - "5432:5432"
      volumes:
        - postgres-pedido-data:/var/lib/postgresql/data
  
    pedido-service:
      build:
        context: ./
      container_name: pedido-service
      ports:
        - "8080:8080"
      environment:
        SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-pedido:5432/pedido_db
        SPRING_DATASOURCE_USERNAME: postgres
        SPRING_DATASOURCE_PASSWORD: 123456
      depends_on:
        - postgres-pedido
  
  volumes:
    postgres-pedido-data:

    ```

- **Estoque** (`estoque/docker-compose.yml`):
    ```yaml
  version: '3.8'
  services:
    postgres-estoque:
      image: postgres:15-alpine
      container_name: postgres-estoque
      environment:
        POSTGRES_USER: postgres
        POSTGRES_PASSWORD: 123456
        POSTGRES_DB: estoque_db
      ports:
        - "5433:5432"
      volumes:
        - postgres-estoque-data:/var/lib/postgresql/data
  
    estoque-service:
      build:
        context: ./
      container_name: estoque-service
      ports:
        - "8081:8081"
      environment:
        SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-estoque:5432/estoque_db
        SPRING_DATASOURCE_USERNAME: postgres
        SPRING_DATASOURCE_PASSWORD: 123456
      depends_on:
        - postgres-estoque
  
  volumes:
    postgres-estoque-data:

    ```

- **Cliente** (`cliente/docker-compose.yml`):
    ```yaml
  version: '3.8'
  services:
    postgres-cliente:
      image: postgres:15-alpine
      container_name: postgres-cliente
      environment:
        POSTGRES_USER: postgres
        POSTGRES_PASSWORD: 123456
        POSTGRES_DB: cliente_db
      ports:
        - "5434:5432"
      volumes:
        - postgres-cliente-data:/var/lib/postgresql/data
  
    cliente-service:
      build:
        context: ./
      container_name: cliente-service
      ports:
        - "8082:8082"
      environment:
        SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-cliente:5432/cliente_db
        SPRING_DATASOURCE_USERNAME: postgres
        SPRING_DATASOURCE_PASSWORD: 123456
      depends_on:
        - postgres-cliente
  
  volumes:
    postgres-cliente-data:
    ```

#### 2. **Subir os Microserviços**
- Entre no diretório principal e execute:
  ```
  docker-compose up --build
  ```
Certifique-se que subiu corretamente verificando os logs do Docker ou acessando os endpoints correspondentes.

#### 3. **Acessar os Endpoints**

Após subir os serviços, os endpoints estarão disponíveis nas seguintes portas padrão:
- Pedido: http://localhost:8080
- Estoque: http://localhost:8081
- Cliente: http://localhost:8082

#### 4. **Testar as Funcionalidades**
Utilize ferramentas como Postman para testar os endpoints REST de cada microserviço.
Certifique-se de que as requisições entre os microserviços estão funcionando conforme esperado.

#### 5. **Monitorar os Contêineres**
Para verificar os contêineres em execução, use o comando:
```bash
docker ps
```
Para parar todos os contêineres, utilize:
```
docker-compose down
```
