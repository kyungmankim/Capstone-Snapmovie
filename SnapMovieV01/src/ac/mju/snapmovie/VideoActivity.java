package ac.mju.snapmovie;

import ac.mju.util.AlbumFactory;
import ac.mju.util.BaseAlbumDirFactory;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class VideoActivity extends Activity {
	private static final int ACTION_TAKE_VIDEO = 3;
	private static final String VIDEO_STORAGE_KEY = "viewvideo";

	private Uri mVideoUri;

	private AlbumFactory mAlbumStorageDirFactory = null;

	private boolean actionFlag;
	private boolean confirmFlag;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		actionFlag = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		callVideoIntent();
	}

	private void callVideoIntent() {
		if (actionFlag) {
			dispatchTakeVideoIntent();
			actionFlag = false;
		}

	}

	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		Uri fileUri = getOutputMediaFileUri();

		takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set
		takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // set

		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}

	private Uri getOutputMediaFileUri() {
		String fileName;
		long t = System.currentTimeMillis();
		String inDate = new java.text.SimpleDateFormat("yyyyMMdd")
				.format(new java.util.Date());
		String inTime = new java.text.SimpleDateFormat("HHmmss")
				.format(new java.util.Date());
		fileName = "SnapMovie_" + inDate + "_" + inTime;
		return Uri.fromFile(mAlbumStorageDirFactory
				.getAlbumStorageDir(fileName));
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("카메라 촬영 완료", "촬ㄹ영끝");
		switch (requestCode) {
		case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				Log.i("누가먼저", "onActivityResult");
				new DialogAsyncTask().execute("1", "2", "3", "4", "5");
			} else {
				Log.e("Error", "카메라 촬영 실패");
			}
			break;
		} // ACTION_TAKE_VIDEO
		} // switch
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
	}

	class DialogAsyncTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			AlertDialog.Builder alert_confirm = new AlertDialog.Builder(
					VideoActivity.this);
			alert_confirm
					.setMessage("재촬영 하시겠습니까?")
					.setCancelable(false)
					.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 'YES'
									confirmFlag = false;
									Intent intent = new Intent(
											VideoActivity.this,
											VideoGalleryActivity.class);
									startActivity(intent);
									finish();
								}
							})
					.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 'No'
									confirmFlag = true;
									actionFlag = true;
									finish();
									startActivity(new Intent(
											VideoActivity.this,
											VideoActivity.class));
								}
							});
			AlertDialog alert = alert_confirm.create();
			alert.show();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(String... params) {
			return new Long(1);
		}
	}

}
