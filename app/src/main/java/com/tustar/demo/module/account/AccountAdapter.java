package com.tustar.demo.module.account;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by tustar on 16-7-6.
 */
public class AccountAdapter extends BaseAdapter {

    private Account[] mAccounts;
    private LayoutInflater mInflater;

    public AccountAdapter(Context context, Account[] accounts) {
        mInflater = LayoutInflater.from(context);
        mAccounts = accounts;
    }

    @Override
    public int getCount() {
        return mAccounts == null ? 0 : mAccounts.length;
    }

    @Override
    public Account getItem(int position) {
        return mAccounts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new AccountHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AccountHolder) convertView.getTag();
        }

        holder.updateData(getItem(position));

        return convertView;
    }

    class AccountHolder {
        TextView name;
        TextView type;

        public AccountHolder(View view) {
            name = (TextView) view.findViewById(android.R.id.text1);
            type = (TextView) view.findViewById(android.R.id.text2);
        }

        public void updateData(Account account) {
            name.setText(account.name);
            type.setText(account.type);
        }
    }
}
