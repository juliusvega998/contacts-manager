package com.example.aspirev3.farmmodule;

import android.os.Bundle;
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
        final Button add_contact = (Button) view.findViewById(R.id.edit_contact_button);
        final Button go_back = (Button) view.findViewById(R.id.go_back);
        final DBHelper db = new DBHelper(getActivity());

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Utility.getUsername(getActivity());
                EditText name_field = (EditText) view.findViewById(R.id.name_edit);
                EditText age_field = (EditText) view.findViewById(R.id.age_edit);

                String name = name_field.getText().toString().trim();
                int age = Integer.parseInt(age_field.getText().toString());

                if(username == null) {
                    Utility.logout(getActivity());
                    Utility.showToast(getActivity(), "Forcing a logout...");
                } else if(db.addContact(username, name, age)) {
                    name_field.setText("");
                    age_field.setText("");
                    name_field.requestFocus();
                    Utility.showToast(getActivity(), "Successfully added a contact!");
                } else {
                    Utility.showToast(getActivity(), "Contact already exists.");
                }
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }
}
