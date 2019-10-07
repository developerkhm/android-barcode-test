package com.syruptable.soda.barcode.response;

import com.google.gson.annotations.SerializedName;

public class InputResponse {

    @SerializedName("result")
    private Result result;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public class Result {

        @SerializedName("txid")
        private String txid;

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public String getTxid() {
            return txid;
        }

        @Override
        public String toString() {
            return
                    "Result{" +
                            "txid = '" + txid + '\'' +
                            "}";
        }
    }

    @Override
    public String toString() {
        return
                "InputResponse{" +
                        "result = '" + result + '\'' +
                        ",code = '" + code + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}