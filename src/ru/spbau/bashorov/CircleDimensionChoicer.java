package ru.spbau.bashorov;

import java.util.List;

public class CircleDimensionChoicer<T extends Comparable<T>>  implements DimensionChoicer<T>{
    private int dimension = 0;

    @Override
    public int choice(List<T[]> data) {
        if (dimension >= data.get(0).length)
            dimension = 0;

        return dimension++;
    }
}
