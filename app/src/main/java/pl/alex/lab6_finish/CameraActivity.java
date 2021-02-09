package pl.alex.lab6_finish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    String TAG = "alex_camera";
    File directory;
    final int REQUEST_CODE_VIDEO = 1;
    int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        createDirectory();

        PackageManager pManager = this.getPackageManager();
        if (!pManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getBaseContext(), "No microfon feature", Toast.LENGTH_SHORT);
            onBackPressed();
        }

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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

    private void createDirectory() {
        directory = new File(
                getApplication().getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString() + "/" + "lab6");
        if (!directory.exists())
            directory.mkdirs();
    }

    private Uri generateFileUri() {
        File file;
        file = new File(directory.getPath() + "/" + "video_"+ System.currentTimeMillis() + ".mp4");
        return Uri.fromFile(file);
    }

    public void camera_open(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri().getPath());
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_VIDEO){
            if (resultCode == RESULT_OK)
            {
                if (intent != null){
                    Log.i(TAG, "Video uri: " + intent.getData().toString());
                }
            }
        }
    }
}