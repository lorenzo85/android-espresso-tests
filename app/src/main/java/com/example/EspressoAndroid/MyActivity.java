package com.example.EspressoAndroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MyActivity extends RoboActivity {

    @InjectView(R.id.testView1)
    private TextView textView;
    @Inject
    private MyService service;

    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onResume() {
        super.onResume();
        String message = service.doSomething("Lorenz");

        if(dialog == null) {
            dialog = createDialog("Alert!!", message);
        } else {
            dialog.hide();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(dialog.isShowing()) dialog.hide();
    }

    public void doSomething(View view) {
        textView.setText("Yee");
    }

    public void doOpen(View view) {
       dialog.show();
    }

    private Dialog createDialog(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(text);
        return builder.create();
    }
}
