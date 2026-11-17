package android.ext.ai;

/* OpenAI-compatible chat completions client. Runs on the caller's (script) thread, like gg.makeRequest. */
public final class AiClient {

    private AiClient() {
    }

    public static org.json.JSONObject chat(org.json.JSONArray messages) throws java.io.IOException, org.json.JSONException {
        java.lang.String strBase = android.ext.ai.AiConfig.url();
        if (strBase == null || strBase.trim().length() == 0) {
            throw new java.io.IOException("AI endpoint is not configured");
        }
        strBase = strBase.trim();
        while (strBase.endsWith("/")) {
            strBase = strBase.substring(0, strBase.length() - 1);
        }
        java.lang.String strEndpoint = strBase.endsWith("/chat/completions") ? strBase : strBase + "/chat/completions";
        org.json.JSONObject jSONObjectBody = new org.json.JSONObject();
        jSONObjectBody.put("model", android.ext.ai.AiConfig.model());
        jSONObjectBody.put("messages", messages);
        jSONObjectBody.put("tools", new org.json.JSONArray(android.ext.ai.AiTools.TOOLS_JSON));
        jSONObjectBody.put("tool_choice", "auto");
        byte[] bArr = jSONObjectBody.toString().getBytes("UTF-8");
        java.net.HttpURLConnection httpURLConnection = (java.net.HttpURLConnection) new java.net.URL(strEndpoint).openConnection();
        try {
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(180000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.addRequestProperty("Content-Type", "application/json");
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.addRequestProperty("Content-Length", java.lang.Integer.toString(bArr.length));
            java.lang.String strKey = android.ext.ai.AiConfig.key();
            if (strKey != null && strKey.length() > 0) {
                httpURLConnection.addRequestProperty("Authorization", "Bearer " + strKey);
            }
            httpURLConnection.setFixedLengthStreamingMode(bArr.length);
            httpURLConnection.connect();
            java.io.OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                outputStream.write(bArr);
            } finally {
                outputStream.close();
            }
            int iCode = httpURLConnection.getResponseCode();
            java.io.InputStream inputStream = iCode >= 400 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
            if (inputStream == null) {
                throw new java.io.IOException("HTTP " + iCode + " " + httpURLConnection.getResponseMessage());
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byte[] bArrBuf = new byte[8192];
            long j = 0;
            try {
                while (true) {
                    int iRead = inputStream.read(bArrBuf);
                    if (iRead == -1) {
                        break;
                    }
                    j += (long) iRead;
                    if (j > 16777216) {
                        throw new java.io.IOException("response body exceeds the 16 MB limit");
                    }
                    byteArrayOutputStream.write(bArrBuf, 0, iRead);
                }
            } finally {
                inputStream.close();
            }
            org.json.JSONObject jSONObjectResponse = null;
            java.lang.String strParseProblem = null;
            try {
                jSONObjectResponse = new org.json.JSONObject(new java.lang.String(byteArrayOutputStream.toByteArray(), "UTF-8"));
            } catch (org.json.JSONException e3) {
                strParseProblem = e3.getMessage();
            }
            if (jSONObjectResponse == null) {
                java.lang.String strPreview = new java.lang.String(byteArrayOutputStream.toByteArray(), "UTF-8").replaceAll("\\s+", " ");
                if (strPreview.length() > 160) {
                    strPreview = strPreview.substring(0, 160) + "…";
                }
                throw new java.io.IOException("HTTP " + iCode + ": not JSON (is the base URL correct?). Body: " + strPreview + (strParseProblem != null ? " — " + strParseProblem : ""));
            }
            if (jSONObjectResponse.has("error")) {
                java.lang.Object objError = jSONObjectResponse.get("error");
                java.lang.String strMessage = objError instanceof org.json.JSONObject ? ((org.json.JSONObject) objError).optString("message", "") : objError.toString();
                throw new java.io.IOException("API error: " + strMessage);
            }
            org.json.JSONArray jSONArrayChoices = jSONObjectResponse.optJSONArray("choices");
            if (jSONArrayChoices == null || jSONArrayChoices.length() == 0) {
                throw new java.io.IOException("no choices in the response");
            }
            org.json.JSONObject jSONObjectMessage = jSONArrayChoices.optJSONObject(0) == null ? null : jSONArrayChoices.optJSONObject(0).optJSONObject("message");
            if (jSONObjectMessage == null) {
                throw new java.io.IOException("no message in the response");
            }
            return jSONObjectMessage;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
