package ac.mju.snapmovie;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import ac.mju.util.AlbumFactory;
import ac.mju.util.BaseAlbumDirFactory;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShootVideoActivity extends Activity {
	private Button mShutter;
	private MyCameraSurface mSurface;
	private String mRootPath;
	private static final String PICFOLDER = "CameraTest";
	private AlbumFactory mAlbumStorageDirFactory = new BaseAlbumDirFactory();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoot_video);

		mSurface = (MyCameraSurface) findViewById(R.id.previewFrame);
		mShutter = (Button) findViewById(R.id.button1);
		mShutter.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mSurface.getmCamera().autoFocus(mAutoFocus);
			}
		});

		/*mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/" + PICFOLDER;*/
		mRootPath = mAlbumStorageDirFactory.getAlbumStorageDirUrl(getAlbumName());
		File fRoot = new File(mRootPath);
		if (fRoot.exists() == false) {
			if (fRoot.mkdir() == false) {
				Toast.makeText(this, "사진을 저장할 폴더가 없습니다.", Toast.LENGTH_LONG)
						.show();
				finish();
				return;
			}
		}
	}

	// 포커싱 성공하면 촬영 허가
	Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {

			mShutter.setEnabled(success);

			mSurface.getmCamera().takePicture(null, null, mPicture);
		}
	};
	// 사진 저장.
	Camera.PictureCallback mPicture = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// 날짜로 파일 이름 만들기
			Calendar calendar = Calendar.getInstance();
			String FileName = String.format("SH%02d%02d%02d-%02d%02d%02d.jpg",
					calendar.get(Calendar.YEAR) % 100,
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE),
					calendar.get(Calendar.SECOND));
			String path = mRootPath + "/" + FileName;

			File file = new File(path);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				return;
			}
			// 파일을 갤러리에 저장
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://" + path);
			intent.setData(uri);
			sendBroadcast(intent);
			Toast.makeText(getApplicationContext(), "사진이 저장 되었습니다",
					Toast.LENGTH_SHORT).show();
			camera.startPreview();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shoot_video, menu);
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
	
	private String getAlbumName() {
		Log.i("getAlbumName", getString(R.string.album_name));
		return getString(R.string.album_name);
	}
}
