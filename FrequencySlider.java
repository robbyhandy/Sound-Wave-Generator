import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FrequencySlider extends JSlider {
	
	public FrequencySlider(JLabel sliderValLabel) {
		this.setMinimum(100);
		this.setMaximum(2000);
		this.setValue(200);
		this.setPreferredSize(new Dimension(200, 10));
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
