package com.scut.adrs.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;

public class SortUtil {
	public static void disaseSort(List<Map.Entry<Disease, Float>> diseaseList) {
		Collections.sort(diseaseList,
				new Comparator<Map.Entry<Disease, Float>>() {
					public int compare(Map.Entry<Disease, Float> o1,
							Map.Entry<Disease, Float> o2) {
						if ((o2.getValue() - o1.getValue()) > 0)
							return 1;
						else if ((o2.getValue() - o1.getValue()) == 0)
							return 0;
						else
							return -1;
					}
				});
	}

	public static void doctorSort(List<Map.Entry<Doctor, Float>> doctorList) {
		Collections.sort(doctorList,
				new Comparator<Map.Entry<Doctor, Float>>() {
					public int compare(Map.Entry<Doctor, Float> o1,
							Map.Entry<Doctor, Float> o2) {
						if ((o2.getValue() - o1.getValue()) > 0)
							return 1;
						else if ((o2.getValue() - o1.getValue()) == 0)
							return 0;
						else
							return -1;
					}
				});
	}
}
