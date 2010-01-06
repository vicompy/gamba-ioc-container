package app.demo.controllers;

import org.lechuga.mvc.dispatcher.RequestContext;

import app.demo.forms.BookmarkForm;

public class BookmarkController {

	public String create(final RequestContext ctx) {

		ctx.setAttr("titleForm", "New interesting bookmark");
		return "bookmark-form";
	}

	public String save(final RequestContext ctx, final BookmarkForm bookmarkForm) {

		return "*/bookmark/create.do";
	}
}
