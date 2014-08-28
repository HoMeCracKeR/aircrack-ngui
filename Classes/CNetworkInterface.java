import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class CNetworkInterface
{

	private boolean m_blnDataLoaded = false;
	private String m_strInterfaceName = "";
	private String m_strIPAddress = "";
	private String m_strSubnetMask = "";
	private String m_strMACAddress = "";
	private String m_strBroadcastAddress = "";
	private String m_strIPv6Address = "";
	private String m_strInterfaceAvailability = "";
	private boolean m_blnHasWirelessCapability = false;
	private String m_strIEEEMode = "";
	private String m_strESSID = "";
	private String m_strMode = "";
	private String m_strFrequency = "";
	private String m_strBSSID = "";
	private static HashMap<String, Integer> m_dicFrequencyToChannel = null;

	// --------------------------------------------------------------------------------
	// Name: CNetworkInterface
	// Abstract: Parameterized constructor
	// --------------------------------------------------------------------------------
	public CNetworkInterface(String strInterface)
	{
		try
		{
			m_strInterfaceName = strInterface;
			
			if (m_dicFrequencyToChannel == null)
				LoadFrequencyToChannelDictionary();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: LoadFrequencyToChannelDictionary
	// Abstract: Loads the map between frequency and channel into the class-wide hashmap
	// --------------------------------------------------------------------------------
	private void LoadFrequencyToChannelDictionary()
	{
		try
		{
			m_dicFrequencyToChannel = new HashMap<String, Integer>();
			m_dicFrequencyToChannel.put("2.412", 1);
			m_dicFrequencyToChannel.put("2.417", 2);
			m_dicFrequencyToChannel.put("2.422", 3);
			m_dicFrequencyToChannel.put("2.427", 4);
			m_dicFrequencyToChannel.put("2.432", 5);
			m_dicFrequencyToChannel.put("2.437", 6);
			m_dicFrequencyToChannel.put("2.442", 7);
			m_dicFrequencyToChannel.put("2.447", 8);
			m_dicFrequencyToChannel.put("2.452", 9);
			m_dicFrequencyToChannel.put("2.457", 10);
			m_dicFrequencyToChannel.put("2.462", 11);
			m_dicFrequencyToChannel.put("2.467", 12);
			m_dicFrequencyToChannel.put("2.472", 13);
			m_dicFrequencyToChannel.put("2.484", 14);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetInterface
	// Abstract: Gets the name of the interface
	// --------------------------------------------------------------------------------
	public String GetInterface()
	{
		return m_strInterfaceName;
	}

	// --------------------------------------------------------------------------------
	// Name: LoadInterfaceData
	// Abstract: Loads the data from IFConfig and IWConfig into the class variables
	// --------------------------------------------------------------------------------
	private void LoadInterfaceData()
	{
		try
		{
			if (m_strInterfaceName.equals("") == false)
			{
				if (CGlobals.clsLocalMachine.GetOperatingSystem() == CLocalMachine.udtOperatingSystemType.WINDOWS) {
					LoadDataFromIpConfig();
					LoadWirelessDataFromNetsh();
				}
				else {
					LoadDataFromIfConfig();
					LoadDataFromIwConfig();
				}
				m_blnDataLoaded = true;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Loads interface information from IpConfig for the specified interface.
	 * @author Paul Bromwell Jr
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	private void LoadDataFromIpConfig()
	{
		try
		{
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	/**
	 * Loads wireless interface information from Netsh for the specified interface.
	 * @author Paul Bromwell Jr
	 * @since Jul 6, 2014
	 * @version 1.0
	 */
	private void LoadWirelessDataFromNetsh()
	{
		try
		{
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: LoadDataFromIfConfig
	// Abstract: Loads the data that is persistant across hard-wire and wireless cards
	//			 (IP address, MAC address, etc.)
	// --------------------------------------------------------------------------------
	private void LoadDataFromIfConfig()
	{
		try
		{
			CIFConfigProcess clsIFConfig = new CIFConfigProcess();
			CIFConfigProcess.CIFConfigProcessOutput clsOutput = clsIFConfig.GetInterfaceInformation(m_strInterfaceName);
			if (clsOutput != null)
			{
				// Copy the output to the class
				m_strMACAddress = clsOutput.strMACAddress;
				m_strIPAddress = clsOutput.strIPAddress;
				m_strBroadcastAddress = clsOutput.strBroadcastAddress;
				m_strSubnetMask = clsOutput.strSubnetMask;
				m_strIPv6Address = clsOutput.strIPv6Address;
				m_strInterfaceAvailability = clsOutput.blnInterfaceUp ? "UP" : "DOWN";
			}
			clsIFConfig.CloseProcess();
			clsIFConfig = null;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: LoadDataFromIwConfig
	// Abstract: Loads the data for the specified interface from IwConfig
	// --------------------------------------------------------------------------------
	private void LoadDataFromIwConfig()
	{
		try
		{
			CIWConfigProcess clsIWConfig = new CIWConfigProcess();
			CIWConfigProcess.CIWConfigProcessOutput clsOutput = clsIWConfig.GetInterfaceInformation(m_strInterfaceName);
			
			if (clsOutput != null)
			{
				m_blnHasWirelessCapability = true;
				m_strIEEEMode = clsOutput.strIEEEMode;
				m_strESSID = clsOutput.strESSID;
				m_strMode = clsOutput.strMode;
				m_strFrequency = clsOutput.strFrequency;
				m_strBSSID = clsOutput.strBSSID;
			}
			else
				m_blnHasWirelessCapability = false;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetMode
	// Abstract: Sets the current mode the network card is in (Managed, Monitor, Master, etc.)
	// --------------------------------------------------------------------------------
	public void SetMode(String strMode)
	{
		try
		{
			String astrCommand[] = new String[] {"iwconfig", m_strInterfaceName, "mode", strMode};
			CProcess clsChangeWirelessMode = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsChangeWirelessMode.GetOutput( ) );
			String strIwconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIwconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIwconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIwconfigOutput);

			clsChangeWirelessMode.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetMode
	// Abstract: Gets the current mode the network card is in (Managed, Monitor, Master, etc.)
	// --------------------------------------------------------------------------------
	public String GetMode()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strMode;
	}

	// --------------------------------------------------------------------------------
	// Name: SetIPAddress
	// Abstract: Sets the interface's IP address
	// --------------------------------------------------------------------------------
	public void SetIPAddress(String strIPAddress)
	{
		try
		{
			String astrCommand[] = new String[] {"ifconfig", m_strInterfaceName, strIPAddress};
			CProcess clsChangeIP = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsChangeIP.GetOutput( ) );
			String strIfconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIfconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIfconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIfconfigOutput);
			
			clsChangeIP.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetIPAddress
	// Abstract: Gets the interface's current IP address
	// --------------------------------------------------------------------------------
	public String GetIPAddress()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strIPAddress;
	}

	// --------------------------------------------------------------------------------
	// Name: SetSubnetMask
	// Abstract: Sets the interface's subnet mask
	// --------------------------------------------------------------------------------
	public void SetSubnetMask(String strSubnetMask)
	{
		try
		{
			String astrCommand[] = new String[] {"ifconfig", m_strInterfaceName, "netmask", strSubnetMask};
			CProcess clsChangeSubnetMask = new CProcess(astrCommand, true, true, true);    			
			BufferedReader brOutput = new BufferedReader( clsChangeSubnetMask.GetOutput( ) );
			String strIfconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIfconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIfconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIfconfigOutput);

			clsChangeSubnetMask.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetSubnetMask
	// Abstract: Gets the interface's current subnet mask
	// --------------------------------------------------------------------------------
	public String GetSubnetMask()
	
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strSubnetMask;
	}

	// --------------------------------------------------------------------------------
	// Name: SetMACAddress
	// Abstract: Sets the MAC address using MACChanger (if installed) or IfConfig
	// --------------------------------------------------------------------------------
	public void SetMACAddress(String strFullAddress)
	{
		try
		{
			boolean blnMACChangerInstalled = CGlobals.clsLocalMachine.ProgramInstalled("macchanger");
			CProcess clsChangeMAC = null;
			String astrCommand[] = null;
			
			TakeDown();
			
			if (blnMACChangerInstalled)
				astrCommand = new String[] {"macchanger", "-m", strFullAddress};
			else
				astrCommand = new String[] {"ifconfig", m_strInterfaceName, "hw", "ether", strFullAddress};
			
			clsChangeMAC = new CProcess(astrCommand, true, true, true);
			clsChangeMAC.CloseProcess();
			
			BringUp();
			
			m_strMACAddress = strFullAddress;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: RandomizeMACAddress
	// Abstract: Sets the MAC address to a random MAC address using MACChanger
	//			 (if installed) or IfConfig
	// --------------------------------------------------------------------------------
	public void RandomizeMACAddress()
	{
		try
		{
			boolean blnMACChangerInstalled = CGlobals.clsLocalMachine.ProgramInstalled("macchanger");
			CProcess clsChangeMAC = null;
			String astrCommand[] = null;
			
			TakeDown();
			
			if (blnMACChangerInstalled)
			{
				astrCommand = new String[] {"macchanger", "-A"};
			}
			else
			{
				String strRandomMACAddress = "";
				for (int intOctectIndex = 1; intOctectIndex <= 6; intOctectIndex += 1)
				{
					int intRandomNumber = ((int)Math.random()) % 256;
					String strRandomNumberAsHex = Integer.toHexString(intRandomNumber).toUpperCase();
					
					if (strRandomMACAddress.equals("") == false)
						strRandomMACAddress += ":";
					strRandomMACAddress += strRandomNumberAsHex;
				}
				
				astrCommand = new String[] {"ifconfig", m_strInterfaceName, "hw", "ether", strRandomMACAddress};
				
				m_strMACAddress = strRandomMACAddress;
			}
				
			clsChangeMAC = new CProcess(astrCommand, true, true, true);
			clsChangeMAC.CloseProcess();
			
			BringUp();
			
			// If MACChanger was used, we have to reload the MAC address from IfConfig
			if (blnMACChangerInstalled)
				LoadInterfaceData();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetMACAddress
	// Abstract: Gets the MAC address of the interface 
	// --------------------------------------------------------------------------------
	public String GetMACAddress()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strMACAddress;
	}

	// --------------------------------------------------------------------------------
	// Name: SetBroadcastAddress
	// Abstract: Sets the interface's broadcast address 
	// --------------------------------------------------------------------------------
	public void SetBroadcastAddress(String strBroadcastAddress)
	{
		try
		{
			String astrCommand[] = new String[] {"ifconfig", m_strInterfaceName, "broadcast", strBroadcastAddress};
			CProcess clsChangeBroadcast = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsChangeBroadcast.GetOutput( ) );
			String strIfconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIfconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIfconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIfconfigOutput);
			
			clsChangeBroadcast.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetBroadcastAddress
	// Abstract: Gets the interface's current broadcast address
	// --------------------------------------------------------------------------------
	public String GetBroadcastAddress()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strBroadcastAddress;
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetIPv6Address
	// Abstract: Gets the IPv6 address for the interface 
	// --------------------------------------------------------------------------------
	public String GetIPv6Address()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strIPv6Address;
	}

	
	// --------------------------------------------------------------------------------
	// Name: GetInterfaceAvailability
	// Abstract: Gets the interface's current availability (up or down)
	// --------------------------------------------------------------------------------
	public String GetInterfaceAvailability()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strInterfaceAvailability;
	}

	// --------------------------------------------------------------------------------
	// Name: HasWirelessCapability
	// Abstract: Gets if the interface supports wireless capabilities
	// --------------------------------------------------------------------------------
	public boolean HasWirelessCapability()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_blnHasWirelessCapability;
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetIEEEMode
	// Abstract: Gets the interface's IEEE wireless mode (802.11bgn)
	// --------------------------------------------------------------------------------
	public String GetIEEEMode()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strIEEEMode;
	}

	// --------------------------------------------------------------------------------
	// Name: SetESSID
	// Abstract: Sets the ESSID that the interface is connected to
	// --------------------------------------------------------------------------------
	public void SetESSID(String strESSID)
	{
		try
		{
			String astrCommand[] = new String[] {"iwconfig", m_strInterfaceName, "essid", strESSID};
			CProcess clsChangeESSID = new CProcess(astrCommand, true, true, true);    			
			BufferedReader brOutput = new BufferedReader( clsChangeESSID.GetOutput( ) );
			String strIwconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIwconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIwconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIwconfigOutput);

			clsChangeESSID.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetESSID
	// Abstract: Gets the ESSID that the interface is connected to
	// --------------------------------------------------------------------------------
	public String GetESSID()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strESSID;
	}

	// --------------------------------------------------------------------------------
	// Name: GetFrequency
	// Abstract: Gets the wireless frequency that the interface is operating on
	// --------------------------------------------------------------------------------
	public String GetFrequency()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strFrequency;
	}

	// --------------------------------------------------------------------------------
	// Name: SetBSSID
	// Abstract: Sets the BSSID that the interface is connected to
	// --------------------------------------------------------------------------------
	public void SetBSSID(String strBSSID)
	{
		try
		{
			String astrCommand[] = new String[] {"iwconfig", m_strInterfaceName, "ap", strBSSID};
			CProcess clsChangeAccessPointBSSID = new CProcess(astrCommand, true, true, true);    			
			BufferedReader brOutput = new BufferedReader( clsChangeAccessPointBSSID.GetOutput( ) );
			String strIwconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIwconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIwconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIwconfigOutput);
			
			clsChangeAccessPointBSSID.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetBSSID
	// Abstract: Gets the BSSID that the interface is connected to
	// --------------------------------------------------------------------------------
	public String GetBSSID()
	{
		if (!m_blnDataLoaded) LoadInterfaceData();
		return m_strBSSID;
	}

	// --------------------------------------------------------------------------------
	// Name: SetChannel
	// Abstract: Sets the channel that the interface is listening on/connected to.
	// --------------------------------------------------------------------------------
	public void SetChannel(String strChannel)
	{
		try
		{
			String astrCommand[] = new String[] {"iwconfig", m_strInterfaceName, "channel", strChannel};
			CProcess clsChangeChannel = new CProcess(astrCommand, true, true, true);
			BufferedReader brOutput = new BufferedReader( clsChangeChannel.GetOutput( ) );
			String strIwconfigOutput = "";
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				strIwconfigOutput += strBuffer;
				strBuffer = brOutput.readLine( );
			}
			
			if ( strIwconfigOutput.equals( "" ) == false )
				JOptionPane.showMessageDialog(null, strIwconfigOutput);
			
			clsChangeChannel.CloseProcess();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: GetChannel
	// Abstract: Gets the channel that the interface is listening on/connected to.
	// --------------------------------------------------------------------------------
	public int GetChannel()
	{
		int intChannel = 0;
		try
		{
			if (m_blnDataLoaded) LoadInterfaceData();
			if (m_strFrequency.equals("") == false)
				intChannel = m_dicFrequencyToChannel.get(m_strFrequency);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return intChannel;
	}
	
	// --------------------------------------------------------------------------------
	// Name: BringUp
	// Abstract: Uses ifconfig to bring up an interface (ifconfig <interface> up)
	// --------------------------------------------------------------------------------
	public void BringUp()
	{
		try 
        {
        	String astrCommand[] = new String[] {"ifconfig", m_strInterfaceName, "up"};
        	CProcess clsIfconfig = new CProcess(astrCommand, false, true, true);
        	clsIfconfig.CloseProcess();
        	
        	m_strInterfaceAvailability = "UP";
        } 
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
	}
	
    // --------------------------------------------------------------------------------
    // Name: TakeDown
    // Abstract: Uses ifconfig to bring down an interface (ifconfig <interface> down) 
    // --------------------------------------------------------------------------------
    public void TakeDown()
    {
        try 
        {
        	String astrCommand[] = new String[] {"ifconfig", m_strInterfaceName, "down"};
        	CProcess clsIfconfig = new CProcess(astrCommand, false, true, true);
        	clsIfconfig.CloseProcess();
        	
        	m_strInterfaceAvailability = "DOWN";
        } 
        catch (Exception excError)
        {
            CUtilities.WriteLog(excError);
        }
    }
    
    // --------------------------------------------------------------------------------
  	// Name: EnableMonitorMode
  	// Abstract: Enables monitor mode on the specified interface
  	// --------------------------------------------------------------------------------
    public void EnableMonitorMode()
	{
		try 
		{
			String astrCommand[] = new String[] {"airmon-ng", "start", m_strInterfaceName};
			CProcess prcAirmonNG = new CProcess(astrCommand, false, true, true);
			prcAirmonNG.CloseProcess();
		} 
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
    
    // --------------------------------------------------------------------------------
 	// Name: DisableMonitorMode
 	// Abstract: Disables monitor mode on the specified interface
 	// --------------------------------------------------------------------------------
    public void DisableMonitorMode()
	{
		try 
		{
			String astrCommand[] = new String[] {"airmon-ng", "stop", m_strInterfaceName};
			CProcess prcAirmonNG = new CProcess(astrCommand, false, true, true);
			prcAirmonNG.CloseProcess();
		} 
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
    
}
