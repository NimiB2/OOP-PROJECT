package nimrodBar;

import java.io.FileNotFoundException;

public interface Examable {
	boolean createExam(DataBase db, int maxExamQuestions) throws FileNotFoundException;
}
