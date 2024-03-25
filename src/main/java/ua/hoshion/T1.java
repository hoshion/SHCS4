package ua.hoshion;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T1 extends Thread {
    @Override
    public void run() {
        System.out.println("T1 is started");
        int startIndex = 0;
        int size = Lab1.N / 4;

        try {
            int f1, p1, x1;

            Data.S21.acquire();
            System.out.println("T1: S21 acquired");
            Data.S31.acquire();
            System.out.println("T1: S31 acquired");
            Data.S41.acquire();
            System.out.println("T1: S41 acquired");

            int fi = Data.multiplyVectorsScalar(Data.B, Data.C, startIndex, size);
            Data.f.addAndGet(fi);

            int[][] ME_MM = Data.partiallyMultiplyMatrices(Data.ME, Data.MM, startIndex, size);
            Data.partiallyMultiplyVectorMatrix(Data.D, ME_MM, startIndex, size, Data.S);
            Arrays.sort(Data.S, startIndex, startIndex + size);

            Data.S22.acquire();
            System.out.println("T1: S22 acquired");

            Arrays.sort(Data.S, startIndex, startIndex + (Lab1.N / 2));

            Data.S32.acquire();
            System.out.println("T1: S32 acquired");

            Arrays.sort(Data.S);

            System.out.println("T1: S11 signal 3");
            Data.S11.release(3);

            f1 = Data.f.get();
            synchronized (Data.CSp) {
                p1 = Data.p;
            }
            Data.Sx.acquire();
            x1 = Data.x;
            Data.Sx.release();

            int[] pS = new int[Lab1.N];
            int[] fxE = new int[Lab1.N];
            Data.partiallyMultiplyScalarVector(p1, Data.S, startIndex, size, pS);
            Data.partiallyMultiplyScalarVector(f1 * x1, Data.E, startIndex, size, fxE);
            Data.partiallyAddVectors(pS, fxE, startIndex, size, Data.A);

            System.out.println("T1: B1 barrier");
            Data.B1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("T1 is finished");
    }
}
