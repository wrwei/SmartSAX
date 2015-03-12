package org.eclipse.epsilon.labs.smartsax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.epsilon.labs.smartsax.effectivemetamodel.EffectiveMetamodel;

public class SmartSAXXMIResource extends XMIResourceImpl{
	
	public static final String OPTION_EFFECTIVE_METAMODELS = "effective-metamodels";
	public static final String OPTION_RECONCILE = "reconcile";
	public static final String OPTION_LOAD_ALL_ATTRIBUTES = "load-all-attributes";
	
	public boolean loadAllAttributes = true;

	protected HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit = new HashMap<String, HashMap<String,ArrayList<String>>>();
	protected HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad = new HashMap<String, HashMap<String,ArrayList<String>>>();

	protected boolean handleFlatObjects = false;
	protected SmartSAXXMILoadImpl sxl;
	
	public void clearCollections()
	{
		objectsAndRefNamesToVisit.clear();
		objectsAndRefNamesToVisit = null;
		actualObjectsToLoad.clear();
		actualObjectsToLoad = null;
		sxl.clearCollections();
	}
	
	@Override
	public void load(Map<?, ?> options) throws IOException {
		
		List<EffectiveMetamodel> effectiveMetamodels = (List<EffectiveMetamodel>) options.get(OPTION_EFFECTIVE_METAMODELS);
		if (effectiveMetamodels != null) {
			/*
			 EffectiveMetamodelReconciler reconciler = new EffectiveMetamodelReconciler();
			 reconciler.reconcile(effectiveMetamodels, getResourceSet().getPackageRegistry().values());
			 */
		}
		
		super.load(options);
	}
	
	public void setObjectsAndRefNamesToVisit(
			HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit) {
		this.objectsAndRefNamesToVisit = objectsAndRefNamesToVisit;
	}
	
	public void setActualObjectsToLoad(
			HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad) {
		this.actualObjectsToLoad = actualObjectsToLoad;
	}
	
	public SmartSAXXMIResource(URI uri) {
		super(uri);
	}
	
	public void setLoadAllAttributes(boolean loadAllAttributes) {
		this.loadAllAttributes = loadAllAttributes;
	}

	
	
	@Override
	protected XMLLoad createXMLLoad() {
		SmartSAXXMILoadImpl xmiLoadImpl = new SmartSAXXMILoadImpl(createXMLHelper());
		sxl = xmiLoadImpl;
		xmiLoadImpl.setLoadAllAttributes(loadAllAttributes);
		xmiLoadImpl.setObjectsAndRefNamesToVisit(objectsAndRefNamesToVisit);
		xmiLoadImpl.setActualObjectsToLoad(actualObjectsToLoad);
		return xmiLoadImpl; 
	}
	
	@Override
	protected XMLLoad createXMLLoad(Map<?, ?> options) {
		if (options != null && Boolean.TRUE.equals(options.get(OPTION_SUPPRESS_XMI)))
	    {
			SmartSAXXMILoadImpl xmiLoadImpl = new SmartSAXXMILoadImpl(new XMLHelperImpl(this));
			xmiLoadImpl.setLoadAllAttributes(loadAllAttributes);
			xmiLoadImpl.setObjectsAndRefNamesToVisit(objectsAndRefNamesToVisit);
			xmiLoadImpl.setActualObjectsToLoad(actualObjectsToLoad);
			return xmiLoadImpl;
	    }
	    else
	    {
	      return createXMLLoad();
	    }
	}
		
}
