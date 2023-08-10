package nimrodBar;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManualExam implements Examable {
	public static Scanner s1 = new Scanner(System.in);

	@Override
	public boolean createExam(DataBase db, int maxExamQuestions) throws FileNotFoundException {
		char yOn;
		int maxDbQuestions = db.getNumOfQuestions();
		Question[] indexs = new Question[maxDbQuestions];
		AnswerCorrect[] indexsAnswers;
		int maxMcAnswers, numAnswersMc, theSerialNum;
		int indexQ, indexA, flag = 0;
		boolean fContinue, choice, res = false, isNumAnswersOk = false;
		boolean fContinueInput;
		StringBuffer examName, solutionName;

		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
		examName = new StringBuffer("exam_" + today.format(dtf2) + ".txt");
		solutionName = new StringBuffer("solution_" + today.format(dtf2) + ".txt");

		indexs = Services.createQuestionsExamArr(db, maxDbQuestions);

		DataBase exam = new DataBase(examName.toString());

		for (int examIndex = 0; examIndex < maxExamQuestions && maxDbQuestions > 0; examIndex++) {
			do {
				printQuestionsArr(indexs, maxDbQuestions, examIndex);
				indexQ = s1.nextInt();
				choice = Services.checkIndex(indexQ, maxDbQuestions);
				isNumAnswersOk = choice;
				try {

					if (!isNumAnswersOk) {
						System.out.println("Please try again.\n");
					} else {
						Question q1 = indexs[--indexQ];
						theSerialNum = q1.SerialNum;

						if (q1 instanceof MultipleChoice) {
							MultipleChoice temp = (MultipleChoice) q1;
							maxMcAnswers = temp.getNumOfAnswersC();

							if (maxMcAnswers < 4) {
								isNumAnswersOk = false;
								throw new TooFewAnswersMcQuestionException();
							}
							isNumAnswersOk = true;
							MultipleChoice mc = new MultipleChoice(temp.getAQuestion(), temp.theDifficult);
							exam.addQuestion(mc);
							exam.getQuestionByIndex1(examIndex).setSerialNum(theSerialNum);
							System.out.println("\nThis is a multiple choice question");
							System.out.println("(You must choose at least 4 answers!)");
							fContinue = true;
							fContinueInput = true;
							numAnswersMc = 0;

							boolean toContinue = true;
							indexsAnswers = Services.createAnswersArrQuestion(temp, maxMcAnswers);
							numAnswersMc = 0;
							while (fContinueInput && fContinue && maxMcAnswers > 0) {

								if (numAnswersMc < 4) {
									toContinue = true;
								} else {
									fContinueInput = true;
									do {
										try {
											System.out.println("Would you like to add more answers? (Y/N)");
											yOn = s1.next().charAt(0);
											toContinue = Services.checkForContinue(yOn);
											fContinueInput = false;
										} catch (InputMismatchException e) {
											System.out.println("Invalid input");
											s1.nextLine();
										} catch (Exception e) {
											System.out.println("Input is incorrect, try again");
											System.out.println("Insert - (Y/N)");
										}
									} while (fContinueInput);
								}

								if (toContinue) {
									do {
										printAnswersArr(indexsAnswers, temp, maxMcAnswers);
										System.out.println("Select the " + (numAnswersMc + 1)
												+ " answer you want to add to the test question:");
										indexA = s1.nextInt();
										res = Services.checkIndex(indexA, maxMcAnswers);

										if (res) {
											AnswerCorrect ac = indexsAnswers[--indexA];
											res = mc.addAnswerC(ac.getAnswerC(), ac.getIsCorrect());
											Services.deleteAnswerFromArr(indexsAnswers, indexA, maxMcAnswers);
											numAnswersMc++;
											maxMcAnswers--;
										}

										if (!res) {
											System.out.println("Try again.");
										}
									} while (!res);
								}

							}

							if (maxMcAnswers == 0) {
								System.out.println("There are no more answers to choose.");
							}
						} else {
							OpenQuestion temp = (OpenQuestion) q1;
							Question op = new OpenQuestion(temp.getAQuestion(), temp.theDifficult, temp.getAnswer());
							exam.addQuestion(op);
							exam.getQuestionByIndex1(examIndex).setSerialNum(theSerialNum);
							exam.addAnswer(temp.getAnswer());
						}

						System.out.print("The question addition is ");
						Services.printIfSuccess(isNumAnswersOk);

						if (isNumAnswersOk) {
							System.out.println("-------------");
							System.out.print(exam.getQuestionByIndex1(examIndex).toString());
							System.out.println("-------------");
						}
					}
				} catch (TooFewAnswersMcQuestionException e) {
					System.out.println(e.getMessage());
					System.out.println("This question has less answers then 4!");
				} finally {
					if (choice) {
						Services.deleteQuestionFromIndexs(indexs, indexQ, maxDbQuestions);
						maxDbQuestions--;
					}
					res = isNumAnswersOk;
				}

			} while (!isNumAnswersOk && maxDbQuestions > 0);
		}
		if (maxDbQuestions == 0) {
			System.out.println("There are no more questions to choose.");
		}
		if (res) {
			maxExamQuestions = exam.getNumOfQuestions();
			Services.createExamFiles(exam, maxExamQuestions, examName, solutionName, flag);
		}
		return res;
	}

	public void printQuestionsArr(Question[] indexs, int maxDbQuestions, int examIndex) {
		System.out.println("\nSelect the " + (examIndex + 1) + " question you want to add to the test:");
		System.out.println("(A multiple choice question can only be added if it has at least 4 answers)\n");
		System.out.println("Questions: ");

		for (int j = 0; j < maxDbQuestions; j++) {
			System.out.print((j + 1) + ") " + indexs[j].getAQuestion());
			if (indexs[j] instanceof MultipleChoice) {
				MultipleChoice temp = (MultipleChoice) indexs[j];
				System.out.println(" (MultipleChoice question)\n(Have " + temp.getNumOfAnswersC() + " answers)\n");
			} else {
				System.out.println("(Open question)\n");
			}
		}
	}

	public void printAnswersArr(AnswerCorrect[] indexs, MultipleChoice mc, int maxMcAnswers) {
		System.out.println("\nYour question- " + mc.getAQuestion());
		System.out.println("These are answers to a question that do not already exist in the test: ");
		for (int i = 0; i < maxMcAnswers; i++) {
			System.out.print((i + 1) + ") " + indexs[i].getAnswerC());
		}
	}

}
