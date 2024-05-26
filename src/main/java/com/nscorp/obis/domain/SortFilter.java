package com.nscorp.obis.domain;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortFilter {

    public static List<Sort.Order> sortOrder(String[] sort){
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if(sort[0].equals("null")){
            ;
        }
        else if(!sort[0].contains(",") && sort.length == 2 && (sort[1].equals("asc") || sort[1].equals("ASC") || sort[1].equals("DESC") || sort[1].equals("desc"))){
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        else {
            for (String sortOrder : sort) {
                if (sortOrder.contains(",")) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                } else if(sortOrder.contains(":")){
                    String[] _sort = sortOrder.split(":");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                } else {
                    orders.add(new Sort.Order(getSortDirection(" "), sortOrder));
                }
            }
        }
        return orders;
    }

    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc") || direction.equals("ASC")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc") || direction.equals("DESC")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
