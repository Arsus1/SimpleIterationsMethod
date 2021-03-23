package io.github.arsus1.exceptions;

/**
 * Класс исключения, сигнализирующий о проблемах возникших во время решения
 */
public class SolverException extends RuntimeException {
    public SolverException(String message) {
        super(message);
    }

    /**
     * Создание исключения
     * @param message Сообщение для исключений
     * @param e Исключение
     */
    public SolverException(String message, Throwable e) {
        super(message, e);
    }

}
