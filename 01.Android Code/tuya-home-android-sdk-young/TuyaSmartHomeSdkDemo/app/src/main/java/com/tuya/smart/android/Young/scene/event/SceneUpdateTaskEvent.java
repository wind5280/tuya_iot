package com.tuya.smart.android.Young.scene.event;

import com.tuya.smart.android.Young.scene.event.model.SceneUpdateTaskModel;

/**
 * create by nielev on 2019-10-29
 */
public interface SceneUpdateTaskEvent {
    void onEvent(SceneUpdateTaskModel model);
}
