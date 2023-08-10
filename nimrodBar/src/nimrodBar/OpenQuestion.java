package nimrodBar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OpenQuestion extends Question implements Serializable {
	private Answer theAnswer;

	public OpenQuestion(String aQuestion, eDifficulty theDifficult, Answer theAnswer) {
		super(aQuestion, theDifficult);
		setAnswer(theAnswer);
	}

	@Override
	public boolean isAnswerExist(Answer theAnswer) {
		if (this.theAnswer.getAnswer().equalsIgnoreCase(theAnswer.getAnswer())) {
			return true;
		}

		return false;
	}

	public boolean setAnswer(Answer theAnswer) {
		if (theAnswer == null) {
			System.out.println("The answer is null");
			return false;
		}

		this.theAnswer = theAnswer;
		return true;
	}

	public Answer getAnswer() {
		return this.theAnswer;
	}

	@Override
	public String toString() {
		return super.toString() + "The answer is:\n" + this.theAnswer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OpenQuestion)) {
			return false;
		}
		if (!(super.equals(obj))) {
			return false;
		}

		OpenQuestion temp = (OpenQuestion) obj;
		return (this.theAnswer == temp.theAnswer);
	}
}
