package dev.osvaldo.calculadora;

import dev.osvaldo.calculadora.controlador.Controlador;
import dev.osvaldo.calculadora.modelo.Operaciones;
import dev.osvaldo.calculadora.vista.Formulario;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada. Crea las tres capas MVC y las conecta.
 *
 * Arquitectura:
 *   Main
 *    ├── Operaciones (Modelo)  — logica de calculo
 *    ├── Formulario  (Vista)   — interfaz grafica Swing
 *    └── Controlador           — coordina Modelo y Vista, maneja eventos
 */
public class Main {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater garantiza que la UI se crea en el EDT
        SwingUtilities.invokeLater(() -> {
            Operaciones modelo = new Operaciones();
            Formulario  vista  = new Formulario();
            new Controlador(modelo, vista);
            vista.setVisible(true);
        });
    }
}
