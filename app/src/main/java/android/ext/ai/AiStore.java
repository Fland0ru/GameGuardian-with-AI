package android.ext.ai;

/* Persistent chat sessions (AI-IDE style history). Sessions are stored as JSON files in the
   app's private files dir; the last active session is resumed by default, older ones via the
   history dialog. Nothing ever leaves the device through this class. */
public final class AiStore {

    public static volatile java.lang.String activeId;
    public static volatile java.lang.String pending = "auto";

    private AiStore() {
    }

    static java.io.File dir() {
        java.io.File base = android.ext.Tools.f0().getFilesDir();
        java.io.File d = new java.io.File(base, "ai_sessions");
        if (!d.exists()) {
            d.mkdirs();
        }
        return d;
    }

    public static java.io.File file(java.lang.String strId) {
        return new java.io.File(dir(), strId + ".json");
    }

    static java.lang.String genId() {
        return "s" + java.lang.System.currentTimeMillis() + "-" + java.lang.Integer.toHexString(new java.util.Random().nextInt(65535));
    }

    public static java.lang.String lastId() {
        java.lang.String str = android.ext.Tools.M0().getString("ai-session-last", "");
        return (str == null || str.length() == 0) ? null : str;
    }

    static void setLast(java.lang.String strId) {
        try {
            android.content.SharedPreferences.Editor editor = android.ext.Tools.M0().edit();
            editor.putString("ai-session-last", strId);
            editor.apply();
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store prefs", th);
        }
    }

    public static org.json.JSONObject loadFile(java.lang.String strId) {
        if (strId == null) {
            return null;
        }
        try {
            java.io.File f = file(strId);
            if (!f.exists()) {
                return null;
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(f);
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int iRead = fileInputStream.read(bArr);
                    if (iRead <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, iRead);
                }
            } finally {
                fileInputStream.close();
            }
            return new org.json.JSONObject(new java.lang.String(byteArrayOutputStream.toByteArray(), "UTF-8"));
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store load", th);
            return null;
        }
    }

    public static java.lang.String save(java.lang.String strId, org.json.JSONArray messages) {
        try {
            if (strId == null || strId.length() == 0 || "nil".equals(strId)) {
                strId = genId();
            }
            java.io.File f = file(strId);
            java.lang.String strTitle = "";
            if (f.exists()) {
                org.json.JSONObject jSONObjectOld = loadFile(strId);
                if (jSONObjectOld != null) {
                    strTitle = jSONObjectOld.optString("title", "");
                }
            }
            if (strTitle.length() == 0) {
                strTitle = deriveTitle(messages);
            }
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            jSONObject.put("id", strId);
            jSONObject.put("title", strTitle);
            jSONObject.put("updatedAt", java.lang.System.currentTimeMillis());
            jSONObject.put("messages", messages);
            jSONObject.put("transcript", render(messages));
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(f, false);
            try {
                fileOutputStream.write(jSONObject.toString().getBytes("UTF-8"));
            } finally {
                fileOutputStream.close();
            }
            activeId = strId;
            setLast(strId);
            return strId;
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store save", th);
            return strId;
        }
    }

    public static void delete(java.lang.String strId) {
        if (strId == null) {
            return;
        }
        try {
            file(strId).delete();
        } catch (java.lang.Throwable ignored) {
        }
        if (strId.equals(activeId)) {
            activeId = null;
        }
        if (strId.equals(lastId())) {
            try {
                android.content.SharedPreferences.Editor editor = android.ext.Tools.M0().edit();
                editor.remove("ai-session-last");
                editor.apply();
            } catch (java.lang.Throwable th) {
                android.ext.e1.l("AI store prefs", th);
            }
        }
    }

    public static void rename(java.lang.String strId, java.lang.String strTitle) {
        org.json.JSONObject jSONObject = loadFile(strId);
        if (jSONObject == null) {
            return;
        }
        try {
            jSONObject.put("title", strTitle == null ? "" : strTitle.trim());
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file(strId), false);
            try {
                fileOutputStream.write(jSONObject.toString().getBytes("UTF-8"));
            } finally {
                fileOutputStream.close();
            }
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store rename", th);
        }
    }

    /* each entry: {String id, String title, Long updatedAt}, newest first, max 50 */
    public static java.util.ArrayList<java.lang.Object[]> list() {
        java.util.ArrayList<java.lang.Object[]> out = new java.util.ArrayList<>();
        try {
            java.io.File[] files = dir().listFiles();
            if (files == null) {
                return out;
            }
            java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
                @Override
                public int compare(java.io.File file, java.io.File file2) {
                    return java.lang.Long.compare(file2.lastModified(), file.lastModified());
                }
            });
            int i = 0;
            for (java.io.File f : files) {
                if (i >= 50) {
                    break;
                }
                if (!f.getName().endsWith(".json")) {
                    continue;
                }
                org.json.JSONObject jSONObject = loadFile(f.getName().substring(0, f.getName().length() - 5));
                if (jSONObject == null) {
                    continue;
                }
                out.add(new java.lang.Object[]{jSONObject.optString("id", ""), jSONObject.optString("title", ""), java.lang.Long.valueOf(jSONObject.optLong("updatedAt", 0L))});
                i++;
            }
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store list", th);
        }
        return out;
    }


    /* transcript view of the conversation: You/AI/[tool] lines, system skipped */
    static java.lang.String render(org.json.JSONArray messages) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        try {
            if (messages != null) {
                for (int i = 0; i < messages.length(); i++) {
                    org.json.JSONObject msg = messages.optJSONObject(i);
                    if (msg == null) {
                        continue;
                    }
                    java.lang.String strRole = msg.optString("role", "");
                    java.lang.String strContent = msg.optString("content", "");
                    org.json.JSONArray calls = msg.optJSONArray("tool_calls");
                    if ("user".equals(strRole)) {
                        sb.append("You: ").append(strContent).append("\n\n");
                    } else if ("assistant".equals(strRole)) {
                        if (calls != null) {
                            for (int j = 0; j < calls.length(); j++) {
                                org.json.JSONObject call = calls.optJSONObject(j);
                                if (call == null) {
                                    continue;
                                }
                                org.json.JSONObject fn = call.optJSONObject("function");
                                sb.append("AI: [tool] ").append(fn == null ? "?" : fn.optString("name", "?")).append("\n\n");
                            }
                        }
                        if (strContent.trim().length() > 0) {
                            sb.append("AI: ").append(strContent).append("\n\n");
                        }
                    } else if ("tool".equals(strRole)) {
                        org.json.JSONObject err = msg.optJSONObject("content");
                        if (err != null && err.has("error")) {
                            sb.append("AI: [tool error] ").append(err.optString("error", "")).append("\n\n");
                        }
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store render", th);
        }
        return sb.toString();
    }

    private static java.lang.String deriveTitle(org.json.JSONArray messages) {
        try {
            if (messages != null) {
                for (int i = 0; i < messages.length(); i++) {
                    org.json.JSONObject jSONObject = messages.optJSONObject(i);
                    if (jSONObject == null || !"user".equals(jSONObject.optString("role", ""))) {
                        continue;
                    }
                    java.lang.String strContent = jSONObject.optString("content", "").replace("\n", " ").trim();
                    if (strContent.length() == 0) {
                        continue;
                    }
                    return strContent.length() > 28 ? strContent.substring(0, 28) + "…" : strContent;
                }
            }
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI store title", th);
        }
        return "AI chat";
    }
}
