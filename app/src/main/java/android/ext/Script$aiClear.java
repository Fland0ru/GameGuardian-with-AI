package android.ext;

/* gg.aiClear() - clears the AI transcript view (used when restoring a saved session). */
final class Script$aiClear extends android.ext.Script$ApiFunction {

    Script$aiClear() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 0;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        android.ext.n2.i(new android.ext.Script$aiClear$1());
        return luaj.LuaValue.f1673c;
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiClear() -> nil";
    }
}
