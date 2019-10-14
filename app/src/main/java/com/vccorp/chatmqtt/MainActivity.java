package com.vccorp.chatmqtt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vccorp.chatmqtt.databinding.ActivityMainBinding;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    private MqttAndroidClient client;
    private String MQTTHOST = "tcp://soldier.cloudmqtt.com:11601";
    private String USERNAME1 = "qctunbtk";
    private String PASSWORD1 = "KNyICLuObvTp";
    private String MQTTHOST1 = "tcp://broker.mqttdashboard.com:8000";
    private String MQTTHOST2 = "tcp://soldier.cloudmqtt.com:10521";
    private String MQTTHOST3 = "tcp://broker.hivemq.com:1883";
    private String USERNAME2 = "jagcqoeu";
    private String PASSWORD2 = "MUy10CiATiMW";
    private String USERNAME = "linh";
    private String PASSWORD = "123456";
    private String topicStr ;
    private MqttConnectOptions options;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        list.add("foo/bar");
        list.add("yellow/white");
        list.add("blue/black");
        Intent intent = getIntent();
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this, MQTTHOST3,
                clientId);
        Log.d(TAG, "onCreate: " + clientId);
        options = new MqttConnectOptions();
        options.setUserName(intent.getStringExtra("USERNAME"));
        options.setPassword(intent.getStringExtra("PASSWORD").toCharArray());
        topicStr = "foo/" + intent.getStringExtra("USERNAME");

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "Connect succesful", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess");
                    setSubcription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "Connect failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure" + exception.getMessage());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        binding.pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pub();
            }
        });
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                binding.subcriptions.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void setSubcription() {
        try {
            client.subscribe(topicStr, 2);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pub() {
        String topic = topicStr;
        String payload = "Hello world from the world ";
        try {
            client.publish(topic, payload.getBytes(), 0, false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }


}
