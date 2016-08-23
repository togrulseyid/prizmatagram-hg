//package com.togrulseyid.prizmatagram.effects.fisheye;
//
///**
// * Created by toghrul on 03.08.2016.
// */
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.FutureTask;
//
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Debug;
//import android.util.Log;
//
//public class MultiRuntimeProcessorFilter {
//
//
//
//    private static final String TAG = "mrpf";
//    private int x = 0;
//    private Bitmap input = null;
//    private int radius;
//
//
//    public void createBitmapSections(int nOp, int[] sections){
//
//        int processors = nOp;
//        int jMax = input.getHeight();
//        int aSectionSize = (int) Math.ceil(jMax/processors);
//        Log.e(TAG, "++++++++++ sections size = "+aSectionSize);
//
//
//        int k = 0;
//        for(int h=0; h<processors+1; h++){
//
//            sections[h] = k;
//            k+= aSectionSize;
//
//
//
//        }
//    }// end of createBitmapSections()
//
//
//
//    @SuppressWarnings("unchecked")
//    public Bitmap barrel (Bitmap input, float k, int r){
//        this.radius = r;
//        this.input = input;
//        int []arr = new int[input.getWidth()*input.getHeight()];
//
//
//        Log.e(TAG, "bitmap height = "+input.getHeight());
//
//
//
//
//        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
//        Log.e(TAG, "no of processors = "+nrOfProcessors);
//
//        int[] sections = new int[nrOfProcessors+1];
//
//
//        createBitmapSections(nrOfProcessors,sections);
//        ExecutorService threadPool = Executors.newFixedThreadPool(nrOfProcessors);
//
//        for(int g=0; g<sections.length;g++){
//            Log.e(TAG, "++++++++++ sections= "+sections[g]);
//        }
//
//        // ExecutorService threadPool = Executors.newFixedThreadPool(nrOfProcessors);
//
//        Object[] task = new Object[nrOfProcessors];
//
//        for(int z = 0; z < nrOfProcessors; z++){
//            task[z]  = (FutureTask<PartialResult>) threadPool.submit(new PartialProcessing(sections[z], sections[z+1] - 1, input, k));
//            Log.e(TAG, "++++++++++ task"+z+"= "+task[z].toString());
//        }
//
//        PartialResult[] results = new PartialResult[nrOfProcessors];
//
//        try{
//            for(int t = 0; t < nrOfProcessors; t++){
//
//                results[t] = ((FutureTask<PartialResult>) task[t]).get();
//
//                results[t].fill(arr);
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        Bitmap dst2 = Bitmap.createBitmap(arr,input.getWidth(),input.getHeight(),input.getConfig());
//
//
//        return dst2;
//
//
//    }//end of barrel()
//
//
//
//
//    public class PartialResult {
//        int startP;
//        int endP;
//        int[] storedValues;
//
//        public PartialResult(int startp, int endp, Bitmap input){
//
//            this.startP = startp;
//            this.endP = endp;
//            this.storedValues = new int[input.getWidth()*input.getHeight()];
//
//
//        }
//
//        public void addValue(int p, int result) {
//            storedValues[p] = result;
//
//        }
//
//        public void fill(int[] arr) {
//
//
//
//            for (int p = startP; p < endP; p++){
//                for(int b=0;b<radius;b++,x++)
//                    arr[x] = storedValues[x];
//
//            }
//            Log.e(TAG, "++++++++++ x ="+x);
//        }
//
//    }//end of partialResult
//
//
//
//
//    public class PartialProcessing implements Callable<PartialResult> {
//        int startJ;
//        int endJ;
//
//
//        private int[] scalar;
//        private float xscale;
//        private float yscale;
//        private float xshift;
//        private float yshift;
//        private float thresh = 1;
//        private int [] s1;
//        private int [] s2;
//        private int [] s3;
//        private int [] s4;
//        private int [] s;
//        private Bitmap input;
//        private float k;
//
//
//
//        public PartialProcessing(int startj, int endj, Bitmap input, float k) {
//
//            this.startJ = startj;
//            this.endJ = endj;
//            this.input = input;
//            this.k = k;
//
//            s = new int[4];
//            scalar = new int[4];
//            s1 = new int[4];
//            s2 = new int[4];
//            s3 = new int[4];
//            s4 = new int[4];
//
//        }
//
//        int [] getARGB(Bitmap buf,int x, int y){
//
//            int rgb = buf.getPixel(y, x); // Returns by default ARGB.
//            // int [] scalar = new int[4];
//            //  scalar[0] = (rgb >>> 24) & 0xFF;
//            scalar[1] = (rgb >>> 16) & 0xFF;
//            scalar[2] = (rgb >>> 8) & 0xFF;
//            scalar[3] = (rgb >>> 0) & 0xFF;
//            return scalar;
//
//        }
//
//
//
//        float getRadialX(float x,float y,float cx,float cy,float k){
//
//            x = (x*xscale+xshift);
//            y = (y*yscale+yshift);
//            float res = x+((x-cx)*k*((x-cx)*(x-cx)+(y-cy)*(y-cy)));
//            return res;
//        }
//
//        float getRadialY(float x,float y,float cx,float cy,float k){
//
//            x = (x*xscale+xshift);
//            y = (y*yscale+yshift);
//            float res = y+((y-cy)*k*((x-cx)*(x-cx)+(y-cy)*(y-cy)));
//            return res;
//        }
//
//
//
//        float calc_shift(float x1,float x2,float cx,float k){
//
//            float x3 = (float)(x1+(x2-x1)*0.5);
//            float res1 = x1+((x1-cx)*k*((x1-cx)*(x1-cx)));
//            float res3 = x3+((x3-cx)*k*((x3-cx)*(x3-cx)));
//
//            if(res1>-thresh && res1 < thresh)
//                return x1;
//            if(res3<0){
//                return calc_shift(x3,x2,cx,k);
//            }
//            else{
//                return calc_shift(x1,x3,cx,k);
//            }
//        }
//
//
//        void sampleImage(Bitmap arr, float idx0, float idx1)
//        {
//
//            // s = new int [4];
//            if(idx0<0 || idx1<0 || idx0>(arr.getHeight()-1) || idx1>(arr.getWidth()-1)){
//                s[0]=0;
//                s[1]=0;
//                s[2]=0;
//                s[3]=0;
//                return;
//            }
//
//            float idx0_fl=(float) Math.floor(idx0);
//            float idx0_cl=(float) Math.ceil(idx0);
//            float idx1_fl=(float) Math.floor(idx1);
//            float idx1_cl=(float) Math.ceil(idx1);
//
//
//
//            s1 = getARGB(arr,(int)idx0_fl,(int)idx1_fl);
//            s2 = getARGB(arr,(int)idx0_fl,(int)idx1_cl);
//            s3 = getARGB(arr,(int)idx0_cl,(int)idx1_cl);
//            s4 = getARGB(arr,(int)idx0_cl,(int)idx1_fl);
//
//            float x = idx0 - idx0_fl;
//            float y = idx1 - idx1_fl;
//
//            // s[0]= (int) (s1[0]*(1-x)*(1-y) + s2[0]*(1-x)*y + s3[0]*x*y + s4[0]*x*(1-y));
//            s[1]= (int) (s1[1]*(1-x)*(1-y) + s2[1]*(1-x)*y + s3[1]*x*y + s4[1]*x*(1-y));
//            s[2]= (int) (s1[2]*(1-x)*(1-y) + s2[2]*(1-x)*y + s3[2]*x*y + s4[2]*x*(1-y));
//            s[3]= (int) (s1[3]*(1-x)*(1-y) + s2[3]*(1-x)*y + s3[3]*x*y + s4[3]*x*(1-y));
//
//
//        }
//
//
//
//        @Override public PartialResult call() {
//
//            PartialResult partialResult = new PartialResult(startJ, endJ,input);
//
//            float centerX=input.getWidth()/2; //center of distortion
//            float centerY=input.getHeight()/2;
//
//
//
//            int width = input.getWidth(); //image bounds
//            int height = input.getHeight();
//
//
//
//            xshift = calc_shift(0,centerX-1,centerX,k);
//
//            float newcenterX = width-centerX;
//            float xshift_2 = calc_shift(0,newcenterX-1,newcenterX,k);
//
//            yshift = calc_shift(0,centerY-1,centerY,k);
//
//            float newcenterY = height-centerY;
//            float yshift_2 = calc_shift(0,newcenterY-1,newcenterY,k);
//
//            xscale = (width-xshift-xshift_2)/width;
//
//            yscale = (height-yshift-yshift_2)/height;
//
//
//            int p = startJ*radius;
//            int origPixel = 0;
//            int color = 0;
//            int i;
//
//            for (int j = startJ; j <  endJ; j++){
//
//                for ( i = 0; i < width; i++, p++){
//
//
//                    origPixel = input.getPixel(i,j);
//
//                    float x = getRadialX((float)j,(float)i,centerX,centerY,k);
//
//
//                    float y = getRadialY((float)j,(float)i,centerX,centerY,k);
//
//                    sampleImage(input,x,y);
//
//                    color = ((s[1]&0x0ff)<<16)|((s[2]&0x0ff)<<8)|(s[3]&0x0ff);
//                    //Log.e(TAG, "radius = "+radius);
//
//                    if(((i-centerX)*(i-centerX) + (j-centerY)*(j-centerY)) <= radius*(radius/4)){
//
//                        partialResult.addValue(p, color);
//
//
//                    }else{
//
//
//                        partialResult.addValue(p, origPixel);
//
//
//
//                    }
//
//                }//end of inner for
//
//            }//end of outer for
//
//            return partialResult;
//        }//end of call
//
//
//    }// end of partialprocessing
//
//}//end of MultiProcesorFilter
