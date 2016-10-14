package org.openforis.collect.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.openforis.collect.Proxy;

public class Proxies {

	public static <P extends Proxy, T> P fromObject(T obj, Class<P> proxyType, Object... extraParameters) {
		if (obj == null) {
			return null;
		}
		try {
			if (extraParameters == null || extraParameters.length == 0) {
				Constructor<P> constructor = proxyType.getDeclaredConstructor(obj.getClass());
				P proxy = constructor.newInstance(obj);
				return proxy;
			} else {
				Class<?>[] extraParameterTypes = determineTypes(extraParameters);
				Constructor<P> constructor = proxyType.getDeclaredConstructor(ArrayUtils.addAll(new Class<?>[]{obj.getClass()}, extraParameterTypes));
				Object[] args = ArrayUtils.addAll(new Object[]{obj}, extraParameters);
				P proxy = constructor.newInstance(args);
				return proxy;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error creating proxy", e);
		}
	}
	
	public static <P extends Proxy, T> List<P> fromList(List<T> objects, Class<P> proxyType, Object... extraParameters) {
		if (objects == null) {
			return Collections.emptyList();
		}
		List<P> result = new ArrayList<P>(objects.size());
		for (T obj : objects) {
			result.add(fromObject(obj, proxyType, extraParameters));
		}
		return result;
	}
	
	private static Class<?>[] determineTypes(Object[] extraParameters) {
		Class<?>[] types = new Class[extraParameters.length];
		for (int i = 0; i < extraParameters.length; i++) {
			Object object = extraParameters[i];
			types[i] = object.getClass();
		}
		return types;
	}

}
