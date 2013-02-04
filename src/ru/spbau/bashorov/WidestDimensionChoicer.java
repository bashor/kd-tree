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
            for (int i = 0; i < data.size(); ++i) {
                min = Math.min(min, data.get(i)[d]);
                max = Math.max(max, data.get(i)[d]);
            }
            if (max - min > maxWideness) {
                widestDimension = d;
                maxWideness = max - min;
            }
        }

        return widestDimension;
    }
}
