package model;

import java.util.Arrays;

/**
 * Hilfsklasse zum Modellieren eines Polynoms 4. Grades und seiner Stammfunktion, ein Polynom 5. Grades.
 * Enthält außerdem Hilfsfunktionen zum Berechnen der Funktionswerte.
 */
public class Function {
    private double[] coefficients;
    private double[] primitiveIntegral;

    /**
     * Erzeuge neue Polynomfunktion. Länge von coefficients bestimmt Grad des Polynoms
     * @param coefficients Koeffizienten des Polynoms in der Form a_0 + a_1*t + ... + a_4*t^4
     */
    public Function(double[] coefficients) {
        this.coefficients = coefficients;

        // Direkt die Stammfunktion mitkalkulieren
        calcPrimitiveIntegral();

    }

    /**
     * Berechnet die Stammfunktion
     */
    private void calcPrimitiveIntegral() {

        // Stammfunktion besitzt ja einen grad mehr
        primitiveIntegral = new double[coefficients.length + 1];

        // + C (Konstante)
        primitiveIntegral[0] = 0;

        for (int i = 1; i < primitiveIntegral.length; i++) {
            primitiveIntegral[i] = coefficients[i-1] / i;
        }
    }

    /**
     * Berechnet den Funktionswert des Polynoms für den Parameter t.
     * @param t Parameter
     * @return Funktionswert
     */
    public double calcNormal(double t) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(t, i);
        }

        return sum;
    }

    /**
     * Berechnet den Funktionswert der Stammfunktion des Polynoms für den Parameter t.
     * @param t Parameter
     * @return Funktionswert
     */
    public double calcPrimitiveIntegralVal(double t) {
        double sum = 0;
        for (int i = 0; i < primitiveIntegral.length; i++) {
            sum += primitiveIntegral[i] * Math.pow(t, i);
        }

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function)) return false;

        Function function = (Function) o;

        if (!Arrays.equals(coefficients, function.coefficients)) return false;
        return Arrays.equals(primitiveIntegral, function.primitiveIntegral);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(coefficients);
        result = 31 * result + Arrays.hashCode(primitiveIntegral);
        return result;
    }
}
