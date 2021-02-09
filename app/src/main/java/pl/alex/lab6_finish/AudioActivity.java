package pl.alex.lab6_finish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class AudioActivity extends AppCompatActivity {
    int PERMISSION_REQUEST_CODE = 1;
    String external_music_storage_path;
    Button record;
    Button stop;
    Button play;
    Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        PackageManager pManager = this.getPackageManager();
        if (!pManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            Toast.makeText(getBaseContext(), "No microfon feature", Toast.LENGTH_SHORT);
            onBackPressed();
        }

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        external_music_storage_path = getApplication().getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString();

        record = findViewById(R.id.buttonRecord);
        stop = findViewById(R.id.buttonStop);
        play = findViewById(R.id.buttonPlay);
        pause = findViewById(R.id.buttonPause);
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] grantResults){
        String notAllowedPermissionNames = "";
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                notAllowedPermissionNames += permissions[i] + ";";
            }
        }
        if (!notAllowedPermissionNames.equals("")){
            Toast.makeText(this, "Not allowed permissions: " + notAllowedPermissionNames, Toast.LENGTH_LONG).show();
        }
    }

    public void recordStart(View view){

    }
}