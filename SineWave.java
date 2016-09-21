import javax.swing.JLabel;

public class SineWave {

	public static final int NUM_SINE_POINTS = 1000;
	
	private static double FREQ = 100;
	private static double VIBRATO_AMP = 1.0;
	private static double VIBRATO_FREQ = 1.0;
	private static double TREMOLO_AMP = 0.0;
	private static double TREMOLO_FREQ = 0.0;
	
	private double harmonicNum;
	private double harmonicAmp;
	private double wavePhase;
	private double vibratoPhase;
	private double[] sineVals;

	public SineWave(double harmonicNum) {
		sineVals = new double[NUM_SINE_POINTS];
		harmonicAmp = 1.0;
		wavePhase = 0.0;
		vibratoPhase = 0.0;
		this.harmonicNum = harmonicNum;
		calcSineVals();
	}
	
	public double[] calcSineVals() {
		for(int i = 0; i < NUM_SINE_POINTS; i++) {
			sineVals[i] = (1 - TREMOLO_AMP * Math.sin(2 * Math.PI * TREMOLO_FREQ * i)) * 
							(harmonicAmp * Math.sin(2 * Math.PI * harmonicNum * i + 
									(VIBRATO_AMP * Math.sin(2 * Math.PI * VIBRATO_FREQ * i + vibratoPhase)) + wavePhase));
		}
		return sineVals;
	}
	
	public double[] getSineVals() {
		return sineVals;
	}

	public static double getFreq() {
		return FREQ;
	}

	public static void setFreq(double freq) {
		SineWave.FREQ = freq;
	}

	public static double getVibratoAmp() {
		return VIBRATO_AMP;
	}

	public static void setVibratoAmp(double vibratoAmp) {
		SineWave.VIBRATO_AMP = vibratoAmp;
	}
	
	public static void setVibratoFreq(double freq) {
		SineWave.VIBRATO_FREQ = freq;
	}
	
	public void setHarmonicAmp(double amp) {
		harmonicAmp = amp;
	}
	
	public void setVibratoPhase(double phase) {
		vibratoPhase = phase;
	}
	
	public void setWavePhase(double phase) {
		wavePhase = phase;
	}
	
	public static void setTremoloAmp(double amp) {
		SineWave.TREMOLO_AMP = amp;
	}
	
	public static void setTremoloFreq(double freq) {
		SineWave.TREMOLO_FREQ = freq;
	}
	
}
