package com.cristian.botondeincendios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView nivelEmer;

    int nivelEmergencia=0;
    boolean[] valores = new boolean[3];

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {

                        //httpHandler handler = new httpHandler();
                        // String txt = handler.post("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events");
                        OkHttpClient client = new OkHttpClient();

                        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"family\"\r\n\r\nCALIDOSOS3\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"type\"\r\n\r\nCALIDOSOS3TYPE\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"version\"\r\n\r\n1.0\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"eventBody\"\r\n\r\n{\"phoneS\":\"+1 682 499 1326\", \"phoneC\":\"2315\", \"phoneID\":\"12345678\"}\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                        Request request = new Request.Builder()
                                .url("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events")
                                .post(body)
                                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("cache-control", "no-cache")
                                .addHeader("Postman-Token", "dbf8f04f-966e-4cd9-9ac5-eff1913344ff")
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Do network action in this function
                    }
                }).start();
            }
        });

        for(int i = 0; i<3; i++){
            valores[i]=false;
        }

        final SeekBar[] seekBar = new SeekBar[3];


        seekBar[0] = findViewById(R.id.seekBar1);
        seekBar[0].setProgress(50);
        seekBar[1] = findViewById(R.id.seekBar2);
        seekBar[1].setProgress(50);
        seekBar[2] = findViewById(R.id.seekBar3);
        seekBar[2].setProgress(50);

        final TextView label1 = findViewById(R.id.label1);
        final TextView label2 = findViewById(R.id.label2);
        final TextView label3 = findViewById(R.id.label3);

        nivelEmer = findViewById(R.id.label4);

        seekBar[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label1.setText("el Sensor de temperatura registra" + progress + "°c");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                evaluarSensor(seekBar1.getProgress(), 0, seekBar);
            }
        });
        seekBar[1].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label2.setText("el Sensor de temperatura registra" + progress + "°c");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                evaluarSensor(seekBar1.getProgress(), 1, seekBar);

            }
        });

        seekBar[2].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label3.setText("el Sensor de temperatura registra" + progress + "°c");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                evaluarSensor(seekBar1.getProgress(), 2,seekBar);

            }
        });


    }

    public void evaluarSensor(int vsensor, int numsensor, SeekBar [] seekBar) {

        for(int i = 0; i<3; i++){
            if(!valores[i] && seekBar[i].getProgress()>=75){
                nivelEmergencia++;
            }
            else if(nivelEmergencia>0 && valores[i] && seekBar[i].getProgress()<75){
                nivelEmergencia--;
            }
        }


        for(int i = 0; i<3; i++){
            if(seekBar[i].getProgress()>=75){
                valores[i]=true;
            }else{
                valores[i]=false;
            }
        }

        nivelEmer.setText("nivel de emergencia " + nivelEmergencia);

        if(nivelEmergencia==3){
            new Thread(new Runnable(){
                @Override
                public void run() {

                    //httpHandler handler = new httpHandler();
                    // String txt = handler.post("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events");
                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                    RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"family\"\r\n\r\nCALIDOSOS2\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"type\"\r\n\r\nCALIDOSOS2TYPE\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"version\"\r\n\r\n1.0\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"eventBody\"\r\n\r\n{\"phoneS\":\"+1 682 499 1326\", \"phoneC\":\"2315\", \"phoneID\":\"12345678\"}\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                    Request request = new Request.Builder()
                            .url("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events")
                            .post(body)
                            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Postman-Token", "dbf8f04f-966e-4cd9-9ac5-eff1913344ff")
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Do network action in this function
                }
            }).start();
            Toast.makeText(getApplicationContext(),
                    "¡Se generó alarma de evacuación!", Toast.LENGTH_SHORT).show();
        }
        else if(nivelEmergencia>0 && nivelEmergencia<3){
            new Thread(new Runnable(){
                @Override
                public void run() {

                    //httpHandler handler = new httpHandler();
                    // String txt = handler.post("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events");
                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                    RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"family\"\r\n\r\nCALIDOSOS1\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"type\"\r\n\r\nCALIDOSOS1TYPE\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"version\"\r\n\r\n1.0\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"eventBody\"\r\n\r\n{\"phoneS\":\"+1 682 499 1326\", \"phoneC\":\"2315\", \"phoneID\":\"12345678\"}\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                    Request request = new Request.Builder()
                            .url("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events")
                            .post(body)
                            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Postman-Token", "dbf8f04f-966e-4cd9-9ac5-eff1913344ff")
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Do network action in this function
                }
            }).start();
            Toast.makeText(getApplicationContext(),
                    "Se envió notificaciones al encargado de seguridad", Toast.LENGTH_SHORT).show();
        }

    }

//    public void notificar(final String eventType){
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//
//                //httpHandler handler = new httpHandler();
//                // String txt = handler.post("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events");
//                OkHttpClient client = new OkHttpClient();
//
//                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
//                RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"family\"\r\n\r\n"+eventType+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"type\"\r\n\r\n\"+eventType+\"TYPE\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"version\"\r\n\r\n1.0\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"eventBody\"\r\n\r\n{\"phoneS\":\"+1 682 499 1326\", \"phoneC\":\"2315\", \"phoneID\":\"12345678\"}\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
//                Request request = new Request.Builder()
//                        .url("http://breeze2-194.collaboratory.avaya.com/services/EventingConnector/events")
//                        .post(body)
//                        .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("Postman-Token", "dbf8f04f-966e-4cd9-9ac5-eff1913344ff")
//                        .build();
//
//                try {
//                    Response response = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // Do network action in this function
//            }
//        }).start();
//    }
}
