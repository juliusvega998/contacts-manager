package com.example.aspirev3.farmmodule;

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
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final DBHelper db = new DBHelper(getActivity());

        Button login = (Button) view.findViewById(R.id.login);
        Button register = (Button) view.findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) view.findViewById(R.id.username_field))
                        .getText().toString().trim();
                String password = ((EditText) view.findViewById(R.id.password_field))
                        .getText().toString();

                if(db.login(username, password) != null) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("username", username);

                    Utility.changeCurrUser(getActivity(), username, true);
                    Utility.showToast(getActivity(), "Log-in success!");

                    startActivity(intent);
                } else {
                    Utility.showToast(getActivity(), "Wrong username or password");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
