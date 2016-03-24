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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.AndroidUtil;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.Date;
import java.util.HashMap;

/**
 * Created by dell on 2016/3/15.
 */
public class UserModifyActivity extends BaseActivity{
    public final static String UPDATE_USER = "chb/shop/updateShop.do?";
    public final static String URL = "http://10.6.12.88:8080/";
    private User user;
    private HashMap hashMap ;
    private ImageView shopphoto;
    /*private EditText phone;
    private EditText shoptype;

    private EditText minconsume;
    private EditText sendexpense;
    private EditText email;
    private EditText name;
    private EditText businessstarttime;
    private EditText businessendtime;*/

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
    /*private ServerUtil serverUtil = new ServerUtil();*/
    private Button btnModify;
    private Button btnReturn;
    /*private ToggleButton isServing;*/

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

            shopphoto.setImageBitmap(bitmap);
            saveCropPic(bitmap);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);
        initView();

    }
    public void initView(){
        user= MyApplication.getInstance().getUser();
        hashMap = user.getUser();
        /*phone = (EditText)findViewById(R.id.phone);


        minconsume = (EditText)findViewById(R.id.minconsume);
        sendexpense =(EditText)findViewById(R.id.sendexpense);

        email=(EditText)findViewById(R.id.email);
        name=(EditText)findViewById(R.id.name);
        businessstarttime=(EditText)findViewById(R.id.businessstarttime);
        businessendtime=(EditText)findViewById(R.id.businessendtime);
        isServing= (ToggleButton) findViewById(R.id.serving);
        Calendar c;
        c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        businessstarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UserModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        businessstarttime.setText(new StringBuilder()
                                .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                                .append(minute < 10 ? "0" + minute : minute));
                    }
                },hour,minute,true).show();
            }
        });
        businessendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UserModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        businessendtime.setText(new StringBuilder()
                                .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                                .append(minute < 10 ? "0" + minute : minute));
                    }
                }, hour, minute, true).show();
            }
        });*/
     /*   isServing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    hashMap.put("isServing", "1");
                else
                    hashMap.put("isServing", "0");
            }
        });*/
        btnModify = (Button)findViewById(R.id.btn_modify);
        btnReturn = (Button)findViewById(R.id.btn_return);
        shopphoto = (ImageView)findViewById(R.id.shopphoto);

        btnReturn.setOnClickListener(clickListener);
        btnModify.setOnClickListener(clickListener);
        shopphoto.setOnClickListener(clickListener);

        putDataFirst();
    }


    public void putDataFirst() {
       /* if (user.getUser().get("phone") != null) {
            phone.setText(user.getUser().get("phone").toString());
        }
        if (user.getUser().get("minconsume") != null) {
            minconsume.setText(user.getUser().get("minconsume").toString());
        }
        if (user.getUser().get("sendexpense") != null) {
            sendexpense.setText(user.getUser().get("sendexpense").toString());
        }

        if (user.getUser().get("email") != null) {
            email.setText(user.getUser().get("email").toString());
        }
        if (user.getUser().get("name") != null) {
            name.setText(user.getUser().get("name").toString());
        }
        if (user.getUser().get("businessstarttime") != null) {
            businessstarttime.setText(user.getUser().get("businessstarttime").toString());
        }
        if (user.getUser().get("businessendtime") != null) {
            businessendtime.setText(user.getUser().get("businessendtime").toString());
        }
        if (user.getUser().get("isServing") != null) {
            if ((int)Double.parseDouble(user.getUser().get("isServing").toString())==1) {
                isServing.setChecked(true);
            }else {
                isServing.setChecked(false);
            }
        }else {
            isServing.setChecked(false);
        }*/

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadBitmap(URL + user.getUser().get("shopphoto").toString().replaceAll("\\\\", "/"));
            }
        });
        thread.start();


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
                case R.id.shopphoto:
                    AlertDialog.Builder dialog = AndroidUtil.getListDialogBuilder(
                            UserModifyActivity.this, items, title, dialogListener);
                    dialog.show();
                    break;
                case R.id.btn_modify:
                    if (tempFile.exists()){
                        updateUser(getData());
                        //Toast.makeText(UserModifyActivity.this,"图片存在",Toast.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(UserModifyActivity.this,"图片不存在",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btn_return:
                    finish();
                    break;
            }

        }
    };



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
            shopphoto.setImageBitmap(bitmap);


            saveCropPic(bitmap);
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

    public void updateUser(final HashMap hashMap){
        RequestParams params = new RequestParams(URL+UPDATE_USER);
        params.addBodyParameter("id",(int)(Double.parseDouble(hashMap.get("id").toString())),null);
        params.addBodyParameter("username",hashMap.get("username").toString(),null);
/*        params.addBodyParameter("password", hashMap.get("password").toString(), null);
        params.addBodyParameter("phone", hashMap.get("phone"), null);
        params.addBodyParameter("address", hashMap.get("address"), null);*/
        params.addBodyParameter("shopphoto", hashMap.get("shopphoto"), null);
        final String name=getPhotoFileName();
        params.addBodyParameter("shopphotodetail",name, null);
      /*  params.addBodyParameter("registertime", hashMap.get("registertime"), null);*/
        params.addBodyParameter("updatetime", hashMap.get("updatetime").toString(), null);
/*        params.addBodyParameter("shoptype", (int)(Double.parseDouble(hashMap.get("shoptype").toString())), null);
        params.addBodyParameter("minconsume", Double.parseDouble(hashMap.get("minconsume").toString()), null);
        params.addBodyParameter("sendexpense", Double.parseDouble(hashMap.get("sendexpense").toString()), null);
        params.addBodyParameter("email", hashMap.get("email").toString(), null);
        params.addBodyParameter("qrcode", hashMap.get("qrcode"), null);
        params.addBodyParameter("shopmessage", hashMap.get("shopmessage"), null);
        params.addBodyParameter("telephone", hashMap.get("telephone"), null);
        params.addBodyParameter("identify", hashMap.get("identify"), null);
        params.addBodyParameter("license", hashMap.get("license"), null);
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("businessstarttime", hashMap.get("businessstarttime").toString(), null);
        params.addBodyParameter("businessendtime", hashMap.get("businessendtime").toString(), null);*/
        params.addBodyParameter("isServing", (int)(Double.parseDouble(hashMap.get("isServing").toString())), null);
/*        params.addBodyParameter("axisX", hashMap.get("axisX"), null);
        params.addBodyParameter("axisY", hashMap.get("axisY"), null);*/
        if(hashMap.get("rank")!=null) {
            params.addBodyParameter("rank", (int) (Double.parseDouble(hashMap.get("rank").toString())), null);
        }else{
            params.addBodyParameter("rank", "0", null);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);

                user.setUser(hashMap);
                MyApplication.getInstance().setUser(user);
                login(MyApplication.getInstance().getUser().getUser().get("username")+"",MyApplication.getInstance().getUser().getUser().get("password")+"");
                Toast.makeText(x.app(), "更新成功，马上去服务器看看吧！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserModifyActivity.this, MainActivity.class);
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
    private HashMap getData(){
        file= new File(tempFile.getPath());

      /*  hashMap.put("phone", phone.getText().toString());
        hashMap.put("minconsume", minconsume.getText().toString());
        hashMap.put("sendexpense", sendexpense.getText().toString());
        hashMap.put("shoptype", shoptype.getText().toString());
        hashMap.put("email", email.getText().toString());
        hashMap.put("name", name.getText().toString());
        hashMap.put("businessstarttime", businessstarttime.getText().toString());
        hashMap.put("businessendtime", businessendtime.getText().toString());*/

        hashMap.put("shopphoto", file);

        hashMap.put("updatetime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
       /* if (isServing.isChecked()) {
            hashMap.put("isServing", 1);
        }
        else {
            hashMap.put("isServing", 2);
        }*/

        return hashMap;


    }

    private void login(String username, String password) {
        RequestParams params = new RequestParams(LoginActivity.ADDRESS);
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", password);
        /*if (etUserName.getText().toString().trim().equals("") || erUserPwd.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            etUserName.setText(etUserName.getText().toString().trim());
            erUserPwd.setText("");
            return;
        }*/
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseLog.i(result);
                Gson gson = new Gson();
                User user = null;
                user = gson.fromJson(result, new TypeToken<User>() {
                }.getType());
                if (user.getStatus().equals("success")) {


                    //bundle.putSerializable("data", user.getInfo());

                    MyApplication.getInstance().setUser(user);

                } else if (user.getStatus().equals("fail")) {

                } else {
                    BaseLog.e("返回数据出错！！！");
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseLog.e(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



}
