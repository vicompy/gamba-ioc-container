package org.gro.mvc.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gro.annoscan.AnnoScanner;
import org.gro.annoscan.AnnotatedMethod;

public class ActionScanner {

	public Map<String, DeclaredAction> doScan(final String basePackage) {

		final Map<String, DeclaredAction> da = new HashMap<String, DeclaredAction>();
		final List<AnnotatedMethod> annotatedMethods = new AnnoScanner().doMethodScan(basePackage,
				Action.class);

		for (final AnnotatedMethod am : annotatedMethods) {
			final Action annoAction = (Action) am.annotation;
			da.put(annoAction.name(), new DeclaredAction(annoAction.name(), am.annotatedClass,
					am.annotatedMethod, annoAction.formBean(), annoAction.validator(), annoAction
							.onValidationError()));
		}

		return da;
	}
}
