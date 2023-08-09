
package com.ai.algorithms;

import com.ai.algorithms.neuralnetwork.NeuralNetwork;
import com.ai.algorithms.search.AStarSearch;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running AI Algorithms Demo...");

        // Neural Network Demo
        System.out.println("
--- Neural Network Demo ---");
        NeuralNetwork nn = new NeuralNetwork(2, 3, 1);
        double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] outputs = {{0}, {1}, {1}, {0}};
        nn.train(inputs, outputs, 10000);

        System.out.println("Prediction for [0, 0]: " + nn.predict(new double[]{0, 0})[0]);
        System.out.println("Prediction for [0, 1]: " + nn.predict(new double[]{0, 1})[0]);
        System.out.println("Prediction for [1, 0]: " + nn.predict(new double[]{1, 0})[0]);
        System.out.println("Prediction for [1, 1]: " + nn.predict(new double[]{1, 1})[0]);

        // A* Search Demo (conceptual, actual implementation would be more complex)
        System.out.println("
--- A* Search Demo (Conceptual) ---");
        // Imagine a graph or grid here
        // AStarSearch astar = new AStarSearch(startNode, goalNode);
        // List<Node> path = astar.findPath();
        System.out.println("A* Search would find the shortest path in a graph.");
    }
}
