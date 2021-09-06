public class Material {
    private int id;
    private String name;
    private String type;
    private String unit;
    private String date;
    private float remaining;

    public Material(int id, String name, String type, String unit, float remaining,String date ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.date = date;
        this.remaining = remaining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }
}
