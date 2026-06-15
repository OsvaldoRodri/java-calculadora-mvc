# Calculadora MVC con Concurrencia — Java Swing 🧮

Calculadora de escritorio que demuestra **arquitectura MVC** y **operaciones concurrentes** con `SwingWorker` en Java.

## Arquitectura MVC

```
src/main/java/dev/osvaldo/calculadora/
├── Main.java                    ← Punto de entrada, conecta las capas
├── modelo/
│   └── Operaciones.java         ← Lógica de cálculo (sin UI, sin hilos)
├── vista/
│   └── Formulario.java          ← Interfaz gráfica Swing (sin lógica)
└── controlador/
    └── Controlador.java         ← Coordina modelo y vista, gestiona eventos
```

**Flujo:**
```
[Usuario hace clic en botón]
        ↓
[Vista notifica al Controlador via ActionListener]
        ↓
[Controlador llama al Modelo para calcular]
        ↓
[Modelo devuelve resultado]
        ↓
[Controlador actualiza la Vista]
```

## Funcionalidades

### Operaciones simples (inmediatas)
Sumar, Restar, Multiplicar, Dividir — resultado instantáneo en el hilo de UI.

### "Calcular Todo" (concurrente con SwingWorker)
Lanza **4 SwingWorkers en paralelo**, uno por operación. Cada uno:
1. Muestra "Calculando..." mientras trabaja en hilo background
2. Simula una operación costosa con `Thread.sleep()`
3. Actualiza su campo en la UI al terminar (via `process()` / `done()`)

La UI **nunca se congela** porque los cálculos corren fuera del Event Dispatch Thread (EDT).

```java
private void lanzarWorker(String tipo, int a, int b, int delayMs) {
    new SwingWorker<String, String>() {
        @Override
        protected String doInBackground() throws InterruptedException {
            publish("Calculando " + tipo + "...");
            Thread.sleep(delayMs);   // simula trabajo pesado
            return calcular(tipo, a, b);
        }
        @Override
        protected void done() {
            actualizarCampo(tipo, get());  // actualiza UI en EDT
        }
    }.execute();
}
```

## Por qué SwingWorker y no Thread directo

En aplicaciones Swing, **toda actualización de UI debe hacerse en el EDT** (Event Dispatch Thread). Si haces `Thread.sleep()` directo en un `ActionListener`, la interfaz se congela.

`SwingWorker` separa el trabajo en dos contextos:
- `doInBackground()` → corre en hilo worker (no bloquea UI)
- `process()` / `done()` → corre en EDT (seguro para actualizar UI)

## Cómo correrlo

```bash
git clone https://github.com/OsvaldoRodri/java-calculadora-mvc.git
cd java-calculadora-mvc
mvn compile exec:java -Dexec.mainClass="dev.osvaldo.calculadora.Main"
```

**Requisitos:** Java 17+, Maven 3.x

## Conceptos demostrados

| Concepto | Implementación |
|----------|---------------|
| Patrón MVC | 3 capas separadas: modelo, vista, controlador |
| `ActionListener` | Controlador escucha eventos de la Vista |
| `SwingWorker` | Operaciones pesadas sin bloquear la UI |
| `doInBackground()` | Cálculo en hilo worker |
| `process()` / `done()` | Actualización segura de UI en EDT |
| Manejo de excepciones | `NumberFormatException`, división por cero |

## Tecnologías

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Swing](https://img.shields.io/badge/UI-Java_Swing-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apache-maven)

---

*Parte del portafolio de [Osvaldo Rodríguez](https://github.com/OsvaldoRodri)*
