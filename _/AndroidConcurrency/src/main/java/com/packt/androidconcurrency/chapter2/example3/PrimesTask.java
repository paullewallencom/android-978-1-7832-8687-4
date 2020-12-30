package com.packt.androidconcurrency.chapter2.example3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.packt.androidconcurrency.R;

import java.math.BigInteger;

public class PrimesTask extends AsyncTask<Integer, Integer, BigInteger> {
    private Context ctx;
    private ProgressDialog progress;
    private TextView resultView;

    public PrimesTask(Context ctx, TextView resultView) {
        this.ctx = ctx;
        this.resultView = resultView;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(ctx);
        progress.setTitle(R.string.calculating);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.setMax(100);
        progress.show();
    }

    @Override
    protected BigInteger doInBackground(Integer... params) {
        int primeToFind = params[0];
        BigInteger prime = new BigInteger("2");
        for (int i=0; i<primeToFind; i++) {
            prime = prime.nextProbablePrime();
            int percentComplete = (int)((i * 100f)/primeToFind);
            publishProgress(percentComplete);
        }
        return prime;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(BigInteger result) {
        resultView.setText(result.toString());
        progress.dismiss();
    }
}
