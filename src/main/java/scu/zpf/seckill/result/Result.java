package scu.zpf.seckill.result;

public class Result<T> {

    private int code;
    private String message;
    private T data;

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
