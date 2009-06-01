package semantic;

public class FieldDescriptor extends Descriptor{
	public String typename;
	//String id;

	@Override
	public String toString() {
		String s = "field name: " + id + "  type: " + typename;
		return s;
	}
	
	public String getInfo() {
		return typename+" "+id+";";
	}
}
