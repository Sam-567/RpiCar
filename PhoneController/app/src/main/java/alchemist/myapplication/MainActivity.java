package alchemist.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.*;
import android.os.StrictMode;

import java.net.*;
import java.io.*;


public class MainActivity extends AppCompatActivity implements OnSeekBarChangeListener{

    Boolean Connected = false;
    DataOutputStream ToServer;
    Socket client;

    //final static String IP = "fe80::ba27:ebff:fed1:971%20";
    final static String IP = "192.168.0.1";
    final static int PORT = 2079;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar Speed = (SeekBar) findViewById(R.id.seekBar3);
        SeekBar Turn = (SeekBar) findViewById(R.id.seekBar);

        Speed.setOnSeekBarChangeListener(this);
        Turn.setOnSeekBarChangeListener(this);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy);


    }

    public void Connect(View v){
        Log.i("Start", "------------------------------------------------------");
        Button ConnectButton = (Button)findViewById(R.id.ConnectButton);
        //Button ConnectButton = (Button) v;

        if(!Connected) {
            //Thread thread = new Thread(new Runnable() {

                //@Override
                //public void run() {
                    try  {
                        client = new Socket(IP, PORT);

                        OutputStream outToServer = client.getOutputStream();
                        ToServer = new DataOutputStream(outToServer);
                        ToServer.writeUTF("Heyo" + "\n");
                        Connected = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            //    }
            //});
            //thread.start();

            if(Connected){
                Log.i("Start", "*************************************************************");
                ConnectButton.setText("Disconnect");
            } else {
                Log.i("Start", "0000000000000000000000000000000000000000000000000000000000000");
            }
        } else {
            try {
                ToServer.writeUTF("Exit");
                wait(200);
                client.close();
                Connected = false;
                ConnectButton.setText("Connect");
            } catch (Exception e) {
                e.printStackTrace();
                ToServer = null;
                client = null;
                Connected = false;
                ConnectButton.setText("Connect");
            }
        }
        //ConnectButton.setClickable(false);
    }

    public void ChangeSpeed(View v){
        SeekBar SpeedBar = (SeekBar) v;
        int speed = SpeedBar.getProgress()-100;
        if(speed <= 5 && speed >= -5){
            speed = 0;
            SpeedBar.setProgress(100);
        }
        Log.i("Speed", Integer.toString(speed));
        if(Connected){
            try {
                ToServer.writeUTF("Forward" + Integer.toString(speed));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            SpeedBar.setProgress(100);
        }
    }

    public void Turn(View v){
        SeekBar TurnBar = (SeekBar) v;
        int Angle = TurnBar.getProgress()-100;
        Log.i("Turn", Integer.toString(Angle));
        if(Connected){
            try {
                ToServer.writeUTF("Turn" + Integer.toString(Angle));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            TurnBar.setProgress(100);
        }
    }

    @Override
    public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seekBar3) {
            ChangeSpeed(seekBar);
        } else if (seekBar.getId() == R.id.seekBar){
            Turn(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar s){
        if (s.getId() == R.id.seekBar){
            s.setProgress(100);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar s){

    }

}
