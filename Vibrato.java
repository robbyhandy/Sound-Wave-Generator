import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Vibrato extends JFrame {
	
	private static final int NUM_WAVES = 7;
	private static SineWave[] sineWaves;
	
	private static double[] finalSineVals;
	
	private static boolean dataChanged;

	public Vibrato() {
		dataChanged = false;
		sineWaves = new SineWave[NUM_WAVES];
		for(int i = 0; i < sineWaves.length; i++) {
			sineWaves[i] = new SineWave(i);
		}
		finalSineVals = new double[SineWave.NUM_SINE_POINTS];
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		JPanel slidersPanel = setupSliders();
		contentPane.add(slidersPanel);
		DisplayPanel disp = new DisplayPanel();
		contentPane.add(disp);
		this.pack();
		this.setVisible(true);
		System.out.println("Should have setup gui");
		calculateAllSineVals();
		calcFinalSineVals();
		dataChanged = true;
		for(int i = 0; i < Integer.MAX_VALUE; i++) {
			if(dataChanged && finalSineVals != null) {
				disp.drawGraphics(finalSineVals);
			}
		}
		System.out.println("Finished Running");
	}
	
	public static void calculateAllSineVals() {
		if(sineWaves != null) {
			for(int i = 0; i < sineWaves.length; i++) {
				sineWaves[i].calcSineVals();
			}
		}
	}
	
	public static void calcFinalSineVals() {
		if(finalSineVals != null) {
			for(int i = 0; i < sineWaves.length; i++) {
				double[] vals = sineWaves[i].getSineVals();
				for(int j = 0; j < finalSineVals.length; j++) {
					finalSineVals[j] += vals[j];
				}
			}
			dataChanged = true;
		}
	}
	
	private JPanel setupSliders() {
		JPanel slidersPanel = new JPanel();
		slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
		slidersPanel.add(setupFrequencySliders());
		slidersPanel.add(setupHarmonicSliders());
		slidersPanel.add(setupVibratoFreqSliders());
		slidersPanel.add(setupVibratoAmpSliders());
		slidersPanel.add(setupVibratoPhaseSliders());
		slidersPanel.add(setupTremoloAmpSliders());
		slidersPanel.add(setupTremoloFreqSliders());
		slidersPanel.add(setupWavePhaseSliders());
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
			harmonicSliderPanel.setLayout(new BoxLayout(harmonicSliderPanel, BoxLayout.X_AXIS));
			harmonicSliderPanel.add(Box.createRigidArea(new Dimension(0,-15)));
			JLabel harmonicAmpVal = new JLabel("1");
			harmonicSliderPanel.add(new JLabel("Harmonic Amplitude - n = " + (i + 1)));
			harmonicSliderPanel.add(new HarmonicAmplSlider(harmonicAmpVal, sineWaves[i]));
			harmonicSliderPanel.add(harmonicAmpVal);
			harmonicSliderSuperPanel.add(harmonicSliderPanel);
		}
		return harmonicSliderSuperPanel;
	}
	
	private JPanel setupVibratoFreqSliders() {
		JPanel vibratoFreqSliderPanel = new JPanel();
		JLabel vibratoFreqVal = new JLabel("0");
		vibratoFreqSliderPanel.add(new JLabel("Vibrato Frequency"));
		vibratoFreqSliderPanel.add(new VibratoAmpSlider(vibratoFreqVal));
		vibratoFreqSliderPanel.add(vibratoFreqVal);
		return vibratoFreqSliderPanel;
	}
	
	private JPanel setupVibratoAmpSliders() {
		JPanel vibratoAmpSliderPanel = new JPanel();
		JLabel vibratoAmpVal = new JLabel("1");
		vibratoAmpSliderPanel.add(new JLabel("Vibrato Amplitude"));
		vibratoAmpSliderPanel.add(new VibratoAmpSlider(vibratoAmpVal));
		vibratoAmpSliderPanel.add(vibratoAmpVal);
		return vibratoAmpSliderPanel;
	}
	
	private JPanel setupVibratoPhaseSliders() {
		JPanel vibratoPhaseSuperPanel = new JPanel();
		vibratoPhaseSuperPanel.setLayout(new BoxLayout(vibratoPhaseSuperPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < NUM_WAVES; i++) {
			JPanel vibratoPhasePanel = new JPanel();
			vibratoPhasePanel.setLayout(new BoxLayout(vibratoPhasePanel, BoxLayout.X_AXIS));
			vibratoPhasePanel.add(Box.createRigidArea(new Dimension(0,-15)));
			JLabel vibratoPhaseVal = new JLabel("0");
			vibratoPhasePanel.add(new JLabel("Vibrato Phase - n = " + (i + 1)));
			vibratoPhasePanel.add(new VibratoPhaseSlider(vibratoPhaseVal, sineWaves[i]));
			vibratoPhasePanel.add(vibratoPhaseVal);
			vibratoPhaseSuperPanel.add(vibratoPhasePanel);
		}
		return vibratoPhaseSuperPanel;
	}
	
	private JPanel setupTremoloAmpSliders() {
		JPanel tremoloAmpSliderPanel = new JPanel();
		JLabel tremoloAmpVal = new JLabel("1");
		tremoloAmpSliderPanel.add(new JLabel("Tremolo Amplitude"));
		tremoloAmpSliderPanel.add(new TremoloAmpSlider(tremoloAmpVal));
		tremoloAmpSliderPanel.add(tremoloAmpVal);
		return tremoloAmpSliderPanel;
	}
	
	private JPanel setupTremoloFreqSliders() {
		JPanel tremoloFreqSliderPanel = new JPanel();
		JLabel tremoloFreqVal = new JLabel("1");
		tremoloFreqSliderPanel.add(new JLabel("Tremolo Frequency"));
		tremoloFreqSliderPanel.add(new TremoloFreqSlider(tremoloFreqVal));
		tremoloFreqSliderPanel.add(tremoloFreqVal);
		return tremoloFreqSliderPanel;
	}
	
	private JPanel setupWavePhaseSliders() {
		JPanel wavePhaseSuperPanel = new JPanel();
		wavePhaseSuperPanel.setLayout(new BoxLayout(wavePhaseSuperPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < NUM_WAVES; i++) {
			JPanel wavePhasePanel = new JPanel();
			wavePhasePanel.setLayout(new BoxLayout(wavePhasePanel, BoxLayout.X_AXIS));
			wavePhasePanel.add(Box.createRigidArea(new Dimension(0,-15)));
			JLabel wavePhaseVal = new JLabel("0");
			wavePhasePanel.add(new JLabel("Wave Phase - n = " + (i + 1)));
			wavePhasePanel.add(new WavePhaseSlider(wavePhaseVal, sineWaves[i]));
			wavePhasePanel.add(wavePhaseVal);
			wavePhaseSuperPanel.add(wavePhasePanel);
		}
		return wavePhaseSuperPanel;
	}
	
	public static void main(String[] args) {
		JFrame mainFrame = new Vibrato();
	}
	
	
	
}
