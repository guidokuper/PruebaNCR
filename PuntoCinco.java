import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PuntoCinco {

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
        HashMap<Integer, Integer> denomsMap = new HashMap<Integer, Integer>();
        denomsMap = readFile();
        int flag = 0;

        for (Integer den : denomsMap.keySet()) {
            if (denomsMap.get(den) > 10) {
                int denomElegida = denomsMap.get(den);
                double multip = (double) vuelto / denomElegida;
                double decimal = multip % 1;
                double entero = multip - decimal;
                if (decimal > 0) {
                    ++entero;
                }
                if ((denomsMap.get(den) - entero) < 10) {
                    ++flag;
                    continue;
                }
                denomsMap.put(den, denomsMap.get(den) - ((int) Math.round(entero)));
                int redondeo = (int) (denomElegida * entero) - vuelto;
                System.out.println("El redondeo a favor del cliente es " + redondeo + " con denominación " + denomElegida);
            } else {
                ++flag;
                continue;
            }
        }
        if (flag == denomsMap.size()) {
            System.out.println("No hay dinero suficiente");
        }
        //Pendiente: Una vez persistida la nueva cantidad de la denominación, guardarla para la próxima llamada.
    }

    public static void createFile() {
        try {
            File myFile = new File("denomsCantidades.txt");
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
            FileWriter myWriter = new FileWriter("denomsCantidades.txt");
            int denoms[] = getDenoms();
            int quantities[] = getQuantities(denoms);
            for (int i = 0; i < denoms.length - 1; i++) {
                myWriter.write(denoms[i] + "=" + quantities[i] + System.getProperty("line.separator"));
            }
            myWriter.write(denoms[denoms.length - 1] + "=" + quantities[quantities.length - 1]);
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

    private static int[] getQuantities(int[] denoms) {
        int quantities[] = new int[denoms.length];
        Scanner myObj = new Scanner(System.in);

        for (int i = 0; i < denoms.length; i++) {
            do {
                System.out.println("Ingrese la cantidad disponible para la denominación " + denoms[i]);
                while (!myObj.hasNextInt()) {
                    System.out.println("Ingrese la cantidad disponible para la denominación " + denoms[i]);
                    myObj.next();
                }
                quantities[i] = myObj.nextInt();
            } while (quantities[i] <= 0);
        }

        return quantities;
    }

    private static HashMap<Integer, Integer> readFile() {
        HashMap<Integer, Integer> denomsMap = new HashMap<Integer, Integer>();
        ArrayList<Integer> denomsListKeys = new ArrayList<Integer>();
        ArrayList<Integer> denomsListValues = new ArrayList<Integer>();
        try {
            File myFile = new File("denomsCantidades.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                Pattern patternK = Pattern.compile("\\d+[=]");
                Matcher matcherK = patternK.matcher(myReader.nextLine());
                while (matcherK.find()) {
                    Pattern pK = Pattern.compile("\\d+");
                    Matcher mK = pK.matcher(matcherK.group());
                    while (mK.find()) {
                        denomsListKeys.add(Integer.parseInt(mK.group()));
                    }
                }
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo");
            e.printStackTrace();
        }
        try {
            File myFile = new File("denomsCantidades.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                Pattern patternV = Pattern.compile("[=]\\d+");
                Matcher matcherV = patternV.matcher(myReader.nextLine());
                while (matcherV.find()) {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(matcherV.group());
                    while (m.find()) {
                        denomsListValues.add(Integer.parseInt(m.group()));
                    }
                }
            }
            for (int i = 0; i < denomsListKeys.size(); i++) {
                denomsMap.put(denomsListKeys.get(i), denomsListValues.get(i));
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo");
            e.printStackTrace();
        }
        return denomsMap;
    }
}
