/*
* Лабораторна робота ЛР4 Варіант 28
* MO = (MD*MC)*e + max(Z)*MX
* Шестеров С. ІМ-12
* Дата 21.04.2024
* */

package ua.hoshion;

public class Lab4 {

    public static int N = 1200;
    public static int P = 4;
    public static int H = Lab4.N / Lab4.P;
    public static void main(String[] args) {
        System.out.println("Lab4 started");

        // Waiting 10 seconds for changing cores amount in Task Manager
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Saving information about start time of program
        long startTime = System.currentTimeMillis();

        // Створення Монітору
        Data monitor = new Data();

        // Initializing threads
        T1 T1 = new T1(monitor);
        T2 T2 = new T2(monitor);
        T3 T3 = new T3(monitor);
        T4 T4 = new T4(monitor);

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
        System.out.println("Time taken: " + elapsedTime + "ms");

        System.out.println("Lab4 finished");
    }
}