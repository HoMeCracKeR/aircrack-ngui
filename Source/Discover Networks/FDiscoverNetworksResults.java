// --------------------------------------------------------------------------------
// Name: FDiscoverNetworksResult
// Abstract: Shows the results of the network scan in an easy-to-use and easy-to-
//			 use table.
// --------------------------------------------------------------------------------

// Includes
import java.io.*;           // InputStreamReader, BufferedReader
import java.awt.*;          // AWT = Abstract Windows Toolkit
import java.awt.event.*;    // Events for AWT
import java.util.*;         // Timer
import java.util.regex.*;	// Matcher, Pattern
import javax.swing.*;       // Newer version of graphics

public class FDiscoverNetworksResults extends CAircrackWindow implements ComponentListener, MouseListener
{
	protected final static long serialVersionUID = 1L;
	
	// Variables for UpdateTextArea class
	private final int m_intREFRESH_RATE_IN_SECONDS = 100;
	private String m_astrNetworksWithWPS[] = new String[0];
	private String m_astrNetworkWPSVersions[] = new String[0];
	private boolean m_ablnNetworkWPSEnabled[] = new boolean[0];
	private int m_intLinesReadFromWashScan = 0;
	private boolean m_blnResultsClosing = false;
	private BufferedReader m_brWashOutput = null;
	private BufferedReader m_brAirodumpNGOutput = null;
	private final Pattern m_ptnCHANNEL_INDICATOR = Pattern.compile("CH +([-][0-9]{1,2}|[0-9]{1,2})");

	// --------------------------------------------------------------------------------
	// Name: UpdateDisplayTables
	// Abstract: Updates the display tables with the output of airodump-ng and wash
	//			 (if selected)
	// --------------------------------------------------------------------------------
    class UpdateDisplayTables extends TimerTask
    {

    	// --------------------------------------------------------------------------------
    	// Name: run
    	// Abstract: What gets executed when the timed process gets triggered
    	// --------------------------------------------------------------------------------
        @Override
        public void run( )
        {
            
            try
            {
            	
            	UpdateWashScannedNetworks( );
         
            	String strBuffer = "";
            	
            	boolean blnRXQDisplayed = false;
            	
                if ( m_blnContinueRunningSearch == true && m_clsRunningSearch != null && m_brAirodumpNGOutput != null )
                {
                    boolean blnSeenChannelIndicator = false;
                    boolean blnFirstChannelIndicator = true;
                    boolean blnAddingNetworks = true;
                 
                    UpdateCalculatedTableFields( );

                    strBuffer = CleanBufferedLine( m_brAirodumpNGOutput.readLine( ) );
                    
                    while ( strBuffer != null )
                    {
                        if ( blnSeenChannelIndicator == false )
                        {
                        	strBuffer = LoopUntilChannelIndicatorSeen( strBuffer );
                            blnSeenChannelIndicator = true;
                        }
                        else
                        {
                        	
                            if ( CurrentLineIsChannelIndicator( strBuffer ) == true && blnFirstChannelIndicator == false )
                                break;
                            else
                                blnFirstChannelIndicator = false;
                            
                            // Skip the line if it starts with a left bracket
                            if ( strBuffer.length() > 0 && strBuffer.toCharArray( )[0] == '[' )
                            {
                                strBuffer = m_brAirodumpNGOutput.readLine( );
                                continue;
                            }
                            
                            // Did we find an indication of a channel?
                            if ( CurrentLineIsChannelIndicator( strBuffer ) == true )
                            {
                            	UpdateHeaderInformationFromBuffer( strBuffer );
                            }
                            else
                            {
                            	// If we see the header for networks or for stations, switch the appropriate adding mode
                            	if ( strBuffer.contains("CIPHER") == true )
                            		blnAddingNetworks = true;
                            	else if ( strBuffer.contains("STATION") == true )
                            		blnAddingNetworks = false;
                            	
                            	if ( strBuffer.contains("CIPHER") == true && strBuffer.contains("RXQ") == true )
                            		blnRXQDisplayed = true;
                            	
                            	// Did we find a network row?
                            	if ( blnAddingNetworks == true && strBuffer.contains("CIPHER") == false && strBuffer.trim().equals("") == false )
                            	{
                            		String astrNetworkData[] = BuildNetworkDataFromBuffer( strBuffer, blnRXQDisplayed );
                            		AddOrUpdateNetworkInformation(astrNetworkData);
                            	}
                            	// Did we find a station row?
                            	else if ( blnAddingNetworks == false && strBuffer.contains("STATION") == false && strBuffer.trim().equals("") == false )
                            	{
                            		String astrStationData[] = BuildStationDataFromBuffer( strBuffer );
                            		AddOrUpdateStationInformation(astrStationData);
                            	}
                            	
                            }
                            
                            strBuffer = CleanBufferedLine( m_brAirodumpNGOutput.readLine( ) );
                        }
                        
                    }
                }
                
                if ( !m_blnResultsClosing )
                	m_timUpdateScreen.schedule( new UpdateDisplayTables(), m_intREFRESH_RATE_IN_SECONDS );
            }
            catch ( Exception excError )
            {
                CUtilities.WriteLog( excError );
            }
        }

    	// --------------------------------------------------------------------------------
    	// Name: UpdateWashScannedNetworks
    	// Abstract: Takes the data from the wash scan and puts it into three arrays:
        //			 network BSSID, WPS version, and WPS enabled.
    	// --------------------------------------------------------------------------------
        private void UpdateWashScannedNetworks( )
        {
        	try
        	{
        		String strBuffer = "";
        		if ( m_clsWashScan != null && m_brWashOutput != null )
            	{
            		if ( m_brWashOutput.ready( ) )
            		{
            			String strNetworkBSSID = "";
            			String strWPSVersion = "";
            			String strWPSLocked = "";
            			
	            		strBuffer = m_brWashOutput.readLine();
	            		
	            		// Skip unusable lines
	            		while ( strBuffer.equals("") == true || strBuffer.contains("WiFi Protected Setup Scan Tool") || strBuffer.contains("Tactical Network Solutions") )
	            			strBuffer = m_brWashOutput.readLine();
	            		m_intLinesReadFromWashScan += 1;
	            		
	            		// Read while there's data in the buffer
	            		while (strBuffer != null && m_brWashOutput.ready() == true)
	            		{
	            			// Skip the two unusable rows (header and line separator)
	            			while ( m_intLinesReadFromWashScan < 2 )
	            			{
	            				if ( m_brWashOutput.ready( ) )
	            				{
		            				strBuffer = m_brWashOutput.readLine();
		            				m_intLinesReadFromWashScan += 1;
	            				}
	            			}
	            			
	            			// Make sure the lines have been skipped and we have some data to display
	            			if ( m_intLinesReadFromWashScan >= 2 && m_brWashOutput.ready() == true )
	            			{
	            				
	            				// Pick the data out of the buffer
	            				strBuffer = m_brWashOutput.readLine();
	            				strNetworkBSSID = strBuffer.substring(0, 23).trim();
	            				strWPSVersion = strBuffer.substring(48, 66).trim();
	            				strWPSLocked = strBuffer.substring(66, 84).trim();
	            				
	            				// Append to our arrays
	            				m_astrNetworksWithWPS = CAircrackUtilities.AddArgumentToArray(strNetworkBSSID, m_astrNetworksWithWPS);
	            				m_astrNetworkWPSVersions = CAircrackUtilities.AddArgumentToArray(strWPSVersion, m_astrNetworkWPSVersions);
	            				if ( strWPSLocked.equals("Yes") )
	            					m_ablnNetworkWPSEnabled = CAircrackUtilities.AddBooleanToEndOfArray(true, m_ablnNetworkWPSEnabled);
	            				else
	            					m_ablnNetworkWPSEnabled = CAircrackUtilities.AddBooleanToEndOfArray(false, m_ablnNetworkWPSEnabled);
	            			}
	            		}
            		}
            	}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }

    	// --------------------------------------------------------------------------------
    	// Name: UpdateCalculatedFields
    	// Abstract: Populates the station ESSIDs and the wash (WPS) data onto the tables
    	// --------------------------------------------------------------------------------
        private void UpdateCalculatedTableFields( )
        {
        	try
        	{
        		UpdateStationESSIDs( );
        		UpdateNetworkWPSStatus( );
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }

    	// --------------------------------------------------------------------------------
    	// Name: UpdateStationESSIDs
    	// Abstract: Matches the BSSIDs of the stations with the BSSIDs of the networks and
        //			 populates the station's ESSID column with the data from the network's
        //			 ESSID column.
    	// --------------------------------------------------------------------------------
        private void UpdateStationESSIDs( )
        {
        	try
        	{
        		// Station ESSID
        		if ( m_tblStationResults != null )
        		{
	        		for ( int intIndex = 0; intIndex < m_tblStationResults.GetRowCount(); intIndex += 1 )
	        		{
	        			String strStationNetworkBSSID = String.valueOf(m_tblStationResults.GetCellValue(intIndex, "BSSID"));
	        			for (int intIndex2 = 0; intIndex2 < m_tblNetworkResults.GetRowCount(); intIndex2 += 1 )
	        			{
	        				String strActualNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intIndex2, "BSSID"));
	        				if ( strStationNetworkBSSID.trim().equals(strActualNetworkBSSID.trim( ) ) )
	        				{
	        					String strActualNetworkESSID = String.valueOf(m_tblNetworkResults.GetCellValue(intIndex2, "ESSID"));
	        					m_tblStationResults.SetCellValueAt(intIndex, "ESSID", strActualNetworkESSID);
	        				}
	        			}
	        		}
        		}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }

    	// --------------------------------------------------------------------------------
    	// Name: UpdateNetworkWPSStatus
    	// Abstract: Matches the network BSSID with the BSSID in the populated array from
        //			 UpdateWashScannedNetworks and if there's an entry, populate with the
        //			 data from the other two arrays (version and enabled)
    	// --------------------------------------------------------------------------------
        private void UpdateNetworkWPSStatus( )
        {
        	try
        	{
	        	// Network WPS Information
	    		for ( int intNetworkIndex = 0; intNetworkIndex < m_tblNetworkResults.GetRowCount(); intNetworkIndex += 1 )
	    		{
	    			String strTableNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intNetworkIndex, "BSSID")).toUpperCase();
	    			for (int intWPSIndex = 0; intWPSIndex < m_astrNetworksWithWPS.length; intWPSIndex += 1 )
	    			{
	    				String strWPSNetworkBSSID = String.valueOf(m_astrNetworksWithWPS[intWPSIndex]).toUpperCase();
	    				if ( strWPSNetworkBSSID.equals(strTableNetworkBSSID) == true )
	    				{
	    					String strWPSDisplayValue = "";
	    					if ( m_ablnNetworkWPSEnabled[intWPSIndex] == true )
	    						strWPSDisplayValue += "Yes";
	    					else
	    						strWPSDisplayValue += "No";
	    					strWPSDisplayValue += " (" + m_astrNetworkWPSVersions[intWPSIndex] + ")";
	    					m_tblNetworkResults.SetCellValueAt(intNetworkIndex, "WPS", strWPSDisplayValue);
	    				}
	    			}
	    		}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }
        
    	// --------------------------------------------------------------------------------
    	// Name: CleanBufferedLine
    	// Abstract: Removes extra spaces and garbage characters from the buffer
    	// --------------------------------------------------------------------------------
        private String CleanBufferedLine(String strBuffer)
        {
        	String strCleanString = "";
        	try
        	{
        		// Replace all Escape key characters with empty strings
                strCleanString = strBuffer.replace(Character.toString((char)27), "");
                
                // Remove extra spaces
                strCleanString = strCleanString.trim( );
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        	return strCleanString;
        }

    	// --------------------------------------------------------------------------------
    	// Name: LoopUntilChannelIndicatorSeen
    	// Abstract: Loops through the airodump-ng buffered reader until an indication of
        //			 the channel is seen.
    	// --------------------------------------------------------------------------------
        private String LoopUntilChannelIndicatorSeen( String strCurrentBuffer )
        {
        	String strBuffer = "";
        	try
        	{
        		Matcher mchChannelPatternMatch = null;
        		boolean blnMatchFound = false;
        		strBuffer = strCurrentBuffer;
        		
                while ( blnMatchFound == false && strBuffer != null )
                {
                	mchChannelPatternMatch = m_ptnCHANNEL_INDICATOR.matcher( strBuffer );
                	blnMatchFound = mchChannelPatternMatch.find( );
                	
                	if ( !blnMatchFound )
                		strBuffer = m_brAirodumpNGOutput.readLine( );
                }
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        	return strBuffer;
        }

    	// --------------------------------------------------------------------------------
    	// Name: LoopUntilChannelIndicatorSeen
    	// Abstract: Loops through the airodump-ng buffered reader until an indication of
        //			 the channel is seen.
    	// --------------------------------------------------------------------------------
        private boolean CurrentLineIsChannelIndicator( String strBuffer )
        {
        	boolean blnLineIsChannelIndicator = false;
        	try
        	{
        		Matcher mchLineIndicatorMatch = m_ptnCHANNEL_INDICATOR.matcher( strBuffer );
        		blnLineIsChannelIndicator = mchLineIndicatorMatch.find( );
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        	return blnLineIsChannelIndicator;
        }
        
    	// --------------------------------------------------------------------------------
    	// Name: UpdateHeaderInformationFromBuffer
    	// Abstract: Extracts header information from buffer and updates labels
    	// --------------------------------------------------------------------------------
        private void UpdateHeaderInformationFromBuffer( String strBuffer )
        {
        	try
        	{
        		String strCurrentChannel = "";
        		String strTimeElapsed = "";
        		String strCurrentTime = "";
        		String strWPAHandshakeBSSID = "";
        		String strCurrentBSSID = "";
        		
        		strCurrentChannel = strBuffer.substring(0, strBuffer.indexOf("]"));
            	strCurrentChannel = strCurrentChannel.replace("CH", "").trim();
            	m_lblCurrentChannel.setText("Channel: " + strCurrentChannel);
            	
            	strTimeElapsed = strBuffer.substring(strBuffer.indexOf("Elapsed:"));
            	strTimeElapsed = strTimeElapsed.substring(0, strTimeElapsed.indexOf("]"));
            	strTimeElapsed = strTimeElapsed.replace("Elapsed:", "").trim();
            	m_lblTimeElapsed.setText("Time Elapsed: " + strTimeElapsed);
            	
            	strCurrentTime = strBuffer.substring(strBuffer.indexOf("[", strBuffer.indexOf("[") + 1) + 1);
            	strCurrentTime = strCurrentTime.trim();
            	
            	if ( strBuffer.contains("WPA handshake") == true )
            		strCurrentTime = strCurrentTime.substring(0, strCurrentTime.indexOf(']'));
            	m_lblCurrentTime.setText(strCurrentTime);
            	
            	if ( strBuffer.contains("WPA handshake") == true )
            	{
            		strWPAHandshakeBSSID = strBuffer.substring(strBuffer.indexOf("WPA handshake") + 15);
            		if ( strWPAHandshakeBSSID.contains(" ") )
            			strWPAHandshakeBSSID = strWPAHandshakeBSSID.substring(0, strWPAHandshakeBSSID.indexOf(" "));
            		strWPAHandshakeBSSID.toUpperCase();
            		
            		// Loop through our networks to try to find possible matches (only 256 networks in the world would match up, so we have good odds)
            		for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetRowCount(); intIndex += 1 )
            		{
            			strCurrentBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intIndex, "BSSID")).toUpperCase();
            			if ( strCurrentBSSID.contains(strWPAHandshakeBSSID))
            			{
            				m_tblNetworkResults.SetCellValueAt(intIndex, "HANDSHAKE", "YES");
            			}
            		}
            	}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }

    	// --------------------------------------------------------------------------------
    	// Name: BuildNetworkDataFromBuffer
    	// Abstract: Builds the network data array from the string buffer by fixed width
    	// --------------------------------------------------------------------------------
        private String[] BuildNetworkDataFromBuffer( String strBuffer, boolean blnRXQDisplayed )
        {
        	String astrNetworkData[] = new String[13];
        	try
        	{
        		if ( blnRXQDisplayed == false )
        		{
	        		astrNetworkData[0] = strBuffer.substring(0, 19).trim(); 	// BSSID
	        		astrNetworkData[1] = strBuffer.substring(19, 24).trim(); 	// PWR
	        		astrNetworkData[2] = strBuffer.substring(24, 35).trim();	// Beacons
	        		astrNetworkData[3] = strBuffer.substring(35, 42).trim();	// #Data
	        		astrNetworkData[4] = strBuffer.substring(42, 47).trim();	// #/s
	        		astrNetworkData[5] = strBuffer.substring(47, 51).trim();	// CH
	        		astrNetworkData[6] = strBuffer.substring(51, 56).trim();	// MB
	        		astrNetworkData[7] = strBuffer.substring(56, 61).trim();	// ENC
	        		astrNetworkData[8] = "";									// WPS
	        		astrNetworkData[9] = strBuffer.substring(61, 68).trim();	// CIPHER
	        		astrNetworkData[10] = strBuffer.substring(68, 73).trim();	// AUTH
	        		astrNetworkData[11] = "";									// HANDSHAKE
	        		astrNetworkData[12] = strBuffer.substring(73).trim();		// ESSID
        		}
        		else
        		{
        			astrNetworkData[0] = strBuffer.substring(0, 19).trim();		// BSSID
        			astrNetworkData[1] = strBuffer.substring(19, 23).trim(); 	// PWR
        			astrNetworkData[2] = strBuffer.substring(28, 39).trim();	// Beacons
	        		astrNetworkData[3] = strBuffer.substring(39, 46).trim();	// #Data
	        		astrNetworkData[4] = strBuffer.substring(46, 51).trim();	// #/s
	        		astrNetworkData[5] = strBuffer.substring(51, 55).trim();	// CH
	        		astrNetworkData[6] = strBuffer.substring(55, 60).trim();	// MB
	        		astrNetworkData[7] = strBuffer.substring(60, 65).trim();	// ENC
	        		astrNetworkData[8] = "";									// WPS
	        		astrNetworkData[9] = strBuffer.substring(65, 72).trim();	// CIPHER
	        		astrNetworkData[10] = strBuffer.substring(72, 77).trim();	// AUTH
	        		astrNetworkData[11] = "";									// HANSHAKE
	        		astrNetworkData[12] = strBuffer.substring(77).trim();		// ESSID
        		}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        	return astrNetworkData;
        }
        
    	// --------------------------------------------------------------------------------
    	// Name: AddOrUpdateNetworkInformation
    	// Abstract: Adds the new network data to the table if the BSSID is not in the table,
        //			 otherwise updates if it does.
    	// --------------------------------------------------------------------------------        
        private void AddOrUpdateNetworkInformation(String astrNetworkInformation[])
        {
        	try
        	{
        		int intRowIndex = m_tblNetworkResults.FindRowIndexByColumnValue("BSSID", astrNetworkInformation[0]);
        		if ( intRowIndex >= 0 )
        		{
        			// Updating
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "BSSID", astrNetworkInformation[0]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "PWR", astrNetworkInformation[1]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "Beacons", astrNetworkInformation[2]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "#Data", astrNetworkInformation[3]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "#/s", astrNetworkInformation[4]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "CH", astrNetworkInformation[5]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "MB", astrNetworkInformation[6]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "ENC", astrNetworkInformation[7]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "CIPHER", astrNetworkInformation[9]);
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "AUTH", astrNetworkInformation[10]);
        			if ( m_tblNetworkResults.GetCellValue(intRowIndex, "ESSID") == null || String.valueOf(m_tblNetworkResults.GetCellValue(intRowIndex, "ESSID")).equals("") )
        				m_tblNetworkResults.SetCellValueAt(intRowIndex, "ESSID", astrNetworkInformation[12]);
        		}
        		else
        		{
        			// Adding
        			m_tblNetworkResults.AddRow(astrNetworkInformation);
        		}
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }

    	// --------------------------------------------------------------------------------
    	// Name: BuildStationDataFromBuffer
    	// Abstract: Builds the station data array from the string buffer by fixed width
    	// --------------------------------------------------------------------------------
        private String[] BuildStationDataFromBuffer( String strBuffer )
        {
        	String astrStationData[] = new String[ 8 ];
        	try
        	{
        		astrStationData[0] = strBuffer.substring(0, 19).trim();			// BSSID
        		astrStationData[1] = "";										// ESSID (populated later)
        		astrStationData[2] = strBuffer.substring(19, 38).trim();		// STATION
        		astrStationData[3] = strBuffer.substring(38, 44).trim();		// PWR
        		astrStationData[4] = strBuffer.substring(44, 52).trim();		// Rate
        		astrStationData[5] = strBuffer.substring(52, 60).trim();		// Lost
        		if ( strBuffer.length() >= 68 )
        			astrStationData[6] = strBuffer.substring(60, 68).trim();	// Frames
        		else
        			astrStationData[6] = strBuffer.substring(60).trim();		// Frames
        		if ( strBuffer.length() >= 68 )
        			astrStationData[7] = strBuffer.substring(68);				// Probe
        		else
        			astrStationData[7] = "";									// Probe
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        	return astrStationData;
        }

    	// --------------------------------------------------------------------------------
    	// Name: AddOrUpdateStationInformation
    	// Abstract: Adds the new station data to the table if the station MAC is not in the
        //			 table, otherwise updates if it does.
    	// --------------------------------------------------------------------------------
        private void AddOrUpdateStationInformation(String astrStationInformation[])
        {
        	try
        	{
        		
        		int intRowIndex = -1;
        		intRowIndex = m_tblStationResults.FindRowIndexByColumnValue("STATION", astrStationInformation[2]);
        		
        		if ( intRowIndex >= 0 )
        		{
        			// Updating
	    			m_tblStationResults.SetCellValueAt(intRowIndex, "BSSID", astrStationInformation[0]);
	    			m_tblStationResults.SetCellValueAt(intRowIndex, "PWR", astrStationInformation[3]);
    				m_tblStationResults.SetCellValueAt(intRowIndex, "Rate", astrStationInformation[4]);
    				m_tblStationResults.SetCellValueAt(intRowIndex, "Lost", astrStationInformation[5]);
    				m_tblStationResults.SetCellValueAt(intRowIndex, "Frames", astrStationInformation[6]);
    				m_tblStationResults.SetCellValueAt(intRowIndex, "Probe", astrStationInformation[7]);
        		}
        		else
        		{
        			// Adding
        			m_tblStationResults.AddRow(astrStationInformation);
        		}        		
        	}
        	catch (Exception excError)
        	{
        		CUtilities.WriteLog(excError);
        	}
        }
        
    }

	// --------------------------------------------------------------------------------
	// Name: UpdateNetworkESSIDs
	// Abstract: Updates the network ESSIDs with an iw scan
	// --------------------------------------------------------------------------------
    class UpdateNetworkESSIDs implements Runnable
    {

    	// --------------------------------------------------------------------------------
    	// Name: UpdateNetworkESSIDs
    	// Abstract: Gets executed when the timed process is triggered
    	// --------------------------------------------------------------------------------
		@Override
		public void run()
		{
			
			try
			{
				String strScanIntensity = "";
				if (m_blnIWDevScanPassive) strScanIntensity = "passive";
				String astrCommand[] = new String[] {"RequiredScripts/GetIWDevScanResults.sh", m_strIWDevInterface, strScanIntensity};
				CProcess clsIWDevScan = new CProcess(astrCommand, true, true, true); 
        		BufferedReader brOutput = new BufferedReader( clsIWDevScan.GetOutput( ) );
        		String strBuffer = brOutput.readLine( );
        		String strNetworkMACAddress = "";
        		String strNetworkESSID = "";
        		String strTableNetworkMACAddress = "";
        		
        		while ( strBuffer != null )
        		{
        			
        			if ( strBuffer.contains("BSS ") && strNetworkMACAddress.equals("") == false )
        			{
        				for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetRowCount(); intIndex += 1 )
        				{
        					strTableNetworkMACAddress = String.valueOf(m_tblNetworkResults.GetCellValue(intIndex, "BSSID"));
        					if ( strTableNetworkMACAddress.toUpperCase().equals(strNetworkMACAddress.toUpperCase()) )
        					{
        						m_tblNetworkResults.SetCellValueAt(intIndex, "ESSID", strNetworkESSID);
        					}
        				}
        				strNetworkMACAddress = "";
        				strNetworkESSID = "";
        			}
        			
        			if ( strBuffer.contains("BSS ") && strNetworkMACAddress.equals("") == true )
        				strNetworkMACAddress = GetMACAddressFromBuffer( strBuffer );
        			
        			if ( strBuffer.contains("SSID: ") )
        				strNetworkESSID = strBuffer.substring(strBuffer.indexOf("SSID: ") + 6).trim();
        			
        			strBuffer = brOutput.readLine( );
        		}
        		
        		CorrectInvalidNetworkNames( );
        		
        		clsIWDevScan.CloseProcess();
        		
        		m_timUpdateNetworkESSIDs = new java.util.Timer( );
        		if ( !m_blnResultsClosing )		m_timUpdateNetworkESSIDs.schedule(new StartUpdateNetworkESSIDs( ), 1000 );
        		
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}
		
		// --------------------------------------------------------------------------------
		// Name: CorrectInvalidNetworkNames
		// Abstract: Networks without ESSIDs broadcast "\x00\x00\x00...", so we need to
		//			 replace that with an empty string.
		// --------------------------------------------------------------------------------
		private void CorrectInvalidNetworkNames( )
		{
			try
			{

        		for ( int intRowIndex = 0; intRowIndex < m_tblNetworkResults.GetRowCount(); intRowIndex += 1 )
        		{
        			String strESSID = String.valueOf(m_tblNetworkResults.GetCellValue(intRowIndex, "ESSID"));
        			 
        			if ( strESSID.contains("\\x00") )
        			{
        				strESSID = strESSID.replace("\\x00", "");
        				if ( strESSID.equals("") )
        					strESSID = " ";
        			}
        				
        			m_tblNetworkResults.SetCellValueAt(intRowIndex, "ESSID", strESSID);
        		}
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}

		// --------------------------------------------------------------------------------
		// Name: GetMACAddressFromBuffer
		// Abstract: Retrieves the network MAC address from the buffer
		// --------------------------------------------------------------------------------
		private String GetMACAddressFromBuffer( String strBuffer )
		{
			String strMACAddress = "";
			try
			{
				strMACAddress = strBuffer.substring(strBuffer.indexOf("BSS ") + 4);
				if ( strMACAddress.contains("(on "))
					strMACAddress = strMACAddress.substring(0, strMACAddress.indexOf("(on "));
				strMACAddress = strMACAddress.trim();
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
			return strMACAddress;
		}
    }

	// --------------------------------------------------------------------------------
	// Name: StartUpdateNetworkESSIDs
	// Abstract: Timer process that starts the new thread
	// --------------------------------------------------------------------------------
    class StartUpdateNetworkESSIDs extends TimerTask
    {

		@Override
		public void run()
		{
			try
			{
				Thread thdUpdateNetworkESSIDs = new Thread( new UpdateNetworkESSIDs( ) );
				thdUpdateNetworkESSIDs.start( );
			}
			catch (Exception excError)
			{
				CUtilities.WriteLog(excError);
			}
		}
    	
    }
    
    private String m_astrArguments[] = null;
    private String m_strMonitorModeInterface = "";
    private String m_strIWDevInterface = "";
    private CProcess m_clsRunningSearch = null;
    private CProcess m_clsWashScan = null;
    private java.util.Timer m_timUpdateScreen = null;
    private java.util.Timer m_timUpdateNetworkESSIDs = null;
    private CTable m_tblNetworkResults = null;
    private CTable m_tblStationResults = null;
    private final String m_astrNETWORK_COLUMNS[] = new String[] {"BSSID", "PWR", "Beacons", "#Data", "#/s", "CH", "MB", "ENC", "WPS", "CIPHER", "AUTH", "HANDSHAKE", "ESSID"};
    private final String m_astrSTATION_COLUMNS[] = new String[] {"BSSID", "ESSID", "STATION", "PWR", "Rate", "Lost", "Frames", "Probe"};
    private JLabel m_lblCurrentChannel = null;
    private JLabel m_lblTimeElapsed = null;
    private JLabel m_lblCurrentTime = null;
    private CDropdownButton m_ddbSaveToFile = null;
    
    // Network Dropdown
    private JMenuItem m_miHoneInOnNetwork = null;
    private JMenuItem m_miHoneInOnNetworkAndCapture = null;
    private JMenuItem m_miHoneInOnNetworkAndCaptureIVS = null;
    private JMenu m_mnuAttackNetwork = null;
    private JMenuItem m_miDeauthenticationAttackNetwork = null;
    private JMenuItem m_miFakeAuthentication = null;
    private JMenuItem m_miARPRequestReplayAttack = null;
    private JMenuItem m_miChopChopAttack = null;
    private JMenuItem m_miFragmentationAttack = null;
    private JMenuItem m_miCaffeLatteAttackNetwork = null;
    private JMenuItem m_miInjectionTest = null;
    private JMenuItem m_miReaverAttack = null;
    private JMenuItem m_miCrackWEPWPAKey = null;
    private JMenuItem m_miCommunicateWithAP = null;
    private JMenuItem m_miForgePackets = null;
    
    // Station Dropdown
    private JMenuItem m_miDeauthenticationAttackStation = null;
    private JMenuItem m_miCaffeLatteAttackStation = null;
    private JMenuItem m_miHydraAttack = null;
    private JMenuItem m_miHping3Attack = null;
    private JMenuItem m_miSpoofToMACAddress = null;
    private JMenuItem m_miSQLMapAttack = null;
    
    private boolean m_blnContinueRunningSearch = true;
    private boolean m_blnIWDevScanPassive = false;
    private String m_strWashScanInterface = "";
    private int m_intAirodumpNGProcessID = -1;
    private int m_intWashScanProcessID = -1;
    
    // --------------------------------------------------------------------------------
    // Name: FDiscoverNetworksResults
    // Abstract: Class constructor
    // --------------------------------------------------------------------------------
    public FDiscoverNetworksResults()
    {
    	super("Discover Networks - Output", 800, 510, false, false, "");  
        try
        {

            // Set the window to run the search once it's set visible
            addComponentListener( this );
            
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            m_timUpdateScreen = new java.util.Timer();
            m_timUpdateScreen.schedule( new UpdateDisplayTables(), m_intREFRESH_RATE_IN_SECONDS );
            
            m_timUpdateNetworkESSIDs = new java.util.Timer( );
    		m_timUpdateNetworkESSIDs.schedule(new StartUpdateNetworkESSIDs( ), 1000 );
            
    		addWindowListener(this);
    		
            Initialize();
            AddControls();
            
        }
        catch (Exception excError)
        {
            // Display error message
            CUtilities.WriteLog( excError );
        }
    }

    // ----------------------------------------------------------------------------
    // Name: Initialize
    // Abstract: Initializes window
    // ----------------------------------------------------------------------------
    private void Initialize( )
    {
        
        try
        {
            int intWidth = 800;
            int intHeight = 510;
            
            // Title
            setTitle( "Discover Networks - Output" );
            
            // Size
            setSize( intWidth, intHeight );
            
            // Center screen
            CUtilities.CenterScreen( this );
            
            // No resize
            setResizable( false );
            
    		
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }
    
    // ----------------------------------------------------------------------------
    // Name: AddControls
    // Abstract: Adds the controls to the window
    // ----------------------------------------------------------------------------
    private void AddControls( )
    {
        
        try
        {
        	
            // Spring layout for frame
            SpringLayout splFrame = new SpringLayout( );
            Container conControlArea = this.getContentPane( );
            conControlArea.setLayout(  splFrame );
            
            m_tblNetworkResults = CUtilities.AddTable(conControlArea, m_astrNETWORK_COLUMNS, null, 50, 5, 200, 780);
            m_tblStationResults = CUtilities.AddTable(conControlArea, m_astrSTATION_COLUMNS, null, 250, 5, 200, 780);
            m_lblCurrentChannel = CUtilities.AddLabel(conControlArea, "Channel: Not indicated yet", 5, 5);
            m_lblTimeElapsed = CUtilities.AddLabel(conControlArea, "Time Elapsed: Not indicated yet", 25, 5);
            m_lblCurrentTime = CUtilities.AddLabel(conControlArea, "", 5, 665);
            m_ddbSaveToFile = CUtilities.AddDropdownButton(conControlArea, this, this, "Save To File", 460, 700, 18, 100);
            m_ddbSaveToFile.SetMenuOptions(new String[] {"To CSV", "To HTML"});
            
            // Popup Menu for Networks
            JPopupMenu mnuNetworkResults = new JPopupMenu( );
            m_miHoneInOnNetwork = CAircrackUtilities.CreateMenuItem("Hone In On Network", mnuNetworkResults, this);
            m_miHoneInOnNetworkAndCapture = CAircrackUtilities.CreateMenuItem("Hone In And Capture", mnuNetworkResults, this);
            m_miHoneInOnNetworkAndCaptureIVS = CAircrackUtilities.CreateMenuItem("Hone In and Capture IVs", mnuNetworkResults, this);
            m_mnuAttackNetwork = new JMenu("Attack Network");
            m_miDeauthenticationAttackNetwork = CAircrackUtilities.CreateMenuItem("Deauthenticate", m_mnuAttackNetwork, this);
            m_miFakeAuthentication = CAircrackUtilities.CreateMenuItem("Fake Authentication", m_mnuAttackNetwork, this);
            m_miARPRequestReplayAttack = CAircrackUtilities.CreateMenuItem("ARP Request Replay", m_mnuAttackNetwork, this);
            m_miChopChopAttack = CAircrackUtilities.CreateMenuItem("Chopchop Attack", m_mnuAttackNetwork, this);
            m_miFragmentationAttack = CAircrackUtilities.CreateMenuItem("Fragmentation Attack", m_mnuAttackNetwork, this);
            m_miCaffeLatteAttackNetwork = CAircrackUtilities.CreateMenuItem("Caffe-latte Attack", m_mnuAttackNetwork, this);
            m_miInjectionTest = CAircrackUtilities.CreateMenuItem("Injection Test", m_mnuAttackNetwork, this);
            m_mnuAttackNetwork.addSeparator();
            m_miReaverAttack = CAircrackUtilities.CreateMenuItem("Reaver", m_mnuAttackNetwork, this);
            mnuNetworkResults.add(m_mnuAttackNetwork);
            m_miCrackWEPWPAKey = CAircrackUtilities.CreateMenuItem("Crack WEP/WPA Key", mnuNetworkResults, this);
            m_miCommunicateWithAP = CAircrackUtilities.CreateMenuItem("Communicate w/AP", mnuNetworkResults, this);
            m_miForgePackets = CAircrackUtilities.CreateMenuItem("Forge Packets", mnuNetworkResults, this);
            m_tblNetworkResults.AddPopupMenu( mnuNetworkResults );
            
            // Popup Menu for Stations
            JPopupMenu mnuStationResults = new JPopupMenu( );
            m_miSpoofToMACAddress = CAircrackUtilities.CreateMenuItem("Spoof to Station MAC", mnuStationResults, this);
            m_miDeauthenticationAttackStation = CAircrackUtilities.CreateMenuItem("Deauthenticate", mnuStationResults, this);
            m_miCaffeLatteAttackStation = CAircrackUtilities.CreateMenuItem("Caffe-latte Attack", mnuStationResults, this);
            m_miHydraAttack = CAircrackUtilities.CreateMenuItem("Hydra Attack", mnuStationResults, this);
            m_miHping3Attack = CAircrackUtilities.CreateMenuItem("Hping3 Attack", mnuStationResults, this);
            m_miSQLMapAttack = CAircrackUtilities.CreateMenuItem("SQLMap Attack", mnuStationResults, this);
            m_tblStationResults.AddPopupMenu( mnuStationResults );
            
            m_miCommunicateWithAP.setEnabled( false );
            m_miSQLMapAttack.setEnabled( false );
            m_miHydraAttack.setEnabled( false );
            m_miHping3Attack.setEnabled( false );
            
            // Set Column Widths for Network Results
            m_tblNetworkResults.SetColumnWidth("BSSID", 120);
            m_tblNetworkResults.SetColumnWidth("PWR", 40);
            m_tblNetworkResults.SetColumnWidth("Beacons", 70);
            m_tblNetworkResults.SetColumnWidth("#Data", 55);
            m_tblNetworkResults.SetColumnWidth("#/s", 40);
            m_tblNetworkResults.SetColumnWidth("CH", 40);
            m_tblNetworkResults.SetColumnWidth("MB", 40);
            m_tblNetworkResults.SetColumnWidth("ENC", 40);
            m_tblNetworkResults.SetColumnWidth("WPS", 40);
            m_tblNetworkResults.SetColumnWidth("CIPHER", 55);
            m_tblNetworkResults.SetColumnWidth("AUTH", 40);
            
            // Set Column Widths for Station Results
            m_tblStationResults.SetColumnWidth("BSSID", 120);
            m_tblStationResults.SetColumnWidth("STATION", 120);
            m_tblStationResults.SetColumnWidth("PWR", 40);
            m_tblStationResults.SetColumnWidth("Rate", 40);
            m_tblStationResults.SetColumnWidth("Lost", 40);
            m_tblStationResults.SetColumnWidth("Frames", 55);
            
        }
        catch ( Exception excError )
        {
            
            // Display error message
            CUtilities.WriteLog( excError );
            
        }
        
    }

    // ----------------------------------------------------------------------------
    // Name: actionPerformed
    // Abstract: Menu item click events
    // ----------------------------------------------------------------------------
    @Override
    public void actionPerformed( ActionEvent aeSource )
    {
        
        try
        {

        	// Network Attacks
        	if ( aeSource.getSource( ) == m_miHoneInOnNetwork ) miHoneInOnNetwork_Click( );
        	else if ( aeSource.getSource( ) == m_miHoneInOnNetworkAndCapture ) miHoneInOnNetworkAndCapture_Click( );
        	else if ( aeSource.getSource( ) == m_miHoneInOnNetworkAndCaptureIVS ) miHoneInOnNetworkAndCaptureIVS_Click( );
        	else if ( aeSource.getSource() == m_miDeauthenticationAttackNetwork ) miDeauthenticationAttackNetwork_Click( );
            else if ( aeSource.getSource() == m_miFakeAuthentication ) miFakeAuthentication_Click( );
            else if ( aeSource.getSource() == m_miARPRequestReplayAttack ) miARPRequestReplayAttack_Click( );
            else if ( aeSource.getSource() == m_miChopChopAttack ) miChopChopAttack_Click( );
            else if ( aeSource.getSource() == m_miFragmentationAttack ) miFragmentationAttack_Click( );
            else if ( aeSource.getSource() == m_miCaffeLatteAttackNetwork ) miCaffeLatteAttackNetwork_Click( );
            else if ( aeSource.getSource() == m_miInjectionTest ) miInjectionTest_Click( );
            else if ( aeSource.getSource() == m_miReaverAttack ) miReaverAttack_Click( );
            else if ( aeSource.getSource() == m_miCrackWEPWPAKey ) miCrackWEPWPAKey_Click( );
            else if ( aeSource.getSource() == m_miCommunicateWithAP ) miCommunicateWithAP_Click( );
            else if ( aeSource.getSource() == m_miForgePackets ) miForgePackets_Click( );
            
            // Station Attacks
            else if ( aeSource.getSource() == m_miDeauthenticationAttackStation ) miDeauthenticationAttackStation( );
            else if ( aeSource.getSource() == m_miCaffeLatteAttackStation ) miCaffeLatteAttackStation( );
            // TODO: Add hydra attack code
            // TODO: Add hping3 attack code
            else if ( aeSource.getSource() == m_miSpoofToMACAddress ) miSpoofToMACAddress_Click( );
            // TODO: Add sql map attack code
        	
            else if ( aeSource.getSource( ) == m_ddbSaveToFile.GetMenuItem("To CSV") ) SaveResultsToCSV( );
            else if ( aeSource.getSource( ) == m_ddbSaveToFile.GetMenuItem("To HTML") ) SaveResultsToHTML( );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        
    }

    // ----------------------------------------------------------------------------
    // Name: miHoneInOnNetwork_Click
    // Abstract: Hones in on the network so only it will be displayed in a new
    //			 results window.
    // ----------------------------------------------------------------------------
    private void miHoneInOnNetwork_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow( );
    		String strBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		String strChannel = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "CH"));
    		
    		String astrCommand[] = new String[0];
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("-bssid", strBSSID, astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("c", strChannel, astrCommand);
    		
    		FDiscoverNetworksResults frmNewScanResults = new FDiscoverNetworksResults( );
            frmNewScanResults.SetArguments( astrCommand );
            frmNewScanResults.SetAirodumpInterface( m_strMonitorModeInterface );
            frmNewScanResults.SetIWDevInterface( m_strIWDevInterface );
            frmNewScanResults.SetIWDevScanPassiveScan( m_blnIWDevScanPassive );
            if ( m_strWashScanInterface.equals("") == false )
            	frmNewScanResults.SetWashInterface(m_strWashScanInterface);
            frmNewScanResults.setVisible( true );
            
            this.dispose( );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miHoneInOnNetworkAndCapture_Click
    // Abstract: Same as Hone In On Network but also writes information to the file
    // ----------------------------------------------------------------------------
    private void miHoneInOnNetworkAndCapture_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow( );
    		String strBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		String strChannel = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "CH"));
    		
    		String astrCommand[] = new String[0];
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("-bssid", strBSSID, astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("c", strChannel, astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("w", "autocapture", astrCommand);
    		
    		FDiscoverNetworksResults frmNewScanResults = new FDiscoverNetworksResults( );
            frmNewScanResults.SetArguments( astrCommand );
            frmNewScanResults.SetAirodumpInterface( m_strMonitorModeInterface );
            frmNewScanResults.SetIWDevInterface( m_strIWDevInterface );
            frmNewScanResults.SetIWDevScanPassiveScan( m_blnIWDevScanPassive );
            if ( m_strWashScanInterface.equals("") == false )
            	frmNewScanResults.SetWashInterface(m_strWashScanInterface);
            frmNewScanResults.setVisible( true );
            
            this.dispose( );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miHoneInOnNetworkAndCaptureIVS_Click
    // Abstract: Same as Hone In On Network and Capture but only captures initialization
    //			 vectors (IVS)
    // ----------------------------------------------------------------------------
    private void miHoneInOnNetworkAndCaptureIVS_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow( );
    		String strBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		String strChannel = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "CH"));
    		
    		String astrCommand[] = new String[0];
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("-bssid", strBSSID, astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("c", strChannel, astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("-ivs", "", astrCommand);
    		astrCommand = CAircrackUtilities.AddArgumentToCommand("w", "autocapture", astrCommand);
    		
    		FDiscoverNetworksResults frmNewScanResults = new FDiscoverNetworksResults( );
            frmNewScanResults.SetArguments( astrCommand );
            frmNewScanResults.SetAirodumpInterface( m_strMonitorModeInterface );
            frmNewScanResults.SetIWDevInterface( m_strIWDevInterface );
            frmNewScanResults.SetIWDevScanPassiveScan( m_blnIWDevScanPassive );
            if ( m_strWashScanInterface.equals("") == false )
            	frmNewScanResults.SetWashInterface(m_strWashScanInterface);
            frmNewScanResults.setVisible( true );
            
            this.dispose( );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miDeauthenticationAttackNetwork_Click
    // Abstract: Opens a replay-inject packets window with de-authentication selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miDeauthenticationAttackNetwork_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmDeauthenticateNetwork = new FReplayInjectPackets( );
    		frmDeauthenticateNetwork.SetNetworkBSSID(strNetworkBSSID);
    		frmDeauthenticateNetwork.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miFakeAuthentication_Click
    // Abstract: Opens a replay-inject packets window with fake authentication selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miFakeAuthentication_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("Fake authentication");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miARPRequestReplayAttack_Click
    // Abstract: Opens a replay-inject packets window with ARP request replay selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miARPRequestReplayAttack_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("ARP request replay attack");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miChopChopAttack_Click
    // Abstract: Opens a replay-inject packets window with chopchop attack selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miChopChopAttack_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("KoreK chopchop attack");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miChopChopAttack_Click
    // Abstract: Opens a replay-inject packets window with fragmentation attack selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miFragmentationAttack_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("Fragmentation attack");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miCaffeLatteAttackNetwork_Click
    // Abstract: Opens a replay-inject packets window with cafe-latte attack selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miCaffeLatteAttackNetwork_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("Cafe-latte attack");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miInjectionTest_Click
    // Abstract: Opens a replay-inject packets window with injection test selected
    //			 and network MAC address copied over.
    // ----------------------------------------------------------------------------    
    private void miInjectionTest_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FReplayInjectPackets frmFakeAuthenticationAttack = new FReplayInjectPackets( );
    		frmFakeAuthenticationAttack.SetAttackMethod("Injection test");
    		frmFakeAuthenticationAttack.SetNetworkBSSID(strNetworkBSSID);
    		frmFakeAuthenticationAttack.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miReaverAttack_Click
    // Abstract: Starts a Reaver window with the target network populated
    // ----------------------------------------------------------------------------    
    private void miReaverAttack_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FOtherToolsReaver frmReaver = new FOtherToolsReaver( );
    		frmReaver.SetNetworkBSSID( strNetworkBSSID );
    		frmReaver.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miInjectionTest_Click
    // Abstract: Opens a crack WEP/WPA key window with the network MAC address
    //			 copied over.
    // ----------------------------------------------------------------------------
    private void miCrackWEPWPAKey_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FCrackWEPWPAKeys frmCrackWEPWPAKeys = new FCrackWEPWPAKeys( );
    		frmCrackWEPWPAKeys.SetNetworkBSSID( strNetworkBSSID );
    		frmCrackWEPWPAKeys.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miCommunicateWithAP_Click
    // Abstract: Opens a communicate w/AP window with the network MAC address copied
    //			 over.
    // ----------------------------------------------------------------------------
    private void miCommunicateWithAP_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FCommunicateWithAP frmCommunicateWithAP = new FCommunicateWithAP();
    		frmCommunicateWithAP.SetTargetMAC( strNetworkBSSID );
    		frmCommunicateWithAP.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miForgePackets_Click
    // Abstract: Opens a forge packets window with the network MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miForgePackets_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblNetworkResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblNetworkResults.GetCellValue(intSelectedRow, "BSSID"));
    		FForgePackets frmForgePackets = new FForgePackets( );
    		frmForgePackets.SetAccessPointMAC( strNetworkBSSID );
    		frmForgePackets.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: miDeauthenticationAttackStation
    // Abstract: Opens a replay-inject packets window with de-authentication selected
    //			 and network MAC address and station MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miDeauthenticationAttackStation( )
    {
    	try
    	{
    		int intSelectedRow = m_tblStationResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblStationResults.GetCellValue(intSelectedRow, "BSSID"));
    		String strStationBSSID = String.valueOf(m_tblStationResults.GetCellValue(intSelectedRow, "STATION"));
    		FReplayInjectPackets frmReplayInjectPackets = new FReplayInjectPackets( );
    		frmReplayInjectPackets.SetNetworkBSSID(strNetworkBSSID);
    		frmReplayInjectPackets.SetDestinationMAC(strStationBSSID);
    		frmReplayInjectPackets.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miCaffeLatteAttackStation
    // Abstract: Opens a replay-inject packets window with cafe-latte attack selected
    //			 and network MAC address and station MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miCaffeLatteAttackStation( )
    {
    	try
    	{
    		int intSelectedRow = m_tblStationResults.GetSelectedRow();
    		String strNetworkBSSID = String.valueOf(m_tblStationResults.GetCellValue(intSelectedRow, "BSSID"));
    		String strStationBSSID = String.valueOf(m_tblStationResults.GetCellValue(intSelectedRow, "STATION"));
    		FReplayInjectPackets frmReplayInjectPackets = new FReplayInjectPackets( );
    		frmReplayInjectPackets.SetAttackMethod("Cafe-latte attack");
    		frmReplayInjectPackets.SetNetworkBSSID(strNetworkBSSID);
    		frmReplayInjectPackets.SetDestinationMAC(strStationBSSID);
    		frmReplayInjectPackets.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: miForgePackets_Click
    // Abstract: Opens a MAC changer window with the station's MAC address copied over.
    // ----------------------------------------------------------------------------
    private void miSpoofToMACAddress_Click( )
    {
    	try
    	{
    		int intSelectedRow = m_tblStationResults.GetSelectedRow();
    		String strStationBSSID = String.valueOf(m_tblStationResults.GetCellValue(intSelectedRow, "STATION"));
    		FOtherToolsMACChanger frmMACChanger = new FOtherToolsMACChanger( );
    		frmMACChanger.SetCustomMACAddress(strStationBSSID);
    		frmMACChanger.setVisible( true );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: StartSearch
    // Abstract: Starts the airodump-ng, iw dev, and wash scans
    // ----------------------------------------------------------------------------
    private void StartSearch( )
    {
        try
        {
            
            String astrCommands[] = GetProcessBuilderString();
            m_clsRunningSearch = new CProcess(astrCommands, true, false, true);
            m_intAirodumpNGProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess(astrCommands[0]);
            m_brAirodumpNGOutput = new BufferedReader(m_clsRunningSearch.GetOutput());
            
            if ( m_strWashScanInterface.equals("") == false )
            {
            	String astrWashCommand[] = new String[] {"wash", "-i", m_strWashScanInterface, "--ignore-fcs"};
            	m_clsWashScan = new CProcess(astrWashCommand, true, false, true);
            	m_intWashScanProcessID = CGlobals.clsLocalMachine.GetProcessIDFromLastRunningProcess("wash");
            	m_brWashOutput = new BufferedReader( m_clsWashScan.GetOutput( ) );
            }
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
            JOptionPane.showMessageDialog( this, excError.getMessage( ) );
        }
    }
    
    // ----------------------------------------------------------------------------
    // Name: GetProcessBuilderString
    // Abstract: Retrieves the string to use to build the process
    // ----------------------------------------------------------------------------
    private String[] GetProcessBuilderString( )
    {
        String astrProcessBuilderArguments[] = null;
        try
        {
            
            if ( m_astrArguments != null)
                astrProcessBuilderArguments = new String[m_astrArguments.length + 2];
            else
                astrProcessBuilderArguments = new String[2];
            
            astrProcessBuilderArguments[0] = "airodump-ng";
            
            if ( m_astrArguments != null )
            {
                for ( int intIndex = 0; intIndex < m_astrArguments.length; intIndex += 1 )
                {
                    astrProcessBuilderArguments[intIndex + 1] = m_astrArguments[intIndex].trim( );
                }
            }
            
            astrProcessBuilderArguments[astrProcessBuilderArguments.length - 1] = m_strMonitorModeInterface.trim( );
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
        return astrProcessBuilderArguments;
    }

    // ----------------------------------------------------------------------------
    // Name: SetArguments
    // Abstract: Sets the airodump-ng arguments (from the Discover Networks screen)
    // ----------------------------------------------------------------------------
    public void SetArguments(String astrArguments[])
    {
        try
        {
            m_astrArguments = astrArguments;
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

    // ----------------------------------------------------------------------------
    // Name: SetAirodumpInterface
    // Abstract: Sets the interface to use for the airodump-ng scan.
    // ----------------------------------------------------------------------------
    public void SetAirodumpInterface(String strMonitorModeInterface)
    {
        try
        {
            m_strMonitorModeInterface = strMonitorModeInterface;
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

    // ----------------------------------------------------------------------------
    // Name: SetIWDevInterface
    // Abstract: Sets the interface to use for the iw dev list scan.
    // ----------------------------------------------------------------------------
    public void SetIWDevInterface( String strIWListInterface )
    {
    	try
    	{
    		m_strIWDevInterface = strIWListInterface;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog( excError );
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: SetIWDevScanPassiveScan
    // Abstract: Sets whether the iw dev scan is passive or active
    // ----------------------------------------------------------------------------
    public void SetIWDevScanPassiveScan(boolean blnIsPassiveScan)
    {
    	try
    	{
    		m_blnIWDevScanPassive = blnIsPassiveScan;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }

    // ----------------------------------------------------------------------------
    // Name: SetWashInterface
    // Abstract: Sets the interface to do the wash scan on
    // ----------------------------------------------------------------------------
    public void SetWashInterface(String strNewWashInterface)
    {
    	try
    	{
    		m_strWashScanInterface = strNewWashInterface;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // ----------------------------------------------------------------------------
    // Name: componentShown
    // Abstract: Starts the scan once the form is displayed
    // ----------------------------------------------------------------------------    
    @Override
    public void componentShown( ComponentEvent arg0 )
    {
        try
        {
            StartSearch( );
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

    // ----------------------------------------------------------------------------
    // Name: windowClosing
    // Abstract: Does process clean-up when the page is starting to close
    // ----------------------------------------------------------------------------
    @Override
    public void windowClosing( WindowEvent arg0 )
    {
        try
        {

        	m_blnResultsClosing = true;
            boolean blnKillProcess = CGlobals.clsLocalMachine.KillRunningProcess(m_intAirodumpNGProcessID);
            if ( !blnKillProcess )
            {
            	JOptionPane.showMessageDialog(null, "Could not kill airodump-ng process (PID " + String.valueOf(m_intAirodumpNGProcessID) +"). Please manually destroy it.");
            }
            
            if ( m_intWashScanProcessID != -1 )
            {
            	blnKillProcess = CGlobals.clsLocalMachine.KillRunningProcess(m_intWashScanProcessID);
            	if ( !blnKillProcess )
            	{
            		JOptionPane.showMessageDialog(null, "Could not kill wash process (PID " + String.valueOf(m_intWashScanProcessID) + "). Please manually destroy it.");
            	}
            	m_clsWashScan.CloseProcess();
            }
            
            m_clsRunningSearch.CloseProcess();
            
        }
        catch (Exception excError)
        {
            CUtilities.WriteLog( excError );
        }
    }

    @Override
	public void mouseClicked(MouseEvent meSource)
    {
    	try
    	{
    		if ( meSource.getSource( ) == m_ddbSaveToFile ) m_ddbSaveToFile.DisplayPopupMenu( meSource );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
	}

    // --------------------------------------------------------------------------------
    // Name: SaveResultsToCSV
    // Abstract: Saves the network and station information to a CSV file.
    // --------------------------------------------------------------------------------
    private void SaveResultsToCSV( )
    {
    	try
    	{
    		String strContents = "";
    		String strRowContents = "";
    		
    		// Add our network headers to the CSV
    		for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetColumnCount( ); intIndex += 1 )
    		{
    			if ( strRowContents.equals("") == false )
    				strRowContents += ",";
    			strRowContents += "\"" + m_tblNetworkResults.GetColumnName( intIndex ) + "\"";
    		}
    		strRowContents += "\n";
    		strContents += strRowContents;
    		strRowContents = "";
    		
    		// Add our network results to the CSV
    		for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetRowCount( ); intIndex += 1 )
    		{
    			for ( int intIndex2 = 0; intIndex2 < m_tblNetworkResults.GetColumnCount( ); intIndex2 += 1 )
    			{
    				if ( strRowContents.equals("") == false )
    					strRowContents += ",";
    				strRowContents += "\""+ m_tblNetworkResults.GetCellValue(intIndex, intIndex2) + "\"";
    			}
    			strRowContents += "\n";
    			strContents += strRowContents;
        		strRowContents = "";
    		}
    		
    		strContents += "\n\n";
    		
    		// Add our client headers to the CSV
    		for ( int intIndex = 0; intIndex < m_tblStationResults.GetColumnCount( ); intIndex += 1 )
    		{
    			if ( strRowContents.equals("") == false )
    				strRowContents += ",";
    			strRowContents += "\"" + m_tblStationResults.GetColumnName( intIndex ) + "\"";
    		}
    		strRowContents += "\n";
    		strContents += strRowContents;
    		strRowContents = "";
    		
    		// Add our client results to the CSV
    		for ( int intIndex = 0; intIndex < m_tblStationResults.GetRowCount( ); intIndex += 1 )
    		{
    			for ( int intIndex2 = 0; intIndex2 < m_tblNetworkResults.GetColumnCount( ); intIndex2 += 1 )
    			{
    				if ( strRowContents.equals("") == false )
    					strRowContents += ",";
    				strRowContents += "\"" + m_tblStationResults.GetCellValue(intIndex, intIndex2) + "\"";
    			}
    			strRowContents += "\n";
    			strContents += strRowContents;
    			strRowContents = "";
    		}
    		
    		String strChosenLocation = CAircrackUtilities.DisplayFileChooser(this, false, ".csv", "Comma-separated values (*.csv)");
    		if ( strChosenLocation.equals("") == false )
    		{
    			FileWriter fsTest = new FileWriter( strChosenLocation );
				BufferedWriter bwOutput = new BufferedWriter( fsTest );
				bwOutput.write( strContents );
				bwOutput.close( );
				JOptionPane.showMessageDialog(null, "Output successfully saved to file.");
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // --------------------------------------------------------------------------------
    // Name: SaveResultsToHTML
    // Abstract: Saves the network and station information to an HTML file.
    // --------------------------------------------------------------------------------
    private void SaveResultsToHTML( )
    {
    	try
    	{
    		String strContents = "";
    		String strRowContents = "";
    		
    		// Add our network headers to the CSV
    		strContents += "<table border=\"1\">";
    		strContents += "	<tr>";
    		for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetColumnCount( ); intIndex += 1 )
    		{
    			strRowContents += "<td>" + m_tblNetworkResults.GetColumnName( intIndex ) + "</td>";
    		}
    		strContents += strRowContents;
    		strContents += "	</tr>";
    		strRowContents = "";
    		
    		// Add our network results to the CSV
    		for ( int intIndex = 0; intIndex < m_tblNetworkResults.GetRowCount( ); intIndex += 1 )
    		{
    			strRowContents += "<tr>";
    			for ( int intIndex2 = 0; intIndex2 < m_tblNetworkResults.GetColumnCount( ); intIndex2 += 1 )
    			{
    				strRowContents += "<td>"+ m_tblNetworkResults.GetCellValue(intIndex, intIndex2) + "</td>";
    			}
    			strRowContents += "</tr>";
    			strContents += strRowContents;
        		strRowContents = "";
    		}
    		
    		strContents += "</table><br /><br /><table border=\"1\"><tr>";
    		
    		// Add our client headers to the CSV
    		for ( int intIndex = 0; intIndex < m_tblStationResults.GetColumnCount( ); intIndex += 1 )
    		{
    			strRowContents += "<td>" + m_tblStationResults.GetColumnName( intIndex ) + "</td>";
    		}
    		strRowContents += "</tr>";
    		strContents += strRowContents;
    		strRowContents = "";
    		
    		// Add our client results to the CSV
    		for ( int intIndex = 0; intIndex < m_tblStationResults.GetRowCount( ); intIndex += 1 )
    		{
    			strRowContents += "<tr>";
    			for ( int intIndex2 = 0; intIndex2 < m_tblNetworkResults.GetColumnCount( ); intIndex2 += 1 )
    			{
    				if ( strRowContents.equals("") == false )
    					strRowContents += ",";
    				strRowContents += "<td>" + m_tblStationResults.GetCellValue(intIndex, intIndex2) + "</td>";
    			}
    			strRowContents += "</tr>";
    			strContents += strRowContents;
    			strRowContents = "";
    		}
    		strContents += "</table>";
    		
    		String strChosenLocation = CAircrackUtilities.DisplayFileChooser(this, false, ".csv", "Comma-separated values (*.csv)");
    		if ( strChosenLocation.equals("") == false )
    		{
    			FileWriter fsTest = new FileWriter( strChosenLocation );
				BufferedWriter bwOutput = new BufferedWriter( fsTest );
				bwOutput.write( strContents );
				bwOutput.close( );
				JOptionPane.showMessageDialog(null, "Output successfully saved to file.");
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    // Not used
    @Override
    public void windowActivated( WindowEvent arg0 ) {}
    @Override
    public void windowClosed( WindowEvent arg0 ) {}
    @Override
    public void windowDeactivated( WindowEvent arg0 ) {}
    @Override
    public void windowDeiconified( WindowEvent arg0 ) {}
    @Override
    public void windowIconified( WindowEvent arg0 ) {}
    @Override
    public void windowOpened( WindowEvent arg0 ) {}
    @Override
    public void componentHidden( ComponentEvent arg0 ) {}
    @Override
    public void componentMoved( ComponentEvent arg0 ) {}
    @Override
    public void componentResized( ComponentEvent arg0 ) {}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
