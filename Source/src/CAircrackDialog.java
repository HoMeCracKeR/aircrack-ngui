
import javax.swing.*;

import org.w3c.dom.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CAircrackDialog extends JDialog implements ActionListener, WindowListener
{

	protected Container m_cntControlContainer = null;
	protected JLabel m_lblSavedProfiles = null;
	protected CComboBox m_cboSavedProfiles = null;
	protected JButton m_btnSaveProfile = null;
	protected JButton m_btnDeleteProfile = null;
	protected String m_strProfileFolderName = "";
	protected ArrayList<CProfileParameter> m_lstParameters = new ArrayList<CProfileParameter>( );
	protected boolean m_blnLoading = true;
	protected final static long serialVersionUID = 1L;

	// --------------------------------------------------------------------------------
	// Name: CAircrackWindow
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public CAircrackDialog( String strTitle, int intWidth, int intHeight, boolean blnResizable, boolean blnExitOnClose, String strProfileFolderName )
	{
		try
		{
			addWindowListener( this );
			m_strProfileFolderName = strProfileFolderName;
			Initialize( strTitle, intWidth, intHeight, blnResizable, blnExitOnClose );
			AddControls( );
			
			if ( strProfileFolderName.equals( "" ) == false )
			{
				CAircrackUtilities.LoadSavedProfilesToComboBox(m_cboSavedProfiles, m_strProfileFolderName);
			}
			m_blnLoading = false;
			
			ImageIcon icoAircrackNGUIIcon = CAircrackUtilities.GetIconFromImage("/Assets/aircrackngui.png", 48, 48);
			setIconImage(icoAircrackNGUIIcon.getImage());
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
	private void Initialize( String strTitle, int intWidth, int intHeight, boolean blnResizable, boolean blnExitOnClose )
	{
		try
		{
            // Title
            setTitle( strTitle );
            
            // Size
            setSize( intWidth, intHeight );
            
            // Center screen
            CUtilities.CenterScreen( this );
            
            // Resizable
            setResizable( blnResizable );
            
            // Modal window
            setModal(true);
            
            // Exit application on close
            if ( blnExitOnClose )
            	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            
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
	private void AddControls( )
	{
		try
		{
			// Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            m_cntControlContainer = this.getContentPane( );
            m_cntControlContainer.setLayout(  splFrame );
            
            if ( m_strProfileFolderName.equals( "" ) == false )
            {
	            m_lblSavedProfiles = CUtilities.AddLabel(m_cntControlContainer, "Saved Profiles:", 5, 5);
	            m_cboSavedProfiles = CUtilities.AddComboBox(m_cntControlContainer, null, 25, 5, 18, 200);
	        	m_btnSaveProfile = CUtilities.AddButton(m_cntControlContainer, this, "", 25, 210, 18, 20);
	        	m_btnDeleteProfile = CUtilities.AddButton(m_cntControlContainer, this, "", 25, 235, 18, 20);
	        	m_btnSaveProfile.setIcon(CAircrackUtilities.GetIconFromImage("Assets/add.png", 18, 18));
	        	m_btnDeleteProfile.setIcon(CAircrackUtilities.GetIconFromImage("Assets/delete.png", 18, 18));
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button clicks, dropdown index changes, etc., etc.
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
			if ( m_cboSavedProfiles != null )
			{
				if ( aeSource.getSource( ) == m_btnSaveProfile )
					btnSaveProfile_Click( );
				else if ( aeSource.getSource( ) == m_cboSavedProfiles.GetJavaComboBox( ) )
					cboSavedProfiles_SelectedIndexChanged( );
				else if ( aeSource.getSource( ) == m_btnDeleteProfile )
					btnDeleteProfile_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSaveProfile_Click
	// Abstract: Adds (or updates) the profile from the form data
	// --------------------------------------------------------------------------------
	private void btnSaveProfile_Click( )
	{
		try
		{
			CXMLWriter clsXMLWriter = new CXMLWriter( );
			Element eleRootNode = clsXMLWriter.AddRootNode("Profile");
			CComboBox cboDropdown = null;
			JCheckBox chkCheckbox = null;
			CTextBox txtTextbox = null;
			JRadioButton rdbButton = null;
			String strRadioOptionText = "";
			CListBox lstListbox = null;
			Element eleNewNode = null;
			
			for ( int intIndex = 0; intIndex < m_lstParameters.size( ); intIndex += 1 )
			{
				CProfileParameter clsParameter = m_lstParameters.get( intIndex );
				CProfileParameter.udtProfileParameterType udtParameterType = clsParameter.GetProfileParameter( );
				String strXMLTag = clsParameter.GetXMLTagName( );
				
				switch ( udtParameterType )
				{
					case PROFILE_PARAMETER_STANDALONE_DROPDOWN:
						cboDropdown = clsParameter.GetDropdown( );
						if ( cboDropdown.GetSelectedIndex( ) >= 0 )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", cboDropdown.GetSelectedItemName( ));
						break;
					case PROFILE_PARAMETER_STANDALONE_CHECKBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						if ( chkCheckbox.isSelected( ) )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", "True");
						break;
					case PROFILE_PARAMETER_STANDALONE_TEXTBOX:
						txtTextbox = clsParameter.GetTextbox( );
						if ( txtTextbox.getText( ).trim( ).equals( "" ) == false )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", txtTextbox.getText().trim());
						break;
					case PROFILE_PARAMETER_CHECKBOX_DROPDOWN:
						chkCheckbox = clsParameter.GetCheckbox( );
						cboDropdown = clsParameter.GetDropdown( );
						if ( chkCheckbox.isSelected( ) == true )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", cboDropdown.GetSelectedItemName( ));
						break;
					case PROFILE_PARAMETER_CHECKBOX_TEXTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						txtTextbox = clsParameter.GetTextbox( );
						if ( chkCheckbox.isSelected( ) == true )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", txtTextbox.getText().trim());
						break;
					case PROFILE_PARAMETER_RADIOBUTTON:
						rdbButton = clsParameter.GetRadioButton( );
						strRadioOptionText = clsParameter.GetOptionName( );
						if ( rdbButton.isSelected( ) )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Option", strRadioOptionText);
						break;
					case PROFILE_PARAMETER_RADIOBUTTON_TEXTBOX:
						rdbButton = clsParameter.GetRadioButton( );
						txtTextbox = clsParameter.GetTextbox( );
						strRadioOptionText = clsParameter.GetOptionName( );
						if ( rdbButton.isSelected( ) )
						{
							eleNewNode = clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Option", strRadioOptionText);
							clsXMLWriter.AddAttributeToNode(eleNewNode, "Value", txtTextbox.getText());
						}
						break;
					case PROFILE_PARAMETER_CHECKBOX_DROPDOWN_TEXTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						cboDropdown = clsParameter.GetDropdown( );
						txtTextbox = clsParameter.GetTextbox( );
						if ( chkCheckbox.isSelected( ) == true )
						{
							eleNewNode = clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Option", cboDropdown.GetSelectedItemName());
							clsXMLWriter.AddAttributeToNode(eleNewNode, "Value", txtTextbox.getText().trim());
						}
						break;
					case PROFILE_PARAMETER_LISTBOX:
						lstListbox = clsParameter.GetListbox( );
						for ( int intIndex2 = 0; intIndex2 < lstListbox.Length( ); intIndex2 += 1 )
							clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", lstListbox.GetItemName(intIndex2));
						break;
					case PROFILE_PARAMETER_CHECKBOX_LISTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						lstListbox = clsParameter.GetListbox( );
						if ( chkCheckbox.isSelected( ) == true )
							for ( int intIndex2 = 0; intIndex2 < lstListbox.Length( ); intIndex2 += 1 )
								clsXMLWriter.AddChildNodeWithAttribute(strXMLTag, eleRootNode, "Value", lstListbox.GetItemName(intIndex2));
				}
			}
			CAircrackUtilities.SaveProfileToDisk(m_cboSavedProfiles, clsXMLWriter, m_strProfileFolderName);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboSavedProfiles_SelectedIndexChanged
	// Abstract: Loads the selected profile from the disk
	// --------------------------------------------------------------------------------
	private void cboSavedProfiles_SelectedIndexChanged( )
	{
		try
		{
			ResetToBlankForm( );
			if ( m_cboSavedProfiles.GetSelectedIndex( ) > 0 )
			{
				LoadSelectedProfile( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: ResetToBlankForm
	// Abstract: Resets the profile parameters to blank
	// --------------------------------------------------------------------------------
	private void ResetToBlankForm( )
	{
		try
		{
			CComboBox cboDropdown = null;
			JCheckBox chkCheckbox = null;
			CTextBox txtTextbox = null;
			JRadioButton rdbButton = null;
			CListBox lstListbox = null;
			for ( int intIndex = 0; intIndex < m_lstParameters.size( ); intIndex += 1 )
			{
				CProfileParameter clsParameter = m_lstParameters.get( intIndex );
				CProfileParameter.udtProfileParameterType udtParameterType = clsParameter.GetProfileParameter( );
				
				switch ( udtParameterType )
				{
					case PROFILE_PARAMETER_STANDALONE_DROPDOWN:
						cboDropdown = clsParameter.GetDropdown( );
						if ( cboDropdown.Length( ) > 0 )
							cboDropdown.SetSelectedIndex( 0 );
						break;
					case PROFILE_PARAMETER_STANDALONE_CHECKBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						chkCheckbox.setSelected( false );
						break;
					case PROFILE_PARAMETER_STANDALONE_TEXTBOX:
						txtTextbox = clsParameter.GetTextbox( );
						txtTextbox.setText( "" );
						break;
					case PROFILE_PARAMETER_CHECKBOX_DROPDOWN:
						chkCheckbox = clsParameter.GetCheckbox( );
						cboDropdown = clsParameter.GetDropdown( );
						chkCheckbox.setSelected( false );
						if ( cboDropdown.Length( ) > 0 )
							cboDropdown.SetSelectedIndex( 0 );
						break;
					case PROFILE_PARAMETER_CHECKBOX_TEXTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						txtTextbox = clsParameter.GetTextbox( );
						chkCheckbox.setSelected( false );
						txtTextbox.setText( "" );
						break;
					case PROFILE_PARAMETER_RADIOBUTTON:
						rdbButton = clsParameter.GetRadioButton( );
						rdbButton.setSelected( false );
						break;
					case PROFILE_PARAMETER_RADIOBUTTON_TEXTBOX:
						rdbButton = clsParameter.GetRadioButton( );
						txtTextbox = clsParameter.GetTextbox( );
						rdbButton.setSelected( false );
						txtTextbox.setText( "" );
						break;
					case PROFILE_PARAMETER_CHECKBOX_DROPDOWN_TEXTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						cboDropdown = clsParameter.GetDropdown( );
						txtTextbox = clsParameter.GetTextbox( );
						chkCheckbox.setSelected( false );
						if ( cboDropdown.Length( ) > 0 )
							cboDropdown.SetSelectedIndex( 0 );
						txtTextbox.setText( "" );
						break;
					case PROFILE_PARAMETER_LISTBOX:
						lstListbox = clsParameter.GetListbox( );
						lstListbox.Clear( );
						break;
					case PROFILE_PARAMETER_CHECKBOX_LISTBOX:
						chkCheckbox = clsParameter.GetCheckbox( );
						lstListbox = clsParameter.GetListbox( );
						chkCheckbox.setSelected( false );
						lstListbox.Clear( );
						break;
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: LoadSelectedProfile
	// Abstract: Loads the profile selected in the dropdown
	// --------------------------------------------------------------------------------
	public void LoadSelectedProfile( )
	{
		try
		{
			CXMLReader clsReader = new CXMLReader( );
 			clsReader.LoadXMLIntoMemory("SavedProfiles/" + m_strProfileFolderName + "/" + m_cboSavedProfiles.GetSelectedItemName( ) + ".profile");
 			Element eleRootElement = clsReader.GetRootElement( );
 			NodeList nlChildren = eleRootElement.getChildNodes( );
 			Element eleCurrentElement = null;
 			String strNodeName = "";
 			String strValue = "";
 			String strOption = "";
 			CComboBox cboDropdown = null;
 			JCheckBox chkCheckbox = null;
 			CTextBox txtTextbox = null;
 			CListBox lstListbox = null;
 			JRadioButton rdbButton = null;
 			String strRadioOptionText = "";
 			
 			for ( int intIndex = 0; intIndex < nlChildren.getLength( ); intIndex += 1 )
 			{
 				eleCurrentElement = (Element)nlChildren.item(intIndex);
 				strNodeName = eleCurrentElement.getNodeName( );
 				strValue = eleCurrentElement.getAttribute("Value");
 				strOption = eleCurrentElement.getAttribute("Option");
 				
 				// Find the associated profile parameter
 				for ( int intParameterIndex = 0; intParameterIndex < m_lstParameters.size( ); intParameterIndex += 1 )
 				{
 					CProfileParameter clsParameter = m_lstParameters.get( intParameterIndex );
 					if ( clsParameter.GetXMLTagName( ).equals( strNodeName ) )
 					{
 						CProfileParameter.udtProfileParameterType udtParameterType = clsParameter.GetProfileParameter( );
 						switch ( udtParameterType )
 						{
 							case PROFILE_PARAMETER_STANDALONE_DROPDOWN:
 								cboDropdown = clsParameter.GetDropdown( );
 								CAircrackUtilities.SetComboBoxSelectedValue(cboDropdown, strValue);
 								break;
 							case PROFILE_PARAMETER_STANDALONE_CHECKBOX:
 								chkCheckbox = clsParameter.GetCheckbox( );
 								if ( strValue.equals("True") )
 									chkCheckbox.setSelected( true );
 								break;
 							case PROFILE_PARAMETER_STANDALONE_TEXTBOX:
 								txtTextbox = clsParameter.GetTextbox( );
 								txtTextbox.setText( strValue );
 								break;
 							case PROFILE_PARAMETER_CHECKBOX_DROPDOWN:
 								chkCheckbox = clsParameter.GetCheckbox( );
 								cboDropdown = clsParameter.GetDropdown( );
 								chkCheckbox.setSelected( true );
 								CAircrackUtilities.SetComboBoxSelectedValue(cboDropdown, strValue);
 								break;
 							case PROFILE_PARAMETER_CHECKBOX_TEXTBOX:
 								chkCheckbox = clsParameter.GetCheckbox( );
 								txtTextbox = clsParameter.GetTextbox( );
 								chkCheckbox.setSelected( true );
 								txtTextbox.setText(strValue);
 								break;
 							case PROFILE_PARAMETER_RADIOBUTTON:
 								rdbButton = clsParameter.GetRadioButton( );
 								strRadioOptionText = clsParameter.GetOptionName( );
 								if ( strRadioOptionText.equals(strOption) )
 									rdbButton.setSelected( true );
 								break;
 							case PROFILE_PARAMETER_RADIOBUTTON_TEXTBOX:
 								rdbButton = clsParameter.GetRadioButton( );
 								txtTextbox = clsParameter.GetTextbox( );
 								strRadioOptionText = clsParameter.GetOptionName( );
 								txtTextbox.setText(strValue);
 								rdbButton.setSelected( true );
 								break;
 							case PROFILE_PARAMETER_CHECKBOX_DROPDOWN_TEXTBOX:
 								chkCheckbox = clsParameter.GetCheckbox( );
 								cboDropdown = clsParameter.GetDropdown( );
 								txtTextbox = clsParameter.GetTextbox( );
 								chkCheckbox.setSelected( true );
 								CAircrackUtilities.SetComboBoxSelectedValue(cboDropdown, strOption);
 								txtTextbox.setText(strValue);
 								break;
 							case PROFILE_PARAMETER_LISTBOX:
 							case PROFILE_PARAMETER_CHECKBOX_LISTBOX:
 								lstListbox = clsParameter.GetListbox( );
 								lstListbox.AddItemToList(strValue, 0);
 								break;
 						}
 					}
 				}
 			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnDeleteProfile_Click
	// Abstract: Deletes the currently selected profile
	// --------------------------------------------------------------------------------
	private void btnDeleteProfile_Click( )
	{
		try
		{
			if (m_cboSavedProfiles.GetSelectedIndex() > 0)
			{
				int intConfirmResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this profile?", "Aircrack-NGUI", JOptionPane.YES_NO_OPTION );
				if ( intConfirmResult == JOptionPane.YES_OPTION )
					CAircrackUtilities.DeleteProfile( m_cboSavedProfiles, m_strProfileFolderName );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: windowOpened
	// Abstract: Called when the window is opened. Sets the action listener and loads
	//			 the default profile (if any). This is in the windowOpened event so
	//			 the child form has a chance to set the profile parameters.
	// --------------------------------------------------------------------------------
	@Override
	public void windowOpened(WindowEvent arg0)
	{
		try
		{
			
			if ( m_strProfileFolderName.equals( "" ) == false )
			{
				
				m_cboSavedProfiles.SetActionListener( this );
				CAircrackUtilities.SetComboBoxSelectedValue(m_cboSavedProfiles, CUserPreferences.GetDefaultProfile(m_strProfileFolderName));
	
			}

		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}
	
}
