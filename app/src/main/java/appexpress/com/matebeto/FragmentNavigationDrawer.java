package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentNavigationDrawer  extends Fragment implements View.OnClickListener {

    private static TextView txtweb, txtfb, txtwts,  txtent, txab, txprof;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        txtweb = (TextView) view.findViewById(R.id.hotdeals);
        txtweb.setOnClickListener(this);

        txtfb = (TextView) view.findViewById(R.id.shop);
        txtfb.setOnClickListener(this);

        txtwts = (TextView) view.findViewById(R.id.insurance);
        txtwts.setOnClickListener(this);

        txtent = (TextView) view.findViewById(R.id.entertainment);
        txtent.setOnClickListener(this);

        txab = (TextView) view.findViewById(R.id.about);
        txab.setOnClickListener(this);

        txprof = (TextView) view.findViewById(R.id.profile);
        txprof.setOnClickListener(this);


        return view;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.hotdeals):
                Intent intent = new Intent(getActivity(), Cars.class);
                startActivity(intent);
                break;

            case (R.id.shop):
                Intent intent2 = new Intent(getActivity(), Cars.class);
                startActivity(intent2);
                break;

            case (R.id.insurance):
                Intent intent3 = new Intent(getActivity(), Cars.class);
                startActivity(intent3);
                break;

            case (R.id.profile):
                Intent intent5 = new Intent(getActivity(), Profile.class);
                startActivity(intent5);
                break;

            case (R.id.about):
                Intent intent4 = new Intent(getActivity(), About.class);
                startActivity(intent4);
                break;
        }
}
}
