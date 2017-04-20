package xzh.com.materialdesign.utils;

import android.os.Environment;

import java.io.File;

public class FileManager {

    private static String homeDir;

    public static String getHomeDir() {
        if (homeDir == null) {
            if (SysUtil.isSdExist()) {
                homeDir = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/MaterialDesign";
            } else {
                homeDir = Environment.getRootDirectory()
                        .getAbsolutePath() + "/MaterialDesign";
            }
        } else {
            File file = new File(homeDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return homeDir;
    }

    public static String getImgCacheDir() {
        return getHomeDir() + ImageUtil.TEMP_IMG_CACHE_FOLDER;
    }


}
