package com.gson_volley.pana.allan.book.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gson_volley.pana.allan.book.extras.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by allan on 01/07/15.
 */

public class GsonRequest<T> extends Request<T> {
    private  Gson gson = new Gson();
    private  Class<T> clazz;
    private  Map<String, String> headers;
    private  Response.Listener<T> listener;





    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }


    public GsonRequest( String url, Class<T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseIgnoreCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }



    //================


}