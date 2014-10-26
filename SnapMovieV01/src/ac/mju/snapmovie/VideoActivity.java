package ac.mju.snapmovie;

import java.io.File;

import ac.mju.util.AlbumFactory;
import ac.mju.util.BaseAlbumDirFactory;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;



public class VideoActivity extends Activity {
	private static final int ACTION_TAKE_VIDEO = 3;

	private static final String VIDEO_STORAGE_KEY = "viewvideo";

	private Uri mVideoUri;

	private AlbumFactory mAlbumStorageDirFactory = null;

	private boolean actionFlag;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		actionFlag = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("on Resume", "error");
		mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		if (actionFlag) {
			dispatchTakeVideoIntent();
			actionFlag = false;
		}
	}

	private String getAlbumName() {
		Log.i("getAlbumName", getString(R.string.album_name));
		return getString(R.string.album_name);
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("SnapMovie", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("카메라 촬영 완료", data.toString());
		switch (requestCode) {
		case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				handleCameraVideo(data);
			} else {
				Log.e("Error", "카메라 촬영 실패");
			}
			break;
		} // ACTION_TAKE_VIDEO
		} // switch
	}

	private void handleCameraVideo(Intent intent) {
		// mVideoUri = (Uri)intent.getExtras().get("data");
		mVideoUri = intent.getData();
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
}
