package com.example.yahya.stt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE = 1;
    private TextView message;
    private ImageButton record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        record = (ImageButton) findViewById(R.id.imageButton);
        message = (TextView) findViewById(R.id.textView);

        System.out.println("Locale.getDefault() "+ Locale.getDefault());

    }
    public void speachtotextClick(View view){
        //request component from speech recognition
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        //required for the intent.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //Optional


        //check if the language of the device is arabic then make reqognizer arabic otherwise english.
        String dl = Locale.getDefault().toString().charAt(0)+""+Locale.getDefault().toString().charAt(1);
        if(dl.equals("ar")){
            System.out.println("Language is arabic");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar_SA");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"قل شيئا...");
        }else{
            System.out.println("Language is English");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak something...");
        }


        try {
            //start recognizer.
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Sorry! Speech recognition is not supported in this device.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != intent) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    message.setText(text);
                }
                break;
            }
        }
    }

}
