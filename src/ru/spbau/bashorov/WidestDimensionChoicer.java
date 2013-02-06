package ru.spbau.bashorov;

import java.util.List;

public class WidestDimensionChoicer implements DimensionChoicer<Float> {
    private int maxDimension;

    public WidestDimensionChoicer(int maxDimension) {
        this.maxDimension = maxDimension;
    }

    @Override
    public void reset(int maxDimension) {
        this.maxDimension = maxDimension;
    }

    @Override
    public int choice(List<Float[]> data) {
        int widestDimension = 0;
        Float maxWideness = Float.MIN_VALUE;

        for (int d = 0; d < maxDimension; ++d) {
            Float min = Float.MAX_VALUE;
            Float max = Float.MIN_VALUE;
            for (Float[] point : data) {
                min = Math.min(min, point[d]);
                max = Math.max(max, point[d]);
            }
            if (max - min > maxWideness) {
                widestDimension = d;
                maxWideness = max - min;
            }
        }

        return widestDimension;
    }
}
