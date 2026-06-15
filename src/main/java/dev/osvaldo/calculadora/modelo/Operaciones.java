package dev.osvaldo.calculadora.modelo;

/**
 * MODELO: contiene la logica de negocio pura.
 * No sabe nada de la interfaz grafica ni de los hilos.
 * Recibe numeros, devuelve resultados como String.
 */
public class Operaciones {

    public String sumar(int a, int b)       { return String.valueOf(a + b); }
    public String restar(int a, int b)      { return String.valueOf(a - b); }
    public String multiplicar(int a, int b) { return String.valueOf(a * b); }

    public String dividir(int a, int b) {
        if (b == 0) return "Error: division por cero";
        return String.valueOf(a / b);
    }
}
