package com.myfood.dto;

import com.myfood.entity.Combo;
import com.myfood.entity.ComboItems;
import lombok.Data;
import java.util.List;

@Data
public class ComboDto extends Combo {

    private List<ComboItems> comboItems;

    private String categoryName;
}
