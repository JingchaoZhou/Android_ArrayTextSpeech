package com.example.zchao.texttospeech;

import android.app.Activity;
import android.os.Bundle;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private Button speechButton;
    private EditText speechText;
    private TextToSpeech tts;
    private Button save;
    private ListView show;
    private ArrayList<String> addArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //初始化TTS
        tts = new TextToSpeech(this,new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                // 判断是否转化成功
                if (status == TextToSpeech.SUCCESS) {
                    //默认设定语言为中文
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("tts", "language not supported");
                    } else {
                        //不支持中文就将语言设置为英文
                        tts.setLanguage(Locale.US);
                    }
                }
            }
        });
        //获取控件
        speechText = findViewById(R.id.edit_text);
        speechText.setFocusable(false); // disable editing
        speechButton = findViewById(R.id.button_speak);
        show = findViewById(R.id.listView);
//        save = findViewById(R.id.save_forlater);
//        save.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String getInput = speechText.getText().toString();
//
//                if(addArray.contains(getInput)){
//                    Toast.makeText(getBaseContext(),"Added",Toast.LENGTH_LONG).show();
//                }
//                else if (getInput == null || getInput.trim().equals("")) {
//                    Toast.makeText(getBaseContext(),"Empty",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    addArray.add(getInput);
//                    ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,addArray);
//                    show.setAdapter(adapter);
//                    ((EditText)findViewById(R.id.edit_text)).setText(" ");
//                }
//            }
//        });
        //为button添加监听


        speechButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO Auto-generated method stub
                loop();

            }
        });
    }

    private void loop(){
        String test = "There are many problems associated with training dogs: the cost of training guide dog is high; not every dog can be a guide dog; the average training time is about one year; a guide dog requires care, food and love; a guide dog is only able to serve for about 12 years; a guide dog cannot guide people to a specific address. Therefore, we come up with the idea of Mobile Voice Assistant aiming to substitute the guide dogs.";
        //String[] blocks = test.split(" ");
        //addArray.addAll(Arrays.asList(blocks));
        addArray.add(test);
        for (String i : addArray) {
            tts.speak(i,TextToSpeech.QUEUE_ADD, null);
            tts.playSilence(10, TextToSpeech.QUEUE_ADD, null);
        }

    }


}
