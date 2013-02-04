package ru.spbau.bashorov;

public class DistanceEvaluator implements BinaryOperation<Float[], Float> {
    @Override
    public Float eval(Float[] left, Float[] right) {
        double result = 0;
        for (int i = 0; i < left.length; ++i) {
            result += (right[i] - left[i]) * (right[i] - left[i]);
        }
        return (float) Math.sqrt(result);
    }
}
