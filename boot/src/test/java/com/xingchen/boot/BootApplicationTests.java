package com.xingchen.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {

	@Test
	public void contextLoads() {
		Calculator calculator = (x,y) -> x+y;
		engine(calculator);

		//6
		engine((x,y)-> x+y);
		//-2
		engine((x,y)-> x-y);
		//8
		engine((x,y)-> x*y);
		//0
		engine((x,y)-> x/y);
		//2
		engine((x,y)-> x%y);
	}

	private static void engine(Calculator calculator){
		int x = 2,y=4;
		int result = calculator.calculate(x,y);
		System.out.println(result);
	}

	private static Calculator create(){
		return (x,y)-> x+y;
	}


	@FunctionalInterface
	interface Calculator{
		int calculate(int x,int y);
	}


	/** 
	 * 功能描述: 赋值上下文
	 * lambda表达式可以显示在赋值运算符的右侧。
	 * 
	 * @param: []
	 * @return: void
	 * @author: xingchen
	 * @date: 2019-08-05 22:48
	 **/
	@Test
	public void test2(){
		Calculator calculator = (x,y) -> x+y;
		System.out.println(calculator.calculate(3,5));
	}

	
	/** 
	 * 功能描述: 
	 * 
	 * @param: []
	 * @return: void
	 * @author: xingchen
	 * @date: 2019-08-05 22:51
	 **/
	@Test
	public void test3(){
		System.out.println(create().calculate(2,8));
	}




//	@FunctionalInterface
	interface Processor {
		int getStringLength(String str);
	}

	@Test
	public void test4(){
		Processor processor = (String str)->str.length();
		String name = "Java Lambda";
		int strLength = processor.getStringLength(name);
		System.out.println(strLength);
	}


	@FunctionalInterface
	public interface  Processor2 {
		<T> void  process(T[] list);
	}

	@FunctionalInterface
	interface BiFunction<T,U,R>{
		//public R apply(T t, U u);

		R apply(T t, U u);
	}

	@Test
	public void test5(){

	}
}
