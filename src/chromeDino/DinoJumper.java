package chromeDino;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DinoJumper {
	static final int y1 = 230;
	static final int releaseTime = 180;
	static final int preTrigX = 700;
	static final int posTrigX = 500;
	static Robot r2;

	static final JumpQueue JQ = new JumpQueue();

	{
		try {
			r2 = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	static int time = 0;

	static final boolean debug = false;

	public static void main(String[] args) throws AWTException,
			InterruptedException {
		Robot r = new Robot();
		if (debug) {
			r.mouseMove(posTrigX - 4 + 40, y1);
		} else {
			long lastPressTime = 0;
			boolean pressed = false;

			boolean preTrigL, posTrigL;
			preTrigL = posTrigL = false;

			while (true) {
				Thread.sleep(10);
				time++;
				if (pressed
						&& System.currentTimeMillis() > lastPressTime
								+ releaseTime) {
					r.keyRelease(KeyEvent.VK_SPACE);
					r.keyPress(KeyEvent.VK_DOWN);
					r.keyRelease(KeyEvent.VK_DOWN);
					pressed = false;
					// System.err.println("RELEASE");
				}
				int wid = 40;
				int hei = 20;
				boolean preTrig = false;
				BufferedImage bi = r.createScreenCapture(new Rectangle(
						preTrigX, y1, wid, hei));
				o: for (int w = 0; w < wid; w++) {
					for (int h = 0; h < hei; h++) {
						if (bi.getRGB(w, h) == -11316397) {
							preTrig = true;
							break o;
						}
					}
				}

				boolean posTrig = false;
				BufferedImage bi2 = r.createScreenCapture(new Rectangle(
						posTrigX, y1, wid, hei));
				o: for (int w = 0; w < wid; w++) {
					for (int h = 0; h < hei; h++) {
						if (bi2.getRGB(w, h) == -11316397) {
							posTrig = true;
							break o;
						}
					}
				}
				// if (preTrig)
				// System.out.println("pretrig");
				// if (posTrig)
				// System.out.println("postrig");
				// preTrig
				// || r.getPixelColor(x1 + xOffset / offDiv, y1 - 22)
				// .getRed() == 83

				if (!preTrig && preTrigL) {
					JQ.pre(time);
				}
				if (!posTrig && posTrigL) {
					JQ.pos(time);
				}
				preTrigL = preTrig;
				posTrigL = posTrig;
				int peek = JQ.peek();
				if (peek != -1 && time >= peek) {
					// System.out.println("TIME: " + time);
					// System.out.println("PEEK: " + peek);
					// System.out.println(JM);
					JQ.jump();
					// r.mouseMove(x1 + xOffset / offDiv, y1);
					// System.out.println(xOffset / offDiv);
					r.keyPress(KeyEvent.VK_SPACE);
					// System.err.println("SPACE");
					pressed = true;
					lastPressTime = System.currentTimeMillis();
					// System.err.println("mid");
					// System.out.println();
				}
			}
		}

	}

}
