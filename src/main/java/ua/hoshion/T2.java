package ua.hoshion;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T2 extends Thread {
    @Override
    public void run() {
        System.out.println("T2 is started");
        int startIndex = Lab1.N / 4;
        int size = Lab1.N / 4;

        try {
            int f1, p1, x1;
            Arrays.fill(Data.D, 1);
            for (int i = 0; i < Lab1.N; i++) {
                Arrays.fill(Data.MM[i], 1);
            }
            Data.p = 1;

            System.out.println("T2: S21 signal 3");
            Data.S21.release(3);
            Data.S31.acquire();
            System.out.println("T2: S31 acquired");
            Data.S41.acquire();
            System.out.println("T2: S41 acquired");

            int fi = Data.multiplyVectorsScalar(Data.B, Data.C, startIndex, size);
            Data.f.addAndGet(fi);

            int[][] ME_MM = Data.partiallyMultiplyMatrices(Data.ME, Data.MM, startIndex, size);
            Data.partiallyMultiplyVectorMatrix(Data.D, ME_MM, startIndex, size, Data.S);
            Arrays.sort(Data.S, startIndex, startIndex + size);

            System.out.println("T2: S22 signal 1");
            Data.S22.release();
            Data.S11.acquire();
            System.out.println("T2: S11 acquired");

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

            System.out.println("T2: B1 barrier");
            Data.B1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("T2 is finished");
    }
}
