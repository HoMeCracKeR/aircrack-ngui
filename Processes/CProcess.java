// --------------------------------------------------------------------------------
// Name: CProcess
// Abstract: Base process class to be inherited by other process types
// --------------------------------------------------------------------------------
import java.io.*;

public class CProcess
{
	private Process m_prcRunningProcess = null;
	private String m_astrRanCommand[] = null;
	
	// --------------------------------------------------------------------------------
	// Name: CProcess
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public CProcess()
	{
	}

	// --------------------------------------------------------------------------------
	// Name: CProcess
	// Abstract: Parameterized constructor
	// --------------------------------------------------------------------------------
	public CProcess(String astrCommand[], boolean blnRedirectErrorStream, boolean blnWaitForCompletion, boolean blnImportantCommand)
	{
		RunProcess(astrCommand, blnRedirectErrorStream, blnWaitForCompletion, blnImportantCommand);
	}

	// --------------------------------------------------------------------------------
	// Name: RunProcess
	// Abstract: Creates a new process based on the provided parameters
	// --------------------------------------------------------------------------------
	public void RunProcess(String astrCommand[], boolean blnRedirectErrorStream, boolean blnWaitForCompletion, boolean blnImportantCommand)
	{
		try
		{
			if (m_prcRunningProcess != null)
				CloseProcess();
			
			m_astrRanCommand = astrCommand;
			
			LogCommandToFile( blnImportantCommand );
			
			ProcessBuilder pbBuilder = new ProcessBuilder( astrCommand );
			pbBuilder.redirectErrorStream( blnRedirectErrorStream );
			m_prcRunningProcess = pbBuilder.start( );
			if ( blnWaitForCompletion )
				m_prcRunningProcess.waitFor( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	

	// --------------------------------------------------------------------------------
	// Name: LogCommandToFile
	// Abstract: Logs the ran command to a file (if the user specified)
	// --------------------------------------------------------------------------------
	private void LogCommandToFile(boolean blnImportantCommand)
	{
		try
		{
			String strProgramOutputLevel = CUserPreferences.GetCommandOutputLevel( );
			
			if ( strProgramOutputLevel.equals("ALL") || ( strProgramOutputLevel.equals("IMPORTANT") && blnImportantCommand ) )
			{
				String strProgramText = "";
				for ( int intIndex = 0; intIndex < m_astrRanCommand.length; intIndex += 1 )
				{
					if ( strProgramText.equals( "" ) == false )
						strProgramText += " ";
					if ( m_astrRanCommand[intIndex].contains(" ") == true )
						strProgramText += "\"" + m_astrRanCommand[intIndex].replace("\"", "\\\"");
					else
						strProgramText += m_astrRanCommand[intIndex].replace("\"", "\\\"");
				}
				CAircrackUtilities.SaveTextToFile( strProgramText + "\r\n", "CommandOutput.txt" );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetOutput
	// Abstract: Gets the output stream of the process
	// --------------------------------------------------------------------------------
	public InputStreamReader GetOutput()
	{
		if (m_prcRunningProcess != null)
			return new InputStreamReader(m_prcRunningProcess.getInputStream());
		else
			return null;
	}

	// --------------------------------------------------------------------------------
	// Name: GetAllOutput
	// Abstract: Gets all of the content from the output stream of the process
	// --------------------------------------------------------------------------------	
	public String GetAllOutput()
	{
		String strOutput = "";
		try
		{
			BufferedReader brOutput = new BufferedReader(GetOutput());
			
			String strBuffer;
			do
			{
				strBuffer = brOutput.readLine();
				if (strBuffer != null)
					strOutput += strBuffer + "\n";
				
			} while (strBuffer != null);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return strOutput;
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetInput
	// Abstract: Gets the input stream of the process
	// --------------------------------------------------------------------------------
	public OutputStreamWriter GetInput()
	{
		if (m_prcRunningProcess != null)
			return new OutputStreamWriter(m_prcRunningProcess.getOutputStream());
		else
			return null;
	}

	// --------------------------------------------------------------------------------
	// Name: CloseProcess
	// Abstract: Closes the process and performs clean-up.
	// --------------------------------------------------------------------------------
	public void CloseProcess()
	{
		try
		{
			if (m_prcRunningProcess != null)
			{
				m_prcRunningProcess.getErrorStream().close();
				m_prcRunningProcess.getInputStream().close();
				m_prcRunningProcess.getOutputStream().close();
				m_prcRunningProcess.destroy();
				m_prcRunningProcess = null;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Retrieves the process's exit code. If the process hasn't completed yet, Integer.MIN_VALUE.
	 * @author Paul Bromwell Jr
	 * @return Process exit code, Integer.MIN_VALUE if the process hasn't completed yet.
	 * @since Jul 11, 2014
	 * @version 1.0
	 */
	public int GetExitCode()
	{
		int intExitCode = 0;
		try
		{
			intExitCode = m_prcRunningProcess.exitValue();
		}
		catch (IllegalThreadStateException itseError)
		{
			// This means the process isn't done yet, which is fine.
			intExitCode = Integer.MIN_VALUE;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intExitCode;
	}
	
	
	// --------------------------------------------------------------------------------
	// Name: LogCommandToFile
	// Abstract: Logs the ran command to a file (if the user specified)
	// --------------------------------------------------------------------------------
	@Override
	protected void finalize() throws Throwable
	{
		if (m_prcRunningProcess != null)
		{
			CloseProcess();
			throw new Exception("Process left unclosed. Caught by the garbage collector.");
		}
	}
	
	
}
