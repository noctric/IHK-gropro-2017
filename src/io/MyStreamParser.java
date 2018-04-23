package io;

import model.Change;
import model.City;
import model.Function;
import model.SubArea;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Parser, der ohne StringBuilder und mit Printwriter arbeitet,
 * da StringBuilder bei großen Dateien sehr schnell out of memory geht.
 */
public class MyStreamParser implements IParser {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private static MyStreamParser instance;

    private enum Parserstate {STATE_READ_SIZE, STATE_READ_DEMAND, STATE_READ_PARKED}

    private MyStreamParser() {}

    public static MyStreamParser getInstance() {
        if (instance == null) {
            instance = new MyStreamParser();
        }
        return instance;
    }





    @Override
    public City parseFromFile(File file) throws IOException, IllegalArgumentException, NumberFormatException {
        Scanner sc = new Scanner(file);

        Parserstate currentState = Parserstate.STATE_READ_SIZE;

        int citySize = 0;

        SubArea[][] subAreas = null;

        // counter, werden beim funktionen parsen gebraucht
        int cnt1 = 0;
        int cnt2 = 0;

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            if (!nextLine.startsWith("#")) {

                if (currentState == Parserstate.STATE_READ_SIZE) {

                    citySize = Integer.parseInt(nextLine);

                    if (citySize <= 0) {
                        throw new IllegalArgumentException("Größe der Stadt muss mindestens 1 sein.");
                    } else if (citySize > 6) {
                        System.out.println("Warnung: Simulationen mit mehr als 6 x 6 Quadranten können sehr lange dauern.");
                    }


                    subAreas = new SubArea[citySize][citySize];

                    for (int i = 0; i < citySize; i++) {
                        for (int j = 0; j < citySize; j++) {
                            subAreas[i][j] = new SubArea();
                        }
                    }

                    currentState = Parserstate.STATE_READ_DEMAND;

                } else if (currentState == Parserstate.STATE_READ_DEMAND) {

                    if (cnt1 < citySize * citySize) {

                        double[] f = new double[5];

                        String[] tmp = nextLine.split("[\\s]+");

                        if (tmp.length == 5) {
                            for (int i = 0; i < tmp.length; i++) {
                                f[i] = Double.parseDouble(tmp[i]);
                            }
                        } else {
                            throw new IllegalArgumentException("Anzahl der Koeffizienten fehlerhaft!");
                        }

                        // finde Zeilenindex heraus. Integer Division gewünscht um abzurunden.
                        int i = cnt1 / (citySize);

                        //finde Spaltenindex
                        int j = cnt1 % (citySize);

                        subAreas[i][j].setNachfrage(new Function(f));

                        cnt1++;
                    }

                    if(cnt1 == citySize * citySize) {
                        currentState = Parserstate.STATE_READ_PARKED;
                    }

                } else if (currentState == Parserstate.STATE_READ_PARKED) {

                    if (cnt2 < citySize * citySize) {

                        double[] f = new double[5];

                        String[] tmp = nextLine.split("[\\s]+");

                        if (tmp.length == 5) {
                            for (int i = 0; i < tmp.length; i++) {
                                f[i] = Double.parseDouble(tmp[i]);
                            }
                        } else {
                            throw new IllegalArgumentException("Anzahl der Koeffizienten fehlerhaft!");
                        }

                        // finde Zeilenindex heraus. Integer Division gewünscht um abzurunden.
                        int i = cnt2 / (citySize);

                        //finde Spaltenindex
                        int j = cnt2 % (citySize);

                        subAreas[i][j].setAbstellung(new Function(f));

                        cnt2++;
                    }
                }
            }
        }

        if (cnt1 != cnt2 || cnt1 != (citySize * citySize) || currentState != Parserstate.STATE_READ_PARKED) {
            throw new IllegalArgumentException("Fehler beim Einlesen der Datei! Bitte auf Syntaxfehler prüfen...");
        }




        return new City(citySize, subAreas);
    }

    @Override
    public void serialize(City city, String fileName) throws IOException {
        Path file = Paths.get("./test_out/" + fileName);

        PrintWriter writer = new PrintWriter("./test_out/" + fileName);

        // Veränderungen schreiben
        // Könnte auch einfach mit einer toString() Methode gelöst werden
        for (Change change : city.getChanges()) {
            writer.println(change.toString());
        }

        // Bedarfsmatrix schreiben
        writer.println("Endzustand des Tages:");
        System.out.println("Endzustand des Tages:");
        for (int i = 0; i < city.getSize(); i++) {
            for (int j = 0; j < city.getSize(); j++) {
                writer.print(city.getSubAreas()[i][j].getCurrentDemand() + " ");
                System.out.print(city.getSubAreas()[i][j].getCurrentDemand() + " ");
            }
            writer.print('\n');
            System.out.print('\n');
        }

        // Maximal-Bedarfsmatrix schreiben
        writer.println("Maximaler Bedarf:\n");
        System.out.println("Maximaler Bedarf:\n");
        for (int i = 0; i < city.getSize(); i++) {
            for (int j = 0; j < city.getSize(); j++) {
                writer.print(city.getSubAreas()[i][j].getMaxDemand() + " ");
                System.out.print(city.getSubAreas()[i][j].getMaxDemand() + " ");
            }
            writer.print('\n');
            System.out.print('\n');
        }

        writer.close();
    }
}
