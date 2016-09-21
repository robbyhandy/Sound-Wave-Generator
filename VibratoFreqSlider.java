import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VibratoFreqSlider extends JSlider {

	private static final int MULTIPLIER = 1000;
	
	public VibratoFreqSlider(JLabel sliderValLabel) {
		this.setMinimum(0 * MULTIPLIER);
		this.setMaximum(10 * MULTIPLIER);
		this.setValue(0 * MULTIPLIER);
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				double val = ((VibratoFreqSlider)e.getSource()).getValue() * 1.0 / MULTIPLIER;
				sliderValLabel.setText(String.valueOf(val));
				SineWave.setVibratoFreq(val);
				Vibrato.calculateAllSineVals();
			}
			
		});
	}
}
