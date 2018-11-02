package alchemist.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.SeekBar;

import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    Boolean Connected;
    DataOutputStream ToServer;
    Socket client;

    final static String IP = "fe80...TODO";
    final static int PORT = 2097;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Connect(View v){
        //Button ConnectButton = (Button)findViewById(R.id.ConnectButton);
        Button ConnectButton = (Button) v;

        if(!Connected) {
            try {
                client = new Socket(IP, PORT);

                OutputStream outToServer = client.getOutputStream();
                ToServer = new DataOutputStream(outToServer);
                ToServer.writeUTF("Heyo" + "\n");
                Connected = True;
                ConnectButton.setText("Disconnect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                client.close();
                Connected = False;
                ConnectButton.setText("Connect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //ConnectButton.setClickable(false);
    }

    public void ChangeSpeed(View v){
        SeekBar SpeedBar = (SeekBar) v;
        int speed = SpeedBar.getProgress();
        if(Connected){
            try {
                ToServer.writeUTF("Forward"+speed);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            SpeedBar.setProgress(0);
        }
    }

    public void Turn(View v){
        SeekBar TurnBar = (SeekBar) v;
        int speed = TurnBar.getProgress()-100;
        if(Connected){
            try {
                ToServer.writeUTF("Turn"+speed);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            TurnBar.setProgress(0);
        }
    }
}
