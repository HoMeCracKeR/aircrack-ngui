// --------------------------------------------------------------------------------
// Name: DSelectStation
// Abstract: Allows you to select a station and return either the MAC address or
//			 IP address
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DSelectStation extends CAircrackDialog
{
	protected final static long serialVersionUID = 1L;
	private udtReturnType m_udtReturnType = udtReturnType.RETURN_TYPE_MAC_ADDRESS;
	private CComboBox m_cboInterface = null;
	private JButton m_btnScan = null;
	private CTable m_tblStations = null;
	private JButton m_btnOK = null;
	private String m_strReturnValue;
	private final String m_astrSTATIONS_TABLE_COLUMNS[] = new String[] {"IP Address", "MAC Address", "Manufacturer"};
	
	// --------------------------------------------------------------------------------
	// Name: udtReturnType
	// Abstract: Specifies the return type for the dialog
	// --------------------------------------------------------------------------------
	public enum udtReturnType
	{
		RETURN_TYPE_MAC_ADDRESS,
		RETURN_TYPE_IP_ADDRESS
	}
	
	// --------------------------------------------------------------------------------
	// Name: DSelectNetwork
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DSelectStation( )
	{
		super("Select Station", 450, 380, false, false, "");
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
            
        	CUtilities.AddLabel(conControlArea, "Interface:", 7, 5);
        	m_cboInterface = CUtilities.AddComboBox(conControlArea, null, 5, 80, 18, 75);
        	m_btnScan = CUtilities.AddButton(conControlArea, this, "Scan", 5, 325, 18, 105);
        	m_tblStations = CUtilities.AddTable(conControlArea, m_astrSTATIONS_TABLE_COLUMNS, null, 25, 5, 300, 425);
        	m_tblStations.SetReadOnly( true );
        	m_btnOK = CUtilities.AddButton(conControlArea, this, "OK", 330, 175, 18, 100);
        	
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the available interfaces to scan on
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces();
			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				m_cboInterface.AddItemToList(aclsInterfaces[intIndex].GetInterface(), 0);
			}
			m_cboInterface.SetSelectedIndex( 0 );
			CAircrackUtilities.SetComboBoxSelectedValue( m_cboInterface, CUserPreferences.GetPreferredStandardInterface( ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_btnScan )			btnScan_Click( );
			else if ( aeSource.getSource( ) == m_btnOK )		btnOK_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnScan_Click
	// Abstract: Scan button Click event
	// --------------------------------------------------------------------------------
	private void btnScan_Click( )
	{
		try
		{
			String strInterfaceName = m_cboInterface.GetSelectedItemName();
			CNetworkInterface clsInterface = new CNetworkInterface(strInterfaceName);
			String strInterfaceIPAddress = clsInterface.GetIPAddress();
			String strSubnetMask = clsInterface.GetSubnetMask();
			String strScanRange = CAircrackUtilities.CalculateScanTarget(strInterfaceIPAddress, strSubnetMask);
			String astrCommand[] = new String[] {"RequiredScripts/OnlyFindHostsNoPorts.sh", strInterfaceName, strScanRange};
			CProcess clsFindHosts = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsFindHosts.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			String strIPAddress = "";
			String strMACAddress = "";
			String strManufacturer = "";
			
			m_tblStations.ClearRows( );
			
			while ( strBuffer != null )
			{
				if ( strBuffer.equals( "" ) == false )
				{
					if ( strBuffer.contains("Nmap scan report for") && strIPAddress.equals("") == false )
					{
						m_tblStations.AddRow(new String[] {strIPAddress, strMACAddress, strManufacturer});
						strIPAddress = "";
						strMACAddress = "";
						strManufacturer = "";
					}
					
					if ( strBuffer.contains("Nmap scan report for") )
						strIPAddress = strBuffer.substring(strBuffer.lastIndexOf(" ") + 1);
					
					if ( strBuffer.contains("MAC Address:") )
					{
						strMACAddress = strBuffer.replace("MAC Address:", "").trim();
						strMACAddress = strMACAddress.substring(0, strMACAddress.indexOf("(")).trim();
						
						strManufacturer = strBuffer.substring(strBuffer.indexOf("(") + 1);
						strManufacturer = strManufacturer.substring(0, strManufacturer.lastIndexOf(")"));
					}
				}
				strBuffer = brOutput.readLine( );
			}
			if ( strIPAddress.equals("") == false )
				m_tblStations.AddRow(new String[] {strIPAddress, strMACAddress, strManufacturer});
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnOK_Click
	// Abstract: Returns the requested data to the calling form
	// --------------------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			String strReturnValue = "";
			
			if ( m_tblStations.GetSelectedRow( ) == -1 )
			{
				JOptionPane.showMessageDialog(null, "No station was selected. Please select a station.");
			}
			else
			{
				switch (m_udtReturnType)
				{
					case RETURN_TYPE_MAC_ADDRESS:
						strReturnValue = String.valueOf(m_tblStations.GetCellValue(m_tblStations.GetSelectedRow( ), "MAC Address"));
						break;
					case RETURN_TYPE_IP_ADDRESS:
						strReturnValue = String.valueOf(m_tblStations.GetCellValue(m_tblStations.GetSelectedRow( ), "IP Address"));
						break;
				}
				m_strReturnValue = strReturnValue;
				dispose( ); // Close the form
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetReturnType
	// Abstract: Sets the type of data to return from this dialog
	// --------------------------------------------------------------------------------
	public void SetReturnType(udtReturnType udtNewReturnType)
	{
		try
		{
			m_udtReturnType = udtNewReturnType;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetSelectedStation
	// Abstract: Allows the outside form to get the selected value
	// --------------------------------------------------------------------------------
	public String GetSelectedStation( )
	{
		return m_strReturnValue;
	}
	
}
