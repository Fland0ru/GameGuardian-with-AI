package android.ext.ai;

/* AI provider configuration (OpenAI-compatible endpoint), persisted in the app's shared preferences. */
public final class AiConfig {

    private AiConfig() {
    }

    public static java.lang.String url() {
        return android.ext.Tools.M0().getString("ai-url", "");
    }

    public static java.lang.String key() {
        return android.ext.Tools.M0().getString("ai-key", "");
    }

    public static java.lang.String model() {
        return android.ext.Tools.M0().getString("ai-model", "");
    }

    public static boolean configured() {
        java.lang.String strUrl = url();
        return strUrl != null && strUrl.trim().length() > 0 && model() != null && model().trim().length() > 0;
    }

    public static void save(java.lang.String strUrl, java.lang.String strKey, java.lang.String strModel) {
        android.content.SharedPreferences.Editor editor = android.ext.Tools.M0().edit();
        editor.putString("ai-url", strUrl == null ? "" : strUrl.trim());
        editor.putString("ai-key", strKey == null ? "" : strKey.trim());
        editor.putString("ai-model", strModel == null ? "" : strModel.trim());
        editor.apply();
    }
}
