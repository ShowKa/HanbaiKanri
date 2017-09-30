package com.showka.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.showka.domain.DomainBase;
import com.showka.kubun.i.Kubun;
import com.showka.system.exception.SystemException;
import com.showka.value.ValueBase;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class EmptyProxy {

	/**
	 * 生成済みのEmpty Object
	 */
	private static HashMap<Class<?>, Object> INSTANCES = new HashMap<Class<?>, Object>();

	/**
	 * DomainのEmptyを取得
	 * 
	 * @param clazz
	 *            クラス
	 * @return Empty
	 */
	public static <T extends DomainBase> T domain(Class<T> clazz) {
		return get(clazz);
	}

	/**
	 * ValueのEmptyを取得
	 * 
	 * @param clazz
	 *            クラス
	 * @return Empty
	 */
	public static <T extends ValueBase> T value(Class<T> clazz) {
		return get(clazz);
	}

	/**
	 * Emptyを取得する。
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Object> T get(Class<T> clazz) {

		// get if already made
		Object _instance = INSTANCES.get(clazz);
		if (_instance != null) {
			return (T) _instance;
		}

		try {

			// get base class
			ClassPool cp = ClassPool.getDefault();
			CtClass origCtClass = cp.get(clazz.getName());

			// make empty class
			String emptyClassName = origCtClass.getName() + "Empty";
			CtClass emptyCtClass = cp.makeClass(emptyClassName, origCtClass);

			// stopPruning
			emptyCtClass.stopPruning(true);

			// override methods of empty class
			CtMethod[] emptyMethods = emptyCtClass.getMethods();
			List<CtMethod> methodsForOverride = new ArrayList<CtMethod>(Arrays.asList(emptyMethods));

			// exclude superclass methods from empty object methods
			CtClass superClass = origCtClass.getSuperclass();
			List<CtMethod> superClassMethods = new ArrayList<CtMethod>();
			if (superClass != null) {
				superClassMethods.addAll(Arrays.asList(superClass.getMethods()));
			}
			methodsForOverride.removeIf(target -> {
				boolean match = superClassMethods.stream().anyMatch(superMethod -> {
					boolean sameName = target.getName().equals(superMethod.getName());
					boolean sameSignature = target.getSignature().equals(superMethod.getSignature());
					return sameName && sameSignature;
				});
				return match;
			});

			// override
			for (CtMethod _m : methodsForOverride) {

				CtMethod method = CtNewMethod.copy(_m, emptyCtClass, null);

				CtClass returnType = method.getReturnType();
				// body will be overridden in the following
				method.setBody("return null;");

				// if return type is Primitive
				if (returnType.equals(CtClass.voidType)) {
					method.setBody("return;");
				} else if (returnType.equals(CtClass.booleanType)) {

					if (method.getName().equals("isEmpty")) {
						method.setBody("return true;");
					} else {
						method.setBody("return false;");
					}

				} else if (returnType.equals(CtClass.byteType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.charType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.doubleType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.floatType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.intType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.longType)) {
					method.setBody("return 0;");
				} else if (returnType.equals(CtClass.shortType)) {
					method.setBody("return 0;");
				}

				// String
				boolean isString = returnType.equals(cp.get(String.class.getName()));
				if (isString) {
					if (method.getName().equals("toString")) {
						method.setBody("return \"Empty Object of " + clazz.getSimpleName() + "\";");
					} else {
						method.setBody("return new String();");
					}
				}

				// Integer
				boolean isInteger = returnType.equals(cp.get(Integer.class.getName()));
				if (isInteger) {
					method.setBody("return Integer.valueOf(0);");
				}

				// Long
				boolean isLong = returnType.equals(cp.get(Long.class.getName()));
				if (isLong) {
					method.setBody("return Long.valueOf(0);");
				}

				// Double
				boolean isDouble = returnType.equals(cp.get(Double.class.getName()));
				if (isDouble) {
					method.setBody("return Double.valueOf(0);");
				}

				// BigDecimal
				boolean isBigDecimal = returnType.equals(cp.get(BigDecimal.class.getName()));
				if (isBigDecimal) {
					method.setBody("return BigDecimal.ZERO;");
				}

				// Domain
				boolean isDomain = returnType.subclassOf(cp.get(DomainBase.class.getName()));
				if (isDomain) {
					String body = "return (" + returnType.getName() + ") " + EmptyProxy.class.getName() + ".domain("
							+ returnType.getName() + ".class);";
					method.setBody(body);
				}

				// Value Object
				boolean isValue = returnType.subclassOf(cp.get(ValueBase.class.getName()));
				if (isValue) {
					String body = "return (" + returnType.getName() + ") " + EmptyProxy.class.getName() + ".value("
							+ returnType.getName() + ".class);";
					method.setBody(body);
				}

				// Kubun
				CtClass[] interfaces = returnType.getInterfaces();
				boolean isKubun = Arrays.asList(interfaces).stream().anyMatch(i -> {
					try {
						return i.equals(cp.get(Kubun.class.getName()));
					} catch (NotFoundException e) {
						throw new SystemException("ClassPool にないかもしれません : " + Kubun.class.getName(), e);
					}
				});
				if (isKubun) {
					method.setBody("return " + returnType.getName() + ".EMPTY;");
				}

				// add method
				emptyCtClass.addMethod(method);
			}

			// add isEmpty() if not exists
			boolean hasIsEmptyMethod = methodsForOverride.stream().anyMatch(m -> {
				return m.getName().equals("isEmpty");
			});
			if (!hasIsEmptyMethod) {
				CtMethod isEmptyMethod = CtNewMethod.make("public boolean isEmpty() { return true; } ", emptyCtClass);
				emptyCtClass.addMethod(isEmptyMethod);
			}

			// add toString
			boolean hasToString = methodsForOverride.stream().anyMatch(m -> {
				return m.getName().equals("toString");
			});
			if (!hasToString) {
				CtMethod isEmptyMethod = CtNewMethod.make("public String toString() { return \"This is Empty Object of "
						+ clazz.getSimpleName() + "\"; } ", emptyCtClass);
				emptyCtClass.addMethod(isEmptyMethod);
			}

			// create no args constructor
			CtConstructor[] originalConstructor = origCtClass.getConstructors();
			CtClass[] params = originalConstructor[0].getParameterTypes();
			String[] args = new String[params.length];
			for (int i = 0; i < params.length; i++) {
				if (params[i].isPrimitive()) {
					if (params[i].getName().equals("boolean")) {
						args[i] = "false";
					} else {
						args[i] = "0";
					}
				} else {
					args[i] = "null";
				}
			}

			StringBuilder cBody = new StringBuilder();
			cBody.append("public ");
			cBody.append(clazz.getSimpleName());
			cBody.append("Empty(){ super(");
			for (int i = 0; i < args.length; i++) {
				if (i >= 1) {
					cBody.append(",");
				}
				cBody.append(args[i]);
			}
			cBody.append("); }");
			CtConstructor c = CtNewConstructor.make(cBody.toString(), emptyCtClass);
			emptyCtClass.addConstructor(c);

			// cretate instance
			Class<?> emptyClass = emptyCtClass.toClass();
			T empty = (T) emptyClass.newInstance();

			// defrost
			emptyCtClass.defrost();

			// done
			INSTANCES.put(clazz, empty);
			return empty;

		} catch (NotFoundException e) {
			throw new SystemException("ClassPoolに存在しないクラスかもしれません : " + clazz.getSimpleName(), e);
		} catch (CannotCompileException e) {
			throw new SystemException("コンパイルできず : " + clazz.getSimpleName(), e);
		} catch (InstantiationException e) {
			throw new SystemException(
					"@NoArgsConstructorが設定されていない、あるいは引数なしのConstructorが実装されていない可能性 : " + clazz.getSimpleName(), e);
		} catch (IllegalAccessException e) {
			throw new SystemException("Constructorがpublicでない可能性 : " + clazz.getSimpleName(), e);
		} catch (IllegalArgumentException e) {
			throw new SystemException("Empty Class の Constructor 生成に失敗。", e);
		} catch (SecurityException e) {
			throw new SystemException("Empty Class の Constructor 生成に失敗。", e);
		}
	}

}