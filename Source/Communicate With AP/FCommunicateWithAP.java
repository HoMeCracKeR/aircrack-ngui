// --------------------------------------------------------------------------------
// Name: FCommunicateWithAP
// Abstract: Allows you to directly communicate with a WEP Access Point without
//			 authenticating.
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class FCommunicateWithAP extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	// Mandatory
	private JLabel m_lblWirelessInterface = null;
	private CComboBox m_cboWirelessInterface = null;
	private JLabel m_lblBuddyNGServerIP = null;
	private CTextBox m_txtBuddyNGServerIP = null;
	
	// Optional
	private JCheckBox m_chkVictimMAC = null;
	private CTextBox m_txtVictimMAC = null;
	private JCheckBox m_chkSourceMAC = null;
	private CTextBox m_txtSourceMAC = null;
	private JCheckBox m_chkSourceIP = null;
	private CTextBox m_txtSourceIP = null;
	private JCheckBox m_chkRouterIP = null;
	private CTextBox m_txtRouterIP = null;
	private JCheckBox m_chkChannel = null;
	private CComboBox m_cboChannel = null;
	
	private JLabel m_lblBuddyNGOutput = null;
	private JTextArea m_txaBuddyNGOutput = null;
	private JScrollPane m_scpBuddyNGOutput = null;
	
	private JLabel m_lblEassideNGOutput = null;
	private JTextArea m_txaEassideNGOutput = null;
	private JScrollPane m_scpEassideNGOutput = null;
	
	private JButton m_btnStart = null;
	
	private Process m_prcBuddyNGProcess = null;
	private Process m_prcEassideNGProcess = null;
	private InputStreamReader m_isrBuddyNGProcess = null;
	private InputStreamReader m_isrEassideNGProcess = null;
	private java.util.Timer m_timUpdateTextAreas = null; 

	// --------------------------------------------------------------------------------
	// Name: FCommunicateWithAP
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FCommunicateWithAP( )
	{
		super("Communicate with AP", 640, 480, false, false, "");
		try
		{
			AddControls( );
			PopulateInterfaces( );
			PopulateChannels( );
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
			// Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            // Mandatory
        	m_lblWirelessInterface = CUtilities.AddLabel(conControlArea, "Interface:", 7, 5);
        	m_cboWirelessInterface = CUtilities.AddComboBox(conControlArea, null, 5, 135, 18, 150);
        	m_lblBuddyNGServerIP = CUtilities.AddLabel(conControlArea, "Buddy-NG Server:", 30, 7);
        	m_txtBuddyNGServerIP = CUtilities.AddTextBox(conControlArea, null, "", 15, 15, 29, 135);
        	
        	// Optional
        	m_chkVictimMAC = CUtilities.AddCheckBox(conControlArea, this, "Victim MAC:", 50, 5);
        	m_txtVictimMAC = CUtilities.AddTextBox(conControlArea, null, "", 15, 17, 51, 135);
        	m_chkSourceMAC = CUtilities.AddCheckBox(conControlArea, this, "Source MAC:", 70, 5);
        	m_txtSourceMAC = CUtilities.AddTextBox(conControlArea, null, "", 15, 17, 72, 135);
        	m_chkSourceIP = CUtilities.AddCheckBox(conControlArea, this, "Source IP:", 90, 5);
        	m_txtSourceIP = CUtilities.AddTextBox(conControlArea, null, "", 15, 15, 92, 135);
        	m_chkRouterIP = CUtilities.AddCheckBox(conControlArea, this, "Router IP:", 110, 5);
        	m_txtRouterIP = CUtilities.AddTextBox(conControlArea, null, "", 15, 15, 112, 135);
        	m_chkChannel = CUtilities.AddCheckBox(conControlArea, this, "Channel:", 130, 5);
        	m_cboChannel = CUtilities.AddComboBox(conControlArea, null, 132, 135, 18, 75);
        	
        	m_btnStart = CUtilities.AddButton(conControlArea, this, "Start", 155, 135, 18, 100);
        	
        	// Output TextAreas
        	m_lblBuddyNGOutput = CUtilities.AddLabel(conControlArea, "Buddy-NG Output:", 8, 310);
        	m_txaBuddyNGOutput = new JTextArea( 10, 27 );
        	m_scpBuddyNGOutput = new JScrollPane( m_txaBuddyNGOutput );
        	conControlArea.add( m_scpBuddyNGOutput );
        	splFrame.getConstraints( m_scpBuddyNGOutput ).setX( Spring.constant( 310 ) );
            splFrame.getConstraints( m_scpBuddyNGOutput ).setY( Spring.constant( 28 ) );
            
            m_txaBuddyNGOutput.setEnabled( false );
            
            m_lblEassideNGOutput = CUtilities.AddLabel(conControlArea, "Easside-NG Output:", 183, 310);
            m_txaEassideNGOutput = new JTextArea( 10, 27 );
            m_scpEassideNGOutput = new JScrollPane( m_txaEassideNGOutput );
        	conControlArea.add( m_scpEassideNGOutput );
        	splFrame.getConstraints( m_scpEassideNGOutput ).setX( Spring.constant( 310 ) );
            splFrame.getConstraints( m_scpEassideNGOutput ).setY( Spring.constant( 203 ) );
            
            m_txaEassideNGOutput.setEnabled( false );
        	
        	m_cboChannel.SetSorted( false );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the available interfaces to perform the attack on
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				m_cboWirelessInterface.AddItemToList(aclsInterfaces[intIndex].GetInterface(), 0);
			}
			m_cboWirelessInterface.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateChannels
	// Abstract: Populates the available channels to perform the attack on
	// --------------------------------------------------------------------------------
	private void PopulateChannels( )
	{
		try
		{
			for ( int intChannel = 1; intChannel <= 14; intChannel += 1 )
			{
				m_cboChannel.AddItemToList(String.valueOf(intChannel), intChannel);
			}
			m_cboChannel.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Whenever a button or checkbox is clicked
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_btnStart )			btnStart_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Starts the communication attack
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			/*
			String astrEasside[] = BuildEassideNGCommand( );
			
			m_txaBuddyNGOutput.setText( "Starting Buddy-NG...\n" );
			m_prcBuddyNGProcess = CAircrackUtilities.BuildAndStartProcess(new String[] {"buddy-ng"}, true, false);
			m_isrBuddyNGProcess = new InputStreamReader( m_prcBuddyNGProcess.getInputStream( ) );
			
			m_txaEassideNGOutput.setText( "Starting Easside-NG...\n");
			m_prcEassideNGProcess = CAircrackUtilities.BuildAndStartProcess(astrEasside, true, false);
			m_isrEassideNGProcess = new InputStreamReader( m_prcEassideNGProcess.getInputStream( ) );

			m_timUpdateTextAreas = new java.util.Timer( );
			m_timUpdateTextAreas.schedule(new CUpdateTextAreas(), 1000);
			*/
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: BuildEassideNGCommand
	// Abstract: Starts the communication attack
	// --------------------------------------------------------------------------------
	private String[] BuildEassideNGCommand( )
	{
		
		String astrEasside[] = new String[] {"easside-ng"};
		try
		{
			astrEasside = CAircrackUtilities.AddArgumentToCommand("f", m_cboWirelessInterface.GetSelectedItemName( ), astrEasside);
			astrEasside = CAircrackUtilities.AddArgumentToCommand("s", m_txtBuddyNGServerIP.getText().trim(), astrEasside);
			if ( m_chkVictimMAC.isSelected( ) == true )
				astrEasside = CAircrackUtilities.AddArgumentToCommand("v", m_txtVictimMAC.getText(), astrEasside);
			if ( m_chkSourceMAC.isSelected( ) == true )
				astrEasside = CAircrackUtilities.AddArgumentToCommand("m", m_txtSourceMAC.getText(), astrEasside);
			if ( m_chkSourceIP.isSelected( ) == true )
				astrEasside = CAircrackUtilities.AddArgumentToCommand("i", m_txtSourceIP.getText(), astrEasside);
			if ( m_chkRouterIP.isSelected( ) == true )
				astrEasside = CAircrackUtilities.AddArgumentToCommand("r", m_txtRouterIP.getText(), astrEasside);
			if ( m_chkChannel.isSelected( ) == true )
				astrEasside = CAircrackUtilities.AddArgumentToCommand("c", String.valueOf(m_cboChannel.GetSelectedItemValue( )), astrEasside);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrEasside;
	}

	// --------------------------------------------------------------------------------
	// Name: CUpdateTextAreas
	// Abstract: Timer process to update the TextAreas
	// --------------------------------------------------------------------------------
	private class CUpdateTextAreas extends TimerTask
	{

		@Override
		public void run()
		{
			try
			{
				
				int intBuffer = 0;
				char chrBuffer = 0;
				
				// First, buddy-ng (Handle your buddies first!)
				if ( m_isrBuddyNGProcess.ready( ) )
				{
					intBuffer = m_isrBuddyNGProcess.read( );
					while ( intBuffer != -1 )
					{
						chrBuffer = (char) intBuffer;
						m_txaBuddyNGOutput.setText(m_txaBuddyNGOutput.getText() + chrBuffer);
						intBuffer = m_isrBuddyNGProcess.read( );
					}
				}
				
				// Reset!
				intBuffer = 0;
				chrBuffer = 0;

				// Second, easside-ng
				if ( m_isrEassideNGProcess.ready( ) )
				{
					intBuffer = m_isrEassideNGProcess.read( );
					while ( intBuffer != -1 )
					{
						chrBuffer = (char) intBuffer;
						m_txaEassideNGOutput.setText( m_txaEassideNGOutput.getText() + chrBuffer );
						intBuffer = m_isrEassideNGProcess.read( );
					}
				}
				
				m_timUpdateTextAreas.schedule(new CUpdateTextAreas(), 1000);
				
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}
		
	}

	// --------------------------------------------------------------------------------
	// Name: SetTargetMAC
	// Abstract: Sets the target MAC address. This is so it can be set before the form
	//			 is displayed (used by Discover Networks).
	// --------------------------------------------------------------------------------
	public void SetTargetMAC( String strNetworkBSSID )
	{
		try
		{
			m_txtVictimMAC.setText( strNetworkBSSID );
			m_chkVictimMAC.setSelected( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}