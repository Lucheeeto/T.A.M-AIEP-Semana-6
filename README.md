# Aplicación de Distribución de Alimentos - Firebase & GPS

## Descripción
Esta es una aplicación Android desarrollada en Java para un sistema de distribución de alimentos. La app permite a los usuarios registrarse, iniciar sesión y acceder a diversas funcionalidades como cálculo de costos de despacho, conversión de ángulos y monitoreo de temperatura.

## Características
- Autenticación de usuarios con Firebase (registro e inicio de sesión).
- Validación de entradas en todas las secciones.
- Conversión de ángulos a radianes.
- Resumen de datos de vehículos.
- Cálculo de costos de despacho basado en reglas de negocio.
- Verificación de alarmas de temperatura para la cadena de frío.
- Obtención de la ubicación GPS con permisos dinámicos.

## Tecnologías Utilizadas
- Lenguaje de programación: Java
- Entorno de desarrollo: Android Studio
- Autenticación y base de datos: Firebase (Authentication y Realtime Database)
- Ubicación: FusedLocationProviderClient de Google Play Services

## Instalación y Configuración
### Prerrequisitos
- Android Studio (versión recomendada: Arctic Fox o superior)
- Dispositivo Android o emulador con API nivel 26 (Android Oreo) o superior
- Cuenta en [Firebase Console](https://console.firebase.google.com/)

### Pasos para configurar el proyecto
1. Clona el repositorio:
git clone https://github.com/Lucheeeto/T.A.M-AIEP-Semana-6.git

2. Abre el proyecto en Android Studio.
3. Configura Firebase:
- Crea un nuevo proyecto en Firebase Console.
- Añade una aplicación Android y proporciona el nombre del paquete (package name) de la aplicación.
- Descarga el archivo `google-services.json` y colócalo en el directorio `app/` del proyecto.
4. Sincroniza el proyecto con los archivos de Gradle.
5. Ejecuta la aplicación en un dispositivo o emulador.

## Uso de la Aplicación
### Login y Registro
- Al abrir la aplicación, se presenta una pantalla de login donde los usuarios pueden ingresar con su correo y contraseña.
- Si no tienen una cuenta, pueden registrarse mediante el botón correspondiente.

### Pantalla Principal (MainActivity)
Después del login, se accede a la pantalla principal que contiene las siguientes secciones:

- **Conversión de ángulos a radianes:** Ingresa un valor en grados y conviértelo a radianes.
- **Resumen de datos de vehículos:** Ingresa marca, modelo y otros datos del vehículo para obtener un resumen.
- **Cálculo de despacho:** Introduce el monto de compra y la distancia para calcular el costo de envío según las reglas de negocio.
- **Verificación de alarmas de temperatura:** Ingresa la temperatura actual del congelador para verificar si está dentro de los límites.
- **Ubicación GPS:** Presiona el botón para obtener la ubicación actual del dispositivo.

### Reglas de Negocio para el Cálculo de Despacho
- Compra superior a $50.000: despacho gratuito dentro de un radio de 20 km.
- Compra entre $25.000 y $49.999: se cobra $150 por kilómetro.
- Compra inferior a $25.000: se cobra $300 por kilómetro.

## Desarrollo del Proyecto
### Configuración de Firebase
Se integró Firebase al proyecto descargando el archivo `google-services.json` y configurando las dependencias en `build.gradle.kts`.

### Diseño de Interfaces
Se implementaron las actividades:
- `LoginActivity`: para autenticación.
- `RegisterActivity`: para registro de nuevos usuarios.
- `MainActivity`: con las funcionalidades principales.

### Implementación de Lógica
- **FirebaseAuth:** para gestionar el registro e inicio de sesión.
- **Validaciones:** se utilizaron expresiones regulares y manejo de excepciones para validar los datos de entrada.
- **GPS:** se empleó `FusedLocationProviderClient` para obtener la ubicación, solicitando permisos en tiempo de ejecución.

### Pruebas
Se realizaron pruebas en emulador y dispositivo físico, verificando:
- Las validaciones de entrada.
- El correcto funcionamiento de la autenticación.
- La obtención y guardado de la ubicación en Firebase Realtime Database.

## Equipo de Desarrollo
- Luis Ojeda - Lógica de negocio y validaciones
- Ignacio Nuñez- Diseño de interfaces y GPS
- Lucas Escobedo - Desarrollo y integración Firebase

## Estado del Proyecto
Completado - Todas las funcionalidades implementadas y testeadas

##Aprendizajes Adquiridos
- Integración completa con Firebase Services
- Manejo de permisos en tiempo de ejecución
- Validación robusta de datos de entrada
- Trabajo colaborativo con control de versiones
- Desarrollo de aplicaciones Android nativas en Java

## Conclusiones
El desarrollo de esta aplicación permitió consolidar los conocimientos en integración de Firebase, manejo de sensores del dispositivo y validación de datos. Se logró crear una aplicación funcional y segura, que cumple con los objetivos planteados. El trabajo en equipo fue fundamental para abordar cada fase del proyecto y resolver los desafíos técnicos que surgieron.

## Referencias
- AIEP (2025). Apunte Semana 6 – Taller de Aplicaciones Móviles.
- Android Developers. (s. f.). Location services. https://developer.android.com/training/location
- Firebase Docs. (s. f.). Authentication for Android. https://firebase.google.com/docs/auth
