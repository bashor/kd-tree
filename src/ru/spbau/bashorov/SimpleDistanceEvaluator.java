package ru.spbau.bashorov;

public class SimpleDistanceEvaluator implements BinaryOperation<Float, Float> {
    @Override
    public Float eval(Float left, Float right) {
        return Math.abs(right - left);
    }
}
