// ----------------------------------------------------------------------
// Name: Pat Callahan
// Class: CUtilities.
//
// Version		Changed By	Notes
// 2008/08/31	P.C.		Updated to 1.1: Top, left, shortcut
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.text.*;			            // DateFormat, SimpleDateFormat
import java.util.*;			            // Date
import java.io.*;			            // BufferedReader
import java.awt.*;			            // All kinds of stuff: container, window, etc
import java.awt.event.*;	            // ActionListener
import javax.swing.*;		            // JEverything
import javax.swing.event.*;	            // DocumentListener
import java.awt.event.ItemListener;     // ItemListener

// ----------------------------------------------------------------------
// Name: CUtilities
// Abstract: Some general purpose utilities
// ----------------------------------------------------------------------
public class CUtilities
{
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Constants
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	private static final String m_strLOG_FILE_EXTENSION = ".log";
	
	// days * hours * minutes * seconds * milliseconds
	private static final long lng10_DAYS = 10 * 24 * 60 * 60 * 1000; 


	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	private static String m_strOldLogFileAndPath = "";
	private static BufferedWriter m_buwLogFile = null;
	

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------


	// ----------------------------------------------------------------------
	// Name: AddButton
	// Abstract: Add a button to the content pane.
	// ----------------------------------------------------------------------
	public static JButton AddButton( Container conParent, ActionListener alParent,
										String strTitle, char chrKeyboardShortcut, 
										int intTop, int intLeft, int intHeight, int intWidth )
	{
		JButton btnNewButton = null;
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New button
			btnNewButton = new JButton( strTitle );
			conParent.add( btnNewButton );
			splParent.getConstraints( btnNewButton ).setY( Spring.constant( intTop ) );
			splParent.getConstraints( btnNewButton ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( btnNewButton ).setHeight( Spring.constant( intHeight ) );
			splParent.getConstraints( btnNewButton ).setWidth( Spring.constant( intWidth ) );
			if( chrKeyboardShortcut != ' ' ) btnNewButton.setMnemonic( chrKeyboardShortcut );
			if( alParent != null ) btnNewButton.addActionListener( alParent );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return btnNewButton;
	}



	// ----------------------------------------------------------------------
	// Name: AddButton
	// Abstract: Add a button to the content pane.
	// ----------------------------------------------------------------------
	public static JButton AddButton( Container conParent, ActionListener alParent,
										String strTitle,  
										int intTop, int intLeft, 
										int intHeight, int intWidth )
	{
		JButton btnNewButton = null;
		try
		{
			// No shortcut so pass in a space
			btnNewButton = AddButton( conParent, alParent, strTitle, ' ',  
										intTop, intLeft, intHeight, intWidth );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return btnNewButton;
	}



	// ----------------------------------------------------------------------
	// Name: AddCheckBox
	// Abstract: Add a CheckBox
	// ----------------------------------------------------------------------
	public static JCheckBox AddCheckBox( Container conParent, ActionListener alParent,
										 String strTitle,
										 int intTop, int intLeft )
	{
		JCheckBox chkNewCheckBox = null;
		
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New CheckBox
			chkNewCheckBox = new JCheckBox( strTitle );
			conParent.add( chkNewCheckBox );
			splParent.getConstraints( chkNewCheckBox ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( chkNewCheckBox ).setY( Spring.constant( intTop ) );
			if( alParent != null ) chkNewCheckBox.addActionListener( alParent );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return chkNewCheckBox;
	}



	// ----------------------------------------------------------------------
	// Name: AddComboBox
	// Abstract: Add a ComboBox to the content pane.
	// ----------------------------------------------------------------------
	public static CComboBox AddComboBox( Container conParent, MouseListener mlParent,
											   int intTop, int intLeft,
										       int intHeight, int intWidth)
	{
		CComboBox cmbNewComboBox = null;
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New cmbNewComboBox
			cmbNewComboBox = new CComboBox( );
			conParent.add( cmbNewComboBox );
			cmbNewComboBox.setPreferredSize( new Dimension( intWidth, intHeight ) );
			splParent.getConstraints( cmbNewComboBox ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( cmbNewComboBox ).setY( Spring.constant( intTop ) );
			if( mlParent != null ) cmbNewComboBox.addMouseListener( mlParent );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return cmbNewComboBox;
	}



	// ----------------------------------------------------------------------
    // Name: AddComboBox
    // Abstract: Add a ComboBox to the content pane.
    // ----------------------------------------------------------------------
    public static CComboBox AddComboBox( Container conParent, MouseListener mlParent, ItemListener ilParent,
                                               int intTop, int intLeft,
                                               int intHeight, int intWidth)
    {
        CComboBox cmbNewComboBox = null;
        try
        {
            // Assume spring layout manager for target container
            SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
    
            // New cmbNewComboBox
            cmbNewComboBox = new CComboBox( );
            conParent.add( cmbNewComboBox );
            cmbNewComboBox.setPreferredSize( new Dimension( intWidth, intHeight ) );
            splParent.getConstraints( cmbNewComboBox ).setX( Spring.constant( intLeft ) );
            splParent.getConstraints( cmbNewComboBox ).setY( Spring.constant( intTop ) );
            if( mlParent != null ) cmbNewComboBox.addMouseListener( mlParent );
        }
        catch( Exception expError )
        {
            // Display Error Message
            CUtilities.WriteLog( expError );
        }
        
        // Return result        
        return cmbNewComboBox;
    }


	// ----------------------------------------------------------------------
	// Name: AddLabel
	// Abstract: Add a label
	// ----------------------------------------------------------------------
	public static JLabel AddImageLabel( Container conParent, String strTitle, String strImageLocation, 
									int intAlignment, int intTop, int intLeft )
	{
		JLabel lblNewLabel = null;
		
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
			ImageIcon icoIcon = new ImageIcon(strImageLocation);
			
			// Resize our image
			Image imgIconImage = icoIcon.getImage( );
			Image imgNewIconImage = imgIconImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			icoIcon = new ImageIcon(imgNewIconImage);
			
			// New Label
			lblNewLabel = new JLabel( strTitle, icoIcon, intAlignment );
			conParent.add( lblNewLabel );
			splParent.getConstraints( lblNewLabel ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( lblNewLabel ).setY( Spring.constant( intTop ) );
			lblNewLabel.setIcon(icoIcon);
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return lblNewLabel;
	}



	// ----------------------------------------------------------------------
	// Name: AddLabel
	// Abstract: Add a label
	// ----------------------------------------------------------------------
	public static JLabel AddLabel( Container conParent, String strTitle, 
									int intTop, int intLeft )
	{
		JLabel lblNewLabel = null;
		
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New Label
			lblNewLabel = new JLabel( strTitle );
			conParent.add( lblNewLabel );
			splParent.getConstraints( lblNewLabel ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( lblNewLabel ).setY( Spring.constant( intTop ) );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return lblNewLabel;
	}



	// ----------------------------------------------------------------------
	// Name: AddListBox
	// Abstract: Add a list to the content pane.
	// ----------------------------------------------------------------------
	public static CListBox AddListBox( Container conParent, MouseListener mlParent,
										int intTop, int intLeft,
										int intHeight, int intWidth )
	{
		CListBox lstNewList = null;
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New lstNewList
			lstNewList = new CListBox( );
			conParent.add( lstNewList );
			lstNewList.setPreferredSize( new Dimension( intWidth, intHeight ) );
			splParent.getConstraints( lstNewList ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( lstNewList ).setY( Spring.constant( intTop ) );
			if( mlParent != null ) lstNewList.addMouseListener( mlParent );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return lstNewList;
	}
	
	// ----------------------------------------------------------------------
	// Name: AddMenu
	// Abstract: Add a menu to a menu bar
	// ----------------------------------------------------------------------
	public static JMenu AddMenu( JMenuBar mbParent, String strTitle )
	{
		return AddMenu( mbParent, strTitle, ' ' );
	}



	// ----------------------------------------------------------------------
	// Name: AddMenu
	// Abstract: Add a menu to a menu bar
	// ----------------------------------------------------------------------
	public static JMenu AddMenu( JMenuBar mbParent, String strTitle, char chrKeyboardShortcut )
	{
		JMenu mnuNewMenu = null;
		
		try
		{		
			// Add to menu bar
			mnuNewMenu = new JMenu( strTitle );
	
			// Set mnemonic if there is one
			if( chrKeyboardShortcut != ' ' ) mnuNewMenu.setMnemonic( chrKeyboardShortcut );
	
			mbParent.add( mnuNewMenu );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return mnuNewMenu;
	}



	// ----------------------------------------------------------------------
	// Name: AddMenuItem
	// Abstract: Add a menu item to a menu
	// ----------------------------------------------------------------------
	public static JMenuItem AddMenuItem( JMenu mnuParent, ActionListener alTarget, 
										 String strTitle )
	{
		return AddMenuItem( mnuParent, alTarget, strTitle, ' ' );
	}
		
		
		
	// ----------------------------------------------------------------------
	// Name: AddMenuItem
	// Abstract: Add a menu item to a menu
	// ----------------------------------------------------------------------
	public static JMenuItem AddMenuItem( JMenu mnuParent, ActionListener alTarget,
										 String strTitle, char chrKeyboardShortcut )
	{
		JMenuItem mniNewMenuItem = null;
		
		try
		{		
			// Add to menu
			mniNewMenuItem = mnuParent.add( strTitle );
			
			// Set mnemonic if there is one
			if( chrKeyboardShortcut != ' ' ) mniNewMenuItem.setMnemonic( chrKeyboardShortcut );
			
			// Add click event
			mniNewMenuItem.addActionListener( alTarget );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return mniNewMenuItem;
	}


	// ----------------------------------------------------------------------
    // Name: AddPanel
    // Abstract:  Adds a panel to the form
    // ----------------------------------------------------------------------
	public static JPanel AddPanel( Container conParent, MouseListener mlParent,
                                    int intTop, int intLeft, int intHeight, int intWidth,
                                    String strTitle)
	{
	    
	    JPanel pnlNewPanel = null;
	    Color clrBorderColor = null;
	    
	    try
	    {
	        
    	    // Assume spring layout manager for target container
            SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
    
            // New panel
            pnlNewPanel = new JPanel( );
            conParent.add( pnlNewPanel );
            pnlNewPanel.setLayout( splParent );
            splParent.getConstraints( pnlNewPanel ).setY( Spring.constant( intTop ) );
            splParent.getConstraints( pnlNewPanel ).setX( Spring.constant( intLeft ) );
            splParent.getConstraints( pnlNewPanel ).setHeight( Spring.constant( intHeight ) );
            splParent.getConstraints( pnlNewPanel ).setWidth( Spring.constant( intWidth ) );
            if( mlParent != null ) pnlNewPanel.addMouseListener( mlParent );
            
            // Set up the border
            clrBorderColor = new Color( 184, 207, 229 );
            pnlNewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( clrBorderColor), strTitle ) );
            
	    }
	    catch ( Exception expError )
	    {
	        
	        CUtilities.WriteLog( expError );
	        
	    }
	    
	    return pnlNewPanel;
	    
	}


	// ----------------------------------------------------------------------
	// Name: AddRadioButton
	// Abstract: Add a RadioButton
	// ----------------------------------------------------------------------
	public static JRadioButton AddRadioButton( Container conParent, ActionListener alParent,
												 ButtonGroup bgpGroup, String strTitle,
												 int intTop, int intLeft )
	{
		JRadioButton optNewRadioButton = null;
		
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	
			// New RadioButton
			optNewRadioButton = new JRadioButton( strTitle );
			conParent.add( optNewRadioButton );
			splParent.getConstraints( optNewRadioButton ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( optNewRadioButton ).setY( Spring.constant( intTop ) );
			if( alParent != null ) optNewRadioButton.addActionListener( alParent );
			bgpGroup.add( optNewRadioButton );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result
		return optNewRadioButton;
	}


	public static JTextArea AddTextArea(Container conParent, DocumentListener dlParent,
	                                    String strDefaultValue, int intWidthInColumns,
	                                    int intHeightInRows, int intTop, int intLeft)
	{
	    JTextArea txaNewTextArea = null;
	    try
	    {
	        SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
	        
	        txaNewTextArea = new JTextArea( strDefaultValue, intHeightInRows, intWidthInColumns );
	        conParent.add( txaNewTextArea );
	        splParent.getConstraints( txaNewTextArea ).setX( Spring.constant( intLeft ) );
	        splParent.getConstraints( txaNewTextArea ).setY( Spring.constant( intTop ) );
	        if ( dlParent != null ) txaNewTextArea.getDocument( ).addDocumentListener( dlParent );
	        
	    }
	    catch (Exception excError)
	    {
	        CUtilities.WriteLog( excError );
	    }
	    return txaNewTextArea;
	}


	// ----------------------------------------------------------------------
	// Name: AddTextBox
	// Abstract: Add a text box
	// ----------------------------------------------------------------------
	public static CTextBox AddTextBox( Container conParent, DocumentListener dlParent,
										String strDefaultValue, 
										int intWidthInColumns, int intMaximumLength, 
										int intTop, int intLeft )
	{
		CTextBox txtNewTextBox = null;
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
			
			// New Textbox
			txtNewTextBox = new CTextBox( strDefaultValue, intWidthInColumns, intMaximumLength );
			conParent.add( txtNewTextBox );
			splParent.getConstraints( txtNewTextBox ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( txtNewTextBox ).setY( Spring.constant( intTop ) );
			if( dlParent != null ) txtNewTextBox.getDocument( ).addDocumentListener( dlParent );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return txtNewTextBox;
	}
	
	// ----------------------------------------------------------------------
	// Name: AddTable
	// Abstract: Add a table
	// ----------------------------------------------------------------------
	public static CTable AddTable(Container conParent, String astrColumns[], Object aaobjData[][], int intTop, int intLeft, int intHeight, int intWidth)
	{
		CTable tblNewTable = null;
		try
		{
			
			tblNewTable = new CTable(conParent, astrColumns, aaobjData);
			tblNewTable.SetTop( intTop );
			tblNewTable.SetLeft( intLeft );
			tblNewTable.SetHeight( intHeight );
			tblNewTable.SetWidth( intWidth );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return tblNewTable;
	}

	public static CDropdownButton AddDropdownButton(Container conParent, ActionListener alParent, MouseListener mlParent, String strButtonText, int intTop, int intLeft, int intHeight, int intWidth)
	{
		CDropdownButton ddbNewButton = null;
		try
		{
			// Assume spring layout manager for target container
			SpringLayout splParent = ( SpringLayout ) conParent.getLayout( );
			
			ddbNewButton = new CDropdownButton( strButtonText );
			conParent.add( ddbNewButton );
			splParent.getConstraints( ddbNewButton ).setX( Spring.constant( intLeft ) );
			splParent.getConstraints( ddbNewButton ).setY( Spring.constant( intTop ) );
			splParent.getConstraints( ddbNewButton ).setWidth( Spring.constant( intWidth ) );
			splParent.getConstraints( ddbNewButton ).setHeight( Spring.constant( intHeight ) );
			if( mlParent != null ) ddbNewButton.addMouseListener(mlParent);
			if( alParent != null ) ddbNewButton.SetParent( alParent );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return ddbNewButton;
	}

	// ----------------------------------------------------------------------
	// Name: AddTabContainer
	// Abstract: Add a tab container
	// ----------------------------------------------------------------------
	public static CTabContainer AddTabContainer(Container conParent, int intTop, int intLeft, int intHeight, int intWidth)
	{
		CTabContainer tbcNewTabContainer = null;
		try
		{
			tbcNewTabContainer = new CTabContainer( );
			tbcNewTabContainer.SetPosition(conParent, intLeft, intTop, intWidth, intHeight);
			conParent.add(tbcNewTabContainer.GetJTabbedPane());
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		return tbcNewTabContainer;
	}
	
    // --------------------------------------------------------------------------------
    // Name: AddScrollPane
    // Abstract: Adds a scrollpane to the control area
    // --------------------------------------------------------------------------------
	public static JPanel AddScrollPane(Container conControlArea, JPanel pnlParent, int intNewPaneHeight, int intScrollPaneWidth, int intScrollPaneHeight, int intX, int intY)
	{
		JPanel pnlScrollablePane = null;
		try
		{
			SpringLayout splFrame = (SpringLayout)conControlArea.getLayout( );
			JScrollPane scpScrollPane = null;
			
			// Setup our new panel
			pnlScrollablePane = new JPanel( );
			splFrame.getConstraints(pnlScrollablePane).setHeight(Spring.constant( intNewPaneHeight ));
			pnlScrollablePane.setLayout( splFrame );
			
			// Setup our new scrollpane
        	scpScrollPane = new JScrollPane( pnlScrollablePane );
        	scpScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        	scpScrollPane.setPreferredSize(new Dimension( intScrollPaneWidth, intScrollPaneHeight ));
        	if ( pnlParent != null )
        		pnlParent.add( scpScrollPane );
        	else
        		conControlArea.add( scpScrollPane );
        	
        	// Position our scrollpane
        	splFrame.getConstraints( scpScrollPane ).setX( Spring.constant( intX ) );
        	splFrame.getConstraints( scpScrollPane ).setY( Spring.constant( intY ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		return pnlScrollablePane;
	}
	
	// ----------------------------------------------------------------------
	// Name: CenterOwner
	// Abstract: Center the dialog over the owner.
	// ----------------------------------------------------------------------
	public static void CenterOwner( JDialog dlgChild )
	{
		try
		{
			int intTop = 0;
			int intLeft = 0;

			// Get owner
			Window winOwner = dlgChild.getOwner( );
			
			// Get owner location
			Rectangle recOwner = winOwner.getBounds( );
			
			// Center child
			intTop = (int) recOwner.getCenterY( ) - ( dlgChild.getHeight( ) / 2 );
			intLeft = (int) recOwner.getCenterX( ) - ( dlgChild.getWidth( ) / 2 );

			// Center child
			dlgChild.setLocation( intLeft, intTop );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: CenterOwner
	// Abstract: Center the frame over the owner.
	// ----------------------------------------------------------------------
	public static void CenterOwner( JFrame fraChild )
	{
		try
		{
			int intTop = 0;
			int intLeft = 0;

			// Get owner
			Window winOwner = fraChild.getOwner( );
			
			// Get owner location
			Rectangle recOwner = winOwner.getBounds( );

			intTop = (int) recOwner.getCenterY( ) - ( fraChild.getHeight( ) / 2 );
			intLeft = (int) recOwner.getCenterX( ) - ( fraChild.getWidth( ) / 2 );

			// Center child
			fraChild.setLocation( intLeft, intTop );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: CenterScreen
	// Abstract: Center the dialog in the screen
	// ----------------------------------------------------------------------
	public static void CenterScreen( JDialog dlgChild )
	{
		try
		{
			Point pntCenter;
			int intTop = 0;
			int intLeft = 0;
			
			// Get center of screen
			pntCenter = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			
			// Center child
			intTop = (int) pntCenter.y - ( dlgChild.getHeight( ) / 2 );
			intLeft = (int) pntCenter.x - ( dlgChild.getWidth( ) / 2 );

			// Center child
			dlgChild.setLocation( intLeft, intTop );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	

	// ----------------------------------------------------------------------
	// Name: CenterScreen
	// Abstract: Center the frame in the screen
	// ----------------------------------------------------------------------
	public static void CenterScreen( JFrame fraChild )
	{
		try
		{
			Point pntCenter;
			int intTop = 0;
			int intLeft = 0;

			// Get center of screen
			pntCenter = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			
			// Center child
			intTop = (int) pntCenter.y - ( fraChild.getHeight( ) / 2 );
			intLeft = (int) pntCenter.x - ( fraChild.getWidth( ) / 2 );

			// Center child
			fraChild.setLocation( intLeft, intTop );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// ----------------------------------------------------------------------
    // Name: ConvertSringToDate
    // Abstract:  Converts a date string to a date using a certain format
    // ----------------------------------------------------------------------
	public static java.sql.Date ConvertStringToDate( String strDateString, String strDateFormat )
	{
	    
	    java.sql.Date dtmConvertedDate = null;
	    DateFormat dfrFormatter;
	    
	    try
	    {
	        
	        if ( IsDateStringInFormat( strDateString, strDateFormat ) == true )
	        {
	            
	            dfrFormatter = new SimpleDateFormat( strDateFormat );
	            
	            dtmConvertedDate = new java.sql.Date( dfrFormatter.parse( strDateString ).getTime( ) );
	            
	        }
	        
	    }
	    catch ( Exception expError )
	    {
	        
	        CUtilities.WriteLog( expError );
	        
	    }
	    
	    return dtmConvertedDate;
	    
	}


	// ----------------------------------------------------------------------
	// Name: DeleteOldLogFiles
	// Abstract:  Delete any log files old than 10 days.
	// ----------------------------------------------------------------------
	private static void DeleteOldLogFiles( )
	{
		try
		{
			String strLogFileDirectory = "";
			File filLogDirectory = null;
			File afilLogFiles[] = null;
			int intIndex = 0;
			File filLogFile = null;
			long lngMilliSecondsOld = 0;
			Date dtmLastModified = null;
					
			// Log file directory
		    strLogFileDirectory = CAircrackUtilities.GetJARRunningPath() + "\\Log\\";
			filLogDirectory = new File( strLogFileDirectory );	    
			
			// Make sure the folder exists first
			if (filLogDirectory.exists() == false)
				filLogDirectory.mkdir();
			
		    // Look for any log files
		    afilLogFiles = filLogDirectory.listFiles( );
		    
			// Check the date of each file
			for( intIndex = 0; intIndex < afilLogFiles.length; intIndex++ )
			{
				// Next log file
				filLogFile = afilLogFiles[ intIndex ];
	
				// Is this a log file?
				if( ( filLogFile.getName( ) ).endsWith( m_strLOG_FILE_EXTENSION ) == true )
				{
					// When was the file last modified?
					lngMilliSecondsOld = filLogFile.lastModified( );
					
					// Add 10 days and make a date
					lngMilliSecondsOld += lng10_DAYS;
					dtmLastModified = new Date( lngMilliSecondsOld );
					
					// Is the file older than 10 days?
					if( dtmLastModified.before( new Date( ) ) == true )
					{
						// Yes.  Delete it
						filLogFile.delete( );
					}
				}
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			expError.printStackTrace( );
		}
		
	}


	// ----------------------------------------------------------------------
	// Name: GetLogFile
	// Abstract: Open the log file for writing.  Use today's date as part of
	//           the file name.  Each day a new log file will be created.
	//           Makes debug easier.
	// ----------------------------------------------------------------------
	private static BufferedWriter GetLogFile( )
	{
		try
		{
			// yyyy/mm/dd
			SimpleDateFormat m_sdfYearMonthDay = new SimpleDateFormat( "yyyyMMdd" );
			String strToday = m_sdfYearMonthDay.format( new Date( ) );
			String strLogFileDirectory = "";
			String strLogFileAndPath = "";
			File filLogDirectory = null; 
	
		    // Log everything in a log directory off of the current application directory
		    strLogFileDirectory = System.getProperty( "user.dir" ) + "/Log/";
		    strLogFileAndPath = strLogFileDirectory + strToday + m_strLOG_FILE_EXTENSION;
		    
		    // Is this a new day/log file?
		    if( m_strOldLogFileAndPath != strLogFileAndPath )
		    {
		        // Create the log directory if it doesn't exist
			    filLogDirectory = new File( strLogFileDirectory );
		        if( filLogDirectory.exists( ) == false ) filLogDirectory.mkdir( );
		        	
		        // Save the log file name
		        m_strOldLogFileAndPath = strLogFileAndPath;
		            
		        // Close file if it exists( not first time )
		        if( m_buwLogFile != null ) m_buwLogFile.close( );
	
		        // Open file and append
				m_buwLogFile = new BufferedWriter( new FileWriter( strLogFileAndPath, true ) );
		        
		        // Delete old log files
		        DeleteOldLogFiles( );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			expError.printStackTrace( );
		}
		
		// Return result
		return m_buwLogFile;
	}
	

	// ----------------------------------------------------------------------
    // Name: IsDateStringInFormat
    // Abstract:  Retrieves whether a string is in a certain date format
    // ----------------------------------------------------------------------
	public static boolean IsDateStringInFormat( String strDateString, String strDateFormat )
	{
	    
	    boolean blnIsInFormat = false;
	    DateFormat dfrFormatter = null;
	    
	    try
	    {
	        
	        dfrFormatter = new SimpleDateFormat( strDateFormat );
	        
	        dfrFormatter.parse( strDateString );
	        
	        // Success
	        blnIsInFormat = true;
	        
	        
	    }
	    catch ( Exception expError )
	    {
	        
	        // Don't report errors. The formatter will just return that the string isn't in the specified format.
	        // (which is what we want)
	        
	    }
	    
	    return blnIsInFormat;
	    
	}
		

	// -------------------------------------------------------------------------
	// Name: MoveListItems
	// Abstract: Move all selected list items from the source to the destination list
	// -------------------------------------------------------------------------
	public static void MoveListItems( CListBox lstSourceList,  
									  CListBox lstDestinationList )
	{
		try
		{
			int intIndex = 0;
			int intSelectedIndex = 0;
			int intNewIndex = 0;
			int aintSelectedIndices[] = lstSourceList.GetSelectedIndices( );
			int aintIndicesToSelect[] = new int[ aintSelectedIndices.length ];

			// Loop through all the list items and add to destination
			for( intIndex = 0; intIndex < aintSelectedIndices.length; intIndex++ )
			{
				// Selected index
				intSelectedIndex = aintSelectedIndices[ intIndex ];
			
				// Add to destination
				intNewIndex = lstDestinationList.AddItemToList( lstSourceList.GetItem( intSelectedIndex ) );
				
				// Save index so we can select all new items in destinaation at the end
				aintIndicesToSelect[ intIndex ] = intNewIndex;
			}
			
			// Select all new items in destination list
			lstDestinationList.SetSelectedIndices( aintIndicesToSelect );

			// Remove selected items from source( go from bottom up )
			for( intIndex = aintSelectedIndices.length - 1; intIndex >= 0; intIndex-- )
			{
				// Selected index
				intSelectedIndex = aintSelectedIndices[ intIndex ];

				// Remove from source					
				lstSourceList.RemoveAt( intSelectedIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	

	// ----------------------------------------------------------------------
	// Name: ReadStringFromUser
	// Abstract: Read a string from the user
	// ----------------------------------------------------------------------
	public static String ReadStringFromUser( )
	{
		String strBuffer = "";
		try
		{		
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;
	
			// Read a line
			strBuffer = burInput.readLine( );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return strBuffer;
	}

	

	// -------------------------------------------------------------------------
	// Name: SetBusyCursor
	// Abstract: Called when the form is load.  Perform some initialization.
	// -------------------------------------------------------------------------
	public static void SetBusyCursor( JDialog dlgTarget, boolean blnBusy )
	{
		try
		{
			// Busy?
			if( blnBusy == true )
			{
				// Yes
				dlgTarget.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
				dlgTarget.setEnabled( false );
			}
			else
			{
				// No
				dlgTarget.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
				dlgTarget.setEnabled( true );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// -------------------------------------------------------------------------
	// Name: SetBusyCursor
	// Abstract: Called when the form is load.  Perform some initialization.
	// -------------------------------------------------------------------------
	public static void SetBusyCursor( JFrame fraTarget, boolean blnBusy )
	{
		try
		{
			// Busy?
			if( blnBusy == true )
			{
				// Yes
				fraTarget.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
				fraTarget.setEnabled( false );
			}
			else
			{
				// No
				fraTarget.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
				fraTarget.setEnabled( true );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: WriteLog
	// Abstract: Write to the log file and display a warning to the user.
	// ----------------------------------------------------------------------
	public static void WriteLog( Exception expError )
	{
		try
		{
			boolean blnDisplay = true;
			
			WriteLog( expError, blnDisplay );
		}
		catch( Exception expWriteLogError )
		{
			// Display Error Message
			expWriteLogError.printStackTrace( );
		}
	}

	// ----------------------------------------------------------------------
	// Name: WriteLog
	// Abstract: Write to the log file and optionally display a warning 
	//			to the user.
	// ----------------------------------------------------------------------
	public static void WriteLog( Exception expError, boolean blnDisplay )
	{
		try
		{
			String strMessage = "";
			int intIndex = 0;

			// Get the stack trace messages from the exception class
			StackTraceElement asteStackTrace[] = expError.getStackTrace( );
			
			strMessage += expError.getMessage( ) + "\n";
			
			// Put all the exceptions together
			for( intIndex = asteStackTrace.length - 1; intIndex >= 0; intIndex-- )
			{ 
				strMessage += asteStackTrace[ intIndex ].toString( ) + "\n\t";
			}
			
			// Remove trailing tab
			strMessage = strMessage.substring( 0, strMessage.length( ) - 1 );
			
			WriteLog( strMessage, blnDisplay );
		}
		catch( Exception expWriteLogError )
		{
			// Display Error Message
			expWriteLogError.printStackTrace( );
		}
	}

	// ----------------------------------------------------------------------
	// Name: WriteLog
	// Abstract: Write to the log file and display a warning to the user.
	// ----------------------------------------------------------------------
	public static void WriteLog( String strMessage )
	{
		try
		{
			boolean blnDisplay = true;
			
			WriteLog( strMessage, blnDisplay );
		}
		catch( Exception expError )
		{
			// Display Error Message
			expError.printStackTrace( );
		}
	}	

	// ----------------------------------------------------------------------
	// Name: WriteLog
	// Abstract: Write to the log file and optionally display a warning 
	//			to the user.
	// ----------------------------------------------------------------------
	public static void WriteLog( String strMessage, boolean blnDisplay )
	{
		try
		{
			BufferedWriter buwLogFile = null;
			String astrMessageLines[] = null;
			int intIndex = 0;
			
			// yyyy/mm/dd
			SimpleDateFormat m_sdfYearMonthDay = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss" );
			
			String strNow = m_sdfYearMonthDay.format( new Date( ) );
			
			// Display the error message?
			if( blnDisplay == true )
			{
				// No, warn the user
				JOptionPane.showMessageDialog( null, strMessage.substring(0, strMessage.indexOf("\n")), "WriteLog", JOptionPane.OK_OPTION );
			}
			
			// Append a date/time stamp
			strMessage = strNow + " - " + strMessage + "\n";
			
			// Get a log file
			buwLogFile = GetLogFile( );
			
	    	// Is the file OK?	
			if( buwLogFile != null )
			{
				// Yes, so log message( use newline function to ensure cross-platform compatibility )
				astrMessageLines = strMessage.split( "\n" );
				for( intIndex = 0; intIndex < astrMessageLines.length; intIndex++ )
				{
					buwLogFile.write( astrMessageLines[ intIndex ] );
					buwLogFile.newLine( );
				}
				// Flush buffer
				buwLogFile.flush( );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			expError.printStackTrace( );
		}
	}
	
}

