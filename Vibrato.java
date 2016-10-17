import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Vibrato extends JFrame implements Runnable {
	
	private static final int NUM_WAVES = 7;
	private static SineWave[] sineWaves;
	
	private static volatile double[] finalSineValsSound;
	private static volatile double[] finalSineValsPicture;

	private static boolean dataChanged;
	private static DisplayPanel disp;
	
	private static SoundGenerator soundGenerator;
	

	public Vibrato() throws LineUnavailableException {
		dataChanged = false;
		sineWaves = new SineWave[NUM_WAVES];
		for(int i = 0; i < sineWaves.length; i++) {
			sineWaves[i] = new SineWave(i + 1);
		}
		finalSineValsSound = new double[(int) (SineWave.NUM_SINE_POINTS * SineWave.BUFFER_DURATION)];
		finalSineValsPicture = new double[SineWave.NUM_SINE_POINTS];
		soundGenerator = new SoundGenerator(sineWaves);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JPanel slidersPanel = setupSliders();
		contentPane.add(slidersPanel);
		disp = new DisplayPanel();
		disp.setPreferredSize(new Dimension(1000, 300));
		disp.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(disp);
		this.pack();
		this.setVisible(true);
		calculateAllSineVals();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void redrawWave() {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				disp.drawWave(finalSineValsPicture);
			}
		};
		thread.start();
		dataChanged = false;
	}
	
	public static void calculateAllSineVals() {
		if(sineWaves != null) {
			for(int i = 0; i < sineWaves.length; i++) {
				sineWaves[i].calcSineVals();
			}
			dataChanged = true;
		}
		calcFinalSineVals();
	}
	
	public static void calcFinalSineVals() {
		if(finalSineValsSound != null && finalSineValsPicture != null) {
			finalSineValsSound = new double[(int) (SineWave.SAMPLE_RATE * SineWave.BUFFER_DURATION)];
			finalSineValsPicture = new double[SineWave.NUM_SINE_POINTS];
			for(int i = 0; i < sineWaves.length; i++) {
				double[] valsSound = sineWaves[i].getSineValsSound();
				double[] valsPicture = sineWaves[i].getSineValsPicture();
				for(int j = 0; j < finalSineValsSound.length; j++) {
					finalSineValsSound[j] += (valsSound[j]);
				}
				for(int j = 0; j < finalSineValsPicture.length; j++) {
					finalSineValsPicture[j] += valsPicture[j];
				}
			}
			soundGenerator.setData(finalSineValsSound);
			redrawWave();
		}
	}
	
	private JButton setupPlayButton() {
		JButton play = new JButton("Start Playing");
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(play.getText().equals("Start Playing")) {
					play.setText("Stop Playing");
					(new Thread(soundGenerator)).start();
				} else { 
					play.setText("Start Playing");
					soundGenerator.stopPlaying();
				}
			}
			
		});
		return play;
	}
	
	private JPanel setupSliders() {
		JPanel slidersSuperPanel = new JPanel();
		slidersSuperPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
		slidersSuperPanel.setPreferredSize(new Dimension(1500, 350));
		JPanel slidersPanelOne = new JPanel();
		slidersPanelOne.setLayout(new BoxLayout(slidersPanelOne, BoxLayout.Y_AXIS));
		slidersPanelOne.add(setupPlayButton());
		slidersPanelOne.add(setupFrequencySliders());
		slidersPanelOne.add(setupHarmonicSliders());
		JPanel slidersPanelTwo = new JPanel();
		slidersPanelTwo.setLayout(new BoxLayout(slidersPanelTwo, BoxLayout.Y_AXIS));
		slidersPanelTwo.add(setupVibratoAmpSliders());
		slidersPanelTwo.add(setupVibratoFreqSliders());
		slidersPanelTwo.add(setupFirstPhaseSliders());
		JPanel slidersPanelThree = new JPanel();
		slidersPanelThree.setLayout(new BoxLayout(slidersPanelThree, BoxLayout.Y_AXIS));
		slidersPanelThree.add(setupTremoloAmpSliders());
		slidersPanelThree.add(setupTremoloFreqSliders());
		slidersPanelThree.add(setupSecondPhaseSliders());
		slidersSuperPanel.add(slidersPanelOne);
		slidersSuperPanel.add(slidersPanelTwo);
		slidersSuperPanel.add(slidersPanelThree);
		return slidersSuperPanel;
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
		vibratoFreqSliderPanel.add(new VibratoFreqSlider(vibratoFreqVal));
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
	
	private JPanel setupFirstPhaseSliders() {
		JPanel firstPhaseSuperPanel = new JPanel();
		firstPhaseSuperPanel.setLayout(new BoxLayout(firstPhaseSuperPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < NUM_WAVES; i++) {
			JPanel firstPhasePanel = new JPanel();
			firstPhasePanel.setLayout(new BoxLayout(firstPhasePanel, BoxLayout.X_AXIS));
			firstPhasePanel.add(Box.createRigidArea(new Dimension(0,-15)));
			JLabel FirstPhaseVal = new JLabel("0");
			firstPhasePanel.add(new JLabel("First Phase - n = " + (i + 1)));
			firstPhasePanel.add(new FirstPhaseSlider(FirstPhaseVal, sineWaves[i]));
			firstPhasePanel.add(FirstPhaseVal);
			firstPhaseSuperPanel.add(firstPhasePanel);
		}
		return firstPhaseSuperPanel;
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
	
	private JPanel setupSecondPhaseSliders() {
		JPanel secondPhaseSuperPanel = new JPanel();
		secondPhaseSuperPanel.setLayout(new BoxLayout(secondPhaseSuperPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < NUM_WAVES; i++) {
			JPanel secondPhasePanel = new JPanel();
			secondPhasePanel.setLayout(new BoxLayout(secondPhasePanel, BoxLayout.X_AXIS));
			secondPhasePanel.add(Box.createRigidArea(new Dimension(0,-15)));
			JLabel secondPhaseVal = new JLabel("0");
			secondPhasePanel.add(new JLabel("Second Phase - n = " + (i + 1)));
			secondPhasePanel.add(new SecondPhaseSlider(secondPhaseVal, sineWaves[i]));
			secondPhasePanel.add(secondPhaseVal);
			secondPhaseSuperPanel.add(secondPhasePanel);
		}
		return secondPhaseSuperPanel;
	}
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					new Vibrato();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}

	@Override
	public void run() {
		
	}
	
	
	
}
