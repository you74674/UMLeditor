package model.uml.object;

public class CompositeObject extends Object{
	private Object[] objects;
	//TODO: complete composite function
	public CompositeObject(Object[] objects){
//		for(Object object: objects){
//			if(object.getLocation())
//		}
		this.setObjects(objects);
	}

	public CompositeObject() {
		// TODO Auto-generated constructor stub
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}
}
