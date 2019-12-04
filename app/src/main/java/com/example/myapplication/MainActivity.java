package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button translate,scan_text;
    ImageButton speak,pronounce;
    EditText enter_text;
    TextView view_for_tanslatedtext;
    String lan="";
    FirebaseTranslator LanguageTranslator;
    TextToSpeech textToSpeech;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Map<String, Integer> map = new HashMap<>();
    List<String> items=new ArrayList<>();
    List<String> k_lang=new ArrayList<>();
    int pos =0;
    int position=0;
    int p=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translate = findViewById(R.id.button);
        enter_text = findViewById(R.id.editText);
        view_for_tanslatedtext = findViewById(R.id.textView);
        scan_text = findViewById(R.id.button2);
        speak =findViewById(R.id.button4);
        pronounce=findViewById(R.id.button5);
       // fetch = findViewById(R.id.button3);

        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        String text = enter_text.getText().toString();

//        getActionBar().hide();
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        items.add("Afrikaans");
        items.add("Arabic");
        items.add( "Belorussian");
        items.add( "Bulgarian");
        items.add( "Bengali");
        items.add( "Catalan");
        items.add( "Czech");
        items.add("Welsh");
        items.add( "Danish");
        items.add( "German");
        items.add( "Greek");
        items.add( "English");
        items.add( "Esperanto");
        items.add( "Spanish");
        items.add("Estonian");
        items.add( "Persian");
        items.add( "Finnish");
        items.add( "French");
        items.add("Irish");
        items.add("Galician");
        items.add( "Gujarati");
        items.add( "Hebrew");
        items.add( "Hindi");
        items.add("Croatian");
        items.add("Haitian");
        items.add( "Hungarian");
        items.add( "Indonesian");
        items.add( "Icelandic");
        items.add( "Italian");
        items.add( "Japanese");
        items.add("Georgian");
        items.add( "Kannada");
        items.add( "Korean");
        items.add( "Lithuanian");
        items.add( "Latvian");
        items.add( "Macedonian");
        items.add( "Marathi");
        items.add( "Malay");
        items.add("Maltese");
        items.add("Dutch");
        items.add( "Norwegian");
        items.add( "Polish");
        items.add( "Portuguese");
        items.add( "Romanian");
        items.add( "Russian");
        items.add( "Slovak");
        items.add("Slovenian");
        items.add( "Albanian");
        items.add( "Swedish");
        items.add("Swahili");
        items.add( "Tamil");
        items.add( "Telugu");
        items.add( "Thai");
        items.add("Taglog");
        items.add("Turkish");
        items.add( "Ukrainian");
        items.add("Urdu");
        items.add( "Vietnamese");
        items.add( "Chinese");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
    spinner.setAdapter(dataAdapter);


        map.put("AF", 0);
        map.put("AR", 1);
        map.put("BE", 2);
        map.put("BG", 3);
        map.put("BN", 4);
        map.put("CA", 5);
        map.put("CS", 6);
        map.put("CY", 7);
        map.put("DA", 8);
        map.put("DE", 9);
        map.put("EL", 10);
        map.put("EN", 11);
        map.put("EO", 12);
        map.put("ES", 13);
        map.put("ET", 14);
        map.put("FA", 15);
        map.put("FI", 16);
        map.put("FR", 17);
        map.put("GA", 18);
        map.put("GL", 19);
        map.put("GU", 20);
        map.put("HE", 21);
        map.put("HR", 23);
        map.put("HT", 24);
        map.put("HU", 25);
        map.put("ID", 26);
        map.put("IS", 27);
        map.put("IT", 28);
        map.put("JA", 29);
        map.put("KA", 30);
        map.put("KN", 31);
        map.put("KD", 32);
        map.put("LT", 33);
        map.put("LV", 34);
        map.put("MK", 35);
        map.put("MR", 36);
        map.put("MS", 37);
        map.put("MT", 38);
        map.put("NL", 39);
        map.put("NO", 40);
        map.put("PL", 41);
        map.put("PT", 42);
        map.put("RO", 43);
        map.put("RU", 44);
        map.put("SK", 45);
        map.put("SL", 46);
        map.put("SQ", 47);
        map.put("SV", 48);
        map.put("SW", 49);
        map.put("TA", 50);
        map.put("TE", 51);
        map.put("TH", 52);
        map.put("TL", 53);
        map.put("TR", 54);
        map.put("UK", 55);
        map.put("UR", 56);
        map.put("VI", 57);
        map.put("ZH", 58);


        //language configuration

        Intent in=getIntent();
        String result=in.getStringExtra("message");
        enter_text.setText(result);
        //translating language
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyLanguage();
            }
        });



// accessing text from an image
        scan_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //speak();
                speech_to_text();
            }
        });

        pronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, position+"  "+String.format("%s", items.get(position)),Toast.LENGTH_SHORT).show();
        pos=position;
        //return p;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void identifyLanguage()
        {
            FirebaseLanguageIdentification languageIdentifier =
                    FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
            languageIdentifier.identifyLanguage(enter_text.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(@Nullable String languageCode) {
                                    if (languageCode != "und") {
                                        // Log.i(TAG, "Language: " + languageCode);
                                        lan = languageCode.toUpperCase();
                                       // pos=Posit();
                                       // Toast.makeText(MainActivity.this,""+pos,Toast.LENGTH_SHORT).show();
                                        translate(lan);
                                        //Toast.makeText(MainActivity.this,""+pos,Toast.LENGTH_SHORT).show();
                                        Toast.makeText(MainActivity.this, "language code is  "+lan, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(MainActivity.this, "Language is undetermined !!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                 //   lan="EN";
                                    // Model couldn’t be loaded or other internal error.
                                    // ...
                                  //  Toast.makeText(MainActivity.this, "Failed to recognize text in image!!", Toast.LENGTH_LONG).show();
                                }
                            });



        }

        private void translate(String lan_code) {
          // int a= posit();
            //
            // Toast.makeText(MainActivity.this,pos,Toast.LENGTH_SHORT).show();
          try {

             //
           //   Toast.makeText(MainActivity.this,position,Toast.LENGTH_SHORT).show();
              FirebaseTranslatorOptions options =
                      new FirebaseTranslatorOptions.Builder()
                              .setSourceLanguage(map.get(lan))
                              .setTargetLanguage(pos)
                              .build();

              LanguageTranslator =
                      FirebaseNaturalLanguage.getInstance().getTranslator(options);

              FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                      .build();
              LanguageTranslator.downloadModelIfNeeded(conditions)
                      .addOnSuccessListener(
                              new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void v) {
                                      Toast.makeText(MainActivity.this, "Successfully Translated !!", Toast.LENGTH_SHORT).show();

                                      setlanguage();
                                  }
                              })
                      .addOnFailureListener(
                              new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {

                                  }
                              });
          }
          catch (Exception e)
          {
              Toast.makeText(MainActivity.this, "Translation not available", Toast.LENGTH_SHORT).show();
          }

        }

        private void setlanguage() {
            LanguageTranslator.translate(enter_text.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(@NonNull String translatedText) {
                                    view_for_tanslatedtext.setText(translatedText);
                                    view_for_tanslatedtext.setTextSize(16);

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "can't determine Language!!", Toast.LENGTH_SHORT).show();

                                }
                            });
        }

        private void speech_to_text()
        {
            Intent voice_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            voice_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            voice_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
            voice_intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));
            try
                {
                    startActivityForResult(voice_intent,REQ_CODE_SPEECH_INPUT);
                }
                catch(Exception e)
                {

                }
            }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    enter_text.setText(result.get(0));
                }
                break;
            }

        }
    }


    private void speak()
    {
        String text = enter_text.getText().toString();
        //Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }





}
