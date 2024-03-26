package ua.hoshion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {

    public static int[] A = new int[Lab2.N];
    public static int[] B = new int[Lab2.N];
    public static int[] C = new int[Lab2.N];
    public static int[] D = new int[Lab2.N];
    public static int[] E = new int[Lab2.N];
    public static int[] S = new int[Lab2.N];
    public static int[][] ME = new int[Lab2.N][Lab2.N];
    public static int[][] MM = new int[Lab2.N][Lab2.N];
    public static int p;
    public static int x;
    public static Semaphore S11 = new Semaphore(0);
    public static Semaphore S21 = new Semaphore(0);
    public static Semaphore S22 = new Semaphore(0);
    public static Semaphore S31 = new Semaphore(0);
    public static Semaphore S32 = new Semaphore(0);
    public static Semaphore S41 = new Semaphore(0);
    public static Semaphore S42 = new Semaphore(0);
    public static Semaphore Sx = new Semaphore(1);
    public static final Object CSp = new Object();
    public static AtomicInteger f = new AtomicInteger(0);
    public static CyclicBarrier B1 = new CyclicBarrier(4);

    public static int[][] readMatrixFromKeyboard(String name) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = new int[Lab2.N][Lab2.N];
        System.out.println("Enter the elements of the matrix " + name + ": ");
        for (int i = 0; i < Lab2.N; i++) {
            for (int j = 0; j < Lab2.N; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    public static int[] readVectorFromKeyboard(String name) {
        Scanner scanner = new Scanner(System.in);
        int[] vector = new int[Lab2.N];
        System.out.println("Enter the elements of the vector " + name + ": ");
        for (int i = 0; i < Lab2.N; i++) {
            vector[i] = scanner.nextInt();
        }
        return vector;
    }

    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int[][] resultMatrix = new int[Lab2.N][Lab2.N];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return resultMatrix;
    }

    public static int[][] partiallyMultiplyMatrices(int[][] matrixA, int[][] matrixB, int startIndex, int size) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;

        int[][] result = new int[rowsA][colsA];

        for (int i = 0; i < rowsA; i++) {
            for (int j = startIndex; j < startIndex + size; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    public static void partiallyMultiplyVectorMatrix(int[] vector, int[][] matrix, int startIndex, int size, int[] result) {
        for (int j = startIndex; j < startIndex + size; j++) {
            result[j] = 0;
            for (int i = 0; i < vector.length; i++) {
                result[j] += vector[i] * matrix[i][j];
            }
        }
    }

    public static void partiallyMultiplyScalarVector(int scalar, int[] vector, int startIndex, int size, int[] result) {
        for (int j = startIndex; j < startIndex + size; j++) {
            result[j] = vector[j] * scalar;
        }
    }

    public static void partiallyAddVectors(int[] vector1, int[] vector2, int startIndex, int size, int[] result) {
        for (int j = startIndex; j < startIndex + size; j++) {
            result[j] = vector1[j] + vector2[j];
        }
    }

    public static int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j] * vector[j];
            }
            resultVector[i] = sum;
        }
        return resultVector;
    }

    public static int[] multiplyVectorMatrix(int[][] matrix, int[] vector) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < matrix[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += vector[j] * matrix[j][i];
            }
            resultVector[i] = sum;
        }
        return resultVector;
    }

    public static int[] multiplyVectorVector(int[] vector1, int[] vector2) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < vector1.length; i++) {
            resultVector[i] = vector1[i] * vector2[i];
        }
        return resultVector;
    }

    public static int multiplyVectorsScalar(int[] vector1, int[] vector2, int startIndex, int size) {
        int res = 0;
        for (int i = startIndex; i < startIndex + size; i++) {
            res += vector1[i] * vector2[i];
        }
        return res;
    }

    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int[][] resultMatrix = new int[Lab2.N][Lab2.N];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return resultMatrix;
    }

    public static int[] addVectors(int[] vector1, int[] vector2) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < vector1.length; i++) {
            resultVector[i] = vector1[i] + vector2[i];
        }
        return resultVector;
    }

    public static int[] addMatrixVector(int[][] matrix1, int[] vector1) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < matrix1.length; i++) {
            int sum = 0;
            for (int j = 0; j < vector1.length; j++) {
                sum += matrix1[i][j] + vector1[j];
            }
            resultVector[i] = sum;
        }
        return resultVector;
    }

    public static int[] addVectorMatrix(int[] vector1, int[][] matrix1) {
        int[] resultVector = new int[Lab2.N];
        for (int i = 0; i < matrix1[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < vector1.length; j++) {
                sum += vector1[j] + matrix1[j][i];
            }
            resultVector[i] = sum;
        }
        return resultVector;
    }

    public static void printResultVector(String threadName, int[] vector) {
        System.out.print("Result vector for " + threadName + ": ");
        for (int value : vector) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void printResultMatrix(String threadName, int[][] matrix) {
        System.out.println("Result matrix for " + threadName + ": ");
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int[][] resultMatrix = new int[Lab2.N][Lab2.N];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                resultMatrix[i][j] = matrix[j][i];
            }
        }
        return resultMatrix;
    }

    public static int[] readNumbersFromFile(int amount, String fileName) {
        try {
            if (fileName == null) {
                fileName = "file.txt";
            }
            List<Integer> numberList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    numberList.add(Integer.parseInt(token));
                }
            }
            reader.close();

            // Convert list to array
            int[] numbers = new int[amount];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = numberList.get(i);
            }
            return numbers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[][] readMatrixFromFile(String name, String fileName) {
        int[][] matrix = new int[Lab2.N][Lab2.N];
        System.out.println("Reading file for matrix " + name);
        for (int i = 0; i < Lab2.N; i++) {
            matrix[i] = readNumbersFromFile(Lab2.N, fileName);
        }
        return matrix;
    }

    public static int[] readVectorFromFile(String name, String fileName) {
        System.out.println("Reading file for vector " + name);
        return readNumbersFromFile(Lab2.N, fileName);
    }

    public static void saveVectorToFile(String fileName, int[] vector) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < vector.length; i++) {
                writer.write(Integer.toString(vector[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] copyArray(int[] original) {
        int[] copiedArray = new int[Lab2.N];
        System.arraycopy(original, 0, copiedArray, 0, original.length);
        return copiedArray;
    }

    public static int[][] copyMatrix(int[][] original) {
        int size = original.length;
        int[][] copiedMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(original[i], 0, copiedMatrix[i], 0, size);
        }

        return copiedMatrix;
    }

    public static void saveMatrixToFile(String fileName, int[][] matrix) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(Integer.toString(matrix[i][j]));
                    writer.write(" ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
