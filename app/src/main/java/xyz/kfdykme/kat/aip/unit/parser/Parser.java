/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package xyz.kfdykme.kat.aip.unit.parser;


import xyz.kfdykme.kat.aip.unit.exception.UnitError;

/**
 * JSON解析
 * @param <T>
 */
public interface Parser<T> {
    T parse(String json) throws UnitError, UnitError, UnitError;
}
