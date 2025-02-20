# 🚀 Projeto Clinix - Serviço de Clínica Médica

Este projeto é um serviço baseado em Spring Boot para gerenciar as clínicas médicas cadastradas no Clinix, os vínculo  delas entre seus médicos e os horários delas.

## 📌 Requisitos
Antes de executar o projeto, certifique-se de ter instalado:
- **JDK 17** ou superior
- **Maven**
- **PostgreSQL**
- **Docker** (opcional, caso queira rodar o banco em um container)

## 📥 Configuração do Ambiente
O projeto utiliza variáveis de ambiente armazenadas em um arquivo `.env`. Certifique-se de criar um arquivo `.env` na raiz do projeto e preencher com as seguintes configurações:

### **Configurações Gerais**
| Variável               | Descrição                                          | Exemplo               |
|------------------------|--------------------------------------------------|-----------------------|
| `APP_NAME`            | Nome da aplicação                                | `ClinixService`      |
| `CONTEXT_PATH`        | Caminho base da API                              | `/api/clinix`        |
| `SERVER_PORT`         | Porta onde o serviço será iniciado               | `8080`               |

### **Configurações de Banco de Dados (PostgreSQL)**
| Variável               | Descrição                                          | Exemplo               |
|------------------------|--------------------------------------------------|-----------------------|
| `DB_URL`              | URL de conexão com o banco de dados              | `jdbc:postgresql://localhost:5432/clinixdb` |
| `DB_USERNAME`         | Usuário do banco de dados                         | `admin`              |
| `DB_PASSWORD`         | Senha do banco de dados                           | `senha123`           |
| `DB_CLASS_NAME`       | Driver JDBC do PostgreSQL                         | `org.postgresql.Driver` |
| `DB_DIALECT`          | Dialeto do Hibernate para PostgreSQL              | `org.hibernate.dialect.PostgreSQLDialect` |
| `DDL_AUTO`            | Estratégia de criação/atualização do banco (`none`, `update`, `create`, `create-drop`) | `update` |

## ▶️ Como Executar o Projeto
### **1️⃣ Configurar o Banco de Dados**
Se estiver usando um banco local, crie um banco de dados PostgreSQL com o nome escolhido para a variável `DB_URL` e configure o `.env` corretamente.

Se quiser rodar um banco PostgreSQL com **Docker**, use o seguinte comando:
```sh
 docker run --name clinix-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=senha123 -e POSTGRES_DB=clinixdb -p 5432:5432 -d postgres
```

### **2️⃣ Instalar Dependências**
No diretório raiz do projeto, execute:
```sh
mvn clean install
```

### **3️⃣ Rodar a Aplicação**
```sh
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080/api/clinix`

## 📄 Endpoints Principais
- `GET /clinicas` - Lista todas as clínicas
- `POST /clinicas` - Cria uma nova clínica
- `GET /vinculos/{clinic_id}` - Lista todos os médicos vinculados a uma clinica
- `POST /vinculos/solicitar/{clinic_id}/{medic_id}` - Solicita o vínculo de um médico a uma clínica
- `Get vinculos/solicitacoes/{clinic_id}/` - Lista de Solicitações de vínculos

## 📌 Considerações Finais
Caso enfrente problemas de conexão com o banco de dados, verifique as configurações no `.env` e certifique-se de que o serviço PostgreSQL está rodando.

Fique à vontade para contribuir e relatar problemas! 🚀

