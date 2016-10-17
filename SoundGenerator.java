import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundGenerator implements Runnable {
	
	private static final int SAMPLE_RATE = 44100;
	private static final int SAMPLE_SIZE = 2;
	private static final int BITS = 16;
	private static final double BUFFER_DURATION = 0.100;
	private static final int SINE_PACKET_SIZE = (int) (SAMPLE_RATE * SAMPLE_SIZE * BUFFER_DURATION);
	
	private SourceDataLine line;
	private boolean keepPlaying;
	private double[] data;
	private ByteBuffer cBuf;
	private int position;
	
	private SineWave[] waves;
	
	public SoundGenerator(SineWave[] waves) throws LineUnavailableException {
		AudioFormat format = new AudioFormat(SAMPLE_RATE,BITS,1,true,true);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, SINE_PACKET_SIZE * 2);
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(format);
		cBuf = ByteBuffer.allocate(SINE_PACKET_SIZE);
		position = 0;
		keepPlaying = false;
		this.waves = waves;
	}


//	public synchronized double[] calcFinalWave() {
//		double[] finalWave = new double[SINE_PACKET_SIZE/SAMPLE_SIZE];
//		for(int i = 0; i < waves.length; i++) {
//			for(int j = 0; j < SineWave.SAMPLE_RATE; j++) {
//				finalWave[j] = 20 * (1 - SineWave.getTremoloAmp() * Math.sin(2 * Math.PI * SineWave.getTremoloFreq() * i / SAMPLE_RATE)) * waves[i].getHarmonicAmp() * Math.sin(2 * Math.PI * 2 * waves[i].getHarmonicNum() * SineWave.getFreq() * i / SAMPLE_RATE + waves[i].getWavePhase() + (SineWave.getVibratoAmp() * Math.sin(2 * Math.PI * SineWave.getVibratoFreq() * i / SAMPLE_RATE + waves[i].getVibratoPhase())));
//			}
//		}
//		return finalWave;
//	}
	
    public double fFreq;                                    //Set from the pitch slider
    public boolean bExitThread = false;
    
    
    //Get the number of queued samples in the SourceDataLine buffer
    private int getLineSampleCount() {
       return line.getBufferSize() - line.available();
    }
    

    //Continually fill the audio output buffer whenever it starts to get empty, SINE_PACKET_SIZE/2
    //samples at a time, until we tell the thread to exit
    public void run() {
    	keepPlaying = true;
       //Position through the sine wave as a percentage (i.e. 0-1 is 0-2*PI)
       double fCyclePosition = 0;
       
       //Open up the audio output, using a sampling rate of 44100hz, 16 bit samples, mono, and big 
       // endian byte ordering.   Ask for a buffer size of at least 2*SINE_PACKET_SIZE
       try {
          AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
          DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, SINE_PACKET_SIZE*3);

          if (!AudioSystem.isLineSupported(info))
             throw new LineUnavailableException();

          line = (SourceDataLine)AudioSystem.getLine(info);
          line.open(format);  
          line.start();
       }
       catch (LineUnavailableException e) {
          System.out.println("Line of that type is not available");
          e.printStackTrace();            
          System.exit(-1);
       }

       System.out.println("Requested line buffer size = " + SINE_PACKET_SIZE*2);            
       System.out.println("Actual line buffer size = " + line.getBufferSize());


       ByteBuffer cBuf = ByteBuffer.allocate(SINE_PACKET_SIZE);

       //On each pass main loop fills the available free space in the audio buffer
       //Main loop creates audio samples for sine wave, runs until we tell the thread to exit
       //Each sample is spaced 1/SAMPLING_RATE apart in time
//       double[] tempData = new double[SINE_PACKET_SIZE/SAMPLE_SIZE];
//       for(int i = 0; i < tempData.length; i++) {
//    	   tempData[i] = Short.MAX_VALUE * Math.sin(2 * Math.PI * 440 * i / 44100);
//       }
       while (keepPlaying) {
//          fFreq = 440;
//
//          double fCycleInc = fFreq/SAMPLE_RATE;   //Fraction of cycle between samples

          cBuf.clear();                             //Toss out samples from previous pass

          //Generate SINE_PACKET_SIZE samples based on the current fCycleInc from fFreq
          for (int i=0; i < data.length; i++) {
//             cBuf.putShort((short)(Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));
        	  cBuf.putShort((short)(1000 * data[i]));
//             fCyclePosition += fCycleInc;
//             if (fCyclePosition > 1)
//                fCyclePosition -= 1;
          }

          //Write sine samples to the line buffer
          // If the audio buffer is full, this would block until there is enough room,
          // but we are not writing unless we know there is enough space.
          line.write(cBuf.array(), 0, cBuf.position());


          //Wait here until there are less than SINE_PACKET_SIZE samples in the buffer
          //(Buffer size is 2*SINE_PACKET_SIZE at least, so there will be room for 
          // at least SINE_PACKET_SIZE samples when this is true)
//          try {
//             while (getLineSampleCount() > SINE_PACKET_SIZE) 
//                Thread.sleep(1);                          // Give UI a chance to run 
//          }
//          catch (InterruptedException e) {                // We don't care about this
//          }
       }

       line.drain();
       line.close();
    }
    
	
//	@Override
//	public void run() {
//		keepPlaying = true;
//		line.start();
//		while(keepPlaying) {
//			
//			cBuf.clear();
//			for(int i = 0; i < SINE_PACKET_SIZE / BITS; i++) {
//				cBuf.putDouble(data[(i + position) % cBuf.limit()]);
//			}
//			line.write(cBuf.array(), 0, cBuf.position());
//			while(line.getBufferSize() - line.available() > line.available()) {
//				try {
//					Thread.sleep(1);
////					System.out.println("Sleep");
////					System.out.println(line.getBufferSize());
////					System.out.println(line.available());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			System.out.println("NOTSTUCK");
//		}
//		line.drain();
//		line.close();
//	}
//	
	public void setData(double[] data) {
		this.data = data.clone();
	}
	
	public void stopPlaying() {
		keepPlaying = false;
	}

}
