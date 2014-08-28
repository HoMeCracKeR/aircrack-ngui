// ----------------------------------------------------------------------
//	Name: Pat Callahan
//	Class: CListBox
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// ----------------------------------------------------------------------
// Name: CListBox
// Abstract: Make a listbox class that combines the list, list model
//			and scrollbars all into one.  Add event firing too.
// ----------------------------------------------------------------------
public class CListBox extends javax.swing.JComponent implements MouseListener
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
	private DefaultListModel m_dlmListItems = null;
	private JList m_lstList = null;
	private JScrollPane m_scpList = null;
	
	private boolean m_blnSorted = true;				// Sort the list by default
	private boolean m_blnQuiet = false;				// fire/don't fire events
		

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------


	// ----------------------------------------------------------------------
	// Name: CListBox
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public CListBox( )
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
				intNewItemIndex = FindSortedIndex( m_dlmListItems, clsNewItem.GetName( ) );
			}
			else
			{
				// No, add it to the end
				intNewItemIndex = m_dlmListItems.getSize( );	
			}
			
			// Insert at the specified location
			m_dlmListItems.insertElementAt( clsNewItem, intNewItemIndex );
			
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
			m_dlmListItems.clear( );
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
	private int FindSortedIndex( DefaultListModel dlmListItems, String strNewName )
	{
		int intNewItemIndex = 0;
		try
		{		
			int intStartIndex = 0;
			int intStopIndex = dlmListItems.getSize( ) - 1;	// 0 based
			int intMiddleIndex = 0;
			String strCurrentName = "";
			
			// Empty list?
			if( dlmListItems.isEmpty( ) == false )
			{
				// Bisect the list
				while( intStartIndex < intStopIndex )
				{
					// Find the middle
					intMiddleIndex = ( intStopIndex + intStartIndex ) / 2;
	
					// Get the middle text
					strCurrentName = dlmListItems.getElementAt( intMiddleIndex ).toString( );
	
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
				strCurrentName = dlmListItems.getElementAt( intNewItemIndex ).toString( );
	
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
			if( intIndex >= 0 && intIndex < m_dlmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dlmListItems.getElementAt( intIndex );
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
			if( intIndex >= 0 && intIndex < m_dlmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dlmListItems.getElementAt( intIndex );
				
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
			if( intIndex >= 0 && intIndex < m_dlmListItems.getSize( ) )
			{
				// Get the selected item
				clsItem = ( CNameValuePair ) m_dlmListItems.getElementAt( intIndex );
				
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
	// Name: GetQuiet
	// Abstract: Are we firing mouse click events?
	// ----------------------------------------------------------------------
	public boolean GetQuiet( )
	{
		return m_blnQuiet;
	}
	

	// ----------------------------------------------------------------------
	// Name: GetSelectedIndex
	// Abstract: Index of the first selected item.
	// ----------------------------------------------------------------------
	public int GetSelectedIndex( )
	{
		return m_lstList.getSelectedIndex( );
	}


	// ----------------------------------------------------------------------
	// Name: GetSelectedIndices
	// Abstract: Indexes of all selected items.
	// ----------------------------------------------------------------------
	public int[] GetSelectedIndices( )
	{
		return m_lstList.getSelectedIndices( );
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
			int intSelectedIndex = m_lstList.getSelectedIndex( );
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
			int intSelectedIndex = m_lstList.getSelectedIndex( );
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
			int intSelectedIndex = m_lstList.getSelectedIndex( );
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
	// Name: GetSelectionMode
	// Abstract: Single, Single Interval or Multiple Intervale.
	// ----------------------------------------------------------------------
	public int GetSelectionMode( )
	{
		// Return result
		return m_lstList.getSelectionMode( );
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
	public void HighlightNextInList( int intIndex )
	{
		try
		{
			int intListItemsCount = m_dlmListItems.getSize( );
			
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
				m_lstList.setSelectedIndex( intIndex );
				m_lstList.ensureIndexIsVisible( intIndex );
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
			m_dlmListItems = new DefaultListModel( );
			
			// List
			m_lstList = new JList( m_dlmListItems );
			m_lstList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
			m_lstList.addMouseListener( this );
			
			// Scroll Pane
			m_scpList = new JScrollPane( m_lstList );
			
			// Add to component			
			this.add( m_scpList );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: IsItemSelected
	// Abstract: is the specified item selected?
	// ----------------------------------------------------------------------
	public boolean IsItemSelected( int intIndex )
	{
		return m_lstList.isSelectedIndex( intIndex );
	}


	// ----------------------------------------------------------------------
	// Name: Length
	// Abstract: how big is the list?
	// ----------------------------------------------------------------------
	public int Length( )
	{
		return m_dlmListItems.size( );
	}

	
	// ----------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Handle mouseListener events
	// ----------------------------------------------------------------------
	public void mouseClicked( MouseEvent meSource )
	{
		try
		{	
			MouseListener amlListBox[] = this.getMouseListeners( );
			int intIndex = 0;
			
			// Was it the JList?
			if( meSource.getSource( ) == m_lstList )
			{
				// Yes
				
				// Change the source to the ListBox
				meSource.setSource( this );

				// Kick the event up to all listeners of the ListBox
				for( intIndex = 0; intIndex < amlListBox.length; intIndex++ )
				{
					amlListBox[ intIndex ].mouseClicked( meSource );
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
	// Name: ReloadList
	// Abstract: Reload the list in sorted order.
	// ----------------------------------------------------------------------
	private void ReloadList( )
	{
		try
		{
			DefaultListModel dlmSortedListItems = new DefaultListModel( );
			int intIndex = 0;
			CNameValuePair clsItem = null;
			String strName = "";
			int intNewItemIndex = 0;
			
			// Create a new list and add all items to it 1 at a time in order
			for( intIndex = 0; intIndex < m_dlmListItems.getSize( ); intIndex++ )
			{
				// Next item
				clsItem = ( CNameValuePair )m_dlmListItems.getElementAt( intIndex );
				
				// Name
				strName = clsItem.GetName( );

				// Find out where it should go
				intNewItemIndex = FindSortedIndex( dlmSortedListItems, strName );
				
				// Insert at the specified location
				dlmSortedListItems.insertElementAt( clsItem, intNewItemIndex );
			}
			
			// Clear old list and set to null
			m_dlmListItems.clear( );
			m_dlmListItems = null;
			
			// Set old list to new sorted list
			m_dlmListItems = dlmSortedListItems;
			m_lstList.setModel( m_dlmListItems );
			
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
			if( intIndex >= 0 && intIndex < m_dlmListItems.getSize( ) )
			{
				// Remove it
				m_dlmListItems.removeElementAt( intIndex );
				
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
	// ----------------------------------------------------------------------
	// MouseListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

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
			if( intSelectedIndex >= -1 && intSelectedIndex < m_dlmListItems.getSize( ) )
			{
				// Select it
				m_lstList.setSelectedIndex( intSelectedIndex );
				m_lstList.ensureIndexIsVisible( intSelectedIndex );
			}
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: SetSelectedIndices
	// Abstract: Select the specified items
	// ----------------------------------------------------------------------
	public void SetSelectedIndices( int aintIndicesToSelect[ ] )
	{
		try
		{	
			// Select all
			m_lstList.setSelectedIndices( aintIndicesToSelect );
			
			// Make sure first item is visible
			m_lstList.ensureIndexIsVisible( aintIndicesToSelect[ 0 ] );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: SetSelectedIndices
	// Abstract: Select the specified items
	// ----------------------------------------------------------------------
	public void AddSelectedIndex( int intNewSelectionIndex )
	{
		try
		{
			m_lstList.addSelectionInterval(intNewSelectionIndex, intNewSelectionIndex);
		}
		catch ( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: AddSelectedItem
	// Abstract: Finds the item by name and sets the index
	// ----------------------------------------------------------------------
	public void AddSelectedItem( String strName )
	{
		try
		{
			int intItemIndex = FindSortedIndex( m_dlmListItems, strName );
			AddSelectedIndex( intItemIndex );
		}
		catch ( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: SetSelectionMode
	// Abstract: Single, Single Interval or Multiple Intervale.
	// ----------------------------------------------------------------------
	public void SetSelectionMode( int intNewSelectionMode )
	{
		try
		{		
			m_lstList.setSelectionMode( intNewSelectionMode );
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
	
	public void ClearSelection( )
	{
		try
		{
			m_lstList.clearSelection( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

}

