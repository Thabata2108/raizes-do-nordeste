# Raízes do Nordeste

Projeto Back-end desenvolvido para a disciplina de Projeto Multidisciplinar do curso de Análise e Desenvolvimento de Sistemas.

O projeto apresenta uma API REST para a rede fictícia Raízes do Nordeste, permitindo o gerenciamento de usuários, unidades, produtos, pedidos, estoque, pagamentos e programa de fidelidade.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL 8
- Swagger / OpenAPI
- Postman
- Git e GitHub

## Principais funcionalidades

- Cadastro e autenticação de usuários
- Controle de acesso por perfil: CLIENTE, FUNCIONARIO e ADMINISTRADOR
- Consulta de unidades e produtos disponíveis
- Realização e acompanhamento de pedidos
- Pedidos pelos canais APP, TOTEM, BALCAO, PICKUP e WEB
- Filtro de pedidos por canal
- Controle de estoque por unidade
- Processamento simulado de pagamentos aprovados e negados
- Programa de fidelidade com pontos e descontos
- Consulta de relatórios administrativos
- Tratamento padronizado de erros HTTP

## Estrutura do projeto

A aplicação foi organizada em camadas:

Controller → Service → Repository → Banco de Dados

A API utiliza MySQL para persistência dos dados e JWT para autenticação e controle de acesso.

## Requisitos para execução

Para executar o projeto localmente é necessário ter instalado:

- Java 21
- MySQL 8
- Git

O projeto utiliza Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

## Banco de dados

Crie no MySQL um banco de dados chamado:

```sql
CREATE DATABASE raizes_do_nordeste;
```

A aplicação utiliza Spring Data JPA e Hibernate para criação e atualização das tabelas.

Não foram utilizadas migrations ou seed automático. Os dados de teste podem ser cadastrados pela própria API.

## Variáveis de ambiente

Antes de iniciar a aplicação, configure as seguintes variáveis de ambiente:

```text
DB_PASSWORD=sua_senha_do_mysql
JWT_SECRET=sua_chave_secreta_jwt
```

O arquivo `.env.example` presente no projeto apresenta as variáveis necessárias sem armazenar dados secretos.

O `.env.example` é apenas uma referência. As variáveis devem ser configuradas no sistema operacional antes da execução da aplicação.

## Executando a aplicação

No terminal, na pasta raiz do projeto, execute:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

A API será iniciada em:

```text
http://localhost:8080
```

## Documentação da API

Com a aplicação em execução, a documentação dos endpoints pode ser consultada pelo Swagger em:

```text
http://localhost:8080/swagger-ui/index.html
```

## Testes com Postman

A coleção utilizada nos testes está disponível em:

```text
postman/Raizes-do-Nordeste.postman_collection.json
```

Para utilizar:

1. Importe o arquivo no Postman.
2. Execute o login de cliente ou administrador.
3. Copie o token JWT retornado.
4. Configure as variáveis `tokenCliente` e `tokenAdmin` da coleção.
5. Execute as requisições desejadas.

A coleção contém testes do fluxo principal e cenários de erro, incluindo:

- autenticação;
- criação de pedido;
- filtro por canal do pedido;
- pagamento aprovado;
- pagamento negado;
- acesso sem token;
- acesso com perfil sem permissão;
- quantidade inválida;
- produto inexistente;
- unidade inexistente;
- estoque insuficiente.

## Pagamento externo

O serviço externo de pagamento foi simulado para fins acadêmicos.

O mock permite testar os resultados `APROVADO` e `NEGADO`, possibilitando verificar o comportamento da API nos dois cenários.

## Observações

O projeto foi desenvolvido como MVP acadêmico. Funcionalidades como auditoria completa e campanhas promocionais não foram implementadas nesta versão e são consideradas possibilidades de evolução do sistema.