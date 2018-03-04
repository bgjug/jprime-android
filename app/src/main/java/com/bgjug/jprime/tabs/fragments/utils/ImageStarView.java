package com.bgjug.jprime.tabs.fragments.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageStarView extends ImageView {

	public ImageStarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public ImageStarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ImageStarView(Context context) {
		super(context);
	}

	private boolean isActivated;

	public void setActive(boolean isActivated){
		this.isActivated = isActivated;
	}
	
	public boolean isActivated(){
		return isActivated;
	}
	
	public void changeState(){
		isActivated = !isActivated;
	}

}
