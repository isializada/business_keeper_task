package com.alizada.business_keeper_task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exchangeRateHistory")
public class ExchangeRateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private Double rate;
    private Double rateAverage;
    private String trend;

    @JsonIgnore
    @Type(type="date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;

    @Transient
    private String message;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateAverage() {
        return rateAverage;
    }

    public void setRateAverage(Double rateAverage) {
        this.rateAverage = rateAverage;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String successResponseJson(){
        return "{" +
                "\"rate\":" + rate +
                ", \"rateAverage\":" + rateAverage +
                ", \"trend\":" + "\"" + trend + "\"" +
                "}";
    }

    public String errorResponseJson(){
        return "{" +
                "\"error\":" + "\"" + message + "\"" +
                "}";
    }

}
