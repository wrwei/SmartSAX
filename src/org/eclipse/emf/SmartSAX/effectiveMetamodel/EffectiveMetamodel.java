package org.eclipse.emf.SmartSAX.effectiveMetamodel;

import java.util.ArrayList;

public class EffectiveMetamodel {

	protected String name;
	protected String NSURI;
	
	protected ArrayList<EffectiveType> allOfKind;
	protected ArrayList<EffectiveType> allOfType;
	protected ArrayList<EffectiveType> types;
	
	public EffectiveMetamodel(String name)
	{
		this.name = name;
		allOfKind = new ArrayList<EffectiveType>();
		allOfType = new ArrayList<EffectiveType>();
		types = new ArrayList<EffectiveType>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNSURI() {
		return NSURI;
	}
	
	public void setNSURI(String nSURI) {
		NSURI = nSURI;
	}
	
	public ArrayList<EffectiveType> getAllOfKind() {
		return allOfKind;
	}
	
	public void addToAllOfKind(String typeName)
	{
		if (!allOfKindContains(typeName)) {
			allOfKind.add(new EffectiveType(typeName));
		}
	}
	
	public boolean allOfKindContains(String typeName)
	{
		for(EffectiveType et: allOfKind)
		{
			if (et.getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<EffectiveType> getAllOfType() {
		return allOfType;
	}
	
	public void addToAllOfType(String typeName)
	{
		if (!allOfTypeContains(typeName)) {
			allOfType.add(new EffectiveType(typeName));
		}
	}
	
	public boolean allOfTypeContains(String typeName)
	{
		for(EffectiveType et: allOfType)
		{
			if (et.getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<EffectiveType> getTypes() {
		return types;
	}
	
	public void addToTypes(String typeName)
	{
		if (!typesContains(typeName)) {
			types.add(new EffectiveType(typeName));
		}
	}
	
	public boolean typesContains(String typeName)
	{
		for(EffectiveType et: types)
		{
			if (et.getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}
	
	public void addAttributeToAllOfKind(String typeName, String attributeName)
	{
		for(EffectiveType et: allOfType)
		{
			if (et.getName().equals(typeName)) {
				et.addAttribute(attributeName);
			}
		}
	}
	
	public void addAttributeToAllOfType(String typeName, String attributeName)
	{
		for(EffectiveType et: allOfType)
		{
			if (et.getName().equals(typeName)) {
				et.addAttribute(attributeName);
			}
		}
	}
	
	public void addAttributeToTypes(String typeName, String attributeName)
	{
		for(EffectiveType et: types)
		{
			if (et.getName().equals(typeName)) {
				et.addAttribute(attributeName);
			}
		}
	}
	
	public void addReferenceToAllOfKind(String typeName, String referenceName)
	{
		for(EffectiveType et: allOfKind)
		{
			if (et.getName().equals(typeName)) {
				et.addReference(referenceName);
			}
		}
	}
	
	public void addReferenceToAllOfType(String typeName, String referenceName)
	{
		for(EffectiveType et: allOfType)
		{
			if (et.getName().equals(typeName)) {
				et.addReference(referenceName);
			}
		}
	}
	
	public void addReferenceToTypes(String typeName, String referenceName)
	{
		for(EffectiveType et: types)
		{
			if (et.getName().equals(typeName)) {
				et.addReference(referenceName);
			}
		}
	}
}
