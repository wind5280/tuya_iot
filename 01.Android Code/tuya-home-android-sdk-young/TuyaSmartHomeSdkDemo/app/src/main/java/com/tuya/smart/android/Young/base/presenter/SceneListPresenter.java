package com.tuya.smart.android.Young.base.presenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;

import com.tuya.smart.android.Young.base.utils.DialogUtil;
import com.tuya.smart.android.Young.scene.activity.SceneEditActivity;
import com.tuya.smart.android.base.utils.PreferencesUtil;
import com.tuya.smart.android.Young.R;
import com.tuya.smart.android.Young.base.app.Constant;
import com.tuya.smart.android.Young.base.utils.ActivityUtils;
import com.tuya.smart.android.Young.base.utils.ToastUtil;
import com.tuya.smart.android.Young.scene.activity.SceneActivity;
import com.tuya.smart.android.Young.scene.view.ISceneListFragmentView;
import com.tuya.smart.android.mvp.presenter.BasePresenter;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.scene.SceneBean;
import com.tuya.smart.home.sdk.callback.ITuyaResultCallback;
import com.tuya.smart.sdk.api.IResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * create by nielev on 2019-10-28
 */
public class SceneListPresenter extends BasePresenter {
    private Activity mActivity;
    private ISceneListFragmentView mView;
    public static final int SMART_TYPE_SCENE = 0;
    public static final int SMART_TYPE_AUTOMATION = 1;
    public static final String SMART_TYPE = "smart_type";
    public static final String SMART_IS_EDIT = "smart_is_edit";

    public SceneListPresenter(Activity activity, ISceneListFragmentView iView) {
        mActivity = activity;
        mView = iView;
        Constant.HOME_ID = PreferencesUtil.getLong("homeId", Constant.HOME_ID);
    }

    public void getSceneList() {
        if (Constant.HOME_ID != -1) {
            TuyaHomeSdk.getSceneManagerInstance().getSceneList(Constant.HOME_ID, new ITuyaResultCallback<List<SceneBean>>() {
                @Override
                public void onSuccess(List<SceneBean> result) {
                    if (null == result || result.isEmpty()) {
                        mView.showEmptyView();
                    } else {
                        separateSceneAndAuto(result);
                    }
                    mView.loadFinish();
                }

                @Override
                public void onError(String errorCode, String errorMessage) {
                    ToastUtil.shortToast(mActivity, errorMessage);
                    mView.loadFinish();
                }
            });
        }
    }


    private void separateSceneAndAuto(List<SceneBean> result) {
        List<SceneBean> scenes = new ArrayList<>();
        List<SceneBean> autos = new ArrayList<>();
        for (SceneBean sceneBean :
            result) {
            if (null == sceneBean.getConditions() || sceneBean.getConditions().isEmpty()) {
                scenes.add(sceneBean);
            } else {
                autos.add(sceneBean);
            }
        }
        mView.showSceneListView(scenes, autos);
    }

    public void addScene() {
        Intent intent = new Intent(mActivity, SceneActivity.class);
        intent.putExtra(SMART_TYPE, SMART_TYPE_SCENE);
        intent.putExtra(SMART_IS_EDIT, false);
        ActivityUtils.startActivity(mActivity, intent, ActivityUtils.ANIMATE_FORWARD, false);
    }



    public void addAuto() {
        Intent intent = new Intent(mActivity, SceneActivity.class);
        intent.putExtra(SMART_TYPE, SMART_TYPE_AUTOMATION);
        intent.putExtra(SMART_IS_EDIT, false);
        ActivityUtils.startActivity(mActivity, intent, ActivityUtils.ANIMATE_FORWARD, false);
    }

    public void editScene() {
        Intent intent = new Intent(mActivity, SceneEditActivity.class);
        intent.putExtra(SMART_TYPE, SMART_TYPE_AUTOMATION);
        intent.putExtra(SMART_IS_EDIT, false);
        ActivityUtils.startActivity(mActivity, intent, ActivityUtils.ANIMATE_FORWARD, false);
    }

    /**执行操作*/
    public void execute(SceneBean bean) {
        TuyaHomeSdk.newSceneInstance(bean.getId()).executeScene(new IResultCallback() {
            @Override
            public void onError(String code, String error) {

            }

            @Override
            public void onSuccess() {
                ToastUtil.shortToast(mActivity, R.string.success);
            }
        });
    }

    /**删除场景*/
    public  void  deleteScene(SceneBean bean)
    {
        DialogUtil.simpleConfirmDialog(mActivity, mActivity.getString(R.string.delete_scene), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    TuyaHomeSdk.newSceneInstance(bean.getId()).deleteScene(new IResultCallback() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.shortToast(mActivity, "删除成功！");
                        }
                        @Override
                        public void onError(String errorCode, String errorMessage) {
                        }
                    });
            }
            }
        });
    }





    public void switchAutomation(SceneBean bean) {
        if (bean.isEnabled()) {
            TuyaHomeSdk.newSceneInstance(bean.getId()).disableScene(bean.getId(), new IResultCallback() {
                @Override
                public void onError(String code, String error) {
                    ToastUtil.shortToast(mActivity, error);
                }

                @Override
                public void onSuccess() {
                    ToastUtil.shortToast(mActivity, R.string.success);
                }
            });
        } else {
            TuyaHomeSdk.newSceneInstance(bean.getId()).enableScene(bean.getId(), new IResultCallback() {
                @Override
                public void onError(String code, String error) {
                    ToastUtil.shortToast(mActivity, error);
                }

                @Override
                public void onSuccess() {
                    ToastUtil.shortToast(mActivity, R.string.success);
                }
            });
        }
    }


}
