package pl.alex.lab6_finish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioActivity extends AppCompatActivity {
    int PERMISSION_REQUEST_CODE = 1;
    String path_write_audio_file;
    String file_name = "my_record.3gp";
    Button record;
    Button stop;
    Button play;
    Button pause;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

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
        path_write_audio_file = getApplication().getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString() + "/" + file_name;

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
        try {
            releaseRecorder();
            File file_write = new File(path_write_audio_file);
            if (file_write.exists()){
                file_write.delete();
            }
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(path_write_audio_file);
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), "Problems with recording audio", Toast.LENGTH_LONG);
        }
    }

    public void play(View view){
        releasePlayer();

        File file = new File(this.path_write_audio_file);
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this.path_write_audio_file);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "error; datasource unavailable", Toast.LENGTH_LONG);
        }
    }

    public void pause(View view){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void recordStop(View view){
        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
        }
    }

    private void releaseRecorder(){
        if (mediaRecorder != null){
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }
}