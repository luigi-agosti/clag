package novoda.clag.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

public class ETagFilter implements Filter {

	private static final Logger logger = Logger.getLogger(ETagFilter.class);
	
	private static final String MD5 = "MD5";
	
	private static final String ENCODING = "UTF-8";
	
	private static final String GET_METHOD = "get";

	private static final String ETAG_HEADER = "ETag";

	private static final String IF_MODIFIED_SINCE_HEADER = "If-Modified-Since";

	private static final String LAST_MODIFIED_HEADER = "Last-Modified";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if(!GET_METHOD.equalsIgnoreCase(req.getMethod())) {
			chain.doFilter(req, res);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ETagResponseWrapper wrappedResponse = new ETagResponseWrapper(res, baos);
		chain.doFilter(req, wrappedResponse);
		byte[] bytes = baos.toByteArray();

		String token = '"' + getMd5Digest(bytes) + '"';
		res.setHeader(ETAG_HEADER, token);
		String previousToken = req.getHeader(ETAG_HEADER);
		if (previousToken != null && previousToken.equals(token)) {
			logger.info("setting new headers for etag");
			res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
			res.setHeader(LAST_MODIFIED_HEADER, req
					.getHeader(IF_MODIFIED_SINCE_HEADER));
		} else {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			Date lastModified = cal.getTime();
			res.setDateHeader(LAST_MODIFIED_HEADER, lastModified.getTime());
			res.setContentLength(bytes.length);
			ServletOutputStream sos = res.getOutputStream();
			sos.write(bytes);
			sos.flush();
			sos.close();
		}
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {

	}

	private static String getMd5Digest(byte[] bytes) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(
					"MD5 cryptographic algorithm is not available.", e);
		}
		byte[] messageDigest = md.digest(bytes);
		BigInteger number = new BigInteger(1, messageDigest);
		StringBuffer sb = new StringBuffer('0');
		sb.append(number.toString(16));
		return sb.toString();
	}

	public class ETagResponseWrapper extends HttpServletResponseWrapper {

		private HttpServletResponse response = null;
		private ServletOutputStream stream = null;
		private PrintWriter writer = null;
		private OutputStream buffer = null;

		public ETagResponseWrapper(HttpServletResponse response,
				OutputStream buffer) {
			super(response);
			this.response = response;
			this.buffer = buffer;
		}

		@Override
		public void flushBuffer() throws IOException {
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (stream == null) {
				stream = new ETagResponseStream(response, buffer);
			}
			return stream;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (writer == null) {
				writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), ENCODING));
			}
			return writer;
		}

	}
	
	public class ETagResponseStream extends ServletOutputStream {

		private boolean closed = false;
        private OutputStream stream = null;

        public ETagResponseStream(HttpServletResponse response, OutputStream stream) throws IOException {
                super();
                this.stream = stream;
        }

        public void close() throws IOException {
                if (!closed) {
                        stream.close();
                        closed = true;
                }
        }

        public void flush() throws IOException {
                if (!closed) {
                        stream.flush();
                }
        }

        public void write(int b) throws IOException {
                if (!closed) {
                        stream.write((byte) b);
                }
        }

        public void write(byte b[], int off, int len) throws IOException {
                if (!closed) {
                        stream.write(b, off, len);
                }
        }

        public void write(byte b[]) throws IOException {
                write(b, 0, b.length);
        }

        public boolean closed() {
                return closed;
        }

        public void reset() {
                //noop
        }
	}

}
