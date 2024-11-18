import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

// Клас для пошуку зображень у масиві файлів з використанням Fork/Join
public class ImageSearchTask extends RecursiveTask<List<File>> {

    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff"};
    private static final int THRESHOLD = 10; // Поріг для розбиття задачі
    private final File[] files;

    public ImageSearchTask(File[] files) {
        this.files = files;
    }

    @Override
    protected List<File> compute() {
        List<File> imageFiles = new ArrayList<>();

        if (files.length <= THRESHOLD) {
            // Якщо кількість файлів менша або дорівнює порогу, обробляємо їх в межах поточного завдання
            for (File file : files) {
                if (FileUtils.isImage(file, IMAGE_EXTENSIONS)) {
                    imageFiles.add(file);
                }
            }
        } else {
            // Якщо файлів багато, розбиваємо задачу на підзадачі
            List<ImageSearchTask> subtasks = new ArrayList<>();
            int mid = files.length / 2;

            // Розбиваємо масив файлів на дві частини і створюємо підзадачі
            ImageSearchTask subtask1 = new ImageSearchTask(FileUtils.sliceArray(files, 0, mid));
            ImageSearchTask subtask2 = new ImageSearchTask(FileUtils.sliceArray(files, mid, files.length));

            subtasks.add(subtask1);
            subtasks.add(subtask2);

            // Використовуємо invokeAll для запуску всіх підзадач
            invokeAll(subtasks);

            // Чекаємо на результат виконання підзадач
            for (ImageSearchTask subtask : subtasks) {
                imageFiles.addAll(subtask.join()); // Join результатів підзадач
            }
        }

        return imageFiles;
    }
}