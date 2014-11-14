package ac.mju.snapmovie;

import java.io.File;
import java.util.ArrayList;

import ac.mju.util.BaseAlbumDirFactory;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoGalleryActivity extends Activity implements OnClickListener {
	// view
	private ListView listView;
	private VideoView selectVideo;

	// video
	private MediaController mediaController;

	// work
	private ArrayAdapter<String> fileList;
	private File[] listFiles;
	private ArrayList<String> list;

	/**
	 * 특정 폴더만들어서 그곳에 전부 저장 및 가져와서 리스트업 해줘야 함. 
	 * 비디오 사이즈 조절 추가하여야함
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_gallery);
		setUI();
		setResult();
		setVideoView();
	}

	private void setVideoView() {
		// TODO Auto-generated method stub
		mediaController = new MediaController(this);
		mediaController.setAnchorView(selectVideo);
		selectVideo.setMediaController(mediaController);

		Uri videoUri = null;
		if (listFiles[0] != null) {
			videoUri = Uri.parse(listFiles[0].getAbsolutePath());
			selectVideo.setVideoURI(videoUri);
			// selectVideo.requestFocus();
			selectVideo.start();
		}
	}

	private void setUI() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		Button prevButton = (Button) findViewById(R.id.btn_videoGallery_prev);
		prevButton.setOnClickListener(this);
		Button nextButton = (Button) findViewById(R.id.btn_videoGallery_next);
		nextButton.setOnClickListener(this);
		selectVideo = (VideoView) findViewById(R.id.selectVideo);
		listView = (ListView) findViewById(R.id.filenamelist);
	}

	private void setResult() {
		// TODO Auto-generated method stub
		listFiles = (this.getListFiles());
		list = new ArrayList<String>();
		// for (File file : listFiles) 오름차순
		// list.add(file.getName());
		if (listFiles != null) { // 내림차순
			for (int i = listFiles.length - 1; i != -1; i--) {
				File file = listFiles[i];
				list.add(file.getName());
			}
		}

		fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		listView.setAdapter(fileList);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.i("list", "call? " + position + "/" + id);

				boolean flag = swapItems(position);
				if (flag)
					fileList.notifyDataSetInvalidated();
				// listView.setSelectionFromTop(0, 0);
			}
		});
	}

	protected boolean swapItems(int position) {
		// TODO Auto-generated method stub
		if (position < 1)
			return false;
		File tempFile = listFiles[position];
		String tempString = list.get(position);

		list.set(position, list.get(position - 1));
		list.set(position - 1, tempString);

		listFiles[position] = listFiles[position - 1];
		listFiles[position - 1] = tempFile;

		return true;
	}

	private File[] getListFiles() {
		return new File(BaseAlbumDirFactory.getCameraDIR()).listFiles();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_gallery, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_videoGallery_prev:
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;
		case R.id.btn_videoGallery_next:
			startActivity(new Intent(this, VideoFilterActivity.class));
			finish();
			break;
		}
	}

}
