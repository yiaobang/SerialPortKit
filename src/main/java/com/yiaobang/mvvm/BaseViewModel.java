package com.yiaobang.mvvm;

public abstract class BaseViewModel<M extends BaseModel> {
    protected final M model;

    protected BaseViewModel(M model) {
        this.model = model;
    }

    public M getModel() {
        return this.model;
    }
}
