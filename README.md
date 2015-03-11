# SmartSAX

SmartSAX aims at partial loading XMI-based EMF models to reduce the time and memory consumption for conventional EMF model loader


For Demonstration, please run the main() method in the Demonstration class.

In the Demonstration class, firstly we declare the EffectiveMetamodel by inserting types and features to it. This Effective Metamodel is actually the elements needed to compute the GraBaTe 2009 query. 

After declaring the Effective Metamodel. We create an instance of SmartSAXLoad, we specify the metamodel file and the model file so that SmartSAXLoad can load both. We then add the effective metamodel to SmartSAXLoad and call reconcileEffectiveMetamodel() so that SmartSAXLoad knows which meta-class to create when it comes to the actual loading. 

To load the file, simply call loadModelFromUri() in SmartSAXLoad.
