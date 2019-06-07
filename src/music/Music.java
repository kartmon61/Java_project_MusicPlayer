package Project;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Music implements Serializable{
	
	private int musicNum;
	private String musicName;
	private String musicTime;
	
	public static final int NAME_SIZE = 10;
	public static final int TIME_SIZE = 4;
	public static final int OBJECT_SIZE = 4 + NAME_SIZE * 2 + 8;
	
	public Music() {
		this.musicNum = 0;
		this.musicName = "";
		this.musicTime = "";
	}

	public Music(int musicNum, String musicName, String musicTime) {
		super();
		this.musicNum = musicNum;
		this.musicName = musicName;
		this.musicTime = musicTime;
	}

	public int getMusicNum() {
		return musicNum;
	}

	public void setMusicNum(int musicNum) {
		this.musicNum = musicNum;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicTime() {
		return musicTime;
	}

	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}
	
	
	public void writeData(RandomAccessFile raf) {
		try {
			raf.writeInt(musicNum);
			
			//if musicName is more than 10 ommit after 10 char
			if(musicName.length()>10) {
				musicName=musicName.substring(0, 10);
			}
			raf.writeChars(musicName);
			raf.writeChars(musicTime);
			
			for(int i = musicName.length();i<TIME_SIZE;i++) {
				raf.writeChar(0X00);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readData(RandomAccessFile raf) {
		int i = 0;
		try {
			musicNum = raf.readInt();
			while(i<NAME_SIZE) {
				char name = raf.readChar();
				i++;
				if(name==0X00) {
					break;
				}
				else {
					musicName +=name;
				}
			}
			
			while(i<TIME_SIZE) {
				char time = raf.readChar();
				i++;
				if(time==0X00) {
					break;
				}
				else {
					musicTime +=time;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
