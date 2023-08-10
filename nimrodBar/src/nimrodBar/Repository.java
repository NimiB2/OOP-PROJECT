package nimrodBar;

import java.util.Arrays;

public class Repository {
	private DataBase[] dbArr;
	private int numOfSubjects;

	public Repository(DataBase[] dbArr) {
		this.dbArr = dbArr;
		this.numOfSubjects = dbArr.length;
	}

	public int getNumOfSubjects() {
		return this.numOfSubjects;
	}

	public DataBase[] getAllDb() {
		return this.dbArr;
	}

	public DataBase getDbByIndex(int index) {
		return this.dbArr[index];
	}

	public int getSubjectIndex(String subject) {

		for (int i = 0; i < this.numOfSubjects; i++) {
			if (this.dbArr[i].getSubject().equalsIgnoreCase(subject)) {
				return i;
			}
		}
		return (-1);
	}

	public boolean isSubjectExists(String Subject) {
		int res;

		res = getSubjectIndex(Subject);
		if (res == (-1)) {
			return false;
		}
		return true;

	}

	public boolean addDb(DataBase db) {
		if (this.numOfSubjects > 0 && isSubjectExists(db.getSubject())) {
			return false;
		}

		this.dbArr = Arrays.copyOf(this.dbArr, this.dbArr.length + 1);
		this.dbArr[this.numOfSubjects++] = db;
		return true;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("All existing databases subject(" + this.numOfSubjects + ") :\n");
		for (int i = 0; i < this.numOfSubjects; i++) {
			str.append("Subject #" + (i + 1) + ": ");
			str.append(this.dbArr[i].getSubject() + "\n");
		}
		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Repository)) {
			return false;
		}
		Repository temp = (Repository) obj;
		return ((Arrays.equals(this.dbArr, temp.dbArr)));
	}

}
