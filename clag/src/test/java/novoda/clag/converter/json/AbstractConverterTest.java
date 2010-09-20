package novoda.clag.converter.json;

import novoda.clag.converter.Converter;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;
import novoda.clag.model.MetaEntity.OnConflictPolicy;
import novoda.clag.provider.Provider;
import novoda.clag.provider.gae.GaeProvider;
import novoda.clag.servlet.context.Context;
import novoda.clag.servlet.context.GaeRestContext;

public abstract class AbstractConverterTest {

	protected Context context = new GaeRestContext();
	
	protected Converter converter = initConverter();

	protected Provider getSampleProvider() {
		Provider provider = new GaeProvider();
		provider.add(getSampleEntity());
		return provider;
	}
	
	protected MetaEntity getSampleEntity() {
		MetaEntity entity = new MetaEntity("novoda.clag.Example", "Example");
		entity.add(new MetaProperty.Builder("title").clazz(String.class).build());
		entity.add(new MetaProperty.Builder("description").clazz(String.class).build());
		entity.add(new MetaProperty.Builder("cost").clazz(Integer.class).build());
		entity.add(new MetaProperty.Builder("id").clazz(Integer.class).key(true).build());
		return entity;
	}

	protected MetaEntity getComplexEntity() {
		MetaEntity entity = new MetaEntity("novoda.clag.Example", "Example");
		entity.add(new MetaProperty.Builder("title").clazz(String.class).build());
		entity.add(new MetaProperty.Builder("id").clazz(Integer.class)
				.unique(true).onConflictPolicy(OnConflictPolicy.REPLACE).key(
						true).build());
		return entity;
	}
	
	protected abstract Converter initConverter();
}
