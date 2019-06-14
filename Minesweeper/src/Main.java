import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) throws IOException {
		showMenu();
	}
	public static void showMenu() {
		Menu menu = new Menu();
	}
	public static void createEasyTable() throws IOException {
		GUI gui = new GUI(9,9,10);
	}
	public static void createMediumTable() throws IOException {
		GUI gui = new GUI(16,16,40);
	}
	public static void createExpertTable() throws IOException {
		GUI gui = new GUI(25,25,99);
	}
	public static class Menu {
		private JFrame frame;
		private JPanel defaultPanel;
		private JButton easyButton;
		private JButton mediumButton;
		private JButton expertButton;
		public Menu() {
			frame = new JFrame("Menu");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int height = 100;
			int width = 300;
			frame.setSize(width,height);
			frame.setResizable(false);
			frame.setLocation((int)(screenSize.width-width)/2,(int)(screenSize.height-height)/2);
			//TOP PANEL
			JPanel topPanel = new JPanel();
			topPanel.add(new JLabel("Choose a difficulty"));
			frame.add(topPanel,BorderLayout.NORTH);
			//DEFAULT PANEL
			defaultPanel = new JPanel();
			frame.add(defaultPanel);
			easyButton = new JButton("Easy");
			easyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					try {
						createEasyTable();
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.setVisible(false);
					frame.dispose();
				}
			});
			mediumButton = new JButton("Medium");
			mediumButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					try {
						createMediumTable();
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.setVisible(false);
					frame.dispose();
				}
			});
			expertButton = new JButton("Expert");
			expertButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					try {
						createExpertTable();
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.setVisible(false);
					frame.dispose();
				}
			});
			defaultPanel.add(easyButton);
			defaultPanel.add(mediumButton);
			defaultPanel.add(expertButton);
			frame.validate();
		}
	}
}
