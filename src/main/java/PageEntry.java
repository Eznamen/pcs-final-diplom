import java.io.File;

import java.util.Map;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(File pdf, int page, Map.Entry<String, Integer> entry) {
        this.pdfName = pdf.getName();
        this.page = page;
        this.count = entry.getValue();
    }

    @Override
    public String toString() {
        return "\n {" + " \n" +
                "   \"pdfName\": \"" + pdfName + "\", \n" +
                "   \"page\": " + page + ", \n" +
                "   \"count\": " + count + " \n" +
                " }";
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        return this.count >= pageEntry.count ? -1 : 0;
    }
}