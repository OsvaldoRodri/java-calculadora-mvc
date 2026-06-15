# java-calculadora-mvc

Calculadora de escritorio en Java con arquitectura MVC y operaciones concurrentes usando SwingWorker.

## Arquitectura

```
src/main/java/dev/osvaldo/calculadora/
├── Main.java
├── modelo/Operaciones.java      lógica de cálculo, sin dependencias de UI
├── vista/Formulario.java        interfaz Swing, sin lógica de negocio
└── controlador/Controlador.java coordina modelo y vista, maneja eventos
```

El controlador implementa `ActionListener` y es el único punto de contacto entre la vista y el modelo.

## Concurrencia con SwingWorker

El botón "Calcular Todo" lanza cuatro `SwingWorker` en paralelo. En Swing, las operaciones pesadas no pueden correr en el Event Dispatch Thread sin congelar la interfaz. Cada worker ejecuta el cálculo en `doInBackground()`, publica un mensaje intermedio con `publish()` y actualiza el campo de resultado en `done()`, que corre en el EDT.

## Cómo correr

```bash
git clone https://github.com/OsvaldoRodri/java-calculadora-mvc.git
cd java-calculadora-mvc
mvn compile exec:java -Dexec.mainClass="dev.osvaldo.calculadora.Main"
```

Requisitos: Java 17, Maven.
