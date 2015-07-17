package br.com.garagesoft.notasfaceis;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Green01 on 17/07/2015.
 */
public class CadernosFragment extends Fragment {

    public CadernosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadernos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }
}
