package scu.zpf.seckill.result;

public class CodeMessage {



    private int code;
    private String message;

    public static CodeMessage SUCCESS = new CodeMessage(0, "success");
    public static CodeMessage SERVER_ERROR = new CodeMessage(888, "服务端异常");
    public static CodeMessage BIND_ERROR = new CodeMessage(889, "参数校验异常: %s");

    private CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

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

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + message + "]";
    }



}
