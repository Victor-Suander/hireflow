# Estrutura do Projeto HireFlow

## 📁 Organização de Pacotes

```
com.victor.hireFlow/
├── controller/          # Endpoints REST
│   ├── HomeController.java
│   └── UserController.java
├── service/             # Lógica de negócio
│   ├── UserService.java (interface)
│   └── impl/
│       └── UserServiceImpl.java (implementação)
├── repository/          # Acesso a dados
│   └── UserRepository.java
├── entity/              # Modelos JPA
│   └── User.java
└── HireFlowApplication.java
```

## 📋 Descrição das Camadas

### 1. **Entity** (Modelo de Dados)
- Localização: `com.victor.hireFlow.entity`
- Arquivos: `User.java`
- Responsabilidade: Mapear tabelas do banco de dados em objetos Java
- Anotações: `@Entity`, `@Table`, `@Column`, `@Id`, etc.

### 2. **Repository** (Acesso a Dados)
- Localização: `com.victor.hireFlow.repository`
- Arquivos: `UserRepository.java`
- Responsabilidade: Interface para operações CRUD no banco de dados
- Herança: `JpaRepository<Entity, ID>`
- Métodos customizados: `findByEmail()`, `existsByEmail()`

### 3. **Service** (Lógica de Negócio)
- Interface: `com.victor.hireFlow.service.UserService`
- Implementação: `com.victor.hireFlow.service.impl.UserServiceImpl`
- Responsabilidade: 
  - Processar lógica de negócio
  - Validações complexas
  - Orquestrar chamadas ao repository
  - Não acessa banco diretamente

### 4. **Controller** (Endpoints REST)
- Localização: `com.victor.hireFlow.controller`
- Arquivos: `UserController.java`
- Responsabilidade:
  - Mapear requisições HTTP
  - Validar dados de entrada
  - Chamar serviços
  - Retornar respostas HTTP

## 🔄 Fluxo de Requisição

```
HTTP Request
    ↓
Controller (recebe @RequestBody, valida)
    ↓
Service (executa lógica de negócio)
    ↓
Repository (acessa banco de dados)
    ↓
Entity (retorna do banco)
    ↓
HTTP Response (JSON)
```

## 📚 Endpoints Disponíveis

### User Controller (`/api/users`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/users` | Listar todos os usuários |
| GET | `/api/users/{id}` | Buscar usuário por ID |
| GET | `/api/users/email/{email}` | Buscar usuário por email |
| POST | `/api/users` | Criar novo usuário |
| PUT | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Deletar usuário |

## 🛠️ Exemplo de Uso

### Criar Usuário
```bash
POST http://localhost:8080/api/users
Content-Type: application/json

{
    "email": "usuario@example.com",
    "name": "João Silva",
    "password": "senha123"
}
```

### Listar Usuários
```bash
GET http://localhost:8080/api/users
```

### Buscar por ID
```bash
GET http://localhost:8080/api/users/1
```

## 🔧 Dependências Necessárias

Adicione ao seu `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Database H2 (desenvolvimento) -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

## ✅ Próximos Passos

1. Configurar banco de dados em `application.properties`
2. Criar mais entidades conforme necessário (Job, Application, etc.)
3. Implementar DTOs (Data Transfer Objects) para validação
4. Adicionar tratamento de exceções customizado
5. Implementar autenticação e autorização
6. Adicionar testes unitários e de integração

