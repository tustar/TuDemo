package com.tustar.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;
import com.tustar.demo.widget.systemWidget.CustomActionBar;

import static com.tustar.demo.activity.CustomWidgetActivity.CustomType;

public class CustomWidgetShowActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CustomType type = (CustomType) getIntent().
                getSerializableExtra(IntentExtraKey.FLAG);
        switch (type) {
            case MEASURE:
                setContentView(R.layout.activity_custom_widget_measure);
                break;
            case ACTIONBAR:
                setContentView(R.layout.activity_custom_widget_actionbar);
                initTopBarView();
                break;
            case TEXTVIEW:
                setContentView(R.layout.activity_custom_widget_textview);
                break;
            case CIRCLEPROGRESS:
                setContentView(R.layout.activity_custom_widget_circleprogress);
                break;
            case VOLUME:
                setContentView(R.layout.activity_custom_widget_volume);
                break;
            case SCROLLVIEW:
                setContentView(R.layout.activity_custom_widget_scrollview);
                break;
            case VIEWLAYOUT:
                setContentView(R.layout.activity_custom_widget_viewlayout);
        }
    }

    private void initTopBarView() {
        // 获得我们创建的topbar
        CustomActionBar mTopbar = (CustomActionBar) findViewById(R.id.custom_widget_topBar);
        // 为topbar注册监听事件，传入定义的接口
        // 并以匿名类的方式实现接口内的方法
        mTopbar.setOnTopbarClickListener(
                new CustomActionBar.topbarClickListener() {

                    @Override
                    public void rightClick() {
                        Toast.makeText(mContext,
                                "right", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void leftClick() {
                        Toast.makeText(mContext,
                                "left", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        // 控制topbar上组件的状态
        mTopbar.setButtonVisable(0, true);
        mTopbar.setButtonVisable(1, false);
    }
}
