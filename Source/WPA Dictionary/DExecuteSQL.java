// --------------------------------------------------------------------------------
// Name: DExecuteSQL
// Abstract: Executes any SQL statement against the SQLite database
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DExecuteSQL extends CAircrackDialog implements ActionListener
{
	
	private String m_strDictionaryLocation = "";
	
	private JTextArea m_txaOutput = null;
	private JButton m_btnClearOutput = null;
	private JTextArea m_txaInput = null;
	private JButton m_btnExecute = null;
	protected final static long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------
	// Name: DExecuteSQL
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DExecuteSQL( )
	{
		super("Execute SQL", 425, 350, false, false, "");
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
            JScrollPane scpOutputScrollPane = null;
            JScrollPane scpInputScrollPane = null;
            
            CUtilities.AddLabel(conControlArea, "Output:", 5, 5);
            m_txaOutput = new JTextArea( 8, 37 );
            scpOutputScrollPane = new JScrollPane( m_txaOutput );
            conControlArea.add(scpOutputScrollPane);
            splFrame.getConstraints( scpOutputScrollPane ).setX( Spring.constant( 5 ) );
            splFrame.getConstraints( scpOutputScrollPane ).setY( Spring.constant( 25 ) );
        	m_txaOutput.setEnabled( false ); // Read-only!
        	m_txaOutput.setBackground(Color.black); // Easier to read the disabled color text w/black bg
        	
        	CUtilities.AddLabel(conControlArea, "Query:", 155, 5);
        	m_btnClearOutput = CUtilities.AddButton(conControlArea, this, "Clear", 150, 312, 18, 100);
        	
        	m_txaInput = new JTextArea( 8, 37 );
        	scpInputScrollPane = new JScrollPane( m_txaInput );
        	conControlArea.add(scpInputScrollPane);
            splFrame.getConstraints( scpInputScrollPane ).setX( Spring.constant( 5 ) );
            splFrame.getConstraints( scpInputScrollPane ).setY( Spring.constant( 175 ) );
            m_txaInput.setBackground(Color.black); // Keep consistant with the output panel
            m_txaInput.setForeground(new Color(184, 207, 229, 255));
            
        	m_btnExecute = CUtilities.AddButton(conControlArea, this, "Execute", 300, 312, 18, 100);
            
        }
        catch ( Exception excError )
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetDictionaryLocation
	// Abstract: Sets the location of the dictionary
	// --------------------------------------------------------------------------------
	public void SetDictionaryLocation(String strNewDictionaryLocation)
	{
		try
		{
			m_strDictionaryLocation = strNewDictionaryLocation;
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
			
			if ( aeSource.getSource( ) == m_btnClearOutput )	btnClearOutput_Click( );
			else if ( aeSource.getSource( ) == m_btnExecute )	btnExecute_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnClearOutput_Click
	// Abstract: Clears the output window
	// --------------------------------------------------------------------------------
	private void btnClearOutput_Click( )
	{
		try
		{
			m_txaOutput.setText("");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnExecute_Click
	// Abstract: Execute button Click event. Executes the SQL query against the database.
	// --------------------------------------------------------------------------------
	private void btnExecute_Click( )
	{
		try
		{
			if ( m_txaInput.getText().trim().equals("") == false )
			{
				m_txaOutput.setText(m_txaOutput.getText() + ">" + m_txaInput.getText() + "\n\n");
				String astrCommands[] = new String[] {"airolib-ng", m_strDictionaryLocation, "--sql", m_txaInput.getText()};
				CProcess clsQueryDatabase = new CProcess(astrCommands, true, true, true);
				BufferedReader brOutput = new BufferedReader( clsQueryDatabase.GetOutput( ) );
				String strBuffer = brOutput.readLine( );
				
				while ( strBuffer != null )
				{
					m_txaOutput.setText(m_txaOutput.getText() + strBuffer + "\n");
					strBuffer = brOutput.readLine( );
				}
				
				clsQueryDatabase.CloseProcess();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No query provided. Please provide a query to send to the database.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
