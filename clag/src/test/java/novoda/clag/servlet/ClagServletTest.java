package novoda.clag.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Hashtable;

import novoda.clag.introspector.jdo.JdoIntrospector;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.mock.MockProvider;
import novoda.clag.mock.MockConverter;
import novoda.clag.servlet.config.Configurator;
import novoda.clag.servlet.context.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author luigi.agosti
 */
public class ClagServletTest {

	private static ServletRunner sr;
	private WebRequest request;
	private ServletUnitClient sc;

	@BeforeClass
	public static void initialSetUp() {
		Hashtable<String, String> initParameters = new Hashtable<String, String>();
		initParameters.put(Configurator.InitParameters.CONVERTER,
				MockConverter.class.getName());
		initParameters.put(Configurator.InitParameters.CONTENT_PROVICER,
				MockProvider.class.getName());
		initParameters.put(Configurator.InitParameters.INTROSPECTOR,
				JdoIntrospector.class.getName());
		initParameters.put(Configurator.InitParameters.CONTENT_CLASSES,
				Story.class.getName());

		sr = new ServletRunner();
		sr.registerServlet("data/*", ClagServlet.class.getName(),
				initParameters);
	}

	@Before
	public void setUp() {
		sc = sr.newClient();
		request = new GetMethodWebRequest(
				"http://test.meterware.com/data");
	}

	@Test
	public void shouldRespondOnQueryMethod() throws IOException, SAXException {
		request = new GetMethodWebRequest(
			"http://test.meterware.com/data/Story");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test
	public void shouldRespondOnQueryWithoutValue() throws IOException, SAXException {
		request = new GetMethodWebRequest(
		"http://test.meterware.com/data/Story?query");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test
	public void shouldRespondOnQueryIfSpecifiedMethodWithValue() throws IOException, SAXException {
		request = new GetMethodWebRequest(
		"http://test.meterware.com/data/Story");
		request.setParameter("query", new String[] { "true" });
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert cursor", response.getText());
	}

	@Test(expected = RuntimeException.class)
	public void shouldRespondOnQueryIfSpecifiedMethodWithFalse() throws IOException, SAXException {
		request = new GetMethodWebRequest(
		"http://test.meterware.com/data/Story");
		request.setParameter("query", new String[] { "false" });
		sc.getResponse(request);
	}

	@Test
	public void shouldRespondOnSchemaMethod() throws IOException, SAXException {
		request = new GetMethodWebRequest(
			"http://test.meterware.com/data/Story");
		request.setParameter("schema", new String[] { "true" });
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert mds", response.getText());
	}
	
	@Test
	public void shouldRespondOnSchemaMethodWithValue() throws IOException, SAXException {
		request = new GetMethodWebRequest(
		"http://test.meterware.com/data/Story?schema=true");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert mds", response.getText());
	}

	@Test
	public void shouldRespondOnSchemaMethodWithoutValue() throws IOException, SAXException {
		request = new GetMethodWebRequest(
		"http://test.meterware.com/data/Story?schema");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		assertEquals("content type", "text/plain", response.getContentType());
		assertEquals("requested resource", "convert mds", response.getText());
	}

}
