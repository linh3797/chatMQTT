package com.vccorp.chatmqtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vccorp.chatmqtt.databinding.ActivityMainBinding;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    private MqttAndroidClient client;
    private String MQTTHOST = "tcp://soldier.cloudmqtt.com:11601";
    private String USERNAME = "qctunbtk";
    private String PASSWORD = "KNyICLuObvTp";
    private String topicStr = "sensor/+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clientId = MqttClient.generateClientId();
                client = new MqttAndroidClient(MainActivity.this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);

                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Log.d(TAG, "onSuccess");
                            Toast.makeText(MainActivity.this, "Connect success!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Log.d(TAG, "onFailure");
                            Toast.makeText(MainActivity.this, "Connect failed!", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
//        String clientId = MqttClient.generateClientId();
//        client =
//                new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,
//                        clientId);
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
//        options.setUserName(USERNAME);
//        options.setPassword(PASSWORD.toCharArray());
//
//        try {
//            IMqttToken token = client.connect(options);
//            token.setActionCallback(new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Toast.makeText(MainActivity.this, "Connect Succesful", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Toast.makeText(MainActivity.this, "Connect failed!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }


    }

    public void publish() {
//        String topic = topicStr;
//        String mesage = "hello world from VietNam";
//        try {
//            client.publish(topic, mesage.getBytes(), 0, false);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    Toast.makeText(MainActivity.this, "Connect success!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                    Toast.makeText(MainActivity.this, "Connect failed!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
