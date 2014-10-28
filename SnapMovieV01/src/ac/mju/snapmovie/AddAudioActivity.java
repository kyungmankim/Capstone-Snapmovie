package ac.mju.snapmovie;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

public class AddAudioActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private ListView audioList;
	private ListView voiceList;
	private MediaPlayer player;
	private boolean isPlaying = false;
	private Button prevButton;
	private Button nextButton;
	private VideoView FilterdVideo;
	private HashMap<String, Integer> audioMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_audio);

		prevButton = (Button) findViewById(R.id.btn_videoFilter_prev);
		prevButton.setOnClickListener(this);
		nextButton = (Button) findViewById(R.id.btn_videoFilter_next);
		nextButton.setOnClickListener(this);
		FilterdVideo = (VideoView) findViewById(R.id.videoview_filtered_video);
		audioList = (ListView) findViewById(R.id.list_audio);
		voiceList = (ListView) findViewById(R.id.list_voice);
		
		audioMap = new HashMap<String, Integer>();
		audioMap.put("kalimba", R.raw.kalimba);
		audioMap.put("maid_with_the_flaxen_hair",
				R.raw.maid_with_the_flaxen_hair);
		audioMap.put("sleep_away", R.raw.sleep_away);

		ArrayList<String> audioNameList = new ArrayList<String>();
		audioNameList.add("kalimba");
		audioNameList.add("maid_with_the_flaxen_hair");
		audioNameList.add("sleep_away");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, audioNameList);

		audioList.setAdapter(adapter);
		audioList.setOnItemClickListener(this);
		voiceList.setAdapter(adapter);
		voiceList.setOnItemClickListener(this);

	}

	private void getAudioFile() {
		// TODO Auto-geneFiled method stub
		ArrayList<File> audioFileList = new ArrayList<File>();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.e("stop: isplaying", String.valueOf(isPlaying));
		if (player != null) {
			player.stop();
			player = null;
			isPlaying = false;
		}

		String audioName = audioList.getItemAtPosition(position).toString();
		int audioId = audioMap.get(audioName);
		player = MediaPlayer.create(this, audioId);

		player.start();
		isPlaying = true;
		Log.e("start: isplaying", String.valueOf(isPlaying));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_videoFilter_prev:
			startActivity(new Intent(this, VideoFilterActivity.class));
			break;
		case R.id.btn_videoFilter_next:
			startActivity(new Intent(this, MainActivity.class));
			break;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.release();
		}
		player = null;
	}
}
