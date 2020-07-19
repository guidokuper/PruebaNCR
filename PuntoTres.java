import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PuntoTres {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        int vuelto;

        do {
            System.out.println("Ingrese el vuelto");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese el vuelto");
                myObj.next();
            }
            vuelto = myObj.nextInt();
        } while (vuelto <= 0);

        createFile();
        obtenerRedondeo(vuelto);

    }

    private static void obtenerRedondeo(int vuelto) {
        ArrayList<Integer> denomsList = new ArrayList<Integer>();
        denomsList = readFile();
        int denomElegida = denomsList.get(0);
        double multip = (double) vuelto / denomElegida;
        double decimal = multip % 1;
        double entero = multip - decimal;

        if (decimal > 0) {
            ++entero;
        }

        int redondeo = (int) (denomElegida * entero) - vuelto;

        for (int i = 1; i < denomsList.size(); i++) {
            int denomActual = denomsList.get(i);
            multip = (double) vuelto / denomActual;
            decimal = multip % 1;
            entero = multip - decimal;

            if (decimal > 0) {
                ++entero;
            }

            int redondeoDos = (int) (denomActual * entero) - vuelto;

            if (redondeoDos <= redondeo) {
                redondeo = redondeoDos;
                denomElegida = denomActual;
            }

        }

        System.out.println("El redondeo a favor del cliente es " + redondeo + " con denominación " + denomElegida);
    }

    public static void createFile() {
        try {
            File myFile = new File("denominaciones.txt");
            if (myFile.createNewFile()) {
                System.out.println("Archivo creado: " + myFile.getName());
                writeFile();
            } else {
                System.out.println("El archivo ya existe");
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void writeFile() {
        try {
            FileWriter myWriter = new FileWriter("denominaciones.txt");
            int denoms[] = getDenoms();
            for (int i = 0; i < denoms.length; i++) {
                myWriter.write(denoms[i] + " ");
            }
            myWriter.close();
            System.out.println("Se escribió en el archivo");
        } catch (Exception e) {
            System.out.println("Error escribiendo el archivo");
            e.printStackTrace();
        }
    }

    private static int[] getDenoms() {
        int cantDenoms;
        Scanner myObj = new Scanner(System.in);

        do {
            System.out.println("Ingrese la cantidad de denominaciones");
            while (!myObj.hasNextInt()) {
                System.out.println("Ingrese la cantidad de denominaciones");
                myObj.next();
            }
            cantDenoms = myObj.nextInt();
        } while (cantDenoms <= 1);

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
            } while (denoms[i] <= 0);
        }
        return denoms;
    }

    private static ArrayList<Integer> readFile() {
        ArrayList<Integer> denomsList = new ArrayList<Integer>();
        try {
            File myFile = new File("denominaciones.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNext()) {
                denomsList.add(myReader.nextInt());
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo");
            e.printStackTrace();
        }
        return denomsList;
    }
}