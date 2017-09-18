/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package xyz.kfdykme.kat.aip.unit.listener;


import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.model.UNITResult;

public interface OnResultListener<T> {
    void onResult(T result);

    void onError(UnitError error);

    void onUnitResult(UNITResult result);
}
