package com.scut.adrs.recommendation.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CosinSimTool {
	boolean isException=false;
	Map<String, int[]> vectorMap = new HashMap<String, int[]>();

    int[] tempArray = null;
    //构造器1
    public CosinSimTool(String[] strArray1, String[] strArray2) {
        for (String str: strArray1) {
            if (vectorMap.containsKey(str)) {
                vectorMap.get(str)[0]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(str, tempArray);
            }
        }
        for (String str : strArray1) {
            if (vectorMap.containsKey(str)) {
                vectorMap.get(str)[1]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 0;
                tempArray[1] = 1;
                vectorMap.put(str, tempArray);
            }
        }
    }
    //构造器2
    public CosinSimTool(ArrayList<String> strArray1, ArrayList<String> strArray2) {
    	if(strArray1.size()==0||strArray2.size()==0){
    		isException=true;
    	}
    	//System.out.println("strArray1的值为："+strArray1.toString());
    	//System.out.println("strArray2的值为："+strArray2.toString());
    	
        for (String str: strArray1) {
            if (vectorMap.containsKey(str)) {
                vectorMap.get(str)[0]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(str, tempArray);
            }
        }
        for (String str : strArray2) {
            if (vectorMap.containsKey(str)) {
                vectorMap.get(str)[1]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 0;
                tempArray[1] = 1;
                vectorMap.put(str, tempArray);
            }
        }
    }

    // 求余弦相似度
    public Double sim() {
    	if(isException){
    		return 0D;
    	}
//    	for(String str:vectorMap.keySet()){
//    		System.out.println("疾病"+str+"的两个维分别为："+vectorMap.get(str)[0]+"  "+vectorMap.get(str)[1]);
//    	}
        double result = 0;
        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
       // System.out.println("consin里面计算出来的余弦相似度为："+result);
        return result;
    }

    private double sqrtMulti(Map<String, int[]> paramMap) {
        double result = 0;
        result = squares(paramMap);
        result = Math.sqrt(result);
        return result;
    }

    // 求平方和
    private double squares(Map<String, int[]> paramMap) {
        double result1 = 0;
        double result2 = 0;
        Set<String> keySet = paramMap.keySet();
        for (String str : keySet) {
            int temp[] = paramMap.get(str);
            result1 += (temp[0] * temp[0]);
            result2 += (temp[1] * temp[1]);
        }
        return result1 * result2;
    }

    // 点乘法
    private double pointMulti(Map<String, int[]> paramMap) {
        double result = 0;
        Set<String> keySet = paramMap.keySet();
        for (String str : keySet) {
            int temp[] = paramMap.get(str);
            result += (temp[0] * temp[1]);
        }
        return result;
    }
}
