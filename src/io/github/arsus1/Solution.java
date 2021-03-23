package io.github.arsus1;

import java.util.Arrays;

/**
 * Класс, представляющий решение СЛАУ
 */
public class Solution {
    private final double[] answers;
    private final long iterations;
    private final double[] error;

    /**
     * Создание решения СЛАУ
     * @param answers Ответы
     * @param iterations Число итераций
     * @param error Погрешность вычислений
     */
    public Solution(double[] answers, long iterations, double[] error) {
        this.answers = answers;
        this.iterations = iterations;
        this.error = error;
    }

    /**
     * Текстовое представление решений СЛАУ
     * @return Возвращает текст
     */
    public String toString() {
        return "Number of iterations: " + iterations + "\n" +
                "Answers: " + "\n" +
                Arrays.toString(answers).replaceAll(",", ",\n") + "\n" +
                "Errors: " + "\n" +
                Arrays.toString(error).replaceAll(",", ",\n");
    }

    /**
     * Возвращает решения СЛАУ
     * @return Решения СЛАУ
     */
    public double[] getAnswers() {
        return answers;
    }

    /**
     * Возвращает число итераций
     * @return Число итераций
     */
    public long getIterations() {
        return iterations;
    }

    /**
     * Возвращает столбец погрешностей
     * @return Столбец погрешностей
     */
    public double[] getError() {
        return error;
    }
}
