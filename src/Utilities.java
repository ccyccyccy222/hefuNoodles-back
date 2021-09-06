public class Utilities {
    private int key;
    private String date;
    private float waterUnit;
    private float waterVolume;
    private float waterAmount;
    private float electricUnit;
    private float electricVolume;
    private float electricAmount;
    private float gasUnit;
    private float gasVolume;
    private float gasAmount;
    private float totalAmount;

    public Utilities(int key, String date, float waterUnit, float waterVolume, float waterAmount, float electricUnit, float electricVolume, float electricAmount, float gasUnit, float gasVolume, float gasAmount, float totalAmount) {
        this.key = key;
        this.date = date;
        this.waterUnit = waterUnit;
        this.waterVolume = waterVolume;
        this.waterAmount = waterAmount;
        this.electricUnit = electricUnit;
        this.electricVolume = electricVolume;
        this.electricAmount = electricAmount;
        this.gasUnit = gasUnit;
        this.gasVolume = gasVolume;
        this.gasAmount = gasAmount;
        this.totalAmount = totalAmount;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(float waterUnit) {
        this.waterUnit = waterUnit;
    }

    public float getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(float waterVolume) {
        this.waterVolume = waterVolume;
    }

    public float getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(float waterAmount) {
        this.waterAmount = waterAmount;
    }

    public float getElectricUnit() {
        return electricUnit;
    }

    public void setElectricUnit(float electricUnit) {
        this.electricUnit = electricUnit;
    }

    public float getElectricVolume() {
        return electricVolume;
    }

    public void setElectricVolume(float electricVolume) {
        this.electricVolume = electricVolume;
    }

    public float getElectricAmount() {
        return electricAmount;
    }

    public void setElectricAmount(float electricAmount) {
        this.electricAmount = electricAmount;
    }

    public float getGasUnit() {
        return gasUnit;
    }

    public void setGasUnit(float gasUnit) {
        this.gasUnit = gasUnit;
    }

    public float getGasVolume() {
        return gasVolume;
    }

    public void setGasVolume(float gasVolume) {
        this.gasVolume = gasVolume;
    }

    public float getGasAmount() {
        return gasAmount;
    }

    public void setGasAmount(float gasAmount) {
        this.gasAmount = gasAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
