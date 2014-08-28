// --------------------------------------------------------------------------------
// Name: FCreateWPADictionary
// Abstract: Shows the options of what you can do with a WPA dictionary
// --------------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FCreateWPADictionary extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtDictionaryLocation = null;
	private JButton m_btnDictionaryLocation = null;
	
	private JButton m_btnShowDatabaseStats = null;
	private JButton m_btnExecuteSQL = null;
	private JButton m_btnCleanDatabase = null;
	private JButton m_btnStartBatchProcessing = null;
	private JButton m_btnVerifyDatabase = null;
	private JButton m_btnImportData = null;
	private JButton m_btnExportData = null;

	// --------------------------------------------------------------------------------
	// Name: FCreateWPADictionary
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FCreateWPADictionary( )
	{
		super("WPA Dictionary", 420, 195, false, false, "");
		try
        {
            AddControls();
        }
        catch (Exception excError)
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Shows the options of what you can do with a WPA dictionary
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
        {
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            CUtilities.AddLabel(conControlArea, "Database Name or Location:", 12, 5);
            m_txtDictionaryLocation = CUtilities.AddTextBox(conControlArea, null, "", 11, 100, 10, 210);
            m_btnDictionaryLocation = CUtilities.AddButton(conControlArea, this, "...", 10, 340, 18, 50);
            
            m_btnShowDatabaseStats = CUtilities.AddButton(conControlArea, this, "Show Database Stats", 40, 5, 25, 200);
        	m_btnExecuteSQL = CUtilities.AddButton(conControlArea, this, "Execute SQL", 40, 210, 25, 200);
        	m_btnCleanDatabase = CUtilities.AddButton(conControlArea, this, "Clean Database", 70, 5, 25, 200);
        	m_btnStartBatchProcessing = CUtilities.AddButton(conControlArea, this, "Start Batch Processing", 70, 210, 25, 200);
        	m_btnVerifyDatabase = CUtilities.AddButton(conControlArea, this, "Verify Database", 100, 5, 25, 200);
        	m_btnImportData = CUtilities.AddButton(conControlArea, this, "Import Data", 100, 210, 25, 200);
        	m_btnExportData = CUtilities.AddButton(conControlArea, this, "Export Data", 130, 5, 25, 200);
            
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
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
			if ( aeSource.getSource( ) == m_btnDictionaryLocation ) 		btnDictionaryLocation_Click( );
			else if ( aeSource.getSource( ) == m_btnShowDatabaseStats )		btnShowDatabaseStats_Click( );
			else if ( aeSource.getSource( ) == m_btnExecuteSQL )			btnExecuteSQL_Click( );
			else if ( aeSource.getSource( ) == m_btnCleanDatabase )			btnCleanDatabase_Click( );
			else if ( aeSource.getSource( ) == m_btnStartBatchProcessing )	btnStartBatchProcessing_Click( );
			else if ( aeSource.getSource( ) == m_btnVerifyDatabase )		btnVerifyDatabase_Click( );
			else if ( aeSource.getSource( ) == m_btnImportData )			btnImportData_Click( );
			else if ( aeSource.getSource( ) == m_btnExportData )			btnExportData_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnDictionaryLocation_Click
	// Abstract: Displays a file chooser dialog to select the dictionary file
	// --------------------------------------------------------------------------------
	private void btnDictionaryLocation_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtDictionaryLocation, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnShowDatabaseStats_Click
	// Abstract: Displays a database stats window
	// --------------------------------------------------------------------------------
	private void btnShowDatabaseStats_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DShowDictionaryStatus dlgShowDictionaryStatus = new DShowDictionaryStatus( );
				dlgShowDictionaryStatus.SetDictionaryLocation( m_txtDictionaryLocation.getText( ) );
				dlgShowDictionaryStatus.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnExecuteSQL_Click
	// Abstract: Displays the execute SQL window
	// --------------------------------------------------------------------------------
	private void btnExecuteSQL_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DExecuteSQL dlgExecuteSQL = new DExecuteSQL( );
				dlgExecuteSQL.SetDictionaryLocation( m_txtDictionaryLocation.getText( ) );
				dlgExecuteSQL.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnCleanDatabase_Click
	// Abstract: Creates a clean database window
	// --------------------------------------------------------------------------------
	private void btnCleanDatabase_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DCleanDatabase dlgCleanDatabase = new DCleanDatabase( );
				dlgCleanDatabase.SetDictionaryLocation( m_txtDictionaryLocation.getText( ) );
				dlgCleanDatabase.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnVerifyDatabase_Click
	// Abstract: Creates a verify database window
	// --------------------------------------------------------------------------------
	private void btnVerifyDatabase_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DVerifyDatabase dlgVerifyDatabase = new DVerifyDatabase( );
				dlgVerifyDatabase.SetDictionaryLocation( m_txtDictionaryLocation.getText( ) );
				dlgVerifyDatabase.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnStartBatchProcessing_Click
	// Abstract: Starts batch processing to create encrypted keys 
	// --------------------------------------------------------------------------------	
	private void btnStartBatchProcessing_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to start batch processing on the database? This action cannot be cancelled.", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION); 
				if ( intResult == 0 ) // Yes
				{
					String astrCommand[] = new String[] {"airolib-ng", m_txtDictionaryLocation.getText(), "--batch"};
					CProcess clsBatchProcessDatabase = new CProcess(astrCommand, true, true, true);
					BufferedReader brOutput = new BufferedReader( clsBatchProcessDatabase.GetOutput( ) );
					String strOutput = brOutput.readLine( );
					while ( strOutput != null )
					{
						if ( strOutput.indexOf("All ESSID processed.") >= 0 )
						{
							JOptionPane.showMessageDialog(null, strOutput);
							break;
						}
							
						strOutput = brOutput.readLine( );
					}
					clsBatchProcessDatabase.CloseProcess();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnImportData_Click
	// Abstract: Creates an import data window 
	// --------------------------------------------------------------------------------
	private void btnImportData_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DImportData dlgImportData = new DImportData( );
				dlgImportData.SetDictionaryLocation(m_txtDictionaryLocation.getText().trim());
				dlgImportData.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnExportData_Click
	// Abstract: Creates an export data window 
	// --------------------------------------------------------------------------------
	private void btnExportData_Click( )
	{
		try
		{
			if ( m_txtDictionaryLocation.getText().trim().equals("") == false )
			{
				DExportData dlgExportData = new DExportData( );
				dlgExportData.SetDictionaryLocation(m_txtDictionaryLocation.getText().trim());
				dlgExportData.setVisible( true );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No database selected. Please specify a database to use.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
