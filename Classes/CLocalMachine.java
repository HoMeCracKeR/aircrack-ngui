import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;


public class CLocalMachine
{
	enum udtOperatingSystemType
	{
		WINDOWS,
		MACOSX,
		LINUX
	}
	
	public class CPackageStatus
	{
		public String strPackageName;
		public boolean blnInstalled;
	}
	
	private String m_strCurrentUser = "";
	private int m_intMonitorModeInterfacesCount = -1;
	private udtOperatingSystemType m_udtOperatingSystem = null;	

	// --------------------------------------------------------------------------------
    // Name: GetOperatingSystem
    // Abstract: Populates and/or returns the current user's operating system
    // --------------------------------------------------------------------------------
	public udtOperatingSystemType GetOperatingSystem()
	{
		if (m_udtOperatingSystem == null) {

			String strOperatingSystem = System.getProperty("os.name").toLowerCase(); 
			if (strOperatingSystem.indexOf("windows") >= 0)
				m_udtOperatingSystem = udtOperatingSystemType.WINDOWS;
			else if (strOperatingSystem.indexOf("mac") >= 0)
				m_udtOperatingSystem = udtOperatingSystemType.MACOSX;
			else if (strOperatingSystem.indexOf("nix") >= 0 || strOperatingSystem.indexOf("nux") >= 0 || strOperatingSystem.indexOf("aix") >= 0)
				m_udtOperatingSystem = udtOperatingSystemType.LINUX;
		}
		return m_udtOperatingSystem;
	}
	
	// --------------------------------------------------------------------------------
    // Name: GetCurrentUser
    // Abstract: Returns the current user of the system
    // --------------------------------------------------------------------------------
	public String GetCurrentUser()
	{
		if (m_strCurrentUser.equals(""))
			PopulateActiveUser();
		return m_strCurrentUser;
	}
	
    // --------------------------------------------------------------------------------
    // Name: GetActiveUser
    // Abstract: Gets the user that Aircrack-NGUI is running under
    // --------------------------------------------------------------------------------
    public void PopulateActiveUser()
    {
        try 
        {
    		CWhoAmIProcess clsWhoAmI = new CWhoAmIProcess();
    		
    		m_strCurrentUser = clsWhoAmI.Execute();
            
            clsWhoAmI.CloseProcess();
        } 
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }
	
    // --------------------------------------------------------------------------------
    // Name: GetAllMonitorableInterfaces
    // Abstract: Gets a list of monitorable network interfaces
    // --------------------------------------------------------------------------------
    public CNetworkInterface[] GetAllMonitorableInterfaces()
    {
    	CNetworkInterface aclsMonitorableInterfaces[] = new CNetworkInterface[ 0 ];
        try
        {
        	CProcess clsSearch = new CProcess(new String[] {"iwconfig"}, false, true, false);
        	BufferedReader brOutput = new BufferedReader( clsSearch.GetOutput() );
        	String strBuffer = brOutput.readLine( );
        	
        	// Loop through each line of the iwconfig output
        	while ( strBuffer != null )
        	{

        		// Specifying an interface name?
        		if ( strBuffer.indexOf("IEEE") > 0 )
        		{
        			String strInterfaceName = strBuffer.substring(0, strBuffer.indexOf("IEEE"));
        			strInterfaceName = strInterfaceName.trim( );
        			
        			CNetworkInterface clsNewInterface = new CNetworkInterface( strInterfaceName );
        			if (clsNewInterface.GetMode().equals("MONITOR"))
        			{
        				// Add to the return array
        				CNetworkInterface aclsTempArray[] = new CNetworkInterface[aclsMonitorableInterfaces.length + 1];
        				for ( int intIndex = 0; intIndex < aclsMonitorableInterfaces.length; intIndex += 1 )
        					aclsTempArray[intIndex] = aclsMonitorableInterfaces[intIndex];
        				aclsTempArray[aclsTempArray.length - 1] = clsNewInterface;
        				aclsMonitorableInterfaces = aclsTempArray;
        			}
        		}
        		        		
        		strBuffer = brOutput.readLine( );
        	}
     
        	m_intMonitorModeInterfacesCount = aclsMonitorableInterfaces.length;
        	
        	clsSearch.CloseProcess();
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
        return aclsMonitorableInterfaces;
    }
    
    // --------------------------------------------------------------------------------
    // Name: GetAvailableNetworkInterfaces
    // Abstract: Gets a list of available network interfaces
    // --------------------------------------------------------------------------------
    public CNetworkInterface[] GetAvailableNetworkInterfaces()
    {
        CNetworkInterface[] aclsNetworkInterfaces = new CNetworkInterface[0];
        
        try 
        {
            int intIndex = 0;
            CProcess clsIfconfig = new CProcess(new String[] {"ifconfig"}, false, true, false);
            BufferedReader brOutput = new BufferedReader( clsIfconfig.GetOutput( ) ); 
            String strBuffer = brOutput.readLine( );
            
            while( strBuffer != null ) 
            {
            	// This is how Fedora specifies interfaces
                if (strBuffer.indexOf(": flags=") > 0)
                {
                    // New network interface
                	CNetworkInterface[] aclsTemp = new CNetworkInterface[aclsNetworkInterfaces.length + 1];
                    for ( intIndex = 0; intIndex < aclsTemp.length; intIndex += 1)
                    	aclsTemp[intIndex] = aclsNetworkInterfaces[intIndex];
                    aclsTemp[intIndex] = new CNetworkInterface(strBuffer.substring(0, strBuffer.indexOf(": flags=")).trim());
                    aclsNetworkInterfaces = aclsTemp;
                }
                
                // This is how Ubuntu/BackTrack specifies interfaces
                else if (strBuffer.indexOf("Link encap:") > 0)
                {
                	// New network interface
                	CNetworkInterface[] aclsTemp = new CNetworkInterface[aclsNetworkInterfaces.length + 1];
                    for ( intIndex = 0; intIndex < aclsNetworkInterfaces.length; intIndex += 1)
                    	aclsTemp[intIndex] = aclsNetworkInterfaces[intIndex];
                    aclsTemp[intIndex] = new CNetworkInterface(strBuffer.substring(0, strBuffer.indexOf("Link encap:")).trim());
                    aclsNetworkInterfaces = aclsTemp;
                }
                strBuffer = brOutput.readLine( ); 
            }
            
            clsIfconfig.CloseProcess();
        } 
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
        
        return aclsNetworkInterfaces;
    }
    
    // --------------------------------------------------------------------------------
    // Name: GetNetworkInterfacesInMonitorMode
    // Abstract: Gets a list of network interfaces in monitor mode
    // --------------------------------------------------------------------------------
    public CNetworkInterface[] GetNetworkInterfacesReadyForMonitorMode()
    {
    	CNetworkInterface[] aclsNetworkInterfaces = new CNetworkInterface[ 0 ];
        
        try 
        {
            int intIndex = 0;
            
            CProcess clsAirmonNG = new CProcess(new String[] {"airmon-ng"}, false, true, false);
            BufferedReader brOutput = new BufferedReader( clsAirmonNG.GetOutput( ) ); 
            String strBuffer = brOutput.readLine( );
            
            // First two lines are garbage (blank space). Read again.
            brOutput.readLine( );
            brOutput.readLine( );
            
            // Header row. Keep reading to the data.
            brOutput.readLine( );
            brOutput.readLine( );
            
            // Read to the end of the buffer
            while( strBuffer != null ) 
            {
                if (strBuffer.indexOf("\t") > 0)
                {
                    // New network interface
                	CNetworkInterface[] aclsNewNetworkInterfaces = new CNetworkInterface[aclsNetworkInterfaces.length + 1];
                    for ( intIndex = 0; intIndex < aclsNewNetworkInterfaces.length; intIndex += 1)
                    	aclsNewNetworkInterfaces[intIndex] = aclsNetworkInterfaces[intIndex];
                    aclsNewNetworkInterfaces[intIndex] = new CNetworkInterface(strBuffer.substring(0, strBuffer.indexOf("\t")).trim());
                    aclsNetworkInterfaces = aclsNewNetworkInterfaces;
                }
                strBuffer = brOutput.readLine(); 
            }
            
            clsAirmonNG.CloseProcess();
        } 
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        } 
        
        return aclsNetworkInterfaces;        
    }
    
    // --------------------------------------------------------------------------------
    // Name: GetNumberOfInterfacesInMonitorMode
    // Abstract: Returns the number of interfaces in monitor mode
    // --------------------------------------------------------------------------------
    public int GetNumberOfInterfacesInMonitorMode( )
    {
    	try
    	{
	    	// If the count isn't set yet
	    	if (m_intMonitorModeInterfacesCount == -1)
	    	{
	    		// This will populate the count
	    		GetAllMonitorableInterfaces();
	    	}
	        return m_intMonitorModeInterfacesCount;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    		return 0;
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: ProgramInstalled
    // Abstract: Returns true if a program can be found on the user's computer in their
    //			 PATH variable. Returns false if not.
    // --------------------------------------------------------------------------------
    public boolean ProgramInstalled(String strProgramName)
    {
    	boolean blnProgramInstalled = false;
    	try
    	{
    		String strPathToProgram = CUserPreferences.GetApplicationPath( CAircrackUtilities.GetProgramMap( strProgramName ) );
    		if ( strPathToProgram.equals( "" ) )
    		{
    			CWhereIsProcess clsWhereIs = new CWhereIsProcess();
    			ArrayList<String> lstLocations = clsWhereIs.Execute(strProgramName);
    			
    			if (lstLocations.size() > 1 || lstLocations.get(0).equals("") == false)
    				blnProgramInstalled = true;
	    		
	    		clsWhereIs.CloseProcess();
    		}
    		else
    		{
    			File filApplicationPath = new File(strPathToProgram);
    			for (String strFile : filApplicationPath.list())
    			{
    				// Windows uses "\" while Mac/Linux/Unix uses "/". Do a string replace so we don't have to accommodate for two
    				// different types of paths
    				strFile = strFile.replace("\\", "/");
    				strFile = strFile.substring(strFile.lastIndexOf("/") + 1);
    				
    				String strProgramNameEXE = strFile + ".exe";
    				String strProgramNameCOM = strFile + ".com";
    				String strProgramNameBAT = strFile + ".bat";
    				String strProgramNameSH = strFile + ".sh";
    				
    				if (strFile.equals(strProgramName) || strFile.equals(strProgramNameEXE) || strFile.equals(strProgramNameCOM) ||
    					strFile.equals(strProgramNameBAT) || strFile.equals(strProgramNameSH))
    				{
    					blnProgramInstalled = true;
    					break;
    				}
    			}
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	return blnProgramInstalled;
    }
    
    // --------------------------------------------------------------------------------
    // Name: OneOfProgramsInstalled
    // Abstract: Returns true if at least one of the programs in the array are installed.
    //			 Returns false if none of the programs are installed.
    // --------------------------------------------------------------------------------
    public boolean OneOfProgramsInstalled( String astrProgramNames[] )
    {
    	boolean blnOneOfProgramsInstalled = false;
    	try
    	{
    		for ( int intIndex = 0; intIndex < astrProgramNames.length; intIndex += 1 )
    		{
    			if ( ProgramInstalled( astrProgramNames[intIndex] ) == true )
    			{
    				blnOneOfProgramsInstalled = true;
    				break;
    			}
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	return blnOneOfProgramsInstalled;
    }
    
    // --------------------------------------------------------------------------------
    // Name: GetAllNetworkDevices
    // Abstract: Retrieves all network devices listed in ifconfig (up or down).
    // --------------------------------------------------------------------------------
    public String[] GetAllNetworkDevices( )
    {
    	String astrNetworkDevices[] = new String[ 0 ];
    	try
    	{
    		String astrCommand[] = new String[] {"ifconfig", "-a"};
    		CProcess clsIfConfigAll = new CProcess(astrCommand, true, true, false);
    		BufferedReader brOutput = new BufferedReader( clsIfConfigAll.GetOutput( ) );
    		String strBuffer = brOutput.readLine( );
    		String astrTempArray[];
    		
    		while ( strBuffer != null )
    		{
    			if ( strBuffer.contains("Link encap:") )
    			{
    				strBuffer = strBuffer.substring(0, strBuffer.indexOf("Link encap:"));
    				astrTempArray = new String[ astrNetworkDevices.length + 1 ];
    				for ( int intIndex = 0; intIndex < astrTempArray.length - 1; intIndex += 1 )
    					astrTempArray[intIndex] = astrNetworkDevices[intIndex];
    				astrTempArray[astrTempArray.length - 1] = strBuffer.trim();
    				astrNetworkDevices = astrTempArray;
    			}
    			strBuffer = brOutput.readLine( );
    		}
    		
    		clsIfConfigAll.CloseProcess();
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	return astrNetworkDevices;
    }

    // --------------------------------------------------------------------------------
    // Name: SetClipboardContents
    // Abstract: Copies the contents to the clipboard
    // --------------------------------------------------------------------------------
    public void SetClipboardContents(String strNewClipboardContents)
    {
    	try
    	{
    		StringSelection ssStringSelection = new StringSelection( strNewClipboardContents );
    		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ssStringSelection, null);
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // --------------------------------------------------------------------------------
    // Name: GetProcessIDFromLastRunningProcess
    // Abstract: Gets the PID from the process that was started the most recently by
	//			 a certain program name.
    // --------------------------------------------------------------------------------
	public int GetProcessIDFromLastRunningProcess(String strProcessName)
	{
		int intProcessID = -1;
		try
		{
			String astrCommand[] = new String[] {"RequiredScripts/GetLastRunningProcess.sh", strProcessName};
			CProcess clsGetProcessID = new CProcess(astrCommand, false, true, false);
			BufferedReader brOutput = new BufferedReader( clsGetProcessID.GetOutput( ) );
			String strBuffer = brOutput.readLine( );
			strBuffer = strBuffer.trim();
			if ( strBuffer.equals("") == false )
			{
				strBuffer = strBuffer.substring(0, strBuffer.indexOf(" ")).trim();
				intProcessID = Integer.valueOf(strBuffer);
			}
			clsGetProcessID.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intProcessID;
	}

    // --------------------------------------------------------------------------------
    // Name: KillRunningProcess
    // Abstract: Destroys a running process. Report failure if it doesn't work!
    // --------------------------------------------------------------------------------
	public boolean KillRunningProcess(int intProcessID)
	{
		boolean blnSuccessful = false;
		try
		{
			String astrEasyWayCommand[] = new String[] {"kill", "-15", String.valueOf(intProcessID)};
			String astrHardWayCommand[] = new String[] {"kill", "-9", String.valueOf(intProcessID)};
			CProcess clsDoThingsTheEasyWay = new CProcess(astrEasyWayCommand, true, true, false);
			CProcess clsDoThingsTheHardWay = null;
			
			if ( intProcessID != -1 ) // If we try to kill process ID -1, it will kill all processes (not good!)
			{
				
				Thread.sleep(200); // Wait 200 milliseconds
				
				// Did we keel it?
				int intSurvivingProcessID = GetProcessIDFromLastRunningProcess(String.valueOf(intProcessID));
				if ( intSurvivingProcessID == intProcessID )
				{
					// So, I see you like to play rough.
					clsDoThingsTheHardWay = new CProcess(astrHardWayCommand, true, true, false );
				
					Thread.sleep(200); // Wait 200 milliseconds
					
					intSurvivingProcessID = GetProcessIDFromLastRunningProcess(String.valueOf(intProcessID));
					
					if ( intSurvivingProcessID == -1 )
						blnSuccessful = true;
					
					clsDoThingsTheHardWay.CloseProcess();
				}
				else
				{
					blnSuccessful = true;
				}
				
				clsDoThingsTheEasyWay.CloseProcess();
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccessful;
	}

	// --------------------------------------------------------------------------------
    // Name: GetNonMonitorModeDevices
    // Abstract: Returns the devices that aren't in monitor mode
    // --------------------------------------------------------------------------------
	public CNetworkInterface[] GetNonMonitorModeDevices()
	{
		CNetworkInterface aclsNonMonitorModeInterfaces[] = new CNetworkInterface[ 0 ];
		try
		{
			CNetworkInterface aclsInterfaces[] = GetAvailableNetworkInterfaces( );
			CNetworkInterface aclsMonitorInterfaces[] = GetAllMonitorableInterfaces( );
			boolean blnIsMonitorInterface = false;
			
			for ( int intIndex = 0; intIndex < aclsInterfaces.length; intIndex += 1 )
			{
				blnIsMonitorInterface = false;
				for ( int intIndex2 = 0; intIndex2 < aclsMonitorInterfaces.length; intIndex2 += 1 )
				{
					if (aclsInterfaces[intIndex].GetInterface().equals(aclsMonitorInterfaces[intIndex2].GetInterface()))
						blnIsMonitorInterface = true;
				}
				if ( blnIsMonitorInterface == false )
				{
					CNetworkInterface aclsTemp[] = new CNetworkInterface[aclsNonMonitorModeInterfaces.length + 1];
					for (int intCopyIndex = 0; intCopyIndex < aclsNonMonitorModeInterfaces.length; intCopyIndex += 1)
						aclsTemp[intCopyIndex] = aclsNonMonitorModeInterfaces[intCopyIndex];
					aclsTemp[aclsTemp.length - 1] = aclsInterfaces[intIndex];
					aclsNonMonitorModeInterfaces = aclsTemp;
				}					
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return aclsNonMonitorModeInterfaces;
	}

	/**
	 * Gets a list of statuses for the required packages and returns as an ArrayList
	 * @author Paul Bromwell Jr
	 * @return List of package statuses
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	public ArrayList<CPackageStatus> GetPackageStatuses()
	{
		ArrayList<CPackageStatus> lstPackageStatuses = new ArrayList<CPackageStatus>();
		try
		{
			String[] astrPackages = CAircrackUtilities.GetRequiredPackages();

			for (int intIndex = 0; intIndex < astrPackages.length; intIndex++)
			{
				CPackageStatus clsNewPackageStatus = new CPackageStatus();
				clsNewPackageStatus.strPackageName = astrPackages[intIndex];
				clsNewPackageStatus.blnInstalled = ProgramInstalled(astrPackages[intIndex]);
				
				lstPackageStatuses.add(clsNewPackageStatus);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return lstPackageStatuses;
	}
	
	// --------------------------------------------------------------------------------
    // Name: ManagePackages
    // Abstract: Handles adding or removing of packages
    // --------------------------------------------------------------------------------
	public void ManagePackages(String strAction, String astrPackages[])
	{
		try
		{
			CAptGetProcess clsAptGetProcess = new CAptGetProcess();
			if (strAction.equals("install") && clsAptGetProcess.InstallPackages(astrPackages) == false)
				JOptionPane.showMessageDialog(null, "None of the selected packages are eligible for install.", "Cannot Install Packages", JOptionPane.OK_OPTION);
			else if (strAction.equals("purge") && clsAptGetProcess.RemovePackages(astrPackages) == false)
				JOptionPane.showMessageDialog(null,  "Failed to purge packages from the system.", "Cannot Purge Packages", JOptionPane.OK_OPTION);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
}
