package ru.spbau.bashorov;

import java.util.List;

public interface DimensionChoicer<T extends Comparable<T>> {
    void reset(int maxDimension);
    int choice(List<T[]> data);
}
