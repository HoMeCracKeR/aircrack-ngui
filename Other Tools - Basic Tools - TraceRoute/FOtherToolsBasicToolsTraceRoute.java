// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsTraceRoute
// Abstract: Graphical interface for trace route.
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import java.awt.event.*;

public class FOtherToolsBasicToolsTraceRoute extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtTarget = null;
	
	private JCheckBox m_chkSocketLevelDebugging = null; // -d
	private JCheckBox m_chkDontFragmentProbePackets = null; // -F
	private JCheckBox m_chkDontMapIPAddresses = null; // -n
	private JCheckBox m_chkBypassNormalRoutingTables = null; // -r
	private JCheckBox m_chkShowICMPExtensions = null; // -e
	private JCheckBox m_chkPerformASPathLookups = null; // -A
	
	private JCheckBox m_chkForceIPVersion = null; // -4 or -6
	private CComboBox m_cboForceIPVersion = null;
	private JCheckBox m_chkSpecifyProbeType = null; // -I or -T (ICMP or TCP SYN)
	private CComboBox m_cboSpecifyProbeType = null;
	
	private JCheckBox m_chkSetFirstTimeToLive = null; // -f
	private CTextBox m_txtSetFirstTimeToLive = null; // Default 1
	private JCheckBox m_chkSetGateway = null; // -g
	private CTextBox m_txtSetGateway = null;
	private JCheckBox m_chkSetInterface = null; // -i
	private CComboBox m_cboSetInterface = null;
	private JCheckBox m_chkSetMaximumHops = null; // -m
	private CTextBox m_txtSetMaximumHops = null; // Default 30
	private JCheckBox m_chkSetSimultaneousProbeCount = null; // -N
	private CTextBox m_txtSetSimultaneousProbeCount = null;
	
	private JCheckBox m_chkSetPort = null; // -p
	private CTextBox m_txtSetPort = null;
	private JCheckBox m_chkSetTypeOfServiceValue = null; // -t
	private CTextBox m_txtSetTypeOfServiceValue = null;
	private JCheckBox m_chkSetWaitTime = null; // -w
	private CTextBox m_txtSetWaitTime = null; // default 5
	private JCheckBox m_chkSetProbePacketsPerHop = null; // -q
	private CTextBox m_txtSetProbePacketsPerHop = null; // default 3
	private JCheckBox m_chkSetSourceAddress = null; // -s
	private CTextBox m_txtSetSourceAddress = null;
	private JCheckBox m_chkSetTimeBetweenProbes = null; // -z
	private CTextBox m_txtSetTimeBetweenProbes = null; // default 0
	
	private JButton m_btnStart = null;
	
	private String m_strPassedInTarget = "";

	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsTraceRoute
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsBasicToolsTraceRoute( )
	{
		super( "Trace Route", 430, 540, false, false, "TraceRoute" );
		try
		{
			AddControls( );
			PopulateIPVersions( );
			PopulateProbeTypes( );
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
			CUtilities.AddLabel(m_cntControlContainer, "Target:", 57, 5);
			m_txtTarget = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 55, 65);
			m_chkSocketLevelDebugging = CUtilities.AddCheckBox(m_cntControlContainer, null, "Socket-level Debugging", 80, 5);
			m_chkDontFragmentProbePackets = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Fragment Probes", 80, 200);
			m_chkDontMapIPAddresses = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Map IP Addresses", 105, 5);
			m_chkBypassNormalRoutingTables = CUtilities.AddCheckBox(m_cntControlContainer, null, "Bypass Routing Tables", 105, 200);
			m_chkShowICMPExtensions = CUtilities.AddCheckBox(m_cntControlContainer, null, "Show ICMP Extensions", 130, 5);
			m_chkPerformASPathLookups = CUtilities.AddCheckBox(m_cntControlContainer, null, "Perform AS Path Lookups", 130, 200);
			m_chkForceIPVersion = CUtilities.AddCheckBox(m_cntControlContainer, null, "Force IP Version:", 155, 5); // -4 or -6
			m_cboForceIPVersion = CUtilities.AddComboBox(m_cntControlContainer, null, 158, 150, 18, 75);
			m_chkSpecifyProbeType = CUtilities.AddCheckBox(m_cntControlContainer, null, "Probe Type:", 180, 5); // -I or -T (ICMP or TCP SYN)
			m_cboSpecifyProbeType = CUtilities.AddComboBox(m_cntControlContainer, null, 183, 115, 18, 100);
			
			m_chkSetFirstTimeToLive = CUtilities.AddCheckBox(m_cntControlContainer, null, "Set First TTL:", 205, 5); // -f
			m_txtSetFirstTimeToLive = CUtilities.AddTextBox(m_cntControlContainer, null, "1", 8, 10, 207, 125); // Default 1
			m_chkSetGateway = CUtilities.AddCheckBox(m_cntControlContainer, null, "Set Gateway:", 230, 5); // -g
			m_txtSetGateway = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 232, 127);
			m_chkSetInterface = CUtilities.AddCheckBox(m_cntControlContainer, null, "Interface:", 255, 5); // -i
			m_cboSetInterface = CUtilities.AddComboBox(m_cntControlContainer, null, 257, 100, 18, 100);
			m_chkSetMaximumHops = CUtilities.AddCheckBox(m_cntControlContainer, null, "Max Hops:", 280, 5); // -m
			m_txtSetMaximumHops = CUtilities.AddTextBox(m_cntControlContainer, null, "30", 8, 10, 282, 105); // Default 30
			m_chkSetSimultaneousProbeCount = CUtilities.AddCheckBox(m_cntControlContainer, null, "Simultaneous Probe Count:", 305, 5); // -N
			m_txtSetSimultaneousProbeCount = CUtilities.AddTextBox(m_cntControlContainer, null, "", 8, 10, 307, 225);
			m_chkSetPort = CUtilities.AddCheckBox(m_cntControlContainer, null, "Port:", 330, 5); // -p
			m_txtSetPort = CUtilities.AddTextBox(m_cntControlContainer, null, "", 8, 10, 332, 65);
			m_chkSetTypeOfServiceValue = CUtilities.AddCheckBox(m_cntControlContainer, null, "ToS Value:", 355, 5); // -t
			m_txtSetTypeOfServiceValue = CUtilities.AddTextBox(m_cntControlContainer, null, "", 8, 10, 357, 105);
			m_chkSetWaitTime = CUtilities.AddCheckBox(m_cntControlContainer, null, "Set Wait Time:", 380, 5); // -w
			m_txtSetWaitTime = CUtilities.AddTextBox(m_cntControlContainer, null, "5", 8, 10, 382, 135); // default 5
			m_chkSetProbePacketsPerHop = CUtilities.AddCheckBox(m_cntControlContainer, null, "Probe Packets Per Hop:", 405, 5); // -q
			m_txtSetProbePacketsPerHop = CUtilities.AddTextBox(m_cntControlContainer, null, "3", 8, 10, 407, 200); // default 3
			m_chkSetSourceAddress = CUtilities.AddCheckBox(m_cntControlContainer, null, "Source Address:", 430, 5); // -s
			m_txtSetSourceAddress = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 432, 150);
			m_chkSetTimeBetweenProbes = CUtilities.AddCheckBox(m_cntControlContainer, null, "Time Between Probes:", 455, 5); // -z
			m_txtSetTimeBetweenProbes = CUtilities.AddTextBox(m_cntControlContainer, null, "0", 8, 10, 457, 190); // default 0

			m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 485, getWidth() / 2 - 150 / 2, 18, 150);
			
			m_lstParameters.add(new CProfileParameter("Target", m_txtTarget));
			m_lstParameters.add(new CProfileParameter("SocketLevelDebugging", m_chkSocketLevelDebugging));
			m_lstParameters.add(new CProfileParameter("DontFragmentProbePackets", m_chkDontFragmentProbePackets));
			m_lstParameters.add(new CProfileParameter("DontMapIPAddresses", m_chkDontMapIPAddresses));
			m_lstParameters.add(new CProfileParameter("BypassNormalRoutingTables", m_chkBypassNormalRoutingTables));
			m_lstParameters.add(new CProfileParameter("ShowICMPExtensions", m_chkShowICMPExtensions));
			m_lstParameters.add(new CProfileParameter("PerformASPathLookups", m_chkPerformASPathLookups));
			m_lstParameters.add(new CProfileParameter("ForceIPVersion", m_chkForceIPVersion, m_cboForceIPVersion));
			m_lstParameters.add(new CProfileParameter("ProbeType", m_chkSpecifyProbeType, m_cboSpecifyProbeType));
			m_lstParameters.add(new CProfileParameter("FirstTimeToLive", m_chkSetFirstTimeToLive, m_txtSetFirstTimeToLive));
			m_lstParameters.add(new CProfileParameter("Gateway", m_chkSetGateway, m_txtSetGateway));
			m_lstParameters.add(new CProfileParameter("Interface", m_chkSetInterface, m_cboSetInterface));
			m_lstParameters.add(new CProfileParameter("MaximumHops", m_chkSetMaximumHops, m_txtSetMaximumHops));
			m_lstParameters.add(new CProfileParameter("SimultaneousProbeCount", m_chkSetSimultaneousProbeCount, m_txtSetSimultaneousProbeCount));
			m_lstParameters.add(new CProfileParameter("Port", m_chkSetPort, m_txtSetPort));
			m_lstParameters.add(new CProfileParameter("TypeOfService", m_chkSetTypeOfServiceValue, m_txtSetTypeOfServiceValue));
			m_lstParameters.add(new CProfileParameter("WaitTime", m_chkSetWaitTime, m_txtSetWaitTime));
			m_lstParameters.add(new CProfileParameter("ProbePacketsPerHop", m_chkSetProbePacketsPerHop, m_txtSetProbePacketsPerHop));
			m_lstParameters.add(new CProfileParameter("SourceAddress", m_chkSetSourceAddress, m_txtSetSourceAddress));
			m_lstParameters.add(new CProfileParameter("TimeBetweenProbes", m_chkSetTimeBetweenProbes, m_txtSetTimeBetweenProbes));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateIPVersions
	// Abstract: Adds IPv4 and IPv6 options to the dropdown
	// --------------------------------------------------------------------------------
	private void PopulateIPVersions( )
	{
		try
		{
			m_cboForceIPVersion.AddItemToList("IPv4", 4);
			m_cboForceIPVersion.AddItemToList("IPv6", 6);
			m_cboForceIPVersion.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateProbeTypes
	// Abstract: Adds the ICMP and TCP SYN probe types
	// --------------------------------------------------------------------------------
	private void PopulateProbeTypes( )
	{
		try
		{
			m_cboSpecifyProbeType.AddItemToList("ICMP", (int)'I');
			m_cboSpecifyProbeType.AddItemToList("TCP SYN", (int)'T');
			m_cboSpecifyProbeType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Adds the available interfaces to run traceroute on
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsNetworkInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
			for ( int intIndex = 0; intIndex < aclsNetworkInterfaces.length; intIndex += 1 )
			{
				m_cboSetInterface.AddItemToList(aclsNetworkInterfaces[intIndex].GetInterface(), 0);
			}
			m_cboSetInterface.SetSelectedIndex( 0 );
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
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnStart ) btnStart_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Builds the traceroute command and launches a results window
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			String astrCommand[] = BuildCommand( );
			FOtherToolsBasicToolsTraceRouteOutput frmOutput = new FOtherToolsBasicToolsTraceRouteOutput( );
			frmOutput.SetCommand(astrCommand);
			frmOutput.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Builds the traceroute command and launches a results window
	// --------------------------------------------------------------------------------
	private String[] BuildCommand( )
	{
		String astrCommand[] = null;
		try
		{
			astrCommand = new String[] {"traceroute"};
			
			// Checkbox-only options
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSocketLevelDebugging, "d", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDontFragmentProbePackets, "F", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDontMapIPAddresses, "n", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkBypassNormalRoutingTables, "r", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowICMPExtensions, "e", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPerformASPathLookups, "A", "", astrCommand);

			// Checkbox and other field options
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkForceIPVersion, String.valueOf(m_cboForceIPVersion.GetSelectedItemValue()), "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyProbeType, String.valueOf((char)m_cboSpecifyProbeType.GetSelectedItemValue()), "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetFirstTimeToLive, "f", m_txtSetFirstTimeToLive.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetGateway, "g", m_txtSetGateway.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetInterface, "i", m_cboSetInterface.GetSelectedItemName(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetMaximumHops, "m", m_txtSetMaximumHops.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetSimultaneousProbeCount, "N", m_txtSetSimultaneousProbeCount.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetPort, "p", m_txtSetPort.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetTypeOfServiceValue, "t", m_txtSetTypeOfServiceValue.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetWaitTime, "w", m_txtSetWaitTime.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetProbePacketsPerHop, "q", m_txtSetProbePacketsPerHop.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetSourceAddress, "s", m_txtSetSourceAddress.getText().trim(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetTimeBetweenProbes, "z", m_txtSetTimeBetweenProbes.getText().trim(), astrCommand);
			
			astrCommand = CAircrackUtilities.AddArgumentToArray(m_txtTarget.getText().trim(), astrCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetDestination
	// Abstract: allows other forms to set the destination 
	// --------------------------------------------------------------------------------
	public void SetDestination(String strDestination)
	{
		try
		{
			m_strPassedInTarget = strDestination;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	@Override
	public void windowOpened( WindowEvent weSource )
	{
		super.windowOpened( weSource );
		m_txtTarget.setText( m_strPassedInTarget );
	}
}
