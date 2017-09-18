/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package xyz.kfdykme.kat.aip.unit.parser;

import org.json.JSONException;
import org.json.JSONObject;



import android.util.Log;

import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.aip.unit.model.ResponseResult;

public class DefaultParser implements Parser<ResponseResult> {

    @Override
    public ResponseResult parse(String json) throws UnitError {
        Log.e("xx", "DefaultParser:" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has("error_code")) {
                UnitError error = new UnitError(jsonObject.optInt("error_code"), jsonObject.optString("error_msg"));
                throw error;
            }

            ResponseResult result = new ResponseResult();
            result.setLogId(jsonObject.optLong("log_id"));
            result.setJsonRes(json);

            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            UnitError error = new UnitError(UnitError.ErrorCode.JSON_PARSE_ERROR, "Json parse error:" + json, e);
            throw error;
        }
    }
}
