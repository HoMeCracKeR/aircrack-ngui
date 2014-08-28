// ----------------------------------------------------------------------
//	Name: Pat Callahan
//	Class: CTextBox
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import javax.swing.*;
import javax.swing.text.*;

// ----------------------------------------------------------------------
// Name: CTextBox
// Abstract: TextField with a maximum length
// ----------------------------------------------------------------------
public class CTextBox extends JTextField
{
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Constants
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	protected final static long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// Name: CMaximumLengthDocument
	// Abstract: Extend the PlainDocument class to limit text.
	// ----------------------------------------------------------------------
	public class CMaximumLengthDocument extends PlainDocument
	{
		
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		// Constants
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		protected final static long serialVersionUID = 1L;
	
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		// Properties( never make public )
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		private int m_intMaximumLength = 0;
		
	
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		// Methods
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
	
	
		// ----------------------------------------------------------------------
		// Name: GetMaximumLength
		// Abstract: Get the maximum length
		// ----------------------------------------------------------------------
		public int GetMaximumLength( )
		{
			return m_intMaximumLength;
		}
		
		// ----------------------------------------------------------------------
		// Name: insertString
		// Abstract: Insert a string into the document.
		// ----------------------------------------------------------------------
		public void insertString( int intOffset, String strInput, AttributeSet asInput )
		{
			try
			{
				String strOldString = "";
				String strNewString = "";
				
				// Anything being input?
				if( strInput != null ) 
				{          
			        // Get the old string
			        strOldString = this.getText( 0, getLength( ) );
			        
					// Build a complete string with the old and the new
					strNewString = strOldString.substring( 0, intOffset ) + 
									strInput + 
									strOldString.substring( intOffset );
							
					// Is the new string OK?			
					if( IsValidString( strNewString ) == true ) 
					{        
						// Yes, so let the parent class handle it  
						super.insertString( intOffset, strInput, asInput );
					}
				}
			}
			catch( Exception expError )
			{
				// Display Error Message
				CUtilities.WriteLog( expError );
			}
		}


		// ----------------------------------------------------------------------
		// Name: IsValidString
		// Abstract: Is the input string valid?
		// ----------------------------------------------------------------------
		public boolean IsValidString( String strNewString ) 
		{
			boolean blnValidString = false;
		
			try
			{
				// Is the new string short enough?  
				if( m_intMaximumLength == 0 || 
					m_intMaximumLength >= strNewString.length( ) ) blnValidString = true;
			}
			catch( Exception expError )
			{
				// Display Error Message
				CUtilities.WriteLog( expError );
			}
			
			// Return result
			return blnValidString;
		}
	
		// ----------------------------------------------------------------------
		// Name: SetMaximumLength
		// Abstract: Set the maximum length
		// ----------------------------------------------------------------------
		public void SetMaximumLength( int intMaximumLength )
		{
			try
			{
				// Range check
				if( intMaximumLength < 0 ) intMaximumLength = 0;
				if( intMaximumLength > 32767 ) intMaximumLength = 32767;
				
				m_intMaximumLength = intMaximumLength;
			}
			catch( Exception expError )
			{
				// Display Error Message
				CUtilities.WriteLog( expError );
			}
		}

	}
	

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------


	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	private CMaximumLengthDocument m_clsDocument = null;


	// ----------------------------------------------------------------------
	// Name: CTextBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CTextBox( )
	{
		this( null, 0, 0 );
	}


	// ----------------------------------------------------------------------
	// Name: CTextBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CTextBox( int intColumns )
	{
		this( null, intColumns, 0 );
	}


	// ----------------------------------------------------------------------
	// Name: CTextBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CTextBox( int intColumns, int intMaximumLength )
	{
		this( null, intColumns, intMaximumLength );
	}


	// ----------------------------------------------------------------------
	// Name: CTextBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CTextBox( String strText )
	{
		this( strText, 0, 0 );
	}


	// ----------------------------------------------------------------------
	// Name: CTextBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CTextBox( String strText, int intColumns, int intMaximumLength )
	{
		// Call parent constructor		
		super( null, intColumns );
		try
		{
			// Document to limit text( from keyboard and paste )
			m_clsDocument = new CMaximumLengthDocument( );
			this.setDocument( m_clsDocument );
			
			// Set the maximum length
			m_clsDocument.SetMaximumLength( intMaximumLength );
			
			// Set the default text
			setText( strText );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: GetMaximumLength
	// Abstract: Get the maximum length
	// ----------------------------------------------------------------------
	public int GetMaximumLength( )
	{
		return m_clsDocument.GetMaximumLength( );
	}





	// ----------------------------------------------------------------------
	// Name: SetMaximumLength
	// Abstract: Set the maximum length
	// ----------------------------------------------------------------------
	public void SetMaximumLength( int intMaximumLength )
	{
		try
		{
			m_clsDocument.SetMaximumLength( intMaximumLength );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	
}


