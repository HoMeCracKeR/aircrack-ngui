// --------------------------------------------------------------------------------
// Name: DImportData
// Abstract: Imports the data into the database from various formats
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class DImportData extends CAircrackDialog implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	private String m_strDictionaryLocation = "";
	private CComboBox m_cboImportType = null;
	private final String[] m_astrIMPORT_TYPES_COMMAND_ARGUMENTS = new String[] {"essid", "passwd", "cowpatty"};

	private JRadioButton m_rdbImportFromFile = null;
	private JButton m_btnImport = null;
	
	// Import ESSID/Import Password Options
	private JRadioButton m_rdbImportFromTextArea = null;
	
	// Import ESSID Options
	private JRadioButton m_rdbImportSurroundingNetworkNames = null;
	
	private JLabel m_lblImportSettings = null;
	
	// From File
	private JLabel m_lblImportFromFile = null;
	private CTextBox m_txtImportFromFile = null;
	private JButton m_btnImportFromFile = null;
	
	// From Text Area
	private JTextArea m_txaImportFromTextArea = null;
	private JScrollPane m_scpImportFromTextArea = null;
	
	// From Surrounding Networks
	private CListBox m_lstAvailableNetworks = null;
	private JLabel m_lblSurroundingNetworksInterface = null;
	private CComboBox m_cboSurroundingNetworksInterface = null;
	private JLabel m_lblSurroundingNetworksScanMethod = null;
	private JRadioButton m_rdbScanMethodIWList = null;
	private JRadioButton m_rdbScanMethodAirodump = null;
	private JButton m_btnScanForSurroundingNetworks = null;
	
	// --------------------------------------------------------------------------------
	// Name: DImportData
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DImportData( )
	{
		super("Import Data", 420, 405, false, false, "");
		try
		{
			AddControls( );
			PopulateImportTypes( );
			PopulateNetworkInterfaces( );
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
            
            ButtonGroup bgpImportType = new ButtonGroup( );
            ButtonGroup bgpScanMethod = new ButtonGroup( );
            
            CUtilities.AddLabel(conControlArea, "Import Type:", 6, 5);
            m_cboImportType = CUtilities.AddComboBox(conControlArea, null, 5, 100, 18, 150);
            m_btnImport = CUtilities.AddButton(conControlArea, this, "Import", 350, 160, 18, 100);
            
            CUtilities.AddLabel(conControlArea, "Import Method:", 30, 5);
            m_rdbImportFromFile = CUtilities.AddRadioButton(conControlArea, this, bgpImportType, "From File", 45, 5);
            
            // Import ESSID/Password Options
        	m_rdbImportFromTextArea = CUtilities.AddRadioButton(conControlArea, this, bgpImportType, "From Textbox", 65, 5);
        	
        	// Import ESSID Options
        	m_rdbImportSurroundingNetworkNames = CUtilities.AddRadioButton(conControlArea, this, bgpImportType, "From Surrounding WPA Networks", 85, 5);
            
        	m_lblImportSettings = CUtilities.AddLabel(conControlArea, "", 115, 5);
        	
        	// Import From File
        	m_lblImportFromFile = CUtilities.AddLabel(conControlArea, "File:", 141, 5);
        	m_txtImportFromFile = CUtilities.AddTextBox(conControlArea, null, "", 15, 100, 140, 40);
        	m_btnImportFromFile = CUtilities.AddButton(conControlArea, this, "...", 140, 210, 18, 50);
        	
        	// Import From Text Area
        	m_txaImportFromTextArea = new JTextArea( 10, 36 );
        	m_scpImportFromTextArea = new JScrollPane( m_txaImportFromTextArea );
            conControlArea.add(m_scpImportFromTextArea);
            splFrame.getConstraints( m_scpImportFromTextArea ).setX( Spring.constant( 7 ) );
            splFrame.getConstraints( m_scpImportFromTextArea ).setY( Spring.constant( 140 ) );
        	
            // Import From Surrounding Networks
            m_lstAvailableNetworks = CUtilities.AddListBox(conControlArea, null, 140, 5, 200, 200 );
        	m_lblSurroundingNetworksInterface = CUtilities.AddLabel(conControlArea, "Interface:", 141, 215);
        	m_cboSurroundingNetworksInterface = CUtilities.AddComboBox(conControlArea, null, 140, 290, 18, 100);
        	m_lblSurroundingNetworksScanMethod = CUtilities.AddLabel(conControlArea, "Method:", 168, 215);
        	m_rdbScanMethodIWList = CUtilities.AddRadioButton(conControlArea, this, bgpScanMethod, "IW List", 165, 285);
        	m_rdbScanMethodAirodump = CUtilities.AddRadioButton(conControlArea, this, bgpScanMethod, "Airodump", 185, 285);
        	m_btnScanForSurroundingNetworks = CUtilities.AddButton(conControlArea, this, "Scan", 210, 285, 18, 75);
        	
            m_cboImportType.SetSorted( false );
            m_cboImportType.SetActionListener( this );
            m_lblImportFromFile.setVisible( false );
            m_txtImportFromFile.setVisible( false );
            m_btnImportFromFile.setVisible( false );
            m_scpImportFromTextArea.setVisible( false );
            m_cboSurroundingNetworksInterface.SetSorted( false );
            
            m_lstAvailableNetworks.setVisible( false );
            m_lstAvailableNetworks.SetSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            m_btnScanForSurroundingNetworks.setVisible( false );
        	m_lblSurroundingNetworksInterface.setVisible ( false );
        	m_cboSurroundingNetworksInterface.setVisible ( false );
        	m_lblSurroundingNetworksScanMethod.setVisible ( false );
        	m_rdbScanMethodIWList.setVisible ( false );
        	m_rdbScanMethodAirodump.setVisible ( false );
        	m_btnScanForSurroundingNetworks.setVisible ( false );
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateImportTypes
	// Abstract: Populates the types of import you can do
	// --------------------------------------------------------------------------------
	private void PopulateImportTypes( )
	{
		try
		{
			m_cboImportType.AddItemToList("ESSID(s)", 0);
			m_cboImportType.AddItemToList("Password(s)", 1);
			m_cboImportType.AddItemToList("coWPAtty Table", 2);
			m_cboImportType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateNetworkInterfaces
	// Abstract: Populates the different network interfaces to scan on
	// --------------------------------------------------------------------------------
	private void PopulateNetworkInterfaces( )
	{
		try
		{
			CNetworkInterface aclsNetworkInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
			for ( int intIndex = 0; intIndex < aclsNetworkInterfaces.length; intIndex += 1 )
				m_cboSurroundingNetworksInterface.AddItemToList(aclsNetworkInterfaces[intIndex].GetInterface(), intIndex);

			m_cboSurroundingNetworksInterface.SetSelectedIndex( 0 );
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
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Radio button checks, button clicks, combobox changes
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_rdbImportFromFile )							rdbImportFromFile_Check( );
			else if ( aeSource.getSource( ) == m_btnImportFromFile )					btnImportFromFile_Click( );
			else if ( aeSource.getSource( ) == m_cboImportType.GetJavaComboBox( ) )		cboImportType_Change( );
			else if ( aeSource.getSource( ) == m_rdbImportFromTextArea )				rdbImportFromTextArea_Check( );
			else if ( aeSource.getSource( ) == m_rdbImportSurroundingNetworkNames )		rdbImportSurroundingNetworkNames_Check( );
			else if ( aeSource.getSource( ) == m_btnScanForSurroundingNetworks )		btnScanForSurroundingNetworks_Click( );
			else if ( aeSource.getSource( ) == m_btnImport )							btnImport_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: rdbImportFromFile_Check
	// Abstract: Displays the import from file parameters
	// --------------------------------------------------------------------------------
	private void rdbImportFromFile_Check( )
	{
		try
		{
			m_lblImportSettings.setText("Importing " + m_cboImportType.GetSelectedItemName() + " from File");
			ShowImportFromFileParameters( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: ShowImportFromFileParameters
	// Abstract: Hides all parameters and then just shows the import from file parameters
	// --------------------------------------------------------------------------------	
	private void ShowImportFromFileParameters( )
	{
		try
		{
			HideAllImportParameters( );
			m_lblImportFromFile.setVisible( true );
            m_txtImportFromFile.setVisible( true );
            m_btnImportFromFile.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: HideAllImportParameters
	// Abstract: Hides all import parameters
	// --------------------------------------------------------------------------------	
	private void HideAllImportParameters( )
	{
		try
		{
			m_lblImportFromFile.setVisible( false );
            m_txtImportFromFile.setVisible( false );
            m_btnImportFromFile.setVisible( false );
            m_scpImportFromTextArea.setVisible( false );
            m_lstAvailableNetworks.setVisible ( false );
            m_btnScanForSurroundingNetworks.setVisible ( false );
        	m_lblSurroundingNetworksInterface.setVisible ( false );
        	m_cboSurroundingNetworksInterface.setVisible ( false );
        	m_lblSurroundingNetworksScanMethod.setVisible ( false );
        	m_rdbScanMethodIWList.setVisible ( false );
        	m_rdbScanMethodAirodump.setVisible ( false );
        	m_btnScanForSurroundingNetworks.setVisible ( false );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnImportFromFile_Click
	// Abstract: Displays a choose file dialog to specify the file to import from
	// --------------------------------------------------------------------------------
	private void btnImportFromFile_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtImportFromFile, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: cboImportType_Change
	// Abstract: Changes the type of import
	// --------------------------------------------------------------------------------
	private void cboImportType_Change( )
	{
		try
		{
			String strSelectedImportType = m_cboImportType.GetSelectedItemName( );
			if ( strSelectedImportType.equals( "ESSID(s)" ) )
			{
				DisplayImportingESSIDs( );
			}
			else if ( strSelectedImportType.equals( "Password(s)" ) )
			{
				DisplayImportingPasswords( );
			}
			else if ( strSelectedImportType.equals( "coWPAtty Table" ) )
			{
				DisplayImportingCoWPAttyFile( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayImportingESSIDs
	// Abstract: Shows the importing ESSIDs fields
	// --------------------------------------------------------------------------------
	private void DisplayImportingESSIDs( )
	{
		try
		{
			if ( m_rdbImportSurroundingNetworkNames.isVisible() == false )
				m_rdbImportSurroundingNetworkNames.setVisible( true );
			if ( m_rdbImportFromTextArea.isVisible() == false )
				m_rdbImportFromTextArea.setVisible( true );
			if ( m_rdbImportFromFile.isSelected( ) == true )
				m_lblImportSettings.setText("Importing ESSID(s) from File");
			else if ( m_rdbImportFromTextArea.isSelected( ) == true )
				m_lblImportSettings.setText("Importing ESSID(s) from Textbox");
			else if ( m_rdbImportSurroundingNetworkNames.isSelected( ) == true )
				m_lblImportSettings.setText("Importing ESSID(s) from Surrounding Networks");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayImportingPasswords
	// Abstract: Shows the importing passwords fields
	// --------------------------------------------------------------------------------
	private void DisplayImportingPasswords( )
	{
		try
		{
			m_rdbImportSurroundingNetworkNames.setVisible( false );
			if ( m_rdbImportSurroundingNetworkNames.isSelected( ) == true )
			{
				m_rdbImportFromFile.setSelected( true );
				rdbImportFromFile_Check( );
			}
			if ( m_rdbImportFromTextArea.isVisible() == false )
				m_rdbImportFromTextArea.setVisible( true );
			if ( m_rdbImportFromFile.isSelected( ) == true )
				m_lblImportSettings.setText("Importing Password(s) from File");
			else if ( m_rdbImportFromTextArea.isSelected( ) == true )
				m_lblImportSettings.setText("Importing Password(s) from Textbox");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayImportingCoWPAttyFile
	// Abstract: Shows the importing coWPAtty fields
	// --------------------------------------------------------------------------------	
	private void DisplayImportingCoWPAttyFile( )
	{
		try
		{
			m_rdbImportSurroundingNetworkNames.setVisible( false );
			if ( m_rdbImportSurroundingNetworkNames.isSelected( ) == true )
			{
				m_rdbImportFromFile.setSelected( true );
				rdbImportFromFile_Check( );
			}
			m_rdbImportFromTextArea.setVisible( false );
			if ( m_rdbImportFromTextArea.isSelected( ) == true )
			{
				m_rdbImportFromFile.setSelected( true );
				rdbImportFromFile_Check( );
			}
			if ( m_rdbImportFromFile.isSelected( ) == true )
				m_lblImportSettings.setText("Importing coWPAtty Table from File");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: rdbImportFromTextArea_Check
	// Abstract: Shows the import from text area options
	// --------------------------------------------------------------------------------
	private void rdbImportFromTextArea_Check( )
	{
		try
		{
			m_lblImportSettings.setText("Importing " + m_cboImportType.GetSelectedItemName() + " from Textbox");
			ShowImportFromTextAreaParameters( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ShowImportFromTextAreaParameters
	// Abstract: Hides all parameters and just shows the text area
	// --------------------------------------------------------------------------------
	private void ShowImportFromTextAreaParameters( )
	{
		try
		{
			HideAllImportParameters( );
			m_scpImportFromTextArea.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: rdbImportSurroundingNetworkNames_Check
	// Abstract: Shows the import from surrounding networks options
	// --------------------------------------------------------------------------------
	private void rdbImportSurroundingNetworkNames_Check( )
	{
		try
		{
			m_lblImportSettings.setText("Importing " + m_cboImportType.GetSelectedItemName() + " from Surrounding Networks");
			ShowImportFromSurroundingNetworksParameters( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ShowImportFromSurroundingNetworksParameters
	// Abstract: Hides all parameters and just shows the from surrounding networks
	//			 options
	// --------------------------------------------------------------------------------
	private void ShowImportFromSurroundingNetworksParameters( )
	{
		try
		{
			HideAllImportParameters( );
			m_lstAvailableNetworks.setVisible( true );
            m_btnScanForSurroundingNetworks.setVisible( true );
        	m_lblSurroundingNetworksInterface.setVisible ( true );
        	m_cboSurroundingNetworksInterface.setVisible ( true );
        	m_lblSurroundingNetworksScanMethod.setVisible ( true );
        	m_rdbScanMethodIWList.setVisible ( true );
        	m_rdbScanMethodAirodump.setVisible ( true );
        	m_btnScanForSurroundingNetworks.setVisible ( true );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnScanForSurroundingNetworks_Click
	// Abstract: Scans for surrounding networks depending on the type of scan supplied
	// --------------------------------------------------------------------------------
	private void btnScanForSurroundingNetworks_Click( )
	{
		try
		{
			if ( m_rdbScanMethodIWList.isSelected( ) == true )
			{
				PopulateNearbyNetworksWithIWListScan( m_cboSurroundingNetworksInterface.GetSelectedItemName( ) );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No scan method specified.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateNearbyNetworksWithIWListScan
	// Abstract: Populates the dropdown with networks from an IW list scan
	// --------------------------------------------------------------------------------
	private void PopulateNearbyNetworksWithIWListScan( String strNetworkInterface )
	{
		try
		{
			m_lstAvailableNetworks.Clear( );
			String astrCommand[] = new String[] {"iwlist", strNetworkInterface, "scan"};
			CProcess clsSurroundingNetworks = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsSurroundingNetworks.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			while ( strBuffer != null )
			{
				if ( strBuffer.indexOf("ESSID:") > 0 )
				{
					strBuffer = strBuffer.substring(strBuffer.indexOf( "\"" ) + 1, strBuffer.lastIndexOf( "\"" ) );
					if ( strBuffer.trim().equals("") == false )
					{
						m_lstAvailableNetworks.AddItemToList(strBuffer, 0);
					}
				}
				strBuffer = brOutput.readLine( );
			}
			clsSurroundingNetworks.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnImport_Click
	// Abstract: Import button Click event
	// --------------------------------------------------------------------------------
	private void btnImport_Click( )
	{
		try
		{
			String strImportType = m_astrIMPORT_TYPES_COMMAND_ARGUMENTS[ m_cboImportType.GetSelectedItemValue( ) ];
			String strFileName = "";
			boolean blnFileWasGenerated = false;
			
			if ( m_rdbImportFromFile.isSelected( ) == true )
			{
				strFileName = m_txtImportFromFile.getText( );
			}
			else if ( m_rdbImportFromTextArea.isSelected() == true )
			{
				blnFileWasGenerated = true;
				strFileName = SaveTextAreaContentsToFile( );
			}
			else if ( m_rdbImportSurroundingNetworkNames.isSelected( ) == true )
			{
				blnFileWasGenerated = true;
				strFileName = SaveSurroundingNetworkNamesToFile( );
			}
			
			String astrCommand[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--import", strImportType, strFileName};
			CProcess clsImportData = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsImportData.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			while ( strBuffer != null )
			{
				if ( strBuffer.indexOf("Could not open file/stream for reading." ) >= 0 )
					JOptionPane.showMessageDialog(null, "Error: Could not open file/stream for reading.");
				if ( strBuffer.indexOf("Done.") >= 0 )
					JOptionPane.showMessageDialog(null, "Import successful!");
					
				strBuffer = brOutput.readLine( );
			}
			
			if ( blnFileWasGenerated == true )
			{
				File filGeneratedFile = new File( strFileName );
				
				if ( filGeneratedFile.delete( ) == false )
				{
					JOptionPane.showMessageDialog(null, "Could not delete temporary file \"" + strFileName + "\". Please manually delete it.");
				}
					
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SaveTextAreaContentsToFile
	// Abstract: Saves the text value of the text area to a ftemporary ile
	// --------------------------------------------------------------------------------
	private String SaveTextAreaContentsToFile( )
	{
		String strFileName = "";
		try
		{
			strFileName = "/tmp/AircrackNGUI_";
			Calendar calCurrentDate = Calendar.getInstance( );
			strFileName += String.valueOf(calCurrentDate.get(Calendar.MONTH)) + String.valueOf(calCurrentDate.get(Calendar.DAY_OF_MONTH)) + String.valueOf(calCurrentDate.get(Calendar.YEAR)) + "_";
			strFileName += String.valueOf(calCurrentDate.get(Calendar.HOUR)) + String.valueOf(calCurrentDate.get(Calendar.MINUTE)) + String.valueOf(calCurrentDate.get(Calendar.SECOND));
			strFileName += ".txt";
			
			FileWriter filFileStream = new FileWriter( strFileName );
			BufferedWriter bwInput = new BufferedWriter( filFileStream );
			bwInput.write( m_txaImportFromTextArea.getText( ) );
			bwInput.close( );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strFileName;
	}

	// --------------------------------------------------------------------------------
	// Name: SaveSurroundingNetworkNamesToFile
	// Abstract: Saves the nearby network names to a file
	// --------------------------------------------------------------------------------
	private String SaveSurroundingNetworkNamesToFile( )
	{
		String strFileName = "";
		try
		{
			strFileName = "/tmp/AircrackNGUI_";
			Calendar calCurrentDate = Calendar.getInstance( );
			strFileName += String.valueOf(calCurrentDate.get(Calendar.MONTH)) + String.valueOf(calCurrentDate.get(Calendar.DAY_OF_MONTH)) + String.valueOf(calCurrentDate.get(Calendar.YEAR)) + "_";
			strFileName += String.valueOf(calCurrentDate.get(Calendar.HOUR)) + String.valueOf(calCurrentDate.get(Calendar.MINUTE)) + String.valueOf(calCurrentDate.get(Calendar.SECOND));
			strFileName += ".txt";
			
			int aintSelectedIndices[] = m_lstAvailableNetworks.GetSelectedIndices( );
			FileWriter filFileStream = new FileWriter( strFileName );
			BufferedWriter bwInput = new BufferedWriter( filFileStream );
			
			for ( int intIndex = 0; intIndex < aintSelectedIndices.length; intIndex += 1 )
				bwInput.write( m_lstAvailableNetworks.GetItemName( intIndex ) + "\n" );
			bwInput.close( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strFileName;
	}
		
}