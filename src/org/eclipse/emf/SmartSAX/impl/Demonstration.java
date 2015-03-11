package org.eclipse.emf.SmartSAX.impl;

import org.eclipse.emf.SmartSAX.effectiveMetamodel.EffectiveMetamodel;

public class Demonstration {

	public static void main(String[] args) throws Exception {
		EffectiveMetamodel DOM = new EffectiveMetamodel("DOM");
		DOM.addToAllOfKind("TypeDeclaration");
		DOM.addReferenceToAllOfKind("TypeDeclaration", "bodyDeclarations");
		DOM.addReferenceToAllOfKind("TypeDeclaration", "name");
		
		DOM.addToAllOfKind("BodyDeclaration");
		DOM.addToAllOfKind("AbstractTypeDeclaration");
		DOM.addToAllOfKind("SimpleName");
		DOM.addAttributeToAllOfKind("SimpleName", "fullyQualifiedName");
		
		DOM.addToAllOfKind("MethodDeclaration");
		DOM.addReferenceToAllOfKind("MethodDeclaration", "modifiers");
		DOM.addReferenceToAllOfKind("MethodDeclaration", "returnType");
		
		DOM.addToAllOfKind("Type");
		DOM.addToAllOfKind("SimpleType");
		
		DOM.addReferenceToAllOfKind("SimpleType", "name");
		
		DOM.addToAllOfKind("Name");
		DOM.addAttributeToAllOfKind("Name", "fullyQualifiedName");
		
		DOM.addToAllOfKind("Modifier");
		DOM.addAttributeToAllOfKind("Modifier", "static");
		DOM.addAttributeToAllOfKind("Modifier", "public");
		
		
		SmartSAXLoad smartSAXLoad = new SmartSAXLoad();
		smartSAXLoad.addMetamodelFileUri("model/JDTAST.ecore");
		smartSAXLoad.setModelUri("model/set0.xmi");

		smartSAXLoad.addEffectiveMetamodel(DOM);
		smartSAXLoad.preProcess();
		
		System.out.println("loading...");
		smartSAXLoad.loadModelFromUri();
		//System.out.println(smartSAXLoad.getModelImpl().getContents());
		System.out.println("loading finished");
	}
}
