package cs437gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Ticks extends JComponent {
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension size = getSize();

		for(int position = 0; position <= size.width; position += 10) { 
			int tick = position / 10;
			int seconds = tick % 60;
			int minutes = (tick / 60) % 60;
			int hours = (tick / 3600);

			String time = hours + ":" + minutes + ":" + seconds;

			if (tick % 10 == 0) {
				g.drawLine(position, 0, position, size.height * 7 / 8);
				g.drawString(time, position + 1, size.height * 7 / 8);
			}
			else {
				g.drawLine(position, 0, position, size.height * 2 / 8);
			}
		}
    }
}