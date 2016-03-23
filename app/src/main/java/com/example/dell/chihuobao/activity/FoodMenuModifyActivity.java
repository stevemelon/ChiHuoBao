package com.example.dell.chihuobao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.Food;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.example.dell.chihuobao.util.AndroidUtil;
import com.example.dell.chihuobao.util.FoodCategoryChooseAdapter;
import com.example.dell.chihuobao.util.MyApplication;
import com.example.dell.chihuobao.util.ServerUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by dell on 2016/3/15.
 */
public class FoodMenuModifyActivity extends BaseActivity{
    private final static String DELETE_FOOD ="chb/shop/deleteProduct.do";
    private final static String UPDATE_FOOD = "chb/shop/updateProduct.do";
    private final static String QUERY_ONE_PRODUCT = "chb/shop/selectProductById.do";
    private final static String URL = "http://10.6.12.110:8080/";

    private ImageView ivFoodImage;
    private EditText etFoodName;
    private EditText etFoodPrice;
    private EditText etFoodDescription;
    private Spinner spFoodType;
    private EditText etFoodAchieveMoney;
    private EditText etFoodReduceMoney;
    private HashMap foodHashMap = new HashMap();
    private String date;
    private Food food;

    private String[] items = { "拍照", "相册" };
    private String title = "选择照片";

    private File file;
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    private Bitmap bitmap;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());
    private ServerUtil serverUtil = new ServerUtil();
    private Button btnModify;
    private Button btnDelete;
    private String getId;
    private String foodCategoryIdSelected;
    private ArrayList<Food> foodArrayList = new ArrayList<>();
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.ic_launcher)
            .build();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            putDataFirst();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x.Ext.init(getApplication());
        super.onCreate(savedInstanceState);

        //getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.activity_food_menu_modify);

        Intent intent = getIntent();
        getId = intent.getStringExtra("id");
        getIdDataAndInit(getId);


    }
    public void initView(){
        etFoodName = (EditText)findViewById(R.id.et_food_modify_name);
        etFoodPrice = (EditText)findViewById(R.id.et_food_modify_price);
        spFoodType = (Spinner)findViewById(R.id.sp_food_modify_type);
        etFoodDescription = (EditText)findViewById(R.id.et_food_description);
        etFoodAchieveMoney = (EditText)findViewById(R.id.et_food_modify_achieve_money);
        etFoodReduceMoney =(EditText)findViewById(R.id.et_food_modify_reduce_money);
        btnModify = (Button)findViewById(R.id.btn_modify);
        btnDelete = (Button)findViewById(R.id.btn_delete);
        ivFoodImage = (ImageView)findViewById(R.id.iv_food_modify);
        FoodCategoryChooseAdapter foodCategoryChooseAdapter = new FoodCategoryChooseAdapter(MyApplication.getFoodCategoryArrayList(),this);
        spFoodType.setAdapter(foodCategoryChooseAdapter);
        spFoodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodCategoryIdSelected = ((FoodCategory)parent.getItemAtPosition(position)).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDelete.setOnClickListener(clickListener);
        btnModify.setOnClickListener(clickListener);
        ivFoodImage.setOnClickListener(clickListener);
    }
    public void getIdDataAndInit(String id){
        RequestParams params = new RequestParams(URL+QUERY_ONE_PRODUCT);
        params.addBodyParameter("id", id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                initView();
                parseOneData(result);
                Thread thread  = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadBitmap(URL + food.getPhoto().replaceAll("\\\\", "/"));
                    }
                });
                thread.start();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



    public void parseOneData(String result){
        Gson gson  = new Gson();
        food = gson.fromJson(result,Food.class);
    }


    public void putDataFirst(){
        etFoodName.setText(food.getName());
        etFoodPrice.setText(food.getPrice());
        etFoodAchieveMoney.setText(food.getAchievemoney());
        etFoodReduceMoney.setText(food.getReducemoney());
        etFoodDescription.setText(food.getDescription());
        ivFoodImage.setImageBitmap(bitmap);
        saveCropPic(bitmap);
        
    }






    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".jpg";
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_food_modify:
                    AlertDialog.Builder dialog = AndroidUtil.getListDialogBuilder(
                            FoodMenuModifyActivity.this, items, title, dialogListener);
                    dialog.show();
                    break;
                case R.id.btn_modify:
                    if (tempFile.exists()){
                        Toast.makeText(FoodMenuModifyActivity.this,"图片存在",Toast.LENGTH_SHORT).show();
                        updateFood(getData());
                    }else {
                        Toast.makeText(FoodMenuModifyActivity.this,"图片不存在",Toast.LENGTH_SHORT).show();
                    }


                    break;

                case R.id.btn_delete:
                    deleteFood(getId);

                    break;
            }

        }
    };

    private HashMap getData(){
        file= new File(tempFile.getPath());
        foodHashMap.put("id",food.getId());
        foodHashMap.put("shopid", food.getShopid());
        foodHashMap.put("categoryid",foodCategoryIdSelected);
        foodHashMap.put("name",etFoodName.getText().toString());
        foodHashMap.put("storenumber","100");
        foodHashMap.put("price",etFoodPrice.getText().toString());
        foodHashMap.put("description",etFoodDescription.getText().toString());
        foodHashMap.put("salescount","0");
        foodHashMap.put("status","1");
        foodHashMap.put("achievemoney",etFoodAchieveMoney.getText().toString());
        foodHashMap.put("reducemoney",etFoodReduceMoney.getText().toString());
        foodHashMap.put("rank","6");
        foodHashMap.put("photodetail",tempFile.getPath());
        foodHashMap.put("photo",file);
        foodHashMap.put("inserttime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        return foodHashMap;


    }

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    // 调用拍照
                    startCamera(dialog);

                    break;
                case 1:
                    // 调用相册
                    startPick(dialog);

                    break;

                default:
                    break;
            }
        }
    };
    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    setPicToView(data);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            bitmap= bundle.getParcelable("data");
            ivFoodImage.setImageBitmap(bitmap);
            saveCropPic(bitmap);

        }
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        params.addBodyParameter("status", hashMap.get("status"), null);
        params.addBodyParameter("achievemoney", hashMap.get("achievemoney"), null);
        params.addBodyParameter("reducemoney", hashMap.get("reducemoney"), null);
        params.addBodyParameter("rank", hashMap.get("rank"), null);
        params.addBodyParameter("photodetail",hashMap.get("photodetail"),null);
        params.addBodyParameter("photo",hashMap.get("photo"),null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
                Toast.makeText(x.app(), "更新成功，马上去服务器看看吧！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoodMenuModifyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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
                Toast.makeText(x.app(), "删除成功，马上去服务器看看吧！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoodMenuModifyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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

    private void downloadBitmap(String url) {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();

                //图片压缩处理
                //BitmapFactory.Options option = new BitmapFactory.Options();
                //option.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
                //option.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式
                bitmap = BitmapFactory.decodeStream(inputStream, null,null);
                handler.sendEmptyMessage(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();

        }

    }
}
