package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import managers.AudioManager;
import managers.GameManager;
import managers.Resolution;

public class SettingsWindow extends Window {
	public SettingsWindow() {
		super(760, 530);
		add(new Text("Fullscreen", JLabel.LEFT, 70, 60, 400, 100), JLayeredPane.DEFAULT_LAYER);
		add(new Text("Resolution", JLabel.LEFT, 70, 160, 400, 100), JLayeredPane.DEFAULT_LAYER);
		add(new Text("Sound", JLabel.LEFT, 70, 260, 380, 100), JLayeredPane.DEFAULT_LAYER);
		Button fullscreen = new Button(490, 75, 200, 70, GameManager.FULLSCREEN ? 0 : 1, "On", "Off");
		Button resolution = new Button(490, 175, 200, 70, GameManager.RESOLUTION.indexOf(), "1920 × 1080", "1600 × 900", "1366 × 768", "1280 × 720", "800 × 450");
		Button sound = new Button(490, 275, 200, 70, AudioManager.SOUND ? 0 : 1, "On", "Off");
		Button close = new Button("Close", 270, 380, 200, 100);
		add(fullscreen, JLayeredPane.DEFAULT_LAYER);
		add(resolution, JLayeredPane.DEFAULT_LAYER);
		add(sound, JLayeredPane.DEFAULT_LAYER);
		add(close);
		fullscreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				GameManager.FULLSCREEN = !GameManager.FULLSCREEN;
				if (GameManager.FULLSCREEN)
					GameManager.RESOLUTION = Resolution.values()[0];
				GameManager.writeSettings();
				resolution.setDisabled(GameManager.FULLSCREEN);
			}
		});
		resolution.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!GameManager.FULLSCREEN) {
					GameManager.RESOLUTION = Resolution.values()[resolution.index()];
					GameManager.writeSettings();
				}
			}
		});
		sound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				AudioManager.SOUND = !AudioManager.SOUND;
				GameManager.writeSettings();
			}
		});
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.removeFromGlassPane(SettingsWindow.this);
				((JLabel) event.getSource()).setBorder(null);
				GameManager.writeSettings();
				GameManager.readSettings();
				Screen.createScreen();
			}
		});
		resolution.setDisabled(GameManager.FULLSCREEN);
	}

}
