package com.planittesting.jupitertoys.bdd.steps.support;

public class BaseSteps {
    protected ObjectContainer container;
	protected static final double FLOATING_POINT_ERROR = 0.001;

	public BaseSteps(ObjectContainer container) {
		this.container = container;
	}
}
