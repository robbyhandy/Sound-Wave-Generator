import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FrequencySlider extends JSlider {
	
	public FrequencySlider(JLabel sliderValLabel) {
		this.setMinimum(0);
		this.setMaximum(2000);
		this.setValue(220);
		this.setPreferredSize(new Dimension(200, 20));
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int val = ((FrequencySlider)e.getSource()).getValue();
				sliderValLabel.setText(String.valueOf(val));
				SineWave.setFreq(val);
				Vibrato.calculateAllSineVals();
			}
			
		});
	}
	
}
