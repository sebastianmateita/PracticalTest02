package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {
    public ServerThread serverThread;
    public ClientThread clientThread;

    private Button setAlarm, connect;
    public EditText serverPortEditText;

    private class SetAlarmListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            clientThread = new ClientThread("localhost", 5000);
            clientThread.start();
        }

    }

    private class Connect implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.serverSocket == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        setAlarm = findViewById(R.id.set_alarm_button);
        setAlarm.setOnClickListener(new SetAlarmListener());

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        connect = findViewById(R.id.connect_button);

        connect.setOnClickListener(new Connect());
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}

