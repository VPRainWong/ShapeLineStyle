package com.vp.plugin.sample.linestyle.actions;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IClassDiagramUIModel;
import com.vp.plugin.diagram.IComponentDiagramUIModel;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.diagram.IShapeUIModel;
import com.vp.plugin.diagram.format.LineStyle;
import com.vp.plugin.diagram.shape.IClassUIModel;
import com.vp.plugin.diagram.shape.IComponentUIModel;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;

public class LineStyleActionControl implements VPActionController {
	
	private LineStyle[] _styles = {LineStyle.None, LineStyle.Style1, LineStyle.Style2, 
								   LineStyle.Style3, LineStyle.Style4, LineStyle.Style5,
								   LineStyle.Style6, LineStyle.Style7, LineStyle.Style8,
								   LineStyle.Style9, LineStyle.Style10, LineStyle.Style11,
								   LineStyle.Style12, LineStyle.Style13, LineStyle.Style14,
								   LineStyle.Style15, LineStyle.Style16, LineStyle.Style17,
								   LineStyle.Style18, LineStyle.Style19, LineStyle.Style20,
								   LineStyle.Style21, LineStyle.Style22}; 
	
	@Override
	public void performAction(VPAction arg0) {
		IClassUIModel classShape = getClassFromDiagram();
		if (classShape != null) {
			// obtain the original name alignment value
			LineStyle style = classShape.getLineModel().getLineStyle();
			
			// lookup the index of current alignment value 
			// in _alignments array, and increase index by 1
			int styleIndex = 0;
			for (int i = 0; i < _styles.length; i++) {
				if (style == _styles[i]) {
					styleIndex = i+1;
					break;
				}
			}			
			
			// if alignment value is bigger then the length of
			// _alignments array, then use mod to calculate the 
			// reminder value as the new index
			if (styleIndex >= _styles.length) {		
				styleIndex = _styles.length % styleIndex;
			}

			// set the new name alignment
			classShape.getLineModel().setLineStyle(_styles[styleIndex]);
		}			
	}
	
	private IClassUIModel getClassFromDiagram() {
		// obtain the project and loop through the diagrams
		IProject project = ApplicationManager.instance().getProjectManager().getProject();
		
		// loop through the diagrams to locate
		// the one call "Line Style Test"
		IDiagramUIModel[] diagrams = project.toDiagramArray();		
		if (diagrams != null) {
			for (IDiagramUIModel diagram : diagrams) {
				if (diagram instanceof IClassDiagramUIModel && "Line Style Test".equals(diagram.getName())) {
					// once the diagram was found, then see does 
					// it contain a class call "MyClass"
					IDiagramElement[] elements = diagram.toDiagramElementArray();
					if (elements != null) {
						for (IDiagramElement element : elements) {
							// return the diagram if "MyClass" was found
							if (element instanceof IClassUIModel && "MyClass".equals(element.getModelElement().getName())) {
								return (IClassUIModel) element;								
							}
						}						
					}										
				}
			}			
		}
		return createClass();
	}
	
	private IClassUIModel createClass() {
		DiagramManager diagramManager = ApplicationManager.instance().getDiagramManager();
		
		// create class diagram
		IClassDiagramUIModel classDiagram = (IClassDiagramUIModel) diagramManager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_CLASS_DIAGRAM);
		classDiagram.setName("Line Style Test");
		
		// create class shape
		IClass sampleClass = IModelElementFactory.instance().createClass();
		sampleClass.setName("MyClass");
		IClassUIModel sampleClassShape = (IClassUIModel) diagramManager.createDiagramElement(classDiagram, sampleClass);
		sampleClassShape.setBounds(100, 100, 80, 40);
		sampleClassShape.setRequestResetCaption(true);
		
		// show up the diagram
		diagramManager.openDiagram(classDiagram);
		
		// return the class shape
		return sampleClassShape;
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
