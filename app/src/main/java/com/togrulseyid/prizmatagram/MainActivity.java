package com.togrulseyid.prizmatagram;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.togrulseyid.filechooserlibrary.FileChooserDialog;
import com.togrulseyid.prizmatagram.activity.CameraViewActivity;
import com.togrulseyid.prizmatagram.activity.FilterActivity;
import com.togrulseyid.prizmatagram.models.FilterModel;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "NativeActivity";

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
            Log.i(TAG, "OpenCV loaded was not successfully");
        }
        System.loadLibrary("PrizmaTagramN");
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    // System.loadLibrary("PrizmaTagramN");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_gallery).setOnClickListener(btnDialogSelectImages);
        findViewById(R.id.button_camera).setOnClickListener(btnTakePictures);

        startGalleryActivity("/mnt/sdcard/Pictures/Instagram/IMG_20160613_160151.jpg");
    }

    public void startGalleryActivity(String bitmap) {
//        TODO: Fragment ile elemeyi dushun. Her bir Filter ucun fragment yuklensin.
//        TODO: Onda rahat olacaq VIEW ClickListeners Touch and etc.
        FilterModel filterModelList = new FilterModel(bitmap);
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FilterActivity.EXTRA_NAME, filterModelList);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            return true;
        } else if (id == R.id.action_next) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////////====================================================================================
    private View.OnClickListener btnTakePictures = new View.OnClickListener() {
        public void onClick(View v) {

//            startGalleryActivity(file.getAbsolutePath());
            Intent intent = new Intent(MainActivity.this, CameraViewActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnDialogSelectImages = new View.OnClickListener() {
        public void onClick(View v) {
            // Create the dialog.
            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);

            // Assign listener for the select event.
            dialog.addListener(MainActivity.this.onFileSelectedListener);

            // Define the filter for select images.
            dialog.setFilter(".*jpg|.*png|.*gif|.*JPG|.*PNG|.*GIF");
            dialog.setShowOnlySelectable(false);
            dialog.setShowCancelButton(true);


            // Show the dialog.
            dialog.show();
        }
    };


    /////////////////////////////////
    private FileChooserDialog.OnFileSelectedListener onFileSelectedListener = new FileChooserDialog.OnFileSelectedListener() {
        public void onFileSelected(Dialog source, File file) {

            Log.d("filePath", file.getAbsolutePath());
            startGalleryActivity(file.getAbsolutePath());
            source.hide();
        }

        public void onFileSelected(Dialog source, File folder, String name) {
            source.hide();
            Toast toast = Toast.makeText(MainActivity.this, "File created: " + folder.getName() + "/" + name, Toast.LENGTH_LONG);
            toast.show();
        }
    };

//
//private View.OnClickListener btnDialogSimpleOpen = new View.OnClickListener() {
//    public void onClick(View v) {
//        // Create the dialog.
//        FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//        // Assign listener for the select event.
//        dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//        // Show the dialog.
//        dialog.show();
//    }
//};
//
//    private View.OnClickListener btnDialogOpenDownloads = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Define start folder.
//            dialog.loadFolder(Environment.getExternalStorageDirectory() + "/Download/");
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//    private View.OnClickListener btnDialogSelectFolders = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Activate the folder mode.
//            dialog.setFolderMode(true);
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//    private View.OnClickListener btnDialogCreateFiles = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Activate the button for create files.
//            dialog.setCanCreateFiles(true);
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//
//    private View.OnClickListener btnDialogAskConfirmation = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Activate the button for create files.
//            dialog.setCanCreateFiles(true);
//
//            // Activate the confirmation dialogs.
//            dialog.setShowConfirmation(true, true);
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//    private View.OnClickListener btnDialogCustomLabels = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Activate the folder mode.
//            dialog.setFolderMode(true);
//
//            // Activate the button for create files.
//            dialog.setCanCreateFiles(true);
//
//            // Activate the button for cancel.
//            dialog.setShowCancelButton(true);
//
//            // Activate the confirmation dialogs.
//            dialog.setShowConfirmation(true, true);
//
//            // Define the labels.
//            FileChooserLabels labels = new FileChooserLabels();
//            labels.createFileDialogAcceptButton = "AcceptButton";
//            labels.createFileDialogCancelButton = "CancelButton";
//            labels.createFileDialogMessage = "DialogMessage";
//            labels.createFileDialogTitle = "DialogTitle";
//            labels.labelAddButton = "AddButton";
//            labels.labelSelectButton = "SelectButton";
//            labels.messageConfirmCreation = "messageConfirmCreation";
//            labels.messageConfirmSelection = "messageConfirmSelection";
//            labels.labelConfirmYesButton = "yesButton";
//            labels.labelConfirmNoButton = "noButton";
//            labels.labelCancelButton = "cancelButton";
//            dialog.setLabels(labels);
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//    private View.OnClickListener btnDialogCancelButton = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Activate the button for create files.
//            dialog.setShowCancelButton(true);
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };


////////////////////////////////////////////////////////////////////////////
//
//    private View.OnClickListener btnDialogFilterFolders = new View.OnClickListener() {
//        public void onClick(View v) {
//            // Create the dialog.
//            FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
//
//            // Assign listener for the select event.
//            dialog.addListener(MainActivity.this.onFileSelectedListener);
//
//            // Define start folder and don't allow to go up that folder.
//            dialog.loadFolder(Environment.getExternalStorageDirectory() + "");
//            dialog.setFolderFilter("("+Environment.getExternalStorageDirectory()+")|((.*)Download(.*))");
//
//            // Show the dialog.
//            dialog.show();
//        }
//    };
//
//
//    /////////////============================================================
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            boolean fileCreated = false;
//            String filePath = "";
//
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                if (bundle.containsKey(FileChooserActivity.OUTPUT_NEW_FILE_NAME)) {
//                    fileCreated = true;
//                    File folder = (File) bundle.get(FileChooserActivity.OUTPUT_FILE_OBJECT);
//                    String name = bundle.getString(FileChooserActivity.OUTPUT_NEW_FILE_NAME);
//                    filePath = folder.getAbsolutePath() + "/" + name;
//                } else {
//                    fileCreated = false;
//                    File file = (File) bundle.get(FileChooserActivity.OUTPUT_FILE_OBJECT);
//                    filePath = file.getAbsolutePath();
//                }
//            }
//
//            String message = fileCreated ? "File created" : "File opened";
//            message += ": " + filePath;
//            Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
//            toast.show();
//
//        }
//    }
}

