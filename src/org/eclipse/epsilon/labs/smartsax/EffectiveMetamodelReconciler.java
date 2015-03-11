package org.eclipse.epsilon.labs.smartsax;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.labs.smartsax.effectivemetamodel.EffectiveMetamodel;
import org.eclipse.epsilon.labs.smartsax.effectivemetamodel.EffectiveType;

public class EffectiveMetamodelReconciler {

	protected ArrayList<EffectiveMetamodel> effectiveMetamodels = new ArrayList<EffectiveMetamodel>();
	protected ArrayList<EPackage> packages = new ArrayList<EPackage>();
	
	protected HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit = new HashMap<String, HashMap<String,ArrayList<String>>>();
	protected HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad = new HashMap<String, HashMap<String,ArrayList<String>>>();
	protected ArrayList<EClass> visitedClasses = new ArrayList<EClass>();

	
	public ArrayList<EffectiveMetamodel> getEffectiveMetamodels() {
		return effectiveMetamodels;
	}
	
	public void addEffectiveMetamodel(EffectiveMetamodel effectiveMetamodel)
	{
		effectiveMetamodels.add(effectiveMetamodel);
	}
	
	public void addPackage(EPackage ePackage)
	{
		packages.add(ePackage);
	}
	
	public HashMap<String, HashMap<String, ArrayList<String>>> getObjectsAndRefNamesToVisit() {
		return objectsAndRefNamesToVisit;
	}
	
	public HashMap<String, HashMap<String, ArrayList<String>>> getActualObjectsToLoad() {
		return actualObjectsToLoad;
	}
	
	
	public void reconcile()
	{
		for(EPackage ePackage: packages)
		{
			for(EClassifier eClassifier: ePackage.getEClassifiers())
			{
				if (eClassifier instanceof EClass) {
					if (actualObjectToLoad(ePackage, (EClass) eClassifier)) {
						addActualObjectToLoad((EClass) eClassifier);
					}
					EClass eClass = (EClass) eClassifier;
					visitedClasses.clear();
					visitEClass(eClass);
				}
			}
		}
		
		for(EPackage ePackage: packages)
		{
			for(EClassifier eClassifier: ePackage.getEClassifiers())
			{
				if (eClassifier instanceof EClass) {
					EClass leClass = (EClass) eClassifier;
					if (actualObjectToLoad(ePackage, (EClass) eClassifier)) {
						
						for(EReference eReference: leClass.getEAllReferences())
						{
							if(actualObjectsToLoad.get(ePackage.getName()).get(eClassifier.getName()).contains(eReference.getName()))
							{
								EClass eType = (EClass) eReference.getEType();
								addActualObjectToLoad(eType);
							}
						}
					}
				}
			}
		}
		
		for(EffectiveMetamodel em: effectiveMetamodels)
		{
			for(EffectiveType et: em.getAllOfKind())
			{
				ArrayList<String> features = actualObjectsToLoad.get(em.getName()).get(et.getName());
				features.addAll(et.getAttributes());
				features.addAll(et.getReferences());
			}
			for(EffectiveType et: em.getAllOfType())
			{
				ArrayList<String> features = actualObjectsToLoad.get(em.getName()).get(et.getName());
				features.addAll(et.getAttributes());
				features.addAll(et.getReferences());
			}
		}
	}

	
	public boolean actualObjectToLoad(EPackage ePackage, EClass eClass)
	{
		for(EffectiveMetamodel em: effectiveMetamodels)
		{
			if (em.getName().equalsIgnoreCase(ePackage.getName())) {
				for(EffectiveType et: em.getAllOfKind())
				{
					String elementName = et.getName();
					if (elementName.equals(eClass.getName())) {
						return true;
					}
					
					EClass kind = (EClass) ePackage.getEClassifier(elementName);
					if(eClass.getESuperTypes().contains(kind))
					{
						return true;
					}
				}
				
				for(EffectiveType et: em.getAllOfType())
				{
					String elementName = et.getName();
					if (elementName.equals(eClass.getName())) {
						return true;
					}
				}
			}
		}
		return false;

	}
	
	public void addActualObjectToLoad(EClass eClass)
	{
		//get the epackage name
		String epackage = eClass.getEPackage().getName();
		//get the submap with the epackage name
		HashMap<String, ArrayList<String>> subMap = actualObjectsToLoad.get(epackage);
		//if sub map is null
		if (subMap == null) {
			//create new sub map
			subMap = new HashMap<String, ArrayList<String>>();
			//create new refs for the map
			ArrayList<String> refs = getFeaturesForClassToLoad(eClass);
			
			//add the ref to the sub map
			subMap.put(eClass.getName(), refs);
			//add the sub map to objectsAndRefNamesToVisit
			actualObjectsToLoad.put(epackage, subMap);
		}
		else
		{
			//if sub map is not null, get the refs by class name
			ArrayList<String> refs = subMap.get(eClass.getName());

			//if refs is null, create new refs and add the ref and then add to sub map
			if (refs == null) {
				refs = getFeaturesForClassToLoad(eClass);
				subMap.put(eClass.getName(), refs);
			}
		}
	}
	
	public ArrayList<String> getFeaturesForClassToLoad(EClass eClass)
	{
		//get the package
		EPackage ePackage = eClass.getEPackage();
		//prepare the result
		ArrayList<String> result = new ArrayList<String>();
		
		//for all model containers
		for(EffectiveMetamodel em: effectiveMetamodels)
		{
			//if the container is the container needed
			if (em.getName().equals(ePackage.getName())) {
				//for elements all of kind
				loop1:
				for(EffectiveType et: em.getAllOfKind())
				{
					//if class name equals, add all attributes and references
					if (eClass.getName().equals(et.getName())) {
						result.addAll(et.getAttributes());
						result.addAll(et.getReferences());
						break loop1;
					}
					
					//if eclass is a sub class of the kind, add all attributes and references
					EClass kind = (EClass) ePackage.getEClassifier(et.getName());
					if (eClass.getEAllSuperTypes().contains(kind)) {
						result.addAll(et.getAttributes());
						result.addAll(et.getReferences());
						break loop1;
					}
				}
				
				//for elements all of type
				loop2:
				for(EffectiveType et: em.getAllOfType())
				{
					//if class name equals, add all references and attributes
					if (eClass.getName().equals(et.getName())) {
						result.addAll(et.getAttributes());
						result.addAll(et.getReferences());
						break loop2;
					}
				}
			}
		}
		return result;
	}

	public void visitEClass(EClass eClass)
	{
		//add this class to the visited
		visitedClasses.add(EcoreUtil.copy(eClass));
		
		//if this one is a live class, should addRef()
		if (liveClass(eClass.getEPackage(), eClass.getName())) {
			addRef(eClass, null);
			insertPlaceHolderOjbects(eClass.getEPackage(), eClass);
		}
		
		for(EReference eReference: eClass.getEAllReferences())
		{
			if (!visitedEClass((EClass) eReference.getEType())) {
				visitEClass((EClass) eReference.getEType());
			}
			
			if (liveReference(eReference)) {
				addRef(eClass, eReference);
				insertPlaceHolderOjbects(eClass.getEPackage(), eClass);
			}
		}
		
		for(EClassifier every: eClass.getEPackage().getEClassifiers())
		{
			if (every instanceof EClass) {
				EClass theClass = (EClass) every;
				if (theClass.getEAllSuperTypes().contains(eClass)) {
					for(EReference eReference: theClass.getEAllReferences())
					{
						if (!visitedEClass((EClass) eReference.getEType())) {
							visitEClass((EClass) eReference.getEType());
						}
						
						if (liveReference(eReference)) {
							addRef(theClass, eReference);
							insertPlaceHolderOjbects(theClass.getEPackage(), theClass);
						}
					}
				}
			}
		}
	}

	public boolean liveClass(EPackage ePackage, String className)
	{
		for(EffectiveMetamodel em: effectiveMetamodels)
		{
			//get the package first
			if (em.getName().equalsIgnoreCase(ePackage.getName())) {
				
				//for all of kinds
				for(EffectiveType et: em.getAllOfKind())
				{
					//the element n ame
					String elementName = et.getName();
					//if name equals return true
					if (className.equals(elementName)) {
						return true;
					}
					
					//get the eclass for the mec
					EClass kind = (EClass) ePackage.getEClassifier(elementName);
					//get the eclass for the current class under question
					EClass actual = (EClass) ePackage.getEClassifier(className);
					//if the current class under question is a sub class of the mec, should return true
					if(actual.getEAllSuperTypes().contains(kind))
					{
						return true;
					}
					//if the current class under question is a super class of the mec, should also return true
					if (kind.getEAllSuperTypes().contains(actual)) 
					{
						return true;
					}
				}
				
				for(EffectiveType et: em.getAllOfType())
				{
					//the element n ame
					String elementName = et.getName();
					//if name equals return true
					if (className.equals(elementName)) {
						return true;
					}
					
					//get the eclass for the mec
					EClass type = (EClass) ePackage.getEClassifier(elementName);
					//get the eclass for the class under question
					EClass actual = (EClass) ePackage.getEClassifier(className);
					//if the class under question is a super class of the mec, should return true
					if (type.getEAllSuperTypes() != null && type.getEAllSuperTypes().contains(actual)) 
					{
						return true;
					}

				}
				
				for(EffectiveType et: em.getTypes())
				{
					//if the class under question is an "empty" class that is to be loaded, return true;
					if (et.getName().equals(className)) {
						return true;
					}
					
					//get the eclass for the mec
					EClass type = (EClass) ePackage.getEClassifier(et.getName());
					//get the eclass for the class under question
					EClass actual = (EClass) ePackage.getEClassifier(className);
					//if the class under question is a super class of the mec, should return true
					if (type.getEAllSuperTypes().contains(actual)) 
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addRef(EClass eClass, EReference eReference)
	{
		//get the epackage name
		String epackage = eClass.getEPackage().getName();
		//get the submap with the epackage name
		HashMap<String, ArrayList<String>> subMap = objectsAndRefNamesToVisit.get(epackage);
		//if sub map is null
		if (subMap == null) {
			//create new sub map
			subMap = new HashMap<String, ArrayList<String>>();
			//create new refs for the map
			ArrayList<String> refs = new ArrayList<String>();
			//if eReference is not null
			if (eReference != null) {
				//add the eReference to the ref
				refs.add(eReference.getName());
			}
			//add the ref to the sub map
			subMap.put(eClass.getName(), refs);
			//add the sub map to objectsAndRefNamesToVisit
			objectsAndRefNamesToVisit.put(epackage, subMap);
		}
		else {
			//if sub map is not null, get the refs by class name
			ArrayList<String> refs = subMap.get(eClass.getName());

			//if refs is null, create new refs and add the ref and then add to sub map
			if (refs == null) {
				refs = new ArrayList<String>();
				if(eReference != null)
				{
					refs.add(eReference.getName());
				}
				subMap.put(eClass.getName(), refs);
			}
			//if ref is not null, add the ref
			else {
				if (eReference != null) {
					if (!refs.contains(eReference.getName())) {
						refs.add(eReference.getName());	
					}
				}
			}
		}
	}

	public void insertPlaceHolderOjbects(EPackage ePackage, EClass eClass)
	{
		boolean inserted = false;
		for(EffectiveMetamodel em: effectiveMetamodels)
		{
			if (em.getName().equalsIgnoreCase(ePackage.getName())) {
				for(EffectiveType et: em.getAllOfKind())
				{
					if (et.getName().equals(eClass.getName())) {
						inserted = true;
						return;
					}
					EClass kind = (EClass) ePackage.getEClassifier(et.getName());
					for(EClass superClass: eClass.getEAllSuperTypes())
					{
						if (kind.getName().equals(superClass.getName())) {
							inserted = true;
							return;
						}
					}

				}
				for(EffectiveType et: em.getAllOfType())
				{
					if (et.getName().equals(eClass.getName())) {
						inserted = true;
						return;
					}
				}
				if (!inserted) {
					inserted = true;
					em.addToTypes(eClass.getName());
					break;
				}
			}
		}
		if (!inserted) {
			EffectiveMetamodel newEffectiveMetamodel = new EffectiveMetamodel(ePackage.getName());
			newEffectiveMetamodel.addToTypes(eClass.getName());
			effectiveMetamodels.add(newEffectiveMetamodel);
			
		}
	}

	public boolean visitedEClass(EClass eClass)
	{
		for(EClass clazz: visitedClasses)
		{
			if (clazz.getName().equals(eClass.getName())) {
				return true;
			}
		}
		return false;
	}


	public boolean liveReference(EReference eReference)
	{
		if(eReference.isContainment())
		{
			EClassifier eClassifier = eReference.getEType();
			EClass etype = (EClass) eClassifier;
			if (liveClass(etype.getEPackage(), etype.getName())) {
				return true;
			}
			
			return false;
		}
		return false;
		
	}

}


