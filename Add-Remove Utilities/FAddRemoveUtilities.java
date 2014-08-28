// --------------------------------------------------------------------------------
// Name: CAddRemoveUtilities
// Abstract: Screen used to install/remove packages that Aircrack-NGUI needs to run
// --------------------------------------------------------------------------------

// Import
import java.awt.event.ActionEvent;

// Imports
import javax.swing.*;
import java.util.*;

public class FAddRemoveUtilities extends CAircrackDialog
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CListBox m_lstNotInstalled = null;
	private CListBox m_lstInstalled = null;
	private JButton m_btnAdd = null;
	private JButton m_btnAddAll = null;
	private JButton m_btnRemove = null;
	private JButton m_btnRemoveAll = null;
	
	// --------------------------------------------------------------------------------
	// Name: CAddRemoveUtilities
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FAddRemoveUtilities()
	{
		super("Add/Remove Utilities", 480, 400, false, false, "");
		try
		{
			
			AddControls();
			PopulateListBoxes();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the screen
	// --------------------------------------------------------------------------------
	private void AddControls()
	{
		try
		{
			CUtilities.AddLabel(m_cntControlContainer, "Not Installed:", 10, 10);
			m_lstNotInstalled = CUtilities.AddListBox(m_cntControlContainer, null, 25, 10, 300, 225);
			CUtilities.AddLabel(m_cntControlContainer, "Installed:", 10, 245);
			m_lstInstalled = CUtilities.AddListBox(m_cntControlContainer, null, 25, 245, 300, 225);
			m_btnAdd = CUtilities.AddButton(m_cntControlContainer, this, "Add", 330, 20, 25, 100);
			m_btnAddAll = CUtilities.AddButton(m_cntControlContainer, this, "Add All", 330, 125, 25, 100);
			m_btnRemove = CUtilities.AddButton(m_cntControlContainer, this, "Remove", 330, 245, 25, 100);
			m_btnRemoveAll = CUtilities.AddButton(m_cntControlContainer, this, "Remove All", 330, 350, 25, 120);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulateListBoxes
	// Abstract: Populates the listboxes with packages that are either installed or not
	//			 installed.
	// --------------------------------------------------------------------------------
	private void PopulateListBoxes()
	{
		try
		{
			CLocalMachine clsThisComputer = CGlobals.clsLocalMachine;
			
			m_lstInstalled.Clear();
			m_lstNotInstalled.Clear();
			
			ArrayList<CLocalMachine.CPackageStatus> lstPackageStatuses = clsThisComputer.GetPackageStatuses();
			
			for (int intIndex = 0; intIndex < lstPackageStatuses.size(); intIndex += 1)
			{
				CLocalMachine.CPackageStatus clsCurrentPackage = lstPackageStatuses.get(intIndex);
				
				if (clsCurrentPackage.blnInstalled)
					m_lstInstalled.AddItemToList(clsCurrentPackage.strPackageName, 0);
				else
					m_lstNotInstalled.AddItemToList(clsCurrentPackage.strPackageName, 0);
			}
			
			if (m_lstInstalled.Length() > 0)
				m_lstInstalled.SetSelectedIndex( 0 );
			if (m_lstNotInstalled.Length() > 0)
				m_lstNotInstalled.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks, etc., etc.
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed(aeSource);
		try
		{
			if ( aeSource.getSource( ) == m_btnAdd )				btnAdd_Click();
			else if ( aeSource.getSource( ) == m_btnAddAll )		btnAddAll_Click();
			else if ( aeSource.getSource( ) == m_btnRemove )		btnRemove_Click();
			else if ( aeSource.getSource( ) == m_btnRemoveAll )		btnRemoveAll_Click();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnAdd_Click
	// Abstract: Installs the selected package
	// --------------------------------------------------------------------------------
	private void btnAdd_Click()
	{
		try
		{
			int intSelectedIndex = m_lstNotInstalled.GetSelectedIndex( );
			if (intSelectedIndex > -1)
			{
				String strPackageName = m_lstNotInstalled.GetSelectedItemName( );
				int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to install " + strPackageName + "?", "Install Package", JOptionPane.YES_NO_OPTION);
				if (intResult == JOptionPane.YES_OPTION)
				{
					CLocalMachine clsThisPC = CGlobals.clsLocalMachine;
					clsThisPC.ManagePackages("install", new String[] {strPackageName});
					PopulateListBoxes();
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnAddAll_Click
	// Abstract: Installs all not-installed packages
	// --------------------------------------------------------------------------------
	private void btnAddAll_Click()
	{
		try
		{
			if (m_lstNotInstalled.Length( ) > 0)
			{
				int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to install all packages?", "Install Package", JOptionPane.YES_NO_OPTION);
				if (intResult == JOptionPane.YES_OPTION)
				{
					String astrPackages[] = new String[m_lstNotInstalled.Length()];
					for (int intIndex = 0; intIndex < m_lstNotInstalled.Length(); intIndex += 1)
						astrPackages[intIndex] = m_lstNotInstalled.GetItemName(intIndex);
					CLocalMachine clsThisPC = CGlobals.clsLocalMachine;
					clsThisPC.ManagePackages("install", astrPackages);
					PopulateListBoxes();
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnRemove_Click
	// Abstract: Removes the selected package
	// --------------------------------------------------------------------------------
	private void btnRemove_Click()
	{
		try
		{
			int intSelectedIndex = m_lstInstalled.GetSelectedIndex( );
			if (intSelectedIndex > -1)
			{
				String strPackageName = m_lstInstalled.GetSelectedItemName( );
				int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + strPackageName + "?", "Remove Package", JOptionPane.YES_NO_OPTION);
				if (intResult == JOptionPane.YES_OPTION)
				{
					CLocalMachine clsThisPC = CGlobals.clsLocalMachine;
					clsThisPC.ManagePackages("purge", new String[] {strPackageName});
					PopulateListBoxes();
				}
					
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnRemoveAll_Click
	// Abstract: Removes all installed packages
	// --------------------------------------------------------------------------------
	private void btnRemoveAll_Click()
	{
		if (m_lstInstalled.Length( ) > 0)
		{
			int intResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove all packages?", "Remove Package", JOptionPane.YES_NO_OPTION);
			if (intResult == JOptionPane.YES_OPTION)
			{
				String astrPackages[] = new String[m_lstInstalled.Length()];
				for (int intIndex = 0; intIndex < m_lstInstalled.Length(); intIndex += 1)
					astrPackages[intIndex] = m_lstInstalled.GetItemName(intIndex);
				CLocalMachine clsThisPC = CGlobals.clsLocalMachine;
				clsThisPC.ManagePackages("purge", astrPackages);
				PopulateListBoxes();
			}
		}
	}
	
}