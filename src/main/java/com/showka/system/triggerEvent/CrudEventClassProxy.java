package com.showka.system.triggerEvent;

import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import com.showka.domain.DomainRoot;
import com.showka.system.exception.SystemException;
import com.showka.system.triggerEvent.CrudEvent.EventType;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;

public class CrudEventClassProxy {

	/**
	 * 作成済みのClass
	 */
	private static Map<Class<?>, Class<?>> CLASS_MAP = new HashMap<>();

	/**
	 * CrueEvent Class を取得
	 * 
	 * @param domainClass
	 *            DomainBase.class
	 * @return CrudEvent<T>.class
	 */
	@SuppressWarnings("unchecked")
	public static <T extends DomainRoot> Class<CrudEvent<T>> get(Class<T> domainClass) {
		// get if already made
		Class<CrudEvent<T>> _class = (Class<CrudEvent<T>>) CLASS_MAP.get(domainClass);
		if (_class != null) {
			return _class;
		}
		try {
			// get base class
			ClassPool cp = ClassPool.getDefault();
			CtClass origCtClass = cp.get(CrudEvent.class.getName());
			// event class name
			String domainName = domainClass.getSimpleName();
			String eventClassName = origCtClass.getName() + domainName;
			// get or make
			try {
				CtClass _eventClass = cp.get(eventClassName);
				Class<CrudEvent<T>> _event = _eventClass.toClass();
				CLASS_MAP.put(domainClass, _event);
				return _event;
			} catch (Exception e) {
				System.out.println(e.getClass().getName() + " : " + e.getMessage());
				System.out.println("作成 : " + eventClassName);
			}
			CtClass eventCtClass = cp.makeClass(eventClassName, origCtClass);
			// stopPruning
			// ClassPool.doPruning というeventが発生した時にこのクラスが削除されるのを禁止する
			// pruning = 剪定
			eventCtClass.stopPruning(true);
			// defrost
			eventCtClass.defrost();
			// set Generic Type like following
			// Lcom/showka/event/CrudEvent<Lcom/showka/domain/u06/Urikake;>;
			String sigOrigClass = origCtClass.getName().replaceAll("\\.", "/");
			String sigDomainClass = domainClass.getName().replaceAll("\\.", "/");
			String sig = "L" + sigOrigClass + "<L" + sigDomainClass + ";>;";
			eventCtClass.setGenericSignature(sig);
			// constructor 生成
			StringBuilder cBody = new StringBuilder();
			// declare
			cBody.append("public ");
			cBody.append(eventCtClass.getSimpleName());
			// arguments
			cBody.append("(java.lang.Object source,");
			cBody.append(EventType.class.getName() + " type,");
			cBody.append(domainClass.getName() + " domain)");
			// body
			cBody.append("{ super(source, type, domain); }");
			// add
			CtConstructor c = CtNewConstructor.make(cBody.toString(), eventCtClass);
			eventCtClass.addConstructor(c);
			// to class
			ClassLoader loader = CrudEvent.class.getClassLoader();
			ProtectionDomain protectionDomain = CrudEvent.class.getProtectionDomain();
			Class<CrudEvent<T>> eventClass = eventCtClass.toClass(loader, protectionDomain);
			// done
			CLASS_MAP.put(domainClass, eventClass);
			return eventClass;
		} catch (Exception e) {
			throw new SystemException("EventClass自動生成失敗", e);
		}
	}
}