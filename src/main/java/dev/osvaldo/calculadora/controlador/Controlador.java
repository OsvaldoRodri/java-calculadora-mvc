package dev.osvaldo.calculadora.controlador;

import dev.osvaldo.calculadora.modelo.Operaciones;
import dev.osvaldo.calculadora.vista.Formulario;

import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * CONTROLADOR: coordina el Modelo y la Vista.
 * - Escucha eventos de la Vista (botones)
 * - Delega calculos al Modelo
 * - Actualiza la Vista con los resultados
 *
 * Las operaciones del boton "Calcular Todo" corren en hilos separados
 * usando SwingWorker para no bloquear la interfaz grafica (EDT).
 */
public class Controlador implements ActionListener {

    private final Operaciones modelo;
    private final Formulario  vista;

    public Controlador(Operaciones modelo, Formulario vista) {
        this.modelo = modelo;
        this.vista  = vista;

        // Registrar listeners
        vista.btnSumar.addActionListener(this);
        vista.btnRestar.addActionListener(this);
        vista.btnMultiplicar.addActionListener(this);
        vista.btnDividir.addActionListener(this);
        vista.btnTodo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.btnSumar)       calcularSimple("SUMA");
        if (src == vista.btnRestar)      calcularSimple("RESTA");
        if (src == vista.btnMultiplicar) calcularSimple("MULT");
        if (src == vista.btnDividir)     calcularSimple("DIV");
        if (src == vista.btnTodo)        calcularTodo();
    }

    /** Calculo inmediato en el hilo de la UI (operacion rapida) */
    private void calcularSimple(String tipo) {
        int[] nums = leerNumeros();
        if (nums == null) return;

        String resultado = switch (tipo) {
            case "SUMA"  -> modelo.sumar(nums[0], nums[1]);
            case "RESTA" -> modelo.restar(nums[0], nums[1]);
            case "MULT"  -> modelo.multiplicar(nums[0], nums[1]);
            case "DIV"   -> modelo.dividir(nums[0], nums[1]);
            default      -> "?";
        };
        vista.txtResultado.setText(resultado);
    }

    /**
     * Lanza 4 SwingWorkers en paralelo.
     * Cada uno simula una operacion costosa (sleep) sin bloquear la UI.
     * Patron: doInBackground() corre en hilo worker, process() actualiza la UI en EDT.
     */
    private void calcularTodo() {
        int[] nums = leerNumeros();
        if (nums == null) return;

        lanzarWorker("SUMA",  nums[0], nums[1], 4000);
        lanzarWorker("RESTA", nums[0], nums[1], 3000);
        lanzarWorker("MULT",  nums[0], nums[1], 2000);
        lanzarWorker("DIV",   nums[0], nums[1], 1000);
    }

    private void lanzarWorker(String tipo, int a, int b, int delayMs) {
        new SwingWorker<String, String>() {

            @Override
            protected String doInBackground() throws InterruptedException {
                publish("Calculando " + tipo.toLowerCase() + "...");
                Thread.sleep(delayMs);
                return switch (tipo) {
                    case "SUMA"  -> modelo.sumar(a, b);
                    case "RESTA" -> modelo.restar(a, b);
                    case "MULT"  -> modelo.multiplicar(a, b);
                    case "DIV"   -> modelo.dividir(a, b);
                    default      -> "?";
                };
            }

            @Override
            protected void process(List<String> chunks) {
                actualizarCampo(tipo, chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                try { actualizarCampo(tipo, get()); }
                catch (Exception ex) { actualizarCampo(tipo, "Error"); }
            }
        }.execute();
    }

    private void actualizarCampo(String tipo, String valor) {
        switch (tipo) {
            case "SUMA"  -> vista.txtSuma.setText(valor);
            case "RESTA" -> vista.txtResta.setText(valor);
            case "MULT"  -> vista.txtMult.setText(valor);
            case "DIV"   -> vista.txtDiv.setText(valor);
        }
    }

    private int[] leerNumeros() {
        try {
            int a = Integer.parseInt(vista.txtNumero1.getText().trim());
            int b = Integer.parseInt(vista.txtNumero2.getText().trim());
            return new int[]{a, b};
        } catch (NumberFormatException ex) {
            vista.txtResultado.setText("Error: numeros invalidos");
            return null;
        }
    }
}
