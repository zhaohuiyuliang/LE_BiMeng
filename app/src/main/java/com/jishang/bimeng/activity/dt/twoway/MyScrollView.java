package com.jishang.bimeng.activity.dt.twoway;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView{
	
//	private ScrollViewListener scrollViewListener = null; 
	
	public interface OnGetBottomListener {
        public void onBottom();
    }
	
	private OnGetBottomListener onGetBottomListener;
	
	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
//	  public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
//	        this.scrollViewListener = scrollViewListener;  
//	    }  
	  
	    @Override  
	    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
	    	super.onScrollChanged(x, y, oldx, oldy);  
	    
//	        if(scrollViewListener != null) {  
//	            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
//	        }  
	        //鍒板簳閮紝缁檒istview鏀炬澗娑堟伅锛岃鍏惰幏鍙栬Е鎽镐簨浠�
	        if( getChildCount()>=1&&getHeight() + getScrollY() == getChildAt(getChildCount()-1).getBottom()){
				  onGetBottomListener.onBottom();
			}
	    }  
	    
	    public interface ScrollViewListener {    
	        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);   
	    }  
 
	    public void setBottomListener(OnGetBottomListener listener){
	    	onGetBottomListener = listener;
	    }

}
