package com.example.dell.chihuobao.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.BaseFragment;
import com.example.dell.chihuobao.appwidget.MyMarkerView;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class BarChartFragment extends BaseFragment implements OnChartGestureListener {
    public static final String BARCHART_URL="http://10.6.12.88:8080/chb/shop/getStatisticeByDay.do";
    User user = MyApplication.getInstance().getUser();
    HashMap hashMap = user.getUser();
    ArrayList<Map<String, String>> oderList=null;
    View v;
    public static Fragment newInstance() {
        return new BarChartFragment();
    }

    private BarChart mChart;
    Typeface tf;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {

                ArrayList<String> xVals = new ArrayList<String>();
                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                if (oderList!=null) {
                    for (int i = 0; i < oderList.size(); i++) {
                        xVals.add(oderList.get(i).get("day"));
                        yVals1.add(new BarEntry(Float.parseFloat(oderList.get(i).get("dailySales")), i));
                    }
                }




                BarDataSet set1 = new BarDataSet(yVals1, "日营业额");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(tf);

                mChart.setData(data);
                updateView();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_simple_bar, container, false);
        
        // create a new chart object
        mChart = new BarChart(getActivity());
        mChart.setDescription("");
        mChart.setOnChartGestureListener(this);
        
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        mChart.setMarkerView(mv);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        
        tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");

        setData();
        
       /* Legend l = mChart.getLegend();
        l.setTypeface(tf);
        
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);
        
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        
        // programatically add the chart
        FrameLayout parent = (FrameLayout) v.findViewById(R.id.parentLayout);
        parent.addView(mChart);*/
        
        return v;
    }
    public void updateView(){
        Legend l = mChart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        // programatically add the chart
        FrameLayout parent = (FrameLayout) v.findViewById(R.id.parentLayout);
        parent.addView(mChart);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END");
        mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }
   
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

	@Override
	public void onChartTranslate(MotionEvent me, float dX, float dY) {
		Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
	}
    private void setData() {

        RequestParams params = new RequestParams(BARCHART_URL);
        params.addQueryStringParameter("shopId",(int)Double.parseDouble(hashMap.get("id").toString())+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseLog.d(result);
                Gson gson = new Gson();
                oderList = gson.fromJson(result, new TypeToken<ArrayList<Map<String, String>>>() {
                }.getType());
                Message msg = new Message();
               /* Bundle bundle = new Bundle();
                bundle.putSerializable("oderList",oderList);
                msg.setData(bundle);*/
                msg.what = 1;
                handler.sendMessage(msg);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
