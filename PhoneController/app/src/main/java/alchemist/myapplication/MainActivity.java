package alchemist.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Boolean Connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Connect(View v){
        //Button ConnectButton = (Button)findViewById(R.id.ConnectButton);
        Button ConnectButton = (Button) v;

        if(Connected){
            ConnectButton.setText("Disconnect");
        }
        //ConnectButton.setClickable(false);
    }
}
