# Rest PROJECT



### 1. Docker-compose

```bash
cd path/to/project/
docker-compose -f docker-compose.yml up
```

### 2. Build Docker

```bash
cd path/to/project/
docker build -t rest .
```

### 3. Run the MySQL Container

```bash
docker run --name mysql-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db -e MYSQL_PASSWORD=root -p 3306:3306 -d mysql:latest
```

### 4. Create Tables


#### 4.1 Copy SQL file to container

```bash
docker cp ./src/main/resources/create_tables.sql mysql-db:/create_tables.sql

```

#### 4.2 execute script

```bash
docker exec -i mysql-db mysql -u root -p'root' db < ./create_tables.sql

```

#### 5. Run Your Application

```bash
docker run --name rest-app --link mysql-db -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/db -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=root rest

```


### 5. Test

In Postman POST:

```bash 
http://localhost:8080/api/usuarios
```

example of request

```bash 
{
  "cpf": "123.456.789-00",
  "nome": "João da Silva",
  "dataNascimento": "19/05/1990",
  "rua": "Rua das Flores",
  "numero": "123",
  "complemento": "Apto 45",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "12345-678",
  "usuarioCriacao": "admin"
}
```