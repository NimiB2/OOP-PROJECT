package nimrodBar;

@SuppressWarnings("serial")
public class TooFewAnswersMcQuestionException extends Exception {
	public TooFewAnswersMcQuestionException() {
		super("For add multiple choice to the test,it must have at least 4 answers");
	}
}
