public class Day implements Cloneable, Comparable<Day>{
	
	private int year;
	private int month;
	private int day;

    private static final String monthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}

    public Day(String date) throws ExInvalidDateFormat {
        set(date);
    }

	@Override
	public int compareTo(Day anotherDay) {
		if (anotherDay == null) {
			return 0;
		}
        if (year < anotherDay.year) return -1;
        else if (year > anotherDay.year) return 1;
        else if (month < anotherDay.month) return -1;
        else if (month > anotherDay.month) return 1;
        else if (day < anotherDay.day) return -1;
        else if (day > anotherDay.day) return 1;
        else return 0;
	}
	
    @Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
	public String toString() {
        return day+"-"+ monthNames.substring((month-1)*3, (month)*3) + "-"+ year; // (month-1)*3,(month)*3
	}

    public void set(String sDay) throws ExInvalidDateFormat {
		String[] sDayParts = sDay.split("-");

		if (sDayParts.length < 3) {
			throw new ExInvalidDateFormat();
		}
		try {
			this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
			this.year = Integer.parseInt(sDayParts[2]);
			this.month = monthNames.indexOf(sDayParts[1])/3+1;

			String correctMonthName = monthNames.substring((month-1)*3, (month)*3);

			if (!valid(year, month, day)) {
				throw new ExInvalidDateFormat();
			}
			if (!sDayParts[1].equals(correctMonthName)) {
				throw new ExInvalidDateFormat();
			}
		}
		catch (NumberFormatException e) {
			throw new ExInvalidDateFormat();
		}
    }

	public boolean isEndOfMonth() {
		if (day==28 && month==2 && !isLeapYear(year)) return true;
		if (day==29 && month==2 && isLeapYear(year)) return true;
		if (day==30 && (month==4 || month==6 || month==9 || month==11)) return true;
		if (day==31) return true;
		return false;
	}

	public boolean isEndOfYear() {
		return month==12 && day==31;
	}

	public void addDays(int n) {
        while (n > 0) {
            int daysInMonth = daysInMonth(year, month);
            if (day + n <= daysInMonth) {
                day += n;
                n = 0;
            } else {
                n -= (daysInMonth - day + 1);
                day = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }
        }
    }

	private int daysInMonth(int year, int month) {
        switch (month) {
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                if (isLeapYear(year))
                    return 29;
                else
                    return 28;
        }
        return 0;
    }
}