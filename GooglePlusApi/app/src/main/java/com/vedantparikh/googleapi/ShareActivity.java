package com.vedantparikh.googleapi;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.common.api.GoogleApiClient;


public class ShareActivity extends Activity  {

    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        shareButton = (Button) findViewById(R.id.share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new PlusShare.Builder(v.getContext())
                        .setType("text/plain")
                        .setText(((EditText) findViewById(R.id.editText)).getText())
                        .getIntent();

                startActivityForResult(shareIntent, 0);
            }
        });

    }

}
