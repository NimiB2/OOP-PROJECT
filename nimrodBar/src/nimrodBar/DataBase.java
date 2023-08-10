package nimrodBar;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class DataBase implements Serializable {
	private String subject;
	private Question[] allQuestions;
	private int numOfQuestions;

	private Answer[] allAnswers;
	private int numOfAnswers;

	private int maxquestions = 5;
	private int maxAnswers = 5;

	public DataBase(String subject) {
		this.allQuestions = new Question[maxquestions];
		this.allAnswers = new Answer[maxAnswers];
		this.subject = subject;

		this.numOfQuestions = 0;
		this.numOfAnswers = 0;
	}

	public String getSubject() {
		return this.subject;
	}

	public boolean setQuestion(int index, Question aQuestion) {
		if (index > this.numOfQuestions) {
			return false;
		}

		if (numOfQuestions == 0) {
			return false;
		}

		if (isQuestionExists(aQuestion.getAQuestion())) {
			return false;
		}

		this.allQuestions[index].setAQuestion(aQuestion.getAQuestion());
		return true;
	}

	public Question getQuestionByIndex1(int index) {
		return this.allQuestions[index];
	}

	public int getQuestionIndex(String aQuestion) {

		for (int i = 0; i < this.numOfQuestions; i++) {
			if (this.allQuestions[i].getAQuestion().equalsIgnoreCase(aQuestion)) {
				return i;
			}
		}
		return (-1);
	}

	public boolean isQuestionExists(String aQuestion) {
		int res;

		res = getQuestionIndex(aQuestion);
		if (res == (-1)) {
			return false;
		}
		return true;

	}

	public boolean addQuestion(Question aQuestion) {
		if (aQuestion == null) {
			System.out.println("The question is empty");
			return false;
		}

		if (isQuestionExists(aQuestion.getAQuestion())) {
			System.out.println("The question already exists in the database");
			return false;
		}

		if (this.numOfQuestions == maxquestions) {
			this.allQuestions = Arrays.copyOf(this.allQuestions, this.allQuestions.length * 2);
			this.maxquestions = this.allQuestions.length;
		}

		this.allQuestions[this.numOfQuestions++] = aQuestion;
		return true;

	}

	public boolean deleteQuestion(int index) {
		if (index > this.numOfQuestions) {
			return false;
		}

		this.allQuestions[index] = this.allQuestions[this.numOfQuestions - 1];
		this.allQuestions[this.numOfQuestions - 1] = null;
		this.numOfQuestions--;
		return true;

	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public boolean setAnswer(int index, Answer theAnswer) {
		if (index > this.numOfAnswers) {
			return false;
		}

		if (this.numOfAnswers == 0) {
			return false;
		}

		if (isAnswerExist(theAnswer)) {
			return false;
		}

		this.allAnswers[index].setAnswer(theAnswer.getAnswer());
		return true;
	}

	public Answer getAnswer(int index) {
		return this.allAnswers[index];
	}

	public int getAnswerByIndex(String theAnswer) {

		for (int i = 0; i < this.numOfAnswers; i++) {
			if (this.allAnswers[i].getAnswer().equalsIgnoreCase(theAnswer)) {
				return i;
			}
		}
		return (-1);
	}

	public boolean isAnswerExist(Answer theAnswer) {
		int res;

		res = getAnswerByIndex(theAnswer.getAnswer());
		if (res == (-1)) {
			return false;
		}
		return true;
	}

	public boolean addAnswer(Answer theAnswer) {
		if (theAnswer == null) {
			System.out.println("The answer is empty");
			return false;
		}

		if (isAnswerExist(theAnswer)) {
			System.out.println("The answer already exists in the database");
			return false;
		}

		if (this.numOfAnswers == maxAnswers) {
			this.allAnswers = Arrays.copyOf(this.allAnswers, this.allAnswers.length * 2);
			this.maxAnswers = this.allAnswers.length;
		}

		this.allAnswers[numOfAnswers++] = theAnswer;
		return true;
	}

	public boolean deleteAnswer(int index) {
		if (index > this.numOfAnswers) {
			return false;
		}

		this.allAnswers[index] = this.allAnswers[this.numOfAnswers - 1];
		this.allAnswers[this.numOfAnswers - 1] = null;
		this.numOfAnswers--;
		return true;
	}

	public int getNumOfAnswers() {
		return numOfAnswers;
	}

	public String showQuestions() {
		StringBuffer str = new StringBuffer("All questions:\n");
		for (int i = 0; i < this.numOfQuestions; i++) {
			str.append((i + 1) + ") " + this.allQuestions[i].getAQuestion());
			str.append("\n");
		}
		return str.toString();
	}

	public String showAnswers() {
		StringBuffer str = new StringBuffer("All the answers are in the database:\n");
		for (int i = 0; i < this.numOfAnswers; i++) {
			str.append((i + 1) + ") " + this.allAnswers[i].getAnswer() + "\n");
		}
		return str.toString();
	}

	public String toString() {
		StringBuffer str = new StringBuffer("Total number of questions: " + this.numOfQuestions + "\n");
		for (int i = 0; i < this.numOfQuestions; i++) {
			str.append("Question " + (i + 1) + ") ");
			str.append(this.allQuestions[i].toString() + "\n");
		}
		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DataBase)) {
			return false;
		}

		DataBase temp = (DataBase) obj;
		return (this.subject.equals(temp.subject) && (Arrays.equals(this.allAnswers, temp.allAnswers))
				&& (Arrays.equals(this.allQuestions, temp.allQuestions)));
	}
}
