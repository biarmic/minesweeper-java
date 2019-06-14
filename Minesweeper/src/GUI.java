import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI {
	private JFrame frame;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JLabel mineLabel;
	private MineTable mineTable;
	private MButton[][] buttons;
	private int possibleMines;
	private int remainingTiles;
	private Color gray = new Color(190,190,190);
	private static Image icon1;
	private static Image icon2;
	private static Image icon3;
	private static Image icon4;
	private static Image icon5;
	private static Image icon6;
	private static Image icon7;
	private static Image icon8;
	private static Image iconFlag;
	private static Image iconMine;
	public GUI(int row, int column, int numMines) throws IOException {
		possibleMines = numMines;
		remainingTiles = row*column;
		mineTable = new MineTable(row,column,numMines);
		buttons = new MButton[row][column];
		frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height/3*2;
		int width = height/column*row;
		frame.setSize(width,height);
		frame.setResizable(false);
		frame.setLocation((int)(screenSize.width-width)/2,(int)(screenSize.height-height)/2);
		//TOP PANEL
		topPanel = new JPanel();
		topPanel.setBackground(gray);
		mineLabel = new JLabel("Mines: " + mineTable.getNumMines());
		topPanel.add(mineLabel);
		frame.add(topPanel,BorderLayout.NORTH);
		//MIDDLE PANEL
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(row,column));
		frame.add(middlePanel,BorderLayout.CENTER);
		prepareButtons();
		frame.validate();
		Image img = ImageIO.read(getClass().getResource("1.png"));
		icon1 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("2.png"));
		icon2 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("3.png"));
		icon3 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("4.png"));
		icon4 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("5.png"));
		icon5 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("6.png"));
		icon6 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("7.png"));
		icon7 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("8.png"));
		icon8 = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("flag.png"));
		iconFlag = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
		img = ImageIO.read(getClass().getResource("mine.png"));
		iconMine = img.getScaledInstance(buttons[0][0].getSize().width,buttons[0][0].getSize().height,java.awt.Image.SCALE_SMOOTH);
	}
	private void prepareButtons() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new MButton(i,j,mineTable.getNumber(i,j));
				buttons[i][j].addMouseListener(new MMouseListener());
				middlePanel.add(buttons[i][j]);
			}
		}
	}
	private void revealEmpties(int row, int column) throws IOException {
		if(isInTable(row,column) && !buttons[row][column].pressed) {
			buttons[row][column].setPressed();
			remainingTiles--;
			if(buttons[row][column].getNumber()==0) {
				revealEmpties(row-1,column-1);
				revealEmpties(row-1,column);
				revealEmpties(row-1,column+1);
				revealEmpties(row+1,column-1);
				revealEmpties(row+1,column);
				revealEmpties(row+1,column+1);
				revealEmpties(row,column-1);
				revealEmpties(row,column+1);
			}
		}
	}
	private void revealMines() throws IOException {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(buttons[i][j].isMine()) {
					buttons[i][j].setMineIcon();
				}
			}
		}
		JOptionPane.showMessageDialog(null,"Better Luck Next Time");
		frame.setVisible(false);
		frame.dispose();
		Main.showMenu();
	}
	private boolean isInTable(int row, int column) {
		if(row>=0 && column>=0 && row<buttons.length && column<buttons[0].length)
			return true;
		else
			return false;
	}
	private void updateMineLabel() {
		mineLabel.setText("Mines: " + possibleMines);
	}
	private boolean isGameOver() {
		if(remainingTiles==mineTable.getNumMines())
			return true;
		else
			return false;
	}
	public class MMouseListener implements MouseListener{
		MButton button;
		@Override
		public void mouseClicked(MouseEvent event) {
			if(event.getSource() instanceof MButton) {
				button = (MButton) event.getSource();
				if(event.getButton()==MouseEvent.BUTTON1)
					try {
						leftClick();
					} catch (IOException e) {
						e.printStackTrace();
					}
				else
					try {
						rightClick();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		private void leftClick() throws IOException {
			if(!button.isPressed() && !button.isFlagged() && button.isMine()) {
				revealMines();
			}else if(!button.isPressed() && !button.isFlagged()) {
				revealEmpties(button.getRow(),button.getColumn());
				if(isGameOver()) {
					JOptionPane.showMessageDialog(null,"Congratulations");
					frame.setVisible(false);
					frame.dispose();
					Main.showMenu();
				}
			}
		}
		private void rightClick() throws IOException {
			if(!button.isPressed() && !button.isFlagged()) {
				button.setFlagIcon();
				possibleMines--;
				updateMineLabel();
			}else if(!button.isPressed()) {
				button.removeFlagIcon();
				possibleMines++;
				updateMineLabel();
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
	public class MButton extends JButton{
		private int row;
		private int column;
		private int number;
		private boolean pressed = false;
		private boolean flagged = false;
		public MButton(int row, int column, int number) {
			super();
			this.row = row;
			this.column = column;
			this.number = number;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
		public int getNumber() {
			return number;
		}
		public boolean isMine() {
			if(number==-1)
				return true;
			else
				return false;
		}
		public boolean isPressed() {
			return pressed;
		}
		public boolean isFlagged() {
			return flagged;
		}
		public void setPressed() throws IOException {
			pressed = true;
			setBackground(new Color(255,255,255));
			if(number==1) {
				setBackground(new Color(255,255,255));
			    setIcon(new ImageIcon(icon1));
			}else if(number==2) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon2));
			}else if(number==3) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon3));
			}else if(number==4) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon4));
			}else if(number==5) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon5));
			}else if(number==6) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon6));
			}else if(number==7) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon7));
			}else if(number==8) {
				setBackground(new Color(255,255,255));
				setIcon(new ImageIcon(icon8));
			}
		}
		public void setFlagIcon() throws IOException {
			flagged = true;
		    setIcon(new ImageIcon(iconFlag));
		}
		public void setMineIcon() throws IOException {
			pressed = true;
			setBackground(new Color(255,255,255));
		    setIcon(new ImageIcon(iconMine));
		}
		public void removeFlagIcon() {
			flagged = false;
			setIcon(null);
		}
	}
}
