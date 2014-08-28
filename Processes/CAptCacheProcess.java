//Imports
import java.io.*;

/**
 * Class used to encapsulate calls to apt-cache. (Linux only)
 * @author Paul Bromwell Jr
 * @since Jul 6, 2014
 * @version 1.0
 */
public class CAptCacheProcess extends CProcess
{
	/**
	 * Determines if the specified package is in the cache.
	 * @author Paul Bromwell Jr
	 * @param strPackageName Name of the package to check in the cache
	 * @return True if the package is in the cache, otherwise false.
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public boolean ProgramInCache(String strPackageName)
	{
		boolean blnProgramInCache = false;
		try
		{
			RunProcess(new String[] {"apt-cache", "search", strPackageName}, true, true, true);
			BufferedReader brOutput = new BufferedReader(GetOutput());
			String strBuffer = brOutput.readLine();
			if (strBuffer != null && strBuffer.equals("") == false)
			{
				while (strBuffer != null)
				{
					if (strBuffer.startsWith(strPackageName + " "))
					{
						blnProgramInCache = true;
						break;
					}
					
					strBuffer = brOutput.readLine();
				}
			}
			CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnProgramInCache;
	}
}
