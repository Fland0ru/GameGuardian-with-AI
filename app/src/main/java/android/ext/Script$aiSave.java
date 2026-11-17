package android.ext;

/* gg.aiSave(id, messages) -> id - persists the conversation so it survives closing the
   dialog and restarting the app. The transcript is derived from the messages on the Java
   side. Returns the session id (generating one for a new chat). Runs on the script thread. */
final class Script$aiSave extends android.ext.Script$ApiFunction {

    Script$aiSave() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 2;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        java.lang.String strId = varargs.E(1);
        if (strId == null) {
            strId = "";
        }
        try {
            luaj.LuaTable luaTable = varargs.c0(2, null);
            if (luaTable == null) {
                return luaj.LuaValue.K2(strId);
            }
            org.json.JSONArray messages = android.ext.ai.AiJson.messagesFromLua(luaTable);
            java.lang.String strSaved = android.ext.ai.AiStore.save(strId, messages);
            return luaj.LuaValue.K2(strSaved == null ? strId : strSaved);
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("aiSave", th);
            return luaj.LuaValue.K2(strId);
        }
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiSave(id, table messages) -> string id";
    }
}
