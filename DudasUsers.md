# Dudas del Microservicio de Usuarios

## ¿Qué es ResponseEntity<?> y por qué no usar void u otra cosa?
`ResponseEntity<?>` es una clase de Spring Boot que representa toda la respuesta HTTP (código de estado, encabezados y el cuerpo/texto de la respuesta).
* **¿Por qué no usar `void`?** Si usaras `void`, no le devolverías ningún mensaje a quien hizo la petición (Postman). El usuario no sabría si la operación fue exitosa, si falló, ni recibiría sus datos.
* **¿Por qué el `<?>`?** El signo de interrogación es un "Comodín" en Java. Le dice al sistema: "Esta respuesta puede devolver cualquier tipo de dato". A veces devolverás un texto como `"Usuario registrado"`, y a veces un objeto completo con el perfil. Usar `<?>` te permite cambiar el tipo de dato que devuelves en tiempo real dependiendo de si fue un éxito (`ResponseEntity.ok(objeto)`) o un error (`ResponseEntity.status(400).body("Faltan datos")`).

## ¿Qué son CustomNamingStrategy y JpaConfig?
Spring Boot y JPA por defecto tienen la mala costumbre de cambiar los nombres de las variables que usas en Java (`camelCase`, ejemplo: `apellidoPaterno`) a formato de base de datos con guiones bajos (`snake_case`, ejemplo: `apellido_paterno`).
* **CustomNamingStrategy:** Tiene mucho código porque su función es "regañar" a Spring y apagar ese comportamiento. Le dice a la base de datos: "Respeta los nombres exactamente como yo los escribí en mis clases, no les pongas guiones bajos".
* **JpaConfig:** Está vacía pero tiene un `@Configuration`. A veces se usa para configurar Beans manuales de base de datos, pero como no tiene nada escrito adentro, no hace nada útil. **Puedes borrar JpaConfig** si quieres limpiar tu proyecto, no pasará nada.

## ¿Cómo funciona SecurityConfig y se puede borrar?
`SecurityConfig` es el filtro de seguridad de tu microservicio, aunque ahora mismo lo tienes configurado de una manera muy relajada.
* **¿Qué hace?** Apaga una configuración llamada CSRF (que estorba cuando usas tokens JWT) y le dice a Spring que deje pasar las peticiones a `/api/auth/**` sin pedir token. El resto de rutas también las está dejando pasar temporalmente por tu configuración `.anyRequest().permitAll()`.
* **¿Se puede quitar?** **NO LA BORRES.** Aunque las rutas estén públicas, adentro de esa clase está guardado el `@Bean` del `PasswordEncoder` (la herramienta que encripta las contraseñas con BCrypt). Si borras este archivo, tu servicio de inicio de sesión (`AuthService`) marcará un error crítico porque no sabrá cómo encriptar ni comparar contraseñas.

## ¿Por qué los Repository están casi todos vacíos?
Esa es la magia de **Spring Data JPA**. Al hacer que tus interfaces hereden de `JpaRepository`, Spring automáticamente "escribe" por ti el código de las operaciones básicas. No necesitas programar métodos para guardar, actualizar, buscar por ID o borrar; JPA te los regala. 
A diferencia de MyBatis (el que usas en Estacionamiento donde debes escribir tú el SQL), aquí solo basta con declarar nombres de funciones como `existsByCorreo()` y JPA adivina cómo hacer la consulta SQL basándose solo en el nombre del método.

## ¿Para qué funciona la capa de Service?
La capa de Service es el "Cerebro" del sistema donde van todas las reglas de negocio.
* El **Controller** solo es el mesero: toma la petición de Postman y la entrega al servicio.
* El **Repository** solo es el bodeguero: guarda datos o los lee de la base de datos sin preguntar nada.
* El **Service** hace el trabajo sucio: Valida que los datos no vengan vacíos, revisa que el usuario no exista ya, aplica la seguridad BOLA y lanza errores si algo está mal. Esto se hace para que el Controller quede muy pequeñito y limpio, y toda la lógica difícil se encapsule en el Service.
