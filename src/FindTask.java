import java.util.concurrent.RecursiveTask;

public class FindTask extends RecursiveTask<int[]> {
    private final int[][] array;
    private final int startRow, endRow;

    public FindTask(int[][] array, int startRow, int endRow) {
        this.array = array;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    protected int[] compute() {
        if (endRow - startRow <= 10) { // базовий випадок
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] == i + j) {
                        return new int[]{array[i][j], i, j}; // значення і його індекси
                    }
                }
            }
            return null;
        } else { // рекурсивний поділ
            int mid = (startRow + endRow) / 2;
            FindTask leftTask = new FindTask(array, startRow, mid);
            FindTask rightTask = new FindTask(array, mid, endRow);

            // Запускаємо обидві задачі через invokeAll
            invokeAll(leftTask, rightTask);

            // Отримуємо результати
            int[] leftResult = leftTask.join();
            int[] rightResult = rightTask.join();

            // Повертаємо перший знайдений результат
            return rightResult != null ? rightResult : leftResult;
        }
    }
}
