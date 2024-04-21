package ua.hoshion;

public class T3 extends Thread {

    private final Data monitor;
    private int f3, e3;

    public T3 (Data monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("T3 is started");
        int startIndex = Lab4.N / 2;

        // Введення e, MX
        Data.readMatrix(Data.MC);

        // Сигнал задачі T1, T2, T4 про введення MC
        this.monitor.signalIn();

        // Чекати на уведення даних в задачі T1, T4
        this.monitor.waitIn();

        // Обчислення1 f3 = min(Zн)
        this.f3 = Data.partiallyMaxInVector(Data.Z, startIndex);

        // Обчислення2 f = max(f, f3)
        this.monitor.maxF(this.f3);               // КД1

        // Сигнал Т1, Т2, Т4 про завершення обчислення f
        this.monitor.signalMax();

        // Чекати на завершення обчислень f в потоках T1 T2 T4
        this.monitor.waitMax();

        // Копія f3=f
        this.f3 = this.monitor.copyF();          // КД2

        // Копія e3=e
        this.e3 = this.monitor.copyE();          // КД3

        // Обчислення3 MOн = e3*(MD*MCн) + f3*MXн
        int[][] MD_MC = Data.partiallyMultiplyMatrices(Data.MD, Data. MC, startIndex);
        int[][] e_MD_MC = Data.partiallyMultiplyMatrixScalar(MD_MC, this.e3, startIndex);
        int[][] f_MX = Data.partiallyMultiplyMatrixScalar(Data.MX, this.f3, startIndex);
        Data.partiallyAddMatrices(e_MD_MC, f_MX, startIndex, Data.MO);

        // Чекати обчислення MOн в потоках T1 T2 T4
        this.monitor.waitCalc();

        // Виведення MO
        Data.printResultMatrix("T3", Data.MO);

        System.out.println("T3 is finished");
    }
}
