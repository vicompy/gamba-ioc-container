package org.gro.mvc.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gro.annoscan.AnnoScanner;
import org.gro.annoscan.AnnotatedMethod;

public class ActionScanner {

	public Map<String, DeclaredAction> doScan(final String basePackage) {

		final Map<String, DeclaredAction> da = new HashMap<String, DeclaredAction>();
		final List<AnnotatedMethod> l = new AnnoScanner().doMethodScan(basePackage, Action.class);

		for (final AnnotatedMethod am : l) {
			final Action a = (Action) am.annotation;
			da.put(a.name(), new DeclaredAction(a.name(), am.annotatedClass, am.annotatedMethod,
					a.formBean(), a.validator(), a.onValidationError()));
		}

		return da;
	}
}
