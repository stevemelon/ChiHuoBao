package com.example.dell.chihuobao.util;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.chihuobao.activity.FoodMenuAddNewFoodActivity;
import com.example.dell.chihuobao.activity.MainActivity;
import com.example.dell.chihuobao.bean.Food;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 2016/3/15.
 */
public class ServerUtil {
    private final static String ADD_FOOD = "/chb/shop/addProduct.do";
    private final static String DELETE_FOOD ="/chb/shop/deleteProduct.do";
    private final static String UPDATE_FOOD = "/chb/shop/updateProduct.do";
    private final static String QUERY_PRODUCT= "/chb/shop/queryProduct.do";
    private final static String QUERY_CATEGORY= "/chb/shop/queryCategory.do";
    private final static String URL = "http://10.6.12.44:8080";

    private String resultProduct =new String();
    private String resultCategory = new String();
    public ServerUtil() {
        super();
    }

    /**
     * 查找食物种类
     */
    public String queryCategory(String shopId){
        RequestParams params  = new RequestParams(URL+QUERY_CATEGORY);
        params.addBodyParameter("shopid",shopId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultCategory = result;
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return resultCategory;
    }


    /**
     * 新增食品，将参数放入hashmap
     */
    public void addFood(HashMap hashMap){
        RequestParams params = new RequestParams(URL+ADD_FOOD);
        params.addBodyParameter("shopid",hashMap.get("shopid"),null);
        params.addBodyParameter("categoryid", hashMap.get("categoryid"), null);
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("storenumber", hashMap.get("storenumber"), null);
        params.addBodyParameter("price", hashMap.get("price"), null);
        params.addBodyParameter("description", hashMap.get("description"), null);
        params.addBodyParameter("inserttime", hashMap.get("inserttime"), null);
        params.addBodyParameter("salescount", hashMap.get("salescount"),null);
        params.addBodyParameter("status", hashMap.get("statu"), null);
        params.addBodyParameter("achievemoney", hashMap.get("achievemoney"), null);
        params.addBodyParameter("reducemoney", hashMap.get("reducemoney"), null);
        params.addBodyParameter("rank", hashMap.get("rank"), null);
        params.addBodyParameter("photodetail",hashMap.get("photodetail"),null);
        params.addBodyParameter("photo",hashMap.get("photo"),null);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
                Toast.makeText(x.app(), "上传成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "上传失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public void updateFood(HashMap hashMap){
        RequestParams params = new RequestParams(URL+UPDATE_FOOD);
        params.addBodyParameter("id",hashMap.get("id"),null);
        params.addBodyParameter("shopid",hashMap.get("shopid"),null);
        params.addBodyParameter("categoryid", hashMap.get("categoryid"), null);
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("storenumber", hashMap.get("storenumber"), null);
        params.addBodyParameter("price", hashMap.get("price"), null);
        params.addBodyParameter("description", hashMap.get("description"), null);
        params.addBodyParameter("inserttime", hashMap.get("inserttime"), null);

        params.addBodyParameter("salescount", hashMap.get("salescount"),null);
        params.addBodyParameter("status", hashMap.get("statu"), null);
        params.addBodyParameter("achievemoney", hashMap.get("achievemoney"), null);
        params.addBodyParameter("reducemoney", hashMap.get("reducemoney"), null);
        params.addBodyParameter("rank", hashMap.get("rank"), null);
        params.addBodyParameter("photodetail",hashMap.get("photodetail"),null);
        params.addBodyParameter("photo",hashMap.get("photo"),null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
                Toast.makeText(x.app(), "更新成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "更新失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public void deleteFood(String id){
        RequestParams params = new RequestParams(URL+DELETE_FOOD);
        params.addBodyParameter("id",id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
                Toast.makeText(x.app(), "删除成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "删除失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public String queryProduct(String shopId,String categoryId){
        RequestParams params = new RequestParams(URL+QUERY_PRODUCT);
        params.addBodyParameter("shopid",shopId);
        params.addBodyParameter("categoryid", categoryId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultProduct = result;
                Log.d("result", result);
                Toast.makeText(x.app(), "查询成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "查询失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return resultProduct;

    }

    public String queryCategoryTest(String url){
        RequestParams params  = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultCategory = result;
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return resultCategory;
    }

    /*public ArrayList parseData(String result,String tag){
        Gson gson  = new Gson();
        switch (tag){
            case "category":
                ArrayList<FoodCategory> foodCategoryArrayList = gson.fromJson(result,new TypeToken<ArrayList<FoodCategory>>() {}.getType());
                return foodCategoryArrayList;

            case "product":
                ArrayList<Food> foodArrayList = gson.fromJson(result,new TypeToken<ArrayList<Food>>() {}.getType());
                return foodArrayList;
            default:
                break;
        }




    }*/

    public String queryProductTest(String url){
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultProduct = result;
                Log.d("result", result);
                Toast.makeText(x.app(), "查询成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "查询失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return resultProduct;

    }


}
