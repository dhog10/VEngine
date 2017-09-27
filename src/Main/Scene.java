package Main;
import java.awt.*;
import java.util.Iterator;

import javax.swing.JComponent;

import Entities.Entity;
import Handling.Handler;

public class Scene extends JComponent{
	private Game game;
	private Handler handler;
	private int frames = 0;
	private long lastFrame = 0;
	private long lastUpdate = 0;
	private long curtime = 0;
	private long targetFPSMillis;
	private String targetTPS;
	private String targetFPS;
	
	public Scene(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		targetFPSMillis = 1000 / game.getTargetFPS();
	}
	
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;

		// Commented because it causes FPS issues
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		// Debug info
		if(game.getConfig().showFPS) {
			g.setColor(new Color(255,0,0));
			targetFPS = "∞";
			if(game.getTargetFPS() != -1) {
				targetFPS = Integer.toString(game.getTargetFPS());
			}
			g.drawString("FPS: " + Integer.toString(game.fps) + " / " + targetFPS, 10, 20);
		}
		
		if(game.getConfig().showTPS) {
			g.setColor(new Color(255,0,0));
			targetTPS = "∞";
			if(game.getTargetTPS() != -1) {
				targetTPS = Integer.toString(game.getTargetTPS());
			}
			g.drawString("TPS: " + Integer.toString(game.tps) + " / " + targetTPS, 10, 40);
		}
		
		synchronized(handler.getEntities()) {
			Iterator it = handler.getEntities().iterator();
			while(it.hasNext()) {
				Entity e = (Entity)it.next();
				e.render(g2);
			}
		}

		game.getHUD().render(g2);
		
		frames++;
		curtime = System.nanoTime();
		if(curtime - lastUpdate > 1000000000) {
			game.fps = frames;
			frames = 0;
			lastUpdate = curtime;
		}
		
		if(game.getTargetFPS() != -1) {
			try {
				Thread.sleep(targetFPSMillis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastFrame = curtime;
		repaint();
	}
}
