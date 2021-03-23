package io.github.arsus1;

import io.github.arsus1.exceptions.SolverException;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * Метод простых итераций
 */
public class SimpleIterationsSolver implements Solver {
    /**
     * Проверка на соблюдение нестрогого условия
     * @param matrix Матрица
     * @return Соблюдено ли условие
     */
    private boolean matrixNormConditionMet(Matrix matrix) {
        int[] row = new int[matrix.getDimension()];
        boolean[] flag = new boolean[matrix.getDimension()];
        for (int i = 0; i < matrix.getDimension(); i++) {
            double sum = 0;
            double foo = 0;
            int max = 0;
            for (int j = 0; j < matrix.getDimension(); j++) {
                if (foo < abs(matrix.get(i, j)) && !flag[j]) {
                    max = j;
                    foo = abs(matrix.get(i, j));
                }
                sum += abs(matrix.get(i, j));
            }
            if (2 * abs(matrix.get(i, max)) >= sum && !flag[max]) {
                flag[max] = true;
                row[max] = i;
            } else return false;
        }
        double[][] element = new double[matrix.getDimension()][matrix.getDimension() + 1];
        for (int i = 0; i < matrix.getDimension(); i++) {
            for (int j = 0; j <= matrix.getDimension(); j++) {
                element[i][j] = matrix.get(row[i], j);
            }
        }
        matrix.setData(element);
        return true;
    }

    /**
     * Возвращает столбец независимых коэффициентов
     * @param matrix Матрица
     * @return Столбец независимых коэффициентов
     */
    private double[] getIndependentCoefficients(Matrix matrix) {
        double[] coefficients = new double[matrix.getDimension()];
        for (int i = 0; i < matrix.getDimension(); i++) {
            coefficients[i] = matrix.get(i, matrix.getDimension());
        }

        return coefficients;
    }

    /**
     * Возвращает матрицу коэффициентов при X
     * @param matrix Матрица
     * @return Коэффициенты при X
     */
    private double[][] getCoefficientMatrix(Matrix matrix) {
        double[][] coefficients = new double[matrix.getDimension()][matrix.getDimension()];
        for (int i = 0; i < matrix.getDimension(); i++) {
            for (int j = 0; j < matrix.getDimension(); j++) {
                coefficients[i][j] = matrix.get(i, j);
            }
        }

        return coefficients;
    }

    @Override
    public Solution getSolution(Matrix matrix, double accuracy) throws SolverException {

        if (accuracy < 0 || accuracy > 1) {
            throw new SolverException("Invalid accuracy!");
        }

        if (matrixNormConditionMet(matrix)) {

            for (int i = 0; i < matrix.getDimension(); i++) {
                double divisor = matrix.get(i, i);
                matrix.set(i, i, 0);
                for (int j = 0; j <= matrix.getDimension(); j++) {
                    matrix.set(i, j, matrix.get(i, j) / divisor);
                }
            }

            double[][] A = getCoefficientMatrix(matrix);
            double[] B = getIndependentCoefficients(matrix);
            double[] ans = new double[matrix.getDimension()];
            double[] newAns = new double[matrix.getDimension()];
            System.arraycopy(B, 0, newAns, 0, matrix.getDimension());

            double currentAcc = 0;
            long iterations = 0;
            do {
                System.arraycopy(newAns, 0, ans, 0, matrix.getDimension());

                for (int i = 0; i < matrix.getDimension(); i++) {
                    double val = 0;
                    for (int j = 0; j < matrix.getDimension(); j++) {
                        val += A[i][j] * ans[j];
                    }
                    newAns[i] = B[i] - val;
                }

                currentAcc = 0;
                for (int i = 0; i < matrix.getDimension(); i++) {
                    currentAcc = max(currentAcc, abs(ans[i] - newAns[i]));
                }

                iterations++;
            } while (currentAcc > accuracy);

            double[] error = new double[matrix.getDimension()];
            for (int i = 0; i < matrix.getDimension(); i++) {
                error[i] = abs(ans[i] - newAns[i]);
            }

            return new Solution(newAns, iterations, error);
        } else {
            throw new SolverException("Impossible to solve!");
        }
    }
}
