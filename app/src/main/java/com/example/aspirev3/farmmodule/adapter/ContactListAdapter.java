package com.example.aspirev3.farmmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aspirev3.farmmodule.R;
import com.example.aspirev3.farmmodule.Utility;

/**
 * Created by Aspire V3 on 6/17/2016.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private String[] mContacts;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.contact);
        }
    }

    public ContactListAdapter(Context context, String[] contacts) {
        this.mContacts = contacts;
        this.mContext = context;
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_text_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(Html.fromHtml(mContacts[position]));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showToast(mContext, Integer.toString(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.length;
    }
}
