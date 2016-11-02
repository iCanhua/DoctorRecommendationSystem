package com.scut.adrs.util;

import java.io.UnsupportedEncodingException;

public class EncodingConvert {
	public static String convert(String string) {

		try {
			if (string != null) {
				// get方法传递中文参数转码
				string = new String(string.getBytes("iso-8859-1"), "utf-8");
			} else {
				string = "";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
}
