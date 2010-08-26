package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import novoda.clag.introspector.AbstractIntrospector;
import novoda.clag.introspector.Introspector;

import org.junit.Test;

public class AbstractIntrospectorTest {
	
	@Test
	public void stringClass() {
		assertEquals(Introspector.Type.STRING, AbstractIntrospector.getType(String.class));
	}

	@Test
	public void integerClass() {
		assertEquals(Introspector.Type.INTEGER, AbstractIntrospector.getType(Integer.class));
	}

	@Test
	public void longClass() {
		assertEquals(Introspector.Type.INTEGER, AbstractIntrospector.getType(Long.class));
	}
	
	@Test
	public void dateClass() {
		assertEquals(Introspector.Type.INTEGER, AbstractIntrospector.getType(Date.class));	
	}

	@Test
	public void listOfString() {
		assertEquals(Introspector.Type.STRING, AbstractIntrospector.getType(List.class));
	}

}