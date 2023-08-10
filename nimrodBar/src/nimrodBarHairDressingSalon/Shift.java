package nimrodBarHairDressingSalon;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import nimrodBar.DataBase;
import nimrodBar.Question;

public class Shift implements Comparable<Shift> {
	private HairDresser[] allHairDressers;
	private Treatment[] allTreatments;
	private Date theDate;
	private Date startTime;
	private Date endTime;
	private int maxTreatments = 5;
	private int totalProfits;
	private int numOfHairDressers;
	private int numOfTreatments;

	public Shift(Date theDate, Date startTime, Date endTime, int maxHairDressers) {
		this.theDate = theDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.allHairDressers = new HairDresser[maxHairDressers];
		this.allTreatments = new Treatment[this.maxTreatments];
		this.numOfHairDressers = 0;
		this.numOfTreatments = 0;
		this.totalProfits = 0;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public Date getDate() {
		return this.theDate;
	}

	public int getNumOfHairDressers() {
		return this.numOfHairDressers;
	}

	public int getNumOfTreatments() {
		return this.numOfTreatments;
	}

	public HairDresser getHairDresserByIndex(int index) {
		return this.allHairDressers[index];
	}

	public int getHairDresserIndex(String hairDresser) {

		for (int i = 0; i < this.numOfHairDressers; i++) {
			if (this.allHairDressers[i].hairDresser().equalsIgnoreCase(hairDresser)) {
				return i;
			}
		}
		return (-1);
	}

	public boolean isHairDresserExists(String hairDresser) {
		int res;

		res = getHairDresserIndex(hairDresser);
		if (res == (-1)) {
			return false;
		}
		return true;

	}

	public Treatment getTreatmentByIndex(int index) {
		return this.allTreatments[index];
	}

	public int getTreatmentIndex(String theTreatment) {

		for (int i = 0; i < this.numOfHairDressers; i++) {
			if (this.allTreatments[i].theTreatment().equalsIgnoreCase(theTreatment)) {
				return i;
			}
		}
		return (-1);
	}

	public boolean isTreatmentExists(String theTreatment) {
		int res;

		res = getHairDresserIndex(theTreatment);
		if (res == (-1)) {
			return false;
		}
		return true;

	}

	public double calculationOfTotalProfits() {
		this.totalProfits = 0;
		for (int i = 0; i < numOfTreatments; i++) {
			this.totalProfits += this.allTreatments[i].totalPrice();
			if (this.allTreatments[i].getClient() instanceof vipClient) {
				this.totalProfits -= CoffeeAndPastry.getCost();
			}
		}
		long diff = this.endTime.getTime() - this.startTime.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		for (int i = 0; i < numOfHairDressers; i++) {
			this.totalProfits -= (this.allHairDressers[i].getBasicSalary() * (diffHours));

			if (this.allHairDressers[i] instanceof Senior) {
				this.totalProfits -= CoffeeAndPastry.getCost();
			}
		}
		return this.totalProfits;
	}

	public void sortTreatmentsByStartTime() {
		Arrays.sort(this.allTreatments, new comperTreatmentByStartTime());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Shift)) {
			return false;
		}

		Shift temp = (Shift) obj;
		return ((Arrays.equals(this.allHairDressers, temp.allHairDressers))
				&& (Arrays.equals(this.allTreatments, temp.allTreatments)) && (this.startTime == temp.startTime)
				&& (this.endTime == temp.endTime) && (this.theDate == temp.theDate)
				&& (this.totalProfits == temp.totalProfits));
	}

	public String toString() {
		StringBuffer str = new StringBuffer("Shift in: " + this.theDate + "\n");
		str.append("start at: " + this.startTime + "\n");
		str.append("end at: " + this.endTime + "\n");
		str.append("Num of Hair Dressers: " + this.numOfHairDressers + " and they: \n");
		for (int i = 0; i < this.numOfHairDressers; i++) {
			str.append((i + 1) + ") " + this.allHairDressers[i].toString() + "\n");
		}
		str.append("Num of Treatments: " + this.numOfTreatments + "and they: \n");
		for (int i = 0; i < this.numOfTreatments; i++) {
			str.append((i + 1) + ") " + this.allTreatments[i].toString() + "\n");
		}
		str.append("the total profits of this shift is: " + this.totalProfits);
		return str.toString();
	}
}
