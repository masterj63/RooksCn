import java.util.Arrays;

class Board {
    private final byte[] board;
    final int size;

    Board(byte[] board) {
        if (board == null)
            throw new IllegalArgumentException();

        this.board = board;
        this.size = board.length;
    }

    byte get(int i) {
        return board[i];
    }

    byte[] cols() {
        byte[] cols = new byte[size];
        Arrays.fill(cols, (byte) -1);
        for (byte i = 0; i < size; i++) {
            byte j = board[i];
            if (j == -1)
                continue;
            cols[j] = i;
        }
        return cols;
    }

    Board remove(int i) {
        if (board[i] == -1)
            throw new IllegalArgumentException();

        byte[] b = board.clone();
        b[i] = -1;
        return new Board(b);
    }

    Board add(int i, byte j) {
        if (board[i] != -1)
            throw new IllegalArgumentException();

        byte[] b = board.clone();
        b[i] = j;
        return new Board(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            throw new IllegalArgumentException("null or class mismatch");

        Board b = (Board) o;

        if (!Arrays.equals(board, b.board))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}