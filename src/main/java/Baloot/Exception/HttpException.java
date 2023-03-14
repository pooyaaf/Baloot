package Baloot.Exception;

public class HttpException extends Exception{
    private Integer httpStatusCode = 400;

    public Integer getStatus() {
        return httpStatusCode;
    }

    public void setStatus(Integer code) {
        httpStatusCode = code;
    }
}
