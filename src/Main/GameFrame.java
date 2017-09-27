package Main;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import Handling.Handler;

public class GameFrame {
	private Game game;
	private Handler handler;
	private JFrame frame;
	private int width, height;
	private String title;
	private Scene scene;
	
	public GameFrame(Game game, Handler handler, String title, int width, int height) {
		this.game = game;
		this.handler = handler;
		this.title = title;
		this.width = width;
		this.height = height;
		
		frame = new JFrame(title);
		frame.setSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(30,30,30));
		frame.setResizable(false);
		frame.setVisible(true);
		
		scene = new Scene(game, handler);
		frame.getContentPane().add(scene);

		frame.addMouseListener(Game.input.getMouseListener());
		frame.addMouseMotionListener(Game.input.getMouseMotionListener());
	}
	
	public void setResolution(int w, int h) {
		width = w;
		height = h;
		frame.setSize(new Dimension(w, h));
	}
	
	public Dimension getResolution() {
		return new Dimension(width, height);
	}
	
	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(title);
	}
	
	public void setBackgroundColor(Color color) {
		frame.getContentPane().setBackground(color);
	}
	
	public String getTitle() {
		return title;
	}
}
