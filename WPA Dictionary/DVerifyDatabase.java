// --------------------------------------------------------------------------------
// Name: DVerifyDatabase
// Abstract: Verifies the SQLite database
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DVerifyDatabase extends CAircrackDialog implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	private String m_strDictionaryLocation = "";
	private JLabel m_lblExplanation = null;
	private JCheckBox m_chkVerifyAllPMKs = null;
	private JButton m_btnVerifyDatabase = null;
	private CTable m_tblVerifyResults = null;
	private final String[] m_astrVERIFY_RESULTS_COLUMN_HEADERS = new String[]{"ESSID", "CHECKED", "STATUS"};

	// --------------------------------------------------------------------------------
	// Name: DVerifyDatabase
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DVerifyDatabase( )
	{
		super("Verify Dictionary", 420, 175, false, false, "");
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
            
            m_lblExplanation = CUtilities.AddLabel(conControlArea,  "<html>" + 
            														"You have chosen to verify the database. This will remove<br />" + 
            														"a random number of invalid PMKs (Pairwise Master Keys)<br />" +
            														"in the database. Please check the option below if you<br />" +
            														"want to check all PMKs and remove all invalid ones from<br />" +
            														"the database." + 
            														"</html>", 5, 5);
        	m_chkVerifyAllPMKs = CUtilities.AddCheckBox(conControlArea, this, "Verify all PMKs in database", 85, 5);
        	m_btnVerifyDatabase = CUtilities.AddButton(conControlArea, this, "Verify", 115, 160, 18, 100);
        	m_tblVerifyResults = CUtilities.AddTable(conControlArea, m_astrVERIFY_RESULTS_COLUMN_HEADERS, null, 0, 0, 175, 420);
        	m_tblVerifyResults.SetVisible( false );
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetDictionaryLocation
	// Abstract: Sets the dictionary location. This is done by FCreateWPADictionary.
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
	// Abstract: Button clicks
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_btnVerifyDatabase )		btnVerifyDatabase_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnVerifyDatabase_Click
	// Abstract: Verifies the database and if applicable, displays the verification
	//			 results.
	// --------------------------------------------------------------------------------
	private void btnVerifyDatabase_Click( )
	{
		try
		{
			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--verify"};
			if ( m_chkVerifyAllPMKs.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToArray("all", astrCommand);
			CProcess clsVerifyDatabase = new CProcess(astrCommand, true, true, true);

			if ( m_chkVerifyAllPMKs.isSelected( ) == false )
			{
				
				m_tblVerifyResults.SetVisible( true );
				m_lblExplanation.setVisible( false );
				m_chkVerifyAllPMKs.setVisible( false );
	            m_btnVerifyDatabase.setVisible( false );
				BufferedReader brOutput = new BufferedReader( clsVerifyDatabase.GetOutput( ) );
				brOutput.readLine( ); // Checking all PMKs. This could take a while...
				brOutput.readLine( ); // Printing column headers
				String strBuffer = brOutput.readLine( );
				while ( strBuffer != null )
				{
					if ( strBuffer.equals("") == false )
					{
						String astrColumnValues[] = strBuffer.split("\t");
						m_tblVerifyResults.AddRow(astrColumnValues);
					}
					strBuffer = brOutput.readLine( );
				}
				JOptionPane.showMessageDialog(null, "The database has been verified.");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The database has been verified.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
