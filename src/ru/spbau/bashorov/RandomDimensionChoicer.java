package ru.spbau.bashorov;

import java.util.List;
import java.util.Random;

public class RandomDimensionChoicer<T extends Comparable<T>>  implements DimensionChoicer<T> {
    private final Random random;

    RandomDimensionChoicer() {
        random = new Random();
    }

    RandomDimensionChoicer(int seed) {
        random = new Random(seed);
    }

    @Override
    public int choice(List<T[]> data) {
        return random.nextInt(data.get(0).length);
    }
}
