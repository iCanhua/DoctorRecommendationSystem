package com.doctor.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doctor.pojo.Doctor;

@Controller
public class RecommandController {

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
	 * @param symptoms
	 *            （传递过来的symptoms字符串，用逗号分隔）
	 * @return List<Doctor> （推荐医生列表的json字符串）
	 */
	@RequestMapping("/recommand")
	@ResponseBody
	public List<Doctor> recommand(String symptoms) {
		try {
			// get方法传递中文参数转码
			symptoms = new String(symptoms.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(symptoms);
		List<Doctor> list = new ArrayList<Doctor>();
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		return list;
	}

	/**
	 * 
	 * @param symptoms
	 *            （传递过来的symptoms字符串，用逗号分隔）
	 * @param model
	 *            （推荐医生列表保存在model中）
	 * @return 推荐医生列表界面
	 */
	@RequestMapping("/recommandJsp")
	public String recommandJsp(String symptoms, Model model) {
		try {
			// get方法传递中文参数转码
			symptoms = new String(symptoms.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(symptoms);
		List<Doctor> list = new ArrayList<Doctor>();
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		model.addAttribute("doctors", list);
		return "doctor";
	}
}
