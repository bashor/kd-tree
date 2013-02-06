package ru.spbau.bashorov;

import java.util.List;

public class CircleDimensionChoicer<T extends Comparable<T>> implements DimensionChoicer<T> {
    private int dimension = 0;
    private int maxDimension;

    CircleDimensionChoicer(int maxDimension) {
        this.maxDimension = maxDimension;
    }

    @Override
    public void reset(int maxDimension) {
        this.maxDimension = maxDimension;
        dimension = 0;
    }

    @Override
    public int choice(List<T[]> data) {
        if (dimension >= maxDimension)
            dimension = 0;

        return dimension++;
    }
}
