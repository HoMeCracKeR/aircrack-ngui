// --------------------------------------------------------------------------------
// Imports
// --------------------------------------------------------------------------------
import java.util.*;

// --------------------------------------------------------------------------------
// Name: CWhereIsProcess
// Abstract: Process class to handle the "whereis" command ("where" in Windows).
// --------------------------------------------------------------------------------
public class CWhereIsProcess extends CProcess
{
	
	// --------------------------------------------------------------------------------
	// Name: CWhereIsProcess
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public CWhereIsProcess()
	{
		super();
	}
	
	// --------------------------------------------------------------------------------
	// Name: Execute
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------	
	public ArrayList<String> Execute(String strProgramName)
	{
		ArrayList<String> lstLocations = new ArrayList<String>();
		try
		{
			String strCommandName = "";
			switch(CGlobals.clsLocalMachine.GetOperatingSystem())
			{
				case WINDOWS:
					strCommandName = "where";
					break;
				
				case MACOSX:
				case LINUX:
					strCommandName = "whereis";
					break;
			}
			super.RunProcess(new String[] {strCommandName, strProgramName}, true, true, false);
			String strOutput = super.GetAllOutput();
			String astrOutput[] = null;
			
			// Windows and Linux have different output for this process. Annoying, but doable.
			if (CGlobals.clsLocalMachine.GetOperatingSystem() == CLocalMachine.udtOperatingSystemType.WINDOWS)
			{
				// Make sure it's not the Windows fail message
				if (strOutput.trim().equals("INFO: Could not find files for the given pattern(s).") == false)
					astrOutput = strOutput.split("\n");
			}
			else
			{
				// Trim out the fat
				strOutput = strOutput.replace(strProgramName + ":", "").trim();
				
				// Search results are delimited by a space, as opposed to Windows which is a new line
				astrOutput = strOutput.split(" ");
			}
			
			// If we have results, add them to the array list
			if (astrOutput != null)
				for (int intIndex = 0; intIndex < astrOutput.length; intIndex++)
					lstLocations.add(astrOutput[intIndex]);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return lstLocations;
	}
	
}
