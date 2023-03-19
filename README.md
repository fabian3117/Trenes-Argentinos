# Trenes-Argentinos.
Olá, aqui fede👋 - Hi I'm fede.
### Descripcion del proyecto

Conciste en una copia modificacion de la actual aplicacion Trenes argentinos :train2: del ministerio de transporte de la nacion argentina.
Se añadiran caracteristicas que considero mas acertadas como usuario de la misma.

### Herramientas utilizadas :computer: 
 
 - Java. _Lenguaje_
 - Android studio. _IDE_
 - Jadx. _Descompilador_
 - Retrofit
 
 ### Compontentes android utilizados
 - GPS.
 - Retrofit.
 - SharedPreference.
 - MaterialDesign.
 
 
 ### Retos
 
 Los retos consistieron en ver como la aplicacion original consulta las api para poder saber que archivos consultar.
 La obtencion de funciones que se encargan de generacion usuario - clave para la obtencion de un token del servidor.
 Luego de lo cual fue obtener detalles y documentacion de la forma de interactuar utilizando retrofit.
 De la misma forma fue un reto ordenar los datos puesto que desde mi lado eran json con poca relacion entre si.
 Generacion de una primera version funcional donde obtenga correctamente el token de habilitacion , y el estado de los servicios.
 ### Diferencias a la aplicacion original
 
 -Integracion con google assistant. _Lo cual permitiria realizar comandos como "Ok google En cuanto viene el tren a Once"_.
 
 -Integracion con smartwatch. _En Actuando el telefono como proveedor de datos para el mismo a fin de mantener una Caratural/WatchFace/widget con los datos de un servicio especifico_.
 
 -Simplicidad en su utilizacion.
 
 ### Diagramas
 
 ## 🚇Pantalla de inicio
 Esta sera la pantalla de inicio donde se vera toda la informacion pertinente
 <p>
 <img src="https://raw.githubusercontent.com/fabian3117/Trenes-Argentinos/main/Princi.jpg"width="400" height="400" </img>
 </p>
 <br>
 
 ## 🛠️Caracteristicas.
 - Disponemos de 2 elementos botones con estilo _Chip_ los cuales mostraran 2 estaciones favoritas asociadas a cada ramal.
 - Una vez tocado el ramal obtendremos toda la informacion relevante sin tener que pasar a un nuevo activity como es el caso de la aplicacion oficial.
 - Donde se indica ESTADO SERVICIO se mostrada un detalle sobre el mismo para no requerir ir a una activity distinta para obtener la informacion.
 - En la parte inferior se podrá cambiar entre el _fragment_ de visualizacion de los ramales como se observa _En la captura_ y un apartado para configurar las alertas.
 ## Descripción de las alertas ‼️🚨.
 Para las alertas se podra configurar en funcion de un horario para a través de un servicio en segundo plano obtener informacion sobre mi ramal.
 Ejemplo:
 Salgo hacia la estacion de _merlo_ perteneciente al ramal _Sarmiento_ todos los dias a las 12:00🕛.
 Entonces podré configurar el servicio de alertas para que a través de un proceso en segundo plano el celular verifique utilizando la API el estado de dicho ramal y caso de haber alguna alerta _Demoras-Corte por X motivo-Problemas operativos_.
 🫡⚠️ Se notificará mediante una alerta al usuario.
 ## 😉Diagrama de funcionamiento de la aplicacion.
 ⚠️Este diagrama es un bosquejo y no representa la versión final de la aplicación, ni su estado actual de desarrollo.
 <p>
 <img src="https://raw.githubusercontent.com/fabian3117/Trenes-Argentinos/main/Diagram.jpg" </img>
 </p>
