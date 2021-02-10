package com.nnk.springboot.DTO;

import javax.validation.constraints.NotNull;

public class CurvePointDTO {

    private Integer id;

    @NotNull(message = "Curve Id is mandatory")
    private Integer curveId;

    private Double term;

    @NotNull(message="Term is mandatory")
    private Double value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
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
}
