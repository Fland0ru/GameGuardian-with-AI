package android.ext;

/* gg.aiWaitUser - blocks the script thread until the user sends a message in the AI dialog.
   Returns the message text, or nil when the dialog was closed. */
final class Script$aiWaitUser extends android.ext.Script$ApiFunction {

    Script$aiWaitUser() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 0;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        java.lang.String str = android.ext.ai.AiController.awaitUser();
        return str == null ? luaj.LuaValue.f1673c : luaj.LuaValue.K2(str);
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiWaitUser() -> string || nil";
    }
}
