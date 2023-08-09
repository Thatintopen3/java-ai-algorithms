
package com.ai.algorithms.common;

/**
 * Generic interface for AI algorithms.
 */
public interface AIAlgorithm<Input, Output> {
    Output execute(Input input);
}
