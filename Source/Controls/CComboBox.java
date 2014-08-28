// ----------------------------------------------------------------------
//	Name: Pat Callahan
//	Class: CComboBox
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// ----------------------------------------------------------------------
// Name: CComboBox
// Abstract: Make a ComboBox class that combines the list, list model
//			and scrollbars all into one.  Add event firing too.
// ----------------------------------------------------------------------
public class CComboBox extends javax.swing.JComponent implements MouseListener
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
	private DefaultComboBoxModel m_dcmListItems = null;
	private JComboBox m_cmbList = null;
	
	private boolean m_blnSorted = true;				// Sort the list by default
	private boolean m_blnQuiet = false;				// fire/don't fire events
		

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------


	// ----------------------------------------------------------------------
	// Name: CComboBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CComboBox( )
	{
		super( );
		
		try
		{	
			Initialize( );		
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: AddItem
	// Abstract: Add an item to list and select.
	// ----------------------------------------------------------------------
	public int AddItemToList( CNameValuePair clsNewItem )
	{
		int intNewItemIndex = 0;
		try
		{		
			intNewItemIndex = AddItemToList( clsNewItem, true );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result		
		return intNewItemIndex;
	}



	// ----------------------------------------------------------------------
	// Name: AddItem
	// Abstract: Add an item to list
	// ----------------------------------------------------------------------
	public int AddItemToList( CNameValuePair clsNewItem, boolean blnSelect )
	{
		int intNewItemIndex = 0;
		try
		{	
			// Sorted?
			if( m_blnSorted == true )
			{
				// Yes, find out where it should go
				intNewItemIndex = FindSortedIndex( m_dcmListItems, clsNewItem.GetName( ) );
			}
			else
			{
				// No, add it to the end
				intNewItemIndex = m_dcmListItems.getSize( );	
			}
			
			// Insert at the specified location
			m_dcmListItems.insertElementAt( clsNewItem, intNewItemIndex );
			
			// Select?
			if( blnSelect == true ) SetSelectedIndex( intNewItemIndex );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return intNewItemIndex;
	}
	

	// ----------------------------------------------------------------------
	// Name: AddItem
	// Abstract: Add an item to list and select.
	// ----------------------------------------------------------------------
	public int AddItemToList( String strName, int intValue )
	{
		int intNewItemIndex = 0;
		try
		{		
			CNameValuePair clsNewItem = new CNameValuePair( strName, intValue );

			intNewItemIndex = AddItemToList( clsNewItem, true );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result		
		return intNewItemIndex;
	}


	// ----------------------------------------------------------------------
	// Name: AddItem
	// Abstract: Add an item to list
	// ----------------------------------------------------------------------
	public int AddItemToList( String strName, int intValue, boolean blnSelect )
	{
		int intNewItemIndex = 0;
		try
		{	
			CNameValuePair clsNewItem = new CNameValuePair( strName, intValue );

			intNewItemIndex = AddItemToList( clsNewItem, blnSelect );			
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return intNewItemIndex;
	}
	

	// ----------------------------------------------------------------------
	// Name: Clear
	// Abstract: Clear the list
	// ----------------------------------------------------------------------
	public void Clear( )
	{
		try
		{		
			m_dcmListItems.removeAllElements( );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: FindSortedIndex
	// Abstract: Given a name find its sorted location in the list.
	//			Use bisection method.
	// ----------------------------------------------------------------------
	private int FindSortedIndex( DefaultComboBoxModel dcmListItems, String strNewName )
	{
		int intNewItemIndex = 0;
		try
		{		
			int intStartIndex = 0;
			int intStopIndex = dcmListItems.getSize( ) - 1;	// 0 based
			int intMiddleIndex = 0;
			String strCurrentName = "";
			
			// Empty list?
			if( dcmListItems.getSize( ) > 0 )
			{
				// Bisect the list
				while( intStartIndex < intStopIndex )
				{
					// Find the middle
					intMiddleIndex = ( intStopIndex + intStartIndex ) / 2;
	
					// Get the middle text
					strCurrentName = dcmListItems.getElementAt( intMiddleIndex ).toString( );
	
					// Less than middle?
					if( strNewName.compareToIgnoreCase( strCurrentName ) < 0 ) 
					{
						// Yes, move the Stop index up
						intStopIndex = intMiddleIndex;
					}
					else
					{
						// No, move the Start index down
						intStartIndex = intMiddleIndex + 1;
					}
				}
				
				// Set new item index
				intNewItemIndex = intStartIndex;
	
				// 1 last compare.  Goes before or after current spot?
				strCurrentName = dcmListItems.getElementAt( intNewItemIndex ).toString( );
	
				// Insert after?
				if( strNewName.compareToIgnoreCase( strCurrentName ) >= 0 ) intNewItemIndex++;
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		
		// Return result		
		return intNewItemIndex;
	}


	// ----------------------------------------------------------------------
	// Name: GetItem
	// Abstract: Get the name value pair instance of the specified item
	// ----------------------------------------------------------------------
	public CNameValuePair GetItem( int intIndex )
	{
		CNameValuePair clsItem = null;
		try
		{	
			// Boundary check
			if( intIndex >= 0 && intIndex < m_dcmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dcmListItems.getElementAt( intIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return clsItem;
	}
	
	
	// ----------------------------------------------------------------------
	// Name: GetItemName
	// Abstract: Get the name of the specified item
	// ----------------------------------------------------------------------
	public String GetItemName( int intIndex )
	{
		String strItemName = "";
		try
		{	
			CNameValuePair clsItem = null;
			
			// Boundary check
			if( intIndex >= 0 && intIndex < m_dcmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dcmListItems.getElementAt( intIndex );
				
				// Get the name
				strItemName = clsItem.GetName( );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return strItemName;
	}
	

	// ----------------------------------------------------------------------
	// Name: GetItemValue
	// Abstract: Get the value of the specified item
	// ----------------------------------------------------------------------
	public int GetItemValue( int intIndex )
	{
		int intItemValue = 0;
		try
		{	
			CNameValuePair clsItem = null;
			
			// Boundary check
			if( intIndex >= 0 && intIndex < m_dcmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dcmListItems.getElementAt( intIndex );
				
				// Get the Value
				intItemValue = clsItem.GetValue( );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return intItemValue;
	}
	

	// ----------------------------------------------------------------------
    // Name: SetActionListener
    // Abstract: Sets the action listener of the ComboBox
    // ----------------------------------------------------------------------
	public JComboBox GetJavaComboBox( )
	{
	    return m_cmbList;
	}


	// ----------------------------------------------------------------------
	// Name: GetQuiet
	// Abstract: Are we firing mouse click events?
	// ----------------------------------------------------------------------
	public boolean GetQuiet( )
	{
		return m_blnQuiet;
	}


	// ----------------------------------------------------------------------
	// Name: GetSelectedIndex
	// Abstract: To sort or not to sort.  That is the question.
	// ----------------------------------------------------------------------
	public int GetSelectedIndex( )
	{
		return m_cmbList.getSelectedIndex( );
	}


	// ----------------------------------------------------------------------
	// Name: GetSelectedItem
	// Abstract: Get the name value pair instance of the selected item
	// ----------------------------------------------------------------------
	public CNameValuePair GetSelectedItem( )
	{
		CNameValuePair clsSelectedItem = null;
		try
		{	
			int intSelectedIndex = m_cmbList.getSelectedIndex( );
			clsSelectedItem = GetItem( intSelectedIndex );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return clsSelectedItem;
	}


	// ----------------------------------------------------------------------
	// Name: GetSelectedItemName
	// Abstract: Get the name of the selected item
	// ----------------------------------------------------------------------
	public String GetSelectedItemName( )
	{
		String strSelectedItemName = "";
		try
		{	
			int intSelectedIndex = m_cmbList.getSelectedIndex( );
			strSelectedItemName = GetItemName( intSelectedIndex );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return strSelectedItemName;
	}


	// ----------------------------------------------------------------------
	// Value: GetSelectedItemValue
	// Abstract: Get the Value of the selected item
	// ----------------------------------------------------------------------
	public int GetSelectedItemValue( )
	{
		int intSelectedItemValue = 0;
		try
		{	
			int intSelectedIndex = m_cmbList.getSelectedIndex( );
			intSelectedItemValue = GetItemValue( intSelectedIndex );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
		// Return result
		return intSelectedItemValue;
	}


	// ----------------------------------------------------------------------
	// Name: GetSorted
	// Abstract: To sort or not to sort.  That is the question.
	// ----------------------------------------------------------------------
	public boolean GetSorted( )
	{
		return m_blnSorted;
	}


	// ----------------------------------------------------------------------
	// Name: HighlightNextInList
	// Abstract: Highlight next closest item in the list
	// ----------------------------------------------------------------------
	private void HighlightNextInList( int intIndex )
	{
		try
		{
			int intListItemsCount = m_dcmListItems.getSize( );
			
	   		// Are there any items in the list( might have deleted the last one )?
			if( intListItemsCount > 0 )
			{
				// Yes, did we delete the last item?
				if( intIndex >= intListItemsCount )
				{
		            // Yes, move the index to the new last item
					intIndex = intListItemsCount - 1;
				}
	
		        // Select next closest item
				m_cmbList.setSelectedIndex( intIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Center, set the title, etc
	// ----------------------------------------------------------------------
	private void Initialize( )
	{
		try
		{	
			// Layout
			BorderLayout blDialog = new BorderLayout( );
			this.setLayout( blDialog );		

			// List model
			m_dcmListItems = new DefaultComboBoxModel( );
			
			// Combo Box
			m_cmbList = new JComboBox( m_dcmListItems );
			m_cmbList.addMouseListener( this );
			m_cmbList.setBackground( Color.white );
			
			// Add to component			
			this.add( m_cmbList );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: Length
	// Abstract: how big is the list?
	// ----------------------------------------------------------------------
	public int Length( )
	{
		return m_dcmListItems.getSize( );
	}



	// ----------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Handle mouseListener events
	// ----------------------------------------------------------------------
	public void mouseClicked( MouseEvent meSource )
	{
		try
		{	
			MouseListener amlComboBox[] = this.getMouseListeners( );
			int intIndex = 0;
			
			// Was it the JList?
			if( meSource.getSource( ) == m_cmbList )
			{
				// Yes
				
				// Change the source to the ListBox
				meSource.setSource( this );

				// Kick the event up to all listeners of the ListBox
				for( intIndex = 0; intIndex < amlComboBox.length; intIndex++ )
				{
					amlComboBox[ intIndex ].mouseClicked( meSource );
				}
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}


	public void mouseEntered( MouseEvent meSource ) { }



	public void mouseExited( MouseEvent meSource ) { }



	// Don't care
	public void mousePressed( MouseEvent meSource ) { }

	public void mouseReleased( MouseEvent meSource ) { }
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// MouseListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: ReloadList
	// Abstract: Reload the list in sorted order.
	// ----------------------------------------------------------------------
	private void ReloadList( )
	{
		try
		{
			DefaultComboBoxModel dlmSortedListItems = new DefaultComboBoxModel( );
			int intIndex = 0;
			CNameValuePair clsItem = null;
			String strName = "";
			int intNewItemIndex = 0;
			
			// Create a new list and add all items to it 1 at a time in order
			for( intIndex = 0; intIndex < m_dcmListItems.getSize( ); intIndex++ )
			{
				// Next item
				clsItem = ( CNameValuePair )m_dcmListItems.getElementAt( intIndex );
				
				// Name
				strName = clsItem.GetName( );

				// Find out where it should go
				intNewItemIndex = FindSortedIndex( dlmSortedListItems, strName );
				
				// Insert at the specified location
				dlmSortedListItems.insertElementAt( clsItem, intNewItemIndex );
			}
			
			// Clear old list and set to null
			m_dcmListItems.removeAllElements( );
			m_dcmListItems = null;
			
			// Set old list to new sorted list
			m_dcmListItems = dlmSortedListItems;
			m_cmbList.setModel( m_dcmListItems );
			
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	
	// ----------------------------------------------------------------------
	// Name: RemoveAt
	// Abstract: Remove the item at the specified index.
	// ----------------------------------------------------------------------
	public void RemoveAt( int intIndex )
	{
		try
		{	
			// Boundary check
			if( intIndex >= 0 && intIndex < m_dcmListItems.getSize( ) )
			{
				// Remove it
				m_dcmListItems.removeElementAt( intIndex );
				
				// Select next item
				HighlightNextInList( intIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	
	// ----------------------------------------------------------------------
    // Name: SetActionListener
    // Abstract: Sets the action listener of the ComboBox
    // ----------------------------------------------------------------------
	public void SetActionListener(ActionListener alParent)
	{
	    try
	    {
	        m_cmbList.addActionListener( alParent );
	    }
	    catch ( Exception excError )
	    {
	        CUtilities.WriteLog( excError );
	    }
	}
	
	public void SetEnabled(boolean blnEnabled)
	{
	    try
	    {
	        m_cmbList.setEnabled(blnEnabled);
	        m_cmbList.setEditable(blnEnabled);
	    }
	    catch (Exception excError)
	    {
	        CUtilities.WriteLog(excError);
	    }
	}
	// ----------------------------------------------------------------------
	// Name: SetQuiet
	// Abstract: Fire/Don't fire events.
	// ----------------------------------------------------------------------
	public void SetQuiet( boolean blnQuiet )
	{
		m_blnQuiet = blnQuiet;
	}
	// ----------------------------------------------------------------------
	// Name: SetSelectedIndex
	// Abstract: Select the specified item
	// ----------------------------------------------------------------------
	public void SetSelectedIndex( int intSelectedIndex )
	{
		try
		{	
			// Boundary check
			if( intSelectedIndex >= -1 && intSelectedIndex < m_dcmListItems.getSize( ) )
			{
				// Select it
				m_cmbList.setSelectedIndex( intSelectedIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: SetSorted
	// Abstract: To sort or not to sort.  That is the question.
	// ----------------------------------------------------------------------
	public void SetSorted( boolean blnSorted )
	{
		try
		{
			// Start sorting?
			if( m_blnSorted == false && blnSorted == true )
			{
				// Reload the list because it maybe unsorted
				ReloadList( );
			}
			
			m_blnSorted = blnSorted;
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
}

