import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HarmonicAmplSlider extends JSlider {

	private static final int MULTIPLIER = 1000;
	
	public HarmonicAmplSlider(JLabel sliderValLabel, SineWave wave) {
		this.setMinimum(0);
		this.setMaximum(2 * MULTIPLIER);
		this.setValue(1 * MULTIPLIER);
		this.setPreferredSize(new Dimension(200, 20));
		sliderValLabel.setText(String.valueOf(this.getValue()/MULTIPLIER));
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				double val = ((HarmonicAmplSlider)e.getSource()).getValue() * 1.0 / MULTIPLIER;
				sliderValLabel.setText(String.valueOf(val));
				wave.setHarmonicAmp(val);
				wave.calcSineVals();
			}
		});
	}
}
