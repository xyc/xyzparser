package semantic;

public class LocalDescriptor extends Descriptor{
	//name of type
	//name of local
	public String typename;
	//String id;

	@Override
	public String toString() {

		String s = "local name: " + id + "  type: " + typename;
		return s;
	}
	
	public String getInfo() {
		return typename+" "+id+";";
	}
	/*
	public static LocalDescriptor createLocalDescriptor(String typename, String name){
		LocalDescriptor desc = new LocalDescriptor();
		desc.name = name;
		desc.type = Type.createType(typename);
		return desc;
	}*/
}
