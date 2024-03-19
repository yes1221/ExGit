
package buscaminas_bueno;
import Teclat.*;
import java.util.Random;
public class Buscaminas_bueno {
static char BANDERA = 'P';
    static char TAPAT = 'X';
    static char MINA = 'M';

    public static void main(String[] args) {
        boolean juego = true;
        int Minas = 0;
        char[][] taulerVisible = null;
        boolean[][] taulerMines = null;
        while (juego == true) {
            if (Minas == 0) {
                taulerVisible = creaTaulerVisible();
                Minas = Teclat.lligInt("Cuantas minas quieres");
                taulerMines = creaTaulerMines(taulerVisible, Minas);
                mostraTauler(taulerVisible, taulerMines, false);
            } else {
                System.out.println("Menú:");
                System.out.println("1. Picar una posición");
                System.out.println("2. Poner una bandera");
                System.out.println("3. Salir del juego");
                int opcion = Teclat.lligInt("Que vas opcion vas a elegir");
                switch (opcion) {
                    case 1:
                        char revancha = 0;
                        int fila = Teclat.lligInt("Que fila quieres destapar") - 1;
                        int columna = Teclat.lligInt("Que columna quieres destapar") - 1;

                        boolean mina = pica(taulerVisible, taulerMines, fila, columna);
                        if (mina) {
                            System.out.println("Te ha explotado una mina");
                            System.out.println("Has perdido");
                            mostraTauler(taulerVisible,taulerMines,true);
                            
                            
                        } else {
                            destapa(taulerVisible, taulerMines, fila, columna);
                            mostraTauler(taulerVisible, taulerMines, false);
                            if (qdestapades(taulerVisible) == taulerVisible.length * taulerVisible[0].length - Minas) {
                                System.out.println("Has ganado");  
                                 revancha=Teclat.lligChar("Quieres volver a jugar otra partida?");
                            }else if (revancha=='S'||revancha=='s'){
                                Minas =0;                              
                            }else{
                                
                            }
                        }
                        break;

                    case 2:
                        int filaBandera = Teclat.lligInt("En que fila quieres poner la bandera")-1;
                        int columnaBandera = Teclat.lligInt("En que columna quieres poner la bandera")-1;
                        if(taulerVisible[filaBandera][columnaBandera]==BANDERA){
                           taulerVisible[filaBandera][columnaBandera]=TAPAT;
                        }
                        if (!destapada(taulerVisible, filaBandera, columnaBandera)) {
                            taulerVisible[filaBandera][columnaBandera] = BANDERA;
                            mostraTauler(taulerVisible, taulerMines, false);
                        } else {
                            System.out.println("La casilla esta destapada");
                            mostraTauler(taulerVisible, taulerMines, false);
                        }
                        break;
                    case 3:
                        System.out.println("Has salido del juego");
                        juego = false;
                        break;

                }

            }

        }

    }

    public static char[][] creaTaulerVisible() {
        int filas = Teclat.lligInt("Dime el numero de filas");
        int columnas = Teclat.lligInt("Dime el numero de columnas");

        char TaulerVisible[][] = new char[filas + 1][columnas + 1];
        for (int fil = 0; fil < filas; fil++) {
            for (int col = 0; col < columnas; col++) {
                TaulerVisible[fil][col] = TAPAT;
            }
        }
        return TaulerVisible;
    }

    public static boolean[][] creaTaulerMines(char[][] TaulerVisible, int Minas) {
        Random random = new Random();
        int filas = TaulerVisible.length;
        int columnas = TaulerVisible[0].length;
        boolean[][] TaulerMines = new boolean[filas][columnas];
        int MinasTotales = 0;
        while (MinasTotales < Minas) {
            int fila = random.nextInt(filas - 1);
            int columna = random.nextInt(columnas - 1);
            if (!TaulerMines[fila][columna]) {
                TaulerMines[fila][columna] = true;
                MinasTotales++;
            }
        }
        return TaulerMines;
    }

    public static boolean minada(boolean[][] taulerMines, int filaPosicion, int columnaPosicion) {
        if (taulerMines[filaPosicion][columnaPosicion]) {
            return true;
        }
        return false;
    }

    public static boolean incorrecta(char[][] taulerVisible, int filaPosicion, int columnaPosicion) {
        int filas = taulerVisible.length - 1;
        int columnas = taulerVisible[0].length - 1;

        if (filaPosicion < 0 || filaPosicion > filas) {
            return true;
        } else if (columnaPosicion < 0 || columnaPosicion > columnas) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean incorrecta(boolean[][] taulerMines, int filaPosicion, int columnaPosicion) {
        int filas = taulerMines.length - 1;
        int columnas = taulerMines[0].length - 1;

        if (filaPosicion < 0 || filaPosicion > filas) {
            return true;
        } else if (columnaPosicion < 0 || columnaPosicion > columnas) {
            return true;
        } else {
            return false;
        }
    }

    public static int qma(boolean[][] taulerMines, int filaPosicion, int columnaPosicion) {
        int minasCerca = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int FilaComprueba = filaPosicion + i;
                int ColumnaComprueba = columnaPosicion + j;
                if (FilaComprueba >= 0 && FilaComprueba < taulerMines.length && ColumnaComprueba >= 0 && ColumnaComprueba < taulerMines[0].length) {
                    if (taulerMines[FilaComprueba][ColumnaComprueba]) {
                        minasCerca++;
                    }
                }
            }
        }

        return minasCerca;
    }

    public static boolean destapada(char[][] taulerVisible, int filaPosicion, int columnaPosicion) {
        if (taulerVisible[filaPosicion][columnaPosicion] != TAPAT) {
            return true;
        } else {
            return false;
        }
    }

    public static int qdestapades(char[][] taulerVisible) {
        int qDestapades = 0;

        for (int fila = 0; fila < taulerVisible.length; fila++) {
            for (int columna = 0; columna < taulerVisible[0].length; columna++) {
                if (taulerVisible[fila][columna] != TAPAT) {
                    qDestapades++;
                }
            }
        }

        return qDestapades;
    }

    public static void mostraTauler(char[][] taulerVisible, boolean[][] taulerMines, boolean mostrarMines) {
        int filas = taulerVisible.length - 1;
        int columnas = taulerVisible[0].length - 1;

        System.out.println("  ");
        for (int i = 1; i <= columnas; i++) {
            System.out.print(i + " ");
        }
        System.out.println(" ");

        for (int fila = 0; fila < filas; fila++) {
            System.out.print(fila + 1 + "|");
            for (int columna = 0; columna < columnas; columna++) {
                char casella = taulerVisible[fila][columna];
                if (casella != TAPAT) {
                    System.out.print(casella + "|");
                } else if (mostrarMines && taulerMines[fila][columna]) {
                    System.out.print(MINA + "|");
                } else {
                    System.out.print(TAPAT + "|");
                }
            }
            System.out.println(" " + (fila + 1));
        }

        System.out.print("  ");
        for (int i = 1; i <= columnas; i++) {
            System.out.print(i + " ");
        }
        System.out.println(" ");
    }

    public static boolean pica(char[][] taulerVisible, boolean[][] taulerMines, int filaPosicion, int columnaPosicion) {
        if (taulerMines[filaPosicion][columnaPosicion]) {
            return true;
        } else {
            destapa(taulerVisible, taulerMines, filaPosicion, columnaPosicion);
            return false;
        }
    }

    public static void destapa(char[][] taulerVisible, boolean[][] taulerMines, int filaPosicion, int columnaPosicion) {

        if (incorrecta(taulerVisible, filaPosicion, columnaPosicion) || incorrecta(taulerMines, filaPosicion, columnaPosicion) || destapada(taulerVisible, filaPosicion, columnaPosicion)) {
            return;
        }

        if (taulerVisible[filaPosicion][columnaPosicion] == BANDERA) {
            return;
        }

        int minasCerca = qma(taulerMines, filaPosicion, columnaPosicion);

        if (minasCerca > 0) {
            taulerVisible[filaPosicion][columnaPosicion] = (char) (minasCerca + '0'); // Convertir el número en caracter
            return;
        }

        taulerVisible[filaPosicion][columnaPosicion] = '_';

        destapa(taulerVisible, taulerMines, filaPosicion - 1, columnaPosicion - 1);
        destapa(taulerVisible, taulerMines, filaPosicion - 1, columnaPosicion);
        destapa(taulerVisible, taulerMines, filaPosicion - 1, columnaPosicion + 1);
        destapa(taulerVisible, taulerMines, filaPosicion, columnaPosicion - 1);
        destapa(taulerVisible, taulerMines, filaPosicion, columnaPosicion + 1);
        destapa(taulerVisible, taulerMines, filaPosicion + 1, columnaPosicion - 1);
        destapa(taulerVisible, taulerMines, filaPosicion + 1, columnaPosicion);
        destapa(taulerVisible, taulerMines, filaPosicion + 1, columnaPosicion + 1);
    }
}
