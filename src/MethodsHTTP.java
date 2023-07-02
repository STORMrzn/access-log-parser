import java.util.Arrays;

enum MethodsHTTP {
    GET("GET"),POST("POST"),DELETE("DELETE"),OPTIONS("OPTIONS"),PUT("PUT"),HEAD("HEAD"),PATCH("PATCH"),TRACE("TRACE"),CONNECT("CONNECT");
    private String method;

    MethodsHTTP(String method) {
        this.method = method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MethodsHTTP getMethod() {
        return new MethodsHTTP(method); //тут как-то создать объект енама по пришедшему стрингу
    }
}
