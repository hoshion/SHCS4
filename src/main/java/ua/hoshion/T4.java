package ua.hoshion;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends Thread {
    @Override
    public void run() {
        System.out.println("T4 is started");
        int startIndex = 3 * Lab2.N / 4;
        int size = Lab2.N / 4;

        try {
            int f1, p1, x1;
            Arrays.fill(Data.C, 1);
            Data.x = 1;

            System.out.println("T4: S41 signal 3");
            Data.S41.release(3);
            Data.S21.acquire();
            System.out.println("T4: S21 acquired");
            Data.S31.acquire();
            System.out.println("T4: S31 acquired");

            int fi = Data.multiplyVectorsScalar(Data.B, Data.C, startIndex, size);
            Data.f.addAndGet(fi);

            int[][] ME_MM = Data.partiallyMultiplyMatrices(Data.ME, Data.MM, startIndex, size);
            Data.partiallyMultiplyVectorMatrix(Data.D, ME_MM, startIndex, size, Data.S);
            Arrays.sort(Data.S, startIndex, startIndex + size);

            System.out.println("T4: S42 signal 1");
            Data.S42.release();
            Data.S11.acquire();
            System.out.println("T4: S11 acquired");

            f1 = Data.f.get();
            synchronized (Data.CSp) {
                p1 = Data.p;
            }
            Data.Sx.acquire();
            x1 = Data.x;
            Data.Sx.release();

            int[] pS = new int[Lab2.N];
            int[] fxE = new int[Lab2.N];
            Data.partiallyMultiplyScalarVector(p1, Data.S, startIndex, size, pS);
            Data.partiallyMultiplyScalarVector(f1 * x1, Data.E, startIndex, size, fxE);
            Data.partiallyAddVectors(pS, fxE, startIndex, size, Data.A);

            System.out.println("T4: B1 barrier");
            Data.B1.await();

            Data.printResultVector("T4", Data.A);
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("T4 is finished");
    }
}
