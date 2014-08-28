import java.awt.*; // Container
import java.awt.event.*; // MouseAdapter 
import java.util.*;
import javax.swing.*; // JTable
import javax.swing.table.*; // DefaultTableModel

// --------------------------------------------------------------------------------
// Name: CTable
// Abstract: Common functionality for a table
// --------------------------------------------------------------------------------
public class CTable
{
	
	private CustomTableModel m_dtmTableModel = null;
	private JTable m_tblTable = null;
	private JScrollPane m_scpScrollPane = null;
	private Container m_conParent = null;
	private int m_intTop = 0;
	private int m_intLeft = 0;
	private int m_intWidth = 25;
	private int m_intHeight = 25;
	private SpringLayout m_splParent = null;
	private JPopupMenu m_pumPopupMenu = null;
	private ArrayList<CColumnReadOnlyPair> m_lstReadOnlyRules = new ArrayList<CColumnReadOnlyPair>( );
	
	private class CustomTableModel extends DefaultTableModel
	{
		protected final static long serialVersionUID = 1L;
		
		public CustomTableModel(int intRowCount, int intColumnCount)
		{
			super(intRowCount, intColumnCount);
		}
		
		public boolean isCellEditable(int intRow, int intColumn)
		{
			boolean blnReadOnly = false;
			for ( int intIndex = 0; intIndex < m_lstReadOnlyRules.size( ); intIndex += 1 )
				if ( m_lstReadOnlyRules.get(intIndex).GetColumnIndex( ) == intColumn )
					blnReadOnly = m_lstReadOnlyRules.get(intIndex).GetReadOnly( );
			return !blnReadOnly;
		}
	}
	
	private class CColumnReadOnlyPair
	{
		private int m_intColumnIndex = 0;
		private boolean m_blnReadOnly = false;
		
		public CColumnReadOnlyPair( int intColumnIndex, boolean blnReadOnly )
		{
			m_intColumnIndex = intColumnIndex;
			m_blnReadOnly = blnReadOnly;
		}
		
		public int GetColumnIndex( )
		{
			return m_intColumnIndex;
		}
		
		public void SetReadOnly( boolean blnReadOnly )
		{
			m_blnReadOnly = blnReadOnly;
		}
		
		public boolean GetReadOnly( )
		{
			return m_blnReadOnly;
		}
	}
		
	public CTable(Container conParent)
	{
		try
		{
			m_dtmTableModel = new CustomTableModel(0, 1);
			m_dtmTableModel.setColumnIdentifiers(new String[] {"Col 1"});
			m_tblTable = new JTable(m_dtmTableModel);
			m_scpScrollPane = new JScrollPane(m_tblTable);
			m_tblTable.setFillsViewportHeight( true );
			m_conParent = conParent;
			m_conParent.add(m_scpScrollPane);
			m_splParent = (SpringLayout)m_conParent.getLayout();
			m_splParent.getConstraints( m_scpScrollPane ).setX( Spring.constant( m_intLeft ) );
			m_splParent.getConstraints( m_scpScrollPane ).setY( Spring.constant( m_intTop ) );
			m_splParent.getConstraints( m_scpScrollPane ).setHeight( Spring.constant( m_intHeight ) );
			m_splParent.getConstraints( m_scpScrollPane ).setWidth( Spring.constant( m_intWidth ) );
			m_tblTable.setAutoCreateColumnsFromModel( false );
			AddMouseAdapterToTable( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public CTable(Container conParent, String astrColumns[])
	{
		try
		{
			m_dtmTableModel = new CustomTableModel(0, astrColumns.length);
			m_dtmTableModel.setColumnIdentifiers(astrColumns);
			m_tblTable = new JTable(m_dtmTableModel);
			m_scpScrollPane = new JScrollPane(m_tblTable);
			m_tblTable.setFillsViewportHeight( true );
			m_conParent = conParent;
			m_conParent.add(m_scpScrollPane);
			m_splParent = (SpringLayout)m_conParent.getLayout();
			m_splParent.getConstraints( m_scpScrollPane ).setX( Spring.constant( m_intLeft ) );
			m_splParent.getConstraints( m_scpScrollPane ).setY( Spring.constant( m_intTop ) );
			m_splParent.getConstraints( m_scpScrollPane ).setHeight( Spring.constant( m_intHeight ) );
			m_splParent.getConstraints( m_scpScrollPane ).setWidth( Spring.constant( m_intWidth ) );
			m_tblTable.setAutoCreateColumnsFromModel( false );
			for ( int intIndex = 0; intIndex < astrColumns.length; intIndex += 1 )
				m_lstReadOnlyRules.add(new CColumnReadOnlyPair(intIndex, false));
			AddMouseAdapterToTable( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public CTable(Container conParent, String astrColumns[], Object aobjFirstRow[])
	{
		try
		{
			m_dtmTableModel = new CustomTableModel(0, astrColumns.length);
			m_dtmTableModel.setColumnIdentifiers(astrColumns);
			m_tblTable = new JTable(m_dtmTableModel);
			m_scpScrollPane = new JScrollPane(m_tblTable);
			m_tblTable.setFillsViewportHeight( true );
			m_conParent = conParent;
			m_conParent.add(m_scpScrollPane);
			m_splParent = (SpringLayout)m_conParent.getLayout();
			m_splParent.getConstraints( m_scpScrollPane ).setX( Spring.constant( m_intLeft ) );
			m_splParent.getConstraints( m_scpScrollPane ).setY( Spring.constant( m_intTop ) );
			m_splParent.getConstraints( m_scpScrollPane ).setHeight( Spring.constant( m_intHeight ) );
			m_splParent.getConstraints( m_scpScrollPane ).setWidth( Spring.constant( m_intWidth ) );
			m_tblTable.setAutoCreateColumnsFromModel( false );

			for ( int intIndex = 0; intIndex < astrColumns.length; intIndex += 1 )
				m_lstReadOnlyRules.add(new CColumnReadOnlyPair(intIndex, false));
			
			if ( aobjFirstRow != null )
				m_dtmTableModel.addRow(aobjFirstRow);
			
			AddMouseAdapterToTable( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public CTable(Container conParent, String astrColumns[], Object aaobjRows[][])
	{
		try
		{
			m_dtmTableModel = new CustomTableModel(0, astrColumns.length);
			m_dtmTableModel.setColumnIdentifiers(astrColumns);
			m_tblTable = new JTable(m_dtmTableModel);
			m_scpScrollPane = new JScrollPane(m_tblTable);
			m_tblTable.setFillsViewportHeight( true );
			m_conParent = conParent;
			m_conParent.add(m_scpScrollPane);
			m_splParent = (SpringLayout)m_conParent.getLayout();
			m_splParent.getConstraints( m_scpScrollPane ).setX( Spring.constant( m_intLeft ) );
			m_splParent.getConstraints( m_scpScrollPane ).setY( Spring.constant( m_intTop ) );
			m_splParent.getConstraints( m_scpScrollPane ).setHeight( Spring.constant( m_intHeight ) );
			m_splParent.getConstraints( m_scpScrollPane ).setWidth( Spring.constant( m_intWidth ) );
			m_tblTable.setAutoCreateColumnsFromModel( false );
			
			for ( int intIndex = 0; intIndex < astrColumns.length; intIndex += 1 )
				m_lstReadOnlyRules.add(new CColumnReadOnlyPair(intIndex, false));
			
			if ( aaobjRows != null )
			{
				for ( int intIndex = 0; intIndex < aaobjRows.length; intIndex += 1)
					m_dtmTableModel.addRow(aaobjRows[intIndex]);
			}
			
			AddMouseAdapterToTable( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	private void AddMouseAdapterToTable( )
	{
		try
		{
			m_tblTable.addMouseListener( new MouseAdapter( ){
				
				private void PerformPopupMenuClick( MouseEvent meSource )
				{
					try
					{
						int intRowIndex = m_tblTable.rowAtPoint( meSource.getPoint( ) );
						if ( intRowIndex >= 0 && intRowIndex < m_tblTable.getRowCount( ) )
							m_tblTable.setRowSelectionInterval(intRowIndex, intRowIndex);
						else
							m_tblTable.clearSelection( );
						
						intRowIndex = m_tblTable.getSelectedRow( );
						if ( intRowIndex < 0 )
							return;
						if ( meSource.isPopupTrigger( ) && meSource.getComponent( ) instanceof JTable )
						{
							if ( m_pumPopupMenu != null )
							{
								m_pumPopupMenu.show( meSource.getComponent( ), meSource.getX( ), meSource.getY( ) );
							}
						}
					}
					catch (Exception excError)
					{
						CUtilities.WriteLog(excError);
					}
				}
				
				@Override
				public void mouseReleased(MouseEvent meSource)
				{
					try
					{
						PerformPopupMenuClick( meSource );
					}
					catch (Exception excError)
					{
						CUtilities.WriteLog(excError);
					}
				}
				
				@Override
				public void mousePressed(MouseEvent meSource)
				{
					try
					{
						PerformPopupMenuClick( meSource );
					}
					catch (Exception excError)
					{
						CUtilities.WriteLog(excError);
					}
				}
			});
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void AddRow(Object aobjNewRow[])
	{
		try
		{
			m_dtmTableModel.addRow(aobjNewRow);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void DeleteRow(int intRowIndex)
	{
		try
		{
			m_dtmTableModel.removeRow( intRowIndex );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void ClearRows( )
	{
		try
		{
			while ( m_dtmTableModel.getRowCount() > 0 ) DeleteRow( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetVisible(boolean blnVisible)
	{
		try
		{
			m_scpScrollPane.setVisible( blnVisible );
			m_tblTable.setVisible( blnVisible );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetLeft(int intNewLeft)
	{
		try
		{
			m_intLeft = intNewLeft;
			m_splParent.getConstraints( m_scpScrollPane ).setX( Spring.constant( m_intLeft ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetTop(int intNewTop)
	{
		try
		{
			m_intTop = intNewTop;
			m_splParent.getConstraints( m_scpScrollPane ).setY( Spring.constant( m_intTop ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetWidth(int intNewWidth)
	{
		try
		{
			m_intWidth = intNewWidth;
			m_splParent.getConstraints( m_scpScrollPane ).setWidth( Spring.constant( m_intWidth ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetHeight(int intNewHeight)
	{
		try
		{
			m_intHeight = intNewHeight;
			m_splParent.getConstraints( m_scpScrollPane ).setHeight( Spring.constant( m_intHeight ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public boolean ColumnExists(String strColumnName)
	{
		boolean blnExists = false;
		try
		{
			int intColumnIndex = m_dtmTableModel.findColumn(strColumnName);
			
			if ( intColumnIndex != -1 )
				blnExists = true;
			else
				blnExists = false;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnExists;
	}
	
	public void AddColumn(String strColumnName, int intColumnIndex)
	{
		try
		{
			m_dtmTableModel.addColumn(strColumnName);
			m_tblTable.moveColumn(m_dtmTableModel.findColumn(strColumnName), intColumnIndex);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void DeleteColumn(String strColumnName)
	{
		try
		{
			int intColumnIndex = m_dtmTableModel.findColumn(strColumnName);
			JOptionPane.showMessageDialog(null, intColumnIndex);
			TableColumn tbcTableColumn = m_tblTable.getColumnModel().getColumn(intColumnIndex);
			m_tblTable.removeColumn(tbcTableColumn);
		}
		catch (Exception excError)
		{
			JOptionPane.showMessageDialog(null, excError.getMessage());
			CUtilities.WriteLog(excError);
		}
	}
	
	public int GetSelectedRow( )
	{
		int intRowIndex = -1;
		try
		{
			intRowIndex = m_tblTable.getSelectedRow( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intRowIndex;
	}
	
	public Object GetCellValue(int intRowIndex, int intColumnIndex)
	{
		Object objCellValue = null;
		try
		{
			objCellValue = m_tblTable.getValueAt(intRowIndex, intColumnIndex);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return objCellValue;
	}
	
	public Object GetCellValue(int intRowIndex, String strColumnName)
	{
		Object objCellValue = null;
		try
		{
			objCellValue = GetCellValue(intRowIndex, m_dtmTableModel.findColumn(strColumnName));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return objCellValue;
	}
	
	public void AddPopupMenu( JPopupMenu pumPopupMenu )
	{
		try
		{
			m_pumPopupMenu = pumPopupMenu;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	public int FindRowIndexByColumnValue(String strColumnName, String strSearchValue)
	{
		int intRowIndex = -1;
		
		try
		{
			int intColumnIndex = m_dtmTableModel.findColumn(strColumnName);
			String strTempValue = "";
			if ( intColumnIndex >= 0 )
			{
				for ( int intIndex = 0; intIndex < m_tblTable.getRowCount(); intIndex += 1 )
				{
					strTempValue = String.valueOf(m_tblTable.getValueAt(intIndex, intColumnIndex));
					if ( strTempValue.equals( strSearchValue ) )
					{
						intRowIndex = intIndex;
						break;
					}
				}
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		
		return intRowIndex;
	}
	
	public void SetCellValueAt(int intRowIndex, String strColumnName, String strColumnValue)
	{
		try
		{
			int intColumnIndex = m_dtmTableModel.findColumn( strColumnName );
			if ( intColumnIndex >= 0 )
				m_dtmTableModel.setValueAt(strColumnValue, intRowIndex, intColumnIndex);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public int GetRowCount( )
	{
		int intRowCount = 0;
		
		try
		{
			if ( m_dtmTableModel != null )
				intRowCount = m_dtmTableModel.getRowCount();
			else
				intRowCount = 0;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		
		return intRowCount;
	}
	
	public void SetColumnWidth(String strColumnName, int intColumnWidth)
	{
		try
		{
			int intColumnIndex = m_dtmTableModel.findColumn(strColumnName);
			TableColumn clmDesignatedColumn = null;
			if ( intColumnIndex >= 0 )
			{
				clmDesignatedColumn = m_tblTable.getColumnModel().getColumn(intColumnIndex);
				clmDesignatedColumn.setPreferredWidth(intColumnWidth);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetReadOnly(boolean blnNewReadOnly)
	{
		try
		{
			for ( int intIndex = 0; intIndex < m_lstReadOnlyRules.size( ); intIndex += 1 )
				m_lstReadOnlyRules.get( intIndex ).SetReadOnly( blnNewReadOnly );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetReadOnly(boolean blnNewReadOnly, String strColumnName)
	{
		try
		{
			int intColumnIndex = -1;
			for ( int intIndex = 0; intIndex < m_tblTable.getColumnCount( ); intIndex += 1 )
			{
				if ( GetColumnName( intIndex ).equals( strColumnName ) )
				{
					intColumnIndex = intIndex;
					break;
				}
			}
			
			for ( int intIndex = 0; intIndex < m_lstReadOnlyRules.size( ); intIndex += 1 )
			{
				if ( intColumnIndex == intIndex )
					m_lstReadOnlyRules.get( intIndex ).SetReadOnly( blnNewReadOnly );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public int GetColumnCount( )
	{
		return m_dtmTableModel.getColumnCount( );
	}
	
	public String GetColumnName( int intColumnIndex )
	{
		return m_dtmTableModel.getColumnName( intColumnIndex );
	}
	
	public void SetEnabled( boolean blnEnabled )
	{
		m_tblTable.setEnabled( blnEnabled );
	}
	
	public void SetSelectedIndex( int intIndex )
	{
		try
		{
			if ( intIndex < 0 ) intIndex = 0;
			if ( intIndex > GetRowCount( ) ) intIndex = GetRowCount( ) - 1;
		
			m_tblTable.setRowSelectionInterval(intIndex, intIndex);
			
			m_tblTable.scrollRectToVisible(m_tblTable.getCellRect(intIndex, 0, true));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	public void SetMouseListener( MouseListener meListener )
	{
		m_tblTable.addMouseListener( meListener );
	}
	
	public JTable GetTable( )
	{
		return m_tblTable;
	}
	
} 