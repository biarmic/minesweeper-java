package windows;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import gamestuff.Level;
import managers.AudioManager;
import managers.GameManager;

public class Screen extends JFrame {
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	private static Screen screen;
	private static GameManager gameManager = new GameManager();
	private static AudioManager audioManager = new AudioManager();
	private static JLayeredPane currentPane;
	private static Menu menu = new Menu();
	private static GameWindow game = new GameWindow();
	private static PlayWindow playWindow = new PlayWindow();
	private static PauseWindow pauseWindow = new PauseWindow();
	private static SettingsWindow settingsWindow = new SettingsWindow();
	private static GridBagConstraints gbc = new GridBagConstraints();

	public Screen() {
		screen = this;
		Container container = new Container();
		container.setPreferredSize(GameManager.DIMENSION);
		if (GameManager.FULLSCREEN) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		} else {
			setUndecorated(true);
			setContentPane(container);
			pack();
			this.setLocation((SCREEN_WIDTH - GameManager.WIDTH) / 2, (SCREEN_HEIGHT - GameManager.HEIGHT) / 2);
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentPane = menu;
		setLayeredPane(currentPane);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (isInGlassPane(pauseWindow))
						removeFromGlassPane(pauseWindow);
					else if (isInGlassPane(settingsWindow))
						removeFromGlassPane(settingsWindow);
					else if (isInGlassPane(playWindow))
						removeFromGlassPane(playWindow);
					else if (currentPane == menu && ((JPanel) screen.getGlassPane()).getComponentCount() == 0)
						System.exit(0);
					else
						addToGlassPane(pauseWindow);
				}
			}
		});
		getGlassPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getSource() instanceof JLabel && ((JLabel) event.getSource()).getParent() != getGlassPane())
					event.consume();
			}
		});
		((JPanel) getGlassPane()).setLayout(new GridBagLayout());
		setVisible(true);
	}

	public static void addToGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents()) {
			glass.remove(comp);
			currentPane.add(comp, new Integer(currentPane.highestLayer() + 1));
		}
		glass.add(pane, gbc);
		glass.setVisible(true);
		currentPane.repaint();
		glass.repaint();
	}

	public static void removeFromGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		glass.remove(pane);
		for (Component comp : currentPane.getComponentsInLayer(currentPane.highestLayer())) {
			if (comp instanceof JLayeredPane && !(comp instanceof GameBar)) {
				currentPane.remove(comp);
				glass.add(comp, gbc);
				break;
			}
		}
		currentPane.repaint();
		glass.repaint();
		glass.setVisible(glass.getComponents().length != 0);
	}

	public static boolean isInGlassPane(Component search) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents())
			if (search == comp)
				return true;
		return false;
	}

	public static void clearGlassPane() {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents())
			glass.remove(comp);
		currentPane.repaint();
		glass.repaint();
		glass.setVisible(false);
	}

	public static void setClickable(boolean clickable) {
		((JPanel) screen.getGlassPane()).setVisible(!clickable);
	}

	public static void createBoard(int row, int col, int mine, Level level) {
		clearGlassPane();
		game.createBoard(row, col, mine, level);
		currentPane = game;
		screen.setLayeredPane(currentPane);
	}

	public static void showPlayWindow() {
		addToGlassPane(playWindow);
	}

	public static void showMenu() {
		clearGlassPane();
		currentPane = menu;
		menu.repaint();
		screen.setLayeredPane(currentPane);
	}

	public static void showSettingsWindow() {
		addToGlassPane(settingsWindow);
	}

	public static void createScreen() {
		menu = new Menu();
		game = new GameWindow();
		playWindow = new PlayWindow();
		pauseWindow = new PauseWindow();
		settingsWindow = new SettingsWindow();
		gbc = new GridBagConstraints();
		Container container = new Container();
		container.setPreferredSize(GameManager.DIMENSION);
		if (GameManager.FULLSCREEN)
			screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else {
			screen.setContentPane(container);
			screen.pack();
			screen.setLocation((SCREEN_WIDTH - GameManager.WIDTH) / 2, (SCREEN_HEIGHT - GameManager.HEIGHT) / 2);
		}
		currentPane = menu;
		screen.setLayeredPane(currentPane);
	}

	public static void startTimer() {
		game.startTimer();
	}

	public static void stopTimer() {
		game.stopTimer();
	}

	public static void main(String[] args) {
		new Screen();
	}
}
