EJERCICIO DE AUTOMATIZACION E2E Y API - DEMOBLAZE

1. Requisitos

- Java 11 o superior.
- Maven 3.8 o superior.
- Google Chrome para la prueba UI.
- Acceso de red a https://www.demoblaze.com/ y https://api.demoblaze.com/.

2. Ejecutar toda la suite

Desde la carpeta del proyecto:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite all

3. Ejecutar las pruebas E2E con Serenity

Ejecutar la compra:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui

Ejecutar en modo headless:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Headless

Ejecutar por tag:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@e2e' -Headless
    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@purchase' -Headless
    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@e2e and @purchase' -Headless

El runner utiliza @e2e como tag predeterminado. El parametro -Tag permite ejecutar otro tag
o una expresion de tags de Cucumber.

4. Ejecutar las pruebas API con Karate

La suite API valida cuatro casos: crear un usuario nuevo, intentar crear un usuario existente,
iniciar sesion con credenciales validas e iniciar sesion con una contrasena incorrecta.

Ejecutar Signup y Login:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api

Ejecutar solo Signup:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@signup'

Ejecutar solo Login:

    powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@login'

5. Script portable

El script run-tests.ps1 configura el JDK y Maven incluidos en la carpeta tools. Puede usarse
cuando los comandos java o mvn no estan configurados en el PATH de Windows.

6. Reportes

- Serenity: target/site/serenity/
- Resultados de pruebas: target/surefire-reports/
- Reportes Karate: target/karate-reports/

7. Estructura

La implementacion utiliza el paquete base com.demoblaze.automationtest, con tareas, preguntas,
interacciones, userinterfaces, glue, hooks y runners separados.

8. Datos

Los datos utilizados son de prueba.
