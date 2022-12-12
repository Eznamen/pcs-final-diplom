import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private File pdf;
    private Map<String, List<PageEntry>> result;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        this.result = new HashMap<>();
        if (pdfsDir.isDirectory()) {
            for (File item : pdfsDir.listFiles()) {
                var doc = new PdfDocument(new PdfReader(item));
                int sizePage = doc.getNumberOfPages();
                for (int i = 1; i < (sizePage + 1); i++) {
                    PdfPage page = doc.getPage(i);
                    var text = PdfTextExtractor.getTextFromPage(page);
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> mapWords = new HashMap<>();
                    for (String word : words) {
                        word = word.toLowerCase();
                        Integer value = mapWords.get(word) == null ? 1 : mapWords.containsKey(word) ? (mapWords.get(word) + 1) : 1;
                        mapWords.put(word, value);
                    }
                    for (Map.Entry<String, Integer> entry : mapWords.entrySet()) {
                        List<PageEntry> pageEntryList = new ArrayList<>();
                        String key = entry.getKey();
                        PageEntry pageEntry = new PageEntry(item, i, entry);
                        pageEntryList = result.get(key) == null ? pageEntryList : result.containsKey(key) ? result.get(key) : pageEntryList;
                        pageEntryList.add(pageEntry);
                        result.put(key, pageEntryList);
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> pageEntryList = this.result.getOrDefault(word, Collections.emptyList());
        Collections.sort(pageEntryList);
        return pageEntryList;
    }
}
