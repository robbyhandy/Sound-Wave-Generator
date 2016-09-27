import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VibratoAmpSlider extends JSlider {

	private static final int MULTIPLIER = 1000;
	
	public VibratoAmpSlider(JLabel sliderValLabel) {
		this.setMinimum(0 * MULTIPLIER);
		this.setMaximum(2 * MULTIPLIER);
		this.setValue(0 * MULTIPLIER);
		this.setPreferredSize(new Dimension(200, 20));
		sliderValLabel.setText(String.valueOf(this.getValue()/MULTIPLIER));

		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				double val = ((VibratoAmpSlider)e.getSource()).getValue() * 1.0 / MULTIPLIER;
				sliderValLabel.setText(String.valueOf(val));
				SineWave.setVibratoAmp(val);
				Vibrato.calculateAllSineVals();
			}
		});
	}
}
