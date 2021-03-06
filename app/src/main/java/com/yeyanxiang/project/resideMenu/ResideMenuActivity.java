package com.yeyanxiang.project.resideMenu;

import com.yeyanxiang.project.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * @author 叶雁翔
 * 
 * @Email yanxiang1120@gmail.com
 * 
 * @version 1.0
 * 
 * @update 2014年3月13日
 * 
 * @简介
 */
public class ResideMenuActivity extends Activity implements
		View.OnClickListener {

	private ResideMenu resideMenu;
	private ResideMenuActivity mContext;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.residemain);
		mContext = this;
		setUpViews();
		setUpMenu();
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);

		// create menu items;
		String titles[] = { "Home", "Profile", "Calendar", "Settings" };
		int icon[] = { R.drawable.icon_home, R.drawable.icon_profile,
				R.drawable.icon_calendar, R.drawable.icon_settings };

		for (int i = 0; i < titles.length; i++) {
			ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
			item.setOnClickListener(this);
			resideMenu.addMenuItem(item);
		}

		// add gesture operation's ignored views
		FrameLayout ignored_view = (FrameLayout) findViewById(R.id.ignored_view);
		resideMenu.addIgnoredView(ignored_view);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.onInterceptTouchEvent(ev)
				|| super.dispatchTouchEvent(ev);
	}

	private void setUpViews() {
		Button btn_open = (Button) findViewById(R.id.btn_open_menu);
		btn_open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resideMenu.openMenu();
			}
		});
	}

	@Override
	public void onClick(View view) {
		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
		}
	};
}
