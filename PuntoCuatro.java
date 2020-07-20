import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PuntoCuatro {

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
            File myFile = new File("denomsNiveles.txt");
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
            FileWriter myWriter = new FileWriter("denomsNiveles.txt");
            int denoms[] = getDenoms();
            String levels[] = getLevels(denoms);
            for (int i = 0; i < denoms.length - 1; i++) {
                myWriter.write(denoms[i] + "=" + levels[i] + System.getProperty("line.separator"));
            }
            myWriter.write(denoms[denoms.length - 1] + "=" + levels[levels.length - 1]);
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

    private static String[] getLevels(int[] denoms) {
        String levels[] = new String[denoms.length];
        Scanner myObj = new Scanner(System.in);
        String possibleLevels[] = {"BAJO", "NORMAL"};

        for (int i = 0; i < denoms.length; i++) {
            do {
                System.out.println("Ingrese el nivel de la denominacion " + denoms[i]);
                levels[i] = myObj.next();
            } while (!Arrays.asList(possibleLevels).contains(levels[i]));
        }

        return levels;
    }

    private static ArrayList<Integer> readFile() {
        ArrayList<Integer> denomsList = new ArrayList<Integer>();
        try {
            File myFile = new File("denomsNiveles.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                Pattern pattern = Pattern.compile("\\d+[=]NORMAL");
                Matcher matcher = pattern.matcher(myReader.nextLine());
                while (matcher.find()){
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(matcher.group());
                    while (m.find()) {
                        denomsList.add(Integer.parseInt(m.group()));
                    }
                }
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo");
            e.printStackTrace();
        }
        return denomsList;
    }
}