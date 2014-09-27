// Imports
import javax.swing.*;

import java.awt.*;
import java.util.Calendar;

/**
 * Form used as a splash screen to display while the form is loading
 * @author Paul Bromwell Jr
 * @since Jul 5, 2014
 * @version 1.0
 */
public class FSplashScreen extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private JLabel m_lblHeadingLabel = null;
	private JLabel m_lblSubHeadingLabel = null;
	
	/**
	 * Class constructor
	 * @author Paul Bromwell Jr
	 * @since Jul 5, 2014
	 * @version 1.0
	 */
	public FSplashScreen()
	{
		super("", 400, 300, false, false, "");
		try
		{
			setUndecorated(true);
			
			AddControls();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Adds the controls to the form 
	 * @author Paul Bromwell Jr
	 * @since Jul 5, 2014
	 * @version 1.0 
	 */
	private void AddControls()
	{
		try
		{
			// Heading label
			m_lblHeadingLabel = CUtilities.AddLabel(m_cntControlContainer, "Aircrack-NGUI", 25, 25);
			Font fntDefaultFont = m_lblHeadingLabel.getFont();
			Font fntHeaderFont = new Font(fntDefaultFont.getFamily(), fntDefaultFont.getStyle(), 24);
			m_lblHeadingLabel.setFont(fntHeaderFont);
			
			// Sub-heading label
			m_lblSubHeadingLabel  = CUtilities.AddLabel(m_cntControlContainer, "Computer penetration testing for the Average Joe...", 65, 25);
			Font fntSubHeaderFont = new Font(fntDefaultFont.getFamily(), Font.ITALIC, fntDefaultFont.getSize());
			m_lblSubHeadingLabel.setFont(fntSubHeaderFont);
			
			CUtilities.AddLabel(m_cntControlContainer, "Copyright \u00a9 " + Calendar.getInstance().get(Calendar.YEAR) + " by Paul Bromwell Jr", 275, 25);
			CUtilities.AddLabel(m_cntControlContainer, "Loading, please wait...", 175, 125);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

}
