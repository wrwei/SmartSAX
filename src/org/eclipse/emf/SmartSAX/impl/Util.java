package org.eclipse.emf.SmartSAX.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class Util {

	public static void initialiseResourceFactoryRegistry() {
		final Map<String, Object> etfm = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		
		if (!etfm.containsKey("*")) {
			etfm.put("*", new XMIResourceFactoryImpl());
		}
	}
	
	protected static void setDataTypesInstanceClasses(Resource metamodel) {
		Iterator<EObject> it = metamodel.getAllContents();
		while (it.hasNext()) {
			EObject eObject = (EObject) it.next();
			if (eObject instanceof EEnum) {
				// ((EEnum) eObject).setInstanceClassName("java.lang.Integer");
			} else if (eObject instanceof EDataType) {
				EDataType eDataType = (EDataType) eObject;
				String instanceClass = "";
				if (eDataType.getName().equals("String")) {
					instanceClass = "java.lang.String";
				} else if (eDataType.getName().equals("Boolean")) {
					instanceClass = "java.lang.Boolean";
				} else if (eDataType.getName().equals("Integer")) {
					instanceClass = "java.lang.Integer";
				} else if (eDataType.getName().equals("Float")) {
					instanceClass = "java.lang.Float";
				} else if (eDataType.getName().equals("Double")) {
					instanceClass = "java.lang.Double";
				}
				if (instanceClass.trim().length() > 0) {
					eDataType.setInstanceClassName(instanceClass);
				}
			}
		}
	}

	
	public static List<EPackage> register(URI uri, EPackage.Registry registry) throws Exception {
		
		List<EPackage> ePackages = new ArrayList<EPackage>();
		
		initialiseResourceFactoryRegistry();

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(),
				EcorePackage.eINSTANCE);
	
		Resource metamodel = resourceSet.createResource(uri);
		metamodel.load(Collections.EMPTY_MAP);
		
		setDataTypesInstanceClasses(metamodel);

		Iterator<EObject> it = metamodel.getAllContents();
		while (it.hasNext()) {
			Object next = it.next();
			if (next instanceof EPackage) {
				EPackage p = (EPackage) next;
				
				if (p.getNsURI() == null || p.getNsURI().trim().length() == 0) {
					if (p.getESuperPackage() == null) {
						p.setNsURI(p.getName());
					}
					else {
						p.setNsURI(p.getESuperPackage().getNsURI() + "/" + p.getName());
					}
				}
				
				if (p.getNsPrefix() == null || p.getNsPrefix().trim().length() == 0) {
					if (p.getESuperPackage() != null) {
						if (p.getESuperPackage().getNsPrefix()!=null) {
							p.setNsPrefix(p.getESuperPackage().getNsPrefix() + "." + p.getName());
						}
						else {
							p.setNsPrefix(p.getName());
						}
					}
				}
				
				if (p.getNsPrefix() == null) p.setNsPrefix(p.getName());
				registry.put(p.getNsURI(), p);
				metamodel.setURI(URI.createURI(p.getNsURI()));
				ePackages.add(p);
			}
		}
		
		return ePackages;
		
	}
	
	public static void collectDependencies(EPackage ePackage, List<EPackage> dependencies) {
		Collection<EObject> crossReferencedElements = EcoreUtil.ExternalCrossReferencer.find(ePackage.eResource()).keySet();
		
		for (Object crossReferencedElement : crossReferencedElements) {
			
			if (crossReferencedElement instanceof EClassifier) {
				EClassifier eClass = (EClassifier) crossReferencedElement;
				EPackage referencedPackage = eClass.getEPackage();
				if (referencedPackage != null) {
					EPackage topEPackage = getTopEPackage(referencedPackage);
					if (!dependencies.contains(topEPackage)) {
						dependencies.add(topEPackage);
						collectDependencies(topEPackage, dependencies);
					}
				}
			}
		}
	}

	public static EPackage getTopEPackage(EPackage ePackage) {
		EPackage top = ePackage;
		while (top.getESuperPackage()!=null) {
			top = top.getESuperPackage();
		}
		return top;
	}


}
