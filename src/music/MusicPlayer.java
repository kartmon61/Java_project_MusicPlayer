/** 
 * 12131819 YOOK DONGHYUN, 12345678 LEE JINHO
 * Java Application Programming-002 (Prof. Tamer) // Final Project
 * ============================================================================
 * update log
 * -----------------------------------------------------------------------------
 * - 2019.06.09 : code-refactoring and put some comments on each code (by YOOK)
 */
package music;

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
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MusicPlayer extends JFrame implements ActionListener
{
   private static final long serialVersionUID = 1L;
   
   /** GUI Components */
   private JFrame frame;
   private JPanel pnlSongName;
   private JLabel lblSongTitle;
   private JLabel lblArtistTitle;
   private JPanel pnlOutput;
   private JPanel pnlTimeOutput;
   private JPanel pnlMenu;
   private JLabel lblOpenFile;
   private JLabel lblOpenPlaylist;
   private JLabel lblAddSong;
   private JLabel lblNewPlaylist;
   private JLabel lblRemoveSong;
   private JLabel lblSavePlaylist;
   private JLabel lblRemovePlaylist;
   private JButton btnOpenFile;
   private JButton btnAddSong;
   private JButton btnRemoveSong;
   private JButton btnOpenPlaylist;
   private JButton btnNewPlaylist;
   private JButton btnSavePlaylist;
   private JButton btnRemovePlaylist;
   private JPanel pnlList;
   private JPanel pnlShowList;
   private JLabel lblSongName;
   private JLabel lblArtist;
   private JLabel lblDuration;
   private JScrollPane scrollPane;
   private JPanel pnlTime;
   private JPanel pnlController;
   private JLabel lblTime;
   private JProgressBar progressBar;
   private JButton btnBack;
   private JButton btnPlay;
   private JButton btnPause;
   private JButton btnStop;
   private JButton btnNext;
   private JLabel lblVol;
   private JSlider slider;
   
   private JList list;
   public DefaultListModel listModel;

   /** User-defined Member Fields */
   private ArrayList<String> musicList;
   private ArrayList<Music> music;
   private Thread playMP3;
   private int musicNum;
   private String musicName;
   private String musicArtist;
   private String musicTime;
   private String musicPath;
   private String fileName;
   private String filePath;
   private JPanel pnlButton;
   private JPanel pnlVolume;
   
   private VolumeController volumeController = new VolumeController(0.5F);

   /** Launch the application */
   public static void main(String[] args) 
   {
      EventQueue.invokeLater(new Runnable() 
      {
         public void run() 
         {
            try 
            {
               MusicPlayer window = new MusicPlayer();
               window.frame.setVisible(true);
            } 
            catch (Exception e) { e.printStackTrace(); }
         }
      });
   }

   /** Create the application */
   public MusicPlayer() { initialize(); }

   /** Initialize the contents of the frame */
   private void initialize() 
   {
      /* JFrame */
      try 
      {
         /** Apply Look And Feel */
         UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
         JFrame.setDefaultLookAndFeelDecorated(true);
      } 
      catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
      {
         e.printStackTrace();
      }
      frame = new JFrame("Music Player");
      frame.setBounds(100, 100, 565, 642);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      /** ====================== Header of Music Player (Start) ========================== */
      /* Panel for Song Name */
      pnlSongName = new JPanel();
      pnlSongName.setBorder(new EmptyBorder(20, 10, 10, 10));
      frame.getContentPane().add(pnlSongName, BorderLayout.NORTH);
      pnlSongName.setLayout(new GridLayout(0, 1, 0, 0));
      
      /* Label for song title */
      lblSongTitle = new JLabel("Sample Song Name");
      lblSongTitle.setFont(new Font("Nanum Gothic", Font.BOLD, 20));
      pnlSongName.add(lblSongTitle);
      
      /* Label for artist title */
      lblArtistTitle = new JLabel("Sample Artist Name");
      lblArtistTitle.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      pnlSongName.add(lblArtistTitle);
      /** ====================== Header of Music Player (End) ========================== */
      
      
      /** ====================== Body of Music Player (Start) ========================== */
      /* Panel for Output */
      pnlOutput = new JPanel();
      pnlOutput.setBorder(new EmptyBorder(0, 10, 10, 10));
      frame.getContentPane().add(pnlOutput, BorderLayout.CENTER);
      GridBagLayout gbl_pnlOutput = new GridBagLayout();
      gbl_pnlOutput.columnWidths = new int[]{400, 0};
      gbl_pnlOutput.rowHeights = new int[]{20, 162, 0, 0};
      gbl_pnlOutput.columnWeights = new double[]{1.0, Double.MIN_VALUE};
      gbl_pnlOutput.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
      pnlOutput.setLayout(gbl_pnlOutput);
      
      /* ======================== Time Output (Start) =================================== */
      /* Panel for Time Output */
      pnlTimeOutput = new JPanel();
      GridBagConstraints gbc_pnlTimeOutput = new GridBagConstraints();
      gbc_pnlTimeOutput.insets = new Insets(0, 0, 5, 0);
      gbc_pnlTimeOutput.anchor = GridBagConstraints.NORTH;
      gbc_pnlTimeOutput.fill = GridBagConstraints.HORIZONTAL;
      gbc_pnlTimeOutput.gridx = 0;
      gbc_pnlTimeOutput.gridy = 0;
      pnlOutput.add(pnlTimeOutput, gbc_pnlTimeOutput);
      pnlTimeOutput.setLayout(new GridLayout(0, 1, 0, 0));
      
      /* Panel for Time */
      pnlTime = new JPanel();
      FlowLayout flowLayout = (FlowLayout) this.pnlTime.getLayout();
      flowLayout.setHgap(1);
      pnlTimeOutput.add(pnlTime);
      
      /* Label for Time */
      lblTime = new JLabel("Time");
      lblTime.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      pnlTime.add(lblTime);
      
      /* Progress Bar */
      progressBar = new JProgressBar();
      pnlTime.add(progressBar);
      /* ======================== Time Output (End) ====================================== */
      
      /* ======================== Controller (Start) ===================================== */
      /* Panel for Controller */
      pnlController = new JPanel();
      pnlTimeOutput.add(pnlController);
      this.pnlController.setLayout(new GridLayout(2, 0, 0, 0));
      
      /** =================== Panel for MP3 Control Buttons ============================== */
      this.pnlButton = new JPanel();
      this.pnlController.add(this.pnlButton);
      
      /* Back Button */
      btnBack = new JButton(new ImageIcon("src/resources/back.png"));
      this.pnlButton.add(this.btnBack);
      btnBack.addActionListener(this);
      
      /* Play Button */
      btnPlay = new JButton(new ImageIcon("src/resources/play.png"));
      this.pnlButton.add(this.btnPlay);
      btnPlay.addActionListener(this);
      
      /* Pause Button */
      btnPause =  new JButton(new ImageIcon("src/resources/pause.png"));
      this.pnlButton.add(this.btnPause);
      this.btnPause.setEnabled(false);
      btnPause.addActionListener(this);
      
      /* Stop Button */
      btnStop = new JButton(new ImageIcon("src/resources/stop.png"));
      this.pnlButton.add(this.btnStop);
      this.btnStop.setEnabled(false);
      btnStop.addActionListener(this);
      
      /* Next Button */
      btnNext = new JButton(new ImageIcon("src/resources/next.png"));
      this.pnlButton.add(this.btnNext);
      btnNext.addActionListener(this);
   
      /** ====================== Panel for Volume Control ============================== */
      this.pnlVolume = new JPanel();
      this.pnlController.add(this.pnlVolume);
      this.pnlVolume.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
      
      /* Label for Volume */
      lblVol = new JLabel(new ImageIcon("src/resources/volume.png"));
      this.pnlVolume.add(this.lblVol);
      
      /* Slider for Volume */
      volumeController.start();
      slider = new JSlider();
      slider.addChangeListener(new ChangeListener() 
      {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                JSlider source = (JSlider)e.getSource();
                 if (!source.getValueIsAdjusting())
                 {
                   System.out.println("Set volume to " + (int)source.getValue());
                   volumeController.volumeControl((float)(source.getValue())/100);
                   
                   lblVolSize.setText("" + (int)source.getValue());
                 }
            }
        });
      this.pnlVolume.add(this.slider);
      
      /* Label for Volume Size */
      this.lblVolSize = new JLabel("50");
      this.lblVolSize.setFont(new Font("Dialog", Font.BOLD, 18));
      this.pnlVolume.add(this.lblVolSize);
      
      
      /* ======================== Controller (End) =================================== */
      
      /* ======================== Music List (Start) =================================== */
      /* Panel for List */
      pnlList = new JPanel();
      GridBagConstraints gbc_pnlList = new GridBagConstraints();
      gbc_pnlList.insets = new Insets(0, 0, 5, 0);
      gbc_pnlList.fill = GridBagConstraints.BOTH;
      gbc_pnlList.gridx = 0;
      gbc_pnlList.gridy = 1;
      pnlOutput.add(pnlList, gbc_pnlList);
      pnlList.setLayout(new BorderLayout(0, 0));
      
      /* Panel for ShowList */
      pnlShowList = new JPanel();
      pnlList.add(pnlShowList, BorderLayout.NORTH);
      pnlShowList.setLayout(new GridLayout(0, 3, 0, 0));
      lblSongName = new JLabel(" Song name");
      pnlShowList.add(lblSongName);
      lblSongName.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      lblArtist = new JLabel("Artist");
      pnlShowList.add(lblArtist);
      lblArtist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      lblDuration = new JLabel("Duration");
      pnlShowList.add(lblDuration);
      lblDuration.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      
      /* Label for Song Name */
      
      /* Label for Artist */
      
      /* Label for Duration */
      scrollPane = new JScrollPane();
      pnlList.add(scrollPane, BorderLayout.CENTER);
      
      /* JList */
      listModel = new DefaultListModel(); // instantiate DefaultListModel
      musicList = new ArrayList<String>(); // instantiate ArrayList MusicList
      music = new ArrayList<Music>(); // instantiate ArrayList for Music
      list = new JList(listModel); // instantiate JList with DefaultListModel
      list.setVisibleRowCount(1);
      scrollPane.setViewportView(list);
      /* ======================== Music List (End) =================================== */

      /* ======================== Control Button List (Start) ======================== */
      /* Panel for Control Menu */
      pnlMenu = new JPanel();
      GridBagConstraints gbc_pnlMenu = new GridBagConstraints();
      gbc_pnlMenu.fill = GridBagConstraints.BOTH;
      gbc_pnlMenu.gridx = 0;
      gbc_pnlMenu.gridy = 2;
      pnlOutput.add(pnlMenu, gbc_pnlMenu);
      GridBagLayout gbl_pnlMenu = new GridBagLayout();
      gbl_pnlMenu.columnWidths = new int[]{90, 0, 0, 0, 200, 0};
      gbl_pnlMenu.rowHeights = new int[]{50, 50, 50, 50, 0};
      gbl_pnlMenu.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
      gbl_pnlMenu.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
      pnlMenu.setLayout(gbl_pnlMenu);

      /* Open File Button */
      btnOpenFile = new JButton(new ImageIcon("src/resources/OpenFile.png"));
      btnOpenFile.addActionListener(this);
      // Layout for Open file
      GridBagConstraints gbc_btnOpenFile = new GridBagConstraints();
      gbc_btnOpenFile.insets = new Insets(0, 0, 5, 5);
      gbc_btnOpenFile.gridx = 1;
      gbc_btnOpenFile.gridy = 0;
      pnlMenu.add(btnOpenFile, gbc_btnOpenFile);
      // Label for Open File
      lblOpenFile = new JLabel("Open File");
      lblOpenFile.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblOpenFile = new GridBagConstraints();
      gbc_lblOpenFile.fill = GridBagConstraints.BOTH;
      gbc_lblOpenFile.insets = new Insets(0, 0, 5, 5);
      gbc_lblOpenFile.gridx = 2;
      gbc_lblOpenFile.gridy = 0;
      pnlMenu.add(lblOpenFile, gbc_lblOpenFile);
      
      /* Open Play List Button */
      btnOpenPlaylist = new JButton(new ImageIcon("src/resources/OpenPlayList.png"));
      btnOpenPlaylist.addActionListener(this);
      // Layout for Open Play List
      GridBagConstraints gbc_btnOpenPlaylist = new GridBagConstraints();
      gbc_btnOpenPlaylist.insets = new Insets(0, 0, 5, 5);
      gbc_btnOpenPlaylist.gridx = 3;
      gbc_btnOpenPlaylist.gridy = 0;
      pnlMenu.add(btnOpenPlaylist, gbc_btnOpenPlaylist);
      // Label for Open Play List
      lblOpenPlaylist = new JLabel("Open Playlist");
      lblOpenPlaylist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblOpenPlaylist = new GridBagConstraints();
      gbc_lblOpenPlaylist.fill = GridBagConstraints.BOTH;
      gbc_lblOpenPlaylist.insets = new Insets(0, 0, 5, 0);
      gbc_lblOpenPlaylist.gridx = 4;
      gbc_lblOpenPlaylist.gridy = 0;
      pnlMenu.add(lblOpenPlaylist, gbc_lblOpenPlaylist);
      
      /* Add Song Button */
      btnAddSong = new JButton(new ImageIcon("src/resources/AddSong.png"));
      btnAddSong.addActionListener(this);
      // Layout for Add Song
      GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
      gbc_btnAddSong.insets = new Insets(0, 0, 5, 5);
      gbc_btnAddSong.gridx = 1;
      gbc_btnAddSong.gridy = 1;
      pnlMenu.add(btnAddSong, gbc_btnAddSong);
      // Label for Add Song
      lblAddSong = new JLabel("Add Song");
      lblAddSong.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblAddSong = new GridBagConstraints();
      gbc_lblAddSong.fill = GridBagConstraints.BOTH;
      gbc_lblAddSong.insets = new Insets(0, 0, 5, 5);
      gbc_lblAddSong.gridx = 2;
      gbc_lblAddSong.gridy = 1;
      pnlMenu.add(lblAddSong, gbc_lblAddSong);
      
      /* New Play List Button */
      btnNewPlaylist = new JButton(new ImageIcon("src/resources/NewPlayList.png"));
      btnNewPlaylist.addActionListener(this);
      // Layout for New Play List
      GridBagConstraints gbc_btnNewPlaylist = new GridBagConstraints();
      gbc_btnNewPlaylist.insets = new Insets(0, 0, 5, 5);
      gbc_btnNewPlaylist.gridx = 3;
      gbc_btnNewPlaylist.gridy = 1;
      pnlMenu.add(btnNewPlaylist, gbc_btnNewPlaylist);
      // Label for New Play List
      lblNewPlaylist = new JLabel("New Playlist");
      lblNewPlaylist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblNewPlaylist = new GridBagConstraints();
      gbc_lblNewPlaylist.fill = GridBagConstraints.BOTH;
      gbc_lblNewPlaylist.insets = new Insets(0, 0, 5, 0);
      gbc_lblNewPlaylist.gridx = 4;
      gbc_lblNewPlaylist.gridy = 1;
      pnlMenu.add(lblNewPlaylist, gbc_lblNewPlaylist);
      
      /* Remove Song Button */
      btnRemoveSong = new JButton(new ImageIcon("src/resources/RemoveSong.png"));
      btnRemoveSong.addActionListener(this);
      // Layout for Remove Song
      GridBagConstraints gbc_btnRemoveSong = new GridBagConstraints();
      gbc_btnRemoveSong.insets = new Insets(0, 0, 5, 5);
      gbc_btnRemoveSong.gridx = 1;
      gbc_btnRemoveSong.gridy = 2;
      pnlMenu.add(btnRemoveSong, gbc_btnRemoveSong);
      // Label for Remove Song
      lblRemoveSong = new JLabel("Remove Song");
      lblRemoveSong.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblRemoveSong = new GridBagConstraints();
      gbc_lblRemoveSong.fill = GridBagConstraints.BOTH;
      gbc_lblRemoveSong.insets = new Insets(0, 0, 5, 5);
      gbc_lblRemoveSong.gridx = 2;
      gbc_lblRemoveSong.gridy = 2;
      pnlMenu.add(lblRemoveSong, gbc_lblRemoveSong);
      
      /* Save Play List Button */
      btnSavePlaylist = new JButton(new ImageIcon("src/resources/SavePlayList.png"));
      btnSavePlaylist.addActionListener(this);
      // Layout for Save Play List
      GridBagConstraints gbc_btnSavePlaylist = new GridBagConstraints();
      gbc_btnSavePlaylist.insets = new Insets(0, 0, 5, 5);
      gbc_btnSavePlaylist.gridx = 3;
      gbc_btnSavePlaylist.gridy = 2;
      pnlMenu.add(btnSavePlaylist, gbc_btnSavePlaylist);
      // Label for Save Play List
      lblSavePlaylist = new JLabel("Save Playlist");
      lblSavePlaylist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblSavePlaylist = new GridBagConstraints();
      gbc_lblSavePlaylist.fill = GridBagConstraints.BOTH;
      gbc_lblSavePlaylist.insets = new Insets(0, 0, 5, 0);
      gbc_lblSavePlaylist.gridx = 4;
      gbc_lblSavePlaylist.gridy = 2;
      pnlMenu.add(lblSavePlaylist, gbc_lblSavePlaylist);
      
      /* Remove Play List Button */
      btnRemovePlaylist = new JButton(new ImageIcon("src/resources/RemovePlayList.png"));
      btnRemovePlaylist.addActionListener(this);
      // Layout for Remove Play List
      GridBagConstraints gbc_btnRemovePlaylist = new GridBagConstraints();
      gbc_btnRemovePlaylist.insets = new Insets(0, 0, 0, 5);
      gbc_btnRemovePlaylist.gridx = 3;
      gbc_btnRemovePlaylist.gridy = 3;
      pnlMenu.add(btnRemovePlaylist, gbc_btnRemovePlaylist);
      // Label for Remove Play List
      lblRemovePlaylist = new JLabel("Remove Playlist");
      lblRemovePlaylist.setFont(new Font("Nanum Gothic", Font.BOLD, 15));
      GridBagConstraints gbc_lblRemovePlaylist = new GridBagConstraints();
      gbc_lblRemovePlaylist.fill = GridBagConstraints.BOTH;
      gbc_lblRemovePlaylist.gridx = 4;
      gbc_lblRemovePlaylist.gridy = 3;
      pnlMenu.add(lblRemovePlaylist, gbc_lblRemovePlaylist);   
      /* ======================== Control Button List (End) ======================== */
      /** ====================== Body of Music Player (End) ============================= */
   }
   
   public void updateTitleLabel() // update title label according to which music is on play
   {
      lblSongTitle.setText(music.get(list.getSelectedIndex()).getMusicName());
      lblArtistTitle.setText(music.get(list.getSelectedIndex()).getMusicArtist());
   }
   
   public static void volumeControl(float volume)
   {
      Mixer.Info[] mixers = AudioSystem.getMixerInfo();
//      System.out.println("There are " + mixers.length + " mixer info objects");
      
        for (Mixer.Info mixerInfo : mixers) 
        {
//            System.out.println("mixer name: " + mixerInfo.getName());
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
//            System.out.println(mixer);
            Line.Info[] lineInfos = mixer.getTargetLineInfo(); // target, not source changes all the volumes
 
            for (Line.Info lineInfo : lineInfos) 
            {
                //System.out.println("  Line.Info: " + lineInfo);
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }
                    FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
//                    System.out.println(volCtrl.getMinimum() * 100);
//                    System.out.println(volCtrl.getMaximum() * 100);
                    volCtrl.setValue(volume);
//                    System.out.println("Set volume at " + volume);
//                    volCtrl.setValue((float) volume);
                    //System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaEx) {
                    //System.out.println("  -!-  " + iaEx);
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
   }
   
   CustomPlayer player = new CustomPlayer();
   boolean pause_flag = false;
   private JLabel lblVolSize;
   
   @Override
   public void actionPerformed(ActionEvent e) 
   {
      /* ================================ Music Player Manipulation (Start) ===================================== */
      /** Click Back Button */
      if(e.getSource() == btnBack)
      {
         try
         {
            try
            {
               System.out.println("Back"); // for test only
               player.player.close();
            }
            catch(Exception ex)
            {
               System.err.println(ex + ": There is no MP3 playing thread");
            }
            finally
            {
               if(list.getSelectedIndex() > 0) // if it is not the first song
               {
                  list.setSelectedIndex(list.getSelectedIndex() - 1); // go previous
                  updateTitleLabel(); // and update label
               }
               else // print error message on console
                  throw new Exception("There is no previous song!");
            }
         }
         catch (Exception e1) { System.err.println(e1); }
         finally
         {
            btnPlay.setEnabled(false); // if user press play button, deactivate play button
            btnStop.setEnabled(true);
            btnPause.setEnabled(true);
            
            String path = music.get(list.getSelectedIndex()).getMusicPath(); // get path from selected list item
            
            player.setPath(path);
            player.play(-1);
         }
      }
      
      /** Click Play Button */
      if(e.getSource() == btnPlay)
      {
         if(pause_flag == false)
         {
            System.out.println("Play"); // for test only
            
            btnPlay.setEnabled(false); // if user press play button, deactivate play button
            btnStop.setEnabled(true);
            btnPause.setEnabled(true);
            
            String path = music.get(list.getSelectedIndex()).getMusicPath(); // get path from selected list item
            updateTitleLabel();
            
            player.setPath(path);
            player.play(-1);
         }
         else
         {
            System.out.println("Resume"); // for test only
            player.resume();
         }
      }
      
      /** Click Pause Button */
      if(e.getSource() == btnPause)
      {
         System.out.println("Pause"); // for test only
         
         pause_flag = true;
         btnPlay.setEnabled(true);
         
         player.pause();
      }
      
      /** Click Stop Button */
      if(e.getSource() == btnStop)
      {
         try 
         {
            System.out.println("Stop"); // for test only
            
            player.player.close();
            btnPlay.setEnabled(true); // activate play button
            
            pause_flag = false;
         }
         catch(Exception ex)
         {
            System.err.println(ex + ": MP3 Playing thread is terminated");
         }
      }
      
      /** Click Next Button */
      if(e.getSource() == btnNext)
      {
         try
         {
            try 
            {
               System.out.println("Next"); // for test only
               player.player.close();
            }
            catch(Exception ex1)
            {
               System.err.println(ex1 + ": There is no MP3 playing thread");
            }
            finally
            {
               if(list.getSelectedIndex() < listModel.getSize() - 1) // if it reaches the end of the list
               {
                  list.setSelectedIndex(list.getSelectedIndex() + 1);
                  updateTitleLabel();
               }
               else
                  throw new Exception("There is no next song!");
            }
         }
         catch(Exception ex2)
         {
            System.err.println(ex2);
         }
         finally
         {
            String path = music.get(list.getSelectedIndex()).getMusicPath(); // get path from selected list item
            
            player.setPath(path);
            player.play(-1);
            
            btnPlay.setEnabled(false); // if user press play button, deactivate play button
         }
      }
      /* ================================ Music Player Manipulation (End) ===================================== */
      
      
      /* ================================ File Manipulation (Start) =========================================== */
      /** Click Open File Button */
      if(e.getSource() == btnOpenFile) 
      {
         JFileChooser fc = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3","mp3");
         fc.setFileFilter(filter);
         int returnVal = fc.showOpenDialog(MusicPlayer.this);
         
         if(returnVal==JFileChooser.APPROVE_OPTION) 
         {
            File file = fc.getSelectedFile();
            fileName=null;
            filePath=null;
            
            try 
            {
               FileReader fis = new FileReader(file);
               BufferedReader reader = new BufferedReader(fis);
               MusicTimer t = new MusicTimer(file);
               t.calTime();
               String fileName = file.getName(); // get file name
               String[] split = fileName.split(";"); // use semicolon as denominator
               
               musicName = split[0]; // set name
               String temp = split[1]; 
               String[] tempArr = temp.split(".mp3");
               musicArtist = tempArr[0]; // set artist
               /** Need to be handled */
               musicTime = t.getTime(); // set playing time
               musicPath = file.getPath(); // set path
               
               lblSongTitle.setText(musicName); // set song title label
               lblArtistTitle.setText(musicArtist); // set 
               
               musicNum = 0;
               
               listModel.clear();
               musicList.clear();
               music.clear();
               
               music.add(new Music(musicNum+1,musicName,musicArtist,musicTime,musicPath)); // add to music
               musicList.add(music.get(musicNum).getMusicNum() + "\t\t\t\t\t" 
                        + music.get(musicNum).getMusicName() + "\t\t\t\t\t"
                        + music.get(musicNum).getMusicArtist() + "\t\t\t\t\t"
                        + music.get(musicNum).getMusicTime()); // add to music list
               
               for(String song : musicList) 
                  listModel.addElement(song);
            } 
            catch (FileNotFoundException e1) { e1.printStackTrace(); }
         }
         list.setSelectedIndex(0); // put focus on first element of list
      }
      
      /** Click Add Song Button */
      if(e.getSource() == btnAddSong) 
      {
         JFileChooser fc = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3","mp3");
         fc.setFileFilter(filter);
         int returnVal = fc.showOpenDialog(MusicPlayer.this);
         
         if(returnVal==JFileChooser.APPROVE_OPTION) 
         {
            File file = fc.getSelectedFile();
            
            try 
            {
               FileReader fis = new FileReader(file);
               String fileName = file.getName();
               String[] split = fileName.split("; ");
               MusicTimer t = new MusicTimer(file);
               t.calTime();
               
               musicName = split[0]; // set name
               String temp = split[1]; 
               String[] tempArr = temp.split(".mp3");
               musicArtist = tempArr[0]; // set artist
               /** Need to be handled */
               musicTime = t.getTime();
               musicPath = file.getPath();
               listModel.clear();
               
               musicNum +=1; // increase number
               music.add(new Music(musicNum+1,musicName,musicArtist,musicTime,musicPath)); // add to music
               musicList.add(music.get(musicNum).getMusicNum() + "\t\t\t\t\t"
                        + music.get(musicNum).getMusicName() + "\t\t\t\t\t"
                        + music.get(musicNum).getMusicArtist() + "\t\t\t\t\t"
                        + music.get(musicNum).getMusicTime()); // add to music list
               
               for(String song : musicList) // add to DefaultListModel to show on GUI
                  listModel.addElement(song);
            } 
            catch (FileNotFoundException e1) { e1.printStackTrace(); }
         }
         list.setSelectedIndex(0); // put focus on first element of list
      }
      
      /** Click Remove Song Button */
      if(e.getSource() == btnRemoveSong) 
      {
          int delCount = 0;
           for (int pos : list.getSelectedIndices()) 
           {
              listModel.remove(pos - delCount);
               delCount++;
           }
      }
      
      /** Click Open Play List Button */
      if(e.getSource() == btnOpenPlaylist) 
      {
         JFileChooser fc = new JFileChooser();
         FileNameExtensionFilter fnef1 = new FileNameExtensionFilter("txt","txt");
         fc.setFileFilter(fnef1);
         int returnVal = fc.showOpenDialog(MusicPlayer.this);
         
         if(returnVal==JFileChooser.APPROVE_OPTION) 
         {
            File file = fc.getSelectedFile();
            
            try 
            {
               // initialize 
               music.clear();
               musicList.clear();
               listModel.clear();
               
               fileName = file.getName(); // get file name 
               filePath = file.getPath(); // get file path
               
               FileReader fr = new FileReader(file);
               BufferedReader br = new BufferedReader(fr);
            
               String sCurrentLine;
               while((sCurrentLine = br.readLine()) != null) 
               {
                  String[] split = sCurrentLine.split("\t"); // use tab as denominator
                  
                  musicNum = Integer.parseInt(split[0]) - 1; // set number
                  musicName = split[1]; // set name
                  musicArtist = split[2]; // set artist
                  musicTime = split[3]; // set playing time
                  musicPath = split[4]; // set path
                  
                  music.add(new Music(musicNum+1,musicName,musicArtist,musicTime,musicPath)); // add to music
                  musicList.add(music.get(musicNum).getMusicNum()+"\t\t\t\t\t" 
                           + music.get(musicNum).getMusicName()+"\t\t\t\t\t"
                           + music.get(musicNum).getMusicArtist()+"\t\t\t\t\t"
                           + music.get(musicNum).getMusicTime()); // add to music list
               }
               br.close();
               
               for(String song : musicList) // add to DefaultListModel to show on GUI
                  listModel.addElement(song);
            } 
            catch (IOException e1) { e1.printStackTrace(); } 
         }
         list.setSelectedIndex(0); // focus on first element of list
      }
      
      /** Click New Play List Button */
      if(e.getSource() == btnNewPlaylist) 
      {
         music.clear();
         musicList.clear();
         listModel.clear();
      }
      
      /** Click Save Play List Button */
      if(e.getSource() == btnSavePlaylist) 
      {
         if(fileName == null) // if play list does not exist, create new one 
         {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter fnef1 = new FileNameExtensionFilter("txt","txt");
            fc.setFileFilter(fnef1);
            int returnVal = fc.showSaveDialog(MusicPlayer.this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
               File file = new File(fc.getSelectedFile()+".txt"); // save file in text format
               
               try 
               {
                  fileName = file.getName(); // set file name
                  filePath = file.getPath(); // set file path
                  
                  FileWriter fw = new FileWriter(file);
                  BufferedWriter bw = new BufferedWriter(fw);
                  
                  for(int i = 0; i <= musicNum; i++) // update music list 
                  {
                     bw.write(musicList.get(i)+"\t"+music.get(i).getMusicPath());
                     bw.newLine();
                  }
                  
                  bw.flush();
                  bw.close();
                  System.out.println("Save success");
               } 
               catch (IOException e1) { e1.printStackTrace(); }
            }
         }
         else // if play list already exists, just update it
         {
            try 
            {
               File file = new File(filePath);
               BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            
               for(int i=0; i <= musicNum; i++) 
               {
                  bw.write(musicList.get(i));
                  bw.newLine();
               }
               bw.flush();
               bw.close();
            } 
            catch (IOException e1) { e1.printStackTrace(); }
         }
      }

      /** Click Remove Play List Button */
      if(e.getSource() == btnRemovePlaylist) 
      {
         try
         {
            File file = new File(filePath);
             if(fileName != null) // In case, play list exists
             {
                file.delete();
                System.out.println(fileName + " is deleted!");
                fileName = null;
                filePath = null;
             }
             else // In case, play list does not exist
                throw new Exception("Delete operation is failed!");
          }
         catch(Exception e1) { System.err.println(e1); }
         
         music.clear();
         musicList.clear();
          listModel.clear();
      }
   }
   /* =================================== File Manipulation (End) ============================================== */
}