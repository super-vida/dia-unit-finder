package cz.prague.vida.dia.units;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ProgressBar;

import cz.prague.vida.dia.units.UnitFinder.R;

import java.util.ArrayList;

public class UnitFinderActivity extends AppCompatActivity implements
        RecognitionListener {

    private static final int REQUEST_RECORD_PERMISSION = 100;
    private TextView returnedText;
    private ImageButton recordButton;
    private Button resetButton;
    private ImageButton shareButton;
    private String currentPartialText;
    private String currentConfirmedText;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private ProgressBar progressBar;
    private String LOG_TAG = "SpeechRecognizer";
    Boolean recordButtonStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //returnedText = (TextView) findViewById(R.id.textView);
        recordButton = (ImageButton) findViewById(R.id.recordButton);
        recordButtonStatus = false;
        currentPartialText = "";
        currentConfirmedText = "";

        progressBar = (ProgressBar) findViewById(R.id.progressbar);




        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
//                "cs-CZ");

        String languagePref = "cs";//or, whatever iso code...
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);

//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recordButtonStatus) {
                    recordButtonStatus = false;
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    recordButton.setBackground(getDrawable(R.drawable.microphone_button_off));
                } else {
                    ActivityCompat.requestPermissions
                            (UnitFinderActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    REQUEST_RECORD_PERMISSION);
                    recordButtonStatus = true;
                    recordButton.setBackground(getDrawable(R.drawable.microphone_button_on));
                }
            }


        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(UnitFinderActivity.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            recordButtonStatus = false;
            recordButton.setBackground(getDrawable(R.drawable.microphone_button_off));
            Log.i(LOG_TAG, "destroy");
        }
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        recordButtonStatus = false;
        recordButton.setBackground(getDrawable(R.drawable.microphone_button_off));
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        recordButtonStatus = false;
        recordButton.setBackground(getDrawable(R.drawable.microphone_button_off));
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");

    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "Results");

    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "Ready For Speech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "Results");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = matches.get(0);
        Log.i(LOG_TAG, text);
        FoodEntry foodEntry = FoodEntryBuilder.createBuilder().build(text);

        final ListView listView = findViewById(R.id.listView);

       ArrayList arrayList = (ArrayList) Source.getFoodstuff(foodEntry.getFoodName());

        ArrayList presenterList =  (ArrayList) OutputFormatter.createFormatter().format(Source.getFoodstuff(foodEntry.getFoodName()), foodEntry);

        if (presenterList.isEmpty()){
            Log.d(LOG_TAG, "Nenalezeno!");
            Toast.makeText(this, "Potravina nebyla nenalezena!", Toast.LENGTH_SHORT).show();
        }
        else {
            FoodListAdapter arrayAdapter = new FoodListAdapter(this, R.layout.food_table_layout, presenterList);
            listView.setAdapter(arrayAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(listView,width, height, focusable);
                    View popupView = new TextView(UnitFinderActivity.this);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

                    popupView.setBackgroundColor(Color.BLUE);
                    ((TextView) popupView).setTextColor(Color.WHITE);

                    StringBuilder sb = new StringBuilder();
                    Foodstuff fs = ((FoodListAdapter)arg0.getAdapter()).getItem(0).getFoodstuff();
                    sb.append("Skupina:\n");
                    sb.append(fs.getGroup());
                    sb.append("\n");
                    sb.append("Jídlo:\n");
                    sb.append(fs.getName());
                    sb.append("\n");
                    sb.append("Poznámka:\n");
                    sb.append(fs.getDescription());
                    sb.append("\n");

                    ((TextView) popupView).setText(sb.toString());
                    ((TextView) popupView).setMinWidth(500);
                    ((TextView) popupView).setMinHeight(300);
                    popupView.setPadding(20,20,20,20);
                    popupWindow.setContentView(popupView);
                    popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 400);
//                    Toast.makeText(UnitFinderActivity.this, "Permission Denied!", Toast
//                            .LENGTH_SHORT).show();

                }

            });

        }
       // returnedText.setText(OutputFormatter.createFormatter().format(Source.getFoodstuff(foodEntry.getFoodName()), foodEntry));
        recordButtonStatus = false;
        recordButton.setBackground(getDrawable(R.drawable.microphone_button_off));
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "RMS Changed: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "ERROR: There was an error recording audio.";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "ERROR: There was an error with the Client.";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "ERROR:  You need to accept permissions first.  Please go to your phone Settings -> Apps -> Speech to Text and accept.";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "ERROR:  There was a Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "ERROR: There was a Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "ERROR: Bohužel jsem nic nenašel.";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "ERROR: RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "ERROR:  A Server error occurred";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "ERROR: I didn't quite catch that";
                break;
            default:
                message = "Hmm, Nejsem si jistý, zkuste to prosím ještě jednou.";
                break;
        }
        return message;
    }


}
