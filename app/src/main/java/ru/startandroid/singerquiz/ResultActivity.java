package ru.startandroid.singerquiz;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView1;
    TextView resultTextView2;
    TextView resultTextView3;
    Button btnYes;
    Button btnNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        resultTextView1 = (TextView)findViewById(R.id.resultTextView1);
        resultTextView2 = (TextView)findViewById(R.id.resultTextView2);
        resultTextView3 = (TextView)findViewById(R.id.resultTextView3);
        resultTextView1.setText(getString(R.string.result1));
        resultTextView2.setText(getString(R.string.result2, String.valueOf(getIntent().getIntExtra(SingerFragment.CORRECT_KEY, 0)),
                String.valueOf(getIntent().getIntExtra(SingerFragment.ARG_NUMBER_QUESTIONS, 0))));
        resultTextView3.setText(getString(R.string.result3));


        btnYes = (Button)findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);

        btnNo = (Button)findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnYes:
                setResult(RESULT_OK);
                break;
            case R.id.btnNo:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }




}