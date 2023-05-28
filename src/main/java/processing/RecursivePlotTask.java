package processing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class RecursivePlotTask extends RecursiveTask<Double> {
    private final static double PRECISION_BOLZANO_STEP = 1E-9;
    public final static double PRECISION_BOLZANO_INIT = 1E-10;
    public final static double PRECISION_BOLZANO_MAX = 2;
    private final static int NUMBER_CONCURRENT_TASKS = 2000;
    private final double min;
    private final double max;
    private static boolean hasSpawnedChildTasks = false;
    ArrayDeque<Double> cashFlows;

    RecursivePlotTask(ArrayDeque<Double> cashFlows, double min, double max) {
        this.cashFlows = cashFlows;
        this.max = max;
        this.min = min;
    }

    @Override
    protected Double compute() {
        if (!hasSpawnedChildTasks) {
            RecursivePlotTask.hasSpawnedChildTasks = true;

            return spawnChildTasks();
        } else {
            return findSolution();
        }
    }

    private Double spawnChildTasks() {
        RecursivePlotTask[] recursivePlotTasks = createChildTasks();
        ArrayList<Future<Double>> futures = getFutures(recursivePlotTasks);

        return getFirstCompleted(futures);
    }

    private RecursivePlotTask[] createChildTasks() {
        RecursivePlotTask[] recursivePlotTasks = new RecursivePlotTask[RecursivePlotTask.NUMBER_CONCURRENT_TASKS];
        double interval = (RecursivePlotTask.PRECISION_BOLZANO_MAX - RecursivePlotTask.PRECISION_BOLZANO_INIT) /
                RecursivePlotTask.NUMBER_CONCURRENT_TASKS;

        for (int i = 0; i < RecursivePlotTask.NUMBER_CONCURRENT_TASKS; i++) {
            recursivePlotTasks[i] = new RecursivePlotTask(cashFlows, RecursivePlotTask.PRECISION_BOLZANO_INIT +
                    interval * i, RecursivePlotTask.PRECISION_BOLZANO_INIT + interval * (i + 1));
        }

        return recursivePlotTasks;
    }

    private ArrayList<Future<Double>> getFutures(RecursivePlotTask[] recursivePlotTasks) {
        ArrayList<Future<Double>> futures = new ArrayList<>();

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (RecursivePlotTask task : recursivePlotTasks) {
            futures.add(forkJoinPool.submit(task));
        }

        return futures;
    }

    private Double getFirstCompleted(ArrayList<Future<Double>> futures) {
        boolean done = false;
        Double result = null;
        while (!done) {
            for (Future<Double> future : futures) {
                if (future.isDone()) {
                    done = true;
                    try {
                        result = future.get();
                        futures.remove(future);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                }
            }
        }
        killChildren(futures);
        return result;
    }

    private void killChildren(ArrayList<Future<Double>> futures) {
        for (Future<Double> child : futures) {
            child.cancel(true);
        }
    }

    private Double findSolution() {
        return FinancialCalculatorCore.getIRRBolzano(cashFlows, max, min, PRECISION_BOLZANO_STEP);
    }

//    No consigo hacer que Java divida bien los decimales, me he tirado 4h y es un horror, así que he definido las divisiones a mano
//    private Double forkJoinTask() {
//
//        RecursiveBolzanoTask subtask1 = new RecursiveBolzanoTask(cashFlows, min, halfway);
//        RecursiveBolzanoTask subtask2 = new RecursiveBolzanoTask(cashFlows, halfway + PRECISION_BOLZANO_STEP, max);
//
//        invokeAll(subtask1, subtask2);
//
//        Double partialResult1 = subtask1.join();
//        Double partialResult2 = subtask2.join();
//
//        if (partialResult1 != null) {
//            return partialResult1;
//        } else {
//            return partialResult2;
//        }
//    }

}
