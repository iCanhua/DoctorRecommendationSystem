package com.scut.adrs.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.recommendation.service.TestService;

@Controller
public class RecommendController {
	@Autowired
	TestService testService;

	/**
	 * 
	 * @return 症状选择页面
	 */
	@RequestMapping("/symptom")
	public String symptom() {
		return "symptom";
	}

	/**
	 * 
	 * @return 异步获取症状
	 */
	@RequestMapping("/getSymptoms")
	@ResponseBody
	public List<String> getSymptoms() {
		List<String> symptoms = new ArrayList<String>();
		symptoms.add("鼻塞");
		symptoms.add("流鼻涕");
		symptoms.add("鼻出血");
		symptoms.add("流鼻血");
		symptoms.add("嗅觉障碍");
		symptoms.add("鼻痛");
		symptoms.add("鼻腔有异物");
		symptoms.add("鼻子红肿");
		return symptoms;
	}

	/**
	 * 
	 * @param symptoms
	 *            （患者选择的症状）
	 * @param model
	 *            （传递问题参数）
	 * @return
	 */
	@RequestMapping("/questionListJsp")
	public String questionListJsp(String symptoms, Model model) {
		try {
			if (symptoms != null) {
				// get方法传递中文参数转码
				symptoms = new String(symptoms.getBytes("iso-8859-1"), "utf-8");
			} else {
				symptoms = "";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Question> list = new ArrayList<Question>();
		List<String> choices1 = new ArrayList<String>();
		choices1.add("呼吸困难");
		choices1.add("胸闷");
		choices1.add("咳嗽");
		choices1.add("乏力");
		choices1.add("胸痛");
		Question question1 = new Question("您是否伴随有以下症状：", choices1);
		List<String> choices2 = new ArrayList<String>();
		choices2.add("粉尘吸入史");
		choices2.add("吸烟");
		choices2.add("剧烈运动");
		choices2.add("化学毒物接触史");
		choices2.add("矿井作业");
		Question question2 = new Question("您在发病前是否存在以下情况：", choices2);
		List<String> choices3 = new ArrayList<String>();
		choices3.add("近期呼吸道感染");
		choices3.add("过敏体质");
		choices3.add("动植物接触史");
		Question question3 = new Question("您在发病前是否存在以下情况：", choices3);
		list.add(question1);
		list.add(question2);
		list.add(question3);
		model.addAttribute("questions", list);
		model.addAttribute("symptoms", symptoms);
		return "question";
	}

	/**
	 * 
	 * @param symptoms
	 *            （传递过来的symptoms字符串，用逗号分隔）
	 * @param model
	 *            （推荐医生列表保存在model中）
	 * @return 推荐医生列表界面
	 */
	@RequestMapping("/recommendJsp")
	public String recommendJsp(String symptoms, Model model) {
		try {
			if (symptoms != null) {
				// get方法传递中文参数转码
				symptoms = new String(symptoms.getBytes("iso-8859-1"), "utf-8");
			} else {
				symptoms = "";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Doctor> list = testService.getDoctor();
		model.addAttribute("doctors", list);
		return "doctor";
	}

}
