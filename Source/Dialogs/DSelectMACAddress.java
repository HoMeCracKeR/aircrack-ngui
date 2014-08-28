// --------------------------------------------------------------------------------
// Name: DSelectMACAddress
// Abstract: Select either one of your network interface's MAC addresses, a network
//			 address, or a station's address on the network
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DSelectMACAddress extends CAircrackDialog
{
	protected final static long serialVersionUID = 1L;

	private JRadioButton m_rdbMyComputersMAC = null;
	private CComboBox m_cboMyComputersMAC = null;
	private JRadioButton m_rdbAccessPointMAC = null;
	private JRadioButton m_rdbComputerMAC = null;
	private JLabel m_lblSelectedMACAddress = null;
	private JButton m_btnOK = null;
	
	private String m_strSelectedMAC = "";
	
	// --------------------------------------------------------------------------------
	// Name: DSelectNetwork
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DSelectMACAddress( )
	{
		super("Select MAC Address", 315, 175, false, false, "");
		try
		{
			AddControls( );
			PopulateInterfaces( );
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
            ButtonGroup bgpMACType = new ButtonGroup( );
            
            CUtilities.AddLabel(conControlArea, "Please select MAC type:", 5, 5);
            m_rdbMyComputersMAC = CUtilities.AddRadioButton(conControlArea, this, bgpMACType, "My Computer's MAC", 20, 5);
        	m_cboMyComputersMAC = CUtilities.AddComboBox(conControlArea, null, 22, 180, 18, 100);
        	m_rdbAccessPointMAC = CUtilities.AddRadioButton(conControlArea, this, bgpMACType, "Access Point MAC", 40, 5);
        	m_rdbComputerMAC = CUtilities.AddRadioButton(conControlArea, this, bgpMACType, "Computer MAC", 60, 5);
        	m_lblSelectedMACAddress = CUtilities.AddLabel(conControlArea, "<html>Selected MAC Address:<br>None selected</html>", 85, 5);
        	m_btnOK = CUtilities.AddButton(conControlArea, this, "OK", 120, 120, 18, 75);
        	
        	m_cboMyComputersMAC.GetJavaComboBox( ).addActionListener( this );
        	m_rdbMyComputersMAC.setSelected( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the interfaces available to the computer in the dropdown
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				m_cboMyComputersMAC.AddItemToList(aclsInterfaces[intIndex].GetInterface(), 0);
			}
			m_cboMyComputersMAC.SetSelectedIndex( 0 );
			CAircrackUtilities.SetComboBoxSelectedValue( m_cboMyComputersMAC, CUserPreferences.GetPreferredStandardInterface( ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click or checkbox click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_rdbMyComputersMAC )								rdbMyComputersMAC_Click( );
			else if ( aeSource.getSource( ) == m_cboMyComputersMAC.GetJavaComboBox( ) )		cboMyComputersMAC_SelectedIndexChanged( );
			else if ( aeSource.getSource( ) == m_rdbAccessPointMAC )						rdbAccessPointMAC_Click( );
			else if ( aeSource.getSource( ) == m_rdbComputerMAC )							m_rdbComputerMAC_Click( );
			else if ( aeSource.getSource( ) == m_btnOK )									dispose( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: rdbMyComputersMAC_Click
	// Abstract: My Computers MAC radio button Click event
	// --------------------------------------------------------------------------------
	private void rdbMyComputersMAC_Click( )
	{
		try
		{
			m_cboMyComputersMAC.setVisible( m_rdbMyComputersMAC.isSelected( ) );
			String strSelectedInterface = m_cboMyComputersMAC.GetSelectedItemName( );
			m_strSelectedMAC = new CNetworkInterface(strSelectedInterface).GetMACAddress();
			m_lblSelectedMACAddress.setText("<html>Selected MAC Address:<br>" + m_strSelectedMAC + " (" + strSelectedInterface + ")</html>");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboMyComputersMAC_SelectedIndexChanged
	// Abstract: My Computers MAC combobox SelectedIndexChanged event
	// --------------------------------------------------------------------------------
	private void cboMyComputersMAC_SelectedIndexChanged( )
	{
		try
		{
			String strSelectedInterface = m_cboMyComputersMAC.GetSelectedItemName( );
			m_strSelectedMAC = new CNetworkInterface(strSelectedInterface).GetMACAddress();
			m_lblSelectedMACAddress.setText("<html>Selected MAC Address:<br>" + m_strSelectedMAC + " (" + strSelectedInterface + ")</html>");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: rdbAccessPointMAC_Click
	// Abstract: Access Point MAC radio button Click event
	// --------------------------------------------------------------------------------
	private void rdbAccessPointMAC_Click( )
	{
		try
		{
			m_cboMyComputersMAC.setVisible( m_rdbMyComputersMAC.isSelected( ) );
			
			DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
			dlgSelectNetwork.SetReturnType( DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID );
			dlgSelectNetwork.setVisible( true );
			String strSelectedMAC = dlgSelectNetwork.GetSelectedNetwork( );
			if ( strSelectedMAC.equals( "" ) == false )
			{
				m_strSelectedMAC = strSelectedMAC;
				m_lblSelectedMACAddress.setText("<html>Selected MAC Address:<br>" + m_strSelectedMAC + " (network)</html>");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: m_rdbComputerMAC_Click
	// Abstract: Computer MAC radio button Click event
	// --------------------------------------------------------------------------------
	private void m_rdbComputerMAC_Click( )
	{
		try
		{
			m_cboMyComputersMAC.setVisible( m_rdbMyComputersMAC.isSelected( ) );
			
			DSelectStation dlgSelectStation = new DSelectStation( );
			dlgSelectStation.SetReturnType(DSelectStation.udtReturnType.RETURN_TYPE_MAC_ADDRESS);
			dlgSelectStation.setVisible( true );
			String strSelectedMAC = dlgSelectStation.GetSelectedStation( );
			if ( strSelectedMAC.equals( "" ) == false )
			{
				m_strSelectedMAC = strSelectedMAC;
				m_lblSelectedMACAddress.setText("<html>Selected MAC Address:<br>" + m_strSelectedMAC + " (station)</html>");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetSelectedMACAddress
	// Abstract: Returns the selected MAC address to the form
	// --------------------------------------------------------------------------------
	public String GetSelectedMACAddress( )
	{
		return m_strSelectedMAC;
	}
	
}
