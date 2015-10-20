package ru.samsu.mj.rooks.type_c;

import java.util.ArrayList;
import java.util.List;

public class ListPositionByN {
    private static List<byte[]> res;
    private static byte[] arr;

    static List<byte[]> get() {
        res = new ArrayList<>();
        arr = new byte[Main.N];
        rec(0);
        return res;
    }

    private static void rec(int pos) {
        if (pos == Main.N) {
            res.add(arr.clone());
            return;
        }

        arr[pos] = -1;
        rec(1 + pos);

        for (byte j = 0; j < pos; j++) {
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
            rec(1 + pos);
        }

        arr[pos] = -1;
    }
}
