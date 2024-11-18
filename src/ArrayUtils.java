import java.util.Arrays;
import java.util.Random;

public class ArrayUtils {

    public static int[][] generateArray(int rows, int cols, int min, int max) {
        Random rand = new Random();
        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = rand.nextInt(max - min + 1) + min;
            }
        }
        return array;
    }

    public static void displayArray(int[][] array) {
        // Знаходимо максимальне число для визначення ширини колонки
        int maxNumber = Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .max()
                .orElse(0);
        int columnWidth = String.valueOf(maxNumber).length() + 2; // Додаємо трохи простору

        // Вивід з вирівнюванням
        for (int[] row : array) {
            for (int num : row) {
                System.out.printf("%" + columnWidth + "d", num); // Форматований вивід
            }
            System.out.println(); // Перехід на новий рядок
        }
    }
}
