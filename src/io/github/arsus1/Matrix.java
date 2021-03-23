package io.github.arsus1;

import io.github.arsus1.exceptions.SolverException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

/**
 * Класс, представляющий матрицу
 */
public class Matrix {
    private final int dimension;
    private final double[][] data;

    /**
     * Создание матрицы из массива данных и размера
     * @param data Коэффициенты матрицы
     * @param dimension Количество уравнений
     * @return Возвращает созданную матрицу
     */
    public static Matrix createMatrix(double[][] data, int dimension) {
        return new Matrix(data, dimension);
    }

    /**
     * Генерирование матрицу со случайными коэффициентами в заданном диапазоне
     * @param dimension Количество уравнений
     * @param min Минимальное число
     * @param max Максимальное число
     * @return Возвращает заполненную матрицу
     */
    public static Matrix generateRandom(int dimension, double min, double max) {
        double[][] data = new double[dimension][dimension + 1];
        Random random = new Random();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j <= dimension; j++) {
                data[i][j] = (random.nextDouble() * (max - min)) + min;
            }
        }

        return new Matrix(data, dimension);
    }

    /**
     * Загрузка матрицы из файла
     * @param path Путь до файла
     * @return Возвращает матрицу из файла
     */
    public static Matrix load(Path path) {
        try {
            return load(new FileInputStream(path.toFile()));
        } catch (IOException e) {
            throw new SolverException("Unable to load matrix!", e);
        }
    }

    /**
     * Ввод матрицы из потока данных
     * @param is Входной поток
     * @return Возвращает матрицу из потока данных
     */
    public static Matrix load(InputStream is) {
        Scanner scanner = new Scanner(is);
        int dimension = scanner.nextInt();
        double[][] data = new double[dimension][dimension + 1];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j <= dimension; j++) {
                data[i][j] = scanner.nextDouble();
            }
        }

        return new Matrix(data, dimension);

    }

    /**
     * Создаёт матрицу
     * @param data Коэффициент матрицы
     * @param dimension Количество уравнений
     */
    private Matrix(double[][] data, int dimension) {
        this.data = data;
        this.dimension = dimension;
    }

    /**
     * Возвращает решение системы уравнений
     * @param solver Решатель системы уравнений
     * @param accuracy Погрешность результата
     * @return Возвращает системы уравнений
     * @throws SolverException При невозможности решить СЛАУ
     */
    public Solution solve(Solver solver, double accuracy) throws SolverException {
        return solver.getSolution(this, accuracy);
    }

    /**
     * Возвращает число уравнений
     * @return Число уравнений
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Возвращает коэффициент матрицы по положению в матрице
     * @param x Положение по X
     * @param y Положение по Y
     * @return Коэффициент матрицы
     */
    public double get(int x, int y) {
        if (x >= dimension || x < 0 || y > dimension || y < 0) {
            throw new SolverException("Wrong bounds: {x: " + x + ", y: " + y + "}");
        }
        return data[x][y];
    }

    /**
     * Устанавливает коэффициент матрицы
     * @param x Коэффициент X
     * @param y Коэффициент Y
     * @param val Новое значение коэффициента
     */
    public void set(int x, int y, double val) {
        if (x >= dimension || x < 0 || y > dimension || y < 0) {
            throw new SolverException("Wrong bounds: {x: " + x + ", y: " + y + "}");
        }
        data[x][y] = val;
    }

    /**
     * Устанавливает новую матрицу коэффициентов
     * @param data Коэффициенты матрицы
     */
    public void setData(double[][] data) {
        if (data.length != dimension || data[0].length != dimension + 1) {
            throw new SolverException("Wrong data!");
        }
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, dimension + 1);
        }
    }

    /**
     * Текстовое представление матрицы
     * @return Возвращает текст
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j <= dimension; j++) {
                sb.append(data[i][j]).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
