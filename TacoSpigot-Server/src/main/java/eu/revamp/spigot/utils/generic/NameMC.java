package eu.revamp.spigot.utils.generic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class NameMC {
    public  static boolean isNameMCVerified(UUID uuid) {
        try {
            String url = "https://api.namemc.com/server/revampmc.eu/likes?profile=" + uuid.toString();
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("true")) {
                    return true;
                }
            }
            br.close();
            return false;
        }
        catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to check if user is nameMC verified. Are you online?");
            return false;
        }
    }
}
