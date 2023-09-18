package com.planittesting.jupitertoys.tests.support.dataproviders;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import com.planittesting.jupitertoys.model.data.ContactData;

public class CsvContactDataAggregator implements ArgumentsAggregator {

	@Override
	public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
			throws ArgumentsAggregationException {
		
		ContactData contactData = new ContactData(
			accessor.getString(0),
			accessor.getString(1),
			accessor.getString(2),
			accessor.getString(3),
			accessor.getString(4));
		
		return contactData;
	}

}
