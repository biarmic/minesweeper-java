package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import managers.AudioManager;
import managers.GameManager;

public class Button extends JLabel {
	public static Color BACKGROUND_COLOR = new Color(27, 56, 32);
	private static Color BORDER_COLOR = Color.white;
	private LineBorder BORDER;
	private int index;
	private boolean disabled = false;

	public Button(String text, int x, int y, int width, int height) {
		super(text, JLabel.CENTER);
		int scaledX = (int) (x / (double) (1920.0 / GameManager.WIDTH));
		int scaledY = (int) (y / (double) (1080.0 / GameManager.HEIGHT));
		int scaledWidth = (int) (width / (double) (1920.0 / GameManager.WIDTH));
		int scaledHeight = (int) (height / (double) (1080.0 / GameManager.HEIGHT));
		BORDER = new LineBorder(BORDER_COLOR, (int) (4 * (double) GameManager.HEIGHT_SCALE));
		setBounds(scaledX, scaledY, scaledWidth, scaledHeight);
		setFont(new Font(GameManager.font.getName(), Font.PLAIN, scaledHeight / 2));
		setOpaque(true);
		setBackground(BACKGROUND_COLOR);
		setForeground(Color.white);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				Button.this.setBorder(BORDER);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				Button.this.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent event) {
				setFont(new Font(GameManager.font.getName(), Font.PLAIN, (int) (scaledHeight * 0.475)));
				setBounds(scaledX + (int) (scaledWidth * 0.025), scaledY + (int) (scaledHeight * 0.025), (int) (scaledWidth * 0.95), (int) (scaledHeight * 0.95));
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				AudioManager.playSound("click");
				setFont(new Font(GameManager.font.getName(), Font.PLAIN, scaledHeight / 2));
				setBounds(scaledX, scaledY, scaledWidth, scaledHeight);
			}
		});
	}

	public Button(int x, int y, int width, int height, String... array) {
		this(x, y, width, height, 0, array);
	}

	public Button(int x, int y, int width, int height, int index, String... array) {
		this(array[index], x, y, width, height);
		this.index = index;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!disabled) {
					Button.this.index = (Button.this.index + 1) % array.length;
					Button.this.setText(array[Button.this.index]);
				}
			}
		});
	}

	public int index() {
		return index;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
