package Project;

public class Music {
	
	private int musicNum;
	private String musicName;
	private String musicTime;
	private String musicArtist;
	
	
	public Music(int musicNum, String musicName, String musicArtist, String musicTime) {
		super();
		this.musicNum = musicNum;
		this.musicName = musicName;
		this.musicTime = musicTime;
		this.musicArtist = musicArtist;
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


	public String getMusicArtist() {
		return musicArtist;
	}


	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}

	
	
	
	
	
}
