package ru.spbau.bashorov;

import java.util.List;
import java.util.Random;

public class RandomDimensionChoicer<T extends Comparable<T>> implements DimensionChoicer<T> {
    private final Random random;
    private int maxDimension;

    RandomDimensionChoicer(int maxDimension) {
        this.maxDimension = maxDimension;
        random = new Random();
    }

    @Override
    public void reset(int maxDimension) {
        this.maxDimension = maxDimension;
    }

    RandomDimensionChoicer(int maxDimension, int seed) {
        this.maxDimension = maxDimension;
        random = new Random(seed);
    }

    @Override
    public int choice(List<T[]> data) {
        return random.nextInt(maxDimension);
    }
}
