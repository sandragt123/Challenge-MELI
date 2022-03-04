# PROYECTO MERCADO LIBRE

El microservicio dnaevaluator expone dos servicios rest.  Su estructura de carpetas consta de 3 capas, implementando así una arquitectura limpia donde la capa de dominio es independiente del framework

application: En esta capa se encuentra el controller(MutantController) que es la puerta de entrada o adaptador primario, obtiene los datos para enviarlos a la capa de dominio.

domain: En ésta se encuentra la clase que contiene la lógica de negocio (MutantService), los modelos y una interfaz que se inyecta a través del constructor para posteriormente comúnicarse con la capa de infraestructura.

infrastructure: En ésta se encuentra la clase que se encarga de transformar los modelos de negocio en DTOs(ResultMutantService) para luego hacer los procesamientos en base de datos y entregar respuesta a la capa de dominio.

#Especificaciones

Este proyecto fue desarrollado en el siguiente entorno:

Spring Boot versión 2.6.4

Java versión 1.8.0_241

Gradle versión 7.4

Base de datos  MySQL versión 5.7 (Google Cloud)

IDE de desarrollo: IntelliJ IDEA Community Edition 2021.3.2

El microservicio se encuentra desplegado en Google App Engine

#SERVICIOS REST

# url: https://dnaevaluator.rj.r.appspot.com/api/mutant
Descripción: Se encarga de evaluar si un ADN ingresado pertenece a un humano o mutante, a su vez guarda en base de datos el resultado.
Si se consulta nuevamente el ADN éste recupera la información de la BD.

Validaciones: Se valida el ADN ingresado. éste debe  cumplir con la regla de negocio especificada: "En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G),"

parámetros: String [] dna

método: POST

Content-Type: application/json

Posibles respuestas:

STATUS 200: Cuando el ADN ingresado es mutante(más de una secuencia de cuatro letras iguales)

STATUS 403: Cuando el ADN ingresado es humano.

STATUS 400: Cuando la secuencia ingresada no cumple con las especificaciones de la regla de negocio.


# url: https://dnaevaluator.rj.r.appspot.com/api/stats
Descripción: Retorna en un objeto tipo json las estadísticas según la cantidad de humanos y mutantes evaluados, esta información se recupera de la base de datos

parámetros: No recibe

método: GET

respuesta:

STATUS 200

Content-Type: application/json

{
"count_mutant_dna": 5,
"count_human_dna": 8,
"ratio": 0.63
}

#COBERTURA DE PRUEBAS

El porcentaje total de lineas de código cubieras con pruebas initarias es del 88%














