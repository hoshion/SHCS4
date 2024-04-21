package ua.hoshion;

public class T4 extends Thread {

    private final Data monitor;
    private int f4, e4;

    public T4 (Data monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("T4 is started");
        int startIndex = 3 * Lab4.N / 4;

        // Введення Z, MD
        Data.readVector(Data.Z);
        Data.readMatrix(Data.MD);

        // Сигнал задачі T1, T2, T3 про введення z, MD
        this.monitor.signalIn();

        // Чекати на уведення даних в задачі T1, T3
        this.monitor.waitIn();

        // Обчислення1 f4 = min(Zн)
        this.f4 = Data.partiallyMaxInVector(Data.Z, startIndex);

        // Обчислення2 f = max(f, f4)
        this.monitor.maxF(this.f4);               // КД1

        // Сигнал Т1, Т2, Т3 про завершення обчислення f
        this.monitor.signalMax();

        // Чекати на завершення обчислень f в потоках T1 T2 T3
        this.monitor.waitMax();

        // Копія f4=f
        this.f4 = this.monitor.copyF();          // КД2

        // Копія e1=e
        this.e4 = this.monitor.copyE();          // КД3

        // Обчислення3 MOн = e4*(MD*MCн) + f4*MXн
        int[][] MD_MC = Data.partiallyMultiplyMatrices(Data.MD, Data. MC, startIndex);
        int[][] e_MD_MC = Data.partiallyMultiplyMatrixScalar(MD_MC, this.e4, startIndex);
        int[][] f_MX = Data.partiallyMultiplyMatrixScalar(Data.MX, this.f4, startIndex);
        Data.partiallyAddMatrices(e_MD_MC, f_MX, startIndex, Data.MO);

        // Сигнал задачі T3 про обчислення MOн
        this.monitor.signalCalc();

        System.out.println("T4 is finished");
    }
}
