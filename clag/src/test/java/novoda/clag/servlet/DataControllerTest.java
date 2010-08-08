package novoda.clag.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Hashtable;

import novoda.clag.introspector.jdo.JdoIntrospector;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.mock.MockContentProvider;
import novoda.clag.mock.MockConverter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author luigi.agosti
 */
public class DataControllerTest {

	private static ServletRunner sr;
	private WebRequest request;
	private ServletUnitClient sc;

	@BeforeClass
	public static void initialSetUp() {
		Hashtable<String,String> initParameters = new Hashtable<String,String>();
		initParameters.put(DataController.InitParameters.CONVERTER, MockConverter.class.getName());
		initParameters.put(DataController.InitParameters.CONTENT_PROVICER, MockContentProvider.class.getName());
		initParameters.put(DataController.InitParameters.INTROSPECTOR, JdoIntrospector.class.getName());
		initParameters.put(DataController.InitParameters.CONTENT_CLASSES, Story.class.getName());
		
		sr = new ServletRunner();
		sr.registerServlet("dataController", DataController.class
				.getName(), initParameters);
	}

	@Before
	public void setUp() {
		sc = sr.newClient();
		request = new PostMethodWebRequest(
				"http://test.meterware.com/dataController");
	}

	@Test
	public void shouldRespondOnQueryMethod() throws IOException, SAXException {
		request.setParameter(RequestReader.Parameter.QUERY, "true");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test
	public void shouldRespondOnSchemaMethod() throws IOException, SAXException {
		request.setParameter(RequestReader.Parameter.SCHEMA, "true");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert mds", response.getText());
	}

	@Test
	public void shouldRespondOnQueryForWhenNameIsSpecified() throws IOException, SAXException {
		request.setParameter(RequestReader.Parameter.QUERY, "true");
		request.setParameter(RequestReader.Parameter.NAME, Story.class.getName());
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test
	public void shouldRespondOnSchemaGiveMeAProperResult() throws IOException, SAXException {
		request.setParameter(RequestReader.Parameter.QUERY, "true");
		request.setParameter(RequestReader.Parameter.NAME, Story.class.getName());
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test
	public void shouldLoadTheClassesAndPassThemToTheIntrospector() throws IOException, SAXException {
		request.setParameter(RequestReader.Parameter.QUERY, "true");
		request.setParameter(RequestReader.Parameter.NAME, Story.class.getName());
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

}
