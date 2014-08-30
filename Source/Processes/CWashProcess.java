
public class CWashProcess extends CProcess
{
	public void StartWashScan(String strInterface)
	{
		try
		{
			String astrCommand[] = new String[] {"wash", "-i", strInterface, "--ignore-fcs"};
			super.RunProcess(astrCommand, true, false, true);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
}
