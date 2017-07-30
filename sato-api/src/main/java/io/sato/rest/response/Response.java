package io.sato.rest.response;

/**
 * Created by Can A MOGOL on 12.07.2017
 */
public class Response<T> {

    private long traceId;

    private int status;

    private T data;

    private String message;

    private Response() {
    }

    public long getTraceId() {
        return traceId;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder<T> {

        private long traceId;

        private int status;

        private T data;

        private String message;

        public Builder<T> traceId(long traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Response<T> build() {
            Response<T> response = new Response<>();
            response.traceId = traceId;
            response.status = status;
            response.data = data;
            response.message = message;
            return response;
        }
    }

}
