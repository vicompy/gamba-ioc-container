package app.demo.forms;

public class BookmarkForm {

	private Integer id;

	private String title;
	private String url;
	private String comments;

	private String spaceSeparatedTags;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public String getSpaceSeparatedTags() {
		return spaceSeparatedTags;
	}

	public void setSpaceSeparatedTags(final String spaceSeparatedTags) {
		this.spaceSeparatedTags = spaceSeparatedTags;
	}

}
