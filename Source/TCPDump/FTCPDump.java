// --------------------------------------------------------------------------------
// Name: FTCPDump
// Abstract: Graphical interface for the TCP dump program
// --------------------------------------------------------------------------------

// Includes
import javax.swing.*;
import java.awt.event.*;

public class FTCPDump extends CAircrackWindow
{
	protected final static long serialVersionUID = 1L;
	
	private CTextBox m_txtExpression = null;
	
	private JCheckBox m_chkPrintPacketsInASCII = null; // -A
	private JCheckBox m_chkPrintInASDOTNotation = null; // -b
	private JCheckBox m_chkPrintLinkLevelHeader = null; // -e
	private JCheckBox m_chkNumericOutputForeignAddresses = null; // -f
	private JCheckBox m_chkDetect80211DraftMeshHeaders = null; // -H
	private JCheckBox m_chkInterfaceToMonitorMode = null; // -I
	private JCheckBox m_chkDontVerifyCheckSums = null; // -K
	private JCheckBox m_chkNumericOutputLocalAddresses = null; // -n
	private JCheckBox m_chkDontShowFullyQualifiedNames = null; // -N
	private JCheckBox m_chkPreventPremiscuousMode = null; // -p
	private JCheckBox m_chkPrintAbsoluteTCPSequenceNumbers = null; // -S
	private JCheckBox m_chkPrintUndecodedNFSHandles = null; // -u
	private JCheckBox m_chkDontSaveFilesAsRoot = null; // -Z
	
	private JCheckBox m_chkOperatingSystemCaptureBufferSize = null; // -B
	private CTextBox m_txtOperatingSystemCaptureBufferSize = null;
	private JCheckBox m_chkExitAfterReceivingPackets = null; // -c
	private CTextBox m_txtExitAfterReceivingPackets = null;
	private JCheckBox m_chkMaximumFileSize = null; // -C
	private CTextBox m_txtMaximumFileSize = null;
	private JCheckBox m_chkDumpPacketMatchingCode = null;
	private CComboBox m_cboDumpPacketMatchingCode = null; //-d (human readable format), -dd (C Program Fragment), -ddd (decimal numbers)
	private JCheckBox m_chkNewDumpFileEverySecond = null; // -G
	private CTextBox m_txtNewDumpFileEverySecond = null;
	private JCheckBox m_chkInterface = null; // -i
	private CComboBox m_cboInterface = null; // -D to get interfaces
	private JCheckBox m_chkVerbosityLevel = null;
	private CComboBox m_cboVerbosityLevel = null; // -v, -vv, -vvv, -q
	private JCheckBox m_chkReadPacketsFromFile = null; // -r
	private CTextBox m_txtReadPacketsFromFile = null;
	private JButton m_btnReadPacketsFromFile = null;
	private JCheckBox m_chkTimestampOptions = null;
	private CComboBox m_cboTimestampOptions = null; //-t (none), -tt (unformatted), -ttt (between lines), -tttt (default), -ttttt (between now and first)   
	private JCheckBox m_chkWritePacketsToFile = null; //-w
	private CTextBox m_txtWritePacketsToFile = null;
	private JButton m_btnWritePacketsToFile = null;
	private JCheckBox m_chkPrintPacketContents = null; 
	private CComboBox m_cboPrintPacketContentsHexOrASCII = null; // -x or -X 
	private JCheckBox m_chkPrintPacketContentsIncludeLinkLevelHeader = null; // -xx or -XX

	private JButton m_btnStart = null;
	
	// --------------------------------------------------------------------------------
	// Name: FTCPDump
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FTCPDump()
	{
		super("TCP Dump", 640, 480, false, false, "TCPDump");
		try
		{
			AddControls( );
			AddParameterAssignments( );
			AddDumpPacketMatchingCodeOptions( );
			AddInterfaces( );
			AddVerbosityOptions( );
			AddTimestampOptions( );
			AddPacketContentsOptions( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the options to the form
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
			CUtilities.AddLabel(m_cntControlContainer, "Expression:", 51, 5);
			m_txtExpression = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 50, 90);
			
			m_chkPrintPacketsInASCII = CUtilities.AddCheckBox(m_cntControlContainer, null, "Print Packets In ASCII", 70, 5);
			m_chkPrintInASDOTNotation = CUtilities.AddCheckBox(m_cntControlContainer, null, "Print In ASDOT Notation", 70, 325);
			m_chkPrintLinkLevelHeader = CUtilities.AddCheckBox(m_cntControlContainer, null, "Print Link-Level Header", 90, 5);
			m_chkNumericOutputForeignAddresses = CUtilities.AddCheckBox(m_cntControlContainer, null, "Numeric Output Foreign Addresses", 90, 325);
			m_chkDetect80211DraftMeshHeaders = CUtilities.AddCheckBox(m_cntControlContainer, null, "Detect 802.11 Draft Mesh Headers", 110, 5);
			m_chkInterfaceToMonitorMode = CUtilities.AddCheckBox(m_cntControlContainer, null, "Interface To Monitor Mode", 110, 325);
			m_chkDontVerifyCheckSums = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Verify Checksums", 130, 5);
			m_chkNumericOutputLocalAddresses = CUtilities.AddCheckBox(m_cntControlContainer, null, "Numeric Output Local Addresses", 130, 325);
			m_chkDontShowFullyQualifiedNames = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Show Fully Qualified Names", 150, 5);
			m_chkPreventPremiscuousMode = CUtilities.AddCheckBox(m_cntControlContainer, null, "Prevent Premiscuous Mode", 150, 325);
			m_chkPrintUndecodedNFSHandles = CUtilities.AddCheckBox(m_cntControlContainer, null, "Print Undecoded NFS Handles", 170, 5);
			m_chkDontSaveFilesAsRoot = CUtilities.AddCheckBox(m_cntControlContainer, null, "Don't Save Files As Root", 170, 325);
			m_chkPrintAbsoluteTCPSequenceNumbers = CUtilities.AddCheckBox(m_cntControlContainer, null, "Print Absolute TCP Sequence Numbers", 190, 5);
			
			JPanel pnlMoreOptions = CUtilities.AddScrollPane(m_cntControlContainer, null, 280, 630, 200, 5, 215);
			m_chkOperatingSystemCaptureBufferSize = CUtilities.AddCheckBox(pnlMoreOptions, null, "Operating System Capture Buffer Size:", 5, 5);
			m_txtOperatingSystemCaptureBufferSize = CUtilities.AddTextBox(pnlMoreOptions, null, "", 8, 10, 7, 305);
			CUtilities.AddLabel(pnlMoreOptions, "KiB", 8, 400);
			m_chkExitAfterReceivingPackets = CUtilities.AddCheckBox(pnlMoreOptions, null, "Exit After Receiving # of Packets:", 30, 5);
			m_txtExitAfterReceivingPackets = CUtilities.AddTextBox(pnlMoreOptions, null, "", 8, 10, 32, 265);
			m_chkMaximumFileSize = CUtilities.AddCheckBox(pnlMoreOptions, null, "Maximum File Size:", 55, 5);
			m_txtMaximumFileSize = CUtilities.AddTextBox(pnlMoreOptions, null, "", 8, 10, 57, 165);
			CUtilities.AddLabel(pnlMoreOptions, "MB", 57, 260);
			m_chkDumpPacketMatchingCode = CUtilities.AddCheckBox(pnlMoreOptions, null, "Dump Packet Matching Code:", 80, 5);
			m_cboDumpPacketMatchingCode = CUtilities.AddComboBox(pnlMoreOptions, null, 82, 240, 18, 200);
			m_chkNewDumpFileEverySecond = CUtilities.AddCheckBox(pnlMoreOptions, null, "New Dump File:", 105, 5);
			m_txtNewDumpFileEverySecond = CUtilities.AddTextBox(pnlMoreOptions, null, "", 8, 10, 107, 140);
			CUtilities.AddLabel(pnlMoreOptions, "seconds", 107, 235);
			m_chkInterface = CUtilities.AddCheckBox(pnlMoreOptions, null, "Interface:", 130, 5);
			m_cboInterface = CUtilities.AddComboBox(pnlMoreOptions, null, 132, 100, 18, 100);
			m_chkVerbosityLevel = CUtilities.AddCheckBox(pnlMoreOptions, null, "Verbosity Level:", 155, 5);
			m_cboVerbosityLevel = CUtilities.AddComboBox(pnlMoreOptions, null, 157, 145, 18, 100);
			m_chkReadPacketsFromFile = CUtilities.AddCheckBox(pnlMoreOptions, null, "Read Packets From File:", 180, 5);
			m_txtReadPacketsFromFile = CUtilities.AddTextBox(pnlMoreOptions, null, "", 15, 100, 182, 200);
			m_btnReadPacketsFromFile = CUtilities.AddButton(pnlMoreOptions, this, "...", 182, 375, 18, 50);
			m_chkTimestampOptions = CUtilities.AddCheckBox(pnlMoreOptions, null, "Timestamp Options:", 205, 5);
			m_cboTimestampOptions = CUtilities.AddComboBox(pnlMoreOptions, null, 207, 175, 18, 200);   
			m_chkWritePacketsToFile = CUtilities.AddCheckBox(pnlMoreOptions, null, "Write Packets To File:", 230, 5);
			m_txtWritePacketsToFile = CUtilities.AddTextBox(pnlMoreOptions, null, "", 15, 100, 232, 185);
			m_btnWritePacketsToFile = CUtilities.AddButton(pnlMoreOptions, this, "...", 232, 360, 18, 50);
			m_chkPrintPacketContents = CUtilities.AddCheckBox(pnlMoreOptions, null, "Print Packet Contents:", 255, 5); 
			m_cboPrintPacketContentsHexOrASCII = CUtilities.AddComboBox(pnlMoreOptions, null, 257, 190, 18, 100); 
			m_chkPrintPacketContentsIncludeLinkLevelHeader = CUtilities.AddCheckBox(pnlMoreOptions, null, "Include Link-Level Header", 255, 300);
			
			m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 420, 270, 18, 100);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddParameterAssignments
	// Abstract: Adds the parameter assignments from the form options
	// --------------------------------------------------------------------------------
	private void AddParameterAssignments( )
	{
		try
		{
			
			m_lstParameters.add(new CProfileParameter("Expression", m_txtExpression));
			m_lstParameters.add(new CProfileParameter("PrintPacketsInASCII", m_chkPrintPacketsInASCII));
			m_lstParameters.add(new CProfileParameter("PrintInASDOTNotation", m_chkPrintInASDOTNotation));
			m_lstParameters.add(new CProfileParameter("PrintLinkLevelHeader", m_chkPrintLinkLevelHeader));
			m_lstParameters.add(new CProfileParameter("NumericOutputForeignAddresses", m_chkNumericOutputForeignAddresses));
			m_lstParameters.add(new CProfileParameter("Detect802211DraftMeshHeaders", m_chkDetect80211DraftMeshHeaders));
			m_lstParameters.add(new CProfileParameter("InterfaceToMonitorMode", m_chkInterfaceToMonitorMode));
			m_lstParameters.add(new CProfileParameter("DontVerifyCheckSums", m_chkDontVerifyCheckSums));
			m_lstParameters.add(new CProfileParameter("NumericOutputLocalAddresses", m_chkNumericOutputLocalAddresses));
			
			m_lstParameters.add(new CProfileParameter("DontShowFullyQualifiedNames", m_chkDontShowFullyQualifiedNames));
			m_lstParameters.add(new CProfileParameter("PreventPremiscuousMode", m_chkPreventPremiscuousMode));
			m_lstParameters.add(new CProfileParameter("PrintUndecodedNFSHandles", m_chkPrintUndecodedNFSHandles));
			m_lstParameters.add(new CProfileParameter("DontSaveFilesAsRoot", m_chkDontSaveFilesAsRoot));
			m_lstParameters.add(new CProfileParameter("PrintAbsoluteTCPSequenceNumbers", m_chkPrintAbsoluteTCPSequenceNumbers));
			m_lstParameters.add(new CProfileParameter("OperatingSystemCaptureBufferSize", m_chkOperatingSystemCaptureBufferSize, m_txtOperatingSystemCaptureBufferSize));
			m_lstParameters.add(new CProfileParameter("ExitAfterReceivingPackets", m_chkExitAfterReceivingPackets, m_txtExitAfterReceivingPackets));
			m_lstParameters.add(new CProfileParameter("MaximumFileSize", m_chkMaximumFileSize, m_txtMaximumFileSize));
			m_lstParameters.add(new CProfileParameter("DumpPacketMatchingCode", m_chkDumpPacketMatchingCode, m_cboDumpPacketMatchingCode));
			m_lstParameters.add(new CProfileParameter("NewDumpFileEverySecond", m_chkNewDumpFileEverySecond, m_txtNewDumpFileEverySecond));
			m_lstParameters.add(new CProfileParameter("Interface", m_chkInterface, m_cboInterface));
			m_lstParameters.add(new CProfileParameter("VerbosityLevel", m_chkVerbosityLevel, m_cboVerbosityLevel));
			m_lstParameters.add(new CProfileParameter("ReadPacketsFromFile", m_chkReadPacketsFromFile, m_txtReadPacketsFromFile));
			m_lstParameters.add(new CProfileParameter("TimestampOptions", m_chkTimestampOptions, m_cboTimestampOptions));
			m_lstParameters.add(new CProfileParameter("WritePacketsToFile", m_chkWritePacketsToFile, m_txtWritePacketsToFile));
			m_lstParameters.add(new CProfileParameter("PrintPacketContents", m_chkPrintPacketContents, m_cboPrintPacketContentsHexOrASCII));
			m_lstParameters.add(new CProfileParameter("PrintPacketContentsLinkLevelHeader", m_chkPrintPacketContentsIncludeLinkLevelHeader));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddDumpPacketMatchingCodeOptions
	// Abstract: Adds the dropdown options to the dropdown
	// --------------------------------------------------------------------------------
	private void AddDumpPacketMatchingCodeOptions( )
	{
		try
		{
			m_cboDumpPacketMatchingCode.SetSorted(false);
			
			CTCPDumpProcess.udtPacketMatchingCodeType audtPacketCodes[] = CTCPDumpProcess.udtPacketMatchingCodeType.values();
			for (CTCPDumpProcess.udtPacketMatchingCodeType udtPacketCode : audtPacketCodes)
			{
				String strName = udtPacketCode.toString();
				strName = strName.toLowerCase();
				strName = strName.replaceAll("_",  " ");
				strName = CAircrackUtilities.ToProperCase(strName);
				m_cboDumpPacketMatchingCode.AddItemToList(strName, 0);
			}
			m_cboDumpPacketMatchingCode.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddInterfaces
	// Abstract: Adds the dropdown options to the dropdown
	// --------------------------------------------------------------------------------
	private void AddInterfaces( )
	{
		try
		{
			m_cboInterface.SetSorted( false );
			
			CTCPDumpProcess clsTCPDump = new CTCPDumpProcess();
			String[] astrInterfaces = clsTCPDump.GetAvailableInterfaces();
			
			for (String strInterface : astrInterfaces)
				m_cboInterface.AddItemToList(strInterface, 0);
			
			m_cboInterface.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddVerbosityOptions
	// Abstract: Adds the dropdown options to the dropdown
	// --------------------------------------------------------------------------------
	private void AddVerbosityOptions( )
	{
		try
		{
			m_cboVerbosityLevel.SetSorted(false);
			
			CTCPDumpProcess.udtVerbosityType audtVerbosityLevels[] = CTCPDumpProcess.udtVerbosityType.values();
			for (CTCPDumpProcess.udtVerbosityType udtVerbosityLevel : audtVerbosityLevels)
			{
				String strName = udtVerbosityLevel.toString();
				strName = strName.toLowerCase();
				strName = strName.replaceAll("_",  " ");
				strName = CAircrackUtilities.ToProperCase(strName);
				m_cboVerbosityLevel.AddItemToList(strName, 0);
			}
			m_cboVerbosityLevel.SetSelectedIndex( 0 );		
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddTimestampOptions
	// Abstract: Adds the dropdown options to the dropdown
	// --------------------------------------------------------------------------------
	private void AddTimestampOptions( )
	{
		try
		{
			m_cboTimestampOptions.SetSorted(false);
			
			CTCPDumpProcess.udtTimestampOptionsType audtTimestampOptions[] = CTCPDumpProcess.udtTimestampOptionsType.values();
			for (CTCPDumpProcess.udtTimestampOptionsType udtTimestampOption : audtTimestampOptions)
			{
				String strName = udtTimestampOption.toString();
				strName = strName.toLowerCase();
				strName = strName.replaceAll("_",  " ");
				strName = CAircrackUtilities.ToProperCase(strName);
				m_cboTimestampOptions.AddItemToList(strName, 0);
			}
			m_cboTimestampOptions.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: AddPacketContentsOptions
	// Abstract: Adds the dropdown options to the dropdown
	// --------------------------------------------------------------------------------
	private void AddPacketContentsOptions( )
	{
		try
		{
			m_cboPrintPacketContentsHexOrASCII.SetSorted(false);
			
			CTCPDumpProcess.udtPrintPacketContentsType audtPacketContentTypes[] = CTCPDumpProcess.udtPrintPacketContentsType.values();
			for (CTCPDumpProcess.udtPrintPacketContentsType udtPacketContentType : audtPacketContentTypes)
			{
				String strName = udtPacketContentType.toString();
				m_cboPrintPacketContentsHexOrASCII.AddItemToList(strName, 0);
			}
			m_cboPrintPacketContentsHexOrASCII.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Calls the appropriate function depending on which item was clicked
	// --------------------------------------------------------------------------------
	public void actionPerformed( ActionEvent aeSource )
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnReadPacketsFromFile ) CAircrackUtilities.DisplayFileChooser(m_txtReadPacketsFromFile, this, m_chkReadPacketsFromFile);
				else if ( aeSource.getSource( ) == m_btnWritePacketsToFile ) CAircrackUtilities.DisplayFileChooser(m_txtWritePacketsToFile, this, m_chkWritePacketsToFile);
				else if ( aeSource.getSource( ) == m_btnStart ) btnStart_Click( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Start button Click event
	// --------------------------------------------------------------------------------
	public void btnStart_Click( )
	{
		try
		{
			CTCPDumpProcess clsTCPDump = new CTCPDumpProcess();
			
			clsTCPDump.SetExpression(m_txtExpression.getText().trim());
			
			if (m_chkPrintPacketsInASCII.isSelected())							clsTCPDump.PrintPacketsInASCII();
			if (m_chkPrintInASDOTNotation.isSelected())							clsTCPDump.PrintInASDOTNotation();
			if (m_chkPrintLinkLevelHeader.isSelected())							clsTCPDump.PrintLinkLevelHeader();
			if (m_chkNumericOutputForeignAddresses.isSelected())				clsTCPDump.NumericOutputForeignAddresses();
			if (m_chkDetect80211DraftMeshHeaders.isSelected())					clsTCPDump.Detect80211DraftMeshHeaders();
			if (m_chkInterfaceToMonitorMode.isSelected())						clsTCPDump.InterfaceToMonitorMode();
			if (m_chkDontVerifyCheckSums.isSelected())							clsTCPDump.DontVerifyChecksums();
			if (m_chkNumericOutputLocalAddresses.isSelected())					clsTCPDump.NumericOutputLocalAddresses();
			if (m_chkDontShowFullyQualifiedNames.isSelected())					clsTCPDump.DontShowFullyQualifiedNames();
			if (m_chkPreventPremiscuousMode.isSelected())						clsTCPDump.PreventPremiscuousMode();
			if (m_chkPrintAbsoluteTCPSequenceNumbers.isSelected())				clsTCPDump.PrintAbsoluteTCPSequenceNumbers();
			if (m_chkPrintUndecodedNFSHandles.isSelected())						clsTCPDump.PrintUndecodedNFSHandles();
			if (m_chkDontSaveFilesAsRoot.isSelected())							clsTCPDump.DontSaveFilesAsRoot();
			if (m_chkOperatingSystemCaptureBufferSize.isSelected())				clsTCPDump.SetOperatingSystemCaptureBufferSizeKB(Integer.parseInt(m_txtOperatingSystemCaptureBufferSize.getText( ).trim( )));
			if (m_chkExitAfterReceivingPackets.isSelected())					clsTCPDump.SetExitAfterReceivingNumberOfPackets(Integer.parseInt(m_txtExitAfterReceivingPackets.getText( ).trim( )));
			if (m_chkMaximumFileSize.isSelected())								clsTCPDump.SetMaximumFileSizeMB(Integer.parseInt(m_txtMaximumFileSize.getText( ).trim( )));
			if (m_chkNewDumpFileEverySecond.isSelected())						clsTCPDump.SetNewDumpFileSeconds(Integer.parseInt(m_txtNewDumpFileEverySecond.getText( ).trim( )));
			if (m_chkInterface.isSelected())									clsTCPDump.SetInterface(m_cboInterface.GetSelectedItemName( ));
			if (m_chkReadPacketsFromFile.isSelected())							clsTCPDump.SetReadPacketsFile(m_txtReadPacketsFromFile.getText( ).trim( ));
			if (m_chkWritePacketsToFile.isSelected())							clsTCPDump.SetWritePacketsToFile(m_txtWritePacketsToFile.getText( ).trim( ));
			if (m_chkDumpPacketMatchingCode.isSelected())						clsTCPDump.SetDumpPacketMatchingCode(CTCPDumpProcess.udtPacketMatchingCodeType.values()[m_cboDumpPacketMatchingCode.GetSelectedIndex()]);
			if (m_chkVerbosityLevel.isSelected())								clsTCPDump.SetVerbosity(CTCPDumpProcess.udtVerbosityType.values()[m_cboVerbosityLevel.GetSelectedIndex()]);
			if (m_chkTimestampOptions.isSelected())								clsTCPDump.SetTimestampOptions(CTCPDumpProcess.udtTimestampOptionsType.values()[m_cboTimestampOptions.GetSelectedIndex()]);
			if (m_chkPrintPacketContents.isSelected())							clsTCPDump.SetPrintPacketContentsType(CTCPDumpProcess.udtPrintPacketContentsType.values()[m_cboPrintPacketContentsHexOrASCII.GetSelectedIndex()]);
			if (m_chkPrintPacketContentsIncludeLinkLevelHeader.isSelected())	clsTCPDump.IncludeLinkLevelHeader();

			clsTCPDump.Start();
			
			DProgramOutput dlgOutput = new DProgramOutput("TCP Dump Results", clsTCPDump, false);
			dlgOutput.setVisible( true );
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
}
