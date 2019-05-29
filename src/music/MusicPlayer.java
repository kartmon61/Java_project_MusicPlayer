package Project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

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

public class MusicPlayer {

	private JFrame frame;
	
	
	private JPanel pSongName;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPanel pOutput;
	private JPanel pTimeOutput;
	private JLabel lblNewLabel_2;
	private JProgressBar progressBar;
	private JPanel pMenu;
	private JScrollPane scrollSongList;
	private JTextArea taSongList;
	private JLabel lbOpenFile;
	private JLabel lbOpenPlaylist;
	private JLabel lbAddSong;
	private JLabel lbNewPlaylist;
	private JLabel lbRemoveSong;
	private JLabel lbSaveayost;
	private JLabel lbNone;
	private JLabel lbRemovePlaylist;

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
		frame.setBounds(100, 100, 420, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pSongName = new JPanel();
		pSongName.setBorder(new EmptyBorder(20, 10, 10, 10));
		frame.getContentPane().add(pSongName, BorderLayout.NORTH);
		pSongName.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("Sample Song Name");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		pSongName.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Sample Artist Name");
		pSongName.add(lblNewLabel_1);
		
		pOutput = new JPanel();
		pOutput.setBorder(new EmptyBorder(0, 10, 10, 10));
		frame.getContentPane().add(pOutput, BorderLayout.CENTER);
		GridBagLayout gbl_pOutput = new GridBagLayout();
		gbl_pOutput.columnWidths = new int[]{400, 0};
		gbl_pOutput.rowHeights = new int[]{30, 0, 0, 0};
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
		pTimeOutput.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel_2 = new JLabel("Time");
		pTimeOutput.add(lblNewLabel_2);
		
		progressBar = new JProgressBar();
		pTimeOutput.add(progressBar);
		taSongList = new JTextArea();
		scrollSongList = new JScrollPane(taSongList);
		GridBagConstraints gbc_scrollSongList = new GridBagConstraints();
		gbc_scrollSongList.insets = new Insets(0, 0, 5, 0);
		gbc_scrollSongList.fill = GridBagConstraints.BOTH;
		gbc_scrollSongList.gridx = 0;
		gbc_scrollSongList.gridy = 1;
		pOutput.add(scrollSongList, gbc_scrollSongList);
		
		pMenu = new JPanel();
		GridBagConstraints gbc_pMenu = new GridBagConstraints();
		gbc_pMenu.fill = GridBagConstraints.BOTH;
		gbc_pMenu.gridx = 0;
		gbc_pMenu.gridy = 2;
		pOutput.add(pMenu, gbc_pMenu);
		pMenu.setLayout(new GridLayout(0, 2, 0, 0));
		
		lbOpenFile = new JLabel("Open file");
		pMenu.add(lbOpenFile);
		
		lbOpenPlaylist = new JLabel("Open playlist");
		pMenu.add(lbOpenPlaylist);
		
		lbAddSong = new JLabel("Add song");
		pMenu.add(lbAddSong);
		
		lbNewPlaylist = new JLabel("New playlist");
		pMenu.add(lbNewPlaylist);
		
		lbRemoveSong = new JLabel("Remove song");
		pMenu.add(lbRemoveSong);
		
		lbSaveayost = new JLabel("Save playlist");
		pMenu.add(lbSaveayost);
		
		lbNone = new JLabel("");
		pMenu.add(lbNone);
		
		lbRemovePlaylist = new JLabel("Remove playlist");
		pMenu.add(lbRemovePlaylist);
		
		
		
		
		
		
	}

}
