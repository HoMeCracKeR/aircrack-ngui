// --------------------------------------------------------------------------------
// Name: CFileChooserFilter
// Abstract: Easy way to specify a file type filter on file choosers
// --------------------------------------------------------------------------------

// Includes
import java.io.File;
import javax.swing.filechooser.*;

public class CFileChooserFilter extends FileFilter
{
	private String m_strExtension = "";
	private String m_strExtensionDescription = "";
	
	// --------------------------------------------------------------------------------
	// Name: CFileChooserFilter
	// Abstract: Class constructor
	// --------------------------------------------------------------------------------
	public CFileChooserFilter(String strAcceptedExtension, String strExtensionDescription)
	{
		try
		{
			m_strExtension = strAcceptedExtension;
			m_strExtensionDescription = strExtensionDescription;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// --------------------------------------------------------------------------------
	// Name: accept
	// Abstract: Returns if the file can be selected
	// --------------------------------------------------------------------------------
	@Override
	public boolean accept(File filSelectedFile)
	{
		boolean blnAccepted = true;
		try
		{
			if ( filSelectedFile.isDirectory( ) )
				blnAccepted = false;
			if ( filSelectedFile.getName( ).endsWith( m_strExtension ) )
				blnAccepted = true;
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
			blnAccepted = false;
		}
		return blnAccepted;
	}

	// --------------------------------------------------------------------------------
	// Name: getDescription
	// Abstract: Returns the description of the filter
	// --------------------------------------------------------------------------------	
	@Override
	public String getDescription( )
	{
		return m_strExtensionDescription;
	}
}
