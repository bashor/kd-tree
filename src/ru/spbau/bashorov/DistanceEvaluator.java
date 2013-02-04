package ru.spbau.bashorov;

public class DistanceEvaluator implements BinaryOperation<Float[], Float> {
    private final int maxDimension;

    public DistanceEvaluator(int maxDimension) {
        this.maxDimension = maxDimension;
    }

    @Override
    public Float eval(Float[] left, Float[] right) {
        double result = 0;
        for (int i = 0; i < maxDimension; ++i) {
            result += (right[i] - left[i]) * (right[i] - left[i]);
        }
        return (float) Math.sqrt(result);
    }
}
