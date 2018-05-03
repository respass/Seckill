package scu.zpf.seckill.exception;

import scu.zpf.seckill.result.CodeMessage;

public class GlobalException extends RuntimeException {

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        super(codeMessage.toString());
        this.codeMessage = codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
