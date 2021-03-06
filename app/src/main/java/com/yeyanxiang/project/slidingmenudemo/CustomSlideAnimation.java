package com.yeyanxiang.project.slidingmenudemo;

import android.graphics.Canvas;
import android.view.animation.Interpolator;

import com.yeyanxiang.project.R;
import com.yeyanxiang.view.slidemenu.SlidingMenu.CanvasTransformer;

/**
 * @author 叶雁翔
 * 
 * @Email yanxiang1120@gmail.com
 * 
 * @version 1.0
 * 
 * @update 2014年3月14日
 * 
 * @简介
 */
public class CustomSlideAnimation extends CustomAnimation {

	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	public CustomSlideAnimation() {
		// see the class CustomAnimation for how to attach
		// the CanvasTransformer to the SlidingMenu
		super(R.string.anim_slide, new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.translate(
						0,
						canvas.getHeight()
								* (1 - interp.getInterpolation(percentOpen)));
			}
		});
	}

}
