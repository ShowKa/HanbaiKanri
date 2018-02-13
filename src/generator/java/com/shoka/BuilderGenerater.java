package com.shoka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.showka.domain.Busho;
import com.showka.domain.DomainBase;
import com.showka.domain.builder.DomainBuilderBase;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class BuilderGenerater {

	private static String TEMPLATE_FILE = "BuilderTemplate.ftl";
	private static String DESTINATION = "generated/";

	@SuppressWarnings("rawtypes")
	static Class TARGET_CLASS = Busho.class;

	public static void main(String[] args) {

		// get domain private fields
		List<Field> privateFields = new ArrayList<>();
		ArrayList<Field> allFields = new ArrayList<Field>();
		for (Field f : TARGET_CLASS.getDeclaredFields()) {
			allFields.add(f);
		}

		// get domain parent private fields if exists
		Class<?> parent = TARGET_CLASS.getSuperclass();
		while (parent != null) {
			if (!DomainBase.class.isAssignableFrom(parent)) {
				break;
			}
			for (Field f : parent.getDeclaredFields()) {
				allFields.add(f);
			}
			parent = parent.getSuperclass();
		}

		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers())) {
				privateFields.add(field);
			}
		}

		// config
		DomainConfig domainConfig = new DomainConfig();
		domainConfig.setBuilderBaseClass(DomainBuilderBase.class.getName());
		domainConfig.setDomain(TARGET_CLASS.getSimpleName().replaceAll("Domain$", ""));
		ArrayList<HashMap<String, String>> members = new ArrayList<HashMap<String, String>>();
		for (Field m : privateFields) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", m.getType().getSimpleName());
			map.put("name", m.getName());
			map.put("explain", m.getName());
			members.add(map);
		}

		// set members
		domainConfig.setMembers(members);

		// configuration
		Configuration fileMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
		fileMarkerConfig.setClassForTemplateLoading(BuilderGenerater.class, "/");

		try {

			// template
			freemarker.template.Template template = fileMarkerConfig.getTemplate(TEMPLATE_FILE);

			// map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("builderBaseClass", domainConfig.getBuilderBaseClass());
			map.put("domain", domainConfig.getDomain());
			map.put("members", domainConfig.getMembers());

			StringBuilder memberListWithComma = new StringBuilder();
			for (HashMap<String, String> m : domainConfig.getMembers()) {
				String name = m.get("name");
				if (name.equals("recordId")) {
					continue;
				}
				if (name.equals("version")) {
					continue;
				}
				memberListWithComma.append("," + name);
			}
			map.put("memberListWithComma", memberListWithComma.toString().replaceAll("^,", ""));

			// write
			File file = new File(DESTINATION + domainConfig.getDomain() + "DomainBuilder.java");
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			template.process(map, writer);

		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		System.out.println("Success!!");
	}

}
