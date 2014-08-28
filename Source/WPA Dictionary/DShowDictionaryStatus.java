// --------------------------------------------------------------------------------
// Name: DShowDictionaryStatus
// Abstract: Shows the status of the dictionary file
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DShowDictionaryStatus extends CAircrackDialog implements ComponentListener
{
	protected final static long serialVersionUID = 1L;
	private String m_strDictionaryLocation = "";
	private CTable m_tblDatabaseContents = null;
	private final String m_astrDATABASE_CONTENTS_HEADERS[] = new String[] {"ESSID", "Priority", "Done"};
	private JLabel m_lblDictionaryStatus = null;

	// --------------------------------------------------------------------------------
	// Name: DShowDictionaryStatus
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DShowDictionaryStatus( )
	{
		super("Dictionary Status", 420, 350, false, false, "");
		try
		{
			Initialize( );
			AddControls( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Initializes the window
	// --------------------------------------------------------------------------------
	private void Initialize( )
	{
		try
        {
            addComponentListener( this );
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
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
            
            m_tblDatabaseContents = CUtilities.AddTable(conControlArea, m_astrDATABASE_CONTENTS_HEADERS, null, 5, 5, 280, 405);
            m_lblDictionaryStatus = CUtilities.AddLabel(conControlArea, "", 285, 5);
            
            // Change the label font size
            Font fntDictionaryStatus = m_lblDictionaryStatus.getFont();
            m_lblDictionaryStatus.setFont(new Font(fntDictionaryStatus.getFontName(), fntDictionaryStatus.getStyle(), 11));
            
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetDictionaryLocation
	// Abstract: Sets the dictionary location
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
	// Name: componentShown
	// Abstract: Shows the contents of the database in a table when the form loads
	// --------------------------------------------------------------------------------
	@Override
	public void componentShown(ComponentEvent ceSource)
	{
		try
		{
			PopulateDatabaseContents( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateDatabaseContents
	// Abstract: Shows the contents of the database in a table
	// --------------------------------------------------------------------------------
	private void PopulateDatabaseContents( )
	{
		try
		{
			String astrCommands[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--stats"}; 
			CProcess clsGetDatabaseContents = new CProcess(astrCommands, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsGetDatabaseContents.GetOutput( ) );
			String strOutput = brOutput.readLine( );
			if ( strOutput != null )
			{
				if ( strOutput.equals("Database <" + m_strDictionaryLocation + "> does not already exist, exiting ...") )
				{
					JOptionPane.showMessageDialog(null, "The database isn't created yet. Please use the import feature to create the database.");
				}
				else if ( strOutput.indexOf("possible combinations have been computed") >= 0 )
				{
					String astrIndividualSentences[] = strOutput.split("database. ");
					m_lblDictionaryStatus.setText("<html>" + astrIndividualSentences[0] + "database.<br />" + astrIndividualSentences[1] + "</html>");
					brOutput.readLine( ); // white space
					brOutput.readLine( ); // column headers
					strOutput = brOutput.readLine( );
					while ( strOutput != null )
					{
						String astrColumns[] = strOutput.split("\t");
						if ( astrColumns[0].equals("") == false )
							m_tblDatabaseContents.AddRow(astrColumns);
						strOutput = brOutput.readLine( );
					}
				}
			}
			clsGetDatabaseContents.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// Not used
	@Override
	public void componentHidden(ComponentEvent arg0) {}
	@Override
	public void componentMoved(ComponentEvent arg0) {}
	@Override
	public void componentResized(ComponentEvent arg0) {}
	
}
