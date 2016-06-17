package com.example.aspirev3.farmmodule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class EditContactsActivityFragment extends Fragment {

    public EditContactsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_contacts, container, false);
        final Intent intent = getActivity().getIntent();

        final Button go_back = (Button) view.findViewById(R.id.go_back);
        final Button edit_contact = (Button) view.findViewById(R.id.edit_contact_button);
        final Button delete_contact = (Button) view.findViewById(R.id.delete_contact_button);
        final EditText name_field = (EditText) view.findViewById(R.id.name_edit);
        final EditText age_field = (EditText) view.findViewById(R.id.age_edit);

        final String oldName = intent.getExtras().getString("name", "");
        final String oldAge = intent.getExtras().getString("age", "");

        name_field.setText(oldName);
        age_field.setText(oldAge);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                    .setTitle("Deleting a contact")
                    .setMessage("Are you sure you want to delete " + oldName + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper db = new DBHelper(getActivity());
                            Intent intent = new Intent(getActivity(), ViewContactActivity.class);
                            String username = Utility.getUsername(getActivity());
                            db.deleteContact(username, oldName, oldAge);
                            Utility.showToast(getActivity(), "Deleted contact successfully!");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
            }
        });

        edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getActivity());
                Intent intent = new Intent(getActivity(), ViewContactActivity.class);

                String username = Utility.getUsername(getActivity());
                String newName = name_field.getText().toString();
                String newAge = age_field.getText().toString();

                db.editContact(username, oldName, oldAge, newName, newAge);
                Utility.showToast(getActivity(), "Updated contact successfully!");
                startActivity(intent);
            }
        });

        return view;
    }
}
