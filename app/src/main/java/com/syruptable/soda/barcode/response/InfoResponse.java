package com.syruptable.soda.barcode.response;

import com.google.gson.annotations.SerializedName;

public class InfoResponse{

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

		@SerializedName("created_time")
		private String createdTime;

		@SerializedName("code")
		private String code;

		@SerializedName("price")
		private String price;

		@SerializedName("store_name")
		private String storeName;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("store_key")
		private String storeKey;

		public void setCreatedTime(String createdTime){
			this.createdTime = createdTime;
		}

		public String getCreatedTime(){
			return createdTime;
		}

		public void setCode(String code){
			this.code = code;
		}

		public String getCode(){
			return code;
		}

		public void setPrice(String price){
			this.price = price;
		}

		public String getPrice(){
			return price;
		}

		public void setStoreName(String storeName){
			this.storeName = storeName;
		}

		public String getStoreName(){
			return storeName;
		}

		public void setProductName(String productName){
			this.productName = productName;
		}

		public String getProductName(){
			return productName;
		}

		public void setStoreKey(String storeKey){
			this.storeKey = storeKey;
		}

		public String getStoreKey(){
			return storeKey;
		}

		@Override
		public String toString(){
			return
					"Result{" +
							"created_time = '" + createdTime + '\'' +
							",code = '" + code + '\'' +
							",price = '" + price + '\'' +
							",store_name = '" + storeName + '\'' +
							",product_name = '" + productName + '\'' +
							",store_key = '" + storeKey + '\'' +
							"}";
		}
	}


	@Override
 	public String toString(){
		return 
			"InfoResponse{" + 
			"result = '" + result + '\'' + 
			",code = '" + code + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}