package org.dieschnittstelle.ess.wsv.interpreter;


import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;

import org.dieschnittstelle.ess.utils.Http;
import org.dieschnittstelle.ess.wsv.interpreter.json.JSONObjectSerialiser;

import static org.dieschnittstelle.ess.utils.Utils.*;


/*
 * TODO WSV1: implement this class such that the crud operations declared on ITouchpointCRUDService in .ess.wsv can be successfully called from the class AccessRESTServiceWithInterpreter in the .esa.wsv.client project
 */
public class JAXRSClientInterpreter implements InvocationHandler {

    // use a logger
    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(JAXRSClientInterpreter.class);

    // declare a baseurl
    private String baseurl;

    // declare a common path segment
    private String commonPath;

    // use our own implementation JSONObjectSerialiser
    private JSONObjectSerialiser jsonSerialiser = new JSONObjectSerialiser();

    // use an attribute that holds the serviceInterface (useful, e.g. for providing a toString() method)
    private Class serviceInterface;

    // use a constructor that takes an annotated service interface and a baseurl. the implementation should read out the path annotation, we assume we produce and consume json, i.e. the @Produces and @Consumes annotations will not be considered here
    public JAXRSClientInterpreter(Class serviceInterface, String baseurl) {
        this.serviceInterface = serviceInterface;
        this.baseurl = baseurl;
        
        if (serviceInterface.isAnnotationPresent(Path.class)) {
            Path pathAnnotation = (Path) serviceInterface.getAnnotation(Path.class);
            this.commonPath = pathAnnotation.value();
        } else {
            throw new RuntimeException("Cannot construct InvocationHandler - JAX-RS root resources require top-level path annotation");
        }
        
        logger.info("<constructor>: " + serviceInterface + " / " + baseurl + " / " + commonPath);
    }

    // TODO: implement this method interpreting jax-rs annotations on the meth argument
    @Override
    public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable {
        
        if ("toString".equals(meth.getName())) {
            return "JAX-RS Client Proxy for " + serviceInterface.getSimpleName();
        }
        
        HttpClient client = Http.createSyncClient();
        
        String requestUrl = baseurl + commonPath;
        
        if (meth.isAnnotationPresent(Path.class)) {
            Path methodPath = meth.getAnnotation(Path.class);
            requestUrl += methodPath.value();
        }
        
        // a value that needs to be sent via the http request body
        Object requestBodyData = null;
        
        // TODO: check whether we have method arguments - only consider pathparam annotations (if any) on the first argument here - if no args are passed, the value of args is null! if no pathparam annotation is present assume that the argument value is passed via the body of the http request
        if (args != null && args.length > 0) {
            Annotation[] firstParamAnnotations = meth.getParameterAnnotations()[0];
            boolean hasPathParam = false;
            
            for (Annotation annotation : firstParamAnnotations) {
                if (annotation instanceof PathParam) {
                    PathParam pathParam = (PathParam) annotation;
                    String paramName = pathParam.value();
                    String paramValue = String.valueOf(args[0]);
                    requestUrl = requestUrl.replace("{" + paramName + "}", paramValue);
                    hasPathParam = true;
                    break;
                }
            }
            
            if (hasPathParam && args.length > 1) {
                requestBodyData = args[1];
            } else if (!hasPathParam) {
                requestBodyData = args[0];
            }
        }
        
        // declare a HttpUriRequest variable
        HttpUriRequest request = null;
        
        if (meth.isAnnotationPresent(GET.class)) {
            request = new HttpGet(requestUrl);
        } 
        else if (meth.isAnnotationPresent(POST.class)) {
            request = new HttpPost(requestUrl);
        }
        else if (meth.isAnnotationPresent(PUT.class)) {
            request = new HttpPut(requestUrl);
        }
        else if (meth.isAnnotationPresent(DELETE.class)) {
            request = new HttpDelete(requestUrl);
        }
        else {
            throw new UnsupportedOperationException("No supported HTTP method annotation found on method: " + meth.getName());
        }
        
        request.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        
        if (requestBodyData != null && request instanceof HttpEntityEnclosingRequest) {
            ByteArrayOutputStream requestBodyStream = new ByteArrayOutputStream();
            jsonSerialiser.writeObject(requestBodyData, requestBodyStream);
            
            ByteArrayEntity requestEntity = new ByteArrayEntity(requestBodyStream.toByteArray());
            ((HttpEntityEnclosingRequest) request).setEntity(requestEntity);
            
            request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        }
        
        HttpResponse response = client.execute(request);

        logger.info("invoke(): received response: " + response);

        // check the response code
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Type returnType = meth.getGenericReturnType();

            if (returnType == void.class || returnType == Void.class) {
                return null;
            }
            else if (returnType == boolean.class || returnType == Boolean.class) {
                return true;
            }
            else {
                Object returnValue = jsonSerialiser.readObject(
                    response.getEntity().getContent(), 
                    returnType
                );
                return returnValue;
            }
        }
        else {
            throw new RuntimeException("Got unexpected status from server: " + response.getStatusLine());
        }
    }
}
