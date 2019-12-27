package gamestuff;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import managers.AudioManager;
import managers.GameManager;

public class Tile extends JLabel {
	private static ImageIcon FLAG_ICON;
	private static ImageIcon MINE_ICON;
	public static Color BACKGROUND_COLOR = new Color(13, 214, 131);
	public static Color FLAGGED_COLOR = new Color(125, 201, 4);
	public static Color REVEALED_COLOR = new Color(7, 61, 39);
	public static Color MINE_COLOR = new Color(231, 238, 43);
	public static Color MINE_EXPLODED_COLOR = new Color(238, 116, 43);
	public static Color BORDER_COLOR = Color.white;
	public static Color COLOR_1 = new Color(86, 93, 232);
	public static Color COLOR_2 = new Color(14, 173, 32);
	public static Color COLOR_3 = new Color(171, 19, 14);
	public static Color COLOR_4 = new Color(185, 104, 222);
	public static Color COLOR_5 = new Color(164, 181, 81);
	public static Color COLOR_6 = new Color(24, 144, 181);
	public static Color COLOR_7 = new Color(173, 173, 173);
	private static LineBorder BORDER = new LineBorder(BORDER_COLOR, 4);
	public static int SIDE;
	public static int X;
	public static int Y;
	public static Tile[][] grid;
	public static final int MINE = -1;
	private int value = 0;
	private int row;
	private int col;
	private boolean isRevealed = false;
	private boolean isFlagged = false;
	private boolean isAnimated = false;

	public Tile(int row, int col) {
		super("", JLabel.CENTER);
		this.row = row;
		this.col = col;
		setBounds(X + col * (SIDE * 6 / 5), Y + SIDE / 5 + row * (SIDE * 6 / 5), SIDE, SIDE);
		setOpaque(true);
		setBackground(BACKGROUND_COLOR);
		setFont(new Font(GameManager.font.getName(), Font.BOLD, (int) (SIDE * 0.66)));
		setForeground(Color.white);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				if (!isRevealed)
					setBorder(BORDER);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				if (!isRevealed)
					setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent event) {
				if (!isAnimated) {
					isAnimated = true;
					setFont(new Font(GameManager.font.getName(), Font.BOLD, (int) (SIDE * 0.627)));
					setBounds(getX() + (int) Math.ceil(SIDE * 0.025), getY() + (int) Math.ceil(SIDE * 0.025), (int) (SIDE * 0.95), (int) (SIDE * 0.95));
					if (getIcon() != null)
						scaleIcon();
				}
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				AudioManager.playSound("gameclick");
				setFont(new Font(GameManager.font.getName(), Font.BOLD, (int) (SIDE * 0.66)));
				setBounds(X + col * (SIDE * 6 / 5), Y + SIDE / 5 + row * (SIDE * 6 / 5), SIDE, SIDE);
				if (getIcon() != null)
					scaleIcon();
				isAnimated = false;
			}
		});
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return col;
	}

	public int getValue() {
		return value;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public boolean changeFlag() {
		isFlagged = !isFlagged;
		setIcon(isFlagged ? FLAG_ICON : null);
		setBackground(isFlagged ? FLAGGED_COLOR : BACKGROUND_COLOR);
		return isFlagged;
	}

	public void setMine() {
		value = MINE;
	}

	public void calculate() {
		if (value != -1)
			for (int i = -1; i <= 1; i++)
				for (int j = -1; j <= 1; j++)
					if (!isOutside(row + i, col + j) && grid[row + i][col + j].value == MINE)
						value++;
	}

	public static boolean isOutside(int row, int col) {
		return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
	}

	public boolean reveal(boolean exploded) {
		setText(value == 0 || value == MINE ? "" : String.valueOf(value));
		setIcon(value == MINE ? MINE_ICON : null);
		setBackground(value == MINE && exploded ? MINE_EXPLODED_COLOR : value == MINE ? MINE_COLOR : REVEALED_COLOR);
		setForeground(getRevealedColor());
		setBorder(null);
		isRevealed = true;
		return value == MINE;
	}

	private Color getRevealedColor() {
		switch (value) {
		case 1:
			return COLOR_1;
		case 2:
			return COLOR_2;
		case 3:
			return COLOR_3;
		case 4:
			return COLOR_4;
		case 5:
			return COLOR_5;
		case 6:
			return COLOR_6;
		case 7:
			return COLOR_7;
		case 8:
			return Color.white;
		default:
			return null;
		}
	}

	public void scaleIcon() {
		setIcon(new ImageIcon(Tile.FLAG_ICON.getImage().getScaledInstance(getWidth() * 2 / 3, getWidth() * 2 / 3, Image.SCALE_SMOOTH)));
	}

	public static void updateIcon() {
		try {
			int scaledSize = Tile.SIDE * 2 / 3;
			Tile.FLAG_ICON = new ImageIcon(ImageIO.read(new File("src/images/flag.png")));
			Image image = Tile.FLAG_ICON.getImage();
			Image newimg = image.getScaledInstance(scaledSize, scaledSize, Image.SCALE_SMOOTH);
			Tile.FLAG_ICON = new ImageIcon(newimg);
			Tile.MINE_ICON = new ImageIcon(ImageIO.read(new File("src/images/mine.png")));
			image = Tile.MINE_ICON.getImage();
			newimg = image.getScaledInstance(scaledSize, scaledSize, Image.SCALE_SMOOTH);
			Tile.MINE_ICON = new ImageIcon(newimg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
