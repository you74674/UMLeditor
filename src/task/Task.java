package task;

import java.awt.event.MouseEvent;

/*
 *on canvas
 * 		select:
 * 			click:
 * 				cancel any select
 * 			drag:
 * 				draw rectangle
 * 				release:
 * 					 try to select multiple object
 * 		association line, generalization line, composition line:
 * 			click:
 * 				no effect
 * 			drag:
 * 				no effect
 * 		class, use case:
 * 			click:
 * 				add new object
 * 			drag:
 * 				no effect
 * 		
 * on objects
 * 		select:
 * 			click:
 * 				select the object
 * 			drag:
 * 				move the object
 * 				release:
 * 					stop moving
 * 		association line, generalization line, composition line:
 * 			click:
 * 				no effect
 * 			drag:
 * 				from object to object
 * 				release:
 * 					add line
 * 		class, use case:
 * 			click:
 * 				add new object?
 * 			drag:
 * 				no effect
 * 
 * */
public abstract class Task {

	abstract public void mousePressed(MouseEvent e);
	abstract public void mouseReleased(MouseEvent e);
	abstract public void mouseDragged(MouseEvent e);
	
	abstract public void pressedOnObject(MouseEvent e);
	abstract public void releasedOnObject(MouseEvent e);
	abstract public void draggedOnObject(MouseEvent e);
	
	abstract public void pressedOnCanvas(MouseEvent e);
	abstract public void releasedOnCanvas(MouseEvent e);
	abstract public void draggedOnCanvas(MouseEvent e);
}
