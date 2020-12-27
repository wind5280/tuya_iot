package com.tuya.smart.android.Young.scene.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.tuya.smart.android.Young.R;
import com.tuya.smart.android.Young.base.activity.BaseActivity;
import com.tuya.smart.android.Young.base.utils.ToastUtil;
import com.tuya.smart.android.Young.scene.adapter.SceneConditionAdapter;
import com.tuya.smart.android.Young.scene.adapter.SceneTaskAdapter;
import com.tuya.smart.android.Young.scene.presenter.ScenePresenter;
import com.tuya.smart.android.Young.scene.view.ISceneView;
import com.tuya.smart.home.sdk.bean.scene.SceneCondition;
import com.tuya.smart.home.sdk.bean.scene.SceneTask;

import java.util.Calendar;
import java.util.List;

/**
 * create by nielev on 2019-10-28
 */
public class SceneActivity extends BaseActivity implements View.OnClickListener, ISceneView,TimePicker.OnTimeChangedListener{


    private ScenePresenter mPresenter;
    private View mTv_condition;
    private View mTv_task;
    private View mBtn_add_condition;
    private View mBtn_add_task;
    private RecyclerView mRcv_condition;
    private RecyclerView mRcv_task;
    private SceneConditionAdapter mCondtionAdapter;
    private SceneTaskAdapter mTaskAdapter;
    private Button mBtn_add_bg;
    private ImageView mIv_bg;
    private EditText mEt_add_name;
    private TextView tv_time;
    private Button  tv_setTime;

    /**时间设定*/
    private int year, month, day, hour, minute;
    private StringBuffer date, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);  //主要对布局进行初始化
        initToolbar();  //初始化标题栏
        initMenu();
        initView();
        initAdapter();
        initPresenter();

        /***
         *  初始化时间及日期
         */
        initDateTime();

    }

    private void initMenu() {
        setTitle(getString(R.string.home_scene));
        setMenu(R.menu.toolbar_save, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mPresenter.save();
                return false;
            }
        });
    }

    private void initView() {
        mBtn_add_condition = findViewById(R.id.btn_add_condition);
        mBtn_add_condition.setOnClickListener(this);
        mBtn_add_task = findViewById(R.id.btn_add_task);
        mBtn_add_task.setOnClickListener(this);
        mTv_condition = findViewById(R.id.tv_condition);
        mTv_task = findViewById(R.id.tv_task);
        mRcv_condition = findViewById(R.id.rcv_condition);
        mRcv_task = findViewById(R.id.rcv_task);
        mRcv_condition.setLayoutManager(new LinearLayoutManager(this));
        mRcv_task.setLayoutManager(new LinearLayoutManager(this));
        mBtn_add_bg = findViewById(R.id.btn_add_bg);
        mBtn_add_bg.setOnClickListener(this);
        mIv_bg = findViewById(R.id.iv_bg);
        mEt_add_name = findViewById(R.id.et_add_name);

        tv_time =findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        tv_setTime =findViewById(R.id.tv_setTime);
        tv_setTime.setOnClickListener(this);
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
        tv_time.setText(hour + "时" + minute + "分");

        time = new StringBuffer();
    }

    private void initAdapter() {
        mCondtionAdapter = new SceneConditionAdapter(this);
        mTaskAdapter = new SceneTaskAdapter(this);
        mRcv_condition.setAdapter(mCondtionAdapter);
        mRcv_task.setAdapter(mTaskAdapter);
    }

    private void initPresenter() {
        mPresenter = new ScenePresenter(this, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_condition:
                mPresenter.addCondition();
                break;
            case R.id.btn_add_task:
                mPresenter.addTask();
                break;
            case R.id.btn_add_bg:
                mPresenter.addBg();
                break;
            case R.id.tv_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) { //清除上次记录的日期
                            time.delete(0, time.length());
                        }
                        tv_time.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(this, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker.setIs24HourView(true); //设置24小时制
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("设置时间");
                dialog2.setView(dialogView2);
                dialog2.show();

                break;
            case R.id.tv_setTime:
                mPresenter.addTimer(""+hour+":"+minute);
                break;
                default:break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public void showSceneView() {
        mTv_condition.setVisibility(View.GONE);
        mBtn_add_condition.setVisibility(View.GONE);
    }

    @Override
    public void showAutoView() {
        mTv_condition.setVisibility(View.VISIBLE);
        mBtn_add_condition.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateConditions(List<SceneCondition> conditions) {
        mCondtionAdapter.setData(conditions);
    }

    @Override
    public void updateTasks(List<SceneTask> tasks) {
        mTaskAdapter.setData(tasks);
    }

    @Override
    public void showBg(String s) {
        Picasso.with(this).load(s).into(mIv_bg);
    }

    @Override
    public String getSceneName() {
        return mEt_add_name.getText().toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mPresenter)mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String time =data.getStringExtra("gettime");
            tv_time.setText(time);
            tv_time.setTextColor(Color.parseColor("#4A4A4A"));
        }

    }

}
