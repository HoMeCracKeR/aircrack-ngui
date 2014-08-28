/**
 * Process class to handle retrieving the username of the user running the app.
 * @author Paul Bromwell Jr
 * @since May 6, 2014
 * @version 1.0
 */
public class CWhoAmIProcess extends CProcess
{

	/**
	 * Class constructor
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
	 * @version 1.0
	 */
	public CWhoAmIProcess()
	{
		super();
	}

	/**
	 * Runs the WhoAmI process and returns the username of the user running Aircrack-NGUI. 
	 * @author Paul Bromwell Jr
	 * @since May 6, 2014
 	 * @version 1.0
	 * @return Username of the user running Aircrack-NGUI.
	 */
	public String Execute()
	{
		String strUser = "";
		try
		{
			super.RunProcess(new String[] {"whoami"}, true, true, false);
			String strOutput = super.GetAllOutput();
			
			// Unix returns just the username, but Windows returns Domain\Username. Trim out the domain.
			if (CGlobals.clsLocalMachine.GetOperatingSystem() == CLocalMachine.udtOperatingSystemType.WINDOWS)
				strOutput = strOutput.substring(strOutput.lastIndexOf("\\") + 1);
			
			strUser = strOutput.trim(); // trim for good measure
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strUser;
	}
}
