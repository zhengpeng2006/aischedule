package com.asiainfo.monitor.tools.util;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class SortUtil {

	/**
	 * 插入排序
	 * @param src
	 */
	public static void InsertionSort(int[] src) {
		for (int i = 1; i < src.length; i++) {
			int now = i;
			int frank = src[i];
			while (now > 0 && src[now - 1] <= frank) {
				src[now] = src[now - 1];
				now--;
			}
			src[now] = frank;

		}
	}

	/**
	 * 选择[排序
	 * @param src
	 */
	public static void SelectSort(int[] src) {
		int max;
		int stmp;
		for (int i = 0; i < src.length - 1; i++) {
			max = i;
			for (int j = i + 1; j < src.length; j++)
				if (src[j] > src[max])
					max = j;

			stmp = src[i];
			src[i] = src[max];
			src[max] = stmp;

		}
	}

	/**
	 * 冒泡排序
	 * @param src
	 */
	public static void BubbleSort(int[] src) {
		int stmp;
		for (int i = 1; i < src.length; i++) {
			for (int j = 0; j < i; j++) {
				if (src[i] > src[j]) {
					stmp = src[i];
					src[i] = src[j];
					src[j] = stmp;
				}
			}

		}
		for (int t = 0; t < src.length; t++) {
			System.out.print(src[t] + "--");
		}
	}

	/**
	 * 快速排序
	 * @param number
	 * @return
	 */
	public static int[] QuickSort(int[] number) {
		QuickSort(number, 0, number.length - 1);
		return number;
	}

	private static void QuickSort(int[] number, int left, int right) {
		int stmp;
		if (left < right) {
			int s = number[(left + right) / 2];
			int i = left - 1;
			int j = right + 1;
			while (true) {
				while (number[++i] > s);
				while (number[--j] < s);
				if (i >= j)
					break;
				stmp = number[i];
				number[i] = number[j];
				number[j] = stmp;
			}
			QuickSort(number, left, i - 1);
			QuickSort(number, j + 1, right);
		}
	}
	
	/**
	 * 根据Map 的值进行排序
	 * @param h
	 * @return
	 */
	public static Map.Entry[] getSortedHashtableByValue(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());
				Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());
				return key1.compareTo(key2);
			}
		});
		return entries;
	}  
	
	public static void main(String[] args){
		/*
		java.util.ArrayList dcs=new java.util.ArrayList();
		int[] src=new int[]{23,12,11,3,10,20,12};
		Map sortMap = new HashMap(); 
		sortMap.put("Cache_1",src[0]);
		sortMap.put("Cache_2",src[1]);
		sortMap.put("Cache_3",src[2]);
		sortMap.put("Cache_4",src[3]);
		sortMap.put("Cache_5",src[4]);
		sortMap.put("Cache_6",src[5]);
		sortMap.put("Cache_7",src[6]);
		
		int rank=5;
		
		Map.Entry[] entrys=getSortedHashtableByValue(sortMap);
		
		if (sortMap!=null && !sortMap.isEmpty()){
			int totlen=sortMap.keySet().size();
			int len=(totlen>rank)?rank:totlen;
			
			for (int i=0;i<len;i++){
				totlen--;
				dcs.add(entrys[totlen]);
				
			}
		}
		
		for (int i=0;i<dcs.size();i++){
			Map.Entry entry=(Map.Entry)dcs.get(i);
			System.out.println("key:"+entry.getKey()+";value="+entry.getValue());
		}*/
		
	        /**
		int[] src=new int[]{23,12,11,3,10,20,12};
		for (int t=0; t<src.length;t++)
        {
            System.out.print(src[t]+"--");
        }
		String[] types=new String[]{"Cache_1","Cache_2","Cache_3","Cache_4","Cache_5","Cache_6","Cache_7"};
		Map sortMap=new HashMap();
		sortMap.put("Cache_1",src[0]);
		sortMap.put("Cache_2",src[1]);
		sortMap.put("Cache_3",src[2]);
		sortMap.put("Cache_4",src[3]);
		sortMap.put("Cache_5",src[4]);
		sortMap.put("Cache_6",src[5]);
		sortMap.put("Cache_7",src[6]);
		
		List infoIds = new ArrayList( sortMap.entrySet()); 

		
		Collections.sort(infoIds);
		
		
		int rank=5;
		
		if (src!=null && src.length>0){
			SortUtil.InsertionSort(src);
			Map dataContainers=new LinkHashMap();
			int len=(src.length>rank)?rank:src.length;
			
			
			for (int i=0;i<=len;i++){
				Iterator it=sortMap.keySet().iterator();
				while(it.hasNext()){
					String item=it.next().toString();
					int value=Integer.parseInt(sortMap.get(item).toString());
					if (value==src[i]){
						dataContainers.put(item,value);
						sortMap.remove(item);
						break;
					}
				}
			}
			Iterator it=dataContainers.keySet().iterator();
			while(it.hasNext()){
				String type=it.next().toString();
				System.out.println("key:"+type+";value="+dataContainers.get(type));
			}
		}*/
	}
}
