import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TremoloFreqSlider extends JSlider {

	public static final int MULTIPLIER = 10000;
	
	public TremoloFreqSlider(JLabel sliderValLabel) {
		this.setMinimum(0 * MULTIPLIER);
		this.setMaximum(10 * MULTIPLIER);
		this.setValue(0 * MULTIPLIER);
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				double val = ((TremoloFreqSlider)e.getSource()).getValue() * 1.0 / MULTIPLIER;
				sliderValLabel.setText(String.valueOf(val));
				SineWave.setTremoloFreq(val);
				Vibrato.calculateAllSineVals();
			}
			
		});
	}
}
