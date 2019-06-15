/** 
 * 12131819 YOOK DONGHYUN, 12345678 LEE JINHO
 * Java Application Programming-002 (Prof. Tamer) // Final Project
 * ============================================================================
 * update log
 * -----------------------------------------------------------------------------
 * - 2019.06.09 : code-refactoring and put some comments on each code (by YOOK)
 */
package music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class ZoomPlayer implements Runnable // Thread-based
{	
	private String path;
	
	public ZoomPlayer(String path) { this.path = path; }

	@Override
	public void run() 
	{
		try 
		{
			AdvancedPlayer p = new AdvancedPlayer(new FileInputStream(this.path));
			p.play();
		} 
		catch (FileNotFoundException | JavaLayerException e) { e.printStackTrace(); } 
	}
}