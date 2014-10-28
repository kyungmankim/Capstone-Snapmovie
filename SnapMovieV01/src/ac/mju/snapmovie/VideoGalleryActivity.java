package ac.mju.snapmovie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ac.mju.util.BaseAlbumDirFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoGalleryActivity extends Activity {
	private AlertDialog alertDialog;
	private ListView listView;

	/**
	 * 특정 폴더만들어서 그곳에 전부 저장 및 가져와서 리스트업 해줘야 함.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_gallery);
		setUI();
		setResult();
	}

	private void setUI() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.filenamelist);

	}

	private void setResult() {
		// TODO Auto-generated method stub
		File[] listFiles = (new File(BaseAlbumDirFactory.getCameraDIR())
				.listFiles());
		List<String> list = new ArrayList<String>();
		for (File file : listFiles)
			list.add(file.getName());
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		listView.setAdapter(fileList);
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (alertDialog != null)
			alertDialog.dismiss();
	}

}
