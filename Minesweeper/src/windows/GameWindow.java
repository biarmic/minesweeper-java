package windows;

import javax.swing.JLayeredPane;
import gamestuff.Level;

public class GameWindow extends JLayeredPane {
	private GameBar bar = new GameBar();
	private Board board;

	public GameWindow() {
		Board.bar = bar;
		add(bar, new Integer(1));
	}

	public void createBoard(int row, int col, int mine, Level level) {
		if (board != null)
			remove(board);
		board = new Board(row, col, mine);
		add(board, JLayeredPane.DEFAULT_LAYER);
		bar.refresh(level, mine);
	}

	public void startTimer() {
		bar.startTimer();
	}

	public void stopTimer() {
		bar.stopTimer();
	}
}
