# User Management Service

Este servicio proporciona una API RESTful para la gestión de usuarios.

## Funcionalidades

- Registro de usuarios
- Consulta de usuarios
- Eliminación de usuarios
- Autenticación de usuarios
- Deshabilitación de usuarios
- Obtención de información de usuarios

## Información del proyecto
- Se utilizó H2 como BD embebida.
- Se aplico diferentes patrones de diseño, entre los que destacan:
  - Patrón de Repositorio: UserRepository es un ejemplo de este patrón.
  - Patrón de Servicio: UserServiceImpl implementa la interfaz UserService, lo que es un ejemplo de este patrón.
  - Patrón de Inyección de Dependencias (Dependency Injection): La inyección de UserRepository y ApplicationProperties en UserServiceImpl es un ejemplo de este patrón.
  - Patrón de Data Transfer Object (DTO): UserDto y LoginRequest son ejemplos de este patrón.
  - Patrón de Singleton: UserMapper es un ejemplo de este patrón, ya que utiliza una instancia estática (INSTANCE) para proporcionar un punto de acceso global.
  - Patrón de Factory: Este patrón se utiliza para crear objetos sin especificar la clase exacta que se creará. Mappers.getMapper(UserMapper.class) es un ejemplo de este patrón.
  - Patrón de Builder: Las anotaciones @Builder en las clases UserDto, LoginRequest, User y Phone son ejemplos de este patrón.
- Se trabajo con programación reactiva (reactor).
- Se generó excepciones personalizadas
- Se creó un generador de JWT
- Se utilizó las buenas prácticas priorizando la mantenibilidad del código.

## Configuración

### Requisitos previos

- Java 8 o superior
- Maven 3.x

### Configuración del proyecto

1. Clona el repositorio:

    ```bash
    git clone https://github.com/gcas17/business-user-v1.git
    ```

2. Navega al directorio del proyecto:

    ```bash
    cd business-user-v1
    ```

3. Compila el proyecto:

    ```bash
    mvn clean install
    ```

### Configuración de la base de datos

El servicio utiliza una base de datos H2 en memoria. No se requiere configuración adicional.

1. Scripts (informativo):
    ```bash
    DROP TABLE IF EXISTS customer;
   CREATE TABLE customer(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   lastlogin TIMESTAMP,
   token VARCHAR(255),
   isactive BOOLEAN
   );
   
   DROP TABLE IF EXISTS phone;
   CREATE TABLE phone(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   number VARCHAR(255),
   citycode VARCHAR(255),
   countrycode VARCHAR(255),
   user_id VARCHAR(255),
   FOREIGN KEY (user_id) REFERENCES Customer(id)
   )
    ```

## Uso

1. Ejecuta la aplicación:

    ```bash
    java -jar target/business-user-v1-0.0.1-SNAPSHOT.jar
    ```

2. Accede a la API a través de la siguiente URL:

    ```
    http://localhost:8080/swagger-ui.html
    ```

## Endpoints

1. Registro de usuario
   Crea un nuevo usuario en el sistema.
   POST http://localhost:8080/party/customer-profile/v1/users
   
   #### Ejemplo de solicitud:
   ```json
   {
     "name": "Juan Rodriguez",
     "email": "juan@rodriguez.org",
     "password": "hunter2",
     "phones": [
       {
         "number": "1234567",
         "citycode": "1",
         "contrycode": "57"
       }
     ]
   }
   ```

   #### Ejemplo de respuesta:
   ```json
   {
      "id": 1,
      "created": "2024-05-04T02:36:37.4186357",
      "modified": "2024-05-04T02:36:37.4186357",
      "lastlogin": "2024-05-04T02:36:37.4186357",
      "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5jb20iLCJpYXQiOjE3MTQ4MDgxOTcsImV4cCI6MTcxNDgwODIyN30.-Da-_9YGEFqfuugXA5NLJmHQQzJMr3w_-ga7rRhnV-nCCkNATaAJmRKxma2AxbKt8TL8l0WNl71PETxh5AeOgQ",
      "isactive": true
   }
   ```

2. Consulta de usuario
   Consulta un usuario en el sistema.

   GET http://localhost:8080/party/customer-profile/v1/users/1
   
   #### Ejemplo de solicitud:
   ```json
   No aplica.
   ```

   #### Ejemplo de respuesta:
   ```json
   {
      "id": 1,
      "created": "2024-05-04T02:36:37.418636",
      "modified": "2024-05-04T02:36:37.418636",
      "lastlogin": "2024-05-04T02:36:37.418636",
      "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5jb20iLCJpYXQiOjE3MTQ4MDgxOTcsImV4cCI6MTcxNDgwODIyN30.-Da-_9YGEFqfuugXA5NLJmHQQzJMr3w_-ga7rRhnV-nCCkNATaAJmRKxma2AxbKt8TL8l0WNl71PETxh5AeOgQ",
      "isactive": true,
      "phones": [
         {
            "id": 1,
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57",
            "userId": 1
         },
         {
            "id": 2,
            "number": "1234568",
            "cityCode": "2",
            "countryCode": "58",
            "userId": 1
         }
      ]
   }
   ```

3. Listar usuarios
   Lista todos los usuarios en el sistema.

   GET http://localhost:8080/party/customer-profile/v1/users
   
   #### Ejemplo de solicitud:
   ```json
   No aplica.
   ```
   
   #### Ejemplo de respuesta:
   ```json
   [
      {
         "id": 1,
         "created": "2024-05-04T02:36:37.418636",
         "modified": "2024-05-04T02:36:37.418636",
         "lastlogin": "2024-05-04T02:36:37.418636",
         "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5jb20iLCJpYXQiOjE3MTQ4MDgxOTcsImV4cCI6MTcxNDgwODIyN30.-Da-_9YGEFqfuugXA5NLJmHQQzJMr3w_-ga7rRhnV-nCCkNATaAJmRKxma2AxbKt8TL8l0WNl71PETxh5AeOgQ",
         "isactive": true,
         "phones": [
            {
               "id": 1,
               "number": "1234567",
               "cityCode": "1",
               "countryCode": "57",
               "userId": 1
            },
            {
               "id": 2,
               "number": "1234568",
               "cityCode": "2",
               "countryCode": "58",
               "userId": 1
            }
         ]
      }
   ]
   ```

4. Autenticación de usuario
   Login del usuario en el sistema.

   POST http://localhost:8080/party/customer-profile/v1/users/login
   
   #### Ejemplo de solicitud:
   ```json
   {
      "email": "juan@rodriguez2.com",
      "password": "hunter3"
   }
   ```
   
   #### Ejemplo de respuesta:
   ```json
   No aplica. Solo brinda un HttpStatus 200 cuando la petición es exitosa.
   ```

5. Deshabilitar usuario
   Consulta un usuario en el sistema.
   POST http://localhost:8080/party/customer-profile/v1/users/1/disable
   
   #### Ejemplo de solicitud:
   ```json
   No aplica.
   ```
   
   #### Ejemplo de respuesta:
   ```json
   No aplica. Solo brinda un HttpStatus 200 cuando la petición es exitosa.
   ```

6. Eliminar usuario
   Elimina un usuario del sistema de forma fisica.

   DELETE http://localhost:8080/party/customer-profile/v1/users/2

   #### Ejemplo de solicitud:
   ```json
   No aplica.
   ```

   #### Ejemplo de respuesta:
   ```json
   No aplica. Response HTTP Status 200 OK si se eliminó correctamente.
   ```

7. Generar JWT de usuario
   Genera un JWT con duración de 60 segundos con el correo del usuario. Se puede validar en jwt.io

   GET http://localhost:8080/party/customer-profile/v1/users/generate-authorization-token?email=juan@rodriguez.com
   
   #### Ejemplo de solicitud:
   Query param con el correo del usuario
   ```json
   email:juan@rodriguez.com
   ```
   
   #### Ejemplo de respuesta:
   ```json
   eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5jb20iLCJpYXQiOjE3MTQ4MDczNzYsImV4cCI6MTcxNDgwNzQwNn0.lx772S_ErBqVuJVIBjMdf8H0sT8HL267ZFJfgxP1eda7LEj6lBlFAGh-c6iGs_rgi44SsjutusGQS8bfFlr42A
   ```



