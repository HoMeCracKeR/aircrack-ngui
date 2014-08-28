// --------------------------------------------------------------------------------
// Name: DCleanDatabase
// Abstract: Cleans the SQLite database
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DCleanDatabase extends CAircrackDialog implements ActionListener
{

	private String m_strDictionaryLocation = "";
	private JCheckBox m_chkReduceFilesize = null;
	private JButton m_btnCleanDatabase = null;
	protected final static long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------
	// Name: DCleanDatabase
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DCleanDatabase( )
	{
		super("Clean Database", 420, 175, false, false, "");
		try
		{
			AddControls( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the window
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
        {
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            CUtilities.AddLabel(conControlArea,  "<html>" + 
													"You have chosen to clean the database. This will remove<br />" + 
													"old PMKs (Pairwise Master Key) from the database.<br />" + 
													"Please check the option below if you want to attempt to<br />" + 
													"reduce the database filesize" +
													"</html>", 5, 5);
        	m_chkReduceFilesize = CUtilities.AddCheckBox(conControlArea, this, "Attempt to reduce filesize", 85, 5);
        	m_btnCleanDatabase = CUtilities.AddButton(conControlArea, this, "Clean", 115, 160, 18, 100);
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}

	// --------------------------------------------------------------------------------
	// Name: SetDictionaryLocation
	// Abstract: Sets the location of the dictionary (used by the main form)
	// --------------------------------------------------------------------------------
	public void SetDictionaryLocation(String strNewDictionaryLocation)
	{
		try
		{
			m_strDictionaryLocation = strNewDictionaryLocation;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_btnCleanDatabase )		btnCleanDatabase_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnCleanDatabase_Click
	// Abstract: Clean database button click event. Cleans the SQLite database of old
	//			 data. Also attempts to reduce filesize if selected.
	// --------------------------------------------------------------------------------
	private void btnCleanDatabase_Click( )
	{
		try
		{
			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--clean"};
			if ( m_chkReduceFilesize.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToArray("all", astrCommand);
			
			CProcess clsCleanDatabase = new CProcess(astrCommand, true, true, true);
			clsCleanDatabase.CloseProcess();
			JOptionPane.showMessageDialog(null, "Cleaning complete");
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
