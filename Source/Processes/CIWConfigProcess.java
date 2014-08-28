//Imports
import java.io.*;

/**
 * Class used to encapsulate calls to iwconfig (Linux/Mac only)
 * @author Paul Bromwell Jr
 * @since Jul 6, 2014
 * @version 1.0
 */
public class CIWConfigProcess extends CProcess
{
	
	/**
	 * Class used to return output to the calling function. Operates as a struct.
	 * @author Paul Bromwell Jr
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public class CIWConfigProcessOutput
	{
		public String strIEEEMode = "";
		public String strESSID = "";
		public String strMode = "";
		public String strFrequency = "";
		public String strBSSID = "";
	}
	
	/**
	 * Gets network interface information available through iwconfig and returns it.
	 * @author Paul Bromwell Jr
	 * @param strInterfaceName
	 * @return CIWConfigProcessOutput object containing the interface's data
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public CIWConfigProcessOutput GetInterfaceInformation(String strInterfaceName)
	{
		CIWConfigProcessOutput clsOutput = null;
		try
		{
			RunProcess(new String[] {"iwconfig", strInterfaceName}, true, true, false);
			BufferedReader brOutput = new BufferedReader( GetOutput( ) );
			String strBuffer = brOutput.readLine();
			
			// If the interface in question doesn't have a wireless extension
			if (strBuffer.contains("no wireless extensions"))
				return clsOutput; // Don't bother with the rest of this

			clsOutput = new CIWConfigProcessOutput();
			
			while (strBuffer != null)
			{
				
				// IEEE Mode
				if (strBuffer.contains("IEEE"))
					clsOutput.strIEEEMode = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "IEEE");
				
				// ESSID
				if (strBuffer.contains("ESSID:"))
					clsOutput.strESSID = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer.replace("\"", ""), "ESSID:");
				
				// Wireless Mode
				if (strBuffer.contains("Mode:"))
					clsOutput.strMode = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "Mode:").toUpperCase();
				
				// Frequency
				if (strBuffer.contains("Frequency"))
					clsOutput.strFrequency = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "Frequency:");
				
				// BSSID
				if (strBuffer.contains("Access Point:"))
					clsOutput.strBSSID = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "Access Point:");
				
				strBuffer = brOutput.readLine();
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return clsOutput;
	}

}
