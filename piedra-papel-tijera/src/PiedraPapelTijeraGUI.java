import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class PiedraPapelTijeraGUI extends JFrame {
    private JButton piedraButton, papelButton, tijeraButton, crearUsuarioButton, iniciarSesionButton, verHistorialButton;
    private JLabel resultadoLabel, historialLabel, usuarioLabel;
    private JComboBox<String> modoCombo, usuariosCombo;
    private String jugada1 = null;
    private int jugadores = 1;

    private Map<String, HistorialUsuario> usuarios = new HashMap<>();
    private String usuarioActual = null;

    public PiedraPapelTijeraGUI() {
        setTitle("Piedra, Papel o Tijera");
        setSize(700, 500); // Tamaño más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        modoCombo = new JComboBox<>(new String[]{"1 jugador", "2 jugadores"});
        usuariosCombo = new JComboBox<>();
        crearUsuarioButton = new JButton("Crear usuario");
        iniciarSesionButton = new JButton("Iniciar sesión");
        verHistorialButton = new JButton("Ver historial");

        usuarioLabel = new JLabel("Usuario: (no iniciado)");
        resultadoLabel = new JLabel("Elige tu jugada:");
        historialLabel = new JLabel("<html>Historial:<br></html>");
        historialLabel.setVerticalAlignment(SwingConstants.TOP);

        piedraButton = new JButton("Piedra");
        papelButton = new JButton("Papel");
        tijeraButton = new JButton("Tijera");

        //  Modo y usuarios
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        add(new JLabel("Modo de juego:"), gbc);
        gbc.gridx = 1;
        add(modoCombo, gbc);
        gbc.gridx = 2;
        add(new JLabel("Usuarios:"), gbc);
        gbc.gridx = 3;
        add(usuariosCombo, gbc);

        // Botones de usuario
        gbc.gridx = 0; gbc.gridy = 1;
        add(crearUsuarioButton, gbc);
        gbc.gridx = 1;
        add(iniciarSesionButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(usuarioLabel, gbc);

        // Resultado
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        add(resultadoLabel, gbc);

        // Botones de jugada
        gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(piedraButton, gbc);
        gbc.gridx = 1;
        add(papelButton, gbc);
        gbc.gridx = 2;
        add(tijeraButton, gbc);
        gbc.gridx = 3;
        add(verHistorialButton, gbc);

        // Historial
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        JScrollPane scrollHistorial = new JScrollPane(historialLabel);
        scrollHistorial.setPreferredSize(new Dimension(600, 200));
        add(scrollHistorial, gbc);

        piedraButton.addActionListener(e -> jugar("piedra"));
        papelButton.addActionListener(e -> jugar("papel"));
        tijeraButton.addActionListener(e -> jugar("tijera"));

        modoCombo.addActionListener(e -> {
            jugadores = modoCombo.getSelectedIndex() == 0 ? 1 : 2;
            jugada1 = null;
            resultadoLabel.setText(jugadores == 1 ? "Elige tu jugada:" : "Jugador 1, elige tu jugada:");
        });

        crearUsuarioButton.addActionListener(e -> crearUsuario());
        iniciarSesionButton.addActionListener(e -> iniciarSesion());
        verHistorialButton.addActionListener(e -> mostrarHistorial());

        actualizarBotones(false);
    }

    private void crearUsuario() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre de usuario:");
        if (nombre != null && !nombre.trim().isEmpty() && !usuarios.containsKey(nombre)) {
            usuarios.put(nombre, new HistorialUsuario());
            usuariosCombo.addItem(nombre);
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Nombre inválido o ya existe.");
        }
    }

    private void iniciarSesion() {
        String nombre = (String) usuariosCombo.getSelectedItem();
        if (nombre != null && usuarios.containsKey(nombre)) {
            usuarioActual = nombre;
            usuarioLabel.setText("Usuario: " + usuarioActual);
            resultadoLabel.setText(jugadores == 1 ? "Elige tu jugada:" : "Jugador 1, elige tu jugada:");
            historialLabel.setText("<html>Historial:<br></html>");
            actualizarBotones(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario válido.");
        }
    }

    private void actualizarBotones(boolean habilitar) {
        piedraButton.setEnabled(habilitar);
        papelButton.setEnabled(habilitar);
        tijeraButton.setEnabled(habilitar);
        verHistorialButton.setEnabled(habilitar);
    }

    private void jugar(String jugada) {
        if (usuarioActual == null) return;

        HistorialUsuario historialUsuario = usuarios.get(usuarioActual);

        if (jugadores == 1) {
            String jugadaBot = elegirJugadaBot();
            String resultado = verificarGanador(jugada, jugadaBot);
            resultadoLabel.setText("Resultado: " + resultado + " (Bot eligió: " + jugadaBot + ")");
            historialUsuario.agregarPartida("J1: " + jugada + " - Bot: " + jugadaBot + " => " + resultado);
            historialUsuario.actualizarEstadisticas(resultado);
        } else {
            if (jugada1 == null) {
                jugada1 = jugada;
                resultadoLabel.setText("Jugador 2, elige tu jugada:");
            } else {
                String jugada2 = jugada;
                String resultado = verificarGanador(jugada1, jugada2);
                resultadoLabel.setText("Resultado: " + resultado + " (J1: " + jugada1 + ", J2: " + jugada2 + ")");
                historialUsuario.agregarPartida("J1: " + jugada1 + " - J2: " + jugada2 + " => " + resultado);
                historialUsuario.actualizarEstadisticas(resultado);
                jugada1 = null;
            }
        }
    }

    private void mostrarHistorial() {
        if (usuarioActual == null) return;
        HistorialUsuario h = usuarios.get(usuarioActual);
        StringBuilder sb = new StringBuilder("<html>Historial:<br>");
        for (String partida : h.partidas) {
            sb.append(partida).append("<br>");
        }
        sb.append("<br>Victorias: ").append(h.victorias);
        sb.append("<br>Derrotas: ").append(h.derrotas);
        sb.append("<br>Empates: ").append(h.empates);
        sb.append("</html>");
        historialLabel.setText(sb.toString());
    }

    private String elegirJugadaBot() {
        String[] opciones = {"piedra", "papel", "tijera"};
        Random rnd = new Random();
        return opciones[rnd.nextInt(3)];
    }

    private String verificarGanador(String jugador1, String jugador2) {
        if (jugador1.equals(jugador2)) {
            return "Empate";
        } else if ((jugador1.equals("piedra") && jugador2.equals("tijera")) ||
                   (jugador1.equals("tijera") && jugador2.equals("papel")) ||
                   (jugador1.equals("papel") && jugador2.equals("piedra"))) {
            return "Jugador 1 gana";
        } else {
            return jugadores == 1 ? "Bot gana" : "Jugador 2 gana";
        }
    }

    static class HistorialUsuario {
        List<String> partidas = new ArrayList<>();
        int victorias = 0, derrotas = 0, empates = 0;

        void agregarPartida(String partida) {
            partidas.add(partida);
        }

        void actualizarEstadisticas(String resultado) {
            if (resultado.contains("Empate")) empates++;
            else if (resultado.contains("Jugador 1 gana")) victorias++;
            else derrotas++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PiedraPapelTijeraGUI gui = new PiedraPapelTijeraGUI();
            gui.setVisible(true);
        });
    }
}