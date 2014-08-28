// --------------------------------------------------------------------------------
// Name: CUserPreferences
// Abstract: Class to hold preferred settings and Sticky Settings
// --------------------------------------------------------------------------------

// Includes
import java.util.*;

public class CUserPreferences
{

	private static String m_strPreferredStandardInterface = "";
	private static String m_strPreferredMonitorInterface = "";
	private static HashMap<String, String> m_mapApplicationPaths = new HashMap<String, String>();
	private static HashMap<String, String> m_mapDefaultProfiles = new HashMap<String, String>();
	private static String m_strCommandLogLevel = "NONE";

	// --------------------------------------------------------------------------------
	// Name: SetPreferredStandardInterface
	// Abstract: Set method for Preferred Standard Interface
	// --------------------------------------------------------------------------------
	public static void SetPreferredStandardInterface(String strNewPreferredStandardInterface)
	{
		try
		{
			m_strPreferredStandardInterface = strNewPreferredStandardInterface;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetPreferredStandardInterface
	// Abstract: Get method for Preferred Standard Interface
	// --------------------------------------------------------------------------------
	public static String GetPreferredStandardInterface( )
	{
		return m_strPreferredStandardInterface;
	}

	// --------------------------------------------------------------------------------
	// Name: SetPreferredMonitorInterface
	// Abstract: Set method for Preferred Monitor Interface
	// --------------------------------------------------------------------------------
	public static void SetPreferredMonitorInterface(String strNewPreferredMonitorInterface)
	{
		try
		{
			m_strPreferredMonitorInterface = strNewPreferredMonitorInterface;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetPreferredMonitorInterface
	// Abstract: Get method for Preferred Monitor Interface
	// --------------------------------------------------------------------------------
	public static String GetPreferredMonitorInterface( )
	{
		return m_strPreferredMonitorInterface;
	}

	// --------------------------------------------------------------------------------
	// Name: AddApplicationPath
	// Abstract: Adds an application path to the map
	// --------------------------------------------------------------------------------
	public static void AddApplicationPath(String strKey, String strPath)
	{
		try
		{
			m_mapApplicationPaths.put(strKey, strPath);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetApplicationPath
	// Abstract: Returns an application path from the map
	// --------------------------------------------------------------------------------
	public static String GetApplicationPath(String strKey)
	{
		String strPath = "";
		try
		{
			if ( m_mapApplicationPaths.containsKey( strKey ) == true )
			{
				strPath = m_mapApplicationPaths.get( strKey );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strPath;
	}

	// --------------------------------------------------------------------------------
	// Name: ClearAllApplicationPaths
	// Abstract: Clears all application path key-value pairs
	// --------------------------------------------------------------------------------
	public static void ClearAllApplicationPaths( )
	{
		try
		{
			m_mapApplicationPaths.clear( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetApplicationPaths
	// Abstract: Returns the map storing all the application paths
	// --------------------------------------------------------------------------------
	public static Map<String, String> GetApplicationPaths( )
	{
		return m_mapApplicationPaths;
	}

	// --------------------------------------------------------------------------------
	// Name: SetDefaultProfile
	// Abstract: Sets the default profile name
	// --------------------------------------------------------------------------------
	public static void SetDefaultProfile(String strProfileFolderName, String strProfileName)
	{
		try
		{
			if ( m_mapDefaultProfiles.containsKey(strProfileFolderName) == true )
				m_mapDefaultProfiles.remove(strProfileFolderName);
			
			m_mapDefaultProfiles.put(strProfileFolderName, strProfileName);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetDefaultProfile
	// Abstract: Gets the default profile name
	// --------------------------------------------------------------------------------
	public static String GetDefaultProfile( String strProfileFolderName )
	{
		String strDefaultProfile = "";
		try
		{
			if ( m_mapDefaultProfiles.containsKey(strProfileFolderName) == true )
				strDefaultProfile = m_mapDefaultProfiles.get( strProfileFolderName );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strDefaultProfile;
	}

	// --------------------------------------------------------------------------------
	// Name: SetCommandOutputLevel
	// Abstract: Sets the command output level
	// --------------------------------------------------------------------------------
	public static void SetCommandOutputLevel( String strOutputLevel )
	{
		try
		{
			if ( strOutputLevel.equals("IMPORTANT") == true || strOutputLevel.equals("ALL") == true )
				m_strCommandLogLevel = strOutputLevel;
			else
				m_strCommandLogLevel = "NONE";
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetCommandOutputLevel
	// Abstract: Gets the command output level
	// --------------------------------------------------------------------------------
	public static String GetCommandOutputLevel( )
	{
		return m_strCommandLogLevel;
	}
}
