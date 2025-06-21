package com.awbd.mybarberapp.dtos;


import com.awbd.mybarberapp.domain.Category;
import com.awbd.mybarberapp.domain.Currency;

import com.awbd.mybarberapp.domain.Info;
import com.awbd.mybarberapp.domain.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String code;
    private Double reservePrice;
    private Boolean restored;
    private Info info;
    private Participant seller;
    private List<Category> categories;
    private Currency currency;

}
