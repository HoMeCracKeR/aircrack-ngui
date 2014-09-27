public class CReaverProcess extends CProcess 
{
	public enum udtVerbosityType
	{
		VERBOSE,
		VERBOSEST,
		QUIET
	}
	
	// Required
	private String m_strInterface = "";
	private String m_strNetworkBSSID = "";
	
	// Optional
	private String m_strHostSystemMAC = "";
	private String m_strNetworkESSID = "";
	private int m_intChannel = -1;
	private String m_strOutputFile = "";
	private String m_strRestoreSession = "";
	private String m_strExecuteCommand = "";
	private boolean m_blnDaemonize = false;
	private boolean m_blnAutoDetectSettings = false;
	private boolean m_blnFixedChannel = false;
	private boolean m_bln5GHzChannels = false;
	private udtVerbosityType m_udtVerbosity = null;
	
	// Advanced Settings
	private String m_strWPSPin = "";
	private int m_intDelayBetweenAttempts = -1;
	private int m_intLockDelay = -1;
	private int m_intMaximumAttempts = -1;
	private int m_intFailWait = -1;
	private int m_intRecurringDelay = -1;
	private int m_intTimeout = -1;
	private int m_intM57Timeout = -1;
	private boolean m_blnDoNotAssociate = false;
	private boolean m_blnDoNotSendNACKs = false;
	private boolean m_blnUseSmallDHKeys = false;
	private boolean m_blnIgnoreLocks = false;
	private boolean m_blnEAPTerminate = false;
	private boolean m_blnAlwaysSendNACKs = false;
	private boolean m_blnMimickWindows7Registrar = false;
	
	public void SetInterface(String strInterface)
	{
		m_strInterface = strInterface;
	}
	
	public void SetNetworkBSSID(String strBSSID)
	{
		m_strNetworkBSSID = strBSSID;
	}
	
	public void SetHostSystemMAC(String strHostSystemMAC)
	{
		m_strHostSystemMAC = strHostSystemMAC;
	}
	
	public void SetNetworkESSID(String strESSID)
	{
		m_strNetworkESSID = strESSID;
	}
	
	public void SetChannel(int intChannel)
	{
		m_intChannel = intChannel;
	}
	
	public void SetOutputFile(String strOutputFile)
	{
		m_strOutputFile = strOutputFile;
	}
	
	public void SetRestoreSessionFile(String strRestoreSessionFile)
	{
		m_strRestoreSession = strRestoreSessionFile;
	}
	
	public void SetCommandToExecute(String strCommand)
	{
		m_strExecuteCommand = strCommand;
	}
	
	public void Daemonize()
	{
		m_blnDaemonize = true;
	}
	
	public void AutoDetectSettings()
	{
		m_blnAutoDetectSettings = true;
	}
	
	public void UseFixedChannel()
	{
		m_blnFixedChannel = true;
	}
	
	public void Use5GHzChannels()
	{
		m_bln5GHzChannels = true;
	}
	
	public void SetVerbosity(udtVerbosityType udtVerbosity)
	{
		m_udtVerbosity = udtVerbosity;
	}
	
	public void SetWPSPin(String strWPSPin)
	{
		m_strWPSPin = strWPSPin;
	}
	
	public void SetDelayBetweenAttempts(int intDelayBetweenAttempts)
	{
		m_intDelayBetweenAttempts = intDelayBetweenAttempts;
	}
	
	public void SetLockDelay(int intLockDelay)
	{
		m_intLockDelay = intLockDelay;
	}
	
	public void SetMaximumAttempts(int intMaximumAttempts)
	{
		m_intMaximumAttempts = intMaximumAttempts;
	}
	
	public void SetFailWait(int intFailWait)
	{
		m_intFailWait = intFailWait;
	}
	
	public void SetRecurringDelay(int intRecurringDelay)
	{
		m_intRecurringDelay = intRecurringDelay;
	}
	
	public void SetTimeout(int intTimeout)
	{
		m_intTimeout = intTimeout;
	}
	
	public void SetM57Timeout(int intTimeout)
	{
		m_intM57Timeout = intTimeout;
	}
	
	public void DoNotAssociate()
	{
		m_blnDoNotAssociate = true;
	}
	
	public void DoNotSendNACKs()
	{
		m_blnDoNotSendNACKs = true;
	}
	
	public void UseSmallDHKeys()
	{
		m_blnUseSmallDHKeys = true;
	}
	
	public void IgnoreLocks()
	{
		m_blnIgnoreLocks = true;
	}
	
	public void EAPTerminate()
	{
		m_blnEAPTerminate = true;
	}
	
	public void AlwaysSendNACKs()
	{
		m_blnAlwaysSendNACKs = true;
	}
	
	public void MimickWindows7Registrar()
	{
		m_blnMimickWindows7Registrar = true;
	}
	
	public void Start()
	{
		try
		{
			String astrCommand[] = new String[] {"reaver"};
			
			astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_strInterface, astrCommand);
			astrCommand = CAircrackUtilities.AddArgumentToCommand("b", m_strNetworkBSSID, astrCommand);
			
			astrCommand = AddOptionalSettings( astrCommand );
				
			astrCommand = AddAdvancedSettings( astrCommand );
			
			RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	private String[] AddOptionalSettings( String astrCommand[] )
	{
		String astrNewArray[] = null;
		try
		{
			astrNewArray = astrCommand;
			
			if (m_strHostSystemMAC.equals("") == false)		astrNewArray = CAircrackUtilities.AddArgumentToCommand("m", m_strHostSystemMAC, astrNewArray);
			if (m_strNetworkESSID.equals("") == false)		astrNewArray = CAircrackUtilities.AddArgumentToCommand("e", m_strNetworkESSID, astrNewArray);
			if (m_intChannel > -1)							astrNewArray = CAircrackUtilities.AddArgumentToCommand("c", String.valueOf(m_intChannel), astrNewArray);
			if (m_strOutputFile.equals("") == false)		astrNewArray = CAircrackUtilities.AddArgumentToCommand("o", m_strOutputFile, astrNewArray);
			if (m_strRestoreSession.equals("") == false)	astrNewArray = CAircrackUtilities.AddArgumentToCommand("s", m_strRestoreSession, astrNewArray);
			if (m_strExecuteCommand.equals("") == false)	astrNewArray = CAircrackUtilities.AddArgumentToCommand("C", m_strExecuteCommand, astrNewArray);
			if (m_blnDaemonize)								astrNewArray = CAircrackUtilities.AddArgumentToCommand("D", "", astrNewArray);
			if (m_blnAutoDetectSettings)					astrNewArray = CAircrackUtilities.AddArgumentToCommand("a", "", astrNewArray);
			if (m_blnFixedChannel)							astrNewArray = CAircrackUtilities.AddArgumentToCommand("f", "", astrNewArray);
			if (m_bln5GHzChannels)							astrNewArray = CAircrackUtilities.AddArgumentToCommand("5", "", astrNewArray);
			if (m_udtVerbosity != null)
			{
				switch (m_udtVerbosity)
				{
					case VERBOSE:
						astrNewArray = CAircrackUtilities.AddArgumentToCommand("v", "", astrNewArray);
						break;
					case VERBOSEST:
						astrNewArray = CAircrackUtilities.AddArgumentToCommand("vv", "", astrNewArray);
						break;
					case QUIET:
						astrNewArray = CAircrackUtilities.AddArgumentToCommand("q", "", astrNewArray);
						break;
				}
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewArray;
	}

	private String[] AddAdvancedSettings( String astrCommand[] )
	{
		String astrNewCommand[] = null;
		try
		{			
			astrNewCommand = astrCommand;
			
			if (m_strWPSPin.equals("") == false)			astrNewCommand = CAircrackUtilities.AddArgumentToCommand("p", m_strWPSPin, astrNewCommand);
			if (m_intDelayBetweenAttempts > -1)				astrNewCommand = CAircrackUtilities.AddArgumentToCommand("d", String.valueOf(m_intDelayBetweenAttempts), astrNewCommand);
			if (m_intLockDelay > -1)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("l", String.valueOf(m_intLockDelay), astrNewCommand);
			if (m_intMaximumAttempts > -1)					astrNewCommand = CAircrackUtilities.AddArgumentToCommand("g", String.valueOf(m_intMaximumAttempts), astrNewCommand);
			if (m_intFailWait > -1)							astrNewCommand = CAircrackUtilities.AddArgumentToCommand("x", String.valueOf(m_intFailWait), astrNewCommand);
			if (m_intRecurringDelay > -1)					astrNewCommand = CAircrackUtilities.AddArgumentToCommand("r", String.valueOf(m_intRecurringDelay), astrNewCommand);
			if (m_intTimeout > -1)							astrNewCommand = CAircrackUtilities.AddArgumentToCommand("t", String.valueOf(m_intTimeout), astrNewCommand);
			if (m_intM57Timeout > -1)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("T", String.valueOf(m_intM57Timeout), astrNewCommand);
			
			if (m_blnDoNotAssociate)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("A", "", astrNewCommand);
			if (m_blnDoNotSendNACKs)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("N", "", astrNewCommand);
			if (m_blnUseSmallDHKeys)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("S", "", astrNewCommand);
			if (m_blnIgnoreLocks)							astrNewCommand = CAircrackUtilities.AddArgumentToCommand("L", "", astrNewCommand);
			if (m_blnEAPTerminate)							astrNewCommand = CAircrackUtilities.AddArgumentToCommand("E", "", astrNewCommand);
			if (m_blnAlwaysSendNACKs)						astrNewCommand = CAircrackUtilities.AddArgumentToCommand("n", "", astrNewCommand);
			if (m_blnMimickWindows7Registrar)				astrNewCommand = CAircrackUtilities.AddArgumentToCommand("w", "", astrNewCommand);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrNewCommand;
	}
}
