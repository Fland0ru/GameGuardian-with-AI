package android.ext;

/* JADX INFO: loaded from: classes.dex */
class ConfigListAdapter$a extends android.ext.g0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final android.ext.ConfigListAdapter f54a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConfigListAdapter$a(android.ext.ConfigListAdapter configListAdapter, java.lang.String str) {
        super(str);
        this.f54a = configListAdapter;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    /* Network access removed: remote changelog fetch disabled, offline text shown instead. */
    public void run() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.change_log));
        sb.append(": offline build - no network access.");
        android.ext.n2.i(new android.ext.ConfigListAdapter$a$a(this, sb));
    }
}
