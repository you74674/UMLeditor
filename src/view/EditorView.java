package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Stack;

import javax.swing.JOptionPane;

import task.Task;
import task.TaskFactory;
import view.base.EditArea;
import view.listener.DrawListener;
import view.uml.CompositeView;
import view.uml.ObjectView;
import view.uml.basic.BasicView;
import view.uml.basic.PortView;
import view.uml.line.LineView;


/**
 * 
 * handle only showing editor and get user control: drawListener
 *
 */
public class EditorView extends EditArea implements ActionListener, ItemListener{
	private Task task;
	private DrawListener drawListener;
	private ObjectView[] selected;
	
	public void setComponents(){
		setLayout(null);
		setBackground(Config.editorBackgroundColor);
		
		setComponentPopupMenu(new PopupMenu());
	}
	public EditorView(MainFrame parent){
		super();
		
		parent.addItemListener(this);
		parent.addActionListener(this);

//		editor=parent.getModel().getEditor();
		
		setComponents();
		
		//listener
		drawListener=new DrawListener(this);
		addMouseListener(drawListener);
		addMouseMotionListener(drawListener);
		
		selected=new ObjectView[]{};
	}
	public void setTask(Task task){
		this.task=task;
	}
	public Task getTask() {
		return task;
	}
	
	public ObjectView[] getSelected() {
		return selected;
	}
	public void setSelected(ObjectView[] selected) {
		for(ObjectView objectView: this.selected)
			objectView.setSelected(false);
		this.selected = selected;
		for(ObjectView objectView: selected)
			objectView.setSelected(true);
	}
	public void changeName(ObjectView[] selected){
		if(selected.length==1)
			if(selected[0] instanceof BasicView){
				String newName=JOptionPane.showInputDialog("Enter new name:");
				if(newName!=null){
					((BasicView)selected[0]).setName(newName);
					selected[0].repaint();
				}
			}
	}
	public void group(ObjectView[] selected){
		//make group!
		if(selected.length>=2){
			int minX=getWidth(), minY=getHeight(),
				maxX=0, maxY=0;
			CompositeView compositeView=new CompositeView();
//			CompositeObject object=new CompositeObject();
//			compositeView.setObject(object);
			for(Component component: selected){
				minX=Math.min(component.getX(), minX);
				minY=Math.min(component.getY(), minY);
				maxX=Math.max(component.getX()+component.getWidth(), maxX);
				maxY=Math.max(component.getY()+component.getHeight(), maxY);
			}
			compositeView.setLocation(minX, minY);
			compositeView.setSize(maxX-minX, maxY-minY);
			for(Component component: selected){
				remove(component);
				
				component.setLocation(component.getX()-minX, component.getY()-minY);
				compositeView.add(component);
			}
//			compositeView.setVisible(true);
			add(compositeView);
			repaint();
			compositeView.validate();
			
			setSelected(new ObjectView[]{compositeView});
		}
	}
	public void ungroup(ObjectView[] selected){
		for(ObjectView objectView: selected){
			if(objectView instanceof CompositeView){
				CompositeView compositeView=(CompositeView) objectView;
				for(Component component: compositeView.getComponents()){
					component.setLocation(component.getX()+compositeView.getX(), component.getY()+compositeView.getY());
					compositeView.remove(component);
					add(component);
				}
				remove(compositeView);
			}
		}
	}	
	
	//draw lines...no idea how to do.
	@Override
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		
		Stack<Container> S=new Stack<Container>();
		S.push(this);
		for(Container container=S.pop(); ;container=S.pop()){
			for(Component component: container.getComponents()){
				if(component instanceof BasicView){
					for(PortView portView: ((BasicView)component).getPorts()){
						for(LineView lineView: portView.getLineViews()){
							Point fromPoint=portView.getAbsoluteLocation();
							Point toPoint=lineView.getTo().getAbsoluteLocation();
							g.drawLine(fromPoint.x+5, fromPoint.y+5, toPoint.x+5, toPoint.y+5);
						}
					}
				}else if(component instanceof CompositeView)
					S.push((Container) component);
			}
			if(S.isEmpty())
				break;
		}
	};

	//listen to task change
	@Override
	public void itemStateChanged(ItemEvent e) {
		setSelected(new ObjectView[]{});
		
		task=new TaskFactory().getTask(e.getItem().toString());
	}
	
	//TODO: move to somewhere...
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().startsWith("change")){
			changeName(selected);
		}
		else if(e.getActionCommand().startsWith("group")){
			group(selected);
		}
		else if(e.getActionCommand().startsWith("ungroup")){
			ungroup(selected);
		}
	}

}
