# Raízes do Nordeste

Projeto Back-end desenvolvido para a disciplina de Projeto Multidisciplinar do curso de Análise e Desenvolvimento de Sistemas.

O projeto apresenta uma API REST para a rede fictícia Raízes do Nordeste, permitindo o gerenciamento de usuários, unidades, produtos, pedidos, 
estoque, pagamentos e programa de fidelidade.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Swagger / OpenAPI
- Postman
- Git e GitHub

## Principais funcionalidades

- Cadastro e autenticação de usuários
- Controle de acesso por perfil: CLIENTE, FUNCIONARIO e ADMINISTRADOR
- Consulta de unidades e produtos disponíveis
- Realização e acompanhamento de pedidos
- Controle de estoque por unidade
- Processamento simulado de pagamentos
- Programa de fidelidade com pontos e descontos
- Consulta de relatórios administrativos

## Estrutura do projeto

A aplicação foi organizada em camadas:

Controller → Service → Repository → Banco de Dados

A API utiliza MySQL para persistência dos dados e JWT para autenticação e controle de acesso.

## Documentação da API

Com a aplicação em execução, a documentação dos endpoints pode ser consultada pelo Swagger:

http://localhost:8080/swagger-ui/index.html

## Observação

O serviço externo de pagamento foi simulado para fins acadêmicos.
