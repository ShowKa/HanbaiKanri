package com.showka.common;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import junit.framework.TestCase;
import mockit.integration.junit4.JMockit;

/**
 * Test Case Class for simple class.
 *
 * <pre>
 * this class doesn't launch SpringBoot.
 * 
 * <pre>
 */
@RunWith(JMockit.class)
public abstract class SimpleTestCase extends TestCase {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
}
