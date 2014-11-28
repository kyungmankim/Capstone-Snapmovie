package ac.mju.snapmovie;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import ac.mju.util.BaseAlbumDirFactory;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class AddAudioActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private ListView audioList;
	private ListView voiceList;
	private MediaPlayer audioPlayer;
	private MediaController videocontroller;
	private boolean isPlaying = false;
	private Button prevButton;
	private Button nextButton;
	private VideoView FilterdVideo;
	private HashMap<String, Integer> audioMap;
	private String videoFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_audio);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		prevButton = (Button) findViewById(R.id.btn_videoFilter_prev);
		prevButton.setOnClickListener(this);
		nextButton = (Button) findViewById(R.id.btn_videoFilter_next);
		nextButton.setOnClickListener(this);

		FilterdVideo = (VideoView) findViewById(R.id.videoview_filtered_video);
		videoFilePath = BaseAlbumDirFactory
				.getAlbumStorageDirPath("SnapMovie_20141028_155126");
		Log.e("VideoFilePath", videoFilePath);
		FilterdVideo.setVideoPath(videoFilePath);
		FilterdVideo.start();

		videocontroller = new MediaController(AddAudioActivity.this);
		FilterdVideo.setMediaController(videocontroller);
		FilterdVideo.postDelayed(new Runnable() {
			public void run() {
				videocontroller.show(0);
			}
		}, 100);
		FilterdVideo.start();

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
		if (audioPlayer != null) {
			audioPlayer.stop();
			audioPlayer = null;
			isPlaying = false;
		}

		String audioName = audioList.getItemAtPosition(position).toString();
		int audioId = audioMap.get(audioName);
		audioPlayer = MediaPlayer.create(this, audioId);

		audioPlayer.start();
		isPlaying = true;
		Log.e("start: isplaying", String.valueOf(isPlaying));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_videoFilter_prev:
			startActivity(new Intent(this, VideoFilterActivity.class));
			finish();
			break;
		case R.id.btn_videoFilter_next:
			startActivity(new Intent(this, MainActivity.class));
			Toast.makeText(AddAudioActivity.this, "완료되었습니다", Toast.LENGTH_SHORT)
					.show();
			this.deleteVideos();
			finish();
			break;
		}
	}

	private void deleteVideos() {
		// TODO Auto-generated method stub
		// 나머지 비디오 삭제
	}

	public void onDestroy() {
		super.onDestroy();
		if (audioPlayer != null) {
			audioPlayer.release();
		}
		audioPlayer = null;
	}
}
