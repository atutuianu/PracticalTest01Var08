package ro.pub.cs.systems.eim.practicaltest01.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;
import ro.pub.cs.systems.eim.practicaltest01.service.PracticalTest01Var08Service;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private EditText riddle, answer, attempt;
    private Button checkButton, backButton;


    //punctul D
    private int serviceStatus = Constants.SERVICE_STOPPED;

    //punctul D
    private IntentFilter intentFilter = new IntentFilter();

    //back to main activity
    private ButtonClickListener backButtonListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_button:
                    setResult(RESULT_OK, null);
                    break;
            }
            finish();
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA);
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
            Toast.makeText(getApplicationContext(), receivedText, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }


    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var08Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        checkButton = findViewById(R.id.check_button);
        backButton = findViewById(R.id.back_button);
        attempt = findViewById(R.id.attempt);
        riddle = findViewById(R.id.riddle);
        answer = findViewById(R.id.answer);

        backButton.setOnClickListener(backButtonListener);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("riddle")) {
            String riddleText = intent.getStringExtra("riddle");
            riddle.setText(riddleText);
        }

        if (intent != null && intent.getExtras().containsKey("answer")) {
            String answerText = intent.getStringExtra("answer");
            answer.setText(answerText);
        }

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attempt.getText().toString().equals(answer.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Correct answer!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect answer!", Toast.LENGTH_LONG).show();
                }
            }
        });

        intentFilter.addAction(Constants.ACTION);

        if (serviceStatus == Constants.SERVICE_STOPPED) {
            System.out.println("START SERVICE");
            Intent intent1 = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
            intent1.putExtra("answer", answer.getText().toString());
            getApplicationContext().startService(intent1);
            serviceStatus = Constants.SERVICE_STARTED;
        }
    }
}
