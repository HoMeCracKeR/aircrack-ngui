
public class CEassideNGProcess extends CProcess
{
	private String m_strInterface = "";
	private String m_strBuddyNGServerIP = "";
	private String m_strVictimMAC = "";
	private String m_strSourceMAC = "";
	private String m_strSourceIP = "";
	private String m_strRouterIP = "";
	private int m_intChannel = 0; 
	
	public void SetInterface(String strNewInterface)
	{
		m_strInterface = strNewInterface;
	}
	
	public void SetBuddyNGServerIP(String strIP)
	{
		m_strBuddyNGServerIP = strIP;
	}
	
	public void SetVictimMAC(String strNewMAC)
	{
		m_strVictimMAC = strNewMAC;
	}
	
	public void SetSourceMAC(String strNewMAC)
	{
		m_strSourceMAC = strNewMAC;
	}
	
	public void SetSourceIP(String strNewIP)
	{
		m_strSourceIP = strNewIP;
	}
	
	public void SetRouterIP(String strNewIP)
	{
		m_strRouterIP = strNewIP;
	}
	
	public void SetChannel(int intNewChannel)
	{
		m_intChannel = intNewChannel;
	}
	
	public void Start()
	{
		try
		{
			if (m_strInterface.equals("") == false && m_strBuddyNGServerIP.equals("") == false)
			{
				String astrCommand[] = new String[] {"easside-ng", m_strInterface, m_strBuddyNGServerIP};
				if (m_strVictimMAC.equals("") == false)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("v", m_strVictimMAC, astrCommand);
				if (m_strSourceMAC.equals("") == false)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("m", m_strSourceMAC, astrCommand);
				if (m_strSourceIP.equals("") == false)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_strSourceIP, astrCommand);
				if (m_strRouterIP.equals("") == false)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_strRouterIP, astrCommand);
				if (m_intChannel > 0)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("c", String.valueOf(m_intChannel), astrCommand);
				super.RunProcess(astrCommand, true, false, true);
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
