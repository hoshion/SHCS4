package ua.hoshion;

public class T2 extends Thread {

    private final Data monitor;
    private int f2, e2;

    public T2 (Data monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("T2 is started");
        int startIndex = Lab4.N / 4;

        // Чекати на уведення даних в задачі T1, T3, T4
        this.monitor.waitIn();

        // Обчислення1 f2 = min(Zн)
        this.f2 = Data.partiallyMaxInVector(Data.Z, startIndex);

        // Обчислення2 f = max(f, f2)
        this.monitor.maxF(this.f2);               // КД1

        // Сигнал Т1, Т3, Т4 про завершення обчислення f
        this.monitor.signalMax();

        // Чекати на завершення обчислень f в потоках T1 T3 T4
        this.monitor.waitMax();

        // Копія f2=f
        this.f2 = this.monitor.copyF();          // КД2

        // Копія e2=e
        this.e2 = this.monitor.copyE();          // КД3

        // Обчислення3 MOн = e2*(MD*MCн) + f2*MXн
        int[][] MD_MC = Data.partiallyMultiplyMatrices(Data.MD, Data. MC, startIndex);
        int[][] e_MD_MC = Data.partiallyMultiplyMatrixScalar(MD_MC, this.e2, startIndex);
        int[][] f_MX = Data.partiallyMultiplyMatrixScalar(Data.MX, this.f2, startIndex);
        Data.partiallyAddMatrices(e_MD_MC, f_MX, startIndex, Data.MO);

        // Сигнал задачі T3 про обчислення MOн
        this.monitor.signalCalc();

        System.out.println("T2 is finished");
    }
}
