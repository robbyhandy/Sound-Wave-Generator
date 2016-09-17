import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HarmonicAmplitudeSlider extends JSlider {

	private static final int MULTIPLIER = 1000;
	
	public HarmonicAmplitudeSlider(JLabel sliderValLabel) {
		this.setMinimum(0);
		this.setMaximum(2 * MULTIPLIER);
		this.setValue(1 * MULTIPLIER);
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
