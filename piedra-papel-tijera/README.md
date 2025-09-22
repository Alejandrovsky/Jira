# Piedra, Papel o Tijera GUI

Este proyecto es una implementación gráfica del clásico juego "Piedra, Papel o Tijera". La aplicación permite a los jugadores elegir entre las tres opciones y determina el ganador en función de las reglas del juego.

## Estructura del Proyecto

El proyecto tiene la siguiente estructura de archivos:

```
piedra-papel-tijera-gui
├── src
│   ├── Main.java                # Punto de entrada de la aplicación
│   ├── PiedraPapelTijeraGUI.java # Clase que configura la interfaz gráfica
│   └── utils
│       └── GameLogic.java       # Clase que implementa la lógica del juego
├── README.md                    # Documentación del proyecto
```

## Instrucciones para Ejecutar la Aplicación

1. Asegúrate de tener Java instalado en tu sistema.
2. Clona o descarga el repositorio en tu máquina local.
3. Navega a la carpeta del proyecto en la terminal.
4. Compila los archivos Java utilizando el siguiente comando:
   ```
   javac src/*.java src/utils/*.java
   ```
5. Ejecuta la aplicación con el siguiente comando:
   ```
   java src/Main
   ```

## Funcionalidad

- Los jugadores pueden elegir entre "piedra", "papel" o "tijera" a través de botones en la interfaz gráfica.
- El resultado del juego se muestra en la pantalla, indicando si hay un ganador o si es un empate.
- Se mantiene un historial de las partidas jugadas.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas mejorar el proyecto, por favor abre un issue o envía un pull request.