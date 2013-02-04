package ru.spbau.bashorov;

public interface BinaryOperation<T, R> {
    R eval(T left, T right);
}
