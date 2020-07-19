import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;

public class Test {
    public static int[] bubbleSort(int[] arr) {
        int[] res = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length - i - 1; j++) {
                if (res[j] >= res[j+1]) {
                    int temp = res[j];
                    res[j] = res[j+1];
                    res[j+1] = temp;
                }
            }
        }
        return res;
    }

    public static int[] insertionSort(int[] arr) {
        int[] res = Arrays.copyOf(arr, arr.length);
        for (int i = 1; i < res.length; i++) {
            int j = i;
            for (; j > 0 && res[j] <= res[j-1]; j--) {
                int temp = res[j];
                res[j] = res[j-1];
                res[j-1] = temp;
            }
        }
        return res;
    }

    public static int[] mergeSort(int[] arr) {
        int[] res = Arrays.copyOf(arr, arr.length);
        int[] temp = new int[arr.length];
        doMergeSort(res, temp, 0, res.length - 1);
        return res;
    }

    private static void doMergeSort(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            doMergeSort(arr, temp, left, mid);
            doMergeSort(arr, temp, mid + 1, right);
            merge(arr, temp, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        System.arraycopy(arr, left, temp, left, right - left + 1);
        int leftCurr = left;
        int rightCurr = mid + 1;
        int tempCurr = left;
        while (leftCurr <= mid && rightCurr <= right) {
            if (temp[leftCurr] <= temp[rightCurr]) {
                arr[tempCurr++] = temp[leftCurr++];
            } else {
                arr[tempCurr++] = temp[rightCurr++];
            }
        }
        while (leftCurr <= mid) {
            arr[tempCurr++] = temp[leftCurr++];
        }
        while (rightCurr <= right) {
            arr[tempCurr++] = temp[rightCurr++];
        }
    }

    public static int[] selectionSort(int[] arr) {
        int[] res = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < res.length; i++) {
            int min = i;
            int j = i + 1;
            for (; j < res.length; j++) {
                if (res[j] <= res[min]) {
                    min = j;
                }
            }
            int temp = res[i];
            res[i] = res[min];
            res[min] = temp;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {5,8,7,9,3,1,0,4,6,2};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(bubbleSort(arr)));
        System.out.println(Arrays.toString(insertionSort(arr)));
        System.out.println(Arrays.toString(selectionSort(arr)));
        System.out.println(Arrays.toString(mergeSort(arr)));
    }
}
