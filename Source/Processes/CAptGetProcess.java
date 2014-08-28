// Imports
import java.util.*;

/**
 * Class used to encapsulate calls to apt-get. (Linux only)
 * @author Paul Bromwell Jr
 * @since Jul 6, 2014
 * @version 1.0
 */
public class CAptGetProcess extends CProcess
{
	/**
	 * Installs one or more packages via apt-get.
	 * @author Paul Bromwell Jr
	 * @param astrPackageNames Array of packages to install
	 * @return True if successful, otherwise false.
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public boolean InstallPackages(String astrPackageNames[])
	{
		boolean blnSuccess = false;
		try
		{
			String[] astrPackagesToInstall = FilterToAvailablePackagesInCache(astrPackageNames);
			if (astrPackagesToInstall.length > 0)
			{
				String strCommand = "sudo apt-get -y install ";
				for (String strPackage : astrPackagesToInstall)
					strCommand += strPackage + " ";
				strCommand = strCommand.trim();
				DProgramOutput dlgProgramOutput = new DProgramOutput("Install Packages", strCommand.split(" "), false);
				dlgProgramOutput.setVisible(true);
				
				blnSuccess = true;
			}
			else
				blnSuccess = false;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccess;
	}
	
	/**
	 * Filters the list of packages to only packages available through apt-get.
	 * @author Paul Bromwell Jr
	 * @param lstUnfilteredList Unfiltered list of required packages
	 * @return Filtered list of packages available through apt-get.
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	private String[] FilterToAvailablePackagesInCache(String[] lstUnfilteredList)
	{
		String[] astrFilteredList = null;
		try
		{
			ArrayList<String> lstFilteredList = new ArrayList<String>(Arrays.asList(lstUnfilteredList));
			CAptCacheProcess clsAptCache = new CAptCacheProcess();
			
			for (int intIndex = 0; intIndex < lstFilteredList.size( ); intIndex += 1)
			{
				if (clsAptCache.ProgramInCache(lstFilteredList.get(intIndex)) == false)
				{
					lstFilteredList.remove(intIndex);
					intIndex --;
				}
			}
			
			astrFilteredList = new String[lstFilteredList.size()];
			astrFilteredList = lstFilteredList.toArray(astrFilteredList);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrFilteredList;
	}
	
	
	/**
	 * Removes one or more packages via apt-get.
	 * @author Paul Bromwell Jr
	 * @param astrPackageNames Array of packages to remove
	 * @return True if successful, otherwise false.
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public boolean RemovePackages(String astrPackageNames[])
	{
		boolean blnSuccess = false;
		try
		{
			String strCommand = "sudo apt-get -y purge ";
			for (String strPackage : astrPackageNames)
				strCommand += strPackage + " ";
			strCommand = strCommand.trim();
			DProgramOutput dlgProgramOutput = new DProgramOutput("Purge Packages", strCommand.split(" "), false);
			dlgProgramOutput.setVisible(true);
			
			blnSuccess = true;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccess;
	}
}
