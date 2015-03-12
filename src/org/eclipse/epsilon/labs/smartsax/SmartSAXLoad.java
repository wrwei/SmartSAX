package org.eclipse.epsilon.labs.smartsax;


public class SmartSAXLoad {
//
//	protected ArrayList<EffectiveMetamodel> effectiveMetamodels = new ArrayList<EffectiveMetamodel>();
//	
//	protected HashMap<String, HashMap<String, ArrayList<String>>> objectsAndRefNamesToVisit = new HashMap<String, HashMap<String,ArrayList<String>>>();
//	protected HashMap<String, HashMap<String, ArrayList<String>>> actualObjectsToLoad = new HashMap<String, HashMap<String,ArrayList<String>>>();
//
//	protected ArrayList<EPackage> packages;
//	protected ArrayList<URI> metamodelFileUris = new ArrayList<URI>();
//	protected URI modelUri;
//	
//	protected boolean loadAllAttributes = false;
//	protected EffectiveMetamodelReconciler effectiveMetamodelReconciler = new EffectiveMetamodelReconciler();
//	
//	protected Resource modelImpl;
//	
//	public void addEffectiveMetamodel(EffectiveMetamodel effectiveMetamodel)
//	{
//		effectiveMetamodels.add(effectiveMetamodel);
//	}
//	
//	public void addMetamodelFileUri(String fileName)
//	{
//		metamodelFileUris.add(URI.createURI(fileName));
//	}
//	
//	public void setModelUri(String fileName) {
//		modelUri = URI.createURI(fileName);
//	}
//	
//	public void reconcileEffectiveMetamodel() throws Exception
//	{
//		ResourceSet resourceSet = createResourceSet();
//		
//        // Check if global package registry contains the EcorePackage
//		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
//			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
//		}
//		
//		determinePackagesFrom(resourceSet);
//		for(EPackage ePackage: packages)
//		{
//			effectiveMetamodelReconciler.addPackage(ePackage);
//		}
//		
//		for(EffectiveMetamodel em: effectiveMetamodels)
//		{
//			effectiveMetamodelReconciler.addEffectiveMetamodel(em);
//		}
//		effectiveMetamodelReconciler.reconcile();
//		
//		objectsAndRefNamesToVisit = effectiveMetamodelReconciler.getObjectsAndRefNamesToVisit();
//		actualObjectsToLoad = effectiveMetamodelReconciler.getActualObjectsToLoad();
//
//	}
//
//	
//	public void loadModelFromUri() throws Exception {
//		ResourceSet resourceSet = createResourceSet();
//		
//        // Check if global package registry contains the EcorePackage
//		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
//			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
//		}
//		
//		determinePackagesFrom(resourceSet);
//		
//		for (EPackage ep : packages) {
//			String nsUri = ep.getNsURI();
//			if (nsUri == null || nsUri.trim().length() == 0) {
//				nsUri = ep.getName();
//			}
//			resourceSet.getPackageRegistry().put(nsUri, ep);
//		}
//		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
//				
//		Resource model = resourceSet.createResource(modelUri);
//		try {
//			model.load(null);
//			EcoreUtil.resolveAll(model);
//		} catch (IOException e) {
//			// Unload the model, so it will not be wrongly cached as "loaded",
//			// causing the intermittent errors produced in bug #386255
//			model.unload();
//			throw new Exception(e);
//		}
//		modelImpl = model;
//	}
//	
//	public Resource getModelImpl() {
//		return modelImpl;
//	}
//
//	protected ResourceSet createResourceSet() {
//		ResourceSet resourceSet = new ResourceSetImpl();
//		SmartSAXModelResourceFactory factory = new SmartSAXModelResourceFactory();
//		factory.setLoadAllAttributes(loadAllAttributes);
//		factory.setObjectsAndRefNamesToVisit(objectsAndRefNamesToVisit);
//		factory.setActualObjectsToLoad(actualObjectsToLoad);
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", factory);   // <----------------------- point of change
//		return resourceSet;
//	}
//	
//	protected void determinePackagesFrom(ResourceSet resourceSet) throws Exception {
//		packages = new ArrayList<EPackage>();
//
//		for (URI metamodelFileUri : this.metamodelFileUris) {
//			List<EPackage> metamodelPackages;
//			try {
//				metamodelPackages = Util.register(metamodelFileUri, resourceSet.getPackageRegistry());
//			} catch (Exception e) {
//				throw new Exception(e);
//			}
//			for (EPackage metamodelPackage : metamodelPackages) {
//				packages.add(metamodelPackage);
//				Util.collectDependencies(metamodelPackage, packages);
//			}
//		}	
//	}
//
//
//	public static void main(String[] args) {
//		SmartSAXLoad main = new SmartSAXLoad();
//		main.addMetamodelFileUri("model/JDTAST.ecore");
//		main.setModelUri("model/set0.xmi");
//		try {
//			main.loadModelFromUri();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
