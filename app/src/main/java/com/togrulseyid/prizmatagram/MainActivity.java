package com.togrulseyid.prizmatagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.togrulseyid.prizmatagram.natives.NativeClass;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "NativeActivity";

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
            Log.i(TAG, "OpenCV loaded was not successfully");
        }
        System.loadLibrary("PrizmaTagramN");
        System.loadLibrary("opencv_java3");
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
        boolean loaded = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        if (!loaded) {
            Log.d(TAG, "Can not load zibil");
        }

    }

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

    }


    //    private Bitmap bmp;
    private Mat mat;

    public void doit(int id) {
        mat = new Mat();
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inSampleSize=2;

//        if (bmp != null)
//            bmp.recycle();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);

        Utils.bitmapToMat(bmp, mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        long startTime = System.currentTimeMillis();
//        NativeClass.myNativeFunction(mat.getNativeObjAddr());
//        NativeClass.nativeDithering(mat.getNativeObjAddr());
//        NativeClass.nativeMosaic(mat.getNativeObjAddr(), 24);
        NativeClass.nativeTest(mat.getNativeObjAddr());
        long stopTime = System.currentTimeMillis();


        // create a bitMap
        Bitmap bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
        // convert Mat to Android's bitmap:
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        Utils.matToBitmap(mat, bitMap);
        imageView.setImageBitmap(bitMap);


//        Utils.matToBitmap(mat, bmp, true);
//        imageView.setImageBitmap(bmp);
        mat.release();

        long elapsedTime = stopTime - startTime;
        Log.d(TAG + "X", "elapsedTimeCPP: " + elapsedTime);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (bmp != null)
//            bmp.recycle();
//        if (mat != null)
//            mat.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private Stack<Integer> stack = new Stack<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
//            stack.add(R.drawable.togrul8);
//            stack.add(R.drawable.togrul7);
//            stack.add(R.drawable.togrul6);
//            stack.add(R.drawable.togrul5);
//            stack.add(R.drawable.togrul4);
//            stack.add(R.drawable.togrul3);
            stack.add(R.drawable.togrul2);
            stack.add(R.drawable.togrul9);
//            stack.add(R.drawable.pass_pic);
//            stack.add(R.drawable.togrul1);
            return true;
        } else if (id == R.id.action_next) {
            if (!stack.empty())
                doit(stack.pop());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

