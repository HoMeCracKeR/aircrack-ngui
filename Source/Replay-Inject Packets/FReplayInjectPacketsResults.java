// --------------------------------------------------------------------------------
// Name: FReplayInjectPacketsResults
// Abstract: Shows the results of a replay/inject packet attack
// --------------------------------------------------------------------------------

// Includes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class FReplayInjectPacketsResults extends CAircrackWindow implements WindowListener, ComponentListener
{
	protected final static long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------
	// Name: UpdateTextArea
	// Abstract: Updates the text area to show the results of the attack
	// --------------------------------------------------------------------------------
    class UpdateTextArea extends TimerTask
    {

    	// --------------------------------------------------------------------------------
    	// Name: run
    	// Abstract: Function that gets called when the timer task is executed
    	// --------------------------------------------------------------------------------
        @Override
        public void run( )
        {
            try
            {            
            	int intBuffer = 0;
            	int intPreviousCharacter = -1;
                if ( m_clsRunningAttack != null && m_rdrSearch.ready( ) == true )
                {
                    
                	intBuffer = m_rdrSearch.read( );
                    while ( intBuffer != -1 )
                    {
                    	if ( intBuffer == 13 && intPreviousCharacter != 10 )
                    	{
                    		m_txaResults.setText(m_txaResults.getText().substring(0, m_txaResults.getText().lastIndexOf("\r") + 1));
                    		intPreviousCharacter = intBuffer;
                    		intBuffer = m_rdrSearch.read( );
                    		if ( intBuffer == -1 )
                    			break;
                    	}
                        m_txaResults.setText( m_txaResults.getText( ) + (char)intBuffer );
                        intPreviousCharacter = intBuffer;
                        intBuffer = m_rdrSearch.read( );
                    }
                    
                }
                
                m_timUpdateScreen.schedule( new UpdateTextArea(), 100 );
            }
            catch ( Exception excError )
            {
                CUtilities.WriteLog( excError );
            }
        }
        
    }
    
    private JTextArea m_txaResults = null;
    private JScrollPane m_sbrResults = null;
    private java.util.Timer m_timUpdateScreen = null;
    private CProcess m_clsRunningAttack = null;
    private String m_astrCommands[] = null;
    private int m_intAireplayNGProcessID = -1;
    
    private InputStreamReader m_rdrSearch = null;

	// --------------------------------------------------------------------------------
	// Name: FReplayInjectPacketsResults
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
    public FReplayInjectPacketsResults( )
    {
    	super("Replay/Inject Packets - Output", 510, 270, false, false, "");
        try
        {
            Initialize();
            AddControls();
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Initializes the window
	// --------------------------------------------------------------------------------
    private void Initialize()
    {
        try
        {
            // Need to start the attack when the form is shown
            addComponentListener( this );
            
            m_timUpdateScreen = new java.util.Timer();
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }
    
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the window
	// --------------------------------------------------------------------------------
    private void AddControls()
    {
        try
        {
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            m_txaResults = new JTextArea( "", 15, 45 );
            
            splFrame.getConstraints( m_txaResults ).setX( Spring.constant( 5 ) );
            splFrame.getConstraints( m_txaResults ).setY( Spring.constant( 5 ) );
            m_txaResults.setLineWrap( true );
            m_sbrResults = new JScrollPane(m_txaResults);
            m_sbrResults.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
            
            conControlArea.add( m_sbrResults );
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }
    
	// --------------------------------------------------------------------------------
	// Name: SetCommands
	// Abstract: Sets the commands to execute
	// --------------------------------------------------------------------------------
    public void SetCommands(String astrCommands[])
    {
        try
        {

        	m_astrCommands = astrCommands;
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: StartAttack
	// Abstract: Starts the attack
	// --------------------------------------------------------------------------------
    private void StartAttack( )
    {
        try
        {
            
            String astrCommands[] = m_astrCommands;
            if ( m_astrCommands != null )
            {
                m_clsRunningAttack = new CProcess(astrCommands, true, false, true);
                m_intAireplayNGProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess(astrCommands[0]);
                m_rdrSearch = m_clsRunningAttack.GetOutput( );
                m_timUpdateScreen.schedule( new UpdateTextArea(), 1000 );
            }
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
            JOptionPane.showMessageDialog( this, excError.getMessage( ) );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: windowClosing
	// Abstract: Cleans up the attack processes so no residue is left over
	// --------------------------------------------------------------------------------
    @Override
    public void windowClosing( WindowEvent arg0 )
    {
        try
        {
        	if ( m_intAireplayNGProcessID != -1 )
        	{
        		if ( CGlobals.clsLocalMachine.KillRunningProcess(m_intAireplayNGProcessID) == false )
        		{
        			JOptionPane.showMessageDialog(null, "Could not kill process \"" + m_astrCommands[0] + "\" (" + m_intAireplayNGProcessID + "). Please manually destroy it.");
        		}
        	}
        	
        	if ( m_clsRunningAttack != null )
        	{
        		m_clsRunningAttack.CloseProcess();
        	}
        	
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

	// --------------------------------------------------------------------------------
	// Name: componentShown
	// Abstract: Starts the attack when the window is shown
	// --------------------------------------------------------------------------------
    @Override
    public void componentShown( ComponentEvent arg0 )
    {
        try
        {
            StartAttack();
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }
    
    // Not used
    @Override
    public void windowActivated( WindowEvent arg0 ) {}
    @Override
    public void windowClosed( WindowEvent arg0 ) {}
    @Override
    public void windowDeactivated( WindowEvent arg0 ) {}
    @Override
    public void windowDeiconified( WindowEvent arg0 ) {}
    @Override
    public void windowIconified( WindowEvent arg0 ) {}
    @Override
    public void windowOpened( WindowEvent arg0 ) {}
    @Override
    public void componentHidden( ComponentEvent arg0 ) {}
    @Override
    public void componentMoved( ComponentEvent arg0 ) {}
    @Override
    public void componentResized( ComponentEvent arg0 ) {}
    
}
