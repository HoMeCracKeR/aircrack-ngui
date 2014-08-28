// --------------------------------------------------------------------------------
// Name: FNetworkInterfaces
// Abstract: Allows you to view and change your system's network interfaces
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;          // AWT = Abstract Windows Toolkit
import java.awt.event.*;    // Events for AWT
import javax.swing.*;       // Newer version of graphics

public class FNetworkInterfaces extends CAircrackWindow implements MouseListener
{
	protected final static long serialVersionUID = 1L;
	
    private CListBox m_lstNetworkInterfaces = null;
    private JButton m_btnCreateMonitorInterface  = null;
    private JButton m_btnDestroyMonitorInterface = null;
    private String m_astrNetworkDevices[] = null;
    private CDropdownButton m_ddbCopyToClipboard = null;
    private JButton m_btnSaveToConfigFile = null;
    
    // Display - ifconfig
    private JLabel m_lblNetworkInterfaceName = null;
    private JLabel m_lblHardwareAddress = null;
    private JLabel m_lblIPAddress = null;
    private JLabel m_lblBroadcastAddress = null;
    private JLabel m_lblSubnetMask = null;
    private JLabel m_lblIPAddress6 = null;
    
    // Display - iwconfig
    private JLabel m_lblNoWirelessExtensions = null;
    private JLabel m_lblIEEEMode = null;
    private JLabel m_lblESSID = null;
    private JLabel m_lblMode = null;
    private JLabel m_lblFrequency = null;
    private JLabel m_lblAccessPointMAC = null;
    
    // --------------------------------------------------------------------------------
    // Name: FNetworkInterfaces
    // Abstract: Class constructor
    // --------------------------------------------------------------------------------
    public FNetworkInterfaces( )
    {
    	super("Network Interfaces", 600, 485, false, false, "");
        try
        {
            AddControls();
            PopulateNetworkInterfaces();
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
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
            
        	boolean blnAirmonNGInstalled = CGlobals.clsLocalMachine.ProgramInstalled("airmon-ng");
        	
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            m_lstNetworkInterfaces  = CUtilities.AddListBox(conControlArea, this, 30, 10, 400, 180);
            CUtilities.AddLabel(conControlArea, "Network Interfaces:", 10, 10);
            m_btnCreateMonitorInterface  = CUtilities.AddButton(conControlArea, this, "Create Monitor Interface", 380, 200, 25, 230);
            m_btnDestroyMonitorInterface = CUtilities.AddButton(conControlArea, this, "Destroy Monitor Interface", 410, 200, 25, 230);
            m_ddbCopyToClipboard = CUtilities.AddDropdownButton(conControlArea, this, this, "Copy To Clipboard", 350, 200, 25, 200);
            m_btnSaveToConfigFile = CUtilities.AddButton(conControlArea, this, "Save To Config File", 350, 430, 18, 150);

            m_lblNetworkInterfaceName = CUtilities.AddLabel(conControlArea, "No interface selected", 30, 200);
            m_lblHardwareAddress = CUtilities.AddLabel(conControlArea, "HW Address:", 60, 200);
            m_lblIPAddress = CUtilities.AddLabel(conControlArea, "IP Address:", 80, 200);
            m_lblBroadcastAddress = CUtilities.AddLabel(conControlArea, "Broadcast Address:", 100, 200);
            m_lblSubnetMask = CUtilities.AddLabel(conControlArea, "Subnet Mask:", 120, 200);
            m_lblIPAddress6 = CUtilities.AddLabel(conControlArea, "IPv6 Address:", 140, 200);
    
            m_lblNoWirelessExtensions = CUtilities.AddLabel(conControlArea, "No wireless extensions", 160, 200);
            m_lblIEEEMode = CUtilities.AddLabel(conControlArea, "IEEE Mode:", 180, 200);
            m_lblESSID = CUtilities.AddLabel(conControlArea, "ESSID:", 200, 200);
            m_lblMode = CUtilities.AddLabel(conControlArea, "Mode:", 220, 200);
            m_lblFrequency = CUtilities.AddLabel(conControlArea, "Frequency:", 240, 200);
            m_lblAccessPointMAC = CUtilities.AddLabel(conControlArea, "Access Point MAC:", 260, 200);
            
            Font fntCurrentFont = m_lblNetworkInterfaceName.getFont( );
            fntCurrentFont = new Font(fntCurrentFont.getFamily(), Font.BOLD, fntCurrentFont.getSize() + 10);
            m_lblNetworkInterfaceName.setFont( fntCurrentFont );
            m_lblNoWirelessExtensions.setForeground( Color.RED );

            m_lblNetworkInterfaceName.addMouseListener( this );
            m_lblHardwareAddress.addMouseListener( this );
            m_lblIPAddress.addMouseListener( this );
            m_lblBroadcastAddress.addMouseListener( this );
            m_lblSubnetMask.addMouseListener( this );
            m_lblESSID.addMouseListener( this );
            m_lblFrequency.addMouseListener( this );
            m_lblAccessPointMAC.addMouseListener( this );
            m_lblMode.addMouseListener( this );
            
            m_ddbCopyToClipboard.SetMenuOptions(new String[] {"Hardware Address", "IP Address", "IPv6 Address", "ESSID", "Frequency", "Channel", "Access Point MAC"});
            
            m_btnCreateMonitorInterface.setEnabled( blnAirmonNGInstalled );
            m_btnDestroyMonitorInterface.setEnabled( blnAirmonNGInstalled );
            
            ResetDataFields( );
            
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateNetworkInterfaces
    // Abstract: Populates the available network devices (up or down)
    // ----------------------------------------------------------------------------
    public void PopulateNetworkInterfaces( )
    {
        try
        {
        	
        	String strPreferredInterface = CUserPreferences.GetPreferredStandardInterface( );
        	m_lstNetworkInterfaces.Clear( );
        	boolean blnSelected = false;
        	boolean blnPreferredInterfaceSelected = false;
        	
        	m_astrNetworkDevices = CGlobals.clsLocalMachine.GetAllNetworkDevices( );

            for (int intIndex = 0; intIndex < m_astrNetworkDevices.length; intIndex += 1)
            {
            	blnSelected = false;
            	if ( m_astrNetworkDevices[intIndex].equals( strPreferredInterface ) )
            	{
            		blnSelected = true;
            		blnPreferredInterfaceSelected = true;
            	}
                m_lstNetworkInterfaces.AddItemToList(m_astrNetworkDevices[intIndex], intIndex, blnSelected);
            }
            
            if ( blnPreferredInterfaceSelected ) PopulateNetworkDeviceInformation( );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }

    // ----------------------------------------------------------------------------
    // Name: actionPerformed
    // Abstract: Button presses and drop-down button option selections
    // ----------------------------------------------------------------------------
    @Override
    public void actionPerformed( ActionEvent aeSource )
    {
        if (aeSource.getSource() == m_btnCreateMonitorInterface)
            btnCreateMonitorInterface_Click();
        else if (aeSource.getSource() == m_btnDestroyMonitorInterface)
        	btnDestroyMonitorInterface_Click();
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("Hardware Address"))
        	CopyHardwareAddressToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("IP Address"))
        	CopyIPAddressToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("IPv6 Address"))
        	CopyIPv6AddressToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("ESSID"))
        	CopyESSIDToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("Frequency"))
        	CopyFrequencyToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("Channel"))
        	CopyChannelToClipboard( );
        else if (aeSource.getSource() == m_ddbCopyToClipboard.GetMenuItem("Access Point MAC"))
        	CopyAccessPointMACToClipboard( );
        else if (aeSource.getSource( ) == m_btnSaveToConfigFile )
        	btnSaveToConfigFile_Click( );
    }
    
    // ----------------------------------------------------------------------------
    // Name: btnCreateMonitorInterface_Click
    // Abstract: Creates a new monitor-mode interface from the selected interface
    // ----------------------------------------------------------------------------
    private void btnCreateMonitorInterface_Click()
    {
        try
        {
            if ( m_lstNetworkInterfaces.GetSelectedIndex() > -1 )
            {
                String strInterface = m_lstNetworkInterfaces.GetSelectedItemName();
                
                // Turn it on
                CNetworkInterface clsInterface = new CNetworkInterface( strInterface );
                clsInterface.EnableMonitorMode();
                
                // Refresh our list of devices
                PopulateNetworkInterfaces( );
                
            }
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }
    
    // ----------------------------------------------------------------------------
    // Name: btnDestroyMonitorInterface_Click
    // Abstract: Destroys an existing monitor-mode interface that's currently selected
    // ----------------------------------------------------------------------------
    private void btnDestroyMonitorInterface_Click( )
    {
    	try
    	{
    		if ( m_lstNetworkInterfaces.GetSelectedIndex() > -1 )
            {
                String strInterface = m_lstNetworkInterfaces.GetSelectedItemName();
                
                // Turn it off
                CNetworkInterface clsInterface = new CNetworkInterface( strInterface );
                clsInterface.DisableMonitorMode();
                
                m_lstNetworkInterfaces.ClearSelection( );
                
                // Refresh our list of devices
                PopulateNetworkInterfaces( );
                
            }
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: CopyHardwareAddressToClipboard
    // Abstract: Copies the hardware (MAC) address to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyHardwareAddressToClipboard( )
    {
    	try
    	{
    		String strHardwareAddress = m_lblHardwareAddress.getText().replace("HW Address:", "").trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strHardwareAddress);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: CopyIPAddressToClipboard
    // Abstract: Copies the IPv4 address to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyIPAddressToClipboard( )
    {
    	try
    	{
    		String strIPAddress = m_lblIPAddress.getText().replace("IP Address:", "").trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strIPAddress);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: CopyIPv6AddressToClipboard
    // Abstract: Copies the Ipv6 address to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyIPv6AddressToClipboard( )
    {
    	try
    	{
    		String strIPv6Address = m_lblIPAddress6.getText().replace("IPv6 Address:", "").trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strIPv6Address);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: CopyESSIDToClipboard
    // Abstract: Copies the ESSID (network name) to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyESSIDToClipboard( )
    {
    	try
    	{
    		String strESSID = m_lblESSID.getText().replace("ESSID:", "").trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strESSID);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: CopyFrequencyToClipboard
    // Abstract: Copies the frequency to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyFrequencyToClipboard( )
    {
    	try
    	{
    		String strFrequency = m_lblFrequency.getText().replace("Frequency:", "").trim();
    		strFrequency = strFrequency.substring(0, strFrequency.indexOf("(channel")).trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strFrequency);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: CopyChannelToClipboard
    // Abstract: Copies the channel to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyChannelToClipboard( )
    {
    	try
    	{
    		String strChannel = m_lblFrequency.getText().substring(m_lblFrequency.getText().indexOf("(channel") + 8);
    		strChannel = strChannel.substring(0, strChannel.indexOf(")")).trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strChannel);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
 
    // ----------------------------------------------------------------------------
    // Name: CopyAccessPointMACToClipboard
    // Abstract: Copies the access point MAC address (BSSID) to the clipboard
    // ----------------------------------------------------------------------------
    private void CopyAccessPointMACToClipboard( )
    {
    	try
    	{
    		String strAccessPointMAC = m_lblAccessPointMAC.getText().replace("Access Point MAC:", "").trim();
    		CGlobals.clsLocalMachine.SetClipboardContents(strAccessPointMAC);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: btnSaveToConfigFile_Click
    // Abstract: Saves the network card configuration to a file
    // ----------------------------------------------------------------------------
    private void btnSaveToConfigFile_Click( )
    {
    	try
    	{
    		
    		String strFileOutput = "";
    		strFileOutput += m_lblNetworkInterfaceName.getText( ) + "\r\n";
    		strFileOutput += m_lblHardwareAddress.getText( ) + "\r\n";
    		strFileOutput += m_lblIPAddress.getText( ) + "\r\n";
    		strFileOutput += m_lblBroadcastAddress.getText( ) + "\r\n";
    		strFileOutput += m_lblSubnetMask.getText( ) + "\r\n";
    		strFileOutput += m_lblIPAddress6.getText( ) + "\r\n";
    		
    		if ( m_lblNoWirelessExtensions.isVisible( ) == false && m_lblNetworkInterfaceName.getText( ).equals("No interface selected") == false )
    		{
    			strFileOutput += m_lblIEEEMode.getText( ) + "\r\n";
    			strFileOutput += m_lblESSID.getText( ) + "\r\n";
    			strFileOutput += m_lblMode.getText( ) + "\r\n";
    			strFileOutput += m_lblFrequency.getText( ) + "\r\n";
    			strFileOutput += m_lblAccessPointMAC.getText( ) + "\r\n";
    		}
    		
    		String strDestinationLocation = CAircrackUtilities.DisplayFileChooser(this, false, ".txt", "Text files (*.txt)");
    		if ( strDestinationLocation.equals("") == false )
    		{
    			if ( CAircrackUtilities.SaveTextToFile(strFileOutput, strDestinationLocation) == true )
    				JOptionPane.showMessageDialog(null, "File successfully saved.");
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: mouseClicked
    // Abstract: Mouse clicks on labels and list boxes
    // ----------------------------------------------------------------------------
    public void mouseClicked( MouseEvent meSource )
    {
    	try
    	{
    		if ( meSource.getSource() == m_lstNetworkInterfaces )
    			PopulateNetworkDeviceInformation( );
    		
    		else if ( meSource.getSource( ) == m_lblNetworkInterfaceName )
    			DisplayToggleNetworkInterfaceDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblHardwareAddress )
    			DisplayChangeMACAddressDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblIPAddress )
    			DisplayChangeIPAddressDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblBroadcastAddress )
    			DisplayChangeBroadcastAddressDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblSubnetMask )
    			DisplayChangeSubnetMaskDialog( );

    		else if ( meSource.getSource( ) == m_lblESSID )
    			DisplayChangeESSIDDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblFrequency )
    			DisplayChangeChannelDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblAccessPointMAC )
    			DisplayChangeAccessPointMACDialog( );
    		
    		else if ( meSource.getSource( ) == m_lblMode )
    			DisplayChangeWirelessModeDialog( );
    		
    		else if ( meSource.getSource( ) == m_ddbCopyToClipboard )
    			ShowCopyToClipboardPopup( meSource );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: PopulateNetworkDeviceInformation
    // Abstract: Populates the network device information labels with the network
    //			 interface information
    // ----------------------------------------------------------------------------
    private void PopulateNetworkDeviceInformation( )
    {
    	try
    	{
    		ResetDataFields( );
    		
    		String strNetworkInterfaceName = m_lstNetworkInterfaces.GetSelectedItemName( );
    		CNetworkInterface clsCurrentInterface = new CNetworkInterface( strNetworkInterfaceName );
    		
    		m_lblNetworkInterfaceName.setText( strNetworkInterfaceName + " (" + clsCurrentInterface.GetInterfaceAvailability() + ")" );
    		m_lblHardwareAddress.setText("HW Address: " + clsCurrentInterface.GetMACAddress());
    		m_lblIPAddress.setText("IP Address: " + clsCurrentInterface.GetIPAddress());
    		m_lblBroadcastAddress.setText("Broadcast Address: " + clsCurrentInterface.GetBroadcastAddress());
    		m_lblSubnetMask.setText("Subnet Mask: " + clsCurrentInterface.GetSubnetMask());
    		m_lblIPAddress6.setText("IPv6 Address: " + clsCurrentInterface.GetIPv6Address());
    		
    		if (clsCurrentInterface.HasWirelessCapability())
    		{
    			ShowWirelessFields();
    			
    			m_lblIEEEMode.setText("IEEE Mode: " + clsCurrentInterface.GetIEEEMode());
    			m_lblESSID.setText("ESSID: " + clsCurrentInterface.GetESSID());
    			m_lblMode.setText("Mode: " + clsCurrentInterface.GetMode());
    			m_lblFrequency.setText("Frequency: " + clsCurrentInterface.GetFrequency() + " (channel " + clsCurrentInterface.GetChannel() + ")");
    			m_lblAccessPointMAC.setText("Access Point MAC: " + clsCurrentInterface.GetBSSID());
    		}
    		else
    		{
    			m_lblNoWirelessExtensions.setVisible( true );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: ResetDataFields
    // Abstract: Resets the data fields back to the default information
    // ----------------------------------------------------------------------------
    private void ResetDataFields( )
    {
    	try
    	{
            m_lblNetworkInterfaceName.setText("No interface selected");
            m_lblHardwareAddress.setText("HW Address:");
            m_lblIPAddress.setText("IP Address:");
            m_lblBroadcastAddress.setText("Broadcast Address:");
            m_lblSubnetMask.setText("Subnet Mask:");
            m_lblIPAddress6.setText("IPv6 Address:");
            
            m_lblNoWirelessExtensions.setVisible( false );
            m_lblIEEEMode.setVisible( false );
            m_lblIEEEMode.setText("IEEE Mode:");
            m_lblESSID.setVisible( false );
            m_lblESSID.setText("ESSID:");
            m_lblMode.setVisible( false );
            m_lblMode.setText("Mode:");
            m_lblFrequency.setVisible( false );
            m_lblFrequency.setText("Frequency:");
            m_lblAccessPointMAC.setVisible( false );
            m_lblAccessPointMAC.setText("Access Point MAC:");

    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: ShowWirelessFields
    // Abstract: Shows the wireless information fields
    // ----------------------------------------------------------------------------
    private void ShowWirelessFields( )
    {
    	try
    	{
    		m_lblIEEEMode.setVisible( true );
            m_lblESSID.setVisible( true );
            m_lblMode.setVisible( true );
            m_lblFrequency.setVisible( true );
            m_lblAccessPointMAC.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayToggleNetworkInterfaceDialog
    // Abstract: Shows the toggle network interface dialog. Allows you to turn on
    //			 or turn off an interface.
    // ----------------------------------------------------------------------------
    private void DisplayToggleNetworkInterfaceDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to toggle this interface?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			if ( clsInterface.GetInterfaceAvailability().equals("UP") )
    				clsInterface.TakeDown();
    			else
    				clsInterface.BringUp();
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeMACAddressDialog
    // Abstract: Shows the change MAC address dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeMACAddressDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your MAC address?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewMACAddress = JOptionPane.showInputDialog("Please enter the new MAC address:");
    			
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetMACAddress(strNewMACAddress);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeIPAddressDialog
    // Abstract: Shows the change IP address dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeIPAddressDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your IP address?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewIPAddress = JOptionPane.showInputDialog("Please enter the new IP address:");
    			
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetIPAddress(strNewIPAddress);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: DisplayChangeBroadcastAddressDialog
    // Abstract: Shows the change broadcast address dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeBroadcastAddressDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your broadcast address?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewBroadcastAddress = JOptionPane.showInputDialog("Please enter the new broadcast address:");
    			
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetBroadcastAddress(strNewBroadcastAddress);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeSubnetMaskDialog
    // Abstract: Shows the change subnet mask address dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeSubnetMaskDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your subnet mask?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewSubnetMask = JOptionPane.showInputDialog("Please enter the new subnet mask:");
    			
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetSubnetMask(strNewSubnetMask);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeESSIDDialog
    // Abstract: Shows the change ESSID dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeESSIDDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your ESSID?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewESSID = JOptionPane.showInputDialog("Please enter the new ESSID:");
    			CNetworkInterface clsInterface = new CNetworkInterface( m_lstNetworkInterfaces.GetSelectedItemName( ) );
    			clsInterface.SetESSID(strNewESSID);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: DisplayChangeFrequencyOrChannelDialog
    // Abstract: Shows the change frequency or channel dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeChannelDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your channel?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewChannel = JOptionPane.showInputDialog("Please enter the new channel:");
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetChannel(strNewChannel);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeAccessPointMACDialog
    // Abstract: Shows the change access point MAC dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeAccessPointMACDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your access point BSSID?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{
    			String strNewAccessPointBSSID = JOptionPane.showInputDialog("Please enter the new BSSID:");
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetBSSID(strNewAccessPointBSSID);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: DisplayChangeWirelessModeDialog
    // Abstract: Shows the change wireless mode dialog.
    // ----------------------------------------------------------------------------
    private void DisplayChangeWirelessModeDialog( )
    {
    	try
    	{
    		int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your wireless mode?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION);
    		if ( intResult == 0 )
    		{

    			String strNewWirelessMode = JOptionPane.showInputDialog("Please enter the new mode (Ad-Hoc, Managed, Master, Repeater, Secondary, Monitor):");
    			
    			CNetworkInterface clsInterface = new CNetworkInterface(m_lstNetworkInterfaces.GetSelectedItemName());
    			clsInterface.SetMode(strNewWirelessMode);
    			
    			// Re-populate info
    			PopulateNetworkDeviceInformation( );
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: ShowCopyToClipboardPopup
    // Abstract: Shows the copy-to-clipboard popup
    // ----------------------------------------------------------------------------
    private void ShowCopyToClipboardPopup(MouseEvent meSource)
    {
    	try
    	{
    		m_ddbCopyToClipboard.getPopupMenu().show( meSource.getComponent( ), meSource.getX( ), meSource.getY( ) );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // Not used
    public void mouseEntered( MouseEvent arg0 ) {}
    public void mouseExited( MouseEvent arg0 ) {}
    public void mousePressed( MouseEvent arg0 ) {}
    public void mouseReleased( MouseEvent arg0 ) {}    
}
