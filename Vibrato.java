import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Vibrato extends JFrame {
	
	private static final int NUM_WAVES = 7;

	public Vibrato() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		JPanel slidersPanel = setupSliders();
		slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
	}
	
	private JPanel setupSliders() {
		JPanel slidersPanel = new JPanel();
		slidersPanel.add(setupFrequencySliders());
		slidersPanel.add(setupHarmonicSliders());
		slidersPanel.add(setupVibratoSliders());
		slidersPanel.add(setupTremoloSliders());
		return slidersPanel;
	}
	
	private JPanel setupFrequencySliders() {
		JPanel frequencySliderPanel = new JPanel();
		JLabel frequencyVal = new JLabel("0");
		frequencySliderPanel.add(new JLabel("Tone Frequency"));
		frequencySliderPanel.add(new FrequencySlider(frequencyVal));
		frequencySliderPanel.add(frequencyVal);
		return frequencySliderPanel;
		
	}
	
	private JPanel setupHarmonicSliders() {
		JPanel harmonicSliderSuperPanel = new JPanel();
		harmonicSliderSuperPanel.setLayout(new BoxLayout(harmonicSliderSuperPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < NUM_WAVES; i++) {
			JPanel harmonicSliderPanel = new JPanel();
			JLabel harmonicAmpVal = new JLabel("1");
			harmonicSliderPanel.add(new JLabel("Harmonic Amplitude - n = " + (i + 1)));
			harmonicSliderPanel.add(new HarmonicAmplitudeSlider(harmonicAmpVal));
			harmonicSliderPanel.add(harmonicAmpVal);
			harmonicSliderSuperPanel.add(harmonicSliderPanel);
		}
		return harmonicSliderSuperPanel;
	}
	
	private JPanel setupVibratoSliders() {
		return new JPanel();
	}
	
	private JPanel setupTremoloSliders() {
		return new JPanel();
	}
	
	public static void main(String[] args) {
		JFrame mainFrame = new Vibrato();
	}
	
	
	
}
