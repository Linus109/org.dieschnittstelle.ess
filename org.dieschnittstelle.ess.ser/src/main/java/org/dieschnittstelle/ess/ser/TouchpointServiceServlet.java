package org.dieschnittstelle.ess.ser;

import java.io.ObjectOutputStream;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.dieschnittstelle.ess.utils.Utils.*;

import org.apache.logging.log4j.Logger;

public class TouchpointServiceServlet extends HttpServlet {

	protected static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(TouchpointServiceServlet.class);

	public TouchpointServiceServlet() {
		show("TouchpointServiceServlet: constructor invoked\n");
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("doGet()");

		// we assume here that GET will only be used to return the list of all
		// touchpoints

		// obtain the executor for reading out the touchpoints
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			response.setStatus(HttpServletResponse.SC_OK);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			// write the object
			oos.writeObject(exec.readAllTouchpoints());
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}

	/*
	 * TODO: SER3 server-side implementation of createNewTouchpoint
	 */
	/*
	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {

		// assume POST will only be used for touchpoint creation, i.e. there is
		// no need to check the uri that has been used

		// obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute

		try {
			// create an ObjectInputStream from the request's input stream
		
			// read an AbstractTouchpoint object from the stream
		
			// call the create method on the executor and take its return value
		
			// set the response status as successful, using the appropriate
			// constant from HttpServletResponse
		
			// then write the object to the response's output stream, using a
			// wrapping ObjectOutputStream
		
			// ... and write the object to the stream
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	*/

	/*
	 * TODO: SER4 server-side implementation of deleteTouchpoint
	 */

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
	    logger.info("doDelete()");

	    try {
	        // Get the executor
	        TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
	                .getAttribute("touchpointCRUD");

	        // Extract id from URL: /touchpoints/{id}
	        String pathInfo = request.getPathInfo(); // e.g. "/5"
	        if (pathInfo == null || pathInfo.length() <= 1) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }
	        String idStr = pathInfo.substring(1); // remove leading slash
	        long id = Long.parseLong(idStr);

	        // Delete the touchpoint
	        boolean deleted = exec.deleteTouchpoint(id);

	        // Set response status
	        if (deleted) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        }
	    } catch (Exception e) {
	        logger.error("Exception in doDelete: " + e, e);
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}
}
