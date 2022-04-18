public class UserDetails {
    String dobDay, dobMonth, dobYear;

    public UserDetails(){

    }

    public UserDetails(String bDay, String bMonth, String bYear){
        this.dobDay = bDay;
        this.dobMonth = bMonth;
        this.dobYear = bYear;

    }

    public String getDobDay() {
        return dobDay;
    }

    public void setDobDay(String dobDay) {
        this.dobDay = dobDay;
    }

    public String getDobMonth() {
        return dobMonth;
    }

    public void setDobMonth(String dobMonth) {
        this.dobMonth = dobMonth;
    }

    public String getDobYear() {
        return dobYear;
    }

    public void setDobYear(String dobYear) {
        this.dobYear = dobYear;
    }
}
