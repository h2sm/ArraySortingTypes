import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new PerformanceTester().test(new BubbleSort(), new InsertionSort(),new SelectionSort(), new MergeSort());
    }
}
abstract class SortingAlgorithm{
    abstract void sortAsc(double[] arr);//сортировка массива по возрастанию
    abstract void sortDesc(double [] values);//сортировка массива по убыванию
}
class PerformanceTester{
    void test(SortingAlgorithm ... algs){
        int[] numIters = {1000, 1000, 100, 100, 10};
        int[] numElems = {100, 1000, 10000, 100000, 1000000 };
        for (var alg:algs) {
            for (int i=0; i<5; i++){//i номер набора
                long time =0;
                for (int iters=0;iters<numIters[i];iters++){//количество итераций
                    double [] arr = createNewRandomDoubleArray(numElems[i]);//создаем новый массив даблов
                    long start = System.currentTimeMillis();//начинаем отсчет
                    alg.sortAsc(arr);//сортировка по возрастанию
                    //alg.sortDesc(arr);
                    long end = System.currentTimeMillis();//заканчиваем отсчет
                    time += (end - start);
                    System.out.println("Итерация:" + iters + " NUMITERS:" + numIters[i] + " NUMELEMS:" + numElems[i] + " Время: " + time + " Среднее время:" + (double) time/numIters[i]);
                }

            }
        }
    }
    private Random ran = new Random();
    double[] createNewRandomDoubleArray(Integer numElems){
        double[]arr = new double[numElems];
        for (int i = 0; i<numElems;i++){
            arr[i] = ran.nextDouble();
            //System.out.println(arr[i]);//генерируем arr
        }
        return arr;
    }
}
class BubbleSort extends SortingAlgorithm{
    void sortAsc(double[] arr){
        int n = arr.length;
        double temp = 0;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr[j-1] > arr[j]){
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
    void sortDesc(double[] arr){
        int i, j;
        double temp=0;
        int num = arr.length;
        for ( i = 0; i < (num - 1); i++) {
            for (j = 0; j < num - i - 1; j++) {
                if (arr[j] < arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
class InsertionSort extends SortingAlgorithm{
    void sortAsc(double[] arr) {
        int n = arr.length;
        for (int j = 1; j < n; j++) {
            double key = arr[j];
            int i = j-1;
            while ( (i > -1) && ( arr[i] > key ) ) {
                arr[i+1] = arr[i];
                i--;
            }
            arr[i+1] = key;
        }
    }
    void sortDesc(double[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            double valueToSort = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] < valueToSort) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = valueToSort;
        }
    }
}
class SelectionSort extends SortingAlgorithm{
    void sortAsc(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min])
                    min = j;
            double temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }
    void sortDesc(double[] values) {
        int n = values.length;
        for(int i = 0; i < n-1; i++){
            int maxPosition=i;
            int minPosition=i;
            for(int j = i+1; j < n; j++){
                if(values[j] < values[minPosition]){
                    minPosition = j;
                }
                if(values[j] > values[maxPosition]){
                    maxPosition = j;
                }
            }
            swap(values,maxPosition,i);
            swap(values,minPosition,n-i-1);
        }
    }
    void swap(double[] values, int i, int j){
        double temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }
}
class MergeSort extends SortingAlgorithm{
    void sortAsc(double[] arr) {
        mergeSortAsc(arr, arr.length);
    }
    void mergeSortAsc(double[] arr, int len){
        if (len<2) return;
        int mid = len/2;
        double[] left = new double[mid];
        double[] right = new double[len - mid];
        for (int i=0;i<mid;i++) left[i]=arr[i];
        for (int i=mid;i<len;i++) right[i-mid]=arr[i];
        mergeSortAsc(left,mid);
        mergeSortAsc(right,len-mid);
        mergeAscMethod(arr, left, right, mid, len-mid);
    }
    void mergeAscMethod(double[] arr, double[] left, double [] right, int l, int r){
        int i=0, j=0, k=0;
        while (i<l && j< r){
            if (left[i] > right[i]) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while (i <l) arr[k++]=left[i++];
        while (j<r) arr[k++] = right[j++];
    }
    void sortDesc(double[] arr) {
        mergeSortDesc(arr, arr.length);
    }
    void mergeSortDesc(double[] arr, int len){
        if (len<2) return;
        int mid = len/2;
        double[] left = new double[mid];
        double[] right = new double[len-mid];

        for (int i=0; i<mid;i++) left[i] = arr[i];
        for (int i=mid;i<len;i++) right[i-mid] = arr[i];
        mergeSortDesc(left,mid);
        mergeSortDesc(right,len-mid);
        mergeDescMethod(arr, left, right, mid, len-mid);
    }
    void mergeDescMethod(double[] arr, double[] left, double[] right, int l, int r){
        int i=0, j=0, k=0;
        while (i<l && j<r){
            if (left[i] <= right[j]) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while (i<l) arr[k++] = left[i++];
        while (j<r) arr[k++] = right[j++];
    }



}
