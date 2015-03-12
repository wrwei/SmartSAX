package org.eclipse.epsilon.labs.smartsax;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.labs.smartsax.effectivemetamodel.EffectiveMetamodel;

public class Demonstration {

	public static void main(String[] args) throws Exception {
		EffectiveMetamodel effectiveMetamodel = new EffectiveMetamodel("DOM");
		
		/* 
		 EffectiveType typeDeclaration = effectiveMetamodel.addToAllOfKind("TypeDeclaration");
		 */
		
		effectiveMetamodel.addToAllOfKind("TypeDeclaration");
		effectiveMetamodel.addReferenceToAllOfKind("TypeDeclaration", "bodyDeclarations");
		effectiveMetamodel.addReferenceToAllOfKind("TypeDeclaration", "name");
		
		effectiveMetamodel.addToAllOfKind("BodyDeclaration");
		effectiveMetamodel.addToAllOfKind("AbstractTypeDeclaration");
		effectiveMetamodel.addToAllOfKind("SimpleName");
		effectiveMetamodel.addAttributeToAllOfKind("SimpleName", "fullyQualifiedName");
		
		effectiveMetamodel.addToAllOfKind("MethodDeclaration");
		effectiveMetamodel.addReferenceToAllOfKind("MethodDeclaration", "modifiers");
		effectiveMetamodel.addReferenceToAllOfKind("MethodDeclaration", "returnType");
		
		effectiveMetamodel.addToAllOfKind("Type");
		effectiveMetamodel.addToAllOfKind("SimpleType");
		
		effectiveMetamodel.addReferenceToAllOfKind("SimpleType", "name");
		
		effectiveMetamodel.addToAllOfKind("Name");
		effectiveMetamodel.addAttributeToAllOfKind("Name", "fullyQualifiedName");
		
		effectiveMetamodel.addToAllOfKind("Modifier");
		effectiveMetamodel.addAttributeToAllOfKind("Modifier", "static");
		effectiveMetamodel.addAttributeToAllOfKind("Modifier", "public");
		
		/*
		SmartSAXLoad smartSAXLoad = new SmartSAXLoad();
		smartSAXLoad.addMetamodelFileUri("model/JDTAST.ecore");
		smartSAXLoad.setModelUri("model/set0.xmi");

		smartSAXLoad.addEffectiveMetamodel(effectiveMetamodel);
		smartSAXLoad.reconcileEffectiveMetamodel();
		
		System.out.println("loading...");
		smartSAXLoad.loadModelFromUri();
		System.out.println(smartSAXLoad.getModelImpl().getContents().size());
		System.out.println("loading finished");
		*/
		ResourceSet resourceSet = new ResourceSetImpl();
		
		ResourceSet ecoreResourceSet = new ResourceSetImpl();
		ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource ecoreResource = ecoreResourceSet.createResource(URI.createFileURI(new File("model/JDTAST.ecore").getAbsolutePath()));
		ecoreResource.load(null);
		for (EObject o : ecoreResource.getContents()) {
			EPackage ePackage = (EPackage) o;
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartSAXModelResourceFactory() /*new XMIResourceFactoryImpl()*/);
		Resource resource = resourceSet.createResource(URI.createFileURI(new File("model/set0.xmi").getAbsolutePath()));
		Map<String, Object> loadOptions = new HashMap<String, Object>();
		loadOptions.put(SmartSAXXMIResource.OPTION_EFFECTIVE_METAMODELS, effectiveMetamodel);
		resource.load(loadOptions);
		for (EObject o : resource.getContents()) {
			System.out.println(o);
		}
		
	}
}
