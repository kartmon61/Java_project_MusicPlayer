package music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

public class MusicTimer {
	private File file;
	private double totalSec;
	private int min;
	private int sec;
	private String time;
	
	MusicTimer(File file){
		this.file = file;
	}
	
	public void calTime() {
		Header h = null;
		FileInputStream fi = null;
		Bitstream bitstream = null;
		
		
		try {
			
			fi = new FileInputStream(file);
			bitstream = new  Bitstream(fi);
			h = bitstream.readFrame();
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BitstreamException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	    long tn = 0;
	    
	    try {
			tn = fi.getChannel().size();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	   totalSec = h.total_ms((int) tn)/1000;
	   System.out.println(totalSec);
	   min = (int)(totalSec/60);
	   sec = (int)(totalSec-min*60);
	   
	   time = min + ":" +sec;
	}

	public double getTotalSec() {
		return totalSec;
	}

	public void setTotalSec(double totalSec) {
		this.totalSec = totalSec;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
   
   

}
