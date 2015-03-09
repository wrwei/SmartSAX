package org.eclipse.emf.SmartSAX.effectiveMetamodel;

import java.util.ArrayList;

public class EffectiveType {

	protected String name;
	protected ArrayList<String> attributes;
	protected ArrayList<String> references;
	
	public EffectiveType(String name)
	{
		this.name = name;
		attributes = new ArrayList<String>();
		references = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getAttributes() {
		return attributes;
	}
	
	public void addAttribute(String attr)
	{
		attributes.add(attr);
	}
	
	public ArrayList<String> getReferences() {
		return references;
	}
	
	public void addReference(String ref)
	{
		references.add(ref);
	}
	
	public ArrayList<String> getFeatures()
	{
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(attributes);
		result.addAll(references);
		return result;
	}
}
