/*
* Лабораторна робота ЛР2 Варіант 4
* A = sort(D*(ME*MM))*p + (B*C)*E*x
* Шестеров С. ІМ-12
* Дата 25.03.2024
* */

package ua.hoshion;

public class Lab2 {

    public static int N = 1200;
    public static void main(String[] args) {

        // Waiting 10 seconds for changing cores amount in Task Manager
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Saving information about start time of program
        long startTime = System.currentTimeMillis();

        // Initializing threads
        T1 T1 = new T1();
        T2 T2 = new T2();
        T3 T3 = new T3();
        T4 T4 = new T4();

        // Starting threads
        T1.start();
        T2.start();
        T3.start();
        T4.start();

        // Making main thread wait for other child threads
        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Saving information about end time of program
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        // Showing info about taken time
        System.out.println("Time taken: " + elapsedTime + " nanoseconds");
    }
}