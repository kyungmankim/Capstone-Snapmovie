package ac.mju.snapmovie;

import java.io.File;

import ac.mju.util.AlbumFactory;
import ac.mju.util.BaseAlbumDirFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

// Uri fileUri; 이부분 비디오 저장위치 지정하는 부분.
/*
 * fileUri = getOutputMediaFileUri(); // create a file to save the video
 * takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set
 * the image file name
 * takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // set
 * the video image quality to high
 */
// Action for 'Yes' Button
// Action for 'NO' Button
// 본 액티비티 피니쉬 후 다시시도
// ACTION_TAKE_VIDEO
// switch
// TODO Auto-generated method stub
// mVideoUri = (Uri)intent.getExtras().get("data");
// 비디오뷰에 플ㄹㄹ레이할것인지?
public class VideoActivity extends Activity {
	private static final int ACTION_TAKE_VIDEO = 3;

	private static final String VIDEO_STORAGE_KEY = "viewvideo";

	private Uri mVideoUri;

	private AlbumFactory mAlbumStorageDirFactory = null;

	private boolean actionFlag;
	private boolean confirmFlag;

	private AlertDialog alertDialog;

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

	/*
	 * private File getAlbumDir() { File storageDir = null;
	 * 
	 * if (Environment.MEDIA_MOUNTED.equals(Environment
	 * .getExternalStorageState())) {
	 * 
	 * storageDir = mAlbumStorageDirFactory .getAlbumStorageDir(getAlbumName());
	 * 
	 * if (storageDir != null) { if (!storageDir.mkdirs()) { if
	 * (!storageDir.exists()) { Log.d("SnapMovie",
	 * "failed to create directory"); return null; } } }
	 * 
	 * } else { Log.v(getString(R.string.app_name),
	 * "External storage is not mounted READ/WRITE."); }
	 * 
	 * return storageDir; }
	 */

	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		// Uri fileUri; 이부분 비디오 저장위치 지정하는 부분.
		/*
		 * fileUri = getOutputMediaFileUri(); // create a file to save the video
		 * takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set
		 * the image file name
		 * takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // set
		 * the video image quality to high
		 */

		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("카메라 촬영 완료", data.toString());
		switch (requestCode) {
		case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				handleCameraVideo(data);

				// 여기서 알ㄹ러트창 띄워주ㅝ야함 confirmFlag 변경하고

				if (confirmFlag == true) {
					// 본 액티비티 피니쉬 후 다시시도
				} else {
					Intent intent = new Intent(this, VideoGalleryActivity.class);
					startActivity(intent);
					finish();
				}
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
		// 비디오뷰에 플레이할것인지?
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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (alertDialog != null)
			alertDialog.dismiss();
	}
	/*
	 * 알러트창 AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
	 * alertBuilder.setMessage("동영상을 더 찍으시겠습니까?" ).setCancelable(false);
	 * alertBuilder.setPositiveButton("네", new DialogInterface.OnClickListener()
	 * { public void onClick(DialogInterface dialog, int id) { // Action for
	 * 'Yes' Button confirmFlag = true; dialog.dismiss(); }
	 * }).setNegativeButton("다음단계로", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int id) { // Action for 'NO'
	 * Button confirmFlag = false; dialog.cancel(); } }); alertDialog =
	 * alertBuilder.create(); alertDialog.setTitle("진행 확인"); alertDialog.show();
	 */
}
