// --------------------------------------------------------------------------------
// Name: DSpecifyManufacturer
// Abstract: Allows user to specify a manufacturer for the MAC address
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DSpecifyManufacturer extends CAircrackDialog implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	private String m_strManufacturerPrefix = "";
	
	private CTextBox m_txtManufacturerName = null;
	private JButton m_btnSearch = null;
	
	private CListBox m_lstManufacturer = null;
	
	private JButton m_btnOK = null;
	
	// --------------------------------------------------------------------------------
	// Name: DSpecifyManufacturer
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DSpecifyManufacturer( )
	{
		super("Specify Manufacturer - MAC Address", 430, 310, false, false, "");
		try
		{
			AddControls( );
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
            
            CUtilities.AddLabel(conControlArea, "Search: ", 7, 5);
        	m_txtManufacturerName = CUtilities.AddTextBox(conControlArea, null, "", 25, 100, 5, 65);
        	m_btnSearch = CUtilities.AddButton(conControlArea, this, "Go", 5, 345, 18, 60);
        	
        	CUtilities.AddLabel(conControlArea, "Manufacturers:", 30, 5);
        	m_lstManufacturer = CUtilities.AddListBox(conControlArea, null, 50, 5, 200, 405);
        	
        	m_btnOK = CUtilities.AddButton(conControlArea, this, "OK", 255, getWidth() / 2 - 75 / 2, 18, 75);
        	
        	m_lstManufacturer.SetSorted( false );
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
			if ( aeSource.getSource( ) == m_btnSearch )		btnSearch_Click( );
			else if ( aeSource.getSource( ) == m_btnOK )	btnOK_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSearch_Click
	// Abstract: Performs a search for manufacturers with the specified name.
	// --------------------------------------------------------------------------------
	private void btnSearch_Click( )
	{
		try
		{
			boolean blnContinueSearch = true;
			boolean blnNonWirelessMACs = true;
			BufferedReader brOutput = null;
			String strBuffer = "";
			
			m_lstManufacturer.Clear( );
			
			if ( m_txtManufacturerName.getText().trim().equals("") == true )
			{
				blnContinueSearch = CAircrackUtilities.ConvertIntegerToBoolean(JOptionPane.showConfirmDialog(null, "You are doing an empty search that will pull back all manufacturers. This could take a while. Do you want to continue?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION));
				blnContinueSearch = !blnContinueSearch; // OK is 0, No is 1. Need to flip the boolean
			}
			
			if ( blnContinueSearch )
			{
				CMACChangerProcess clsMACChangerSearch = new CMACChangerProcess();
				brOutput = new BufferedReader(clsMACChangerSearch.GetOutput());
				strBuffer = brOutput.readLine( );
				
				while ( strBuffer != null )
				{
					// No need for extra spaces
					strBuffer = strBuffer.trim( );
					
					// Non-wireless interface cards
					if ( strBuffer.equals("Misc MACs:") )
					{
						blnNonWirelessMACs = true;
						brOutput.readLine( ); // Column Headers
						brOutput.readLine( ); // Line separators
					}
					
					// Wireless interface cards
					if ( strBuffer.equals("Wireless MACs:") )
					{
						blnNonWirelessMACs = false;
						brOutput.readLine( ); // Column Headers
						brOutput.readLine( ); // Line separators
					}
					
					// Add the MAC to the list
					if ( strBuffer.equals("Misc MACs:") == false && strBuffer.equals("Wireless MACs:") == false )
					{
						strBuffer = strBuffer.substring( strBuffer.indexOf("-") + 1 );
						strBuffer = strBuffer.trim( );
						if ( blnNonWirelessMACs == false )
							strBuffer += " (wireless)";
						m_lstManufacturer.AddItemToList(strBuffer, 0);
					}
					
					strBuffer = brOutput.readLine( );
				}
				
				clsMACChangerSearch.CloseProcess( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnOK_Click
	// Abstract: Closes the form with the selected interface stored.
	// --------------------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			if ( m_lstManufacturer.GetSelectedIndex( ) == -1 )
			{
				JOptionPane.showMessageDialog(null, "No manufacturer selected.");
			}
			else
			{
				m_strManufacturerPrefix = m_lstManufacturer.GetSelectedItemName( );
				m_strManufacturerPrefix = m_strManufacturerPrefix.substring( 0, m_strManufacturerPrefix.indexOf("-") );
				m_strManufacturerPrefix = m_strManufacturerPrefix.trim( );
				setVisible( false );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetManufacturerPrefix
	// Abstract: Returns the selected prefix to an outside form.
	// --------------------------------------------------------------------------------
	public String GetManufacturerPrefix( )
	{
		return m_strManufacturerPrefix;
	}

}
