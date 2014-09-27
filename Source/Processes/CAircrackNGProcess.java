// Imports

/**
 * Class used to encapulate calls to aircrack-ng (part of the aircrack-ng suite).
 * @author Paul Bromwell Jr
 * @since Jul 12, 2014
 * @version 1.0
 */
public class CAircrackNGProcess extends CProcess
{
	/**
	 * Enumeration for storing attack mode
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public enum udtAttackModeType
	{
		WEP,
		WPA
	}
	
	/**
	 * Enumeration for storing attack method
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public enum udtAttackMethodType
	{
		PTW,
		KoreK
	}
	
	// Required fields
	private String m_strIVSCAPFile = "";
	
	// Common Functions
	private udtAttackModeType m_udtAttackMode = null;
	private String m_strNetworkESSID = "";
	private String m_strNetworkBSSID = "";
	private int m_intCPUCores = 0;
	private boolean m_blnHideStatusInformation = false;
	private String m_strWriteKeyToFile = "";
	
	// Static WEP Functions
	private boolean m_blnSearchAlphaNumericCharacters = false;
	private boolean m_blnSearchBinaryCodedDecimalCharacters = false;
	private boolean m_blnWEPDecloakMode = false;
	private boolean m_blnSingleCrackAttempt = false;
	private boolean m_blnShowASCIIKey = false;
	private String m_strKeyMask = "";
	private String m_strIVsFromMACAddress = "";
	private String m_strKeyLength = "";
	private String m_strBruteForceLevel = "";
	private udtAttackMethodType m_udtAttackMethod = null;
	private String m_strMaxIVsUsed = "";
	
	// Lookup Functions
	private String m_strDictionaryFile = "";
	private String m_strDatabaseFile = "";
	
	/**
	 * Sets the location of the IVS CAP file to crack
	 * @author Paul Bromwell Jr
	 * @param strIVSCapFile IVS CAP file location
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetIVSCapFile(String strIVSCapFile)
	{
		m_strIVSCAPFile = strIVSCapFile;
	}
	
	/**
	 * Sets the attack mode of aircrack-ng.
	 * @author Paul Bromwell Jr
	 * @param udtAttackMode Mode to attack the PCAP file with
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetAttackMode(udtAttackModeType udtAttackMode)
	{
		m_udtAttackMode = udtAttackMode;
	}
	
	/**
	 * Sets the ESSID of the network whose packets we capture
	 * @author Paul Bromwell Jr
	 * @param strESSID Network ESSID
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetNetworkESSID(String strESSID)
	{
		m_strNetworkESSID = strESSID;
	}
	
	/**
	 * Sets the BSSID of the network whose packets we capture
	 * @author Paul Bromwell Jr
	 * @param strBSSID Network BSSID
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetNetworkBSSID(String strBSSID)
	{
		m_strNetworkBSSID = strBSSID;
	}

	/**
	 * Sets the number of CPU cores to use while cracking
	 * @author Paul Bromwell Jr
	 * @param intCPUCores Number of CPU cores to use
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetCPUCores(int intCPUCores)
	{
		m_intCPUCores = intCPUCores;
	}
	
	/**
	 * Hides the status information from the output of aircrack-ng
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void HideStatusInformation()
	{
		m_blnHideStatusInformation = true;
	}
	
	/**
	 * Sets the name of the file to write the network key to
	 * @author Paul Bromwell Jr
	 * @param strKeyFile Filename to write the network key to
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetKeyFile(String strKeyFile)
	{
		m_strWriteKeyToFile = strKeyFile;
	}
	
	/**
	 * Sets aircrack-ng to search the WEP output for alphanumeric characters
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SearchAlphaNumericCharacters()
	{
		m_blnSearchAlphaNumericCharacters = true;
	}
	
	/**
	 * Sets aircrack-ng to search the WEP output for decimal characters
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SearchBinaryCodedDecimalCharacters()
	{
		m_blnSearchBinaryCodedDecimalCharacters = true;
	}
	
	/**
	 * Enables WEP decloak mode while cracking
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void UseWEPDecloakMode()
	{
		m_blnWEPDecloakMode = true;
	}
	
	/**
	 * Sets aircrack-ng to only attempt to crack the key once
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SingleCrackAttempt()
	{
		m_blnSingleCrackAttempt = true;
	}
	
	/**
	 * Display the WEP ASCII key in the program output
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void ShowASCIIKey()
	{
		m_blnShowASCIIKey = true;
	}
	
	/**
	 * Sets the key mask to use while cracking
	 * @author Paul Bromwell Jr
	 * @param strKeyMask Key mask to use
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetKeyMask(String strKeyMask)
	{
		m_strKeyMask = strKeyMask;
	}
	
	/**
	 * Only pull initialization vectors from the specific MAC.
	 * @author Paul Bromwell Jr
	 * @param strMACAddress MAC address to pull IVs from
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetIVsFromMAC(String strMACAddress)
	{
		m_strIVsFromMACAddress = strMACAddress;
	}

	/**
	 * Sets the length of the passkey (if known)
	 * @author Paul Bromwell Jr
	 * @param intKeyLength Length of the passkey
	 * @since Jul 12, 2014
	 * @version 1.0 
	 */
	public void SetKeyLength(String strKeyLength)
	{
		m_strKeyLength = strKeyLength;
	}

	/**
	 * Sets the brute-force level of aircrack-ng to use while cracking the key
	 * @author Paul Bromwell Jr
	 * @param strBruteForceLevel Brute force level
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetBruteForceLevel(String strBruteForceLevel)
	{
		m_strBruteForceLevel = strBruteForceLevel;
	}

	/**
	 * Sets the attack method for aircrack-ng to use while cracking WEP keys
	 * @author Paul Bromwell Jr
	 * @param udtAttackMethod Attack method
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetAttackMethod(udtAttackMethodType udtAttackMethod)
	{
		m_udtAttackMethod = udtAttackMethod;
	}

	/**
	 * Set the maximum number of IVs to use to crack the WEP key
	 * @author Paul Bromwell Jr
	 * @param strIVsUsed Maximum number of IVs
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetMaxIVsUsed(String strIVsUsed)
	{
		m_strMaxIVsUsed = strIVsUsed;
	}
	
	/**
	 * Sets the location of the dictionary file to crack WPA codes on
	 * @author Paul Bromwell Jr
	 * @param strDictionaryFile Location of the dictionary file
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetDictionaryFile(String strDictionaryFile)
	{
		m_strDictionaryFile = strDictionaryFile;
	}
	
	/**
	 * Sets the location of the database file to crack WPA codes on
	 * @author Paul Bromwell Jr
	 * @param strDatabaseFile Location of the coWPAtty file
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void SetDatabaseFile(String strDatabaseFile)
	{
		m_strDatabaseFile = strDatabaseFile;
	}
	
	/**
	 * Cracks the WPA/WEP file with the provided settings
	 * @author Paul Bromwell Jr
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	public void Crack()
	{
		try
		{
			String[] astrCommand = new String[] {"aircrack-ng"};
			
			// Common options
			if (m_udtAttackMode != null)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("a",TranslateAttackModeToString(m_udtAttackMode), astrCommand);
			if (m_strNetworkESSID.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("e", m_strNetworkESSID, astrCommand);
			if (m_strNetworkBSSID.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("b", m_strNetworkBSSID, astrCommand);
			if (m_intCPUCores > 0)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("p", String.valueOf(m_intCPUCores), astrCommand);
			if (m_blnHideStatusInformation)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("q", "", astrCommand);
			if (m_strWriteKeyToFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("l", m_strWriteKeyToFile, astrCommand);
			
			// Static WEP Functions
			if (m_blnSearchAlphaNumericCharacters)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("c", "", astrCommand);
			if (m_blnSearchBinaryCodedDecimalCharacters)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("t", "", astrCommand);
			if (m_blnWEPDecloakMode)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("D", "", astrCommand);
			if (m_blnSingleCrackAttempt)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("1", "", astrCommand);
			if (m_blnShowASCIIKey)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("s", "", astrCommand);
			if (m_strKeyMask.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("d", m_strKeyMask, astrCommand);
			if (m_strIVsFromMACAddress.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("m", m_strIVsFromMACAddress, astrCommand);
			if (m_strKeyLength.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("n", String.valueOf(m_strKeyLength), astrCommand);
			if (m_strBruteForceLevel.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("f", m_strBruteForceLevel, astrCommand);
			if (m_udtAttackMethod != null )
				astrCommand = CAircrackUtilities.AddArgumentToCommand(TranslateAttackMethodToString(m_udtAttackMethod), "", astrCommand);
			if (m_strMaxIVsUsed.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("M", m_strMaxIVsUsed, astrCommand);
			
			// Lookup Functions
			if (m_strDictionaryFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("w", m_strDictionaryFile, astrCommand);
			if (m_strDatabaseFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_strDatabaseFile, astrCommand);
			
			// Required
			astrCommand = CAircrackUtilities.AddArgumentToArray(m_strIVSCAPFile, astrCommand);
			
			RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Translates the attack mode enumeration to a string that aircrack-ng will understand
	 * @author Paul Bromwell Jr
	 * @param udtAttackMode Attack mode to translate
	 * @return aircrack-ng digestable string
	 * @since Jul 12, 2014
	 * @version 1.0
	 */
	private String TranslateAttackModeToString(udtAttackModeType udtAttackMode)
	{
		String strAttackModeCode = "";
		try
		{
			switch (m_udtAttackMode)
			{
				case WEP:
					strAttackModeCode = "1";
					break;
				case WPA:
					strAttackModeCode = "2";
					break;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strAttackModeCode;
	}
	
	/**
	 * Translates the attack method enumeration to a string that aircrack-ng will understand
	 * @author Paul Bromwell Jr
	 * @param udtAttackMethod Attack method to translate
	 * @return aircrack-ng digestible string
	 * @since Jul 13, 2014
	 * @version 1.0
	 */
	private String TranslateAttackMethodToString(udtAttackMethodType udtAttackMethod)
	{
		String strAttackMethodCode = "";
		try
		{
			switch (m_udtAttackMethod)
			{
				case PTW:
					strAttackMethodCode = "z";
					break;
				case KoreK:
					strAttackMethodCode = "K";
					break;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strAttackMethodCode;
	}
}