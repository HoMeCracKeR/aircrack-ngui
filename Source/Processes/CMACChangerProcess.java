import java.io.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public class CMACChangerProcess extends CProcess
{
	
	public void SetSpecificMAC(String strInterface, String strNewMAC)
	{
		try
		{
			if (CAircrackUtilities.ValidMACAddress(strNewMAC))
			{
				String astrCommand[] = new String[] {"macchanger", "-m", strNewMAC, strInterface};
				super.RunProcess(astrCommand, true, true, true);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void SetRandomMAC(String strInterface)
	{
		try
		{
			String astrCommand[] = new String[] {"macchanger", "-A", strInterface};
			super.RunProcess(astrCommand, true, true, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public void GetManufacturers(String strSearch)
	{
		try
		{
			String astrCommand[] = new String[] {"macchanger", "--list=" + strSearch};
			super.RunProcess(astrCommand, true, true, false);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public String[] GetInterfaceMACAndManufacturer(String strInterface)
	{
		String astrResult[] = null;
		try
		{
			String astrCommand[] = new String[] {"macchanger", "-s", strInterface};
			super.RunProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader(GetOutput());
			String strCurrentInformation = brOutput.readLine().replaceAll("Current MAC: ", "").trim();
			Matcher mhrMatcher = CAircrackUtilities.GetMACAddressFormat().matcher(strCurrentInformation);
			
			if (mhrMatcher.find())
			{
				MatchResult rstResult = mhrMatcher.toMatchResult();
				int intMACStartIndex = rstResult.start();
				int intMACEndIndex = rstResult.end();
				
				String strMACAddress = strCurrentInformation.substring(intMACStartIndex, intMACEndIndex);
				String strManufacturer = strCurrentInformation.substring(strCurrentInformation.indexOf("(") + 1, strCurrentInformation.lastIndexOf(")"));

				astrResult = new String[] {strMACAddress, strManufacturer};
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrResult;
	}
}
