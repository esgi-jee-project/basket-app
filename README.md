# JEE Basket Api

This api provide route to manage team, player, match and contract

## Dependencies

### Infrastructure
1. Keycloak for oauth authentication
2. Elastic search for complete search on match
3. PostgreSql for saving data

### Code
1. Spring boot to manage api routes and http response
2. Spring data to manage connection to postgresql and elastic
3. Lombok to generate getter and setter
4. Keycloak with Spring security to manage authentication


## How to build and deploy

This api provide DockerFile to build and run our api

### Environments
| Name | Description |
| ------- | ------- |
|PGSQL_HOST | Database host |
|PGSQL_DBNAME | Database name |
|PGSQL_ADMIN_USERNAME | Database username |
|PGSQL_ADMIN_PASSWORD | Database password |
|OPENID_PROVIDER_URL| Url of the keycloak openid id provider|
|OPENID_PROVIDER_REALM| Keycloak Realm |
|OPENID_PROVIDER_CLIENT | Keycloak client |
|ELASTIC_HOST| Elastic search host |
|ELASTIC_USER| Elastic search user |
|ELASTIC_PASSWORD| Elastic search password |

### Docker build command
```bash
docker build -t esgi-jee-api .
```
### Docker run command
```bash
docker run -p 8080:8080 -e ....All the env esgi-jee-api
```