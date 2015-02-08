package com.aol.micro.server.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.rest.jersey.JerseyRestApplication;
import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.google.common.collect.Lists;
public class RestApplicationTest {

	

		@Test
		public void testDefaultConstructor() {
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication.getResourcesMap().put(Thread.currentThread().getName(), Lists.newArrayList(new ServletStatusResource()));
			JerseyRestApplication app = new JerseyRestApplication();
			assertTrue(app.isRegistered(ServletStatusResource.class));
			
				
			assertThat(	app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jersey.JacksonFeature".intern()));
			
		}

		@Test
		public void testDefaultConstructorCleared() {
			JerseyRestApplication.getResourcesMap().clear();
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication app = new JerseyRestApplication();
			assertThat(app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jersey.JacksonFeature"));
			assertFalse(app.isRegistered(ServletStatusResource.class));
			
		}

		@Test
		public void testConstructor() {
			JerseyRestApplication.getResourcesMap().clear();
			JerseyRestApplication app = new JerseyRestApplication(Lists.newArrayList(new ServletStatusResource()));
			assertThat(app.getApplication().getClasses().size(),is(4));
			assertTrue(app.isRegistered(ServletStatusResource.class));
		}
		
	

	

	

}