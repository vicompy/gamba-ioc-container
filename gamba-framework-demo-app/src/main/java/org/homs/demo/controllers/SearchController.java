package org.homs.demo.controllers;

import java.util.List;

import org.homs.demo.formbeans.SearchBean;
import org.homs.demo.models.Artifact;
import org.homs.demo.models.ArtifactBO;
import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;
import org.homs.gamba.scanner.Action;

public class SearchController {

	private final ArtifactBO artifactService;

	public SearchController() {
		artifactService = new ArtifactBO();
	}

	@Action(name = "search", formBean = SearchBean.class)
	public String search(final RequestContext req, final SearchBean form) {

		try {

			final List<Artifact> l = artifactService.search(form.getWordToSearch(), form.getDept());

			req.getRequest().setAttribute("artifactlist", l);
			req.getRequest().setAttribute("form", form);

			return "show-results";

		} catch (final Exception exc) {
			req.getRequest().setAttribute("exception", exc);
			return "error";
		}
	}

	@Action(name = "start")
	public String startingView(final RequestContext req, final EmptyFormBean form) {

		final Long artifactsCount = artifactService.artifactsCount();
		req.getRequest().setAttribute("artifactsCount", artifactsCount);

		return "search-form";
	}

}
