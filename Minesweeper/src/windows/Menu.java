package windows;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import managers.GameManager;

public class Menu extends JLayeredPane {
	public static Color BACKGROUND_COLOR = new Color(33, 124, 79);

	public Menu() {
		setOpaque(true);
		setBackground(BACKGROUND_COLOR);
		setPreferredSize(GameManager.DIMENSION);
		add(new Text("Minesweeper", JLabel.CENTER, 510, 250, 900, 250));
		Button settingsButton = new Button("Settings", 365, 610, 300, 150);
		add(settingsButton, JLayeredPane.DEFAULT_LAYER);
		settingsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showSettingsWindow();
			}
		});
		Button playButton = new Button("Play", 810, 610, 300, 150);
		add(playButton, JLayeredPane.DEFAULT_LAYER);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showPlayWindow();
			}
		});
		Button exitButton = new Button("Exit", 1255, 610, 300, 150);
		add(exitButton, JLayeredPane.DEFAULT_LAYER);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				System.exit(0);
			}
		});
	}
}
