package model.uml;

import java.util.Arrays;
import java.util.HashSet;

import model.uml.object.Object;

/*	data structure for UML file:
 * 		set of objects
 * 	thats all?
 * */
public class UML{
	private HashSet<Object> objects;

	public UML(){
		objects=new HashSet<Object>();
	}
	
	//save, load
	public HashSet<Object> getObjects() {
		return objects;
	}
	public void setObjects(HashSet<Object> objects) {
		this.objects = objects;
	}
	
	//add, remove
	public void add(Object object) {
		objects.add(object);
	}
	public void remove(Object object) {
		objects.remove(object);
	}
	public void removeAll(Object[] objects){
		this.objects.removeAll(Arrays.asList(objects));
	}
}
