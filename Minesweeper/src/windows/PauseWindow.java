package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class PauseWindow extends Window {
	public PauseWindow() {
		super(650, 200);
		Button menu = new Button("Menu", 50, 50, 200, 100);
		Button newGame = new Button("New Game", 300, 50, 300, 100);
		add(menu);
		add(newGame);
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.removeFromGlassPane(PauseWindow.this);
				Screen.showMenu();
				((JLabel) event.getSource()).setBorder(null);
			}
		});
		newGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showPlayWindow();
				((JLabel) event.getSource()).setBorder(null);
			}
		});
	}
}
