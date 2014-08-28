// --------------------------------------------------------------------------------
// Name: FForgePackets
// Abstract: Allows you to create new packets using data from other packets
// --------------------------------------------------------------------------------

// Includes
import java.awt.event.*;
import javax.swing.*;

public class FForgePackets extends CAircrackWindow implements ActionListener
{
	protected final static long serialVersionUID = 1L;
	
	// Controls
	private CComboBox m_cboPacketType = null;
	private JCheckBox m_chkSetFromDSBit = null;
	private JCheckBox m_chkClearToDSBit = null;
	private JCheckBox m_chkDisableWEPEncryption = null;
	
	private JCheckBox m_chkSetFrameControlWord = null;
	private CTextBox m_txtSetFrameControlWord = null;
	
	private JCheckBox m_chkSetAccessPointMACAddress = null;
	private CTextBox m_txtSetAccessPointMACAddress = null;
	private JButton m_btnSetAccessPointMACAddress = null;
	
	private JCheckBox m_chkSetDestinationMACAddress = null;
	private CTextBox m_txtSetDestinationMACAddress = null;
	private JButton m_btnSetDestinationMACAddress = null;
	
	private JCheckBox m_chkSetSourceMACAddress = null;
	private CTextBox m_txtSetSourceMACAddress = null;
	private JButton m_btnSetSourceMACAddress = null;
	
	private JCheckBox m_chkSetSourceIP = null;
	private CTextBox m_txtSetSourceIP = null;
	private JButton m_btnSetSourceIP = null;
	
	private JCheckBox m_chkSetDestinationIP = null;
	private CTextBox m_txtSetDestinationIP = null;
	private JButton m_btnSetDestinationIP = null;

	private JCheckBox m_chkWritePacketToPCAP = null;
	private CTextBox m_txtWritePacketToPCAP = null;
	private JButton m_btnWritePacketToPCAP = null;
	
	private JCheckBox m_chkReadPacketFromPCAP = null;
	private CTextBox m_txtReadPacketFromPCAP = null;
	private JButton m_btnReadPacketFromPCAP = null;
	
	private JCheckBox m_chkReadPRGAFromFile = null;
	private CTextBox m_txtReadPRGAFromFile = null;
	private JButton m_btnReadPRGAFromFile = null;
	
	private JCheckBox m_chkSetTTLInIPHeader = null;
	private CTextBox m_txtSetTTLInIPHeader = null;
	
	private JCheckBox m_chkSetNullPacketSize = null;
	private CTextBox m_txtSetNullPacketSize = null;
	
	private JButton m_btnStart = null;
	
	private String m_strPassedInAccessPointMAC = "";

	// --------------------------------------------------------------------------------
	// Name: FForgePackets
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public FForgePackets( )
	{
		super("Forge Packets", 450, 405, false, false, "ForgePackets");
		try
		{
			AddControls( );
			PopulatePacketTypes( );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Adds the controls to the window
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
			CUtilities.AddLabel(m_cntControlContainer, "Packet Type:", 51, 5);
            m_cboPacketType = CUtilities.AddComboBox(m_cntControlContainer, null, 50, 100, 18, 150);
            m_chkSetFromDSBit = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set FromDS Bit", 70, 5);
        	m_chkClearToDSBit = CUtilities.AddCheckBox(m_cntControlContainer, this, "Clear ToDS Bit", 90, 5);
        	m_chkDisableWEPEncryption = CUtilities.AddCheckBox(m_cntControlContainer, this, "Disable WEP Encryption", 110, 5);
    
        	m_chkSetFrameControlWord = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Frame Control Word:", 130, 5);
        	m_txtSetFrameControlWord = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 132, 210);
        	
        	m_chkSetAccessPointMACAddress = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Access Point MAC:", 150, 5);
        	m_txtSetAccessPointMACAddress = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 17, 152, 210);
        	m_btnSetAccessPointMACAddress = CUtilities.AddButton(m_cntControlContainer, this, "...", 152, 381, 18, 50);
        	
        	m_chkSetDestinationMACAddress = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Destination MAC:", 170, 5);
        	m_txtSetDestinationMACAddress = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 17, 172, 210);
        	m_btnSetDestinationMACAddress = CUtilities.AddButton(m_cntControlContainer, this, "...", 172, 381, 18, 50);
        	
        	m_chkSetSourceMACAddress = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Source MAC:", 190, 5);
        	m_txtSetSourceMACAddress = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 17, 192, 210);
        	m_btnSetSourceMACAddress = CUtilities.AddButton(m_cntControlContainer, this, "...", 192, 381, 18, 50);

        	m_chkSetSourceIP = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Source IP:", 210, 5);
        	m_txtSetSourceIP = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 212, 210);
        	m_btnSetSourceIP = CUtilities.AddButton(m_cntControlContainer, this, "...", 212, 381, 18, 50);

        	m_chkSetDestinationIP = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Destination IP:", 230, 5);
        	m_txtSetDestinationIP = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 232, 210);
        	m_btnSetDestinationIP = CUtilities.AddButton(m_cntControlContainer, this, "...", 232, 381, 18, 50);

        	m_chkWritePacketToPCAP = CUtilities.AddCheckBox(m_cntControlContainer, this, "Write Packet To PCAP:", 250, 5);
        	m_txtWritePacketToPCAP = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 252, 210);
        	m_btnWritePacketToPCAP = CUtilities.AddButton(m_cntControlContainer, this, "...", 252, 381, 18, 50);
        	
        	m_chkReadPacketFromPCAP = CUtilities.AddCheckBox(m_cntControlContainer, this, "Read Packets From PCAP:", 270, 5);
        	m_txtReadPacketFromPCAP = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 272, 210);
        	m_btnReadPacketFromPCAP = CUtilities.AddButton(m_cntControlContainer, this, "...", 272, 381, 18, 50);
        	
        	m_chkReadPRGAFromFile = CUtilities.AddCheckBox(m_cntControlContainer, this, "Read PRGA From File:", 290, 5);
        	m_txtReadPRGAFromFile = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 292, 210);
        	m_btnReadPRGAFromFile = CUtilities.AddButton(m_cntControlContainer, this, "...", 292, 381, 18, 50);
    
        	m_chkSetTTLInIPHeader = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set TTL In IP-Header:", 310, 5);
        	m_txtSetTTLInIPHeader = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 312, 210);
        	
        	m_chkSetNullPacketSize = CUtilities.AddCheckBox(m_cntControlContainer, this, "Set Null Packet Size:", 330, 5);
        	m_txtSetNullPacketSize = CUtilities.AddTextBox(m_cntControlContainer, null, "", 15, 100, 332, 210);
        	
        	m_btnStart = CUtilities.AddButton(m_cntControlContainer, this, "Start", 355, 175, 18, 100);
        	
            m_cboPacketType.SetSorted( false );
            m_cboPacketType.SetActionListener( this );
            m_chkSetNullPacketSize.setEnabled( false );
            m_txtSetNullPacketSize.setEditable( false );
            
            m_lstParameters.add(new CProfileParameter("PacketType", m_cboPacketType));
            m_lstParameters.add(new CProfileParameter("SetFromDSBit", m_chkSetFromDSBit));
            m_lstParameters.add(new CProfileParameter("ClearToDSBit", m_chkClearToDSBit));
            m_lstParameters.add(new CProfileParameter("DisableWEPEncryption", m_chkDisableWEPEncryption));
            m_lstParameters.add(new CProfileParameter("FrameControlWord", m_chkSetFrameControlWord, m_txtSetFrameControlWord));
            m_lstParameters.add(new CProfileParameter("AccessPointMAC", m_chkSetAccessPointMACAddress, m_txtSetAccessPointMACAddress));
            m_lstParameters.add(new CProfileParameter("DestinationMAC", m_chkSetDestinationMACAddress, m_txtSetDestinationMACAddress));
            m_lstParameters.add(new CProfileParameter("SourceMAC", m_chkSetSourceMACAddress, m_txtSetSourceMACAddress));
            m_lstParameters.add(new CProfileParameter("SourceIP", m_chkSetSourceIP, m_txtSetSourceIP));
			m_lstParameters.add(new CProfileParameter("DestinationIP", m_chkSetDestinationIP, m_txtSetDestinationIP));
			m_lstParameters.add(new CProfileParameter("WritePacketToPCAP", m_chkWritePacketToPCAP, m_txtWritePacketToPCAP));
			m_lstParameters.add(new CProfileParameter("ReadPacketFromPCAP", m_chkReadPacketFromPCAP, m_txtReadPacketFromPCAP));
			m_lstParameters.add(new CProfileParameter("ReadPRGAFromFile", m_chkReadPRGAFromFile, m_txtReadPRGAFromFile));
			m_lstParameters.add(new CProfileParameter("SetTTLInHeader", m_chkSetTTLInIPHeader, m_txtSetTTLInIPHeader));
			m_lstParameters.add(new CProfileParameter("NullPacketSize", m_chkSetNullPacketSize, m_txtSetNullPacketSize));
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: PopulatePacketTypes
	// Abstract: Populates the different types of packets you can create
	// --------------------------------------------------------------------------------
	private void PopulatePacketTypes( )
	{
		try
		{
			CPacketForgeNGProcess.udtPacketTypeType audtPacketTypes[] = CPacketForgeNGProcess.udtPacketTypeType.values();
			for (CPacketForgeNGProcess.udtPacketTypeType udtPacketType : audtPacketTypes)
				m_cboPacketType.AddItemToList(udtPacketType.toString(), 0);
			
			m_cboPacketType.SetSelectedIndex( 0 );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Button click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		super.actionPerformed( aeSource );
		try
		{
			if ( m_blnLoading == false )
			{
				if ( aeSource.getSource( ) == m_btnSetAccessPointMACAddress )				btnSetAccessPointMACAddress_Click( );
				else if ( aeSource.getSource( ) == m_btnStart )								btnStart_Click( );
				else if ( aeSource.getSource( ) == m_btnSetDestinationMACAddress )			btnSetDestinationMACAddress_Click( );
				else if ( aeSource.getSource( ) == m_btnSetSourceMACAddress )				btnSetSourceMACAddress_Click( );
				else if ( aeSource.getSource( ) == m_btnSetSourceIP )						btnSetSourceIP_Click( );
				else if ( aeSource.getSource( ) == m_btnSetDestinationIP )					btnSetDestinationIP_Click( );
				else if ( aeSource.getSource( ) == m_btnWritePacketToPCAP )					CAircrackUtilities.DisplayFileChooser(m_txtWritePacketToPCAP, this, m_chkWritePacketToPCAP);
				else if ( aeSource.getSource( ) == m_btnReadPacketFromPCAP )				CAircrackUtilities.DisplayFileChooser(m_txtReadPacketFromPCAP, this, m_chkReadPacketFromPCAP);
				else if ( aeSource.getSource( ) == m_btnReadPRGAFromFile )					CAircrackUtilities.DisplayFileChooser(m_txtReadPRGAFromFile, this, m_chkReadPRGAFromFile);
				else if ( aeSource.getSource( ) == m_cboPacketType.GetJavaComboBox( ) )		cboPacketType_SelectedIndexChanged( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnSetAccessPointMACAddress_Click
	// Abstract: Displays a dialog to select a network access point MAC address
	// --------------------------------------------------------------------------------
	private void btnSetAccessPointMACAddress_Click( )
	{
		try
		{
			DSelectNetwork dlgSelectNetwork = new DSelectNetwork( );
	       	dlgSelectNetwork.SetReturnType(DSelectNetwork.udtSelectNetworkReturnType.RETURN_TYPE_NETWORK_BSSID);
	       	dlgSelectNetwork.setVisible( true );
	       	String strSelectedNetwork = dlgSelectNetwork.GetSelectedNetwork( );
	       	if ( strSelectedNetwork.equals( "" ) == false )
	       	{
	       		m_txtSetAccessPointMACAddress.setText( dlgSelectNetwork.GetSelectedNetwork( ) );
	       		m_chkSetAccessPointMACAddress.setSelected( true );
	       	}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnStart_Click
	// Abstract: Start button click event. Starts the packet forging process.
	// --------------------------------------------------------------------------------
	private void btnStart_Click( )
	{
		try
		{
			CPacketForgeNGProcess clsPacketForge = new CPacketForgeNGProcess();
			CPacketForgeNGProcess.udtPacketTypeType audtPacketTypes[] = CPacketForgeNGProcess.udtPacketTypeType.values();
			
			for (CPacketForgeNGProcess.udtPacketTypeType udtPacketType : audtPacketTypes)
				if (udtPacketType.toString().equals(m_cboPacketType.GetSelectedItemName()))
					clsPacketForge.SetPacketType(udtPacketType);
			
			if ( m_chkSetFromDSBit.isSelected( ) )
				clsPacketForge.SetFromDSBit();
			
			if ( m_chkClearToDSBit.isSelected( ) )
				clsPacketForge.ClearToDSBit();
			
			if ( m_chkDisableWEPEncryption.isSelected( ) )
				clsPacketForge.DisableWEPEncryption();
			
			if ( m_chkSetFrameControlWord.isSelected( ) )
				clsPacketForge.SetFrameControlWord(m_txtSetFrameControlWord.getText().trim());
			
			if ( m_chkSetAccessPointMACAddress.isSelected( ) )
				clsPacketForge.SetAccessPointMAC(m_txtSetAccessPointMACAddress.getText().trim());

			if ( m_chkSetDestinationMACAddress.isSelected( ) )
				clsPacketForge.SetDestinationMAC(m_txtSetDestinationMACAddress.getText().trim());
			
			if ( m_chkSetSourceMACAddress.isSelected( ) )
				clsPacketForge.SetSourceMAC(m_txtSetSourceMACAddress.getText().trim());

			if ( m_chkSetSourceIP.isSelected( ) )
				clsPacketForge.SetSourceIP(m_txtSetSourceIP.getText().trim());

			if ( m_chkSetDestinationIP.isSelected( ) )
				clsPacketForge.SetDestinationIP(m_txtSetDestinationIP.getText().trim());
			
			if ( m_chkWritePacketToPCAP.isSelected( ) )
				clsPacketForge.SetWritePCAPFile(m_txtWritePacketToPCAP.getText().trim());
			
			if ( m_chkReadPacketFromPCAP.isSelected( ) )
				clsPacketForge.SetReadPCAPFile(m_txtReadPacketFromPCAP.getText().trim());
			
			if ( m_chkReadPRGAFromFile.isSelected( ) )
				clsPacketForge.SetReadPRGAFile(m_txtReadPRGAFromFile.getText().trim());
			
			if ( m_chkSetTTLInIPHeader.isSelected( ) )
				clsPacketForge.SetIPTimeToLive(m_txtSetTTLInIPHeader.getText().trim());
			
			if ( m_chkSetNullPacketSize.isSelected( ) )
				clsPacketForge.SetNullPacketSize(m_txtSetNullPacketSize.getText().trim());
			
			clsPacketForge.ForgePacket();
			
			DProgramOutput dlgForgePackets = new DProgramOutput("Forge Packets - Output", clsPacketForge, true);
			dlgForgePackets.setVisible( true );
		
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSetDestinationMACAddress_Click
	// Abstract: Shows the dialog for setting the destination MAC
	// --------------------------------------------------------------------------------
	private void btnSetDestinationMACAddress_Click( )
	{
		try
		{
			DSelectMACAddress dlgSelectMAC = new DSelectMACAddress( );
			dlgSelectMAC.setVisible( true );
			String strSelectedMAC = dlgSelectMAC.GetSelectedMACAddress( );
			if ( strSelectedMAC.equals( "" ) == false )
			{
				m_txtSetDestinationMACAddress.setText( strSelectedMAC );
				m_chkSetDestinationMACAddress.setSelected( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnSetSourceMACAddress_Click
	// Abstract: Shows the dialog for setting the source MAC
	// --------------------------------------------------------------------------------
	private void btnSetSourceMACAddress_Click( )
	{
		try
		{
			DSelectMACAddress dlgSelectMAC = new DSelectMACAddress( );
			dlgSelectMAC.setVisible( true );
			String strSelectedMAC = dlgSelectMAC.GetSelectedMACAddress( );
			if ( strSelectedMAC.equals( "" ) == false )
			{
				m_txtSetSourceMACAddress.setText( strSelectedMAC );
				m_chkSetSourceMACAddress.setSelected( true );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: btnSetSourceIP_Click
	// Abstract: Shows the dialog for setting the source IP
	// --------------------------------------------------------------------------------
	private void btnSetSourceIP_Click( )
	{
		try
		{
			DSelectStation dlgSelectStation = new DSelectStation( );
			dlgSelectStation.SetReturnType( DSelectStation.udtReturnType.RETURN_TYPE_IP_ADDRESS );
			dlgSelectStation.setVisible( true );
			String strSourceIP = dlgSelectStation.GetSelectedStation( );
			if ( strSourceIP.equals( "" ) == false )
			{
				m_chkSetSourceIP.setSelected( true );
				m_txtSetSourceIP.setText( strSourceIP );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Name: btnSetDestinationIP_Click
	// Abstract: Shows the dialog for setting the destination IP
	// --------------------------------------------------------------------------------
	private void btnSetDestinationIP_Click( )
	{
		try
		{
			DSelectStation dlgSelectStation = new DSelectStation( );
			dlgSelectStation.SetReturnType( DSelectStation.udtReturnType.RETURN_TYPE_IP_ADDRESS );
			dlgSelectStation.setVisible( true );
			String strDestinationIP = dlgSelectStation.GetSelectedStation( );
			if ( strDestinationIP.equals( "" ) == false )
			{
				m_chkSetDestinationIP.setSelected( true );
				m_txtSetDestinationIP.setText( strDestinationIP );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: cboPacketType_SelectedIndexChanged
	// Abstract: If null packets is selected, enable the extra option. Disable if not.
	// --------------------------------------------------------------------------------
	private void cboPacketType_SelectedIndexChanged( )
	{
		try
		{
			boolean blnNullPacketsSelected = ( m_cboPacketType.GetSelectedItemValue( ) == 3 );
			m_chkSetNullPacketSize.setEnabled( blnNullPacketsSelected );
			m_txtSetNullPacketSize.setEditable( blnNullPacketsSelected );
			m_txtSetNullPacketSize.setEnabled( blnNullPacketsSelected );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: SetAccessPointMAC
	// Abstract: Sets the access point from an outside form. Used by Discover Networks.
	// --------------------------------------------------------------------------------
	public void SetAccessPointMAC( String strNetworkBSSID )
	{
		try
		{
			m_strPassedInAccessPointMAC = strNetworkBSSID;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	
	@Override
	public void windowOpened(WindowEvent weSource)
	{
		super.windowOpened( weSource );
		if ( m_strPassedInAccessPointMAC.equals("") == false )
		{
			m_txtSetAccessPointMACAddress.setText( m_strPassedInAccessPointMAC );
			m_chkSetAccessPointMACAddress.setSelected( true );
		}
	}
	
}
