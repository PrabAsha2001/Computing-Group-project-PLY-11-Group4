package javaClass;

public class ViewCategory {

    private String category;
    private int productCount;

    public ViewCategory(String category, int productCount) {
        this.category = category;
        this.productCount = productCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

