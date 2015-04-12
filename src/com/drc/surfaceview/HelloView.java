package com.drc.surfaceview;

import android.os.*;  
import android.content.*;  
import android.graphics.*;  
import android.util.*;  
import android.view.*;  

 public class HelloView extends SurfaceView implements SurfaceHolder.Callback {  
     private HelloThread mThread = null;  
   
     public HelloView(Context context) {  
         super(context);  
         SurfaceHolder holder = getHolder();  
         holder.addCallback(this);  
         mThread = new HelloThread(holder, context, new Handler());  
         setFocusable(true); // need to get the key events  
     }  
     public HelloThread getThread() {  
         return mThread;  
     }  
   
     public void surfaceCreated(SurfaceHolder holder) {  
         mThread.setRunning(true);  
         mThread.start();  
     }  
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {  
     }  
     public void surfaceDestroyed(SurfaceHolder holder) {  
         boolean retry = true;  
         mThread.setRunning(false);  
         while (retry) {  
             try {  
                 mThread.join();  
                 retry = false;  
             } catch (InterruptedException e) {  
             }  
         }  
     }  
   
     @Override  
     public boolean onKeyDown(int keyCode, KeyEvent event) {  
         mThread.doKeyDown(keyCode, event);  
         return super.onKeyDown(keyCode, event);  
     }  
     @Override  
     public boolean onKeyUp(int keyCode, KeyEvent event) {  
         mThread.doKeyUp(keyCode, event);  
         return super.onKeyUp(keyCode, event);  
     }  
   
   /*  class FPSTimer {  
         private int mFPS;  
         private double mSecPerFrame;  
         private double mSecTiming;  
         private long mCur;  
         public FPSTimer(int fps) {  
             mFPS = fps;  
             reset();  
         }  
         public void reset() {  
             mSecPerFrame = 1.0 / mFPS;  
             mCur = System.currentTimeMillis();  
             mSecTiming = 0.0;  
         }  
         public boolean elapsed() {  
             long next = System.currentTimeMillis();  
             long passage_time = next - mCur;  
             mCur = next;  
             mSecTiming += (passage_time/1000.0);  
             mSecTiming -= mSecPerFrame;  
             if (mSecTiming > 0) {  
                 if (mSecTiming > mSecPerFrame) {  
                     reset();  
                     return true; // force redraw  
                 }  
                 return false;  
             }  
             try {  
                 Thread.sleep((long)(-mSecTiming * 1000.0));  
            } catch (InterruptedException e) {  
             }  
             return true;  
         }  
     }     */

     class HelloThread extends Thread {  
         private int mX = 0;  
         private int mY = 0;  
         private int mKeyCode = -1;  
         private SurfaceHolder mSurfaceHolder;  
         private boolean mRun = false;  
         public HelloThread(SurfaceHolder holder, Context context, Handler handler) {  
             mSurfaceHolder = holder;  
         }  
         public void setRunning(boolean b) {  
             mRun = b;  
         }  
         public void doKeyDown(int keyCode, KeyEvent msg) {  
             mKeyCode = keyCode;  
         }  
         public void doKeyUp(int keyCode, KeyEvent msg) {  
             mKeyCode = -1;  
         }  
         public void run() {  
            // int fps = 0;  
             long cur = System.currentTimeMillis();  
             boolean isdraw = true;  
           //  FPSTimer timer = new FPSTimer(60);  
             while (mRun) {  
                 Canvas c = null;  
                 if (isdraw) {  
                     try {  
                         c = mSurfaceHolder.lockCanvas(null);  
                         synchronized (mSurfaceHolder) {  
                             doDraw(c);  
                         }  
                        // fps++;  
                     } finally {  
                         if (c != null)  
                             mSurfaceHolder.unlockCanvasAndPost(c);  
                     }  
                 }  
              /*   isdraw = timer.elapsed();  
                 long now = System.currentTimeMillis();  
                 if (now - cur > 1000) {  
                     Log.d("KZK", "FPS=" + (fps * 1000 / ((double)now - cur)));  
                     fps = 0;  
                     cur = now;  
                 }  */
             }  
         }  
   
         protected void doDraw(Canvas canvas) {  
             Paint paint = new Paint(); 
             
             //paint.setAntiAlias(true);  
             Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.icon);
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_UP) mY--;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_DOWN) mY++;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_LEFT) mX--;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT) mX++; 
             canvas.drawBitmap(bitmap, mX, mY, paint);
             
             
           /*  if (mKeyCode == KeyEvent.KEYCODE_DPAD_UP) mY--;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_DOWN) mY++;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_LEFT) mX--;  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT) mX++;  
             paint.setStyle(Paint.Style.FILL);  
             paint.setColor(Color.argb(255,255,255,0));  
             canvas.drawRect(new Rect(mX+0,mY+0,mX+40,mY+40),paint);  
   
             String str = "";  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_UP) str = "DPAD_UP";  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_DOWN) str = "DPAD_DOWN";  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_LEFT) str = "DPAD_LEFT";  
             if (mKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT) str = "DPAD_RIGHT";  
             canvas.drawText("keyCode>" + mKeyCode + " " + str, 0, 250, paint);  */
         }  
    }  
 }  