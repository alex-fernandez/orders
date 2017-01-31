package com.alexfrndz.pojo.response.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationSearchRequest {

    private Integer start = 0;

    private Integer count = 20;


}
