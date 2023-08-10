package nimrodBar;

@SuppressWarnings("serial")
public class TooManyQuestionsException extends Exception {
	public TooManyQuestionsException() {
		super("A test can contain between 1-10 questions");
	}
}
