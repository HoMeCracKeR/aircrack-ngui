// --------------------------------------------------------------------------------
// Name: DExportData
// Abstract: Exports the data in the database to various formats
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DExportData extends CAircrackDialog implements ActionListener
{
	
	private String m_strDictionaryLocation = "";
	private CComboBox m_cboExportType = null;
	private CTextBox m_txtExportLocation = null;
	private JButton m_btnExportLocation = null;
	private JLabel m_lblExportCowPattyESSID = null;
	private CComboBox m_cboExportCowPattyESSID = null;
	private JLabel m_lblExportTables = null;
	private CListBox m_lstExportTables = null;
	private JButton m_btnExport = null;
	protected final static long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------
	// Name: DExportData
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DExportData( )
	{
		super("Export Data", 340, 215, false, false, "");
		try
		{
			AddControls( );
			PopulateExportTypes( );
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
            
            CUtilities.AddLabel(conControlArea, "Export Type:", 6, 5);
        	m_cboExportType = CUtilities.AddComboBox(conControlArea, null, 5, 100, 18, 125);
        	CUtilities.AddLabel(conControlArea, "Location:", 31, 5);
        	m_txtExportLocation = CUtilities.AddTextBox(conControlArea, null, "", 15, 100, 30, 100);
        	m_btnExportLocation = CUtilities.AddButton(conControlArea, this, "...", 30, 275, 18, 50);
        	m_lblExportCowPattyESSID = CUtilities.AddLabel(conControlArea, "ESSID:", 55, 5);
        	m_cboExportCowPattyESSID = CUtilities.AddComboBox(conControlArea, null, 55, 100, 18, 200);
        	m_lblExportTables = CUtilities.AddLabel(conControlArea, "Tables:", 55, 5);
        	m_lstExportTables = CUtilities.AddListBox(conControlArea, null, 55, 100, 100, 200);
        	m_btnExport = CUtilities.AddButton(conControlArea, this, "Export", 160, 120, 18, 100);
        	
        	m_cboExportType.SetActionListener( this );
        	m_lblExportTables.setVisible( false );
        	m_lstExportTables.setVisible( false );
        	m_lstExportTables.SetSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetDictionaryLocation
	// Abstract: Sets the location of the dictionary
	// --------------------------------------------------------------------------------
	public void SetDictionaryLocation(String strNewDictionaryLocation)
	{
		try
		{
			m_strDictionaryLocation = strNewDictionaryLocation;
			PopulateDictionaryTables( );
			PopulateDictionaryESSIDs( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateExportTypes
	// Abstract: Populates the type of exports you can do
	// --------------------------------------------------------------------------------
	private void PopulateExportTypes( )
	{
		try
		{
			m_cboExportType.AddItemToList("coWPAtty File", 0);
			m_cboExportType.AddItemToList("CSV File", 0);
			m_cboExportType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateDictionaryTables
	// Abstract: Populates the tables of the dictionary that you can export
	// --------------------------------------------------------------------------------
	private void PopulateDictionaryTables( )
	{
		try
		{

			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--sql", "SELECT name FROM sqlite_master"};
			CProcess clsGetTables = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput2 = new BufferedReader( clsGetTables.GetOutput( ) );
			brOutput2.readLine( ); // Header
			String strTableName = brOutput2.readLine( );
			
			while ( strTableName != null )
			{
				m_lstExportTables.AddItemToList(strTableName, 0);
				strTableName = brOutput2.readLine( );
			}
			
			clsGetTables.CloseProcess();
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateDictionaryESSIDs
	// Abstract: Populates the different ESSIDs from the dictionary
	// --------------------------------------------------------------------------------
	private void PopulateDictionaryESSIDs( )
	{
		try
		{
			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--sql", "SELECT essid FROM essid"};
			CProcess clsGetESSIDs = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput2 = new BufferedReader( clsGetESSIDs.GetOutput( ) );
			brOutput2.readLine( ); // Header
			String strESSIDName = brOutput2.readLine( );
			
			while ( strESSIDName != null )
			{
				m_cboExportCowPattyESSID.AddItemToList(strESSIDName, 0);
				strESSIDName = brOutput2.readLine( );
			}
			
			clsGetESSIDs.CloseProcess();
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
			if ( aeSource.getSource( ) == m_btnExportLocation )							btnExportLocation_Click( );
			else if ( aeSource.getSource( ) == m_cboExportType.GetJavaComboBox( ) )		cboExportType_Change( );
			else if ( aeSource.getSource( ) == m_btnExport )							btnExport_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnExportLocation_Click
	// Abstract: Export button click event. Displays a file chooser to specify the location
	//			 of where the exported data is supposed to go.
	// --------------------------------------------------------------------------------
	private void btnExportLocation_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtExportLocation, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboExportType_Change
	// Abstract: Hide fields depending on the type of export
	// --------------------------------------------------------------------------------
	private void cboExportType_Change( )
	{
		try
		{
			if ( m_cboExportType.GetSelectedItemName( ).equals("CSV File") )
			{
				m_lblExportTables.setVisible( true );
	        	m_lstExportTables.setVisible( true );
	        	m_lblExportCowPattyESSID.setVisible( false );
	        	m_cboExportCowPattyESSID.setVisible( false );
			}
			else
			{
				m_lblExportTables.setVisible( false );
	        	m_lstExportTables.setVisible( false );
	        	m_lblExportCowPattyESSID.setVisible( true );
	        	m_cboExportCowPattyESSID.setVisible( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnExport_Click
	// Abstract: Performs the actual export.
	// --------------------------------------------------------------------------------
	private void btnExport_Click( )
	{
		try
		{
			String strExportLocation = "";
			if ( m_txtExportLocation.getText( ).trim( ).equals("") == true )
			{
				JOptionPane.showMessageDialog(null, "No output location specified.");
				return;
			}
			else
			{
				strExportLocation = m_txtExportLocation.getText( ).trim( );
			}
			if ( m_cboExportType.GetSelectedItemName( ).equals("coWPAtty File") == true )
			{
				ExportToCowPattyFile( strExportLocation );
			}
			else if ( m_cboExportType.GetSelectedItemName( ).equals("CSV File") == true )
			{
				ExportToCommaSeparatedValuesFile( strExportLocation );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ExportToCowPattyFile
	// Abstract: Exports the table data to a coWPAtty file.
	// --------------------------------------------------------------------------------
	private void ExportToCowPattyFile( String strExportLocation )
	{
		try
		{
			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--export", "cowpatty", m_cboExportCowPattyESSID.GetSelectedItemName( ), strExportLocation };
			CProcess clsCowPattyTable = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsCowPattyTable.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				if ( strBuffer.equals("Done.") == true )
				{
					JOptionPane.showMessageDialog(null, "Export successful!");
				}
				else if (    strBuffer.equals("You must specify essid and output file.") == true
						  || strBuffer.equals("The file already exists and I won't overwrite it.") == true
						  || strBuffer.equals("There is no such ESSID in the database or there are no PMKs for it.") == true )
				{
					JOptionPane.showMessageDialog(null, strBuffer);
				}
				
				strBuffer = brOutput.readLine( );
			}
			
			clsCowPattyTable.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ExportToCommaSeparatedValuesFile
	// Abstract: Exports the table data to a coWPAtty file.
	// --------------------------------------------------------------------------------
	private void ExportToCommaSeparatedValuesFile( String strExportLocation )
	{
		try
		{
			int aintSelectedIndices[] = m_lstExportTables.GetSelectedIndices( );
			if ( aintSelectedIndices.length == 1 && aintSelectedIndices[0] == 0 )
			{
				JOptionPane.showMessageDialog(null, "No tables selected. Please select at least one table and click 'Export' again.");
				return;
			}
			
			FileWriter filFileStream = new FileWriter( strExportLocation );
			BufferedWriter bwOutput = new BufferedWriter( filFileStream );
			
			for ( int intIndex = 0; intIndex < aintSelectedIndices.length; intIndex += 1 )
			{
				if ( aintSelectedIndices[intIndex] == 0 )
					continue;
				String strTableName = m_lstExportTables.GetItem( aintSelectedIndices[intIndex] ).GetName( );
				String strSQLQuery = "SELECT * FROM " + strTableName;
				String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--sql", strSQLQuery};
				CProcess clsGetTableContents = new CProcess(astrCommand, true, true, true);
				BufferedReader brOutput = new BufferedReader( clsGetTableContents.GetOutput( ) );
				String strBuffer = brOutput.readLine( );
				
				while ( strBuffer != null )
				{
					String astrFields[] = strBuffer.split("\t");
					String strOutputLine = "";
					
					for ( int intIndex2 = 0; intIndex2 < astrFields.length; intIndex2 += 1 )
					{
						if ( strOutputLine.equals("") == false )
							strOutputLine += ",";
						strOutputLine += "\"" + astrFields[intIndex2] + "\"";
					}
					
					bwOutput.write(strOutputLine + "\n");
					strBuffer = brOutput.readLine( );
				}
				bwOutput.write("\n");
				
				clsGetTableContents.CloseProcess();
			}
			
			bwOutput.close( );
			
			DisplaySavedFile( strExportLocation );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplaySavedFile
	// Abstract: Displays the saved CSV file
	// --------------------------------------------------------------------------------
	private void DisplaySavedFile( String strExportLocation )
	{
		try
		{
			Desktop dtpDesktop = Desktop.getDesktop( );
			if ( dtpDesktop.isSupported( Desktop.Action.EDIT ) == true )
			{
				File filSavedFile = new File( strExportLocation );
				if ( filSavedFile.exists( ) == true && filSavedFile.length( ) > 0 )
				{
					dtpDesktop.edit( filSavedFile );	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Export failed. Please try again. If the problem persists, submit an issue.");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Desktop not supported. You will need to manually open the file.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
