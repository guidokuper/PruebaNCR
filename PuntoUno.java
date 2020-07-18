import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PuntoUno {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        int vuelto;
        int denom;
        List<Integer> denomsPosibles = Arrays.asList( 5, 10, 20, 50, 100 );

        do {
            System.out.println("Ingrese el vuelto");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese el vuelto");
                myObj.next();
            }
            vuelto = myObj.nextInt();
        } while (vuelto <= 0);

        do {
            System.out.println("Ingrese la denominación");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese la denominación");
                myObj.next();
            }
            denom = myObj.nextInt();
        } while (!denomsPosibles.contains(denom));

        obtenerRedondeo(vuelto, denom);

    }

    private static void obtenerRedondeo(int vuelto, int denom) {
        double multip = (double) vuelto / denom;
        double decimal = multip % 1;
        double entero = multip - decimal;

        if (decimal > 0) {
            ++entero;
        }

        int redondeo = (int) (denom * entero) - vuelto;
        System.out.println("El redondeo a favor del cliente es " + redondeo);

    }

}