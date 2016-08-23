package com.togrulseyid.prizmatagram.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.togrulseyid.filechooserlibrary.FileChooserDialog;
import com.togrulseyid.nativelibrary.BitmapFilter;
import com.togrulseyid.prizmatagram.R;
import com.togrulseyid.prizmatagram.effects.WaterFilter;
import com.togrulseyid.prizmatagram.models.FilterModel;
import com.togrulseyid.prizmatagram.natives.Library;
import com.togrulseyid.prizmatagram.natives.NativeClass;
import com.togrulseyid.prizmatagram.utils.Algorithms;

import junit.framework.Assert;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author toghrul
 *         Created by toghrul on 25.07.2016.
 */
public class FilterActivity extends AppCompatActivity {
    private static final String PNG = ".png";
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";
    private FilterModel filterModel;
    private Bitmap bitMap;
    private Bitmap bitMapOriginal;
    private Mat mat;

    @InjectView(R.id.imageView)
    SubsamplingScaleImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);


        ButterKnife.inject(this);

        filterModel = (FilterModel) getIntent().getExtras().getSerializable(EXTRA_NAME);
        Assert.assertNotNull(filterModel);


        Assert.assertNotNull(filterModel.getSrcBitmap());

        bitMapOriginal = filterModel.getSrcBitmap();
        bitMap = filterModel.getSrcBitmap();


        mat = new Mat();
        if (bitMapOriginal == null || bitMapOriginal.isRecycled())
            Toast.makeText(getApplicationContext(), "poxu cixdi", Toast.LENGTH_LONG).show();
        org.opencv.android.Utils.bitmapToMat(bitMapOriginal, mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        bitMap = Bitmap.createBitmap(bitMapOriginal.getWidth(), bitMapOriginal.getHeight(), Bitmap.Config.RGB_565);

        imageView.setImage(ImageSource.bitmap(bitMapOriginal));

    }


    public void onClickEffectButton(View view) {
//        view.getTag().toString();

        if (bitMap != null && !bitMap.isRecycled())
            bitMap.recycle();

        Library library;

        long startTime = System.currentTimeMillis();
        switch (view.getTag().toString()) {

            case Algorithms.STR_NATIVE_TEST:
                NativeClass.nativeTest(mat.getNativeObjAddr());
                bitMap = Bitmap.createBitmap(bitMapOriginal.getWidth(), bitMapOriginal.getHeight(), Bitmap.Config.RGB_565);
//                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_FUNCTION:
                NativeClass.myNativeFunction(mat.getNativeObjAddr());
                bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
//                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_DITHERING:
                NativeClass.nativeDithering(mat.getNativeObjAddr());
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                break;


            case Algorithms.STR_NATIVE_MOSAIC:
                NativeClass.nativeMosaic(mat.getNativeObjAddr(), 24);
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_TELEVISION:
                NativeClass.nativeTelevision(mat.getNativeObjAddr());
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_PIXELATE:
                NativeClass.nativePixelize(mat.getNativeObjAddr(), 5);
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_PIXELATE_2:
                NativeClass.nativePixelate(mat.getNativeObjAddr(), 5);
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_OIL_PAINT:
                int intensity = 20;
                int radius = 5;
                NativeClass.nativeOilPaint(mat.getNativeObjAddr(), intensity, radius);
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_WATER_FILTER:
                WaterFilter waterFilter = new WaterFilter();

//                bitMapX = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                bitMap = Bitmap.createScaledBitmap(bitMapOriginal, bitMapOriginal.getWidth(), bitMapOriginal.getHeight(), false);
                waterFilter.filter(bitMapOriginal, bitMap);
                break;


            case Algorithms.STR_NATIVE_FISH_EYE:
                NativeClass.nativeFishEye(mat.getNativeObjAddr());
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_FLIP:
                NativeClass.nativeFlip(mat.getNativeObjAddr());
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_MIRROR:
                NativeClass.nativeMirror(mat.getNativeObjAddr());
                //bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                org.opencv.android.Utils.matToBitmap(mat, bitMap);
                break;
            //TODO: zirradim bura o kitabxanadakilari
            case Algorithms.GRAY_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.GRAY_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.RELIEF_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.RELIEF_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.AVERAGE_BLUR_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.AVERAGE_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.OIL_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.OIL_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.NEON_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.NEON_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.PIXELATE_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.PIXELATE_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.TV_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.TV_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.INVERT_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.INVERT_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.BLOCK_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.BLOCK_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.OLD_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.OLD_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SHARPEN_STYLE:
                library = new Library(bitMapOriginal.copy(bitMapOriginal.getConfig(), true));
                library.applyStyle(BitmapFilter.SHARPEN_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.LIGHT_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.LIGHT_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.LOMO_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.LOMO_STYLE);
                bitMap = library.getBitmap();
                break;
            case Algorithms.HDR_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.HDR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.GAUSSIAN_BLUR_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.GAUSSIAN_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SOFT_GLOW_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.SOFT_GLOW_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SKETCH_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.SKETCH_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.MOTION_BLUR_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.MOTION_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.GOTHAM_STYLE:
                library = new Library(bitMapOriginal);
                library.applyStyle(BitmapFilter.GOTHAM_STYLE);
                bitMap = library.getBitmap();
                break;

            default:
                Toast.makeText(this, "Mumkun deyil " + view.getTag().toString(), Toast.LENGTH_LONG).show();
                break;

        }

        long stopTime = System.currentTimeMillis();
        imageView.setImage(ImageSource.bitmap(bitMap));

        mat.release();
        long elapsedTime = stopTime - startTime;
        Log.d(TAG + "X", "elapsedTimeCPP: " + elapsedTime);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

            saveImage();
            return true;
        } else if (id == R.id.action_original) {
            Toast.makeText(getApplicationContext(), "original", Toast.LENGTH_LONG).show();
            resetOriginalImage();
            return true;
        } else if (id == R.id.action_apply) {
            applyImageEffect();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void resetOriginalImage() {
        bitMapOriginal = filterModel.getSrcBitmap();
        imageView.setImage(ImageSource.bitmap(bitMapOriginal));
    }

    void saveImage() {
        // Create the dialog.
        FileChooserDialog dialog = new FileChooserDialog(FilterActivity.this);

        // Assign listener for the select event.
        dialog.addListener(FilterActivity.this.onFileSelectedListener);

        // Activate the button for create files.
        dialog.setCanCreateFiles(true);

        // Show the dialog.
        dialog.show();
    }

    void applyImageEffect() {
        imageView.buildDrawingCache();
        bitMapOriginal = imageView.getDrawingCache();
    }


    /////////////////////////////////
    private FileChooserDialog.OnFileSelectedListener onFileSelectedListener = new FileChooserDialog.OnFileSelectedListener() {
        public void onFileSelected(Dialog source, File file) {
            source.hide();

//            Bitmap bmp = bitMapOriginal;
            Bitmap bmp = bitMap;

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file.getAbsolutePath());
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

                Toast toast = Toast.makeText(FilterActivity.this, "File selected: " + file.getName(), Toast.LENGTH_LONG);
                toast.show();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public void onFileSelected(Dialog source, File folder, String name) {
            source.hide();
//            Bitmap bmp = bitMapOriginal;
            Bitmap bmp = bitMap;

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(folder.getAbsolutePath() + File.separator + name + PNG);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
                Toast toast = Toast.makeText(FilterActivity.this, "File created " + folder.getName() + "/" + name + PNG, Toast.LENGTH_LONG);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FileDirIs", "Error: " + e.getLocalizedMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    };


}