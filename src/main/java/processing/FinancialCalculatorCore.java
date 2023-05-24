package processing;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ForkJoinPool;

public class FinancialCalculatorCore {
    private final static double BOLZANO_SEARCH_AMPLITUDE = 0.9;
    public final static double BOLZANO_MIN_STEP = 2E-5;
    private static final double BOLZANO_INIT = 1E-5;

    public static double getNPV(ArrayDeque<Double> p_cashFlows, double discount) {
        Iterator<Double> cashFlows = p_cashFlows.iterator();
        double NPV = 0;
        int i = 0;

        while (cashFlows.hasNext()) {
            NPV += (cashFlows.next() / Math.pow((1 + discount), i));
            i++;
        }
        return NPV;
    }


    /**
     * ArrayDeque exigido porque es necesario conocer la longitud total de los objetos, Iterator no lo permite
     *
     * @param p_cashFlows
     * @return
     */
    public static double getIRRSchneider(ArrayDeque<Double> p_cashFlows) {
        ArrayDeque<Double> cashFlows = p_cashFlows.clone();
        double numerator, denominator;
        final int years = cashFlows.size();

        numerator = cashFlows.stream().reduce(Double::sum).orElseThrow(RuntimeException::new);

        // El denominador del método de Schneider no acumula la inversión inicial
        cashFlows.pop();
        denominator = cashFlows.stream().reduce((prev, next) -> prev + years * next).orElseThrow(RuntimeException::new);

        return numerator / denominator;
    }

    public static double getIRRBolzano(ArrayDeque<Double> cashFlows) {
//        double lowerGuessIRR, higherGuessIRR;
//
//        lowerGuessIRR = -Math.abs(concurrent.FinancialCalculatorCore.BOLZANO_SEARCH_AMPLITUDE);
//        higherGuessIRR = Math.abs(concurrent.FinancialCalculatorCore.BOLZANO_SEARCH_AMPLITUDE);
//
//        if (NPVSharesSameSign(cashFlows, lowerGuessIRR, higherGuessIRR)) {
//            throw new NoSuchElementException();
//        }
//
//        return findSolution(cashFlows, lowerGuessIRR, higherGuessIRR);


        /*
        IRR is the value that zeroes the NPV
         */
        double IRR = BOLZANO_INIT, step = BOLZANO_MIN_STEP, previousNPV, actualNPV;
        boolean intersectsX = false;

        step *= bolzanoDirection(cashFlows, IRR);

        previousNPV = getNPV(cashFlows, IRR);
        while (!intersectsX) {
            IRR += step;
            actualNPV = getNPV(cashFlows, IRR);

            if (Math.signum(previousNPV) != Math.signum(actualNPV)) {
                intersectsX = true;
            } else {
                previousNPV = actualNPV;
            }
        }

        return IRR;
    }

//    private static double findSolution(ArrayDeque<Double> cashFlows, double lowerGuessIRR, double higherGuessIRR) {
//        double pivot = 0;
//
//        while (higherGuessIRR - lowerGuessIRR > concurrent.FinancialCalculatorCore.BOLZANO_MIN_STEP) {
//            pivot = (lowerGuessIRR + higherGuessIRR) / 2;
//
//            if (NPVSharesSameSign(cashFlows, lowerGuessIRR, pivot)){
//                lowerGuessIRR=pivot;
//            }else {
//                higherGuessIRR=pivot;
//            }
//
//        }
//
//        return pivot;
//    }

//    private static boolean NPVSharesSameSign(ArrayDeque<Double> cashFlows, double lowerGuessIRR, double higherGuessIRR) {
//
//        if (getNPV(cashFlows, lowerGuessIRR) * getNPV(cashFlows, lowerGuessIRR) > 0) {
//            return true;
//        }else {
//            return false;
//        }
//    }


    public static Double getIRRBolzano(ArrayDeque<Double> cashFlows, double startIRR, double maxIRR, double step) {
        double IRR = startIRR, previousNPV, actualNPV;
        boolean intersectsX = false;

        step *= bolzanoDirection(cashFlows, IRR);

        previousNPV = getNPV(cashFlows, IRR);
        while (!intersectsX && (IRR >= maxIRR)) {
            IRR += step;
            actualNPV = getNPV(cashFlows, IRR);

            if (Math.signum(previousNPV) != Math.signum(actualNPV)) {
                intersectsX = true;
            } else {
                previousNPV = actualNPV;
            }
        }

        if (intersectsX) {
            return IRR;
        } else {
            return null;
        }
    }

    /**
     * Calculates bolzano direction, that depends on the current IRR, set to BOLZANO_INIT.
     * If current IRR makes NPV positive, then we must increment the IRR, thus direction is set positive
     * If current IRR makes NPV negative, then we must decrement the IRR, thus direction is set negative
     *
     * @param cashFlows Used to calculate the NPV, the cashFlows of the investment
     * @param IRR       Used to calculate the NPV, the starting point of getIRRBolzano
     * @return the sign of the VAN
     */
    private static double bolzanoDirection(ArrayDeque<Double> cashFlows, double IRR) {

        return Math.signum(getNPV(cashFlows, IRR));
    }

    /**
     * Precision takes time, you will need A LOT OF TIME and RAM to run this, and it is concurrent
     */
    public static double getPreciseIRRBolzano(ArrayDeque<Double> cashFlows) {
        Double IRR;
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {

            IRR = forkJoinPool.invoke(new RecursivePlotTask(cashFlows, RecursivePlotTask.PRECISION_BOLZANO_INIT,
                    RecursivePlotTask.PRECISION_BOLZANO_MAX));
            forkJoinPool.shutdownNow();
        }

        return IRRNullSafety(IRR);
    }

    private static double IRRNullSafety(Double IRR) {
        if (IRR == null) {
            throw new NullPointerException("No se ha encontrado el TIR, puede ser negativo o mayor que 2");
        } else {
            return IRR;
        }
    }

    public static double median(double[] values) {
        Arrays.sort(values);

        int length = values.length;
        int middleIndex = length / 2;

        if (length % 2 == 0) {
            return (values[middleIndex - 1] + values[middleIndex]) / 2.0;
        } else {
            return values[middleIndex];
        }
    }

    public static double mean(double[] values) {
        double sum = 0;
        int length = values.length;

        for (double value : values) {
            sum += value;
        }

        return sum / length;
    }

    public static double variance(double[] values) {
        double mean = mean(values);
        double sumSquaredDiff = 0;
        int length = values.length;

        for (double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }

        return sumSquaredDiff / length;
    }

    public static double sampleVariance(double[] values) {
        double mean = mean(values);
        double sumSquaredDiff = 0;
        int length = values.length;

        for (double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }

        return sumSquaredDiff / (length - 1);
    }

    public static double covariance(double[] x1, double[] x2) {
        if (x1.length != x2.length) {
            throw new IllegalArgumentException("Input arrays must have the same length.");
        }

        double meanX1 = mean(x1);
        double meanX2 = mean(x2);

        double sum = 0;
        int length = x1.length;

        for (int i = 0; i < length; i++) {
            double diffX1 = x1[i] - meanX1;
            double diffX2 = x2[i] - meanX2;
            sum += diffX1 * diffX2;
        }

        return sum / (length - 1);
    }
}
