import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PuntoDos {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        int vuelto;
        int cantDenoms;
        List<Integer> denomsPosibles = Arrays.asList(5, 10, 20, 50, 100);

        do {
            System.out.println("Ingrese el vuelto");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese el vuelto");
                myObj.next();
            }
            vuelto = myObj.nextInt();
        } while (vuelto <= 0);

        do {
            System.out.println("Ingrese la cantidad de denominaciones");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese la cantidad de denominaciones");
                myObj.next();
            }
            cantDenoms = myObj.nextInt();
        } while (cantDenoms <= 0 || cantDenoms > 5);

        int denoms[] = new int[cantDenoms];

        System.out.println("Ingrese " + cantDenoms + " denominaciones");

        for (int i = 0; i < cantDenoms; i++) {
            do {
                System.out.println("Ingrese la denominación número " + (i + 1));
                while (!myObj.hasNextInt()) {
                    System.out.println("Ingrese la denominación número " + (i + 1));
                    myObj.next();
                }
                denoms[i] = myObj.nextInt();
            } while (!denomsPosibles.contains(denoms[i]));

        }

        obtenerRedondeo(vuelto, denoms);

    }

    private static void obtenerRedondeo(int vuelto, int[] denoms) {
        double multip = (double) vuelto / denoms[0];
        double decimal = multip % 1;
        double entero = multip - decimal;

        if (decimal > 0) {
            ++entero;
        }

        int redondeo = (int) (denoms[0] * entero) - vuelto;

        for (int i = 1; i < denoms.length; i++) {
            multip = (double) vuelto / denoms[i];
            decimal = multip % 1;
            entero = multip - decimal;

            if (decimal > 0) {
                ++entero;
            }

            int redondeoDos = (int) (denoms[i] * entero) - vuelto;

            if (redondeoDos <= redondeo) {
                redondeo = redondeoDos;
            }

        }

        System.out.println("El redondeo a favor del cliente es " + redondeo);

    }

}