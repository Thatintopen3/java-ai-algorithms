
package com.ai.algorithms.neuralnetwork;

import java.util.Random;

public class NeuralNetwork {
    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;
    private double[] biasHidden;
    private double[] biasOutput;
    private double learningRate = 0.1;
    private Random random = new Random();

    public NeuralNetwork(int numInputs, int numHidden, int numOutputs) {
        weightsInputHidden = new double[numInputs][numHidden];
        weightsHiddenOutput = new double[numHidden][numOutputs];
        biasHidden = new double[numHidden];
        biasOutput = new double[numOutputs];

        initializeWeightsAndBiases();
    }

    private void initializeWeightsAndBiases() {
        // Initialize weights randomly between -1 and 1
        for (int i = 0; i < weightsInputHidden.length; i++) {
            for (int j = 0; j < weightsInputHidden[0].length; j++) {
                weightsInputHidden[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        for (int i = 0; i < weightsHiddenOutput.length; i++) {
            for (int j = 0; j < weightsHiddenOutput[0].length; j++) {
                weightsHiddenOutput[i][j] = random.nextDouble() * 2 - 1;
            }
        }

        // Initialize biases randomly between -1 and 1
        for (int i = 0; i < biasHidden.length; i++) {
            biasHidden[i] = random.nextDouble() * 2 - 1;
        }
        for (int i = 0; i < biasOutput.length; i++) {
            biasOutput[i] = random.nextDouble() * 2 - 1;
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double dSigmoid(double y) {
        return y * (1 - y);
    }

    public double[] predict(double[] input) {
        // Calculate hidden layer output
        double[] hiddenOutputs = new double[biasHidden.length];
        for (int i = 0; i < biasHidden.length; i++) {
            double sum = biasHidden[i];
            for (int j = 0; j < input.length; j++) {
                sum += input[j] * weightsInputHidden[j][i];
            }
            hiddenOutputs[i] = sigmoid(sum);
        }

        // Calculate output layer output
        double[] finalOutputs = new double[biasOutput.length];
        for (int i = 0; i < biasOutput.length; i++) {
            double sum = biasOutput[i];
            for (int j = 0; j < hiddenOutputs.length; j++) {
                sum += hiddenOutputs[j] * weightsHiddenOutput[j][i];
            }
            finalOutputs[i] = sigmoid(sum);
        }
        return finalOutputs;
    }

    public void train(double[][] inputs, double[][] targets, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < inputs.length; i++) {
                double[] input = inputs[i];
                double[] target = targets[i];

                // Feedforward
                double[] hiddenOutputs = new double[biasHidden.length];
                for (int h = 0; h < biasHidden.length; h++) {
                    double sum = biasHidden[h];
                    for (int j = 0; j < input.length; j++) {
                        sum += input[j] * weightsInputHidden[j][h];
                    }
                    hiddenOutputs[h] = sigmoid(sum);
                }

                double[] finalOutputs = new double[biasOutput.length];
                for (int o = 0; o < biasOutput.length; o++) {
                    double sum = biasOutput[o];
                    for (int h = 0; h < hiddenOutputs.length; h++) {
                        sum += hiddenOutputs[h] * weightsHiddenOutput[h][o];
                    }
                    finalOutputs[o] = sigmoid(sum);
                }

                // Backpropagation
                // Calculate output layer errors
                double[] outputErrors = new double[biasOutput.length];
                for (int o = 0; o < biasOutput.length; o++) {
                    outputErrors[o] = target[o] - finalOutputs[o];
                }

                // Calculate hidden layer errors
                double[] hiddenErrors = new double[biasHidden.length];
                for (int h = 0; h < biasHidden.length; h++) {
                    double sum = 0;
                    for (int o = 0; o < biasOutput.length; o++) {
                        sum += outputErrors[o] * weightsHiddenOutput[h][o];
                    }
                    hiddenErrors[h] = sum;
                }

                // Update output layer weights and biases
                for (int o = 0; o < biasOutput.length; o++) {
                    biasOutput[o] += outputErrors[o] * dSigmoid(finalOutputs[o]) * learningRate;
                    for (int h = 0; h < hiddenOutputs.length; h++) {
                        weightsHiddenOutput[h][o] += hiddenOutputs[h] * outputErrors[o] * dSigmoid(finalOutputs[o]) * learningRate;
                    }
                }

                // Update hidden layer weights and biases
                for (int h = 0; h < biasHidden.length; h++) {
                    biasHidden[h] += hiddenErrors[h] * dSigmoid(hiddenOutputs[h]) * learningRate;
                    for (int j = 0; j < input.length; j++) {
                        weightsInputHidden[j][h] += input[j] * hiddenErrors[h] * dSigmoid(hiddenOutputs[h]) * learningRate;
                    }
                }
            }
        }
    }
}
