// --------------------------------------------------------------------------------
// Name: FOtherToolsNikto
// Abstract: Screen for setting up a Nikto scan. 
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FOtherToolsNikto extends CAircrackWindow implements ActionListener, MouseListener
{
	protected final static long serialVersionUID = 1L;
	
	private JCheckBox m_chkShowRedirects = null; // -Display 1
	private JCheckBox m_chkShowCookiesReceived = null; // -Display 2
	private JCheckBox m_chkShowAllOKResponses = null; // -Display 3
	private JCheckBox m_chkShowURLsWithAuthentication = null; // -Display 4
	private JCheckBox m_chkShowDebuggingInformation = null; // -Display D
	private JCheckBox m_chkDisplayAllHTTPErrors = null; // -Display E
	private JCheckBox m_chkPrintProgressToSTDOut = null; // -Display P
	private JCheckBox m_chkNoIPsOrHostNames = null; // -Display S
	private JCheckBox m_chkVerboseOutput = null; // -Display V
	
	private JCheckBox m_chkRandomURIEncoding = null; // -evasion 1
	private JCheckBox m_chkDirectorySelfReference = null; // -evasion 2
	private JCheckBox m_chkPrematureURLEnding = null; // -evasion 3
	private JCheckBox m_chkPrependLongRandomString = null; // -evasion 4
	private JCheckBox m_chkFakeParameter = null; // -evasion 5
	private JCheckBox m_chkTabAsRequestSpacer = null; // -evasion 6
	private JCheckBox m_chkChangeCaseURL = null; // -evasion 7
	private JCheckBox m_chkUseWindowsDirectorySeparator = null; // -evasion 8
	private JCheckBox m_chkUseCarriageReturnAsRequestSpacer = null; // -evasion A
	private JCheckBox m_chkUseBinaryValueAsRequestSpacer = null; // -evasion B
	
	private JCheckBox m_chkInterestingFile = null; // -Tuning 1
	private JCheckBox m_chkMisconfigurationDefaultFile = null; // Tuning 2
	private JCheckBox m_chkInformationDisclosure = null; // -Tuning 3
	private JCheckBox m_chkXSSScriptHTMLInjection = null; // -Tuning 4
	private JCheckBox m_chkRemoteFileRetrievalInsideWebRoot = null; // -Tuning 5
	private JCheckBox m_chkDenialOfService = null; // -Tuning 6
	private JCheckBox m_chkRemoteFileRetrievalServerWide = null; // -Tuning 7
	private JCheckBox m_chkCommandExecutionRemoteShell = null; // -Tuning 8
	private JCheckBox m_chkSQLInjection = null; // -Tuning 9
	private JCheckBox m_chkFileUpload = null; // -Tuning 0
	private JCheckBox m_chkAuthenticationBypass = null; // -Tuning a
	private JCheckBox m_chkSoftwareIdentification = null; // -Tuning b
	private JCheckBox m_chkRemoteSourceInclusion = null; // -Tuning c
	
	private JCheckBox m_chkCheckDatabaseForErrors = null; // -dbcheck
	private JCheckBox m_chkDisableResponseCache = null; // -nocache
	private JCheckBox m_chkDisableInteractiveFeatures = null; // -nointeractive
	private JCheckBox m_chkDisableDNSLookups = null; // -nolookup
	private JCheckBox m_chkDisableSSL = null; // -nossl
	private JCheckBox m_chkDisableGuessing404Page = null; // -no404
	private JCheckBox m_chkIgnoreCodes = null; // -IgnoreCode
	
	private JCheckBox m_chkConfirmSubmittingUpdates = null; // -ask
	private CComboBox m_cboConfirmSubmittingUpdates = null; // yes, no, auto
	
	private CListBox m_lstSpecifyCGIDirectories = null;
	private JButton m_btnAddCGIDirectory = null;
	private JButton m_btnEditCGIDirectory = null;
	private JButton m_btnDeleteCGIDirectory = null;
	
	private JCheckBox m_chkSpecifyConfigurationFile = null; // -config
	private CTextBox m_txtSpecifyConfigurationFile = null;
	private JButton m_btnSpecifyConfiguraitonFile = null;
	
	private JCheckBox m_chkOutputFormat = null; // -Format
	private CComboBox m_cboOutputFormat = null; // csv, htm, msf+, nbe, txt, xml
	private final String m_astrOUTPUT_FORMAT_SHORT[] = new String[] {"csv", "htm", "msf+", "nbe", "txt", "xml"};
	
	private CListBox m_lstSpecifyHosts = null;
	private JButton m_btnAddHost = null;
	private JButton m_btnEditHost = null;
	private JButton m_btnDeleteHost = null;
	
	private JCheckBox m_chkSpecifyAuthentication = null; // -id
	private CTextBox m_txtAuthenticationUsername = null;
	private CTextBox m_txtAuthenticationPassword = null;
	private CTextBox m_txtAuthenticationRealm = null;
	
	private JCheckBox m_chkCertificateKeyFile = null;
	private CTextBox m_txtCertificateKeyFile = null;
	private JButton m_btnCertificateKeyFile = null;

	private JCheckBox m_chkMaximumTestingTimePerHost = null; // -maxtime
	private CTextBox m_txtMaximumTestingTimePerHost = null;
	
	private JCheckBox m_chkWriteOutputToFile = null; // -output
	private CTextBox m_txtWriteOutputToFile = null;
	private JButton m_btnWriteOutputToFile = null;
	
	private JCheckBox m_chkPauseBetweenTests = null; // -Pause (seconds)
	private CTextBox m_txtPauseBetweenTests = null;
	
	private JCheckBox m_chkRunSpecificPlugins = null;
	private CListBox m_lstRunSpecificPlugins = null;
	
	private JCheckBox m_chkSpecificPort = null; // -port
	private CTextBox m_txtSpecificPort = null;
	
	private JCheckBox m_chkClientCertificateFile = null; // -RSAcert
	private CTextBox m_txtClientCertificateFile = null;
	private JButton m_btnClientCertificateFile = null;
	
	private JCheckBox m_chkPrependRootValueToAllRequests = null; // -root
	private CTextBox m_txtPrependRootValueToAllRequests = null;
	
	private JCheckBox m_chkSingleRequestMode = null; // -Single
	private JCheckBox m_chkForceSSLMode = null; // -ssl
	
	private JCheckBox m_chkTimeoutForRequests = null; // -timeout
	private CTextBox m_txtTimeoutForRequests = null;
	
	private JCheckBox m_chkOnlyLoadUserDatabases = null; // -Userdbs
	private CComboBox m_cboOnlyLoadUserDatabases = null; // all, test
	
	private JCheckBox m_chkUseProxy = null; // -useproxy
	private JButton m_btnEditNiktoConfigurationFile = null; // nikto.conf
	
	private CListBox m_lstSpecifyVirtualHosts = null;
	private JButton m_btnAddVirtualHost = null;
	private JButton m_btnEditVirtualHost = null;
	private JButton m_btnDeleteVirtualHost = null;
	
	private CDropdownButton m_ddbPlugins = null; // -list-plugins, -update

	private JButton m_btnStart = null;
	
	private final String m_strNIKTO_PROGRAM_PATH = CUserPreferences.GetApplicationPath(CAircrackUtilities.GetProgramMap("nikto.pl"));
	
	private String m_strPassedInHosts = "";
	
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsNikto
	// Abstract: Class constructor 
	// --------------------------------------------------------------------------------
	public FOtherToolsNikto( )
	{
		super("Nikto", 525, 500, false, false, "Nikto");
		try
		{
			AddControls( );
			PopulateSubmittingUpdatesPreference( );
			PopulateOutputFormats( );
			PopulateUserDatabaseOptions( );
			PopulatePlugins( );
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
			JPanel pnlSingleOptions = CUtilities.AddScrollPane(m_cntControlContainer, null, 1175, 500, 365, 5, 65);

            CUtilities.AddLabel(pnlSingleOptions, "Show...", 5, 5);
            m_chkShowRedirects = CUtilities.AddCheckBox(pnlSingleOptions, null, "Redirects", 20, 5);
        	m_chkShowCookiesReceived = CUtilities.AddCheckBox(pnlSingleOptions, null, "Cookies Received", 20, 200);
        	m_chkShowAllOKResponses = CUtilities.AddCheckBox(pnlSingleOptions, null, "All OK Responses", 40, 5);
        	m_chkShowURLsWithAuthentication = CUtilities.AddCheckBox(pnlSingleOptions, null, "URLs w/Authentication", 40, 200);
        	m_chkShowDebuggingInformation = CUtilities.AddCheckBox(pnlSingleOptions, null, "Debugging Info", 60, 5);
        	m_chkDisplayAllHTTPErrors = CUtilities.AddCheckBox(pnlSingleOptions, null, "All HTTP Errors", 60, 200);
        	m_chkPrintProgressToSTDOut = CUtilities.AddCheckBox(pnlSingleOptions, null, "Progress To STD Out", 80, 5);
        	m_chkNoIPsOrHostNames = CUtilities.AddCheckBox(pnlSingleOptions, null, "No IPs/Host Names", 80, 200);
        	m_chkVerboseOutput = CUtilities.AddCheckBox(pnlSingleOptions, null, "Verbose Output", 100, 5);

        	CUtilities.AddLabel(pnlSingleOptions, "Evasion...", 130, 5);
        	m_chkRandomURIEncoding = CUtilities.AddCheckBox(pnlSingleOptions, null, "Random URI Encoding", 150, 5);
        	m_chkDirectorySelfReference = CUtilities.AddCheckBox(pnlSingleOptions, null, "Directory Self-Reference", 150, 200);
        	m_chkPrematureURLEnding = CUtilities.AddCheckBox(pnlSingleOptions, null, "Premature URL Ending", 170, 5);
        	m_chkPrependLongRandomString = CUtilities.AddCheckBox(pnlSingleOptions, null, "Prepend Long Random String", 170, 200);
        	m_chkFakeParameter = CUtilities.AddCheckBox(pnlSingleOptions, null, "Fake Parameter", 190, 5);
        	m_chkTabAsRequestSpacer = CUtilities.AddCheckBox(pnlSingleOptions, null, "Tab As Request Spacer", 190, 200);
        	m_chkChangeCaseURL = CUtilities.AddCheckBox(pnlSingleOptions, null, "Change Case URL", 210, 5);
        	m_chkUseWindowsDirectorySeparator = CUtilities.AddCheckBox(pnlSingleOptions, null, "Windows Directory Separator", 210, 200);
        	m_chkUseBinaryValueAsRequestSpacer = CUtilities.AddCheckBox(pnlSingleOptions, null, "Binary Value Separator", 230, 5);
        	m_chkUseCarriageReturnAsRequestSpacer = CUtilities.AddCheckBox(pnlSingleOptions, null, "Carriage Return Separator", 230, 200);
        	
        	CUtilities.AddLabel(pnlSingleOptions, "Tuning...", 260, 5);
        	m_chkInterestingFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Interesting File", 280, 5);
        	m_chkMisconfigurationDefaultFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Misconfig. Default File", 280, 200);
        	m_chkInformationDisclosure = CUtilities.AddCheckBox(pnlSingleOptions, null, "Information Disclosure", 300, 5);
        	m_chkXSSScriptHTMLInjection = CUtilities.AddCheckBox(pnlSingleOptions, null, "Inject XSS/Script/HTML", 300, 200);
        	m_chkDenialOfService = CUtilities.AddCheckBox(pnlSingleOptions, null, "Denial of Service", 320, 5);
        	m_chkRemoteFileRetrievalInsideWebRoot = CUtilities.AddCheckBox(pnlSingleOptions, null, "Remote File Retrieval - Web Root", 320, 200);
        	m_chkSQLInjection = CUtilities.AddCheckBox(pnlSingleOptions, null, "SQL Injection", 340, 5);
        	m_chkRemoteFileRetrievalServerWide = CUtilities.AddCheckBox(pnlSingleOptions, null, "Remote File Retrieval - Server Wide", 340, 200);
        	m_chkFileUpload = CUtilities.AddCheckBox(pnlSingleOptions, null, "File Upload", 360, 5);
        	m_chkCommandExecutionRemoteShell = CUtilities.AddCheckBox(pnlSingleOptions, null, "Command Execution Remote Shell", 360, 200);
        	m_chkAuthenticationBypass = CUtilities.AddCheckBox(pnlSingleOptions, null, "Authentication Bypass", 380, 5);
        	m_chkSoftwareIdentification = CUtilities.AddCheckBox(pnlSingleOptions, null, "Software Identification", 380, 200);
        	m_chkRemoteSourceInclusion = CUtilities.AddCheckBox(pnlSingleOptions, null, "Remote Source Inclusion", 400, 5);
        	
        	CUtilities.AddLabel(pnlSingleOptions, "Miscellaneous...", 430, 5);
        	m_chkDisableResponseCache = CUtilities.AddCheckBox(pnlSingleOptions, null, "Disable Response Cache", 450, 5);
        	m_chkCheckDatabaseForErrors = CUtilities.AddCheckBox(pnlSingleOptions, null, "Check Database For Errors", 450, 200);
        	m_chkDisableDNSLookups = CUtilities.AddCheckBox(pnlSingleOptions, null, "Disable DNS Lookups", 470, 5);
        	m_chkDisableInteractiveFeatures = CUtilities.AddCheckBox(pnlSingleOptions, null, "Disable Interactive Features", 470, 200);
        	m_chkDisableSSL = CUtilities.AddCheckBox(pnlSingleOptions, null, "Disable SSL", 490, 5);
        	m_chkDisableGuessing404Page = CUtilities.AddCheckBox(pnlSingleOptions, null, "Disable Guessing 404 Pages", 490, 200);
        	m_chkIgnoreCodes = CUtilities.AddCheckBox(pnlSingleOptions, null, "Ignore Codes", 510, 5);
        	m_chkSingleRequestMode = CUtilities.AddCheckBox(pnlSingleOptions, null, "Single Request Mode", 510, 200);
        	m_chkForceSSLMode = CUtilities.AddCheckBox(pnlSingleOptions, null, "Force SSL", 530, 5);
        	m_chkUseProxy = CUtilities.AddCheckBox(pnlSingleOptions, null, "Use Proxy", 530, 200);
        	
        	m_chkConfirmSubmittingUpdates = CUtilities.AddCheckBox(pnlSingleOptions, null, "Confirm Submitting Updates:", 550, 5);
        	m_cboConfirmSubmittingUpdates = CUtilities.AddComboBox(pnlSingleOptions, null, 553, 235, 18, 75);
        	
        	m_chkSpecifyConfigurationFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Configuration File:", 572, 5);
        	m_txtSpecifyConfigurationFile = CUtilities.AddTextBox(pnlSingleOptions, null, m_strNIKTO_PROGRAM_PATH + "nikto.conf", 15, 100, 575, 165);
        	m_btnSpecifyConfiguraitonFile = CUtilities.AddButton(pnlSingleOptions, this, "...", 575, 335, 18, 50);
        	
        	m_chkOutputFormat = CUtilities.AddCheckBox(pnlSingleOptions, null, "Output Format:", 594, 5);
        	m_cboOutputFormat = CUtilities.AddComboBox(pnlSingleOptions, null, 597, 140, 18, 250);
        	
        	m_chkSpecifyAuthentication = CUtilities.AddCheckBox(pnlSingleOptions, null, "Authentication:", 617, 5); // -id
        	CUtilities.AddLabel(pnlSingleOptions, "Username", 637, 145);
        	m_txtAuthenticationUsername = CUtilities.AddTextBox(pnlSingleOptions, null, "", 8, 100, 620, 145);
        	CUtilities.AddLabel(pnlSingleOptions, "Password", 637, 240);
        	m_txtAuthenticationPassword = CUtilities.AddTextBox(pnlSingleOptions, null, "", 8, 100, 620, 240);
        	CUtilities.AddLabel(pnlSingleOptions, "Realm", 637, 335);
        	m_txtAuthenticationRealm = CUtilities.AddTextBox(pnlSingleOptions, null, "", 8, 100, 620, 335);
        	
        	m_chkCertificateKeyFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Certificate Key File:", 658, 5);
        	m_txtCertificateKeyFile = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 660, 170);
        	m_btnCertificateKeyFile = CUtilities.AddButton(pnlSingleOptions, this, "...", 660, 340, 18, 50);

        	m_chkMaximumTestingTimePerHost = CUtilities.AddCheckBox(pnlSingleOptions, null, "Max. Testing Time Per Host:", 683, 5);
        	m_txtMaximumTestingTimePerHost = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 685, 230);
        	
        	m_chkWriteOutputToFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Write Output to File:", 707, 5);
        	m_txtWriteOutputToFile = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 710, 180);
        	m_btnWriteOutputToFile = CUtilities.AddButton(pnlSingleOptions, this, "...", 710, 350, 18, 50);
        	
        	m_chkPauseBetweenTests = CUtilities.AddCheckBox(pnlSingleOptions, null, "Pause Between Tests (seconds):", 733, 5);
        	m_txtPauseBetweenTests = CUtilities.AddTextBox(pnlSingleOptions, null, "", 8, 10, 735, 265);
        	
        	m_chkSpecificPort = CUtilities.AddCheckBox(pnlSingleOptions, null, "Port:", 757, 5);
        	m_txtSpecificPort = CUtilities.AddTextBox(pnlSingleOptions, null, "", 4, 5, 760, 65);
        	
        	m_chkClientCertificateFile = CUtilities.AddCheckBox(pnlSingleOptions, null, "Client Certificate File:", 782, 5);
        	m_txtClientCertificateFile = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 785, 185);
        	m_btnClientCertificateFile = CUtilities.AddButton(pnlSingleOptions, this, "...", 455, 785, 18, 50);
        	
        	m_chkPrependRootValueToAllRequests = CUtilities.AddCheckBox(pnlSingleOptions, null, "Prepend Root Value to Requests:", 807, 5); // -root
        	m_txtPrependRootValueToAllRequests = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 810, 270);
        	
        	m_chkTimeoutForRequests = CUtilities.AddCheckBox(pnlSingleOptions, null, "Request Timeout:", 832, 5);
        	m_txtTimeoutForRequests = CUtilities.AddTextBox(pnlSingleOptions, null, "", 15, 100, 835, 155);
        	
        	m_chkOnlyLoadUserDatabases = CUtilities.AddCheckBox(pnlSingleOptions, null, "Only Load User Databases:", 857, 5);
        	m_cboOnlyLoadUserDatabases = CUtilities.AddComboBox(pnlSingleOptions, null, 860, 225, 18, 240);

        	m_chkRunSpecificPlugins = CUtilities.AddCheckBox(pnlSingleOptions, null, "Run Specific Plugins:", 883, 5);
        	m_lstRunSpecificPlugins = CUtilities.AddListBox(pnlSingleOptions, null, 885, 180, 50, 200);
        	m_lstRunSpecificPlugins.SetSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        	
        	CUtilities.AddLabel(pnlSingleOptions, "Hosts:", 940, 10);
        	m_lstSpecifyHosts = CUtilities.AddListBox(pnlSingleOptions, null, 940, 130, 70, 200);
        	m_btnAddHost = CUtilities.AddButton(pnlSingleOptions, this, "", 940, 335, 18, 50);
        	m_btnEditHost = CUtilities.AddButton(pnlSingleOptions, this, "", 965, 335, 18, 50);
        	m_btnDeleteHost = CUtilities.AddButton(pnlSingleOptions, this, "", 990, 335, 18, 50);
        	m_btnAddHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/add.png", 18, 18));
        	m_btnEditHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/edit.png", 18, 18));
        	m_btnDeleteHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/delete.png", 18, 18));
        	
        	CUtilities.AddLabel(pnlSingleOptions, "Virtual Hosts:", 1015, 10);
        	m_lstSpecifyVirtualHosts = CUtilities.AddListBox(pnlSingleOptions, null, 1015, 130, 70, 200);
        	m_btnAddVirtualHost = CUtilities.AddButton(pnlSingleOptions, this, "", 1015, 335, 18, 50);
        	m_btnEditVirtualHost = CUtilities.AddButton(pnlSingleOptions, this, "", 1040, 335, 18, 50);
        	m_btnDeleteVirtualHost = CUtilities.AddButton(pnlSingleOptions, this, "", 1065, 335, 18, 50);
        	m_btnAddVirtualHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/add.png", 18, 18));
        	m_btnEditVirtualHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/edit.png", 18, 18));
        	m_btnDeleteVirtualHost.setIcon(CAircrackUtilities.GetIconFromImage("Assets/delete.png", 18, 18));
        	
        	CUtilities.AddLabel(pnlSingleOptions, "CGI Directories:", 1090, 10);
        	m_lstSpecifyCGIDirectories = CUtilities.AddListBox(pnlSingleOptions, null, 1090, 130, 70, 200);
        	m_btnAddCGIDirectory = CUtilities.AddButton(pnlSingleOptions, this, "", 1090, 335, 18, 50);
        	m_btnEditCGIDirectory = CUtilities.AddButton(pnlSingleOptions, this, "", 1115, 335, 18, 50);
        	m_btnDeleteCGIDirectory = CUtilities.AddButton(pnlSingleOptions, this, "", 1140, 335, 18, 50);
        	m_btnAddCGIDirectory.setIcon(CAircrackUtilities.GetIconFromImage("Assets/add.png", 18, 18));
        	m_btnEditCGIDirectory.setIcon(CAircrackUtilities.GetIconFromImage("Assets/edit.png", 18, 18));
        	m_btnDeleteCGIDirectory.setIcon(CAircrackUtilities.GetIconFromImage("Assets/delete.png", 18, 18));
        	
        	m_ddbPlugins = CUtilities.AddDropdownButton(m_cntControlContainer, this, this, "Plugins", 432, 5, 18, 150);
        	m_ddbPlugins.SetMenuOptions( new String[] {"List Plug-ins", "Update Plug-ins"} );
        	
        	m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 432, 187, 18, 150);
        	m_btnEditNiktoConfigurationFile = CUtilities.AddButton(m_cntControlContainer, this, "Edit Nikto.conf", 432, 365, 18, 140);
        	
        	m_lstParameters.add(new CProfileParameter("ShowRedirects", m_chkShowRedirects));
        	m_lstParameters.add(new CProfileParameter("ShowCookiesReceived", m_chkShowCookiesReceived));
        	m_lstParameters.add(new CProfileParameter("ShowAllOKResponses", m_chkShowAllOKResponses));
        	m_lstParameters.add(new CProfileParameter("ShowURLsWithAuthentication", m_chkShowURLsWithAuthentication));
        	m_lstParameters.add(new CProfileParameter("ShowDebuggingInformation", m_chkShowDebuggingInformation));
        	m_lstParameters.add(new CProfileParameter("DisplayAllHTTPErrors", m_chkDisplayAllHTTPErrors));
        	m_lstParameters.add(new CProfileParameter("PrintProgressToSTDOut", m_chkPrintProgressToSTDOut));
        	m_lstParameters.add(new CProfileParameter("NoIPsOrHostNames", m_chkNoIPsOrHostNames));
        	m_lstParameters.add(new CProfileParameter("VerboseOutput", m_chkVerboseOutput));
        	m_lstParameters.add(new CProfileParameter("RandomURIEncoding", m_chkRandomURIEncoding));
        	m_lstParameters.add(new CProfileParameter("DirectorySelfReference", m_chkDirectorySelfReference));
        	m_lstParameters.add(new CProfileParameter("PrematureURLEnding", m_chkPrematureURLEnding));
        	m_lstParameters.add(new CProfileParameter("PrependLongRandomString", m_chkPrependLongRandomString));
        	m_lstParameters.add(new CProfileParameter("FakeParameter", m_chkFakeParameter));
        	m_lstParameters.add(new CProfileParameter("TabAsRequestSpacer", m_chkTabAsRequestSpacer));
        	m_lstParameters.add(new CProfileParameter("ChangeCaseURL", m_chkChangeCaseURL));
        	m_lstParameters.add(new CProfileParameter("UseWindowsDirectorySeparator", m_chkUseWindowsDirectorySeparator));
        	m_lstParameters.add(new CProfileParameter("UseCarriageReturnAsRequestSpacer", m_chkUseCarriageReturnAsRequestSpacer));
        	m_lstParameters.add(new CProfileParameter("UseBinaryValueAsRequestSpacer", m_chkUseBinaryValueAsRequestSpacer));
        	m_lstParameters.add(new CProfileParameter("InterestingFile", m_chkInterestingFile));
        	m_lstParameters.add(new CProfileParameter("MisconfigurationDefaultFile", m_chkMisconfigurationDefaultFile));
        	m_lstParameters.add(new CProfileParameter("InformationDisclosure", m_chkInformationDisclosure));
        	m_lstParameters.add(new CProfileParameter("XSSScriptHTMLInjection", m_chkXSSScriptHTMLInjection));
        	m_lstParameters.add(new CProfileParameter("RemoteFileRetrievalInsideWebRoot", m_chkRemoteFileRetrievalInsideWebRoot));
        	m_lstParameters.add(new CProfileParameter("DenialOfService", m_chkDenialOfService));
        	m_lstParameters.add(new CProfileParameter("RemoteFileRetrievalServerWide", m_chkRemoteFileRetrievalServerWide));
        	m_lstParameters.add(new CProfileParameter("CommandExecutionRemoteShell", m_chkCommandExecutionRemoteShell));
        	m_lstParameters.add(new CProfileParameter("SQLInjection", m_chkSQLInjection));
        	m_lstParameters.add(new CProfileParameter("FileUpload", m_chkFileUpload));
        	m_lstParameters.add(new CProfileParameter("AuthenticationBypass", m_chkAuthenticationBypass));
        	m_lstParameters.add(new CProfileParameter("SoftwareIdentification", m_chkSoftwareIdentification));
        	m_lstParameters.add(new CProfileParameter("RemoteSourceInclusion", m_chkRemoteSourceInclusion));
        	m_lstParameters.add(new CProfileParameter("CheckDatabaseForErrors", m_chkCheckDatabaseForErrors));
        	m_lstParameters.add(new CProfileParameter("DisableResponseCache", m_chkDisableResponseCache));
        	m_lstParameters.add(new CProfileParameter("DisableInteractiveFeatures", m_chkDisableInteractiveFeatures));
        	m_lstParameters.add(new CProfileParameter("DisableDNSLookups", m_chkDisableDNSLookups));
        	m_lstParameters.add(new CProfileParameter("DisableSSL", m_chkDisableSSL));
        	m_lstParameters.add(new CProfileParameter("DisableGuessing404Page", m_chkDisableGuessing404Page));
        	m_lstParameters.add(new CProfileParameter("IgnoreCodes", m_chkIgnoreCodes));
        	m_lstParameters.add(new CProfileParameter("SingleRequestMode", m_chkSingleRequestMode));
        	m_lstParameters.add(new CProfileParameter("ForceSSLMode", m_chkForceSSLMode));
        	m_lstParameters.add(new CProfileParameter("UseProxy", m_chkUseProxy));
        	m_lstParameters.add(new CProfileParameter("ConfirmSubmittingUpdates", m_chkConfirmSubmittingUpdates, m_cboConfirmSubmittingUpdates));
        	m_lstParameters.add(new CProfileParameter("ConfigurationFile", m_chkSpecifyConfigurationFile, m_txtSpecifyConfigurationFile));
        	m_lstParameters.add(new CProfileParameter("OutputFormat", m_chkOutputFormat, m_cboOutputFormat));
        	m_lstParameters.add(new CProfileParameter("AuthenticationUsername", m_chkSpecifyAuthentication, m_txtAuthenticationUsername));
        	m_lstParameters.add(new CProfileParameter("AuthenticationPassword", m_chkSpecifyAuthentication, m_txtAuthenticationPassword));
        	m_lstParameters.add(new CProfileParameter("AuthenticationRealm", m_chkSpecifyAuthentication, m_txtAuthenticationRealm));
			m_lstParameters.add(new CProfileParameter("CertificateKeyFile", m_chkCertificateKeyFile, m_txtCertificateKeyFile));
			m_lstParameters.add(new CProfileParameter("MaximumTestingTimePerHour", m_chkMaximumTestingTimePerHost, m_txtMaximumTestingTimePerHost));
			m_lstParameters.add(new CProfileParameter("WriteOutputToFile", m_chkWriteOutputToFile, m_txtWriteOutputToFile));
			m_lstParameters.add(new CProfileParameter("PauseBetweenTests", m_chkPauseBetweenTests, m_txtPauseBetweenTests));
			m_lstParameters.add(new CProfileParameter("SpecificPort", m_chkSpecificPort, m_txtSpecificPort));
			m_lstParameters.add(new CProfileParameter("ClientCertificateFile", m_chkClientCertificateFile, m_txtClientCertificateFile));
			m_lstParameters.add(new CProfileParameter("PrependRootValueToAllRequests", m_chkPrependRootValueToAllRequests, m_txtPrependRootValueToAllRequests));
			m_lstParameters.add(new CProfileParameter("RunSpecificPlugins", m_chkRunSpecificPlugins, m_lstRunSpecificPlugins));
			m_lstParameters.add(new CProfileParameter("Host", m_lstSpecifyHosts));
			m_lstParameters.add(new CProfileParameter("VirtualHost", m_lstSpecifyVirtualHosts));
			m_lstParameters.add(new CProfileParameter("CGIDirectories", m_lstSpecifyCGIDirectories));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateSubmittingUpdatesPreference
	// Abstract: Populates the options for how to submit updates 
	// --------------------------------------------------------------------------------
	private void PopulateSubmittingUpdatesPreference( )
	{
		try
		{
			m_cboConfirmSubmittingUpdates.SetSorted( false );
			m_cboConfirmSubmittingUpdates.AddItemToList("Auto", 0);
			m_cboConfirmSubmittingUpdates.AddItemToList("Yes", 1);
			m_cboConfirmSubmittingUpdates.AddItemToList("No", 2);
			m_cboConfirmSubmittingUpdates.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateOutputFormats
	// Abstract: Populates the options for outputting the data 
	// --------------------------------------------------------------------------------
	private void PopulateOutputFormats( )
	{
		try
		{
			m_cboOutputFormat.SetSorted( false );
			m_cboOutputFormat.AddItemToList("Comma-Separated Values", 0);
			m_cboOutputFormat.AddItemToList("HyperText Markup Language", 1);
			m_cboOutputFormat.AddItemToList("Log to Metasploit", 2);
			m_cboOutputFormat.AddItemToList("Nessus NBE", 3);
			m_cboOutputFormat.AddItemToList("Text file", 4);
			m_cboOutputFormat.AddItemToList("Extensible Markup Language", 5);
			m_cboOutputFormat.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateUserDatabaseOptions
	// Abstract: Populates the options for user databases 
	// --------------------------------------------------------------------------------
	private void PopulateUserDatabaseOptions( )
	{
		try
		{
			m_cboOnlyLoadUserDatabases.SetSorted( false );
			m_cboOnlyLoadUserDatabases.AddItemToList("Only user databases", 0);
			m_cboOnlyLoadUserDatabases.AddItemToList("Disable standard databases", 1);
			m_cboOnlyLoadUserDatabases.AddItemToList("Disable test databases", 2);
			m_cboOnlyLoadUserDatabases.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulatePlugins
	// Abstract: Populates the available plugins 
	// --------------------------------------------------------------------------------
	private void PopulatePlugins( )
	{
		try
		{
			String strNiktoScript = m_strNIKTO_PROGRAM_PATH + "nikto.pl";
			String strNiktoConfig = m_strNIKTO_PROGRAM_PATH + "nikto.conf";
			String astrCommand[] = new String[] {strNiktoScript, "-config", strNiktoConfig, "-list-plugins"};
			CProcess clsNiktoPlugins = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsNiktoPlugins.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				if ( strBuffer.indexOf("Plugin: ") == 0 )
				{
					m_lstRunSpecificPlugins.AddItemToList(strBuffer.substring(8), 0);
				}
				strBuffer = brOutput.readLine( );
			}
			
			clsNiktoPlugins.CloseProcess( );
			
			m_lstRunSpecificPlugins.SetSelectedIndex( 0 );
			m_lstRunSpecificPlugins.SetSelectedIndex( -1 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Action event listener. 
	// --------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnSpecifyConfiguraitonFile ) CAircrackUtilities.DisplayFileChooser(m_txtSpecifyConfigurationFile, this, m_chkSpecifyConfigurationFile);
				else if ( aeSource.getSource( ) == m_btnCertificateKeyFile ) CAircrackUtilities.DisplayFileChooser(m_txtCertificateKeyFile, this, m_chkCertificateKeyFile);
				else if ( aeSource.getSource( ) == m_btnWriteOutputToFile ) CAircrackUtilities.DisplayFileChooser(m_txtWriteOutputToFile, this, m_chkWriteOutputToFile);
				else if ( aeSource.getSource( ) == m_btnClientCertificateFile ) CAircrackUtilities.DisplayFileChooser(m_txtClientCertificateFile, this, m_chkClientCertificateFile);
				else if ( aeSource.getSource( ) == m_btnAddHost ) DisplayAddDialog( m_lstSpecifyHosts, "Please specify the name of the new host:" );
				else if ( aeSource.getSource( ) == m_btnEditHost ) DisplayEditDialog( m_lstSpecifyHosts, "Please specify the new name of this host:" );
				else if ( aeSource.getSource( ) == m_btnDeleteHost ) DisplayDeleteDialog( m_lstSpecifyHosts, "Are you sure you want to delete this host?");
				else if ( aeSource.getSource( ) == m_btnAddVirtualHost ) DisplayAddDialog( m_lstSpecifyVirtualHosts, "Please specify the name of the new virtual host:" );
				else if ( aeSource.getSource( ) == m_btnEditVirtualHost ) DisplayEditDialog( m_lstSpecifyVirtualHosts, "Please specify the new name of this virtual host:" );
				else if ( aeSource.getSource( ) == m_btnDeleteVirtualHost ) DisplayDeleteDialog( m_lstSpecifyVirtualHosts, "Are you sure you want to delete this virtual host?" );
				else if ( aeSource.getSource( ) == m_btnAddCGIDirectory ) DisplayAddDialog( m_lstSpecifyCGIDirectories, "Please specify the name of the new CGI directory:" );
				else if ( aeSource.getSource( ) == m_btnEditCGIDirectory ) DisplayEditDialog( m_lstSpecifyCGIDirectories, "Please specify the new name of this CGI directory:" );
				else if ( aeSource.getSource( ) == m_btnDeleteCGIDirectory ) DisplayDeleteDialog( m_lstSpecifyCGIDirectories, "Are you sure you want to delete this CGI directory?" );
				else if ( aeSource.getSource( ) == m_ddbPlugins.GetMenuItem("List Plug-ins") ) miListPlugins_Click( );
				else if ( aeSource.getSource( ) == m_ddbPlugins.GetMenuItem("Update Plug-ins") ) miUpdatePlugins_Click( );
				else if ( aeSource.getSource( ) == m_btnStart ) btnStart_Click( );
				else if ( aeSource.getSource( ) == m_btnEditNiktoConfigurationFile ) btnEditNiktoConfigurationFile_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
		
	// --------------------------------------------------------------------------------
	// Name: DisplayAddDialog
	// Abstract: Displays the add item dialog for the listbox with the specified message. 
	// --------------------------------------------------------------------------------
	private void DisplayAddDialog(CListBox lstListToAddTo, String strMessage)
	{
		try
		{
			String strNewValue = JOptionPane.showInputDialog( strMessage );
			lstListToAddTo.AddItemToList(strNewValue, 0);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayEditDialog
	// Abstract: Displays the edit item dialog for the listbox with the specified message. 
	// --------------------------------------------------------------------------------
	private void DisplayEditDialog(CListBox lstListToEditFrom, String strMessage)
	{
		try
		{
			if ( lstListToEditFrom.GetSelectedIndex( ) > -1 )
			{
				String strNewValue = JOptionPane.showInputDialog( strMessage );
				CNameValuePair clsNameValuePair = lstListToEditFrom.GetSelectedItem( );
				clsNameValuePair.SetName( strNewValue );
				lstListToEditFrom.RemoveAt( lstListToEditFrom.GetSelectedIndex( ) );
				lstListToEditFrom.AddItemToList( clsNameValuePair, true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: DisplayDeleteDialog
	// Abstract: Displays the delete item dialog for the listbox with the specified message. 
	// --------------------------------------------------------------------------------
	private void DisplayDeleteDialog(CListBox lstListToDeleteFrom, String strMessage)
	{
		try
		{
			if ( lstListToDeleteFrom.GetSelectedIndex( ) > -1 )
			{
				if ( ! CAircrackUtilities.ConvertIntegerToBoolean(JOptionPane.showConfirmDialog(null, strMessage, "Aircrack-NGUI", JOptionPane.YES_NO_OPTION)) == true )
				{
					lstListToDeleteFrom.RemoveAt( lstListToDeleteFrom.GetSelectedIndex( ) );
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miListPlugins_Click
	// Abstract: Lists the plugins configured 
	// --------------------------------------------------------------------------------
	private void miListPlugins_Click( )
	{
		try
		{
			String strNiktoProgram = m_strNIKTO_PROGRAM_PATH + "nikto.pl";
			String strNiktoConfigFile = m_txtSpecifyConfigurationFile.getText( );
			String astrCommand[] = new String[] {strNiktoProgram, "-config", strNiktoConfigFile, "-list-plugins"};
			
			DProgramOutput dlgNiktoConfigFile = new DProgramOutput( "Nikto Output", astrCommand );
			dlgNiktoConfigFile.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miUpdatePlugins_Click
	// Abstract: Updates the plug-ins 
	// --------------------------------------------------------------------------------
	private void miUpdatePlugins_Click( )
	{
		try
		{
			String strNiktoProgram = m_strNIKTO_PROGRAM_PATH + "nikto.pl";
			String strNiktoConfigFile = m_txtSpecifyConfigurationFile.getText( );
			String astrCommand[] = new String[] {strNiktoProgram, "-config", strNiktoConfigFile, "-update"};
			
			DProgramOutput dlgNiktoConfigFile = new DProgramOutput( "Nikto Output", astrCommand );
			dlgNiktoConfigFile.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Start button Click event. Launches the Nikto process. 
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			String strNiktoProgram = m_strNIKTO_PROGRAM_PATH + "nikto.pl";
			String astrCommand[] = new String[] { strNiktoProgram };
			
			astrCommand = AppendShowOptionsToCommand( astrCommand );
			astrCommand = AppendEvasionOptionsToCommand( astrCommand );
			astrCommand = AppendTuningOptionsToCommand( astrCommand );
			astrCommand = AppendMiscellaneousOptionsToCommand( astrCommand );
			astrCommand = AppendHostsVirtualHostsAndCGIDirectories( astrCommand );
			
			DProgramOutput dlgProgramOutput = new DProgramOutput("Nikto Output", astrCommand);
			dlgProgramOutput.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AppendShowOptionsToCommand
	// Abstract: Appends the "Show..." options to the command. 
	// --------------------------------------------------------------------------------
	private String[] AppendShowOptionsToCommand( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrCommand;
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowRedirects, "Display", "1", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowCookiesReceived, "Display", "2", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowAllOKResponses, "Display", "3", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowURLsWithAuthentication, "Display", "4", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkShowDebuggingInformation, "Display", "D", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisplayAllHTTPErrors, "Display", "E", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPrintProgressToSTDOut, "Display", "P", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkNoIPsOrHostNames, "Display", "S", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkVerboseOutput, "Display", "V", astrNewCommand);			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: AppendEvasionOptionsToCommand
	// Abstract: Appends the "Evasion..." options to the command. 
	// --------------------------------------------------------------------------------
	private String[] AppendEvasionOptionsToCommand( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrCommand;
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRandomURIEncoding, "evasion", "1", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDirectorySelfReference, "evasion", "2", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPrematureURLEnding, "evasion", "3", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPrependLongRandomString, "evasion", "4", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkFakeParameter, "evasion", "5", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTabAsRequestSpacer, "evasion", "6", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkChangeCaseURL, "evasion", "7", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseWindowsDirectorySeparator, "evasion", "8", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseCarriageReturnAsRequestSpacer, "evasion", "A", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseBinaryValueAsRequestSpacer, "evasion", "B", astrNewCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: AppendTuningOptionsToCommand
	// Abstract: Appends the tuning options to the command 
	// --------------------------------------------------------------------------------
	private String[] AppendTuningOptionsToCommand( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrCommand;
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkInterestingFile, "Tuning", "1", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkMisconfigurationDefaultFile, "Tuning", "2", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkInformationDisclosure, "Tuning", "3", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkXSSScriptHTMLInjection, "Tuning", "4", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRemoteFileRetrievalInsideWebRoot, "Tuning", "5", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDenialOfService, "Tuning", "6", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRemoteFileRetrievalServerWide, "Tuning", "7", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkCommandExecutionRemoteShell, "Tuning", "8", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSQLInjection, "Tuning", "9", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkFileUpload, "Tuning", "0", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkAuthenticationBypass, "Tuning", "a", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSoftwareIdentification, "Tuning", "b", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRemoteSourceInclusion, "Tuning", "c", astrNewCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: AppendMiscellaneousOptionsToCommand
	// Abstract: Appends the miscellaneous options to the command 
	// --------------------------------------------------------------------------------
	private String[] AppendMiscellaneousOptionsToCommand( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrCommand;
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkCheckDatabaseForErrors, "dbcheck", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisableResponseCache, "nocache", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisableInteractiveFeatures, "nointeractive", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisableDNSLookups, "nolookup", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisableSSL, "nossl", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDisableGuessing404Page, "no404", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkIgnoreCodes, "IgnoreCode", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSingleRequestMode, "Single", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkForceSSLMode, "ssl", "", astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkUseProxy, "useproxy", "", astrNewCommand);
			astrNewCommand = AppendMiscellaneousOptionsWithControlsToCommand( astrCommand );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: AppendMiscellaneousOptionsWithControlsToCommand
	// Abstract: Appends the miscellaneous options that require more controls than just
	//			 checkboxes to the command 
	// --------------------------------------------------------------------------------
	private String[] AppendMiscellaneousOptionsWithControlsToCommand( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrCommand;
			
			// Checkbox and Textbox
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyConfigurationFile, "config", m_txtSpecifyConfigurationFile.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkCertificateKeyFile, "key", m_txtCertificateKeyFile.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkMaximumTestingTimePerHost, "maxtime", m_txtMaximumTestingTimePerHost.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkWriteOutputToFile, "output", m_txtWriteOutputToFile.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPauseBetweenTests, "Pause", m_txtPauseBetweenTests.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecificPort, "port", m_txtSpecificPort.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkClientCertificateFile, "RSAcert", m_txtClientCertificateFile.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPrependRootValueToAllRequests, "root", m_txtPrependRootValueToAllRequests.getText().trim(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTimeoutForRequests, "timeout", m_txtTimeoutForRequests.getText().trim(), astrNewCommand);
			
			// Checkbox and ComboBox
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkConfirmSubmittingUpdates, "ask", m_cboConfirmSubmittingUpdates.GetSelectedItemName().toLowerCase(), astrNewCommand);
			astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkOutputFormat, "Format", m_astrOUTPUT_FORMAT_SHORT[m_cboOutputFormat.GetSelectedIndex()], astrNewCommand);
			
			if ( m_cboOnlyLoadUserDatabases.GetSelectedItemValue( ) == 0 )
				astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkOnlyLoadUserDatabases, "Userdbs", "", astrNewCommand);
			else if ( m_cboOnlyLoadUserDatabases.GetSelectedItemValue( ) == 1 )
				astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkOnlyLoadUserDatabases, "Userdbs", "all", astrNewCommand);
			else if ( m_cboOnlyLoadUserDatabases.GetSelectedItemValue( ) == 2 )
				astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkOnlyLoadUserDatabases, "Userdbs", "tests", astrNewCommand);
			
			if ( m_chkSpecifyAuthentication.isSelected( ) )
			{
				String strAuthentication = m_txtAuthenticationUsername.getText( ).trim( );
				strAuthentication += ":" + m_txtAuthenticationPassword.getText( ).trim( );
				if ( m_txtAuthenticationRealm.getText( ).trim( ).equals( "" ) == false )
					strAuthentication += ":" + m_txtAuthenticationRealm.getText( ).trim( );
				astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyAuthentication, "id", strAuthentication, astrNewCommand);
			}
			
			if ( m_chkRunSpecificPlugins.isSelected( ) )
			{
				int aintSelectedPlugins[] = m_lstRunSpecificPlugins.GetSelectedIndices( );
				String strPlugin = "";
				String strCommaDelimitedPluginList = "";
				for ( int intIndex = 0; intIndex < aintSelectedPlugins.length; intIndex += 1 )
				{
					strPlugin = m_lstRunSpecificPlugins.GetItemName( aintSelectedPlugins[intIndex] );
					if ( strCommaDelimitedPluginList.equals("") == false )
						strCommaDelimitedPluginList += ",";
					strCommaDelimitedPluginList += strPlugin;
				}
				astrNewCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkRunSpecificPlugins, "Plugins", strCommaDelimitedPluginList, astrNewCommand);
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}
	
	// --------------------------------------------------------------------------------
	// Name: AppendHostsVirtualHostsAndCGIDirectories
	// Abstract: Appends the hosts, virtual hosts, and CGI directories to the command
	// --------------------------------------------------------------------------------
	private String[] AppendHostsVirtualHostsAndCGIDirectories( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{
			int intIndex = 0;
			String strValue = "";
			String strCGIDirectories = "";
			
			astrNewCommand = astrCommand;
			
			// Hosts
			for ( intIndex = 0; intIndex < m_lstSpecifyHosts.Length( ); intIndex += 1 )
			{
				strValue = m_lstSpecifyHosts.GetItemName( intIndex );
				astrNewCommand = CAircrackUtilities.AddArgumentToCommand("host", strValue, astrNewCommand);
			}
			
			// Virtual Host
			for ( intIndex = 0; intIndex < m_lstSpecifyVirtualHosts.Length( ); intIndex += 1 )
			{
				strValue = m_lstSpecifyVirtualHosts.GetItemName( intIndex );
				astrNewCommand = CAircrackUtilities.AddArgumentToCommand("vhost", strValue, astrNewCommand);
			}
			
			// CGI Directory
			for ( intIndex = 0; intIndex < m_lstSpecifyCGIDirectories.Length( ); intIndex += 1 )
			{
				strValue = m_lstSpecifyCGIDirectories.GetItemName( intIndex );
				if ( strCGIDirectories.equals("") == false )
					strCGIDirectories += " ";
				strCGIDirectories += strValue;
			}
			
			if ( m_lstSpecifyCGIDirectories.Length( ) > 0 )
				astrNewCommand = CAircrackUtilities.AddArgumentToCommand("Cgidirs", strValue, astrNewCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: btnEditNiktoConfigurationFile_Click
	// Abstract: Opens the nikto configuration file in a text editor
	// --------------------------------------------------------------------------------
	private void btnEditNiktoConfigurationFile_Click( )
	{
		try
		{
			String strNiktoConfigurationFile = m_txtSpecifyConfigurationFile.getText( ).trim( );
			Desktop dtpDesktop = Desktop.getDesktop( );
			if ( dtpDesktop.isSupported( Desktop.Action.EDIT ) == true )
			{
				File filSavedFile = new File( strNiktoConfigurationFile );
				if ( filSavedFile.exists( ) == true && filSavedFile.length( ) > 0 )
				{
					dtpDesktop.edit( filSavedFile );	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nikto configuration file at \"" + strNiktoConfigurationFile + "\" doesn't exist.");
				}
			}
			else
			{
				String astrCommand[] = new String[] {"xterm", "-e", "nano " + strNiktoConfigurationFile};
				CProcess clsXTermWindow = new CProcess(astrCommand, true, false, false);
				clsXTermWindow.CloseProcess();
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Mouse button click event 
	// --------------------------------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent meSource)
	{
		try
		{
			if ( meSource.getSource( ) == m_ddbPlugins ) m_ddbPlugins.DisplayPopupMenu( meSource );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddHost
	// Abstract: Allows outside forms to set the host 
	// --------------------------------------------------------------------------------
	public void AddHost( String strNewHost )
	{
		try
		{
			if ( m_strPassedInHosts.equals("") == false )
				m_strPassedInHosts += "%%%";
			m_strPassedInHosts += strNewHost;
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
		if ( m_strPassedInHosts.equals("") == false )
		{
			String astrHosts[] = m_strPassedInHosts.split("%%%");
			for ( int intIndex = 0; intIndex < astrHosts.length; intIndex += 1 )
			{
				m_lstSpecifyHosts.AddItemToList(astrHosts[intIndex], 0);
			}
		}
	}
	
	// Not used
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
