# Microservicio de Estacionamiento

## Que es MyBatis
MyBatis es un framework de persistencia (una herramienta para conectarse a la base de datos). A diferencia de otros frameworks que intentan esconder el SQL por completo, MyBatis te permite escribir tus propias sentencias SQL directamente y luego mapea (convierte) automáticamente los resultados de esas consultas de la base de datos a objetos de Java.

## Que es MapperScan y el metodo tradicional
MapperScan es una anotacion que le dice a Spring Boot "busca en esta carpeta todas las interfaces que sean mappers de MyBatis y preparalas automaticamente". 
El metodo tradicional, sin usar MapperScan, seria registrar cada mapper uno por uno manualmente en un archivo XML de configuracion o programando una clase por cada mapper, lo cual es muy tardado y repetitivo.

## Que es un Bean y que hace MyBatisConfig
Un Bean en Spring es simplemente un objeto de Java que es creado y administrado por el propio Spring. En lugar de que tu escribas "new Objeto()" en el codigo, Spring lo crea al arrancar la aplicacion y te lo da listo para usar cuando lo pidas.
MyBatisConfig es una clase que usamos para crear manualmente los Beans que MyBatis necesita para funcionar (como el SqlSessionFactory, que es la fabrica de conexiones a la base de datos). Lo hicimos asi a mano para evitar un choque que habia con la otra tecnologia que usan los otros microservicios (Spring Data JPA).

## Que es Jakarta
Jakarta (antes conocido como Java EE) es un conjunto de reglas y herramientas estandar para hacer aplicaciones en Java. Cuando ves importaciones que dicen "jakarta.persistence", significa que estas usando esas herramientas estandar para decirle a Java como funcionan ciertas cosas, como las anotaciones de las bases de datos o validaciones.

## El @Mapper en los Repository
Es exactamente lo que leiste. Sirve para hacer peticiones SQL muy facil. Cuando le pones @Mapper a una interface, le estas diciendo a MyBatis: "No te voy a escribir la logica de esta funcion, solo le voy a poner la anotacion @Select('SELECT * FROM tabla') arriba, y tu te encargas de ejecutar ese SQL en la base de datos automaticamente".

## Como funciona JwtUtils y el jwtSecret
En este fragmento de codigo:
* El @Value busca el texto secreto que guardaste en tu archivo application.properties y lo mete en la variable jwtSecret.
* La funcion getSigningKey() toma ese texto secreto (que esta codificado en un formato llamado Base64), lo decodifica y lo convierte en una "Llave Criptografica" real para Java (eso hace el Keys.hmacShaKeyFor). Esa llave se usa como si fuera un sello oficial inviolable.

## Porque es un @Component y que es
Ponerle @Component a una clase le dice a Spring: "Crea una instancia de esta clase (un Bean) y guardala en tu memoria". Esto sirve para que JwtUtils exista una sola vez en toda la aplicacion y cualquier otro archivo (como los servicios o controladores) lo pueda pedir prestado para usar sus funciones sin tener que crearlo desde cero cada vez.

## Como se firma el token y se regresa
Cuando un usuario pone bien su contrasena, el sistema toma sus datos (nombre, rol, id) y usa la llave criptografica (la de getSigningKey) para "sellar" esa informacion. El resultado es el Token JWT (esa cadena larga de texto). Luego, el servidor mete ese token en un formato JSON y lo envia como respuesta al cliente (Postman) con un estado 200 OK.

## La capa de servicio y el ciclo For
La capa de servicio es donde pones las "reglas de negocio" (todos los ifs, validaciones y calculos) antes de guardar algo en la base de datos.
Sobre tu duda del codigo:
for (Map<String, Object> v : vehiclesList)

Eso **NO** es un operador ternario. Se llama "For-Each" (Por cada uno). Es la forma mas recomendada y estandar en Java para recorrer listas. Se lee literalmente asi: "Por cada vehiculo (v) que haya dentro de la lista (vehiclesList), ejecuta el bloque de codigo".

Si se te hace raro de leer o prefieres la forma clasica de la escuela con la variable "i" que cuenta desde cero, el codigo seria exactamente este:

for (int i = 0; i < vehiclesList.size(); i++) {
    Map<String, Object> v = vehiclesList.get(i);
    Integer vid = (Integer) v.get("idVehiculo");
    if (vid != null) {
        if (movimientoRepository.contarMovimientosActivosPorVehiculo(vid) > 0) {
            activeInside++;
        }
    }
}

El For-Each de los dos puntos (v : vehiclesList) es en realidad para no escribir tanto, pero ambas formas hacen exactamente lo mismo, puedes usar la que te parezca mas intuitiva.
