// --------------------------------------------------------------------------------
// Name: FOtherToolsMACChanger
// Abstract: MAC Changer dialog
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;

public class FOtherToolsMACChanger extends CAircrackWindow implements ActionListener, DocumentListener 
{
	protected final static long serialVersionUID = 1L;
	
	private CComboBox m_cboInterfaces = null;
	private JButton m_btnUpdateInterfaceInformation = null;
	private CNetworkInterface m_aclsNetworkInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
	private JLabel m_lblCurrentMACAddress = null;
	private JLabel m_lblCurrentManufacturer = null;
	
	private ButtonGroup m_bgpAction = null;
	private JRadioButton m_rdbSpecifyFullAddress = null;
	private JRadioButton m_rdbUseRandomMACAddress = null;
	private CTextBox m_atxtMACFullAddress[] = null;
	private JButton m_btnSpecifyManufacturerAddress = null;

	private JButton m_btnExecute = null;
	
	private final String m_strCURRENT_MAC_ADDRESS_HEADER = "Current MAC:                              ";
	private final String m_strCURRENT_MANUFACTURER_HEADER = "Current Manufacturer:              ";
	
	private Pattern m_ptnMACAddressFormat = Pattern.compile("[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}");
		
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsMACChanger
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsMACChanger( )
	{
		super("MAC Changer", 430, 275, false, false, "MACChanger");
		try
		{
			AddControls( );
			PopulateNetworkInterfaces( );
			PopulateSavedProfiles( );
			if ( CUserPreferences.GetPreferredStandardInterface( ).equals( "" ) == false )
				btnUpdateInterfaceInformation_Click( );
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
			CUtilities.AddLabel(m_cntControlContainer, "Interface:", 81, 5);
			m_cboInterfaces = CUtilities.AddComboBox(m_cntControlContainer, null, 80, 80, 18, 150);
			m_btnUpdateInterfaceInformation = CUtilities.AddButton(m_cntControlContainer, this, "Update", 80, 240, 18, 100);
			m_lblCurrentMACAddress = CUtilities.AddLabel(m_cntControlContainer, m_strCURRENT_MAC_ADDRESS_HEADER + "Not specified.", 105, 5);
			m_lblCurrentManufacturer = CUtilities.AddLabel(m_cntControlContainer, m_strCURRENT_MANUFACTURER_HEADER + "Not specified.", 130, 5);
			
			m_bgpAction = new ButtonGroup( );
			m_rdbUseRandomMACAddress = CUtilities.AddRadioButton(m_cntControlContainer, this, m_bgpAction, "Use Random MAC Address", 155, 5);
			m_rdbSpecifyFullAddress = CUtilities.AddRadioButton(m_cntControlContainer, this, m_bgpAction, "Specify Full Address:", 180, 5);
			
			m_atxtMACFullAddress = new CTextBox[6];
			int intLeftPosition = 180;
			for ( int intIndex = 0; intIndex < m_atxtMACFullAddress.length; intIndex += 1 )
			{
				m_atxtMACFullAddress[intIndex] = CUtilities.AddTextBox(m_cntControlContainer, this, "00", 2, 2, 182, intLeftPosition);
				intLeftPosition += 30;
			}
			
			m_btnSpecifyManufacturerAddress = CUtilities.AddButton(m_cntControlContainer, this, "...", 182, 360, 18, 50);
			
			m_btnExecute = CUtilities.AddButton(m_cntControlContainer, this, "Execute", 215, getWidth( ) / 2 - 100 /2, 18, 100);
			
			m_lstParameters.add(new CProfileParameter("Interface", m_cboInterfaces));
			for ( int intIndex = 0; intIndex < 6; intIndex += 1 )
				m_lstParameters.add(new CProfileParameter("MACAddress" + intIndex, m_atxtMACFullAddress[intIndex]));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateNetworkInterfaces
	// Abstract: Populates the network interfaces to spoof the MAC on
	// --------------------------------------------------------------------------------
	private void PopulateNetworkInterfaces( )
	{
		try
		{
			int intIndex = 0;
			for ( intIndex = 0; intIndex < m_aclsNetworkInterfaces.length; intIndex += 1 )
				m_cboInterfaces.AddItemToList(m_aclsNetworkInterfaces[intIndex].GetInterface(), intIndex);
			m_cboInterfaces.SetSelectedIndex( 0 );
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboInterfaces, CUserPreferences.GetPreferredStandardInterface());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateSavedProfiles
	// Abstract: Populates the profiles from the hard disk
	// --------------------------------------------------------------------------------
	private void PopulateSavedProfiles( )
	{
		try
		{
    		m_cboSavedProfiles.AddItemToList("", 0);
    		File dirDiscoverNetworks = new File("SavedProfiles/MACChanger");
    		if ( dirDiscoverNetworks.exists( ) )
    		{
	    		File afilProfiles[] = dirDiscoverNetworks.listFiles( );
	    		for ( int intIndex = 0; intIndex < afilProfiles.length; intIndex += 1 )
	    		{
	    			m_cboSavedProfiles.AddItemToList(afilProfiles[intIndex].getName().replace(".profile", ""), 0);
	    		}
    		}
    		m_cboSavedProfiles.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnUpdateInterfaceInformation ) btnUpdateInterfaceInformation_Click( );
				else if ( aeSource.getSource( ) == m_btnExecute ) btnExecute_Click( );
				else if ( aeSource.getSource( ) == m_btnSpecifyManufacturerAddress ) btnSpecifyManufacturerAddress_Click( );
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnUpdateInterfaceInformation_Click
	// Abstract: Execute button click event
	// --------------------------------------------------------------------------------
	private void btnUpdateInterfaceInformation_Click( )
	{
		try
		{
			String strSelectedInterface = m_aclsNetworkInterfaces[m_cboInterfaces.GetSelectedItem().GetValue()].GetInterface();
			CMACChangerProcess clsMACChanger = new CMACChangerProcess();
			String astrResult[] = clsMACChanger.GetInterfaceMACAndManufacturer(strSelectedInterface);
			if (astrResult != null)
			{
				m_lblCurrentMACAddress.setText(m_strCURRENT_MAC_ADDRESS_HEADER + astrResult[0]);
				m_lblCurrentManufacturer.setText(m_strCURRENT_MANUFACTURER_HEADER + astrResult[1]);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnExecute_Click
	// Abstract: Execute button click event
	// --------------------------------------------------------------------------------
	private void btnExecute_Click( )
	{
		try
		{
			String strInterface = m_aclsNetworkInterfaces[m_cboInterfaces.GetSelectedItem().GetValue()].GetInterface();
			String strFullAddress = "";
			CNetworkInterface clsInterface = new CNetworkInterface( strInterface );
			
			if ( m_rdbSpecifyFullAddress.isSelected( ) == true )
			{
				for ( int intIndex = 0; intIndex < m_atxtMACFullAddress.length; intIndex += 1)
				{
					if ( strFullAddress != "" )
						strFullAddress += ":";
					strFullAddress += m_atxtMACFullAddress[intIndex].getText();
				}
				
				if (CAircrackUtilities.ValidMACAddress(strFullAddress))
					clsInterface.SetMACAddress(strFullAddress);
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid MAC Address");
					return;
				}
			}
			else if ( m_rdbUseRandomMACAddress.isSelected( ) == true )
				clsInterface.RandomizeMACAddress();
			
			btnUpdateInterfaceInformation_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSpecifyManufacturerAddress_Click
	// Abstract: Launches a dialog to select a MAC prefix from a specific vendor
	// --------------------------------------------------------------------------------
	private void btnSpecifyManufacturerAddress_Click( )
	{
		try
		{
			String strManufacturerPrefix = "";
			String astrPrefixSections[] = null;
			DSpecifyManufacturer dlgSpecifyManufacturer = new DSpecifyManufacturer( );
			dlgSpecifyManufacturer.setVisible( true );
			strManufacturerPrefix = dlgSpecifyManufacturer.GetManufacturerPrefix( );
			if ( strManufacturerPrefix.equals("") == false )
			{
				astrPrefixSections = strManufacturerPrefix.split(":");
				for ( int intIndex = 0; intIndex < 3; intIndex += 1 )
					m_atxtMACFullAddress[intIndex].setText(astrPrefixSections[intIndex]);
				m_rdbSpecifyFullAddress.setSelected( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: changedUpdate
	// Abstract: Fires whenever a textbox is updated
	// --------------------------------------------------------------------------------
	@Override
	public void changedUpdate(DocumentEvent deSource)
	{
		try
		{
			UpdateFullMACAddressTextBoxes(deSource);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: UpdateFullMACAddressTextBoxes
	// Abstract: If the length entered into a textbox is 2, move to the next textbox.
	//			 If it's on the last textbox, shift focus to the execute button.
	// --------------------------------------------------------------------------------
	private void UpdateFullMACAddressTextBoxes(DocumentEvent deSource)
	{
		try
		{
			
			for ( int intIndex = 0; intIndex < m_atxtMACFullAddress.length; intIndex += 1 )
			{
				if ( deSource.getDocument() == m_atxtMACFullAddress[intIndex].getDocument() && intIndex != 5 && m_atxtMACFullAddress[intIndex].getText().length() == 2 )
				{
					m_atxtMACFullAddress[intIndex + 1].requestFocusInWindow( );
					m_atxtMACFullAddress[intIndex + 1].selectAll();
				}
				else if ( deSource.getDocument( ) == m_atxtMACFullAddress[intIndex].getDocument( ) && intIndex == 5 && m_atxtMACFullAddress[intIndex].getText().length() == 2 )
				{
					m_btnExecute.requestFocusInWindow( );
				}
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: insertUpdate
	// Abstract: Fired whenever text is added to a textbox
	// --------------------------------------------------------------------------------
	@Override
	public void insertUpdate(DocumentEvent deSource)
	{
		try
		{
			UpdateFullMACAddressTextBoxes(deSource);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: removeUpdate
	// Abstract: Fired whenever text is removed from a textbox
	// --------------------------------------------------------------------------------	
	@Override
	public void removeUpdate(DocumentEvent deSource)
	{
		try
		{
			UpdateFullMACAddressTextBoxes(deSource);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetCustomMACAddress
	// Abstract: Sets the text of each textbox according to a provided MAC address.
	//			 Used by Discover Networks and Discover Hosts.
	// --------------------------------------------------------------------------------
	public void SetCustomMACAddress(String strMACAddress)
	{
		try
		{
			String astrHexadecimalOctets[] = strMACAddress.split(":");
			for ( int intIndex = 0; intIndex < astrHexadecimalOctets.length; intIndex += 1 )
			{
				m_atxtMACFullAddress[intIndex].setText(astrHexadecimalOctets[intIndex]);
			}
			m_rdbSpecifyFullAddress.setSelected( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

}
