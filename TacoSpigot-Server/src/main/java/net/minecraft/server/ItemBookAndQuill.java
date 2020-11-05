/*package net.minecraft.server;


public class ItemBookAndQuill extends Item {
    public ItemBookAndQuill() {
        c(1);
    }

    public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
        var3.openBook(var1);
        var3.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return var1;
    }

    private static final String[] MOJANG_CRASH_TRANSLATIONS = new String[] { "translation.test.invalid", "translation.test.invalid2" };
*/
    //TODO OLD CODE
    /*
    public static boolean b(NBTTagCompound var0) {
        if (var0 == null)
            return false;
        if (!var0.hasKeyOfType("pages", 9))
            return false;
        long byteTotal = 0L;
        int maxBookPageSize = RevampSpigot.getInstance().getBookMaxPageBytesAllowed();
        double multiplier = Math.max(0.3D, Math.min(1.0D, RevampSpigot.getInstance().getBookMaxPageBytesTotalSizeMultiplier()));
        long byteAllowed = maxBookPageSize;
        NBTTagList pages = var0.getList("pages", 8);
        if (pages.size() > Settings.IMP.SETTINGS.BOOK.MAX_PAGES_SIZE)
            return false;
        for (int var2 = 0; var2 < pages.size(); var2++) {
            String page = pages.getString(var2);
            if (page == null || page.equals("null"))
                return false;
            if (page.length() > Settings.IMP.SETTINGS.BOOK.MAX_PAGE_CONTENT_LENGTH) {
                RevampSpigot.getInstance().getLogger().warning("Detected too big page content! " + page.length());
                return false;
            }
            int byteLength = (page.getBytes(StandardCharsets.UTF_8)).length;
            byteTotal += byteLength;
            int length = page.length();
            int multibytes = 0;
            if (byteLength != length)
                for (char c : page.toCharArray()) {
                    if (c > '')
                        multibytes++;
                }
            byteAllowed = (long)(byteAllowed + maxBookPageSize * Math.min(1.0D, Math.max(0.1D, length / 255.0D)) * multiplier);
            if (multibytes > 1)
                byteAllowed -= multibytes;
            if (byteTotal > byteAllowed) {
                RevampSpigot.getInstance().getLogger().info("Tried to create too large of a book. Book Size: " + byteTotal + " - Allowed:  " + byteAllowed + " - Pages: " + pages
                        .size());
                return false;
            }
            String noSpaces = page.replace(" ", "");
            if (noSpaces.startsWith("{\"translate\""))
                for (String crashTranslation : MOJANG_CRASH_TRANSLATIONS) {
                    String translationJson = String.format("{\"translate\":\"%s\"}", crashTranslation);
                    if (page.equalsIgnoreCase(translationJson))
                        return false;
                }
        }
        return true;
    }*/
    //TODO OLD CODE
/*
    //TODO ADDED CODE
    public static boolean b(NBTTagCompound var0) {
        if (var0 == null)
            return false;
        if (!var0.hasKeyOfType("pages", 9))
            return false;
        long byteTotal = 0L;
        int maxBookPageSize = Settings.IMP.SETTINGS.BOOK.MAX_PAGE_BYTES_ALLOWED;
        double multiplier = Math.max(0.3D, Math.min(1.0D,
                Settings.IMP.SETTINGS.BOOK.MAX_PAGE_BYTES_TOTAL_SIZE_MULTIPLIER));
        long byteAllowed = maxBookPageSize;
        NBTTagList pages = var0.getList("pages", 8);
        if (pages.size() > Settings.IMP.SETTINGS.BOOK.MAX_PAGES_SIZE)
            return false;
        for (int var2 = 0; var2 < pages.size(); var2++) {
            String page = pages.getString(var2);
            if (page == null || page.equals("null"))
                return false;
            if (page.length() > Settings.IMP.SETTINGS.BOOK.MAX_PAGE_CONTENT_LENGTH) {
                RevampSpigot.getInstance().getLogger()
                        .warning("Detected too big page content! " + page.length());
                return false;
            }
            int byteLength = (page.getBytes(StandardCharsets.UTF_8)).length;
            byteTotal += byteLength;
            int length = page.length();
            int multibytes = 0;
            if (byteLength != length) {
                char[] arrayOfChar;
                int i;
                byte b;
                for (i = (arrayOfChar = page.toCharArray()).length, b = 0; b < i; ) {
                    char c = arrayOfChar[b];
                    if (c > '')
                        multibytes++;
                    b++;
                }
            }
            byteAllowed = (long)(byteAllowed + maxBookPageSize * Math.min(1.0D, Math.max(0.1D, length / 255.0D)) * multiplier);
            if (multibytes > 1)
                byteAllowed -= multibytes;
            if (byteTotal > byteAllowed) {
                RevampSpigot.getInstance().getLogger().info("Tried to create too large of a book. Book Size: " + byteTotal +
                        " - Allowed:  " + byteAllowed + " - Pages: " + pages.size());
                return false;
            }
            String noSpaces = page.replace(" ", "");
            if (noSpaces.startsWith("{\"translate\"")) {
                int i;
                byte b;
                String[] arrayOfString;
                for (i = (arrayOfString = MOJANG_CRASH_TRANSLATIONS).length, b = 0; b < i; ) {
                    String crashTranslation = arrayOfString[b];
                    String translationJson = String.format("{\"translate\":\"%s\"}", crashTranslation);
                    if (page.equalsIgnoreCase(translationJson))
                        return false;
                    b++;
                }
            }
        }
        return true;
    }
    //TODO ADDED CODE

    public static boolean isValid(NBTTagCompound var0) {
        if (var0 == null)
            return false;
        if (var0.hasKeyOfType("pages", 9)) {
            NBTTagList pages = var0.getList("pages", 8);
            if (pages.size() > Settings.IMP.SETTINGS.BOOK.MAX_PAGES_SIZE)
                return false;
            for (int var2 = 0; var2 < pages.size(); var2++) {
                String page = pages.getString(var2);
                if (page == null || page.equals("null"))
                    return false;
                if (page.length() > Settings.IMP.SETTINGS.BOOK.MAX_PAGE_CONTENT_LENGTH) {
                    RevampSpigot.getInstance().getLogger().warning("Detected too big page content! " + page.length());
                    return false;
                }
                String noSpaces = page.replace(" ", "");
                if (noSpaces.startsWith("{\"translate\"")) {
                    String[] arrayOfString;
                    int i;
                    byte b;
                    for (i = (arrayOfString = MOJANG_CRASH_TRANSLATIONS).length, b = 0; b < i; ) {
                        String crashTranslation = arrayOfString[b];
                        String translationJson = String.format("{\"translate\":\"%s\"}", crashTranslation);
                        if (page.equalsIgnoreCase(translationJson))
                            return false;
                        b++;
                    }
                }
            }
        }
        return true;
    }
}

*/
package net.minecraft.server;

public class ItemBookAndQuill extends Item {
    public ItemBookAndQuill() {
        this.c(1);
    }

    public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
        var3.openBook(var1);
        var3.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return var1;
    }

    public static boolean b(NBTTagCompound var0) {
        if (var0 == null) {
            return false;
        } else if (!var0.hasKeyOfType("pages", 9)) {
            return false;
        } else {
            NBTTagList var1 = var0.getList("pages", 8);

            for(int var2 = 0; var2 < var1.size(); ++var2) {
                String var3 = var1.getString(var2);
                if (var3 == null) {
                    return false;
                }

                if (var3.length() > 32767) {
                    return false;
                }
            }

            return true;
        }
    }
}
