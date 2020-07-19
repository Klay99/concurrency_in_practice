import java.util.Arrays;

public class RemoveTrashFile {
    public int[] removeTrashFiles(int[] arr) {
        doSort(arr, 0, arr.length - 1);
        return arr;
    }

    public int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    public int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            for (; j > 0 && arr[j-1] >= temp; j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
        return arr;
    }

    private void doSort(int[] arr, int left, int right) {
        if (left < right) {
            int partition = partition(arr, left, right);
            doSort(arr, left, partition);
            doSort(arr, partition + 1, right);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int temp = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = temp;
        return left;
    }

    public static void main(String[] args) {
        RemoveTrashFile r = new RemoveTrashFile();
        int[] a = {5,6,2,3,8,4,9,7,1,0};
        a = r.removeTrashFiles(a);
        a = r.bubbleSort(a);
        System.out.println(Arrays.toString(a));
    }
}
