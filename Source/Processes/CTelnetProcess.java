public class CTelnetProcess extends CProcess
{
	public enum udtForceIPVersionType
	{
		IPV4,
		IPV6
	}
	
	private String m_strTarget = "";
	private int m_intPort = 0;
	private boolean m_bln8BitOperation = false;
	private boolean m_blnDisableEscapeCharacter = false;
	private boolean m_bln8BitOutputPath = false;
	private boolean m_blnAttemptAutomaticLogin = false;
	private boolean m_blnDebuggingMode = false;
	private boolean m_blnEmulateRLogin = false;
	private udtForceIPVersionType m_udtForceIPVersion = null;
	private String m_strBindLocalSocket = "";
	private String m_strTypeOfService  = "";
	private char m_chrEscapeCharacter = ' ';
	private String m_strLoginName = "";
	private String m_strTraceFile = "";
	
	public void SetTarget(String strTarget)
	{
		m_strTarget = strTarget;
	}
	
	public void SetTarget(String strTarget, int intPort)
	{
		SetTarget(strTarget);
		SetPort(intPort);
	}
	
	public void SetPort(int intPort)
	{
		m_intPort = intPort;
	}
	
	public void Enable8BitOperation()
	{
		m_bln8BitOperation = true;
	}
	
	public void DisableEscapeCharacter()
	{
		m_blnDisableEscapeCharacter = true;
	}
	
	public void Enable8BitOutputPath()
	{
		m_bln8BitOutputPath = true;
	}
	
	public void AttemptAutomaticLogin()
	{
		m_blnAttemptAutomaticLogin = true;
	}
	
	public void EnableDebuggingMode()
	{
		m_blnDebuggingMode = true;
	}
	
	public void EmulateRLogin()
	{
		m_blnEmulateRLogin = true;
	}
	
	public void SetIPVersion(udtForceIPVersionType udtIPVersion)
	{
		m_udtForceIPVersion = udtIPVersion;
	}
	
	public void SetBindLocalSocket(String strLocalSocket)
	{
		m_strBindLocalSocket = strLocalSocket;
	}
	
	public void SetTypeOfService(String strTypeOfService)
	{
		m_strTypeOfService = strTypeOfService;
	}
	
	public void SetEscapeCharacter(char chrEscapeCharacter)
	{
		m_chrEscapeCharacter = chrEscapeCharacter;
	}
	
	public void SetLoginName(String strLoginName)
	{
		m_strLoginName = strLoginName;
	}
	
	public void SetTraceFile(String strTraceFile)
	{
		m_strTraceFile = strTraceFile;
	}
	
	public void StartTelnet()
	{
		try
		{
			if (m_strTarget == "" || m_intPort == 0)
				return;
			
			String astrArguments[] = new String[] {"telnet"};
			if (m_bln8BitOperation)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-B", astrArguments);
			if (m_blnDisableEscapeCharacter)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-E", astrArguments);
			if (m_bln8BitOutputPath)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-L", astrArguments);
			if (m_blnAttemptAutomaticLogin)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-a", astrArguments);
			if (m_blnDebuggingMode)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-d", astrArguments);
			if (m_blnEmulateRLogin)
				astrArguments = CAircrackUtilities.AddArgumentToArray("-r", astrArguments);
			if (m_udtForceIPVersion != null)
			{
				switch (m_udtForceIPVersion)
				{
					case IPV4:
						astrArguments = CAircrackUtilities.AddArgumentToArray("-4", astrArguments);
						break;
					case IPV6:
						astrArguments = CAircrackUtilities.AddArgumentToArray("-6", astrArguments);
						break;
				}
			}
			if (m_strBindLocalSocket != "")
			{
				astrArguments = CAircrackUtilities.AddArgumentToArray("-b", astrArguments);
				astrArguments = CAircrackUtilities.AddArgumentToArray(m_strBindLocalSocket, astrArguments);
			}
			if (m_strTypeOfService != "")
			{
				astrArguments = CAircrackUtilities.AddArgumentToArray("-S", astrArguments);
				astrArguments = CAircrackUtilities.AddArgumentToArray(m_strTypeOfService, astrArguments);
			}
			if (m_chrEscapeCharacter != ' ')
			{
				astrArguments = CAircrackUtilities.AddArgumentToArray("-e", astrArguments);
				astrArguments = CAircrackUtilities.AddArgumentToArray(String.valueOf(m_chrEscapeCharacter), astrArguments);
			}
			if (m_strLoginName != "")
			{
				astrArguments = CAircrackUtilities.AddArgumentToArray("-l", astrArguments);
				astrArguments = CAircrackUtilities.AddArgumentToArray(m_strLoginName, astrArguments);
			}
			if (m_strTraceFile != "")
			{
				astrArguments = CAircrackUtilities.AddArgumentToArray("-n", astrArguments);
				astrArguments = CAircrackUtilities.AddArgumentToArray(m_strTraceFile, astrArguments);
			}
			
			astrArguments = CAircrackUtilities.AddArgumentToArray(m_strTarget, astrArguments);
			astrArguments = CAircrackUtilities.AddArgumentToArray(String.valueOf(m_intPort), astrArguments);
			
			super.RunProcess(astrArguments, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
