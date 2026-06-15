package dev.osvaldo.calculadora.vista;

import javax.swing.*;
import java.awt.*;

/**
 * VISTA: interfaz grafica con Java Swing.
 * Solo define los componentes visuales.
 * No contiene logica de negocio ni manejo de eventos directos.
 * Los campos publicos permiten que el Controlador los lea/escriba.
 */
public class Formulario extends JFrame {

    // Entradas
    public JTextField txtNumero1  = new JTextField(8);
    public JTextField txtNumero2  = new JTextField(8);

    // Resultado simple
    public JTextField txtResultado = new JTextField(10);

    // Resultados de "Calcular Todo" (corren en paralelo)
    public JTextField txtSuma  = new JTextField(8);
    public JTextField txtResta = new JTextField(8);
    public JTextField txtMult  = new JTextField(8);
    public JTextField txtDiv   = new JTextField(8);

    // Botones
    public JButton btnSumar      = new JButton("Sumar");
    public JButton btnRestar     = new JButton("Restar");
    public JButton btnMultiplicar= new JButton("Multiplicar");
    public JButton btnDividir    = new JButton("Dividir");
    public JButton btnTodo       = new JButton("Calcular Todo (paralelo)");

    public Formulario() {
        setTitle("Calculadora MVC — Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(panelEntradas(), BorderLayout.NORTH);
        add(panelBotones(),  BorderLayout.CENTER);
        add(panelResultados(), BorderLayout.SOUTH);

        txtResultado.setEditable(false);
        txtSuma.setEditable(false);
        txtResta.setEditable(false);
        txtMult.setEditable(false);
        txtDiv.setEditable(false);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel panelEntradas() {
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JLabel("Número 1:")); p.add(txtNumero1);
        p.add(new JLabel("Número 2:")); p.add(txtNumero2);
        return p;
    }

    private JPanel panelBotones() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel fila1 = new JPanel(new FlowLayout());
        fila1.add(btnSumar); fila1.add(btnRestar);
        fila1.add(btnMultiplicar); fila1.add(btnDividir);
        JPanel fila2 = new JPanel(new FlowLayout());
        fila2.add(new JLabel("Resultado rápido:")); fila2.add(txtResultado);
        p.add(fila1); p.add(fila2);
        return p;
    }

    private JPanel panelResultados() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));
        p.add(btnTodo);
        JPanel fila = new JPanel(new FlowLayout());
        fila.add(new JLabel("Suma:")); fila.add(txtSuma);
        fila.add(new JLabel("Resta:")); fila.add(txtResta);
        fila.add(new JLabel("Mult:")); fila.add(txtMult);
        fila.add(new JLabel("Div:")); fila.add(txtDiv);
        p.add(fila);
        return p;
    }
}
