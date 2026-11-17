package android.ext;

/* gg.aiRequest - calls the configured OpenAI-compatible chat endpoint with the conversation
   and tool definitions. Runs on the script thread, like gg.makeRequest. */
final class Script$aiRequest extends android.ext.Script$ApiFunction {

    Script$aiRequest() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 1;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        try {
            if (!android.ext.ai.AiConfig.configured()) {
                return luaj.LuaValue.K2("AI is not configured. Open AI settings and fill in the endpoint URL and model name.");
            }
            luaj.LuaTable luaTable = varargs.c0(1, null);
            if (luaTable == null) {
                return luaj.LuaValue.K2("aiRequest: table of messages expected");
            }
            org.json.JSONArray messages = android.ext.ai.AiJson.messagesFromLua(luaTable);
            org.json.JSONObject message = android.ext.ai.AiClient.chat(messages);
            luaj.LuaTable resp = android.ext.ai.AiJson.luaFromResponse(message);
            resp.b2("role", "assistant");
            return resp;
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("aiRequest", th);
            return luaj.LuaValue.K2(android.ext.Script.V3(th));
        }
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiRequest(table messages) -> table || string with error";
    }
}
