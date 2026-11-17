package android.ext;

/* gg.aiShow - appends text to the AI chat transcript. Runs the update on the UI thread. */
final class Script$aiShow extends android.ext.Script$ApiFunction {

    Script$aiShow() {
    }

    @Override // android.ext.Script$ApiFunction
    protected int Y2() {
        return 1;
    }

    @Override // android.ext.Script$ApiFunction
    public luaj.Varargs b3(luaj.Varargs varargs) {
        final java.lang.String strText = varargs.E(1);
        android.ext.n2.i(new android.ext.Script$aiShow$1(strText));
        return luaj.LuaValue.f1673c;
    }

    @Override // android.ext.Script$ApiFunction
    java.lang.String k3() {
        return "gg.aiShow(string text) -> nil";
    }
}
