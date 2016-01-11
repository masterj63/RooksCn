import java.util.ArrayList;
import java.util.List;

public class MatrixDimaComparator {
    static int compare(byte[] a, byte[] b, byte[][] aa, byte[][] bb) {
        int t = MatrixComparator.compare(aa, bb);
        if (t == 0) return 0;
        if (t == -1)
            return -compare(b, a, bb, aa);
        boolean ok = true;
        for (byte[] chain : chained(b)) {
            byte j1 = chain[1];
            byte j2 = chain[2];
            byte j3 = chain[3];
            byte[] newb = b.clone();
            newb[j2] = -1;
            newb[j3] = -1;
            newb[j3] = j1;
            byte[][] newbb = MatricesByListPosition.positionToMatrix(newb);
            boolean ok2 = false;
            ok2 |= +1 == MatrixComparator.compare(aa, newbb);
            ok2 |= (row(j2, a) >= row(j2, b) && col(j2, a) >= col(j2, b));
            ok &= ok2;
        }
        if (ok)
            return +1;
        else
            return 0;
    }

    private static byte row(byte j, byte[] a) {
        return a[j];
    }

    private static byte col(byte j, byte[] a) {
        for (byte i = 0; i < a.length; i++)
            if (a[i] == j)
                return i;
        return -1;
    }

    private static List<byte[]> chained(byte[] a) {
        List<byte[]> res = new ArrayList<>();
        for (byte j2 = 0; j2 < a.length; j2++) {
            byte j1 = a[j2];
            if (j1 == -1)
                continue;
            for (byte j3 = 0; j3 < a.length; j3++)
                if (a[j3] == j2) {
                    byte[] t = new byte[4];
                    t[1] = j1;
                    t[2] = j2;
                    t[3] = j3;
                    res.add(t);
                }
        }
        return res;
    }
}
