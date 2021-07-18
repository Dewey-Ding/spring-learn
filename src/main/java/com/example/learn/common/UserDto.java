package com.example.learn.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String name;
    private Integer age;



    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(132);
        BigDecimal b = new BigDecimal(153);
        System.out.println(a.divide(b,5,RoundingMode.HALF_UP) );
    }
}
