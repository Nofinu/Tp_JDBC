package Tp2.model;

import java.util.Date;

public class Sell {
    private int id;
    private Personne personne;
    private Car car;
    private Date date;

    public Sell(Personne personne, Car car, Date date) {
        this.personne = personne;
        this.car = car;
        this.date = date;
    }

    public Sell(int id, Personne personne, Car car, Date date) {
        this(personne, car, date);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
