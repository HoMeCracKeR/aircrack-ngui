import java.io.BufferedReader;


public class CTCPDumpProcess extends CProcess
{
	public enum udtPacketMatchingCodeType
	{
		HUMAN_READABLE_FORMAT,
		C_PROGRAM_FRAGMENT,
		DECIMAL_NUMBERS
	}
	
	public enum udtVerbosityType
	{
		VERBOSE,
		VERBOSER,
		VERBOSEST,
		QUIET
	}
	
	public enum udtTimestampOptionsType
	{
		NONE,
		UNFORMATTED,
		BETWEEN_LINES,
		DEFAULT,
		BETWEEN_NOW_AND_FIRST
	}
	
	public enum udtPrintPacketContentsType
	{
		HEX,
		ASCII
	}
	
	private String m_strExpression = "";
	private boolean m_blnPrintPacketsInASCII = false;
	private boolean m_blnPrintInASDOTNotation = false;
	private boolean m_blnPrintLinkLevelHeader = false;
	private boolean m_blnNumericOutputForeignAddresses = false;
	private boolean m_blnDetect80211DraftMeshHeaders = false;
	private boolean m_blnInterfaceToMonitorMode = false;
	private boolean m_blnDontVerifyChecksums = false;
	private boolean m_blnNumericOutputLocalAddresses = false;
	private boolean m_blnDontShowFullyQualifiedNames = false;
	private boolean m_blnPreventPremiscuousMode = false;
	private boolean m_blnPrintUndecodedNFSHandles = false;
	private boolean m_blnDontSaveFilesAsRoot = false;
	private boolean m_blnPrintAbsoluteTCPSequenceNumbers = false;
	private int m_intOperatingSystemCaptureBufferSizeKB = -1;
	private int m_intExitAfterReceivingNumberOfPackets = -1;
	private int m_intMaximumFileSizeMB = -1;
	private udtPacketMatchingCodeType m_udtDumpPacketMatchingCode = null;
	private int m_intNewDumpFileSeconds = -1;
	private String m_strInterface = "";
	private udtVerbosityType m_udtVerbosity = null;
	private String m_strReadPacketsFromFile = "";
	private udtTimestampOptionsType m_udtTimestampOptions = null;
	private String m_strWritePacketsToFile = "";
	private udtPrintPacketContentsType m_udtPrintPacketContents = null;
	private boolean m_blnIncludeLinkLevelHeader = false;
	
	public void SetExpression(String strExpression)
	{
		m_strExpression = strExpression;
	}
	
	public void PrintPacketsInASCII()
	{
		m_blnPrintPacketsInASCII = true;
	}
	
	public void PrintInASDOTNotation()
	{
		m_blnPrintInASDOTNotation = true;
	}
	
	public void PrintLinkLevelHeader()
	{
		m_blnPrintLinkLevelHeader = true;
	}
	
	public void NumericOutputForeignAddresses()
	{
		m_blnNumericOutputForeignAddresses = true;
	}
	
	public void Detect80211DraftMeshHeaders()
	{
		m_blnDetect80211DraftMeshHeaders = true;
	}
	
	public void InterfaceToMonitorMode()
	{
		m_blnInterfaceToMonitorMode = true;
	}
	
	public void DontVerifyChecksums()
	{
		m_blnDontVerifyChecksums = true;
	}
	
	public void NumericOutputLocalAddresses()
	{
		m_blnNumericOutputLocalAddresses = true;
	}
	
	public void DontShowFullyQualifiedNames()
	{
		m_blnDontShowFullyQualifiedNames = true;
	}
	
	public void PreventPremiscuousMode()
	{
		m_blnPreventPremiscuousMode = true;
	}
	
	public void PrintUndecodedNFSHandles()
	{
		m_blnPrintUndecodedNFSHandles = true;
	}
	
	public void DontSaveFilesAsRoot()
	{
		m_blnDontSaveFilesAsRoot = true;
	}
	
	public void PrintAbsoluteTCPSequenceNumbers()
	{
		m_blnPrintAbsoluteTCPSequenceNumbers = true;
	}
	
	public void SetOperatingSystemCaptureBufferSizeKB(int intBufferSize)
	{
		m_intOperatingSystemCaptureBufferSizeKB = intBufferSize;
	}
	
	public void SetExitAfterReceivingNumberOfPackets(int intMaxPackets)
	{
		m_intExitAfterReceivingNumberOfPackets = intMaxPackets;
	}
	
	public void SetMaximumFileSizeMB(int intMaxSize)
	{
		m_intMaximumFileSizeMB = intMaxSize;
	}
	
	public void SetDumpPacketMatchingCode(udtPacketMatchingCodeType udtMatchingCode)
	{
		m_udtDumpPacketMatchingCode = udtMatchingCode;
	}
	
	public void SetNewDumpFileSeconds(int intSeconds)
	{
		m_intNewDumpFileSeconds = intSeconds;
	}
	
	public void SetInterface(String strInterface)
	{
		m_strInterface = strInterface;
	}
	
	public void SetVerbosity(udtVerbosityType udtVerbosity)
	{
		m_udtVerbosity = udtVerbosity;
	}
	
	public void SetReadPacketsFile(String strReadFile)
	{
		m_strReadPacketsFromFile = strReadFile;
	}
	
	public void SetTimestampOptions(udtTimestampOptionsType udtTimestampOptions)
	{
		m_udtTimestampOptions = udtTimestampOptions;
	}
	
	public void SetWritePacketsToFile(String strWriteFile)
	{
		m_strWritePacketsToFile = strWriteFile;
	}
	
	public void SetPrintPacketContentsType(udtPrintPacketContentsType udtPrintPacketContents)
	{
		m_udtPrintPacketContents = udtPrintPacketContents;
	}
	
	public void IncludeLinkLevelHeader()
	{
		m_blnIncludeLinkLevelHeader = true;
	}
	
	public void Start()
	{
		try
		{
			String astrCommand[] = new String[] { "tcpdump" };
			
			if (m_blnPrintPacketsInASCII)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("A", "", astrCommand);
			if (m_blnPrintInASDOTNotation)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("b", "", astrCommand);
			if (m_blnPrintLinkLevelHeader)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("e", "", astrCommand);
			if (m_blnNumericOutputForeignAddresses)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("f", "", astrCommand);
			if (m_blnDetect80211DraftMeshHeaders)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("H", "", astrCommand);
			if (m_blnInterfaceToMonitorMode)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("I", "", astrCommand);
			if (m_blnDontVerifyChecksums)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("K", "", astrCommand);
			if (m_blnNumericOutputLocalAddresses)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("n", "", astrCommand);
			if (m_blnDontShowFullyQualifiedNames)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("N", "", astrCommand);
			if (m_blnPreventPremiscuousMode)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("p", "", astrCommand);
			if (m_blnPrintAbsoluteTCPSequenceNumbers)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("S", "", astrCommand);
			if (m_blnPrintUndecodedNFSHandles)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("u", "", astrCommand);
			if (m_blnDontSaveFilesAsRoot)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("Z", "", astrCommand);
			
			if (m_intOperatingSystemCaptureBufferSizeKB > -1)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("B", String.valueOf(m_intOperatingSystemCaptureBufferSizeKB), astrCommand);
			if (m_intExitAfterReceivingNumberOfPackets > -1)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("c", String.valueOf(m_intExitAfterReceivingNumberOfPackets), astrCommand);
			if (m_intMaximumFileSizeMB > -1)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("C", String.valueOf(m_intMaximumFileSizeMB), astrCommand);
			
			if (m_udtDumpPacketMatchingCode != null)
			{
				if (m_udtDumpPacketMatchingCode == udtPacketMatchingCodeType.HUMAN_READABLE_FORMAT)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("d", "", astrCommand);
				else if (m_udtDumpPacketMatchingCode == udtPacketMatchingCodeType.C_PROGRAM_FRAGMENT)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("dd", "", astrCommand);
				else if (m_udtDumpPacketMatchingCode == udtPacketMatchingCodeType.DECIMAL_NUMBERS)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("ddd", "", astrCommand);
			}

			if (m_intNewDumpFileSeconds > -1)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("G", String.valueOf(m_intNewDumpFileSeconds), astrCommand);
			if (m_strInterface.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("i", m_strInterface, astrCommand);
			
			if (m_udtVerbosity != null)
			{
				if (m_udtVerbosity == udtVerbosityType.VERBOSE)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("v", "", astrCommand);
				else if (m_udtVerbosity == udtVerbosityType.VERBOSER)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("vv", "", astrCommand);
				else if (m_udtVerbosity == udtVerbosityType.VERBOSEST)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("vvv", "", astrCommand);
				else if (m_udtVerbosity == udtVerbosityType.QUIET)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("q", "", astrCommand);
			}
			
			if (m_strReadPacketsFromFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("r", m_strReadPacketsFromFile.trim( ), astrCommand);
			
			if (m_udtTimestampOptions != null)
			{
				if (m_udtTimestampOptions == udtTimestampOptionsType.NONE)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("t", "", astrCommand);
				else if (m_udtTimestampOptions == udtTimestampOptionsType.UNFORMATTED)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("tt", "", astrCommand);
				else if (m_udtTimestampOptions == udtTimestampOptionsType.BETWEEN_LINES)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("ttt", "", astrCommand);
				else if (m_udtTimestampOptions == udtTimestampOptionsType.DEFAULT)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("tttt", "", astrCommand);
				else if (m_udtTimestampOptions == udtTimestampOptionsType.BETWEEN_NOW_AND_FIRST)
					astrCommand = CAircrackUtilities.AddArgumentToCommand("ttttt", "", astrCommand);
			}
			
			if (m_strWritePacketsToFile.equals("") == false)
				astrCommand = CAircrackUtilities.AddArgumentToCommand("w", m_strWritePacketsToFile.trim( ), astrCommand);
			
			if (m_udtPrintPacketContents != null)
			{
				String strArgument = "";
				if (m_blnIncludeLinkLevelHeader)
					strArgument = "xx";
				else
					strArgument = "x";
				
				if (m_udtPrintPacketContents == udtPrintPacketContentsType.ASCII)
					strArgument = strArgument.toUpperCase();
				
				astrCommand = CAircrackUtilities.AddArgumentToCommand(strArgument, "", astrCommand);
			}

			astrCommand = CAircrackUtilities.AddArgumentToArray(m_strExpression.trim( ), astrCommand);
			
			RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public String[] GetAvailableInterfaces()
	{
		String astrInterfaces[] = new String[0];
		try
		{
			String astrCommand[] = new String[] {"tcpdump", "-D"};
			RunProcess(astrCommand, true, true, false);
			BufferedReader brOutput = new BufferedReader(GetOutput());
			String strBuffer = brOutput.readLine( );
			
			while ( strBuffer != null )
			{
				if ( strBuffer.contains("(") == true )
					strBuffer = strBuffer.substring(0, strBuffer.indexOf("("));
				
				strBuffer = strBuffer.substring(strBuffer.indexOf(".") + 1);
				
				astrInterfaces = CAircrackUtilities.AddArgumentToArray(strBuffer, astrInterfaces);
				
				strBuffer = brOutput.readLine( );
			}
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return astrInterfaces;
	}
}
