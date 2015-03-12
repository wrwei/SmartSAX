# SmartSAX

SmartSAX aims at partial loading XMI-based EMF models to reduce the time and memory consumption for conventional EMF model loader


For Demonstration, please run the main() method in the Demonstration class.
The static method generateEffectiveMetamodel() generates the effective metamodel needed to compute the GraBaTs 2009 query. 
After the effective metamodel is generated, put them in an list, like below:


		ArrayList<EffectiveMetamodel> effectiveMetamodels = new ArrayList<EffectiveMetamodel>();
		EffectiveMetamodel effectiveMetamodel = Demonstration.generateEffectiveMetamodel();
		effectiveMetamodels.add(effectiveMetamodel);
		
		ResourceSet resourceSet = new ResourceSetImpl();
		
		ResourceSet ecoreResourceSet = new ResourceSetImpl();
		ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource ecoreResource = ecoreResourceSet.createResource(URI.createFileURI(new File("model/JDTAST.ecore").getAbsolutePath()));
		ecoreResource.load(null);
		for (EObject o : ecoreResource.getContents()) {
			EPackage ePackage = (EPackage) o;
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartSAXModelResourceFactory());
		Resource resource = resourceSet.createResource(URI.createFileURI(new File("model/set0.xmi").getAbsolutePath()));
		Map<String, Object> loadOptions = new HashMap<String, Object>();
		loadOptions.put(SmartSAXXMIResource.OPTION_EFFECTIVE_METAMODELS, effectiveMetamodels);
		loadOptions.put(SmartSAXXMIResource.OPTION_LOAD_ALL_ATTRIBUTES, true);
		resource.load(loadOptions);
		for (EObject o : resource.getContents()) {
			System.out.println(o);
		}
