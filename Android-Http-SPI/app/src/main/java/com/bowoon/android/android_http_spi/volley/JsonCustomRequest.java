package com.bowoon.android.android_http_spi.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.bowoon.android.android_http_spi.common.HttpOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Null on 2018-01-29.
 */

public class JsonCustomRequest extends JsonRequest<JSONObject> {
    private HttpOption option;

    public JsonCustomRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);

        this.option = new HttpOption();
    }

    public JsonCustomRequest(int method, String url, HttpOption option, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);

        this.option = option;
    }

    @Override
    public String getBodyContentType() {
        Log.i("getBodyContentType", option.getBodyContentType() + "");
        if (option.getBodyContentType() != null) {
            Log.i("getBodyContentType", option.getBodyContentType());
            return option.getBodyContentType();
        }
        return "application/json";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (option.getHeaders() != null) {
            Log.i("getHeaders", "getHeaders");
            return option.getHeaders();
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (option.getParams() != null) {
            Log.i("getParams", "getParams");
            return option.getParams();
        }
        return super.getParams();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
