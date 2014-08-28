import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class CXMLDocument
{
	
	private Document m_docWriterDocument = null;
	private DocumentBuilder m_dbDocumentBuilder = null;
	
	// ----------------------------------------------------------------------
	// Name: CXMLDocument
	// Abstract: Class constructor
	// ----------------------------------------------------------------------
	public CXMLDocument( )
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			m_dbDocumentBuilder = factory.newDocumentBuilder();
	        m_docWriterDocument = m_dbDocumentBuilder.newDocument();
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}

	// ----------------------------------------------------------------------
	// Name: AddRootNode
	// Abstract: Adds a new root node with the specified name
	// ----------------------------------------------------------------------
	public Element AddRootNode(String strNodeTag)
	{
		Element eleNewElement = null;
		try
		{
			eleNewElement = m_docWriterDocument.createElement(strNodeTag);
			m_docWriterDocument.appendChild(eleNewElement);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return eleNewElement;
	}
	
	// ----------------------------------------------------------------------
	// Name: AddChildNode
	// Abstract: Adds a new child node with the specified name
	// ----------------------------------------------------------------------
	public Element AddChildNode(String strNodeTag, Element eleParent)
	{
		Element eleNewElement = null;
		try
		{
			eleNewElement = m_docWriterDocument.createElement(strNodeTag);
			eleParent.appendChild(eleNewElement);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return eleNewElement;
	}

	// ----------------------------------------------------------------------
	// Name: AddChildNodeWithAttribute
	// Abstract: Adds a new node with the specified attribute attached
	// ----------------------------------------------------------------------
	public Element AddChildNodeWithAttribute(String strNodeTag, Element eleParent, String strAttributeName, String strAttributeValue)
	{
		Element eleNewElement = null;
		try
		{
			eleNewElement = AddChildNode( strNodeTag, eleParent );
			AddAttributeToNode( eleNewElement, strAttributeName, strAttributeValue );
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
		return eleNewElement;
	}

	// ----------------------------------------------------------------------
	// Name: AddAttributeToNode
	// Abstract: Adds an attribute to a node
	// ----------------------------------------------------------------------
	public void AddAttributeToNode(Element eleElement, String strAttributeName, String strAttributeValue)
	{
		try
		{
			eleElement.setAttribute(strAttributeName, strAttributeValue);
		}
		catch (Exception excError)
		{
			CUtilities.WriteLog(excError);
		}
	}
	
	// ----------------------------------------------------------------------
	// Name: SaveXMLToDisk
	// Abstract: Saves the in-memory XML to a file
	// ----------------------------------------------------------------------
	public void SaveXMLToDisk(String strFilePath)
	{
		try
		{
			DOMSource domSource = new DOMSource( m_docWriterDocument );
			PrintStream psStream = new PrintStream( strFilePath );
			StreamResult swResult = new StreamResult( psStream );
			TransformerFactory tfTransformerFactory = TransformerFactory.newInstance();
			Transformer tfmTransformer = tfTransformerFactory.newTransformer();
			tfmTransformer.transform(domSource, swResult);
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
				m_docWriterDocument = m_dbDocumentBuilder.parse( filXMLDocument );
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
			if ( m_docWriterDocument != null )
			{
				eleRootElement = m_docWriterDocument.getDocumentElement( );
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
					if ( eleSearchElement != null )
						break;
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
