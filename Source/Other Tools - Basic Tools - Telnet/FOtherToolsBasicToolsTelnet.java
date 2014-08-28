// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsTelnet
// Abstract: Graphical interface for the telnet tool
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FOtherToolsBasicToolsTelnet extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CTextBox m_txtTarget = null;
	private CTextBox m_txtPort = null;
	private JCheckBox m_chkRequest8BitOperation = null; // -8
	private JCheckBox m_chkDisableEscapeCharacter = null; // -E
	private JCheckBox m_chk8BitOutputDataPath = null; // -L
	private JCheckBox m_chkAttemptAutomaticLogin = null; // -a
	private JCheckBox m_chkDebuggingMode = null; // -d
	private JCheckBox m_chkEmulateRlogin = null; // -r
	private JCheckBox m_chkForceIPVersion = null; // -4, -6
	private CComboBox m_cboForceIPVersion = null;
	private JCheckBox m_chkBindLocalSocket = null; // -b
	private CTextBox m_txtBindLocalSocket = null;
	private JCheckBox m_chkTypeOfService = null; // -S
	private CTextBox m_txtTypeOfService = null;
	private JCheckBox m_chkEscapeCharacter = null; // -e
	private CTextBox m_txtEscapeCharacter = null;
	private JCheckBox m_chkLoginName = null; // -l
	private CTextBox m_txtLoginName = null;
	private JCheckBox m_chkTraceFile = null; // -n
	private CTextBox m_txtTraceFile = null;
	private JButton m_btnTraceFile = null;
	private JButton m_btnGo = null;
	
	private String m_strPassedInTarget = "";
	
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsTelnet
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsBasicToolsTelnet( )
	{
		super( "Telnet", 440, 330, false, false, "Telnet" );
		try
		{
			AddControls( );
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
			CUtilities.AddLabel( m_cntControlContainer, "Target:", 51, 5 );
			m_txtTarget = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 100, 50, 60 );
			CUtilities.AddLabel(m_cntControlContainer, "Port:", 51, 235);
			m_txtPort = CUtilities.AddTextBox(m_cntControlContainer, null, "23", 4, 5, 50, 275);
			
			m_chkRequest8BitOperation = CUtilities.AddCheckBox(m_cntControlContainer, null, "Request 8-Bit Operation", 70, 5);
			m_chkDisableEscapeCharacter = CUtilities.AddCheckBox(m_cntControlContainer, null, "Disable Escape Character", 70, 220);
			m_chk8BitOutputDataPath = CUtilities.AddCheckBox(m_cntControlContainer, null, "8-Bit Output Path", 90, 5);
			m_chkAttemptAutomaticLogin = CUtilities.AddCheckBox(m_cntControlContainer, null, "Attempt Automatic Login", 90, 220);
			m_chkDebuggingMode = CUtilities.AddCheckBox(m_cntControlContainer, null, "Debugging Mode", 110, 5);
			m_chkEmulateRlogin = CUtilities.AddCheckBox(m_cntControlContainer, null, "Emulate R-Login", 110, 220);
			
			m_chkForceIPVersion = CUtilities.AddCheckBox(m_cntControlContainer, null, "Force IP Version:", 130, 5);
			m_cboForceIPVersion = CUtilities.AddComboBox(m_cntControlContainer, null, 132, 150, 18, 50);
			m_chkBindLocalSocket = CUtilities.AddCheckBox(m_cntControlContainer, null, "Bind Local Socket:", 150, 5);
			m_txtBindLocalSocket = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 152, 160);
			m_chkTypeOfService = CUtilities.AddCheckBox(m_cntControlContainer, null, "Type Of Service:", 171, 5);
			m_txtTypeOfService = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 173, 145);
			m_chkEscapeCharacter = CUtilities.AddCheckBox(m_cntControlContainer, null, "Escape Character:", 190, 5);
			m_txtEscapeCharacter = CUtilities.AddTextBox(m_cntControlContainer, null, "", 2, 1, 193, 160);
			m_chkLoginName = CUtilities.AddCheckBox(m_cntControlContainer, null, "Login Name:", 210, 5);
			m_txtLoginName = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 213, 120);
			m_chkTraceFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "Trace File:", 230, 5);
			m_txtTraceFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 233, 105);
			m_btnTraceFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 233, 275, 18, 50);
			
			m_btnGo = CUtilities.AddButton(m_cntControlContainer, this, "Go", 270, 170, 18, 100);
			
			m_cboForceIPVersion.AddItemToList("4", 4);
			m_cboForceIPVersion.AddItemToList("6", 6);
			m_cboForceIPVersion.SetSelectedIndex(0);
			
			m_lstParameters.add(new CProfileParameter("Target", m_txtTarget));
			m_lstParameters.add(new CProfileParameter("Port", m_txtPort));
			m_lstParameters.add(new CProfileParameter("Request8BitOperation", m_chkRequest8BitOperation));
			m_lstParameters.add(new CProfileParameter("DisableEscapeCharacter", m_chkDisableEscapeCharacter));
			m_lstParameters.add(new CProfileParameter("8BitOutputPath", m_chk8BitOutputDataPath));
			m_lstParameters.add(new CProfileParameter("AttemptAutomaticLogin", m_chkAttemptAutomaticLogin));
			m_lstParameters.add(new CProfileParameter("DebuggingMode", m_chkDebuggingMode));
			m_lstParameters.add(new CProfileParameter("EmulateRLogin", m_chkEmulateRlogin));
			m_lstParameters.add(new CProfileParameter("ForceIPVersion", m_chkForceIPVersion, m_cboForceIPVersion));
			m_lstParameters.add(new CProfileParameter("BindLocalSocket", m_chkBindLocalSocket, m_txtBindLocalSocket));
			m_lstParameters.add(new CProfileParameter("TypeOfService", m_chkTypeOfService, m_txtTypeOfService));
			m_lstParameters.add(new CProfileParameter("EscapeCharacter", m_chkEscapeCharacter, m_txtEscapeCharacter));
			m_lstParameters.add(new CProfileParameter("LoginName", m_chkLoginName, m_txtLoginName));
			m_lstParameters.add(new CProfileParameter("TraceFile", m_chkTraceFile, m_txtTraceFile));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks event handler
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( aeSource.getSource( ) == m_btnTraceFile ) CAircrackUtilities.DisplayFileChooser(m_txtTraceFile, this);
			else if ( aeSource.getSource( ) == m_btnGo ) btnGo_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnGo_Click
	// Abstract: Launches the Telnet command
	// --------------------------------------------------------------------------------
	private void btnGo_Click( )
	{
		try
		{
			CTelnetProcess clsTelnet = new CTelnetProcess();
			clsTelnet.SetTarget(m_txtTarget.getText().trim(), Integer.parseInt(m_txtPort.getText().trim()));
			if (m_chkRequest8BitOperation.isSelected())
				clsTelnet.Enable8BitOperation();
			if (m_chkDisableEscapeCharacter.isSelected())
				clsTelnet.DisableEscapeCharacter();
			if (m_chkAttemptAutomaticLogin.isSelected())
				clsTelnet.AttemptAutomaticLogin();
			if (m_chkDebuggingMode.isSelected())
				clsTelnet.EnableDebuggingMode();
			if (m_chkEmulateRlogin.isSelected())
				clsTelnet.EmulateRLogin();
			if (m_chkForceIPVersion.isSelected())
			{
				if (m_cboForceIPVersion.GetSelectedItemValue() == 4)
					clsTelnet.SetIPVersion(CTelnetProcess.udtForceIPVersionType.IPV4);
				else if (m_cboForceIPVersion.GetSelectedItemValue() == 6)
					clsTelnet.SetIPVersion(CTelnetProcess.udtForceIPVersionType.IPV6);
			}
			if (m_chkBindLocalSocket.isSelected())
				clsTelnet.SetBindLocalSocket(m_txtBindLocalSocket.getText().trim());
			if (m_chkTypeOfService.isSelected())
				clsTelnet.SetTypeOfService(m_txtTypeOfService.getText().trim());
			if (m_chkEscapeCharacter.isSelected())
				clsTelnet.SetEscapeCharacter(m_txtEscapeCharacter.getText().trim().charAt(0));
			if (m_chkLoginName.isSelected())
				clsTelnet.SetLoginName(m_txtLoginName.getText().trim());
			if (m_chkTraceFile.isSelected())
				clsTelnet.SetTraceFile(m_txtTraceFile.getText().trim());
			
			clsTelnet.StartTelnet();
			
			DProgramOutput dlgTelnet = new DProgramOutput("Telnet", clsTelnet, true);
			dlgTelnet.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetDestination
	// Abstract: Sets the destination for the telnet (used by outside forms)
	// --------------------------------------------------------------------------------
	public void SetDestination( String strTarget )
	{
		try
		{
			m_strPassedInTarget = strTarget;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: windowOpened
	// Abstract: Sets the specified target text if specified
	// --------------------------------------------------------------------------------
	@Override
	public void windowOpened( WindowEvent weSource )
	{
		super.windowOpened( weSource );
		try
		{
			if ( m_strPassedInTarget.equals("") == false )
			{
				m_txtTarget.setText( m_strPassedInTarget );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
}
