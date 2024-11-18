import java.util.concurrent.ForkJoinPool;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FindTaskWorkStealingExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = 0, cols = 0, min = 0, max = 0;

        // Введення кількості рядків
        while (rows <= 0) {
            System.out.print("Enter the number of rows (positive integer): ");
            rows = getValidatedIntInput(scanner);
        }

        // Введення кількості стовпців
        while (cols <= 0) {
            System.out.print("Enter the number of columns (positive integer): ");
            cols = getValidatedIntInput(scanner);
        }

        // Введення мінімального значення
        System.out.print("Enter the minimum value: ");
        min = getValidatedIntInput(scanner);

        // Введення максимального значення
        while (max <= min) {
            System.out.print("Enter the maximum value (greater than minimum): ");
            max = getValidatedIntInput(scanner);
        }

        // Генерація масиву
        int[][] array = ArrayUtils.generateArray(rows, cols, min, max);
        System.out.println("\nGenerated array:");
        ArrayUtils.displayArray(array);

        // Запуск Fork/Join
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = ForkJoinPool.commonPool();

        int[] result = pool.invoke(new FindTask(array, 0, rows));
        long endTime = System.currentTimeMillis();

        // Вивід результату
        if (result != null) {
            System.out.printf("Found element: %d at (i = %d, j = %d)%n", result[0], result[1], result[2]);
        } else {
            System.out.println("No element found matching the condition.");
        }

        System.out.printf("Execution time: %d ms%n", (endTime - startTime));
    }


    private static int getValidatedIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter an integer: ");
                scanner.next(); // очищення некоректного вводу
            }
        }
    }
}

