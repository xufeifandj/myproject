package com.yeyanxiang.project.indicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.yeyanxiang.view.indicator.TitlePageIndicator;
import com.yeyanxiang.view.indicator.TitlePageIndicator.IndicatorStyle;
import com.yeyanxiang.view.indicator.TitlePageIndicator.OnCenterItemClickListener;
import com.yeyanxiang.project.R;

public class SampleTitlesCenterClickListener extends BaseSampleActivity
		implements OnCenterItemClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_titles);

		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
		indicator.setOnCenterItemClickListener(this);
		mIndicator = indicator;
	}

	@Override
	public void onCenterItemClick(int position) {
		Toast.makeText(this, "You clicked the center title!",
				Toast.LENGTH_SHORT).show();
	}
}