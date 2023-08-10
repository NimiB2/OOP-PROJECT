package nimrodBar;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class MultipleChoice extends Question implements Serializable {
	private final int MAX_ANSWERS = 10;

	private AnswerCorrect[] allAnswersC;
	private int numOfAnswersC;

	public MultipleChoice(String aQuestion, eDifficulty theDifficult) {
		super(aQuestion, theDifficult);
		this.allAnswersC = new AnswerCorrect[MAX_ANSWERS];
		this.numOfAnswersC = 0;
	}

	@Override
	public boolean isAnswerExist(Answer theAnswer) {

		for (int i = 0; i < this.numOfAnswersC; i++) {
			if (this.allAnswersC[i].getAnswerC().getAnswer().equalsIgnoreCase(theAnswer.getAnswer())) {
				return true;
			}
		}
		return false;
	}

	public AnswerCorrect getAnswerC(int index) {
		return this.allAnswersC[index];
	}

	public AnswerCorrect[] getAnswers() {
		return this.allAnswersC;
	}

	public boolean setAnswerC(int index, Answer theAnswer, boolean isCorrect) {
		if (theAnswer.getAnswer().equals(null)) {
			return false;
		}

		if (index > this.numOfAnswersC) {
			return false;
		}

		this.allAnswersC[index].setAnswer(theAnswer);
		this.allAnswersC[index].setIsCorrect(isCorrect);
		return true;
	}

	public boolean addAnswerC(Answer theAnswer, boolean isCorrect) {
		if (theAnswer == null) {
			return false;
		}

		if (this.numOfAnswersC == MAX_ANSWERS) {
			return false;
		}

		AnswerCorrect aC = new AnswerCorrect(theAnswer, isCorrect);

		this.allAnswersC[this.numOfAnswersC++] = aC;
		return true;
	}

	public boolean deleteAnswerC(int index) {
		if (index > this.numOfAnswersC) {
			return false;
		}

		this.allAnswersC[index] = this.allAnswersC[this.numOfAnswersC - 1];
		this.allAnswersC[this.numOfAnswersC - 1] = null;
		this.numOfAnswersC--;
		return true;
	}

	public int getNumOfAnswersC() {
		return this.numOfAnswersC;
	}

	public int getMaxAnswers() {
		return this.MAX_ANSWERS;
	}

	public String showAnswers() {
		StringBuffer str = new StringBuffer();
		if (this.numOfAnswersC > 0) {
			str.append("The answers: \n");
			for (int i = 0; i < this.numOfAnswersC; i++) {
				str.append((i + 1) + ")" + this.allAnswersC[i].toString());
			}
		} else {
			str.append("No answers for this qustion\n");
		}

		return str.toString();
	}

	@Override
	public String toString() {
		return super.toString() + showAnswers();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultipleChoice)) {
			return false;
		}
		if (!(super.equals(obj))) {
			return false;
		}

		MultipleChoice temp = (MultipleChoice) obj;
		return (Arrays.equals(this.allAnswersC, temp.allAnswersC));
	}
}
