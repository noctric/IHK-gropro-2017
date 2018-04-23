package model;

import java.util.ArrayList;

/**
 * Modellierungsklasse zur Darstellung des Gesamtproblems. Entält eine Liste von Quadranten und Änderungen.
 */
public class City {
    private int size;
    private SubArea[][] subAreas;
    private ArrayList<Change> changes = new ArrayList<>();

    /**
     * Erstelle neue Stadt-Modellierung.
     *
     * @param size     Größe der Stadt mit size x size Quadranten.
     * @param subAreas ein zwei-dimensionales Array der Größe size x size. Enthält die Quadranten und deren Funktionen.
     */
    public City(int size, SubArea[][] subAreas) {
        this.size = size;
        this.subAreas = subAreas;
    }

    /**
     * Erstelle neue Stadt-Modellierung.
     *
     * @param size Größe der Stadt als m x m. Muss größer als 0 sein!
     */
    public City(int size) {
        this.size = size;
        subAreas = new SubArea[size][size];
    }

    /**
     * Sortiert die Liste aller Veränderungen aufsteigend nach der Zeit.
     * Abstellungen werden dabei bevorzugt.
     */
    public void sortChanges() {
        this.changes.sort(Change.CHANGE_COMPARATOR);
    }

    public SubArea[][] getSubAreas() {
        return subAreas;
    }

    public ArrayList<Change> getChanges() {
        return changes;
    }

    public int getSize() {
        return size;
    }

    /**
     * Füge eine Veränderung der Stadt hinzu.
     * @param change Das Veränderungs-Modell.
     */
    public void addChange(Change change) {
        this.changes.add(change);
    }
}
