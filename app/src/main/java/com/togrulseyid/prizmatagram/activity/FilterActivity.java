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

import org.opencv.android.Utils;
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

    @InjectView(R.id.imageView)
    SubsamplingScaleImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


//        startActivity(new Intent(this, CartoonifierApp.class));
//        finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        filterModel = (FilterModel) getIntent().getExtras().getSerializable(EXTRA_NAME);
        Assert.assertNotNull(filterModel);
        Assert.assertNotNull(filterModel.getSrcBitmap());

        bitMapOriginal = filterModel.getSrcBitmap().copy(filterModel.getSrcBitmap().getConfig(), true);
        filterModel.setCurrentBitmap(filterModel.getSrcBitmap().copy(filterModel.getSrcBitmap().getConfig(), false));
        imageView.setImage(ImageSource.bitmap(bitMapOriginal));
    }


    public void onClickEffectButton(View view) {
//        view.getTag().toString();

        if (bitMap != null && !bitMap.isRecycled())
            bitMap.recycle();

        bitMapOriginal = filterModel.getCurrentBitmap().copy(filterModel.getCurrentBitmap().getConfig(), true);
        bitMap = filterModel.getCurrentBitmap().copy(filterModel.getCurrentBitmap().getConfig(), true);

        Mat mat = new Mat();
        Utils.bitmapToMat(bitMap, mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        Library library = new Library(bitMapOriginal);

        long startTime = System.currentTimeMillis();

        switch (view.getTag().toString()) {

            case Algorithms.STR_NATIVE_TEST:
                NativeClass.nativeTest(mat.getNativeObjAddr());
//                NativeClass.cartoonifyImages(mat.getNativeObjAddr(), false, true, false);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_FUNCTION:
                NativeClass.myNativeFunction(mat.getNativeObjAddr());
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_DITHERING:
                NativeClass.nativeDithering(mat.getNativeObjAddr());
                break;

            case Algorithms.STR_NATIVE_MOSAIC:
                NativeClass.nativeMosaic(mat.getNativeObjAddr(), 24);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_TELEVISION:
                NativeClass.nativeTelevision(mat.getNativeObjAddr());
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_PIXELATE:
                NativeClass.nativePixelize(mat.getNativeObjAddr(), 5);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_PIXELATE_2:
                NativeClass.nativePixelate(mat.getNativeObjAddr(), 5);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_OIL_PAINT:
                int intensity = 20;
                int radius = 5;
                NativeClass.nativeOilPaint(mat.getNativeObjAddr(), intensity, radius);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_WATER_FILTER:
                WaterFilter waterFilter = new WaterFilter();
                waterFilter.filter(bitMapOriginal, bitMap);
                break;

            case Algorithms.STR_NATIVE_FISH_EYE:
                NativeClass.nativeFishEye(mat.getNativeObjAddr());
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_FLIP:
                NativeClass.nativeFlip(mat.getNativeObjAddr());
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            case Algorithms.STR_NATIVE_MIRROR:
                NativeClass.nativeMirror(mat.getNativeObjAddr());
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;


            case Algorithms.STR_NATIVE_STYLIZATION:
                NativeClass.nativeStylization(mat.getNativeObjAddr(), 60, 0.45f);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
                Utils.matToBitmap(mat, bitMap);
                break;

            //TODO: zirradim bura o kitabxanadakilari
            case Algorithms.GRAY_STYLE:
                library.applyStyle(BitmapFilter.GRAY_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.RELIEF_STYLE:
                library.applyStyle(BitmapFilter.RELIEF_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.AVERAGE_BLUR_STYLE:
                library.applyStyle(BitmapFilter.AVERAGE_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.OIL_STYLE:
                library.applyStyle(BitmapFilter.OIL_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.NEON_STYLE:
                library.applyStyle(BitmapFilter.NEON_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.PIXELATE_STYLE:
                library.applyStyle(BitmapFilter.PIXELATE_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.TV_STYLE:
                library.applyStyle(BitmapFilter.TV_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.INVERT_STYLE:
                library.applyStyle(BitmapFilter.INVERT_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.BLOCK_STYLE:
                library.applyStyle(BitmapFilter.BLOCK_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.OLD_STYLE:
                library.applyStyle(BitmapFilter.OLD_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SHARPEN_STYLE:
                library.applyStyle(BitmapFilter.SHARPEN_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.LIGHT_STYLE:
                library.applyStyle(BitmapFilter.LIGHT_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.LOMO_STYLE:
                library.applyStyle(BitmapFilter.LOMO_STYLE);
                bitMap = library.getBitmap();
                break;
            case Algorithms.HDR_STYLE:
                library.applyStyle(BitmapFilter.HDR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.GAUSSIAN_BLUR_STYLE:
                library.applyStyle(BitmapFilter.GAUSSIAN_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SOFT_GLOW_STYLE:
                library.applyStyle(BitmapFilter.SOFT_GLOW_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.SKETCH_STYLE:
                library.applyStyle(BitmapFilter.SKETCH_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.MOTION_BLUR_STYLE:
                library.applyStyle(BitmapFilter.MOTION_BLUR_STYLE);
                bitMap = library.getBitmap();
                break;

            case Algorithms.GOTHAM_STYLE:
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
        Log.d(TAG, "elapsedTime: " + elapsedTime);
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

    private void resetOriginalImage() {
        bitMapOriginal = filterModel.getSrcBitmap();
        filterModel.setCurrentBitmap(filterModel.getSrcBitmap().copy(filterModel.getSrcBitmap().getConfig(), true));
        imageView.setImage(ImageSource.bitmap(bitMapOriginal));

        Toast.makeText(getApplicationContext(), getString(R.string.message_action_saved), Toast.LENGTH_LONG).show();
    }

    private void saveImage() {
        // Create the dialog.
        FileChooserDialog dialog = new FileChooserDialog(FilterActivity.this);

        // Assign listener for the select event.
        dialog.addListener(FilterActivity.this.onFileSelectedListener);

        // Activate the button for create files.
        dialog.setCanCreateFiles(true);

        // Show the dialog.
        dialog.show();
    }

    private void applyImageEffect() {
        imageView.buildDrawingCache();
        filterModel.setCurrentBitmap(imageView.getDrawingCache());
        Toast.makeText(getApplicationContext(), getString(R.string.message_action_applied), Toast.LENGTH_LONG).show();
    }

    private FileChooserDialog.OnFileSelectedListener onFileSelectedListener = new FileChooserDialog.OnFileSelectedListener() {
        public void onFileSelected(Dialog source, File file) {
            source.hide();

            Bitmap bmp = bitMap;

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file.getAbsolutePath());
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

                Toast.makeText(getApplicationContext(), getString(R.string.message_action_saved), Toast.LENGTH_LONG).show();
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
            Bitmap bmp = bitMap;

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(folder.getAbsolutePath() + File.separator + name + PNG);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

                Toast.makeText(getApplicationContext(), getString(R.string.message_action_saved), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bitMap != null && !bitMap.isRecycled())
            bitMap.recycle();
        if (bitMapOriginal != null && !bitMapOriginal.isRecycled())
            bitMapOriginal.recycle();

    }
}
