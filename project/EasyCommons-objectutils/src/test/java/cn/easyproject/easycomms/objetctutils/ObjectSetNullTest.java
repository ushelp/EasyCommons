package cn.easyproject.easycomms.objetctutils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ObjectSetNullTest {
	public static void main(String[] args) {
		
	
		Set<D> ds=new HashSet<D>();
		ds.add(new D("DDD1"));
		ds.add(new D("DDD2"));
		ds.add(new D("DDD3"));
		
		A a=new A("AAAA","BBBB",new B("CCCCC","CCCC2",new C("DDDD"),ds));
		
	
		
		a.bs.add(new B("BS1111","BS1111#", new C("C111"),ds));
		a.bs.add(new B("BS2222","BS1111#", new C("C2222"),ds));
		a.bs.add(new B("BS3333","BS1111#", new C("C3333"),ds));
		a.bs.add(new B("BS4444","BS1111#", new C("C4444"),ds));

		
		
		
		// 设置a对象中以下属性为null：s、b.s2、b.c.s3
//		EasyObjectUtils.setNull(a, "s","b.s2","b.c.s3");
		// 设置a对象中以下属性为null：s、b.s2、b.c
//		EasyObjectUtils.setNull(a, "s","b.s2","b.c");
		// 设置a对象中以下属性为null：s、b.s2、b.c、bs集合中的s1属性、bs集合中的c.s3属性
//		EasyObjectUtils.setNull(a, "s","b.s2","b.c","{bs}.s1","{bs}.c.s3");
		//  设置a对象中以下属性为null：s、b.s2、b.c、b属性中ds集合中的s4属性
//		EasyObjectUtils.setNull(a, "s","b.s2","b.c","b.{ds}.s4");
	//  设置a对象中以下属性为null：s、b.s2、b.c、bs集合中ds集合中的s4属性
		EasyObjectSetNull.setNull(a, "s","b.s2","b.c","{bs}.{ds}.s4");
		System.out.println(a);
		
		/*
		 * 
		 * "s","b.s2","b.c.s3"
		 */
		
		E e=new E("E1", "E2", new D[]{new D("D4"),new D("D5"),new D("D6")});
		
		System.out.println(e);
//		EasyObjectUtils.setNull(e, "s","ds");
		EasyObjectSetNull.setNull(e, "s","[ds].s4");
//		EasyObjectUtils.setNull(e, "s","ds");
//		EasyObjectUtils.setNull(e, "s","[ds]");

		
		System.out.println(e);
	}
}


 class E{
	
	public String s;
	public String s2;
	
	D[] ds;

	public E(String s, String s2, D[] ds) {
		super();
		this.s = s;
		this.s2 = s2;
		this.ds = ds;
	}

	@Override
	public String toString() {
		return "E [s=" + s + ", s2=" + s2 + ", ds=" + Arrays.toString(ds) + "]";
	}
	
	
}
class A{
	public String s;
	public String s2;
	public Object b;
	public Set<B> bs=new HashSet<B>();
	public A(String s, String s2, Object b) {
		super();
		this.s = s;
		this.s2 = s2;
		this.b = b;
	}
	@Override
	public String toString() {
		return "A [s=" + s + ", s2=" + s2 + ", b=" + b + ", bs=" + bs + "]";
	}
	
	
	
}

class B{
	public String s1;
	public String s2;
	public C c;
	public Set<D> ds=new HashSet<D>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public B(String s1,String s2,C c,Set ds) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.c=c;
		this.ds=ds;
	}

	@Override
	public String toString() {
		return "B [s1=" + s1 + ", s2=" + s2 + ", c=" + c + ", ds=" + ds + "]";
	}




	

}


 class C{
	public String s3;

	public C(String s3) {
		super();
		this.s3 = s3;
	}

	@Override
	public String toString() {
		return "C [s3=" + s3 + "]";
	}
}
 
 
 class D{
	 public String s4;

	public D(String s4) {
		super();
		this.s4 = s4;
	}

	@Override
	public String toString() {
		return "D [s4=" + s4 + "]";
	}
	 
	
 }
 
 
 
 