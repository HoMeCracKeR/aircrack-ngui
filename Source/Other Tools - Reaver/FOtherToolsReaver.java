// --------------------------------------------------------------------------------
// Name: FOtherToolsReaver
// Abstract: Reaver window. Allows you to break into WPS-enabled WPA networks.
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import java.awt.event.*;

public class FOtherToolsReaver extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	// Required
	private CComboBox m_cboMonitorInterface = null;
	private CTextBox m_txtNetworkBSSID = null;
	private JButton m_btnNetworkBSSID = null;
	
	// Optional
	private JCheckBox m_chkSetHostSystemMAC = null; // -m
	private CTextBox m_txtSetHostSystemMAC = null;
	private JCheckBox m_chkSetNetworkESSID = null; // -e
	private JButton m_btnSetNetworkESSID = null;
	private CTextBox m_txtSetNetworkESSID = null;
	private JCheckBox m_chkSetChannel = null; // -c
	private CComboBox m_cboSetChannel = null;
	private JCheckBox m_chkSetOutFile = null; // -o
	private CTextBox m_txtSetOutFile = null;
	private JButton m_btnSetOutFile = null;
	private JCheckBox m_chkRestoreSession = null; // -s
	private CTextBox m_txtRestoreSession = null;
	private JButton m_btnRestoreSession = null;
	private JCheckBox m_chkExecuteCommand = null; // -C
	private CTextBox m_txtExecuteCommand = null;
	private JCheckBox m_chkDaemonize = null; // -D
	private JCheckBox m_chkAutoDetectSettings = null ; // -a
	private JCheckBox m_chkFixedChannel = null; // -f
	private JCheckBox m_chk5GHzChannels = null; // -5
	private JCheckBox m_chkSetVerbosity = null;
	private CComboBox m_cboVerbosityLevel = null; // -v, -vv, -q
	
	// Advanced Settings
	private JCheckBox m_chkSpecifyWPSPin = null; // -p
	private CTextBox m_txtSpecifyWPSPin = null;
	private JCheckBox m_chkSetDelayBetweenAttempts = null; // -d
	private CTextBox m_txtSetDelayBetweenAttempts = null;
	private JCheckBox m_chkLockDelay = null; // -l
	private CTextBox m_txtLockDelay = null;
	private JCheckBox m_chkSetMaxAttempts = null; // -g
	private CTextBox m_txtSetMaxAttempts = null;
	private JCheckBox m_chkSetFailWait = null; // -x
	private CTextBox m_txtSetFailWait = null;
	private JCheckBox m_chkSetRecurringDelay = null; // -r
	private CTextBox m_txtSetRecurringDelay = null;
	private JCheckBox m_chkSetTimeout = null; // -t
	private CTextBox m_txtSetTimeout = null;
	private JCheckBox m_chkM57Timeout = null; // -T
	private CTextBox m_txtM57Timeout = null;
	private JCheckBox m_chkDoNotAssociate = null; // -A
	private JCheckBox m_chkDoNotSendNACKs = null; // -N
	private JCheckBox m_chkUseSmallDHKeys = null; // -S
	private JCheckBox m_chkIgnoreLocks = null; // -L
	private JCheckBox m_chkEAPTerminate = null; // -E
	private JCheckBox m_chkAlwaysSendNACKs = null; // -n
	private JCheckBox m_chkMimickWindows7Registrar = null; // -w
	
	private JButton m_btnStart = null;
	
	private String m_strPassedInNetworkBSSID = "";
	
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsReaver
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsReaver( )
	{
		super("Reaver", 430, 680, false, false, "Reaver");
		try
		{
			AddControls( );
			PopulateMonitorModeInterfaces( );
			PopulateChannels( );
			PopulateVerbosityLevels( );
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
            
            // Required Settings
            CUtilities.AddLabel(m_cntControlContainer, "Required Settings:", 65, 5);
        	CUtilities.AddLabel(m_cntControlContainer, "Interface:", 86, 5); // -i
        	m_cboMonitorInterface = CUtilities.AddComboBox(m_cntControlContainer, null, 85, 80, 18, 100);
        	CUtilities.AddLabel(m_cntControlContainer, "BSSID:", 106, 5); // -b
        	m_txtNetworkBSSID = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 17, 105, 80);
        	m_btnNetworkBSSID = CUtilities.AddButton(m_cntControlContainer, this, "...", 105, 250, 18, 50);
        	
        	// Optional Settings
        	CUtilities.AddLabel(m_cntControlContainer, "Optional Settings:", 130, 5);
        	m_chkDaemonize = CUtilities.AddCheckBox(m_cntControlContainer, null, "Daemonize", 145, 5);
        	m_chkAutoDetectSettings = CUtilities.AddCheckBox(m_cntControlContainer, null, "Auto-detect Settings", 145, 200);
        	m_chkFixedChannel = CUtilities.AddCheckBox(m_cntControlContainer, null, "Fixed Channel", 165, 5);
        	m_chk5GHzChannels = CUtilities.AddCheckBox(m_cntControlContainer, null, "5 GHz Channels", 165, 200); 
        	m_chkSetHostSystemMAC = CUtilities.AddCheckBox(m_cntControlContainer, null, "Host System MAC:", 187, 5);
        	m_txtSetHostSystemMAC = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 17, 190, 165);
        	m_chkSetNetworkESSID = CUtilities.AddCheckBox(m_cntControlContainer, null, "Network ESSID:", 208, 5);
        	m_txtSetNetworkESSID = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 210, 165);
        	m_btnSetNetworkESSID = CUtilities.AddButton(m_cntControlContainer, this, "...", 210, 335, 18, 50);
        	m_chkSetChannel = CUtilities.AddCheckBox(m_cntControlContainer, null, "Channel:", 228, 5);
        	m_cboSetChannel = CUtilities.AddComboBox(m_cntControlContainer, null, 230, 165, 18, 75);
        	m_chkSetOutFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "Output File:", 248, 5);
        	m_txtSetOutFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 250, 165);
        	m_btnSetOutFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 250, 335, 18, 50);
        	m_chkRestoreSession = CUtilities.AddCheckBox(m_cntControlContainer, null, "Restore Session:", 268, 5);
        	m_txtRestoreSession = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 270, 165);
        	m_btnRestoreSession = CUtilities.AddButton(m_cntControlContainer, this, "...", 270, 335, 18, 50);
        	m_chkExecuteCommand = CUtilities.AddCheckBox(m_cntControlContainer, null, "Execute Command:", 288, 5);
        	m_txtExecuteCommand = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 300, 290, 165);
        	m_chkSetVerbosity = CUtilities.AddCheckBox(m_cntControlContainer, null, "Verbosity:", 308, 5);
        	m_cboVerbosityLevel = CUtilities.AddComboBox(m_cntControlContainer, null, 310, 165, 18, 150);
        	
        	// Advanced Settings
        	CUtilities.AddLabel(m_cntControlContainer, "Advanced Settings:", 340, 5);
        	m_chkDoNotAssociate = CUtilities.AddCheckBox(m_cntControlContainer, null, "Do Not Associate", 355, 5);
        	m_chkDoNotSendNACKs = CUtilities.AddCheckBox(m_cntControlContainer, this, "Do Not Send NACKs", 355, 200);
        	m_chkUseSmallDHKeys = CUtilities.AddCheckBox(m_cntControlContainer, null, "Use Small DH Keys", 375, 5);
        	m_chkIgnoreLocks = CUtilities.AddCheckBox(m_cntControlContainer, null, "Ignore Locks", 375, 200);
        	m_chkEAPTerminate = CUtilities.AddCheckBox(m_cntControlContainer, null, "EAP Terminate", 395, 5);
        	m_chkAlwaysSendNACKs = CUtilities.AddCheckBox(m_cntControlContainer, this, "Always Send NACKs", 395, 200);
        	m_chkMimickWindows7Registrar = CUtilities.AddCheckBox(m_cntControlContainer, null, "Mimick Windows 7 Registrar", 415, 5);
        	m_chkSpecifyWPSPin = CUtilities.AddCheckBox(m_cntControlContainer, null, "WPS Pin:", 438, 5);
        	m_txtSpecifyWPSPin = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 8, 440, 165);
        	m_chkSetDelayBetweenAttempts = CUtilities.AddCheckBox(m_cntControlContainer, null, "Attempt Delay:", 458, 5);
        	m_txtSetDelayBetweenAttempts = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 460, 165);
        	m_chkLockDelay = CUtilities.AddCheckBox(m_cntControlContainer, null, "Lock Delay:", 478, 5);
        	m_txtLockDelay = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 480, 165);
        	m_chkSetMaxAttempts = CUtilities.AddCheckBox(m_cntControlContainer, null, "Max Attempts:", 498, 5);
        	m_txtSetMaxAttempts = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 500, 165);
        	m_chkSetFailWait = CUtilities.AddCheckBox(m_cntControlContainer, null, "Fail Wait:", 518, 5);
        	m_txtSetFailWait = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 520, 165);
        	m_chkSetRecurringDelay = CUtilities.AddCheckBox(m_cntControlContainer, null, "Recurring Delay:", 538, 5);
        	m_txtSetRecurringDelay = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 540, 165);
        	m_chkSetTimeout = CUtilities.AddCheckBox(m_cntControlContainer, null, "Timeout:", 558, 5);
        	m_txtSetTimeout = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 560, 165);
        	m_chkM57Timeout = CUtilities.AddCheckBox(m_cntControlContainer, null, "M57 Timeout:", 578, 5);
        	m_txtM57Timeout = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 580, 165);
        	
        	m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 600, 152, 18, 125);
        	
        	m_cboSetChannel.SetSorted( false );
        	m_cboVerbosityLevel.SetSorted( false );
            
        	m_lstParameters.add(new CProfileParameter("Interface", m_cboMonitorInterface));
        	m_lstParameters.add(new CProfileParameter("NetworkBSSID", m_txtNetworkBSSID));
        	m_lstParameters.add(new CProfileParameter("Daemonize", m_chkDaemonize));
        	m_lstParameters.add(new CProfileParameter("AutoDetectSettings", m_chkAutoDetectSettings));
        	m_lstParameters.add(new CProfileParameter("FixedChannel", m_chkFixedChannel));
        	m_lstParameters.add(new CProfileParameter("5GHzChannels", m_chk5GHzChannels));
        	m_lstParameters.add(new CProfileParameter("HostSystemMAC", m_chkSetHostSystemMAC, m_txtSetHostSystemMAC));
        	m_lstParameters.add(new CProfileParameter("NetworkESSID", m_chkSetNetworkESSID, m_txtSetNetworkESSID));
        	m_lstParameters.add(new CProfileParameter("Channel", m_chkSetChannel, m_cboSetChannel));
        	m_lstParameters.add(new CProfileParameter("OutFile", m_chkSetOutFile, m_txtSetOutFile));
        	m_lstParameters.add(new CProfileParameter("RestoreSession", m_chkRestoreSession, m_txtRestoreSession));
        	m_lstParameters.add(new CProfileParameter("ExecuteCommand", m_chkExecuteCommand, m_txtExecuteCommand));
        	m_lstParameters.add(new CProfileParameter("Verbosity", m_chkSetVerbosity, m_cboVerbosityLevel));
        	m_lstParameters.add(new CProfileParameter("DoNotAssociate", m_chkDoNotAssociate));
        	m_lstParameters.add(new CProfileParameter("DoNotSendNACKs", m_chkDoNotSendNACKs));
        	m_lstParameters.add(new CProfileParameter("UseSmallDHKeys", m_chkUseSmallDHKeys));
        	m_lstParameters.add(new CProfileParameter("IgnoreLocks", m_chkIgnoreLocks));
        	m_lstParameters.add(new CProfileParameter("EAPTerminate", m_chkEAPTerminate));
        	m_lstParameters.add(new CProfileParameter("AlwaysSendNACKs", m_chkAlwaysSendNACKs));
        	m_lstParameters.add(new CProfileParameter("MimickWindows7Registrar", m_chkMimickWindows7Registrar));
        	m_lstParameters.add(new CProfileParameter("WPSPin", m_chkSpecifyWPSPin, m_txtSpecifyWPSPin));
        	m_lstParameters.add(new CProfileParameter("DelayBetweenAttempts", m_chkSetDelayBetweenAttempts, m_txtSetDelayBetweenAttempts));
        	m_lstParameters.add(new CProfileParameter("LockDelay", m_chkLockDelay, m_txtLockDelay));
        	m_lstParameters.add(new CProfileParameter("MaxAttempts", m_chkSetMaxAttempts, m_txtSetMaxAttempts));
        	m_lstParameters.add(new CProfileParameter("FailWait", m_chkSetFailWait, m_txtSetFailWait));
        	m_lstParameters.add(new CProfileParameter("RecurringDelay", m_chkSetRecurringDelay, m_txtSetRecurringDelay));
        	m_lstParameters.add(new CProfileParameter("Timeout", m_chkSetTimeout, m_txtSetTimeout));
        	m_lstParameters.add(new CProfileParameter("M57Timeout", m_chkM57Timeout, m_txtM57Timeout));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateMonitorModeInterfaces
	// Abstract: Populates the dropdown with interfaces in monitor mode
	// --------------------------------------------------------------------------------
	private void PopulateMonitorModeInterfaces( )
	{
		try
		{
			CNetworkInterface aclsMonitorModeInterfaces[] = CGlobals.clsLocalMachine.GetAllMonitorableInterfaces( );
			for ( int intIndex = 0; intIndex < aclsMonitorModeInterfaces.length; intIndex += 1 )
				m_cboMonitorInterface.AddItemToList(aclsMonitorModeInterfaces[intIndex].GetInterface(), 0);
			m_cboMonitorInterface.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateChannels
	// Abstract: Populates the available channels to attack
	// --------------------------------------------------------------------------------
	private void PopulateChannels( )
	{
		try
		{
			// Add channels 1 to 14 to the combobox
			for ( int intChannel = 1; intChannel <= 14; intChannel += 1 )
				m_cboSetChannel.AddItemToList(String.valueOf(intChannel), intChannel);
			
			// Default to channel 1
			m_cboSetChannel.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateVerbosityLevels
	// Abstract: Populates the different verbosity levels the program can display.
	// --------------------------------------------------------------------------------
	private void PopulateVerbosityLevels( )
	{
		try
		{
			m_cboVerbosityLevel.AddItemToList("Verbose", 0);
			m_cboVerbosityLevel.AddItemToList("Really Verbose", 1);
			m_cboVerbosityLevel.AddItemToList("Quiet", 2);
			
			m_cboVerbosityLevel.SetSelectedIndex( 0 );
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
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnNetworkBSSID )			btnNetworkBSSID_Click( );
				else if ( aeSource.getSource( ) == m_btnSetNetworkESSID )	btnSetNetworkESSID_Click( );
				else if ( aeSource.getSource( ) == m_btnSetOutFile )		btnSetOutFile_Click( );
				else if ( aeSource.getSource( ) == m_btnRestoreSession )	btnRestoreSession_Click( );
				else if ( aeSource.getSource( ) == m_chkDoNotSendNACKs )	chkDoNotSendNACKs_Click( );
				else if ( aeSource.getSource( ) == m_chkAlwaysSendNACKs )	chkAlwaysSendNACKs_Click( );
				else if ( aeSource.getSource( ) == m_btnStart )				btnStart_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnNetworkBSSID_Click
	// Abstract: Display a choose network dialog and set the result to the textbox
	// --------------------------------------------------------------------------------
	private void btnNetworkBSSID_Click( )
	{
		try
		{
			DSelectNetwork dlgSelectNetworkBSSID = new DSelectNetwork( );
			dlgSelectNetworkBSSID.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID);
			dlgSelectNetworkBSSID.setVisible( true );
			m_txtNetworkBSSID.setText( dlgSelectNetworkBSSID.GetSelectedNetwork( ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetNetworkBSSID
	// Abstract: Allows an outside form to set the network BSSID. Used by Discover
	//			 Networks.
	// --------------------------------------------------------------------------------
	public void SetNetworkBSSID(String strNetworkBSSID)
	{
		try
		{
			m_strPassedInNetworkBSSID = strNetworkBSSID;
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
		m_txtNetworkBSSID.setText( m_strPassedInNetworkBSSID );
	}

	// --------------------------------------------------------------------------------
	// Name: btnSetNetworkESSID_Click
	// Abstract: Display a choose network dialog and set the result to the textbox
	// --------------------------------------------------------------------------------
	private void btnSetNetworkESSID_Click( )
	{
		try
		{
			DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
			dlgSelectNetwork.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_ESSID);
			dlgSelectNetwork.setVisible( true );
			m_txtSetNetworkESSID.setText( dlgSelectNetwork.GetSelectedNetwork( ) );
			if ( m_txtSetNetworkESSID.getText().equals("") == false )
				m_chkSetNetworkESSID.setSelected( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSetOutFile_Click
	// Abstract: Show a dialog to specify an output location
	// --------------------------------------------------------------------------------
	private void btnSetOutFile_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtSetOutFile, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnRestoreSession_Click
	// Abstract: Selects a session file to restore from
	// --------------------------------------------------------------------------------
	private void btnRestoreSession_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtRestoreSession, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: chkDoNotSendNACKs_Click
	// Abstract: If enabled, disable always send NACKs. If disabled, allow for always
	//			 send NACKs.
	// --------------------------------------------------------------------------------
	private void chkDoNotSendNACKs_Click( )
	{
		try
		{
			UpdateNACKCheckBoxes( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: UpdateNACKCheckBoxes
	// Abstract: If one checkbox is checked, disable the other. If none are checked,
	//			 allow both.
	// --------------------------------------------------------------------------------
	private void UpdateNACKCheckBoxes( )
	{
		try
		{
			if ( m_chkAlwaysSendNACKs.isSelected( ) )
			{
				m_chkDoNotSendNACKs.setSelected( false );
				m_chkDoNotSendNACKs.setEnabled( false );
			}
			else if ( m_chkDoNotSendNACKs.isSelected( ) )
			{
				m_chkAlwaysSendNACKs.setSelected( false );
				m_chkAlwaysSendNACKs.setEnabled( false );
			}
			else if ( m_chkAlwaysSendNACKs.isSelected( ) == false && m_chkDoNotSendNACKs.isSelected( ) == false )
			{
				m_chkAlwaysSendNACKs.setEnabled( true );
				m_chkDoNotSendNACKs.setEnabled( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: chkAlwaysSendNACKs_Click
	// Abstract: If enabled, disable never send NACKs. If disabled, allow for never
	//			 send NACKs.
	// --------------------------------------------------------------------------------
	private void chkAlwaysSendNACKs_Click( )
	{
		try
		{
			UpdateNACKCheckBoxes( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Starts the Reaver attack in a new window
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			String astrCommand[] = new String[] {"reaver"};
			
			// Add the required settings
			astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_cboMonitorInterface.GetSelectedItemName(), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentToCommand("b", m_txtNetworkBSSID.getText().trim(), astrCommand);
			
			// Add the optional settings
			astrCommand = AddOptionalSettings( astrCommand );
				
			// Add the advanced settings
			astrCommand = AddAdvancedSettings( astrCommand );
			
			// Run the command and show the results
			DProgramOutput dlgProgramOutput = new DProgramOutput("Reaver Output", astrCommand);
			dlgProgramOutput.setVisible( true );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddOptionalSettings
	// Abstract: Adds the optional settings to the command
	// --------------------------------------------------------------------------------
	private String[] AddOptionalSettings( String astrCommand[] )
	{
		String astrNewArray[] = null;
		try
		{
			astrNewArray = astrCommand;
			
			// Host System MAC
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkSetHostSystemMAC, "m", m_txtSetHostSystemMAC.getText(), astrNewArray);
			
			// Network ESSID
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkSetNetworkESSID, "e", m_txtSetNetworkESSID.getText(), astrNewArray);
			
			// Channel
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkSetChannel, "c", m_cboSetChannel.GetSelectedItemName(), astrNewArray);

			// Output File
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkSetOutFile, "o", m_txtSetOutFile.getText(), astrNewArray);

			// Restore Session
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkRestoreSession, "s", m_txtRestoreSession.getText(), astrNewArray);

			// Execute Command
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkExecuteCommand, "C", m_txtExecuteCommand.getText(), astrNewArray);
			
			// Daemonize
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkDaemonize, "D", "", astrNewArray);
			
			// Auto-detect Settings
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkAutoDetectSettings, "a", "", astrNewArray);
			
			// Fixed Channel
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chkFixedChannel, "f", "", astrNewArray);
			
			// 5 GHz Channels
			astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chk5GHzChannels, "5", "", astrNewArray);
			
			// Verbosity
			if ( m_chkSetVerbosity.isSelected( ) == true )
			{
				if ( m_cboVerbosityLevel.GetSelectedItemValue( ) == 0 )			// Verbose
					astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chk5GHzChannels, "v", "", astrNewArray);
				else if ( m_cboVerbosityLevel.GetSelectedItemValue( ) == 1 )	// Really Verbose
					astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chk5GHzChannels, "vv", "", astrNewArray);
				else if ( m_cboVerbosityLevel.GetSelectedItemValue( ) == 2 )	// Quiet
					astrNewArray = CAircrackUtilities.AddArgumentIfChecked(m_chk5GHzChannels, "q", "", astrNewArray);
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewArray;
	}

	// --------------------------------------------------------------------------------
	// Name: AddAdvancedSettings
	// Abstract: Adds the advanced settings to the command
	// --------------------------------------------------------------------------------
	private String[] AddAdvancedSettings( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			
			astrNewCommand = astrCommand;
			
			// WPS Pin
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyWPSPin, "p", m_txtSpecifyWPSPin.getText(), astrNewCommand);
			
			// Delay Between Attempts
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetDelayBetweenAttempts, "d", m_txtSetDelayBetweenAttempts.getText(), astrNewCommand);
			
			// Lock Delay
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkLockDelay, "l", m_txtLockDelay.getText(), astrNewCommand);
			
			// Max Attempts
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetMaxAttempts, "g", m_txtSetMaxAttempts.getText(), astrNewCommand);
			
			// Fail Wait
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetFailWait, "x", m_txtSetFailWait.getText(), astrNewCommand);

			// Recurring Delay
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetRecurringDelay, "r", m_txtSetRecurringDelay.getText(), astrNewCommand);

			// Timeout
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSetTimeout, "t", m_txtSetTimeout.getText(), astrNewCommand);
			
			// M57 Timeout
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkM57Timeout, "T", m_txtM57Timeout.getText(), astrNewCommand);
			
			// Do Not Associate
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDoNotAssociate, "A", "", astrNewCommand);
			
			// Do Not Send NACKs
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDoNotSendNACKs, "N", "", astrNewCommand);
			
			// Use Small DH Keys
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseSmallDHKeys, "S", "", astrNewCommand);
			
			// Ignore Locks
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkIgnoreLocks, "L", "", astrNewCommand);
			
			// EAP Terminate
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkEAPTerminate, "E", "", astrNewCommand);
			
			// Always Send NACKs
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkAlwaysSendNACKs, "n", "", astrNewCommand);
			
			// Mimick Windows 7 Registrar
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkMimickWindows7Registrar, "w", "", astrNewCommand);

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}
}