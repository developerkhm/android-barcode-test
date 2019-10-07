package com.syruptable.soda.barcode.response;

import com.google.gson.annotations.SerializedName;

public class DeleteResponse{

	@SerializedName("result")
	private Result result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public class Result{

		@SerializedName("deleted_time")
		private String deletedTime;

		public void setDeletedTime(String deletedTime){
			this.deletedTime = deletedTime;
		}

		public String getDeletedTime(){
			return deletedTime;
		}

		@Override
		public String toString(){
			return
					"Result{" +
							"deleted_time = '" + deletedTime + '\'' +
							"}";
		}
	}

	@Override
 	public String toString(){
		return 
			"DeleteResponse{" + 
			"result = '" + result + '\'' + 
			",code = '" + code + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}