// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsWhoIs
// Abstract: WhoIs Dialog. Lets you find the owners of websites.
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FOtherToolsBasicToolsWhoIs extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
    private CTextBox m_txtQuery = null;
    
    private JCheckBox m_chkDisplayVersion = null; // --version
    private JCheckBox m_chkDisplayHelpText = null; // --help
    private JCheckBox m_chkUseConfigurationFile = null; // -c FILE --config=FILE
    private CTextBox m_txtUseConfigurationFile = null;
    private JCheckBox m_chkOverrideHostConfiguration = null; // -h HOST --host=HOST
    private CTextBox m_txtOverrideHostConfiguration = null;
    private JCheckBox m_chkSpecifyPort = null; // -p PORT --port=PORT
    private CTextBox m_txtSpecifyPort = null;
    private JCheckBox m_chkForceLookup = null; // -f --force-lookup
    private JCheckBox m_chkOutputVerboseInformation = null; // -v --verbose
    private JCheckBox m_chkDisableQueryRedirect = null; // -n --no-redirect
    private JCheckBox m_chkDisableWhoIsServers = null; // -s --no-whoisservers
    private JCheckBox m_chkSendRawQuery = null; // -a --raw
    private JCheckBox m_chkDisplayRedirections = null; // -i --display-redirections
    private JCheckBox m_chkDisableCache = null; // -d --disable-cache
    private JCheckBox m_chkForceRWhoIsProtocol = null; // -r --rwhois
    private JCheckBox m_chkSpecifyDisplay = null; // --rwhois-display=DISPLAY
    private CTextBox m_txtSpecifyDisplay = null;
    private JCheckBox m_chkLimitResponses = null; // --rwhois-limit=LIMIT
    private CTextBox m_txtLimitResponses = null;
    
    private JButton m_btnStart = null;
    
    private String m_strPassedInTarget = "";

	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsWhoIs
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
    public FOtherToolsBasicToolsWhoIs( )
    {
    	super( "Who Is", 450, 355, false, false, "WhoIs" );
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
	// Abstract: Adds the controls to the window
	// --------------------------------------------------------------------------------
    private void AddControls( )
    {
        try
        {

            CUtilities.AddLabel( m_cntControlContainer, "Query:", 57, 5 );
            m_txtQuery = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 100, 55, 60 );
            
            m_chkDisplayVersion = CUtilities.AddCheckBox( m_cntControlContainer, this, "Display Version", 75, 5 );
            m_chkDisplayHelpText = CUtilities.AddCheckBox( m_cntControlContainer, this, "Display Help", 75, 205 );
            m_chkForceLookup = CUtilities.AddCheckBox( m_cntControlContainer, this, "Force Lookup", 95, 5 );
            m_chkOutputVerboseInformation = CUtilities.AddCheckBox( m_cntControlContainer, this, "Display Verbose Info", 95, 205 );
            m_chkDisableQueryRedirect = CUtilities.AddCheckBox( m_cntControlContainer, this, "Disable Query Redirect", 115, 5 );
            m_chkDisableWhoIsServers = CUtilities.AddCheckBox( m_cntControlContainer, this, "Display WhoIs Servers", 115, 205 );
            m_chkSendRawQuery = CUtilities.AddCheckBox( m_cntControlContainer, this, "Send Raw Query", 135, 5 );
            m_chkDisplayRedirections = CUtilities.AddCheckBox( m_cntControlContainer, this, "Display Redirections", 135, 205 );
            m_chkDisableCache = CUtilities.AddCheckBox( m_cntControlContainer, this, "Disable Cache", 155, 5 );
            m_chkForceRWhoIsProtocol = CUtilities.AddCheckBox( m_cntControlContainer, this, "Force RWhoIs Protocol", 155, 205 );
            
            m_chkUseConfigurationFile = CUtilities.AddCheckBox( m_cntControlContainer, this, "Use Configuration File:", 175, 5 );
            m_txtUseConfigurationFile = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 178, 250 );
            m_chkOverrideHostConfiguration = CUtilities.AddCheckBox( m_cntControlContainer, this, "Override Host Configuration:", 195, 5 );
            m_txtOverrideHostConfiguration = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 198, 250 );
            m_chkSpecifyPort = CUtilities.AddCheckBox( m_cntControlContainer, this, "Specify Port:", 215, 5 );
            m_txtSpecifyPort = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 218, 250 );
            m_chkSpecifyDisplay = CUtilities.AddCheckBox( m_cntControlContainer, this, "Specify Display:", 235, 5 );
            m_txtSpecifyDisplay = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 238, 250 );
            m_chkLimitResponses = CUtilities.AddCheckBox( m_cntControlContainer, this, "Limit Responses:", 255, 5 );
            m_txtLimitResponses = CUtilities.AddTextBox( m_cntControlContainer, null, "", 15, 17, 258, 250 );
            
            m_btnStart = CUtilities.AddButton( m_cntControlContainer, this, "Start", 288, 150, 30, 150 );
            
            m_lstParameters.add(new CProfileParameter("Query", m_txtQuery));
            m_lstParameters.add(new CProfileParameter("DisplayVersion", m_chkDisplayVersion));
            m_lstParameters.add(new CProfileParameter("DisplayHelpText", m_chkDisplayHelpText));
            m_lstParameters.add(new CProfileParameter("ForceLookup", m_chkForceLookup));
            m_lstParameters.add(new CProfileParameter("DisplayVerbose", m_chkOutputVerboseInformation));
            m_lstParameters.add(new CProfileParameter("DisableQueryRedirect", m_chkDisableQueryRedirect));
            m_lstParameters.add(new CProfileParameter("DisableWhoIsServers", m_chkDisableWhoIsServers));
            m_lstParameters.add(new CProfileParameter("SendRawQuery", m_chkSendRawQuery));
            m_lstParameters.add(new CProfileParameter("DisplayRedirections", m_chkDisplayRedirections));
            m_lstParameters.add(new CProfileParameter("DisableCache", m_chkDisableCache));
            m_lstParameters.add(new CProfileParameter("ForceRWhoIsProtocol", m_chkForceRWhoIsProtocol));
            m_lstParameters.add(new CProfileParameter("ConfigurationFile", m_chkUseConfigurationFile, m_txtUseConfigurationFile));
            m_lstParameters.add(new CProfileParameter("OverrideHostConfiguration", m_chkOverrideHostConfiguration, m_txtOverrideHostConfiguration));
            m_lstParameters.add(new CProfileParameter("SpecifyPort", m_chkSpecifyPort, m_txtSpecifyPort));
            m_lstParameters.add(new CProfileParameter("SpecifyDisplay", m_chkSpecifyDisplay, m_txtSpecifyDisplay));
            m_lstParameters.add(new CProfileParameter("LimitResponses", m_chkLimitResponses, m_txtLimitResponses));
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }
    
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click events
	// --------------------------------------------------------------------------------
    @Override
    public void actionPerformed( ActionEvent aeSource )
    {
    	super.actionPerformed( aeSource );
        try
        {
        	if ( m_blnLoading == false )
			{
        		if ( aeSource.getSource( ) == m_btnStart )		btnStart_Click( );
			}
        }
        catch (Exception excError)
        {
        	CUtilities.WriteLog(excError);
        }
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Starts the WhoIs lookup
	// --------------------------------------------------------------------------------
    private void btnStart_Click( )
    {
    	try
    	{
    		String astrArguments[] = new String[] {"whois"};
    		
    		// Add the selected options
    		if ( m_chkDisplayVersion.isSelected( ) == true )
    			astrArguments = CAircrackUtilities.AddArgumentToCommand("-version", "", astrArguments);
    		
    		if ( m_chkDisplayHelpText.isSelected( ) == true )
    			astrArguments = CAircrackUtilities.AddArgumentToCommand("-help", "", astrArguments);
    		
    		if ( m_chkUseConfigurationFile.isSelected( ) == true )
    			astrArguments = CAircrackUtilities.AddArgumentToCommand("c", m_txtUseConfigurationFile.getText().trim(), astrArguments);
    		
    	    if ( m_chkOverrideHostConfiguration.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("h", m_txtOverrideHostConfiguration.getText().trim(), astrArguments);
    	    
    	    if ( m_chkSpecifyPort.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("p", m_txtSpecifyPort.getText().trim(), astrArguments);

    	    if ( m_chkForceLookup.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("f", "", astrArguments);
    	    
    	    if ( m_chkOutputVerboseInformation.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("v", "", astrArguments);
    	    
    	    if ( m_chkDisableQueryRedirect.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("n", "", astrArguments);
    	    
    	    if ( m_chkDisableWhoIsServers.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("s", "", astrArguments);
    	    
    	    if ( m_chkSendRawQuery.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("a", "", astrArguments);

    	    if ( m_chkDisplayRedirections.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("i", "", astrArguments);

    	    if ( m_chkDisableCache.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("d", "", astrArguments);
    	    
    	    if ( m_chkForceRWhoIsProtocol.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("r", "", astrArguments);
    	    
    	    if ( m_chkSpecifyDisplay.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("-rwhois-display=" + m_txtSpecifyDisplay.getText().trim(), "", astrArguments);
    	    
    	    if ( m_chkLimitResponses.isSelected( ) == true )
    	    	astrArguments = CAircrackUtilities.AddArgumentToCommand("-rwhois-limit=" + m_txtLimitResponses.getText().trim(), "", astrArguments);
    		
    		// Append the query
    		astrArguments = CAircrackUtilities.AddArgumentToArray(m_txtQuery.getText().trim(), astrArguments);
    		
    		DProgramOutput dlgWhoIs = new DProgramOutput( "WhoIs - Output", astrArguments );
    		dlgWhoIs.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Starts the WhoIs lookup
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
    
    public void windowOpened( WindowEvent weSource )
    {
    	super.windowOpened( weSource );
    	m_txtQuery.setText( m_strPassedInTarget );
    }

}
