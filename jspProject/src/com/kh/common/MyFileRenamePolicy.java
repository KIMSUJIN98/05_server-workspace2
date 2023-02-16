package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	// 원본 파일 전달 받아서 파일명 수정작업 후 수정된 파일을 반환시켜주는 메소드
	@Override
	public File rename(File originFile) {																	// File io로 input!
		
		// 원본 파일명 ("aaa.jpg")
		String originName = originFile.getName();
		
		// => 수정 파일명 ("2023021612105554321.png")
		// 				파일업로드한시간(년월일시분초) + 5자리랜덤값(10000~99999) + 원본파일 확장자
		
		// 1. 파일 업로드 시간 (년월일시분초 형태) => (String currentTime)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "20230216121055" 파일업로드한시간(년월일시분초)
		
		// 2. 5자리 랜덤값 (int ranNum)
		int ranNum = (int)(Math.random() * 90000 + 10000); // 54321 랜덤값
		
		// 3. 원본파일 확장자 (String ext)																		// 확장자는 .jpg .png와 같이 표현되므로 .뒤의 문자열을 가져오면 됨
		String ext = originName.substring(originName.lastIndexOf(".")); // ".png" 원본파일 확장자
		
		String changeName = currentTime + ranNum + ext;
		
		return new File(originFile.getParent(), changeName); 												// String이 아닌 File형을 리턴해야하므로
		// 원본의 디렉토리를 알아낸 후 변경된 이름으로 저장하는 메소드
	}
}
