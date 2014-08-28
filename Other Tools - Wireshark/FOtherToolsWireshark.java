// --------------------------------------------------------------------------------
// Name: FOtherToolsWireshark
// Abstract: A graphical interface for starting Wireshark
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class FOtherToolsWireshark extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	private JCheckBox m_chkCaptureAutostopCondition = null;
	private CComboBox m_cboAutostopConditionType = null;
	private CTextBox m_txtAutostopConditionValue = null;
	private JLabel m_lblAutostopConditionUnit = null;
	private final String m_astrAUTOSTOP_CONDITIONS[] = new String[] {"duration", "filesize", "files"};
	
	private JCheckBox m_chkEnableMultipleFilesMode = null;
	private CComboBox m_cboMultipleFilesModeType = null;
	private CTextBox m_txtMultipleFilesModeValue = null;
    private JLabel m_lblMultipleFilesModeUnit = null;    
    private JCheckBox m_chkSpecifyPacketLimit = null;
    private CTextBox m_txtSpecifyPacketLimit = null;
    
    private JCheckBox m_chkSpecifyConfigurationFile = null;
    private CTextBox m_txtSpecifyConfigurationFile = null;
    private JButton m_btnSpecifyConfigurationFile = null;
	
    private JCheckBox m_chkPrintCapturableDevices = null;
    
    private JCheckBox m_chkSpecifyXServerDisplay = null;
    private CTextBox m_txtSpecifyXServerDisplay = null;
    
    private JCheckBox m_chkSpecifyFilter = null;
    private CTextBox m_txtSpecifyFilter = null;
    
    private JCheckBox m_chkGoToPacket = null;
    private CTextBox m_txtGoToPacket = null;
    
    private JCheckBox m_chkPrintOptionsAndHelp = null;
    
    private JCheckBox m_chkHideCaptureInfoDialog = null;
    
    private JCheckBox m_chkSpecifyInterface = null;
    private CComboBox m_cboSpecifyInterface = null;
    
    private JCheckBox m_chkSpecifyJumpFilter = null;
    private CTextBox m_txtSpecifyJumpFilter = null;
    
    private JCheckBox m_chkJumpFilterPacketBefore = null;
    
    private JCheckBox m_chkStartCaptureImmediately = null;
    
    private JCheckBox m_chkLoadKerberosCryptoKeys = null;
    private CTextBox m_txtLoadKerberosCryptoKeys = null;
    private JButton m_btnLoadKerberosCryptoKeys = null;
    
    private JCheckBox m_chkEnableAutoScrolling = null;
    
    private JCheckBox m_chkListDataLinkTypes = null;
    
    private JCheckBox m_chkSpecifyFont = null;
    private CComboBox m_cboSpecifyFont = null;
    
    private JCheckBox m_chkDisableNameResolution = null;

    private JCheckBox m_chkSpecificNameResolution = null;
    private CTextBox m_txtSpecificNameResolution = null;
    private CComboBox m_cboSpecificNameResolution = null;
    private JButton m_btnSpecificNameResolution = null;
    
    private JCheckBox m_chkDontUsePromiscuousMode = null;
    
    private JCheckBox m_chkCloseProgramAfterCapture = null;
    
    private JCheckBox m_chkReadInputFromFile = null;
    private CTextBox m_txtReadInputFromFile = null;
    private JButton m_btnReadInputFromFile = null;
    
    private JCheckBox m_chkSpecifyReadFilter = null;
    private CTextBox m_txtSpecifyReadFilter = null;
    
    private JCheckBox m_chkAutoUpdatePacketDisplay = null;
    
    private JCheckBox m_chkSnapshotLength = null;
    private CTextBox m_txtSnapshotLength = null;
    
    private JCheckBox m_chkSetPacketTimestampFormat = null;
    private CComboBox m_cboSetPacketTimestampFormat = null;
    private final String m_astrTimestampFormats[] = {"ad", "a", "r", "d", "dd", "e"};
    
    private JCheckBox m_chkDisplayVersionInfo = null;
    
    private JButton m_btnStart = null;
    
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsWireshark
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsWireshark()
	{
		super("Wireshark", 600, 670, false, false, "Wireshark");
		try
		{
			AddControls( );
			AddAutoStopConditionTypes( );
			AddMultipleFileModeTypes( );
			AddAvailableInterfaces( );
			AddFontsToDropdown( );
			AddSpecificNameResolutions( );
			AddTimestampFormats( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the form
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
            m_chkPrintCapturableDevices = CUtilities.AddCheckBox(m_cntControlContainer, this, "Print Capturable Devices", 45, 10);
            m_chkPrintOptionsAndHelp = CUtilities.AddCheckBox(m_cntControlContainer, this, "Print Options and Help", 45, 250);
            m_chkHideCaptureInfoDialog = CUtilities.AddCheckBox(m_cntControlContainer, this, "Hide Capture Info Dialog", 70, 10);
            m_chkJumpFilterPacketBefore = CUtilities.AddCheckBox(m_cntControlContainer, this, "Jump Filter - Select Packet Before", 70, 250);
            m_chkStartCaptureImmediately = CUtilities.AddCheckBox(m_cntControlContainer, this, "Start Capture Immediately", 95, 10);
            m_chkEnableAutoScrolling = CUtilities.AddCheckBox(m_cntControlContainer, this, "Enabled Auto-scrolling", 95, 250);
            m_chkListDataLinkTypes = CUtilities.AddCheckBox(m_cntControlContainer, this, "List Data Link Types", 120, 10);
            m_chkDisableNameResolution = CUtilities.AddCheckBox(m_cntControlContainer, this, "Disable Name Resolution", 120, 250);
            m_chkDontUsePromiscuousMode = CUtilities.AddCheckBox(m_cntControlContainer, this, "Don't Use Promiscuous Mode", 145, 10);
            m_chkCloseProgramAfterCapture = CUtilities.AddCheckBox(m_cntControlContainer, this, "Close Program After Capture", 145, 250);
            m_chkAutoUpdatePacketDisplay = CUtilities.AddCheckBox(m_cntControlContainer, this, "Auto-update Packet Display", 170, 10);
            m_chkDisplayVersionInfo = CUtilities.AddCheckBox(m_cntControlContainer, this, "Display Version Info", 170, 250);
            m_chkCaptureAutostopCondition = CUtilities.AddCheckBox(m_cntControlContainer, this, "Capture Autostop Condition:", 195, 10);
            m_cboAutostopConditionType = CUtilities.AddComboBox(m_cntControlContainer, null, 197, 240, 18, 100 );
            m_txtAutostopConditionValue = CUtilities.AddTextBox(m_cntControlContainer, null, "", 8, 10, 197, 345);
            m_lblAutostopConditionUnit = CUtilities.AddLabel(m_cntControlContainer, "sec", 197, 440);
            m_cboAutostopConditionType.SetActionListener(this);
            m_chkEnableMultipleFilesMode = CUtilities.AddCheckBox(m_cntControlContainer, this, "Enable Multiple Files:", 220, 10);
            m_cboMultipleFilesModeType = CUtilities.AddComboBox(m_cntControlContainer, null, 222, 190, 18, 100);
            m_txtMultipleFilesModeValue = CUtilities.AddTextBox(m_cntControlContainer, null, "", 8, 10, 222, 295);
            m_lblMultipleFilesModeUnit = CUtilities.AddLabel(m_cntControlContainer, "sec", 224, 390);
            m_cboMultipleFilesModeType.SetActionListener(this);
            m_chkSpecifyPacketLimit = CUtilities.AddCheckBox(m_cntControlContainer, this, "Limit Packets:", 245, 10);
            m_txtSpecifyPacketLimit = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 247, 140);
            m_chkSpecifyConfigurationFile = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Config File:", 270, 10);
            m_txtSpecifyConfigurationFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 100, 272, 175);
            m_btnSpecifyConfigurationFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 272, 305, 18, 40);
            m_chkSpecifyXServerDisplay = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify X Server Display:", 295, 10);
            m_txtSpecifyXServerDisplay = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 297, 215);
            m_chkSpecifyFilter = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Filter:", 320, 10);
            m_txtSpecifyFilter = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 322, 135);
            m_chkGoToPacket = CUtilities.AddCheckBox(m_cntControlContainer, this, "Go To Packet:", 345, 10);
            m_txtGoToPacket = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 347, 135);
            m_chkSpecifyInterface = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Interface:", 370, 10);
            m_cboSpecifyInterface = CUtilities.AddComboBox(m_cntControlContainer, null, 372, 165, 18, 135);
            m_cboSpecifyInterface.SetSorted( true );
            
            m_chkSpecifyJumpFilter = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Jump Filter:", 395, 10);
            m_txtSpecifyJumpFilter = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 397, 175);
            m_chkLoadKerberosCryptoKeys = CUtilities.AddCheckBox(m_cntControlContainer, this, "Load Kerberos Crypto Keys:", 420, 10);
            m_txtLoadKerberosCryptoKeys = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 100, 422, 240);
            m_btnLoadKerberosCryptoKeys = CUtilities.AddButton(m_cntControlContainer, this, "...", 422, 370, 18, 40);
            m_chkSpecifyFont = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Font:", 445, 10);
            m_cboSpecifyFont = CUtilities.AddComboBox(m_cntControlContainer, null, 447, 130, 18, 175);
            m_chkSpecificNameResolution = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specific Name Resolution:", 470, 10);
            m_txtSpecificNameResolution = CUtilities.AddTextBox(m_cntControlContainer, null, "", 10, 10, 472, 220);
            m_cboSpecificNameResolution = CUtilities.AddComboBox(m_cntControlContainer, null, 472, 340, 18, 150);
            m_btnSpecificNameResolution = CUtilities.AddButton(m_cntControlContainer, this, "Add", 472, 500, 18, 75);
            m_chkReadInputFromFile = CUtilities.AddCheckBox(m_cntControlContainer, this, "Read Input From File:", 495, 10);
            m_txtReadInputFromFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 100, 497, 190);
            m_btnReadInputFromFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 497, 320, 18, 40);
            m_chkSpecifyReadFilter = CUtilities.AddCheckBox(m_cntControlContainer, this, "Specify Read Filter:", 520, 10);
            m_txtSpecifyReadFilter = CUtilities.AddTextBox(m_cntControlContainer, null, "", 11, 15, 522, 175);
            m_chkSnapshotLength = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Snapshot Length:", 545, 10);
            m_txtSnapshotLength = CUtilities.AddTextBox(m_cntControlContainer, null, "0", 11, 15, 547, 195);
            m_chkSetPacketTimestampFormat = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Packet Timestamp Format:", 570, 10);
            m_cboSetPacketTimestampFormat = CUtilities.AddComboBox(m_cntControlContainer, null, 572, 255, 18, 175);
            
            m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 600, 250, 18, 100);
            
            m_lstParameters.add(new CProfileParameter("PrintCapturableDevices", m_chkPrintCapturableDevices));
            m_lstParameters.add(new CProfileParameter("PrintOptionsAndHelp", m_chkPrintOptionsAndHelp));
            m_lstParameters.add(new CProfileParameter("HideCaptureInfoDialog", m_chkHideCaptureInfoDialog));
            m_lstParameters.add(new CProfileParameter("JumpFilterPacketBefore", m_chkJumpFilterPacketBefore));
            m_lstParameters.add(new CProfileParameter("StartCaptureImmediately", m_chkStartCaptureImmediately));
            m_lstParameters.add(new CProfileParameter("EnableAutoScrolling", m_chkEnableAutoScrolling));
            m_lstParameters.add(new CProfileParameter("ListDataLinkTypes", m_chkListDataLinkTypes));
            m_lstParameters.add(new CProfileParameter("DisableNameResolution", m_chkDisableNameResolution));
            m_lstParameters.add(new CProfileParameter("DontUsePromiscuousMode", m_chkDontUsePromiscuousMode));
            m_lstParameters.add(new CProfileParameter("CloseProgramAfterCapture", m_chkCloseProgramAfterCapture));
            m_lstParameters.add(new CProfileParameter("AutoUpdatePacketDisplay", m_chkAutoUpdatePacketDisplay));
            m_lstParameters.add(new CProfileParameter("DisplayVersionInfo", m_chkDisplayVersionInfo));
            m_lstParameters.add(new CProfileParameter("AutoStopCondition", m_chkCaptureAutostopCondition, m_cboAutostopConditionType, m_txtAutostopConditionValue));
            m_lstParameters.add(new CProfileParameter("MultipleFilesMode", m_chkEnableMultipleFilesMode, m_cboMultipleFilesModeType, m_txtMultipleFilesModeValue));
            m_lstParameters.add(new CProfileParameter("PacketLimit", m_chkSpecifyPacketLimit, m_txtSpecifyPacketLimit));
            m_lstParameters.add(new CProfileParameter("ConfigurationFile", m_chkSpecifyConfigurationFile, m_txtSpecifyConfigurationFile));
            m_lstParameters.add(new CProfileParameter("XServerDisplay", m_chkSpecifyXServerDisplay, this.m_txtSpecifyXServerDisplay));
            m_lstParameters.add(new CProfileParameter("Filter", m_chkSpecifyFilter, m_txtSpecifyFilter));
            m_lstParameters.add(new CProfileParameter("GoToPacket", m_chkGoToPacket, m_txtGoToPacket));
            m_lstParameters.add(new CProfileParameter("Interface", m_chkSpecifyInterface, m_cboSpecifyInterface));
            m_lstParameters.add(new CProfileParameter("JumpFilter", m_chkSpecifyJumpFilter, m_txtSpecifyJumpFilter));
            m_lstParameters.add(new CProfileParameter("LoadKerberosCryptoKeys", m_chkLoadKerberosCryptoKeys, m_txtLoadKerberosCryptoKeys));
            m_lstParameters.add(new CProfileParameter("Font", m_chkSpecifyFont, m_cboSpecifyFont));
            m_lstParameters.add(new CProfileParameter("NameResolution", m_chkSpecificNameResolution, m_txtSpecificNameResolution));
            m_lstParameters.add(new CProfileParameter("ReadInputFromFile", m_chkReadInputFromFile, m_txtReadInputFromFile));
            m_lstParameters.add(new CProfileParameter("ReadFilter", m_chkSpecifyReadFilter, m_txtSpecifyReadFilter));
            m_lstParameters.add(new CProfileParameter("SnapshotLength", m_chkSnapshotLength, m_txtSnapshotLength));
            m_lstParameters.add(new CProfileParameter("PacketTimestampFormat", m_chkSetPacketTimestampFormat, m_cboSetPacketTimestampFormat));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddAutoStopConditionTypes
	// Abstract: Adds the auto-stop condition types to the dropdown
	// --------------------------------------------------------------------------------
	private void AddAutoStopConditionTypes( )
	{
		try
		{
			m_cboAutostopConditionType.AddItemToList("Duration", 0);
			m_cboAutostopConditionType.AddItemToList("Filesize", 1);
			m_cboAutostopConditionType.AddItemToList("File Count", 2);
			m_cboAutostopConditionType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddMultipleFileModeTypes
	// Abstract: Adds the modes for specifying multiple files to the dropdown
	// --------------------------------------------------------------------------------
	private void AddMultipleFileModeTypes( )
	{
		try
		{
			m_cboMultipleFilesModeType.AddItemToList("Duration", 0);
			m_cboMultipleFilesModeType.AddItemToList("Filesize", 1);
			m_cboMultipleFilesModeType.AddItemToList("File Count", 2);
			m_cboMultipleFilesModeType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddAvailableInterfaces
	// Abstract: Adds the available interfaces to the dropdown
	// --------------------------------------------------------------------------------
	private void AddAvailableInterfaces( )
	{
		try
		{
			String astrCommand[] = new String[] {"wireshark", "-D"};
			CProcess clsAvailableCaptureInterfaces = new CProcess(astrCommand, true, true, false);
			BufferedReader brResults = new BufferedReader(clsAvailableCaptureInterfaces.GetOutput());
			String strBuffer = "";
			String strCleanedInterface = "";
			
			while ( strBuffer != null )
			{
				strCleanedInterface = "";
				strBuffer = brResults.readLine();
				if ( strBuffer != null )
				{
					if ( strBuffer.equals("Capture-Message: Capture Interface List ...") == false )
					{
						strCleanedInterface = strBuffer.substring(strBuffer.indexOf(" ") + 1);
						if ( strCleanedInterface.indexOf(" ") > 0 )
							strCleanedInterface = strCleanedInterface.substring(1, strCleanedInterface.indexOf(" "));
						
						m_cboSpecifyInterface.AddItemToList(strCleanedInterface, 0);
					}
				}
			}
			
			clsAvailableCaptureInterfaces.CloseProcess();
			m_cboSpecifyInterface.SetSelectedIndex(0);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddFontsToDropdown
	// Abstract: Adds the system's fonts to the dropdown
	// --------------------------------------------------------------------------------
	private void AddFontsToDropdown( )
	{
		try
		{
			GraphicsEnvironment geEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			String astrFontFamilies[] = geEnvironment.getAvailableFontFamilyNames();
			int intIndex = 0;
			
			for ( intIndex = 0; intIndex < astrFontFamilies.length; intIndex += 1)
			{
				m_cboSpecifyFont.AddItemToList(astrFontFamilies[intIndex], 0);
			}
			
			m_cboSpecifyFont.SetSelectedIndex(0);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddSpecificNameResolutions
	// Abstract: Adds the options for specific name resolution to the dropdown
	// --------------------------------------------------------------------------------
	private void AddSpecificNameResolutions( )
	{
		try
		{

			m_cboSpecificNameResolution.AddItemToList("MAC Address", (int)'m');
			m_cboSpecificNameResolution.AddItemToList("Network Address", (int)'n');
			m_cboSpecificNameResolution.AddItemToList("Transport Layer", (int)'t');
			m_cboSpecificNameResolution.AddItemToList("Concurrent DNS", (int)'C');
			m_cboSpecificNameResolution.SetSelectedIndex(0);
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddTimestampFormats
	// Abstract: Adds the different formats of a timestamp to the dropdown
	// --------------------------------------------------------------------------------
	private void AddTimestampFormats( )
	{
		try
		{
			m_cboSetPacketTimestampFormat.AddItemToList("Absolute with date", 0);
			m_cboSetPacketTimestampFormat.AddItemToList("Absolute", 1);
			m_cboSetPacketTimestampFormat.AddItemToList("Relative", 2);
			m_cboSetPacketTimestampFormat.AddItemToList("Delta", 3);
			m_cboSetPacketTimestampFormat.AddItemToList("Delta displayed", 4);
			m_cboSetPacketTimestampFormat.AddItemToList("Epoch", 5);
			m_cboSetPacketTimestampFormat.SetSelectedIndex(2);
	    }
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Combobox changes and button clicks
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource() == m_cboAutostopConditionType.GetJavaComboBox() ) cboAutostopConditionType_SelectedIndexChanged( );
				else if ( aeSource.getSource() == m_cboMultipleFilesModeType.GetJavaComboBox() ) cboMultipleFilesModeType_SelectedIndexChanged( );
				else if ( aeSource.getSource() == m_btnSpecifyConfigurationFile ) btnSpecifyConfigurationFile_Click( );
				else if ( aeSource.getSource() == m_btnLoadKerberosCryptoKeys ) btnLoadKerberosCryptoKeys_Click( );
				else if ( aeSource.getSource() == m_btnSpecificNameResolution ) btnSpecificNameResolution_Click( );
				else if ( aeSource.getSource() == m_btnReadInputFromFile ) btnReadInputFromFile_Click( );
				else if ( aeSource.getSource() == m_btnStart ) btnStart_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboAutostopConditionType_SelectedIndexChanged
	// Abstract: Autostop condition type dropdown selected index changed. Changes the
	//			 unit label to match the selected dropdown value
	// --------------------------------------------------------------------------------
	private void cboAutostopConditionType_SelectedIndexChanged( )
	{
		try
		{
			CNameValuePair clsSelectedValue = m_cboAutostopConditionType.GetSelectedItem();
			if ( clsSelectedValue.GetName().equals("Duration") == true )
				m_lblAutostopConditionUnit.setText("sec");
			else if ( clsSelectedValue.GetName().equals("Filesize") == true )
				m_lblAutostopConditionUnit.setText("KB");
			else if ( clsSelectedValue.GetName().equals("File Count") == true )
				m_lblAutostopConditionUnit.setText("files");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboMultipleFilesModeType_SelectedIndexChanged
	// Abstract: Multiple files mode dropdown selected index changed. Changes the
	//			 unit label to match the selected dropdown value
	// --------------------------------------------------------------------------------
	private void cboMultipleFilesModeType_SelectedIndexChanged( )
	{
		try
		{
			CNameValuePair clsSelectedValue = m_cboMultipleFilesModeType.GetSelectedItem();
			if ( clsSelectedValue.GetName() == "Duration")
				m_lblMultipleFilesModeUnit.setText("sec");
			else if ( clsSelectedValue.GetName() == "Filesize")
				m_lblMultipleFilesModeUnit.setText("KB");
			else if ( clsSelectedValue.GetName() == "File Count")
				m_lblMultipleFilesModeUnit.setText("files");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSpecifyConfigurationFile_Click
	// Abstract: Displays a file chooser dialog to specify the location of the config
	//			 file.
	// --------------------------------------------------------------------------------
	private void btnSpecifyConfigurationFile_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtSpecifyConfigurationFile, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnLoadKerberosCryptoKeys_Click
	// Abstract: Displays a file chooser for specifying the location of Kerberos Crypto
	//			 Keys.
	// --------------------------------------------------------------------------------
	private void btnLoadKerberosCryptoKeys_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtLoadKerberosCryptoKeys, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSpecificNameResolution_Click
	// Abstract: Adds the selected name resolution to the textbox
	// --------------------------------------------------------------------------------
	private void btnSpecificNameResolution_Click( )
	{
		try
		{
			char chrNewValue = (char)m_cboSpecificNameResolution.GetSelectedItem().GetValue();
			if ( m_txtSpecificNameResolution.getText().indexOf(chrNewValue) < 0 )
			{
				m_txtSpecificNameResolution.setText(m_txtSpecificNameResolution.getText() + "" + chrNewValue);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnReadInputFromFile_Click
	// Abstract: Specifies the file to read packets in from
	// --------------------------------------------------------------------------------
	private void btnReadInputFromFile_Click( )
	{
		try
		{
			CAircrackUtilities.DisplayFileChooser(m_txtReadInputFromFile, this);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Start button Click event. Starts the wireshark process.
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			String astrCommand[] = new String[] {"wireshark"};
			
			// Add all of the selected options
			if ( m_chkPrintCapturableDevices.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("D", "", astrCommand);
            
			if ( m_chkPrintOptionsAndHelp.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("h", "", astrCommand);

			if ( m_chkHideCaptureInfoDialog.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("H", "", astrCommand);

			if ( m_chkJumpFilterPacketBefore.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("j", "", astrCommand);

			if ( m_chkStartCaptureImmediately.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("K", "", astrCommand);

			if ( m_chkEnableAutoScrolling.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("l", "", astrCommand);

			if ( m_chkListDataLinkTypes.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("L", "", astrCommand);

			if ( m_chkDisableNameResolution.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("D", "", astrCommand);

			if ( m_chkDontUsePromiscuousMode.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("p", "", astrCommand);

			if ( m_chkCloseProgramAfterCapture.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("Q", "", astrCommand);

			if ( m_chkAutoUpdatePacketDisplay.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("S", "", astrCommand);

			if ( m_chkDisplayVersionInfo.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("v", "", astrCommand);

			if ( m_chkCaptureAutostopCondition.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("a", m_astrAUTOSTOP_CONDITIONS[m_cboAutostopConditionType.GetSelectedItemValue()] + ":" + m_txtAutostopConditionValue.getText().trim(), astrCommand);

			if ( m_chkEnableMultipleFilesMode.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("b", m_astrAUTOSTOP_CONDITIONS[m_cboMultipleFilesModeType.GetSelectedItemValue()] + ":" + m_txtMultipleFilesModeValue.getText().trim(), astrCommand);

			if ( m_chkSpecifyPacketLimit.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("c", m_txtSpecifyPacketLimit.getText().trim(), astrCommand);

			if ( m_chkSpecifyConfigurationFile.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("C", m_txtSpecifyConfigurationFile.getText().trim(), astrCommand);

			if ( m_chkSpecifyXServerDisplay.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("-display=" + m_txtSpecifyXServerDisplay.getText().trim(), "", astrCommand);

			if ( m_chkSpecifyFilter.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("f", m_txtSpecifyFilter.getText().trim(), astrCommand);

			if ( m_chkGoToPacket.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("g", m_txtGoToPacket.getText().trim(), astrCommand);

			if ( m_chkSpecifyInterface.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_cboSpecifyInterface.GetSelectedItemName(), astrCommand);

			if ( m_chkSpecifyJumpFilter.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("J", m_txtSpecifyJumpFilter.getText().trim(), astrCommand);

			if ( m_chkLoadKerberosCryptoKeys.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("k", m_txtLoadKerberosCryptoKeys.getText().trim(), astrCommand);

			if ( m_chkSpecifyFont.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("m", m_cboSpecifyFont.GetSelectedItemName(), astrCommand);

			if ( m_chkSpecificNameResolution.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("N", m_txtSpecificNameResolution.getText( ).trim( ), astrCommand);

			if ( m_chkReadInputFromFile.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_txtReadInputFromFile.getText( ).trim( ), astrCommand);

			if ( m_chkSpecifyReadFilter.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("R", m_txtSpecifyReadFilter.getText( ).trim( ), astrCommand);

			if ( m_chkSnapshotLength.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("s", m_txtSnapshotLength.getText( ).trim( ), astrCommand);

			if ( m_chkSetPacketTimestampFormat.isSelected( ) == true )
				astrCommand = CAircrackUtilities.AddArgumentToCommand("t", m_astrTimestampFormats[m_cboSetPacketTimestampFormat.GetSelectedItemValue()], astrCommand);
			
			DProgramOutput dlgWireshark = new DProgramOutput("Wireshark - Output", astrCommand);
			dlgWireshark.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
