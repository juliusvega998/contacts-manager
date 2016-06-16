package com.example.aspirev3.farmmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.aspirev3.farmmodule.database.DBHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterActivityFragment extends Fragment {

    public RegisterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        final Button register = (Button) view.findViewById(R.id.register);
        final CheckBox accept = (CheckBox) view.findViewById(R.id.accept);
        final DBHelper db = new DBHelper(getActivity());

        register.setEnabled(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) view.findViewById(R.id.username_reg))
                        .getText().toString().trim();
                String password = ((EditText) view.findViewById(R.id.password_reg))
                        .getText().toString();
                String cpassword = ((EditText) view.findViewById(R.id.cpassword_reg))
                        .getText().toString();

                if(username.equals("") || password.equals("")){
                    Utility.showToast(getActivity(), "A field was left empty.");
                }
                else if(!password.equals(cpassword)) {
                    Utility.showToast(getActivity(), "Passwords does not match.");
                }
                else if (db.register(username, password)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("username", username);

                    Utility.changeCurrUser(getActivity(), username, true);

                    Utility.showToast(getActivity(), "Registered!");
                    startActivity(intent);
                } else {
                    Utility.showToast(getActivity(), "Username exists.");
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.setEnabled(accept.isChecked());
            }
        });

        return view;
    }
}
