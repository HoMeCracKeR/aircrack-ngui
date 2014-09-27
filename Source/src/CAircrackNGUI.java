// --------------------------------------------------------------------------------
// Name: CAircrackNGUI
// Abstract: Startup page. Launches FMain if the user is running as root.
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import org.w3c.dom.*;
import java.io.*;

public class CAircrackNGUI
{
	
	// --------------------------------------------------------------------------------
	// Name: main
	// Abstract: Program's entry point.
	// --------------------------------------------------------------------------------
    public static void main(String astrCommandLineArguments[])
    {
        try
        {
        	CLocalMachine clsLocalMachine = CGlobals.clsLocalMachine;
        	boolean blnDebugMode = false;
        	
        	if (astrCommandLineArguments.length > 0)
        		for (int intIndex = 0; intIndex < astrCommandLineArguments.length; intIndex += 1)
            		if (astrCommandLineArguments[intIndex].equals("--debug"))
            			blnDebugMode = true;
        	
        	// There are 3 conditions to running Aircrack-NGUI, one must be true.
        	// 1. User is on Unix system running the app as root
        	// 2. Debug mode is turned on (for development only)
        	// 3. User is running Windows, in which case, they're on their own.
        	boolean blnCanRunNGUI = false;
        	if (clsLocalMachine.GetCurrentUser().equals("root") == true && CGlobals.clsLocalMachine.GetOperatingSystem() != CLocalMachine.udtOperatingSystemType.WINDOWS)
        		blnCanRunNGUI = true;
        	else if (blnDebugMode == true)
        		blnCanRunNGUI = true;
        	else if (CGlobals.clsLocalMachine.GetOperatingSystem() == CLocalMachine.udtOperatingSystemType.WINDOWS)
        		blnCanRunNGUI = true;
        	
            // Are you running as root?
        	if (blnCanRunNGUI)
        	{
            	// Display splash screen
        		FSplashScreen frmSplashScreen = new FSplashScreen();
        		frmSplashScreen.setVisible(true);
        		
            	// Load the settings from the settings file
            	LoadSettingsFromFile( );
            	
            	CAircrackUtilities.BuildProgramMappings( );
            	
            	// Launch the main form
                FMain frmMain = new FMain();
                frmMain.setVisible(true);
                
                frmSplashScreen.setVisible(false);
            }
        	else
            {
            	// Let the user know he needs to run as root
                JOptionPane.showMessageDialog( null, "You must be logged in as root to use Aircrack-NGUI.", "Aircrack-NGUI", JOptionPane.OK_OPTION );
            }
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError.toString());
        }
    }

	// --------------------------------------------------------------------------------
	// Name: LoadSettingsFromFile
	// Abstract: Loads the settings from the settings file into memory
	// --------------------------------------------------------------------------------
    private static void LoadSettingsFromFile( )
    {
    	try
    	{
    		File filSettingsFile = new File("Settings.profile");
    		CXMLReader clsSettingsFileReader = null;
    		Element eleRootElement = null;
    		NodeList nlChildNodes = null;
    		int intIndex = 0;
    		Element eleChildNode = null;
    		String strNodeName = "";
    		String strProgramName = "";
    		String strPath = "";
    		
    		if ( filSettingsFile.exists( ) == true )
    		{
    			clsSettingsFileReader = new CXMLReader( );
    			clsSettingsFileReader.LoadXMLIntoMemory("Settings.profile");
    			eleRootElement = clsSettingsFileReader.GetRootElement( );
    			nlChildNodes = eleRootElement.getChildNodes( );
    			for ( intIndex = 0; intIndex < nlChildNodes.getLength( ); intIndex += 1 )
    			{
    				eleChildNode = (Element)nlChildNodes.item(intIndex);
    				strNodeName = eleChildNode.getNodeName( );
    				
    				if ( strNodeName.equals( "PreferredInterface" ) )
    					CUserPreferences.SetPreferredStandardInterface( eleChildNode.getAttribute( "Value" ) );
    				else if ( strNodeName.equals( "PreferredMonitorInterface" ) )
    					CUserPreferences.SetPreferredMonitorInterface( eleChildNode.getAttribute( "Value" ) );
    				else if ( strNodeName.equals("CommandLogLevel") )
    					CUserPreferences.SetCommandOutputLevel( eleChildNode.getAttribute( "Value" ) );
    				else if ( strNodeName.equals( "ApplicationPath" ) )
    				{
    					strProgramName = eleChildNode.getAttribute( "Program" );
    					strPath = eleChildNode.getAttribute( "Path" );
    					CUserPreferences.AddApplicationPath(strProgramName, strPath);
    				}
    				else if ( strNodeName.equals( "DefaultProfile" ) )
    				{
    					String strProfileName = eleChildNode.getAttribute("Profile");
    					String strProfileFolderName = eleChildNode.getAttribute("Feature");
    					CUserPreferences.SetDefaultProfile(strProfileFolderName, strProfileName);
    				}
    			}
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
}
