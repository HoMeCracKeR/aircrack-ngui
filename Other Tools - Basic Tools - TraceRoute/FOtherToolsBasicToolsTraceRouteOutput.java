// --------------------------------------------------------------------------------
// Name: FOtherToolsBasicToolsTraceRouteOutput
// Abstract: Displays the traceroute output in a nice table
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FOtherToolsBasicToolsTraceRouteOutput extends CAircrackWindow implements ComponentListener, WindowListener
{
	protected final static long serialVersionUID = 1L;
	
	private String m_astrCommand[] = null;
	private final String m_astrHOPS_TABLE_COLUMNS[] = new String[] {"HOP", "DESTINATION"};
	
	// Controls
	private JRadioButton m_rdbViewTable = null;
	private JRadioButton m_rdbViewTextArea = null;
	private CTable m_tblHops = null;
	private JTextArea m_txaProgramOutput = null;
	private JScrollPane m_scpProgramOutput = null;
	private JButton m_btnSaveToFile = null;
	private CProcess m_clsTraceRoute = null;
	private BufferedReader m_brTraceRouteOutput = null;
	private int m_intTraceRouteProcessID = -1;
	private java.util.Timer m_timPopulateTraceRouteData = null;
	private String m_strIPAddressOfTarget = "";
	
	private JPopupMenu m_pumAttackMenu = null;
	private JMenuItem m_miScanHost = null;
	private JMenuItem m_miPing = null; 
	private JMenuItem m_miTelnet = null;
	private JMenuItem m_miTraceRoute = null;
	private JMenuItem m_miWhoIs = null;
	private JMenuItem m_miNikto = null;
	private JMenuItem m_miHydra = null;
	private JMenuItem m_miSQLMap = null;
	
	
	class CPopulateTraceRouteData extends TimerTask
	{
		@Override
		public void run( )
		{
			try
			{
				if ( m_brTraceRouteOutput != null )
				{
					if ( m_brTraceRouteOutput.ready( ) )
					{
						String strBuffer = m_brTraceRouteOutput.readLine( );
						String strHopNumber = "";
						String strHopDestination = "";
						
						if ( strBuffer.contains("traceroute to " + m_astrCommand[m_astrCommand.length - 1]) )
						{
							m_strIPAddressOfTarget = strBuffer.substring(strBuffer.indexOf("(") + 1, strBuffer.indexOf(")"));
							strBuffer = m_brTraceRouteOutput.readLine( );
						}
						
						while ( strBuffer != null )
						{
							m_txaProgramOutput.setText(m_txaProgramOutput.getText() + strBuffer + "\r\n");
							strHopNumber = strBuffer.substring(0, strBuffer.indexOf("  "));
							if ( strBuffer.contains("*") == false )
								strHopDestination = strBuffer.substring(strBuffer.indexOf("  ") + 2, strBuffer.indexOf("  ", strBuffer.indexOf("  ") + 2));
							else
								strHopDestination = "";
							
							m_tblHops.AddRow(new String[] {strHopNumber, strHopDestination});
							if ( strBuffer.contains(m_strIPAddressOfTarget) )
								JOptionPane.showMessageDialog(null, "Trace route completed!");
							strBuffer = m_brTraceRouteOutput.readLine( );
						}
					}
				}
				m_timPopulateTraceRouteData.schedule(new CPopulateTraceRouteData( ), 500);
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: FOtherToolsBasicToolsTraceRouteOutput
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FOtherToolsBasicToolsTraceRouteOutput( )
	{
		super("Trace Route - Output", 600, 515, false, false, "");
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

            ButtonGroup bgpViewTypes = new ButtonGroup( );
            
            m_rdbViewTable = CUtilities.AddRadioButton(conControlArea, this, bgpViewTypes, "View Table", 5, 5);
        	m_rdbViewTextArea = CUtilities.AddRadioButton(conControlArea, this, bgpViewTypes, "View TextArea", 5, 200);
            
        	m_tblHops = CUtilities.AddTable(conControlArea, m_astrHOPS_TABLE_COLUMNS, null, 35, 5, 425, 585);
        	m_tblHops.SetColumnWidth("DESTINATION", 800);
        	
            m_txaProgramOutput = new JTextArea( 27, 39 );
            m_txaProgramOutput.setLineWrap( false );
            m_scpProgramOutput = new JScrollPane(m_txaProgramOutput);
			conControlArea.add( m_scpProgramOutput );
			splFrame.getConstraints( m_scpProgramOutput ).setX( Spring.constant( 5 ) );
            splFrame.getConstraints( m_scpProgramOutput ).setY( Spring.constant( 35 ) );
            splFrame.getConstraints( m_scpProgramOutput ).setWidth( Spring.constant(585) );
            splFrame.getConstraints( m_scpProgramOutput ).setHeight( Spring.constant(425) );
            m_scpProgramOutput.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
            m_scpProgramOutput.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
            
            m_btnSaveToFile = CUtilities.AddButton(conControlArea, this, "Save To File", 462, 470, 18, 120);
            
            m_scpProgramOutput.setVisible( false );
            m_tblHops.SetVisible( false );
            
            ChangeOutputDisplay( true, false );
            
            m_rdbViewTable.setSelected( true );
            addComponentListener(this);
            
            m_pumAttackMenu = new JPopupMenu( );
            m_miScanHost = CAircrackUtilities.CreateMenuItem("Scan Host", m_pumAttackMenu, this);
        	m_miPing = CAircrackUtilities.CreateMenuItem("Ping", m_pumAttackMenu, this); 
        	m_miTelnet = CAircrackUtilities.CreateMenuItem("Telnet", m_pumAttackMenu, this);
        	m_miTraceRoute = CAircrackUtilities.CreateMenuItem("Trace Route", m_pumAttackMenu, this);
        	m_miWhoIs = CAircrackUtilities.CreateMenuItem("Who Is", m_pumAttackMenu, this);
        	m_pumAttackMenu.addSeparator( );
        	m_miNikto = CAircrackUtilities.CreateMenuItem("Nikto", m_pumAttackMenu, this);
        	m_miHydra = CAircrackUtilities.CreateMenuItem("Hydra", m_pumAttackMenu, this);
        	m_miSQLMap = CAircrackUtilities.CreateMenuItem("SQL Map", m_pumAttackMenu, this);
            m_tblHops.AddPopupMenu(m_pumAttackMenu);
            
            m_miHydra.setEnabled( false );
            m_miSQLMap.setEnabled( false );
            
            m_timPopulateTraceRouteData = new java.util.Timer( );
            m_timPopulateTraceRouteData.schedule(new CPopulateTraceRouteData( ), 500);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ChangeOutputDisplay
	// Abstract: Switches between displaying the table and displaying the textarea
	// --------------------------------------------------------------------------------
	private void ChangeOutputDisplay( boolean blnDisplayTable, boolean blnDisplayTextArea )
	{
		try
		{
			m_tblHops.SetVisible( blnDisplayTable );
			m_txaProgramOutput.setVisible( blnDisplayTextArea );
			m_scpProgramOutput.setVisible( blnDisplayTextArea );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetCommand
	// Abstract: Sets the traceroute command
	// --------------------------------------------------------------------------------
	public void SetCommand(String astrCommand[])
	{
		try
		{
			m_astrCommand = astrCommand;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Context menu item click event
	// --------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_rdbViewTable )				ChangeOutputDisplay(true, false);
			else if ( aeSource.getSource( ) == m_rdbViewTextArea )		ChangeOutputDisplay(false, true);
			else if ( aeSource.getSource( ) == m_miScanHost )			miScanHost_Click( );
			else if ( aeSource.getSource( ) == m_miPing )				miPing_Click( );
			else if ( aeSource.getSource( ) == m_miTelnet )				miTelnet_Click( );
			else if ( aeSource.getSource( ) == m_miTraceRoute )			miTraceRoute_Click( );
			else if ( aeSource.getSource( ) == m_miWhoIs )				miWhoIs_Click( );
			else if ( aeSource.getSource( ) == m_miNikto )				miNikto_Click( );
			else if ( aeSource.getSource( ) == m_btnSaveToFile )		btnSaveToFile_Click( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miScanHost_Click
	// Abstract: Opens a new Discover Hosts window with the target pointed at this IP
	// --------------------------------------------------------------------------------
	private void miScanHost_Click( )
	{
		try
		{
			int intSelectedRow = m_tblHops.GetSelectedRow( );
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(intSelectedRow, "DESTINATION"));
			if ( strTargetIPAddress.contains("(") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));
			
			FDiscoverHosts frmDiscoverHosts = new FDiscoverHosts( );
			frmDiscoverHosts.SetDestination( strTargetIPAddress );
			frmDiscoverHosts.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miPing_Click
	// Abstract: Opens a new Ping window with the target specified
	// --------------------------------------------------------------------------------
	private void miPing_Click( )
	{
		try
		{
			int intSelectedRow = m_tblHops.GetSelectedRow();
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(intSelectedRow, "DESTINATION"));
			if ( strTargetIPAddress.contains("(") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));

			FOtherToolsBasicToolsPing frmPing = new FOtherToolsBasicToolsPing( );
			frmPing.SetDestination( strTargetIPAddress );
			frmPing.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miTelnet_Click
	// Abstract: Opens a new Telnet window with the target specified
	// --------------------------------------------------------------------------------
	private void miTelnet_Click( )
	{
		try
		{
			int intSelectedRow = m_tblHops.GetSelectedRow( );
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(intSelectedRow, "DESTINATION"));
			if ( strTargetIPAddress.contains("(") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));
			
			FOtherToolsBasicToolsTelnet frmTelnet = new FOtherToolsBasicToolsTelnet( );
			frmTelnet.SetDestination( strTargetIPAddress );
			frmTelnet.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miTraceRoute_Click
	// Abstract: Opens a new Trace Route window with the target specified
	// --------------------------------------------------------------------------------
	private void miTraceRoute_Click( )
	{
		try
		{
			int intSelectedRow = m_tblHops.GetSelectedRow();
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(intSelectedRow, "DESTINATION"));
			if ( strTargetIPAddress.contains("(") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));

			FOtherToolsBasicToolsTraceRoute frmTraceRoute = new FOtherToolsBasicToolsTraceRoute( );
			frmTraceRoute.SetDestination( strTargetIPAddress );
			frmTraceRoute.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Context menu item click event
	// --------------------------------------------------------------------------------
	private void miWhoIs_Click( )
	{
		try
		{
			int intSelectedRow = m_tblHops.GetSelectedRow();
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(intSelectedRow, "DESTINATION"));
			if ( strTargetIPAddress.contains("(") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));
			
			FOtherToolsBasicToolsWhoIs frmWhoIs = new FOtherToolsBasicToolsWhoIs( );
			frmWhoIs.SetDestination( strTargetIPAddress );
			frmWhoIs.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// ----------------------------------------------------------------------------
    // Name: miNikto_Click
    // Abstract: Opens a new Nikto window with the selected IP address targeted
    // ----------------------------------------------------------------------------
	private void miNikto_Click( )
	{
		try
		{
			String strTargetIPAddress = String.valueOf(m_tblHops.GetCellValue(m_tblHops.GetSelectedRow(), "DESTINATION"));
			if ( strTargetIPAddress.contains(")") && strTargetIPAddress.contains(")") )
				strTargetIPAddress = strTargetIPAddress.substring(strTargetIPAddress.indexOf("(") + 1, strTargetIPAddress.indexOf(")"));			
			FOtherToolsNikto frmNikto = new FOtherToolsNikto( );
			frmNikto.AddHost( strTargetIPAddress );
			frmNikto.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// ----------------------------------------------------------------------------
    // Name: btnSaveToFile_Click
    // Abstract: Saves the output to a file (if table view, HTML file. If textarea,
	//			 text file).
    // ----------------------------------------------------------------------------
	private void btnSaveToFile_Click( )
	{
		try
		{
			if ( m_rdbViewTable.isSelected( ) == true )
			{
				// Generate the HTML file
				String strFileOutput = "";
				String strCurrentLine = "";
				strFileOutput = "<table border=\"0\">";
				strCurrentLine = "<tr>";
				for ( int intIndex = 0; intIndex < m_tblHops.GetColumnCount( ); intIndex += 1 )
					strCurrentLine += "<td>" + m_tblHops.GetColumnName(intIndex) + "</td>";
				strCurrentLine += "</tr>";
				strFileOutput += strCurrentLine;
				strCurrentLine = "";
				
				for ( int intIndex = 0; intIndex < m_tblHops.GetRowCount( ); intIndex += 1 )
				{
					strCurrentLine += "<tr>";
					for ( int intIndex2 = 0; intIndex2 < m_tblHops.GetColumnCount( ); intIndex2 += 1 )
						strCurrentLine += "<td>" + m_tblHops.GetCellValue(intIndex, intIndex2) + "</td>";
					strCurrentLine += "</tr>";
					
					strFileOutput += strCurrentLine;
					strCurrentLine = "";
				}
				strFileOutput += "</table>";
				
				String strDestinationFile = CAircrackUtilities.DisplayFileChooser(this, false, ".html", "HyperText Markup Language (HTML) file (*.html)");
				if ( strDestinationFile.equals("") == false )
				{
					if ( CAircrackUtilities.SaveTextToFile(strFileOutput, strDestinationFile) == true )
						JOptionPane.showMessageDialog(null, "File successfully saved.");
				}						
			}
			else if ( m_rdbViewTextArea.isSelected( ) == true )
			{
				// Generate the text file
				String strDestinationFile = CAircrackUtilities.DisplayFileChooser(this, false, ".txt", "Text file (*.txt)");
				if ( strDestinationFile.equals("") == false )
				{
					if ( CAircrackUtilities.SaveTextToFile(m_txaProgramOutput.getText( ), strDestinationFile) == true )
						JOptionPane.showMessageDialog(null, "File successfully saved.");
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: componentShown
	// Abstract: When the window is set to visible, run the trace route
	// --------------------------------------------------------------------------------
	public void componentShown(ComponentEvent ceSource)
	{
		try
		{
			StartTraceRoute( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: StartTraceRoute
	// Abstract: Starts the trace route to the target
	// -------------------------------------------------------------------------------
	private void StartTraceRoute( )
	{
		try
		{
			m_clsTraceRoute = new CProcess(m_astrCommand, true, false, true);
			m_brTraceRouteOutput = new BufferedReader( m_clsTraceRoute.GetOutput( ) );
			m_intTraceRouteProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess(m_astrCommand[0]);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}


	// --------------------------------------------------------------------------------
	// Name: windowClosing
	// Abstract: Kill the traceroute process
	// -------------------------------------------------------------------------------	
	public void windowClosing(WindowEvent weSource)
	{
		try
		{
			if ( CGlobals.clsLocalMachine.KillRunningProcess(m_intTraceRouteProcessID) == false )
			{
				JOptionPane.showMessageDialog(null, "Could not kill process \"" + m_astrCommand[0] + "\" (PID: " + m_intTraceRouteProcessID + "). Please manually destroy the process.");
			}
			m_clsTraceRoute.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	
	// Not used
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentResized(ComponentEvent arg0) {}
	
}
