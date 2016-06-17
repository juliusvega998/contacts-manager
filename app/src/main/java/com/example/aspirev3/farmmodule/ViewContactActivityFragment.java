package com.example.aspirev3.farmmodule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aspirev3.farmmodule.adapter.ContactListAdapter;
import com.example.aspirev3.farmmodule.database.DBHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewContactActivityFragment extends Fragment {

    public ViewContactActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences settings = PreferenceManager.
                getDefaultSharedPreferences(getActivity());
        final DBHelper db = new DBHelper(getActivity());
        String username = settings.getString("currUsername", null);
        String[] html_contact_list = db.getHtmlContacts(username);

        View view = inflater.inflate(R.layout.fragment_view_contact, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.contact_list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new ContactListAdapter(getActivity(), db.getContacts(username),
                html_contact_list));
        return view;
    }
}
