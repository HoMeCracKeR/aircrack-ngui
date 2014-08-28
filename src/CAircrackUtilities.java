// --------------------------------------------------------------------------------
// Name: CAircrackUtilities
// Abstract: Functions and procedures that are used throughout the program
// --------------------------------------------------------------------------------

// Includes
import java.awt.*; // Toolkit
import java.awt.event.*; // ActionListener
import java.io.*; // BufferedReader, IOException, InputStreamReader

import javax.swing.*;

import java.util.*;

public class CAircrackUtilities
{
    
	private static HashMap<String, String> m_mapProgramMappings = new HashMap<String, String>();
	
	// --------------------------------------------------------------------------------
	// Name: BuildProgramMappings
	// Abstract: Builds the map between the program names and the program paths
	// --------------------------------------------------------------------------------
	public static void BuildProgramMappings( )
	{
		try
		{
			m_mapProgramMappings.put("airodump-ng", "aircrack-ng");
			m_mapProgramMappings.put("wash", "wash");
			m_mapProgramMappings.put("nmap", "nmap");
			m_mapProgramMappings.put("airgraph-ng", "aircrack-ng");
			m_mapProgramMappings.put("airlib-ng", "aircrack-ng");
			m_mapProgramMappings.put("aireplay-ng", "aircrack-ng");
			m_mapProgramMappings.put("aircrack-ng", "aircrack-ng");
			m_mapProgramMappings.put("easside-ng", "aircrack-ng");
			m_mapProgramMappings.put("buddy-ng", "aircrack-ng");
			m_mapProgramMappings.put("packetforge-ng", "aircrack-ng");
			m_mapProgramMappings.put("arpspoof", "dsniff");
			m_mapProgramMappings.put("dsniff", "dsniff");
			m_mapProgramMappings.put("wireshark", "wireshark");
			m_mapProgramMappings.put("airgraph-ng", "aircrack-ng");
			m_mapProgramMappings.put("vomit", "vomit");
			m_mapProgramMappings.put("msfconsole", "metasploit");
			m_mapProgramMappings.put("msfgui", "metasploit");
			m_mapProgramMappings.put("armitage", "armitage");
			m_mapProgramMappings.put("airgraph-ng", "aircrack-ng");
			m_mapProgramMappings.put("burpsuite", "burpsuite");
			m_mapProgramMappings.put("nikto.pl", "nikto");
			m_mapProgramMappings.put("hping3", "hping");
			m_mapProgramMappings.put("macchanger", "macchanger");
			m_mapProgramMappings.put("sslstrip.py", "sslstrip");
			m_mapProgramMappings.put("redfang", "redfang");
			m_mapProgramMappings.put("msgsnarf", "msgsnarf");
			m_mapProgramMappings.put("tcpdump", "tcpdump");
			m_mapProgramMappings.put("sqlscan.py", "sqlscan");
			m_mapProgramMappings.put("sqlmap.py", "sqlmap");
			m_mapProgramMappings.put("ipcalc", "ipcalc");
			m_mapProgramMappings.put("tor", "tornetwork");
			m_mapProgramMappings.put("reaver", "reaver");
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetProgramMap
	// Abstract: Gets the program path key for a particular program
	// --------------------------------------------------------------------------------
	public static String GetProgramMap(String strProgramName)
	{
		String strProgramMap = "";
		try
		{
			strProgramMap = m_mapProgramMappings.get(strProgramName);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strProgramMap;
	}
	

	// --------------------------------------------------------------------------------
	// Name: GetRequiredPackages
	// Abstract: Gets a list of required packages for NGUI to be used to its full
    //			 capacity
	// --------------------------------------------------------------------------------
    public static String[] GetRequiredPackages()
    {
    	String astrRequiredPackages[] = null;
    	try
    	{
    		ArrayList<String> lstPackages = new ArrayList<String>();
    		lstPackages.add("aircrack-ng");
    		lstPackages.add("nmap");
    		lstPackages.add("dsniff");
    		lstPackages.add("wireshark");
    		lstPackages.add("armitage");
    		lstPackages.add("nikto");
			lstPackages.add("hping3");
			lstPackages.add("macchanger");
			lstPackages.add("sslstrip");
			lstPackages.add("tcpdump");
			lstPackages.add("sqlscan");
			lstPackages.add("sqlmap");
			lstPackages.add("reaver");
			
			astrRequiredPackages = new String[lstPackages.size()];
			for (int intIndex = 0; intIndex < lstPackages.size(); intIndex += 1)
				astrRequiredPackages[intIndex] = lstPackages.get(intIndex);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	return astrRequiredPackages;
    }
    
	
	// --------------------------------------------------------------------------------
	// Name: AddArgumentToArray
	// Abstract: Adds an argument to an array of arguments. Essentially resizes the
	//			 array by +1 and assigns the last element to the new argument
	// --------------------------------------------------------------------------------
    public static String[] AddArgumentToArray(String strNewArgument, String astrOldArray[])
    {
        String astrNewArray[] = null;
        try
        {
            astrNewArray = new String[astrOldArray.length + 1];
            for ( int intIndex = 0; intIndex < astrOldArray.length; intIndex += 1 )
            {
                astrNewArray[intIndex] = astrOldArray[intIndex];
            }
            astrNewArray[astrNewArray.length - 1] = strNewArgument;
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        return astrNewArray;
    }
    
	// --------------------------------------------------------------------------------
	// Name: AddArgumentToCommand
	// Abstract: Same as add argument to array, but lets you specify a tack option
	// --------------------------------------------------------------------------------
    public static String[] AddArgumentToCommand(String strTackOption, String strValue, String[] astrOldArray)
    {
        String[] astrNewArray = null;
        try
        {
        	if ( strTackOption.equals("") == false )
        		astrNewArray = AddArgumentToArray("-" + strTackOption, astrOldArray);
            if ( strValue.equals( "" ) == false)
            {
            	if ( astrNewArray != null)
            		astrNewArray = AddArgumentToArray(strValue, astrNewArray);
            	else
            		astrNewArray = AddArgumentToArray(strValue, astrOldArray);
            }
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        return astrNewArray;
    }

    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser for a JFrame that lets a user select a file.
    //			 The path is then copied to the textbox.
    // --------------------------------------------------------------------------------
    public static void DisplayFileChooser(CTextBox clsTarget, JFrame frmParent)
	{
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
            int intReturnValue = jfcFileChooser.showOpenDialog(frmParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
    
    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser for a JFrame that lets a user select a file.
    //			 The path is then copied to the textbox and the checkbox is checked.
    // --------------------------------------------------------------------------------
    public static void DisplayFileChooser(CTextBox clsTarget, JFrame frmParent, JCheckBox chkCheckBox)
	{
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
            int intReturnValue = jfcFileChooser.showOpenDialog(frmParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
                chkCheckBox.setSelected( true );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}


    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser for a JDialog that lets a user select a file.
    //			 The path is then copied to the textbox.
    // --------------------------------------------------------------------------------
    public static void DisplayFileChooser(CTextBox clsTarget, JDialog dlgParent)
	{
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
            int intReturnValue = jfcFileChooser.showOpenDialog(dlgParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser for a JFrame that lets a user select a file.
    //			 The path is then copied to the textbox and the checkbox is checked.
    // --------------------------------------------------------------------------------
    public static void DisplayFileChooser(CTextBox clsTarget, JDialog dlgParent, JCheckBox chkCheckBox)
	{
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
            int intReturnValue = jfcFileChooser.showOpenDialog(dlgParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
                chkCheckBox.setSelected( true );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
    
    // --------------------------------------------------------------------------------
    // Name: CreateMenuItem
    // Abstract: Creates a menu item under the parent menu. Used for context menus.
    // --------------------------------------------------------------------------------
    public static JMenuItem CreateMenuItem(String strDisplayText, JMenu mnuParentMenu, ActionListener alParent)
	{
		JMenuItem miNewItem = null;
		try
		{
			miNewItem = new JMenuItem( strDisplayText );
			miNewItem.addActionListener( alParent );
			mnuParentMenu.add( miNewItem );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return miNewItem;
	}

    // --------------------------------------------------------------------------------
    // Name: CreateMenuItem
    // Abstract: Creates a menu item under the parent menu. Used for context menus.
    // --------------------------------------------------------------------------------
    public static JMenuItem CreateMenuItem(String strDisplayText, char chrMnemonic, JMenu mnuParentMenu, ActionListener alParent)
	{
		JMenuItem miNewItem = null;
		try
		{
			miNewItem = new JMenuItem( strDisplayText );
			miNewItem.setMnemonic(chrMnemonic);
			miNewItem.addActionListener( alParent );
			mnuParentMenu.add( miNewItem );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return miNewItem;
	}
    
    // --------------------------------------------------------------------------------
    // Name: CreateMenuItem
    // Abstract: Creates a menu item under the popup menu. Used for context menus.
    // --------------------------------------------------------------------------------
	public static JMenuItem CreateMenuItem(String strDisplayText, JPopupMenu pumParentMenu, ActionListener alParent)
	{
		JMenuItem miNewItem = null;
		try
		{
			miNewItem = new JMenuItem( strDisplayText );
			miNewItem.addActionListener( alParent );
			pumParentMenu.add( miNewItem );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return miNewItem;
	}

    // --------------------------------------------------------------------------------
    // Name: AddBooleanToEndOfArray
    // Abstract: Appends a boolean value to the end of a boolean array
    // --------------------------------------------------------------------------------
	public static boolean[] AddBooleanToEndOfArray(boolean blnNewBoolean, boolean ablnOldArray[])
	{
		boolean ablnNewArray[] = null;
		try
		{
			ablnNewArray = new boolean[ablnOldArray.length + 1];
			for ( int intIndex = 0; intIndex < ablnOldArray.length; intIndex += 1 )
			{
				ablnNewArray[intIndex] = ablnOldArray[intIndex];
			}
			ablnNewArray[ablnNewArray.length - 1] = blnNewBoolean;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return ablnNewArray;
	}

    // --------------------------------------------------------------------------------
    // Name: AddArgumentIfChecked
    // Abstract: ADds the argument value if the checkbox is checked
    // --------------------------------------------------------------------------------
	public static String[] AddArgumentIfChecked(JCheckBox chkCheckBox, String strTackOption, String strValue, String astrOldCommand[])
	{
		String astrNewCommand[] = null;
		try
		{
			astrNewCommand = astrOldCommand;
			if ( chkCheckBox.isSelected( ) == true )
			{
				astrNewCommand = AddArgumentToCommand(strTackOption, strValue, astrNewCommand);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}
	
    // --------------------------------------------------------------------------------
    // Name: AddArgumentIfChecked
    // Abstract: ADds the argument value if the checkbox is checked
    // --------------------------------------------------------------------------------
	public static boolean ConvertIntegerToBoolean(int intValue)
	{
		boolean blnValue = false;
		try
		{
			if ( intValue > 0 )
				blnValue = true;
			else
				blnValue = false;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnValue;
	}
	
    // --------------------------------------------------------------------------------
    // Name: GetJARRunningPath
    // Abstract: Gets the path of the running JAR file
    // --------------------------------------------------------------------------------
	public static String GetJARRunningPath( )
	{
		String strPath = "";
		try
		{
			strPath = new File(".").getAbsolutePath();
			
			// If the path ends with a dot, remove it.
			if (strPath.endsWith("."))
				strPath = strPath.substring(0, strPath.lastIndexOf("."));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strPath;
	}

	// --------------------------------------------------------------------------------
    // Name: SetComboBoxSelectedValue
    // Abstract: Sets the combobox's selected value
    // --------------------------------------------------------------------------------
	public static void SetComboBoxSelectedValue(CComboBox cboComboBox, String strSelectedValue)
	{
		try
		{
			int intSelectedIndex = 0;
			
			for ( int intIndex = 0; intIndex < cboComboBox.Length(); intIndex += 1 )
			{
				if ( cboComboBox.GetItemName(intIndex).equals(strSelectedValue))
				{
					intSelectedIndex = intIndex;
					break;
				}
			}
			cboComboBox.SetSelectedIndex( intSelectedIndex );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: GetIconFromImage
    // Abstract: Takes an image path and returns an icon at a given size. Use -1, -1
	//			 for no scaling.
    // --------------------------------------------------------------------------------
	public static ImageIcon GetIconFromImage(String strImagePath, int intWidth, int intHeight)
	{
		ImageIcon icoImage = null;
		try
		{
			if (strImagePath.startsWith("/Assets/"))
				strImagePath = GetJARRunningPath() + strImagePath;
			icoImage = new ImageIcon(strImagePath);
			if ( intWidth != -1 && intHeight != -1 )
			{
				Image imgIconImage = icoImage.getImage( );
				Image imgNewIconImage = imgIconImage.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
				icoImage = new ImageIcon(imgNewIconImage);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return icoImage;
	}

    // --------------------------------------------------------------------------------
    // Name: MakeDirectoryPath
    // Abstract: Makes the chosen directory path
    // --------------------------------------------------------------------------------
	public static boolean MakeDirectoryPath(String strDirectoryPath)
	{
		boolean blnSuccess = false;
		try
		{
			File filDirectoryPath = new File(strDirectoryPath);
			if ( filDirectoryPath.exists() == false )
			{
				if ( strDirectoryPath.contains("/") )
					blnSuccess = filDirectoryPath.mkdirs();
				else
					blnSuccess = filDirectoryPath.mkdir();
			}
			else
				blnSuccess = true;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccess;
	}
	
    // --------------------------------------------------------------------------------
    // Name: MakeDirectoryPath
    // Abstract: Makes the chosen directory path
    // --------------------------------------------------------------------------------
	public static boolean ValueExistsInComboBox(String strValue, CComboBox cboTarget)
	{
		boolean blnExists = false;
		try
		{
			for ( int intIndex = 0; intIndex < cboTarget.Length(); intIndex += 1 )
			{
				if ( cboTarget.GetItemName(intIndex).equals(strValue) )
				{
					blnExists = true;
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnExists;
	}

    // --------------------------------------------------------------------------------
    // Name: DisplayDirectoryChooser
    // Abstract: Displays a file chooser directed towards directories only
    // --------------------------------------------------------------------------------
	public static int DisplayDirectoryChooser(CTextBox clsTarget, JFrame frmParent)
	{
		int intReturnValue = -1;
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
			jfcFileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            intReturnValue = jfcFileChooser.showOpenDialog(frmParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intReturnValue;
	}
	
    // --------------------------------------------------------------------------------
    // Name: DisplayDirectoryChooser
    // Abstract: Displays a file chooser directed towards directories only
    // --------------------------------------------------------------------------------
	public static int DisplayDirectoryChooser(CTextBox clsTarget, JDialog dlgParent)
	{
		int intReturnValue = -1;
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
			jfcFileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            intReturnValue = jfcFileChooser.showOpenDialog(dlgParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                clsTarget.setText( filSelectedFile.getAbsoluteFile( ).toString( ) );
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intReturnValue;
	}

    // --------------------------------------------------------------------------------
    // Name: DeleteFilesRecursively
    // Abstract: Deletes files in all sub-directories
    // --------------------------------------------------------------------------------
	public static void DeleteFilesRecursively(File filFileToDelete)
	{
		try
		{
			if ( filFileToDelete.isDirectory( ) )
			{
				for (File filSubFile : filFileToDelete.listFiles( ) )
				{
					DeleteFilesRecursively(filSubFile);
				}
			}
			filFileToDelete.delete( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: CalculateScanTarget
    // Abstract: Calculates the range to scan based on IP address and subnet mask
    // --------------------------------------------------------------------------------
	public static String CalculateScanTarget(String strIPAddress, String strSubnetMask)
	{
		String strNetworkScanTarget = "";
		try
		{
			// Class A networks
			if ( strSubnetMask.equals( "255.0.0.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.indexOf(".") + 1) + "0.0.0/8";
			}
			// Class B networks
			else if ( strSubnetMask.equals( "255.255.0.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.lastIndexOf(".", strIPAddress.lastIndexOf(".") - 1) + 1) + "0.0/16";
			}
			// Class C networks
			else if ( strSubnetMask.equals( "255.255.255.0" ) == true )
			{
				strNetworkScanTarget = strIPAddress.substring(0, strIPAddress.lastIndexOf(".") + 1) + "0/24";
			}
			// Custom or undeterminable subnet
			else
			{
				JOptionPane.showMessageDialog(null, "Aircrack-NGUI only supports scanning targets in Class A, B, and C networks (subnet mask 255.0.0.0, 255.255.0.0, or 255.255.255.0)");
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strNetworkScanTarget;
	}

    // --------------------------------------------------------------------------------
    // Name: LoadSavedProfilesToComboBox
    // Abstract: Loads the saved profiles in the specified directory into the dropdown
    // --------------------------------------------------------------------------------
	public static void LoadSavedProfilesToComboBox(CComboBox cboDropdown, String strDirectoryName)
	{
		try
		{
			cboDropdown.AddItemToList("", 0);
    		File dirSavedProfiles = new File("SavedProfiles/" + strDirectoryName);
    		if ( dirSavedProfiles.exists( ) )
    		{
	    		File afilProfiles[] = dirSavedProfiles.listFiles( );
	    		for ( int intIndex = 0; intIndex < afilProfiles.length; intIndex += 1 )
	    		{
	    			cboDropdown.AddItemToList(afilProfiles[intIndex].getName().replace(".profile", ""), 0);
	    		}
    		}
    		cboDropdown.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser with a filter and returns the selected path
    // --------------------------------------------------------------------------------
	public static String DisplayFileChooser(JFrame frmParent, boolean blnAllowAll, String strExtension, String strExtensionDescription)
	{
		String strSelectedFile = "";
		int intReturnValue = -1;
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
			jfcFileChooser.setFileFilter( new CFileChooserFilter( strExtension, strExtensionDescription ) );
			jfcFileChooser.setAcceptAllFileFilterUsed( blnAllowAll );
            intReturnValue = jfcFileChooser.showOpenDialog(frmParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                strSelectedFile = filSelectedFile.getAbsoluteFile( ).toString( );
                if ( strSelectedFile.endsWith( strExtension ) == false )
                	strSelectedFile += strExtension;
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
			strSelectedFile = "";
		}
		return strSelectedFile;
	}

    // --------------------------------------------------------------------------------
    // Name: DisplayFileChooser
    // Abstract: Displays a file chooser with a filter and returns the selected path
    // --------------------------------------------------------------------------------
	public static String DisplayFileChooser(JDialog dlgParent, boolean blnAllowAll, String strExtension, String strExtensionDescription)
	{
		String strSelectedFile = "";
		int intReturnValue = -1;
		try
		{
			JFileChooser jfcFileChooser = new JFileChooser();
			jfcFileChooser.setFileFilter( new CFileChooserFilter( strExtension, strExtensionDescription ) );
			jfcFileChooser.setAcceptAllFileFilterUsed( blnAllowAll );
            intReturnValue = jfcFileChooser.showOpenDialog(dlgParent);
            if ( intReturnValue == JFileChooser.APPROVE_OPTION )
            {
                File filSelectedFile = jfcFileChooser.getSelectedFile( );
                strSelectedFile = filSelectedFile.getAbsoluteFile( ).toString( );
                if ( strSelectedFile.endsWith( strExtension ) == false )
                	strSelectedFile += strExtension;
            }
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
			strSelectedFile = "";
		}
		return strSelectedFile;
	}

    // --------------------------------------------------------------------------------
    // Name: LoadProfilesIntoComboBox
    // Abstract: Loads the profiles in the given folder to the combobox
    // --------------------------------------------------------------------------------
	public static void LoadProfilesIntoComboBox(CComboBox cboTarget, String strFolderName)
	{
		try
		{
			cboTarget.Clear( );
			cboTarget.AddItemToList("", 0);
			
			File dirDiscoverNetworks = new File("SavedProfiles/" + strFolderName);
    		if ( dirDiscoverNetworks.exists( ) )
    		{
	    		File afilProfiles[] = dirDiscoverNetworks.listFiles( );
	    		for ( int intIndex = 0; intIndex < afilProfiles.length; intIndex += 1 )
	    		{
	    			cboTarget.AddItemToList(afilProfiles[intIndex].getName().replace(".profile", ""), 0);
	    		}
    		}
    		cboTarget.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: DeleteProfile
    // Abstract: Deletes the selected profile
    // --------------------------------------------------------------------------------
	public static void DeleteProfile(CComboBox cboTarget, String strFolderName)
	{
		try
		{
			File filSelectedProfile = new File("SavedProfiles/" + strFolderName + "/" + cboTarget.GetSelectedItemName() + ".profile");
			if ( filSelectedProfile.exists( ) == true )
				filSelectedProfile.delete( );
			cboTarget.RemoveAt(cboTarget.GetSelectedIndex());
			cboTarget.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: SaveProfileToDisk
    // Abstract: Saves the created profile to the hard disk
    // --------------------------------------------------------------------------------
	public static void SaveProfileToDisk( CComboBox cboTarget, CXMLWriter clsCreatedDocument, String strFolderName )
	{
		try
		{
			String strNewProfileName = JOptionPane.showInputDialog("Please enter a name for the new profile:");
			
			// If the user didn't cancel
			if (strNewProfileName != null)
			{
				if ( CAircrackUtilities.MakeDirectoryPath("SavedProfiles/" + strFolderName) == true )
				{
					
					clsCreatedDocument.SaveXMLToDisk( "SavedProfiles/" + strFolderName + "/" + strNewProfileName + ".profile" );
					
					if ( ValueExistsInComboBox( strNewProfileName, cboTarget ) == false )
						cboTarget.AddItemToList( strNewProfileName, 0 );
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Couldn't create directory SavedProfiles/" + strFolderName);
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

    // --------------------------------------------------------------------------------
    // Name: SaveTextToFile
    // Abstract: Saves the passed-in text to a specified file
    // --------------------------------------------------------------------------------
	public static boolean SaveTextToFile( String strTextToWrite, String strDestinationFile )
	{
		boolean blnSuccessful = false;
		try
		{
			strDestinationFile = CAircrackUtilities.GetJARRunningPath() + strDestinationFile;
			File filCommandOutput = new File(strDestinationFile);
			if (filCommandOutput.exists() == false)
				filCommandOutput.createNewFile();
			FileWriter filFileStream = new FileWriter( strDestinationFile, true );
			BufferedWriter bwOutput = new BufferedWriter( filFileStream );
			bwOutput.write(strTextToWrite);
			bwOutput.close();
			filFileStream.close();
			
			blnSuccessful = true;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccessful;
	}

    // --------------------------------------------------------------------------------
    // Name: ToProperCase
    // Abstract: Takes a string and converts it to proper case (every first letter of each
	//			 word capitalized)
    // --------------------------------------------------------------------------------
	public static String ToProperCase(String strOriginal)
	{
		String strProperCaseString = "";
		try
		{
			String astrWordsInOriginal[] = strOriginal.split(" ");
			char achrCharactersInWord[] = null;
			int intIndex = 0;
			for ( intIndex = 0; intIndex < astrWordsInOriginal.length; intIndex += 1 )
			{
				achrCharactersInWord = astrWordsInOriginal[intIndex].toCharArray( );
				if ( achrCharactersInWord.length > 0 )
				{
					if ( achrCharactersInWord[0] >= 'a' && achrCharactersInWord[0] <= 'z' )
						achrCharactersInWord[0] = String.valueOf(achrCharactersInWord[0]).toUpperCase( ).toCharArray( )[ 0 ];
				}
				
				if ( strProperCaseString != "" )
					strProperCaseString += " ";
				
				strProperCaseString += String.valueOf(achrCharactersInWord);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strProperCaseString;
	}
	
	/**
	 * Takes a line of output from ifconfig or iwconfig and returns the value for the indicated field
	 * @author Paul Bromwell Jr
	 * @param strOutputLine Output line from ifconfig or iwconfig
	 * @param strFieldName Name of the field to get from the line
	 * @return Field's value from the output line
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public static String GetUnixIFIWConfigOutputField(String strOutputLine, String strFieldName)
	{
		String strValue = "";
		try
		{
			strValue = strOutputLine.substring(strOutputLine.indexOf(strFieldName) + strFieldName.length()).trim();
			if (strValue.contains(" "))
				strValue = strValue.substring(0, strValue.indexOf(" "));
			strValue = strValue.trim();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strValue;
	}
}