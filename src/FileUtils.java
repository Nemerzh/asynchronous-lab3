import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    // Метод для відкриття зображення
    public static void openImage(File imageFile) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(imageFile); // Відкриває зображення у стандартному переглядачі
            } catch (IOException e) {
                System.err.println("Failed to open image: " + e.getMessage());
            }
        } else {
            System.err.println("Desktop is not supported on this platform.");
        }
    }

    // Перевірка чи є файл зображенням
    public static boolean isImage(File file, String[] IMAGE_EXTENSIONS) {
        for (String ext : IMAGE_EXTENSIONS) {
            if (file.getName().toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Допоміжний метод для поділу масиву файлів на підмасиви
    public static File[] sliceArray(File[] array, int start, int end) {
        File[] slice = new File[end - start];
        System.arraycopy(array, start, slice, 0, slice.length);
        return slice;
    }
}
