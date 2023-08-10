package nimrodBar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnswerCorrect implements Serializable {
	private Answer theAnswer;
	private boolean isCorrect;

	public AnswerCorrect(Answer theAnswer, boolean isCorrect) {
		setAnswer(theAnswer);
		setIsCorrect(isCorrect);
	}

	public boolean getIsCorrect() {
		return this.isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public boolean setAnswer(Answer theAnswer) {
		if (theAnswer == null) {
			return false;
		}

		this.theAnswer = theAnswer;
		return true;
	}

	public Answer getAnswerC() {
		return this.theAnswer;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(this.theAnswer.getAnswer() + " = " + this.isCorrect + "\n");

		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AnswerCorrect)) {
			return false;
		}
		AnswerCorrect temp = (AnswerCorrect) obj;
		return ((this.theAnswer.equals(temp.theAnswer)) && (this.isCorrect == temp.isCorrect));
	}
}
