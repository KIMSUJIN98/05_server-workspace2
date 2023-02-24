package com.kh.model.vo;

public class Method {
	
	public void method1() {
	
		String str = "Apple, Kiwi, Strawberry, Banana";
		String[] arr = str.split(",");
		System.out.println(arr[1]);
	}

	/*
	2번 문제
	 int result = 10 / 0;을 실행하면 0으로 나누려 했기 때문에 ArithmeticException이 발생하게 된다. 부모 RuntimeException의 자식 중 하나가 ArithmeticException 이므로 다형성이 적용되어 2번 코드로의 기술이 가능해진다.
	
	3번 문제
	0으로 나누기 연산을 하면 발생되는 예외는 ArithmeticException이다. ArithmeticException은 자식타입으로 부모 RuntimeException 중 하나이다.
	
	4번 문제
	배열에서 부적절한 인덱스에 접근하려고 할 때 발생되는 예외는 ArrayIndexOutOfBoundsException 이다. ArrayIndexOutOfBoundsException 은 자식타입으로 부모 RuntimeException 중 하나이다.
	
	5번 문제
	배열의 크기로 음수를 제시했을 때 발생되는 예외는 NegativeArraySizeException 이다. NegativeArraySizeException 은 자식타입으로 부모 RuntimeException 중 하나이다.
	
	6번 문제
	1) put()은 추가하는 메소드이다.
	2) get()은 객체를 반환하는 메소드이다.
	3) set()은 수정, 변경하는 메소드이다.
	4) keySet()은 키만 추출하여 Set에 담고, 이터레이터를 통해 접근한다.
	5) entrySet()은 키와 값을 같이 추출하여 Set에 담고, 이터레이터를 통해 접근한다.
	
	7번 문제
	1) add()는 추가하는 메소드이다.
	2) set()은 수정, 변경하는 메소드이다.
	3) size()는 담긴 값의 개수를 반환하는 메소드이다. // 컬렉션의 크기를 반환하는 메소드이다.
	4) remove()는 삭제하는 메소드이다.
	5) get()은 객체를 반환하는 메소드이다.
	
	8번 문제
	1. 배열은 크기를 지정해줘야 하지만, 컬렉션은 크기 지정을 하지 않아도 된다.
	2. 배열은 추가, 변경, 삭제하고자 하면 그 과정이 매우 복잡하지만, 컬렉션은 메소드를 통해 간편하게 수행 가능하다.
	3. 배열은 한가지 타입의 여러 데이터를 담을 수 있지만, 컬렉션은 여러 타입의 여러 데이터들을 담을 수 있다.
	
	*/
}
