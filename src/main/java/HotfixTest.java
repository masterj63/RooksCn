import java.util.List;

public class HotfixTest {
    static void doTest(List<byte[]> boardsList, byte[][][] matrices) {
        final int n = boardsList.size();
        for (int i0 = 0; i0 < n; i0++)
            for (int i1 = 0; i1 < n; i1++) {
                if (i0 == i1)
                    continue;

                if (MatrixDimaComparator.compare(
                        boardsList.get(i0), boardsList.get(i1),
                        matrices[i0], matrices[i1]
                ) != -1)
                    continue;

                for (int i2 = 0; i2 < n; i2++) {
                    if (i2 == i0 || i2 == i1)
                        continue;
                    if (MatrixDimaComparator.compare(
                            boardsList.get(i1), boardsList.get(i2),
                            matrices[i1], matrices[i2]
                    ) != -1)
                        continue;

                    if (MatrixDimaComparator.compare(
                            boardsList.get(i1), boardsList.get(i2),
                            matrices[i1], matrices[i2]
                    ) != -1)
                        Main.toReport.append("\nNOT TRANSITIVE");
//                    else
//                        Main.toReport.append("\nok...");
                }
            }
    }
}
