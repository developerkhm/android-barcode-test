package com.syruptable.soda.barcode;

import com.syruptable.soda.barcode.response.DeleteResponse;
import com.syruptable.soda.barcode.response.InfoResponse;
import com.syruptable.soda.barcode.response.InputResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("pos/input")
    @FormUrlEncoded
    Observable<InputResponse> input(
            @Header("API-KEY") String apikey,
            @Field("code") String code,
            @Field("store_key") String storeKey,
            @Field("store_name") String storeName,
            @Field("price") String price,
            @Field("product_name") String productName
    );

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("pos/info")
    @FormUrlEncoded
    Observable<InfoResponse> info(
            @Field("txid") String txid
    );

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("pos/delete")
    @FormUrlEncoded
    Observable<DeleteResponse> delete(
            @Field("txid") String txid
    );
}
