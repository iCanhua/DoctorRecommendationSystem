package com.scut.adrs.nlcomprehension.service.imp;
import com.github.andrewoma.dexx.collection.ArrayList;
import com.scut.adrs.nlcomprehension.service.*;
import com.scut.adrs.util.DicInsertUtil;

import java.util.List;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.*;
import org.ansj.recognition.Recognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.*;
import org.springframework.stereotype.Service;

@Service
public class AnsjDescriptionParser implements DescriptionParser{
	
	public AnsjDescriptionParser(){
		userLibraryInit();
	}
	
	@Override
	public Result parse(String description) {
		Result result= DicAnalysis.parse(description);
		return result;
	}
	
	private boolean userLibraryInit(){
		DicInsertUtil dicInsertUtil;
		return true;
	}
	
	private void stopWordLibraryInit(){ 	
	}
}
