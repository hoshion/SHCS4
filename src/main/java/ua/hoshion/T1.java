package ua.hoshion;

public class T1 extends Thread {

    private final Data monitor;
    private int f1, e1;

    public T1 (Data monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("T1 is started");
        int startIndex = 0;

        // Введення e, MX
        this.monitor.e = Data.readScalar();
        Data.readMatrix(Data.MX);

        // Сигнал задачі T2, T3, T4 про введення e, MX
        this.monitor.signalIn();

        // Чекати на уведення даних в задачі T3, T4
        this.monitor.waitIn();

        // Обчислення1 f1 = min(Zн)
        this.f1 = Data.partiallyMaxInVector(Data.Z, startIndex);

        // Обчислення2 f = max(f, f1)
        this.monitor.maxF(this.f1);               // КД1

        // Сигнал Т2, Т3, Т4 про завершення обчислення f
        this.monitor.signalMax();

        // Чекати на завершення обчислень f в потоках T2 T3 T4
        this.monitor.waitMax();

        // Копія f1=f
        this.f1 = this.monitor.copyF();          // КД2

        // Копія e1=e
        this.e1 = this.monitor.copyE();          // КД3

        // Обчислення3 MOн = e1*(MD*MCн) + f1*MXн
        int[][] MD_MC = Data.partiallyMultiplyMatrices(Data.MD, Data. MC, startIndex);
        int[][] e_MD_MC = Data.partiallyMultiplyMatrixScalar(MD_MC, this.e1, startIndex);
        int[][] f_MX = Data.partiallyMultiplyMatrixScalar(Data.MX, this.f1, startIndex);
        Data.partiallyAddMatrices(e_MD_MC, f_MX, startIndex, Data.MO);

        // Сигнал задачі T3 про обчислення MOн
        this.monitor.signalCalc();

        System.out.println("T1 is finished");
    }
}
