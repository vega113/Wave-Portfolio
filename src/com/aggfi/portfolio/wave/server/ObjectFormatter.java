package com.aggfi.portfolio.wave.server;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class ObjectFormatter {
	public static String toString(Object obj) {
		StringBuilder buff = new StringBuilder();
		if (obj != null) {
			try {
				String name = obj.getClass().getSimpleName();
				buff.append("<").append(name).append(">");
				if (!formatBasicType(obj, buff)) {
					BeanInfo info = Introspector.getBeanInfo(obj.getClass(),
							Object.class);
					PropertyDescriptor[] propertys = info
							.getPropertyDescriptors();
					int len = (propertys != null) ? propertys.length : 0;
					for (int i = 0; i < len; i++) {
						Method readMethod = propertys[i].getReadMethod();
						if (readMethod == null)
							continue;
						Class type = readMethod.getReturnType();
						Object res = readMethod.invoke(obj, new Object[0]);
						String nextLine="";
						if (res != null) {
							buff.append("\n<").append(readMethod.getName())
									.append(">");
							if (!formatBasicType(res, buff)) {

								if (type.isPrimitive()|| type.getName().startsWith("java.lang")) {
									buff.append(res);
								} else {
									buff.append(ObjectFormatter.toString(res));
									nextLine="\n";
								}
							}
							buff.append(nextLine);
							buff.append("</").append(readMethod.getName())
									.append(">");
						}
					}
				}
				buff.append("\n</").append(name).append(">");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buff.toString();
	}

	protected static boolean formatBasicType(Object obj, StringBuilder buff) {
		boolean isBasicType = false;
		if (obj != null) {
			Class type = obj.getClass();
			if (type.isArray()) {
				int size = Array.getLength(obj);
				for (int j = 0; j < size; j++) {
					Object element = Array.get(obj, j);
					if (element != null) {
						buff.append("\n"+ObjectFormatter.toString(element));
					}
				}
				isBasicType = true;
			} else if (java.util.Map.class.isAssignableFrom(type)) {
				isBasicType = true;
				ObjectFormatter.formatBasicType(((java.util.Map) obj).values()
						.toArray(), buff);
			} else if (java.util.Collection.class.isAssignableFrom(type)) {
				isBasicType = true;
				ObjectFormatter.formatBasicType(((java.util.Collection) obj)
						.toArray(), buff);
			} else if (type.isPrimitive()|| type.getName().startsWith("java.lang")) {
				isBasicType = true;
				buff.append(obj);
			}
		}
		return isBasicType;
	}
}
