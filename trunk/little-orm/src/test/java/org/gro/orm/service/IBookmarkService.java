package org.gro.orm.service;

import org.gro.orm.service.ents.User;
import org.gro.orm.service.ents.UserUrl;

public interface IBookmarkService extends Transactional {

	User loadUser(final String userName);
	UserUrl[] loadAllUserUrls();

}