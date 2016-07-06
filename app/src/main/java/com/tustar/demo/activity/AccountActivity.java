package com.tustar.demo.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tustar.demo.R;
import com.tustar.demo.adapter.AccountAdapter;

public class AccountActivity extends BaseActivity {

    private ListView mAccountListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAccountListView = (ListView)findViewById(R.id.account_listView);
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        AccountAdapter adapter = new AccountAdapter(this, accounts);
        mAccountListView.setAdapter(adapter);
        
        View emptyView = View.inflate(this, R.layout.global_empty_layout, null);
        addContentView(emptyView, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        mAccountListView.setEmptyView(emptyView);
    }
}
