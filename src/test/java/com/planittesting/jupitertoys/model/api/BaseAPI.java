package com.planittesting.jupitertoys.model.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;

public abstract class BaseAPI<T extends BaseAPI<T>> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected HttpUrl baseURL;
    protected OkHttpClient client;
    protected MediaType mediaType;
    protected Response rawResponse;
    private String responseBody;
    private HttpUrl.Builder httpBuilder;
    private Map<String, String> headers;

    public BaseAPI(HttpUrl baseURL) {
        this.client = new OkHttpClient();
        this.httpBuilder=baseURL.newBuilder();
        this.headers = Map.of();
    }

    public String getResponseBody() throws IOException {
        if(responseBody == null) responseBody = Optional.ofNullable(rawResponse.body()).get().string();
        return responseBody;
    }

    public Response getRawResponse() {
        return this.rawResponse;
    }

    public String get()
            throws IOException {
        return run(getBuilder().get().build());
    }

    public String post(String body)
            throws IOException {
        return run(getBuilder().post(RequestBody.create(mediaType, body)).build());
    }

    public String put(String body)
            throws IOException {
        return run(getBuilder().put(RequestBody.create(mediaType, body)).build());
    }

    public String delete(String body)
            throws IOException {
        return run(getBuilder().delete(RequestBody.create(mediaType, body)).build());
    }

    public String delete()
            throws IOException {
        return run(getBuilder().delete().build());
    }

    @SuppressWarnings("unchecked")
    public T withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public T withoutParams() {
        updateHttpBuilder(Arrays.asList(), Collections.emptyMap());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public T withPathSegments(List<String> pathSegments) {
        updateHttpBuilder(pathSegments, Collections.emptyMap());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public T withQueryParams(Map<String, String> queryParams) {
        updateHttpBuilder(Arrays.asList(), queryParams);
        return (T)this;
    }

    private Builder getBuilder() {
        var builder = new Request.Builder().url(httpBuilder.build());
        headers.forEach((k, v) -> builder.addHeader(k, v));
        return builder;
    }

    private void updateHttpBuilder(List<String> positionParams, Map<String, String> queryParams) {        
        positionParams.forEach(p -> httpBuilder.addPathSegment(p));
        queryParams.forEach((k,v) -> httpBuilder.addQueryParameter(k,v));
    }

    protected void setResourceURL(String resourceURL) {
        if(resourceURL.startsWith("/")) resourceURL = resourceURL.replaceFirst("\\/", "");
        Stream.of(resourceURL.split("/")).forEach(s -> this.httpBuilder.addPathSegment(s));
    }

    protected void setMediaType(String type) {
        this.mediaType = MediaType.get(type);
    }

    protected void setResponseBody(String body) {
        logger.info("Response body: {}",body);
        this.responseBody = body;
    }

    private String run(Request request) throws IOException {
        logger.info("Seding {} request to {} with headers <{}>", request.method(), request.url().toString(), request.headers());
        rawResponse = client.newCall(request).execute();
        setResponseBody(Optional.ofNullable(rawResponse.body()).get().string());
        return getResponseBody();
    }
}

