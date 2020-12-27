package com.tuya.smart.android.Young.scene.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tuya.smart.android.Young.R;
import com.tuya.smart.android.Young.base.activity.BaseActivity;
import com.tuya.smart.android.Young.base.utils.ActivityUtils;
import com.tuya.smart.android.Young.scene.presenter.ConditionTaskChoosePresenter;
import com.tuya.smart.android.Young.scene.presenter.OperatorValuePresenter;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.IGetAllTimerWithDevIdCallback;
import com.tuya.smart.sdk.api.IGetTimerWithTaskCallback;
import com.tuya.smart.sdk.bean.TimerTask;

import java.util.ArrayList;
import java.util.Calendar;

import static com.tuya.smart.android.Young.base.presenter.SceneListPresenter.SMART_IS_EDIT;
import static com.tuya.smart.android.Young.base.presenter.SceneListPresenter.SMART_TYPE;
import static com.tuya.smart.android.Young.base.presenter.SceneListPresenter.SMART_TYPE_AUTOMATION;
import static com.tuya.smart.android.Young.scene.presenter.ScenePresenter.IS_CONDITION;

/**
 * create by nielev on 2019-10-29
 */
public class ConditionTaskChooseActivity extends BaseActivity implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener{

    private boolean isCondition;
    private ConditionTaskChoosePresenter mPresenter;
    public TextView tv_date;       //日期设置
    public  TextView tv_getList;       //时间设置
    private TextView tv_list;
    private TextView tv_gettimer;
    private TextView tv_timer;
    private Context mContext;

    /**时间设定*/
    private int year, month, day, hour, minute;
    private StringBuffer date, time;

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_task_choose);
        initToolbar();
        initMenu();
        initView();
        initPresenter();
        initDateTime();
        mContext =this;
    }

    void goChooseDeviceActivity()
    {
        Intent intent = new Intent(this, SceneActivity.class);
        intent.putExtra(SMART_TYPE, SMART_TYPE_AUTOMATION);
        intent.putExtra(SMART_IS_EDIT, false);
        intent.putExtra("gettime",""+hour+":"+minute);
        ActivityUtils.startActivity(this, intent, ActivityUtils.ANIMATE_FORWARD, false);
    }

    private void initMenu() {
        isCondition = getIntent().getBooleanExtra(IS_CONDITION, false);
        setTitle(isCondition ? getString(R.string.ty_scene_select_condition) : getString(R.string.ty_scene_select_task));
    }

    private void initView() {
        findViewById(R.id.btn_device).setOnClickListener(this);
        tv_date=findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        tv_getList=findViewById(R.id.tv_getList);
        tv_getList.setOnClickListener(this);
        tv_list =findViewById(R.id.tv_list);

        tv_gettimer =findViewById(R.id.tv_gettimer);
        tv_gettimer.setOnClickListener(this);
        tv_timer =findViewById(R.id.tv_timer);
//        tv_list.a

//        date = new StringBuffer();
//        time = new StringBuffer();

//        tv_time.setText(hour + "时" + minute + "分");
//        tv_time.setTextColor(Color.parseColor("#80000000"));

//         timePicker = (TimePicker) findViewById(R.id.timePicker);
//         timePicker.setIs24HourView(true); //设置24小时制
    }

    private void initPresenter() {
        mPresenter = new ConditionTaskChoosePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_device:
                mPresenter.selectDeviceTask(isCondition);
                break;
            case R.id.tv_date:
//                changeDate();
                break;
            case R.id.tv_getList:
                TuyaHomeSdk.getTimerManagerInstance().getAllTimerWithDeviceId(OperatorValuePresenter.device_id, new IGetAllTimerWithDevIdCallback() {
                    @Override
                    public void onSuccess(ArrayList<TimerTask> taskArrayList) {
//                        tv_list.addTouchables(taskArrayList);
//                        taskArrayList.toString();
                        tv_list.setText(taskArrayList.toString());
                        tv_list.setTextColor(Color.parseColor("#4B4949"));
                        Toast.makeText(mContext, "获取设备下的定时 成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        Toast.makeText(mContext, "获取设备下的定时 失败" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.tv_gettimer:
                TuyaHomeSdk.getTimerManagerInstance().getTimerWithTask("task01", OperatorValuePresenter.device_id, new IGetTimerWithTaskCallback() {
                    @Override
                    public void onSuccess(TimerTask timerTask) {
                        tv_timer.setText( timerTask.toString());
                        tv_timer.setTextColor(Color.parseColor("#4B4949"));
                        Toast.makeText(mContext, "获取定时任务下的定时 成功", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        Toast.makeText(mContext, "获取定时任务下的定时 失败" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                });

                break;
                default:break;
        }
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);  //24小时方法
        minute = calendar.get(Calendar.MINUTE);
    }

    /**选择日期*/
    public  void  changeDate()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tv_date.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                tv_date.setTextColor(Color.parseColor("#80000000"));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month-1 , day, this);
    }

    /**选择时间*/
    public  void changeTime()
    {
//        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
//        builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (time.length() > 0) { //清除上次记录的日期
//                    time.delete(0, time.length());
//                }
//                tv_time.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
//                tv_time.setTextColor(Color.parseColor("#80000000"));
//                dialog.dismiss();
//            }
//        });
//        builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog2 = builder2.create();
//        View dialogView2 = View.inflate(this, R.layout.dialog_time, null);

        if (time.length() > 0) { //清除上次记录的日期
            time.delete(0, time.length());
        }

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(this);

        Intent intent =new Intent();
        intent.putExtra("gettime",""+hour+":"+ minute);
        setResult(RESULT_OK,intent);
        finish();

//        tv_time.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
//        tv_time.setTextColor(Color.parseColor("#80000000"));
//        dialog2.setTitle("设置时间");
//        dialog2.setView(dialogView2);
//        dialog2.show();

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear+1;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mPresenter)mPresenter.onDestroy();
    }

}
