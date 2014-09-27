
public class CDSniffProcess extends CProcess
{
	
	private String m_strExpression = "";
	private boolean m_blnHalfDuplexTCPStreamReassembly = false;
	private boolean m_blnDebuggingMode = false;
	private boolean m_blnAutomaticProtocolDetection = false;
	private boolean m_blnDontResolveToHostnames = false;
	private String m_strInterface = "";
	private String m_strPCAPFile = "";
	private int m_intSnapLenBytes = -1;
	private String m_strServicesFile = "";
	private String m_strTriggers = "";
	private String m_strSaveFile = "";
	private String m_strWriteFile = "";
	
	public void SetExpression(String strExpression)
	{
		m_strExpression = strExpression;
	}
	
	public void UseHalfDuplexTCPStreamReassembly()
	{
		m_blnHalfDuplexTCPStreamReassembly = true;
	}
	
	public void UseDebuggingMode()
	{
		m_blnDebuggingMode = true;
	}
	
	public void UseAutomaticProtocolDetection()
	{
		m_blnAutomaticProtocolDetection = true;
	}
	
	public void DontResolveToHostNames()
	{
		m_blnDontResolveToHostnames = true;
	}
	
	public void SetInterface(String strInterface)
	{
		m_strInterface = strInterface;
	}
	
	public void SetPCAPFile(String strPCAPFile)
	{
		m_strPCAPFile = strPCAPFile;
	}
	
	public void SetSnapLenBytes(int intSnapLenBytes)
	{
		m_intSnapLenBytes = intSnapLenBytes;
	}
	
	public void SetServicesFile(String strServicesFile)
	{
		m_strServicesFile = strServicesFile;
	}
	
	public void SetTriggers(String strTriggers)
	{
		m_strTriggers = strTriggers;
	}
	
	public void SetSaveFile(String strSaveFile)
	{
		m_strSaveFile = strSaveFile;
	}
	
	public void SetWriteFile(String strWriteFile)
	{
		m_strWriteFile = strWriteFile;
	}
	
	public void Start()
	{
		try
		{
			String astrCommand[] = new String[] { "dsniff" };
			
			if (m_blnHalfDuplexTCPStreamReassembly)			astrCommand = CAircrackUtilities.AddArgumentToCommand("c", "", astrCommand);
			if (m_blnDebuggingMode)							astrCommand = CAircrackUtilities.AddArgumentToCommand("d", "", astrCommand);
			if (m_blnAutomaticProtocolDetection)			astrCommand = CAircrackUtilities.AddArgumentToCommand("m", "", astrCommand);
    		if (m_blnDontResolveToHostnames)				astrCommand = CAircrackUtilities.AddArgumentToCommand("n", "", astrCommand);
    		if (m_strInterface.equals("") == false)			astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_strInterface, astrCommand);
    		if (m_strPCAPFile.equals("") == false)			astrCommand = CAircrackUtilities.AddArgumentToCommand("p", m_strPCAPFile.trim( ), astrCommand);
    		if (m_intSnapLenBytes > -1)						astrCommand = CAircrackUtilities.AddArgumentToCommand("s", String.valueOf(m_intSnapLenBytes), astrCommand);
    		if (m_strServicesFile.equals("") == false)		astrCommand = CAircrackUtilities.AddArgumentToCommand("f", m_strServicesFile.trim( ), astrCommand);
    		if (m_strTriggers.equals("") == false)			astrCommand = CAircrackUtilities.AddArgumentToCommand("t", m_strTriggers.trim( ), astrCommand);
    		if (m_strSaveFile.equals("") == false)			astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_strSaveFile.trim( ), astrCommand);
    		if (m_strWriteFile.equals("") == false)			astrCommand = CAircrackUtilities.AddArgumentToCommand("w", m_strWriteFile.trim( ), astrCommand);
    		if (m_strExpression.equals("") == false)		astrCommand = CAircrackUtilities.AddArgumentToArray(m_strExpression, astrCommand);
    		
    		RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
