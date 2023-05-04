package Tp2.model;

public class Car{
    private int id;
    private String name;
    private String year;
    private float  power;
    private float price;

    public Car(String name, String year, float power, float price) {
        this.name = name;
        this.year = year;
        this.power = power;
        this.price = price;
    }

    public Car(int id, String name, String year, float power, float price) {
        this(name, year, power, price);
        this.id = id;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
