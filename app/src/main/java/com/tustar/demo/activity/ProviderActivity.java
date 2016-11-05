package com.tustar.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tustar.demo.R;
import com.tustar.demo.provider.History;

import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProviderActivity extends BaseActivity {

    @BindView(R.id.provider_result)
    TextView mProviderResult;
    private History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);
    }

    public void insert(View view) {
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        history = new History();
        history.formula = String.valueOf(a + " + " + b + "=");
        history.result = String.valueOf(a + b);
        history.createdAt = new Date();
        history = History.addHistory(getContentResolver(), history);
        mProviderResult.setText(String.valueOf(history));
    }

    public void update(View view) {
        if (null == history) {
            mProviderResult.setText("Update history is null");
            return;
        }
        history.formula = "Update";
        history.formula = "Update";
        boolean result = History.updateHistory(getContentResolver(), history);
        String message = "Update history " + history.id;
        String retStr = result ? " success!" : " failure!";
        message += retStr;
        mProviderResult.setText(message);
    }

    public void query(View view) {
        List<History> histories = History.getHistories(getContentResolver(), null, null);
        mProviderResult.setText("Query " + String.valueOf(histories));
        if (!histories.isEmpty()) {
            history = histories.get(0);
        }
    }

    public void delete(View view) {
        if (null == history) {
            mProviderResult.setText("Delete history is null");
            return;
        }
        boolean result = History.deleteHistory(getContentResolver(), history.id);
        String message = "Delete history " + history.id;
        String retStr = result ? " success!" : " failure!";
        message += retStr;
        mProviderResult.setText(message);
    }
}
