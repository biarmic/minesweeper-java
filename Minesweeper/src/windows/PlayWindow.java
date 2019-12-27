package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import gamestuff.Level;
import managers.GameManager;

public class PlayWindow extends Window {

	public PlayWindow() {
		super(1300, 750);
		Button easy = new Button("Easy", 100, 100, 300, 150);
		add(easy, JLayeredPane.DEFAULT_LAYER);
		easy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.createBoard(9, 9, 10, Level.Easy);
				easy.setBorder(null);
			}
		});
		Button medium = new Button("Medium", 100, 300, 300, 150);
		add(medium, JLayeredPane.DEFAULT_LAYER);
		medium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.createBoard(16, 16, 40, Level.Medium);
				medium.setBorder(null);
			}
		});
		Button hard = new Button("Hard", 100, 500, 300, 150);
		add(hard, JLayeredPane.DEFAULT_LAYER);
		hard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.createBoard(16, 30, 99, Level.Hard);
				hard.setBorder(null);
			}
		});
		Text custom = new Text("Custom", JLabel.CENTER, 400, 40, 900, 150);
		add(custom, JLayeredPane.DEFAULT_LAYER);
		JLabel rowLabel = new JLabel("Row: 9");
		rowLabel.setBounds(GameManager.calculateDimension(480, 170, 280, 100));
		rowLabel.setFont(new Font(GameManager.font.getName(), Font.PLAIN, (int) (60 * GameManager.HEIGHT_SCALE)));
		rowLabel.setForeground(Color.white);
		add(rowLabel, new Integer(1));
		Slider rowSlider = new Slider(780, 205, 400, 30, 9, 24, rowLabel, "Row: ");
		add(rowSlider, new Integer(2));
		JLabel colLabel = new JLabel("Column: 9");
		colLabel.setBounds(GameManager.calculateDimension(480, 270, 280, 100));
		colLabel.setFont(new Font(GameManager.font.getName(), Font.PLAIN, (int) (60 * GameManager.HEIGHT_SCALE)));
		colLabel.setForeground(Color.white);
		add(colLabel, new Integer(1));
		Slider colSlider = new Slider(780, 305, 400, 30, 9, 30, colLabel, "Column: ");
		add(colSlider, new Integer(2));
		JLabel mineLabel = new JLabel("Mines: 10");
		mineLabel.setBounds(GameManager.calculateDimension(480, 370, 280, 100));
		mineLabel.setFont(new Font(GameManager.font.getName(), Font.PLAIN, (int) (60 * GameManager.HEIGHT_SCALE)));
		mineLabel.setForeground(Color.white);
		add(mineLabel, new Integer(1));
		Slider mineSlider = new Slider(780, 405, 400, 30, 10, 40, mineLabel, "Mines: ");
		add(mineSlider, new Integer(2));
		Button play = new Button("Play", 700, 500, 300, 150);
		add(play, JLayeredPane.DEFAULT_LAYER);
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.createBoard(rowSlider.getValue(), colSlider.getValue(), mineSlider.getValue(), Level.Custom);
				play.setBorder(null);
			}
		});
	}

}
