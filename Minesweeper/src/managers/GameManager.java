package managers;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import windows.Screen;

public class GameManager {
	public static boolean FULLSCREEN;
	public static Resolution RESOLUTION;
	public static int WIDTH;
	public static int HEIGHT;
	public static Dimension DIMENSION;
	public static double WIDTH_SCALE;
	public static double HEIGHT_SCALE;
	public static Font font;

	public GameManager() {
		Container container = new Container();
		container.setPreferredSize(GameManager.DIMENSION);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Screen.SCREEN_WIDTH = (int) screenSize.getWidth();
		Screen.SCREEN_HEIGHT = (int) screenSize.getHeight();
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/others/font.otf")).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		readSettings();
	}

	public static void writeSettings() {
		try {
			String str = "";
			str += FULLSCREEN + "\n";
			str += RESOLUTION.indexOf() + "\n";
			str += AudioManager.SOUND + "\n";
			FileWriter fw = new FileWriter(new File("src/others/settings.txt"));
			fw.write(str);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readSettings() {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get("src/others/settings.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FULLSCREEN = Boolean.parseBoolean(lines.get(0));
		RESOLUTION = Resolution.values()[Integer.parseInt(lines.get(1))];
		WIDTH = RESOLUTION.width();
		WIDTH_SCALE = WIDTH / 1920.0;
		HEIGHT = RESOLUTION.height();
		HEIGHT_SCALE = HEIGHT / 1080.0;
		DIMENSION = new Dimension(WIDTH, HEIGHT);
		AudioManager.SOUND = Boolean.parseBoolean(lines.get(2));
	}

	public static Rectangle calculateDimension(int x, int y, int width, int height) {
		return new Rectangle((int) (x * WIDTH_SCALE), (int) (y * HEIGHT_SCALE), (int) (width * WIDTH_SCALE), (int) (height * HEIGHT_SCALE));
	}
}
