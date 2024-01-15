# Collaborative Voting System

Olá, eu sou um projeto desenvolvido para fins de um desafio técnico, e ao logo dessa documentação você vai conhecer mais sobre mim e como fazer com que eu funcione no seu ambiente.

# Sobre

Sou um projeto que foi criado para gerenciar pautas e suas sessões de votações. Meu objetivo é que você consiga criar uma pauta, iniciar uma sessão de votação com um tempo de duração, que você vai determinar, e partir dai, associados poderem votar. Além disso vai ser possível visualizar os resultados das votações de forma normal ou detalhada

## Começando

### Requisitos

- Java 17
- Maven: 3.8.2 +
- Sua IDE favorita (IntelliJ IDEA, Sts, etc.)
- Docker e Docke compose

### Instalação

##### 1) Utilizando Docker e Docker compose

1. Pegar o repositório do git e extrair em uma pasta local
     ```bash
    git clone https://github.com/CharlesHenr1queDEV/collaborative-voting-system.git
    ```
2. Navegar até a pasta 
    ```bash
    cd collaborative-voting-system
    ```
3. Builda o projeto:
    ```bash
    mvn clean package
    ```
4. Gerar uma imagem docker
     ```bash
     docker build --tag=collaborative.voting.system:1.0 .
    ```
4. Execute o docker-compose 
     ```bash
    docker-compose up ou docker-compose up -d
    ```
    - Certifique-se de ter o Docker e docker-compose instalado em sua maquina
    - Certifique-se de que as portas configuradas não estejam sendo utilizadas.
    - Configure as conexões do RABBITMQ  e MySQL de acordo com sua necessidade.     Obs: Por padrão já está tudo configurado.
5. Acessar a documentação da API no swagger
    - http://localhost:8080/swagger-ui/index.html

##### 2) Utilizando uma IDE

1. Pegar o repositório do git e extrair em uma pasta local
     ```bash
    git clone https://github.com/CharlesHenr1queDEV/collaborative-voting-system.git
    ```
2. Navegar até a pasta 
    ```bash
    cd collaborative-voting-system
    ```
3. Atualizar as dependências
    ```bash
    mvn clean install
    ```
4. Importar o projeto em sua IDE favoriva
    - Import existing maven project

5. Executar o docker-compose somente o MySQL e RabbitMQ
    - Exemplo
    ![Exemplo de Imagem](https://i.ibb.co/16wqHwY/example-compose-comment.jpg)
6. Run ou Run Debug
7. Acessar a documentação da API no swagger
    - http://localhost:8080/swagger-ui/index.html

##### 3) Utilizando somente Docker compose

1. Baixar o arquivo docker-compose ou copiar o conteúdo
   [Link: docker-compose](https://github.com/CharlesHenr1queDEV/technical-challenge-dbc/blob/main/docker-compose.yml)
2. Executar o arquivo
    ```bash
    docker-compose up 
    ```
    - A imagem docker já está no docker hub aberta, com isso nesse arquivo já tem toda a configuração para execução do projeto


### Uso
1. Acesse a documentação do swagger para obter detalhes sobre a API
    - http://localhost:8080/swagger-ui/index.html

### Features
Gerenciar pautas, iniciar sessões de votação, liberar a votação para diversos associados e obter o resultado final dessas votações para saber se uma pauta foi ou não aprovada. 

### Testes
O projeto inclui testes de integração da api para garantir o funcionamento da aplicação. Execute os testes usando:
```bash
  mvn test
 ```
  *Obs: Foram criados 23 testes nos endpoints da API para garantir o funcionamento do sistema*

 ### API Endpoints
 
 *Obs: todas os endpoints possuem a opção de passar um parâmetro pelo header chamado de language: en ou br, que vai retornar mensagens traduzidas na linguagem escolhida. Foram traduzidos apenas mensagens de exceções em erros na parte de regra de négocio da aplicação.*
#### Criar nova pauta

Endpoint: api/v1/schedule/create

Method: POST

Parameters:
- title:  Título da sessão
- description: Descrição da sessão

Request Body:
- Content Type: application/json

Example Obj:
```json
{
  "title": "Título da Sessão",
  "description": "Descrição detalhada da sessão"
}
```

#### Buscar pauta por id
Endpoint: api/v1/schedule/{id}

Method: GET

Parameters:
- id:  Id da pauta


#### Listar todas as pautas
Endpoint: api/v1/schedule

Method: GET

#### Obter status de uma pauta
Endpoint: api/v1/schedule/status/{scheduleId}

Method: GET

Parameters:
- id:  Id da pauta

#### Obter resultado de uma pauta
Endpoint: api/v1/schedule/result-detailed/{scheduleId}

Method: GET

Parameters:
- id:  Id da pauta

#### Abrir sessão em uma pauta
Endpoint: api/v1/voting-session/open

Method: GET

Parameters:
- id:  Id do schedule
- votingDurationMinutes: Intervalo de tempo que a sessão vai ficar aberta para votação

Endpoint: api/v1/vote

Method: POST

Parameters:
- schduleId:  Id da pauta
- associateIdentifier: Idenfificador do associado (CPF)
- voteChoice: Escolha do voto (sim ou não)

Request Body:
- Content Type: application/json

Example Obj:
```json
{
  "schduleId": 1,
  "associateIdentifier": "07178984406",
  "voteChoice": "sim"
}
```
# Desafios bonus
 1. Tarefa Bônus 1 - Integração com sistemas externos
    - Foi solicitado para desconsiderar pois o API não está funcionando
 2. Tarefa Bônus 2 - Mensageria e filas
    - Explicação: Para resolver esse problema, foi criada a seguinte solução: ao inicializar uma nova sessão de votação, é configurado um tempo para que ela fique aberta. Dessa forma, tenho no código o tempo que a sessão permanecerá aberta. Em seguida, criei um agendamento em Java que executará um determinado método após o intervalo de tempo estabelecido para o encerramento da sessão. Após esse intervalo de tempo e com a sessão já finalizada, o método será executado para calcular os votos e enviar uma mensagem para o RabbitMQ com um objeto contendo informações, tais como: a quantidade total de votos, a quantidade de votos "sim", a quantidade de votos "não" e o status resultante (APROVADO, NÃO APROVADO, EMPATE).
    
    - Ex da mensagem:
     ![Exemplo de Imagem](https://i.ibb.co/MsnzccH/rabbit-message.jpg)
    

 3. Tarefa Bônus 3 - Performance
     - Explicação: Na abordagem de desempenho, busquei manter o código o mais simples e prático possível, implementando otimizações contínuas ao longo do desenvolvimento para aprimorar o tempo de execução. Embora eu tenha conhecimento da ferramenta JMeter, infelizmente, não tenho total domínio para aplicar todas as suas propostas no prazo estabelecido. No entanto, possuo uma curva de aprendizado rápida e acredito que posso evoluir bastante nesse quesito.

4. Tarefa Bônus 4 - Versionamento da API
    - Explicação: Para versionar a API da minha aplicação, eu adotei a estratégia de versionamento semântico, utilizando um número de versão no formato "MAJOR.MINOR.PATCH". A abordagem semântica ajuda a transmitir informações claras sobre as mudanças na API.


# Equipe de desenvolvedores

* Charles Henrique  - [Linkedin](https://www.linkedin.com/in/charles-henrique-5b432a143/)
 
