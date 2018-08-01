package net.aliveplex.witoong623.myjapanesevocabularies.utils

fun String.isJapanese(): Boolean {
    if (this.isEmpty()) return false
    for (c in this.toCharArray()) {
        val unicode: Character.UnicodeBlock = Character.UnicodeBlock.of(c)
        if (unicode == Character.UnicodeBlock.HIRAGANA || unicode == Character.UnicodeBlock.KATAKANA
                || unicode == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
            continue
        }
        else {
            return false
        }
    }
    return true
}

val String.isAllKana: Boolean
    get() {
        if (this.isEmpty()) return false
        for (c in this.toCharArray()) {
            val unicode = Character.UnicodeBlock.of(c)
            if (unicode == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                return false
            }
        }
        return true
    }

val String.isAllKanji: Boolean
    get() {
        if (this.isEmpty()) return false
        for (c in this.toCharArray()) {
            val unicode = Character.UnicodeBlock.of(c)
            if (unicode == Character.UnicodeBlock.HIRAGANA || unicode == Character.UnicodeBlock.KATAKANA) {
                return false
            }
        }
        return true
    }