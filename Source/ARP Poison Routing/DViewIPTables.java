// --------------------------------------------------------------------------------
// Name: DViewIPTables
// Abstract: Allows user to view and edit IP table rules
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

public class DViewIPTables extends CAircrackDialog
{
	protected final static long serialVersionUID = 1L;
	private JLabel m_lblPolicy = null;
	private CTable m_tblPolicies = null;
	private JButton m_btnDeletePolicy = null;
	private JButton m_btnOK = null;
	private final String m_astrTABLE_COLUMNS[] = new String[] {"NUM", "TARGET", "PROT", "OPT", "SOURCE", "DESTINATION", "RULE"};
	
	// --------------------------------------------------------------------------------
	// Name: DViewIPTables
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DViewIPTables( )
	{
		super("View IP Tables", 550, 410, false, false, "");
		try
		{
			AddControls( );
			RunIPTablesAndPopulateData( );
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
            
            m_lblPolicy = CUtilities.AddLabel(conControlArea, "CHAIN", 5, 5);
        	m_tblPolicies = CUtilities.AddTable(conControlArea, m_astrTABLE_COLUMNS, null, 25, 5, 300, 530);
        	m_btnDeletePolicy = CUtilities.AddButton(conControlArea, this, "Delete", 330, 434, 18, 100);
        	m_btnOK = CUtilities.AddButton(conControlArea, this, "OK", 360, getWidth( ) / 2 - 100 / 2, 18, 100);
   		}
   		catch (Exception excError)
   		{
   			CUtilities.WriteLog(excError);
   		}
	}

	// --------------------------------------------------------------------------------
	// Name: RunIPTablesAndPopulateData
	// Abstract: Runs the IP tables command to get all iptable rules against the FORWARD
	//			 policy.
	// --------------------------------------------------------------------------------
	private void RunIPTablesAndPopulateData( )
	{
		try
		{
			String astrCommand[] = new String[] {"iptables", "-L", "FORWARD", "-n", "--line-numbers"};
			CProcess clsIPTables = new CProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader( clsIPTables.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			m_lblPolicy.setText(strBuffer);
			brOutput.readLine( );
			strBuffer = brOutput.readLine( );
			
			String strEntryNumber = "";
			String strTarget = "";
			String strProtocol = "";
			String strOptions = "";
			String strSource = "";
			String strDestination = "";
			String strRule = "";
			
			while ( strBuffer != null )
			{
				strEntryNumber = strBuffer.substring(0, 5).trim( );
				strTarget = strBuffer.substring(5, 16).trim( );
				strProtocol = strBuffer.substring(16, 21).trim( );
				strOptions = strBuffer.substring(21, 25).trim( );
				strSource = strBuffer.substring(25, 46).trim( );
				strDestination = strBuffer.substring(46, 66).trim( );
				strRule = strBuffer.substring(66).trim( );
				
				m_tblPolicies.AddRow(new String[] {strEntryNumber, strTarget, strProtocol, strOptions, strSource, strDestination, strRule});
				strBuffer = brOutput.readLine( );
			}
			
			clsIPTables.CloseProcess();
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
			if ( aeSource.getSource( ) == m_btnDeletePolicy )		btnDeletePolicy_Click( );
			else if ( aeSource.getSource( ) == m_btnOK )			btnOK_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnDeletePolicy_Click
	// Abstract: Deletes the selected policy
	// --------------------------------------------------------------------------------
	private void btnDeletePolicy_Click( )
	{
		try
		{
			int intSelectedRow = m_tblPolicies.GetSelectedRow( );
			String astrCommand[] = null;
			int intPolicyNumber = -1;
			if ( intSelectedRow > -1 )
			{
				intPolicyNumber = Integer.parseInt(String.valueOf(m_tblPolicies.GetCellValue(intSelectedRow, "NUM")));
				astrCommand = new String[] {"iptables", "-D", "FORWARD", String.valueOf(intPolicyNumber)}; 
				CProcess clsDeletePolicy = new CProcess(astrCommand, true, true, true);
				clsDeletePolicy.CloseProcess();
				m_tblPolicies.ClearRows( );
				RunIPTablesAndPopulateData( );
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No policy selected to delete.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnOK_Click
	// Abstract: Closes the window
	// --------------------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			setVisible( false );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
