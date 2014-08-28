// --------------------------------------------------------------------------------
// Name: FGraphNetwork
// Abstract: Allows you to create a graphical representation of a network scan
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FGraphNetwork extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CTextBox m_txtInputFile = null;
	private JButton m_btnInputFileBrowse = null;
	private CTextBox m_txtOutputFile = null;
	private JButton m_btnOutputFileBrowse = null;
	private CComboBox m_cboGraphType = null;
	private JButton m_btnStart = null;
	
	// --------------------------------------------------------------------------------
	// Name: FGraphNetwork
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FGraphNetwork()
	{
		super("Graph Network", 450, 135, false, false, "");
		try
		{
			AddControls( );
			PopulateGraphTypes( );
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
            
            CUtilities.AddLabel(conControlArea, "Input File (.csv):", 5, 5);
        	m_txtInputFile = CUtilities.AddTextBox(conControlArea, null, "", 15, 100, 5, 140);
        	m_btnInputFileBrowse = CUtilities.AddButton(conControlArea, this, "...", 5, 310, 18, 50);
        	CUtilities.AddLabel(conControlArea, "Output File (.png):", 30, 5);
        	m_txtOutputFile = CUtilities.AddTextBox(conControlArea, null, "", 15, 100, 29, 140);
        	m_btnOutputFileBrowse = CUtilities.AddButton(conControlArea, this, "...", 29, 310, 18, 50);
        	CUtilities.AddLabel(conControlArea, "Graph Type:", 55, 5);
        	m_cboGraphType = CUtilities.AddComboBox(conControlArea, null, 54, 140, 18, 240);
        	m_btnStart = CUtilities.AddButton(conControlArea, this, "Start", 80, 175, 18, 100);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: PopulateGraphTypes
	// Abstract: Populates the types of graphs you can make
	// --------------------------------------------------------------------------------
	private void PopulateGraphTypes( )
	{
		try
		{
			m_cboGraphType.AddItemToList("CAPR (Client/AP Relationship)", 0);
			m_cboGraphType.AddItemToList("CPG (Common Probe Graph)", 0);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
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
			if ( aeSource.getSource( ) == m_btnInputFileBrowse ) CAircrackUtilities.DisplayFileChooser(m_txtInputFile, this);
			else if ( aeSource.getSource( ) == m_btnOutputFileBrowse ) CAircrackUtilities.DisplayFileChooser(m_txtOutputFile, this);
			else if ( aeSource.getSource( ) == m_btnStart ) btnStart_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Start button click event. Starts the creation of the image file.
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			
			String strErrors = "";
			boolean blnValidated = true;
			if ( m_txtInputFile.getText().trim().equals("") == true )
			{
				strErrors += "Input file cannot be blank.\n";
				blnValidated = false;
			}
			if ( m_txtOutputFile.getText().trim().equals("") == true )
			{
				strErrors += "Output file cannot be blank.\n";
				blnValidated = false;
			}
			
			if ( blnValidated )
			{
				String astrArguments[] = BuildCommandFromForm( );
				CProcess clsAirgraphNG = new CProcess(astrArguments, true, true, true);
				clsAirgraphNG.CloseProcess();
				Desktop.getDesktop().open(new File(m_txtOutputFile.getText().trim()));
			}
			else
			{
				JOptionPane.showMessageDialog(null, strErrors);
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: BuildCommandFromForm
	// Abstract: Builds the airgraph-ng command from the populated form elements
	// --------------------------------------------------------------------------------
	private String[] BuildCommandFromForm( )
	{
		String astrArguments[] = null;
		try
		{
			astrArguments = new String[] {"airgraph-ng"};
			astrArguments = CAircrackUtilities.AddArgumentToCommand("i", m_txtInputFile.getText().trim(), astrArguments);
			astrArguments = CAircrackUtilities.AddArgumentToCommand("o", m_txtOutputFile.getText().trim(), astrArguments);
			if ( m_cboGraphType.GetSelectedItemName().equals("CAPR (Client/AP Relationship)") == true )
				astrArguments = CAircrackUtilities.AddArgumentToCommand("g", "capr", astrArguments);
			else if ( m_cboGraphType.GetSelectedItemName().equals("CPG (Common Probe Graph)") == true )
				astrArguments = CAircrackUtilities.AddArgumentToCommand("g", "cpg", astrArguments);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrArguments;
	}
	
}
