// --------------------------------------------------------------------------------
// Name: DProgramOutput
// Abstract: Displays the output of a program
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class DProgramOutput extends CAircrackDialog implements MouseListener, KeyListener
{
	protected final static long serialVersionUID = 1L;
	private JTextArea m_txaTextArea = null;
	private JScrollPane m_scpTextArea = null;
	private JTextArea m_txaInputArea = null;
	private JScrollPane m_scpInputArea = null;
	private java.util.Timer m_timUpdateTextArea = null;
	private CProcess m_clsRunningProcess = null;
	private InputStreamReader m_isrRunningProcess = null;
	private OutputStreamWriter m_oswRunningProcess = null;
	private int m_intProcessID = -1;
	private boolean m_blnWindowClosing = false;
	private String m_strProgramName = "";
	private CDropdownButton m_ddbSaveOutput = null;
	private String m_astrCommand[] = null;
	private boolean m_blnAllowInput = false;
	private JPopupMenu m_pumTextAreaContextMenu = null;
	private JMenuItem m_miCopy = null;
	private JMenuItem m_miPaste =  null;
	private JMenuItem m_miPing = null;
	private JMenuItem m_miDig = null;
	private JMenuItem m_miTelnet = null;
	private JMenuItem m_miTraceRoute = null;
	private JMenuItem m_miWhoIs = null;
	private JMenuItem m_miARPPoisonRouting = null;
	private JMenuItem m_miDiscoverHosts = null;
	private JMenuItem m_miNikto = null;
	private JMenuItem m_miDeauthenticationAttack = null;
	private Pattern m_ptnIPAddressFormat = Pattern.compile("^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$");
	private JCheckBox m_chkSendNewline = null;
	
	// --------------------------------------------------------------------------------
	// Name: DProgramOutput
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DProgramOutput( String strTitle, String astrCommand[] )
	{
		super(strTitle, 450, 450, false, false, "");
		try
		{
			Initialize( strTitle );
			AddControls( );
			m_astrCommand = astrCommand;
			ExecuteCommand( astrCommand );
			AddTextAreaMouseListener( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: DProgramOutput
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public DProgramOutput( String strTitle, String astrCommand[], boolean blnAllowInput )
	{
		super(strTitle, 450, !blnAllowInput ? 450 : 525, false, false, "");
		try
		{
			m_blnAllowInput = blnAllowInput;
			Initialize( strTitle );
			AddControls( );
			m_astrCommand = astrCommand;
			ExecuteCommand( astrCommand );
			AddTextAreaMouseListener( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	/**
	 * Class constructor (running process already provided)
	 * @param strTitle
	 * @param prcProcess
	 * @param blnAllowInput
	 */
	public DProgramOutput(String strTitle, CProcess prcProcess, boolean blnAllowInput)
	{
		super(strTitle, 450, !blnAllowInput ? 450 : 525, false, false, "");
		try
		{
			m_blnAllowInput = blnAllowInput;
			Initialize( strTitle );
			AddControls( );
			m_clsRunningProcess = prcProcess;
			m_isrRunningProcess = m_clsRunningProcess.GetOutput( );
			m_oswRunningProcess = m_clsRunningProcess.GetInput( );
			
			if (m_isrRunningProcess == null || m_oswRunningProcess == null)
			{
				JOptionPane.showMessageDialog(null,  "No output found from process. Exited faster than window was created.", "No Output", JOptionPane.OK_OPTION);
				setVisible(false);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				return;
			}
			AddTextAreaMouseListener( );
			
			m_timUpdateTextArea = new java.util.Timer( );
			m_timUpdateTextArea.schedule( new UpdateTextArea( ), 1 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Initializes the window
	// --------------------------------------------------------------------------------
	private void Initialize( String strTitle )
	{
		try
		{
            addWindowListener(this);
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
			
            int intSaveOutputTop = 400;
            if ( m_blnAllowInput )
            	intSaveOutputTop += 70;
            
			m_txaTextArea = new JTextArea( 26, 39 );
			m_txaTextArea.setLineWrap( true );
			m_scpTextArea = new JScrollPane(m_txaTextArea);
			conControlArea.add( m_scpTextArea );
			splFrame.getConstraints( m_scpTextArea ).setX( Spring.constant( 5 ) );
            splFrame.getConstraints( m_scpTextArea ).setY( Spring.constant( 5 ) );            
            m_scpTextArea.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
            
            if ( m_blnAllowInput )
            {
            	m_txaInputArea = new JTextArea( 4, 39 );
            	m_txaInputArea.addKeyListener( this );
            	m_txaInputArea.setLineWrap( true );
            	m_scpInputArea = new JScrollPane( m_txaInputArea );
            	conControlArea.add( m_scpInputArea );
            	splFrame.getConstraints( m_scpInputArea ).setX( Spring.constant( 5 ) );
            	splFrame.getConstraints( m_scpInputArea ).setY( Spring.constant( 400 ) );
            	m_scpInputArea.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
            	
            	m_chkSendNewline = CUtilities.AddCheckBox(conControlArea, null, "Send Newline With Command", 470, 5);
            	m_chkSendNewline.setSelected( true );
            }
            
            m_ddbSaveOutput = CUtilities.AddDropdownButton(conControlArea, this, this, "Save Output", intSaveOutputTop, 325, 18, 150);
            m_ddbSaveOutput.SetMenuOptions(new String[] {"To TXT", "To HTML"});
            
            m_pumTextAreaContextMenu = new JPopupMenu( );
            m_miCopy = CAircrackUtilities.CreateMenuItem("Copy", m_pumTextAreaContextMenu, this);
            m_miPaste = CAircrackUtilities.CreateMenuItem("Paste", m_pumTextAreaContextMenu, this);
            m_pumTextAreaContextMenu.addSeparator( );
            m_miPing = CAircrackUtilities.CreateMenuItem("Ping", m_pumTextAreaContextMenu, this);
        	m_miDig = CAircrackUtilities.CreateMenuItem("Dig", m_pumTextAreaContextMenu, this);
        	m_miTelnet = CAircrackUtilities.CreateMenuItem("Telnet", m_pumTextAreaContextMenu, this);
        	m_miTraceRoute = CAircrackUtilities.CreateMenuItem("Trace Route", m_pumTextAreaContextMenu, this);
        	m_miWhoIs = CAircrackUtilities.CreateMenuItem("WhoIs", m_pumTextAreaContextMenu, this);
        	m_miARPPoisonRouting = CAircrackUtilities.CreateMenuItem("ARP Poison Routing", m_pumTextAreaContextMenu, this);
        	m_miDiscoverHosts = CAircrackUtilities.CreateMenuItem("Discover Hosts", m_pumTextAreaContextMenu, this);
        	m_miNikto = CAircrackUtilities.CreateMenuItem("Nikto", m_pumTextAreaContextMenu, this);
        	m_miDeauthenticationAttack = CAircrackUtilities.CreateMenuItem("De-authentication Attack", m_pumTextAreaContextMenu, this);
            
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ResizeForm
	// Abstract: Resizes the form to the specified size
	// --------------------------------------------------------------------------------
	public void ResizeForm( int intNewWidth, int intNewHeight )
	{
		try
		{
			setSize( intNewWidth, intNewHeight );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: ExecuteCommand
	// Abstract: Executes the specified command
	// --------------------------------------------------------------------------------
	private void ExecuteCommand( String astrCommand[] )
	{
		try
		{
			int intGrabProcessIDAttempt = 0;
			m_clsRunningProcess = new CProcess(astrCommand, true, false, true);
			m_isrRunningProcess = m_clsRunningProcess.GetOutput( );
			m_oswRunningProcess = m_clsRunningProcess.GetInput( );
			m_strProgramName = astrCommand[0];
			if ( m_strProgramName.indexOf("/") >= 0 )
				m_strProgramName = m_strProgramName.substring(m_strProgramName.lastIndexOf("/") + 1);
			m_intProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess(m_strProgramName);
			
			while ( m_intProcessID == -1 && intGrabProcessIDAttempt < 10 )
			{
				Thread.sleep(100);
				m_intProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess(m_strProgramName);
				intGrabProcessIDAttempt += 1;
			}
			
			if ( intGrabProcessIDAttempt == 10 )
				JOptionPane.showMessageDialog( null, "Could not recover the process ID for process \"" + m_strProgramName + "\".\nPlease manually ensure that this program is killed when the output is complete.");
				
			m_timUpdateTextArea = new java.util.Timer( );
			m_timUpdateTextArea.schedule( new UpdateTextArea( ), 1 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: UpdateTextArea
	// Abstract: Updates the text area with the program's output
	// --------------------------------------------------------------------------------
	private class UpdateTextArea extends TimerTask
	{

		@Override
		public void run()
		{
			try
			{
				if ( m_blnWindowClosing == false )
				{
					if ( m_isrRunningProcess.ready( ) == true )
					{
						int intBuffer = m_isrRunningProcess.read( );
						if ( intBuffer != -1 )
						{
							char chrBuffer = (char) intBuffer;
							while ( intBuffer != -1 )
							{
								m_txaTextArea.append(String.valueOf(chrBuffer));
								intBuffer = m_isrRunningProcess.read( );
								chrBuffer = (char) intBuffer;
								m_txaTextArea.setText(m_txaTextArea.getText().replace("stty: standard input: Invalid argument\n", ""));
								
								// Scroll to the bottom of the text area
								if ( m_txaTextArea.getText().equals("") == false )
									m_scpTextArea.getVerticalScrollBar().setValue(m_scpTextArea.getVerticalScrollBar().getMaximum());
							}
						}
					}
					else if (m_clsRunningProcess.GetExitCode() != Integer.MIN_VALUE)
					{
						m_txaTextArea.append("\n\nProcess completed with exit code: " + m_clsRunningProcess.GetExitCode());
						m_scpTextArea.getVerticalScrollBar().setValue(m_scpTextArea.getVerticalScrollBar().getMaximum());
						return;
					}
					m_timUpdateTextArea.schedule( new UpdateTextArea( ), 1 );
				}
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}
		
	}

	// --------------------------------------------------------------------------------
	// Name: AddTextAreaMouseListener
	// Abstract: Makes the context menu work for the textarea 
	// --------------------------------------------------------------------------------
	private void AddTextAreaMouseListener( )
	{
		try
		{
			m_txaTextArea.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked( MouseEvent meSource )
				{
					try
					{
						if ( meSource.getButton( ) == MouseEvent.BUTTON3 )
						{
							if ( m_pumTextAreaContextMenu != null )
							{
								Clipboard clbSystemClipboard = Toolkit.getDefaultToolkit( ).getSystemClipboard( );
								String strSelectionText = m_txaTextArea.getSelectedText( );
								Transferable tfbClipboardText = clbSystemClipboard.getContents( null );
								String strClipboardText = "";
								boolean blnIsSelectionIPAddress = false;
								if ( tfbClipboardText.isDataFlavorSupported( DataFlavor.stringFlavor ) == true )
									strClipboardText = (String)tfbClipboardText.getTransferData(DataFlavor.stringFlavor);

								if ( strSelectionText != null )
									m_miCopy.setEnabled( strSelectionText.equals("") == false );
								else
									m_miCopy.setEnabled( false );
								
								if ( strClipboardText.equals( "" ) == false )
									m_miPaste.setEnabled( true );
								else
									m_miPaste.setEnabled( false );
								
								if ( strSelectionText != null )
									blnIsSelectionIPAddress = m_ptnIPAddressFormat.matcher( strSelectionText.trim( ) ).find( );
								
								m_miPing.setVisible( blnIsSelectionIPAddress );
								m_miDig.setVisible( blnIsSelectionIPAddress );
								m_miTelnet.setVisible( blnIsSelectionIPAddress );
								m_miTraceRoute.setVisible( blnIsSelectionIPAddress );
								m_miWhoIs.setVisible( blnIsSelectionIPAddress );
								m_miARPPoisonRouting.setVisible( blnIsSelectionIPAddress );
								m_miDiscoverHosts.setVisible( blnIsSelectionIPAddress );
								m_miNikto.setVisible( blnIsSelectionIPAddress );
								m_miDeauthenticationAttack.setVisible( blnIsSelectionIPAddress );
								
								m_pumTextAreaContextMenu.show( meSource.getComponent( ), meSource.getX( ), meSource.getY( ) );
							}
						}
					}
					catch (Exception excError)
					{
						CUtilities.WriteLog( excError );
					}
				}
			});
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: windowClosing
	// Abstract: Make sure the process is killed before the window closes 
	// --------------------------------------------------------------------------------
	@Override
    public void windowClosing( WindowEvent arg0 )
    {
        try
        {
        	m_blnWindowClosing = true;
        	
        	m_clsRunningProcess.CloseProcess();
        	
        	if ( m_intProcessID != -1 && CGlobals.clsLocalMachine.KillRunningProcess(m_intProcessID) == false)
        		JOptionPane.showMessageDialog(null, "Could not kill process " + m_strProgramName + " (PID " + m_intProcessID + "). Please manually destroy it.");
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click events
	// --------------------------------------------------------------------------------	
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( aeSource.getSource( ) == m_ddbSaveOutput.GetMenuItem("To TXT") ) miSaveOutputToTextFile_Click( );
			else if ( aeSource.getSource( ) == m_ddbSaveOutput.GetMenuItem("To HTML") ) miSaveOutputToHTMLFile_Click( );
			else if ( aeSource.getSource( ) == m_miCopy ) miCopy_Click( );
			else if ( aeSource.getSource( ) == m_miPaste ) miPaste_Click( );
			else if ( aeSource.getSource( ) == m_miPing ) miPing_Click( );
			else if ( aeSource.getSource( ) == m_miDig ) miDig_Click( );
			else if ( aeSource.getSource( ) == m_miTelnet ) miTelnet_Click( );
			else if ( aeSource.getSource( ) == m_miTraceRoute ) miTraceRoute_Click( );
			else if ( aeSource.getSource( ) == m_miWhoIs ) miWhoIs_Click( );
			else if ( aeSource.getSource( ) == m_miARPPoisonRouting ) miARPPoisonRouting_Click( );
			else if ( aeSource.getSource( ) == m_miDiscoverHosts ) miDiscoverHosts_Click( );
			else if ( aeSource.getSource( ) == m_miNikto ) miNikto_Click( );
			else if ( aeSource.getSource( ) == m_miDeauthenticationAttack ) miDeauthenticationAttack_Click( );
		}
 		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miSaveOutputToTextFile_Click
	// Abstract: Saves the output of the text area to a text file
	// --------------------------------------------------------------------------------
	private void miSaveOutputToTextFile_Click( )
	{
		try
		{
			String strSavePath = CAircrackUtilities.DisplayFileChooser(this, false, ".txt", "Text files (*.txt)");
			if ( strSavePath.equals( "" ) == false )
			{
				String strCommand = BuildCommandStringFromArray( );
				FileWriter fsTest = new FileWriter( strSavePath );
				BufferedWriter bwOutput = new BufferedWriter( fsTest );
				bwOutput.write( strCommand + "\n" );
				bwOutput.write( "################################################################################\n");
				bwOutput.write( "\n" );
				bwOutput.write( m_txaTextArea.getText( ) );
				bwOutput.close( );
				JOptionPane.showMessageDialog(null, "Output successfully saved to file.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miSaveOutputToHTMLFile_Click
	// Abstract: Saves the output of the text area to an HTML file
	// --------------------------------------------------------------------------------
	private void miSaveOutputToHTMLFile_Click( )
	{
		try
		{
			String strSavePath = CAircrackUtilities.DisplayFileChooser(this, false, ".html", "HyperText Markup Language files (*.html)");
			if ( strSavePath.equals( "" ) == false )
			{
				String strCommand = BuildCommandStringFromArray( );
				FileWriter fsTest = new FileWriter( strSavePath );
				BufferedWriter bwOutput = new BufferedWriter( fsTest );
				bwOutput.write( "<html><head><title>" + strCommand + "</title></head><body>");
				bwOutput.write( "<p style=\"font-family: Courier New; font-size: 10pt;\">" + strCommand + "</p>" );
				bwOutput.write( "<hr />" );
				bwOutput.write( "<p style=\"font-family: Courier New; font-size: 10pt;\">" + m_txaTextArea.getText( ).replace("\n", "<br />") + "</p>" );
				bwOutput.write( "</body></html>");
				bwOutput.close( );
				JOptionPane.showMessageDialog(null, "Output successfully saved to file.");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miCopy_Click
	// Abstract: Copies the selected text from the textarea
	// --------------------------------------------------------------------------------
	private void miCopy_Click( )
	{
		try
		{
			CGlobals.clsLocalMachine.SetClipboardContents( GetSelectedText( ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miPaste_Click
	// Abstract: Pastes the selected text into the textarea
	// --------------------------------------------------------------------------------
	private void miPaste_Click( )
	{
		try
		{
			Clipboard clpSystemClipboard = Toolkit.getDefaultToolkit( ).getSystemClipboard( );
			String strClipboardContents = (String)clpSystemClipboard.getData( DataFlavor.stringFlavor );
			if ( strClipboardContents.equals( "" ) == false )
			{
				int intCaretPosition = m_txaTextArea.getCaretPosition( );
				String strNewText = m_txaTextArea.getText( );
				String strBeforeCaret = strNewText.substring(0, intCaretPosition);
				String strAfterCaret = strNewText.substring( intCaretPosition );
				strNewText = strBeforeCaret + strClipboardContents + strAfterCaret;
				m_txaTextArea.setText( strNewText );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miPing_Click
	// Abstract: Opens up a ping window from the selected output text
	// --------------------------------------------------------------------------------
	private void miPing_Click( )
	{
		try
		{
			FOtherToolsBasicToolsPing frmPing = new FOtherToolsBasicToolsPing( );
			frmPing.SetDestination( GetSelectedText( ) );
			frmPing.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetSelectedText
	// Abstract: Retrieves the selected text from the textarea
	// --------------------------------------------------------------------------------
	private String GetSelectedText( )
	{
		String strSelectedText = "";
		try
		{
			strSelectedText = m_txaTextArea.getSelectedText( );
			if ( strSelectedText == null )
				strSelectedText = "";
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		return strSelectedText;
	}
	
	// --------------------------------------------------------------------------------
	// Name: miDig_Click
	// Abstract: Opens a new dig window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miDig_Click( )
	{
		try
		{
			FOtherToolsBasicToolsDig frmDig = new FOtherToolsBasicToolsDig( );
			frmDig.SetDestination( GetSelectedText( ) );
			frmDig.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miTelnet_Click
	// Abstract: Opens a new telnet window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miTelnet_Click( )
	{
		try
		{
			FOtherToolsBasicToolsTelnet frmTelnet = new FOtherToolsBasicToolsTelnet( );
			frmTelnet.SetDestination( GetSelectedText( ) );
			frmTelnet.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miTraceRoute_Click
	// Abstract: Opens a new trace route window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miTraceRoute_Click( )
	{
		try
		{
			FOtherToolsBasicToolsTraceRoute frmTraceRoute = new FOtherToolsBasicToolsTraceRoute( );
			frmTraceRoute.SetDestination( GetSelectedText( ) );
			frmTraceRoute.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: miWhoIs_Click
	// Abstract: Opens a new who is window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miWhoIs_Click( )
	{
		try
		{
			FOtherToolsBasicToolsWhoIs frmWhoIs = new FOtherToolsBasicToolsWhoIs( );
			frmWhoIs.SetDestination( GetSelectedText( ) );
			frmWhoIs.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miARPPoisonRouting_Click
	// Abstract: Opens a new ARP poison routing window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miARPPoisonRouting_Click( )
	{
		try
		{
			FARPPoisonRouting frmARPPoisonRouting = new FARPPoisonRouting( );
			frmARPPoisonRouting.SetTargetLocation( GetSelectedText( ) );
			frmARPPoisonRouting.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miDiscoverHosts_Click
	// Abstract: Opens a new Discover Hosts window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miDiscoverHosts_Click( )
	{
		try
		{
			FDiscoverHosts frmDiscoverHosts = new FDiscoverHosts( );
			frmDiscoverHosts.SetDestination( GetSelectedText( ) );
			frmDiscoverHosts.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miNikto_Click
	// Abstract: Opens a new Nikto window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miNikto_Click( )
	{
		try
		{
			FOtherToolsNikto frmNikto = new FOtherToolsNikto( );
			frmNikto.AddHost( GetSelectedText( ) );
			frmNikto.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: miDeauthenticationAttack_Click
	// Abstract: Opens a new de-authentication window with the selected text as the target
	// --------------------------------------------------------------------------------
	private void miDeauthenticationAttack_Click( )
	{
		try
		{
			FReplayInjectPackets frmDeauthenticationAttack = new FReplayInjectPackets( );
			frmDeauthenticationAttack.SetAttackMethod( "Deauthentication" );
			frmDeauthenticationAttack.SetDestinationIP( GetSelectedText( ) );
			frmDeauthenticationAttack.setVisible( true );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: BuildCommandStringFromArray
	// Abstract: Takes the array used to create this window and puts it on one line.
	// --------------------------------------------------------------------------------
	private String BuildCommandStringFromArray( )
	{
		String strCommand = "";
		try
		{
			for ( int intIndex = 0; intIndex < m_astrCommand.length; intIndex += 1 )
			{
				if ( strCommand.equals( "" ) == false )
					strCommand += " ";
				strCommand += m_astrCommand[ intIndex ];
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strCommand;
	}

	// --------------------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Mouse click event handler
	// --------------------------------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent meSource)
	{
		try
		{
			if ( meSource.getSource( ) == m_ddbSaveOutput )		m_ddbSaveOutput.DisplayPopupMenu( meSource );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: keyTyped
	// Abstract: Key press mouse event
	// --------------------------------------------------------------------------------
	@Override
	public void keyTyped(KeyEvent keSource)
	{
		try
		{
			if ( keSource.getSource( ) == m_txaInputArea && (int)keSource.getKeyChar( ) == 10 &&
					keSource.isShiftDown( ) == true )
			{
				m_txaInputArea.append("\n");
			}
			else if ( keSource.getSource( ) == m_txaInputArea && (int)keSource.getKeyChar( ) == 10 )
			{
				String strCommand = m_txaInputArea.getText( );

				m_txaTextArea.append( strCommand );

				try
				{
					if (m_chkSendNewline.isSelected())
						m_oswRunningProcess.write( strCommand + "\n" );
					else
						m_oswRunningProcess.write( strCommand );
					m_oswRunningProcess.flush( );
				}
				catch (IOException ioError)
				{
					if ( ioError.getMessage( ).equals("Pipeline broken") || ioError.getMessage( ).equals("Stream closed") )
					{
						// Pipeline broken or stream closed means that the program has terminated and is no 
						// longer accepting input. Alert the user and disable the textarea.
						JOptionPane.showMessageDialog(null, "The program is no longer accepting input because it has terminated.");
						m_txaInputArea.setEnabled( false );
						m_txaInputArea.setEditable( false );
					}
					else
					{
						CUtilities.WriteLog( ioError );
					}
				}
				catch (Exception excError)
				{
					CUtilities.WriteLog( excError );
				}
				
				m_txaInputArea.setText( "" );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// Not used
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent meSource) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void keyPressed(KeyEvent arg0) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}

}
