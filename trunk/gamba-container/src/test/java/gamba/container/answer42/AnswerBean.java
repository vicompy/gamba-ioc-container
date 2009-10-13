package gamba.container.answer42;

public class AnswerBean {

	// No. of years to the calculate the Ultimate Answer
	private int years;

	// The Answer to Life, the Universe, and Everything
	private String ultimateAnswer;

	public AnswerBean() {
	}

	public AnswerBean(final int years, final String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
	}

	public int getYears() {
		return years;
	}

	public String getUltimateAnswer() {
		return ultimateAnswer;
	}

	public void setYears(final int years) {
		this.years = years;
	}

	public void setUltimateAnswer(final String ultimateAnswer) {
		this.ultimateAnswer = ultimateAnswer;
	}

}
