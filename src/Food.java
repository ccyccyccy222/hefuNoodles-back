public class Food {
    private String id;
    private String name;
    private float price;
    private String imgurl;
    private String bill;

    public Food(String id, String name, float price, String imgurl, String bill) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgurl = imgurl;
        this.bill = bill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
