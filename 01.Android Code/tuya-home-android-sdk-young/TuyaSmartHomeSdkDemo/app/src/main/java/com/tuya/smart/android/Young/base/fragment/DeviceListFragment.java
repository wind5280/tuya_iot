package com.tuya.smart.android.Young.base.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tuya.smart.android.common.utils.NetworkUtil;
import com.tuya.smart.android.Young.R;
import com.tuya.smart.android.Young.base.activity.BaseActivity;
import com.tuya.smart.android.Young.base.presenter.DeviceListFragmentPresenter;
import com.tuya.smart.android.Young.base.utils.AnimationUtil;
import com.tuya.smart.android.Young.base.utils.ToastUtil;
import com.tuya.smart.android.Young.base.view.IDeviceListFragmentView;
import com.tuya.smart.android.Young.device.CommonDeviceAdapter;
import com.tuya.smart.android.Young.family.view.SwitchFamilyText;
import com.tuya.smart.sdk.bean.DeviceBean;

import java.util.List;


/**
 * Created by letian on 16/7/18.
 */
public class DeviceListFragment extends BaseFragment implements IDeviceListFragmentView {

    private static final String TAG = "DeviceListFragment";
    private volatile static DeviceListFragment mDeviceListFragment;
    private View mContentView;
    private DeviceListFragmentPresenter mDeviceListFragmentPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CommonDeviceAdapter mCommonDeviceAdapter;
    private ListView mDevListView;
    private TextView mNetWorkTip;
    private View mRlView;
    private View mAddDevView;
    private View mBackgroundView;
    private View mShortcutView;

    public static Fragment newInstance() {
        if (mDeviceListFragment == null) {
            synchronized (DeviceListFragment.class) {
                if (mDeviceListFragment == null) {
                    mDeviceListFragment = new DeviceListFragment();
                }
            }
        }
        return mDeviceListFragment;
    }

    /**DeviceListFragment  初始化*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_device_list, container, false);
        initToolbar(mContentView);//初始化导航栏
        initMenu();     //标题栏菜单，
        initView();
        initAdapter();
        initSwipeRefreshLayout();  //下来刷新
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
//        L.d(TAG,"tuyaTime: "+ TuyaUtil.formatDate(System.currentTimeMillis(),"yyyy-mm-dd hh:mm:ss"));
        mDeviceListFragmentPresenter.getDataFromServer();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtil.isNetworkAvailable(getContext())) {
                    mDeviceListFragmentPresenter.getDataFromServer(); //从服务器获取数据
                } else {
                    loadFinish();  //刷新完成
                }
            }
        });
    }

    private void initAdapter() {
        mCommonDeviceAdapter = new CommonDeviceAdapter(getActivity());
        mDevListView.setAdapter(mCommonDeviceAdapter);
        mDevListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /** 设备被长按*/
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return mDeviceListFragmentPresenter.onDeviceLongClick((DeviceBean) parent.getAdapter().getItem(position));
            }
        });
        mDevListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**设备被短按*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDeviceListFragmentPresenter.onDeviceClick((DeviceBean) parent.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public void updateDeviceData(List<DeviceBean> myDevices) {
        if (mCommonDeviceAdapter != null) {
            mCommonDeviceAdapter.setData(myDevices);
        }
    }

    @Override
    public void loadStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.swipe_container);
        mNetWorkTip = (TextView) mContentView.findViewById(R.id.network_tip);
        mDevListView = (ListView) mContentView.findViewById(R.id.lv_device_list);
        mShortcutView = mContentView.findViewById(R.id.tv_shortcut);
        mRlView = mContentView.findViewById(R.id.rl_list);
        mAddDevView = mContentView.findViewById(R.id.tv_empty_func);
        mBackgroundView = mContentView.findViewById(R.id.list_background_tip);
        mAddDevView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(getActivity(), "Open soon");
            }
        });

        mShortcutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeviceListFragmentPresenter.gotoShortcutActivity();
            }
        });
    }

    protected void initPresenter() {
        mDeviceListFragmentPresenter = new DeviceListFragmentPresenter(this, this);
    }

    protected void initMenu() {
        SwitchFamilyText switchFamilyText = new SwitchFamilyText(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getToolBar().addView(switchFamilyText, layoutParams);

        setMenu(R.menu.toolbar_add_device, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add_device) {
                    mDeviceListFragmentPresenter.addDevice();
                }
                return false;
            }
        });
    }

    @Override
    public void loadFinish() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void showNetWorkTipView(int tipRes) {
        mNetWorkTip.setText(tipRes);
        if (mNetWorkTip.getVisibility() != View.VISIBLE) {
            AnimationUtil.translateView(mRlView, 0, 0, -mNetWorkTip.getHeight(), 0, 300, false, null);
            mNetWorkTip.setVisibility(View.VISIBLE);
        }
    }

    public void hideNetWorkTipView() {
        if (mNetWorkTip.getVisibility() != View.GONE) {
            AnimationUtil.translateView(mRlView, 0, 0, mNetWorkTip.getHeight(), 0, 300, false, null);
            mNetWorkTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBackgroundView() {
        BaseActivity.setViewGone(mDevListView);
        BaseActivity.setViewVisible(mBackgroundView);
        BaseActivity.setViewGone(mShortcutView);
    }

    @Override
    public void hideBackgroundView() {
        BaseActivity.setViewVisible(mDevListView);
        BaseActivity.setViewGone(mBackgroundView);
        BaseActivity.setViewVisible(mShortcutView);
    }

    @Override
    public void gotoCreateHome() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDeviceListFragmentPresenter.onDestroy();
    }

}
