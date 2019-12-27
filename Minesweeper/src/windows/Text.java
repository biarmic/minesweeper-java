package windows;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import managers.GameManager;

public class Text extends JLabel {
	public Text(String text, int align, int x, int y, int width, int height) {
		super(text, align);
		setBounds(GameManager.calculateDimension(x, y, width, height));
		setFont(new Font(GameManager.font.getName(), Font.PLAIN, (int) (getHeight() * 0.66)));
		setForeground(Color.white);
	}
}
