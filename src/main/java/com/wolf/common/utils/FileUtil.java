package com.wolf.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.multipart.MultipartFile;

import com.wolf.extra.video.database.entity.Video;

/**
 * 文件操作类
 */
public final class FileUtil {

	/**
	 * 获取文件MIME
	 * @param filename
	 * @return
	 */
	public static String getContentType(String filePath) {
		String type = null;
		Path path = Paths.get(filePath);
		try {
			type = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return type;
	}

	/**
	 * 获取文件拓展名
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static void transferTo(MultipartFile source, File target)
			throws IllegalStateException, IOException {
		if (source == null) {
			throw new FileNotFoundException("File source not found. ");
		}

		if (target == null) {
			throw new FileNotFoundException("File target not found. ");
		}

		if (!target.getParentFile().exists()) {
			target.getParentFile().mkdirs();
		}
		source.transferTo(target);
	}
}
