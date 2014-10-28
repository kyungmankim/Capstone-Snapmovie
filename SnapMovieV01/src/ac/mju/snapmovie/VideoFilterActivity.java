package ac.mju.snapmovie;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

public class VideoFilterActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_filter);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		Button prevButton = (Button) findViewById(R.id.btn_videoFilter_prev);
		prevButton.setOnClickListener(this);
		Button nextButton = (Button) findViewById(R.id.btn_videoFilter_next);
		nextButton.setOnClickListener(this);
		VideoView FilterdVideo = (VideoView) findViewById(R.id.videoview_filtered_video);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_videoFilter_prev:
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.btn_videoFilter_next:
			startActivity(new Intent(this, AddAudioActivity.class));
			break;
		}
	}
}
