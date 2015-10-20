package ru.samsu.mj.rooks.type_c;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPositionByN {
    private static List<byte[]> res;
    private static byte[] arr;

    static List<byte[]> get() {
        res = new ArrayList<>();
        arr = new byte[Main.N];
        Arrays.fill(arr, (byte) -1);
        rec(0);
        return res;
    }

    private static void rec(int pos) {
        if (pos == Main.N) {
            res.add(arr.clone());
            return;
        }

        rec(1 + pos);

        int maxPos = Math.min(pos, Main.N - pos);

        for (byte j = 0; j < maxPos; j++) {
            if (arr[pos] != -1)
                continue;
            boolean used = false;
            for (int k = 0; k < pos; k++)
                used |= j == arr[k];

            byte d = (byte) (Main.N - 1 - pos - j);
            byte pos2 = (byte) (pos + d);
            byte j2 = (byte) (j + d);
            used |= j2 == arr[pos2];

            if (used)
                continue;

            arr[pos] = j;

            int r = Main.N - pos - j - 1;
            int i1 = pos + r, i2 = j + d;
            arr[i1] = (byte) i2;

            rec(1 + pos);

            arr[i1] = -1;
            arr[pos] = -1;
        }

    }
}
