import java.awt.*;
import javax.swing.*;

public class CTabContainer
{
	private JTabbedPane m_tpnTabContainer = new JTabbedPane( );
	
	// --------------------------------------------------------------------------------
	// Name: AddTab
	// Abstract: Adds a tab to the tab container
	// --------------------------------------------------------------------------------
	public JPanel AddTab( String strTabName )
	{
		JPanel pnlNewTab = null;
		try
		{
			pnlNewTab = new JPanel( );
			pnlNewTab.setLayout( new SpringLayout( ) );
			m_tpnTabContainer.addTab(strTabName, pnlNewTab);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		return pnlNewTab;
	}
	
	// --------------------------------------------------------------------------------
	// Name: SetPosition
	// Abstract: Sets the position in the container
	// --------------------------------------------------------------------------------
	public void SetPosition( Container conContainer, int intX, int intY, int intWidth, int intHeight )
	{
		try
		{
			SpringLayout splLayout = (SpringLayout)conContainer.getLayout( );
			splLayout.getConstraints( m_tpnTabContainer ).setX( Spring.constant( intX ) );
			splLayout.getConstraints( m_tpnTabContainer ).setY( Spring.constant( intY ) );
			splLayout.getConstraints( m_tpnTabContainer ).setWidth( Spring.constant( intWidth ) );
			splLayout.getConstraints( m_tpnTabContainer ).setHeight( Spring.constant( intHeight ) );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	// --------------------------------------------------------------------------------
	// Name: GetJTabbedPane
	// Abstract: Gets the Java tabbed pane
	// --------------------------------------------------------------------------------
	public JTabbedPane GetJTabbedPane( )
	{
		return m_tpnTabContainer;
	}
}
