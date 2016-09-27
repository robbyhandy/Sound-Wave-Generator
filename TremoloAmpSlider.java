import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TremoloAmpSlider extends JSlider {

	public static final int MULTIPLIER = 1000;
	
	public TremoloAmpSlider(JLabel sliderValLabel) {
		this.setMinimum(0 * MULTIPLIER);
		this.setMaximum(1 * MULTIPLIER);
		this.setValue(0 * MULTIPLIER);
		this.setPreferredSize(new Dimension(200, 20));
		sliderValLabel.setText(String.valueOf(this.getValue()/MULTIPLIER));

		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				double val = ((TremoloAmpSlider)e.getSource()).getValue() * 1.0 / MULTIPLIER;
				sliderValLabel.setText(String.valueOf(val));
				SineWave.setTremoloAmp(val);
				Vibrato.calculateAllSineVals();
			}
			
		});
	}
}
