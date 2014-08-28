// --------------------------------------------------------------------------------
// Name: DSelectNetwork
// Abstract: Displays the select network dialog
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DSelectNetwork extends CAircrackDialog
{

	// --------------------------------------------------------------------------------
	// Name: udtSelectNetworkReturnType
	// Abstract: User-defined type for what piece of network information to return,
	//			 either the network hardware address (BSSID) or the network name (ESSID)
	// --------------------------------------------------------------------------------
	public enum udtSelectNetworkReturnType
	{
		RETURN_TYPE_NETWORK_ESSID,
		RETURN_TYPE_NETWORK_BSSID
	}
	
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CComboBox m_cboInterface = null;
	private CListBox m_lstDetectedNetworks = null;
	private JButton m_btnScan = null;
	private JButton m_btnOK = null;
	
	private String m_astrNetworkESSIDs[] = null;
	private String m_astrNetworkBSSIDs[] = null;
	
	private String m_strSelectedValue = "";
	private udtSelectNetworkReturnType m_udtSelectNetworkReturnType = udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID;
	
	// --------------------------------------------------------------------------------
	// Name: DSelectNetwork
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DSelectNetwork( )
	{
		super("Select Network", 315, 325, false, false, "");
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
        	m_cboInterface = CUtilities.AddComboBox(conControlArea, null, 5, 80, 18, 150);
        	m_lstDetectedNetworks = CUtilities.AddListBox(conControlArea, null, 50, 5, 200, 300);
        	m_btnScan = CUtilities.AddButton(conControlArea, this, "Scan", 255, 229, 18, 75);
        	m_btnOK = CUtilities.AddButton(conControlArea, this, "OK", 275, 120, 18, 75);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateInterfaces
	// Abstract: Populates the interfaces to scan on.
	// --------------------------------------------------------------------------------
	private void PopulateInterfaces( )
	{
		try
		{
			CNetworkInterface aclsNetworkInterfaces[] = CGlobals.clsLocalMachine.GetAvailableNetworkInterfaces( );
			for ( int intIndex = 0; intIndex < aclsNetworkInterfaces.length; intIndex += 1 )
			{
				m_cboInterface.AddItemToList(aclsNetworkInterfaces[intIndex].GetInterface(), 0);
			}
			m_cboInterface.SetSelectedIndex( 0 );
			CAircrackUtilities.SetComboBoxSelectedValue(m_cboInterface, CUserPreferences.GetPreferredStandardInterface());
			
			if ( m_cboInterface.GetItem( 0 ).GetName( ).equals( CUserPreferences.GetPreferredStandardInterface( ) ) || m_cboInterface.GetSelectedIndex( ) > 0 )
				m_btnScan.requestFocusInWindow( );
				
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetReturnType
	// Abstract: Sets the type of data you want to return (ESSID or BSSID)
	// --------------------------------------------------------------------------------
	public void SetReturnType(udtSelectNetworkReturnType udtNewSelectNetworkReturnType)
	{
		try
		{
			m_udtSelectNetworkReturnType = udtNewSelectNetworkReturnType;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetSelectedNetwork
	// Abstract: Gets the type of data you want to return (ESSID or BSSID)
	// --------------------------------------------------------------------------------
	public String GetSelectedNetwork( )
	{
		return m_strSelectedValue;
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
			if ( aeSource.getSource( ) == m_btnScan )		btnScan_Click( );
			else if ( aeSource.getSource( ) == m_btnOK )	btnOK_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnScan_Click
	// Abstract: Scan button click event.
	// --------------------------------------------------------------------------------
	private void btnScan_Click( )
	{
		try
		{
			PerformIWListScan( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PerformIWListScan
	// Abstract: Performs an IW list scan
	// --------------------------------------------------------------------------------
	private void PerformIWListScan( )
	{
		try
		{
			String strInterface = m_cboInterface.GetSelectedItem( ).GetName( );
			String astrCommand[] = new String[] {"iwlist", strInterface, "scan"};
			CProcess clsSearch = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsSearch.GetOutput( ) );
			String strOutput = brOutput.readLine( ); // Indicating the scan completed
			strOutput = brOutput.readLine( );
			String strNetworkESSID = "";
			String strNetworkBSSID = "";
			String strNetworkDisplayName = "";
			
			m_astrNetworkESSIDs = new String[0];
			m_astrNetworkBSSIDs = new String[0];
			
			while ( strOutput != null )
			{
				if ( strOutput.trim( ).indexOf( "Cell " ) == 0 && strNetworkBSSID.equals( "" ) == false )
				{
					m_astrNetworkESSIDs = CAircrackUtilities.AddArgumentToArray(strNetworkESSID, m_astrNetworkESSIDs);
					m_astrNetworkBSSIDs = CAircrackUtilities.AddArgumentToArray(strNetworkBSSID, m_astrNetworkBSSIDs);
					strNetworkESSID = "";
					strNetworkBSSID = "";
				}
				
				if ( strOutput.trim( ).indexOf( "Address: " ) >= 0 )
				{
					strNetworkBSSID = strOutput.substring(strOutput.indexOf("Address: ") + 9);
					strNetworkBSSID = strNetworkBSSID.trim();
				}
				
				if ( strOutput.trim( ).indexOf( "ESSID:" ) >= 0 )
				{
					strNetworkESSID = strOutput.substring(strOutput.indexOf("ESSID:") + 6);
					
					// Remove the quotes around the network name
					strNetworkESSID = strNetworkESSID.replaceAll("\"", "");
				}
				
				strOutput = brOutput.readLine( );
			}
			
			// The last network isn't added in this case. Add it.)
			if ( strNetworkBSSID.equals( "" ) == false )
			{
				m_astrNetworkESSIDs = CAircrackUtilities.AddArgumentToArray(strNetworkESSID, m_astrNetworkESSIDs);
				m_astrNetworkBSSIDs = CAircrackUtilities.AddArgumentToArray(strNetworkBSSID, m_astrNetworkBSSIDs);
			}
			
			for ( int intIndex = 0; intIndex < m_astrNetworkBSSIDs.length; intIndex += 1 )
			{
				if ( m_astrNetworkESSIDs[intIndex].equals("") == true )
					strNetworkDisplayName = "<hidden> - " + m_astrNetworkBSSIDs[intIndex];
				else
					strNetworkDisplayName = m_astrNetworkESSIDs[intIndex] + " - " + m_astrNetworkBSSIDs[intIndex];
				
				m_lstDetectedNetworks.AddItemToList(strNetworkDisplayName, intIndex);
			}
			
			m_lstDetectedNetworks.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			JOptionPane.showMessageDialog(null, excError.getMessage());
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnOK_Click
	// Abstract: Selects the network and closes the form
	// --------------------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			if ( m_lstDetectedNetworks.GetSelectedIndex() >= 0 )
			{
				if ( m_udtSelectNetworkReturnType == udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID )
					m_strSelectedValue = m_astrNetworkBSSIDs[m_lstDetectedNetworks.GetSelectedItem().GetValue()];
				else if ( m_udtSelectNetworkReturnType == udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_ESSID )
					m_strSelectedValue = m_astrNetworkESSIDs[m_lstDetectedNetworks.GetSelectedItem().GetValue()];
			}
			
			setVisible( false );
				
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
