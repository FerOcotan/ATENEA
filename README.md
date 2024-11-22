ATENEA - Sistema de Asistencia Educativa
Descripción
ATENEA es una aplicación móvil que permite gestionar la asistencia de estudiantes en instituciones educativas de manera rápida, sencilla y segura utilizando tecnología de códigos QR.

🚀 Funcionalidades
Registro de asistencia en tiempo real mediante escaneo de QR.
Acceso rápido a reportes de asistencia.
Interfaz intuitiva y adaptable a las necesidades del entorno educativo.

📋 Requisitos Previos
Firebase: Configura un proyecto en Firebase y habilita los servicios necesarios (Authentication, Firestore, etc.).
SHA-1 y SHA-256: agregar las huellas digitales de tu aplicación al proyecto de Firebase:

En la consola de Firebase -> Configuración del proyecto -> Integridad de la aplicación.
Agregar las claves SHA-1 y SHA-256 generadas desde el archivo build.gradle del proyecto.
Archivo google-services.json:

Este archivo contiene las credenciales del proyecto Firebase y es necesario para ejecutar la API de Google.
Descárgalo desde la consola de Firebase y colócalo en la carpeta app/ de tu proyecto.

Descargar el archivo google-services.json y ubícar en app/.
Sincronizar las dependencias:
Abrir el proyecto en Android Studio y sincronizar el archivo build.gradle para instalar las dependencias necesarias.

🛠️ Uso
Ejecutar la aplicación en un emulador o dispositivo físico.
Registrar la asistencia escaneando los códigos QR, brindados por la administracion.
Instrucciones de como utilizar!

A travez del menu inferior dirígete a la pestaña añadir tarea
![firstep](https://github.com/user-attachments/assets/c7ecfc3f-f102-493b-a1c9-220ad70ece06)


Luego rellena los campos, que te solicitan para crear una materia, estos datos seran mas util adelante para validacionees.

![firstep1_1](https://github.com/user-attachments/assets/99ec52a2-edb4-4d44-a8fb-27143abaf9ea)
![firstsetp1_3](https://github.com/user-attachments/assets/f36e7adf-6428-49df-927e-fbb730647d5c)

Ahora a travez del menu inferior dirígete a la pestaña lista.
![secondstep](https://github.com/user-attachments/assets/255d547f-5099-4853-9876-c25ff0376c87)

Selecciona los campos, que se solicitan para crear una lista, en el primero deberas escoger la universidad en la que impartes la materia y en el segundo se muestran tus materias creadas anteriormente.

![secondstep1_1](https://github.com/user-attachments/assets/8d6030fa-08e7-4f99-90ee-1e07a2432ee3)


En ambas pestañas, bajo el boton de guardar esta otro boton este nos sirve para verificar las listas creadas o materias creadas y corroborar que todo esta bien.
![terceruno](https://github.com/user-attachments/assets/86555d09-02cd-42ae-9d9d-71c364e7b5d5)
![tece](https://github.com/user-attachments/assets/b61debb5-8551-4827-ae61-145c2dc21289)

>Ahora a travez del menu inferior dirígete al boton flotante que se encuentra en medio de la pantalla.
![cuartostep1](https://github.com/user-attachments/assets/86e40ba5-0ba7-4344-9fff-ec8d51297578)
>

Para registrar la asistencia presiona sobre el icono blanco de la camara, esto abrira tu camara y te permitira capturar la asistencia.

![cuartopaso](https://github.com/user-attachments/assets/fb2b5737-d891-4e00-9487-14d9e85cf60a)


Una vez que abras tu camara acerca a la camara el codigo QR, posteriormente te saldra un cuadro de dialogo solicitando que selecciones la lista donde quieres almacenar la asistencia.
¡Nota! Existen varias validaciones sobre capturar la asistencia, primero no se puede registrar la misma asistencia en la misma lista, en un plazo de 1 hora.
Segunda validacion: la lista sumara 1 cada vez que el estudiante este presente

![cuarto3](https://github.com/user-attachments/assets/aa8f8ac9-02c3-40f6-a697-68ac4a326fb4)


![cuarto2](https://github.com/user-attachments/assets/cb9f5ebf-e041-4f1e-a72b-7e3ab8333515)


Utilizando el menu inferior clic en la pestaña reportes, aqui veras que se despliega la lista de asistencias creadas una vez scaneado algun codigo QR dentro de la lista podras descargarla presionanando el boton descargar


![quinto1](https://github.com/user-attachments/assets/37cd4c75-93fa-4894-8ef2-8baebdce86cd)
![quinto2](https://github.com/user-attachments/assets/345acd00-03d5-4675-bb78-b1bfac316327)
