// --------------------------------------------------------------------------------
// Name: FOtherToolsHPing
// Abstract: Graphical tool for the HPing command-line utility
// --------------------------------------------------------------------------------

import java.awt.event.ActionEvent;

// Imports
import javax.swing.*;

public class FOtherToolsHPing extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtTarget = null;
	private JButton m_btnRun = null;
	
	// Base Options
	private JCheckBox m_chkNumericOutput = null; // -n
	private JCheckBox m_chkQuietOutput = null; // -q
	private JCheckBox m_chkVerboseOutput = null; // -v
	private JCheckBox m_chkDebugMode = null; // -D
	private JCheckBox m_chkPacketCount = null; // -c count
	private CTextBox m_txtPacketCount = null;
	private JCheckBox m_chkPacketDelayMillis = null; // -i u[number]
	private CTextBox m_txtPacketDelayMillis = null;
	
	// Protocol Selection
	private JCheckBox m_chkProtocol = null;
	private CComboBox m_cboProtocol = null; // -0 (raw IP mode), -1 (ICMP mode), -2 (UDP mode), -9 (Listen Mode)
	
	// Common Options
	private JCheckBox m_chkDumpPacketsHex = null; // -j
	private JCheckBox m_chkDumpPacketsASCII = null; // -J
	private JCheckBox m_chkUseSafeProtocol = null; // -B
	private JCheckBox m_chkSendEOF = null; // -u
	private JCheckBox m_chkTraceRouteMode = null; // -T
	private JCheckBox m_chkStaticTimeToLive = null; // --tr-keep-ttl
	private JCheckBox m_chkStopOnTimeout = null; // --tr-stop
	private JCheckBox m_chkHideRTTInformation = null; // --tr-no-rtt
	private JCheckBox m_chkExitWithFlagAsCode = null; // --tcpexitcode
	private JCheckBox m_chkDataSize = null; // -d size
	private CTextBox m_txtDataSize = null;
	private JCheckBox m_chkPacketDataFromFile = null; // -E filename
	private CTextBox m_txtPacketDataFromFile = null;
	private JButton m_btnPacketDataFromFile = null;
	private JCheckBox m_chkSignature = null; // -e signature
	private CTextBox m_txtSignature = null;
	
	// IP Related Options
	private JCheckBox m_chkIPID = null; // -N
	private JCheckBox m_chkRawIPMode = null; // -H
	private JCheckBox m_chkWindowsReplyIDs = null; // -W
	private JCheckBox m_chkDisplayIDIncrements = null; // -r
	private JCheckBox m_chkSplitFragments = null; // -f
	private JCheckBox m_chkMoreFragments = null; // -x
	private JCheckBox m_chkDontFragment = null; // -y
	private JCheckBox m_chkRecordRoute = null; // -R
	private JCheckBox m_chkSourceAddress = null; // -a hostname
	private CTextBox m_txtSourceAddress = null;
	private JCheckBox m_chkTimeToLive = null; // -t ttl
	private CTextBox m_txtTimeToLive = null;
	private JCheckBox m_chkFragmentOffset = null; // -g value
	private CTextBox m_txtFragmentOffset = null;
	private JCheckBox m_chkVirtualMTU = null; // -m value
	private CTextBox m_txtVirtualMTU = null;
	private JCheckBox m_chkTypeOfService = null; // -o value
	private CTextBox m_txtTypeOfService = null;
	
	// ICMP Related Options
	private JCheckBox m_chkSetIPVersionInHeader = null; // --icmp-ipver
	private JCheckBox m_chkSetHeaderLength = null; // --icmp-iphlen
	private JCheckBox m_chkSetPacketLength = null; // --icmp-iplen
	private JCheckBox m_chkSetPacketIPID = null; // --icmp-ipid
	private JCheckBox m_chkSetProtocol = null; // --icmp-ipproto
	private JCheckBox m_chkSetChecksum = null; // --icmp-cksum
	private JCheckBox m_chkICMPType = null; // -C type
	private CTextBox m_txtICMPType = null;
	private JCheckBox m_chkICMPCode = null; // -K code
	private CTextBox m_txtICMPCode = null;
	
	// TCP/UDP Related Options
	private JCheckBox m_chkStaticSourcePort = null; // --keep
	private JCheckBox m_chkTCPWindowSize = null; // -w
	private JCheckBox m_chkFakeTCPDataOffset = null; // -O
	private JCheckBox m_chkTCPSequenceNumber = null; // -M
	private JCheckBox m_chkTCPACK = null; // -L
	private JCheckBox m_chkBadChecksum = null; // -b
	private JCheckBox m_chkEnableTCPTimestamp = null; // --tcp-timestamp
	private JCheckBox m_chkTCPFlagFIN = null; // -F
	private JCheckBox m_chkTCPFlagSYN = null; // -S
	private JCheckBox m_chkTCPFlagRST = null; // -R
	private JCheckBox m_chkTCPFlagPUSH = null; // -P
	private JCheckBox m_chkTCPFlagACK = null; // -A
	private JCheckBox m_chkTCPFlagURG = null; // -U
	private JCheckBox m_chkTCPFlagXMAS = null; // -X
	private JCheckBox m_chkTCPFlagYMAS = null; // -Y
	private JCheckBox m_chkBasePortNumber = null; // -s port
	private CTextBox m_txtBasePortNumber = null;
	private JCheckBox m_chkDestinationPort = null; // -p port
	private CTextBox m_txtDestinationPort = null;
	
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsHPing
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsHPing()
	{
		super("HPing", 640, 480, false, false, "HPing");
		try
		{
			AddControls();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the class controls onto the form
	// --------------------------------------------------------------------------------
	private void AddControls()
	{
		try
		{
            CTabContainer tbcTabContainer = CUtilities.AddTabContainer(m_cntControlContainer, 75, 10, 350, 610);
            JPanel pnlGeneral = tbcTabContainer.AddTab("General");
            JPanel pnlIPOptions = tbcTabContainer.AddTab("IP Options");
            JPanel pnlICMPOptions = tbcTabContainer.AddTab("ICMP Options");
            JPanel pnlTCPUDPOptions = tbcTabContainer.AddTab("TCP/UDP Options");
			
            CUtilities.AddLabel(m_cntControlContainer, "Target:", 50, 5);
        	m_txtTarget = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 200, 49, 65);
        	m_btnRun = CUtilities.AddButton(m_cntControlContainer, this, "Run", 428, 270, 20, 100);
            
            AddGeneralControls( pnlGeneral );
            AddIPOptionsControls( pnlIPOptions );
            AddICMPOptionsControls( pnlICMPOptions );
            AddTCPUDPOptionsControls( pnlTCPUDPOptions );
            
            InitializeDropdownValues();
            
            AddProfileParameters();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddGeneralControls
	// Abstract: Adds the class controls for General settings onto the form
	// --------------------------------------------------------------------------------
	private void AddGeneralControls( JPanel pnlGeneral )
	{
		try
		{
			// Base Options
			m_chkNumericOutput = CUtilities.AddCheckBox(pnlGeneral, null, "Numeric Output", 5, 5);
			m_chkQuietOutput = CUtilities.AddCheckBox(pnlGeneral, null, "Quiet Output", 5, 200);
			m_chkVerboseOutput = CUtilities.AddCheckBox(pnlGeneral, null, "Verbose Output", 25, 5);
			m_chkDebugMode = CUtilities.AddCheckBox(pnlGeneral, null, "Debug Mode", 25, 200);
			m_chkPacketCount = CUtilities.AddCheckBox(pnlGeneral, null, "Packet Count:", 50, 5);
			m_txtPacketCount = CUtilities.AddTextBox(pnlGeneral, null, "", 8, 10, 53, 130);
			m_chkPacketDelayMillis = CUtilities.AddCheckBox(pnlGeneral, null, "Packet Delay (Millis):", 75, 5);
			m_txtPacketDelayMillis = CUtilities.AddTextBox(pnlGeneral, null, "", 8, 10, 78, 180);
			
			// Protocol Selection
			m_chkProtocol = CUtilities.AddCheckBox(pnlGeneral, null, "Protocol:", 110, 5);
			m_cboProtocol = CUtilities.AddComboBox(pnlGeneral, null, 111, 95, 20, 150);
			
			// Common Options
			m_chkDumpPacketsHex = CUtilities.AddCheckBox(pnlGeneral, null, "Dump Packets in Hex", 150, 5);
			m_chkDumpPacketsASCII = CUtilities.AddCheckBox(pnlGeneral, null, "Dump Packets in ASCII", 150, 200);
			m_chkUseSafeProtocol = CUtilities.AddCheckBox(pnlGeneral, null, "Use Safe Protocol", 170, 5);
			m_chkSendEOF = CUtilities.AddCheckBox(pnlGeneral, null, "Send EOF", 170, 200);
			m_chkTraceRouteMode = CUtilities.AddCheckBox(pnlGeneral, null, "Trace Route Mode", 190, 5);
			m_chkStaticTimeToLive = CUtilities.AddCheckBox(pnlGeneral, null, "Static Time To Live", 190, 200);
			m_chkStopOnTimeout = CUtilities.AddCheckBox(pnlGeneral, null, "Stop On Timeout", 210, 5);
			m_chkHideRTTInformation = CUtilities.AddCheckBox(pnlGeneral, null, "Hide RTT Information", 210, 200);
			m_chkExitWithFlagAsCode = CUtilities.AddCheckBox(pnlGeneral, null, "Exit With Flag As Code", 230, 5);
			m_chkDataSize = CUtilities.AddCheckBox(pnlGeneral, null, "Data Size:", 252, 5);
			m_txtDataSize = CUtilities.AddTextBox(pnlGeneral, null, "", 8, 10, 255, 105);
			m_chkPacketDataFromFile = CUtilities.AddCheckBox(pnlGeneral, null, "Packet Data From File:", 273, 5);
			m_txtPacketDataFromFile = CUtilities.AddTextBox(pnlGeneral, null, "", 15, 100, 275, 190);
			m_btnPacketDataFromFile = CUtilities.AddButton(pnlGeneral, this, "...", 275, 360, 18, 50);
			m_chkSignature = CUtilities.AddCheckBox(pnlGeneral, null, "Signature:", 296, 5);
			m_txtSignature = CUtilities.AddTextBox(pnlGeneral, null, "", 15, 100, 300, 105);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddIPOptionsControls
	// Abstract: Adds the class controls for IP settings onto the form
	// --------------------------------------------------------------------------------
	private void AddIPOptionsControls(JPanel pnlIPOptions)
	{
		try
		{
			m_chkIPID = CUtilities.AddCheckBox(pnlIPOptions, null, "IP ID", 5, 5);
			m_chkRawIPMode = CUtilities.AddCheckBox(pnlIPOptions, null, "Raw IP Mode", 5, 200);
			m_chkWindowsReplyIDs = CUtilities.AddCheckBox(pnlIPOptions, null, "Windows Reply IDs", 25, 5);
			m_chkDisplayIDIncrements = CUtilities.AddCheckBox(pnlIPOptions, null, "Display ID Increments", 25, 200);
			m_chkSplitFragments = CUtilities.AddCheckBox(pnlIPOptions, null, "Split Fragments", 45, 5);
			m_chkMoreFragments = CUtilities.AddCheckBox(pnlIPOptions, null, "More Fragments", 45, 200);
			m_chkDontFragment = CUtilities.AddCheckBox(pnlIPOptions, null, "Don't Fragment", 65, 5);
			m_chkRecordRoute = CUtilities.AddCheckBox(pnlIPOptions, null, "Record Route", 65, 200);
			m_chkSourceAddress = CUtilities.AddCheckBox(pnlIPOptions, null, "Source Address:", 90, 5);
			m_txtSourceAddress = CUtilities.AddTextBox(pnlIPOptions, null, "", 15, 100, 92, 150);
			m_chkTimeToLive = CUtilities.AddCheckBox(pnlIPOptions, null, "Time To Live:", 115, 5);
			m_txtTimeToLive = CUtilities.AddTextBox(pnlIPOptions, null, "", 15, 100, 117, 125);
			m_chkFragmentOffset = CUtilities.AddCheckBox(pnlIPOptions, null, "Fragment Offset:", 140, 5);
			m_txtFragmentOffset = CUtilities.AddTextBox(pnlIPOptions, null, "", 15, 100, 142, 155);
			m_chkVirtualMTU = CUtilities.AddCheckBox(pnlIPOptions, null, "Virtual MTU:", 165, 5);
			m_txtVirtualMTU = CUtilities.AddTextBox(pnlIPOptions, null, "", 15, 100, 167, 120);
			m_chkTypeOfService = CUtilities.AddCheckBox(pnlIPOptions, null, "Type Of Service:", 190, 5);
			m_txtTypeOfService = CUtilities.AddTextBox(pnlIPOptions, null, "", 15, 100, 192, 150);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddICMPOptionsControls
	// Abstract: Adds the class controls for ICMP settings onto the form
	// --------------------------------------------------------------------------------
	private void AddICMPOptionsControls(JPanel pnlICMPOptions)
	{
		try
		{
			m_chkSetIPVersionInHeader = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set IP Version Header", 5, 5);
			m_chkSetHeaderLength = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set Header Length", 5, 200);
			m_chkSetPacketLength = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set Packet Length", 25, 5);
			m_chkSetPacketIPID = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set Packet IP ID", 25, 200);
			m_chkSetProtocol = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set Protocol", 45, 5);
			m_chkSetChecksum = CUtilities.AddCheckBox(pnlICMPOptions, null, "Set Checksum", 45, 200);
			m_chkICMPType = CUtilities.AddCheckBox(pnlICMPOptions, null, "ICMP Type:", 70, 5);
			m_txtICMPType = CUtilities.AddTextBox(pnlICMPOptions, null, "", 15, 100, 72, 110);
			m_chkICMPCode = CUtilities.AddCheckBox(pnlICMPOptions, null, "ICMP Code:", 95, 5);
			m_txtICMPCode = CUtilities.AddTextBox(pnlICMPOptions, null, "", 15, 100, 97, 110);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddTCPUDPOptionsControls
	// Abstract: Adds the class controls for TCP/UDP settings onto the form
	// --------------------------------------------------------------------------------
	private void AddTCPUDPOptionsControls(JPanel pnlTCPUDPOptions)
	{
		try
		{
			m_chkStaticSourcePort = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Static Source Port", 5, 5);
			m_chkTCPWindowSize = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP Window Size", 5, 200);
			m_chkFakeTCPDataOffset = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Fake TCP Data Offset", 25, 5);
			m_chkTCPSequenceNumber = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP Sequence Number", 25, 200);
			m_chkTCPACK = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP ACK", 45, 5);
			m_chkBadChecksum = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Bad Checksum", 45, 200);
			m_chkEnableTCPTimestamp = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Enable TCP Timestamp", 65, 5);
			m_chkTCPFlagFIN = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP FIN Flag", 65, 200);
			m_chkTCPFlagSYN = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP SYN Flag", 85, 5);
			m_chkTCPFlagRST = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP RST Flag", 85, 200);
			m_chkTCPFlagPUSH = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP PUSH Flag", 105, 5);
			m_chkTCPFlagACK = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP ACK Flag", 105, 200);
			m_chkTCPFlagURG = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP URG Flag", 125, 5);
			m_chkTCPFlagXMAS = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP XMAS Flag", 125, 200);
			m_chkTCPFlagYMAS = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "TCP YMAS Flag", 145, 5);
			m_chkBasePortNumber = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Base Port Number:", 170, 5);
			m_txtBasePortNumber = CUtilities.AddTextBox(pnlTCPUDPOptions, null, "", 15, 100, 172, 165);
			m_chkDestinationPort = CUtilities.AddCheckBox(pnlTCPUDPOptions, null, "Destination Port:", 195, 5);
			m_txtDestinationPort = CUtilities.AddTextBox(pnlTCPUDPOptions, null, "", 15, 100, 197, 155);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: InitializeDropdownValues
	// Abstract: Adds the values to the dropdowns
	// --------------------------------------------------------------------------------
	private void InitializeDropdownValues()
	{
		try
		{
			m_cboProtocol.SetSorted( false );
			m_cboProtocol.AddItemToList("Raw IP Mode", 0);
			m_cboProtocol.AddItemToList("ICMP Mode", 1);
			m_cboProtocol.AddItemToList("TCP/UDP Mode", 2);
			m_cboProtocol.AddItemToList("Listen Mode", 9);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddGeneralControls
	// Abstract: Adds the class controls for General settings onto the form
	// --------------------------------------------------------------------------------
	private void AddProfileParameters()
	{
		try
		{
			m_lstParameters.add(new CProfileParameter("Target", m_txtTarget));
			AddGeneralControlParameters();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddGeneralControlParameters
	// Abstract: Creates the parameter objects for each of the controls under the
	//			 General tab.
	// --------------------------------------------------------------------------------
	private void AddGeneralControlParameters()
	{
		try
		{
			m_lstParameters.add(new CProfileParameter("NumericOutput", m_chkNumericOutput));
			m_lstParameters.add(new CProfileParameter("QuietOutput", m_chkQuietOutput));
			m_lstParameters.add(new CProfileParameter("VerboseOutput", m_chkVerboseOutput));
			m_lstParameters.add(new CProfileParameter("DebugMode", m_chkDebugMode)); 
			m_lstParameters.add(new CProfileParameter("PacketCount", m_chkPacketCount, m_txtPacketCount));
			m_lstParameters.add(new CProfileParameter("PacketDelay", m_chkPacketDelayMillis, m_txtPacketDelayMillis));
			m_lstParameters.add(new CProfileParameter("Protocol", m_chkProtocol, m_cboProtocol));
			m_lstParameters.add(new CProfileParameter("DumpPacketsInHex", m_chkDumpPacketsHex));
			m_lstParameters.add(new CProfileParameter("DumpPacketsInASCII", m_chkDumpPacketsASCII));
			m_lstParameters.add(new CProfileParameter("UseSafeProtocol", m_chkUseSafeProtocol));
			m_lstParameters.add(new CProfileParameter("SendEOF", m_chkSendEOF));
			m_lstParameters.add(new CProfileParameter("TraceRouteMode", m_chkTraceRouteMode));
			m_lstParameters.add(new CProfileParameter("StaticTimeToLive", m_chkStaticTimeToLive));
			m_lstParameters.add(new CProfileParameter("StopOnTimeout", m_chkStopOnTimeout));
			m_lstParameters.add(new CProfileParameter("HideRTTInformation", m_chkHideRTTInformation));
			m_lstParameters.add(new CProfileParameter("ExitWithFlagAsCode", m_chkExitWithFlagAsCode));
			m_lstParameters.add(new CProfileParameter("DataSize", m_chkDataSize, m_txtDataSize));
			m_lstParameters.add(new CProfileParameter("PacketDataFromFile", m_chkPacketDataFromFile, m_txtPacketDataFromFile));
			m_lstParameters.add(new CProfileParameter("Signature", m_chkSignature, m_txtSignature));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks, dropdown changes, etc.
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed(aeSource);
		try
		{
			if (aeSource.getSource() == m_btnPacketDataFromFile)	CAircrackUtilities.DisplayFileChooser(m_txtPacketDataFromFile, this, m_chkPacketDataFromFile);
			else if (aeSource.getSource() == m_btnRun)				btnRun_Click();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnRun_Click
	// Abstract: Executes the hping command
	// --------------------------------------------------------------------------------
	private void btnRun_Click() 
	{
		try
		{
			if (m_txtTarget.getText().trim().equals("") == true)
			{
				JOptionPane.showMessageDialog(null, "Target is required.");
				return;
			}
			
			String strProgram = "";
			if (CGlobals.clsLocalMachine.ProgramInstalled("hping2"))
				strProgram = "hping2";
			else
				strProgram = "hping3";
			
			String astrCommand[] = new String[] {strProgram};
			
			astrCommand = AddGeneralControlArguments( astrCommand );
			astrCommand = AddIPOptionsControlArguments( astrCommand );
			astrCommand = AddICMPOptionsControlArguments( astrCommand );
			astrCommand = AddTCPUDPOptionsControlArguments( astrCommand );

			astrCommand = CAircrackUtilities.AddArgumentToArray(m_txtTarget.getText(), astrCommand);
			
			DProgramOutput dlgHPingOutput = new DProgramOutput("HPing Output", astrCommand);
			dlgHPingOutput.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddGeneralControlArguments
	// Abstract: Adds arguments to the command from the General controls
	// --------------------------------------------------------------------------------
	private String[] AddGeneralControlArguments(String[] astrCommand)
	{
		try
		{
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkNumericOutput, "n", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkQuietOutput, "q", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkVerboseOutput, "v", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDebugMode, "D", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPacketCount, "c", m_txtPacketCount.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPacketDelayMillis, "i", "u" + m_txtPacketDelayMillis.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkProtocol, String.valueOf(m_cboProtocol.GetSelectedItemValue()), "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDumpPacketsHex, "j", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDumpPacketsASCII, "J", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseSafeProtocol, "B", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSendEOF, "u", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTraceRouteMode, "T", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkStaticTimeToLive, "-tr-keep-ttl", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkStopOnTimeout, "-tr-stop", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkHideRTTInformation, "-tr-no-rtt", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkExitWithFlagAsCode, "-tcpexitcode", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDataSize, "d", m_txtDataSize.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPacketDataFromFile, "E", m_txtPacketDataFromFile.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSignature, "e", m_txtSignature.getText(), astrCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddIPOptionsControl
	// Abstract: Adds arguments to the command from the IP controls
	// --------------------------------------------------------------------------------
	private String[] AddIPOptionsControlArguments(String[] astrCommand)
	{
		try
		{
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkIPID, "N", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRawIPMode, "H", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkWindowsReplyIDs, "W", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisplayIDIncrements, "r", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSplitFragments, "f", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkMoreFragments, "x", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDontFragment, "y", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRecordRoute, "R", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSourceAddress, "a", m_txtSourceAddress.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTimeToLive, "t", m_txtTimeToLive.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkFragmentOffset, "g", m_txtFragmentOffset.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkVirtualMTU, "m", m_txtVirtualMTU.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTypeOfService, "o", m_txtTypeOfService.getText(), astrCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddICMPOptionsControlArguments
	// Abstract: Adds arguments to the command from the ICMP controls
	// --------------------------------------------------------------------------------
	private String[] AddICMPOptionsControlArguments(String[] astrCommand)
	{
		try
		{
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetIPVersionInHeader, "-icmp-ipver", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetHeaderLength, "-icmp-iphlen", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetPacketLength, "-icmp-iplen", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetPacketIPID, "-icmp-ipid", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetProtocol, "-icmp-ipproto", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetChecksum, "-icmp-cksum", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkICMPType, "C", m_txtICMPType.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkICMPCode, "K", m_txtICMPCode.getText(), astrCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddTCPUDPOptionsControlArguments
	// Abstract: Adds arguments to the command from the TCP/UDP controls
	// --------------------------------------------------------------------------------
	private String[] AddTCPUDPOptionsControlArguments(String[] astrCommand)
	{
		try
		{
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkStaticSourcePort, "-keep", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPWindowSize, "w", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkFakeTCPDataOffset, "O", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPSequenceNumber, "M", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPACK, "L", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkBadChecksum, "b", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkEnableTCPTimestamp, "-tcp-timestamp", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagFIN, "F", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagSYN, "S", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagRST, "R", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagPUSH, "P", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagACK, "A", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagURG, "U", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagXMAS, "X", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTCPFlagYMAS, "Y", "", astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkBasePortNumber, "s", m_txtBasePortNumber.getText(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDestinationPort, "p", m_txtDestinationPort.getText(), astrCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}

}
