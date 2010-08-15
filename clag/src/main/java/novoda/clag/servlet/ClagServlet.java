package novoda.clag.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import novoda.clag.servlet.action.Query;
import novoda.clag.servlet.action.Schema;
import novoda.clag.servlet.config.Configurator;
import novoda.clag.servlet.config.ServletConfigurator;
import novoda.clag.servlet.context.Context;

import org.apache.log4j.Logger;

/**
 * Restful servlet that handle all the requests.
 * It then redirect to the correct action, collect the result and 
 * send it back through the response.
 * 
 * @author luigi.agosti
 */
public class ClagServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ClagServlet.class);

	private static final String CONTENT_TYPE = "text/plain";

	private Configurator configurator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		configurator = new ServletConfigurator(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Executing get request for : " + req.getRequestURI());
		Context context = configurator.getContext();
		context.setRequest(req);
		String result = null;
		if (context.isSchema()) {
			logger.debug("Executing schema");
			result = new Schema().execute(context);
		} else if (context.isQuery()) {
			logger.debug("Executing query");
			result = new Query().execute(context);
		} else {
			throw new RuntimeException("Type not implemented!"
					+ req.getRequestURI());
		}
		logger.debug("request executed, sending back the result");
		resp.setContentType(CONTENT_TYPE);
		resp.setContentLength(result.length());
		PrintWriter out = resp.getWriter();
		out.println(result);
		out.close();
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Executing post request for : " + req.getRequestURI());
		// TODO create
		throw new RuntimeException("Post not implemented yet!");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Executing delete request for : " + req.getRequestURI());
		// TODO delete
		throw new RuntimeException("Delete not implemented yet!");
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Executing put request for : " + req.getRequestURI());
		// TODO update or replace
		throw new RuntimeException("Put not implemented yet!");
	}

}
