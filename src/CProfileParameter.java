import javax.swing.*;

public class CProfileParameter
{
	public enum udtProfileParameterType
	{
		PROFILE_PARAMETER_STANDALONE_DROPDOWN,
		PROFILE_PARAMETER_STANDALONE_CHECKBOX,
		PROFILE_PARAMETER_STANDALONE_TEXTBOX,
		PROFILE_PARAMETER_CHECKBOX_TEXTBOX,
		PROFILE_PARAMETER_CHECKBOX_DROPDOWN,
		PROFILE_PARAMETER_RADIOBUTTON,
		PROFILE_PARAMETER_RADIOBUTTON_TEXTBOX,
		PROFILE_PARAMETER_CHECKBOX_DROPDOWN_TEXTBOX,
		PROFILE_PARAMETER_LISTBOX,
		PROFILE_PARAMETER_CHECKBOX_LISTBOX
	}
	
	private udtProfileParameterType m_udtProfileParameter;
	private CComboBox m_cboDropdown = null;
	private JCheckBox m_chkCheckbox = null;
	private CTextBox m_txtTextbox = null;
	private JRadioButton m_rdbRadioButton = null;
	private CListBox m_lstListbox = null;
	private String m_strXMLTagName = "";
	private String m_strOptionName = "";
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (stand-alone dropdown)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, CComboBox cboDropdown)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_STANDALONE_DROPDOWN;
		m_strXMLTagName = strTagName;
		m_cboDropdown = cboDropdown;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (stand-alone checkbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JCheckBox chkCheckBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_STANDALONE_CHECKBOX;
		m_strXMLTagName = strTagName;
		m_chkCheckbox = chkCheckBox;
	}

	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (stand-alone textbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, CTextBox txtTextBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_STANDALONE_TEXTBOX;
		m_strXMLTagName = strTagName;
		m_txtTextbox = txtTextBox;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (checkbox and textbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JCheckBox chkCheckBox, CTextBox txtTextBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_CHECKBOX_TEXTBOX;
		m_strXMLTagName = strTagName;
		m_chkCheckbox = chkCheckBox;
		m_txtTextbox = txtTextBox;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (checkbox and dropdown)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JCheckBox chkCheckBox, CComboBox cboDropdown)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_CHECKBOX_DROPDOWN;
		m_strXMLTagName = strTagName;
		m_chkCheckbox = chkCheckBox;
		m_cboDropdown = cboDropdown;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (stand-alone radio button)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JRadioButton rdbRadioButton, String strRadioButtonName)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_RADIOBUTTON;
		m_strXMLTagName = strTagName;
		m_rdbRadioButton = rdbRadioButton;
		m_strOptionName = strRadioButtonName;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (radio button and textbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JRadioButton rdbRadioButton, String strRadioButtonName, CTextBox txtTextBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_RADIOBUTTON_TEXTBOX;
		m_strXMLTagName = strTagName;
		m_rdbRadioButton = rdbRadioButton;
		m_strOptionName = strRadioButtonName;
		m_txtTextbox = txtTextBox;
	}
	
	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (checkbox, dropdown, and textbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JCheckBox chkCheckBox, CComboBox cboDropdown, CTextBox txtTextBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_CHECKBOX_DROPDOWN_TEXTBOX;
		m_strXMLTagName = strTagName;
		m_chkCheckbox = chkCheckBox;
		m_cboDropdown = cboDropdown;
		m_txtTextbox = txtTextBox;
	}

	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (stand-alone listbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, CListBox lstListBox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_LISTBOX;
		m_strXMLTagName = strTagName;
		m_lstListbox = lstListBox;
	}

	// --------------------------------------------------------------------------------
	// Name: CProfileParameter
	// Abstract: Class constructor (checkbox and listbox)
	// --------------------------------------------------------------------------------
	public CProfileParameter(String strTagName, JCheckBox chkCheckBox, CListBox lstListbox)
	{
		m_udtProfileParameter = udtProfileParameterType.PROFILE_PARAMETER_CHECKBOX_LISTBOX;
		m_strXMLTagName = strTagName;
		m_lstListbox = lstListbox;
	}
	
	public udtProfileParameterType GetProfileParameter( )
	{
		return m_udtProfileParameter;
	}
	
	public CComboBox GetDropdown( )
	{
		return m_cboDropdown;
	}
	
	public JCheckBox GetCheckbox( )
	{
		return m_chkCheckbox;
	}
	
	public CTextBox GetTextbox( )
	{
		return m_txtTextbox;
	}
	
	public JRadioButton GetRadioButton( )
	{
		return m_rdbRadioButton;
	}
	
	public CListBox GetListbox( )
	{
		return m_lstListbox;
	}
	
	public String GetXMLTagName( )
	{
		return m_strXMLTagName;
	}
	
	public String GetOptionName( )
	{
		return m_strOptionName;
	}
}
