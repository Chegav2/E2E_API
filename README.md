# Demoblaze: automatizacion E2E y API

Proyecto de pruebas automatizadas para la tienda Demoblaze. Contiene una suite UI con Serenity BDD y una suite API con Karate.

## Requisitos

- Java 11 o superior.
- Maven 3.8 o superior.
- Chrome instalado para la suite UI.
- Acceso de red a Demoblaze.

El proyecto incluye un JDK y Maven portables en la carpeta `tools`.

## Como ejecutar

## Configuracion inicial

Antes de ejecutar cualquier prueba por primera vez:

1. Abrir en VS Code la carpeta `serenitybdd-demoblaze-testing-e2e`.
2. Verificar que existan las carpetas `tools\jdk-17.0.21+9` y `tools\apache-maven-3.10.1`.
3. Tener acceso a internet para que Maven descargue las dependencias del proyecto.
4. Ejecutar la API para comprobar la configuracion:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api
```

La prueba API con Karate requiere Java, Maven y acceso a `https://api.demoblaze.com/`.
No requiere Chrome.

La prueba UI con Serenity requiere adicionalmente Google Chrome y acceso a
`https://www.demoblaze.com/`.

Abrir PowerShell en la carpeta del proyecto:

```powershell
cd C:\Users\ch_ga\Documents\TEST_E2E\serenitybdd-demoblaze-testing-e2e
```

Ejecutar toda la suite:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite all
```

Ejecutar las pruebas UI con Serenity:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui
```

Ejecutar UI en modo headless:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Headless
```

Ejecutar UI por tag:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@purchase' -Headless
```

Ejecutar las pruebas API con Karate:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api
```

Ejecutar solo Signup:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@signup'
```

Ejecutar solo Login:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@login'
```

La suite API valida cuatro casos: usuario nuevo, usuario existente, login correcto y login
con contrasena incorrecta. Los escenarios Outline consumen los datos de `src/test/resources/data/api/`
desde archivos CSV y JSON.

La prueba E2E tambien utiliza un Scenario Outline y consume el caso de compra desde
`src/test/resources/data/e2e/purchase_cases.csv`.

El script configura automáticamente el JDK y Maven portables cuando no estan disponibles en el PATH de Windows.

## Ejecucion desde los runners

Tambien se pueden ejecutar las pruebas desde los runners en VS Code.

Prerequisitos:

- Tener instalada la extension Extension Pack for Java.
- Abrir la carpeta del proyecto `serenitybdd-demoblaze-testing-e2e`.
- Tener configurado un JDK 11 o superior en VS Code.
- Tener Chrome instalado para ejecutar la prueba UI.
- Tener acceso de red a Demoblaze para ejecutar la UI y la API.
- Haber descargado las dependencias del proyecto mediante Maven.

Para ejecutar la prueba UI, abrir `PurchaseE2ERunner.java` y seleccionar `Run Test` sobre la clase o sobre el metodo de prueba.

Para ejecutar la API, abrir `KarateApiRunner.java` y seleccionar `Run Test` sobre `testRunner()`.

El boton `Run Java File` no debe utilizarse para estos archivos porque son runners de JUnit y no contienen un metodo `main`.

Desde el runner de Karate se ejecutan por defecto los escenarios con el tag `@api`. Para ejecutar un tag especifico se recomienda usar el script portable:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@signup'
```

## Tags configurados en los runners

### Runner UI: PurchaseE2ERunner

Archivo:

`src/test/java/com/demoblaze/automationtest/runners/PurchaseE2ERunner.java`

El tag predeterminado se configura dentro de la anotacion `@CucumberOptions`:

```java
@CucumberOptions(
    features = "src/test/resources/features/BEPMC",
    glue = "com.demoblaze.automationtest.glue",
    tags = "@e2e"
)
```

El tag tambien aparece en el feature:

`src/test/resources/features/BEPMC/purchase_e2e.feature`

```gherkin
@e2e @purchase @demoblaze
Scenario Outline: Compra exitosa de dos productos
```

Para ejecutar por tag desde PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@e2e' -Headless
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite ui -Tag '@purchase' -Headless
```

### Runner API: KarateApiRunner

Archivo:

`src/test/java/com/demoblaze/automationtest/runners/KarateApiRunner.java`

El tag predeterminado se obtiene en esta linea:

```java
String tag = System.getProperty("karate.filter.tags", "@api");
```

Si no se envia ningun tag, el runner ejecuta `@api`. Los tags del feature API estan en:

`src/test/resources/karate/demoblaze_auth.feature`

```gherkin
@api @demoblaze
Feature: Demoblaze authentication API
```

Los escenarios tienen tags especificos:

```gherkin
@signup @new-user
@signup @existing-user
@login @valid-credentials
@login @invalid-credentials
```

Para ejecutar la API completa:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api
```

Para ejecutar un tag especifico:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@signup'
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@login'
powershell -ExecutionPolicy Bypass -File .\run-tests.ps1 -Suite api -Tag '@invalid-credentials'
```

## Estructura

```text
src/main/java/com/demoblaze/automationtest/
  model/
  util/
    constants/
    execution/
    overwritedata/
    dbconection/
  exceptions/
  tasks/
  questions/
  interactions/
  userinterfaces/

src/test/java/com/demoblaze/automationtest/
  glue/
  hooks/
  runners/
  util/

src/test/resources/
  features/BEPMC/
  data/api/
  data/e2e/
  data/manualtest/
  assets/
  properties/
  karate/
```

## Reportes

- Serenity: `target/site/serenity/index.html`
- Resultados JUnit: `target/surefire-reports/`
- Cucumber UI: `target/cucumber-reports/cucumber.json`
- Karate: `target/karate-reports/karate-summary.html`

## Documentacion

- [Arquetipo maestro](ARQUETIPO_MAESTRO_DEMOBLAZE_TIENDA.md)
- [Instrucciones paso a paso](readme.txt)
- [Conclusiones](conclusiones.txt)
