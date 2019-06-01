package com.mazhe.util;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * 返回结果封装实体
 * @author mazhe
 */
public class RestResponseEntity<T> extends ResponseEntity<RestResponseEntity.BodyWrapper<T>> {
    public static BodyBuilder status2(HttpStatus status) {

        return new RestResponseEntity.DefaultBuilder(status);
    }

    public static BodyBuilder status2(int status) {

        return status2(HttpStatus.valueOf(status));
    }

    public static BodyBuilder ok2() {

        return status2(HttpStatus.OK);
    }

    public static <T> RestResponseEntity<T> ok2(T body) {

        BodyBuilder builder = ok2();
        return builder.body(body);
    }

    public static BodyBuilder created2(URI location) {

        BodyBuilder builder = status2(HttpStatus.CREATED);
        return builder.location(location);
    }

    public static BodyBuilder accepted2() {

        return status2(HttpStatus.ACCEPTED);
    }

    public static HeadersBuilder<?> noContent2() {

        return status2(HttpStatus.NO_CONTENT);
    }

    public static BodyBuilder badRequest2() {

        return status2(HttpStatus.BAD_REQUEST);
    }

    public static HeadersBuilder<?> notFound2() {

        return status2(HttpStatus.NOT_FOUND);
    }

    public static BodyBuilder unprocessableEntity2() {

        return status2(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private final HttpStatus statusCode;

    public RestResponseEntity(HttpStatus statusCode) {

        super(statusCode);
        this.statusCode = statusCode;
    }

    public RestResponseEntity(
            T body,
            HttpStatus statusCode) {

        super(wrap(body), statusCode);
        this.statusCode = statusCode;
    }

    public RestResponseEntity(
            MultiValueMap<String, String> headers,
            HttpStatus statusCode) {

        super(headers, statusCode);
        this.statusCode = statusCode;
    }

    public RestResponseEntity(
            T body,
            MultiValueMap<String, String> headers,
            HttpStatus statusCode) {

        super(wrap(body), headers, statusCode);
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatus getStatusCode() {

        return this.statusCode;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        RestResponseEntity<?>
                otherEntity = (RestResponseEntity<?>) other;
        return ObjectUtils.nullSafeEquals(this.statusCode, otherEntity.statusCode);
    }

    @Override
    public int hashCode() {

        return (super.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.statusCode));
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("<");
        builder.append(this.statusCode.toString());
        builder.append(' ');
        builder.append(this.statusCode.getReasonPhrase());
        builder.append(',');
        T data = getBody().getData();
        HttpHeaders headers = getHeaders();
        if (data != null) {
            builder.append(data);
            if (headers != null) {
                builder.append(',');
            }
        }
        if (headers != null) {
            builder.append(headers);
        }
        builder.append('>');
        return builder.toString();
    }

    public interface HeadersBuilder<B extends HeadersBuilder<B>> {
        B header(
                String headerName,
                String... headerValues);

        B headers(HttpHeaders headers);

        B allow(HttpMethod... allowedMethods);

        B eTag(String eTag);

        B lastModified(long lastModified);

        B location(URI location);

        B cacheControl(CacheControl cacheControl);

        RestResponseEntity<Void> build();
    }


    public interface BodyBuilder extends
            HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        BodyBuilder contentType(MediaType contentType);

        <T> RestResponseEntity<T> body(T body);
    }


    private static class DefaultBuilder implements
            BodyBuilder {

        private final HttpStatus status;

        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(HttpStatus status) {

            this.status = status;
        }

        @Override
        public BodyBuilder header(
                String headerName,
                String... headerValues) {

            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(HttpHeaders headers) {

            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder allow(HttpMethod... allowedMethods) {

            this.headers.setAllow(new LinkedHashSet<HttpMethod>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {

            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {

            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder eTag(String eTag) {

            if (eTag != null) {
                if (!eTag.startsWith("\"") && !eTag.startsWith("W/\"")) {
                    eTag = "\"" + eTag;
                }
                if (!eTag.endsWith("\"")) {
                    eTag = eTag + "\"";
                }
            }
            this.headers.setETag(eTag);
            return this;
        }

        @Override
        public BodyBuilder lastModified(long date) {

            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder location(URI location) {

            this.headers.setLocation(location);
            return this;
        }

        @Override
        public BodyBuilder cacheControl(CacheControl cacheControl) {

            String ccValue = cacheControl.getHeaderValue();
            if (ccValue != null) {
                this.headers.setCacheControl(cacheControl.getHeaderValue());
            }
            return this;
        }

        @Override
        public RestResponseEntity<Void> build() {

            return new RestResponseEntity<Void>(null, this.headers, this.status);
        }

        @Override
        public <T> RestResponseEntity<T> body(T body) {

            return new RestResponseEntity<T>(body, this.headers, this.status);
        }
    }

    private static <T> BodyWrapper<T> wrap(T value) {

        return new BodyWrapper<>(value);
    }

    public static class BodyWrapper<T> implements RestResponseData<T> {
        public BodyWrapper(T value) {

            this.data = value;
        }

        private T data;

        @Override
        public T getData() {

            return data;
        }
    }
}
