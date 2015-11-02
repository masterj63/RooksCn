package ru.samsu.mj.rooks.type_c;

import java.util.*;

import static java.util.Arrays.fill;

class ClassificationChecker {
    private final Board[] boards;
    private final Set<Integer>[] invSort;
    private final Map<Board, Integer> boardIndMap;

    private ClassificationChecker(List<byte[]> boardsList, List<Integer>[] sort) {
        final int n = boardsList.size();

        boards = new Board[boardsList.size()];
        for (int i = 0; i < n; i++)
            boards[i] = new Board(boardsList.get(i));

        invSort = Sorter.inverseSort(sort);

        boardIndMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            boardIndMap.put(boards[i], i);
    }

    static boolean checkGrad(List<byte[]> boardsList, List<Integer>[] sort) {
        return false;//ClassificationChecker checker = new ClassificationChecker(boardsList, sort);
    }

    private static boolean rookIsGreater(int a, int b, int c, int d) {
        return a > c && b < d;
    }

    private Set<Integer> getL(int ind) {
        return invSort[ind];
    }

    private class BytePair {
        final byte b0, b1;

        public BytePair(int b0, int b1) {
            this.b0 = (byte) b0;
            this.b1 = (byte) b1;
        }
    }
}