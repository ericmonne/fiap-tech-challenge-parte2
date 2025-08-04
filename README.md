# Tech Challenge 2 - Sistema de Gestão para Restaurantes

## 📌 Descrição do Problema

Na região, um grupo de restaurantes decidiu se unir para desenvolver um sistema de gestão compartilhada, visando reduzir os altos custos de soluções individuais. Com isso, os clientes poderão escolher os restaurantes pela qualidade da comida, não pela tecnologia usada. A entrega do sistema será feita em fases, permitindo melhorias contínuas com base no uso real e no feedback dos usuários.

## 🎯 Objetivo do Projeto

O projeto busca criar um sistema centralizado e robusto para gerenciamento dos restaurantes e interação com os clientes. Nesta fase, serão implementadas:

- Gestão de tipos de usuários  
- Cadastro de restaurantes  
- Cadastro de itens para o menu  
- Aplicação de boas práticas de código limpo  
- Integração com testes automatizados, documentação e uso de Docker para facilitar a execução e implantação do sistema

---

## 🏛️ Arquitetura do Sistema

O projeto segue os princípios da **Clean Architecture**, promovendo desacoplamento, testabilidade e flexibilidade. As principais camadas são:

- **Domain**: entidades e regras de negócio  
- **Application**: casos de uso (use cases)  
- **Infrastructure**: persistência de dados, integrações externas  
- **API Routes**: endpoints expostos via HTTP  

A aplicação utiliza **Spring Boot**, mas está preparada para permitir substituições tecnológicas sem impactos nas regras de negócio.

---

## **Pré-requisitos**

Antes de começar, certifique-se de que as seguintes ferramentas estão instaladas e configuradas corretamente em sua máquina local:

1. **Minikube**: Utilizado para criar e gerenciar um cluster Kubernetes local.  
   - Instalação do Minikube: [Guia de Instalação do Minikube](https://minikube.sigs.k8s.io/docs/)

2. **kubectl**: Ferramenta de linha de comando para interagir com seu cluster Kubernetes.  
   - Instalação do kubectl: [Guia de Instalação do kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)

3. **Docker**: Utilizado para construir e enviar imagens Docker.  
   - Instalação do Docker: [Guia de Instalação do Docker](https://docs.docker.com/get-docker/)

4. **Imagem Docker do PostgreSQL**: Utilizaremos a imagem oficial do PostgreSQL.

---

## 🧪 Cobertura de Testes

O projeto adota uma estratégia de testes bem definida, composta por diferentes níveis de validação automatizada do sistema:

### ✅ Testes Unitários

Foram implementados testes unitários com foco nas **regras de negócio**, com o objetivo de atingir pelo menos **80% de cobertura de código**. Esses testes validam o comportamento de métodos isolados, sem dependências externas, garantindo precisão e robustez na lógica central do sistema.

### 🔗 Testes de Integração

Além dos testes unitários, foram desenvolvidos **testes de integração** para verificar o funcionamento conjunto entre os componentes do sistema — como as camadas **Application**, **Infrastructure**, e o **banco de dados**.

---

## ✅ Validação da Cobertura

A cobertura dos testes é monitorada com o auxílio do **JaCoCo**, que gera relatórios em formato HTML após a execução da suíte de testes.

---

## 🌐 Endpoints da API

### 🔐 Tipos de Usuário (`/usertypes`)

| Método | Descrição | Autorização |
|--------|-----------|-------------|
| GET    | Lista tipos de usuário | Autenticado |
| POST   | Cria novo tipo de usuário | OWNER |
| GET `/id` | Retorna tipo específico | Autenticado |
| PUT `/id` | Atualiza tipo existente | OWNER |
| PATCH `/id?activate=` | Ativa/desativa tipo | OWNER |

---

### 🍽️ Restaurantes (`/restaurants`)

| Método | Descrição | Autorização |
|--------|-----------|-------------|
| GET    | Lista restaurantes | Autenticado |
| POST   | Cria novo restaurante | OWNER |
| GET `/id` | Retorna restaurante por ID | Autenticado |
| PUT `/id` | Atualiza restaurante | OWNER |
| PATCH `/id?activate=` | Ativa/desativa restaurante | OWNER |

---

### 📋 Itens do Cardápio (`/menu-items`)

| Método | Descrição | Autorização |
|--------|-----------|-------------|
| GET    | Lista itens de um restaurante | Autenticado |
| POST   | Cria item no cardápio (com imagem) | OWNER |
| PUT `/id` | Atualiza item existente | OWNER |
| DELETE `/id` | Remove item do cardápio | OWNER |

---

## ⚙️ Tecnologias e Ferramentas

| Ferramenta | Finalidade |
|------------|------------|
| **Spring Boot** | Framework principal |
| **H2 Database** | Banco em memória para testes |
| **Springdoc OpenAPI** | Geração de documentação Swagger |
| **JUnit 5 + AssertJ + Mockito** | Testes unitários |
| **Cucumber + Gherkin** | Testes BDD |
| **Allure** | Relatórios de testes |
| **Rest-Assured** | Testes de API |
| **Jacoco** | Cobertura de testes |
| **Docker** | Containerização da aplicação |

---
