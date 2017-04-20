package xzh.com.materialdesign.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MDSwipeRefreshLayout extends SwipeRefreshLayout {

	public MDSwipeRefreshLayout(Context context) {
		super(context);
	}
	
	public MDSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(!isEnabled()) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if(!isEnabled()) {
			return false;
		}
		return super.onTouchEvent(arg0);
	}
}