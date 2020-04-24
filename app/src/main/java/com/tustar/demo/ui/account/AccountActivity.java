package com.tustar.demo.ui.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tustar.util.Logger;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends BaseActivity {

    private static final String TAG = "AccountActivity";

    private ListView mAccountListView;
    private AuthenticatorDescription[] mAuthDescs;
    private Map<String, AuthenticatorDescription> mTypeToAuthDescription = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAccountListView = (ListView) findViewById(R.id.account_listView);
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        mAuthDescs = am.getAuthenticatorTypes();
        for (int i = 0; i < mAuthDescs.length; i++) {
            mTypeToAuthDescription.put(mAuthDescs[i].type, mAuthDescs[i]);
        }

        AccountAdapter adapter = new AccountAdapter(this, accounts);
        mAccountListView.setAdapter(adapter);

        View emptyView = View.inflate(this, R.layout.item_empty, null);
        addContentView(emptyView, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        mAccountListView.setEmptyView(emptyView);
    }

    protected CharSequence getLabelForType(final String accountType) {
        CharSequence label = null;
        if (mTypeToAuthDescription.containsKey(accountType)) {
            try {
                AuthenticatorDescription desc = mTypeToAuthDescription
                        .get(accountType);
                Context authContext = createPackageContext(desc.packageName, 0);
                label = authContext.getResources().getText(desc.labelId);
            } catch (PackageManager.NameNotFoundException e) {
                Logger.w(TAG, "No label name for account type " + accountType);
            } catch (Resources.NotFoundException e) {
                Logger.w(TAG, "No label resource for account type " + accountType);
            }
        }
        return label;
    }
}
