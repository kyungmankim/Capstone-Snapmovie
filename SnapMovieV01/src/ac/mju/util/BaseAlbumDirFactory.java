package ac.mju.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by �?경만 on 2014-09-26.
 */
public final class BaseAlbumDirFactory extends AlbumFactory {

	// Standard storage location for digital camera files
	private static final String CAMERA_DIR = "/dcim/";

	@Override
	public File getAlbumStorageDir(String albumName) {
		return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
	}

	@Override
	public String getAlbumStorageDirUrl(String albumName) {
		return Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName;
	}
}
