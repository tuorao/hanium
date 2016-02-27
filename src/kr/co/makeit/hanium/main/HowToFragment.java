package kr.co.makeit.hanium.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HowToFragment extends Fragment {
	private int imgRes;
	private View mContentView;

	public static HowToFragment newInstance(int imgRes) {
		HowToFragment fragment = new HowToFragment();
		Bundle localBundle = new Bundle();
		localBundle.putInt("imgRes", imgRes);
		fragment.setArguments(localBundle);
		return fragment;
	}

	public void onActivityCreated(Bundle paramBundle) {
		this.imgRes = getArguments().getInt("imgRes");
		((ImageView) this.mContentView.findViewById(R.id.iv1))
				.setImageResource(this.imgRes);
		super.onActivityCreated(paramBundle);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.mContentView = paramLayoutInflater.inflate(
				R.layout.fragment_howtouse1, paramViewGroup, false);
		return this.mContentView;
	}
}
