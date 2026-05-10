# S3 Tables Demo

Uma aplicação Spring Boot que demonstra a integração com AWS S3 Tables, fornecendo uma API REST para consultar informações de tabelas armazenadas no S3.

## Visão Geral

Esta aplicação segue o padrão de arquitetura hexagonal (ports and adapters) para fornecer um serviço de consulta de dados de tabelas S3. A aplicação é construída com Java 21 e Spring Boot 3.5.6, oferecendo uma estrutura limpa e testável.

## Funcionalidades

- ✅ Integração com AWS S3 Tables SDK
- ✅ API REST para consulta de metadados de tabelas
- ✅ Suporte a autenticação AWS com credenciais básicas ou session tokens
- ✅ Mapeamento de DTOs com MapStruct
- ✅ Arquitetura hexagonal para fácil manutenção e testes

## Arquitetura

A aplicação está organizada em camadas seguindo o padrão hexagonal:

```
├── domain/              # Camada de domínio (modelos e portas)
│   ├── model/          # Entidades de domínio (DadosTabela)
│   └── ports/          # Interfaces de entrada e saída
│       ├── in/         # Portas de entrada (ConsultaPort)
│       └── out/        # Portas de saída (TabelaRepositoryPort)
├── application/        # Camada de aplicação (use cases)
│   └── ConsultaUsecase # Lógica de negócio para consulta de tabelas
└── infrastructure/     # Camada de infraestrutura (adapters)
    ├── controller/     # REST controllers
    ├── adapters/       # Implementações dos adapters
    │   ├── in/        # Adapters de entrada
    │   └── out/       # Adapters de saída
    ├── config/        # Configurações AWS
    ├── mapper/        # Mappers de DTO
    └── dto/           # Data Transfer Objects
```

## Requisitos

- Java 21+
- Maven 3.6+
- Credenciais AWS com acesso ao S3 Tables
- ARN da tabela S3 para consultar

## Instalação e Configuração

### 1. Clonar o repositório

```bash
git clone <repository-url>
cd s3tables-demo
```

### 2. Configurar variáveis de ambiente

Configure as seguintes variáveis de ambiente antes de executar a aplicação:

```bash
export AWS_ACCESS_KEY_ID=seu-access-key
export AWS_SECRET_ACCESS_KEY=sua-secret-key
export AWS_REGION=us-east-1  # opcional, padrão: us-east-1
export S3_TABLE_ARN=arn:aws:s3tables:region:account-id:tableb/bucket/table  # ARN da tabela
export AWS_SESSION_TOKEN=seu-session-token  # opcional, para credenciais temporárias
```

### 3. Compilar o projeto

```bash
mvn clean package
```

### 4. Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Endpoints

### GET /teste

Retorna os metadados de uma tabela S3.

**URL:** `http://localhost:8080/teste`

**Método:** `GET`

**Resposta (200 OK):**

```json
{
  "name": "exemplo_tabela",
  "type": "ICEBERG",
  "tableArn": "arn:aws:s3tables:us-east-1:123456789012:tableb/bucket/table",
  "namespace": ["default", "schema"],
  "namespaceId": "ns-123456",
  "versionToken": "v1.0",
  "metadataLocation": "s3://bucket/path/metadata",
  "warehouseLocation": "s3://bucket/path/warehouse",
  "createdAt": "2024-01-15T10:30:00Z",
  "createdBy": "user@example.com",
  "modifiedAt": "2024-01-15T10:30:00Z",
  "ownerAccountId": "123456789012",
  "format": "ICEBERG",
  "tableBucketId": "bucket-id-123"
}
```

## Estrutura dos Componentes

### Domain Layer

#### DadosTabela (Modelo de Domínio)
Record que representa os dados de uma tabela S3 com seus metadados.

**Atributos:**
- `name`: Nome da tabela
- `type`: Tipo da tabela
- `tableArn`: ARN completo da tabela
- `namespace`: Namespace da tabela
- `namespaceId`: ID do namespace
- `versionToken`: Token de versão
- `metadataLocation`: Localização dos metadados
- `warehouseLocation`: Localização do warehouse
- `createdAt`: Data de criação
- `createdBy`: Criador da tabela
- `modifiedAt`: Data da última modificação
- `ownerAccountId`: ID da conta AWS proprietária
- `format`: Formato da tabela
- `tableBucketId`: ID do bucket da tabela

#### ConsultaPort (Porta de Entrada)
Interface que define o contrato para consulta de tabelas.

```java
public interface ConsultaPort {
    DadosTabela consultarTabela();
}
```

#### TabelaRepositoryPort (Porta de Saída)
Interface que define o contrato para acesso aos dados de tabelas.

```java
public interface TabelaRepositoryPort {
    DadosTabela consultarTabela(String arn);
}
```

### Application Layer

#### ConsultaUsecase
Implementa a lógica de negócio para consultar tabelas. Orquestra a comunicação entre o controller e o repository.

### Infrastructure Layer

#### TesteController
REST controller que expõe o endpoint `/teste` para consultar dados de tabelas.

#### TabelaRepository
Adapter que implementa a `TabelaRepositoryPort`, integrando-se com a AWS S3 Tables API.

#### ConsultaService
Adapter que implementa a `ConsultaPort`, fornecendo os dados formatados para uso na aplicação.

#### TableMapper
Mapper que converte `DadosTabela` para `TableDto` usando MapStruct.

#### TableDto
DTO para transmissão de dados via API REST.

#### AwsConfig
Configuração Spring que define os beans da AWS:
- `AwsCredentials`: Credenciais AWS configuradas
- `S3TablesClient`: Cliente S3 Tables configurado

#### AwsProperties
Record que carrega as propriedades de configuração AWS do `application.yml`.

## Configuração de Propriedades

O arquivo `src/main/resources/application.yml` define as propriedades da aplicação:

```yaml
spring:
  application:
    name: s3tables-demo

aws:
  access-key: ${AWS_ACCESS_KEY_ID}
  secret-key: ${AWS_SECRET_ACCESS_KEY}
  session-token: ${AWS_SESSION_TOKEN:}
  region: ${AWS_REGION:us-east-1}
  s3-table-arn: ${S3_TABLE_ARN}
```

### Variáveis de Ambiente

| Variável | Tipo | Obrigatório | Descrição |
|----------|------|-------------|-----------|
| AWS_ACCESS_KEY_ID | String | Sim | Access key da AWS |
| AWS_SECRET_ACCESS_KEY | String | Sim | Secret access key da AWS |
| AWS_SESSION_TOKEN | String | Não | Session token (para credenciais temporárias) |
| AWS_REGION | String | Não | Região AWS (padrão: us-east-1) |
| S3_TABLE_ARN | String | Sim | ARN da tabela S3 a consultar |

## Dependências Principais

- **Spring Boot 3.5.6**: Framework web
- **AWS SDK 2.34.9**: Integração com serviços AWS
- **MapStruct 1.6.3**: Mapeamento de objetos
- **Instancio 6.0.0**: Testes com geração automática de dados
- **JUnit**: Framework de testes

## Desenvolvimento e Testes

### Executar testes

```bash
mvn test
```

### Compilar sem executar testes

```bash
mvn clean compile
```

### Gerar JAR executável

```bash
mvn clean package -DskipTests
```

## Fluxo de Execução

1. **Request chega ao TesteController** (`/teste`)
2. **Controller chama ConsultaUsecase** para obter dados
3. **ConsultaUsecase delega para ConsultaPort** (implementado por ConsultaService)
4. **ConsultaService chama TabelaRepositoryPort** (implementado por TabelaRepository)
5. **TabelaRepository usa S3TablesClient** para consultar AWS S3 Tables
6. **Dados retornam em cascata** pelas camadas
7. **TableMapper converte DadosTabela para TableDto**
8. **Controller retorna DTO em JSON**

## Tratamento de Erros

Atualmente, a aplicação não possui tratamento global de erros implementado. Em caso de erro na comunicação com AWS, a aplicação retornará uma exceção não capturada. Para produção, considere implementar:

- `@ControllerAdvice` para tratamento centralizado de exceções
- Exceções customizadas de domínio
- Respostas de erro padronizadas

## Próximas Melhorias

- [ ] Implementar tratamento global de erros
- [ ] Adicionar cache de dados de tabelas
- [ ] Implementar paginação para múltiplas tabelas
- [ ] Adicionar validação de entrada
- [ ] Melhorar logging e monitoramento
- [ ] Adicionar testes de integração com AWS

## Recursos e Documentação

- [AWS S3 Tables Documentation](https://docs.aws.amazon.com/AmazonS3/latest/userguide/s3-tables.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MapStruct Documentation](https://mapstruct.org/)
- [AWS SDK for Java v2](https://docs.aws.amazon.com/sdk-for-java/)

## Licença

Projeto de demonstração. Use livremente para fins de aprendizado.

## Autor

Gustavo Silva (@gasfgrv)
