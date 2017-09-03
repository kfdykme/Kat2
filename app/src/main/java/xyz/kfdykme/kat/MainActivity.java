package xyz.kfdykme.kat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import xyz.kfdykme.kat.basic.KatActivity;

public class MainActivity extends KatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(mOnClickListener);

    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_call:
                    onCall();
                    break;
            }
        }
    };

    private void onCall(){
        showToast("onCall");

    }
}
