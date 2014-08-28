// Includes
import javax.swing.*;
import org.w3c.dom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Form used to adjust the various settings throughout the program
 * @author Paul Bromwell Jr
 * @since May 6, 2014
 * @version 1.0
 */
public class FSettings extends CAircrackWindow implements ActionListener, MouseListener
{
	protected final static long serialVersionUID = 1L;
	
	private CComboBox m_cboPreferredInterface = null;
	private CComboBox m_cboPreferredMonitorInterface = null;
	private JRadioButton m_rdbLogNoCommands = null;
	private JRadioButton m_rdbLogImportantCommands = null;
	private JRadioButton m_rdbLogAllCommands = null;
	
	private JCheckBox m_chkSavedProfilesDiscoverNetworks = null;
	private JCheckBox m_chkSavedProfilesDiscoverHosts = null;
	private JCheckBox m_chkSavedProfilesReplayInjectPackets = null;
	private JCheckBox m_chkSavedProfilesForgePackets = null;
	private JCheckBox m_chkSavedProfilesSniffPasswords = null;
	private JCheckBox m_chkSavedProfilesPing = null;
	private JCheckBox m_chkSavedProfilesTraceRoute = null;
	private JCheckBox m_chkSavedProfilesWhoIs = null;
	private JCheckBox m_chkSavedProfilesNikto = null;
	private JCheckBox m_chkSavedProfilesMACChanger = null;
	
	private CComboBox m_cboAircrackNG = null;
	private CTextBox m_txtAircrackNG = null;
	private JButton m_btnAircrackNG = null;
	
	private CComboBox m_cboNMap = null;
	private CTextBox m_txtNMap = null;
	private JButton m_btnNMap = null;
	
	private CComboBox m_cboDSniff = null;
	private CTextBox m_txtDSniff = null;
	private JButton m_btnDSniff = null;
	
	private CComboBox m_cboWireshark = null;
	private CTextBox m_txtWireshark = null;
	private JButton m_btnWireshark = null;
	
	private CComboBox m_cboVOMIT = null;
	private CTextBox m_txtVOMIT = null;
	private JButton m_btnVOMIT = null;
	
	private CComboBox m_cboMetasploit = null;
	private CTextBox m_txtMetasploit = null;
	private JButton m_btnMetasploit = null;
	
	private CComboBox m_cboArmitage = null;
	private CTextBox m_txtArmitage = null;
	private JButton m_btnArmitage = null;
	
	private CComboBox m_cboBURPSuite = null;
	private CTextBox m_txtBURPSuite = null;
	private JButton m_btnBURPSuite = null;
	
	private CComboBox m_cboNikto = null;
	private CTextBox m_txtNikto = null;
	private JButton m_btnNikto = null;
	
	private CComboBox m_cboHydra = null;
	private CTextBox m_txtHydra = null;
	private JButton m_btnHydra = null;
	
	private CComboBox m_cboHping = null;
	private CTextBox m_txtHping = null;
	private JButton m_btnHping = null;

	private CComboBox m_cboMACChanger = null;
	private CTextBox m_txtMACChanger = null;
	private JButton m_btnMACChanger = null;
	
	private CComboBox m_cboSSLStrip = null;
	private CTextBox m_txtSSLStrip = null;
	private JButton m_btnSSLStrip = null;
	
	private CComboBox m_cboRedFang = null;
	private CTextBox m_txtRedFang = null;
	private JButton m_btnRedFang = null;
	
	private CComboBox m_cboMessageSnarf = null;
	private CTextBox m_txtMessageSnarf = null;
	private JButton m_btnMessageSnarf = null;

	private CComboBox m_cboTCPDump = null;
	private CTextBox m_txtTCPDump = null;
	private JButton m_btnTCPDump = null;
	
	private CComboBox m_cboSQLScan = null;
	private CTextBox m_txtSQLScan = null;
	private JButton m_btnSQLScan = null;

	private CComboBox m_cboSQLMap = null;
	private CTextBox m_txtSQLMap = null;
	private JButton m_btnSQLMap = null;

	private CComboBox m_cboSQLNinja = null;
	private CTextBox m_txtSQLNinja = null;
	private JButton m_btnSQLNinja = null;

	private CComboBox m_cboIPCalculator = null;
	private CTextBox m_txtIPCalculator = null;
	private JButton m_btnIPCalculator = null;
	
	private CComboBox m_cboTORNetwork = null;
	private CTextBox m_txtTORNetwork = null;
	private JButton m_btnTORNetwork = null;
	
	private CComboBox m_cboWash = null;
	private CTextBox m_txtWash = null;
	private JButton m_btnWash = null;

	private CComboBox m_cboReaver = null;
	private CTextBox m_txtReaver = null;
	private JButton m_btnReaver = null;
	
	private CDropdownButton m_ddbDefaultSettings = null; 
	
	// Default Profiles
	private CComboBox m_cboDefaultProfileDiscoverHosts = null;
	private CComboBox m_cboDefaultProfileDiscoverNetworks = null;
	private CComboBox m_cboDefaultProfileForgePackets = null;
	private CComboBox m_cboDefaultProfileReplayInjectPackets = null;
	private CComboBox m_cboDefaultProfileSniffPasswords = null;
	private CComboBox m_cboDefaultProfilePing = null;
	private CComboBox m_cboDefaultProfileTraceRoute = null;
	private CComboBox m_cboDefaultProfileWhoIs = null;
	private CComboBox m_cboDefaultProfileMACChanger = null;
	private CComboBox m_cboDefaultProfileNikto = null;
	private CComboBox m_cboDefaultProfileReaver = null;
	private CComboBox m_cboDefaultProfileWireshark = null;
	
	private JButton m_btnSaveSettings = null;

	/**
	 * Class constructor
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 */
	public FSettings( )
	{
		super("Settings", 525, 480, false, false, "");
		try
		{
			AddControls( );
			PopulateStandardInterfaces( );
			PopulateMonitorInterfaces( );
			PopulateApplicationPathDropdowns( );
			PopulateCurrentSettings( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Adds the controls to the window
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 */
	public void AddControls( )
	{
		try
		{   
            CTabContainer tbcTabContainer = CUtilities.AddTabContainer(m_cntControlContainer, 5, 10, 415, 500);
            JPanel pnlPreferences = tbcTabContainer.AddTab("Preferences");
            JPanel pnlSavedProfiles = tbcTabContainer.AddTab("Saved Profiles");
            JPanel pnlApplicationPaths = tbcTabContainer.AddTab("App Paths");
            JPanel pnlDefaultProfiles = tbcTabContainer.AddTab("Default Profiles");
            
            JPanel pnlScrollableApplicationPaths = CUtilities.AddScrollPane(m_cntControlContainer, pnlApplicationPaths, 470, 500, 365, 0, 0);
            
            CUtilities.AddLabel(pnlPreferences, "Preferred Interfaces:", 5, 5);
            CUtilities.AddLabel(pnlPreferences, "Standard Interface:", 30, 5);
        	CUtilities.AddLabel(pnlPreferences, "Monitor Interface:", 55, 5);
        	m_cboPreferredInterface = CUtilities.AddComboBox(pnlPreferences, null, 28, 150, 18, 100);
        	m_cboPreferredMonitorInterface = CUtilities.AddComboBox(pnlPreferences, null, 53, 150, 18, 100);
        	
        	ButtonGroup bgpCommandLog = new ButtonGroup( );
        	CUtilities.AddLabel(pnlPreferences, "Command Log:", 100, 5);
        	m_rdbLogNoCommands = CUtilities.AddRadioButton(pnlPreferences, null, bgpCommandLog, "No commands logged", 115, 5);
        	m_rdbLogImportantCommands = CUtilities.AddRadioButton(pnlPreferences, null, bgpCommandLog, "Only important commands", 135, 5);
        	m_rdbLogAllCommands = CUtilities.AddRadioButton(pnlPreferences, null, bgpCommandLog, "All commands", 155, 5);
        	
        	CUtilities.AddLabel(pnlSavedProfiles, "Clear Saved Profiles", 5, 5);
        	m_chkSavedProfilesDiscoverNetworks = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Discover Networks", 25, 5);
        	m_chkSavedProfilesDiscoverHosts = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Discover Hosts", 25, 200);
        	m_chkSavedProfilesReplayInjectPackets = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Replay/Inject Packets", 45, 5);
        	m_chkSavedProfilesForgePackets = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Forge Packets", 45, 200);
        	m_chkSavedProfilesSniffPasswords = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Sniff Passwords", 65, 5);
        	m_chkSavedProfilesPing = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Ping", 65, 200);
        	m_chkSavedProfilesTraceRoute = CUtilities.AddCheckBox(pnlSavedProfiles, null, "TraceRoute", 85, 5);
        	m_chkSavedProfilesWhoIs = CUtilities.AddCheckBox(pnlSavedProfiles, null, "WhoIs", 85, 200);
        	m_chkSavedProfilesNikto = CUtilities.AddCheckBox(pnlSavedProfiles, null, "Nikto", 105, 5);
        	m_chkSavedProfilesMACChanger = CUtilities.AddCheckBox(pnlSavedProfiles, null, "MAC Changer", 105, 200);

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Aircrack-NG Suite:", 6, 5);
        	m_cboAircrackNG = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 5, 135, 18, 105);
        	m_txtAircrackNG = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 5, 245);
        	m_btnAircrackNG = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 5, 420, 18, 50);
        	m_cboAircrackNG.GetJavaComboBox( ).addActionListener( this );
        	m_txtAircrackNG.setVisible( false );
        	m_btnAircrackNG.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "NMap:", 26, 5);
        	m_cboNMap = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 25, 135, 18, 105);
        	m_txtNMap = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 25, 245);
        	m_btnNMap = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 25, 420, 18, 50);
        	m_cboNMap.GetJavaComboBox( ).addActionListener( this );
        	m_txtNMap.setVisible( false );
        	m_btnNMap.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "DSniff Suite:", 46, 5);
        	m_cboDSniff = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 45, 135, 18, 105);
        	m_txtDSniff = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 45, 245);
        	m_btnDSniff = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 45, 420, 18, 50);
        	m_cboDSniff.GetJavaComboBox( ).addActionListener( this );
        	m_txtDSniff.setVisible( false );
        	m_btnDSniff.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Wireshark:", 66, 5);
        	m_cboWireshark = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 65, 135, 18, 105);
        	m_txtWireshark = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 65, 245);
        	m_btnWireshark = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 65, 420, 18, 50);
        	m_cboWireshark.GetJavaComboBox( ).addActionListener( this );
        	m_txtWireshark.setVisible( false );
        	m_btnWireshark.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "VOMIT:", 86, 5);
        	m_cboVOMIT = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 85, 135, 18, 105);
        	m_txtVOMIT = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 85, 245);
        	m_btnVOMIT = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 85, 420, 18, 50);
        	m_cboVOMIT.GetJavaComboBox( ).addActionListener( this );
        	m_txtVOMIT.setVisible( false );
        	m_btnVOMIT.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Metasploit:", 105, 5);
        	m_cboMetasploit = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 105, 135, 18, 105);
        	m_txtMetasploit = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 105, 245);
        	m_btnMetasploit = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 105, 420, 18, 50);
        	m_cboMetasploit.GetJavaComboBox( ).addActionListener( this );
        	m_txtMetasploit.setVisible( false );
        	m_btnMetasploit.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Armitage:", 126, 5);
        	m_cboArmitage = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 125, 135, 18, 105);
        	m_txtArmitage = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 125, 245);
        	m_btnArmitage = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 125, 420, 18, 50);
        	m_cboArmitage.GetJavaComboBox( ).addActionListener( this );
        	m_txtArmitage.setVisible( false );
        	m_btnArmitage.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "BURP Suite:", 146, 5);
        	m_cboBURPSuite = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 145, 135, 18, 105);
        	m_txtBURPSuite = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 145, 245);        	
        	m_btnBURPSuite = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 145, 420, 18, 50);
        	m_cboBURPSuite.GetJavaComboBox( ).addActionListener( this );
        	m_txtBURPSuite.setVisible( false );
        	m_btnBURPSuite.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Nikto:", 166, 5);
        	m_cboNikto = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 165, 135, 18, 105);
        	m_txtNikto = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 165, 245);
        	m_btnNikto = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 165, 420, 18, 50);
        	m_cboNikto.GetJavaComboBox( ).addActionListener( this );
        	m_txtNikto.setVisible( false );
        	m_btnNikto.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Hydra:", 186, 5);
        	m_cboHydra = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 185, 135, 18, 105);
        	m_txtHydra = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 185, 245);
        	m_btnHydra = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 185, 420, 18, 50);
        	m_cboHydra.GetJavaComboBox( ).addActionListener( this );
        	m_txtHydra.setVisible( false );
        	m_btnHydra.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "HPing:", 206, 5);
        	m_cboHping = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 205, 135, 18, 105);
        	m_txtHping = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 205, 245);
        	m_btnHping = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 205, 420, 18, 50);
        	m_cboHping.GetJavaComboBox( ).addActionListener( this );
        	m_txtHping.setVisible( false );
        	m_btnHping.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "MAC Changer:", 226, 5);
        	m_cboMACChanger = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 225, 135, 18, 105);
        	m_txtMACChanger = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 225, 245);
        	m_btnMACChanger = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 225, 420, 18, 50);
        	m_cboMACChanger.GetJavaComboBox( ).addActionListener( this );
        	m_txtMACChanger.setVisible( false );
        	m_btnMACChanger.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "SSL Strip:", 246, 5);
        	m_cboSSLStrip = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 245, 135, 18, 105);
        	m_txtSSLStrip = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 245, 245);
        	m_btnSSLStrip = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 245, 420, 18, 50);
        	m_cboSSLStrip.GetJavaComboBox( ).addActionListener( this );
        	m_txtSSLStrip.setVisible( false );
        	m_btnSSLStrip.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Red Fang:", 266, 5);
        	m_cboRedFang = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 265, 135, 18, 105);
        	m_txtRedFang = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 265, 245);
        	m_btnRedFang = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 265, 420, 18, 50);
        	m_cboRedFang.GetJavaComboBox( ).addActionListener( this );
        	m_txtRedFang.setVisible( false );
        	m_btnRedFang.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Msgsnarf:", 286, 5);
        	m_cboMessageSnarf = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 285, 135, 18, 105);
        	m_txtMessageSnarf = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 285, 245);
        	m_btnMessageSnarf = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 285, 420, 18, 50);
        	m_cboMessageSnarf.GetJavaComboBox( ).addActionListener( this );
        	m_txtMessageSnarf.setVisible( false );
        	m_btnMessageSnarf.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "TCP Dump:", 306, 5);
        	m_cboTCPDump = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 305, 135, 18, 105);
        	m_txtTCPDump = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 305, 245);
        	m_btnTCPDump = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 305, 420, 18, 50);
        	m_cboTCPDump.GetJavaComboBox( ).addActionListener( this );
        	m_txtTCPDump.setVisible( false );
        	m_btnTCPDump.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "SQL Scan:", 326, 5);
        	m_cboSQLScan = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 325, 135, 18, 105);
        	m_txtSQLScan = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 325, 245);
        	m_btnSQLScan = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 325, 420, 18, 50);
        	m_cboSQLScan.GetJavaComboBox( ).addActionListener( this );
        	m_txtSQLScan.setVisible( false );
        	m_btnSQLScan.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "SQL Map:", 346, 5);
        	m_cboSQLMap = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 345, 135, 18, 105);
        	m_txtSQLMap = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 345, 245);
        	m_btnSQLMap = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 345, 420, 18, 50);
        	m_cboSQLMap.GetJavaComboBox( ).addActionListener( this );
        	m_txtSQLMap.setVisible( false );
        	m_btnSQLMap.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "SQL Ninja:", 366, 5);
        	m_cboSQLNinja = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 365, 135, 18, 105);
        	m_txtSQLNinja = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 365, 245);
        	m_btnSQLNinja = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 365, 420, 18, 50);
        	m_cboSQLNinja.GetJavaComboBox( ).addActionListener( this );
        	m_txtSQLNinja.setVisible( false );
        	m_btnSQLNinja.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "IP Calc:", 386, 5);
        	m_cboIPCalculator = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 385, 135, 18, 105);
        	m_txtIPCalculator = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 385, 245);
        	m_btnIPCalculator = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 385, 420, 18, 50);
        	m_cboIPCalculator.GetJavaComboBox( ).addActionListener( this );
        	m_txtIPCalculator.setVisible( false );
        	m_btnIPCalculator.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "TOR Network:", 406, 5);
        	m_cboTORNetwork = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 405, 135, 18, 105);
        	m_txtTORNetwork = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 405, 245);
        	m_btnTORNetwork = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 405, 420, 18, 50);
        	m_cboTORNetwork.GetJavaComboBox( ).addActionListener( this );
        	m_txtTORNetwork.setVisible( false );
        	m_btnTORNetwork.setVisible( false );
        	
        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Wash:", 426, 5);
        	m_cboWash = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 425, 135, 18, 105);
        	m_txtWash = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 425, 245);
        	m_btnWash = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 425, 420, 18, 50);
        	m_cboWash.GetJavaComboBox( ).addActionListener( this );
        	m_txtWash.setVisible( false );
        	m_btnWash.setVisible( false );

        	CUtilities.AddLabel(pnlScrollableApplicationPaths, "Reaver:", 446, 5);
        	m_cboReaver = CUtilities.AddComboBox(pnlScrollableApplicationPaths, null, 445, 135, 18, 105);
        	m_txtReaver = CUtilities.AddTextBox(pnlScrollableApplicationPaths, null, "", 15, 100, 445, 245);
        	m_btnReaver = CUtilities.AddButton(pnlScrollableApplicationPaths, this, "...", 445, 420, 18, 50);
        	m_cboReaver.GetJavaComboBox( ).addActionListener( this );
        	m_txtReaver.setVisible( false );
        	m_btnReaver.setVisible( false );
    
        	m_ddbDefaultSettings = CUtilities.AddDropdownButton(pnlApplicationPaths, this, this, "Default Settings", 365, 5, 18, 200);
        	m_ddbDefaultSettings.SetMenuOptions(new String[] {"In Path", "BackTrack 5"});
    
        	// Default Profiles
        	CUtilities.AddLabel(pnlDefaultProfiles, "Discover Hosts:", 5, 5);
        	m_cboDefaultProfileDiscoverHosts = CUtilities.AddComboBox(pnlDefaultProfiles, null, 5, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Discover Networks:", 25, 5);
        	m_cboDefaultProfileDiscoverNetworks = CUtilities.AddComboBox(pnlDefaultProfiles, null, 25, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Forge Packets:", 45, 5);
        	m_cboDefaultProfileForgePackets = CUtilities.AddComboBox(pnlDefaultProfiles, null, 45, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Replay/Inject Packets:", 65, 5);
        	m_cboDefaultProfileReplayInjectPackets = CUtilities.AddComboBox(pnlDefaultProfiles, null, 65, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Sniff Passwords:", 85, 5);
        	m_cboDefaultProfileSniffPasswords = CUtilities.AddComboBox(pnlDefaultProfiles, null, 85, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Ping:", 105, 5);
        	m_cboDefaultProfilePing = CUtilities.AddComboBox(pnlDefaultProfiles, null, 105, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Trace Route:", 125, 5);
        	m_cboDefaultProfileTraceRoute = CUtilities.AddComboBox(pnlDefaultProfiles, null, 125, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Who Is:", 145, 5);
        	m_cboDefaultProfileWhoIs = CUtilities.AddComboBox(pnlDefaultProfiles, null, 145, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "MAC Changer:", 165, 5);
        	m_cboDefaultProfileMACChanger = CUtilities.AddComboBox(pnlDefaultProfiles, null, 165, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Nikto:", 185, 5);
        	m_cboDefaultProfileNikto = CUtilities.AddComboBox(pnlDefaultProfiles, null, 185, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Reaver:", 205, 5);
        	m_cboDefaultProfileReaver = CUtilities.AddComboBox(pnlDefaultProfiles, null, 205, 175, 18, 200);
        	CUtilities.AddLabel(pnlDefaultProfiles, "Wireshark:", 225, 5);
        	m_cboDefaultProfileWireshark = CUtilities.AddComboBox(pnlDefaultProfiles, null, 225, 175, 18, 200);
        	
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileDiscoverHosts, "DiscoverHosts");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileDiscoverNetworks, "DiscoverNetworks");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileForgePackets, "ForgePackets");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileSniffPasswords, "SniffPasswords");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfilePing, "Ping");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileTraceRoute, "TraceRoute");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileWhoIs, "WhoIs");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileMACChanger, "MACChanger");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileNikto, "Nikto");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileReaver, "Reaver");
        	CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboDefaultProfileWireshark, "Wireshark");
        	
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileDiscoverHosts, CUserPreferences.GetDefaultProfile("DiscoverHosts"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileDiscoverNetworks, CUserPreferences.GetDefaultProfile("DiscoverNetworks"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileForgePackets, CUserPreferences.GetDefaultProfile("ForgePackets"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileSniffPasswords, CUserPreferences.GetDefaultProfile("SniffPasswords"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfilePing, CUserPreferences.GetDefaultProfile("Ping"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileTraceRoute, CUserPreferences.GetDefaultProfile("TraceRoute"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileWhoIs, CUserPreferences.GetDefaultProfile("WhoIs"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileMACChanger, CUserPreferences.GetDefaultProfile("MACChanger"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileNikto, CUserPreferences.GetDefaultProfile("Nikto"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileReaver, CUserPreferences.GetDefaultProfile("Reaver"));
        	CAircrackUtilities.SetComboBoxSelectedValue(m_cboDefaultProfileWireshark, CUserPreferences.GetDefaultProfile("Wireshark"));
        	
        	m_btnSaveSettings = CUtilities.AddButton(m_cntControlContainer, this, "Save", 425, 188, 18, 150);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Populates the standard interfaces and selects the preferred one if stored
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 */
	private void PopulateStandardInterfaces( )
	{
		try
		{
			CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetNonMonitorModeDevices();
			
			m_cboPreferredInterface.AddItemToList("", 0);
			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				m_cboPreferredInterface.AddItemToList(aclsInterfaces[intIndex].GetInterface(), 0);
			}
			
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboPreferredInterface, CUserPreferences.GetPreferredStandardInterface());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Populates the monitorable interfaces and selects the preferred one if stored
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 */
	private void PopulateMonitorInterfaces( )
	{
		try
		{
			CNetworkInterface aclsMonitorInterfaces[] = CGlobals.clsLocalMachine.GetAllMonitorableInterfaces();
			
			m_cboPreferredMonitorInterface.AddItemToList("", 0);
			for ( int intIndex = 0; intIndex < aclsMonitorInterfaces.length; intIndex += 1 )
			{
				m_cboPreferredMonitorInterface.AddItemToList(aclsMonitorInterfaces[intIndex].GetInterface(), 0);
			}
			
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboPreferredMonitorInterface, CUserPreferences.GetPreferredMonitorInterface());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Populates the two dropdown options for the paths
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	private void PopulateApplicationPathDropdowns( )
	{
		try
		{
			AddPathDropdownOptions(m_cboAircrackNG);
			AddPathDropdownOptions(m_cboNMap);
			AddPathDropdownOptions(m_cboDSniff);
			AddPathDropdownOptions(m_cboWireshark);
			AddPathDropdownOptions(m_cboVOMIT);
        	AddPathDropdownOptions(m_cboMetasploit);
        	AddPathDropdownOptions(m_cboArmitage);
        	AddPathDropdownOptions(m_cboBURPSuite);
        	AddPathDropdownOptions(m_cboNikto);
        	AddPathDropdownOptions(m_cboHydra);
        	AddPathDropdownOptions(m_cboHping);
        	AddPathDropdownOptions(m_cboMACChanger);        	
        	AddPathDropdownOptions(m_cboSSLStrip);
        	AddPathDropdownOptions(m_cboRedFang);
        	AddPathDropdownOptions(m_cboMessageSnarf);
        	AddPathDropdownOptions(m_cboTCPDump);
        	AddPathDropdownOptions(m_cboSQLScan);
        	AddPathDropdownOptions(m_cboSQLMap);
        	AddPathDropdownOptions(m_cboSQLNinja);
        	AddPathDropdownOptions(m_cboIPCalculator);
        	AddPathDropdownOptions(m_cboTORNetwork);
        	AddPathDropdownOptions(m_cboWash);
        	AddPathDropdownOptions(m_cboReaver);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Adds the two options of IN PATH or DIRECTORY to a dropdown
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 * @param cboTarget Target combobox
	 */
	private void AddPathDropdownOptions(CComboBox cboTarget)
	{
		try
		{
			cboTarget.SetSorted( false );
			cboTarget.AddItemToList("IN PATH", 0);
			cboTarget.AddItemToList("DIRECTORY", 1);
			cboTarget.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Populates the current application path settings
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	private void PopulateCurrentSettings( )
	{
		try
		{
			String strProgramName = "";
			String strPath = "";

			for (Map.Entry<String, String> entApplicationPath : CUserPreferences.GetApplicationPaths().entrySet())
			{
				strProgramName = entApplicationPath.getKey( );
				strPath = entApplicationPath.getValue( );
				
				if ( strProgramName.equals( "aircrack-ng" ) )		SetProgramUsesPath(m_cboAircrackNG, m_txtAircrackNG, strPath);
				if ( strProgramName.equals( "nmap" ) )				SetProgramUsesPath(m_cboNMap, m_txtNMap, strPath);
				if ( strProgramName.equals( "dsniff" ) )			SetProgramUsesPath(m_cboDSniff, m_txtDSniff, strPath);
				if ( strProgramName.equals( "wireshark" ) )			SetProgramUsesPath(m_cboWireshark, m_txtWireshark, strPath);
				if ( strProgramName.equals( "vomit" ) )				SetProgramUsesPath(m_cboVOMIT, m_txtVOMIT, strPath);
				if ( strProgramName.equals( "metasploit" ) )		SetProgramUsesPath(m_cboMetasploit, m_txtMetasploit, strPath);
				if ( strProgramName.equals( "armitage" ) )			SetProgramUsesPath(m_cboArmitage, m_txtArmitage, strPath);
				if ( strProgramName.equals( "burp" ) )				SetProgramUsesPath(m_cboBURPSuite, m_txtBURPSuite, strPath);
				if ( strProgramName.equals( "nikto" ) )				SetProgramUsesPath(m_cboNikto, m_txtNikto, strPath);
				if ( strProgramName.equals( "hydra" ) )				SetProgramUsesPath(m_cboHydra, m_txtHydra, strPath);
				if ( strProgramName.equals( "hping" ) )			 	SetProgramUsesPath(m_cboHping, m_txtHping, strPath);
				if ( strProgramName.equals( "macchanger" ) )		SetProgramUsesPath(m_cboMACChanger, m_txtMACChanger, strPath);
				if ( strProgramName.equals( "sslstrip" ) )			SetProgramUsesPath(m_cboSSLStrip, m_txtSSLStrip, strPath);
				if ( strProgramName.equals( "redfang" ) )			SetProgramUsesPath(m_cboRedFang, m_txtRedFang, strPath);
				if ( strProgramName.equals( "msgsnarf" ) )			SetProgramUsesPath(m_cboMessageSnarf, m_txtMessageSnarf, strPath);
				if ( strProgramName.equals( "tcpdump" ) )			SetProgramUsesPath(m_cboTCPDump, m_txtTCPDump, strPath);
				if ( strProgramName.equals( "sqlscan" ) )			SetProgramUsesPath(m_cboSQLScan, m_txtSQLScan, strPath);
				if ( strProgramName.equals( "sqlmap" ) )			SetProgramUsesPath(m_cboSQLMap, m_txtSQLMap, strPath);
				if ( strProgramName.equals( "sqlninja" ) )			SetProgramUsesPath(m_cboSQLNinja, m_txtSQLNinja, strPath);
				if ( strProgramName.equals( "ipcalc" ) )			SetProgramUsesPath(m_cboIPCalculator, m_txtIPCalculator, strPath);
				if ( strProgramName.equals( "tornetwork" ) )		SetProgramUsesPath(m_cboTORNetwork, m_txtTORNetwork, strPath);
				if ( strProgramName.equals( "wash" ) )				SetProgramUsesPath(m_cboWash, m_txtWash, strPath);
				if ( strProgramName.equals( "reaver" ) )			SetProgramUsesPath(m_cboReaver, m_txtReaver, strPath);
			}
			
			if ( CUserPreferences.GetCommandOutputLevel( ).equals("NONE") )
				m_rdbLogNoCommands.setSelected( true );
			else if ( CUserPreferences.GetCommandOutputLevel( ).equals("IMPORTANT") )
				m_rdbLogImportantCommands.setSelected( true );
			else if ( CUserPreferences.GetCommandOutputLevel( ).equals("ALL") )
				m_rdbLogAllCommands.setSelected( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Button clicks
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_cboAircrackNG.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboAircrackNG, m_txtAircrackNG, m_btnAircrackNG);
			else if ( aeSource.getSource( ) == m_btnAircrackNG )								DisplayAndAppendDirectoryChooser(m_txtAircrackNG);
			else if ( aeSource.getSource( ) == m_cboNMap.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboNMap, m_txtNMap, m_btnNMap);
			else if ( aeSource.getSource( ) == m_btnNMap )										DisplayAndAppendDirectoryChooser(m_txtNMap);
			else if ( aeSource.getSource( ) == m_cboDSniff.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboDSniff, m_txtDSniff, m_btnDSniff);
			else if ( aeSource.getSource( ) == m_btnDSniff )									DisplayAndAppendDirectoryChooser(m_txtDSniff);
			else if ( aeSource.getSource( ) == m_cboWireshark.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboWireshark, m_txtWireshark, m_btnWireshark);
			else if ( aeSource.getSource( ) == m_btnWireshark )									DisplayAndAppendDirectoryChooser(m_txtWireshark);
			else if ( aeSource.getSource( ) == m_cboVOMIT.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboVOMIT, m_txtVOMIT, m_btnVOMIT);
			else if ( aeSource.getSource( ) == m_btnVOMIT )										DisplayAndAppendDirectoryChooser(m_txtVOMIT);
			else if ( aeSource.getSource( ) == m_cboMetasploit.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboMetasploit, m_txtMetasploit, m_btnMetasploit);
			else if ( aeSource.getSource( ) == m_btnMetasploit )								DisplayAndAppendDirectoryChooser(m_txtMetasploit);
        	else if ( aeSource.getSource( ) == m_cboArmitage.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboArmitage, m_txtArmitage, m_btnArmitage);
        	else if ( aeSource.getSource( ) == m_btnArmitage )									DisplayAndAppendDirectoryChooser(m_txtArmitage);
        	else if ( aeSource.getSource( ) == m_cboBURPSuite.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboBURPSuite, m_txtBURPSuite, m_btnBURPSuite);
        	else if ( aeSource.getSource( ) == m_btnBURPSuite )									DisplayAndAppendDirectoryChooser(m_txtBURPSuite);
        	else if ( aeSource.getSource( ) == m_cboNikto.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboNikto, m_txtNikto, m_btnNikto);
        	else if ( aeSource.getSource( ) == m_btnNikto )										DisplayAndAppendDirectoryChooser(m_txtNikto);
        	else if ( aeSource.getSource( ) == m_cboHydra.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboHydra, m_txtHydra, m_btnHydra);
        	else if ( aeSource.getSource( ) == m_btnHydra )										DisplayAndAppendDirectoryChooser(m_txtHydra);
        	else if ( aeSource.getSource( ) == m_cboHping.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboHping, m_txtHping, m_btnHping);
        	else if ( aeSource.getSource( ) == m_btnHping )										DisplayAndAppendDirectoryChooser(m_txtHping);
        	else if ( aeSource.getSource( ) == m_cboMACChanger.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboMACChanger, m_txtMACChanger, m_btnMACChanger);
        	else if ( aeSource.getSource( ) == m_btnMACChanger )								DisplayAndAppendDirectoryChooser(m_txtMACChanger);
        	else if ( aeSource.getSource( ) == m_cboSSLStrip.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboSSLStrip, m_txtSSLStrip, m_btnSSLStrip);
        	else if ( aeSource.getSource( ) == m_btnSSLStrip )									DisplayAndAppendDirectoryChooser(m_txtSSLStrip);
        	else if ( aeSource.getSource( ) == m_cboRedFang.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboRedFang, m_txtRedFang, m_btnRedFang);
        	else if ( aeSource.getSource( ) == m_btnRedFang )									DisplayAndAppendDirectoryChooser(m_txtRedFang);
        	else if ( aeSource.getSource( ) == m_cboMessageSnarf.GetJavaComboBox( ) ) 			ShowOptionsBasedOnSelection(m_cboMessageSnarf, m_txtMessageSnarf, m_btnMessageSnarf);
        	else if ( aeSource.getSource( ) == m_btnMessageSnarf )								DisplayAndAppendDirectoryChooser(m_txtMessageSnarf);
        	else if ( aeSource.getSource( ) == m_cboTCPDump.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboTCPDump, m_txtTCPDump, m_btnTCPDump);
        	else if ( aeSource.getSource( ) == m_btnTCPDump )									DisplayAndAppendDirectoryChooser(m_txtTCPDump);
        	else if ( aeSource.getSource( ) == m_cboSQLScan.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboSQLScan, m_txtSQLScan, m_btnSQLScan);
        	else if ( aeSource.getSource( ) == m_btnSQLScan )									DisplayAndAppendDirectoryChooser(m_txtSQLScan);
        	else if ( aeSource.getSource( ) == m_cboSQLMap.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboSQLMap, m_txtSQLMap, m_btnSQLMap);
        	else if ( aeSource.getSource( ) == m_btnSQLMap )									DisplayAndAppendDirectoryChooser(m_txtSQLMap);
        	else if ( aeSource.getSource( ) == m_cboSQLNinja.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboSQLNinja, m_txtSQLNinja, m_btnSQLNinja);
        	else if ( aeSource.getSource( ) == m_btnSQLNinja )									DisplayAndAppendDirectoryChooser(m_txtSQLNinja);
        	else if ( aeSource.getSource( ) == m_cboIPCalculator.GetJavaComboBox( ) )			ShowOptionsBasedOnSelection(m_cboIPCalculator, m_txtIPCalculator, m_btnIPCalculator);
        	else if ( aeSource.getSource( ) == m_btnIPCalculator )								DisplayAndAppendDirectoryChooser(m_txtIPCalculator);
        	else if ( aeSource.getSource( ) == m_cboTORNetwork.GetJavaComboBox( ) )				ShowOptionsBasedOnSelection(m_cboTORNetwork, m_txtTORNetwork, m_btnTORNetwork);
        	else if ( aeSource.getSource( ) == m_btnTORNetwork )								DisplayAndAppendDirectoryChooser(m_txtTORNetwork);
        	else if ( aeSource.getSource( ) == m_cboWash.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboWash, m_txtWash, m_btnWash);
        	else if ( aeSource.getSource( ) == m_btnWash )										DisplayAndAppendDirectoryChooser(m_txtWash);
        	else if ( aeSource.getSource( ) == m_cboReaver.GetJavaComboBox( ) )					ShowOptionsBasedOnSelection(m_cboReaver, m_txtReaver, m_btnReaver);
        	else if ( aeSource.getSource( ) == m_btnReaver )									DisplayAndAppendDirectoryChooser(m_txtReaver);
        	else if ( aeSource.getSource( ) == m_ddbDefaultSettings.GetMenuItem("BackTrack 5") )	ApplyDefaultBackTrack5Settings( );
        	else if ( aeSource.getSource( ) == m_ddbDefaultSettings.GetMenuItem("In Path") )		ApplyDefaultInPathSettings( );		
        	else if ( aeSource.getSource( ) == m_btnSaveSettings )								btnSaveSettings_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Shows the path textbox and directory chooser buttons if DIRECTORY is selected. Otherwise, hide them.
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 * @param cboTarget Combobox that was changed
	 * @param txtPath Textbox control to show/hide.
	 * @param btnDirectoryChooser Directory chooser button to show/hide.
	 */
	private void ShowOptionsBasedOnSelection(CComboBox cboTarget, CTextBox txtPath, JButton btnDirectoryChooser)
	{
		try
		{
			if ( cboTarget.GetSelectedItemValue( ) == 0 )
			{
				txtPath.setVisible( false );
				btnDirectoryChooser.setVisible( false );
			}
			else if ( cboTarget.GetSelectedItemValue( ) == 1 )
			{
				txtPath.setVisible( true );
				btnDirectoryChooser.setVisible( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Displays a directory chooser and appends a "/" on the end of it
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 */
	private void DisplayAndAppendDirectoryChooser(CTextBox clsTarget)
	{
		try
		{
			int intReturnValue = CAircrackUtilities.DisplayDirectoryChooser(clsTarget, this);
			if ( intReturnValue == 0 )
			{
				// TODO: Modify this function to append the appropriate slash depending on operating system
				clsTarget.setText(clsTarget.getText() + "/");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Sets all programs to be listed in the PATH variable.
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	private void ApplyDefaultInPathSettings( )
	{
		try
		{
			SetProgramIsInPath(m_cboAircrackNG, m_txtAircrackNG);
			SetProgramIsInPath(m_cboNMap, m_txtNMap);
			SetProgramIsInPath(m_cboDSniff, m_txtDSniff);
			SetProgramIsInPath(m_cboWireshark, m_txtWireshark);
			SetProgramIsInPath(m_cboVOMIT, m_txtVOMIT);
			SetProgramIsInPath(m_cboMetasploit, m_txtMetasploit);
			SetProgramIsInPath(m_cboArmitage, m_txtArmitage);
			SetProgramIsInPath(m_cboBURPSuite, m_txtBURPSuite);
			SetProgramIsInPath(m_cboNikto, m_txtNikto);
			SetProgramIsInPath(m_cboHydra, m_txtHydra);
			SetProgramIsInPath(m_cboHping, m_txtHping);
			SetProgramIsInPath(m_cboMACChanger, m_txtMACChanger);
			SetProgramIsInPath(m_cboSSLStrip, m_txtSSLStrip);
			SetProgramIsInPath(m_cboRedFang, m_txtRedFang);
			SetProgramIsInPath(m_cboMessageSnarf, m_txtMessageSnarf);
			SetProgramIsInPath(m_cboTCPDump, m_txtTCPDump);
			SetProgramIsInPath(m_cboSQLScan, m_txtSQLScan);
			SetProgramIsInPath(m_cboSQLMap, m_txtSQLMap);
			SetProgramIsInPath(m_cboSQLNinja, m_txtSQLNinja);
			SetProgramIsInPath(m_cboIPCalculator, m_txtIPCalculator);
			SetProgramIsInPath(m_cboWash, m_txtWash);
			SetProgramIsInPath(m_cboReaver, m_txtReaver);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Applies the default BackTrack 5 settings
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	private void ApplyDefaultBackTrack5Settings( )
	{
		try
		{
			SetProgramIsInPath(m_cboAircrackNG, m_txtAircrackNG);
			SetProgramIsInPath(m_cboNMap, m_txtNMap);
			SetProgramIsInPath(m_cboDSniff, m_txtDSniff);
			SetProgramIsInPath(m_cboWireshark, m_txtWireshark);
			SetProgramIsInPath(m_cboVOMIT, m_txtVOMIT);
			SetProgramIsInPath(m_cboMetasploit, m_txtMetasploit);
			SetProgramIsInPath(m_cboArmitage, m_txtArmitage);
			SetProgramUsesPath(m_cboBURPSuite, m_txtBURPSuite, "/pentest/web/burpsuite/");
			SetProgramUsesPath(m_cboNikto, m_txtNikto, "/pentest/web/nikto/");
			SetProgramIsInPath(m_cboHydra, m_txtHydra);
			SetProgramIsInPath(m_cboHping, m_txtHping);
			SetProgramIsInPath(m_cboMACChanger, m_txtMACChanger);
			SetProgramUsesPath(m_cboSSLStrip, m_txtSSLStrip, "/pentest/web/sslstrip/");
			SetProgramUsesPath(m_cboRedFang, m_txtRedFang, "/pentest/bluetooth/bluediving/tools/redfang/");
			SetProgramIsInPath(m_cboMessageSnarf, m_txtMessageSnarf);
			SetProgramIsInPath(m_cboTCPDump, m_txtTCPDump);
			SetProgramUsesPath(m_cboSQLScan, m_txtSQLScan, "/pentest/database/sqlscan/");
			SetProgramUsesPath(m_cboSQLMap, m_txtSQLMap, "/pentest/database/sqlmap/");
			SetProgramUsesPath(m_cboSQLNinja, m_txtSQLNinja, "/pentest/database/sqlninja/");
			SetProgramIsInPath(m_cboIPCalculator, m_txtIPCalculator);
			SetProgramIsInPath(m_cboWash, m_txtWash);
			SetProgramIsInPath(m_cboReaver, m_txtReaver);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetProgramUsesPath
	// Abstract: Sets that the program uses a custom path and populates it
	// --------------------------------------------------------------------------------
	private void SetProgramUsesPath(CComboBox m_cboLocationType, CTextBox m_txtLocation, String strPath)
	{
		try
		{
			m_cboLocationType.SetSelectedIndex( 1 );
			m_txtLocation.setText(strPath);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetProgramIsInPath
	// Abstract: Sets that the program is in the path variable
	// --------------------------------------------------------------------------------
	private void SetProgramIsInPath(CComboBox m_cboLocationType, CTextBox m_txtLocation)
	{
		try
		{
			m_cboLocationType.SetSelectedIndex( 0 );
			m_txtLocation.setText( "" );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSaveSettings_Click
	// Abstract: Saves the settings to a profile file and loads them into memory
	// --------------------------------------------------------------------------------
	private void btnSaveSettings_Click( )
	{
		try
		{
			
			ClearSavedProfiles( );
			LoadSettingsIntoMemory( );
			SaveSettingsToFile( );
			JOptionPane.showMessageDialog(null, "Settings were saved successfully.");
			dispose( ); // Close the form
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ClearSavedProfiles
	// Abstract: Clears the checked profile folders
	// --------------------------------------------------------------------------------
	private void ClearSavedProfiles( )
	{
		try
		{
			File filDiscoverNetworksProfiles = new File("SavedProfiles/DiscoverNetworks");
			File filDiscoverHostsProfiles = new File("SavedProfiles/DiscoverHosts");
			File filReplayInjectPacketsProfiles = new File("SavedProfiles/ReplayInjectPackets");
			File filForgePacketsProfiles = new File("SavedProfiles/ForgePackets");
			File filSniffPasswordsProfiles = new File("SavedProfiles/SniffPasswords");
			File filWhoIsProfiles = new File("SavedProfiles/WhoIs");
			File filNiktoProfiles = new File("SavedProfiles/Nikto");
			File filMACChangerProfiles = new File("SavedProfiles/MACChanger");
			File filPingProfiles = new File("SavedProfiles/Ping");
			File filTraceRouteProfiles = new File("SavedProfiles/TraceRoute");
			
			if ( m_chkSavedProfilesDiscoverNetworks.isSelected( ) && filDiscoverNetworksProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filDiscoverNetworksProfiles );
			if ( m_chkSavedProfilesDiscoverHosts.isSelected( ) && filDiscoverHostsProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filDiscoverHostsProfiles );
			if ( m_chkSavedProfilesReplayInjectPackets.isSelected( ) && filReplayInjectPacketsProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filReplayInjectPacketsProfiles );
			if ( m_chkSavedProfilesForgePackets.isSelected( ) && filForgePacketsProfiles.exists( )  )
				CAircrackUtilities.DeleteFilesRecursively( filForgePacketsProfiles );
			if ( m_chkSavedProfilesSniffPasswords.isSelected( ) && filSniffPasswordsProfiles.exists( ))
				CAircrackUtilities.DeleteFilesRecursively( filSniffPasswordsProfiles );
			if ( m_chkSavedProfilesWhoIs.isSelected( ) && filWhoIsProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filWhoIsProfiles );
			if ( m_chkSavedProfilesNikto.isSelected( ) && filNiktoProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filNiktoProfiles );
			if ( m_chkSavedProfilesMACChanger.isSelected( ) && filMACChangerProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filMACChangerProfiles );
			if ( m_chkSavedProfilesPing.isSelected( ) && filPingProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filPingProfiles );
			if ( m_chkSavedProfilesTraceRoute.isSelected( ) && filTraceRouteProfiles.exists( ) )
				CAircrackUtilities.DeleteFilesRecursively( filTraceRouteProfiles );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: LoadSettingsIntoMemory
	// Abstract: Loads the settings into memory for use from other programs
	// --------------------------------------------------------------------------------
	private void LoadSettingsIntoMemory( )
	{
		try
		{
			if ( m_cboPreferredInterface.GetSelectedIndex( ) > 0 )
				CUserPreferences.SetPreferredStandardInterface( m_cboPreferredInterface.GetSelectedItemName( ) );
			else
				CUserPreferences.SetPreferredStandardInterface( "" );
			
			if ( m_cboPreferredMonitorInterface.GetSelectedIndex( ) > 0 )
				CUserPreferences.SetPreferredMonitorInterface( m_cboPreferredMonitorInterface.GetSelectedItemName( ) );
			else
				CUserPreferences.SetPreferredMonitorInterface( "" );
			
			if ( m_rdbLogNoCommands.isSelected( ) == true )
				CUserPreferences.SetCommandOutputLevel( "NONE" );
			else if ( m_rdbLogImportantCommands.isSelected( ) == true )
				CUserPreferences.SetCommandOutputLevel( "IMPORTANT" );
			else if ( m_rdbLogAllCommands.isSelected( ) == true )
				CUserPreferences.SetCommandOutputLevel( "ALL" );
			
			AddApplicationLocationsToMemory( );
			
			AddDefaultProfilesToMemory( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddApplicationLocationsToMemory
	// Abstract: Loads the application paths into memory
	// --------------------------------------------------------------------------------
	private void AddApplicationLocationsToMemory( )
	{
		try
		{
			CUserPreferences.ClearAllApplicationPaths( );
			
			// Add the application paths
			AddApplicationPathIfSpecified(m_cboAircrackNG, m_txtAircrackNG, "aircrack-ng");
			AddApplicationPathIfSpecified(m_cboNMap, m_txtNMap, "nmap");
			AddApplicationPathIfSpecified(m_cboDSniff, m_txtDSniff, "dsniff");
			AddApplicationPathIfSpecified(m_cboWireshark, m_txtWireshark, "wireshark");
			AddApplicationPathIfSpecified(m_cboVOMIT, m_txtVOMIT, "vomit");
			AddApplicationPathIfSpecified(m_cboMetasploit, m_txtMetasploit, "metasploit");
			AddApplicationPathIfSpecified(m_cboArmitage, m_txtArmitage, "armitage");
			AddApplicationPathIfSpecified(m_cboBURPSuite, m_txtBURPSuite, "burp");
			AddApplicationPathIfSpecified(m_cboNikto, m_txtNikto, "nikto");
			AddApplicationPathIfSpecified(m_cboHydra, m_txtHydra, "hydra");
			AddApplicationPathIfSpecified(m_cboHping, m_txtHping, "hping");
			AddApplicationPathIfSpecified(m_cboMACChanger, m_txtMACChanger, "macchanger");
			AddApplicationPathIfSpecified(m_cboSSLStrip, m_txtSSLStrip, "sslstrip");
			AddApplicationPathIfSpecified(m_cboRedFang, m_txtRedFang, "redfang");
			AddApplicationPathIfSpecified(m_cboMessageSnarf, m_txtMessageSnarf, "msgsnarf");
			AddApplicationPathIfSpecified(m_cboTCPDump, m_txtTCPDump, "tcpdump");
			AddApplicationPathIfSpecified(m_cboSQLScan, m_txtSQLScan, "sqlscan");
			AddApplicationPathIfSpecified(m_cboSQLMap, m_txtSQLMap, "sqlmap");
			AddApplicationPathIfSpecified(m_cboSQLNinja, m_txtSQLNinja, "sqlninja");
			AddApplicationPathIfSpecified(m_cboIPCalculator, m_txtIPCalculator, "ipcalc");
			AddApplicationPathIfSpecified(m_cboTORNetwork, m_txtTORNetwork, "tornetwork");
			AddApplicationPathIfSpecified(m_cboWash, m_txtWash, "wash");
			AddApplicationPathIfSpecified(m_cboReaver, m_txtReaver, "reaver");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddApplicationPathIfSpecified
	// Abstract: Adds the application path to memory if an application path was specified
	// --------------------------------------------------------------------------------
	private void AddApplicationPathIfSpecified(CComboBox cboDropdown, CTextBox txtPath, String strKeyName)
	{
		try
		{
			if ( cboDropdown.GetSelectedItemValue( ) == 1 && txtPath.getText( ).trim( ).equals( "" ) == false )
			{
				CUserPreferences.AddApplicationPath( strKeyName, txtPath.getText( ).trim( ) );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddDefaultProfilesToMemory
	// Abstract: Adds the default profiles to memory if a default profile was specified
	// --------------------------------------------------------------------------------
	private void AddDefaultProfilesToMemory( )
	{
		try
		{
			CUserPreferences.SetDefaultProfile("DiscoverHosts", m_cboDefaultProfileDiscoverHosts.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("DiscoverNetworks", m_cboDefaultProfileDiscoverNetworks.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("ForgePackets", m_cboDefaultProfileForgePackets.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("SniffPasswords", m_cboDefaultProfileSniffPasswords.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("Ping", m_cboDefaultProfilePing.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("TraceRoute", m_cboDefaultProfileTraceRoute.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("WhoIs", m_cboDefaultProfileWhoIs.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("MACChanger", m_cboDefaultProfileMACChanger.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("Nikto", m_cboDefaultProfileNikto.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("Reaver", m_cboDefaultProfileReaver.GetSelectedItemName());
			CUserPreferences.SetDefaultProfile("Wireshark", m_cboDefaultProfileWireshark.GetSelectedItemName());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SaveSettingsToFile
	// Abstract: Saves the settings to a file to be loaded when the program loads
	// --------------------------------------------------------------------------------
	private void SaveSettingsToFile( )
	{
		try
		{
			CXMLWriter clsNewXMLDocument = new CXMLWriter( );
			Element eleSettingsNode = clsNewXMLDocument.AddRootNode("Settings");
			
			if ( CUserPreferences.GetPreferredStandardInterface( ).equals( "" ) == false )
				clsNewXMLDocument.AddAttributeToNode( clsNewXMLDocument.AddChildNode("PreferredInterface", eleSettingsNode), "Value", CUserPreferences.GetPreferredStandardInterface( ) );
			
			if ( CUserPreferences.GetPreferredMonitorInterface( ).equals( "" ) == false )
				clsNewXMLDocument.AddAttributeToNode( clsNewXMLDocument.AddChildNode("PreferredMonitorInterface", eleSettingsNode), "Value", CUserPreferences.GetPreferredMonitorInterface( ) );
			
			clsNewXMLDocument.AddChildNodeWithAttribute( "CommandLogLevel", eleSettingsNode, "Value", CUserPreferences.GetCommandOutputLevel( ) );
			
			for (Map.Entry<String, String> entApplicationPath : CUserPreferences.GetApplicationPaths().entrySet())
			{
				AddApplicationPathToXMLDocument(clsNewXMLDocument, eleSettingsNode, entApplicationPath.getKey(), entApplicationPath.getValue());
			}
			
			AddDefaultProfiles( clsNewXMLDocument, eleSettingsNode );
			
			clsNewXMLDocument.SaveXMLToDisk("Settings.profile");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddApplicationPathToXMLDocument
	// Abstract: Adds a single application path to the XML document
	// --------------------------------------------------------------------------------
	private void AddApplicationPathToXMLDocument(CXMLWriter clsXMLDocument, Element eleRootNode, String strKey, String strValue)
	{
		try
		{
			Element eleNewNode = clsXMLDocument.AddChildNode("ApplicationPath", eleRootNode);
			clsXMLDocument.AddAttributeToNode(eleNewNode, "Program", strKey);
			clsXMLDocument.AddAttributeToNode(eleNewNode, "Path", strValue);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddDefaultProfiles
	// Abstract: Adds the default profiles to the XML document
	// --------------------------------------------------------------------------------
	private void AddDefaultProfiles( CXMLWriter clsXMLDocument, Element eleRootNode )
	{
		try
		{
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileDiscoverHosts, "DiscoverHosts");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileDiscoverNetworks, "DiscoverNetworks");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileForgePackets, "ForgePackets");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileReplayInjectPackets, "ReplayInjectPackets" );
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileSniffPasswords, "SniffPasswords");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfilePing, "Ping");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileTraceRoute, "TraceRoute");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileWhoIs, "WhoIs");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileMACChanger, "MACChanger");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileNikto, "Nikto");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileReaver, "Reaver");
			AddDefaultProfile( clsXMLDocument, eleRootNode, m_cboDefaultProfileWireshark, "Wireshark");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddDefaultProfile
	// Abstract: Adds a default profile to the XML document
	// --------------------------------------------------------------------------------
	private void AddDefaultProfile( CXMLWriter clsXMLDocument, Element eleRootNode, CComboBox cboDefaultProfileDropdown, String strProfileFolderName )
	{
		try
		{
			if ( cboDefaultProfileDropdown.GetSelectedIndex( ) > 0 )
			{
				Element eleNewNode = clsXMLDocument.AddChildNodeWithAttribute("DefaultProfile", eleRootNode, "Profile", cboDefaultProfileDropdown.GetSelectedItemName());
				clsXMLDocument.AddAttributeToNode(eleNewNode, "Feature", strProfileFolderName);
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
			if ( meSource.getSource( ) == m_ddbDefaultSettings ) m_ddbDefaultSettings.DisplayPopupMenu( meSource );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
