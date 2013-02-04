package ru.spbau.bashorov;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static KDTree<Float> tree;
    private final static BinaryOperation<Float[],Float> distanceEvaluator = new DistanceEvaluator();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        for (int i = 0; i < args.length; i += 2) {
            if (!args[i].equals("exit")) {
                if (i < args.length - 1) {
                    doCommand(args[i], args[i+1]);
                }
            }
        }
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
        tree = new KDTree<Float>(loadData(path, Integer.MAX_VALUE, Integer.MAX_VALUE), new CircleDimensionChoicer() ,distanceEvaluator);
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

    private static void doBenchmark(String dimensionChoicer) {
        if (dimensionChoicer.equals("circle")) {
        } else if (dimensionChoicer.equals("random")) {
        } else if (dimensionChoicer.equals("widest")) {
        }
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

            data.add((Float[]) point.toArray());
        }

        return data;
    }
}
