package io.github.arsus1;

import io.github.arsus1.exceptions.SolverException;

/**
 * Решатель СЛАУ
 */
public interface Solver {
    /**
     * Возвращает решение СЛАУ
     * @param matrix Матрица
     * @param accuracy Допустимая погрешность вычислений
     * @return Решение СЛАУ
     * @throws SolverException Ошибка во время решения СЛАУ
     */
    Solution getSolution(Matrix matrix, double accuracy) throws SolverException;
}
