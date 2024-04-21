package ua.hoshion;

import java.util.Arrays;

public class Data {

    public static int[] Z = new int[Lab4.N];
    public static int[][] MD = new int[Lab4.N][Lab4.N];
    public static int[][] MC = new int[Lab4.N][Lab4.N];
    public static int[][] MX = new int[Lab4.N][Lab4.N];
    public static int[][] MO = new int[Lab4.N][Lab4.N];
    public int e;
    private int f;
    private int fIn = 0;
    private int fMax = 0;
    private int fCalc = 0;

    ///////////////////////////////////////////////////
    //// READ METHODS                              ////
    ///////////////////////////////////////////////////

    public static int readScalar() {
        return 1;
    }

    public static void readVector(int[] vector) {
        Arrays.fill(vector, 1);
    }

    public static void readMatrix(int[][] matrix) {
        for (int[] vector : matrix) {
            Arrays.fill(vector, 1);
        }
    }

    ///////////////////////////////////////////////////
    //// MONITOR METHODS                           ////
    ///////////////////////////////////////////////////

    public synchronized void signalIn() {
        this.fIn++;
        if (this.fIn == 3) {
            this.notifyAll();
        }
    }

    public synchronized void waitIn() {
        try {
            if (this.fIn != 3) {
                this.wait();
            }
        } catch (Exception ignored) {}
    }

    public synchronized void maxF(int value) {
        this.f = Math.max(this.f, value);
    }

    public synchronized void signalMax() {
        this.fMax++;
        if (this.fMax == 4) {
            this.notifyAll();
        }
    }

    public synchronized void waitMax() {
        try {
            if (this.fMax != 4) {
                this.wait();
            }
        } catch (Exception ignored) {}
    }

    public synchronized int copyF() {
        return this.f;
    }

    public synchronized int copyE() {
        return this.e;
    }

    public synchronized void signalCalc() {
        this.fCalc++;
        if (this.fCalc == 3) {
            this.notifyAll();
        }
    }

    public synchronized void waitCalc() {
        try {
            if (this.fCalc != 3) {
                this.wait();
            }
        } catch (Exception ignored) {}
    }

    ///////////////////////////////////////////////////
    //// PARTIAL METHODS                           ////
    ///////////////////////////////////////////////////

    public static int partiallyMaxInVector(int[] vector, int startIndex) {
        int max = vector[startIndex];
        for (int i = startIndex; i < startIndex + Lab4.H; i++) {
            if (vector[i] > max) {
                max = vector[i];
            }
        }
        return max;
    }

    public static int[][] partiallyMultiplyMatrices(int[][] matrixA, int[][] matrixB, int startIndex) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;

        int[][] result = new int[rowsA][colsA];

        for (int i = 0; i < rowsA; i++) {
            for (int j = startIndex; j < startIndex + Lab4.H; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    public static int[][] partiallyMultiplyMatrixScalar(int[][] matrix, int scalar, int startIndex) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = startIndex; j < startIndex + Lab4.H; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return result;
    }

    public static void partiallyAddMatrices(int[][] matrix1, int[][] matrix2, int startIndex, int[][] result) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = startIndex; j < startIndex + Lab4.H; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
    }

    ///////////////////////////////////////////////////
    //// PRINT METHODS                             ////
    ///////////////////////////////////////////////////

    public static void printResultMatrix(String threadName, int[][] matrix) {
        System.out.println("Result matrix for " + threadName + ": ");
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}
