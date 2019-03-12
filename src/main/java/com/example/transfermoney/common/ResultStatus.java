package com.example.transfermoney.common;

public enum ResultStatus {
	SUCCESS(100, "Success"),
	INIT_FAILED(-100, "Init accounts failed"),
	NOT_FOUND(-200, "Account not found"),
	SAME_ACCOUNT(-300, "The same accounts"),
	CONVERSATION_FORBIDDEN(-400, "Conversation not allowed"),
	NO_MONEY(-500, "Insufficient funds"),
	BAD_FORMAT(-600, "Bad input format"),
	JSON_ERROR(-700, "Json error"),
	TRANSFER_FAILED(-800, "Transfer failed");
	
	private int code;
	private String message;
	
	ResultStatus(int code, String message) {
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
}
