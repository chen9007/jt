package com.jt.vo;

import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable {
     private  int  total;
     private List<Item> rows;
}
