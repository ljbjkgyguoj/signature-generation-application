Swagger документация API
http://localhost:8080/swagger-ui/index.html#/

Тестирование:

успешный кейс: 
curl --location 'http://localhost:8080/signature/create/1111' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--header 'Token: second-super-puper-secret' \
--data '{
"sdfgh": "string",
"pojkhvgh": "string",
"additionalProp3": "string"
}'

неверный токен:
curl --location 'http://localhost:8080/signature/create/1111' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--header 'Token: some-token' \
--data '{
"sdfgh": "string",
"pojkhvgh": "string",
"additionalProp3": "string"
}'

без токена:
curl --location 'http://localhost:8080/signature/create/1111' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data '{
"sdfgh": "string",
"pojkhvgh": "string",
"additionalProp3": "string"
}'