import java.util.Arrays;

enum MethodsHTTP {
    GET, POST, DELETE, OPTIONS, PUT, HEAD, PATCH, TRACE, CONNECT;
    private String code;

    public static MethodsHTTP method(String x) {
        MethodsHTTP ret = null;
        for (MethodsHTTP code : MethodsHTTP.values()) {
            if (code.getCode() == x)
                ret = code;
        }
        return ret;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}