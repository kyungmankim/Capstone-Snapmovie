package ac.mju.util;

import java.io.File;

/**
 * Created by �?경만 on 2014-09-26.
 */
public abstract class AlbumFactory {
    public abstract File getAlbumStorageDir(String albumName);

	public abstract String getAlbumStorageDirUrl(String albumName) ;
}
