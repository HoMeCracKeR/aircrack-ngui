// --------------------------------------------------------------------------------
// Name: FReplayInjectPackets
// Abstract: Replays or injects packets to a network
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FReplayInjectPackets extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
    private CComboBox m_cboAttackMethod = null;
    private CComboBox m_cboMonitorModeInterface = null;
    private JButton m_btnStartAttack = null;

    // Deauthentication Count
    private JLabel m_lblDeauthenticationCount = null;
    private CTextBox m_txtDeauthenticationCount = null;
    
    // Fake Authentication Delay
    private JLabel m_lblFakeAuthenticationDelay = null;
    private CTextBox m_txtFakeAuthenticationDelay = null;

    // Packets Per Second
    private JCheckBox m_chkPacketsPerSecond = null;
    private CTextBox m_txtPacketsPerSecond = null;
    
    // Frame Control Word (hex)
    private JCheckBox m_chkFrameControlWord = null;
    private CTextBox m_txtFrameControlWord = null;

    // Access Point MAC address
    private JCheckBox m_chkAccessPointMAC = null;
    private CTextBox m_txtAccessPointMAC = null;
    private JButton m_btnAccessPointMAC = null;

    // Destination MAC
    private JCheckBox m_chkDestinationMAC = null;
    private CTextBox m_txtDestinationMAC = null;
    private JButton m_btnDestinationMAC = null;
    
    // Source MAC
    private JCheckBox m_chkSourceMAC = null;
    private CTextBox m_txtSourceMAC = null;
    private JButton m_btnSourceMAC = null;
    
    // Specify ESSID
    private JCheckBox m_chkSpecifyESSID = null;
    private CTextBox m_txtSpecifyESSID = null;
    private JButton m_btnSpecifyESSID = null;
    
    // Inject From DS
    private JCheckBox m_chkInjectFromDS = null;
    
    // Change Ring Buffer Size
    private JCheckBox m_chkChangeRingBufferSize = null;
    private CTextBox m_txtChangeRingBufferSize = null;
    
    // Set Destination IP in Fragments
    private JCheckBox m_chkSetDestinationIPFragments = null;
    private CTextBox m_txtSetDestinationIPFragments = null;
    private JButton m_btnSetDestinationIPFragments = null;

    // Set Source IP in Fragments
    private JCheckBox m_chkSetSourceIPFragments = null;
    private CTextBox m_txtSetSourceIPFragments = null;
    private JButton m_btnSetSourceIPFragments = null;
    
    // Packets Per Burst
    private JCheckBox m_chkPacketsPerBurst = null;
    private CTextBox m_txtPacketsPerBurst = null;
    
    // Keep Alive Delay
    private JCheckBox m_chkKeepAliveDelay = null;
    private CTextBox m_txtKeepAliveDelay = null;
    
    // Keystream for shared key authentication
    private JCheckBox m_chkKeystreamForSharedKeyAuthentication = null;
    private CTextBox m_txtKeyStreamForSharedKeyAuthentication = null;
    
    // Bit Rate Test
    private JCheckBox m_chkBitRateTest = null;
    
    // Disable AP protection
    private JCheckBox m_chkDisableAPDetection = null;
    
    // Fast matching packet
    private JCheckBox m_chkFastMatchingPacket = null;
    
    // Disable /dev/rtc usage
    private JCheckBox m_chkDisableDevRTCUsage = null;
    
    private String m_strPassedInDestinationMAC = "";
    private String m_strPassedInNetworkBSSID = "";
    private String m_strPassedInNetworkESSID = "";
    private String m_strPassedInAttackMethod = "";
    private String m_strPassedInDestinationIP = "";
    
	// --------------------------------------------------------------------------------
	// Name: FReplayInjectPackets
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
    public FReplayInjectPackets()
    {
    	super("Replay/Inject Packets", 480, 500, false, false, "ReplayInjectPackets");
        try
        {
            AddControls( );
            PopulateAttackMethods( );
            PopulateMonitorModeInterfaces( );
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
            
            CUtilities.AddLabel( m_cntControlContainer, "Attack Method:", 60, 10 );
            m_cboAttackMethod = CUtilities.AddComboBox( m_cntControlContainer, null, 58, 135, 18, 300 );
            CUtilities.AddLabel( m_cntControlContainer, "Interface:", 80, 10 );
            m_cboMonitorModeInterface = CUtilities.AddComboBox( m_cntControlContainer, null, 78, 135, 18, 100 );
            
            m_lblDeauthenticationCount = CUtilities.AddLabel( m_cntControlContainer, "De-auth Count:", 100, 10 );
            m_txtDeauthenticationCount = CUtilities.AddTextBox( m_cntControlContainer, null, "1", 11, 15, 98, 135 );
            m_lblFakeAuthenticationDelay = CUtilities.AddLabel( m_cntControlContainer, "Fake Auth Delay:", 100, 10 );
            m_txtFakeAuthenticationDelay = CUtilities.AddTextBox( m_cntControlContainer, null, "0", 11, 15, 98, 135 );

            m_chkBitRateTest = CUtilities.AddCheckBox( m_cntControlContainer, this, "Bit Rate Test", 120, 10 );
            m_chkDisableAPDetection = CUtilities.AddCheckBox( m_cntControlContainer, this, "Disables AP Detection", 120, 260 );
            
            m_chkFastMatchingPacket = CUtilities.AddCheckBox( m_cntControlContainer, this, "Choose first matching packet", 140, 10 );
            m_chkDisableDevRTCUsage = CUtilities.AddCheckBox( m_cntControlContainer, this, "Disable /dev/rtc usage", 140, 260 );
            
            m_chkInjectFromDS = CUtilities.AddCheckBox( m_cntControlContainer, this, "Inject FromDS packets", 160, 10 );

            m_chkPacketsPerSecond = CUtilities.AddCheckBox( m_cntControlContainer, this, "Packets per Second:", 190, 10 );
            m_txtPacketsPerSecond = CUtilities.AddTextBox( m_cntControlContainer, null, "1", 11, 15, 192, 190 );
            m_chkFrameControlWord = CUtilities.AddCheckBox( m_cntControlContainer, this, "Frame Control Word:", 210, 10 );
            m_txtFrameControlWord = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 15, 212, 190 );
            
            m_chkAccessPointMAC = CUtilities.AddCheckBox( m_cntControlContainer, this, "Access Point MAC:", 230, 10 );
            m_txtAccessPointMAC = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 17, 232, 190 );
            m_btnAccessPointMAC = CUtilities.AddButton(m_cntControlContainer, this, "...", 232, 315, 18, 50);
            m_chkDestinationMAC = CUtilities.AddCheckBox( m_cntControlContainer, this, "Destination MAC:", 250, 10 );
            m_txtDestinationMAC = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 17, 252, 190 );
            m_btnDestinationMAC = CUtilities.AddButton(m_cntControlContainer, this, "...", 252, 315, 18, 50);
            
            m_chkSourceMAC = CUtilities.AddCheckBox( m_cntControlContainer, this, "Source MAC:", 270, 10 );
            m_txtSourceMAC = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 17, 272, 190 );
            m_btnSourceMAC = CUtilities.AddButton(m_cntControlContainer, this, "...", 272, 315, 18, 50);
            m_chkSpecifyESSID = CUtilities.AddCheckBox( m_cntControlContainer, this, "Specify ESSID:", 290, 10 );
            m_txtSpecifyESSID = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 100, 292, 190 );
            m_btnSpecifyESSID = CUtilities.AddButton(m_cntControlContainer, this, "...", 292, 315, 18, 50);
            
            m_chkPacketsPerBurst = CUtilities.AddCheckBox( m_cntControlContainer, this, "Packets Per Burst:", 310, 10 );
            m_txtPacketsPerBurst = CUtilities.AddTextBox( m_cntControlContainer, null, "-1", 11, 15, 312, 190 );
            m_chkKeepAliveDelay = CUtilities.AddCheckBox( m_cntControlContainer, this, "Keep Alive Delay:", 330, 10 );
            m_txtKeepAliveDelay = CUtilities.AddTextBox( m_cntControlContainer, null, "-1", 11, 15, 332, 190 );
            
            m_chkChangeRingBufferSize = CUtilities.AddCheckBox( m_cntControlContainer, this, "Ring Buffer Size:", 350, 10 );
            m_txtChangeRingBufferSize = CUtilities.AddTextBox( m_cntControlContainer, null, "8", 11, 15, 352, 190 );
            m_chkSetDestinationIPFragments = CUtilities.AddCheckBox( m_cntControlContainer, this, "Destination IP:", 370, 10 );
            m_txtSetDestinationIPFragments = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 15, 372, 190 );
            m_btnSetDestinationIPFragments = CUtilities.AddButton(m_cntControlContainer, this, "...", 372, 315, 18, 50);
            
            m_chkSetSourceIPFragments = CUtilities.AddCheckBox( m_cntControlContainer, this, "Source IP:", 390, 10 );
            m_txtSetSourceIPFragments = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 15, 392, 190 );
            m_btnSetSourceIPFragments = CUtilities.AddButton(m_cntControlContainer, this, "...", 392, 315, 18, 50);
            
            m_chkKeystreamForSharedKeyAuthentication = CUtilities.AddCheckBox( m_cntControlContainer, this, "Shared Key:", 410, 10 );
            m_txtKeyStreamForSharedKeyAuthentication = CUtilities.AddTextBox( m_cntControlContainer, null, "", 11, 15, 412, 190 );
            
            m_btnStartAttack = CUtilities.AddButton( m_cntControlContainer, this, "Start Attack", 435, 160, 25, 150 );
            
            // The dropdown defaults to deauthentication. Hide the fake authentication fields.
            m_lblFakeAuthenticationDelay.setVisible( false );
            m_txtFakeAuthenticationDelay.setVisible( false );
            
            // Set this form as the listener for the attack method and the saved profiles
            m_cboAttackMethod.SetActionListener( this );
            m_cboSavedProfiles.SetActionListener( this );

            m_lstParameters.add(new CProfileParameter("AttackMethod", m_cboAttackMethod));
            m_lstParameters.add(new CProfileParameter("Interface", m_cboMonitorModeInterface));
            m_lstParameters.add(new CProfileParameter("DeauthenticationCount", m_txtDeauthenticationCount));
            m_lstParameters.add(new CProfileParameter("FakeAuthenticationDelay", m_txtFakeAuthenticationDelay));
            m_lstParameters.add(new CProfileParameter("BitRateTest", m_chkBitRateTest));
            m_lstParameters.add(new CProfileParameter("DisableAPDetection", m_chkDisableAPDetection));
            m_lstParameters.add(new CProfileParameter("FastMatchingPacket", m_chkFastMatchingPacket));
            m_lstParameters.add(new CProfileParameter("DisableDevRTCUsage", m_chkDisableDevRTCUsage));
            m_lstParameters.add(new CProfileParameter("InjectFromDS", m_chkInjectFromDS));
            m_lstParameters.add(new CProfileParameter("PacketsPerSecond", m_chkPacketsPerSecond, m_txtPacketsPerSecond));
            m_lstParameters.add(new CProfileParameter("FrameControlWord", m_chkFrameControlWord, m_txtFrameControlWord));
            m_lstParameters.add(new CProfileParameter("AccessPointMAC", m_chkAccessPointMAC, m_txtAccessPointMAC));
            m_lstParameters.add(new CProfileParameter("DestinationMAC", m_chkDestinationMAC, m_txtDestinationMAC));
            m_lstParameters.add(new CProfileParameter("SourceMAC", m_chkSourceMAC, m_txtSourceMAC));
            m_lstParameters.add(new CProfileParameter("ESSID", m_chkSpecifyESSID, m_txtSpecifyESSID));
            m_lstParameters.add(new CProfileParameter("PacketsPerBurst", m_chkPacketsPerBurst, m_txtPacketsPerBurst));
            m_lstParameters.add(new CProfileParameter("KeepAliveDelay", m_chkKeepAliveDelay, m_txtKeepAliveDelay));
            m_lstParameters.add(new CProfileParameter("ChangeRingBufferSize", m_chkChangeRingBufferSize, m_txtChangeRingBufferSize));
            m_lstParameters.add(new CProfileParameter("DestinationIPFragments", m_chkSetDestinationIPFragments, m_txtSetDestinationIPFragments));
            m_lstParameters.add(new CProfileParameter("SourceIPFragments", m_chkSetSourceIPFragments, m_txtSetSourceIPFragments));
            m_lstParameters.add(new CProfileParameter("KeystreamForSharedKeyAuthentication", m_chkKeystreamForSharedKeyAuthentication, m_txtKeyStreamForSharedKeyAuthentication));
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        
    }

	// --------------------------------------------------------------------------------
	// Name: PopulateAttackMethods
	// Abstract: Populates the different attack methods to use for replay/inject
    //			 packets.
	// --------------------------------------------------------------------------------
    private void PopulateAttackMethods( )
    {
        
        try
        {
            
            m_cboAttackMethod.SetSorted( false );
            m_cboAttackMethod.AddItemToList("Deauthentication", 0);
            m_cboAttackMethod.AddItemToList("Fake authentication", 1);
            m_cboAttackMethod.AddItemToList("Interactive packet replay", 2);
            m_cboAttackMethod.AddItemToList("ARP request replay attack", 3);
            m_cboAttackMethod.AddItemToList("KoreK chopchop attack", 4);
            m_cboAttackMethod.AddItemToList("Fragmentation attack", 5);
            m_cboAttackMethod.AddItemToList("Cafe-latte attack", 6);
            m_cboAttackMethod.AddItemToList("Client-oriented fragmentation attack", 7);
            m_cboAttackMethod.AddItemToList("WPA Migration Mode", 8);
            m_cboAttackMethod.AddItemToList("Injection test", 9);
            m_cboAttackMethod.SetSelectedIndex( 0 );

        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        
    }

	// --------------------------------------------------------------------------------
	// Name: PopulateMonitorModeInterfaces
	// Abstract: Populates the different interfaces in monitor mode to do the attack
    //			 on.
	// --------------------------------------------------------------------------------
    private void PopulateMonitorModeInterfaces( )
    {
        
        try
        {
            CNetworkInterface aclsMonitorableInterfaces[] = CGlobals.clsLocalMachine.GetAllMonitorableInterfaces();
            for ( int intIndex = 0; intIndex < aclsMonitorableInterfaces.length; intIndex += 1 )
            	m_cboMonitorModeInterface.AddItemToList(aclsMonitorableInterfaces[intIndex].GetInterface(), 0);
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        
    }

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks and dropdown changes
	// --------------------------------------------------------------------------------
    @Override
    public void actionPerformed( ActionEvent aeSource )
    {
    	super.actionPerformed( aeSource );
        try
        {
        	if ( m_blnLoading == false )
			{
	            if ( aeSource.getSource() == m_cboAttackMethod.GetJavaComboBox( ) )			cboAttackMethod_SelectedIndexChanged( );
	            else if ( aeSource.getSource( ) == m_btnStartAttack )						btnStartAttack_Click( );
	            else if ( aeSource.getSource( ) == m_btnAccessPointMAC )					btnAccessPointMAC_Click( );
	            else if ( aeSource.getSource( ) == m_btnSpecifyESSID )						btnSpecifyESSID_Click( );
	            else if ( aeSource.getSource( ) == m_btnDestinationMAC )					btnDestinationMAC_Click( );
	            else if ( aeSource.getSource( ) == m_btnSourceMAC )							btnSourceMAC_Click( );
	            else if ( aeSource.getSource( ) == m_btnSetSourceIPFragments )				btnSetSourceIPFragments_Click( );
	            else if ( aeSource.getSource( ) == m_btnSetDestinationIPFragments )			btnSetDestinationIPFragments_Click( );
			}
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: cboAttackMethod_SelectedIndexChanged
	// Abstract: Hides or shows the fake authentication delay and de-authentication
    //			 packet count depending on the attack method selected.
	// --------------------------------------------------------------------------------
    private void cboAttackMethod_SelectedIndexChanged( )
    {
    	try
    	{
    		CNameValuePair clsSelectedItem = m_cboAttackMethod.GetSelectedItem( );
            
            m_lblFakeAuthenticationDelay.setVisible( false );
            m_txtFakeAuthenticationDelay.setVisible( false );
            m_lblDeauthenticationCount.setVisible( false );
            m_txtDeauthenticationCount.setVisible( false );
            
            if ( clsSelectedItem.GetName( ).equals( "Deauthentication" ) )
            {
                m_lblDeauthenticationCount.setVisible( true );
                m_txtDeauthenticationCount.setVisible( true );
            }
            else if ( clsSelectedItem.GetName( ).equals( "Fake authentication" ) )
            {
                m_lblFakeAuthenticationDelay.setVisible( true );
                m_txtFakeAuthenticationDelay.setVisible( true );
            }
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnStartAttack_Click
	// Abstract: Starts the attack
	// --------------------------------------------------------------------------------
    private void btnStartAttack_Click( )
    {
        
        try
        {
        	DProgramOutput dlgResults = new DProgramOutput( "Replay/Inject Packets - Output", BuildAttackArguments( ), true );
            dlgResults.setVisible( true );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
            JOptionPane.showMessageDialog( null, excError.getMessage( ) ); 
        }
        
    }

	// --------------------------------------------------------------------------------
	// Name: BuildAttackArguments
	// Abstract: Builds the attack arguments to send to the results page
	// --------------------------------------------------------------------------------
    private String[] BuildAttackArguments( )
    {
        String astrArguments[] = {"aireplay-ng"};
        try
        {
            CNameValuePair clsSelectedAttackMethod = m_cboAttackMethod.GetSelectedItem( );
            int intAttackMethodArgument = clsSelectedAttackMethod.GetValue( );
            
            astrArguments = AddAttackArgumentsToArray(astrArguments, intAttackMethodArgument);
            
            astrArguments = CAircrackUtilities.AddArgumentToArray(m_cboMonitorModeInterface.GetSelectedItemName(), astrArguments);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        return astrArguments;
    }
    
	// --------------------------------------------------------------------------------
	// Name: AddAttackArgumentsToArray
	// Abstract: Adds the attack arguments to the program array
	// --------------------------------------------------------------------------------
    private String[] AddAttackArgumentsToArray(String astrArguments[], int intAttackMethod)
    {
        String astrNewArguments[] = astrArguments;
        try
        {
            
            // Argument for the attack method
            if ( intAttackMethod == 0 )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("0", m_txtDeauthenticationCount.getText( ), astrNewArguments);
            else if ( intAttackMethod == 1 )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("1", m_txtFakeAuthenticationDelay.getText( ), astrNewArguments);            
            else
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand(String.valueOf( intAttackMethod ), "", astrNewArguments);
            
            // Packets Per Second
            if ( m_chkPacketsPerSecond.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("x", m_txtPacketsPerSecond.getText( ), astrNewArguments);
            
            // Frame Control Word (hex)
            if ( m_chkFrameControlWord.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("p", m_txtFrameControlWord.getText( ), astrNewArguments);
            
            // Access Point MAC address
            if ( m_chkAccessPointMAC.isSelected( ) )
            {
            	astrNewArguments = CAircrackUtilities.AddArgumentToCommand("a", m_txtAccessPointMAC.getText( ), astrNewArguments);
            	astrNewArguments = CAircrackUtilities.AddArgumentToCommand("b", m_txtAccessPointMAC.getText( ), astrNewArguments);
            }

            // Destination MAC
            if ( m_chkDestinationMAC.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("c", m_txtDestinationMAC.getText( ), astrNewArguments);
            
            // Source MAC
            if ( m_chkSourceMAC.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("h", m_txtSourceMAC.getText( ), astrNewArguments);
            
            // Specify ESSID
            if ( m_chkSpecifyESSID.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("e", m_txtSpecifyESSID.getText( ), astrNewArguments);
            
            // Inject From DS
            if ( m_chkInjectFromDS.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("j", "", astrNewArguments);
            
            // Change Ring Buffer Size
            if ( m_chkChangeRingBufferSize.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("g", m_txtChangeRingBufferSize.getText( ), astrNewArguments);
            
            // Set Destination IP in Fragments
            if ( m_chkSetDestinationIPFragments.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("k", m_txtSetDestinationIPFragments.getText( ), astrNewArguments);

            // Set Source IP in Fragments
            if ( m_chkSetSourceIPFragments.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("l", m_txtSetSourceIPFragments.getText( ), astrNewArguments);

            // Packets Per Burst
            if ( m_chkPacketsPerBurst.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("o", m_txtPacketsPerBurst.getText( ), astrNewArguments);
            
            // Keep Alive Delay
            if ( m_chkKeepAliveDelay.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("q", m_txtKeepAliveDelay.getText( ), astrNewArguments);
            
            // Keystream for shared key authentication
            if ( m_chkKeystreamForSharedKeyAuthentication.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("y", m_txtKeyStreamForSharedKeyAuthentication.getText( ), astrNewArguments);
            
            // Bit Rate Test
            if ( m_chkBitRateTest.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("B", "", astrNewArguments);
            
            // Disable AP protection
            if ( m_chkDisableAPDetection.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("D", "", astrNewArguments);
            
            // Fast matching packet
            if ( m_chkFastMatchingPacket.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("F", "", astrNewArguments);
            
            // Disable /dev/rtc usage
            if ( m_chkFastMatchingPacket.isSelected( ) )
                astrNewArguments = CAircrackUtilities.AddArgumentToCommand("R", "", astrNewArguments);
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        return astrNewArguments;
    }

	// --------------------------------------------------------------------------------
	// Name: btnAccessPointMAC_Click
	// Abstract: Access Point MAC address selection dialog
	// --------------------------------------------------------------------------------
    private void btnAccessPointMAC_Click( )
    {
    	try
    	{
    		DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
    		dlgSelectNetwork.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID);
    		dlgSelectNetwork.setVisible( true );
    		
    		if ( dlgSelectNetwork.GetSelectedNetwork( ).equals( "" ) == false )
    		{
    			m_txtAccessPointMAC.setText( dlgSelectNetwork.GetSelectedNetwork( ) );
    			m_chkAccessPointMAC.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnSpecifyESSID_Click
	// Abstract: Specify ESSID selection dialog
	// --------------------------------------------------------------------------------
    private void btnSpecifyESSID_Click( )
    {
    	try
    	{
    		DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
    		dlgSelectNetwork.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_ESSID);
    		dlgSelectNetwork.setVisible( true );
    		
    		if ( dlgSelectNetwork.GetSelectedNetwork( ).equals( "" ) == false )
    		{
    			m_txtSpecifyESSID.setText( dlgSelectNetwork.GetSelectedNetwork( ) );
    			m_chkSpecifyESSID.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnDestinationMAC_Click
	// Abstract: Destination MAC selection dialog
	// --------------------------------------------------------------------------------
    private void btnDestinationMAC_Click( )
    {
    	try
    	{
    		DSelectMACAddress dlgSelectMACAddress = new DSelectMACAddress( );
    		dlgSelectMACAddress.setVisible( true );
    		String strSelectedMACAddress = dlgSelectMACAddress.GetSelectedMACAddress( );
    		if ( strSelectedMACAddress.equals( "" ) == false )
    		{
    			m_txtDestinationMAC.setText( strSelectedMACAddress );
    			m_chkDestinationMAC.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnSourceMAC_Click
	// Abstract: Source MAC selection dialog
	// --------------------------------------------------------------------------------
    private void btnSourceMAC_Click( )
    {
    	try
    	{
    		DSelectMACAddress dlgSelectMACAddress = new DSelectMACAddress( );
    		dlgSelectMACAddress.setVisible( true );
    		String strSelectedMACAddress = dlgSelectMACAddress.GetSelectedMACAddress( );
    		if ( strSelectedMACAddress.equals( "" ) == false )
    		{
    			m_txtSourceMAC.setText( strSelectedMACAddress );
    			m_chkSourceMAC.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnSetSourceIPFragments_Click
	// Abstract: Shows the dialog to choose a source IP
	// --------------------------------------------------------------------------------
    private void btnSetSourceIPFragments_Click( )
    {
    	try
    	{
    		DSelectStation dlgSelectStation = new DSelectStation( );
    		dlgSelectStation.SetReturnType(DSelectStation.udtReturnType.RETURN_TYPE_IP_ADDRESS);
    		dlgSelectStation.setVisible( true );
    		String strStationIP = dlgSelectStation.GetSelectedStation( );
    		if ( strStationIP.equals( "" ) == false )
    		{
    			m_txtSetSourceIPFragments.setText(strStationIP);
    			m_chkSetSourceIPFragments.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: SetDestinationMAC
	// Abstract: Shows the dialog to choose a destination IP
	// --------------------------------------------------------------------------------
    private void btnSetDestinationIPFragments_Click( )
    {
    	try
    	{
    		DSelectStation dlgSelectStation = new DSelectStation( );
    		dlgSelectStation.SetReturnType(DSelectStation.udtReturnType.RETURN_TYPE_IP_ADDRESS);
    		dlgSelectStation.setVisible( true );
    		String strStationIP = dlgSelectStation.GetSelectedStation( );
    		if ( strStationIP.equals( "" ) == false )
    		{
    			m_txtSetDestinationIPFragments.setText(strStationIP);
    			m_chkSetDestinationIPFragments.setSelected( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: SetDestinationMAC
	// Abstract: Allows an outside form to set the destination MAC address. Used by
    //			 Discover Networks and Discover Hosts.
	// --------------------------------------------------------------------------------
    public void SetDestinationMAC(String strTargetBSSID)
    {
    	try
    	{
    		m_strPassedInDestinationMAC = strTargetBSSID;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: SetNetworkBSSID
	// Abstract: Allows an outside form to set the network BSSID. Used by Discover
    //			 Networks and Discover Hosts.
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

	// --------------------------------------------------------------------------------
	// Name: SetNetworkESSID
	// Abstract: Allows an outside form to set the network ESSID. Used by Discover
    //			 Networks and Discover Hosts.
	// --------------------------------------------------------------------------------
    public void SetNetworkESSID(String strNetworkESSID)
    {
    	try
    	{
    		m_strPassedInNetworkESSID = strNetworkESSID;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: SetAttackMethod
	// Abstract: Allows an outside form to set the attack method. Used by Discover
    //			 Networks and Discover Hosts.
	// --------------------------------------------------------------------------------
    public void SetAttackMethod( String strAttackMethod )
    {
    	try
    	{
    		m_strPassedInAttackMethod = strAttackMethod;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: SetDestinationIP
	// Abstract: Allows an outside form to set the destination IP.
	// --------------------------------------------------------------------------------
    public void SetDestinationIP( String strDestinationIP )
    {
    	try
    	{
    		m_strPassedInDestinationIP = strDestinationIP;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: windowOpened
	// Abstract: Called when the window is opened.
	// --------------------------------------------------------------------------------
    @Override
    public void windowOpened( WindowEvent weSource )
    {
    	super.windowOpened( weSource );
    	try
    	{
	    		
	    	if ( m_strPassedInDestinationMAC.equals("") == false )
	    	{
	    		m_txtDestinationMAC.setText( m_strPassedInDestinationMAC );
	    		m_chkDestinationMAC.setSelected( true );
	    	}
	    	if ( m_strPassedInNetworkBSSID.equals("") == false )
	    	{
	    		m_txtAccessPointMAC.setText( m_strPassedInNetworkBSSID );
	    		m_chkAccessPointMAC.setSelected( true );
	    	}
	    	if ( m_strPassedInNetworkESSID.equals("") == false )
	    	{
	    		m_txtSpecifyESSID.setText( m_strPassedInNetworkESSID );
	    		m_chkSpecifyESSID.setSelected( true );
	    	} 
	    	if ( m_strPassedInDestinationIP.equals("") == false )
	    	{
	    		m_txtSetDestinationIPFragments.setText( m_strPassedInDestinationIP );
	    		m_chkSetDestinationIPFragments.setSelected( true );
	    	}
	        CAircrackUtilities.SetComboBoxSelectedValue(m_cboAttackMethod, m_strPassedInAttackMethod);

    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
}
