package fr.erias.IAMsystem.normalizer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.IAMsystem.exceptions.InvalidCSV;
import fr.erias.IAMsystem.exceptions.ProcessSentenceException;
import fr.erias.IAMsystem.load.Loader;
import fr.erias.IAMsystem.load.TerminologyEntry;
import fr.erias.IAMsystem.tokenizer.TokenizerNormalizer;

/**
 * An instance to normalize a terminology in a CSV format
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class CSVlineHandlerImpl implements CSVlineHandler {

	final static Logger logger = LoggerFactory.getLogger(CSVlineHandlerImpl.class);
	
	private TokenizerNormalizer tokenizerNormalizer;
	
	private short positionOfLabelInColumn;
	private short positionOfCodeInColumn;
	
	private TerminologyEntry terminologyEntry;
	
	private String sep;
	
	private String normalizedLine ;
	
	/**
	 * Create an instance to normalize a terminology in a CSV format
	 * @param stopwords An instance of {@link Stopwords}
	 * @param sep CSV column separator
	 * @param positionOfLabelInColumn the position of the column containing the label to normalise
	 * @throws IOException 
	 */
	public CSVlineHandlerImpl (Stopwords stopwords, String sep, short positionOfLabelInColumn) throws IOException {
		this.sep = sep;
		this.positionOfLabelInColumn = positionOfLabelInColumn;
		this.positionOfCodeInColumn=-1;
		this.tokenizerNormalizer = Loader.getTokenizerNormalizer(stopwords);
		
	}
	
	/**
	 * Create an instance to normalize a terminology in a CSV format
	 * @param stopwords An instance of {@link Stopwords}
	 * @param sep CSV column separator
	 * @param positionOfLabelInColumn the position of the column containing the label to normalise
	 * @throws IOException 
	 */
	public CSVlineHandlerImpl (Stopwords stopwords, String sep, short positionOfLabelInColumn,short positionOfCodeInColumn) throws IOException {
		this.sep = sep;
		this.positionOfLabelInColumn = positionOfLabelInColumn;
		this.positionOfCodeInColumn = positionOfCodeInColumn;
		this.tokenizerNormalizer = Loader.getTokenizerNormalizer(stopwords);
	}
	
	
	public void setPositionOfCodeInColumn(short positionOfCodeInColumn) {
		this.positionOfCodeInColumn = positionOfCodeInColumn;
	}

	/**
	 * See the interface
	 */
	public void processLine(String line) throws InvalidCSV, ProcessSentenceException {
		String[] columns = line.split(this.sep);
		if (columns.length < positionOfLabelInColumn |  columns.length < positionOfCodeInColumn ) {
			throw new InvalidCSV(logger,"Unexpected number of columns at line " + line);
		}
		
		// the term to process
		String label = columns[positionOfLabelInColumn];
		
		// remove first and last quote
		label = label.replaceAll("^\"", "");
		label = label.replaceAll("\"$", "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		
		// normalizedTerm :
		tokenizerNormalizer.tokenizeWithoutEndStart(label);
		String normalizedTerm = tokenizerNormalizer.getNormalizerTerm().getNormalizedSentence();
		if (normalizedTerm.equals("")) {
			normalizedTerm = "nothingRemains";
			logger.info(label + " \t is a stopword - nothing remains of this label");
		}
		sb.append(sep);
		sb.append(normalizedTerm);
		sb.append("\n");
		this.normalizedLine = sb.toString();
		if(positionOfCodeInColumn != -1) {
			this.terminologyEntry = new TerminologyEntry(normalizedTerm, columns[positionOfCodeInColumn]);
		}
	}
	
	/**
	 * See the interface
	 */
	public String getNormalizedLine() {
		return(this.normalizedLine);
	}

	public TerminologyEntry getTerminologyEntry() {
		return terminologyEntry;
	}
}
