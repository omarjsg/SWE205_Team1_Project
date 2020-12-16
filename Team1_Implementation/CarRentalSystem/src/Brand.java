
public class Brand {
    private String brandName;
    private int discount;
    public Brand (String brandName, int discount){
        setBrandName(brandName);
        setDiscount(discount);
    }
    public String getBrandName() {
        return brandName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    @Override
    public String toString() {
    	return String.format("Brand: %s,\t\tDiscount: %d%%", getBrandName(), getDiscount());
    }

}
