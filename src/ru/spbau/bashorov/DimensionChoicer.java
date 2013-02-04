package ru.spbau.bashorov;

import java.util.List;

public interface DimensionChoicer<T extends Comparable<T>> {
    int choice(List<T[]> data);
}
