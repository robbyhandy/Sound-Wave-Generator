import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	private final BufferedImage image;
	private final Path2D.Float data;
	
	{
        Dimension pref = getPreferredSize();
        
        image = (
            GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration()
            .createCompatibleImage(
                pref.width, pref.height, Transparency.OPAQUE
            )
        );
    }
	
	public DisplayPanel() {
		setOpaque(false);
		data = new Path2D.Float();
	}
	
	public void drawGraphics(float[] values) {
		data.reset();
		data.moveTo(0, (float) image.getHeight() / 2);
		float maxAmplitude = 0;
		for(int i = 0; i < values.length; i++) {
			if(Math.abs(values[i]) > maxAmplitude) {
				maxAmplitude = Math.abs(values[i]);
			}
		}
		// To make sure the waveform is displayed within the bounds
		double multiplier = 0.45 * image.getHeight() / maxAmplitude;
		for(int i = 0; i < values.length; i++) {
			data.lineTo(i * image.getWidth(), values[i] * multiplier);
		}
		Graphics2D g2d = image.createGraphics();
		g2d.dispose();
	}
}
