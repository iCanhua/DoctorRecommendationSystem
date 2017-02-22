package com.scut.adrs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

public class FileUtil {
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @throws FileNotFoundException
	 */
	public static String[] readFileByLines(String fileName) throws FileNotFoundException {
		String[] stopWords = new String[3000];
		File file = ResourceUtils.getFile(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				stopWords[line] = tempString;
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return stopWords;
	}
}
