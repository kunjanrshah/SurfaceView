package com.drc.surfaceview;
 
import android.app.*;  
import android.os.*;  
import android.view.*;  
   
 public class SurfaceViews extends Activity {  
     HelloView view = null;  
     @Override  
     public void onCreate(Bundle savedInstanceState) {  
         super.onCreate(savedInstanceState);  
         requestWindowFeature(Window.FEATURE_NO_TITLE);  
         if (view == null)  
             view = new HelloView(this);  
             setContentView(view);  
     }  
 }  