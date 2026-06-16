# Comandos en postman para probar el proyecto
## Admin
Username: superadmin
Password: admin123

## Autenticación
1. Iniciar sesión (login)
POST 
/api/auth/login

JSON
{
    "username": "user",
    "password": "password"
}

### Nota: 
(Copia el token de la respuesta y pégalo en la pestaña "Authorization -> Bearer Token" de Postman para las peticiones que lo requieran)

## Usuarios

1. Registrar usuario

POST 
/api/user/register

Body: JSON
{
    "nombre": "nombre",
    "apellidoPaterno": "apellidoPaterno",
    "apellidoMaterno": "apellidoMaterno",
    "correo": "correo",
    "username": "username",
    "password": "password",
    "idRol": 1,
    "idTipoUsuario": 1,
    "idProgramaEducativo": 1
}
Authorization: Bearer <token>

### Nota: 
Para registrar un usuario se necesita un token de un usuario Administrador (idRol=1)

2. Verificar si existe usuario
GET 
/api/user/{idUsuario}/exist

3. Verificar estatus del usuario
GET 
/api/user/{claveUsuario}/status

4. Obtener perfil del usuario
GET 
/api/user/profile/{idUsuario}

5. Editar usuario
PUT 
/api/user/{idUsuario}

Body: JSON
{
    "nombre": "nombre",
    "apellidoPaterno": "apellidoPaterno",
    "apellidoMaterno": "apellidoMaterno",
    "correo": "correo",
    "telefono": "telefono",
    "idRol": 1,
    "idTipoUsuario": 1,
    "idProgramaEducativo": 1
}

6. Cambiar estatus del usuario
PATCH 
/api/user/{idUsuario}/estatus
