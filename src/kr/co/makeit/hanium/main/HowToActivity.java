package kr.co.makeit.hanium.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class HowToActivity extends FragmentActivity implements
		View.OnClickListener {
	private TextView bottomTextView = null;
	private HowToFragmentAdapter mAdapter = null;
	private PageIndicator indicator = null;
	private ViewPager viewPager = null;
	private TextView messageTextView = null;
	private Drawable rightDrawable = null;
	private boolean isFirstScreen = false;

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			if (HowToActivity.this.viewPager.getCurrentItem() < 3)
				HowToActivity.this.viewPager
						.setCurrentItem(1 + HowToActivity.this.viewPager
								.getCurrentItem());
		}
	};

	public void onBackPressed() {
		if (this.isFirstScreen)
			setResult(-1);
		finish();
		super.onBackPressed();
	}

	public void onClick(View paramView) {
		switch (this.viewPager.getCurrentItem()) {
		default:
			return;
		case 0:
		case 1:
		case 2:
			this.viewPager.setCurrentItem(3);
			return;
		case 3:
		}
		if (this.isFirstScreen)
			setResult(-1);
		while (true) {
			finish();
			return;
		}
	}

	public void onCloseClick(View paramView) {
		if (this.isFirstScreen)
			setResult(-1);
		finish();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.isFirstScreen = getIntent()
				.getBooleanExtra("isFirstScreen", false);
		setContentView(R.layout.fragment_howtouse);
		this.mAdapter = new HowToFragmentAdapter(getSupportFragmentManager());
		this.viewPager = ((ViewPager) findViewById(R.id.pager));
		this.viewPager.setAdapter(this.mAdapter);
		this.indicator = ((CirclePageIndicator) findViewById(R.id.indicator));
		this.indicator.setViewPager(this.viewPager);
		this.indicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					public void onPageScrollStateChanged(int paramInt) {
					}

					public void onPageScrolled(int paramInt1, float paramFloat,
							int paramInt2) {
						switch (paramInt1) {
						default:
							return;
						case 0:
							HowToActivity.this.handler
									.removeCallbacks(HowToActivity.this.runnable);
							HowToActivity.this.handler.postDelayed(
									HowToActivity.this.runnable, 5000L);
							HowToActivity.this.messageTextView
									.setText("설명 1");
							HowToActivity.this.bottomTextView.setText("SKIP");
							return;
						case 1:
							HowToActivity.this.handler
									.removeCallbacks(HowToActivity.this.runnable);
							HowToActivity.this.handler.postDelayed(
									HowToActivity.this.runnable, 5000L);
							HowToActivity.this.messageTextView
									.setText("설명 2");
							HowToActivity.this.bottomTextView.setText("SKIP");
							return;
						case 2:
							HowToActivity.this.handler
									.removeCallbacks(HowToActivity.this.runnable);
							HowToActivity.this.handler.postDelayed(
									HowToActivity.this.runnable, 5000L);
							HowToActivity.this.messageTextView
									.setText("설명 3");
							HowToActivity.this.bottomTextView.setText("SKIP");
							return;
						case 3:
							HowToActivity.this.handler
									.removeCallbacks(HowToActivity.this.runnable);
							HowToActivity.this.messageTextView
									.setText("이제 해볼까용?");
							HowToActivity.this.bottomTextView
									.setText("설명 마치기");
						}

					}

					public void onPageSelected(int paramInt) {
					}
				});
		this.messageTextView = ((TextView) findViewById(R.id.messageTextView));
		this.bottomTextView = ((TextView) findViewById(R.id.bottomTextView));
		findViewById(R.id.bottomLayout).setOnClickListener(this);
		HowToActivity.this.handler.removeCallbacks(HowToActivity.this.runnable);
		HowToActivity.this.handler.postDelayed(HowToActivity.this.runnable,
				5000L);
		HowToActivity.this.messageTextView.setText("이것을 이렇게하면 이렇게 할 수 있습니다.");
		HowToActivity.this.bottomTextView.setText("SKIP");
	}

	private class HowToFragmentAdapter extends FragmentPagerAdapter {
		public HowToFragmentAdapter(FragmentManager arg2) {
			super(arg2);
		}

		public int getCount() {
			return 4;
		}

		public Fragment getItem(int paramInt) {
			switch (paramInt) {
			default:
				return null;
			case 0:
				return HowToFragment.newInstance(R.drawable.page1);
			case 1:
				return HowToFragment.newInstance(R.drawable.page2);
			case 2:
				return HowToFragment.newInstance(R.drawable.page3);
			case 3:
			}
			return HowToFragment.newInstance(R.drawable.page4);
		}
	}
}