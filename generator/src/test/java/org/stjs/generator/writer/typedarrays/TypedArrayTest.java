package org.stjs.generator.writer.typedarrays;

import static org.junit.Assert.*;

import org.junit.Test;
import org.stjs.generator.utils.AbstractStjsTest;

public class TypedArrayTest extends AbstractStjsTest {

	@Test
	public void testEnhancedForLoopArray() throws Exception {
		int expected = EnhancedForLoopIntArray.method();
		assertEquals(expected, executeAndReturnNumber(EnhancedForLoopIntArray.class), 0.01);
	}

	@Test
	public void testFloatArray() throws Exception {
		assertCodeContains(Float32ArrayInit.class, "new Float32Array(1)");
	}

	@Test
	public void testMF32AInit() throws Exception {
		assertCodeContains(MultiF32AInit.class,
				"Array.apply(null, Array(1))" //
						+ ".map(function(){return Array.apply(null, Array(2))" //
						+ ".map(function(){return new Float32Array(3);});});");
	}

	@Test
	public void testFloatArrayInit0() throws Exception {
		assertCodeContains(Float32ArrayInit0.class, "new Float32Array()");
	}

	@Test
	public void testFloatArrayInit00() throws Exception {
		assertCodeContains(Float32ArrayInit0Empty.class, "let arr = new Float32Array()");
	}

	@Test
	public void testMultiF32AInit0Empty() throws Exception {
		assertCodeContains(MultiF32AInit0Empty.class, "let arr = [];");
	}

	@Test
	public void testMultiF32AInit0Empty2() throws Exception {
		assertCodeContains(MultiF32AInit0Empty2.class, "let arr2 = [[]];");
	}

	@Test
	public void testMultiF32AInit0Empty3() throws Exception {
		assertCodeContains(MultiF32AInit0Empty3.class, "let arr3 = [[new Float32Array()]];");
	}

	@Test
	public void testFloatArrayInit1() throws Exception {
		assertCodeContains(Float32ArrayInit1.class, "new Float32Array([1.2, 2.0, 3, 0.4, this.a()])");
	}

	@Test
	public void testMultiFloatArrayInit1() throws Exception {
		/* @formatter:off */
		assertCodeContains(MultiF32AInit1.class,
				"["
				+ "["
			  		+ "new Float32Array([1.2, 2.0, 3, 0.4, this.a()]),"
			  		+ "new Float32Array(),"
			  		+ "new Float32Array(1),"
			  		+ "this.myarray()"
			  	+ "],"
			  	+ "[]"
		  	+ "]");
		/* @formatter:on */
	}

	@Test
	public void testFloatArrayInit2() throws Exception {
		assertCodeContains(Float32ArrayInit2.class, "new Float32Array([1.2, 2.0, 3, 0.4, this.a()])");
	}

	@Test
	public void testTypes() throws Exception {
		String generated = generate(Types.class);
		assertCodeContains(generated, "let bool = new Int8Array()");
		assertCodeContains(generated, "let b = new Int8Array()");
		assertCodeContains(generated, "let s = new Int16Array()");
		assertCodeContains(generated, "let c = new Uint16Array()");
		assertCodeContains(generated, "let i = new Int32Array()");
		assertCodeContains(generated, "let f = new Float32Array()");
		assertCodeContains(generated, "let d = new Float64Array()");
	}

	@Test
    public void testPrimitiveArrayFields() throws Exception {
        String generated = generate(PrimitiveArrayFields.class);
        assertCodeContains(generated, "    bool: Int8Array = null;\n"
				+ "    b: Int8Array = null;\n"
				+ "    s: Int16Array = null;\n"
				+ "    c: Uint16Array = null;\n"
				+ "    i: Int32Array = null;\n"
				+ "    f: Float32Array = null;\n"
				+ "    d: Float64Array = null;\n"
				+ "    bool2d: Array = null;\n" // TODO :: original type is boolean[][] might need to specify this in some way
				+ "    _bool: boolean = false;\n"
				+ "    _b: any = 0;\n"
				+ "    _s: number = 0;\n"
				+ "    _c: any = '\\x00';\n"
				+ "    _i: number = 0;\n"
				+ "    _f: number = 0.0;\n"
				+ "    _d: number = 0.0;\n");
    }

	@Test
	public void testMultiInit() throws Exception {
		String expected = "Array.apply(null, Array(3)).map(function(){return Array(2);});"; //
		assertCodeContains(MultiInit.class, expected);
	}

	@Test
	public void testMultiInit2() throws Exception {
		/* @formatter:off */
		String expected =
		"let ac = [Array.apply(null, Array(2)).map(function() {"+//
        "    return new Int32Array(3);"+//
        "}), Array.apply(null, Array(4)).map(function() {"+//
        "    return new Int32Array(5);"+//
        "}), Array.apply(null, Array(6)).map(function() {"+//
        "    return new Int32Array(7);"+//
        "})]";
		/* @formatter:on */
		assertCodeContains(MultiInit2.class, expected);
	}

	@Test
	public void testArrayMath() throws Exception {
		assertCodeContains(ArrayMath.class, "c[i]++;");
		int expected = ArrayMath.method();
		assertEquals((double) expected, executeAndReturnNumber(ArrayMath.class), 0);
	}

	@Test
	public void testBooleanArray() throws Exception {
		int expected = BooleanArray.method();
		assertEquals((double) expected, executeAndReturnNumber(BooleanArray.class), 0);
	}

	@Test
	public void testInstanceOf() {
		String generated = generate(ArrayInstanceOf.class);
		assertCodeContains(generated, "o instanceof Int8Array");
		assertEquals(44., executeAndReturnNumber(ArrayInstanceOf.class), 0.01);
	}
}
