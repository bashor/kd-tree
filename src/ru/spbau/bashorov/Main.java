package ru.spbau.bashorov;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static KDTree<Float> tree;
    private static Random random = new Random();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        doBenchmark("/home/zalim/au/data_mining/3/MultidimensionalData4AU.txt");

//        for (int i = 0; i < args.length; i += 2) {
//            if (!args[i].equals("exit")) {
//                if (i < args.length - 1) {
//                    doCommand(args[i], args[i+1]);
//                }
//            }
//        }
    }

    private static void doCommand(String command, String arg) throws IOException, ClassNotFoundException {
        if (command.equals("make")) {
            makeTree(arg);
        } else if (command.equals("save")) {
            saveIndex(arg);
        } else if (command.equals("load")) {
            loadIndex(arg);
        } else if (command.equals("benchmark")) {
            doBenchmark(arg);
        }
    }

    private static void makeTree(String path) throws IOException {
        final List<Float[]> data = loadData(path, Integer.MAX_VALUE, Integer.MAX_VALUE);
        final int maxDim = data.get(0).length;
        tree = new KDTree<Float>(data, new CircleDimensionChoicer(maxDim) , new DistanceEvaluator(maxDim));
    }

    private static void saveIndex(String path) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(tree);
        oos.close();

    }

    private static void loadIndex(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        tree = (KDTree<Float>) ois.readObject();
    }

    private static void doBenchmark(String path) throws IOException {
        final int count = 1000;

        final List<Float[]> data = loadData(path, count, Integer.MAX_VALUE);
        final int maxDim = data.get(0).length;


        benchmarkWithReport("makeTest CircleDimensionChoicer", count, new Runnable() {
            @Override
            public void run() {
                KDTree tree = new KDTree<Float>(data, new CircleDimensionChoicer<Float>(maxDim), new DistanceEvaluator(maxDim));
            }
        });

        benchmarkWithReport("makeTest RandomDimensionChoicer", count, new Runnable() {
            @Override
            public void run() {
                KDTree tree = new KDTree<Float>(data, new RandomDimensionChoicer(maxDim), new DistanceEvaluator(maxDim));
            }
        });

        benchmarkWithReport("makeTest WidestDimensionChoicer", count, new Runnable() {
            @Override
            public void run() {
                KDTree tree = new KDTree<Float>(data, new WidestDimensionChoicer(maxDim), new DistanceEvaluator(maxDim));
            }
        });

//        if (dimensionChoicer.equals("circle")) {
//        } else if (dimensionChoicer.equals("random")) {
//        } else if (dimensionChoicer.equals("widest")) {
//        }
    }
    private static void benchmarkWithReport(String text, int repeatCount, Runnable runnable) {
        double  t = benchmark(repeatCount, runnable);
        System.out.printf("%s %f ms\n", text, t);
    }

    private static void benchmark(String text, List<Float[]> data, DimensionChoicer choicer, int repeatCount, Runnable runnable) {
        for (int d = 10; d <= 100; d += 10) {
            KDTree tree = null;
            long start = System.currentTimeMillis();
            for (int i = 0; i < repeatCount; ++i) {
                choicer.reset(d);
                tree = new KDTree<Float>(data, choicer, new DistanceEvaluator(d));
            }
            long stop = System.currentTimeMillis();

            double Tmake =  (stop - start) / 10.0;

            start = System.currentTimeMillis();
            for (int i = 0; i < repeatCount; ++i) {
                Float[] point = data.get(random.nextInt(data.size()));
                tree.getNearestK(point, 10);
            }
            stop = System.currentTimeMillis();

            double Tsearch =  (stop - start) / 10.0;

            System.out.printf("%s\t%d\t%f\t%f", text, d, Tmake, Tsearch);
        }

        final int startN = 100000;
        final int D = data.get(0).length;
        DistanceEvaluator distance = new DistanceEvaluator(D);
        for (int N = startN; N <= 10 * startN; N += startN) {
            List<Float[]> curData = data.subList(0, N);
            KDTree tree = null;
            long start = System.currentTimeMillis();
            for (int i = 0; i < repeatCount; ++i) {
                choicer.reset(D);
                tree = new KDTree<Float>(curData, choicer, distance);
            }
            long stop = System.currentTimeMillis();

            double Tmake =  (stop - start) / 10.0;

            start = System.currentTimeMillis();
            for (int i = 0; i < repeatCount; ++i) {
                Float[] point = curData.get(random.nextInt(data.size()));
                tree.getNearestK(point, 10);
            }
            stop = System.currentTimeMillis();

            double Tsearch =  (stop - start) / 10.0;

            System.out.printf("%s\t%d\t%f\t%f", text, N, Tmake, Tsearch);
        }
    }

    private static double benchmark(int repeatCount, Runnable runnable) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < repeatCount; ++i) {
            runnable.run();
        }

        long stop = System.currentTimeMillis();

        return (stop - start) / 10.0;
    }

    private static List<Float[]> loadData(String path, int maxPointCount, int maxDimensions) throws IOException { //todo catch exceptions
        ArrayList<Float[]> data = new ArrayList<Float[]>();
        BufferedReader in = new BufferedReader(new FileReader(path));

        for (int i = 0; i < maxPointCount; ++i) {
            String line = in.readLine();
            if (line == null) break;

            List<Float> point = new ArrayList<Float>();

            Scanner scanner = new Scanner(line);
            for (int d = 0; d < maxDimensions && scanner.hasNextFloat(); d++) {
                point.add(scanner.nextFloat());
            }

            data.add(point.toArray(new Float[point.size()]));
        }

        return data;
    }
}
