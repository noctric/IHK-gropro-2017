package model;

/**
 * Modellierungsklasse zur Darstellung eines Quadranten. Enthält zwei Funktionen zur Berechnung von Nachfragen
 * und Abstellungen von Autos in diesem Quadranten. Enthält außerdem Felder für den aktuellen und maximalen Bedarf.
 */
public class SubArea {
    private Function nachfrage, abstellung;
    private int currentDemand, maxDemand;

    /**
     * Erstelle ein neues Quadrantenobjekt.
     * @param nachfrage Koeffizienten des Nachfrage-Polynoms.
     * @param abstellung Koeffizienten des Abstellungs-Polynoms.
     */
    public SubArea(Function nachfrage, Function abstellung) {
        this.nachfrage = nachfrage;
        this.abstellung = abstellung;

        this.currentDemand = 0;
        this.maxDemand = 0;
    }

    public SubArea() {
        this.currentDemand = 0;
        this.maxDemand = 0;
    }

    public Function getNachfrage() {
        return nachfrage;
    }

    public Function getAbstellung() {
        return abstellung;
    }

    public void setNachfrage(Function nachfrage) {
        this.nachfrage = nachfrage;
    }

    public void setAbstellung(Function abstellung) {
        this.abstellung = abstellung;
    }

    public int getCurrentDemand() {
        return currentDemand;
    }

    public void setCurrentDemand(int currentDemand) {
        this.currentDemand = currentDemand;
    }

    public int getMaxDemand() {
        return maxDemand;
    }

    public void setMaxDemand(int maxDemand) {
        this.maxDemand = maxDemand;
    }

    /**
     * Addiert eins auf den aktuellen Bedarf des Quadranten und prüft auf Maximum.
     */
    public void plusOneDemand() {
        this.currentDemand++;

        if (currentDemand > maxDemand) {
            this.maxDemand = currentDemand;
        }
    }

    /**
     * Subtrahiert eins vom aktuellen Bedarf
     */
    public void minusOneDemand() {
        this.currentDemand--;
    }
}
