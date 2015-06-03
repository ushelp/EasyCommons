package cn.easyproject.easycomms.objetctutils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectExtractTest {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		Set<B2> bs = new HashSet<B2>();
		Set<C2> cs = new HashSet<C2>();
		cs.add(new C2("CCC#1"));
		cs.add(new C2("CCC#2"));
		cs.add(new C2("CCC#3"));

		bs.add(new B2("BBB#1", cs));
		bs.add(new B2("BBB#2", cs));
		bs.add(new B2("BBB#3", cs));

		A2 a = new A2("AAAA", new B2("BBBB", cs), bs);

		System.out.println(a);
		// Map m= EasyObjectUtils.extract(a,"a", "b");
		// Map m= EasyObjectUtils.extract(a,"a", "b.b");
		// Map m= EasyObjectUtils.extract(a,"a", "{bs}.b");
		// Map m= EasyObjectUtils.extract(a,"a", "{bs}.{cs}.c");
		// Map m= EasyObjectUtils.extract(a,"a", "b.{cs}.c");
		// Map m= EasyObjectUtils.extract(a,"a", "bs");
//		Map m = EasyObjectExtract.extract(a, "a", "{bs}.b");
		// 字段表达式和输出名的映射
		Map<String, String> fieldExpressionAndOutNameMap=new HashMap<String, String>();
		fieldExpressionAndOutNameMap.put("a", "test");
		fieldExpressionAndOutNameMap.put("{bs}.b", "bsb");
		Map m = EasyObjectExtract.extract(a,fieldExpressionAndOutNameMap, "a", "{bs}.b");

		System.out.println(m);
		// Gson gson = new Gson();
		// System.out.println(gson.toJson(m));
	}

}

class A2 {
	public String a;
	public B2 b;
	public Set<B2> bs = new HashSet<B2>();

	public A2(String a, B2 b, Set<B2> bs) {
		super();
		this.a = a;
		this.b = b;
		this.bs = bs;
	}

	@Override
	public String toString() {
		return "A2 [a=" + a + ", b=" + b + ", bs=" + bs + "]";
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public B2 getB() {
		return b;
	}

	public void setB(B2 b) {
		this.b = b;
	}

	public Set<B2> getBs() {
		return bs;
	}

	public void setBs(Set<B2> bs) {
		this.bs = bs;
	}

}

class B2 {
	public String b;
	public Set<C2> cs = new HashSet<C2>();

	public B2(String b, Set<C2> cs) {
		super();
		this.b = b;
		this.cs = cs;
	}

	@Override
	public String toString() {
		return "B2 [b=" + b + ", cs=" + cs + "]";
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Set<C2> getCs() {
		return cs;
	}

	public void setCs(Set<C2> cs) {
		this.cs = cs;
	}

}

class C2 {
	public String c;

	public C2(String c) {
		super();
		this.c = c;
	}

	@Override
	public String toString() {
		return "C2 [c=" + c + "]";
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
