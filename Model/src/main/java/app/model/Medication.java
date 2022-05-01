package app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medications", schema = "public")
public class Medication implements  Identifiable<Long>, Serializable {

    @Id
    @Column(name = "medID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medName")
    private String medName;

    @Column(name = "medRole")
    private String medRole;

    @Column(name = "stocks")
    private Long stocks;

    public Medication(String medName, String medRole, Long stocks) {
        this.medName = medName;
        this.medRole = medRole;
        this.stocks = stocks;
    }

    public Medication() {

    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedRole() {
        return medRole;
    }

    public void setMedRole(String medRole) {
        this.medRole = medRole;
    }

    public Long getStocks() {
        return stocks;
    }

    public void setStocks(Long stocks) {
        this.stocks = stocks;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", medName='" + medName + '\'' +
                ", medRole='" + medRole + '\'' +
                ", stocks=" + stocks +
                '}';
    }
}
