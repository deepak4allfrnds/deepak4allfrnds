package com.bhavesh.surveyapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.cnx.surveyapp.R;


public class LoadingDialog {

    Context ctx;
    ProgressDialog progressDialog;

    public LoadingDialog(Context ctx) {
        this.ctx = ctx;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle(R.string.loading);
    }

    public void show() {
        progressDialog.show();
    }

    public void hide() {
        progressDialog.hide();
    }
}
