package novoda.clag.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import novoda.clag.converter.Converter;
import novoda.clag.introspector.Introspector;
import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;
import novoda.clag.provider.ContentProvider;
import novoda.clag.util.Configurable;

import org.apache.log4j.Logger;

/**
 * 
 * @author luigi.agosti
 */
public class DataController extends HttpServlet implements Configurable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DataController.class);

	private static final String CONTENT_TYPE = "text/plain";
	
	private static final String COMMA = ",";
	
	private RequestReader requestReader;
	
	private ContentProvider contentProvider;
	
	private Converter converter;
	
	public interface InitParameters {
		String CONVERTER = "converter";
		String CONTENT_PROVICER = "provider";
		String INTROSPECTOR = "introspector";
		String CONTENT_CLASSES = "contentClasses";
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.debug("Data Provider Controller : initialization");
		String converterParam = config.getInitParameter(InitParameters.CONVERTER);
		String contentProviderParam = config.getInitParameter(InitParameters.CONTENT_PROVICER);
		String introspectorParam = config.getInitParameter(InitParameters.INTROSPECTOR);
		String contentClassesParam = config.getInitParameter(InitParameters.CONTENT_CLASSES);
		logger.debug("converter : " + converterParam);
		logger.debug("provicer : " + contentProviderParam);
		logger.debug("introspector : " + introspectorParam);
		logger.debug("contentClasses : " + contentClassesParam);
		try {
			converter = (Converter)Class.forName(converterParam).newInstance();
			contentProvider = (ContentProvider)Class.forName(contentProviderParam).newInstance();
			contentProvider.setIntrospector((Introspector)Class.forName(introspectorParam).newInstance());
			List<String> classes = Arrays.asList(contentClassesParam.split(COMMA));
			for(String clazz : classes) {
				logger.debug("adding class to content provider : " + clazz);
				contentProvider.add(Class.forName(clazz));
			}
			isConfigured();
		} catch (Exception e) {
			logger.error("Problem in the initialization, see the following stack trace : ", e);
		}
	}
	
	@Override
	public void isConfigured() {
		contentProvider.isConfigured();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp);
	}


	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Executing request for : " + req.getRequestURI());		
		requestReader = new RequestReader(req);
		String result = null;
		logger.debug("Identifying the request method and calling the content provider");
		if(requestReader.isQuery()) {
			result = query(requestReader);
		} else if(requestReader.isSchema()) {
			result = schema(requestReader);
		} else {
			throw new RuntimeException("Request type not implemented. Must be query or schema for now!");
		}
		logger.debug("request executed, sending back the result");
		resp.setContentType(CONTENT_TYPE);
        resp.setContentLength(result.length());
        PrintWriter out = resp.getWriter();
        out.println(result);
        out.close();
        out.flush();
	}

	protected String schema(RequestReader rw) {
		Entity mds = contentProvider.schema(rw.getName());
		return converter.convert(mds);
	}

	protected String query(RequestReader rw) {
		Entity mds = contentProvider.schema(rw.getName());
		Cursor cursor = contentProvider.query(rw.getName(), rw.getProjection(), rw.getSelection(), rw.getSelectionArgs(), rw.getSortOrder(), mds);
		return converter.convert(cursor, mds);
	}

}
