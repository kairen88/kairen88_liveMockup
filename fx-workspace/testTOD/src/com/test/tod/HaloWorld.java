package com.test.tod;

public class HaloWorld {

	public static void main(String[] args)
	{
		System.out.println("halo world");
		
		int a = 5;
		
		int x = add10(a) + 64 * 93;
		
		if(add10(5) -10 > 0)
			System.out.println("no");
		
		String st = "aaaa" + add10(5) + "ddd";
		
		System.out.println("halo world");
	}
	
	private static int add10(int value)
	{
		int a = value + 10;
		return a;
	}
}
