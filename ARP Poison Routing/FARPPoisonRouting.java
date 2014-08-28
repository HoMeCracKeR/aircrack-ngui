// --------------------------------------------------------------------------------
// Name: FARPPoisonRouting
// Abstract: Screen to build man-in-the-middle attacks
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class FARPPoisonRouting extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------
	// Name: CUpdateLabels
	// Abstract: A timer task that updates how long the man in the middle attack has
	//			 been running.
	// --------------------------------------------------------------------------------
	class UpdateLabels extends TimerTask
	{

		@Override
		public void run()
		{
			try
			{

				// Got to make sure the attack has started already
				if ( m_blnIsSpoofing == true )
				{
					BufferedReader brOutputOfRouterSpoof = m_atkARPPoisoningAttack.GetProcessOutputBuffer("router");
					BufferedReader brOutputOfTargetSpoof = m_atkARPPoisoningAttack.GetProcessOutputBuffer("target");
	
					// Read from the router spoof stream
					String strBuffer = brOutputOfRouterSpoof.readLine( );
					if ( strBuffer != null )
					{
						// Check to see if the router is being spoofed
						if ( strBuffer.indexOf("arp reply " + m_txtTargetLocation.getText( ) ) >= 0 )
						{
							m_lblLastRouterSpoof.setText( "Last Router Spoof: " + m_dtfDateFormatter.format( new Date( ) ) );
						}
					}
					
					// Read from the target spoof stream
					strBuffer = brOutputOfTargetSpoof.readLine( );
					if ( strBuffer != null )
					{
						// Check to see if the target is being spoofed
						if ( strBuffer.indexOf("arp reply " + m_txtRouterLocation.getText( ) ) >= 0 )
						{
							m_lblLastTargetSpoof.setText( "Last Target Spoof: " + m_dtfDateFormatter.format( new Date( ) ) );
						}
					}
	
					// Schedule another update 1 second later
					m_timUpdateScreen.schedule( new UpdateLabels(), 1000 );

				}
				
			}
			catch (Exception excError)
			{
				
				CUtilities.WriteLog(excError);
				
			}
		}
		
	}
	
	// Controls
	private JLabel m_lblARPSpoofVersion = null;
	private JCheckBox m_chkIPForwardingEnabled = null;
	private JButton m_btnCheckIPTables = null;
	private CComboBox m_cboInterfaces = null;
	private CNetworkInterface m_aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
	private CTextBox m_txtRouterLocation = null;
	private JButton m_btnAutodetectRouter = null;
	private CTextBox m_txtTargetLocation = null;
	private CComboBox m_cboDetectedTargets = null;
	private JButton m_btnScanForTargets = null;
	private String m_astrTargetIPAddresses[] = null;
	private JButton m_btnCopyTargetToTextBox = null;
	private JButton m_btnStartSpoofing = null;
	private JLabel m_lblStartTime = null;
	private JLabel m_lblLastRouterSpoof = null;
	private JLabel m_lblLastTargetSpoof = null;
	private JLabel m_lblStopTime = null;
	private CARPPoisoningAttack m_atkARPPoisoningAttack = null;
	private DateFormat m_dtfDateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private java.util.Timer m_timUpdateScreen = null;
	private boolean m_blnIsSpoofing = false;
	
	// --------------------------------------------------------------------------------
	// Name: FARPPoisonRouting
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FARPPoisonRouting( )
	{
		super("ARP Poison Routing", 350, 265, false, false, "");
		try
        {
            AddControls( );
            UpdateLabelWithVersionInformation( );
            UpdateIPForwardingCheckBox( );
            PopulateInterfaces( );
        }
        catch (Exception excError)
        {
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
            
            m_lblARPSpoofVersion = CUtilities.AddLabel(conControlArea, "ARP Spoof", 10, 5);
            m_btnCheckIPTables = CUtilities.AddButton(conControlArea, this, "Check IP Tables", 10, 185, 18, 150);
            m_chkIPForwardingEnabled = CUtilities.AddCheckBox(conControlArea, this, "IP Forwarding Enabled", 30, 5);
            CUtilities.AddLabel(conControlArea, "Interfaces:", 62, 5);
            m_cboInterfaces = CUtilities.AddComboBox(conControlArea, null, 60, 90, 18, 150);
            CUtilities.AddLabel(conControlArea, "Router Location:", 91, 5);
        	m_txtRouterLocation = CUtilities.AddTextBox(conControlArea, null, "", 10, 15, 90, 130);
        	m_btnAutodetectRouter = CUtilities.AddButton(conControlArea, this, "Detect", 90, 250, 18, 85);
        	CUtilities.AddLabel(conControlArea, "Target Location:", 121, 5);
        	m_txtTargetLocation = CUtilities.AddTextBox(conControlArea, null, "", 16, 15, 120, 130);
        	m_cboDetectedTargets = CUtilities.AddComboBox(conControlArea, null, 140, 130, 18, 175);
        	m_btnScanForTargets = CUtilities.AddButton(conControlArea, this, "Scan for Targets", 160, 130, 18, 175);
        	m_btnCopyTargetToTextBox = CUtilities.AddButton(conControlArea, this, "Copy IP to Target", 180, 130, 18, 175);
        	m_btnStartSpoofing = CUtilities.AddButton(conControlArea, this, "Start", 210, 100, 18, 150);
        	
        	m_lblStartTime = CUtilities.AddLabel(conControlArea, "Start Time: Not started", 240, 5);
        	m_lblLastRouterSpoof = CUtilities.AddLabel(conControlArea, "Last Router Spoof: Not spoofed", 260, 5);
        	m_lblLastTargetSpoof = CUtilities.AddLabel(conControlArea, "Last Target Spoof: Not spoofed", 280, 5);
        	m_lblStopTime = CUtilities.AddLabel(conControlArea, "Stop Time: Not stopped", 300, 5);
        	
        	m_cboDetectedTargets.GetJavaComboBox( ).setEditable( false );
        	m_cboDetectedTargets.SetEnabled( false );
        	m_btnCopyTargetToTextBox.setEnabled( false );
        	m_cboDetectedTargets.SetSorted( false );
        	
        	m_timUpdateScreen = new java.util.Timer(); 
   		}
   		catch (Exception excError)
   		{
   			CUtilities.WriteLog(excError);
   		}
	}

	// --------------------------------------------------------------------------------
	// Name: UpdateLabelWithVersionInformation
	// Abstract: Displays the arpspoof version information in a label
	// --------------------------------------------------------------------------------
	private void UpdateLabelWithVersionInformation( )
	{
		try
		{
			CProcess clsVersion = new CProcess(new String[] {"arpspoof"}, true, true, false); 
			BufferedReader brOutput = new BufferedReader( clsVersion.GetOutput( ) );
			String strOutput = brOutput.readLine();
			strOutput = strOutput.replaceAll("Version: ", "");
			m_lblARPSpoofVersion.setText("ARP Spoof v" + strOutput);
			clsVersion.CloseProcess();
		}
		catch (Exception excError)
		{
			JOptionPane.showMessageDialog(null, excError.getMessage());
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: UpdateIPForwardingCheckBox
	// Abstract: If IP forwarding is enabled, the checkbox is checked. And vice versa.
	// --------------------------------------------------------------------------------
	private void UpdateIPForwardingCheckBox( )
	{
		try
		{
			String astrCommand[] = new String[] {"cat", "/proc/sys/net/ipv4/ip_forward"};
			CProcess clsIPForwarding = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsIPForwarding.GetOutput( ) );
			String strOutput = brOutput.readLine( );
			
			if ( strOutput.equals("0") == true )
				m_chkIPForwardingEnabled.setSelected(false);
			else if ( strOutput.equals("1") == true )
				m_chkIPForwardingEnabled.setSelected(true);
			
			clsIPForwarding.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the interface dropdown with available interfaces to do the
	//			 ARP spoof attack with.
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			for ( int intIndex = 0; intIndex < m_aclsInterfaces.length; intIndex += 1 )
				m_cboInterfaces.AddItemToList(m_aclsInterfaces[intIndex].GetInterface( ), intIndex);
			m_cboInterfaces.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: When a button or checkbox is clicked
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_btnAutodetectRouter ) btnAutodetectRouter_Click( );
			else if ( aeSource.getSource( ) == m_btnScanForTargets ) btnScanForTargets_Click( );
			else if ( aeSource.getSource( ) == m_btnCopyTargetToTextBox ) btnCopyTargetToTextBox_Click( );
			else if ( aeSource.getSource( ) == m_btnStartSpoofing ) btnStartSpoofing_Click( );
			else if ( aeSource.getSource( ) == m_chkIPForwardingEnabled ) chkIPForwardingEnabled_Checked( );
			else if ( aeSource.getSource( ) == m_btnCheckIPTables ) btnCheckIPTables_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnAutodetectRouter_Click
	// Abstract: Detects the default gateway on the specified device and populates it
	//			 in the textbox
	// --------------------------------------------------------------------------------
	private void btnAutodetectRouter_Click( )
	{
		try
		{
			String astrCommand[] = new String[] {"route", "-n"};
			CProcess clsInterfaceInformation = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsInterfaceInformation.GetOutput( ) );
			boolean blnAccessPointIPFound = false;
			String strOutput = brOutput.readLine( );
			String strInterface = "";
			
			while ( strOutput != null && blnAccessPointIPFound == false )
			{
				// The line starts out with 0.0.0.0 for specifying the gateway
				if ( strOutput.substring(0, 7).equals("0.0.0.0") == true )
				{
					// Make sure it's the default gateway for the selected interface
					strInterface = strOutput.substring(strOutput.lastIndexOf(' ') + 1);
					if ( strInterface.equals(m_cboInterfaces.GetSelectedItem().GetName()) == true )
					{
						strOutput = strOutput.substring(strOutput.indexOf(' ')).trim();
						strOutput = strOutput.substring(0, strOutput.indexOf(' ')).trim();
						blnAccessPointIPFound = true;
						m_txtRouterLocation.setText(strOutput);
					}
				}
				strOutput = brOutput.readLine( );
			}
			
			clsInterfaceInformation.CloseProcess( );
			
			if ( blnAccessPointIPFound == false )
				JOptionPane.showMessageDialog(null, "Could not find default gateway IP address for interface " + m_cboInterfaces.GetSelectedItem().GetName());
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnScanForTargets_Click
	// Abstract: Runs an nmap scan for possible targets of the ARP spoof
	// --------------------------------------------------------------------------------
	private void btnScanForTargets_Click( )
	{
		try
		{
			String strNetworkScanTarget = CalculateNetworkScanTarget( m_cboInterfaces.GetSelectedItem( ).GetName( ) );
			if ( strNetworkScanTarget.equals("") ) return;
			String astrCommand[] = new String[] {"nmap", "-sn", strNetworkScanTarget}; 
			CProcess clsSearch = new CProcess(astrCommand, true, true, false);
			BufferedReader brResults = new BufferedReader( clsSearch.GetOutput( ) );
			brResults.readLine( ); // first line is blank line
			brResults.readLine( ); // second line is Nmap version information
			String strBuffer = brResults.readLine( ); // Now we're onto hosts
			String strHostIPAddress = "";
			
			while ( strBuffer != null )
			{
				strBuffer = strBuffer.replaceAll("Nmap scan report for ", "");
				// Make sure it's not the indicator of the host being up
				if ( strBuffer.indexOf("Host is up") < 0 && strBuffer.indexOf("MAC Address") < 0 )
				{
					// Make sure it's not the recap of the number of hosts scanned
					if ( strBuffer.indexOf( "Nmap done:" ) < 0 )
					{
						// strHostName = strBuffer.substring(0, strBuffer.indexOf(" ")).replaceAll(".home", "");
						strHostIPAddress = strBuffer;
						// strHostIPAddress = strBuffer.substring(strBuffer.indexOf("(") + 1, strBuffer.lastIndexOf(")")).trim();
						m_astrTargetIPAddresses = CAircrackUtilities.AddArgumentToArray(strHostIPAddress, m_astrTargetIPAddresses);
						m_cboDetectedTargets.AddItemToList(strHostIPAddress, m_astrTargetIPAddresses.length - 1);
					}
				}
				else if ( strBuffer.indexOf("MAC Address") >= 0 )
				{
					// Get the last edited item
					int intIndex = m_cboDetectedTargets.GetJavaComboBox().getItemCount() - 1;
					strBuffer = strBuffer.replaceAll("MAC Address: ", "");
					String strManufacturer = strBuffer.substring(strBuffer.indexOf("(") + 1, strBuffer.lastIndexOf(")"));
					m_cboDetectedTargets.GetItem(intIndex).SetName(m_cboDetectedTargets.GetItem(intIndex).GetName() + " (" + strManufacturer + ")");
				}
				
				strBuffer = brResults.readLine( );
			}
			
			// Any targets found?
			if ( m_astrTargetIPAddresses.length == 0 )
			{
				// Nope, let the user know
				JOptionPane.showMessageDialog(null, "Aircrack-NGUI couldn't detect any targets");
			}
			else
			{
				// Yup, select the first target, enable the copy button, and enable the copy target
				// to textbox button
				m_cboDetectedTargets.SetSelectedIndex( 0 );
				m_cboDetectedTargets.SetEnabled( true );
	        	m_btnCopyTargetToTextBox.setEnabled( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: CalculateNetworkScanTarget
	// Abstract: Calculates the target for the nmap scan (ex. 192.168.0.0/24)
	// --------------------------------------------------------------------------------
	private String CalculateNetworkScanTarget( String strInterfaceName )
	{
		String strNetworkScanTarget = "";
		
		try
		{
			CNetworkInterface clsInterface = new CNetworkInterface( strInterfaceName );
			String strSubnetMask = clsInterface.GetSubnetMask( );
			String strIPAddress = clsInterface.GetIPAddress( );
			
			m_astrTargetIPAddresses = new String[ 0 ];
			m_cboDetectedTargets.Clear( );
			
			// Class A networks
			if ( strSubnetMask.equals( "255.0.0.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.indexOf(".") + 1) + "0.0.0/8";
			}
			// Class B networks
			else if ( strSubnetMask.equals( "255.255.0.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.lastIndexOf(".", strIPAddress.lastIndexOf(".") - 1) + 1) + "0.0/16";
			}
			// Class C networks
			else if ( strSubnetMask.equals( "255.255.255.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.lastIndexOf(".") + 1) + "0/24";
			}
			// Custom or undeterminable subnet
			else
			{
				JOptionPane.showMessageDialog(null, "Aircrack-NGUI only supports scanning targets in Class A, B, and C networks (subnet mask 255.0.0.0, 255.255.0.0, or 255.255.255.0)");
			}

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		
		return strNetworkScanTarget;
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnCopyTargetToTextBox_Click
	// Abstract: Copies the selected target on the dropdown into the target textbox
	// --------------------------------------------------------------------------------
	private void btnCopyTargetToTextBox_Click( )
	{
		try
		{
			if ( m_cboDetectedTargets.GetSelectedIndex( ) >= 0 )
			{
				m_txtTargetLocation.setText(m_astrTargetIPAddresses[ m_cboDetectedTargets.GetSelectedItem( ).GetValue( ) ] );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStartSpoofing_Click
	// Abstract: Starts or stops the ARP spoof attack
	// --------------------------------------------------------------------------------
	private void btnStartSpoofing_Click( )
	{
		try
		{
			
			if ( m_btnStartSpoofing.getText( ).equals("Start") )
				StartARPSpoofAttack( );
			else if ( m_btnStartSpoofing.getText( ).equals("Stop") )
				StopARPSpoofAttack( );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: StartARPSpoofAttack
	// Abstract: Starts the ARP spoof attack
	// --------------------------------------------------------------------------------
	private void StartARPSpoofAttack( )
	{
		try
		{

			boolean blnValidated = true;
			String strErrors = "";
			
			if ( m_txtRouterLocation.getText( ).equals( "" ) )
			{
				strErrors += "Router location cannot be blank.\n";
				blnValidated = false;
			}
			
			if ( m_txtTargetLocation.getText( ).equals( "" ) )
			{
				strErrors += "Target location cannot be blank.\n";
				blnValidated = false;
			}
			
			if ( blnValidated == false )
			{
				JOptionPane.showMessageDialog(null, strErrors);
			}
			else
			{
				setSize(getWidth(), 350);

				// Attack the router first
				String strInterface = m_cboInterfaces.GetSelectedItem( ).GetName( );
				String strTarget = m_txtRouterLocation.getText( );
				String strHost = m_txtTargetLocation.getText( );
				
				m_atkARPPoisoningAttack = new CARPPoisoningAttack();
				m_atkARPPoisoningAttack.SetInterface(strInterface);
				m_atkARPPoisoningAttack.SetRouter(strHost);
				m_atkARPPoisoningAttack.SetTarget(strTarget);
				m_atkARPPoisoningAttack.Attack();
				
				m_lblStartTime.setText("Start Time: " + m_dtfDateFormatter.format(new Date()));
				m_lblStopTime.setText("Stop Time: Not stopped");
				
				m_timUpdateScreen.schedule( new UpdateLabels(), 1000 );
				
				m_btnStartSpoofing.setText("Stop");
		
				m_blnIsSpoofing = true;
				
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: StopARPSpoofAttack
	// Abstract: Stops the ARP spoof attack
	// --------------------------------------------------------------------------------
	private void StopARPSpoofAttack( )
	{
		try
		{
			m_lblStopTime.setText( "Stop Time: " + m_dtfDateFormatter.format( new Date( ) ) );
			
			m_atkARPPoisoningAttack.StopAttack();
			
			m_btnStartSpoofing.setText("Start");
			
			m_blnIsSpoofing = false;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: chkIPForwardingEnabled_Checked
	// Abstract: Enables (or disables) IP forwarding based on a checkbox click
	// --------------------------------------------------------------------------------
	private void chkIPForwardingEnabled_Checked( )
	{
		
		try
		{
			
			int intWrite = 0;
			
			if ( m_chkIPForwardingEnabled.isSelected( ) )
				intWrite = 1;
			else
				intWrite = 0;
			
			String astrCommand[] = new String[] { "RequiredScripts/ToggleIPForward.sh", String.valueOf(intWrite) };
			CProcess clsChangeIPForward = new CProcess(astrCommand, false, true, true);
			clsChangeIPForward.CloseProcess();
			
			UpdateIPForwardingCheckBox( );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnCheckIPTables_Click
	// Abstract: Tries to detect if packets can travel between two interfaces using
	//			 iptables.
	// --------------------------------------------------------------------------------
	private void btnCheckIPTables_Click( )
	{
		try
		{
			
			String strPolicy = "";
			boolean blnRejectingAll = false;
			boolean blnAcceptingAll = true;
			boolean blnHasOtherParameters = false;
			String astrCommand[] = new String[] {"iptables", "-L", "FORWARD"};
			CProcess clsIPTables = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsIPTables.GetOutput( ) );
			
			// Has policy information
			String strHeaderInformation = brOutput.readLine( );
			brOutput.readLine( ); // column headers
			String strBuffer = brOutput.readLine( );
			
			strPolicy = strHeaderInformation.substring(strHeaderInformation.indexOf("("));
			strPolicy = strPolicy.substring(strPolicy.indexOf(" ") + 1, strPolicy.indexOf(")"));
			
			while ( strBuffer != null )
			{
				if ( strBuffer.indexOf("REJECT     all  --  anywhere             anywhere") >= 0 )
					blnRejectingAll = true;
				else if ( strBuffer.indexOf("DROP       all  --  anywhere             anywhere") >= 0 )
					blnRejectingAll = true;
				else if ( strBuffer.indexOf("ACCEPT     all  --  anywhere             anywhere") >= 0 )
					blnAcceptingAll = true;
				else
					blnHasOtherParameters = true;
					
				strBuffer = brOutput.readLine( );
			}
			
			clsIPTables.CloseProcess();
			
			DisplayPortForwardingLookupResults( blnAcceptingAll, blnRejectingAll, blnHasOtherParameters );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayPortForwardingLookupResults
	// Abstract: Displays if the user can send packets through their interface.
	// --------------------------------------------------------------------------------
	private void DisplayPortForwardingLookupResults( boolean blnAcceptingAll, boolean blnRejectingAll, boolean blnHasOtherParameters )
	{
		try
		{
			boolean blnPortForwardingOkay = false;
			boolean blnPortForwardingWarning = false;
			boolean blnDisplayEditRulesDialog = false;
			String strDisplayMessage = "";
			DViewIPTables dlgViewIPTables = null;
			
			if ( blnAcceptingAll == true && blnRejectingAll == false && blnHasOtherParameters == false )
				blnPortForwardingOkay = true;
			else if ( blnAcceptingAll == false && blnRejectingAll == true && blnHasOtherParameters == false )
				blnPortForwardingOkay = false;
			else if ( blnHasOtherParameters )
				blnPortForwardingWarning = true;
			else if ( blnAcceptingAll == false && blnRejectingAll == false && blnHasOtherParameters == false )
				blnPortForwardingOkay = false;
						
			if ( blnPortForwardingOkay )
				JOptionPane.showMessageDialog(null, "Port forwarding is enabled in the IP tables.");
			else
			{
				if ( blnPortForwardingWarning )
					strDisplayMessage = "Port forwarding might be enabled in the IP tables. There are additional rules that Aircrack-NGUI doesn't calculate.";
				else if ( !blnPortForwardingOkay )
					strDisplayMessage = "Port forwarding is not enabled in the IP tables.";
				strDisplayMessage += " Do you want to view/edit the rules now?";
				blnDisplayEditRulesDialog = !CAircrackUtilities.ConvertIntegerToBoolean(JOptionPane.showConfirmDialog(null, strDisplayMessage, "Aircrack-NGUI", JOptionPane.YES_NO_OPTION));
				if ( blnDisplayEditRulesDialog )
				{
					dlgViewIPTables = new DViewIPTables( );
					dlgViewIPTables.setVisible( true );
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetTargetLocation
	// Abstract: Allows outside forms to set the target of the spoof. Used from Discover
	//			 Hosts.
	// --------------------------------------------------------------------------------
	public void SetTargetLocation(String strTargetLocation)
	{
		try
		{
			m_txtTargetLocation.setText( strTargetLocation );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

}