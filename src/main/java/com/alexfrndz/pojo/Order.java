package com.alexfrndz.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Date;
import java.util.Set;

@Data
@XmlRootElement
@XmlSeeAlso(OrderLine.class)
public class Order {

    @NotEmpty(message = "'customerName' required")
    @NotNull(message = "'customerName' required")
    @Size(min = 2, max = 100)
    private String customerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "'orderDate' required")
    private Date orderDate;


    @NotEmpty(message = "'orderLines' required")
    @NotNull(message = "'orderLines' required")
    @Size(min = 1, message = "At least 1 'orderLines' is required")
    @Valid
    private Set<OrderLine> orderLines;

    private Long id;

}
