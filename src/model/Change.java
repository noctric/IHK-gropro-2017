package model;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Modellierungsklasse um eine Veränderung zu einem bestimmten Zeitpunkt in einem beliebigen Quadranten der Stadt
 * darzustellen.
 */
public class Change {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private int x, y;
    private boolean istNachFrage;

    private double time;

    public static final Comparator<Change> CHANGE_COMPARATOR = new Comparator<Change>() {
        @Override
        public int compare(Change o1, Change o2) {
            if (o1.getTime() < o2.getTime()) {
                return -1;
            } else if (o1.getTime() > o2.getTime()) {
                return 1;
            } else if (o1.getTime() == o2.getTime()) {
                if (o1.isIstNachFrage() && !o2.isIstNachFrage()) {
                    return 1;
                } else if (!o1.isIstNachFrage() && o2.isIstNachFrage()) {
                    return -1;
                }
            }
            return 0;
        }
    };

    /**
     * Erstelle einen neuen Änderungseintrag. Die Klasse ist dazu da, um die Ausgabe nachher zu vereinfachen.
     * Alle nötigen Ausgabedaten werden hier gekapselt (außer Bedarfsmatrizen).
     * @param x x Koordinate des Quadranten in dem die Änderung stattgefunden hat.
     * @param y y Koordinate des Quadranten in dem die Änderung stattgefunden hat.
     * @param istNachFrage Gibt an ob es sich um Nachfrage oder Abstellung handelt.
     * @param time Zeit zu der es eine Änderung gab.
     */
    public Change(int x, int y, boolean istNachFrage, double time) {
        this.x = x;
        this.y = y;
        this.istNachFrage = istNachFrage;
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isIstNachFrage() {
        return istNachFrage;
    }

    public double getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Change)) return false;

        Change change = (Change) o;

        if (getX() != change.getX()) return false;
        if (getY() != change.getY()) return false;
        if (isIstNachFrage() != change.isIstNachFrage()) return false;
        return Double.compare(change.getTime(), getTime()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getX();
        result = 31 * result + getY();
        result = 31 * result + (isIstNachFrage() ? 1 : 0);
        temp = Double.doubleToLongBits(getTime());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return (this.isIstNachFrage() ? "Nachfrage " : "Abstellung ")
                + "bei Q"
                + (this.getX() + 1)
                + (this.getY() + 1)
                +" zu t="
                + DECIMAL_FORMAT.format(this.getTime());
    }
}
