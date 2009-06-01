package semantic;

public class ParamDescriptor extends Descriptor{
	//name of type
	//name of param
	public String typename;
	//String id;

	@Override
	public String toString() {
		String s = "para name: " + id + "  type: " + typename;
		return s;
	}
	
	public String getInfo() {
		return typename+" "+id;
	}
}
