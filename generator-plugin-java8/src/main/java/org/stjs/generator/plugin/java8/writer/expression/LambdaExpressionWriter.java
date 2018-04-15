package org.stjs.generator.plugin.java8.writer.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.lang.model.element.Element;

import com.sun.source.tree.VariableTree;
import org.stjs.generator.GenerationContext;
import org.stjs.generator.GeneratorConstants;
import org.stjs.generator.check.expression.IdentifierAccessOuterScopeCheck;
import org.stjs.generator.javac.InternalUtils;
import org.stjs.generator.javac.TreeUtils;
import org.stjs.generator.javascript.JavaScriptBuilder;
import org.stjs.generator.javascript.Keyword;
import org.stjs.generator.utils.JavaNodes;
import org.stjs.generator.writer.JavascriptTypes;
import org.stjs.generator.writer.WriterContributor;
import org.stjs.generator.writer.WriterVisitor;
import org.stjs.generator.writer.declaration.MethodWriter;
import org.stjs.generator.writer.expression.MethodInvocationWriter;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LambdaExpressionTree.BodyKind;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.util.TreeScanner;

/**
 * generates the code for Lambda expressions.
 *
 * <pre>
 * (x, y) -&gt; x + y
 * </pre>
 *
 * gives
 *
 * <pre>
 * function (x,y) {
 * 	return x + y;
 * }
 * </pre>
 * @author acraciun
 * @param <JS>
 */
public class LambdaExpressionWriter<JS> extends JavascriptTypes<JS> implements WriterContributor<LambdaExpressionTree, JS> {

	private static String changeName(String name) {
		if (name.equals(GeneratorConstants.ARGUMENTS_PARAMETER)) {
			return "_" + name;
		}

		return name;
	}

	public List<JS> getParams(List<? extends VariableTree> treeParams, GenerationContext<JS> context) {
		List<JS> params = new ArrayList<>();
		for (VariableTree param : treeParams) {
			String name = changeName(param.getName().toString());

			JS type = getFieldTypeDesc(InternalUtils.symbol(param).asType(), context);;
			params.add(context.js().param(name, type, InternalUtils.isVarArg(param)));
		}
		return params;
	}

	@Override
	public JS visit(WriterVisitor<JS> visitor, LambdaExpressionTree tree, GenerationContext<JS> context) {
		List<JS> params = getParams(tree.getParameters(), context);
		JavaScriptBuilder<JS> js = context.js();
		JS body = visitor.scan(tree.getBody(), context);

		// Functions that have a custom "THIS" should not be arrow functions
		int specialThisParamPos = MethodWriter.getTHISParamPos(tree.getParameters());
		if (specialThisParamPos >= 0) {
			if (tree.getBodyKind() == BodyKind.EXPRESSION) {
				body = js.returnStatement(body);
			}

			if (!(tree.getBody() instanceof BlockTree)) {
				body = js.block(Collections.singleton(body));
			}

			JS lambdaFunc = js.function(null, params, body);
			JS target = js.keyword(Keyword.THIS);
			JS stjsBind = js.property(context.js().name(GeneratorConstants.STJS), "bind");
			return js.functionCall(stjsBind, Arrays.asList(target, lambdaFunc, js.number(specialThisParamPos)));
		}

		return js.arrowFunction(params, body);
	}
}
