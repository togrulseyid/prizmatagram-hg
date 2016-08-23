//package com.togrulseyid.prizmatagram.effects.fisheye;
//
///**
// * Created by toghrul on 03.08.2016.
// */
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.os.Environment;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.tecmark.HorizontalSlider.OnProgressChangeListener;
//
//import java.io.BufferedInputStream;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//
//public class TouchView extends View {
//
//
//    private File tempFile;
//    private byte[] imageArray;
//    private Bitmap bgr;
//
//    private Bitmap crop;
//    private Bitmap crop2;
//    private Bitmap overLay;
//    private Bitmap overLay2;
//
//
//    private float centreX;
//    private float centreY;
//    private float centreA = 200;
//    private float centreB = 200;
//    private Boolean xyFound = false;
//    private int Progress = 1;
//    private static final String TAG = "*********TouchView";
//    private Filters f = null;
//    private boolean bothCirclesInPlace = false;
//    private MultiProcessorFilter mpf;
//    private MultiProcessorFilter mpf2;
//    private MultiRuntimeProcessorFilter mrpf;
//    private MultiRuntimeProcessorFilter mrpf2;
//
//
//    public TouchView(Context context) {
//        super(context);
//
//    }
//
//
//    public TouchView(Context context, AttributeSet attr) {
//        super(context, attr);
//        Log.e(TAG, "++++++++++ inside touchview constructor");
//
//
//        tempFile = new File(Environment.getExternalStorageDirectory().
//                getAbsolutePath() + "/" + "image.jpg");
//
//        imageArray = new byte[(int) tempFile.length()];
//
//
//        try {
//
//            InputStream is = new FileInputStream(tempFile);
//            BufferedInputStream bis = new BufferedInputStream(is);
//            DataInputStream dis = new DataInputStream(bis);
//
//
//            int i = 0;
//
//            while (dis.available() > 0) {
//                imageArray[i] = dis.readByte();
//                i++;
//            }
//
//            dis.close();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//
//        Bitmap bm = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
//
//
//        bgr = bm.copy(bm.getConfig(), true);
//        ;
//
//        overLay = null;
//        overLay2 = null;
//
//
//        bm.recycle();
//
//    }// end of touchView constructor
//
//
//    public void findCirclePixels() {
//
//        //  f = new Filters();
//        //  mpf = new MultiProcessorFilter();
//        //  mpf2 = new MultiProcessorFilter();
//        mrpf = new MultiRuntimeProcessorFilter();
//        mrpf2 = new MultiRuntimeProcessorFilter();
//
//        crop = Bitmap.createBitmap(bgr, Math.max((int) centreX - 75, 0), Math.max((int) centreY - 75, 0), 150, 150);
//        crop2 = Bitmap.createBitmap(bgr, Math.max((int) centreA - 75, 0), Math.max((int) centreB - 75, 0), 150, 150);
//
//        new Thread(new Runnable() {
//            public void run() {
//                float prog = (float) Progress / 150001;
//
//                // final Bitmap bgr3 = f.barrel(crop,prog);
//                // final Bitmap bgr4 = f.barrel(crop2,prog);
//
//                //  final Bitmap bgr3 = mpf.barrel(crop,prog);
//                //  final Bitmap bgr4 = mpf2.barrel(crop2,prog);
//
//                final Bitmap bgr3 = mrpf.barrel(crop, prog);
//                final Bitmap bgr4 = mrpf2.barrel(crop2, prog);
//
//                TouchView.this.post(new Runnable() {
//                    public void run() {
//
//
//                        TouchView.this.overLay = bgr3;
//                        TouchView.this.overLay2 = bgr4;
//
//                        TouchView.this.invalidate();
//
//                    }
//                });
//            }
//        }).start();
//
//
//    }// end of changePixel()
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//
//            case MotionEvent.ACTION_DOWN: {
//
//                if (xyFound == false) {
//                    centreX = (int) ev.getX();
//                    centreY = (int) ev.getY();
//                    xyFound = true;
//                } else {
//                    centreA = (int) ev.getX();
//                    centreB = (int) ev.getY();
//                    bothCirclesInPlace = true;
//                }
//
//
//                break;
//            }
//
//          /*  case MotionEvent.ACTION_MOVE: {
//
//                if(xyFound == false){
//                    centreX = (int) ev.getX();
//                    centreY = (int) ev.getY();
//                    xyFound = true;
//                }else{
//                    centreA = (int) ev.getX();
//                    centreB = (int) ev.getY();
//                    bothCirclesInPlace = true;
//                    }
//
//                    findCirclePixels();
//                 // TouchView.this.invalidate();
//                    break;
//
//            }*/
//
//            case MotionEvent.ACTION_UP:
//
//                break;
//
//        }
//        return true;
//    }//end of onTouchEvent
//
//
//    public void initSlider(final HorizontalSlider slider) {
//        slider.setOnProgressChangeListener(changeListener);
//    }
//
//    private OnProgressChangeListener changeListener = new OnProgressChangeListener() {
//        @Override
//        public void onProgressChanged(View v, int progress) {
//
//            setProgress(progress);
//        }
//    };
//
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Log.e(TAG, "******about to draw bgr ");
//        canvas.drawBitmap(bgr, 0, 0, null);
//
//        if (bothCirclesInPlace == true) {
//
//            if (overLay != null) {
//                Log.e(TAG, "******about to draw overlay1 ");
//                canvas.drawBitmap(overLay, centreX - 75, centreY - 75, null);
//            }
//            if (overLay2 != null) {
//                Log.e(TAG, "******about to draw overlay2 ");
//                canvas.drawBitmap(overLay2, centreA - 75, centreB - 75, null);
//            }
//
//        }
//
//    }//end of onDraw
//
//    protected void setProgress(int progress2) {
//        Log.e(TAG, "***********in SETPROGRESS");
//        this.Progress = progress2;
//
//        findCirclePixels();
//    }
//
//}
