package com.scut.adrs.nlcomprehension.service.imp;

import java.io.FileNotFoundException;

import org.ansj.domain.Result;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.springframework.stereotype.Service;

import com.scut.adrs.nlcomprehension.service.DescriptionParser;
import com.scut.adrs.util.DicInsertUtil;
import com.scut.adrs.util.FileUtil;
/**
 * 描述解析器的Ansj实现，该实现包括对停用词设置、用户词典初始化等流程
 * @author FAN
 *
 */
@Service
public class AnsjDescriptionParser implements DescriptionParser {

	// 停用词过滤
	StopRecognition stopRecongnition;

	public AnsjDescriptionParser() {
		init();
	}

	public void init() {
		userLibraryInit();
		stopWordLibraryInit();
	}
	/**
	 * 返回符合业务需求的句子分词过程
	 * @return 分词结果
	 */
	@Override
	public Result parse(String description) {
		Result result = DicAnalysis.parse(description).recognition(stopRecongnition);
		return result;
	}

	private boolean userLibraryInit() {
		DicInsertUtil dicInsertUtil;
		return true;
	}

	private void stopWordLibraryInit() {
		stopRecongnition = new StopRecognition();
		String[] stopWords = null;
		try {
			stopWords = FileUtil.readFileByLines("classpath:stopwords.txt");
		} catch (FileNotFoundException e) {
			System.out.println("读取文件出错");
			e.printStackTrace();
		}
		stopRecongnition.insertStopWords(stopWords);
	}

	public StopRecognition getStopRecongnition() {
		return stopRecongnition;
	}

	public void setStopRecongnition(StopRecognition stopRecongnition) {
		this.stopRecongnition = stopRecongnition;
	}

}
