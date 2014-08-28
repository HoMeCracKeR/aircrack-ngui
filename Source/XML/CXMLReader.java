// ----------------------------------------------------------------------
// Name: CXMLReader
// Abstract: Class for reading and extracting data from XML files
// ----------------------------------------------------------------------

// Includes
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class CXMLReader
{
	
	private DocumentBuilder m_dbDocumentBuilder = null;
	private Document m_docLoadedDocument = null;
	
	// ----------------------------------------------------------------------
	// Name: CXMLReader
	// Abstract: Class constructor
	// ----------------------------------------------------------------------
	public CXMLReader( )
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        m_dbDocumentBuilder = factory.newDocumentBuilder();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// ----------------------------------------------------------------------
	// Name: LoadXMLIntoMemory
	// Abstract: Loads the XML from the file into memory to parse
	// ----------------------------------------------------------------------
	public boolean LoadXMLIntoMemory(String strPathToXMLFile)
	{
		boolean blnSuccessful = false;
		try
		{
			File filXMLDocument = new File(strPathToXMLFile);
			if ( filXMLDocument.exists( ) == true )
			{
				m_docLoadedDocument = m_dbDocumentBuilder.parse( filXMLDocument );
				blnSuccessful = true;
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return blnSuccessful;
	}
	
	// ----------------------------------------------------------------------
	// Name: GetRootElement
	// Abstract: Gets the root element of the XML document
	// ----------------------------------------------------------------------
	public Element GetRootElement( )
	{
		Element eleRootElement = null;
		try
		{
			if ( m_docLoadedDocument != null )
			{
				eleRootElement = m_docLoadedDocument.getDocumentElement( );
			}
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return eleRootElement;
	}
	
	// ----------------------------------------------------------------------
	// Name: FindElementByTagNameAndValue
	// Abstract: Finds an element with the specified name and specified element
	//			 value
	// ----------------------------------------------------------------------
	public Element FindElementByTagNameAndValue(Element eleRootNode, String strSearchTagName, String strSearchElementName, String strValue)
	{
		Element eleSearchElement = null;
		try
		{
			String strTagName = eleRootNode.getTagName( );
			boolean blnHasElement = false;
			String strElementValue = "";
						
			if ( strTagName.equals( strSearchTagName ) )
			{
				blnHasElement = eleRootNode.hasAttribute(strSearchElementName);
				if ( blnHasElement == true )
				{
					strElementValue = eleRootNode.getAttribute( strSearchElementName );
					if ( strElementValue.equals( strValue ) )
					{
						eleSearchElement = eleRootNode;
					}
				}
			}
			
			if ( eleSearchElement == null )
			{
				for ( int intIndex = 0; intIndex < eleRootNode.getChildNodes( ).getLength( ); intIndex += 1 )
				{
					eleSearchElement = FindElementByTagNameAndValue((Element)eleRootNode.getChildNodes( ).item(intIndex), strSearchTagName, strSearchElementName, strValue);
				}
			}
		}
		catch (Exception excError)
		{ 
			CUtilities.WriteLog( excError );
		}
		return eleSearchElement;
	}
	
}