package android.ext.ai;

/* Conversion between Lua values (luaj) and org.json values, used to pass chat messages and tool results. */
public final class AiJson {

    private AiJson() {
    }

    public static java.lang.Object jsonFromLua(luaj.LuaValue luaValue) {
        if (luaValue == null || luaValue.l1()) {
            return null;
        }
        if (luaValue instanceof luaj.LuaString) {
            return luaValue.B0();
        }
        if (luaValue instanceof luaj.LuaDouble) {
            return java.lang.Double.valueOf(luaValue.x2());
        }
        if (luaValue instanceof luaj.LuaBoolean) {
            return java.lang.Boolean.valueOf(luaValue == luaj.LuaValue.f1674d);
        }
        if (luaValue instanceof luaj.LuaNumber) {
            return java.lang.Long.valueOf(luaValue.z2());
        }
        if (luaValue instanceof luaj.LuaTable) {
            luaj.LuaTable luaTable = (luaj.LuaTable) luaValue;
            int iLength = luaTable.W1();
            if (iLength > 0) {
                org.json.JSONArray jSONArray = new org.json.JSONArray();
                for (int i = 1; i <= iLength; i++) {
                    jSONArray.put(jsonFromLua(luaTable.U0(luaj.LuaValue.I2((long) i))));
                }
                return jSONArray;
            }
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            luaj.LuaTable$i luaTable$iS3 = luaTable.s3();
            while (luaTable$iS3.c()) {
                luaj.LuaValue luaValueB = luaTable$iS3.b();
                luaj.LuaValue luaValueD = luaTable$iS3.d();
                java.lang.Object obj = jsonFromLua(luaValueD);
                if (obj != null) {
                    try {
                        jSONObject.put(luaValueB.B0(), obj);
                    } catch (org.json.JSONException e2) {
                        android.ext.e1.s("AI json encode: " + e2, e2);
                    }
                }
            }
            return jSONObject;
        }
        return luaValue.B0();
    }

    public static luaj.LuaValue luaFromJson(java.lang.Object obj) {
        if (obj == null) {
            return luaj.LuaValue.f1673c;
        }
        if (obj instanceof java.lang.String) {
            return luaj.LuaValue.K2((java.lang.String) obj);
        }
        if (obj instanceof java.lang.Boolean) {
            return ((java.lang.Boolean) obj).booleanValue() ? luaj.LuaValue.f1674d : luaj.LuaValue.f1675e;
        }
        if (obj instanceof java.lang.Integer) {
            return luaj.LuaValue.I2((long) ((java.lang.Integer) obj).intValue());
        }
        if (obj instanceof java.lang.Long) {
            return luaj.LuaValue.I2(((java.lang.Long) obj).longValue());
        }
        if (obj instanceof java.lang.Number) {
            return luaj.LuaValue.J2(((java.lang.Number) obj).doubleValue());
        }
        if (obj instanceof org.json.JSONObject) {
            org.json.JSONObject jSONObject = (org.json.JSONObject) obj;
            luaj.LuaTable luaTable = new luaj.LuaTable();
            java.util.Iterator it = jSONObject.keys();
            while (it.hasNext()) {
                java.lang.String strKey = (java.lang.String) it.next();
                java.lang.Object objOpt = jSONObject.opt(strKey);
                luaj.LuaValue luaValueLuaFromJson = luaFromJson(objOpt);
                if (!luaValueLuaFromJson.l1()) {
                    luaTable.c2(strKey, luaValueLuaFromJson);
                }
            }
            return luaTable;
        }
        if (obj instanceof org.json.JSONArray) {
            org.json.JSONArray jSONArray = (org.json.JSONArray) obj;
            luaj.LuaTable luaTable2 = new luaj.LuaTable();
            luaTable2.R1(jSONArray.length());
            for (int i = 0; i < jSONArray.length(); i++) {
                luaTable2.Y1(i + 1, luaFromJson(jSONArray.opt(i)));
            }
            return luaTable2;
        }
        return luaj.LuaValue.K2(obj.toString());
    }

    public static org.json.JSONArray messagesFromLua(luaj.LuaTable luaTable) throws org.json.JSONException {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        int iLength = luaTable.W1();
        for (int i = 1; i <= iLength; i++) {
            luaj.LuaValue luaValueU0 = luaTable.U0(luaj.LuaValue.I2((long) i));
            if (luaValueU0 instanceof luaj.LuaTable) {
                luaj.LuaTable luaTableMsg = (luaj.LuaTable) luaValueU0;
                org.json.JSONObject jSONObject = new org.json.JSONObject();
                luaj.LuaValue luaValueRole = luaTableMsg.U0(luaj.LuaValue.K2("role"));
                if (luaValueRole instanceof luaj.LuaString) {
                    jSONObject.put("role", luaValueRole.B0());
                }
                luaj.LuaValue luaValueContent = luaTableMsg.U0(luaj.LuaValue.K2("content"));
                if (!luaValueContent.l1()) {
                    java.lang.Object obj = jsonFromLua(luaValueContent);
                    if (obj != null) {
                        if (obj instanceof org.json.JSONObject || obj instanceof org.json.JSONArray) {
                            jSONObject.put("content", obj.toString());
                        } else {
                            jSONObject.put("content", obj);
                        }
                    }
                }
                luaj.LuaValue luaValueToolCallId = luaTableMsg.U0(luaj.LuaValue.K2("tool_call_id"));
                if (luaValueToolCallId instanceof luaj.LuaString) {
                    jSONObject.put("tool_call_id", luaValueToolCallId.B0());
                }
                luaj.LuaValue luaValueToolCalls = luaTableMsg.U0(luaj.LuaValue.K2("tool_calls"));
                if (luaValueToolCalls instanceof luaj.LuaTable) {
                    luaj.LuaTable luaTableCalls = (luaj.LuaTable) luaValueToolCalls;
                    org.json.JSONArray jSONArrayCalls = new org.json.JSONArray();
                    int iCalls = luaTableCalls.W1();
                    for (int iCall = 1; iCall <= iCalls; iCall++) {
                        luaj.LuaValue luaValueCall = luaTableCalls.U0(luaj.LuaValue.I2((long) iCall));
                        if (luaValueCall instanceof luaj.LuaTable) {
                            luaj.LuaTable luaTableCall = (luaj.LuaTable) luaValueCall;
                            org.json.JSONObject jSONObjectCall = new org.json.JSONObject();
                            luaj.LuaValue luaValueId = luaTableCall.U0(luaj.LuaValue.K2("id"));
                            if (luaValueId instanceof luaj.LuaString) {
                                jSONObjectCall.put("id", luaValueId.B0());
                            }
                            luaj.LuaValue luaValueType = luaTableCall.U0(luaj.LuaValue.K2("type"));
                            jSONObjectCall.put("type", luaValueType instanceof luaj.LuaString ? luaValueType.B0() : "function");
                            luaj.LuaValue luaValueFunction = luaTableCall.U0(luaj.LuaValue.K2("function"));
                            if (luaValueFunction instanceof luaj.LuaTable) {
                                luaj.LuaTable luaTableFunction = (luaj.LuaTable) luaValueFunction;
                                org.json.JSONObject jSONObjectFunction = new org.json.JSONObject();
                                luaj.LuaValue luaValueName = luaTableFunction.U0(luaj.LuaValue.K2("name"));
                                if (luaValueName instanceof luaj.LuaString) {
                                    jSONObjectFunction.put("name", luaValueName.B0());
                                }
                                luaj.LuaValue luaValueArguments = luaTableFunction.U0(luaj.LuaValue.K2("arguments"));
                                if (luaValueArguments instanceof luaj.LuaString) {
                                    jSONObjectFunction.put("arguments", luaValueArguments.B0());
                                } else if (luaValueArguments instanceof luaj.LuaTable) {
                                    java.lang.Object objArguments = jsonFromLua(luaValueArguments);
                                    jSONObjectFunction.put("arguments", objArguments instanceof org.json.JSONObject ? ((org.json.JSONObject) objArguments).toString() : "{}");
                                } else {
                                    jSONObjectFunction.put("arguments", "{}");
                                }
                                jSONObjectCall.put("function", jSONObjectFunction);
                            }
                            jSONArrayCalls.put(jSONObjectCall);
                        }
                    }
                    jSONObject.put("tool_calls", jSONArrayCalls);
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    public static luaj.LuaTable luaFromResponse(org.json.JSONObject jSONObjectMessage) {
        luaj.LuaTable luaTable = new luaj.LuaTable();
        java.lang.String strContent = jSONObjectMessage.optString("content", "");
        if (strContent != null && strContent.length() > 0) {
            luaTable.b2("content", strContent);
        }
        org.json.JSONArray jSONArrayToolCalls = jSONObjectMessage.optJSONArray("tool_calls");
        if (jSONArrayToolCalls != null && jSONArrayToolCalls.length() > 0) {
            luaj.LuaTable luaTableCalls = new luaj.LuaTable();
            luaTableCalls.R1(jSONArrayToolCalls.length());
            for (int i = 0; i < jSONArrayToolCalls.length(); i++) {
                org.json.JSONObject jSONObjectCall = jSONArrayToolCalls.optJSONObject(i);
                if (jSONObjectCall == null) {
                    continue;
                }
                luaj.LuaTable luaTableCall = new luaj.LuaTable();
                java.lang.String strId = jSONObjectCall.optString("id", "");
                if (strId.length() > 0) {
                    luaTableCall.b2("id", strId);
                }
                java.lang.String strType = jSONObjectCall.optString("type", "function");
                luaTableCall.b2("type", strType);
                org.json.JSONObject jSONObjectFunction = jSONObjectCall.optJSONObject("function");
                if (jSONObjectFunction != null) {
                    luaj.LuaTable luaTableFunction = new luaj.LuaTable();
                    luaTableFunction.b2("name", jSONObjectFunction.optString("name", ""));
                    java.lang.String strArguments = jSONObjectFunction.optString("arguments", "{}");
                    luaj.LuaValue luaValueArguments = luaj.LuaValue.f1673c;
                    try {
                        luaValueArguments = luaFromJson(new org.json.JSONObject(strArguments));
                    } catch (org.json.JSONException e2) {
                        android.ext.e1.s("AI tool arguments parse: " + e2, e2);
                    }
                    if (luaValueArguments.l1()) {
                        luaValueArguments = new luaj.LuaTable();
                    }
                    luaTableFunction.c2("arguments", luaValueArguments);
                    luaTableCall.c2("function", luaTableFunction);
                }
                luaTableCalls.Y1(i + 1, luaTableCall);
            }
            luaTable.c2("tool_calls", luaTableCalls);
        }
        return luaTable;
    }
}
