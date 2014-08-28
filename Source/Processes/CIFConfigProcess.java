//Imports
import java.io.*;

/**
 * Class used to encapsulate calls to IFConfig. (Linux/Mac only)
 * @author Paul Bromwell Jr
 * @since Jul 6, 2014
 * @version 1.0
 */
public class CIFConfigProcess extends CProcess
{
	// We're using a class here to operate as a struct. Don't panic.
	/**
	 * Class used to return output to the calling function. Operates as a struct.
	 * @author Paul Bromwell Jr
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public class CIFConfigProcessOutput
	{
		public String strMACAddress = "";
		public String strIPAddress = "";
		public String strBroadcastAddress = "";
		public String strSubnetMask = "";
		public String strIPv6Address = "";
		public boolean blnInterfaceUp = false;
	}
	
	/**
	 * Gets network interface information available through ifconfig and returns it.
	 * @author Paul Bromwell Jr
	 * @param strInterfaceName Name of the interface to get information for
	 * @return CIFConfigProcessOutput object containing the interface's data
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public CIFConfigProcessOutput GetInterfaceInformation(String strInterfaceName)
	{
		CIFConfigProcessOutput clsOutput = null;
		try
		{
			RunProcess(new String[] {"ifconfig", strInterfaceName},  true,  true, false);
			BufferedReader brOutput = new BufferedReader( GetOutput( ) );
			String strBuffer = brOutput.readLine();
			clsOutput = new CIFConfigProcessOutput();
			
			while (strBuffer != null)
			{
				
				// MAC Address
				if (strBuffer.contains("HWaddr"))
					clsOutput.strMACAddress = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "HWaddr");
				
				// IP address
				if (strBuffer.contains("inet addr:"))
					clsOutput.strIPAddress = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "inet addr:");
				
				// Broadcast address
				if (strBuffer.contains("Bcast:"))
					clsOutput.strBroadcastAddress = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "Bcast:");
				
				// Subnet mask
				if (strBuffer.contains("Mask:"))
					clsOutput.strSubnetMask = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "Mask:");
				
				// IPv6 Address
				if (strBuffer.contains("inet6 addr:"))
					clsOutput.strIPv6Address = CAircrackUtilities.GetUnixIFIWConfigOutputField(strBuffer, "inet6 addr:");
				
				// Up or Down
				if (strBuffer.contains("MTU"))
					clsOutput.blnInterfaceUp = strBuffer.contains("UP");
				
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
