package com.roba.roba.data;

public class SuccesMessage {

    Double price;

    public SuccesMessage(Double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "SuccesMessage{" +
                "price=" + price +
                '}';
    }
}
