import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;


public class CDropdownButton extends JPanel
{

    String text;  
    GeneralPath arrow;  
    int left;  
    boolean firstTime = true;  
    boolean isSelected = false;  
    private JMenuItem m_amiOptions[];
    private ActionListener m_frmParent = null;
    protected final static long serialVersionUID = 1L;
    
    public CDropdownButton(String text) {  
        this.text = text;  
        setBackground(UIManager.getColor("Menu.background"));  
        setForeground(UIManager.getColor("Menu.foreground"));  
        setOpaque(false);    
    }  
   
    protected void paintComponent(Graphics g) {  
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                            RenderingHints.VALUE_ANTIALIAS_ON);  
        int h = getHeight();  
        Font font = UIManager.getFont("Menu.font");  
        g2.setFont(font);  
        FontRenderContext frc = g2.getFontRenderContext();  
        Rectangle2D r = font.getStringBounds(text, frc);  
        float sx = 5f;  
        float sy = (float)((h + r.getHeight())/2) -  
                       font.getLineMetrics(text, frc).getDescent();  
        g2.drawString(text, sx, sy);  
        double x = sx + r.getWidth() + sx;  
        if(isSelected) {  
            g2.setPaint(Color.gray);  
            g2.draw(new Line2D.Double(x, 0, x, h));  
            g2.setPaint(Color.white);  
            g2.draw(new Line2D.Double(x+1, 0, x+1, h));  
            g2.setPaint(Color.gray);  
            g2.draw(new Rectangle2D.Double(0, 0, getSize().width-1, h-1));  
        }  
        float ax = (float)(x + sx);  
        if(firstTime)  
            createArrow(ax, h);  
        g2.setPaint(UIManager.getColor("Menu.foreground"));  
        g2.fill(arrow);  
        ax += 10f + sx;  
        if(firstTime) {  
            setSize((int)ax, h);         // initial sizing  
            setPreferredSize(getSize());  
            setMaximumSize(getSize());   // resizing behavior  
            left = (int)x + 1;           // for mouse listener  
            firstTime = false;  
        }  
    }  
   
    private void createArrow(float x, int h) {  
        arrow = new GeneralPath();  
        arrow.moveTo(x, h/3f);  
        arrow.lineTo(x + 10f, h/3f);  
        arrow.lineTo(x + 5f, h*2/3f);  
        arrow.closePath();  
    }
   
    public JPopupMenu getPopupMenu() {  
        JPopupMenu popupMenu = new JPopupMenu();  
        if ( m_amiOptions != null )
        {
		    for(int j = 0; j < m_amiOptions.length; j++) {    
		        popupMenu.add(m_amiOptions[j]);  
		    }  
        }
        return popupMenu;  
    } 

    public void SetParent(ActionListener frmParent)
    {
    	try
    	{
    		m_frmParent = frmParent;
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    public void SetMenuOptions(String astrMenuItems[])
    {
    	try
    	{
    		m_amiOptions = new JMenuItem[ 0 ];
    		JMenuItem amiTempList[];
    		for ( int intIndex = 0; intIndex < astrMenuItems.length; intIndex += 1 )
    		{
    			amiTempList = new JMenuItem[ m_amiOptions.length + 1 ];
    			for ( int intIndex2 = 0; intIndex2 < m_amiOptions.length; intIndex2 += 1 )
    			{
    				amiTempList[intIndex2] = m_amiOptions[intIndex2];
    			}
    			amiTempList[amiTempList.length - 1] = new JMenuItem(astrMenuItems[intIndex]);
    			amiTempList[amiTempList.length - 1].addActionListener( m_frmParent );
    			m_amiOptions = amiTempList;
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
    public JMenuItem GetMenuItem(String strTitle)
    {
    	JMenuItem miSearchedItem = null;
    	try
    	{
    		for (int intIndex = 0; intIndex < m_amiOptions.length; intIndex += 1 )
    		{
    			if ( m_amiOptions[intIndex].getText().equals(strTitle) )
    			{
    				miSearchedItem = m_amiOptions[intIndex];
    				break;
    			}
    		}
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    	return miSearchedItem;
    }
    
    public void DisplayPopupMenu( MouseEvent meSource )
    {
    	try
    	{
    		getPopupMenu().show( meSource.getComponent( ), meSource.getX( ), meSource.getY( ) );
    	}
    	catch (Exception excError)
    	{
    		CUtilities.WriteLog(excError);
    	}
    }
    
}
