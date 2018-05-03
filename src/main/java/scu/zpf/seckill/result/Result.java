package scu.zpf.seckill.result;

public class Result<T> {


    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMessage codeMessage) {
        return new Result<>(codeMessage);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(CodeMessage codeMessage) {
        if (codeMessage != null) {
            this.code = codeMessage.getCode();
            this.message = codeMessage.getMessage();
        }
    }


}
