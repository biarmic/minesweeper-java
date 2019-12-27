package windows;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import gamestuff.Level;
import managers.GameManager;

public class GameBar extends JLayeredPane {
	private int mine;
	private Text gameLevel = new Text("", JLabel.LEFT, 30, 20, 300, 80);
	private Text remMine = new Text("", JLabel.CENTER, 860, 20, 200, 80);
	private Text time = new Text("0", JLabel.RIGHT, 1500, 20, 400, 80);
	private Timer timer = new Timer();
	private long seconds = 0;

	public GameBar() {
		setOpaque(true);
		setBackground(Button.BACKGROUND_COLOR);
		setBounds(0, 0, GameManager.WIDTH, GameManager.HEIGHT / 10);
		add(gameLevel, JLayeredPane.DEFAULT_LAYER);
		add(remMine, JLayeredPane.DEFAULT_LAYER);
		add(time, JLayeredPane.DEFAULT_LAYER);
	}

	public void startTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				seconds++;
				time.setText((seconds > 60 ? seconds / 60 + " : " : "") + seconds % 60);
			}
		};
		timer.schedule(task, 1000, 1000);
	}

	public void stopTimer() {
		timer.cancel();
	}

	public void refresh(Level level, int mine) {
		this.mine = mine;
		seconds = 0;
		gameLevel.setText(level.toString());
		remMine.setText(String.valueOf(mine));
		time.setText("0");
		timer.cancel();
	}

	public void refresh(int flag) {
		remMine.setText(String.valueOf(mine - flag));
	}
}
