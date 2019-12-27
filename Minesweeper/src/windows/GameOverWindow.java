package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GameOverWindow extends Window {
	private static final Integer BUTTON_LAYER = 1;

	public GameOverWindow(boolean isWin) {
		super(700, 300);
		add(new Text("<html><div align='center'>You " + (isWin ? "Win" : "Lose") + "</div></html>", JLabel.CENTER, 0, 20, 700, 100), JLayeredPane.DEFAULT_LAYER);
		Button play = new Button("New Game", 50, 150, 280, 100);
		add(play, BUTTON_LAYER);
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.removeFromGlassPane(GameOverWindow.this);
				Screen.showPlayWindow();
				play.setBorder(null);
			}
		});
		Button menu = new Button("Menu", 365, 150, 280, 100);
		add(menu, BUTTON_LAYER);
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showMenu();
				menu.setBorder(null);
			}
		});
	}
}
