// --------------------------------------------------------------------------------
// Name: FDiscoverHosts
// Abstract: Does a network scan and shows the results of it
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.regex.*;

public class FDiscoverHosts extends CAircrackWindow implements ActionListener, MouseListener
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CComboBox m_cboInterfaces = null;
	private JButton m_btnStartScan = null;
	
	// Target Types
	private JRadioButton m_rdbScanMySubnet = null;
	private JRadioButton m_rdbScanCustomTarget = null;
	private CTextBox m_txtScanCustomTarget = null;
	
	// Scan Types
	private JRadioButton m_rdbNormalScan = null;
	private JRadioButton m_rdbSYNScan = null;
	private JRadioButton m_rdbOperatingSystemScan = null;
	private JCheckBox m_chkQuickScan = null;
	
	private CTable m_tblSearchResults = null;

	private final String m_astrScanColumns[] = { "Device Type", "Host Name", "IP Address", "MAC Address", "Manufacturer", "Open Ports" };
	
	private CDropdownButton m_ddbTestDropdown = null;
	
	private JPopupMenu m_pumTablePopupMenu = null;
	private JMenuItem m_miPing = null;
	private JMenuItem m_miTelnet = null;
	private JMenuItem m_miTraceRoute = null;
	private JMenuItem m_miViewHostInformation = null;
	private JMenuItem m_miDeauthenticationAttack = null;
	private JMenuItem m_miARPPoisoningAttack = null;
	private JMenuItem m_miSpoofMACToHostAddress = null;
	private JMenuItem m_miDig = null;
	private JMenuItem m_miNikto = null;
	
	private final Pattern m_ptnPortPattern = Pattern.compile("^[0-9]{1,5}/(tcp|udp)");
	private String m_strNmapOutput = "";
	private String m_strPassedInDestination = "";
	
	// --------------------------------------------------------------------------------
	// Name: FDiscoverHosts
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FDiscoverHosts( )
	{
		super( "Discover Hosts", 800, 360, false, false, "DiscoverHosts" );
		try
		{
			AddControls( );
			PopulateInterfaces( );
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
            ButtonGroup btgScanTargets = new ButtonGroup( );
            ButtonGroup btgScanTypes = new ButtonGroup( );
            
            CUtilities.AddLabel(m_cntControlContainer, "Interface:", 56, 5);
            m_cboInterfaces = CUtilities.AddComboBox(m_cntControlContainer, null, 55, 80, 18, 75);
    
            CUtilities.AddLabel(m_cntControlContainer, "Scan Target:", 80, 5);
        	m_rdbScanMySubnet = CUtilities.AddRadioButton(m_cntControlContainer, this, btgScanTargets, "My Subnet", 95, 5);
        	m_rdbScanCustomTarget = CUtilities.AddRadioButton(m_cntControlContainer, this, btgScanTargets, "Custom Target", 115, 5);
        	m_txtScanCustomTarget = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 140, 25);
            m_txtScanCustomTarget.setEnabled( false );
            m_rdbScanMySubnet.setSelected( true );
        	
            CUtilities.AddLabel(m_cntControlContainer, "Scan Type:", 165, 5);
            m_rdbNormalScan = CUtilities.AddRadioButton(m_cntControlContainer, this, btgScanTypes, "Normal", 180, 5);
            m_rdbSYNScan = CUtilities.AddRadioButton(m_cntControlContainer, this, btgScanTypes, "SYN (-sS)", 205, 5);
            m_rdbOperatingSystemScan = CUtilities.AddRadioButton(m_cntControlContainer, this, btgScanTypes, "OS (-O)", 230, 5);
            m_chkQuickScan = CUtilities.AddCheckBox(m_cntControlContainer, this, "Quick Scan (-F)", 255, 5);
            
            m_btnStartScan = CUtilities.AddButton(m_cntControlContainer, this, "Scan", 285, 93, 18, 75);
        	
            m_tblSearchResults = CUtilities.AddTable(m_cntControlContainer, m_astrScanColumns, null, 5, 285, 300, 500);
            
            m_ddbTestDropdown = CUtilities.AddDropdownButton(m_cntControlContainer, this, this, "Save To File", 305, 680, 18, 100);
            m_ddbTestDropdown.SetMenuOptions(new String[] {"Nmap Output", "Table View - CSV", "Table View - HTML"});
            
            m_rdbNormalScan.setSelected( true );
            
            m_pumTablePopupMenu = new JPopupMenu( );
            m_miViewHostInformation = CAircrackUtilities.CreateMenuItem("View Host Information", m_pumTablePopupMenu, this);
            
            JMenu mnuBasicTools = new JMenu( "Basic Tools" );
            m_miPing = CAircrackUtilities.CreateMenuItem("Ping", mnuBasicTools, this);
            m_miTelnet = CAircrackUtilities.CreateMenuItem("Telnet", mnuBasicTools, this);
            m_miTraceRoute = CAircrackUtilities.CreateMenuItem("Trace Route", mnuBasicTools, this);
            m_miDig = CAircrackUtilities.CreateMenuItem("Dig", mnuBasicTools, this);
            m_pumTablePopupMenu.add( mnuBasicTools );
            
            m_miDeauthenticationAttack = CAircrackUtilities.CreateMenuItem("De-authentication Attack", m_pumTablePopupMenu, this);
            m_miARPPoisoningAttack = CAircrackUtilities.CreateMenuItem("ARP Poisoning Attack", m_pumTablePopupMenu, this);
            m_miSpoofMACToHostAddress = CAircrackUtilities.CreateMenuItem("Spoof MAC to Host Address", m_pumTablePopupMenu, this);
           
            m_pumTablePopupMenu.addSeparator( );
            m_miNikto = CAircrackUtilities.CreateMenuItem("Nikto", m_pumTablePopupMenu, this);
            
            
            m_tblSearchResults.AddPopupMenu( m_pumTablePopupMenu );
            
            m_miViewHostInformation.setEnabled( false );
            
            // Add the profile parameters
            m_lstParameters.add(new CProfileParameter("Interface", m_cboInterfaces));
            m_lstParameters.add(new CProfileParameter("ScanTarget", m_rdbScanMySubnet, "Subnet"));
            m_lstParameters.add(new CProfileParameter("ScanTarget", m_rdbScanCustomTarget, "Custom", m_txtScanCustomTarget));
            m_lstParameters.add(new CProfileParameter("ScanType", m_rdbNormalScan, "Normal"));
            m_lstParameters.add(new CProfileParameter("ScanType", m_rdbSYNScan, "SYN"));
            m_lstParameters.add(new CProfileParameter("ScanType", m_rdbOperatingSystemScan, "OS"));
            m_lstParameters.add(new CProfileParameter("QuickScan", m_chkQuickScan));
		}
		catch (Exception excError)
		{
			JOptionPane.showMessageDialog(null, excError.getMessage());
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the interfaces that you can scan on
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces(); 

			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				if ( aclsInterfaces[intIndex].GetMode( ).equals("MONITOR") == false )
					m_cboInterfaces.AddItemToList(aclsInterfaces[intIndex].GetInterface( ), intIndex);
					
			}
			
			m_cboInterfaces.SetSelectedIndex( 0 );
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboInterfaces, CUserPreferences.GetPreferredStandardInterface());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Menu item, button, and checkbox click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_miPing ) miPing_Click( );
				else if ( aeSource.getSource( ) == m_miTelnet ) miTelnet_Click( );
				else if ( aeSource.getSource( ) == m_miTraceRoute ) miTraceRoute_Click( );
				else if ( aeSource.getSource( ) == m_miDig ) miDig_Click( );
				else if ( aeSource.getSource( ) == m_miViewHostInformation ) JOptionPane.showMessageDialog(null, "VIEW HOST INFORMATION!");
				else if ( aeSource.getSource( ) == m_miDeauthenticationAttack ) miDeauthenticationAttack_Click( );
				else if ( aeSource.getSource( ) == m_miARPPoisoningAttack ) miARPPoisoningAttack_Click( );
				else if ( aeSource.getSource( ) == m_miSpoofMACToHostAddress ) miSpoofMACToHostAddress_Click( );
				else if ( aeSource.getSource( ) == m_btnStartScan ) btnStartScan_Click( );
				else if ( aeSource.getSource( ) == m_rdbScanMySubnet || aeSource.getSource( ) == m_rdbScanCustomTarget ) m_txtScanCustomTarget.setEnabled(m_rdbScanCustomTarget.isSelected());
				else if ( aeSource.getSource( ) == m_miNikto ) miNikto_Click( );
				else if ( aeSource.getSource( ) == m_ddbTestDropdown.GetMenuItem("Nmap Output") ) SaveNmapOutputToFile( );
				else if ( aeSource.getSource( ) == m_ddbTestDropdown.GetMenuItem("Table View - CSV") ) SaveTableViewToCSVFile( );
				else if ( aeSource.getSource( ) == m_ddbTestDropdown.GetMenuItem("Table View - HTML") ) SaveTableViewToHTMLFile( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miPing_Click
	// Abstract: Opens a ping dialog with the target's IP address populated
	// --------------------------------------------------------------------------------
	private void miPing_Click( )
	{
		try
		{
			String strIPAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FOtherToolsBasicToolsPing frmPing = new FOtherToolsBasicToolsPing( );
			frmPing.SetDestination(strIPAddress);
			frmPing.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miTelnet_Click
	// Abstract: Opens a telnet dialog with the target's IP address populated
	// --------------------------------------------------------------------------------
	private void miTelnet_Click( )
	{
		try
		{
			String strIPAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FOtherToolsBasicToolsTelnet frmTelnet = new FOtherToolsBasicToolsTelnet( );
			frmTelnet.SetDestination( strIPAddress );
			frmTelnet.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		} 
	}
	
	// --------------------------------------------------------------------------------
	// Name: miTraceRoute_Click
	// Abstract: Opens a trace route dialog with the target's IP address populated
	// --------------------------------------------------------------------------------
	private void miTraceRoute_Click( )
	{
		try
		{
			String strIPAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FOtherToolsBasicToolsTraceRoute frmTraceRoute = new FOtherToolsBasicToolsTraceRoute( );
			frmTraceRoute.SetDestination( strIPAddress );
			frmTraceRoute.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miDig_Click
	// Abstract: Opens a dig dialog with the target's IP address populated
	// --------------------------------------------------------------------------------
	private void miDig_Click( )
	{
		try
		{
			String strIPAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FOtherToolsBasicToolsDig frmDig = new FOtherToolsBasicToolsDig( );
			frmDig.SetDestination( strIPAddress );
			frmDig.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miDeauthenticationAttack_Click
	// Abstract: Opens a replay-inject packets dialog with de-authentication selected
	//			 and the target's MAC address and the network's BSSID populated
	// --------------------------------------------------------------------------------
	private void miDeauthenticationAttack_Click( )
	{
		try
		{
			ProcessBuilder pbGetAccessPointBSSID = new ProcessBuilder(new String[] {"iwconfig", m_cboInterfaces.GetSelectedItemName()});
			pbGetAccessPointBSSID.redirectErrorStream( true );
			Process prcGetAccessPointBSSID = pbGetAccessPointBSSID.start( );
			prcGetAccessPointBSSID.waitFor( );
			BufferedReader brOutput = new BufferedReader( new InputStreamReader( prcGetAccessPointBSSID.getInputStream( ) ) );
			String strBuffer = brOutput.readLine( );
			
			String strNetworkESSID = "";
			String strNetworkBSSID = "";
			String strTargetBSSID = "";
			
			while ( strBuffer != null && strBuffer.contains("ESSID:") == false )
			{
				strBuffer = brOutput.readLine( );
			}
			strBuffer = strBuffer.substring(strBuffer.indexOf("ESSID:") + 7).trim();
			strBuffer = strBuffer.substring(0, strBuffer.indexOf('"')).trim();
			strNetworkESSID = strBuffer;
			
			while ( strBuffer != null && strBuffer.contains("Access Point:") == false )
			{
				strBuffer = brOutput.readLine( );
			}
			
			strBuffer = strBuffer.substring(strBuffer.indexOf("Access Point:") + 13).trim();
			if ( strBuffer.indexOf(" ") >= 0 )
				strBuffer = strBuffer.substring(0, strBuffer.indexOf(" "));
			strBuffer = strBuffer.trim( );
			strNetworkBSSID = strBuffer;
			
			strTargetBSSID = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "MAC Address")).trim();
			
			FReplayInjectPackets frmReplayInjectPackets = new FReplayInjectPackets( );
			frmReplayInjectPackets.SetDestinationMAC(strTargetBSSID);
			frmReplayInjectPackets.SetNetworkBSSID(strNetworkBSSID);
			frmReplayInjectPackets.SetNetworkESSID(strNetworkESSID);
			frmReplayInjectPackets.setVisible( true );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miARPPoisoningAttack_Click
	// Abstract: Opens an ARP poisoning dialog with the target's IP address populated
	// --------------------------------------------------------------------------------
	private void miARPPoisoningAttack_Click( )
	{
		try
		{
			String strIPAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FARPPoisonRouting frmARPPoisonRouting = new FARPPoisonRouting( );
			frmARPPoisonRouting.SetTargetLocation(strIPAddress);
			frmARPPoisonRouting.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miSpoofMACToHostAddress_Click
	// Abstract: Opens a MAC changer dialog with the target's MAC address populated
	// --------------------------------------------------------------------------------
	private void miSpoofMACToHostAddress_Click( )
	{
		try
		{
			String strMACAddress = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "MAC Address")).trim();
			FOtherToolsMACChanger frmMACChanger = new FOtherToolsMACChanger( );
			frmMACChanger.SetCustomMACAddress(strMACAddress);
			frmMACChanger.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStartScan_Click
	// Abstract: Starts the network scan for targets
	// --------------------------------------------------------------------------------
	private void btnStartScan_Click( )
	{
		try
		{
			String strScanType = GetScanParameter( );
			String strScanTargets = GetScanTargets( m_cboInterfaces.GetSelectedItemName( ) );
			boolean blnFastScan = m_chkQuickScan.isSelected( );
			String astrCompleteCommand[] = GetCompleteCommand(strScanType, strScanTargets, blnFastScan);

			m_strNmapOutput = "nmap";
			for ( int intIndex = 1; intIndex < astrCompleteCommand.length; intIndex += 1 )
			{
				if ( astrCompleteCommand[intIndex].startsWith("-") == false )
					m_strNmapOutput += " \"" + astrCompleteCommand[intIndex].replace("\"", "\\\"") + "\"";
				else
					m_strNmapOutput += " " + astrCompleteCommand[intIndex];
			}
			m_strNmapOutput += "\n\n";
			
			CProcess clsNetworkSearch = new CProcess(astrCompleteCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsNetworkSearch.GetOutput( ) );
			brOutput.readLine( ); // Blank line
			brOutput.readLine( ); // Nmap version
			String strBuffer = brOutput.readLine( );
			
			m_tblSearchResults.ClearRows( );
			
			PerformNetworkScan( strBuffer, brOutput, blnFastScan );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetScanParameter
	// Abstract: Gets the parameter for the type of scan selected
	// --------------------------------------------------------------------------------
	private String GetScanParameter( )
	{
		String strScanParameter = "";
		try
		{
			if ( m_rdbNormalScan.isSelected( ) == true )
				strScanParameter = "";
			else if ( m_rdbSYNScan.isSelected( ) == true )
				strScanParameter = "-sS";
			else if ( m_rdbOperatingSystemScan.isSelected( ) == true )
				strScanParameter = "-O";
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strScanParameter;
	}

	// --------------------------------------------------------------------------------
	// Name: GetScanTargets
	// Abstract: Gets the scan targets depending on the IP address and subnet mask on
	//			 the interface.
	// --------------------------------------------------------------------------------
	private String GetScanTargets( String strInterfaceName )
	{
		String strScanTargets = "";
		
		try
		{
			CNetworkInterface clsInterface = new CNetworkInterface( strInterfaceName );
			String strSubnetMask = clsInterface.GetSubnetMask( );
			String strIPAddress = clsInterface.GetIPAddress( );
			
			if ( m_rdbScanMySubnet.isSelected( ) == true )
			{
				if ( strSubnetMask.equals("255.0.0.0") )
					strScanTargets = strIPAddress.substring(0, strIPAddress.indexOf(".") + 1) + "0.0.0/8";
				else if ( strSubnetMask.equals( "255.255.0.0" ) )
					strScanTargets = strIPAddress.substring(0, strIPAddress.lastIndexOf(".", strIPAddress.lastIndexOf(".") - 1) + 1) + "0.0/16";
				else if ( strSubnetMask.equals( "255.255.255.0" ) )
					strScanTargets = strIPAddress.substring(0, strIPAddress.lastIndexOf(".") + 1) + "0/24";
			}
			else
			{
				strScanTargets = m_txtScanCustomTarget.getText( ).trim( );
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		
		return strScanTargets;
	}

	// --------------------------------------------------------------------------------
	// Name: GetCompleteCommand
	// Abstract: Gets the complete nmap scan command
	// --------------------------------------------------------------------------------
	private String[] GetCompleteCommand(String strScanType, String strScanTargets, boolean blnFastScan)
	{
		String astrCompleteCommand[] = null;
		try
		{
			astrCompleteCommand = new String[] { "nmap", strScanType };
			if ( blnFastScan == true )
				astrCompleteCommand = CAircrackUtilities.AddArgumentToArray("-F", astrCompleteCommand);
			astrCompleteCommand = CAircrackUtilities.AddArgumentToCommand("e", m_cboInterfaces.GetSelectedItemName(), astrCompleteCommand);
			astrCompleteCommand = CAircrackUtilities.AddArgumentToArray(strScanTargets, astrCompleteCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCompleteCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: PerformNormalOrSilentScan
	// Abstract: Populates the result of a normal or silent network scan
	// --------------------------------------------------------------------------------
	private void PerformNetworkScan(String strStartingBuffer, BufferedReader brProcessBuffer, boolean blnFastScan)
	{
		try
		{
			String strHostName = "";
			String strIPAddress = "";
			String strMACAddress = "";
			String strManufacturer = "";
			String strDeviceType = "";
			String strBuffer = strStartingBuffer;
			BufferedReader brOutput = brProcessBuffer;
			int intPortCount = 0;
			Matcher mtrPortMatcher = null;
			
			while ( strBuffer != null )
			{
				m_strNmapOutput += strBuffer + "\n"; 
				mtrPortMatcher = m_ptnPortPattern.matcher(strBuffer);
				if ( strBuffer.indexOf("Nmap scan report for ") >= 0 )
				{
					// Add a new row if we have the data
					if ( strIPAddress.equals("") == false )
					{
						strDeviceType = AdjustDeviceType( strDeviceType, strManufacturer, strHostName );
						String astrData[] = new String[]{"", strHostName, strIPAddress, strMACAddress, strManufacturer, String.valueOf(intPortCount) };
						m_tblSearchResults.AddRow( astrData );
						
						// Reset our variables
						strHostName = "";
						strIPAddress = "";
						strMACAddress = "";
						strManufacturer = "";
						intPortCount = 0;
						strDeviceType = "";
					}
					strHostName = GetHostNameFromBuffer( strBuffer );
					strIPAddress = GetIPAddressFromBuffer( strBuffer );
				}
				
				// MAC Address and Manufacturer
				if ( strBuffer.indexOf("MAC Address") >= 0 )
				{
					strMACAddress = GetMACAddressFromBuffer( strBuffer );
					strManufacturer = GetManufacturerFromBuffer( strBuffer );
				}
				
				// Ports
				if ( mtrPortMatcher.find( ) && strBuffer.contains("closed") == false && strBuffer.contains("filtered") == false )
					intPortCount += 1;
				
				// Device Type
				if ( strBuffer.indexOf("Device type: ") >= 0 )
					strDeviceType = GetDeviceTypeFromBuffer( strBuffer, brOutput);

				strBuffer = brOutput.readLine( );
			}
			
			// The code above doesn't include the last detected host. Add it now.
			if ( strIPAddress.equals("") == false )
			{
				strDeviceType = AdjustDeviceType( strDeviceType, strManufacturer, strHostName );
				String astrData[] = new String[]{strDeviceType, strHostName, strIPAddress, strMACAddress, strManufacturer, String.valueOf(intPortCount) };
				m_tblSearchResults.AddRow( astrData );
			}

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetHostNameFromBuffer
	// Abstract: Gets the host name from the buffer
	// --------------------------------------------------------------------------------
	private String GetHostNameFromBuffer(String strBuffer)
	{
		String strHostName = "";
		try
		{
			if ( strBuffer.indexOf("(") >= 0 )
				strHostName = strBuffer.substring(strBuffer.indexOf("Nmap scan report for ") + 21, strBuffer.indexOf("(")).trim();
			else
				strHostName = "";
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strHostName;
	}

	// --------------------------------------------------------------------------------
	// Name: GetIPAddressFromBuffer
	// Abstract: Gets the IP address from the buffer
	// --------------------------------------------------------------------------------
	private String GetIPAddressFromBuffer(String strBuffer)
	{
		String strIPAddress = "";
		try
		{
			if ( strBuffer.indexOf("(") >= 0 )
				strIPAddress = strBuffer.substring(strBuffer.indexOf("(") + 1, strBuffer.indexOf(")"));
			else
				strIPAddress = strBuffer.substring(strBuffer.indexOf("Nmap scan report for ") + 21).trim();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strIPAddress;
	}

	// --------------------------------------------------------------------------------
	// Name: GetMACAddressFromBuffer
	// Abstract: Gets the MAC address from the buffer
	// --------------------------------------------------------------------------------
	private String GetMACAddressFromBuffer(String strBuffer)
	{
		String strMACAddress = "";
		try
		{
			if ( strBuffer.indexOf("(") >= 0 )
				strMACAddress = strBuffer.substring(strBuffer.indexOf("MAC Address") + 13, strBuffer.indexOf("("));
			else
				strMACAddress = strBuffer.substring(strBuffer.indexOf("MAC Address") + 13).trim();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strMACAddress;
	}

	// --------------------------------------------------------------------------------
	// Name: GetManufacturerFromBuffer
	// Abstract: Gets the manufacturer from the buffer
	// --------------------------------------------------------------------------------
	private String GetManufacturerFromBuffer(String strBuffer)
	{
		String strManufacturer = "";
		try
		{
			if ( strBuffer.indexOf("(") >= 0 )
				strManufacturer = strBuffer.substring(strBuffer.indexOf("(") + 1, strBuffer.indexOf(")"));
			else
				strManufacturer = "";
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strManufacturer;
	}

	// --------------------------------------------------------------------------------
	// Name: AdjustDeviceType
	// Abstract: Allows programmers to set the device type based on manufacturer or 
	//			 host name.
	// --------------------------------------------------------------------------------
	private String AdjustDeviceType( String strDeviceType, String strManufacturer, String strHostName )
	{
		String strNewDeviceType = strDeviceType;
		try
		{
			if ( strDeviceType.equals("") == true )
			{
				// Try to guess the device type by MAC address
				if ( strManufacturer.contains("Apple") == true )
					strDeviceType = "Apple device";
				
				if ( strHostName.indexOf("android_") == 0 )
					strDeviceType = "Android phone";
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strNewDeviceType;
	}

	// --------------------------------------------------------------------------------
	// Name: GetDeviceTypeFromBuffer
	// Abstract: Retrieves the device type from the buffer
	// --------------------------------------------------------------------------------
	private String GetDeviceTypeFromBuffer( String strBuffer, BufferedReader brOutput )
	{
		String strDeviceType = "";
		try
		{
			if ( strBuffer.indexOf("general purpose") >= 0 )
			{
				strBuffer = brOutput.readLine();
				if ( strBuffer.indexOf("Microsoft Windows") >= 0 )
					strDeviceType = "Windows";
				else if ( strBuffer.indexOf("Linux") >= 0 )
					strDeviceType = "Linux";
			}
			else if ( strBuffer.indexOf("bridge") >= 0 || strBuffer.indexOf("broadband router") >= 0 || strBuffer.indexOf("hub") >= 0 ||
					  strBuffer.indexOf("router") >= 0 || strBuffer.indexOf("switch") >= 0 || strBuffer.indexOf("WAP") >= 0 )
				strDeviceType = "Router";

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strDeviceType;
	}

    // ----------------------------------------------------------------------------
    // Name: miNikto_Click
    // Abstract: Opens a new Nikto window with the selected IP address targeted
    // ----------------------------------------------------------------------------
	private void miNikto_Click( )
	{
		try
		{
			String strSelectedIP = String.valueOf(m_tblSearchResults.GetCellValue(m_tblSearchResults.GetSelectedRow(), "IP Address"));
			FOtherToolsNikto frmNikto = new FOtherToolsNikto( );
			frmNikto.AddHost( strSelectedIP );
			frmNikto.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // ----------------------------------------------------------------------------
    // Name: SaveNmapOutputToFile
    // Abstract: Saves the Nmap output to a location of the user's choosing (if
	//			 there's any output to display)
    // ----------------------------------------------------------------------------
	private void SaveNmapOutputToFile( )
	{
		try
		{
			if ( m_strNmapOutput.equals("") == false )
			{
				String strOutputLocation = CAircrackUtilities.DisplayFileChooser(this, false, ".txt", "Text files (*.txt)");
				if ( strOutputLocation.equals("") == false )
					if ( CAircrackUtilities.SaveTextToFile(m_strNmapOutput, strOutputLocation) == true )
						JOptionPane.showMessageDialog(null, "File successfully saved.");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No Nmap output to save to the disk.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
    // ----------------------------------------------------------------------------
    // Name: SaveTableViewToCSVFile
    // Abstract: Saves the contents of the table view to a CSV file
    // ----------------------------------------------------------------------------
	private void SaveTableViewToCSVFile( )
	{
		try
		{
			String strContents = "";
			String strRowContents = "";
			
			// Add the columns to the CSV file
			for ( int intIndex = 0; intIndex < m_tblSearchResults.GetColumnCount( ); intIndex += 1 )
			{
				if ( strRowContents.equals("") == false )
					strRowContents += ",";
				strRowContents += "\"" + m_tblSearchResults.GetColumnName( intIndex ) + "\"";
			}
			strContents = strRowContents + "\n";
			
			// Add the rows to the CSV file
			strRowContents = "";
			for ( int intIndex = 0; intIndex < m_tblSearchResults.GetRowCount( ); intIndex += 1 )
			{
				for ( int intIndex2 = 0; intIndex2 < m_tblSearchResults.GetColumnCount( ); intIndex += 1 )
				{
					if ( strRowContents.equals("") == false )
						strRowContents += ",";
					strRowContents += "\"" + m_tblSearchResults.GetCellValue(intIndex, intIndex2) + "\"";
				}
				strContents += strRowContents + "\n";
				strRowContents = "";
			}
			
			// Open the save file dialog
			String strDestinationFile = CAircrackUtilities.DisplayFileChooser(this, false, ".csv", "Comma-separated values file (*.csv)");
			if ( strDestinationFile.equals("") == false )
			{
				if ( CAircrackUtilities.SaveTextToFile(strContents, strDestinationFile) == true )
				{
					JOptionPane.showMessageDialog(null, "File successfully saved.");
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// ----------------------------------------------------------------------------
    // Name: SaveTableViewToHTMLFile
    // Abstract: Saves the contents of the table view to an HTML file
    // ----------------------------------------------------------------------------
	private void SaveTableViewToHTMLFile( )
	{
		try
		{
			String strContents = "";
			String strRowContents = "";
			
			strContents = "<table border=\"1\">";
			strContents += "<tr>";
			
			// Add the columns to the CSV file
			for ( int intIndex = 0; intIndex < m_tblSearchResults.GetColumnCount( ); intIndex += 1 )
				strRowContents += "<td>" + m_tblSearchResults.GetColumnName( intIndex ) + "</td>";
			strContents += strRowContents + "</tr>";
			
			// Add the rows to the CSV file
			strRowContents = "";
			for ( int intIndex = 0; intIndex < m_tblSearchResults.GetRowCount( ); intIndex += 1 )
			{
				strRowContents = "<tr>";
				for ( int intIndex2 = 0; intIndex2 < m_tblSearchResults.GetColumnCount( ); intIndex += 1 )
					strRowContents += "<td>" + m_tblSearchResults.GetCellValue(intIndex, intIndex2) + "</td>";
				strRowContents += "</tr>";
				strContents += strRowContents;
				strRowContents = "";
			}
			strContents += "</table>";
			
			// Open the save file dialog
			String strDestinationFile = CAircrackUtilities.DisplayFileChooser(this, false, ".html", "HyperText Markup Language (HTML) file (*.html)");
			if ( strDestinationFile.equals("") == false )
			{
				if ( CAircrackUtilities.SaveTextToFile(strContents, strDestinationFile) == true )
				{
					JOptionPane.showMessageDialog(null, "File successfully saved.");
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
    // ----------------------------------------------------------------------------
    // Name: SetDestination
    // Abstract: Allows an outside form to set the destination
    // ----------------------------------------------------------------------------
	public void SetDestination(String strDestination)
	{
		try
		{
			m_strPassedInDestination  = strDestination;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
    // ----------------------------------------------------------------------------
    // Name: windowOpened
    // Abstract: Called when the window is displayed
    // ----------------------------------------------------------------------------
	public void windowOpened( WindowEvent weSource )
	{
		super.windowOpened( weSource );
		try
		{
			if ( m_strPassedInDestination.equals("") == false )
			{
				m_rdbScanCustomTarget.setSelected( true );
				m_txtScanCustomTarget.setText( m_strPassedInDestination );
				m_txtScanCustomTarget.setEnabled( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// ----------------------------------------------------------------------------
    // Name: mouseClicked
    // Abstract: Mouse click event
    // ----------------------------------------------------------------------------
	public void mouseClicked(MouseEvent meSource)
	{
		try
		{
			if ( meSource.getSource( ) == m_ddbTestDropdown ) m_ddbTestDropdown.DisplayPopupMenu( meSource );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// ----------------------------------------------------------------------------
    // Name: LoadSelectedProfile
    // Abstract: Loads the selected profile into the form. Ensures that the textbox
	//			 is available if a custom target is selected
    // ----------------------------------------------------------------------------
	public void LoadSelectedProfile( )
	{
		super.LoadSelectedProfile( );
		try
		{
			m_txtScanCustomTarget.setEnabled(this.m_rdbScanCustomTarget.isSelected());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
