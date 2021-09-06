public class Labor {
    private String laborId;
    private String name;
    private String position;
    private String updateTime;
    private int baseSalary;
    private int overTimeAllowance;
    private int mealAllowance;
    private int socialSecurity;
    private int otherAllowance;
    private int timeOff;
    private int otherOff;
    private int totalSalary;
    private int handSalary;
    private boolean toAccount;

    public Labor(String laborId, String name, String position, String updateTime, int baseSalary, int overTimeAllowance, int mealAllowance, int socialSecurity, int otherAllowance, int timeOff, int otherOff, int totalSalary, int handSalary, boolean toAccount) {
        this.laborId = laborId;
        this.name = name;
        this.position = position;
        this.updateTime = updateTime;
        this.baseSalary = baseSalary;
        this.overTimeAllowance = overTimeAllowance;
        this.mealAllowance = mealAllowance;
        this.socialSecurity = socialSecurity;
        this.otherAllowance = otherAllowance;
        this.timeOff = timeOff;
        this.otherOff = otherOff;
        this.totalSalary = totalSalary;
        this.handSalary = handSalary;
        this.toAccount = toAccount;
    }

    public String getLaborId() {
        return laborId;
    }

    public void setLaborId(String laborId) {
        this.laborId = laborId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getOverTimeAllowance() {
        return overTimeAllowance;
    }

    public void setOverTimeAllowance(int overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
    }

    public int getMealAllowance() {
        return mealAllowance;
    }

    public void setMealAllowance(int mealAllowance) {
        this.mealAllowance = mealAllowance;
    }

    public int getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(int socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public int getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(int otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public int getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(int timeOff) {
        this.timeOff = timeOff;
    }

    public int getOtherOff() {
        return otherOff;
    }

    public void setOtherOff(int otherOff) {
        this.otherOff = otherOff;
    }

    public int getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(int totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getHandSalary() {
        return handSalary;
    }

    public void setHandSalary(int handSalary) {
        this.handSalary = handSalary;
    }

    public boolean isToAccount() {
        return toAccount;
    }

    public void setToAccount(boolean toAccount) {
        this.toAccount = toAccount;
    }
}
