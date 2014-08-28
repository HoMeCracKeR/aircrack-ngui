// --------------------------------------------------------------------------------
// Name: FSniffPasswords
// Abstract: Sniffs passwords out of a certain 
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FSniffPasswords extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtExpression = null;
	
	private JCheckBox m_chkHalfDuplexTCPStreamReassembly = null; // -c
	private JCheckBox m_chkDebuggingMode = null; // -d
	private JCheckBox m_chkAutomaticProtocolDetection = null; // -m
	private JCheckBox m_chkDontResolveToHostnames = null; // -n
	
	private JCheckBox m_chkSpecifyInterface = null; // -i
	private CComboBox m_cboSpecifyInterface = null;

	private JCheckBox m_chkSpecifyPCAPFile = null; // -p
	private CTextBox m_txtSpecifyPCAPFile = null;
	private JButton m_btnSpecifyPCAPFile = null;
	
	private JCheckBox m_chkSpecifySnapLenBytes = null; // -s, default 1024
	private CTextBox m_txtSpecifySnapLenBytes = null;

	private JCheckBox m_chkLoadTriggersFromServicesFile = null; // -f
	private CTextBox m_txtLoadTriggersFromServicesFile = null;
	private JButton m_btnLoadTriggersFromServicesFile = null;

	private JCheckBox m_chkSpecifyTriggers = null; // -t
	private CTextBox m_txtSpecifyTriggers = null;
	
	private JCheckBox m_chkLoadFromSaveFile = null; // -r
	private CTextBox m_txtLoadFromSaveFile = null;
	private JButton m_btnLoadFromSaveFile = null;
	
	private JCheckBox m_chkWriteOutToFile = null; // -w
	private CTextBox m_txtWriteOutToFile = null;
	private JButton m_btnWriteOutToFile = null;
	
	private JButton m_btnStart = null;
		
	// --------------------------------------------------------------------------------
    // Name: FMain
    // Abstract: Class constructor
    // --------------------------------------------------------------------------------
    public FSniffPasswords()
    {
    	super("Sniff Passwords", 525, 375, false, false, "SniffPasswords");
        try
        {
            AddControls();
            PopulateInterfaces();
        }
        catch (Exception excError)
        {
            // Display error message
            
            CUtilities.WriteLog( excError );
        }
    }
    // ----------------------------------------------------------------------------
    // Name: AddControls
    // Abstract: Adds the controls to the window
    // ----------------------------------------------------------------------------
    private void AddControls( )
    {
        
        try
        {

        	CUtilities.AddLabel(m_cntControlContainer, "Expression:", 71, 5);
        	m_txtExpression = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 1000, 70, 90);
        	
        	m_chkHalfDuplexTCPStreamReassembly = CUtilities.AddCheckBox(m_cntControlContainer, null, "Half-duplex TCP Stream Reassembly", 90, 5); // -c
        	m_chkDebuggingMode = CUtilities.AddCheckBox(m_cntControlContainer, null, "Debugging Mode", 90, 290); // -d
        	m_chkAutomaticProtocolDetection = CUtilities.AddCheckBox(m_cntControlContainer, null, "Automatic Protocol Detection", 110, 5); // -m
        	m_chkDontResolveToHostnames = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Resolve Host Names", 110, 290); // -n
        	
        	m_chkSpecifyInterface = CUtilities.AddCheckBox(m_cntControlContainer, null, "Interface:", 132, 5); // -i
        	m_cboSpecifyInterface = CUtilities.AddComboBox(m_cntControlContainer, null, 135, 100, 18, 100);

        	m_chkSpecifyPCAPFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "PCAP File:", 157, 5); // -p
        	m_txtSpecifyPCAPFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 160, 100);
        	m_btnSpecifyPCAPFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 160, 270, 18, 50);
        	
        	m_chkSpecifySnapLenBytes = CUtilities.AddCheckBox(m_cntControlContainer, null, "SnapLen Bytes:", 182, 5); // -s, default 1024
        	m_txtSpecifySnapLenBytes = CUtilities.AddTextBox(m_cntControlContainer, null, "1024", 8, 50, 185, 145);

        	m_chkLoadTriggersFromServicesFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "Triggers From Service File:", 207, 5); // -f
        	m_txtLoadTriggersFromServicesFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 210, 220);
        	m_btnLoadTriggersFromServicesFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 210, 390, 18, 50);

        	m_chkSpecifyTriggers = CUtilities.AddCheckBox(m_cntControlContainer, null, "Triggers:", 232, 5); // -t
        	m_txtSpecifyTriggers = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 235, 95);
        	
        	m_chkLoadFromSaveFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "Load From File:", 257, 5); // -r
        	m_txtLoadFromSaveFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 260, 140);
        	m_btnLoadFromSaveFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 260, 310, 18, 50);
        	
        	m_chkWriteOutToFile = CUtilities.AddCheckBox(m_cntControlContainer, null, "Write To File:", 282, 5); // -w
        	m_txtWriteOutToFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 285, 125);
        	m_btnWriteOutToFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 285, 295, 18, 50);

        	m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 320, 188, 18, 150);
        	
        	m_lstParameters.add(new CProfileParameter("Expression", m_txtExpression));
        	m_lstParameters.add(new CProfileParameter("HalfDuplexTCPStreamReassembly", m_chkHalfDuplexTCPStreamReassembly));
        	m_lstParameters.add(new CProfileParameter("DebuggingMode", m_chkDebuggingMode));
        	m_lstParameters.add(new CProfileParameter("AutomaticProtocolDetection", m_chkAutomaticProtocolDetection));
        	m_lstParameters.add(new CProfileParameter("DontResolveToHostnames", m_chkDontResolveToHostnames));
        	m_lstParameters.add(new CProfileParameter("Interface", m_chkSpecifyInterface, m_cboSpecifyInterface));
        	m_lstParameters.add(new CProfileParameter("PCAPFile", m_chkSpecifyPCAPFile, m_txtSpecifyPCAPFile));
        	m_lstParameters.add(new CProfileParameter("SnapLenBytes", m_chkSpecifySnapLenBytes, m_txtSpecifySnapLenBytes));
        	m_lstParameters.add(new CProfileParameter("LoadTriggersFromServicesFile", m_chkLoadTriggersFromServicesFile, m_txtLoadTriggersFromServicesFile));
        	m_lstParameters.add(new CProfileParameter("Triggers", m_chkSpecifyTriggers, m_txtSpecifyTriggers));
        	m_lstParameters.add(new CProfileParameter("LoadFromFile", m_chkLoadFromSaveFile, m_txtLoadFromSaveFile));
        	m_lstParameters.add(new CProfileParameter("WriteOutToFile", m_chkWriteOutToFile, m_txtWriteOutToFile));
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateInterfaces
    // Abstract: Populates the available interfaces to sniff on
    // ----------------------------------------------------------------------------
    private void PopulateInterfaces( )
    {
    	try
    	{
    		m_cboSpecifyInterface.SetSorted( false );
    		CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
    		String strPreferredInterface = CUserPreferences.GetPreferredStandardInterface( );
    		int intIndex = 0;
    		
    		for ( intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
    			m_cboSpecifyInterface.AddItemToList(aclsInterfaces[intIndex].GetInterface(), 0);
    		
    		m_cboSpecifyInterface.SetSelectedIndex( 0 );
    		
    		CAircrackUtilities.SetComboBoxSelectedValue(m_cboSpecifyInterface, strPreferredInterface);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: actionPerformed
    // Abstract: Button click event handler
    // ----------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent aeSource)
    {
    	super.actionPerformed( aeSource );
    	try
    	{
    		if ( m_blnLoading == false )
			{
	    		if ( aeSource.getSource( ) == m_btnSpecifyPCAPFile )						CAircrackUtilities.DisplayFileChooser(m_txtSpecifyPCAPFile, this, m_chkSpecifyPCAPFile);
	    		else if ( aeSource.getSource( ) == m_btnLoadTriggersFromServicesFile )		CAircrackUtilities.DisplayFileChooser(m_txtLoadTriggersFromServicesFile, this, m_chkLoadTriggersFromServicesFile);
	    		else if ( aeSource.getSource( ) == m_btnLoadFromSaveFile )					CAircrackUtilities.DisplayFileChooser(m_txtLoadFromSaveFile, this, m_chkLoadFromSaveFile);
	    		else if ( aeSource.getSource( ) == m_btnWriteOutToFile )					CAircrackUtilities.DisplayFileChooser(m_txtWriteOutToFile, this, m_chkWriteOutToFile);
	    		else if ( aeSource.getSource( ) == m_btnStart )								btnStart_Click( );
			}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: btnStart_Click
    // Abstract: Start button Click event. Starts the password sniffing process
    // ----------------------------------------------------------------------------
    private void btnStart_Click( )
    {
    	try
    	{
    		String astrCommand[] = new String[] { "dsniff" };
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkHalfDuplexTCPStreamReassembly, "c", "", astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDebuggingMode, "c", "", astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkAutomaticProtocolDetection, "m", "", astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkDontResolveToHostnames, "n", "", astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyInterface, "i", m_cboSpecifyInterface.GetSelectedItemName( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyPCAPFile, "p", m_txtSpecifyPCAPFile.getText( ).trim( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifySnapLenBytes, "s", m_txtSpecifySnapLenBytes.getText( ).trim( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkLoadTriggersFromServicesFile, "f", m_txtLoadTriggersFromServicesFile.getText( ).trim( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkSpecifyTriggers, "t", m_txtSpecifyTriggers.getText( ).trim( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkLoadFromSaveFile, "r", m_txtLoadFromSaveFile.getText( ).trim( ), astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentIfChecked(m_chkWriteOutToFile, "w", m_txtWriteOutToFile.getText( ).trim( ), astrCommand);
    		
    		if ( m_txtExpression.getText( ).equals( "" ) == false )
    			astrCommand = CAircrackUtilities.AddArgumentToArray(m_txtExpression.getText( ).trim( ), astrCommand);
    		
    		DProgramOutput dlgOutput = new DProgramOutput("Sniff Passwords - Output", astrCommand);
    		dlgOutput.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
}