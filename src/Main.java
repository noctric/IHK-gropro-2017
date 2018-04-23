import calculation.MyStrategy;
import io.MyParser;
import io.MyStreamParser;
import model.City;

import java.io.File;
import java.io.IOException;

/**
 * Main Programm, welches alle Dateien aus einem Ordner einliest und die Berechnungsstrategie anwendet.
 * Sofern keine syntaktischen oder logischen Fehler in der Eingabedatei vorhanden sind, werden output-Dateien generiert.
 */
public class Main {

    public static void main(String[] args) {

        if (args.length > 0) {
            File f = new File(args[0]);
            if (f.exists() && f.isDirectory()) {
                File[] files = f.listFiles((dir, name) -> (name.endsWith(".in")));
                assert (files != null);
                for (File file : files) {

                    City city = null;

                    try {
                        city = MyParser.getInstance().parseFromFile(file);
                    } catch (IOException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                    if (city != null) {
                        long startTime = System.currentTimeMillis();
                        System.out.println("Processing " + file.getName() + "...");
                        MyStrategy.getInstance().calculateOneCycle(city);
                        System.out.println("Took " + (System.currentTimeMillis() - startTime)
                                + " ms to calculate " + file.getName());
                        try {
                            MyStreamParser.getInstance().serialize(city, file.getName() + ".out");
                            System.out.println("Took " + (System.currentTimeMillis() - startTime)
                                    + " ms in total to process " + file.getName());
                            System.out.println("Found " + city.getChanges().size() + " changes for " + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            } else {
                System.err.println("Could not find specified Path \'" + args[0] + '\'');
            }
        } else {
            System.err.println("Please specify a path to a directory.");
        }


    }
}
