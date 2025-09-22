import java.util.Random;

public class IniciarSesion {
    public String elegirJugadaBot() {
        String[] opciones = {"piedra", "papel", "tijera"};
        Random rnd = new Random();
        return opciones[rnd.nextInt(opciones.length)];
    }

    public String verificarGanador(String jugador1, String jugador2) {
        if (jugador1.equals(jugador2)) {
            return "Empate";
        } else if ((jugador1.equals("piedra") && jugador2.equals("tijera")) ||
                   (jugador1.equals("tijera") && jugador2.equals("papel")) ||
                   (jugador1.equals("papel") && jugador2.equals("piedra"))) {
            return "Jugador 1 gana";
        } else {
            return "Bot gana";
        }
    }
}