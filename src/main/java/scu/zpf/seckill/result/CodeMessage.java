package scu.zpf.seckill.result;
public class CodeMessage {



    private int code;
    private String message;

    public static CodeMessage SUCCESS = new CodeMessage(0, "success");

    //Global 错误码 8XX
    public static CodeMessage SERVER_ERROR = new CodeMessage(888, "服务端异常");
    public static CodeMessage BIND_ERROR = new CodeMessage(889, "参数校验异常: %s");

    //Login 错误码 9XX
    public static CodeMessage PHONE_NOT_REGISTER = new CodeMessage( 988, "手机号未注册");
    public static CodeMessage PHONE_EMPTY = new CodeMessage(989, "手机号不能为空");
    public static CodeMessage PASSWORD_EMPTY = new CodeMessage(990, "密码不能为空");
    public static CodeMessage PASSWORD_ERROR = new CodeMessage(991, "密码错误");



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

    public CodeMessage fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.message, args);
        return new CodeMessage(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + message + "]";
    }



}
