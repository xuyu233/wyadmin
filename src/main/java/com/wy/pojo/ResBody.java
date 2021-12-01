package com.wy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResBody<T> {
    private  Integer code;
    private  String msg;
    private  long count;
    private  List<T> data  = new ArrayList<T>();
}