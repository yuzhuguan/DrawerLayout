package com.carefree.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by CoderF1 on 2017/11/20.
 */

public class FirstFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        TextView textView = view.findViewById(R.id.content);
        textView.setText("fragment 1");
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }
}
