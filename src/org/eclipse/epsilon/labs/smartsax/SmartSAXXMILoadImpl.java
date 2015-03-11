package org.eclipse.epsilon.labs.smartsax;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.xml.sax.helpers.DefaultHandler;

public class SmartSAXXMILoadImpl extends XMILoadImpl{
	public boolean loadAllAttributes = true;
	
//	protected ArrayList<ModelContainer> modelContainers = new ArrayList<ModelContainer>(); // <-------------------- point of change
//
//	public void addModelContainer(ModelContainer modelContainer) // <-------------------- point of change
//	{
//		modelContainers.add(modelContainer);
//	}
//	
//	public void setModelContainers(ArrayList<ModelContainer> modelContainers) { // <-------------------- point of change
//		this.modelContainers = modelContainers;
//	}
	
//	protected HashMap<String, ArrayList<String>> objectsToLoad = new HashMap<String, ArrayList<String>>();
//	protected HashMap<String, ArrayList<String>> emptyObjectsToLoad = new HashMap<String, ArrayList<String>>();
	protected HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit = new HashMap<String, HashMap<String,ArrayList<String>>>();
	protected HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad = new HashMap<String, HashMap<String,ArrayList<String>>>();
	
	public void clearCollections()
	{
		objectsAndRefNamesToVisit.clear();
		objectsAndRefNamesToVisit = null;
		actualObjectsToLoad.clear();
		actualObjectsToLoad = null;
	}
	
	
//	public void setObjectsToLoad(
//			HashMap<String, ArrayList<String>> objectsToLoad) {
//		this.objectsToLoad = objectsToLoad;
//	}
//	
//	public void setEmptyObjectsToLoad(
//			HashMap<String, ArrayList<String>> emptyObjectsToLoad) {
//		this.emptyObjectsToLoad = emptyObjectsToLoad;
//	}
	
	public void setObjectsAndRefNamesToVisit(
			HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit) {
		this.objectsAndRefNamesToVisit = objectsAndRefNamesToVisit;
	}

	public void setActualObjectsToLoad(
			HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad) {
		this.actualObjectsToLoad = actualObjectsToLoad;
	}
	
	public SmartSAXXMILoadImpl(XMLHelper helper) {
		super(helper);
		// TODO Auto-generated constructor stub
	}
	
	public void setLoadAllAttributes(boolean loadAllAttributes) {
		this.loadAllAttributes = loadAllAttributes;
	}
	
	@Override
	protected DefaultHandler makeDefaultHandler() {
		SmartSAXXMIHandler handler = new SmartSAXXMIHandler(resource, helper, options); // <-------------------- point of change
		handler.setLoadAllAttributes(loadAllAttributes);
		//handler.setModelContainers(modelContainers); // <-------------------- point of change
//		handler.setObjectsToLoad(objectsToLoad);
//		handler.setEmptyObjectsToLoad(emptyObjectsToLoad);
		handler.setObjectsAndRefNamesToVisit(objectsAndRefNamesToVisit);
		handler.setActualObjectsToLoad(actualObjectsToLoad);
		return handler; // <-------------------- point of change
		
	}

}
