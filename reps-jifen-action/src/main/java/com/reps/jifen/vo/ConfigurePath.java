package com.reps.jifen.vo;

import com.reps.core.RepsContext;

public class ConfigurePath {

	/**图片上传路径*/
	public static final String IMG_UPLOAD_PATH = RepsContext.getConst("jifen", "imageUploadPath");
	
	/**图片文件路径*/
	public static final String IMG_FILE_PATH = RepsContext.getConst("jifen", "imagePath");
}
