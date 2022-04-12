package Lesson_5;

public class Lesson_5 {

    static final int size = 10_000_000;
    static final int half = size / 2;

    public static void main(String[] args) {
        float[] arr1 = new float[size];
        float[] arr2 = new float[size];

        methodArr1(arr1);
        methodArr2(arr2);
    }

    private static void methodArr1(float[] arr) {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = formula(arr[i], i);
        }

        System.out.println(System.currentTimeMillis() - a);
    }

    private static void methodArr2(float[] arr) {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        float[] a1 = new float[half];
        float[] a2 = new float[half];

        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);

        Thread thread1  =   new Thread(() -> {
            for (int i = 0; i < half; i++) {
                a1[i] = formula(a1[i], i);
            }
        });
        Thread thread2  =   new Thread(() -> {
            for (int i = 0; i < half; i++) {
                a2[i] = formula(a2[i], i);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);

        System.out.println(System.currentTimeMillis() - a);
    }

    private static float formula(float value, int index) {
        return (float) (value * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
    }
}
