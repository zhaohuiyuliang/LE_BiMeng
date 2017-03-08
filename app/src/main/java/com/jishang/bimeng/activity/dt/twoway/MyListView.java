package com.jishang.bimeng.activity.dt.twoway;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView{
	
	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */	
	int mLastMotionY ;
	
	boolean bottomFlag;
	
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//闃绘鐖剁被鎷︽埅浜嬩欢
		if(bottomFlag){
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int y = (int) ev.getRawY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 棣栧厛鎷︽埅down浜嬩欢,璁板綍y鍧愭爣
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 鏄悜涓嬭繍鍔�,< 0鏄悜涓婅繍鍔�
			int deltaY = y - mLastMotionY;
			
			if(deltaY>0){
				View child = getChildAt(0);
				if(child!=null){
					
				
					if (getFirstVisiblePosition() == 0
							&& child.getTop() == 0) {
							bottomFlag = false;
							getParent().requestDisallowInterceptTouchEvent(false); 
						}
					
					int top = child.getTop();
					int padding = getPaddingTop();
					if (getFirstVisiblePosition() == 0
							&& Math.abs(top - padding) <= 8) {//杩欓噷涔嬪墠鐢�3鍙互鍒ゆ柇,浣嗙幇鍦ㄤ笉琛�,杩樻病鎵惧埌鍘熷洜
							bottomFlag = false;
							getParent().requestDisallowInterceptTouchEvent(false); 
				
						}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		
		
	
		return super.onTouchEvent(ev);
	}
	
	public void setBottomFlag(boolean flag){
		bottomFlag = flag;
	}
}
