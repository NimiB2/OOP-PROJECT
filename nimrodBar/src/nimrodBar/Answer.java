package nimrodBar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Answer implements Serializable {
	private String theAnswer;

	public Answer(String theAnswer) {
		setAnswer(theAnswer);
	}

	public String getAnswer() {
		return this.theAnswer;
	}

	public boolean setAnswer(String theAnswer) {
		if (theAnswer == null) {
			return false;
		}

		this.theAnswer = theAnswer;
		return true;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(this.theAnswer + "\n");
		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Answer)) {
			return false;
		}
		Answer temp = (Answer) obj;
		return (this.theAnswer.equals(temp.theAnswer));
	}

}
