/**
 * Class encapsulating calls to packetforge-ng.
 * @author Paul Bromwell Jr
 * @since Jul 15, 2014
 * @version 1.0
 */
public class CPacketForgeNGProcess extends CProcess
{
	// Enumerations
	public enum udtPacketTypeType
	{
		ARP,
		UDP,
		ICMP,
		Null,
		Custom
	}
	
	// Properties
	private udtPacketTypeType m_udtPacketType = null;
	private boolean m_blnSetFromDSBit = false;
	private boolean m_blnClearToDSBit = false;
	private boolean m_blnDisableWEPEncryption = false;
	private String m_strFrameControlWord = "";
	private String m_strAccessPointMAC = "";
	private String m_strDestinationMAC = "";
	private String m_strSourceMAC = "";
	private String m_strSourceIP = "";
	private String m_strDestinationIP = "";
	private String m_strWritePCAPFile = "";
	private String m_strReadPCAPFile = "";
	private String m_strReadPRGAFile = "";
	private String m_strIPTimeToLive = "";
	private String m_strNullPacketSize = "";
	
	/**
	 * Set the type of packet to forge
	 * @author Paul Bromwell Jr
	 * @param udtPacketType Type of packet to forge.
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetPacketType(udtPacketTypeType udtPacketType)
	{
		m_udtPacketType = udtPacketType;
	}
	
	/**
	 * Have packetforge-ng set the FromDS bit.
	 * @author Paul Bromwell Jr
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetFromDSBit()
	{
		m_blnSetFromDSBit = true;
	}
	
	/**
	 * Have packetforge-ng clear the ToDS bit.
	 * @author Paul Bromwell Jr
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void ClearToDSBit()
	{
		m_blnClearToDSBit = true;		
	}
	
	/**
	 * Disable WEP encryption in the forged packet
	 * @author Paul Bromwell Jr
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void DisableWEPEncryption()
	{
		m_blnDisableWEPEncryption = true;
	}
	
	/**
	 * Set the frame control word in the packet
	 * @author Paul Bromwell Jr
	 * @param strFrameControlWord Frame control word
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetFrameControlWord(String strFrameControlWord)
	{
		m_strFrameControlWord = strFrameControlWord;
	}
	
	/**
	 * Set the MAC address of the access point used in the forged packet
	 * @author Paul Bromwell Jr
	 * @param strAccessPointMAC Access point MAC
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetAccessPointMAC(String strAccessPointMAC)
	{
		m_strAccessPointMAC = strAccessPointMAC;	
	}
	
	/**
	 * Set the MAC of the destination machine used in the forged packet
	 * @author Paul Bromwell Jr
	 * @param strDestinationMAC Destination MAC
	 * @since Jul 15, 2014
	 * @version 1.0 
	 */
	public void SetDestinationMAC(String strDestinationMAC)
	{
		m_strDestinationMAC = strDestinationMAC;
	}
	
	/**
	 * Set the MAC of the source machine used in the forged packet
	 * @author Paul Bromwell Jr
	 * @param strSourceMAC Source MAC
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetSourceMAC(String strSourceMAC)
	{
		m_strSourceMAC = strSourceMAC;
	}
	
	/**
	 * Set the IP of the source machine used in the forged packet
	 * @author Paul Bromwell Jr
	 * @param strSourceIP Source IP
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetSourceIP(String strSourceIP)
	{
		m_strSourceIP = strSourceIP;
	}
	
	/**
	 * Set the IP of the destination machine used in the forged packet
	 * @author Paul Bromwell Jr
	 * @param strDestinationIP Destination IP
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetDestinationIP(String strDestinationIP)
	{
		m_strDestinationIP = strDestinationIP;
	}
	
	/**
	 * Set the location of the PCAP file to write the forged packet to
	 * @author Paul Bromwell Jr
	 * @param strPCAPFile PCAP file to write the packet to
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetWritePCAPFile(String strPCAPFile)
	{
		m_strWritePCAPFile = strPCAPFile;
	}
	
	/**
	 * Set the location of the PCAP file to read the forged packet from
	 * @author Paul Bromwell Jr
	 * @param strPCAPFile PCAP file to read the packet from
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetReadPCAPFile(String strPCAPFile)
	{
		m_strReadPCAPFile = strPCAPFile;
	}

	/**
	 * Set the location of the PRGA file to read the forged packet from
	 * @author Paul Bromwell Jr
	 * @param strPRGAFile PRGA file to read the packet from
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetReadPRGAFile(String strPRGAFile)
	{
		m_strReadPRGAFile = strPRGAFile;
	}

	/**
	 * Set the time to live of the IP packet
	 * @author Paul Bromwell Jr
	 * @param strTimeToLive Time to live
	 * @since Jul 15, 2014
	 * @version 1.0
	 */
	public void SetIPTimeToLive(String strTimeToLive)
	{
		m_strIPTimeToLive = strTimeToLive;
	}

	/**
	 * Set the size of the null packet
	 * @author Paul Bromwell Jr
	 * @param strSize Size of the null packet
	 * @since Jul 16, 2014
	 * @version 1.0
	 */
	public void SetNullPacketSize(String strSize)
	{
		m_strNullPacketSize = strSize;
	}
	
	/**
	 * Launches a packetforge-ng process with the specified settings
	 * @author Paul Bromwell Jr
	 * @since Jul 16, 2014
	 * @version 1.0
	 */
	public void ForgePacket()
	{
		try
		{
			String astrCommand[] = new String[] { "packetforge-ng" };
			
			astrCommand = CAircrackUtilities.AddArgumentToCommand(TranslatePacketTypeToNumber(m_udtPacketType), "", astrCommand);
			
			if (m_blnSetFromDSBit)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("j", "", astrCommand);
			
			if (m_blnClearToDSBit)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("o", "", astrCommand);
			
			if (m_blnDisableWEPEncryption)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("e", "", astrCommand);
			
			if (m_strFrameControlWord.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("p", m_strFrameControlWord, astrCommand);
			
			if (m_strAccessPointMAC.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("a", m_strAccessPointMAC, astrCommand);

			if (m_strDestinationMAC.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("c", m_strDestinationMAC, astrCommand);
			
			if (m_strSourceMAC.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("h", m_strSourceMAC, astrCommand);

			if (m_strSourceIP.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("l", m_strSourceIP, astrCommand);

			if (m_strDestinationIP.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("k", m_strDestinationIP, astrCommand);
			
			if (m_strWritePCAPFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("w", m_strWritePCAPFile, astrCommand);
			
			if (m_strReadPCAPFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_strReadPCAPFile, astrCommand);
			
			if (m_strReadPRGAFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("y", m_strReadPRGAFile, astrCommand);
			
			if (m_strIPTimeToLive.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("t", m_strIPTimeToLive, astrCommand);
			
			if (m_strNullPacketSize.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("s", m_strNullPacketSize, astrCommand);
			
			RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Translates the packet type enumeration to a packetforge-ng digestible number.
	 * @author Paul Bromwell Jr
	 * @param udtPacketType Packet type enumeration
	 * @return Number that packetforge-ng will understand to select the type of packet
	 * @since Jul 16, 2014
	 * @version 1.0
	 */
	private String TranslatePacketTypeToNumber(udtPacketTypeType udtPacketType)
	{
		String strPacketType = "";
		try
		{
			switch (udtPacketType)
			{
				case ARP:
					strPacketType = "0";
					break;
				case UDP:
					strPacketType = "1";
					break;
				case ICMP:
					strPacketType = "2";
					break;
				case Null:
					strPacketType = "3";
					break;
				case Custom:
					strPacketType = "9";
					break;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}		
		return strPacketType;
	}
}
