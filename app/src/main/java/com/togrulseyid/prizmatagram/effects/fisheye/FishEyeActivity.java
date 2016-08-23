//package com.togrulseyid.prizmatagram.effects.fisheye;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.togrulseyid.prizmatagram.R;
//
///**
// * Created by toghrul on 03.08.2016.
// */
//public class FishEyeActivity extends Activity {
//
//
//    private static final String TAG = "*********jjil";
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.touchview);
//        final TouchView touchView = (TouchView) findViewById(R.id.touchview);
//        final HorizontalSlider slider = (HorizontalSlider) findViewById(R.id.slider);
//
//        touchView.initSlider(slider);
//
//
//    }//end of oncreate
//
//
//}
