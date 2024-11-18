import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class ImageFinderWorkStealing {

    public static void main(String[] args) {
        // Запитуємо шлях до директорії у користувача
        System.out.print("Enter the directory path: ");
        String dirPath = new java.util.Scanner(System.in).nextLine();

        File directory = new File(dirPath);
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        // Отримуємо всі файли в директорії
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in the directory.");
            return;
        }

        long startTime = System.currentTimeMillis();
        // Створюємо ForkJoinPool для асинхронного виконання завдань
        ForkJoinPool pool = new ForkJoinPool();

        // Створення та виконання основної задачі
        ImageSearchTask task = new ImageSearchTask(files);
        List<File> imageFiles = pool.invoke(task);

        long endTime = System.currentTimeMillis();
        System.out.printf("Execution time: %d ms%n", (endTime - startTime));


        // Виведення результатів
        System.out.println("Number of image files found: " + imageFiles.size());

        // Якщо є зображення, відкриваємо останнє
        if (!imageFiles.isEmpty()) {
            File lastImage = imageFiles.get(imageFiles.size() - 1);
            System.out.println("Opening last image: " + lastImage.getAbsolutePath());
            FileUtils.openImage(lastImage);
        }

        // Завершуємо роботу ForkJoinPool
        pool.shutdown();
    }


}
