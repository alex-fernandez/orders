package com.alexfrndz.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement
public class OrderLine {


    @NotNull(message = "'qty' is required")
    @Min(message = "Minimum 'qty' is 1", value = 1)
    private int qty;

    @NotEmpty(message = "'product' is required.")
    @NotNull(message = "'product' is required.")
    private String product;

    @NotEmpty(message = "'price' is required.")
    @NotNull(message = "'price' is required")
    private Double price;
}
