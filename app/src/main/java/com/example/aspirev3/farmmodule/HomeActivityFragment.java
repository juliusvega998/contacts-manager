package com.example.aspirev3.farmmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    public HomeActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final Button contacts_button = (Button) view.findViewById(R.id.contacts_button);
        final Button add_contacts_button = (Button) view.findViewById(R.id.add_contact_button);
        final Button logout_button = (Button) view.findViewById(R.id.logout_button);

        final TextView title = (TextView) view.findViewById(R.id.title);
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String username = settings.getString("currUsername", null);
        String greeting = "Hello " + username + "!";

        title.setText(greeting);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.logout(getActivity());
            }
        });

        add_contacts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        contacts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewContactActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
