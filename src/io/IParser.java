package io;

import model.City;

import java.io.File;
import java.io.IOException;

/**
 * Ist für das Einlesen einer Datei, Parsen einer Stadtmodellierung
 * und Serialisieren der Berechnungsergebnisse zuständig.
 */
public interface IParser {

    /**
     * Liest eine Datei ein und erstellt eine neue Modellierung der Stadt.
     * Dabei muss die Datei die korrekte Syntax haben.
     * @param file Das Datei-Objekt.
     * @return ein Modellierungsobjekt der Stadt.
     * @throws IOException Sollte der Pfad oder die Datei nicht existieren
     * @throws IllegalArgumentException Sollte der Aufbau bzw die Syntax der Datei nicht korrekt sein
     * @throws NumberFormatException Sollte eine zu parsende Zahl nicht in den vorgesehenen Datentypen passen (Bsp: m muss vom Typ int sein, wird aber als Kommazahl angegeben)
     */
    public City parseFromFile(File file) throws IOException, IllegalArgumentException, NumberFormatException;

    /**
     * Schreibt die Ergebnisse der Berechnung in eine neue Datei.
     * @param city Die Modellierungsklasse, welche alle Ergebnisse der Berechnung enthält.
     * @param fileName Name der Datei, in die das Ergebnis der Berechnung geschrieben wird.
     * @throws IOException falls Datei nicht geschrieben werden konnte.
     */
    public void serialize(City city, String fileName) throws IOException;
}
