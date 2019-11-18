package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button translate,add_image;
    EditText enter_text;
    TextView view_for_tanslatedtext;
    String lan="";
    FirebaseTranslator LanguageTranslator;
    Map<String, Integer> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translate = findViewById(R.id.button);
        enter_text = findViewById(R.id.editText);
        view_for_tanslatedtext = findViewById(R.id.textView);
        add_image = findViewById(R.id.button2);
       // fetch = findViewById(R.id.button3);
        String text = enter_text.getText().toString();


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
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });
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
                                        translate(lan);
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

          try {
              FirebaseTranslatorOptions options =
                      new FirebaseTranslatorOptions.Builder()
                              .setSourceLanguage(map.get(lan))
                              .setTargetLanguage(FirebaseTranslateLanguage.HI)
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


}
