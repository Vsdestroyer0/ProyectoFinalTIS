# Dudas del Microservicio de Vehiculos

## Que es el substring(7) en el controller y si hay otra manera
En las peticiones HTTP seguras, el token siempre llega en los encabezados (headers) acompañado de la palabra "Bearer " (que significa "Portador"). Es decir, el texto llega como: "Bearer eyJhbGci...".
La palabra "Bearer " seguida del espacio ocupa exactamente 7 caracteres. Cuando usas `authHeader.substring(7)`, le estas diciendo a Java: "Corta los primeros 7 caracteres de este texto y dame solo lo que queda", para obtener el token limpio.

* **¿Hay otra forma de hacerlo?** Sí. En lugar de contar caracteres puedes usar `authHeader.replace("Bearer ", "")`. Otra manera (más profesional) es usar un "Interceptor" o un Filtro Global de Spring Security que haga este recorte automáticamente en una sola clase central antes de que llegue a tus controladores, pero eso requiere más configuración.

## Porque hay una clase vacia llamada VehiculosDTO
Es un archivo "basura" o plantilla que se quedó olvidado durante el desarrollo. Seguramente al inicio del proyecto alguien lo creó como molde, pero después se dieron cuenta de que necesitaban dos moldes distintos y terminaron creando `VehiculoRequestDTO` (para recibir datos de Postman) y `VehiculoResponseDTO` (para mandar datos con marcas y modelos). **Puedes borrar `VehiculosDTO.java`** con total seguridad, no hace nada.

## ¿La capa de seguridad se hace igual que en usuarios?
**NO**, no funciona igual. 
En el microservicio de Usuarios tienes un archivo llamado `SecurityConfig` que bloquea y da permisos a las rutas a nivel global (como un cadenero en la puerta del antro). 
En Vehículos (y Estacionamiento), la seguridad se está haciendo de manera "Manual". Si te fijas en tus controladores y en el Service, en cada función extraes el token y llamas manualmente a `jwtUtils.validateJwtToken(token)`. Es decir, aquí el cadenero no está en la puerta principal, sino que cada mesero (cada función) verifica el gafete del cliente antes de atenderlo.

## Como funciona a detalle el Service de Vehiculos
El Service es el núcleo de tu aplicación, su trabajo principal es desconfiar de todo lo que manda Postman y proteger la base de datos. Funciona paso a paso así:

1. **Valida el Token:** Lo primero que hace en cualquier función es ver si el token sirve. Si ya caducó o lo inventaron, tira un error de inmediato.
2. **Revisa que no manden basura:** Verifica que ningun campo obligatorio venga nulo y que los textos no sobrepasen el tamaño de las columnas de MySQL (para que no colapse la base de datos).
3. **Aplica Seguridad BOLA (La parte más importante):** Si el usuario Juan (ID 5) intenta registrar un carro a nombre de Pedro (ID 10), el Service extrae el ID oculto dentro del token de Juan y se da cuenta de que no coinciden. Automáticamente le deniega el permiso, a menos de que Juan tenga rol de Administrador. Lo mismo aplica para editar o borrar carros.
4. **Validaciones de Reglas de Negocio:**
   * Revisa en la base de datos (usando el Repository) cuántos carros activos tiene el dueño. Si ya tiene 4, no lo deja registrar otro.
   * Revisa que la placa no exista ya en la base de datos. Si se repite, lanza un error para evitar duplicados.
5. **Generación y Guardado:** Genera automáticamente la clave del carro (`V-XXX`), mete todos los datos validados en el formato que entiende la base de datos (`VehiculosEntity`) y finalmente le da la orden al Repository de guardar los cambios usando `repository.save()`.
