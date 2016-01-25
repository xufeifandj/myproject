package com.yeyanxiang.project.indicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.yeyanxiang.view.indicator.LinePageIndicator;
import com.yeyanxiang.project.R;

public class SampleLinesStyledTheme extends BaseSampleActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// The look of this sample is set via a style in the manifest
		setContentView(R.layout.simple_lines);

		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (LinePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}
}