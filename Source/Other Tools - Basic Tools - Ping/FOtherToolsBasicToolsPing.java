// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsPing
// Abstract: Ping dialog
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FOtherToolsBasicToolsPing extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	// Includes
    CTextBox m_txtDestination = null;
    JLabel m_lblDestination = null;
    JCheckBox m_chkAudiblePing = null; // -a
    JCheckBox m_chkAdaptivePing = null; // -A
    JCheckBox m_chkPingBroadcastAddress = null; // -b
    JCheckBox m_chkDontChangeSourceAddress = null; // -B
    JCheckBox m_chkMarkPackets = null; // -m mark
    CTextBox m_txtMarkPackets = null;
    JCheckBox m_chkPacketCount = null; // -c count
    CTextBox m_txtPacketCount = null;
    JCheckBox m_chkSetFlowLabel = null; // -F flow label
    CTextBox m_txtSetFlowLabel = null;
    JCheckBox m_chkFloodPing = null; // -f
    JCheckBox m_chkSetInterval = null; // -i interval
    CTextBox m_txtSetInterval = null;
    JCheckBox m_chkSetInterfaceAddress = null; // -I interface address
    CTextBox m_txtSetInterfaceAddress = null;
    JCheckBox m_chkSetPreload = null; // -l preload
    CTextBox m_txtSetPreload = null;
    JCheckBox m_chkSuppressLoopback = null; // -L
    JCheckBox m_chkSendICMPv6NIQ = null; // -N nioption
    CTextBox m_txtSendICMPv6NIQ = null;
    JCheckBox m_chkNumericOutput = null; // -n
    JCheckBox m_chkSetPattern = null; // -p
    JCheckBox m_chkPrintTimestamp = null; // -D
    JCheckBox m_chkSetQoSBits = null; // -Q tos
    CTextBox m_txtSetQoSBits = null;
    JCheckBox m_chkQuietOutput = null; // -q
    JCheckBox m_chkRecordRoute = null; // -R
    JCheckBox m_chkBypassRoutingTables = null; // -r
    JCheckBox m_chkSetPacketSize = null; // -s packetsize
    CTextBox m_txtSetPacketSize = null;
    JCheckBox m_chkSetSocketSndbuf = null; // -S sndbuf
    CTextBox m_txtSetSocketSndbuf = null;
    JCheckBox m_chkSetIPTimeToLive = null; // -t ttl
    CTextBox m_txtSetIPTimeToLive = null;
    JCheckBox m_chkSetSpecialIPTimestamp = null; // -T timestamp option
    CTextBox m_txtSetSpecialIPTimestamp = null;
    JCheckBox m_chkSelectPathMTUDiscoveryStrategy = null; // -M hint
    CTextBox m_txtSelectPathMTUDiscoveryStrategy = null;
    JCheckBox m_chkPrintFullUserToUserLatency = null; // -U
    JCheckBox m_chkPrintVerboseOutput = null; // -v
    JCheckBox m_chkOnlyShowVersion = null; // -V
    JCheckBox m_chkSetPingDeadline = null; // -w deadline
    CTextBox m_txtSetPingDeadline = null;
    JCheckBox m_chkWaitForResponseTime = null; // -W timeout
    CTextBox m_txtWaitForResponseTime = null;
    
    JButton m_btnStart = null;
    
    private String m_strPassedInTarget = "";
    
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsPing
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
    public FOtherToolsBasicToolsPing( )
    {
    	super("Ping", 500, 630, false, false, "Ping");
        try
        {
            AddControls( );
            CAircrackUtilities.LoadProfilesIntoComboBox(m_cboSavedProfiles, "Ping");
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
            m_lblDestination = CUtilities.AddLabel( m_cntControlContainer, "Destination:", 50, 5 );
            m_txtDestination = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 50, 150 );
            
            m_chkAudiblePing = CUtilities.AddCheckBox( m_cntControlContainer, this, "Audible Ping", 70, 5 ); // -a
            m_chkAdaptivePing = CUtilities.AddCheckBox( m_cntControlContainer, this, "Adaptive Ping", 70, 215 ); // -A
            m_chkPingBroadcastAddress = CUtilities.AddCheckBox( m_cntControlContainer, this, "Ping Broadcast Address", 90, 5 ); // -b
            m_chkDontChangeSourceAddress = CUtilities.AddCheckBox( m_cntControlContainer, this, "Don't Change Source Address", 90, 215 ); // -B
            m_chkFloodPing = CUtilities.AddCheckBox( m_cntControlContainer, this, "Flood Ping", 110, 5 ); // -f
            m_chkSuppressLoopback = CUtilities.AddCheckBox( m_cntControlContainer, this, "Suppress Loopback", 110, 215 ); // -L
            m_chkNumericOutput = CUtilities.AddCheckBox( m_cntControlContainer, this, "Numeric Output", 130, 5 ); // -n
            m_chkSetPattern = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Pattern", 130, 215 ); // -p
            m_chkPrintTimestamp = CUtilities.AddCheckBox( m_cntControlContainer, this, "Print Timestamp", 150, 5 ); // -D
            m_chkQuietOutput = CUtilities.AddCheckBox( m_cntControlContainer, this, "Quiet Output", 150, 215 ); // -q
            m_chkRecordRoute = CUtilities.AddCheckBox( m_cntControlContainer, this, "Record Route", 170, 5 ); // -R
            m_chkBypassRoutingTables = CUtilities.AddCheckBox( m_cntControlContainer, this, "Bypass Routing Tables", 170, 215 ); // -r
            m_chkOnlyShowVersion = CUtilities.AddCheckBox( m_cntControlContainer, this, "Only Show Version", 190, 5 ); // -V
            m_chkPrintVerboseOutput = CUtilities.AddCheckBox( m_cntControlContainer, this, "Print Verbose Output", 190, 215 ); // -v
            m_chkPrintFullUserToUserLatency = CUtilities.AddCheckBox( m_cntControlContainer, this, "Print Full User-To-User Latency", 210, 5 ); // -U
            
            m_chkMarkPackets = CUtilities.AddCheckBox( m_cntControlContainer, this, "Mark Packets:", 230, 5 ); // -m mark
            m_txtMarkPackets = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 233, 220 );
            
            m_chkPacketCount = CUtilities.AddCheckBox( m_cntControlContainer, this, "Packet Count:", 250, 5 ); // -c count
            m_txtPacketCount = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 253, 220 );
            
            m_chkSetFlowLabel = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Flow Label:", 270, 5 ); // -F flow label
            m_txtSetFlowLabel = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 273, 220 );
            
            m_chkSetInterval = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Interval:", 290, 5 ); // -i interval
            m_txtSetInterval = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 293, 220 );
            
            m_chkSetInterfaceAddress = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Interface Address:", 310, 5 ); // -I interface address
            m_txtSetInterfaceAddress = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 313, 220 );
            
            m_chkSetPreload = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Preload:", 330, 5 ); // -l preload
            m_txtSetPreload = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 333, 220 );
            
            m_chkSendICMPv6NIQ = CUtilities.AddCheckBox( m_cntControlContainer, this, "Send ICMPv6 NIQ:", 350, 5 ); // -N nioption
            m_txtSendICMPv6NIQ = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 353, 220 );
            
            m_chkSetQoSBits = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set QoS Bits:", 370, 5 ); // -Q tos
            m_txtSetQoSBits = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 373, 220 );

            m_chkSetPacketSize = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Packet Size:", 390, 5 ); // -s packetsize
            m_txtSetPacketSize = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 393, 220 );
            
            m_chkSetSocketSndbuf = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Socket Sndbuf:", 410, 5 ); // -S sndbuf
            m_txtSetSocketSndbuf = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 413, 220 );
            
            m_chkSetIPTimeToLive = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set IP TTY:", 430, 5 ); // -t ttl
            m_txtSetIPTimeToLive = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 433, 220 );
            
            m_chkSetSpecialIPTimestamp = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Special IP Timestamp:", 450, 5 ); // -T timestamp option
            m_txtSetSpecialIPTimestamp = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 453, 220 );
            
            m_chkSetPingDeadline = CUtilities.AddCheckBox( m_cntControlContainer, this, "Set Ping Deadline:", 470, 5 ); // -w deadline
            m_txtSetPingDeadline = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 473, 220 );
            
            m_chkWaitForResponseTime = CUtilities.AddCheckBox( m_cntControlContainer, this, "Wait for Response Time:", 490, 5 ); // -W timeout
            m_txtWaitForResponseTime = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 493, 220 );
            
            m_chkSelectPathMTUDiscoveryStrategy = CUtilities.AddCheckBox( m_cntControlContainer, this, "<html><body>Select Path MTU<br/>Discovery Strategy:</body></html>", 510, 5 ); // -M hint
            m_txtSelectPathMTUDiscoveryStrategy = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 523, 220 );
            
            m_btnStart = CUtilities.AddButton( m_cntControlContainer, this, "Start", 555, 175, 30, 150 );
            
            m_chkSetPattern.setVisible( false );
            
            m_lstParameters.add(new CProfileParameter("Target", m_txtDestination));
            m_lstParameters.add(new CProfileParameter("AudiblePing", m_chkAudiblePing));
            m_lstParameters.add(new CProfileParameter("AdaptivePing", m_chkAdaptivePing));
            m_lstParameters.add(new CProfileParameter("PingBroadcastAddress", m_chkPingBroadcastAddress));
            m_lstParameters.add(new CProfileParameter("DontChangeSourceAddress", m_chkDontChangeSourceAddress));
            m_lstParameters.add(new CProfileParameter("MarkPackets", m_chkMarkPackets, m_txtMarkPackets));
            m_lstParameters.add(new CProfileParameter("PacketCount", m_chkPacketCount, m_txtPacketCount));
            m_lstParameters.add(new CProfileParameter("FlowLabel", m_chkSetFlowLabel, m_txtSetFlowLabel));
            m_lstParameters.add(new CProfileParameter("FloodPing", m_chkFloodPing));
            m_lstParameters.add(new CProfileParameter("Interval", m_chkSetInterval, m_txtSetInterval));
            m_lstParameters.add(new CProfileParameter("InterfaceAddress", m_chkSetInterfaceAddress, m_txtSetInterfaceAddress));
            m_lstParameters.add(new CProfileParameter("Preload", m_chkSetPreload, m_txtSetPreload));
            m_lstParameters.add(new CProfileParameter("SuppressLoopback", m_chkSuppressLoopback));
            m_lstParameters.add(new CProfileParameter("SendICMPv6NIQ", m_chkSendICMPv6NIQ, m_txtSendICMPv6NIQ));
            m_lstParameters.add(new CProfileParameter("NumericOutput", m_chkNumericOutput));
            m_lstParameters.add(new CProfileParameter("Pattern", m_chkSetPattern));
            m_lstParameters.add(new CProfileParameter("PrintTimestamp", m_chkPrintTimestamp));
            m_lstParameters.add(new CProfileParameter("QoSBits", m_chkSetQoSBits, m_txtSetQoSBits));
            m_lstParameters.add(new CProfileParameter("QuietOutput", m_chkQuietOutput));
            m_lstParameters.add(new CProfileParameter("RecordRoute", m_chkRecordRoute));
            m_lstParameters.add(new CProfileParameter("BypassRoutingTables", m_chkBypassRoutingTables));
            m_lstParameters.add(new CProfileParameter("PacketSize", m_chkSetPacketSize, m_txtSetPacketSize));
            m_lstParameters.add(new CProfileParameter("SocketSndbuf", m_chkSetSocketSndbuf, m_txtSetSocketSndbuf));
            m_lstParameters.add(new CProfileParameter("IPTimeToLive", m_chkSetIPTimeToLive, m_txtSetIPTimeToLive));
            m_lstParameters.add(new CProfileParameter("SpecialIPTimestamp", m_chkSetSpecialIPTimestamp, m_txtSetSpecialIPTimestamp));
            m_lstParameters.add(new CProfileParameter("PathMTUDiscoveryStrategy", m_chkSelectPathMTUDiscoveryStrategy, m_txtSelectPathMTUDiscoveryStrategy));
            m_lstParameters.add(new CProfileParameter("PrintFullUserToUserLatency", m_chkPrintFullUserToUserLatency));
            m_lstParameters.add(new CProfileParameter("PrintVerboseOutput", m_chkPrintVerboseOutput));
            m_lstParameters.add(new CProfileParameter("OnlyShowVersion", m_chkOnlyShowVersion));
            m_lstParameters.add(new CProfileParameter("PingDeadline", m_chkSetPingDeadline, m_txtSetPingDeadline));
            m_lstParameters.add(new CProfileParameter("WaitForResponseTime", m_chkWaitForResponseTime, m_txtWaitForResponseTime));

        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }
    
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click
	// --------------------------------------------------------------------------------
    @Override
    public void actionPerformed( ActionEvent aeSource )
    {
    	super.actionPerformed( aeSource );
    	try
    	{
    		if ( m_blnLoading == false )
			{
    			if ( aeSource.getSource( ) == m_btnStart )			btnStart_Click( );
			}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Starts the ping
	// --------------------------------------------------------------------------------
    private void btnStart_Click( )
    {
    	try
    	{
    		String astrArguments[] = new String[] {"ping"};
    		
    		// Add all the arguments
    		if ( m_chkAudiblePing.isSelected( ) == true )
    			astrArguments = CAircrackUtilities.AddArgumentToCommand("a", "", astrArguments);
    		
    	    if ( m_chkAdaptivePing.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("A", "", astrArguments);
    	    
    	    if ( m_chkPingBroadcastAddress.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("b", "", astrArguments);
    	    
    	    if ( m_chkDontChangeSourceAddress.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("B", "", astrArguments);
    	    
    	    if ( m_chkMarkPackets.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("m", m_txtMarkPackets.getText().trim(), astrArguments);
    	    
    	    if ( m_chkPacketCount.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("c", m_txtPacketCount.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSetFlowLabel.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("F", m_txtSetFlowLabel.getText().trim(), astrArguments);

    	    if ( m_chkFloodPing.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("f", "", astrArguments);

    	    if ( m_chkSetInterval.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("i", m_txtSetInterval.getText().trim(), astrArguments);

    	    if ( m_chkSetInterfaceAddress.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("I", m_txtSetInterfaceAddress.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSetPreload.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("l", m_txtSetPreload.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSuppressLoopback.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("L", "", astrArguments);

    	    if ( m_chkSendICMPv6NIQ.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("N", m_txtSendICMPv6NIQ.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSuppressLoopback.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("n", "", astrArguments);
    	    
    	    // JCheckBox m_chkSetPattern = null; // -p
    	    
    	    if ( m_chkPrintTimestamp.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("D", "", astrArguments);

    	    if ( m_chkSetQoSBits.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("Q", m_txtSetQoSBits.getText().trim(), astrArguments);
    	    
    	    if ( m_chkQuietOutput.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("q", "", astrArguments);
    	    
    	    if ( m_chkRecordRoute.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("R", "", astrArguments);
    	    
    	    if ( m_chkBypassRoutingTables.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("r", "", astrArguments);
    	    
    	    if ( m_chkSetPacketSize.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("s", m_txtSetPacketSize.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSetSocketSndbuf.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("S", m_txtSetSocketSndbuf.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSetIPTimeToLive.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("t", m_txtSetIPTimeToLive.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSetSpecialIPTimestamp.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("T", m_txtSetSpecialIPTimestamp.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSelectPathMTUDiscoveryStrategy.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("M", m_txtSelectPathMTUDiscoveryStrategy.getText().trim(), astrArguments);
    	    
    	    if ( m_chkPrintFullUserToUserLatency.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("U", "", astrArguments);
    	    
    	    if ( m_chkPrintVerboseOutput.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("v", "", astrArguments);

    	    if ( m_chkOnlyShowVersion.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("V", "", astrArguments);

    	    if ( m_chkSetPingDeadline.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("w", m_txtSetPingDeadline.getText().trim(), astrArguments);
    	    
    	    if ( m_chkWaitForResponseTime.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("W", m_txtWaitForResponseTime.getText().trim(), astrArguments);
    		
    		// Add the ping target
    		astrArguments = CAircrackUtilities.AddArgumentToArray(m_txtDestination.getText( ).trim( ), astrArguments);
    		
    		DProgramOutput dlgPingOutput = new DProgramOutput("Ping - Output", astrArguments );
    		dlgPingOutput.setVisible( true );
    		
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: SetDesination
	// Abstract: Allows an outside form to set the destination. Used by Discover Hosts.
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
    	m_txtDestination.setText( m_strPassedInTarget );
    }
    
}
