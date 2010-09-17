package novoda.clag.servlet.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import novoda.clag.model.MetaEntity;
import novoda.clag.util.RequestMapBuilder;

import org.junit.Ignore;
import org.junit.Test;

public class EntityMatchingRestContextTest {

	@Test
	public void shouldGetEntityPropertiesReturnMapWithTextValue() {
		Map<String, Object> properties = entityMatchingText("key",
				MetaEntity.Type.STRING.getValue(), "should be ok",
				MetaEntity.Type.STRING.getValue());

		assertProperty("id", "key", properties);
		assertProperty("second", "should be ok", properties);
	}

	/**
	 * TODO when the real insert is implemented in the provider I can check if
	 * the context is the best place to convert the type
	 */

	@Ignore
	@Test
	public void shouldGetEntityPropertiesReturnMapWithLong() {
		Map<String, Object> properties = entityMatchingText("key",
				MetaEntity.Type.STRING.getValue(), "12", MetaEntity.Type.STRING
						.getValue());

		assertProperty("id", "key", properties);
		assertProperty("second", "should be ok", properties);
	}

	@Ignore
	@Test
	public void shouldGetEntityPropertiesReturnMapWithDate() {
		Map<String, Object> properties = entityMatchingText("key",
				MetaEntity.Type.STRING.getValue(), "122938420",
				MetaEntity.Type.STRING.getValue());

		assertProperty("id", "key", properties);
		assertProperty("second", "should be ok", properties);
	}

	private void assertProperty(String name, Object expected,
			Map<String, Object> properties) {
		assertTrue(properties.containsKey(name));
		assertEquals(expected, properties.get(name));
	}

	private Map<String, Object> entityMatchingText(String fristProperty,
			String firstType, String secondProperty, String secondType) {
		GaeRestContext rr = new GaeRestContext(new RequestMapBuilder().add(
				"id", fristProperty).add("second", secondProperty).build());

		rr.setUri("/data/Story");

		MetaEntity me = new MetaEntity("Example", "Example");
		me.add("id", firstType);
		me.add("second", secondType);

		Map<String, Object> properties = rr.getEntity(me);
		assertNotNull(properties);
		return properties;
	}

}
