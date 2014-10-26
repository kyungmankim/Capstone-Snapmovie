package ac.mju.snapmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

public class AddAudioActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_audio);

		Button prevButton = (Button) findViewById(R.id.btn_videoFilter_prev);
		Button nextButton = (Button) findViewById(R.id.btn_videoFilter_next);
		VideoView FilterdVideo = (VideoView) findViewById(R.id.videoview_filtered_video);
		
		ListView audioList = (ListView)findViewById(R.id.list_audio);
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, audioNameArray);
	//	audioList.setAdapter(adapter);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_videoFilter_prev:
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.btn_videoFilter_next:
			startActivity(new Intent(this, MainActivity.class));
			break;
		}
	}
}
