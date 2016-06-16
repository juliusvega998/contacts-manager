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
import android.widget.EditText;

import com.example.aspirev3.farmmodule.database.DBHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddContactActivityFragment extends Fragment {

    public AddContactActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        final Button add_contact = (Button) view.findViewById(R.id.add_contact_button);
        final DBHelper db = new DBHelper(getActivity());

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String username = settings.getString("currUsername", null);
                String name = ((EditText) view.findViewById(R.id.name_add))
                        .getText().toString().trim();
                int age = Integer.parseInt(((EditText) view.findViewById(R.id.age_add))
                        .getText().toString());

                if(username == null) {
                    Utility.logout(getActivity());
                    Utility.showToast(getActivity(), "Forcing a logout...");
                } else if(db.addContact(username, name, age)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("username", username);

                    Utility.showToast(getActivity(), "Successfully added a contact!");
                    startActivity(intent);
                } else {
                    Utility.showToast(getActivity(), "Contact already exists.");
                }
            }
        });


        return view;
    }
}
