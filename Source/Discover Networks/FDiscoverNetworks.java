// --------------------------------------------------------------------------------
// Name: FDiscoverNetworks
// Abstract: Discovers networks around the computer
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;    	// Events for AWT
import javax.swing.*;       	// Newer version of graphics

public class FDiscoverNetworks extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CComboBox m_cboInterfaces = null;
	
	private JCheckBox m_chkInitializationVectorsOnly = null;
	private JCheckBox m_chkUseGPSdToGetCoordinates = null;
	private JCheckBox m_chkRecordAllBeacons = null;
	private JCheckBox m_chkPrintACKCTSRTSStatistics = null;
	private JCheckBox m_chkChangeUpdateFrequency = null;
	private CTextBox m_txtChangeUpdateFrequency = null;
	private JCheckBox m_chkWriteResultsToFile = null;
	private CTextBox m_txtWriteResultsToFile = null;
	private JCheckBox m_chkSetNetworkPacketTimeout = null;
	private CTextBox m_txtSetNetworkPacketTimeout = null;
	private JCheckBox m_chkSpecificChannels = null;
	private CComboBox m_cboSpecificChannels = null;
	private JCheckBox m_chkSpecificBands = null;
	private CComboBox m_cboSpecificBands = null;
	private JCheckBox m_chkReadPacketsFromFile = null;
	private CTextBox m_txtReadPacketsFromFile = null;
	private JButton m_btnReadPacketsFromFileBrowse = null;
	private JCheckBox m_chkSpecifyEncryptionMethod = null;
	private CComboBox m_cboSpecifyEncryptionMethod = null;
	private JCheckBox m_chkSpecifyNetworkBSSID = null;
	private CTextBox m_txtSpecifyNetworkBSSID = null;
	private JButton m_btnSpecifyNetworkBSSID = null;
	private JCheckBox m_chkOnlyShowClients = null;
	
	private CComboBox m_cboIWDevInterfaces = null;
	private JCheckBox m_chkIWDevPassiveScan = null;
	
	private JCheckBox m_chkPerformWashScan = null;
	private CComboBox m_cboWashInterface = null;
	
	private JButton m_btnNetworkSearch = null;
    
	// --------------------------------------------------------------------------------
	// Name: FDiscoverNetworks
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FDiscoverNetworks()
	{
		super("Discover Networks", 420, 570, false, false, "DiscoverNetworks");
	    try
	    {
	        AddControls();
	        PopulateInterfaces();
	        PopulateChannels();
	        PopulateBands();
	        PopulateEncryptionMethods();
	    }
	    catch (Exception excError)
	    {
	        // Display error message
	        CUtilities.WriteLog( excError );
	    }
	}

    // ----------------------------------------------------------------------------
    // Name: AddControls
    // Abstract: Adds the controls to the window
    // ----------------------------------------------------------------------------
    private void AddControls( )
    {
        
        try
        {
            CUtilities.AddLabel(m_cntControlContainer, "Airodump-NG Settings:", 53, 10);
            CUtilities.AddLabel(m_cntControlContainer, "Interface to scan with:", 73, 10);
            m_cboInterfaces = CUtilities.AddComboBox(m_cntControlContainer, null, 70, 180, 20, 90);
            
            m_chkInitializationVectorsOnly = CUtilities.AddCheckBox(m_cntControlContainer, this, "Only capture initialization vectors", 90, 6);
            m_chkUseGPSdToGetCoordinates = CUtilities.AddCheckBox(m_cntControlContainer, this, "Attempt to use GPSd to get coordinates", 110, 6);
            m_chkRecordAllBeacons = CUtilities.AddCheckBox(m_cntControlContainer, this, "Record all beacons", 130, 6);
            m_chkPrintACKCTSRTSStatistics = CUtilities.AddCheckBox(m_cntControlContainer, this, "Print ACK/CTS/RTS statistics", 150, 6);
            m_chkChangeUpdateFrequency = CUtilities.AddCheckBox(m_cntControlContainer, this, "Change update frequency:", 171, 6);
            m_txtChangeUpdateFrequency = CUtilities.AddTextBox(m_cntControlContainer, null, "1", 4, 5, 173, 220);
            CUtilities.AddLabel(m_cntControlContainer, " sec(s)", 174, 265);
            m_chkWriteResultsToFile = CUtilities.AddCheckBox(m_cntControlContainer, this, "Write Results To File - Prefix:", 191, 6);
            m_txtWriteResultsToFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 14, 15, 193, 235);
            m_chkSetNetworkPacketTimeout = CUtilities.AddCheckBox(m_cntControlContainer, this, "Received packet timeout:", 211, 6);
            m_txtSetNetworkPacketTimeout = CUtilities.AddTextBox(m_cntControlContainer, null, "120", 4, 5, 214, 211);
            CUtilities.AddLabel(m_cntControlContainer, " sec(s)", 216, 256);
            m_chkSpecificChannels = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specific channel:", 233, 6);
            m_cboSpecificChannels = CUtilities.AddComboBox(m_cntControlContainer, null, 234, 155, 20, 50);
            m_cboSpecificChannels.SetSorted(false);
            m_chkSpecificBands = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specific band:", 256, 6);
            m_cboSpecificBands = CUtilities.AddComboBox(m_cntControlContainer, null, 257, 135, 20, 50);
            m_chkReadPacketsFromFile = CUtilities.AddCheckBox(m_cntControlContainer, this, "Read packets from file:", 278, 6);
            m_txtReadPacketsFromFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 50, 280, 195);
            m_btnReadPacketsFromFileBrowse = CUtilities.AddButton(m_cntControlContainer, this, "...", 280, 365, 18, 30);
            m_chkSpecifyEncryptionMethod = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specific encryption method:", 298, 6);
            m_cboSpecifyEncryptionMethod = CUtilities.AddComboBox(m_cntControlContainer, null, 300, 235, 20, 75);
            
            m_chkSpecifyNetworkBSSID = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify network BSSID:", 320, 6);
            m_txtSpecifyNetworkBSSID = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 17, 322, 205);
            m_btnSpecifyNetworkBSSID = CUtilities.AddButton(m_cntControlContainer, this, "...", 322, 335, 18, 30);
            m_chkOnlyShowClients = CUtilities.AddCheckBox(m_cntControlContainer, this, "Only show clients", 340, 6);
            
            CUtilities.AddLabel(m_cntControlContainer, "IW Dev Scan Settings:", 370, 10);
            CUtilities.AddLabel(m_cntControlContainer, "Interface:", 390, 10);
            m_cboIWDevInterfaces = CUtilities.AddComboBox(m_cntControlContainer, null, 388, 85, 20, 90);
            m_chkIWDevPassiveScan = CUtilities.AddCheckBox(m_cntControlContainer, this, "Scan passively", 408, 5);
            
            CUtilities.AddLabel(m_cntControlContainer, "Wash Scan Settings:", 440, 10);
            m_chkPerformWashScan = CUtilities.AddCheckBox(m_cntControlContainer, this, "Perform Wash Scan", 455, 5);
            CUtilities.AddLabel(m_cntControlContainer, "Interface:", 479, 10);
            m_cboWashInterface = CUtilities.AddComboBox(m_cntControlContainer, null, 478, 85, 18, 90);
            
            m_btnNetworkSearch = CUtilities.AddButton(m_cntControlContainer, this, "Begin Search", 508, 135, 25, 150);
            
            m_lstParameters.add(new CProfileParameter("Interface", m_cboInterfaces));
            m_lstParameters.add(new CProfileParameter("InitializationVectors", m_chkInitializationVectorsOnly));
            m_lstParameters.add(new CProfileParameter("GPSdCoordinates", m_chkUseGPSdToGetCoordinates));
            m_lstParameters.add(new CProfileParameter("RecordAllBeacons", m_chkRecordAllBeacons));
            m_lstParameters.add(new CProfileParameter("PrintACKCTSRTSStatistics", m_chkPrintACKCTSRTSStatistics));
            m_lstParameters.add(new CProfileParameter("UpdateFrequency", m_chkChangeUpdateFrequency, m_txtChangeUpdateFrequency));
            m_lstParameters.add(new CProfileParameter("WriteResultsToFile", m_chkWriteResultsToFile, m_txtWriteResultsToFile));
            m_lstParameters.add(new CProfileParameter("NetworkPacketTimeout", m_chkSetNetworkPacketTimeout, m_txtSetNetworkPacketTimeout));
            m_lstParameters.add(new CProfileParameter("Channel", m_chkSpecificChannels, m_cboSpecificChannels));
            m_lstParameters.add(new CProfileParameter("Bands", m_chkSpecificBands, m_cboSpecificBands));
            m_lstParameters.add(new CProfileParameter("ReadPacketsFromFile", m_chkReadPacketsFromFile, m_txtReadPacketsFromFile));
            m_lstParameters.add(new CProfileParameter("EncryptionMethod", m_chkSpecifyEncryptionMethod, m_cboSpecifyEncryptionMethod));
            m_lstParameters.add(new CProfileParameter("NetworkBSSID", m_chkSpecifyNetworkBSSID, m_txtSpecifyNetworkBSSID));
            m_lstParameters.add(new CProfileParameter("OnlyShowClients", m_chkOnlyShowClients));
            m_lstParameters.add(new CProfileParameter("IwDevScanInterface", m_cboIWDevInterfaces));
            m_lstParameters.add(new CProfileParameter("IWDevPassiveScan", m_chkIWDevPassiveScan));
            m_lstParameters.add(new CProfileParameter("WashInterface", m_cboWashInterface));
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }
    
    // ----------------------------------------------------------------------------
    // Name: PopulateInterfaces
    // Abstract: Populates the interfaces for the different scanning options
    // ----------------------------------------------------------------------------
    private void PopulateInterfaces( )
    {
        try
        {
        	CNetworkInterface aclsAllMonitorInterfaces[] = CGlobals.clsLocalMachine.GetAllMonitorableInterfaces();
			CNetworkInterface aclsAllNetworkInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces();
			
			for ( int intIndex = 0; intIndex < aclsAllMonitorInterfaces.length; intIndex += 1 )
			{
				m_cboInterfaces.AddItemToList(aclsAllMonitorInterfaces[intIndex].GetInterface(), 0);
				m_cboWashInterface.AddItemToList(aclsAllMonitorInterfaces[intIndex].GetInterface(), 0);
			}
			for ( int intIndex = 0; intIndex < aclsAllNetworkInterfaces.length; intIndex += 1 )
				m_cboIWDevInterfaces.AddItemToList(aclsAllNetworkInterfaces[intIndex].GetInterface(), 0);
			
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboInterfaces, CUserPreferences.GetPreferredMonitorInterface());
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboWashInterface, CUserPreferences.GetPreferredMonitorInterface());
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboIWDevInterfaces, CUserPreferences.GetPreferredStandardInterface());
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateChannels
    // Abstract: Populates the channels that you can scan on.
    // ----------------------------------------------------------------------------
    private void PopulateChannels( )
    {
        try
        {
            for (int intChannel = 1; intChannel <= 14; intChannel += 1)
            {
                m_cboSpecificChannels.AddItemToList(Integer.toString(intChannel), intChannel);
            }
            m_cboSpecificChannels.SetSelectedIndex(0);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateBands
    // Abstract: Populates the bands that you can scan on.
    // ----------------------------------------------------------------------------
    private void PopulateBands( )
    {
        try
        {
            m_cboSpecificBands.AddItemToList("a", 1);
            m_cboSpecificBands.AddItemToList("b", 2);
            m_cboSpecificBands.AddItemToList("g", 3);
            // m_cboSpecificBands.AddItemToList("n", 1);
            m_cboSpecificBands.SetSelectedIndex(0);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateEncryptionMethods
    // Abstract: Populates the encryption methods that you can scan on.
    // ----------------------------------------------------------------------------
    private void PopulateEncryptionMethods( )
    {
        try
        {
            m_cboSpecifyEncryptionMethod.AddItemToList("OPN", 1);
            m_cboSpecifyEncryptionMethod.AddItemToList("WEP", 2);
            m_cboSpecifyEncryptionMethod.AddItemToList("WPA", 3);
            m_cboSpecifyEncryptionMethod.AddItemToList("WPA1", 4);
            m_cboSpecifyEncryptionMethod.AddItemToList("WPA2", 5);
            m_cboSpecifyEncryptionMethod.SetSelectedIndex(0);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }

    // ----------------------------------------------------------------------------
    // Name: actionPerformed
    // Abstract: Buttons clicks, checkbox checks, menu item selections
    // ----------------------------------------------------------------------------
	public void actionPerformed( ActionEvent aeSource )
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
	        	if ( aeSource.getSource() == m_btnNetworkSearch)				btnNetworkSearch_Click( );
				else if ( aeSource.getSource() == m_chkSpecificChannels)			chkSpecificChannels_Checked( );
				else if ( aeSource.getSource() == m_chkSpecificBands)				chkSpecificBands_Checked( );
				else if ( aeSource.getSource() == m_btnReadPacketsFromFileBrowse) 	CAircrackUtilities.DisplayFileChooser(m_txtReadPacketsFromFile, this);
				else if ( aeSource.getSource( ) == m_btnSpecifyNetworkBSSID )		btnSpecifyNetworkBSSID_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError.toString());
		}    
	}

    // ----------------------------------------------------------------------------
    // Name: btnNetworkSearch_Click
    // Abstract: Starts the network search
    // ----------------------------------------------------------------------------
    private void btnNetworkSearch_Click( )
    {
        try
        {
            String astrArguments[] = null;
            String strAirodumpNGInterface = m_cboInterfaces.GetSelectedItemName( );
            String strIWScanInterface = m_cboIWDevInterfaces.GetSelectedItemName( );
            
            astrArguments = BuildArgumentsList( );

            FDiscoverNetworksResults frmResults = new FDiscoverNetworksResults();
            frmResults.SetArguments( astrArguments );
            frmResults.SetAirodumpInterface( strAirodumpNGInterface );
            frmResults.SetIWDevInterface( strIWScanInterface );
            frmResults.SetIWDevScanPassiveScan( m_chkIWDevPassiveScan.isSelected());
            if ( m_chkPerformWashScan.isSelected() )
            	frmResults.SetWashInterface(m_cboWashInterface.GetSelectedItemName());
            frmResults.setVisible( true );             
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }

    // ----------------------------------------------------------------------------
    // Name: BuildArgumentsList
    // Abstract: Builds the command for the scan
    // ----------------------------------------------------------------------------
    private String[] BuildArgumentsList( )
    {
        String astrArguments[] = null;
        try
        {
        	astrArguments = new String[ 0 ];
        	
            if ( m_chkInitializationVectorsOnly.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("i", "", astrArguments);
            
            if ( m_chkUseGPSdToGetCoordinates.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("g", "", astrArguments);
            
            if ( m_chkRecordAllBeacons.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("e", "", astrArguments);
            
            if ( m_chkPrintACKCTSRTSStatistics.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("-showack", "", astrArguments);
            
            if ( m_chkChangeUpdateFrequency.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("u", m_txtChangeUpdateFrequency.getText(), astrArguments);
            
            if ( m_chkWriteResultsToFile.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("w", m_txtWriteResultsToFile.getText(), astrArguments);
            
            if ( m_chkSetNetworkPacketTimeout.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("-berlin", m_txtSetNetworkPacketTimeout.getText(), astrArguments);
            
            if ( m_chkSpecificChannels.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("c", String.valueOf(m_cboSpecificChannels.GetSelectedItemValue()), astrArguments);
            
            if ( m_chkSpecificBands.isSelected() == true )
            {
                switch (m_cboSpecificBands.GetSelectedItem().GetValue())
                {
                    case 1:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("b", "a", astrArguments);
                        break;
                    case 2:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("b", "b", astrArguments);
                        break;
                    case 3:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("b", "g", astrArguments);
                        break;
                }
            }

            if ( m_chkReadPacketsFromFile.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("r", m_txtReadPacketsFromFile.getText(), astrArguments);

            if ( m_chkSpecifyEncryptionMethod.isSelected() == true )
            {
                switch (m_cboSpecifyEncryptionMethod.GetSelectedItem().GetValue())
                {
                    case 1:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", "OPN", astrArguments);
                        break;
                    case 2:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", "WEP", astrArguments);
                        break;
                    case 3:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", "WPA", astrArguments);
                        break;
                    case 4:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", "WPA1", astrArguments);
                        break;
                    case 5:
                    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", "WPA2", astrArguments);
                        break;
                }
            }
            
            if ( m_chkSpecifyNetworkBSSID.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("d", m_txtSpecifyNetworkBSSID.getText(), astrArguments);

            if ( m_chkOnlyShowClients.isSelected() == true )
            	astrArguments = CAircrackUtilities.AddArgumentToCommand("a", "", astrArguments);
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
        return astrArguments;
    }
    
    // ----------------------------------------------------------------------------
    // Name: chkSpecificChannels_Checked
    // Abstract: Specific channel checkbox checked
    // ----------------------------------------------------------------------------
	private void chkSpecificChannels_Checked( )
	{
		try
		{
			// Bands and channels cannot be specified. If channels is selected, prevent bands
			// from being checked. If not, enable the bands option.
			if ( m_chkSpecificChannels.isSelected() )
			{
				m_chkSpecificBands.setSelected(false);
				m_chkSpecificBands.setEnabled(false);
				m_cboSpecificBands.SetEnabled(false);
			}
			else
			{
				m_chkSpecificBands.setEnabled(true);
				m_cboSpecificBands.SetEnabled(true);
			}	
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // ----------------------------------------------------------------------------
    // Name: chkSpecificBands_Checked
    // Abstract: Specific bands checkbox checked
    // ----------------------------------------------------------------------------
	private void chkSpecificBands_Checked( )
	{
		try
		{
			if ( m_chkSpecificBands.isSelected() )
			{
				m_chkSpecificChannels.setSelected(false);
				m_chkSpecificChannels.setEnabled(false);
				m_cboSpecificChannels.SetEnabled(false);
			}
			else
			{
				m_chkSpecificChannels.setEnabled(true);
				m_cboSpecificChannels.SetEnabled(true);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // ----------------------------------------------------------------------------
    // Name: btnSpecifyNetworkBSSID_Click
    // Abstract: Displays a dialog for selecting a network BSSID
    // ----------------------------------------------------------------------------
	private void btnSpecifyNetworkBSSID_Click( )
	{
		try
		{
			DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
			dlgSelectNetwork.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID);
			dlgSelectNetwork.setVisible( true );
			m_txtSpecifyNetworkBSSID.setText( dlgSelectNetwork.GetSelectedNetwork( ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
