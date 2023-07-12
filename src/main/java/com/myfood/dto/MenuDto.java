package com.myfood.dto;

import com.myfood.entity.Menu;
import com.myfood.entity.Requirements;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto extends Menu {

    private List<Requirements> requirements = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
