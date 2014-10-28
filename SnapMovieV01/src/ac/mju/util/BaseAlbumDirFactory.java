package ac.mju.util;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by �?경만 on 2014-09-26.
 */
public final class BaseAlbumDirFactory extends AlbumFactory {

	// Standard storage location for digital camera files
	private static final String CAMERA_DIR = "/dcim/SnapMovie/";
	private static final String CAMERA_EXTENTION = ".mp4";

	public static String getCameraDIR(){
		return Environment.getExternalStorageDirectory()
				+ CAMERA_DIR;
	}
	
	public BaseAlbumDirFactory() {
		// TODO Auto-generated constructor stub
		File file = new File(Environment.getExternalStorageDirectory()
				+ CAMERA_DIR);
		if (!file.exists()) // 원하는 경로에 폴더가 있는지 확인
			file.mkdirs();
	}

	@Override
	public File getAlbumStorageDir(String fileName) {
		return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ fileName + CAMERA_EXTENTION);
	}

	@Override
	public String getAlbumStorageDirUrl(String albumName) {
		return Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName;
	}
}
