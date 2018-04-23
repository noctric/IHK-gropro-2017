package calculation;

import model.Change;
import model.City;
import model.Function;

/**
 * Implementierung einer Berechnungsstrategie, welche mit dem Bisektionsverfahren arbeitet.
 * Endbedarf und Maximalbedarf werden separat von der eigentlichen Bedarfsberechnung kalkuliert.
 * Singleton-Implementierung.
 */
public class MyStrategy implements IStrategy{

    private static MyStrategy instance;

    private MyStrategy() {
    }

    public static MyStrategy getInstance() {
        if (instance == null) {
            instance = new MyStrategy();
        }

        return instance;
    }


    private static final double ERROR_TOLERANCE = 0.001;

    /**
     * Funktion berechnet Nullstelle der math. Funktion P(x) - k mit Hilfe des Bisektionsverfahrens.
     * @param p Das Funktionsobjekt aus dem die Stammfunktion entnommen wird
     * @param k Parameter für die Gleichung P(x) - k = 0
     * @return Nullstelle von P(x) - k oder -1 falls keine NS für das Intervall t aus [0;24] gefunden wurde
     */
    private double bisection(Function p, int k) {

        double a = 0;
        double b = 24;
        double mid = -1;
        double calc = -1;

        // maximaler Fehler ->eps. eps ist der Abstand zwischen den Punkten a und b.
        double eps = b - a;

        // setze a = 0, b = 24 und prüfe ob als untere und obere Grenze geeignet. VZ-Wechsel!
        if (p.calcPrimitiveIntegralVal(a) - k < 0 && p.calcPrimitiveIntegralVal(b) - k > 0) {
            while( Math.abs(eps) > ERROR_TOLERANCE) {

                mid = (a+b)/2;
                calc = p.calcPrimitiveIntegralVal(mid) - k;

                if (calc > 0) {
                    // neue Obergrenze gefunden
                    b = mid;
                } else if (calc < 0) {
                    // neue Untergrenze gefunden
                    a = mid;
                } else if (calc == 0) {
                    // "optimale" Lösung gefunden.. Breche ab und liefere Ergebnis zurück
                    break;
                }
                eps = calc;
            }
        } else if (p.calcPrimitiveIntegralVal(a) - k == 0) {
            // Untergrenze ist Nullstelle
            return a;
        } else if (p.calcPrimitiveIntegralVal(b) - k == 0) {
            // Obergrenze ist Nullstelle
            return b;
        }

        return mid;

    }

    @Override
    public void calculateOneCycle(City city) {
        int k = 0;
        for (int i = 0; i < city.getSize(); i++) {
            for (int j = 0; j < city.getSize(); j++) {
                k=1;
                while (true) {
                    double t = bisection(city.getSubAreas()[i][j].getNachfrage(), k);
                    k++;

                    // Hier kann nun abgebrochen werden,
                    // da Funktion monoton steigend und Polynom nicht negativ!
                    if (t < 0 || t > 24) {
                        break;
                    }

                    // gültiges t => Teile Veränderung der Stadt mit.
                    city.addChange(new Change(i, j, true, t));

                }

                // setze k wieder auf 1 und berechne nochmal für Abstellung
                // Der Übersicht wegen in zwei Bereiche mit zwei Schleifen eingeteilt.
                k = 1;
                while (true) {
                    double t = bisection(city.getSubAreas()[i][j].getAbstellung(), k);
                    k++;

                    if (t < 0 || t > 24) {
                        break;
                    }

                    // gültiges t => Teile Veränderung der Stadt mit.
                    city.addChange(new Change(i, j, false, t));

                }
            }
        }

        // Berechne Endbedarf und Maximas.
        // Bedarf kann auch in der obigen Berechnung ermittelt werden, ist aber der Übersicht wegen jetzt auch
        // in die untere Methode gewandert, da die Bedarfsberechnung direkt mit der Maximumsberechnung zusammenhängt.
        calcDemandForSubAreas(city);
    }

    /**
     * Berechnet den Maximalen Befarf der Quadranten.
     * Bedarfsänderungen werden vorher sortiert.
     * Methode muss extra aufgerufen werden, da Reihenfolge bei der eigentlichen Berechnung nicht beachtet wird aber
     * für das lokale Maximum relevant ist.
     * @param city Die Stadt für die die lokalen Bedarfs-Maxima ausgerechnet werden sollen.
     */
    private void calcDemandForSubAreas(City city) {

        city.sortChanges();

        for (Change change : city.getChanges()) {

            if (change.isIstNachFrage()) {
                // + 1 Bedarf bei Quadrant
                city.getSubAreas()[change.getX()][change.getY()].plusOneDemand();
            } else {
                // - 1 Bedarf bei Quadrant
                city.getSubAreas()[change.getX()][change.getY()].minusOneDemand();
            }
        }
    }
}
