package com.dac.welang.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DialogueSpliter {
	private List<String> contentA = new ArrayList<>();
	private List<String> contentB = new ArrayList<>();
	public DialogueSpliter(String fileName) {
		try (Scanner scanner = new Scanner(new File(fileName))) {
			String s;
			boolean putA=true;
			while (scanner.hasNext()){
				 s = scanner.nextLine();
				 s = s.trim().replaceAll(" +", " ");
				String sp[] = s.split(":", 2);
				if(!s.isEmpty()){
					if(sp.length==2){
						if(putA){
							contentA.add(sp[1]);
						}else{
							contentB.add(sp[1]);
						}
						putA=!putA;
					}else{
						int currentIndex = contentA.size()-1;
						putA=!putA;
						if(putA){
							contentA.set(currentIndex, contentA.get(currentIndex) + sp[0]);
						}else{
							contentA.set(currentIndex, contentA.get(currentIndex) + sp[0]);
						}
						putA=!putA;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<String> getContentA() {
		return contentA;
	}
	public List<String> getContentB() {
		return contentB;
	}
	
	
}
