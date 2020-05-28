package com.passion.check;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;
import static android.view.accessibility.AccessibilityNodeInfo.FOCUS_ACCESSIBILITY;

@SuppressLint("NewApi")
public class CheckService  extends AccessibilityService{

    private static String TAG = "CheckService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(accessibilityEvent.getEventType()==TYPE_VIEW_CLICKED||accessibilityEvent.getEventType()==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.d(TAG, "onAccessibilityEvent:event:" + accessibilityEvent.toString());

//        Log.d(TAG, "eventType: " + accessibilityEvent.getEventType() + " - " + LogUtil.makeMultipleChoiceStringResult(AccessibilityEvent.class, "TYPE_", accessibilityEvent.getEventType() ));
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null) {
                track(root,0);


                List<AccessibilityNodeInfo> node = root.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/payee_NextBtn");
                AccessibilityNodeInfo focus = root.findFocus(FOCUS_ACCESSIBILITY);
//           Log.d(TAG, root.toString());
            }
        }
    }

    private void track(AccessibilityNodeInfo root, int depth) {
        depth++;
        if(root.getClassName().toString().equals("android.widget.ImageView")){
            Log.d(TAG, "----imageView---"+root.getViewIdResourceName());
        }
        for (int i=0;i<root.getChildCount();i++){

            Log.d(TAG, "depth ------"+depth+"-------index------"+i+"--------"+root.getChild(i).getClassName()+"-----"+root.getChild(i).getViewIdResourceName());
            Log.d(TAG, root.getChild(i).getText()+"");

            track(root.getChild(i),depth);

        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }
}
