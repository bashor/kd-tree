package ru.spbau.bashorov;

import java.util.List;

public class WidestDimensionChoicer implements DimensionChoicer<Float> {

    @Override
    public int choice(List<Float[]> data) {
        int widestDimension = 0;
        Float maxWideness = Float.MIN_VALUE;

        for (int dim = 0; dim < data.get(0).length; ++dim) {
            Float min = Float.MAX_VALUE;
            Float max = Float.MIN_VALUE;
            for (int i = 0; i < data.size(); ++i) {
                min = Math.min(min, data.get(i)[dim]);
                max = Math.max(max, data.get(i)[dim]);
            }
            if (max - min > maxWideness) {
                widestDimension = dim;
                maxWideness = max - min;
            }
        }

        return widestDimension;
    }
}
