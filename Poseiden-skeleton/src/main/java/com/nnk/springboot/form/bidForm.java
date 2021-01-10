package com.nnk.springboot.form;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

public class bidForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Bidlistid")
    private Integer BidListId;

    @Column(name="account")
    private String account;

    @Column(name="type")
    private String type;

    @Column(name="bidquantity")
    private Double bidQuantity;

    @Column(name="askquantity")
    private Double askQuantity;

    @Column(name="bid")
    private Double bid;

    @Column(name="ask")
    private Double ask;

    @Column(name="benchmark")
    private String benchmark;

    @Column(name="bidlistdate")
    private Timestamp bidListDate;

    @Column(name="commentary")
    private String commentary;

    @Column(name="security")
    private String security;

    @Column(name="status")
    private String status;

    @Column(name="trader")
    private String trader;

    @Column(name="book")
    private String book;

    @Column(name="creationname")
    private String creationName;

    @Column(name="creationdate")
    private Timestamp creationDate;

    @Column(name="revisionname")
    private String revisionName;

    @Column(name="revisiondate")
    private Timestamp revisionDate;

    @Column(name="dealname")
    private String dealName;

    @Column(name="dealtype")
    private String dealType;

    @Column(name="sourcelistid")
    private String sourceListId;

    @Column(name="side")
    private String side;
}
