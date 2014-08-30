// --------------------------------------------------------------------------------
// Name: FMain
// Abstract: Main screen
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;          // AWT = Abstract Windows Toolkit
import java.awt.event.*;    // Events for AWT

import javax.swing.*;       // Newer version of graphics

public class FMain extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
    // Main Menu
    private JMenuBar m_mnbMainMenu = null;
    
    // Setup
    private JMenu m_mnuSetup = null;
    private JMenuItem m_miAddRemoveUtilities = null;
    private JMenuItem m_miNetworkDevices = null;
    private JMenuItem m_miSettings = null;
    private JMenuItem m_miExit = null;
    
    private JMenu m_mnuSuites = null;
    
    // Aircrack-NG
    private JMenu m_mnuAircrackNG = null;
    private JMenuItem m_miManageDrivers = null;  // airdriver
    private JMenuItem m_miWirelessCardServer = null; //airserv
    private JMenuItem m_miVirtualTunnel = null; // airtun
    private JMenuItem m_miCreateWPAHashDictionary = null;
    private JMenuItem m_miDiscoverNetworks = null;
    private JMenuItem m_miGraphNetwork = null;
    private JMenuItem m_miDeauthenticateHosts = null; // airdrop
    private JMenuItem m_miReplayInjectPackets = null;
    private JMenuItem m_miDecloakWEPWPAKey = null; // airdecloak
    private JMenuItem m_miCrackWEPWPAKey = null;
    private JMenuItem m_miCommunicatewithAccessPoint = null;
    private JMenuItem m_miCreateFakeAccessPoint = null; // airbase
    private JMenuItem m_miForgePackets = null;
    
    // DSniff
    private JMenu m_mnuDSniff = null;
    private JMenuItem m_miSniffFiles = null;
    private JMenuItem m_miSniffMail = null;
    private JMenuItem m_miSniffMessages = null;
    private JMenuItem m_miSniffURLs = null;
    private JMenuItem m_miWebSpy = null;
    
    // Recon
    private JMenu m_mnuRecon = null;
    private JMenuItem m_miDiscoverHosts = null;
    private JMenuItem m_miPing = null;
    private JMenuItem m_miDig = null;
    private JMenuItem m_miTraceRoute = null;
    private JMenuItem m_miWhoIs = null;
    
    // Exploit
    private JMenu m_mnuExploit = null;
    private JMenuItem m_miARPPoisonRouting = null;
    private JMenu m_mnuMetasploitFramework = null;
    private JMenuItem m_miUpdateMetasploit = null;
    private JMenuItem m_miLaunchMSFConsole = null;
    private JMenuItem m_miLaunchMSFGUI = null;
    private JMenuItem m_miLaunchArmitage = null;
    private JMenuItem m_miQuickExploit = null;
    
    // Post-Exploit
    private JMenu m_mnuPostExploit = null;
    private JMenuItem m_miSniffPasswords = null;
    
    // Defense
    private JMenu m_mnuDefense = null;
    private JMenuItem m_miMACChanger = null;
    private JMenuItem m_miTORNetwork = null;
    
    // Other Tools
    private JMenu m_mnuOtherTools = null;
    
    private JMenuItem m_miTelnet = null;
    private JMenuItem m_miNetstat = null;
    private JMenuItem m_miWireshark = null;
    private JMenuItem m_miBURPSuite = null;
    private JMenuItem m_miNikto = null;
    private JMenuItem m_miHydra = null;
    private JMenuItem m_miHping = null;
    private JMenuItem m_miSSLStrip = null;
    private JMenuItem m_miTCPDump = null;
    private JMenuItem m_miSQLScan = null;
    private JMenuItem m_miSQLMap = null;
    private JMenuItem m_miSQLNinja = null;
    private JMenuItem m_miReaver = null;
    
    // --------------------------------------------------------------------------------
    // Name: FMain
    // Abstract: Class constructor
    // --------------------------------------------------------------------------------
    public FMain()
    {
    	super("Aircrack-NGUI", 525, 325, true, true, "");
        try
        {
            Initialize();
            AddControls();
            DisableControlsWithUnmetDependencies( );
            DisableUnfinishedControls( );
        }
        catch (Exception excError)
        {
            // Display error message
            
            CUtilities.WriteLog( excError );
        }
    }

    // ----------------------------------------------------------------------------
    // Name: Initialize
    // Abstract: Initializes window
    // ----------------------------------------------------------------------------
    private void Initialize( )
    {
        
        try
        {
         
            // Center screen
            CUtilities.CenterScreen( this );
            
        }
        catch ( Exception excError )
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
            
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            m_mnbMainMenu = new JMenuBar( );
            
            m_mnuSetup = new JMenu("Setup");
            m_mnuSetup.setMnemonic('S');
            m_miAddRemoveUtilities = CAircrackUtilities.CreateMenuItem("Add/Remove Utilities", 'A', m_mnuSetup, this);
            m_miNetworkDevices = CAircrackUtilities.CreateMenuItem("Network Devices", 'N', m_mnuSetup, this);
            m_miSettings = CAircrackUtilities.CreateMenuItem("Settings", 'e', m_mnuSetup, this);
            m_mnuSetup.addSeparator( );
            m_miExit = CAircrackUtilities.CreateMenuItem("Exit", 'x', m_mnuSetup, this);
            m_mnbMainMenu.add(m_mnuSetup);
            
            m_mnuSuites = new JMenu("Suites");
            m_mnuAircrackNG = new JMenu("Aircrack-NG");
            m_mnuAircrackNG.setMnemonic('A');
            m_miManageDrivers = CAircrackUtilities.CreateMenuItem("Manage Drivers", 'M', m_mnuAircrackNG, this);
            m_miWirelessCardServer = CAircrackUtilities.CreateMenuItem("Wireless Card Server", 'I', m_mnuAircrackNG, this);
            m_miVirtualTunnel = CAircrackUtilities.CreateMenuItem("Virtual Tunnel", 'V', m_mnuAircrackNG, this);
            m_miCreateFakeAccessPoint = CAircrackUtilities.CreateMenuItem("Create Fake Access Point", 'P', m_mnuAircrackNG, this);
            m_miDiscoverNetworks = CAircrackUtilities.CreateMenuItem("Discover Networks", 'N', m_mnuAircrackNG, this);
            m_miGraphNetwork = CAircrackUtilities.CreateMenuItem("Graph Network", 'G', m_mnuAircrackNG, this);
            m_miForgePackets = CAircrackUtilities.CreateMenuItem("Forge Packets", 'F', m_mnuAircrackNG, this);
            m_miDeauthenticateHosts = CAircrackUtilities.CreateMenuItem("Deauthenticate Hosts", 'D', m_mnuAircrackNG, this);
            m_miReplayInjectPackets = CAircrackUtilities.CreateMenuItem("Replay/Inject Packets", 'R', m_mnuAircrackNG, this);
            m_miCreateWPAHashDictionary = CAircrackUtilities.CreateMenuItem("WPA Dictionary", 'W', m_mnuAircrackNG, this);
            m_miCommunicatewithAccessPoint = CAircrackUtilities.CreateMenuItem("Communciate w/AP", 'C', m_mnuAircrackNG, this);
            m_miDecloakWEPWPAKey = CAircrackUtilities.CreateMenuItem("Decloak WEP/WPA Key", 'E', m_mnuAircrackNG, this);
            m_miCrackWEPWPAKey = CAircrackUtilities.CreateMenuItem("Crack WEP/WPA Key", 'K', m_mnuAircrackNG, this);
            m_mnuSuites.add(m_mnuAircrackNG);
            
            m_mnuDSniff = new JMenu("DSniff");
            m_mnuDSniff.setMnemonic('D');
            m_miSniffPasswords = CAircrackUtilities.CreateMenuItem("Sniff Passwords", 'P', m_mnuDSniff, this);
            m_miSniffFiles = CAircrackUtilities.CreateMenuItem("Sniff Files", 'F', m_mnuDSniff, this);
            m_miSniffMail = CAircrackUtilities.CreateMenuItem("Sniff Mail", 'M', m_mnuDSniff, this);
            m_miSniffMessages = CAircrackUtilities.CreateMenuItem("Sniff Messages", 'E', m_mnuDSniff, this);
            m_miSniffURLs = CAircrackUtilities.CreateMenuItem("Sniff URLs", 'U', m_mnuDSniff, this);
            m_miWebSpy = CAircrackUtilities.CreateMenuItem("Web Spy", 'W', m_mnuDSniff, this);
            m_mnuSuites.add(m_mnuDSniff);
            m_mnbMainMenu.add(m_mnuSuites);
            
            m_mnuRecon = new JMenu("Recon");
            m_mnuRecon.setMnemonic('R');
            
            m_miDiscoverHosts = CAircrackUtilities.CreateMenuItem("Discover Hosts", 'H', m_mnuRecon, this);
            m_miWireshark = CAircrackUtilities.CreateMenuItem("Wireshark", 'W', m_mnuRecon, this);
            m_miPing = CAircrackUtilities.CreateMenuItem("Ping", 'P', m_mnuRecon, this);
            m_miDig = CAircrackUtilities.CreateMenuItem("Dig", 'D', m_mnuRecon, this);
            m_miTraceRoute = CAircrackUtilities.CreateMenuItem("Trace Route", 'R', m_mnuRecon, this);
            m_miWhoIs = CAircrackUtilities.CreateMenuItem("Who Is", 'W', m_mnuRecon, this);
            m_miNikto = CAircrackUtilities.CreateMenuItem("Nikto", 'N', m_mnuRecon, this);
            m_miHping = CAircrackUtilities.CreateMenuItem("HPing2", '2', m_mnuRecon, this);
            m_mnbMainMenu.add(m_mnuRecon);
    
            m_mnuExploit = new JMenu("Exploit");
            m_mnuExploit.setMnemonic('E');
            m_mnuMetasploitFramework = new JMenu("Metasploit");
            m_mnuMetasploitFramework.setMnemonic('M');
            m_miUpdateMetasploit = CAircrackUtilities.CreateMenuItem("MSF Update", 'U', m_mnuMetasploitFramework, this);
            m_miLaunchMSFConsole = CAircrackUtilities.CreateMenuItem("MSF Console", 'C', m_mnuMetasploitFramework, this);
            m_miLaunchMSFGUI = CAircrackUtilities.CreateMenuItem("MSF GUI", 'G', m_mnuMetasploitFramework, this);
            m_miLaunchArmitage = CAircrackUtilities.CreateMenuItem("Armitage", 'A', m_mnuMetasploitFramework, this);
            m_mnuExploit.add(m_mnuMetasploitFramework);
            m_miARPPoisonRouting = CAircrackUtilities.CreateMenuItem("ARP Poison Routing", 'A', m_mnuExploit, this);
            m_miQuickExploit  = CAircrackUtilities.CreateMenuItem("Quick Exploit", 'Q', m_mnuMetasploitFramework, this);
            m_miBURPSuite = CAircrackUtilities.CreateMenuItem("BURP Suite", 'u', m_mnuExploit, this);
            m_miReaver = CAircrackUtilities.CreateMenuItem("Reaver", 'v', m_mnuExploit, this);
            m_mnbMainMenu.add(m_mnuExploit);
            
            m_mnuPostExploit = new JMenu("Post-Exploit");
            m_miTelnet = CAircrackUtilities.CreateMenuItem("Telnet", 'T', m_mnuPostExploit, this);
            m_miTCPDump = CAircrackUtilities.CreateMenuItem("TCP Dump", 'T', m_mnuPostExploit, this);
            m_mnbMainMenu.add(m_mnuPostExploit);
            
            m_mnuDefense = new JMenu("Defense");
            m_mnuDefense.setMnemonic('D');
            m_miMACChanger = CAircrackUtilities.CreateMenuItem("MAC Changer", 'c', m_mnuDefense, this);
            m_miTORNetwork = CAircrackUtilities.CreateMenuItem("TOR Network", 'O', m_mnuDefense, this);
            m_mnbMainMenu.add(m_mnuDefense);
            
            m_mnuOtherTools = new JMenu("Other Tools");
            m_mnuOtherTools.setMnemonic('O');
            m_miNetstat = CAircrackUtilities.CreateMenuItem("Netstat", 'N', m_mnuOtherTools, this);
            m_miHydra = CAircrackUtilities.CreateMenuItem("Hydra", 'H', m_mnuOtherTools, this);
            m_miSSLStrip = CAircrackUtilities.CreateMenuItem("SSL Strip", 'S', m_mnuOtherTools, this);
            m_miSQLScan = CAircrackUtilities.CreateMenuItem("SQL Scan", 'Q', m_mnuOtherTools, this);
            m_miSQLMap = CAircrackUtilities.CreateMenuItem("SQL Map", 'L', m_mnuOtherTools, this);
            m_miSQLNinja = CAircrackUtilities.CreateMenuItem("SQL Ninja", 'j', m_mnuOtherTools, this);
            
            m_mnbMainMenu.add(m_mnuOtherTools);
            
            setJMenuBar(m_mnbMainMenu);
            
        }
        catch ( Exception excError )
        {

            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }
    
    // --------------------------------------------------------------------------------
    // Name: DisableControlsWithUnmetDependencies
    // Abstract: Disables buttons whose programs they need to run aren't installed
    // --------------------------------------------------------------------------------
    private void DisableControlsWithUnmetDependencies( )
    {
    	try
    	{
    		CLocalMachine clsLocalMachine = CGlobals.clsLocalMachine;
    		
    		// Windows has some different program names than Linux/Mac. Handle it here.
    		if (CGlobals.clsLocalMachine.GetOperatingSystem() != CLocalMachine.udtOperatingSystemType.WINDOWS)
    		{
	            m_miNetworkDevices.setEnabled( clsLocalMachine.ProgramInstalled( "ifconfig" ) && clsLocalMachine.ProgramInstalled( "iwconfig" ) );
	            m_miDiscoverNetworks.setEnabled( clsLocalMachine.ProgramInstalled( "iwlist" ) && clsLocalMachine.ProgramInstalled( "airodump-ng" ) );
    		}
    		else
    		{
    			m_miNetworkDevices.setEnabled( clsLocalMachine.ProgramInstalled( "ipconfig" ) && clsLocalMachine.ProgramInstalled( "netsh" ) );
	            m_miDiscoverNetworks.setEnabled( clsLocalMachine.ProgramInstalled( "netsh" ) && clsLocalMachine.ProgramInstalled( "airodump-ng" ) );
    		}
            m_miReplayInjectPackets.setEnabled( clsLocalMachine.ProgramInstalled( "aireplay-ng" ) );
            m_miDiscoverHosts.setEnabled( clsLocalMachine.ProgramInstalled( "nmap" ) );
            m_miCrackWEPWPAKey.setEnabled( clsLocalMachine.ProgramInstalled( "aircrack-ng" ) );
            m_miGraphNetwork.setEnabled( clsLocalMachine.ProgramInstalled( "airgraph-ng" ) );
            m_miCreateWPAHashDictionary.setEnabled( clsLocalMachine.ProgramInstalled( "airolib-ng" ) );
            m_miCommunicatewithAccessPoint.setEnabled( clsLocalMachine.ProgramInstalled( "easside-ng" ) && clsLocalMachine.ProgramInstalled( "wesside-ng" ) );
            m_miForgePackets.setEnabled( clsLocalMachine.ProgramInstalled( "packetforge-ng" ) );
            m_miARPPoisonRouting.setEnabled( clsLocalMachine.ProgramInstalled( "arpspoof" ) );
            m_miSniffPasswords.setEnabled( clsLocalMachine.ProgramInstalled( "dsniff" ) );
            m_miNikto.setEnabled( clsLocalMachine.ProgramInstalled( "nikto.pl" ) );
            m_miSSLStrip.setEnabled( clsLocalMachine.ProgramInstalled( "sslstrip.py" ) );
            m_miTCPDump.setEnabled( clsLocalMachine.ProgramInstalled( "tcpdump" ) );
            
            m_miWireshark.setEnabled( clsLocalMachine.ProgramInstalled( "wireshark" ) );
            // m_miMACChanger.setEnabled( clsLocalMachine.ProgramInstalled( "macchanger" ) );
            m_miReaver.setEnabled( clsLocalMachine.ProgramInstalled( "reaver" ) );
            
            m_mnuMetasploitFramework.setEnabled(clsLocalMachine.OneOfProgramsInstalled(new String[] {"msfupdate", "msfconsole", "msfgui", "armitage", "msfconsole"}));
            m_miUpdateMetasploit.setEnabled( clsLocalMachine.ProgramInstalled("msfupdate" ) );
            m_miLaunchMSFConsole.setEnabled( clsLocalMachine.ProgramInstalled( "msfconsole" ) );
            m_miLaunchMSFGUI.setEnabled( clsLocalMachine.ProgramInstalled( "msfgui" ) );
            m_miLaunchArmitage.setEnabled( clsLocalMachine.ProgramInstalled( "armitage" ) );
            m_miQuickExploit.setEnabled( clsLocalMachine.ProgramInstalled( "msfconsole" ) );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: DisableUnfinishedControls
    // Abstract: Disables controls whose features aren't finished yet
    // --------------------------------------------------------------------------------
    private void DisableUnfinishedControls( )
    {
    	try
    	{
    		m_miManageDrivers.setEnabled(false);
    		m_miWirelessCardServer.setEnabled(false);
    		m_miCommunicatewithAccessPoint.setEnabled( false );
    		m_miVirtualTunnel.setEnabled(false);
    		m_miCreateFakeAccessPoint.setEnabled(false);
    		m_miDecloakWEPWPAKey.setEnabled(false);
    		
    		m_miSniffFiles.setEnabled(false);
    	    m_miSniffMail.setEnabled(false);
    	    m_miSniffMessages.setEnabled(false);
    	    m_miSniffURLs.setEnabled(false);
    	    m_miWebSpy.setEnabled(false);
    		
            m_miNetstat.setEnabled( false );
            m_miBURPSuite.setEnabled( false );
            m_miHydra.setEnabled( false );
            m_miSSLStrip.setEnabled( false );
            m_miSQLScan.setEnabled( false );
            m_miSQLMap.setEnabled( false );
            m_miSQLNinja.setEnabled( false );
            m_miTORNetwork.setEnabled( false );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: actionPerformed
    // Abstract: Event fired whenever a button is clicked
    // --------------------------------------------------------------------------------
    public void actionPerformed( ActionEvent aeSource )
    {
        
        try
        {
        	if ( aeSource.getSource( ) == m_miAddRemoveUtilities )					miAddRemoveUtilities_Click( );
        	else if ( aeSource.getSource( ) == m_miNetworkDevices )          		miNetworkDevices_Click( );
            else if ( aeSource.getSource( ) == m_miSettings )						miSettings_Click( );
            else if ( aeSource.getSource( ) == m_miExit )							miExit_Click( ); 
            else if ( aeSource.getSource( ) == m_miDiscoverNetworks )      			miDiscoverNetworks_Click( ); 
            else if ( aeSource.getSource( ) == m_miReplayInjectPackets )   			miReplayInjectPackets_Click( );
            else if ( aeSource.getSource( ) == m_miDiscoverHosts )					miDiscoverHosts_Click( );
            else if ( aeSource.getSource( ) == m_miCrackWEPWPAKey )					miCrackWEPWPAKey_Click( );
            else if ( aeSource.getSource( ) == m_miCreateWPAHashDictionary )		miCreateWPAHashDictionary_Click( );
            else if ( aeSource.getSource( ) == m_miARPPoisonRouting )				miARPPoisonRouting_Click( );
            else if ( aeSource.getSource( ) == m_miCommunicatewithAccessPoint  )	miCommunicatewithAccessPoint_Click( );
            else if ( aeSource.getSource( ) == m_miForgePackets )					miForgePackets_Click( );
            else if ( aeSource.getSource( ) == m_miGraphNetwork )					miGraphNetwork_Click( );
            else if ( aeSource.getSource( ) == m_miSniffPasswords )					miSniffPasswords_Click( );
            else if ( aeSource.getSource( ) == m_miPing )       					miPing_Click( );
            else if ( aeSource.getSource( ) == m_miTraceRoute )						miTraceRoute_Click( );
            else if ( aeSource.getSource( ) == m_miWhoIs ) 							miWhoIs_Click( );
            else if ( aeSource.getSource( ) == m_miDig )							miDig_Click( );
            else if ( aeSource.getSource( ) == m_miTelnet )							miTelnet_Click( );
            else if ( aeSource.getSource( ) == m_miWireshark )						miWireshark_Click( );
            else if ( aeSource.getSource( ) == m_miNikto )							miNikto_Click( );
            else if ( aeSource.getSource( ) == m_miHping )							miHping_Click( );
            else if ( aeSource.getSource( ) == m_miUpdateMetasploit )				miUpdateMetasploit_Click( );
            else if ( aeSource.getSource( ) == m_miLaunchMSFConsole )				miLaunchMSFConsole_Click( );
            else if ( aeSource.getSource( ) == m_miLaunchMSFGUI )					miLaunchMSFGUI_Click( );
            else if ( aeSource.getSource( ) == m_miLaunchArmitage )					miLaunchArmitage_Click( );
            else if ( aeSource.getSource( ) == m_miQuickExploit )					miQuickExploit_Click( );
            else if ( aeSource.getSource( ) == m_miMACChanger )						miMACChanger_Click( );
            else if ( aeSource.getSource( ) == m_miReaver )							miReaver_Click( );
            else if ( aeSource.getSource( ) == m_miTCPDump )						miTCPDump_Click( );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
        
    }

    // --------------------------------------------------------------------------------
    // Name: miAddRemoveUtilities_Click
    // Abstract: Displays the Add/Remove Utilities window
    // --------------------------------------------------------------------------------
    private void miAddRemoveUtilities_Click( )
    {
    	try
        {
    		FAddRemoveUtilities frmAddRemoveUtilities = new FAddRemoveUtilities();
    		frmAddRemoveUtilities.setVisible(true);
    		
    		DisableControlsWithUnmetDependencies();
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }
    
    // --------------------------------------------------------------------------------
    // Name: miNetworkDevices_Click
    // Abstract: Displays the network interfaces
    // --------------------------------------------------------------------------------
    private void miNetworkDevices_Click( )
    {
        try
        {
            FNetworkInterfaces frmNetworkInterfaces = new FNetworkInterfaces();
            frmNetworkInterfaces.setVisible(true);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }

    // --------------------------------------------------------------------------------
    // Name: miSettings_Click
    // Abstract: Displays the settings window
    // --------------------------------------------------------------------------------
    private void miSettings_Click( )
    {
    	try
    	{
    		FSettings frmSettings = new FSettings( );
    		frmSettings.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: miExit_Click
    // Abstract: Closes the Aircrack window
    // --------------------------------------------------------------------------------
    private void miExit_Click( )
    {
    	try
    	{
    		setVisible( false );
    		dispose( );
    		System.exit( 0 );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
    
    // --------------------------------------------------------------------------------
    // Name: btnDiscoverNetworks_Click
    // Abstract: Discovers all networks in range based on the network interface
    // --------------------------------------------------------------------------------
    private void miDiscoverNetworks_Click( )
    {
        try
        {
            FDiscoverNetworks frmDiscoverNetworks = new FDiscoverNetworks();
            frmDiscoverNetworks.setVisible(true);
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }
    
    // --------------------------------------------------------------------------------
    // Name: btnReplayInjectPackets_Click
    // Abstract: Enables replaying/injecting packets
    // --------------------------------------------------------------------------------
    private void miReplayInjectPackets_Click( )
    {
        try
        {
            if ( CGlobals.clsLocalMachine.GetNumberOfInterfacesInMonitorMode( ) > 0 )
            {
                FReplayInjectPackets frmReplayInjectPackets = new FReplayInjectPackets( );
                frmReplayInjectPackets.setVisible( true );
            }
            else
            {
                JOptionPane.showMessageDialog( null, "No interfaces are in monitor mode. Please use the Network Interfaces button to set at least one interface into monitor mode.", "Aircrack-NGUI", JOptionPane.OK_OPTION );
            }
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }

    // --------------------------------------------------------------------------------
    // Name: btnDiscoverHosts_Click
    // Abstract: Allows for quick detection of hosts on a network
    // --------------------------------------------------------------------------------
    private void miDiscoverHosts_Click( )
    {
    	try
    	{
    		FDiscoverHosts frmDiscoverHosts = new FDiscoverHosts( );
    		frmDiscoverHosts.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	
    }

    // --------------------------------------------------------------------------------
    // Name: btnCrackWEPWPAKey_Click
    // Abstract: Takes information gathered on a wireless network and attempts to break
    //			 the wireless password
    // --------------------------------------------------------------------------------
    private void miCrackWEPWPAKey_Click( )
    {
    	try
    	{
    		FCrackWEPWPAKeys frmCrackWEPWPAKeys = new FCrackWEPWPAKeys( );
    		frmCrackWEPWPAKeys.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: btnCreateWPAHashDictionary_Click
    // Abstract: Creates a dictionary of WPA network names and passwords
    // --------------------------------------------------------------------------------
    private void miCreateWPAHashDictionary_Click( )
    {
    	try
    	{
    		FCreateWPADictionary frmCreateWPADictionary = new FCreateWPADictionary( );
    		frmCreateWPADictionary.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // --------------------------------------------------------------------------------
    // Name: btnARPPoisonRouting_Click
    // Abstract: Allows for the use of ARP poison routing for man-in-the-middle attacks
    // --------------------------------------------------------------------------------
    private void miARPPoisonRouting_Click( )
    {
    	try
    	{
    		FARPPoisonRouting frmARPPoisonRouting = new FARPPoisonRouting( );
    		frmARPPoisonRouting.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: btnCommunicateWithAccessPoint_Click
    // Abstract: Allows you to communicate with a WEP access point without being
    //			 connected
    // --------------------------------------------------------------------------------
    private void miCommunicatewithAccessPoint_Click( )
    {
    	try
    	{
    		FCommunicateWithAP frmCommunicateWithAccessPoint = new FCommunicateWithAP( );
    		frmCommunicateWithAccessPoint.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: btnForgePackets_Click
    // Abstract: Allows you to build packets based on packet data
    // --------------------------------------------------------------------------------
    private void miForgePackets_Click( )
    {
    	try
    	{
    		FForgePackets frmForgePackets = new FForgePackets( );
    		frmForgePackets.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // --------------------------------------------------------------------------------
    // Name: btnGraphNetwork_Click
    // Abstract: Allows you to graph out a network based on a network scan
    // --------------------------------------------------------------------------------
    private void miGraphNetwork_Click( )
    {
    	try
    	{
    		FGraphNetwork frmGraphNetwork = new FGraphNetwork( );
    		frmGraphNetwork.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: miSniffPasswords_Click
    // Abstract: Allows for sniffing passwords out of a specified interface
    // --------------------------------------------------------------------------------
    private void miSniffPasswords_Click( )
    {
    	try
    	{
    		FSniffPasswords frmSniffPasswords = new FSniffPasswords( );
    		frmSniffPasswords.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnPing_Click
	// Abstract: Ping button click. Opens a new Ping window.
	// --------------------------------------------------------------------------------
    private void miPing_Click( )
    {
        try
        {
            FOtherToolsBasicToolsPing frmPing = new FOtherToolsBasicToolsPing( );
            frmPing.setVisible( true );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: btnTraceRoute_Click
	// Abstract: Trace Route button click. Opens a new Trace Route window.
	// --------------------------------------------------------------------------------
    private void miTraceRoute_Click( )
    {
    	try
    	{
    		FOtherToolsBasicToolsTraceRoute frmTraceRoute = new FOtherToolsBasicToolsTraceRoute( );
    		frmTraceRoute.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnWhoIs_Click
	// Abstract: WhoIs button click. Opens a new WhoIs window.
	// --------------------------------------------------------------------------------
    private void miWhoIs_Click( )
    {
        try
        {
            FOtherToolsBasicToolsWhoIs frmWhoIs = new FOtherToolsBasicToolsWhoIs( );
            frmWhoIs.setVisible( true );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: miDig_Click
	// Abstract: Dig button click. Opens a new Dig window.
	// --------------------------------------------------------------------------------
    private void miDig_Click( )
    {
    	try
    	{
    		FOtherToolsBasicToolsDig frmDig = new FOtherToolsBasicToolsDig( );
    		frmDig.setVisible( true );
    	}
    	catch (Exception excError )
    	{
    		CUtilities.WriteLog( excError );
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: miTelnet_Click
	// Abstract: Telnet button click. Opens a new Telnet window.
	// --------------------------------------------------------------------------------
    private void miTelnet_Click( )
    {
    	try
    	{
    		FOtherToolsBasicToolsTelnet frmTelnet = new FOtherToolsBasicToolsTelnet( );
    		frmTelnet.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnWireshark_Click
	// Abstract: Shows the Wireshark window
    // --------------------------------------------------------------------------------
    private void miWireshark_Click( )
    {
    	try
    	{
    		FOtherToolsWireshark frmWireshark = new FOtherToolsWireshark( );
    		frmWireshark.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
	// Name: miNikto_Click
	// Abstract: Opens a new Nikto attack window
    // --------------------------------------------------------------------------------
    private void miNikto_Click( )
    {
    	try
    	{
    		FOtherToolsNikto frmNikto = new FOtherToolsNikto( );
    		frmNikto.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // --------------------------------------------------------------------------------
 	// Name: miNikto_Click
 	// Abstract: Opens a new HPing attack window
     // --------------------------------------------------------------------------------
     private void miHping_Click( )
     {
     	try
     	{
     		FOtherToolsHPing frmHPing = new FOtherToolsHPing( );
     		frmHPing.setVisible( true );
     	}
     	catch (Exception excError)
     	{
     		CUtilities.WriteLog(excError);
     	}
     }
    
	// --------------------------------------------------------------------------------
	// Name: miUpdateMetasploit_Click
	// Abstract: Updates the Metasploit Framework
    // --------------------------------------------------------------------------------
    private void miUpdateMetasploit_Click( )
    {
    	try
		{
			DProgramOutput dlgProgramOutput = new DProgramOutput("Update Metasploit Framework", new String[] {"msfupdate"});
			dlgProgramOutput.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
    }
    
	// --------------------------------------------------------------------------------
	// Name: miLaunchMSFConsole_Click
	// Abstract: Creates a new console that runs MSFConsole
    // --------------------------------------------------------------------------------
    private void miLaunchMSFConsole_Click( )
    {
    	try
		{
			String astrCommand[] = new String[] {"xterm", "-e", "echo \"Please wait for the msfconsole to load...\"; msfconsole;"};
			CProcess clsMSFConsole = new CProcess(astrCommand, false, false, true);
			clsMSFConsole.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
    }
    
	// --------------------------------------------------------------------------------
	// Name: miLaunchMSFGUI_Click
	// Abstract: Creates a new console that runs MSFConsole
    // --------------------------------------------------------------------------------
    private void miLaunchMSFGUI_Click( )
    {
    	try
		{
			String astrCommand[] = new String[] {"xterm", "-e", "echo \"Please wait for the msfgui to load...\"; msfgui;"};
			CProcess clsMSFGUI = new CProcess(astrCommand, false, false, true);
			clsMSFGUI.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
    }
    
	// --------------------------------------------------------------------------------
	// Name: miLaunchArmitage_Click
	// Abstract: Checks if Armitage is already running. If not, create a new one.
    // --------------------------------------------------------------------------------
    private void miLaunchArmitage_Click( )
    {
    	int intArmitageProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess("armitage");
		if ( intArmitageProcessID == -1 )
		{
			CProcess clsArmitage = new CProcess(new String[] {"armitage"}, true, false, true);
			clsArmitage.CloseProcess();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Armitage is already running (PID " + intArmitageProcessID + ")");
		}
    }

	// --------------------------------------------------------------------------------
	// Name: miQuickExploit_Click
	// Abstract: Shows the Quick Exploit window
    // --------------------------------------------------------------------------------
    private void miQuickExploit_Click( )
    {
    	try
    	{
    		FQuickExploit frmQuickExploit = new FQuickExploit( );
    		frmQuickExploit.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
    
	// --------------------------------------------------------------------------------
	// Name: btnMACChanger_Click
	// Abstract: Shows the MAC changer window
    // --------------------------------------------------------------------------------
    private void miMACChanger_Click( )
    {
    	try
    	{
    		FOtherToolsMACChanger frmMACChanger = new FOtherToolsMACChanger( );
    		frmMACChanger.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: btnReaver_Click
	// Abstract: Shows the Reaver window
    // --------------------------------------------------------------------------------
    private void miReaver_Click( )
    {
    	try
    	{
    		FOtherToolsReaver frmReaver = new FOtherToolsReaver( );
    		frmReaver.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

	// --------------------------------------------------------------------------------
	// Name: miTCPDump_Click
	// Abstract: Shows the TCP Dump window
    // --------------------------------------------------------------------------------
    private void miTCPDump_Click( )
    {
    	try
    	{
    		FTCPDump frmTCPDump = new FTCPDump( );
    		frmTCPDump.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }
}
