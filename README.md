# API de Gestión de Pedidos

API REST para el procesamiento masivo de pedidos enviados en archivos CSV. El procesamiento lo realiza por lotes, valida los pedidos, persiste los pedidos válidos y en retorna respuestas en formato estandarizado con información de la carga y errores.

---

## Requisitos Previos

Para ejecutar este proyecto localmente se debe tener:
* **Java 17+**
* **PostgreSQL 18.3+**: ejecutándose en el puerto 5432.

---

## Instrucciones de Ejecución Local

**1. Preparar la Base de Datos:**
Abrir gestor de base de datos y ejecutar el comando para crear una base de datos vacía:
`CREATE DATABASE db_pedidos;`

**2. Configurar Credenciales:**
Abrir el archivo `src/main/resources/application.yaml` y verificar que el usuario y la contraseña de la base de datos coincidan con la instalación local de PostgreSQL.

**3. Compilar y Ejecutar:**
Abrir una terminal en la raíz del proyecto y ejecutar los siguientes comandos dependiendo del sistema operativo.

**Para Windows:**
`mvnw clean install`
`mvnw spring-boot:run`

**Para Linux / macOS:**
`./mvnw clean install`
`./mvnw spring-boot:run`

---

## Instrucciones de Prueba

Para interactuar con la API se puede usar Postman o Swagger:

### Usando Postman
En la carpeta `/postman` de este repositorio se encuentra la colección a importar. Cada solicitud tiene el token de autorización y su propio idempotency key. En la carpeta `/samples` se encuentran los archivos CSV con los pedidos para las pruebas.
1. Importar el archivo `pedidos.postman_collection.json` en Postman.
2. Abrir `1 Carga inicial exitosa`, en la pestaña body seleccionar el archivo `carga_inicial.csv` y ejecutar. 
3. Abrir `2 Carga duplicada`, en la pestaña body seleccionar el archivo `carga_inicial.csv` y ejecutar.
4. Abrir `3 Carga cliente no encontrado`, en la pestaña body seleccionar el archivo `carga_cliente_no_encontrado.csv` y ejecutar.
5. Abrir `4 Carga zona no encontrada`, en la pestaña body seleccionar el archivo `carga_zona_no_existente.csv` y ejecutar.
6. Abrir `5 Carga zona no soporta refrigeracion`, en la pestaña body seleccionar el archivo `carga_zona_no_soporta_refrigeracion.csv` y ejecutar.
7. Abrir `6 Carga fecha pasada`, en la pestaña body seleccionar el archivo `carga_fecha_pasada.csv` y ejecutar.
8. Abrir `7 Carga estado invalido`, en la pestaña body seleccionar el archivo `carga_estado_invalido.csv` y ejecutar.

### Usando Swagger UI
1. Con la aplicación corriendo, abre tu navegador en: **http://localhost:8080/swagger-ui.html**
2. Haz clic en el botón verde **"Authorize"** en la parte superior derecha.
3. Ingresar el token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvIiwibmFtZSI6ImFkbWluIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxOTAwMDAwMDAwfQ.3YBrqlsrbcLwxjlKGvMWyDC5QOilcl9nbM5siJFXVCA
4. Desplegar el endpoint `POST /pedidos/cargar`, hacer clic en "Try it out", ingresar una llave única en `Idempotency-Key` y adjuntar un archivo `.csv`.


---

## Decisiones de Diseño

* El proyecto se realizó utilizando una arquitectura hexagonal con tres partes bien definidas: dominio, aplicación e infraestructura. 
* El dominio y la aplicación solo contienen java puro sin depender de frameworks como spring. 
* El dominio define las entidades, los agregados y el servicio de validación de pedidos. 
* La aplicación define los puertos de entrada y salida, implementa el puerto de entrada de carga de pedidos como un servicio de orquestración. 
* La infraestructura utiliza spring boot y otras dependencias. Define los adaptadores qe salida, los cuales implementan los puertos de salida e implementa el controlador. También se encuentra en la infraestructura configuraciones, entidades, repositorios, mappers entre otros.

---

## Estrategia de Batch

Para realizar el procesamiento por lotes se sigue el siguiente proceso:

1. Se lee línea por línea el archivo csv
2. Se convierte cada línea a un objeto de tipo Pedido y se agrega a una lista
3. Cuando el tamaño de la lista es el del lote se obtienen los ids de cliente, los ids de zona y se realizan dos sconsultas a la base de datos para validar que los clientes y las zonas existan.
4. Se filtra la lista para eliminar pedidos con cliente o zona inválida y zona que no soporta refrigeración si el pedido require refrigeración.
5. Finalmente la lista de pedidos validos se envía en una sola consulta a la base de datos.
6. Se limpian las listas de la memoria y pasa a procesar el siguiente lote.

---

## Supuestos
Para que una solicitud sea exitosa se asume:
* El archivo CSV debe tener el formato `numeroPedido,clienteId,fechaEntrega,estado,zonaEntrega,requiereRefrigeracion` y estar separado por comas.
* Cada solicitud debe enviar un idempotency key único.
* Cada solicitud debe utilizar el token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvIiwibmFtZSI6ImFkbWluIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxOTAwMDAwMDAwfQ.3YBrqlsrbcLwxjlKGvMWyDC5QOilcl9nbM5siJFXVCA
* La base de datos pedidos_db exista en el entorno local.
* Si se utiliza los archivos de postman, se deben ejecutar en orden y se debe cargar el archivo CSV correcto de la carpeta samples.

---

## Límites Conocidos

1. La autorización actualmente se realiza con OAuth pero está configurada para aceptar un único token, para usarse un servidor de autorización real se tendría que modificar la configuración.
