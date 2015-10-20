import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Canvas {
    private final JPanel jPanel;

    private final int pxBetweenNum;
    private final int pxBetweenMat;

    private int maxWidth = 0, maxHeight = 0;
    private final static int W = 180, H = 60;//max dim of label; note this if something goes wrong

    private Map<Integer, Point> indCoordMap = new HashMap<>();
    private List<JLabel> matricesJLabelList = new ArrayList<>();
    private List<JLabel> boardsJLabelList = new ArrayList<>();

    static void draw(List<List<Integer>> layers, List<Integer>[] sort, List<byte[]> boards, byte[][][] matrices) {
        new Canvas(layers, sort, boards, matrices);
    }

    private Canvas(List<List<Integer>> layers, List<Integer>[] sort, List<byte[]> boards, byte[][][] matrices) {
        pxBetweenNum = 15;
        pxBetweenMat = 200 + Main.N * pxBetweenNum;

        JFrame jFrame = new JFrame("Rooks");
        jFrame.setPreferredSize(new Dimension(800, 600));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel = new JPanel(null);

        JScrollPane jScrollPane = new JScrollPane(jPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame.setContentPane(jScrollPane);

        computeCoordinates(layers);
        initializeMatricesJLabelList(matrices, layers);
        initializeBoardsJLabelList(boards, layers);

        jPanel.setPreferredSize(new Dimension(maxWidth, maxHeight));
        jPanel.addMouseListener(new RepaintMouseListener());

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.repaint();

        drawArrows(sort);
    }

    private void drawArrows(final List<Integer>[] sort) {
        JPanel jp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < sort.length; i++)
                    for (int j : sort[i]) {
                        Point from = indCoordMap.get(i);
                        Point to = indCoordMap.get(j);

//                        BufferedImage img = ArrowFactory.arrow(from.x, from.y, to.x, to.y);
//                        g.drawImage(img, from.x, from.y, null);
                        g.setColor(Color.BLACK);
                        int dy = Main.N * pxBetweenNum;
                        g.drawLine(from.x + 10 * pxBetweenNum, from.y + dy, to.x - pxBetweenNum, to.y + dy);
                    }
            }
        };
        jp.setOpaque(false);
        jp.setBounds(0, 0, jPanel.getWidth(), jPanel.getHeight());
        jPanel.add(jp);

    }

    private void computeCoordinates(List<List<Integer>> layers) {
        assert indCoordMap.isEmpty();

        int x = 0;
        for (List<Integer> lay : layers) {
            int y = 0;
            for (Integer ind : lay) {
                Point coord = new Point(x * pxBetweenMat, y * pxBetweenMat);
                indCoordMap.put(ind, coord);
                y++;
            }
            x++;
        }
    }

    private void initializeBoardsJLabelList(List<byte[]> boards, List<List<Integer>> layers) {
        assert boardsJLabelList.isEmpty();
        assert !indCoordMap.isEmpty();

        for (int i = 0; i < layers.size(); i++)
            for (int j : layers.get(i))
                initializeBoardJLabel(boards.get(j), indCoordMap.get(j), i);
    }

    private void initializeBoardJLabel(byte[] board, Point point, int layerIndex) {
        int n = board.length;
        int x = point.x, y = point.y;

        for (int i = 0; i < n; i++) {
            StringBuilder rowString = new StringBuilder();
            rowString.append("<html><p style=\"font-family: Courier New, monospace\">");

            for (int j = 0; j < n; j++) {
                if (board[i] == j)
                    rowString.append('\u2656');//rook here
                else if (i <= j)
                    rowString.append('\u2613');//dark cell here
                else
                    rowString.append('\u2610');//light cell here
                rowString.append(' ');
            }

            rowString.append("</p></html>");
            JLabel jLabel = new JLabel(rowString.toString());

            int tx = x;
            int ty = y + i * pxBetweenNum;

            jLabel.setBounds(tx, ty, W, H);
            maxWidth = Math.max(maxWidth, tx + W);
            maxHeight = Math.max(maxHeight, ty + H);

            boardsJLabelList.add(jLabel);
        }

        {
            int rho = RankUtils.hypotheticalRank(board);
            String footnote = "ker rho:" + rho;

            JLabel jLabel = new JLabel(footnote);
            jLabel.setBounds(x, y + n * pxBetweenNum, W, H);
            maxWidth = Math.max(maxWidth, x + W);
            maxHeight = Math.max(maxHeight, y + n * pxBetweenNum + H);
            boardsJLabelList.add(jLabel);
        }

        {
            n += 1;
            String footnote = "act rho:" + layerIndex;

            JLabel jLabel = new JLabel(footnote);
            jLabel.setBounds(x, y + n * pxBetweenNum, W, H);
            maxWidth = Math.max(maxWidth, x + W);
            maxHeight = Math.max(maxHeight, y + n * pxBetweenNum + H);
            boardsJLabelList.add(jLabel);
            n -= 1;
        }
    }

    private void initializeMatricesJLabelList(byte[][][] matrices, List<List<Integer>> layers) {
        assert matricesJLabelList.isEmpty();
        assert !indCoordMap.isEmpty();

        for (List<Integer> list : layers)
            for (int i : list)
                initializeMatrixJLabel(matrices[i], indCoordMap.get(i));
    }

    private void initializeMatrixJLabel(byte[][] mat, Point point) {
        int n = mat.length;
        int x = point.x, y = point.y;

        for (int i = 0; i < n; i++) {
            StringBuilder rowString = new StringBuilder();
            rowString.append("<html>");
            for (int j = 0; j < n; j++) {
                if (mat[i].length < j)
                    rowString.append(0);
                else if (mat[i].length == j)
                    rowString.append("<font color='#D8D8D8'>0");
                else
                    rowString.append(mat[i][j]);
                rowString.append("&nbsp;");
                rowString.append("&nbsp;");
                rowString.append("&nbsp;");
            }
            rowString.append("</font></html>");
            JLabel jLabel = new JLabel(rowString.toString());

            int tx = x;
            int ty = y + i * pxBetweenNum;

            jLabel.setBounds(tx, ty, W, H);
            maxWidth = Math.max(maxWidth, tx + W);
            maxHeight = Math.max(maxHeight, ty + H);

            matricesJLabelList.add(jLabel);
        }
    }

    private class RepaintMouseListener extends MouseAdapter {
        private CurrentState state = CurrentState.MATRICES;

        private RepaintMouseListener() {
            repaint();
        }

        private void repaint() {
            if (state == CurrentState.BOARDS) {
                for (JLabel jLabel : boardsJLabelList)
                    jPanel.remove(jLabel);

                for (JLabel jLabel : matricesJLabelList)
                    jPanel.add(jLabel);

                state = CurrentState.MATRICES;
            } else {
                for (JLabel jLabel : matricesJLabelList)
                    jPanel.remove(jLabel);

                for (JLabel jLabel : boardsJLabelList)
                    jPanel.add(jLabel);

                state = CurrentState.BOARDS;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getClickCount() == 2) {
                repaint();
                jPanel.repaint();
            }
        }
    }

    private enum CurrentState {
        BOARDS, MATRICES
    }
}
