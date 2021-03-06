package backend.interpreter;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * The class to maintain SLogo command syntax in resource bundles.
 * 
 */
public class ProgramParser {
    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private List<Entry<String, Pattern>> mySymbols;


    /**
     * Constructor of the ProgramParser class.
     */
    public ProgramParser () {
    	clearPatterns();
    }

    /**
     * adds the given resource file to this language's recognized types
     * @param syntax
     */
    public void addPatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        addPatterns(resources);
    }
    
    private void addPatterns(ResourceBundle resources){
    	 Enumeration<String> iter = resources.getKeys();
         while (iter.hasMoreElements()) {
             String key = iter.nextElement();
             String regex = resources.getString(key);
             mySymbols.add(new SimpleEntry<>(key,
                            // THIS IS THE IMPORTANT LINE
                            Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
         }
    }
    
    /**
     * returns the language's type associated with the given text if one exists 
     * @param text
     * @return
     */
    public String getSymbol (String text) {
        final String ERROR = "NO MATCH";
        for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        return ERROR;
    }
 
    /**
     * returns true if the given text matches the given regular expression pattern
     * @param text
     * @param regex
     * @return
     */
    private boolean match (String text, Pattern regex) {
        // THIS IS THE KEY LINE
        return regex.matcher(text).matches();
    }

    /**
     * Clears current stored patterns in the list.
     */
	public void clearPatterns() {
		 mySymbols = new ArrayList<>();	
	}
}
