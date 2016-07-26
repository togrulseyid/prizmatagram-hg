package com.togrulseyid.prizmatagram.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.togrulseyid.prizmatagram.R;
import com.togrulseyid.prizmatagram.models.FilterModel;
import com.togrulseyid.prizmatagram.models.FilterModelList;
import com.togrulseyid.prizmatagram.natives.NativeClass;
import com.togrulseyid.prizmatagram.utils.Algorithms;
import com.togrulseyid.prizmatagram.utils.Utils;

import junit.framework.Assert;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by toghrul on 25.07.2016.
 */
public class FilterActivity extends AppCompatActivity {
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";
    private List<FilterModel> filterModels;
    private FilterModelList filterModelList;

//    @InjectView(R.id.thumbnails)  LinearLayout _thumbnails;
    @InjectView(R.id.btn_close)
    ImageButton _closeButton;
    @InjectView(R.id.imageView)
    SubsamplingScaleImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.inject(this);

//      filterModels = (ArrayList<FilterModel>) getIntent().getExtras().getSerializable(EXTRA_NAME);
        filterModelList = (FilterModelList) getIntent().getExtras().getSerializable(EXTRA_NAME);
        Assert.assertNotNull(filterModelList);
        filterModels = filterModelList.getFilterModels();

        Assert.assertNotNull(filterModelList.getBitmap());
        Assert.assertNotNull(filterModels);

        imageView.setImage(ImageSource.bitmap(filterModelList.getBitmap()));





//        for (final FilterModel filterModel : filterModels) {
//            int borderSize = _thumbnails.getPaddingTop();
//
//            // Get the size of the actual thumbnail image
//            int thumbnailSize = ((FrameLayout.LayoutParams) imageView.getLayoutParams()).bottomMargin - (borderSize * 2);
//
//            // Set the thumbnail layout parameters. Adjust as required
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
//            params.setMargins(5, 5, borderSize, 5);
//
//            // You could also set like so to remove borders
//            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//            //        ViewGroup.LayoutParams.WRAP_CONTENT,
//            //        ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            final RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_filter_item, _thumbnails, false);
//
//            TextView txt = (TextView) relativeLayout.findViewById(R.id.textViewFilterLabel);
//            ImageView img = (ImageView) relativeLayout.findViewById(R.id.imageViewFilterLabel);
//
//            relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Thumbnail clicked");
//
//                    filterIt(filterModel.id, imageView, filterModelList.getBitmap());
//
//                }
//            });
//
//            img.setImageResource(Utils.getResId(getApplicationContext(), filterModel.image, Utils.IDENTIFIER_DRAWABLE));
//            _thumbnails.addView(relativeLayout);
//
////            final ImageView thumbView = new ImageView(this);
////            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////            thumbView.setLayoutParams(params);
////            thumbView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Log.d(TAG, "Thumbnail clicked");
////
////                    filterIt(filterModel.id, imageView, filterModelList.getBitmap());
////
//////
//////                    Glide.with(getApplicationContext())
//////                            .load(Utils.getResId(getApplicationContext(), filterModel.image, Utils.IDENTIFIER_DRAWABLE))
//////                            .asBitmap()
//////                            .into(new SimpleTarget<Bitmap>() {
//////                                @Override
//////                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//////                                    imageView.setImage(ImageSource.bitmap(bitmap));
//////                                }
//////                            });
////                }
////            });
//
//
////            thumbView.setImageResource(Utils.getResId(getApplicationContext(), filterModel.image, Utils.IDENTIFIER_DRAWABLE));
////            Glide.with(getApplicationContext())
////                    .load(image)
////                    .asBitmap()
////                    .into(new SimpleTarget<Bitmap>() {
////                        @Override
////                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
////                            thumbView.setImageBitmap(bitmap);
////                        }
////                    });
//
////            _thumbnails.addView(thumbView);
//
//        }

        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close clicked");
                finish();
            }
        });
    }



    public void onClickEffectButton(View view){
//        view.getTag().toString();

        mat = new Mat();

        if (bitMap != null)
            bitMap.recycle();

//        filterModel.id, imageView,

        org.opencv.android.Utils.bitmapToMat( filterModelList.getBitmap(), mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        long startTime = System.currentTimeMillis();
        switch (view.getTag().toString()) {

            case Algorithms.STR_NATIVE_FUNCTION:
                NativeClass.myNativeFunction(mat.getNativeObjAddr());
                break;


            case Algorithms.STR_NATIVE_DITHERING:
                NativeClass.nativeDithering(mat.getNativeObjAddr());
                break;


            case Algorithms.STR_NATIVE_MOSAIC:
                NativeClass.nativeMosaic(mat.getNativeObjAddr(), 24);
                break;


            case Algorithms.STR_NATIVE_TELEVISION:
                NativeClass.nativeTelevision(mat.getNativeObjAddr());
                break;


            case Algorithms.STR_NATIVE_PIXELIZE:
                NativeClass.nativePixelize(mat.getNativeObjAddr());
                break;


            case Algorithms.STR_NATIVE_OIL_PAINT:
                int intensity = 20;
                int radius = 5;
                NativeClass.nativeOilPaint(mat.getNativeObjAddr(), intensity, radius);
                break;


            case Algorithms.STR_NATIVE_TEST:
                NativeClass.nativeTest(mat.getNativeObjAddr());
                break;

            default:

                Toast.makeText(this, "Mumkun deyil " + view.getTag().toString(), Toast.LENGTH_LONG).show();
                break;

        }
        long stopTime = System.currentTimeMillis();

        bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        org.opencv.android.Utils.matToBitmap(mat, bitMap);
        imageView.setImage(ImageSource.bitmap(bitMap));

        mat.release();
        long elapsedTime = stopTime - startTime;
        Log.d(TAG + "X", "elapsedTimeCPP: " + elapsedTime);
    }



    ///---------------------------

    //    private Bitmap bmp;
    private Mat mat;
    Bitmap bitMap;

    public void filterIt(int algorithm, SubsamplingScaleImageView imageView, Bitmap bmp) {
        mat = new Mat();

        if (bitMap != null)
            bitMap.recycle();

        org.opencv.android.Utils.bitmapToMat(bmp, mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        long startTime = System.currentTimeMillis();
        switch (algorithm) {

            case Algorithms.NATIVE_FUNCTION:
                NativeClass.myNativeFunction(mat.getNativeObjAddr());
                break;


            case Algorithms.NATIVE_DITHERING:
                NativeClass.nativeDithering(mat.getNativeObjAddr());
                break;


            case Algorithms.NATIVE_MOSAIC:
                NativeClass.nativeMosaic(mat.getNativeObjAddr(), 24);
                break;


            case Algorithms.NATIVE_TELEVISION:
                NativeClass.nativeTelevision(mat.getNativeObjAddr());
                break;


            case Algorithms.NATIVE_PIXELIZE:
                NativeClass.nativePixelize(mat.getNativeObjAddr());
                break;


            case Algorithms.NATIVE_OIL_PAINT:
                int intensity = 20;
                int radius = 5;
                NativeClass.nativeOilPaint(mat.getNativeObjAddr(), intensity, radius);
                break;


            case Algorithms.NATIVE_TEST:
                NativeClass.nativeTest(mat.getNativeObjAddr());
                break;

            default:

                Toast.makeText(this, "Mumkun deyil " + algorithm, Toast.LENGTH_LONG).show();
                break;

        }
        long stopTime = System.currentTimeMillis();

        // create a bitMap
//        Bitmap bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
        bitMap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
        // convert Mat to Android's bitmap:
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        org.opencv.android.Utils.matToBitmap(mat, bitMap);
//        imageView.setImageBitmap(bitMap);
        imageView.setImage(ImageSource.bitmap(bitMap));

//        Utils.matToBitmap(mat, bmp, true);
//        imageView.setImageBitmap(bmp);
        mat.release();

        long elapsedTime = stopTime - startTime;
        Log.d(TAG + "X", "elapsedTimeCPP: " + elapsedTime);
    }
}
