package nimrodBar;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AutomaticExam implements Examable {

	@Override
	public boolean createExam(DataBase db, int maxExamQuestions) throws FileNotFoundException {
		Random random = new Random();
		int maxDbQuestions = db.getNumOfQuestions(), theSerialNum;
		Question[] indexs = new Question[maxDbQuestions];
		AnswerCorrect[] indexsAnswers;
		int countTrue, indexQ, indexA;
		int maxMcAnswers, numAnswersMc, flag = 0;
		boolean res = false, isNumAnswersOk = false;

		StringBuffer examName, solutionName;

		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
		examName = new StringBuffer("exam_" + today.format(dtf2) + ".txt");
		solutionName = new StringBuffer("solution_" + today.format(dtf2) + ".txt");

		indexs = Services.createQuestionsExamArr(db, maxDbQuestions);
		DataBase exam = new DataBase(examName.toString());

		for (int examIndex = 0; maxDbQuestions > 0 && examIndex < maxExamQuestions; examIndex++) {
			do {
				indexQ = random.nextInt(maxDbQuestions) + 1;
				Question q1 = indexs[--indexQ];
				theSerialNum = q1.SerialNum;
				try {
					if (q1 instanceof MultipleChoice) {
						MultipleChoice temp = (MultipleChoice) q1;
						maxMcAnswers = temp.getNumOfAnswersC();
						if (maxMcAnswers < 4) {
							throw new TooFewAnswersMcQuestionException();
						} else {
							countTrue = 0;
							for (int j = 0; j < maxMcAnswers && countTrue <= 1; j++) {
								if (temp.getAnswerC(j).getIsCorrect() == true) {
									countTrue++;
								}
							}
							if (countTrue > 1) {
								isNumAnswersOk = false;
							} else {
								MultipleChoice mc = new MultipleChoice(temp.getAQuestion(), temp.theDifficult);
								exam.addQuestion(mc);
								exam.getQuestionByIndex1(examIndex).setSerialNum(theSerialNum);
								indexsAnswers = Services.createAnswersArrQuestion(temp, maxMcAnswers);
								numAnswersMc = 0;
								while (numAnswersMc < 4) {
									indexA = random.nextInt(maxMcAnswers) + 1;
									AnswerCorrect ac = indexsAnswers[--indexA];
									res = mc.addAnswerC(ac.getAnswerC(), ac.getIsCorrect());
									Services.deleteAnswerFromArr(indexsAnswers, indexA, maxMcAnswers);
									numAnswersMc++;
									maxMcAnswers--;
								}
								isNumAnswersOk = true;
							}
						}
					} else {
						OpenQuestion temp = (OpenQuestion) q1;
						OpenQuestion op = new OpenQuestion(temp.getAQuestion(), temp.theDifficult, temp.getAnswer());
						exam.addQuestion(op);
						exam.getQuestionByIndex1(examIndex).setSerialNum(theSerialNum);
						exam.addAnswer(temp.getAnswer());
						isNumAnswersOk = true;
					}

				} catch (TooFewAnswersMcQuestionException e) {
					isNumAnswersOk = false;
				} finally {
					Services.deleteQuestionFromIndexs(indexs, indexQ, maxDbQuestions);
					maxDbQuestions--;
					res = isNumAnswersOk;
				}
			} while (!isNumAnswersOk && maxDbQuestions > 0);
		}

		if (res) {
			maxExamQuestions = exam.getNumOfQuestions();
			Services.createExamFiles(exam, maxExamQuestions, examName, solutionName, flag);
		}
		return res;
	}

}
