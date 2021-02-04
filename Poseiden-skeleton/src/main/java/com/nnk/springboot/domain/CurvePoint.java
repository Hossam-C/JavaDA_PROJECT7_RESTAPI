package com.nnk.springboot.domain;

//import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer Id;

    @Column(name="curveId")
    @NotNull(message = "Curve Id is mandatory")
    private Integer curveId;

    @Column(name="asOfDate")
    private Timestamp asOfDate;

    @Column(name="term")
    @NotNull(message="Term is mandatory")
    private Double term;

    @Column(name="value")
    private Double value;

    @Column(name="creationDate")
    private Timestamp creationDate;


    public CurvePoint(){

    }
    public CurvePoint(Integer curveId, Double term, double value) {
        setCurveId(curveId);
        setTerm(term);
        setValue(value);
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        Timestamp localTimestamp = asOfDate;
        return localTimestamp;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        if (asOfDate == null) {
            this.asOfDate = null;
        }
        else {
            this.asOfDate = new Timestamp(asOfDate.getTime());
        }
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        Timestamp localTimestamp = creationDate;
        return localTimestamp;
    }

    public void setCreationDate(Timestamp creationDate) {
        if (creationDate == null) {
            this.creationDate = null;
        }
        else {
            this.creationDate = new Timestamp(creationDate.getTime());
        }
    }
}
