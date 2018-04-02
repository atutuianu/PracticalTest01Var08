package ro.pub.cs.systems.eim.practicaltest01.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01.R;

public class PracticalTest01MainActivity extends Activity {


    private static final int SEC_CODE = 3;

    private EditText riddle, answer;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        playButton = findViewById(R.id.play_button);
        riddle = findViewById(R.id.riddle);
        answer = findViewById(R.id.answer);


        //incarcare fereastra secondary
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(riddle.getText() != null && !riddle.getText().toString().isEmpty()
                        && answer.getText() != null && !answer.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra("riddle", riddle.getText().toString());
                    intent.putExtra("answer", answer.getText().toString());
                    startActivityForResult(intent, SEC_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "Fill the text!", Toast.LENGTH_LONG).show();
                }
            }
        });


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("riddle")) {
                riddle.setText(savedInstanceState.getString("riddle"));
            } else {
                riddle.setText(String.valueOf(""));
            }
            if (savedInstanceState.containsKey("answer")) {
                answer.setText(savedInstanceState.getString("answer"));
            } else {
                answer.setText(String.valueOf(0));
            }
        } else {
            riddle.setText(String.valueOf(""));
            answer.setText(String.valueOf(""));
        }


    }

    //return din fereastra secondary
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 3) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("riddle", riddle.getText().toString());
        savedInstanceState.putString("answer", answer.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("riddle")) {
            riddle.setText(savedInstanceState.getString("riddle"));
        } else {
            riddle.setText(String.valueOf(""));
        }
        if (savedInstanceState.containsKey("answer")) {
            answer.setText(savedInstanceState.getString("answer"));
        } else {
            answer.setText(String.valueOf(""));
        }
    }

}
