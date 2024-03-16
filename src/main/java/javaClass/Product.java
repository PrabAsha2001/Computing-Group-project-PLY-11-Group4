package javaClass;

public class Product {
    private String code;
    private String productName;
    private  float price;
    private String category;
    private String description;
    private int qty;

    public Product(String code, String productName, float price, String category, String description, int qty) {
        this.code = code;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
