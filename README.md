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
 - Lottie.
 - Services with Handle & Runnable.
 
 ### Requisitos para ejecucion
 Para correr la aplicacion se requiere un SDK MIN>=28.
 dispositivos android con conexion a internet y GPS.
 
 
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
 Tambien se permitira obtener informacion sobre el proximo tren a estacion directamente desde la barra de notificaciones.
 Ejemplo:
 Salgo hacia la estacion de _merlo_ perteneciente al ramal _Sarmiento_ todos los dias a las 12:00🕛.
 Entonces podré configurar el servicio de alertas para que a través de un proceso en segundo plano el celular verifique utilizando la API el estado de dicho ramal y caso de haber alguna alerta _Demoras-Corte por X motivo-Problemas operativos_.
 🫡⚠️ Se notificará mediante una alerta al usuario.
 Ejemplo con lanzamiento del servicio de alertas de proximo tren a abordar en la estacion.
 ## Formato de lanzamiento del servicio.
 Atraves de la pantalla desde donde se visualizan las unidades proximas a la estacion.
 ![image](https://raw.githubusercontent.com/fabian3117/Trenes-Argentinos/main/EstacionVer.jpg)
 
 Podemos ver En el bosquejo como tengo mi listado de estaciones proximas a arribar y el icono flotante para lanzar el servicio de notificaciones.
 ### Muestra de implementacion
 ![image](https://raw.githubusercontent.com/fabian3117/Trenes-Argentinos/main/ScreenEstacionVer.jpg).
 ### Indicativos de servicio Lanzado.
![image](https://user-images.githubusercontent.com/34560661/227729576-520bb89f-c650-4473-a7bf-de27b1f866e7.png)
 ### Notificacion.
 ![image](https://user-images.githubusercontent.com/34560661/227729609-0572f242-7f77-4843-8ca3-d31f7682cfc3.png)
### Forma alternativa.
Como puede observarse se tiene un elemento llamado __Crear acceso directo__.
El cual nos permite iniciar el servicio de la siguente forma.
Desde la pantalla de inicio tenemos un icono que lanza el servicio para mantener al usuario informado.
![image](https://user-images.githubusercontent.com/34560661/227729681-709369be-8cd4-4243-bcdd-1b002d140a88.png)

Esto lanza el intent que contiene la informacion de la estacion(ID) dentro de un bundle para su posterior captacion.
## Algoritmo Las notificaciones.
![AlgoirtmoServic](https://user-images.githubusercontent.com/34560661/227729724-a4d922fe-cf41-4fcb-a81e-0f2241f067ca.jpg)



 ## 😉Diagrama de funcionamiento de la aplicacion.
 ⚠️Este diagrama es un bosquejo y no representa la versión final de la aplicación, ni su estado actual de desarrollo.
 <p>
 <img src="https://raw.githubusercontent.com/fabian3117/Trenes-Argentinos/main/Diagram.jpg" </img>
 </p>
