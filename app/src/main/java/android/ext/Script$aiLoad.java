package android.ext;

/* gg.aiLoad() -> table {id, messages, transcript} || nil - restores the session chosen for
   this dialog (the last active one by default, a picked history entry, or nothing for a new
   chat). Runs on the script thread. */
final class Script$aiLoad extends android.ext.Script$ApiFunction {

    Script$aiLoad() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 0;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        java.lang.String strId = android.ext.ai.AiStore.activeId;
        if (strId == null) {
            return luaj.LuaValue.f1673c;
        }
        org.json.JSONObject jSONObject = android.ext.ai.AiStore.loadFile(strId);
        if (jSONObject == null) {
            return luaj.LuaValue.f1673c;
        }
        try {
            luaj.LuaTable luaTable = new luaj.LuaTable();
            luaTable.b2("id", strId);
            luaj.LuaValue luaValueMessages = android.ext.ai.AiJson.luaFromJson(jSONObject.optJSONArray("messages"));
            if (luaValueMessages.l1()) {
                luaValueMessages = new luaj.LuaTable();
            }
            luaTable.c2("messages", luaValueMessages);
            luaTable.b2("transcript", jSONObject.optString("transcript", ""));
            return luaTable;
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("aiLoad", th);
            return luaj.LuaValue.f1673c;
        }
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiLoad() -> table {id, messages, transcript} || nil";
    }
}
