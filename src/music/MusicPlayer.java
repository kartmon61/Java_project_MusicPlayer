package Project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JList;

public class MusicPlayer extends JFrame implements ActionListener{
	

	

	
	private JFrame frame;
	private JPanel pSongName;
	private JLabel lblSongTitle;
	private JLabel lblArtistTitle;
	private JPanel pOutput;
	private JPanel pTimeOutput;
	private JPanel pMenu;
	private JLabel lbOpenFile;
	private JLabel lbOpenPlaylist;
	private JLabel lbAddSong;
	private JLabel lbNewPlaylist;
	private JLabel lbRemoveSong;
	private JLabel lbSavePlaylist;
	private JLabel lbRemovePlaylist;
	private JButton btnOpenFile;
	private JButton btnAddSong;
	private JButton btnRemoveSong;
	private JButton btnOpenPlaylist;
	private JButton btnNewPlaylist;
	private JButton btnSavePlaylist;
	private JButton btnRemovePlaylist;
	
	
	int i = 0;
	//define the music numbering
	int numbering=0;
	private JPanel pList;
	private JPanel pShowList;
	private JLabel lblSongName;
	private JLabel lblArtist;
	private JLabel lblDuration;
	private JScrollPane scrollPane;
	private JTextArea textArea_1;
	private JPanel pTime;
	private JPanel pController;
	private JLabel lblTime;
	private JProgressBar progressBar;
	private JButton btnNewButton;
	private JButton button;
	private JButton button_1;
	private JButton btnNewButton_1;
	private JLabel lblVol;
	private JSlider slider;
	private JList list;
	
	
	public DefaultListModel listModel;
	ArrayList<String> musicList;
	//initialize Music object
	Music [] music = new Music[10];
	int musicNum;
	String musicName;
	String musicTime;
	String fileName;
	String filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicPlayer window = new MusicPlayer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MusicPlayer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 540, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pSongName = new JPanel();
		pSongName.setBorder(new EmptyBorder(20, 10, 10, 10));
		frame.getContentPane().add(pSongName, BorderLayout.NORTH);
		pSongName.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblSongTitle = new JLabel("Sample Song Name");
		lblSongTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		pSongName.add(lblSongTitle);
		
		lblArtistTitle = new JLabel("Sample Artist Name");
		pSongName.add(lblArtistTitle);
		
		pOutput = new JPanel();
		pOutput.setBorder(new EmptyBorder(0, 10, 10, 10));
		frame.getContentPane().add(pOutput, BorderLayout.CENTER);
		GridBagLayout gbl_pOutput = new GridBagLayout();
		gbl_pOutput.columnWidths = new int[]{400, 0};
		gbl_pOutput.rowHeights = new int[]{20, 162, 0, 0};
		gbl_pOutput.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pOutput.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		pOutput.setLayout(gbl_pOutput);
		
		pTimeOutput = new JPanel();
		GridBagConstraints gbc_pTimeOutput = new GridBagConstraints();
		gbc_pTimeOutput.insets = new Insets(0, 0, 5, 0);
		gbc_pTimeOutput.anchor = GridBagConstraints.NORTH;
		gbc_pTimeOutput.fill = GridBagConstraints.HORIZONTAL;
		gbc_pTimeOutput.gridx = 0;
		gbc_pTimeOutput.gridy = 0;
		pOutput.add(pTimeOutput, gbc_pTimeOutput);
		pTimeOutput.setLayout(new GridLayout(0, 1, 0, 0));
		
		pTime = new JPanel();
		pTimeOutput.add(pTime);
		
		lblTime = new JLabel("Time");
		pTime.add(lblTime);
		
		progressBar = new JProgressBar();
		pTime.add(progressBar);
		
		pController = new JPanel();
		FlowLayout fl_pController = (FlowLayout) pController.getLayout();
		fl_pController.setHgap(1);
		pTimeOutput.add(pController);
		
		btnNewButton = new JButton("|<");
		pController.add(btnNewButton);
		
		button = new JButton(">");
		pController.add(button);
		
		button_1 = new JButton("ㅁ");
		pController.add(button_1);
		
		btnNewButton_1 = new JButton(">|");
		pController.add(btnNewButton_1);
		
		lblVol = new JLabel("Vol");
		pController.add(lblVol);
		
		slider = new JSlider();
		pController.add(slider);
		
		pList = new JPanel();
		GridBagConstraints gbc_pList = new GridBagConstraints();
		gbc_pList.insets = new Insets(0, 0, 5, 0);
		gbc_pList.fill = GridBagConstraints.BOTH;
		gbc_pList.gridx = 0;
		gbc_pList.gridy = 1;
		pOutput.add(pList, gbc_pList);
		pList.setLayout(new BorderLayout(0, 0));
		
		pShowList = new JPanel();
		pList.add(pShowList, BorderLayout.NORTH);
		GridBagLayout gbl_pShowList = new GridBagLayout();
		gbl_pShowList.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pShowList.rowHeights = new int[]{0, 0};
		gbl_pShowList.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pShowList.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pShowList.setLayout(gbl_pShowList);
		
		lblSongName = new JLabel("Song name");
		lblSongName.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
		GridBagConstraints gbc_lblSongName = new GridBagConstraints();
		gbc_lblSongName.insets = new Insets(0, 0, 0, 5);
		gbc_lblSongName.gridx = 1;
		gbc_lblSongName.gridy = 0;
		pShowList.add(lblSongName, gbc_lblSongName);
		
		lblArtist = new JLabel("Artist");
		lblArtist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
		GridBagConstraints gbc_lblArtist = new GridBagConstraints();
		gbc_lblArtist.insets = new Insets(0, 0, 0, 5);
		gbc_lblArtist.gridx = 5;
		gbc_lblArtist.gridy = 0;
		pShowList.add(lblArtist, gbc_lblArtist);
		
		lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
		GridBagConstraints gbc_lblDuration = new GridBagConstraints();
		gbc_lblDuration.gridx = 10;
		gbc_lblDuration.gridy = 0;
		pShowList.add(lblDuration, gbc_lblDuration);
		scrollPane = new JScrollPane();
		pList.add(scrollPane, BorderLayout.CENTER);
		
		//make listModel
		listModel = new DefaultListModel();
		//make musicList
		musicList = new ArrayList<String>();
		
		//add listModel in list
		list = new JList(listModel);
		list.setVisibleRowCount(1);
		scrollPane.setViewportView(list);
		
		

		
		pMenu = new JPanel();
		GridBagConstraints gbc_pMenu = new GridBagConstraints();
		gbc_pMenu.fill = GridBagConstraints.BOTH;
		gbc_pMenu.gridx = 0;
		gbc_pMenu.gridy = 2;
		pOutput.add(pMenu, gbc_pMenu);
		GridBagLayout gbl_pMenu = new GridBagLayout();
		gbl_pMenu.columnWidths = new int[]{74, 0, 0, 0, 200, 0};
		gbl_pMenu.rowHeights = new int[]{42, 42, 42, 42, 0};
		gbl_pMenu.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pMenu.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pMenu.setLayout(gbl_pMenu);
		
		btnOpenFile = new JButton("OF");
		btnOpenFile.addActionListener(this);
			
		GridBagConstraints gbc_btnOpenFile = new GridBagConstraints();
		gbc_btnOpenFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpenFile.gridx = 1;
		gbc_btnOpenFile.gridy = 0;
		pMenu.add(btnOpenFile, gbc_btnOpenFile);
		
		lbOpenFile = new JLabel("Open file");
		GridBagConstraints gbc_lbOpenFile = new GridBagConstraints();
		gbc_lbOpenFile.fill = GridBagConstraints.BOTH;
		gbc_lbOpenFile.insets = new Insets(0, 0, 5, 5);
		gbc_lbOpenFile.gridx = 2;
		gbc_lbOpenFile.gridy = 0;
		pMenu.add(lbOpenFile, gbc_lbOpenFile);
		
		btnOpenPlaylist = new JButton("OP");
		btnOpenPlaylist.addActionListener(this);
		
		GridBagConstraints gbc_btnOpenPlaylist = new GridBagConstraints();
		gbc_btnOpenPlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpenPlaylist.gridx = 3;
		gbc_btnOpenPlaylist.gridy = 0;
		pMenu.add(btnOpenPlaylist, gbc_btnOpenPlaylist);
		
		lbOpenPlaylist = new JLabel("Open playlist");
		GridBagConstraints gbc_lbOpenPlaylist = new GridBagConstraints();
		gbc_lbOpenPlaylist.fill = GridBagConstraints.BOTH;
		gbc_lbOpenPlaylist.insets = new Insets(0, 0, 5, 0);
		gbc_lbOpenPlaylist.gridx = 4;
		gbc_lbOpenPlaylist.gridy = 0;
		pMenu.add(lbOpenPlaylist, gbc_lbOpenPlaylist);
		
		btnAddSong = new JButton("AS");
		btnAddSong.addActionListener(this);
		
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddSong.gridx = 1;
		gbc_btnAddSong.gridy = 1;
		pMenu.add(btnAddSong, gbc_btnAddSong);
		
		lbAddSong = new JLabel("Add song");
		GridBagConstraints gbc_lbAddSong = new GridBagConstraints();
		gbc_lbAddSong.fill = GridBagConstraints.BOTH;
		gbc_lbAddSong.insets = new Insets(0, 0, 5, 5);
		gbc_lbAddSong.gridx = 2;
		gbc_lbAddSong.gridy = 1;
		pMenu.add(lbAddSong, gbc_lbAddSong);
		
		btnNewPlaylist = new JButton("NP");
		btnNewPlaylist.addActionListener(this);
		
		GridBagConstraints gbc_btnNewPlaylist = new GridBagConstraints();
		gbc_btnNewPlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewPlaylist.gridx = 3;
		gbc_btnNewPlaylist.gridy = 1;
		pMenu.add(btnNewPlaylist, gbc_btnNewPlaylist);
		
		
		
		
		
		lbNewPlaylist = new JLabel("New playlist");
		GridBagConstraints gbc_lbNewPlaylist = new GridBagConstraints();
		gbc_lbNewPlaylist.fill = GridBagConstraints.BOTH;
		gbc_lbNewPlaylist.insets = new Insets(0, 0, 5, 0);
		gbc_lbNewPlaylist.gridx = 4;
		gbc_lbNewPlaylist.gridy = 1;
		pMenu.add(lbNewPlaylist, gbc_lbNewPlaylist);
		
		btnRemoveSong = new JButton("RS");
		btnRemoveSong.addActionListener(this);
		
		GridBagConstraints gbc_btnRemoveSong = new GridBagConstraints();
		gbc_btnRemoveSong.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSong.gridx = 1;
		gbc_btnRemoveSong.gridy = 2;
		pMenu.add(btnRemoveSong, gbc_btnRemoveSong);
		
		lbRemoveSong = new JLabel("Remove song");
		GridBagConstraints gbc_lbRemoveSong = new GridBagConstraints();
		gbc_lbRemoveSong.fill = GridBagConstraints.BOTH;
		gbc_lbRemoveSong.insets = new Insets(0, 0, 5, 5);
		gbc_lbRemoveSong.gridx = 2;
		gbc_lbRemoveSong.gridy = 2;
		pMenu.add(lbRemoveSong, gbc_lbRemoveSong);
		
		btnSavePlaylist = new JButton("SP");
		btnSavePlaylist.addActionListener(this);
		
		GridBagConstraints gbc_btnSavePlaylist = new GridBagConstraints();
		gbc_btnSavePlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_btnSavePlaylist.gridx = 3;
		gbc_btnSavePlaylist.gridy = 2;
		pMenu.add(btnSavePlaylist, gbc_btnSavePlaylist);
		
		lbSavePlaylist = new JLabel("Save playlist");
		GridBagConstraints gbc_lbSavePlaylist = new GridBagConstraints();
		gbc_lbSavePlaylist.fill = GridBagConstraints.BOTH;
		gbc_lbSavePlaylist.insets = new Insets(0, 0, 5, 0);
		gbc_lbSavePlaylist.gridx = 4;
		gbc_lbSavePlaylist.gridy = 2;
		pMenu.add(lbSavePlaylist, gbc_lbSavePlaylist);
		
		btnRemovePlaylist = new JButton("RP");
		btnRemovePlaylist.addActionListener(this);
		
		GridBagConstraints gbc_btnRemovePlaylist = new GridBagConstraints();
		gbc_btnRemovePlaylist.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemovePlaylist.gridx = 3;
		gbc_btnRemovePlaylist.gridy = 3;
		pMenu.add(btnRemovePlaylist, gbc_btnRemovePlaylist);
		
		lbRemovePlaylist = new JLabel("Remove playlist");
		GridBagConstraints gbc_lbRemovePlaylist = new GridBagConstraints();
		gbc_lbRemovePlaylist.fill = GridBagConstraints.BOTH;
		gbc_lbRemovePlaylist.gridx = 4;
		gbc_lbRemovePlaylist.gridy = 3;
		pMenu.add(lbRemovePlaylist, gbc_lbRemovePlaylist);	
	}
	

	
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		////When Openfile button clicked
		if(e.getSource()==btnOpenFile) {
			
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3","mp3");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(MusicPlayer.this);
			
			if(returnVal==JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				fileName=null;
				filePath=null;
				
				try {
					
					FileReader fis = new FileReader(file);
					BufferedReader reader = new BufferedReader(fis);
					listModel.removeAllElements();
					musicName = file.getName();
					musicTime = "01:00";
					lblSongTitle.setText(musicName);
					musicNum = 0;
					listModel.clear();
					musicList.clear();
					music[musicNum] = new Music(musicNum+1,musicName,musicTime);
					
					
					
					musicList.add(music[musicNum].getMusicNum()+"\t" + music[musicNum].getMusicName()+
							"\t"+music[musicNum].getMusicTime());
					
					for(String s:musicList) {
						listModel.addElement(s);
					}
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

		}
		////When Addsong button clicked
		else if(e.getSource()==btnAddSong) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3","mp3");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(MusicPlayer.this);
			
			if(returnVal==JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				try {
					FileReader fis = new FileReader(file);
					musicName = file.getName();
					musicTime = "02:00";
					listModel.clear();
					musicNum +=1;
					music[musicNum] = new Music(musicNum+1,musicName,musicTime);
					musicList.add(music[musicNum].getMusicNum()+"\t" + music[musicNum].getMusicName()+
							"\t"+music[musicNum].getMusicTime());
					
					
					for(String s:musicList) {
						listModel.addElement(s);
					}
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
		}
		else if(e.getSource()==btnRemoveSong) {
			
		}
		//When Openplaylist button clicked
		else if(e.getSource()==btnOpenPlaylist) {
			
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter fnef1 = new FileNameExtensionFilter("txt","txt");
			fc.setFileFilter(fnef1);
			int returnVal = fc.showOpenDialog(MusicPlayer.this);
			
			if(returnVal==JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				
				try {
					//clear List
					musicList.clear();
					listModel.clear();
					//get file name
					fileName = file.getName();
					//get file path
					filePath = file.getPath();
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
				
					String sCurrentLine;
					
					while((sCurrentLine = br.readLine())!=null) {
						//split the line by tab
						String[] split = sCurrentLine.split("\t");
						
						//First split is musicNumber
						musicNum = Integer.parseInt(split[0])-1;
						//Second split is musicName
						musicName = split[1];
						//Third split is musicTime
						musicTime = split[2];
						
						//initialize music constructor
						music[musicNum] = new Music(musicNum+1,musicName,musicTime);
						//add music list by the constructor
						musicList.add(music[musicNum].getMusicNum()+"\t" + music[musicNum].getMusicName()+
								"\t"+music[musicNum].getMusicTime());
					}
					
					br.close();
					
					//add listModel
					for(String s:musicList) {
						listModel.addElement(s);
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		//When NewPlaylist button clicked
		else if(e.getSource()==btnNewPlaylist) {
			for(int i=0;i<=musicNum;i++) {
				music[i]=null;
			}
			//clear List
			musicList.clear();
			listModel.clear();
		}
		//When savePlaylist button clicked
		else if(e.getSource()==btnSavePlaylist) {
			//saving the new playlist
			if(fileName==null) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter fnef1 = new FileNameExtensionFilter("txt","txt");
				fc.setFileFilter(fnef1);
				int returnVal = fc.showSaveDialog(MusicPlayer.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					
					try {
						fileName = file.getName();
						filePath = file.getPath();
						
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						
						for(int i=0;i<=musicNum;i++) {
							bw.write(musicList.get(i));
							bw.newLine();
						}
						
						bw.flush();
						bw.close();
						System.out.println("Save success");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			//if playlist is already exist, just update playlist file
			else {
				try {
				File file = new File(filePath);
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				
				for(int i=0;i<=musicNum;i++) {
					bw.write(musicList.get(i));
					bw.newLine();
				}
				bw.flush();
				bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		//removePlaylist
		else if(e.getSource()==btnRemovePlaylist) {
			
			try{
	    		
	    		File file = new File(filePath);
	        	//if using playlist is exist
	    		if(fileName!=null){
	    			file.delete();
	    			System.out.println(fileName + " is deleted!");
	    			fileName=null;
		    		filePath=null;
	    		}
	    		//if using playlist is not exist have to make exception
	    		else{
	    			System.out.println("Delete operation is failed.");
	    		}
	    		
	    	   
	    	}catch(Exception e1){
	    		
	    		e1.printStackTrace();
	    		
	    	}
			
			musicList.clear();
			listModel.clear();
		}
	}
}
