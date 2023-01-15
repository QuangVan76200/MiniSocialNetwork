package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestDatabaseJpaApplication {

	public static int sizeOfParams(List<String> listStr, String a, String b) {
		int position = 0;
		int lessThanPosition = Integer.MAX_VALUE;
		for (int i = 0; i < listStr.size() - 1; i++) {
			for (int j = 0; j < listStr.size(); j++) {
				if (i != j && listStr.get(i).equals(a) && listStr.get(j).equals(b)
						|| listStr.get(i).equals(b) && listStr.get(j).equals(a)) {
					position = Math.abs(j - i);
					if (position <= lessThanPosition) {
						lessThanPosition = position;
					}
				}
			}
		}
		return lessThanPosition;
	}

//	Làm thế nào để tìm tất cả các cặp số trong một mảng số nguyên có tổng bằng với một số cho trước 
	public static List<Integer> printPairs(int[] array, int sum) {
		List<Integer> listDouble = new ArrayList<>();

		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] + array[j] == sum) {
					listDouble.add(array[i]);
					listDouble.add(array[j]);
				}
			}
		}
		return listDouble;
	}
	
//	Cách nào để tìm tất cả các số bị trùng lắp (duplicate) trong một mảng nếu nó chứa nhiều số bị lặp lại?

	public static char firstNonRepeatingChar(String word) {
		Map<Character, Integer> hashMap = new LinkedHashMap<>();
		char C = 'a';
		for (char c : word.toCharArray()) {
			if (!hashMap.containsKey(c)) {
				hashMap.put(c, 1);
			} else {
				hashMap.put(c, hashMap.get(c) + 1);
			}
		}
		for(Entry<Character, Integer> charStr : hashMap.entrySet())
			if(charStr.getValue()== 1) {
				C = charStr.getKey();	
			}
		return C;
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(TestDatabaseJpaApplication.class, args);
		List<String> listTeamp = new ArrayList<>(
				Arrays.asList("cat", "dog", "bird", "fish", "cat", "duck", "chicken", "dog"));
		int[] numbs = new int[] { 2, 4, 3, 5, 6, -2, 4, 7, 8, 9 };

//		System.out.println(TestDatabaseJpaApplication.sizeOfParams(listTeamp, "dog", "duck"));
//		System.out.println(TestDatabaseJpaApplication.printPairs(numbs, 7));
		System.out.println(TestDatabaseJpaApplication.firstNonRepeatingChar("AbcAbcDV"));
	}

}
