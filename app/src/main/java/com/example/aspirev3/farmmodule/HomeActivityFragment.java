package com.example.aspirev3.farmmodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView greet = (TextView) view.findViewById(R.id.greetings);
        String username = getActivity().getIntent().getExtras().getString("username");
        String greeting = "Hello " + username + "!";
        greet.setText(greeting);

        return view;
    }
}
