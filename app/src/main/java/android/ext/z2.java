package android.ext;

/* JADX INFO: loaded from: classes.dex */
public abstract class z2 {

    /* Network access removed: usage stats upload to gameguardian.net disabled. */
    static boolean b(byte[] bArr) {
        android.ext.e1.d("UsageStats send disabled");
        return true;
    }

    public static boolean c(java.lang.String str) {
        return d(str, false);
    }

    public static boolean d(java.lang.String str, boolean z) {
        if (z) {
            return b(str.getBytes());
        }
        android.ext.e1.d("NLV - log not sended");
        return true;
    }
}
