package org.gro.orm.service;

import java.sql.Connection;

import org.gro.orm.dao.GroDaoFactory;
import org.gro.orm.service.daos.TagDao;
import org.gro.orm.service.daos.UrlDao;
import org.gro.orm.service.daos.UserDao;
import org.gro.orm.service.daos.UserUrlDao;
import org.gro.orm.service.ents.Tag;
import org.gro.orm.service.ents.Url;
import org.gro.orm.service.ents.User;
import org.gro.orm.service.ents.UserUrl;

public class BookmarkService implements IBookmarkService {

	private Connection c;

	public void setConnection(final Connection c) {
		this.c = c;
	}

	public User loadUser(final String userName) {

		final UserDao dogdao = (UserDao) GroDaoFactory.newInstance(UserDao.class, c);
		final UrlDao urldao = (UrlDao) GroDaoFactory.newInstance(UrlDao.class, c);
		final TagDao tagdao = (TagDao) GroDaoFactory.newInstance(TagDao.class, c);

		final User user = dogdao.loadByName(userName);
		final Url[] userUrls = urldao.loadByUser(user);
		for (final Url url : userUrls) {
			final Tag[] tags = tagdao.loadByUrl(url);
			url.setTags(tags);
		}
		user.setUrls(userUrls);

		return user;
	}

	public UserUrl[] loadAllUserUrls() {

		final UserUrlDao userUrlDao = (UserUrlDao) GroDaoFactory.newInstance(UserUrlDao.class, c);
		return userUrlDao.loadAll();
	}

	public Tag createTag(final Tag tag) {
		return ((TagDao) GroDaoFactory.newInstance(TagDao.class, c)).create(tag);
	}
	public Tag[] loadAllTags() {
		return ((TagDao) GroDaoFactory.newInstance(TagDao.class, c)).loadAll();
	}

}
