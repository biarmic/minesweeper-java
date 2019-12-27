package windows;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import managers.GameManager;
import java.awt.Color;

public abstract class Window extends JLayeredPane {
	private static Color BACKGROUND_COLOR = new Color(99, 191, 114);
	public static final LineBorder BORDER = new LineBorder(Color.white, 4);

	public Window(int width, int height) {
		this();
		int scaledWidth = (int) (width / (double) (1920.0 / GameManager.WIDTH));
		int scaledHeight = (int) (height / (double) (1080.0 / GameManager.HEIGHT));
		setBounds((GameManager.WIDTH - scaledWidth) / 2, (GameManager.HEIGHT - scaledHeight) / 2, scaledWidth, scaledHeight);
		setPreferredSize(getSize());
	}

	private Window() {
		setBackground(BACKGROUND_COLOR);
		setBorder(BORDER);
		setOpaque(true);
	}
}
