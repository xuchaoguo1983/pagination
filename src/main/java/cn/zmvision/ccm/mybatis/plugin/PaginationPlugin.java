package cn.zmvision.ccm.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class PaginationPlugin extends PluginAdapter {
	final FullyQualifiedJavaType pageListType = new FullyQualifiedJavaType(
			"com.github.miemiedev.mybatis.paginator.domain.PageList");
	final FullyQualifiedJavaType pageBoundsType = new FullyQualifiedJavaType(
			"com.github.miemiedev.mybatis.paginator.domain.PageBounds");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		interfaze.addImportedType(pageListType);
		interfaze.addImportedType(pageBoundsType);
		interfaze.addMethod(generateSelectByPage(method, introspectedTable));

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(
			Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		interfaze.addImportedType(pageListType);
		interfaze.addImportedType(pageBoundsType);
		interfaze.addMethod(generateSelectByPage(method, introspectedTable));

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(pageListType);
		topLevelClass.addImportedType(pageBoundsType);
		topLevelClass
				.addMethod(generateSelectByPage(method, introspectedTable));
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(
			Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(pageListType);
		topLevelClass.addImportedType(pageBoundsType);

		topLevelClass
				.addMethod(generateSelectByPage(method, introspectedTable));
		return true;
	}

	private Method generateSelectByPage(Method method,
			IntrospectedTable introspectedTable) {
		Method m = new Method("selectByExample");

		m.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType parameterType = introspectedTable.getRules()
				.calculateAllFieldsClass();

		final FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(
				"com.github.miemiedev.mybatis.paginator.domain.PageList");
		returnType.addTypeArgument(parameterType);

		m.setReturnType(returnType);

		m.addParameter(new Parameter(new FullyQualifiedJavaType(
				introspectedTable.getExampleType()), "example"));

		m.addParameter(new Parameter(pageBoundsType, "pageBounds"));

		context.getCommentGenerator().addGeneralMethodComment(m,
				introspectedTable);
		return m;
	}

	public boolean validate(List<String> warnings) {
		return true;
	}
}
