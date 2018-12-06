package fr.erias.IAMsystem.ct;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;

/**
 * Just a {@link CT} with a code/uri associated
 * @author Cossin Sebastien
 *
 */
public class CTcode extends CT {

	private String code ;
	private HashSet<String> codes ;
	
	/**
	 * Create a new candidateTerm with a code associated to it
	 * @param candidateTermString The candidate term string as it is in the sentence
	 * @param candidateTokensArray An array containing each token of candidateTermString
	 * @param startPosition The start position of this candidate term in the sentence
	 * @param endPosition The end position of this candidate term in the sentence
	 * @param code the candidateTerm comes from a terminology, it must have a code or uri
	 */
	public CTcode(String candidateTermString, String[] candidateTokensArray, 
			int startPosition, int endPosition, String code) {
		super(candidateTermString, candidateTokensArray, startPosition, endPosition);
		this.code = code;
		this.codes = new HashSet<String>();
	}
	
	public CTcode(String candidateTermString, String[] candidateTokensArray, 
			int startPosition, int endPosition, HashSet<String> codes) {
		super(candidateTermString, candidateTokensArray, startPosition, endPosition);
		this.code = "";
		this.codes = codes;
	}
	
	/**
	 * Create a new candidateTerm with a code associated to it
	 * @param candidateTerm A {@link CT}
	 * @param code The candidateTerm comes from a terminology, it must have a code or uri
	 */
	
	public CTcode(CT candidateTerm, String code) {
		super(candidateTerm);
		this.code = code;
		this.codes =new HashSet<String>();
	}
	
	public CTcode(CT candidateTerm, HashSet<String> codes) {
		super(candidateTerm);
		this.code = "";
		this.codes = codes;
	}
	
	
	
	/**
	 * Get the code of the {@link CT}
	 * @return the code / uri associated to this candidateTerm in a terminology
	 */
	public String  getCode() {
		return(code);
	}
	
	public HashSet<String>  getCodes() {
		return(codes);
	}
	
	/**
	 * Export a candidateTerm to a JsonObject
	 * @return a JSON object representing this candidateTerm
	 */
	public JSONObject getJSONobject() {
		JSONObject json = new JSONObject();
		json.put("term", getCandidateTermString());
		json.put("normalizedTerm", getCandidateTerm());
		json.put("start", getStartPosition());
		json.put("end", getEndPosition());
		json.put("code", getCode());
		return(json);
	}
}
