package org.openforis.collect.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.openforis.collect.metamodel.SurveyViewGenerator.SurveyView;
import org.openforis.collect.metamodel.ui.UIField;
import org.openforis.collect.metamodel.ui.UIForm;
import org.openforis.collect.metamodel.ui.UIFormComponent;
import org.openforis.collect.metamodel.ui.UIFormContentContainer;
import org.openforis.collect.metamodel.ui.UIFormSection;
import org.openforis.collect.metamodel.ui.UIFormSet;
import org.openforis.collect.metamodel.ui.UITable;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.FileAttributeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.TextAttributeDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;

public class UIConfigurationViewGenerator {

	private Locale locale;
	private CollectSurvey survey;
	private SurveyView surveyView;
	private UIConfigurationView result;
	
	public UIConfigurationViewGenerator(CollectSurvey survey, SurveyView surveyView, Locale locale) {
		this.survey = survey;
		this.surveyView = surveyView;
		this.locale = locale;
	}

	public UIConfigurationView generate() {
		result = new UIConfigurationView();
		for (UIFormSet formSet : survey.getUIConfiguration().getFormSets()) {
			result.formSets.add(generateUIFormSetView(formSet));
		}
		return result;
	}
	
	private UIFormSetView generateUIFormSetView(UIFormSet formSet) {
		UIFormSetView view = new UIFormSetView(formSet.getId());
		view.rootEntityDefinitionId = formSet.getRootEntityDefinitionId();
		for (UIForm form : formSet.getForms()) {
			view.forms.add(generateUIFormView(form));
		}
		return view;
	}
	
	private <C extends UIFormContentContainer, V extends UIFormContentContainerView> V 
		generateUIFormContentContainerView(Class<V> viewType, C formContentContainer) {
		try {
			V view = viewType.getConstructor(Integer.TYPE).newInstance(formContentContainer.getId());
			for (UIForm form : formContentContainer.getForms()) { 
				view.forms.add(generateUIFormView(form));
			}
			for (UIFormComponent child : formContentContainer.getChildren()) {
				view.children.add(generateUIFormComponent(child));
			}
			return view;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private UIFormView generateUIFormView(UIForm form) {
		UIFormView view = generateUIFormContentContainerView(UIFormView.class, form);
		view.label = extractLabel(form);
		return view;
	}

	@SuppressWarnings("unchecked")
	private <C extends UIFormComponent, V extends UIFormComponentView> V generateUIFormComponent(C component) {
		V view;
		if (component instanceof UIField) {
			AttributeDefinition attrDef = ((UIField) component).getAttributeDefinition();
			UIFieldView v = new UIFieldView(component.getId());
			if (attrDef instanceof BooleanAttributeDefinition) {
				v.attributeType = "boolean";
			} else if (attrDef instanceof CodeAttributeDefinition) {
				v.attributeType = "code";
			} else if (attrDef instanceof CoordinateAttributeDefinition) {
				v.attributeType = "coordinate";
			} else if (attrDef instanceof DateAttributeDefinition) {
				v.attributeType = "date";
			} else if (attrDef instanceof FileAttributeDefinition) {
				v.attributeType = "file";
			} else if (attrDef instanceof NumberAttributeDefinition) {
				v.attributeType = "number";
			} else if (attrDef instanceof RangeAttributeDefinition) {
				v.attributeType = "range";
			} else if (attrDef instanceof TextAttributeDefinition) {
				v.attributeType = "text";
			} else if (attrDef instanceof TimeAttributeDefinition) {
				v.attributeType = "time";
			} else {
				throw new IllegalArgumentException("Unsupported field type: " + attrDef.getClass().getName());
			}
			v.attributeDefinitionId = attrDef.getId();
			view = (V) v;
		} else if (component instanceof UIFormSection) {
			UIFormSectionView v = new UIFormSectionView(component.getId());
			UIFormSection uiFormSection = (UIFormSection) component;
			EntityDefinition entityDef = uiFormSection.getEntityDefinition();
			v.multiple = entityDef.isMultiple();
			v.entityDefinitionId = entityDef.getId();
			view = (V) v;
		} else if (component instanceof UITable) {
			UITableView v = new UITableView(component.getId());
			v.entityDefinitionId = ((UITable) component).getEntityDefinitionId();
			view = (V) v;
		} else {
			throw new IllegalArgumentException("Unsupported ui object type: " + component.getClass().getName());
		}
		return view;
	}

	private String extractLabel(UIForm uiForm) {
		String label = uiForm.getLabel(locale.getLanguage());
		if (label == null && ! survey.isDefaultLanguage(locale.getLanguage())) {
			uiForm.getLabel(survey.getDefaultLanguage());
		}
		return label;
	}
	
	public static class UIConfigurationView {
		private List<UIFormSetView> formSets = new ArrayList<UIFormSetView>();
		
		public List<UIFormSetView> getFormSets() {
			return formSets;
		}
	}
	
	public static abstract class UIModelObjectView {
		private int id;
		private String type;
		
		public UIModelObjectView(int id, String type) {
			super();
			this.id = id;
			this.type = type;
		}

		public int getId() {
			return id;
		}
		
		public String getType() {
			return type;
		}
	}
	
	public static class UIFormSetView extends UIModelObjectView {
		private Integer rootEntityDefinitionId;
		private List<UIFormView> forms = new ArrayList<UIFormView>();
		
		public UIFormSetView(int id) {
			super(id, "form_set");
		}

		public Integer getRootEntityDefinitionId() {
			return rootEntityDefinitionId;
		}
		
		public List<UIFormView> getForms() {
			return forms;
		}
	}
	
	public static abstract class UIFormContentContainerView extends UIModelObjectView {
		List<UIFormComponentView> children = new ArrayList<UIFormComponentView>();
		List<UIFormView> forms = new ArrayList<UIFormView>();
		
		public UIFormContentContainerView(int id, String type) {
			super(id, type);
		}

		public List<UIFormComponentView> getChildren() {
			return children;
		}
		
		public List<UIFormView> getForms() {
			return forms;
		}
	}
	
	public static class UIFormView extends UIFormContentContainerView {
		private String label;

		public UIFormView(int id) {
			super(id, "form");
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	public static abstract class UIFormComponentView extends UIModelObjectView {
		public UIFormComponentView(int id, String type) {
			super(id, type);
		}
	}
	
	public static class UIFieldView extends UIFormComponentView {
		private int attributeDefinitionId;
		private String attributeType;
		
		public UIFieldView(int id) {
			super(id, "field");
		}
		
		public String getAttributeType() {
			return attributeType;
		}
		
		public int getAttributeDefinitionId() {
			return attributeDefinitionId;
		}
		
	}
	
	public static class UIFormSectionView extends UIFormComponentView {
		private int entityDefinitionId;
		private boolean multiple = false;
		
		public UIFormSectionView(int id) {
			super(id, "section");
		}
		
		public int getEntityDefinitionId() {
			return entityDefinitionId;
		}
		
		public boolean isMultiple() {
			return multiple;
		}
	}
	
	public static class UITableView extends UIFormComponentView {
		private int entityDefinitionId;
		
		public UITableView(int id) {
			super(id, "table");
		}
		
		public int getEntityDefinitionId() {
			return entityDefinitionId;
		}
	}
}
