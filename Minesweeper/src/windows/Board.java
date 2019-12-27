package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLayeredPane;
import gamestuff.Tile;
import managers.AudioManager;
import managers.GameManager;

public class Board extends JLayeredPane {
	public static final Integer GRID_LAYER = 1;
	public static GameBar bar;
	private Tile[][] grid;
	private int row;
	private int col;
	private int mine;
	private int flag;
	private int remTiles;
	private boolean minesPlaced = false;
	private boolean isGameOver = false;

	public Board(int row, int col, int mine) {
		this.row = row;
		this.col = col;
		remTiles = row * col;
		setOpaque(true);
		setBackground(Menu.BACKGROUND_COLOR);
		setBounds(0, GameManager.HEIGHT / 10, GameManager.WIDTH, (int) (GameManager.HEIGHT * 0.9));
		this.mine = mine;
		grid = new Tile[row][col];
		Tile.grid = grid;
		if (getWidth() / getHeight() >= col / row) {
			Tile.SIDE = (int) ((double) (getHeight() * 5.0) / (double) (6.0 * row + 1.0));
		} else {
			Tile.SIDE = (int) ((double) (getWidth() * 5.0) / (double) (6.0 * col + 1.0));
		}
		Tile.updateIcon();
		Tile.X = (int) (((getWidth() - Tile.SIDE * (double) (col + (double) (col + 1) / 5)) / 2));
		Tile.Y = (int) (((getHeight() - Tile.SIDE * (double) (row + (double) (row + 1) / 5)) / 2));
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = new Tile(i, j);
				add(grid[i][j], GRID_LAYER);
				grid[i][j].addMouseListener(new MouseAdapter() {
					private final Set<Integer> pressedButtons = new HashSet<Integer>();

					@Override
					public synchronized void mousePressed(MouseEvent event) {
						pressedButtons.add(event.getButton());
					}

					@Override
					public synchronized void mouseReleased(MouseEvent event) {
						Tile source = (Tile) event.getSource();
						int row = source.getRow();
						int col = source.getColumn();
						if (pressedButtons.contains(MouseEvent.BUTTON1) && pressedButtons.contains(MouseEvent.BUTTON3)) {
							if (source.isRevealed()) {
								int flags = 0;
								for (int i = -1; i <= 1; i++)
									for (int j = -1; j <= 1; j++)
										if (!Tile.isOutside(row + i, col + j) && grid[row + i][col + j].isFlagged())
											flags++;
								if (flags == source.getValue())
									for (int i = -1; i <= 1; i++)
										for (int j = -1; j <= 1; j++)
											if (!Tile.isOutside(row + i, col + j) && !grid[row + i][col + j].isFlagged())
												revealNeighbors(grid[row + i][col + j].getRow(), grid[row + i][col + j].getColumn());
							}
						} else {
							if (!source.isRevealed()) {
								if (event.getButton() == MouseEvent.BUTTON1) {
									if (Board.this.row * Board.this.col == remTiles)
										Screen.startTimer();
									if (!minesPlaced) {
										placeMines(source.getRow(), source.getColumn());
										minesPlaced = true;
									}
									revealNeighbors(source.getRow(), source.getColumn());
								} else if (event.getButton() == MouseEvent.BUTTON3 && !source.isRevealed()) {
									flag += (source.changeFlag() ? 1 : -1);
									bar.refresh(flag);
								}
							}
						}
						pressedButtons.remove(event.getButton());
					}

				});
			}
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return col;
	}

	public void placeMines(int row, int col) {
		int mine = this.mine;
		while (mine > 0) {
			int x = (int) (Math.random() * this.row);
			int y = (int) (Math.random() * this.col);
			boolean isLegal = true;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (x == row + i && y == col + j) {
						isLegal = false;
						break;
					}
				}
				if (!isLegal)
					break;
			}
			if (isLegal && grid[x][y].getValue() != Tile.MINE) {
				grid[x][y].setMine();
				mine--;
			}
		}
		for (int i = 0; i < this.row; i++)
			for (int j = 0; j < this.col; j++)
				grid[i][j].calculate();
	}

	public void revealNeighbors(int row, int col) {
		if (!isGameOver && !grid[row][col].isFlagged()) {
			remTiles += (grid[row][col].isRevealed() ? 0 : -1);
			if (grid[row][col].reveal(true)) {
				endGame(false);
				return;
			}
			if (mine == remTiles) {
				endGame(true);
				return;
			}
			if (grid[row][col].getValue() == 0)
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						if (!Tile.isOutside(row + i, col + j) && !grid[row + i][col + j].isRevealed())
							revealNeighbors(row + i, col + j);
		}
	}

	public void endGame(boolean isWin) {
		Screen.stopTimer();
		isGameOver = true;
		for (int i = 0; i < row; i++)
			for (Tile tile : grid[i])
				tile.reveal(!isWin);
		AudioManager.playSound(isWin ? "win" : "explosion");
		Screen.addToGlassPane(new GameOverWindow(isWin));
	}
}
