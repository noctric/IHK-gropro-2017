package calculation;

import model.City;

/**
 * Schnittstelle für die Berechnungsstrategie. Jede implementierte Berechnungsstrategie muss dieses Interface
 * implementieren.
 * Alle Ergebnisse werden in die City Klasse geschrieben, indem bei einer Bedarfsänderung ein neues Veränderungsobjekt
 * der Stadt hinzugefügt wird.
 */
public interface IStrategy {

    /**
     * Berechnet die Bedarfsverläufe einer Stadt.
     * Insgesamt wird für jeden Quadranten ein Endbedarf und ein Maximalbedarf des Tages berechnet.
     * Weiterhin werden alle Änderungen als sortierte Liste gespeichert.
     * @param city Die Modellierungsklasse, welche alle nötigen Informationen zur Berechnung enthält.
     */
    public void calculateOneCycle(City city);
}
