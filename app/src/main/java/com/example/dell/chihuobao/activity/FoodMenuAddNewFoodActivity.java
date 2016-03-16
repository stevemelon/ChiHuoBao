package com.example.dell.chihuobao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.dell.chihuobao.util.AndroidUtil;
import com.example.dell.chihuobao.util.ServerUtil;


import org.xutils.common.Callback;
import org.xutils.common.util.IOUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.request.HttpRequest;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by dell on 2016/3/9.
 */
public class FoodMenuAddNewFoodActivity extends Activity {
    private ImageView ivFoodImage;
    private EditText etFoodName;
    private EditText etFoodPrice;
    private EditText etFoodDescription;
    private Spinner spFoodType;
    private EditText etFoodAchieveMoney;
    private EditText etFoodReduceMoney;
    private HashMap foodHashMap= new HashMap();
    private String date;
    private Button btnUpload;
    private String[] items = { "拍照", "相册" };
    private String title = "选择照片";

    private  File file;

    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());
    private ServerUtil serverUtil = new ServerUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x.Ext.init(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_add_new_food);
        initView();
        btnUpload.setOnClickListener(clickListener);
        ivFoodImage.setOnClickListener(clickListener);

    }


    public void initView(){
        ivFoodImage = (ImageView)findViewById(R.id.iv_food_add);
        etFoodName = (EditText)findViewById(R.id.et_food_add_name);
        etFoodPrice=(EditText)findViewById(R.id.et_food_add_price);
        etFoodDescription = (EditText)findViewById(R.id.et_food_add_price);
        spFoodType = (Spinner)findViewById(R.id.sp_food_add_type);
        btnUpload  = (Button)findViewById(R.id.btn_upload);
        etFoodAchieveMoney = (EditText)findViewById(R.id.et_food_add_achieve_money);
        etFoodReduceMoney = (EditText)findViewById(R.id.et_food_add_reduce_money);
        spFoodType = (Spinner)findViewById(R.id.sp_food_add_type);
    }


    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".jpg";
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_food_add:
                    AlertDialog.Builder dialog = AndroidUtil.getListDialogBuilder(
                            FoodMenuAddNewFoodActivity.this, items, title, dialogListener);
                    dialog.show();
                    break;
                case R.id.btn_upload:
                    //serverUtil.addFood(getData());
                    RequestParams params = new RequestParams("http://10.6.12.56:8080/chb/shop/deleteProduct.do");
                    params.addBodyParameter("id","33");
                    /*params.addBodyParameter("shopid","1",null);
                    params.addBodyParameter("categoryid", "1", null);params.addBodyParameter("name", "ykg23454", null);
                    params.addBodyParameter("storenumber", "45454", null);
                    params.addBodyParameter("price", "13", null);
                    params.addBodyParameter("description", "12", null);
                    params.addBodyParameter("inserttime", "2016-02-21 23:23:23", null);
                    params.addBodyParameter("salescount", "0",null);
                    params.addBodyParameter("status", "1", null);
                    params.addBodyParameter("achievemoney", "12", null);
                    params.addBodyParameter("reducemoney", "23", null);
                    params.addBodyParameter("rank", "1", null);
                    params.addBodyParameter("photodetail",tempFile.getPath(),null);
                    params.addBodyParameter("photo",new File(tempFile.getPath()),null);*/

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
                    break;
                default:
                    break;

            }





        }
    };

    private HashMap getData(){
        file= new File(tempFile.getPath());
        foodHashMap.put("shopid", "1");
        //foodHashMap.put("categoryid",spFoodType.getSelectedItem().toString());
        foodHashMap.put("categoryid","1");
        foodHashMap.put("name",etFoodName.getText().toString());
        foodHashMap.put("storenumber","100");
        foodHashMap.put("price",etFoodPrice.getText().toString());
        foodHashMap.put("description",etFoodDescription.getText().toString());
        foodHashMap.put("salescount","0");
        foodHashMap.put("status","1");
        foodHashMap.put("achievemoney",etFoodAchieveMoney.getText().toString());
        foodHashMap.put("reducemoney",etFoodReduceMoney.getText().toString());
        foodHashMap.put("rank","6");
        foodHashMap.put("photodetail","qwwwqwq");
        foodHashMap.put("photo",file);
        foodHashMap.put("inserttime",new  SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(new Date()));
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
            final Bitmap bmp = bundle.getParcelable("data");
            ivFoodImage.setImageBitmap(bmp);

            saveCropPic(bmp);
            Log.i("MainActivity", tempFile.getAbsolutePath());
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
}
