// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsDig
// Abstract: Use the dig DNS lookup tool
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import java.awt.event.*;

public class FOtherToolsBasicToolsDig extends CAircrackWindow implements ActionListener
{
	
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtTarget = null;
	private CComboBox m_cboType = null;
	private CTextBox m_txtServer = null;
	
	// Options
	private JCheckBox m_chkSourceIPAddress = null; // -b
	private CTextBox m_txtSourceIPAddress = null;
	private JCheckBox m_chkDefaultQueryClass = null; // -c
	private CComboBox m_cboDefaultQueryClass = null;
	private JCheckBox m_chkLoadRequests = null; // -r
	private CTextBox m_txtLoadRequests = null;
	private JButton m_btnLoadRequests = null;
	private JCheckBox m_chkPort = null; // -p
	private CTextBox m_txtPort = null;
	private JCheckBox m_chkQueryName = null; // -q
	private CTextBox m_txtQueryName = null;
	private JCheckBox m_chkReverseLookup = null; // -x
	private CTextBox m_txtReverseLookup = null;
	private JCheckBox m_chkTSIGKey = null; // -k
	private CTextBox m_txtTSIGKey = null;
	private JButton m_btnTSIGKey = null;
	private JCheckBox m_chkIPVersion = null; // -4, -6
	private CComboBox m_cboIPVersion = null;
	private JCheckBox m_chkMemoryUsageDebugging = null; // -m
	
	// More Options
	private CComboBox m_cboUseTCPQueriesForNameServers = null;
	private CComboBox m_cboIgnoreUDPTruncation = null;
	private CComboBox m_cboDefinedSearchList = null;
	private CComboBox m_cboShowSearchResults = null;
	private CComboBox m_cboOnlyAARecords = null;
	private CComboBox m_cboAuthenticDataBit = null;
	private CComboBox m_cboCheckingDisabledBit = null;
	private CComboBox m_cboDisplayCLASS = null;
	private CComboBox m_cboDisplayTTL = null;
	private CComboBox m_cboRecursionDesired = null;
	private CComboBox m_cboAuthoritativeNameServers = null;
	private CComboBox m_cboTraceServerPath = null;
	private CComboBox m_cboPrintDigVersion = null;
	private CComboBox m_cboPrintShortOutput = null;
	private CComboBox m_cboShowServerIP = null;
	private CComboBox m_cboShowComments = null;
	private CComboBox m_cboShowStatistics = null;
	private CComboBox m_cboPrintQueryWhenSent = null;
	private CComboBox m_cboPrintQuestionSection = null;
	private CComboBox m_cboPrintAnswer = null;
	private CComboBox m_cboPrintAuthoritySection = null;
	private CComboBox m_cboPrintAdditionalSection = null;
	private CComboBox m_cboHumanReadableFormat = null;
	private CComboBox m_cboPrintOneSOARecord = null;
	private CComboBox m_cboKillAfterFail = null;
	private CComboBox m_cboDisplayMalformedAnswers = null;
	private CComboBox m_cboRequestDNSSECRecords = null;
	private CComboBox m_cboIncludeEDNSRequest = null;
	private JCheckBox m_chkDomainName = null; // +domain=somename
	private CTextBox m_txtDomainName = null;
	private JCheckBox m_chkQueryTimeout = null; // +time=T
	private CTextBox m_txtQueryTimeout = null;
	private JCheckBox m_chkUDPMaximumTries = null; // +tries=T
	private CTextBox m_txtUDPMaximumTries = null;
	private JCheckBox m_chkDotCount = null; // +ndots=#
	private CTextBox m_txtDotCount = null;
	private JCheckBox m_chkUDPBufferSize = null; // +bufsize=#
	private CTextBox m_txtUDPBufferSize = null;
	private JCheckBox m_chkEDNSVersion = null; // +edns=#
	private CTextBox m_txtEDNSVersion = null;
	private JCheckBox m_chkTrustedKeys = null; // +trusted-key=#
	private CTextBox m_txtTrustedKeys = null;
	private JButton m_btnTrustedKeys = null;
	private JButton m_btnGo = null;
	
	private String m_strPassedInTarget = "";

	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsDig
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsBasicToolsDig( )
	{
		super("Dig", 500, 400, false, false, "Dig");
		try
		{
			AddControls( );
			AddTypes( );
			AddDefaultQueryClasses( );
			AddIPVersions( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the screen
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
			CUtilities.AddLabel(m_cntControlContainer, "Target:", 57, 5);
			m_txtTarget = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 55, 60);
			CUtilities.AddLabel(m_cntControlContainer, "Type:", 77, 5);
			m_cboType = CUtilities.AddComboBox(m_cntControlContainer, null, 75, 60, 18, 150);
			CUtilities.AddLabel(m_cntControlContainer, "Server:", 97, 5);
			m_txtServer = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 95, 60);
			
			JPanel pnlOptions = CUtilities.AddScrollPane(m_cntControlContainer, null, 940, 485, 200, 5, 125);
			m_chkSourceIPAddress = CUtilities.AddCheckBox(pnlOptions, null, "Source IP:", 2, 5);
			m_txtSourceIPAddress = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 5, 110);
			m_chkDefaultQueryClass = CUtilities.AddCheckBox(pnlOptions, null, "Default Query Class:", 24, 5);
			m_cboDefaultQueryClass = CUtilities.AddComboBox(pnlOptions, null, 27, 180, 18, 60);
			m_chkLoadRequests = CUtilities.AddCheckBox(pnlOptions, null, "Load Requests:", 45, 5);
			m_txtLoadRequests = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 47, 145);
			m_btnLoadRequests = CUtilities.AddButton(pnlOptions, this, "...", 47, 315, 18, 50);
			m_chkPort = CUtilities.AddCheckBox(pnlOptions, null, "Port:", 67, 5);
			m_txtPort = CUtilities.AddTextBox(pnlOptions, null, "", 5, 5, 69, 65);
			m_chkQueryName = CUtilities.AddCheckBox(pnlOptions, null, "Query Name:", 87, 5);
			m_txtQueryName = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 89, 125);
			m_chkReverseLookup = CUtilities.AddCheckBox(pnlOptions, null, "Reverse Lookup:", 107, 5);
			m_txtReverseLookup = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 109, 150);
			m_chkTSIGKey = CUtilities.AddCheckBox(pnlOptions, null, "TSIG Key:", 127, 5);
			m_txtTSIGKey = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 131, 100);
			m_btnTSIGKey = CUtilities.AddButton(pnlOptions, this, "...", 131, 270, 18, 50);
			m_chkIPVersion = CUtilities.AddCheckBox(pnlOptions, null, "IP Version:", 151, 5);
			m_cboIPVersion = CUtilities.AddComboBox(pnlOptions, null, 153, 110, 18, 50);
			m_chkMemoryUsageDebugging = CUtilities.AddCheckBox(pnlOptions, null, "Memory Usage Debugging", 171, 5);
			
			CUtilities.AddLabel(pnlOptions, "More Options", 211, 5);
			CUtilities.AddLabel(pnlOptions, "TCP Queries for Name Servers:", 231, 5);
			m_cboUseTCPQueriesForNameServers = CUtilities.AddComboBox(pnlOptions, null, 229, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Ignore UDP Truncation:", 251, 5);
			m_cboIgnoreUDPTruncation = CUtilities.AddComboBox(pnlOptions, null, 251, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Defined Search List:", 271, 5);
			m_cboDefinedSearchList = CUtilities.AddComboBox(pnlOptions, null, 271, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Show Search Results:", 291, 5);
			m_cboShowSearchResults = CUtilities.AddComboBox(pnlOptions, null, 291, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Only AA Records:", 311, 5);
			m_cboOnlyAARecords = CUtilities.AddComboBox(pnlOptions, null, 311, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Authentic Data Bit:", 331, 5);
			m_cboAuthenticDataBit = CUtilities.AddComboBox(pnlOptions, null, 331, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Checking Disabled Bit:", 351, 5);
			m_cboCheckingDisabledBit = CUtilities.AddComboBox(pnlOptions, null, 351, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Display CLASS:", 371, 5);
			m_cboDisplayCLASS = CUtilities.AddComboBox(pnlOptions, null, 371, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Display TTL:", 391, 5);
			m_cboDisplayTTL = CUtilities.AddComboBox(pnlOptions, null, 391, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Recursion Desired:", 411, 5);
			m_cboRecursionDesired = CUtilities.AddComboBox(pnlOptions, null, 411, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Authoritative Name Servers:", 431, 5);
			m_cboAuthoritativeNameServers = CUtilities.AddComboBox(pnlOptions, null, 431, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Trace Server Path:", 451, 5);
			m_cboTraceServerPath = CUtilities.AddComboBox(pnlOptions, null, 451, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Dig Version:", 471, 5);
			m_cboPrintDigVersion = CUtilities.AddComboBox(pnlOptions, null, 471, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Short Output:", 491, 5);
			m_cboPrintShortOutput = CUtilities.AddComboBox(pnlOptions, null, 491, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Show Server IP:", 511, 5);
			m_cboShowServerIP = CUtilities.AddComboBox(pnlOptions, null, 511, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Show Comments:", 531, 5);
			m_cboShowComments = CUtilities.AddComboBox(pnlOptions, null, 531, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Show Statistics:", 551, 5);
			m_cboShowStatistics = CUtilities.AddComboBox(pnlOptions, null, 551, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Query When Sent:", 571, 5);
			m_cboPrintQueryWhenSent = CUtilities.AddComboBox(pnlOptions, null, 571, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Question Section:", 591, 5);
			m_cboPrintQuestionSection = CUtilities.AddComboBox(pnlOptions, null, 591, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Answer:", 611, 5);
			m_cboPrintAnswer = CUtilities.AddComboBox(pnlOptions, null, 611, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Authority Section:", 631, 5);
			m_cboPrintAuthoritySection = CUtilities.AddComboBox(pnlOptions, null, 631, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print Additional Section:", 651, 5);
			m_cboPrintAdditionalSection = CUtilities.AddComboBox(pnlOptions, null, 651, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Human Readable Format:", 671, 5);
			m_cboHumanReadableFormat = CUtilities.AddComboBox(pnlOptions, null, 671, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Print One SOA Record:", 691, 5);
			m_cboPrintOneSOARecord = CUtilities.AddComboBox(pnlOptions, null, 691, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Kill After Fail:", 711, 5);
			m_cboKillAfterFail = CUtilities.AddComboBox(pnlOptions, null, 711, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Display Malformed Answers:", 731, 5);
			m_cboDisplayMalformedAnswers = CUtilities.AddComboBox(pnlOptions, null, 731, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Request DNSSEC Records:", 751, 5);
			m_cboRequestDNSSECRecords = CUtilities.AddComboBox(pnlOptions, null, 751, 225, 18, 100);
			CUtilities.AddLabel(pnlOptions, "Include EDNS Request:", 771, 5);
			m_cboIncludeEDNSRequest = CUtilities.AddComboBox(pnlOptions, null, 771, 225, 18, 100);
			
			m_chkDomainName = CUtilities.AddCheckBox(pnlOptions, null, "Domain Name:", 791, 5);
			m_txtDomainName = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 791, 200);
			m_chkQueryTimeout = CUtilities.AddCheckBox(pnlOptions, null, "Query Timeout:", 811, 5);
			m_txtQueryTimeout = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 811, 200);
			m_chkUDPMaximumTries = CUtilities.AddCheckBox(pnlOptions, null, "Maximum UDP Tries:", 831, 5);
			m_txtUDPMaximumTries = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 831, 200);
			m_chkDotCount = CUtilities.AddCheckBox(pnlOptions, null, "Dot Count:", 851, 5);
			m_txtDotCount = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 851, 200);
			m_chkUDPBufferSize = CUtilities.AddCheckBox(pnlOptions, null, "UDP Buffer Size:", 871, 5);
			m_txtUDPBufferSize = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 871, 200);
			m_chkEDNSVersion = CUtilities.AddCheckBox(pnlOptions, null, "EDNS Version:", 891, 5);
			m_txtEDNSVersion = CUtilities.AddTextBox(pnlOptions, null, "", 15, 10, 891, 200);
			m_chkTrustedKeys = CUtilities.AddCheckBox(pnlOptions, null, "Trusted Keys:", 911, 5);
			m_txtTrustedKeys = CUtilities.AddTextBox(pnlOptions, null, "", 15, 100, 911, 200);
			m_btnTrustedKeys = CUtilities.AddButton(pnlOptions, this, "...", 911, 370, 18, 50);
	
			m_btnGo = CUtilities.AddButton(m_cntControlContainer, this, "Go", 335, 200, 18, 100);
			
			AddOptionsValues( );
			
			AddProfileParameters( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	private void AddOptionsValues( )
	{
		try
		{
			AddOptionValues( m_cboUseTCPQueriesForNameServers );
			AddOptionValues( m_cboIgnoreUDPTruncation );
			AddOptionValues( m_cboDefinedSearchList );
			AddOptionValues( m_cboShowSearchResults );
			AddOptionValues( m_cboOnlyAARecords );
			AddOptionValues( m_cboAuthenticDataBit );
			AddOptionValues( m_cboCheckingDisabledBit );
			AddOptionValues( m_cboDisplayCLASS );
			AddOptionValues( m_cboDisplayTTL );
			AddOptionValues( m_cboRecursionDesired );
			AddOptionValues( m_cboAuthoritativeNameServers );
			AddOptionValues( m_cboTraceServerPath );
			AddOptionValues( m_cboPrintDigVersion );
			AddOptionValues( m_cboPrintShortOutput );
			AddOptionValues( m_cboShowServerIP );
			AddOptionValues( m_cboShowComments );
			AddOptionValues( m_cboShowStatistics );
			AddOptionValues( m_cboPrintQueryWhenSent );
			AddOptionValues( m_cboPrintQuestionSection );
			AddOptionValues( m_cboPrintAnswer );
			AddOptionValues( m_cboPrintAuthoritySection );
			AddOptionValues( m_cboPrintAdditionalSection );
			AddOptionValues( m_cboHumanReadableFormat );
			AddOptionValues( m_cboPrintOneSOARecord );
			AddOptionValues( m_cboKillAfterFail );
			AddOptionValues( m_cboDisplayMalformedAnswers );
			AddOptionValues( m_cboRequestDNSSECRecords );
			AddOptionValues( m_cboIncludeEDNSRequest );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	private void AddProfileParameters( )
	{
		try
		{
			m_lstParameters.add(new CProfileParameter("Target", m_txtTarget));
			m_lstParameters.add(new CProfileParameter("Type", m_cboType));
			m_lstParameters.add(new CProfileParameter("Server", m_txtServer));
			
			// Options
			m_lstParameters.add(new CProfileParameter("SourceIP", m_chkSourceIPAddress, m_txtSourceIPAddress));
			m_lstParameters.add(new CProfileParameter("DefaultQueryClass", m_chkDefaultQueryClass, m_cboDefaultQueryClass));
			m_lstParameters.add(new CProfileParameter("LoadRequests", m_chkLoadRequests, m_txtLoadRequests));
			m_lstParameters.add(new CProfileParameter("Port", m_chkPort, m_txtPort));
			m_lstParameters.add(new CProfileParameter("QueryName", m_chkQueryName, m_txtQueryName));
			m_lstParameters.add(new CProfileParameter("ReverseLookup", m_chkReverseLookup, m_txtReverseLookup));
			m_lstParameters.add(new CProfileParameter("TSIGKey", m_chkTSIGKey, m_txtTSIGKey));
			m_lstParameters.add(new CProfileParameter("IPVersion", m_chkIPVersion, m_cboIPVersion));
			m_lstParameters.add(new CProfileParameter("MemoryUsageDebugging", m_chkMemoryUsageDebugging));
			
			// More Options
			m_lstParameters.add(new CProfileParameter("TCPQueriesForNameServers", m_cboUseTCPQueriesForNameServers));
			m_lstParameters.add(new CProfileParameter("IgnoreUDPTruncation", m_cboIgnoreUDPTruncation));
			m_lstParameters.add(new CProfileParameter("DefinedSearchList", m_cboDefinedSearchList));
			m_lstParameters.add(new CProfileParameter("ShowSearchResults", m_cboShowSearchResults));
			m_lstParameters.add(new CProfileParameter("OnlyAARecords", m_cboOnlyAARecords));
			m_lstParameters.add(new CProfileParameter("AuthenticDataBit", m_cboAuthenticDataBit));
			m_lstParameters.add(new CProfileParameter("CheckingDisabledBit", m_cboCheckingDisabledBit));
			m_lstParameters.add(new CProfileParameter("DisplayCLASS", m_cboDisplayCLASS));
			m_lstParameters.add(new CProfileParameter("DisplayTTL", m_cboDisplayTTL));
			m_lstParameters.add(new CProfileParameter("RecursionDesired", m_cboRecursionDesired));
			m_lstParameters.add(new CProfileParameter("AuthoritativeNameServers", m_cboAuthoritativeNameServers));
			m_lstParameters.add(new CProfileParameter("TraceServerPath", m_cboTraceServerPath));
			m_lstParameters.add(new CProfileParameter("PrintDigVersion", m_cboPrintDigVersion));
			m_lstParameters.add(new CProfileParameter("PrintShortOutput", m_cboPrintShortOutput));
			m_lstParameters.add(new CProfileParameter("ShowServerIP", m_cboShowServerIP));
			m_lstParameters.add(new CProfileParameter("ShowComments", m_cboShowComments));
			m_lstParameters.add(new CProfileParameter("ShowStatistics", m_cboShowStatistics));
			m_lstParameters.add(new CProfileParameter("PrintQueryWhenSent", m_cboPrintQueryWhenSent));
			m_lstParameters.add(new CProfileParameter("PrintQuestionSection", m_cboPrintQuestionSection));
			m_lstParameters.add(new CProfileParameter("PrintAnswer", m_cboPrintAnswer));
			m_lstParameters.add(new CProfileParameter("PrintAuthoritySection", m_cboPrintAuthoritySection));
			m_lstParameters.add(new CProfileParameter("PrintAdditionalSection", m_cboPrintAdditionalSection));
			m_lstParameters.add(new CProfileParameter("HumanReadableFormat", m_cboHumanReadableFormat));
			m_lstParameters.add(new CProfileParameter("PrintOneSOARecord", m_cboPrintOneSOARecord));
			m_lstParameters.add(new CProfileParameter("KillAfterFail", m_cboKillAfterFail));
			m_lstParameters.add(new CProfileParameter("DisplayMalformedAnswers", m_cboDisplayMalformedAnswers));
			m_lstParameters.add(new CProfileParameter("RequestDNSSECRecords", m_cboRequestDNSSECRecords));
			m_lstParameters.add(new CProfileParameter("IncludeEDNSRequest", m_cboIncludeEDNSRequest));
			m_lstParameters.add(new CProfileParameter("DomainName", m_chkDomainName, m_txtDomainName));
			m_lstParameters.add(new CProfileParameter("QueryTimeout", m_chkQueryTimeout, m_txtQueryTimeout));
			m_lstParameters.add(new CProfileParameter("UDPMaximumTries", m_chkUDPMaximumTries, m_txtUDPMaximumTries));
			m_lstParameters.add(new CProfileParameter("DotCount", m_chkDotCount, m_txtDotCount));
			m_lstParameters.add(new CProfileParameter("UDPBufferSize", m_chkUDPBufferSize, m_txtUDPBufferSize));
			m_lstParameters.add(new CProfileParameter("EDNSVersion", m_chkEDNSVersion, m_txtEDNSVersion));
			m_lstParameters.add(new CProfileParameter("TrustedKeys", m_chkTrustedKeys, m_txtTrustedKeys));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	private void AddOptionValues( CComboBox cboTarget )
	{
		try
		{
			cboTarget.SetSorted( false );
			cboTarget.AddItemToList("DEFAULT", 0);
			cboTarget.AddItemToList("YES", 1);
			cboTarget.AddItemToList("NO", 2);
			cboTarget.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	private void AddTypes( )
	{
		try
		{
			m_cboType.SetSorted( false );
			m_cboType.AddItemToList("ANY", 0);
			m_cboType.AddItemToList("A", 0);
			m_cboType.AddItemToList("AAAA", 0);
			m_cboType.AddItemToList("AFSDB", 0);
			m_cboType.AddItemToList("APL", 0);
			m_cboType.AddItemToList("CAA", 0);
			m_cboType.AddItemToList("CERT", 0);
			m_cboType.AddItemToList("CNAME", 0);
			m_cboType.AddItemToList("DHCID", 0);
			m_cboType.AddItemToList("DLV", 0);
			m_cboType.AddItemToList("DNAME", 0);
			m_cboType.AddItemToList("DNSKEY", 0);
			m_cboType.AddItemToList("DS", 0);
			m_cboType.AddItemToList("HIP", 0);
			m_cboType.AddItemToList("IPSECKEY", 0);
			m_cboType.AddItemToList("KEY", 0);
			m_cboType.AddItemToList("KX", 0);
			m_cboType.AddItemToList("LOC", 0);
			m_cboType.AddItemToList("MX", 0);
			m_cboType.AddItemToList("NAPTR", 0);
			m_cboType.AddItemToList("NS", 0);
			m_cboType.AddItemToList("NSEC", 0);
			m_cboType.AddItemToList("NSEC3", 0);
			m_cboType.AddItemToList("NSEC3PARAM", 0);
			m_cboType.AddItemToList("PTR", 0);
			m_cboType.AddItemToList("RRSIG", 0);
			m_cboType.AddItemToList("RP", 0);
			m_cboType.AddItemToList("SIG", 0);
			m_cboType.AddItemToList("SOA", 0);
			m_cboType.AddItemToList("SPF", 0);
			m_cboType.AddItemToList("SRV", 0);
			m_cboType.AddItemToList("SSHFP", 0);
			m_cboType.AddItemToList("TA", 0);
			m_cboType.AddItemToList("TKEY", 0);
			m_cboType.AddItemToList("TLSA", 0);
			m_cboType.AddItemToList("TSIG", 0);
			m_cboType.AddItemToList("TXT", 0);
			m_cboType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	private void AddDefaultQueryClasses( )
	{
		try
		{
			
			m_cboDefaultQueryClass.SetSorted( false );
			m_cboDefaultQueryClass.AddItemToList("IN", 0);
			m_cboDefaultQueryClass.AddItemToList("HS", 0);
			m_cboDefaultQueryClass.AddItemToList("CH", 0);
			m_cboDefaultQueryClass.SetSelectedIndex( 0 );

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	private void AddIPVersions( )
	{
		try
		{
			m_cboIPVersion.SetSorted( false );
			m_cboIPVersion.AddItemToList("4", 4);
			m_cboIPVersion.AddItemToList("6", 6);
			m_cboIPVersion.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed( aeSource );
		try
		{
			// Make sure the form is done loading
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnLoadRequests ) CAircrackUtilities.DisplayFileChooser(m_txtLoadRequests, this);
				else if ( aeSource.getSource( ) == m_btnTrustedKeys ) CAircrackUtilities.DisplayFileChooser(m_txtTrustedKeys, this);
				else if ( aeSource.getSource( ) == m_btnTSIGKey ) CAircrackUtilities.DisplayFileChooser(m_txtTSIGKey, this);
				else if ( aeSource.getSource( ) == m_btnGo ) btnGo_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	private void btnGo_Click( )
	{
		try
		{
			String astrCommand[] = BuildDigCommand( );
			
			DProgramOutput dlgDig = new DProgramOutput("Dig Output", astrCommand);
			dlgDig.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	private String[] BuildDigCommand( )
	{
		String astrCommand[] = new String[] {"dig"};
		try
		{
			if ( m_txtServer.getText( ).trim( ).equals( "" ) == false )
				astrCommand = CAircrackUtilities.AddArgumentToArray(m_txtServer.getText( ).trim( ), astrCommand );
			astrCommand = CAircrackUtilities.AddArgumentToArray(m_txtTarget.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentToArray(m_cboType.GetSelectedItemName( ), astrCommand);
			
			// Options
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSourceIPAddress, "b", m_txtSourceIPAddress.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDefaultQueryClass, "c", m_cboDefaultQueryClass.GetSelectedItemName( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkLoadRequests, "r", m_txtLoadRequests.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkPort, "p", m_txtPort.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkQueryName, "q", m_txtQueryName.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkReverseLookup, "x", m_txtReverseLookup.getText( ).trim( ), astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkTSIGKey, "k", m_txtTSIGKey.getText( ).trim( ), astrCommand);
			if ( m_chkIPVersion.isSelected( ) == true )
			{
				if ( m_cboIPVersion.GetSelectedItemValue( ) == 4 )
					astrCommand = CAircrackUtilities.AddArgumentToCommand("4", "", astrCommand);
				else
					astrCommand = CAircrackUtilities.AddArgumentToCommand("6", "", astrCommand);
			}
			astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkMemoryUsageDebugging, "m", "", astrCommand);
			
			// More Options
			astrCommand = AddMoreOptionsToCommand( astrCommand );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	private String[] AddMoreOptionsToCommand( String astrCommand[] )
	{
		try
		{
			astrCommand = AddMoreOptionToCommand( m_cboUseTCPQueriesForNameServers, "tcp", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboUseTCPQueriesForNameServers, "ignore", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboDefinedSearchList, "search", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboShowSearchResults, "showsearch", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboOnlyAARecords, "aaonly", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboAuthenticDataBit, "adflag", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboCheckingDisabledBit, "cdflag", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboDisplayCLASS, "cl", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboDisplayTTL, "ttlid", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboRecursionDesired, "recurse", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboAuthoritativeNameServers, "nssearch", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboTraceServerPath, "trace", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintDigVersion, "cmd", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintShortOutput, "short", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboShowServerIP, "identify", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboShowComments, "comments", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboShowStatistics, "stats", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintQueryWhenSent, "qr", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintQuestionSection, "question", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintAnswer, "answer", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintAuthoritySection, "authority", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintAdditionalSection, "additional", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboHumanReadableFormat, "multiline", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboPrintOneSOARecord, "onesoa", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboKillAfterFail, "fail", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboDisplayMalformedAnswers, "besteffort", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboRequestDNSSECRecords, "dnssec", astrCommand );
			astrCommand = AddMoreOptionToCommand( m_cboIncludeEDNSRequest, "nsid", astrCommand );
		
			astrCommand = AddMoreOptionWithValueToCommand( m_chkDomainName, m_txtDomainName, "domain", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkQueryTimeout, m_txtQueryTimeout, "time", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkUDPMaximumTries, m_txtUDPMaximumTries, "tries", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkDotCount, m_txtDotCount, "ndots", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkUDPBufferSize, m_txtUDPBufferSize, "bufsize", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkEDNSVersion, m_txtEDNSVersion, "edns", astrCommand );
			astrCommand = AddMoreOptionWithValueToCommand( m_chkTrustedKeys, m_txtTrustedKeys, "trusted-key", astrCommand );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	private String[] AddMoreOptionToCommand( CComboBox cboTarget, String strOptionName, String astrCommand[] )
	{
		try
		{
			if ( cboTarget.GetSelectedItemName( ).equals( "YES" ) )
			{
				astrCommand = CAircrackUtilities.AddArgumentToArray("+" + strOptionName, astrCommand);
			}
			else if ( cboTarget.GetSelectedItemName( ).equals( "NO" ) )
			{
				astrCommand = CAircrackUtilities.AddArgumentToArray("+no" + strOptionName, astrCommand);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	private String[] AddMoreOptionWithValueToCommand( JCheckBox chkTarget, CTextBox txtTarget, String strOptionName, String astrCommand[])
	{
		try
		{
			if ( chkTarget.isSelected( ) == true )
			{
				astrCommand = CAircrackUtilities.AddArgumentToArray("+" + strOptionName + "=" + txtTarget.getText( ).trim( ), astrCommand);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrCommand;
	}
	
	public void SetDestination( String strIPAddress )
	{
		try
		{
			m_strPassedInTarget = strIPAddress;
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
