package io.github.arsus1;

import io.github.arsus1.exceptions.SolverException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Консольный интерфейс
 */
public class Cli {
    private final InputStream is;
    private final OutputStream os;
    /**
     * Описание команд
     */
    private final List<String> commands = List.of(
            "solve", "Solves linear equations system.",
            "exit", "Exit solver.",
            "help", "Prints help."
    );

    /**
     * Конструктор
     * @param is Входной поток
     * @param os Выходной поток
     */
    public Cli(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }

    /**
     * Печать приветственного сообщения
     * @param ps Поток печати
     */
    private void printGreeting(PrintStream ps) {
        ps.println("Welcome to the Lab 1 \"Simple iterations solver for system of linear equations\"!");
        ps.println("Print \"help\" to get info about possible commands.");
    }

    /**
     * Меню выбора ввода матрицы
     * @param scanner Парсит входной поток
     * @param ps Позволяет выводить сообщения решателя
     */
    private void solveDialog(Scanner scanner, PrintStream ps) {
        ps.println("To solve system of linear equations choose input method:");
        ps.println("1) Read from file");
        ps.println("2) User input");
        ps.println("3) Generate matrix");
        String token = scanner.next();
        try {
            Matrix matrix;
            double accuracy;
            switch (token) {
                case "1" -> {
                    ps.println("Enter file path:");
                    token = scanner.next();
                    Path path = Path.of(token);
                    matrix = Matrix.load(path);
                    ps.println("Enter accuracy: ");
                    token = scanner.next();
                    accuracy = Double.parseDouble(token);
                }

                case "2" -> {
                    ps.println("Enter matrix dimension and coefficients:");
                    matrix = Matrix.load(is);
                    ps.println("Enter accuracy: ");
                    token = scanner.next();
                    accuracy = Double.parseDouble(token);
                }

                case "3" -> {
                    ps.println("Enter matrix dimension:");
                    int dim = scanner.nextInt();
                    ps.println("Enter coefficients min value:");
                    int min = scanner.nextInt();
                    ps.println("Enter coefficients max value:");
                    int max = scanner.nextInt();
                    matrix = Matrix.generateRandom(dim, min, max);
                    ps.println("Enter accuracy: ");
                    token = scanner.next();
                    accuracy = Double.parseDouble(token);
                }

                default -> {
                    ps.println("Unknown command!");
                    return;
                }
            }

            ps.println("Matrix:");
            ps.println(matrix.toString());

            Solution solution = matrix.solve(new SimpleIterationsSolver(), accuracy);
            ps.println(solution.toString());
        } catch (NumberFormatException nfe) {
            ps.println("Error with accuracy value!");
        } catch (SolverException sol) {
            ps.println(sol.getMessage());
        }
    }

    /**
     * Запускает выполнение консоли
     */
    public void execute() {
        Scanner scanner = new Scanner(is);
        PrintStream ps = new PrintStream(os);
        printGreeting(ps);
        while (scanner.hasNext()) {
            String token = scanner.next();
            switch (token) {
                case "solve" -> solveDialog(scanner, ps);

                case "help" -> {
                    for (int i = 0; i < commands.size(); i += 2) {
                        ps.println("\"" + commands.get(i) + "\" - " + commands.get(i + 1));
                    }
                }

                case "exit" -> System.exit(0);

                default -> ps.println("Unknown command!");
            }
        }
    }


    public static void main(String[] args) {
        Cli cli = new Cli(System.in, System.out);
        cli.execute();
    }

}
