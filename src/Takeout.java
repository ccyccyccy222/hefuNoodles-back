public class Takeout {
    String key;
    String date;
    //这里默认只有一个菜品
    String order;
    int state;
    String comment;
    String customer;
    String platform;
    String refundReason;

    public Takeout(String key, String date, String order, int state, String comment, String customer, String platform, String refundReason) {
        this.key = key;
        this.date = date;
        this.order = order;
        this.state = state;
        this.comment = comment;
        this.customer = customer;
        this.platform = platform;
        this.refundReason = refundReason;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
