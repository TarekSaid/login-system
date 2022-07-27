# login-system

O sistema de login é uma API de login simples que demonstra a separação entre o servidor de autorização do OAuth2 e os recursos protegidos em diferentes serviços. Nesse exemplo, há um servidor de autorização e dois recursos, o usuário e seu perfil.

## Execução
a API está separada em 4 serviços diferentes: discovery-service, user-service, profile-service e proxy-service.

### postgres

É necessário ter instalado o PostgreSQL 13.7 na porta padrão com um usuário loginuser, senha loginpass e um banco de dados chamado login-system.
As tabelas e dados do banco serão criados automaticamente através das migrações do FlyWay.

**Via Docker:**

`docker run --name login-db -e POSTGRES_USER=loginuser -e POSTGRES_PASSWORD=loginpass -e POSTGRES_DB=login-system -p 5432:5432 -d postgres:13.7-alpine`

### compilação

Ao compilar o projeto usando `mvn clean install` a partir da pasta raiz, os testes unitários e de integração serão automaticamente executados.

### ordem de execução

Após a compilação, basta executar os serviços através do `java -jar [nome-do-serviço].jar`, na seguinte ordem:

1. discovery-service
2. user-service
3. profile-service
4. proxy-service

## user-service (obter token de autorização)

O user-service é o AuthorizationServer, ou seja, um serviço de backend que autentica e devolve o token para os clientes. Para obter um token de autorização, é necessário chamar o endpoint /oauth/token, conforme o exemplo abaixo:

`curl webClient:secret@localhost:8181/oauth/token -dusername=user -dpassword=password -dgrant_type=password`

O serviço irá buscar na base de dados se o usuário existe. Em caso positivo, irá devolver um resultado parecido com o abaixo:

`{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDczODQxOTcsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIiwiQURNSU4iXSwianRpIjoiNzNlM2ZlNzctZGZkZi00YzM3LTkwYTMtYzdmYWQyM2Y4YTNlIiwiY2xpZW50X2lkIjoid2ViQ2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.bCrmMNLja20pwKXNcjUJ9omHC7gGRRqQUaMDnydCIyw","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjczZTNmZTc3LWRmZGYtNGMzNy05MGEzLWM3ZmFkMjNmOGEzZSIsImV4cCI6MTUwOTkzMjk5NywiYXV0aG9yaXRpZXMiOlsiVVNFUiIsIkFETUlOIl0sImp0aSI6ImI4OTcyMWJmLWIwMmEtNDVjYS1hYjE0LTgzNmM5NzcxNmQ1NyIsImNsaWVudF9pZCI6IndlYkNsaWVudCJ9.1XYKE8sV2D-LbolMR_WmmKc_76R-iUc1Rxx99TUEUZE","expires_in":43199,"scope":"read write","jti":"73e3fe77-dfdf-4c37-90a3-c7fad23f8a3e"}`

O Access Token está sendo criado usando um JWT Access Token encriptado. A vantagem desse tipo de token é que não precisa ser persistido, por ser json, possui todas as informações (criptografadas) no próprio token.

Após a autenticação, é possível acessar os recursos protegidos usando esse token de acesso no header 'Authorization', da seguinte forma:

`curl -H "Authorization: Bearer $token" -H 'application/json' http://localhost:8181/me`

O user-service também tem um recurso protegido: o usuário logado. Além dos endpoints do OAuth2, ele expõe dois endpoints para o usuário: `/me`, que busca o usuário no banco, e `/user`, que retorna o usuário logado. Isso é útil para recursos (como, por exemplo, o de perfil) onde o usuário não possa acessar informações de outros.

## profile-service (informações do perfil do usuário)

O profile-service é um recurso protegido, que precisa de autenticação. Ele expõe o endpoint `/`, que busca o perfil do usuário. Para consultar o usuário logado, o profile usa um Feign Client, apontando para `http://user-service/me`. Através do serviço de descoberta de serviços, o discovery-service, o profile-service consegue receber o usuário logado e usar seu id para trazer o perfil atrelado:

`curl -H "Authorization: Bearer $token" -H 'Content-Type: application/json' http://localhost:8182/`
`{"id":1,"name":"Teste LTDA","businessType":"Loja Online","website":"www.teste.com.br"}`

## discovery-service (descoberta de serviços)

O discovery-service é um serviço backend para a descoberta de serviços, usando o Eureka. Ao criar um novo serviço, ele se registra no discovery-service, o que permite:

1. Fazer o Load Balance de várias instâncias de um mesmo serviço (via Docker);
2. Chamar um serviço registrado via Feign Client;
3. Mapear o nome do serviço no Zuul, como servidor de proxy.

## proxy-service (proxy reverso)

O proxy-service é outro serviço backend, que usa o Netflix Zuul que configura um caminho único para acessar os diferentes serviços. Ele sobe na porta 9999.

Dessa forma, os clientes da API não precisam se preocupar em saber quais serviços estão disponível em quais portas:

`curl -H "Authorization: Bearer $token" -H 'Content-Type: application/json' http://localhost:9999/profile`
`{"id":1,"name":"Teste LTDA","businessType":"Loja Online","website":"www.teste.com.br"}`

## Pontos de Melhoria

Há ainda alguns pontos que podem ser melhorados na API:

1. Não consegui implementar um JdbcTokenStore, por isso optei pela JWT Token Store;
2. Não consegui fazer o teste integrado do profile-service funcionar com o Feign Client, apenas os testes unitários;
3. Não tive tempo de implementar um serviço de configuração (spring-boot-starter-config) ue separasse as configurações do código;
4. Não tive tempo de implementar um cliente de demonstração com o angular-js.
5. Não tive tempo de criar os arquivos Dockerfile para criar imagens do Docker, nem de criar um arquivo docker-compose.yml. Isso iria facilitar o processo de execução da API, pois bastaria ter o Docker Compose instalado e executar o arquivo.
