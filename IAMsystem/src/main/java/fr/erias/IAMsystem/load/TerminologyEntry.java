package fr.erias.IAMsystem.load;

public class TerminologyEntry {
	
	private String term;
	private String typeTerm;
	private String id;
	
	
	public TerminologyEntry(String term,String typeTerm, String id) {
		super();
		this.term = term;
		this.setTypeTerm(typeTerm);
		this.id = id;
	}
	
	public TerminologyEntry(String term, String id) {
		super();
		this.term = term;
		this.setTypeTerm("Unknown");
		this.id = id;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getTypeTerm() {
		return typeTerm;
	}

	public void setTypeTerm(String typeTerm) {
		this.typeTerm = typeTerm;
	}

}
