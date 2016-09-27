import javax.swing.JLabel;

public class SineWave {

	public static final int NUM_SINE_POINTS = 1000;
	public static final int SAMPLE_RATE = 44100;
	public static final double BUFFER_DURATION = .500;
	private static final double DT =  1.0 / SAMPLE_RATE;
	
	private static double FREQ = 220;
	private static double VIBRATO_AMP = 0.0;
	private static double VIBRATO_FREQ = 0.0;
	private static double TREMOLO_AMP = 0.0;
	private static double TREMOLO_FREQ = 0.0;
	
	private double harmonicNum;
	private double harmonicAmp;
	private double wavePhase;
	private double vibratoPhase;
	private volatile double[] sineValsSound;
	private double[] sineValsPicture;

	public SineWave(double harmonicNum) {
		sineValsSound = new double[(int) (SAMPLE_RATE * BUFFER_DURATION)];
		sineValsPicture = new double[NUM_SINE_POINTS];
		harmonicAmp = 1.0;
		wavePhase = 0.0;
		vibratoPhase = 0.0;
		this.harmonicNum = harmonicNum;
		calcSineVals();
	}
	
	public void calcSineVals() {
		System.out.println("FREQ: " + FREQ);
		System.out.println("VIBRATO_AMP: " + VIBRATO_AMP);
		System.out.println("VIBRATO_FREQ: " + VIBRATO_FREQ);
		System.out.println("TREMOLO_AMP: " + TREMOLO_AMP);
		System.out.println("TREMOLO_FREQ: " + TREMOLO_FREQ);
		System.out.println(harmonicNum);
		System.out.println(harmonicAmp);
		System.out.println(wavePhase);
		System.out.println(vibratoPhase);
		
//		for(int i = 1; i <= NUM_SINE_POINTS; i++) {
//			sineVals[i - 1] = 10 * (1 - TREMOLO_AMP * Math.sin(2 * Math.PI * TREMOLO_FREQ * i / NUM_SINE_POINTS)) * 
//							(harmonicAmp * Math.sin(2 * Math.PI * harmonicNum * i * FREQ / NUM_SINE_POINTS + 
//									(VIBRATO_AMP * Math.sin(2 * Math.PI * VIBRATO_FREQ * i / NUM_SINE_POINTS + vibratoPhase)) + wavePhase));
//		}
		
		for(int i = 0; i < NUM_SINE_POINTS; i++) {
			sineValsPicture[i] = 18 * (1 - TREMOLO_AMP * Math.sin(2 * Math.PI * TREMOLO_FREQ * i / NUM_SINE_POINTS)) * harmonicAmp * Math.sin(2 * Math.PI * 2 * harmonicNum * i / NUM_SINE_POINTS + wavePhase + (VIBRATO_AMP * Math.sin(2 * Math.PI * VIBRATO_FREQ * i / NUM_SINE_POINTS + vibratoPhase)));
//			System.out.println(sineVals[i]);
		}
		for(int i = 0; i < sineValsSound.length; i++) {
//			sineValsSound[i] = 1.0 / 70 / 4 *Short.MAX_VALUE * (Math.sin(2 * Math.PI * 440 * i / 44100) + Math.sin(2 * Math.PI * 440 * 2 * i / 44100));
			sineValsSound[i] = (1 - TREMOLO_AMP * Math.sin(2 * Math.PI * TREMOLO_FREQ * i / sineValsSound.length)) * harmonicAmp * Math.sin(2 * Math.PI * harmonicNum * FREQ * i / sineValsSound.length + wavePhase + (VIBRATO_AMP * Math.sin(2 * Math.PI * VIBRATO_FREQ * i / sineValsSound.length + vibratoPhase)));
		}

		Vibrato.calcFinalSineVals();
	}
	
	public synchronized double[] getSineValsSound() {
		return sineValsSound;
	}

	public synchronized double[] getSineValsPicture() {
		return sineValsPicture;
	}
	
	public synchronized static double getFreq() {
		return FREQ;
	}

	public synchronized static void setFreq(double freq) {
		SineWave.FREQ = freq;
	}

	public synchronized static double getVibratoAmp() {
		return VIBRATO_AMP;
	}

	public synchronized static void setVibratoAmp(double vibratoAmp) {
		SineWave.VIBRATO_AMP = vibratoAmp;
	}
	
	public synchronized static void setVibratoFreq(double freq) {
		SineWave.VIBRATO_FREQ = freq;
	}
	
	public synchronized static double getVibratoFreq() {
		return SineWave.VIBRATO_FREQ;
	}
	
	public synchronized void setHarmonicAmp(double amp) {
		harmonicAmp = amp;
	}
	
	public synchronized double getHarmonicAmp() {
		return harmonicAmp;
	}
	
	public synchronized double getHarmonicNum() {
		return harmonicNum;
	}
	
	public synchronized void setVibratoPhase(double phase) {
		vibratoPhase = phase;
	}
	
	public synchronized double getVibratoPhase() {
		return vibratoPhase;
	}
	
	public synchronized void setWavePhase(double phase) {
		wavePhase = phase;
	}
	
	public synchronized double getWavePhase() {
		return wavePhase;
	}
	
	public synchronized static void setTremoloAmp(double amp) {
		SineWave.TREMOLO_AMP = amp;
	}
	
	public synchronized static double getTremoloAmp() {
		return SineWave.TREMOLO_AMP;
	}
	
	public synchronized static void setTremoloFreq(double freq) {
		SineWave.TREMOLO_FREQ = freq;
	}
	
	public synchronized static double getTremoloFreq() {
		return SineWave.TREMOLO_FREQ;
	}
	
}
