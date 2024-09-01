# JavaWebProject

### Введение:
**JavaWebProject** - Небольшой Rest Api Token Based проект, написанный на Java, с использованием фреймворка Spring Boot [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)


### Описание
Проект представляет из себя сервер с базой данных, с возможностью выполнения CRUD операций над сущностью User.  
А так же с аутентификаций пользователя посредством передачи Bearer токена через HTTP заголовки.


### Реализованный функционал:

#### API:

### Auth

**[POST]**

*/api/v1/auth* - получить JWT токен **<API_TOKEN>**
JSON 
```
{
  `username`:"admin",
  `password`:"admin"
}
```

### Users
Headers
Authorization: **Bearer <API_TOKEN> (required)**

**[GET]**

*/api/v1/users?page=1&per_page=10* - получить всех пользователей с пагинацией  
*/api/v1/users/{id}* - получить пользователя по id  

**[POST]**

*/api/v1/users* - создать пользователя  
JSON 
```
{
  `username`:"test123",
  `password`:"test123",
  `email`:"test123@mail.ru"
}
```

**[PATCH]**

*/api/v1/users/{id}* - обновить данные пользователя по id
JSON 
```
{
  `username`:"test321",
  `password`:"test321"
}
```
-----

### Docker Compose
> Докер файлы находятся по пути /src/main/docker  
> sudo docker-compose up --build

### Swagger
> Сваггер документация находится на домашней странице "/"
