# Book API 📚

API REST para uma livraria independente, com integração à Open Library, cache em Redis, e persistência de livros visualizados recentemente.

---

## I. Arquitetura de Solução e Arquitetura Técnica

### 🧱 Arquitetura

A arquitetura adotada segue o modelo tradicional de **camadas**:
- **Controlle:** expõe os endpoints REST.
- **Service:** concentra as regras de negócio.
- **DTOs:** realizam o transporte dos dados.
- **Utils:** abstraem operações comuns (como RedisUtils).
- **Repository:** persistência dos dados via JPA.
- **Model/Entity:** estrutura de dados mapeada para banco.

Além disso, o projeto adota boas práticas:
- **Princípios SOLID**
- **Separação de responsabilidades**
- **Inversão de controle e injeção de dependências com Spring Boot**

### 🧰 Tecnologias utilizadas

| Camada | Tecnologia |
|--------|------------|
| Backend | Java 21 + Spring Boot |
| Cache | Redis |
| API Pública | OpenLibrary |
| Build Tool | Maven |
| Persistência | Spring Data JPA (Postgresql) |
| Testes | JUnit |
| Docker

---

## II. Explicação sobre o Case Desenvolvido (Plano de Implementação)

### 🎯 Objetivo

Desenvolver uma API para consulta de livros, seus autores e gêneros, utilizando a Open Library como base de dados. A aplicação implementa cache para melhorar a performance e armazena os últimos livros visualizados.

### 🔁 Fluxo Principal

1. O `BookController` recebe as requisições.
2. Ele chama o `BookServiceImpl`, que:
   - Consulta primeiro o **RedisUtils** para verificar se os dados estão em cache.
   - Se não estiverem, busca os dados na **API da Open Library**.
   - Armazena no cache com TTL de 5 minutos.
   - Persiste os livros visualizados em `BookRecentlyViewModel`.
   - Retorna um DTO padronizado.

### 📌 Endpoints

- `GET /books`: lista geral de livros
- `GET /books/{id}`: busca por ID
- `GET /books/gender/{gender}`: busca por gênero
- `GET /books/author/{author}`: busca por autor
- `GET /books/users/{userId}/recently-viewed`: últimos visualizados

---

## III. Melhorias e Considerações Finais

### ✅ Concluído
- Implementação da API com padrão de projeto limpo e modular.
- Integração com Open Library.
- Cache eficiente com Redis.
- Armazenamento dos livros visualizados recentemente.
- Conversão entre modelos e DTOs.
- Testes unitários com JUnit.
- Documentação Swagger/OpenAPI.

### 🚧 Melhorias Futuras
- Implementar autenticação (OAuth2 / JWT).

### ⚠️ Desafios Enfrentados
- Lidando com dados inconsistentes da Open Library (campos nulos ou ausentes).
- Entender funcionamento da OpenLibrary.
- Serialização correta dos dados com Redis.
- Garantir flexibilidade para consultas por múltiplos parâmetros.

---

## 📁 Repositório

[🔗 Acessar repositório no GitHub](https://github.com/yramonn/book-api)

