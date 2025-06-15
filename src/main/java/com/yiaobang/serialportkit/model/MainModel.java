package com.yiaobang.serialportkit.model;

import com.yiaobang.mvvm.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainModel extends BaseModel {
    public static final String dark = "/atlantafx/base/theme/cupertino-dark.css";
    public static final String light = "/atlantafx/base/theme/cupertino-light.css";

    private boolean darkTheme = false;
    private boolean top = false;

}
