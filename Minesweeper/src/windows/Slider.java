package windows;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import managers.GameManager;

public class Slider extends JLayeredPane {
	private int min;
	private int max;
	private double level;
	private JLabel line = new JLabel();
	private JLabel bar = new JLabel();
	private JLabel linkedLabel;
	private String prefix;

	public Slider(int x, int y, int width, int height, int min, int max, JLabel linkedLabel, String prefix) {
		this.min = min;
		this.max = max;
		this.linkedLabel = linkedLabel;
		this.prefix = prefix;
		setBounds(GameManager.calculateDimension(x, y, width, height));
		line.setBounds(getHeight() / 6, getHeight() / 3, getWidth() - getHeight() / 3, getHeight() / 3);
		level = line.getWidth() / (double) (max - min);
		line.setBackground(Color.white);
		line.setOpaque(true);
		add(line, JLayeredPane.DEFAULT_LAYER);
		line.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				if (event.getX() < 0 && bar.getX() <= 0)
					bar.setLocation(0, 0);
				else if (event.getX() > line.getWidth() - bar.getWidth() && bar.getX() >= line.getWidth() - bar.getWidth() / 2)
					bar.setLocation(line.getWidth() - bar.getWidth() / 2, 0);
				else
					bar.setLocation(event.getX() + bar.getWidth() / 2, 0);
				updateLinkedLabel();
			}
		});
		bar.setBounds(0, 0, getHeight() / 3, getHeight());
		bar.setBackground(Color.white);
		bar.setOpaque(true);
		add(bar, new Integer(1));
		bar.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				if (event.getX() < 0 && bar.getX() <= 0)
					bar.setLocation(0, 0);
				else if (event.getX() > 0 && bar.getX() >= line.getWidth() - bar.getWidth() / 2)
					bar.setLocation(line.getWidth() - bar.getWidth() / 2, 0);
				else
					bar.setLocation(bar.getX() + event.getX() / 2, 0);
				updateLinkedLabel();
			}
		});
		setPreferredSize(getSize());
	}

	private void updateLinkedLabel() {
		linkedLabel.setText(prefix + (int) (min + ((bar.getX() + bar.getWidth()) / level)));
	}

	public int getValue() {
		return (int) (min + ((bar.getX() + bar.getWidth() / 2) / level));
	}
}
