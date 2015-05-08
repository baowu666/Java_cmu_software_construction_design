package edu.cmu.cs.cs214.hw6.plugin.wordprefix;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import edu.cmu.cs.cs214.hw6.Emitter;
import edu.cmu.cs.cs214.hw6.ReduceTask;

/**
 * The reduce task for a word-prefix map/reduce computation.
 */
public class WordPrefixReduceTask implements ReduceTask {
    private static final long serialVersionUID = 6763871961687287020L;

    @Override
    public void execute(String key, Iterator<String> values, Emitter emitter) throws IOException {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        while (values.hasNext()) {
        	String value = values.next();
        	if (map.containsKey(value))
        		map.put(value, map.get(value) + 1);
        	else
        		map.put(value, 1);
        }
        String maxFreq = map.descendingMap().firstKey();
        emitter.emit(key, maxFreq);
    }

}
