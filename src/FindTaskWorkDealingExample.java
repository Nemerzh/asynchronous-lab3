import java.util.concurrent.*;
import java.util.*;

public class FindTaskWorkDealingExample {
    public static void main(String[] args) throws InterruptedException {
        int rows = 100; // Кількість рядків
        int cols = 100; // Кількість стовпців
        int minValue = 1; // Мінімальне значення елементів
        int maxValue = 100; // Максимальне значення елементів

        // Генерація масиву
        int[][] array = ArrayUtils.generateArray(rows, cols, minValue, maxValue);
        System.out.println("Generated Array:");
        ArrayUtils.displayArray(array);

        // Розподіл задач (Work Dealing)
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Використовуємо список для зберігання знайдених елементів
        List<String> result = Collections.synchronizedList(new ArrayList<>());

        // Завдання на пошук елементів
        long startTime = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            final int rowIndex = i;
            futures.add(executorService.submit(() -> {
                for (int j = 0; j < cols; j++) {
                    if (array[rowIndex][j] == rowIndex + j) {
                        result.add(String.format("Element found at (i = %d, j = %d): %d", rowIndex, j, array[rowIndex][j]));
                        return; // Повертаємо зразу після знаходження елемента
                    }
                }
            }));
        }

        // Закриваємо executorService
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();

        // Виведення результатів
        if (!result.isEmpty()) {
            // Вивести перший знайдений елемент
            System.out.println(result.get(0));
        } else {
            System.out.println("No element found matching the condition.");
        }

        // Час виконання
        System.out.printf("Execution time (Work Dealing): %d ms%n", (endTime - startTime));
    }
}
